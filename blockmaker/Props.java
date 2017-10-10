package blockmaker;

import blockmaker.TypeGFX;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public final class Props
{
	public static void init()
	{
		final int ITEM_HEIGHT = 20;
		int y = 8;
		pane = new Pane();
		pane.setTranslateX( x );

		ObservableList<String> options = FXCollections.observableArrayList();
		for ( TypeGFX.Type t : TypeGFX.Type.values() )
		{
			options.add( t.name() );
		}
		options_box = new ComboBox( options );
		options_box.getSelectionModel().selectFirst();
		options_box.setTranslateX( 8 );
		options_box.setTranslateY( y );
		options_box.valueProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override public void changed( ObservableValue value, String prev, String current )
				{
					//BlockGFXTypesMediator.getTypes().changeGFXType( current );
					//changeView( BlockGFXTypesMediator.getTypes().getSelectedType() );
				}
			}
		);
		pane.getChildren().add( options_box );
		y += 64;

		gfx_x = new Text( 8, y, "X: N/A" );
		pane.getChildren().add( gfx_x );
		y += ITEM_HEIGHT;

		gfx_y = new Text( 8, y, "Y: N/A" );
		pane.getChildren().add( gfx_y );
		y += ITEM_HEIGHT;

		HBox flip_x_box = new HBox();
		flip_x_checkbox = new CheckBox();
		Label flip_x_text = new Label( "Flip X:" );
		flip_x_box.getChildren().addAll( flip_x_text, flip_x_checkbox );
		flip_x_box.setTranslateY( y );
		pane.getChildren().add( flip_x_box );
		
		flip_x_checkbox.selectedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
        		public void changed( ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val )
				{
                	TypesGrid.switchFlipX( new_val );
        		}
    		}
		);
		
		y += ITEM_HEIGHT;


		HBox flip_y_box = new HBox();
		flip_y_checkbox = new CheckBox();
		Label flip_y_text = new Label( "Flip Y:" );
		flip_y_box.getChildren().addAll( flip_y_text, flip_y_checkbox );
		flip_y_box.setTranslateY( y );
		pane.getChildren().add( flip_y_box );
		
		flip_y_checkbox.selectedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
        		public void changed( ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val )
				{
                	TypesGrid.switchFlipY( new_val );
        		}
    		}
		);
		y += ITEM_HEIGHT * 2;
		
		int rotation_x = 0;
		final int ROTATION_WIDTH = 64;
		final Label rotation_label = new Label( "Rotation:" );
		rotation_label.setTranslateY( y );
		pane.getChildren().add( rotation_label );
		y += ITEM_HEIGHT;
		final ToggleGroup rotation_group = new ToggleGroup();
		rotation_button_none = new RadioButton( "0°" );
		rotation_button_none.setToggleGroup( rotation_group );
		rotation_button_none.setTranslateY( y );
		rotation_button_none.setTranslateX( rotation_x );
		rotation_button_none.setSelected( true );
		rotation_button_none.setUserData( "0" );
		rotation_x += ROTATION_WIDTH;
		pane.getChildren().add( rotation_button_none );
		rotation_button_90 = new RadioButton( "90°" );
		rotation_button_90.setToggleGroup( rotation_group );
		rotation_button_90.setTranslateY( y );
		rotation_button_90.setTranslateX( rotation_x );
		rotation_button_90.setUserData( "90" );
		rotation_x += ROTATION_WIDTH;
		pane.getChildren().add( rotation_button_90 );
		rotation_button_180 = new RadioButton( "180°" );
		rotation_button_180.setToggleGroup( rotation_group );
		rotation_button_180.setTranslateY( y );
		rotation_button_180.setTranslateX( rotation_x );
		rotation_button_180.setUserData( "180" );
		rotation_x += ROTATION_WIDTH;
		pane.getChildren().add( rotation_button_180 );
		rotation_button_270 = new RadioButton( "270°" );
		rotation_button_270.setToggleGroup( rotation_group );
		rotation_button_270.setTranslateY( y );
		rotation_button_270.setTranslateX( rotation_x );
		rotation_button_270.setUserData( "270" );
		pane.getChildren().add( rotation_button_270 );
		
		rotation_group.selectedToggleProperty().addListener
		(
			new ChangeListener<Toggle>()
			{
    			public void changed( ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle )
				{
            		if ( rotation_group.getSelectedToggle() != null )
					{
						TypesGrid.setRotation( Integer.parseInt( rotation_group.getSelectedToggle().getUserData().toString() ) );
            		}
        		}
			}
		);

		initiated = true;
	};
	
	public static void changeView( final BlockType type )
	{
		final TypeGFX gfx = type.getGFX();

		if ( gfx.isSet() )
		{
			options_box.getSelectionModel().select( gfx.getTypeString() );

			String x = "N/A";
			String y = "N/A";

			if ( gfx.getX() > -1 )
			{
				x = String.valueOf( gfx.getX() );
			}

			if ( gfx.getY() > -1 )
			{
				y = String.valueOf( gfx.getY() );
			}

			gfx_x.setText( "X: " + x );
			gfx_y.setText( "Y: " + y );

			if ( gfx.flip_x )
			{
				flip_x_checkbox.setSelected( true );
			}
			else
			{
				flip_x_checkbox.setSelected( false );
			}

			if ( gfx.flip_y )
			{
				flip_y_checkbox.setSelected( true );
			}
			else
			{
				flip_y_checkbox.setSelected( false );
			}
			
			switch ( gfx.rotation )
			{
				case ( 90 ):
					rotation_button_90.setSelected( true );
				break;
				case ( 180 ):
					rotation_button_180.setSelected( true );
				break;
				case ( 270 ):
					rotation_button_270.setSelected( true );
				break;
				case ( 0 ):
				default:
					rotation_button_none.setSelected( true );
				break;
			}
		}
		else
		{
			gfx_x.setText( "X: N/A" );
			gfx_y.setText( "Y: N/A" );
			flip_x_checkbox.setSelected( false );
			flip_y_checkbox.setSelected( false );
			rotation_button_none.setSelected( true );
			options_box.getSelectionModel().select( TypeGFX.Type.NULL.name() );
		}
	};

	public static final Pane getPane()
	{
		return pane;
	};
	
	public static boolean getInitiated()
	{
		return initiated;
	};

	private static final int HEIGHT = 600;
	private static int x = 256 * 2;
	private static int width = 800 - x;
	private static Pane pane = null;
	private static Text gfx_x;
	private static Text gfx_y;
	private static CheckBox flip_x_checkbox;
	private static CheckBox flip_y_checkbox;
	private static RadioButton rotation_button_none;
	private static RadioButton rotation_button_90;
	private static RadioButton rotation_button_180;
	private static RadioButton rotation_button_270;
	private static ComboBox options_box;
	private static boolean initiated;
};