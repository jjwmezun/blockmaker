package blockmaker;

import blockmaker.BlockType;
import blockmaker.MezunUtils;
import java.lang.Math;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public final class TypesGrid
{
	public static void setupGrid( String name_, int w )
	{
		width = w;
		int max_types = MezunUtils.MAX_BLOCK_TYPES;
		if ( name_.equals( "universal" ) )
		{
			max_types = MezunUtils.MAX_UNIVERSAL_TYPES;
		}
		height = ( int )( Math.ceil( max_types / w ) );
		name = name_;
		canvas = new Canvas( getWidthPixels(), getHeightPixels() );
		pane = new Pane( canvas );
		ctx = canvas.getGraphicsContext2D();
		types = new BlockType[ max_types ];
		for ( int i = 0; i < types.length; i++ )
		{
			types[ i ] = new BlockType();
		}

		canvas.setOnMousePressed
		(
			new EventHandler<MouseEvent> ()
			{
				@Override
				public void handle( MouseEvent e )
				{
					int x = MezunUtils.pixelsToBlocks( ( int )( e.getX() ) );
					int y = MezunUtils.pixelsToBlocks( ( int )( e.getY() ) );
					selected = MezunUtils.nFromXAndY( x, y, getWidthBlocks() );
					if ( selected >= 0 && selected < types.length )
					{
						Props.changeView( types[ selected ] );
					}
					draw();
				}
			}
		);
	};
		
	public static void changeGFX( int x, int y )
	{
		if ( selected >= 0 && selected < types.length )
		{
			assert( types[ selected ] != null );
			types[ selected ].setFrame( x, y );
			Props.changeView( types[ selected ] );
		}
		draw();
	};
	
	public static void setRotation( int value )
	{
		if ( selected >= 0 && selected < types.length )
		{
			assert( types[ selected ] != null );
			types[ selected ].setRotation( value );
		}
		draw();
	};
	
	public static void switchFlipX( boolean value )
	{
		if ( selected >= 0 && selected < types.length )
		{
			assert( types[ selected ] != null );
			types[ selected ].switchFlipX( value );
		}
		draw();
	};
	
	public static void switchFlipY( boolean value )
	{
		if ( selected >= 0 && selected < types.length )
		{
			assert( types[ selected ] != null );
			types[ selected ].switchFlipY( value );
		}
		draw();
	};

	public static Pane getPane()
	{
		return pane;
	};
	
	public static void clear()
	{
		if ( ctx != null )
		{
			ctx.clearRect( 0, 0, getWidthPixels(), getHeightPixels() );
		}
	};
	
	public static void draw()
	{
		clear();
		ctx.setFill( BG_COLOR );
		ctx.fillRect( 0, 0, getWidthPixels(), getHeightPixels() );

		for ( int i = 0; i < types.length; i++ )
		{
			assert( types[ i ] != null );
			TypeGFX gfx = types[ i ].getGFX();
			if ( gfx.isSet() )
			{
				int destx = MezunUtils.blocksToPixels( MezunUtils.xFromN( i, getWidthBlocks() ) );
				int destw = MezunUtils.BLOCK_SIZE;
				int desty = MezunUtils.blocksToPixels( MezunUtils.yFromN( i, getWidthBlocks() ) );
				int desth = MezunUtils.BLOCK_SIZE;
				
				if ( gfx.flip_x )
				{
					destw = -destw;
					destx += MezunUtils.BLOCK_SIZE;
				}

				if ( gfx.flip_y )
				{
					desth = -desth;
					desty += MezunUtils.BLOCK_SIZE;
				}

				int srcx  = MezunUtils.blocksToPixels( gfx.getX() );
				int srcy  = MezunUtils.blocksToPixels( gfx.getY() );
				ctx.drawImage
				(
					GFXGrid.getIMG(),
					srcx, srcy, MezunUtils.BLOCK_SIZE, MezunUtils.BLOCK_SIZE,
					destx, desty, destw, desth
				);
			}
		}
		
		highlightBlock();
	};

	private static void highlightBlock()
	{
		if ( selected >= 0 )
		{
			int x = MezunUtils.blocksToPixels( MezunUtils.xFromN( selected, getWidthBlocks() ) );
			int y = MezunUtils.blocksToPixels( MezunUtils.yFromN( selected, getWidthBlocks() ) );
			ctx.setFill( HIGHLIGHT_COLOR );
			ctx.fillRect( x, y, MezunUtils.BLOCK_SIZE, MezunUtils.BLOCK_SIZE );
		}
	};
	
	public static int getWidthPixels()
	{
		return MezunUtils.blocksToPixels( width );
	};
	
	public static int getHeightPixels()
	{
		return MezunUtils.blocksToPixels( height );
	};
	
	public static int getWidthBlocks()
	{
		return width;
	};
	
	public static int getHeightBlocks()
	{
		return height;
	};

	private static final Color BG_COLOR = Color.SLATEGRAY;
	private static final Color HIGHLIGHT_COLOR = new Color( 1, 1, 0, 0.5 );
	private static String name = "";
	private static int width = 16;
	private static int height = 21;
	private static Pane pane = null;
	private static Canvas canvas = null;
	private static GraphicsContext ctx = null;
	private static int selected = -1;
	private static BlockType types[];
};