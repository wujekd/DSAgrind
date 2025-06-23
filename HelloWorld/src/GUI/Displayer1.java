package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;     
import javafx.scene.canvas.GraphicsContext; // Needed for drawing
import javafx.scene.layout.Pane;  


public class Displayer1 extends Application {


    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {

		Canvas canvas = new Canvas(400, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.strokeLine(50, 400, 250, 250);
		
		gc.fillOval(47,47,6,6);
		gc.fillOval(347,347,6,6);
		

	    Pane root = new Pane(canvas); // Add canvas to layout
	    Scene scene = new Scene(root, 400, 400);
	    stage.setScene(scene);
	    stage.setTitle("JavaFX Canvas Test");
	    stage.show();
	    }
		
	}
