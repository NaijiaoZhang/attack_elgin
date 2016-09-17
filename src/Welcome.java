import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.misc.GC;

/**
 * @author Naijiao
 *
 */
public class Welcome {
	public static final String TITLE = "ATTACK: Kuiper Belt Elgins";
	
	private Scene myScene;
	private ArrayList<KeyCode>userInput; 
	private ArrayList<KeyCode>KonamiCode;
	private Main application;
	private GridPane grid; 
	
	public Scene init (int width, int height,Main app) {
        application = app;
        populateKonami();
        userInput = new ArrayList<>();
		grid = new GridPane(); 
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        myScene = new Scene(grid,width,height);
        myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        Label gameName = new Label("ATTACK: Kuiper Belt Elgins");
        Button startGame = new Button("Start!");
        startGame.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                HowTo howto = new HowTo();
                Scene gameScene = howto.init(Main.SIZE,Main.SIZE,application);
                mainStage.setScene(gameScene);
            
            }
        });
        grid.add(gameName, 1, 1);
        grid.add(startGame, 1, 2);
        
        return myScene;
    }
	
	private void handleKeyPressed(KeyCode code) {
		switch (code) {
		case RIGHT:
			userInput.add(KeyCode.RIGHT);
			break;
		case LEFT:
			userInput.add(KeyCode.LEFT);
			break;
		case UP:
			userInput.add(KeyCode.UP);
			break;
		case DOWN:
			userInput.add(KeyCode.DOWN);
			break;
		case B:
			userInput.add(KeyCode.B);
			break;
		case A:
			userInput.add(KeyCode.A);
			break;
		case N:
			if(compareUserKonami()){
				generateLevelSkipButtons(); 
			}
			
			break;
		default:
			// do nothing
		}
	}
	
	private boolean compareUserKonami(){
		boolean isSame = true; 
		int count = 9;
		if(userInput.size()>=10){
			for(int i=userInput.size()-1;i>=userInput.size()-10;i--){
				if(!userInput.get(i).equals(KonamiCode.get(count))){
					isSame = false; 
				}
				count--;
			}
		}
		return isSame;
		
	}
	
	private void generateLevelSkipButtons(){
		Button Level1 = new Button("Level 1!");
        Level1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	setUpEventHandler(event,1);
            }
        });
        
        Button Level2 = new Button("Level 2!");
        Level2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	setUpEventHandler(event,2);
            }
        });
        
        Button Level3 = new Button("Level 3!");
        Level3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	setUpEventHandler(event,3);
            }
        });
        
        Button Boss = new Button("Boss!");
        Boss.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	setUpEventHandler(event,4);
            }
        });
  
        
        grid.add(Level1, 1, 3);
        grid.add(Level2, 1, 4);
        grid.add(Level3, 1, 5);
        grid.add(Boss, 1, 6);
        
	}
	
	private void setUpEventHandler(ActionEvent event, int level){
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Game myGame = new Game();
        Scene gameScene = myGame.init(Main.SIZE,Main.SIZE,application,level);
        mainStage.setScene(gameScene);
        KeyFrame frame = new KeyFrame(Duration.millis(Main.MILLISECOND_DELAY),
                e -> myGame.update(Main.SECOND_DELAY));
        Timeline animation = new Timeline();
        application.setTimeline(animation);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	
	private void populateKonami(){
		KonamiCode = new ArrayList<>();
		KonamiCode.add(KeyCode.UP);
		KonamiCode.add(KeyCode.UP);
		KonamiCode.add(KeyCode.DOWN);
		KonamiCode.add(KeyCode.DOWN);
		KonamiCode.add(KeyCode.LEFT);
		KonamiCode.add(KeyCode.RIGHT);
		KonamiCode.add(KeyCode.LEFT);
		KonamiCode.add(KeyCode.RIGHT);
		KonamiCode.add(KeyCode.B);
		KonamiCode.add(KeyCode.A);
	}
}
