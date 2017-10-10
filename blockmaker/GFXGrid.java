package blockmaker;

import blockmaker.MezunUtils;
import blockmaker.TypesGrid;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import blockmaker.TypesGrid;

public final class GFXGrid
{
	public static void setupGFX( String name_ )
	{
		name = name_;
		String tiles_path = "file:/home/jjwmezun/Documents/boskeopolis-land/resources/img/tilesets/" + name + ".png";
		img = new Image( tiles_path );
		width = MezunUtils.pixelsToBlocks( ( int )( img.getWidth() ) );
		height = MezunUtils.pixelsToBlocks( ( int )( img.getHeight() ) );
		canvas = new Canvas( getWidthPixels(), getHeightPixels() );
		pane = new Pane( canvas );
		pane.setTranslateX( TypesGrid.getWidthPixels() );
		ctx = canvas.getGraphicsContext2D();

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
					TypesGrid.changeGFX( x, y );
					draw();
				}
			}
		);
	};

	public static Pane getPane()
	{
		return pane;
	};
	
	public static Image getIMG()
	{
		return img;
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
		ctx.drawImage( img, 0, 0 );
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
	private static Image img = null;
	private static int selected = -1;
};