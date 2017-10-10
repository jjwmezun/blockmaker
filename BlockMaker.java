import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.*;

import blockmaker.GFXGrid;
import blockmaker.Props;
import blockmaker.MezunUtils;
import blockmaker.TypesGrid;

public final class BlockMaker extends Application
{
	public void makeNew( String tileset_name, int types_width )
	{
		int menu_height = ( int )( menu_bar.heightProperty().get() );
		TypesGrid.clear();
		GFXGrid.clear();
		root.getChildren().remove( TypesGrid.getPane() );
		root.getChildren().remove( GFXGrid.getPane() );
		TypesGrid.setupGrid( tileset_name, types_width );
		GFXGrid.setupGFX( tileset_name );
		TypesGrid.getPane().setTranslateY( menu_height );
		GFXGrid.getPane().setTranslateY( menu_height );
		root.getChildren().add( TypesGrid.getPane() );
		root.getChildren().add( GFXGrid.getPane() );
		TypesGrid.draw();
		GFXGrid.draw();
		
		if ( !Props.getInitiated() )
		{
			Props.init();
			root.getChildren().add( Props.getPane() );
		}
	};
	
	public void load( String tileset_name )
	{
		String file_path = "file:/home/jjwmezun/Documents/boskeopolis-land/resources/tilesets/tiled_files/" + tileset_name + "t.png";
		Image tileset_img_file = new Image( file_path );
		
		if ( !tileset_img_file.isError() )
		{
			int w = MezunUtils.pixelsToBlocks( ( int )( tileset_img_file.getWidth() ) );
			makeNew( tileset_name, w );
		}
		else
		{
			Alert missing_dir_error = new Alert( Alert.AlertType.ERROR, "\"" + file_path + "\" is missing." );
			missing_dir_error.setHeaderText( "ERROR: Missing directory" );
			missing_dir_error.showAndWait();
		}
	};

	public static void main( String args[] )
	{
		launch( args );
	};

	public void start( Stage stage_ )
	{
		stage = stage_;
		stage.setTitle( "Block Maker" );
		root = new Pane();
		Scene scene = new Scene( root, WIDTH, HEIGHT );
		stage.setScene( scene );
		root.getChildren().add( makeMenus() );
		stage.show();
	};

	public MenuBar makeMenus()
	{
		menu_bar = new MenuBar();
		final Menu menu_file = new Menu( "File" );
		final MenuItem menu_file_new = new Menu( "New" );
		final MenuItem menu_file_open = new Menu( "Open" );
		final MenuItem menu_file_save = new Menu( "Save" );
		menu_file.getItems().add( menu_file_new );
		menu_file.getItems().add( menu_file_open );
		menu_file.getItems().add( menu_file_save );
		menu_bar.getMenus().add( menu_file );

		menu_file_new.setOnAction
		(
			new EventHandler<ActionEvent>()
			{
				public void handle( ActionEvent t )
				{
					File gfx_dir = new File( "/home/jjwmezun/Documents/boskeopolis-land/resources/img/tilesets/" );
					if ( gfx_dir.exists() )
					{
						FileChooser prompt = new FileChooser();
						prompt.setInitialDirectory( gfx_dir );
						prompt.setTitle( "Open Tileset Image" );
						prompt.getExtensionFilters().add( new FileChooser.ExtensionFilter( "Image Files", "*.png" ) );
						File img_file = prompt.showOpenDialog( stage );

						if ( img_file != null )
						{
							String filename = img_file.getName();
							if ( filename.indexOf( "." ) > 0 )
							{
    							filename = filename.substring( 0, filename.lastIndexOf( "." ) );
							}
							makeNew( filename, MezunUtils.DEFAULT_TILESET_WIDTH );
						}
					}
					else
					{
						Alert missing_dir_error = new Alert( Alert.AlertType.ERROR, "/home/jjwmezun/Documents/boskeopolis-land/\nresources/img/tilesets/ is missing." );
						missing_dir_error.setHeaderText( "ERROR: Missing directory" );
						missing_dir_error.showAndWait();
					}
				}
			}
		);

		menu_file_open.setOnAction
		(
			new EventHandler<ActionEvent>()
			{
				public void handle( ActionEvent t )
				{
					File tilesets_dir = new File( "/home/jjwmezun/Documents/boskeopolis-land/resources/tilesets/" );
					if ( tilesets_dir.exists() )
					{
						DirectoryChooser prompt = new DirectoryChooser();
						prompt.setInitialDirectory( tilesets_dir );
						prompt.setTitle( "Open Tileset Folder" );
						File dir = prompt.showDialog( stage );

						if ( dir != null )
						{
							load( dir.getName() );
						}
					}
				}
			}
		);

		return menu_bar;
	};

	private static final int WIDTH = 800;
	private static final int HEIGHT = 1200;
	private Stage stage = null;
	private Pane root = null;
	private MenuBar menu_bar = null;
};