import java.util.ArrayList;
import java.util.EventObject;
import java.util.Timer;

import com.apple.eawt.Application;

import bullets.drone_one_bullet;
import bullets.drone_two_bullet;
import bullets.dronebullet;
import bullets.playerbullet;
import enemies.drone;
import enemies.drone_one;
import enemies.drone_two;
import enemies.elgin_lord;
import enemy_generation.creation_task;
import enemy_generation.creation_task_three;
import enemy_generation.creation_task_two;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Naijiao Zhang
 */
class Game {
	private final Image background = new Image(getClass().getResourceAsStream("background.jpg"));

	private Scene myScene;
	private Canvas mainCanvas;
	private GraphicsContext primaryDrawer;
	private player protagonist;
	private ArrayList<dronebullet> enemybulletsHolder;
	private ArrayList<drone> enemyHolder;
	private Timer enemyCreation;
	private boolean left, right = false;
	private boolean up, down = false;
	private int level, enemiesLeft;
	private Main application;

	/**
	 * Create the game's scene
	 */
	public Scene init(int width, int height, Main app, int l) {
		application = app;
		enemybulletsHolder = new ArrayList<>();
		enemiesLeft = 30;
		level = l;
		// create a scene graph to organize the scene
		Group root = new Group();
		// create a place to see the shapes
		myScene = new Scene(root, width, height, Color.WHITE);

		mainCanvas = new Canvas(width, height);
		primaryDrawer = mainCanvas.getGraphicsContext2D();
		primaryDrawer.setStroke(Color.WHITE);
		root.getChildren().add(mainCanvas);

		// players and enemies here
		protagonist = new player(435, 500);
		enemyHolder = new ArrayList<>();
		generateBasedOnLevel(level);

		// respond to input
		myScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
		myScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
		return myScene;
	}

	/**
	 * Change properties of shapes to animate them Note, there are more
	 * sophisticated ways to animate shapes, but these simple ways work too.
	 */
	public void update(double elapsedTime) {
		// inner updating done here
		protagonist.update();
		updateEnemies();
		bulletsUpdate();
		collisionCheck();

		if (gameOverCheck()) {
			gameOver();
		}
		if (moveToNextLevelCheck()) {
			enemyCreation.cancel();
			level++;
			enemiesLeft = 30;
			protagonist.setHealth();
			protagonist.setShield();
			generateBasedOnLevel(level);
		}
		else if (level == 4 &&gameWonCheck()) {
			gameWon();
		}

		// render
		primaryDrawer.clearRect(0, 0, Main.SIZE, Main.SIZE);
		primaryDrawer.drawImage(background, 0, 0);
		drawUserInfo();
		drawBossInfo();
		protagonist.render(primaryDrawer);
		renderEnemies();
		renderBullets();
	}

	private void drawUserInfo() {
		primaryDrawer.strokeText("Level " + level, 5, 20);
		primaryDrawer.strokeText("Enemies Left: " + enemiesLeft, 5, 35);
		primaryDrawer.strokeText("Health: " + protagonist.getHealth(), 700, 20);
		primaryDrawer.strokeText("Shields Left: " + protagonist.getShieldNum(), 700, 35);
	}

	private void drawBossInfo() {
		for (int i = 0; i < enemyHolder.size(); i++) {
			if (enemyHolder.get(i) instanceof elgin_lord)
				primaryDrawer.strokeText("Elgin Lord Health: " + ((elgin_lord) enemyHolder.get(i)).getHealth(), 350,
						20);
		}
	}

	private void updateEnemies() {
		for (int i = 0; i < enemyHolder.size(); i++) {
			enemyHolder.get(i).update();
		}
		enemyFire();
	}

	private void enemyFire() {
		for (int i = 0; i < enemyHolder.size(); i++) {
			if (enemyHolder.get(i) instanceof drone_one) {
				if (((drone_one) enemyHolder.get(i)).canBulletCreate()) {
					enemybulletsHolder.add(
							new drone_one_bullet(enemyHolder.get(i).getX(), enemyHolder.get(i).getY(), 5, Math.PI / 2));
				}
			} else if (enemyHolder.get(i) instanceof drone_two) {
				if (((drone_two) enemyHolder.get(i)).canBulletCreate()) {
					for (int k = 0; k <= 5; k++) {
						enemybulletsHolder.add(new drone_one_bullet(enemyHolder.get(i).getX(),
								enemyHolder.get(i).getY(), 5, k * Math.PI / 5));
					}
				}
			} else if (enemyHolder.get(i) instanceof elgin_lord) {
				elgin_lord_shoot(i);
			}

		}
	}

