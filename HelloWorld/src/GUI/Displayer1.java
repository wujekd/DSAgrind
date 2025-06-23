package GUI;

import java.util.List;

import Graphs.Edge;
import Graphs.UnweightedGraph;
import Graphs.UnweightedGraph.SearchTree;
import Graphs.maze1;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
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
		
		int cols = 3;
		int rows = 3;

		Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setFill(Color.web("#00ffcc"));  // teal
		
		gc.fillOval(47,57,6,6);
		gc.fillOval(447,347,6,6);
		
		gc.setLineWidth(6);
		
		UnweightedGraph<Integer> graph = maze1.createMaze(3,3);
		
		graph.addEdge(0,3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 5);
		graph.addEdge(5, 8);
		graph.addEdge(4, 7);
		graph.addEdge(7, 6);
		
		showGrid(gc, cols, rows);

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
	    
	    showMaze(gc, graph, rows, cols);
	    
	    int delayMs = rows * cols * 125;
	    PauseTransition delay = new PauseTransition(Duration.millis(delayMs));
	    delay.setOnFinished(e -> {
	    	SearchTree tree = graph.bfs(0);
	    	drawSearch(gc, tree, rows, cols);
	    });
	    delay.play();
	    
	    double delayMs2 = rows * cols * 125 * 2.3;
	    PauseTransition delay2 = new PauseTransition(Duration.millis(delayMs2));
	    delay2.setOnFinished(e -> {
	    	SearchTree tree = graph.bfs(0);
	    	drawPath(gc, tree, 6, rows, cols);
	    });
	    delay2.play();
	    
	    
	    }
	
	public void showMaze(GraphicsContext gc, UnweightedGraph<Integer> graph, int rows, int cols) {
		
		WallCell[][] wallMap = new WallCell[rows][cols];
		// init cells
		for (int r = 0; r < rows; r++) {
	        for (int c = 0; c < cols; c++) {
	            wallMap[r][c] = new WallCell();
	        }
	    }
		// for each vertex of the graph
		for (Integer v : graph.getVertices()) {
			int row = v / cols;
			int col = v % cols;
			// for each edge establish which walls to remove depending on its direction
			for  (Edge e : graph.getEdges().get(v)) {
				// find direction of the path
				int w = e.v;
				int wRow = w / cols;
				int wCol = w % cols;
				
				if (wRow == row && wCol == col + 1) {
		            wallMap[row][col].right = false;
		            wallMap[wRow][wCol].left = false;
		        } else if (wRow == row && wCol == col - 1) {
		            wallMap[row][col].left = false;
		            wallMap[wRow][wCol].right = false;
		        } else if (wCol == col && wRow == row + 1) {
		            wallMap[row][col].bottom = false;
		            wallMap[wRow][wCol].top = false;
		        } else if (wCol == col && wRow == row - 1) {
		            wallMap[row][col].top = false;
		            wallMap[wRow][wCol].bottom = false;
		        }
			}
			displayMaze(gc, wallMap);
		}
		 
		}
	
	public void displayMaze(GraphicsContext gc, WallCell[][] wallMap) {
		gc.setLineWidth(8);
		gc.setStroke(Color.rgb(255, 165, 0)); 
		int rows = wallMap.length;
		int cols = wallMap[0].length;
		double cellWidth = (double) SCREEN_WIDTH / cols;
	    double cellHeight = (double) SCREEN_HEIGHT / rows;
	    
	    // calculate thread transition time based on maze size and set total transition time 
	    new Thread(() -> {
	        try {
	            for (int r = 0; r < rows; r++) {
	                for (int c = 0; c < cols; c++) {
	                    WallCell cell = wallMap[r][c];
	                    double x = c * cellWidth;
	                    double y = r * cellHeight;

	                    // Must draw on JavaFX thread!
	                    final double fx = x, fy = y;
	                    final WallCell fcell = cell;

	                    javafx.application.Platform.runLater(() -> {
	                        if (fcell.top)    gc.strokeLine(fx, fy, fx + cellWidth, fy);
	                        if (fcell.right)  gc.strokeLine(fx + cellWidth, fy, fx + cellWidth, fy + cellHeight);
	                        if (fcell.bottom) gc.strokeLine(fx, fy + cellHeight, fx + cellWidth, fy + cellHeight);
	                        if (fcell.left)   gc.strokeLine(fx, fy, fx, fy + cellHeight);
	                    });

	                    Thread.sleep(95);
	                    
	                }
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }).start();
	}
	
	public void drawSearch(GraphicsContext gc, SearchTree tree, int rows, int cols) {
	    int[] parent = tree.getParent();
	    List<Integer> order = tree.getSearchOrder();
	    double cellWidth = (double) SCREEN_WIDTH / cols;
	    double cellHeight = (double) SCREEN_HEIGHT / rows;

	    new Thread(() -> {
	        try {
	            for (int i = 0; i < order.size(); i++) {
	                int v = order.get(i);
	                int p = parent[v];

	                if (p != -1) { // skip root
	                    int r1 = v / cols;
	                    int c1 = v % cols;
	                    int r2 = p / cols;
	                    int c2 = p % cols;

	                    double x1 = c1 * cellWidth + cellWidth / 2;
	                    double y1 = r1 * cellHeight + cellHeight / 2;
	                    double x2 = c2 * cellWidth + cellWidth / 2;
	                    double y2 = r2 * cellHeight + cellHeight / 2;

	                    javafx.application.Platform.runLater(() -> {
	                        gc.setStroke(Color.DARKGRAY);
	                        gc.setLineWidth(4); // thick line
	                        gc.strokeLine(x1, y1, x2, y2);
	                    });

	                    Thread.sleep(120); // Delay between steps
	                }
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }).start();
	}
	
	public void drawPath(GraphicsContext gc, SearchTree tree, int exit, int rows, int cols) {
		System.out.println(tree.getPath(exit));
		gc.setLineWidth(18);
		gc.setStroke(Color.rgb(55, 125, 150)); 
	    List<Integer> path = tree.getPath(exit);
	    if (path.size() < 2) return; // nothing to draw if the path is too short

	    double cellWidth = (double) SCREEN_WIDTH / cols;
	    double cellHeight = (double) SCREEN_HEIGHT / rows;

	    for (int i = 0; i < path.size() - 1; i++) {
	        int v1 = path.get(i);
	        int v2 = path.get(i + 1);

	        int r1 = v1 / cols;
	        int c1 = v1 % cols;
	        int r2 = v2 / cols;
	        int c2 = v2 % cols;

	        double x1 = c1 * cellWidth + cellWidth / 2;
	        double y1 = r1 * cellHeight + cellHeight / 2;
	        double x2 = c2 * cellWidth + cellWidth / 2;
	        double y2 = r2 * cellHeight + cellHeight / 2;

	        gc.strokeLine(x1, y1, x2, y2);
	    }
	}

	
	
	public void showGrid(GraphicsContext gc, int x, int y) {
		// vertical
		gc.setLineWidth(1);
		gc.setStroke(Color.rgb(69, 12, 69)); 
		for (int i = 0; i < SCREEN_WIDTH; i = i +(SCREEN_WIDTH / y)) {
			gc.strokeLine(i, 0, i, SCREEN_HEIGHT);
		}
		// horizontal
		for (int i = 0; i < SCREEN_WIDTH; i = i +(SCREEN_WIDTH / x)) {
			gc.strokeLine(0, i, SCREEN_WIDTH, i);
			}
		}
	
		public class WallCell {
		    public boolean top = true;
		    public boolean right = true;
		    public boolean bottom = true;
		    public boolean left = true;
		}
	}
