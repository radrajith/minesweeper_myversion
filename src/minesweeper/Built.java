package minesweeper;


import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Control;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.util.Stack;

public class Built extends Application{
	final static int screenSize_x = 368;
	final static int screenSize_y = 450;
	public int temp = 0;
	private Button[] button = new Button[(screenSize_x/19*screenSize_y/30 )+3];
	private int noOfMine = 25;
	private Stack<Integer> stack = new Stack<>();
	private int[][] matrix = new int[18][16];
	private ArrayList<Integer> locationOfMines = new ArrayList<>();
	Group root = new Group();
	public void start(Stage stage){
		generateMine(noOfMine);
		generateNumbers();
		createButtons();	
		getGrid();
		Scene scene = new Scene(root, screenSize_x, screenSize_y);			//setting up the scene, with size of the window
		stage.setScene(scene);												//adding the scene to stage
		stage.show();														//displaying on the screen
		stage.setTitle("MINESWEEPER GAME");									//title of the window
		
	}	
	private void generateMine(int mine) {
  		Random rand = new Random();
  		for(int i = 0; i<=mine; i++){
  			int randpos = rand.nextInt(250);
  			locationOfMines.add(randpos);
  			int x = randpos/16;
  			int y = randpos%16;
  			matrix[x][y] = -1;
		}
	}
	private void generateNumbers(){
		for(int x = 0; x<18 ; x++){
			for(int y=0; y<16; y++){
				int num = 0;
				if(matrix[x][y]==-1){//do nothing
				}
				else{
					num = checkSurrounding(x,y);
						
					matrix[x][y]= num;
				}
			}
		}
	}
	private int checkSurrounding(int x, int y) {
		int num = 0;
		for(int i =-1; i<2; i++){
			for(int j = -1; j<2 ; j++){
				if(checkCorners(x+i,y+j)){	//checking corners to avoid out of bound error.
					if(matrix[x+i][y+j]==-1){
						num++;
					}
				}
			}
		}
		return num;
	}
	private boolean checkCorners(int x, int y) {
		if((x<0)||(y<0)||(x>17)||(y>15))
			return false;
		else
			return true;
	}
	private void createButtons() {
		for(int i = 0; i<button.length; i++){
			button[i] = new Button("  ");
			button[i].setMaxSize(23, 25);
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
				grid.add(buildButtons(i),x, y);
				i++;
				x++;
		}

		root.getChildren().add(grid);
	}
	public Control buildButtons(int pos){	//assigning click functions to buttons
				button[pos] = new Button("  ");
				mouseClick(pos);
				return button[pos];
	}
	private void mouseClick(int pos){
		button[pos].setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY){
					rightClick(pos);
				}
				else if(event.getButton()==MouseButton.SECONDARY){
					leftClick(pos);
				}
			}
		});
	}
	private void rightClick(int pos){
		System.out.println("right click");
		if(button[pos].getGraphic()!=null){
		//do nothing
			}
		else{
		button[pos].setBackground(null);
		int num = getNumber(pos);
			if(num>0&&num<10){
				button[pos].setText(Integer.toString(num));
			}
			else if(num ==0){
				checkForZeros(pos);
			}
			else if(num ==-1){
				gameLost();
			}
		}
	}
	private void checkForZeros(int pos) {
		int x = pos/16;
		int y = pos%16;
		check8Cell(x, y);
		while(stack.size()!=0){
			y = (int) stack.pop();
			x = (int) stack.pop();
			check8Cell(x,y);
		}
	}

	private void check8Cell(int x, int y) {
		for(int i =-1; i<2; i++){
			for(int j = -1; j<2 ; j++){
				if(checkCorners(x+i,y+j)){	//checking corners to avoid out of bound error.
					if(matrix[x+i][y+j]==0){
						disappearButton(x+i,y+j);
						stack.push(new Integer(x+i));
						stack.push(new Integer(y+j));
					}
					else{
						int pos = ((x+i)*16)+(y+j);
						if(getNumber(pos)!=10)
						button[pos].setText(Integer.toString(getNumber(pos)));
						button[pos].setBackground(null);
					}
				}
			}
		}	
	}
	private void disappearButton(int x, int y) {
		int pos = ((x*16)+(y));
		button[pos].setText("  ");
		button[pos].setBackground(null);
		matrix[x][y] = 10;
		System.out.println("x="+(x));
		System.out.println("y="+(y));
	}
	private void leftClick(int pos){
		//have to redo this flag image on the button, Since its too small.
		if(temp<=locationOfMines.size()){
			if(button[pos].getBackground()!=null){
				
				if(button[pos].getGraphic()!=null){
					button[pos].setGraphic(null);
					button[pos].setText("  ");
				}
				else{
					Image flag = new Image(getClass().getResourceAsStream("flag.png"));
					ImageView imgSize = new ImageView();
					imgSize.setFitWidth(button[pos].getWidth()-16);
					imgSize.setFitHeight(button[pos].getHeight()-8);
					imgSize.setPreserveRatio(true);
					imgSize.fitHeightProperty();
					imgSize.fitWidthProperty();
					imgSize.setImage(flag);
					button[pos].setText("");
					button[pos].setGraphic(imgSize);
					System.out.println(button[pos].getGraphicTextGap());
				}
			}
			temp++;
		}
		if(temp>=locationOfMines.size()){
			checkGameWon();
		}
	}
	
	private int getNumber(int pos){
		int x = pos/16;
		int y = pos%16;
		return matrix[x][y];
	}
	private void gameLost() {
		Text output = new Text(screenSize_x/2-100,screenSize_y/2,"GAME LOST");
		output.setFont(Font.font("TimesNewRoman",40));
		output.setFill(Color.RED);
		root.getChildren().add(output);
		for(int i=0;i<locationOfMines.size(); i++){
		button[locationOfMines.get(i)].setText("x");
		button[locationOfMines.get(i)].setBackground(null);
		}
	}
	private void checkGameWon() {
		for(int i=0;i<locationOfMines.size(); i++){
			int pos = locationOfMines.get(i);
			if(button[pos].getGraphicTextGap() == 4.0){
				if(i==locationOfMines.size()-1){
					gameWon();
				}
			}
		}
	}
	private void gameWon(){
		Text output = new Text(screenSize_x/2-80,screenSize_y/2,"You Won");
		output.setFont(Font.font("TimesNewRoman",40));
		output.setFill(Color.RED);
		root.getChildren().add(output);
	}
	public static void main(String[] args){
		launch(args);
	}
}