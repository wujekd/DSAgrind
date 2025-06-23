package GUI;

import Graphs.UnweightedGraph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;     
import javafx.scene.canvas.GraphicsContext; // Needed for drawing
import javafx.scene.layout.Pane;  


public class Displayer1 extends Application {

	int SCREEN_WIDTH = 600;
	int SCREEN_HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
		int SCREEN_WIDTH = 600;
		int SCREEN_HEIGHT = 600;
		
		int x = 3;
		int y = 3;

		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setStroke(Color.rgb(255, 165, 0));   // orange
		gc.setFill(Color.web("#00ffcc"));  // teal
		
		gc.fillOval(47,57,6,6);
		gc.fillOval(447,347,6,6);
		
		gc.setLineWidth(6);
		
		drawGrid(gc, x, y);

	    Pane root = new Pane(canvas); // Add canvas to layout
	    Scene scene = new Scene(root, Color.BLACK);
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.setX(2); //initial position on the screen
	    stage.setY(2);
//	    stage.setFullScreen(true);
//	    stage.setFullScreenExitHint("you cant esc unless pressq ");
	    stage.setTitle("JavaFX Canvas Test");
	    stage.show();
	    }
	
	public void drawMaze(GraphicsContext gc, UnweightedGraph<Integer> graph, int rows, int cols) {
		
		// for each vertex of the graph
		// for each edge establish if 
	}
	
	// implement drawSearch();
	// implement drawPath();
	
	
	public void drawGrid(GraphicsContext gc, int x, int y) {
		// vertical
		gc.setLineWidth(1);
		for (int i = 0; i < SCREEN_WIDTH; i = i +(SCREEN_WIDTH / y)) {
			gc.strokeLine(i, 0, i, SCREEN_HEIGHT);
		}
		// horizontal
		for (int i = 0; i < SCREEN_WIDTH; i = i +(SCREEN_WIDTH / x)) {
			gc.strokeLine(0, i, SCREEN_WIDTH, i);
		}
	}
	}


/// which way method? Edge e ==> {direction, distance}
