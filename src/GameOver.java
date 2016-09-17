
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOver {
	public static final String TITLE = "ATTACK: Kuiper Belt Elgins";

	private Scene myScene;
	private Main application;
	private int level;

	public Scene init(int width, int height, Main app, int l) {
		application = app;
		level = l;
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		myScene = new Scene(grid, width, height);

		Button startGame = new Button("Play Again?");
		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Game myGame = new Game();
				Scene gameScene = myGame.init(width, height, application, level);
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

		grid.add(startGame, 1, 2);

		// respond to input
		return myScene;
	}

}
