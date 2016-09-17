

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Naijiao Zhang
 */
public class Main extends Application {

	public static final int SIZE = 800;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	private Welcome welcomeScreen;
	private Scene sceneShown;
	private Timeline animationTimeLine;
	private MediaPlayer mediaPlayer; 
	/**
	 * Set things up at the beginning.
	 */
	@Override
	public void start(Stage s) {
		URL resource = getClass().getResource("tohou_music.mp3");
	    Media media = new Media(resource.toString());
	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play();


		welcomeScreen = new Welcome();
		s.setTitle(Welcome.TITLE);

		sceneShown = welcomeScreen.init(SIZE, SIZE, this);
		s.setScene(sceneShown);
		s.show();
	}

	/**
	 * Start the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void setTimeline(Timeline t) {
		animationTimeLine = t;
	}

	public void stopTimeline() {
		animationTimeLine.stop();
	}
}
