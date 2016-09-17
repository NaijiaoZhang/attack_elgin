import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HowTo {
	private final Image background = new Image(getClass().getResourceAsStream("instructions.gif"));
	private Scene myScene;
	private Canvas mainCanvas;
	private GraphicsContext primaryDrawer;
	private Main application; 

	
	public Scene init(int width, int height,Main app) {
		application = app; 
		// create a scene graph to organize the scene
		Group root = new Group();
		// create a place to see the shapes
		myScene = new Scene(root, width, height, Color.WHITE);

		mainCanvas = new Canvas(width, height);
		primaryDrawer = mainCanvas.getGraphicsContext2D();
		primaryDrawer.setStroke(Color.WHITE);
		root.getChildren().add(mainCanvas);
		primaryDrawer.drawImage(background, 0, 0);
		
		Button startGame = new Button("Start Game!");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Game myGame = new Game();
                Scene gameScene = myGame.init(Main.SIZE,Main.SIZE,application,1);
                mainStage.setScene(gameScene);
                KeyFrame frame = new KeyFrame(Duration.millis(Main.MILLISECOND_DELAY),
                        e -> myGame.update(Main.SECOND_DELAY));
                Timeline animation = new Timeline();
                application.setTimeline(animation);
                animation.setCycleCount(Timeline.INDEFINITE);
                animation.getKeyFrames().add(frame);
                animation.play();
            
            }
        });
		
        root.getChildren().add(startGame);
        
		return myScene; 
	}
}
