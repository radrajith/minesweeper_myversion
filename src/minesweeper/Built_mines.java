package minesweeper;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.shape.Rectangle;
import javafx.event.Event;
import javafx.event.EventHandler;
public class Built_mines extends Application{
	final static int screenSize_x = 368;
	final static int screenSize_y = 450;
	private double button_width = 0.0;
	private double button_height = 0.0;
	private int y_mem= 0;
	private Button[] button = new Button[(screenSize_x/19*screenSize_y/30 )+3];
	
	public void start(Stage stage){
		//createButtons();		
		GridPane grid = new GridPane();	
		Group root = new Group();
		//root.getChildren().addAll(getSizeOfCell(button_width, button_height));
		Control[][] matrix = new Control[(int)(screenSize_x/16)][(int)(screenSize_y/18)];
		for(int m=0; m<=16;m++){
		    for(int n=0; n<=30; n++){
				root.getChildren().add(matrix[m][n]);
				int pos = (m*(screenSize_y/18))+n;
				button[pos] = new Button(" ");
				grid.add(button[pos] , m, n);
				mouseClick(pos);
			}
		}
		Scene scene = new Scene(root, screenSize_x, screenSize_y);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("MINESWEEPER GAME");
		
	}	
	
	private void mouseClick(int pos){
		button[pos].setOnMouseClicked(new EventHandler<MouseEvent>(){public void handle(MouseEvent event) {System.out.println("Hello World!");}});

	//	button[pos].setOnMouseClicked(new EventHandler<MouseEvent>(){
    //        public void handle(MouseEvent event) {
    //        	if(event.getButton().equals(event.isPrimaryButtonDown())){
    //        	     System.out.println("Hello World!");
    //        	}
    //       }
	//	}
	}
	
	private void createButtons() {
		for(int i = 0; i<button.length; i++){
			button[i] = new Button("  ");
		}
		
	}
	private Pane getGrid(){
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
		y_mem=y;
		button_width = button[i].getWidth();
		button_height = button[i].getHeight();
		System.out.println(grid.getHeight());
		System.out.println(button_height);
		return grid;
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