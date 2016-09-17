import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWon {
	private final Image background = new Image(getClass().getResourceAsStream("winner.jpg"));
	private Scene myScene;
	private Canvas mainCanvas;
	private GraphicsContext primaryDrawer;

	
	public Scene init(int width, int height) {
		// create a scene graph to organize the scene
		Group root = new Group();
		// create a place to see the shapes
		myScene = new Scene(root, width, height, Color.WHITE);

		mainCanvas = new Canvas(width, height);
		primaryDrawer = mainCanvas.getGraphicsContext2D();
		primaryDrawer.setStroke(Color.WHITE);
		root.getChildren().add(mainCanvas);
		primaryDrawer.drawImage(background, 0, 0);
		
		return myScene; 
	}
}