	private void elgin_lord_shoot(int i) {
		elgin_lord boss = (elgin_lord) enemyHolder.get(i);
		int bossXOffset = 144;
		int bossYOffset = 153;
		if (boss.canBulletCreate()) {
			if (boss.getTime() <= 1800) {
				bossPattern1(boss, bossXOffset, bossYOffset);
			} else if (boss.getTime() <= 3600) {
				bossPattern2(boss, bossXOffset, bossYOffset);
			} else if (boss.getTime() <= 5400) {
				bossPattern3(boss, bossXOffset, bossYOffset);
			} else if (boss.getTime() <= 7200) {
				bossPattern4(boss, bossXOffset, bossYOffset);
			} else if (boss.getTime() <= 9000) {
				enemybulletsHolder.add(
						new drone_two_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 7, Math.PI / 2));
				bossPattern5(boss, bossXOffset, bossYOffset);
			} else if(boss.getTime()<=10800) {
				bossPattern2(boss,bossXOffset,bossYOffset);
				bossPattern3(boss,bossXOffset,bossYOffset);
			}
			else if(boss.getTime()<=12600){
				enemybulletsHolder.add(
						new drone_two_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 7, Math.PI / 2));
				bossPattern5(boss, bossXOffset, bossYOffset);
				bossPattern3(boss,bossXOffset,bossYOffset);
			}
			else{
				boss.resetTime();
			}
		}
	}

	private void bossPattern1(elgin_lord boss, int bossXOffset, int bossYOffset) {
		enemybulletsHolder
				.add(new drone_one_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 7, Math.PI / 2));
	}

	private void bossPattern2(elgin_lord boss, int bossXOffset, int bossYOffset) {
		for (int k = 0; k <= 5; k++) {
			enemybulletsHolder.add(
					new drone_one_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 5, k * Math.PI / 5));
		}
	}

	private void bossPattern3(elgin_lord boss, int bossXOffset, int bossYOffset) {
		enemybulletsHolder
				.add(new drone_one_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 7, Math.PI / 2));
		for (int j = 0; j < 3; j++) {
			enemybulletsHolder.add(new drone_one_bullet(boss.getX() + 30 * j + bossXOffset,
					boss.getY() + 30 * j + bossYOffset, 7, Math.PI / 2));
			enemybulletsHolder.add(new drone_one_bullet(boss.getX() - 30 * j + bossXOffset,
					boss.getY() + 30 * j + bossYOffset, 7, Math.PI / 2));
		}
	}

	private void bossPattern4(elgin_lord boss, int bossXOffset, int bossYOffset) {
		for (int k = 0; k <= 5; k++) {
			enemybulletsHolder.add(
					new drone_one_bullet(boss.getX() + bossXOffset, boss.getY() + bossYOffset, 5, k * Math.PI / 5));
		}
		if (boss.getTime() >= 5400 + 450 && boss.getTime() <= 5400 + 900) {
			for (int k = 0; k < enemybulletsHolder.size(); k++) {
				enemybulletsHolder.get(k).setVelocity(2.5);
			}
		} else if (boss.getTime() >= 5400 + 900 && boss.getTime() <= 5400 + 1350) {
			for (int k = 0; k < enemybulletsHolder.size(); k++) {
				enemybulletsHolder.get(k).setVelocity(5);
			}
		} else if (boss.getTime() >= 5400 + 1350) {
			for (int k = 0; k < enemybulletsHolder.size(); k++) {
				enemybulletsHolder.get(k).setVelocity(2.5);
			}
		}
	}

	private void bossPattern5(elgin_lord boss, int bossXOffset, int bossYOffset) {
		for (int i = 0; i < enemybulletsHolder.size(); i++) {
			if (enemybulletsHolder.get(i) instanceof drone_two_bullet) {
				if (((drone_two_bullet) enemybulletsHolder.get(i)).triggerMultiBullets()) {
					drone_two_bullet splitbullet = (drone_two_bullet) enemybulletsHolder.get(i);
					for (int k = 0; k <= 6; k++) {
						enemybulletsHolder.add(
								new drone_one_bullet(splitbullet.getX(), splitbullet.getY(), 5, k * 2 * Math.PI / 6));
					}
				}
			}
		}
	}

	private void renderEnemies() {
		for (int i = 0; i < enemyHolder.size(); i++) {
			enemyHolder.get(i).render(primaryDrawer);
		}
	}

	private void renderBullets() {
		for (int i = 0; i < enemybulletsHolder.size(); i++) {
			enemybulletsHolder.get(i).render(primaryDrawer);
		}
	}

	private void bulletsUpdate() {
		for (int i = 0; i < enemybulletsHolder.size(); i++) {
			enemybulletsHolder.get(i).update();
			bulletsBoundCheck(enemybulletsHolder.get(i));
		}
	}

	private void bulletsBoundCheck(dronebullet dronebullet) {
		if (dronebullet.getY() > Main.SIZE) {
			enemybulletsHolder.remove(dronebullet);
		}
	}

	private boolean gameWonCheck() {
		if (level == 4) {
			for (int i = 0; i < enemyHolder.size(); i++) {
				if (enemyHolder.get(i) instanceof elgin_lord) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean gameOverCheck() {
		if (protagonist.getHealth() <= 0) {
			return true;
		}
		return false;
	}

	private boolean moveToNextLevelCheck() {
		return enemiesLeft == 0;
	}

	private void gameWon() {
		Stage mainStage = (Stage) mainCanvas.getScene().getWindow();
		GameWon overScreen = new GameWon();
		Scene gameScene = overScreen.init(Main.SIZE, Main.SIZE);
		application.stopTimeline();
		mainStage.setScene(gameScene);
	}

	private void gameOver() {
		Stage mainStage = (Stage) mainCanvas.getScene().getWindow();
		GameOver overScreen = new GameOver();
		Scene gameScene = overScreen.init(Main.SIZE, Main.SIZE, application,level);
		application.stopTimeline();
		mainStage.setScene(gameScene);
	}

	private void generateBasedOnLevel(int l) {
		enemyCreation = new Timer();
		if (l == 1) {
			enemyCreation.schedule(new creation_task(enemyHolder), 300, 6000);
		} else if (l == 2) {
			enemyCreation.schedule(new creation_task_two(enemyHolder), 300, 6000);
		} else if (l == 3) {
			enemyCreation.schedule(new creation_task_three(enemyHolder), 300, 6000);
		} else if (l == 4) {
			enemyHolder.add(new elgin_lord(100, 50, 5, 0));
		}
	}

	private void collisionCheck() {
		enemyBulletPlayerCollision();
		playerBulletEnemyCollision();
	}

	private void enemyBulletPlayerCollision() {
		for (int j = enemybulletsHolder.size() - 1; j >= 0; j--) {
			if (enemybulletsHolder.get(j).getHitBox().intersects(protagonist.getHitBox())) {
				if (protagonist.shieldOn()) {
					enemybulletsHolder.remove(j);
				} else {
					enemybulletsHolder.remove(j);
					protagonist.loseHealth();
				}
			}
		}
	}

	private void playerBulletEnemyCollision() {
		ArrayList<playerbullet> temp = protagonist.getBullets();
		for (int i = temp.size() - 1; i >= 0; i--) {
			for (int j = enemyHolder.size() - 1; j >= 0; j--) {
				if (temp.get(i).getHitBox().intersects(enemyHolder.get(j).getHitBox())) {
					temp.remove(i);
					enemyHolder.get(j).loseHealth();
					if (enemyHolder.get(j).getHealth() <= 0) {
						enemyHolder.remove(j);
						enemiesLeft--;
					}
					break;
				}
			}
		}
	}

	// What to do each time a key is pressed
	private void handleKeyPressed(KeyCode code) {
		switch (code) {
		case RIGHT:
			protagonist.changeXVel(5);
			right = true;
			break;
		case LEFT:
			protagonist.changeXVel(-5);
			left = true;
			break;
		case UP:
			protagonist.changeYVel(-5);
			up = true;
			break;
		case DOWN:
			protagonist.changeYVel(5);
			down = true;
			break;
		case SPACE:
			protagonist.turnOnShield();
			break;
		default:
			// do nothing
		}
	}

	private void handleKeyReleased(KeyCode code) {
		switch (code) {
		case RIGHT:
			if (left != true)
				protagonist.changeXVel(0);
			right = false;
			break;
		case LEFT:
			if (right != true)
				protagonist.changeXVel(0);
			left = false;
			break;
		case UP:
			if (down != true)
				protagonist.changeYVel(0);
			up = false;
			break;
		case DOWN:
			if (up != true)
				protagonist.changeYVel(0);
			down = false;
			break;
		default:
			// do nothing
		}
	}
}
