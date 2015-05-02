package minesweeper;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class Built_mines extends Application{
	final static int screenSize_x = 368;
	final static int screenSize_y = 450;
	private double button_width;
	private double button_height;
	private int y_mem= 0;
	private Button[] button = new Button[(screenSize_x/19*screenSize_y/30 )+3];
	Group root = new Group();
	public void start(Stage stage){
		createButtons();	
		getGrid();
		Scene scene = new Scene(root, screenSize_x, screenSize_y);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("MINESWEEPER GAME");
		
	}	
	private void createButtons() {
		for(int i = 0; i<button.length; i++){
			button[i] = new Button("  ");
		}
		
	}
	private void getGrid(){
		int i=0, y = 0, x=0;
		GridPane grid = new GridPane();
		
		while(i<button.length){ 
			if(i%(16)==0){					//checking weather, we have reached the end of the row.
				y=y+1;
				x=0;
				}
				grid.add(button[i],x, y);
				i++;
				x++;
		}
		//y_mem=y;
		//button_width = button[i].getWidth();
		//button_height = button[i].getHeight();
		//System.out.println(grid.getHeight());
		//System.out.println(button_height);
		root.getChildren().add(grid);
	}

		        // label.setGraphic(new ImageView(getClass().getResource("/images/Folder-icon.png").toExternalForm()));

	private Rectangle getSizeOfCell(double width, double height){
		Rectangle cell = new Rectangle(width, height);
		return cell;
	}

	public static void main(String[] args){
		launch(args);
	}
}