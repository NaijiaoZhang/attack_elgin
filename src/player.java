
import java.awt.Rectangle;
import java.util.ArrayList;

import bullets.playerbullet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class player {
	private final Image playerImage = new Image(getClass().getResourceAsStream("player.png"));
	private final Image shieldImage = new Image(getClass().getResourceAsStream("energy_shield.png"));

	private double xPos, yPos;
	private double xVel, yVel;
	private boolean shieldOn;
	private ArrayList<playerbullet> bulletsHolder;
	private int bulletLimiter, numShields, health, shieldTimer;
	private Rectangle hitBox;

	public player(double initX, double initY) {
		xPos = initX;
		yPos = initY;
		xVel = 0;
		yVel = 0;
		bulletLimiter = 0;
		bulletsHolder = new ArrayList<>();
		numShields = 5;
		hitBox = new Rectangle((int) xPos + 22, (int) yPos+21, 20, 20);
		health = 15;
		shieldTimer = 0;
	}

	public void render(GraphicsContext gc) {
		if (shieldOn) {
			gc.drawImage(shieldImage, xPos - 10, yPos - 10);
		}
		gc.drawImage(playerImage, xPos, yPos);
		renderBullets(gc);
	}

	public void update() {
		xPos += xVel;
		yPos += yVel;
		checkBounds();
		updateHitBox();
		updateShields();
		if (canBulletCreate()) {
			bulletsHolder.add(new playerbullet(xPos + 17, yPos - 10));
		}
		bulletsUpdate();
	}

	public void changeXVel(double velocity) {
		xVel = velocity;
	}

	public void changeYVel(double velocity) {
		yVel = velocity;
	}

	public ArrayList<playerbullet> getBullets() {
		return bulletsHolder;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHealth() {
		health = 15;
	}

	public int getHealth() {
		return health;
	}

	public void loseHealth() {
		health--;
	}

	public void turnOnShield() {
		if (numShields != 0) {
			shieldOn = true;
			numShields--;
		}
	}

	public boolean shieldOn() {
		return shieldOn;
	}

	public int getShieldNum() {
		return numShields;
	}

	public void setShield() {
		numShields = 5;
	}

	private void updateHitBox() {
		hitBox.setLocation((int) xPos + 5, (int) yPos);
	}

	private void checkBounds() {
		if (xPos <= 0) {
			xPos = Main.SIZE + xPos;
		} else if (xPos >= Main.SIZE) {
			xPos = xPos - Main.SIZE;
		} else if (yPos+yVel <= 200) {
			yPos-=yVel; 
			yVel = 0;
		} else if (yPos+yVel >= Main.SIZE) {
			yPos-=yVel;
			yVel = 0; 
		}
	}

	private void updateShields() {
		if (shieldOn) {
			shieldTimer++;
			if (shieldTimer % 180 == 0) {
				shieldTimer = 0;
				shieldOn = false;
			}
		}
	}

	private void renderBullets(GraphicsContext gc) {
		for (int i = 0; i < bulletsHolder.size(); i++) {
			bulletsHolder.get(i).render(gc);
		}
	}

	private boolean canBulletCreate() {
		bulletLimiter++;
		if (bulletLimiter % 20 == 0) {
			bulletLimiter = 0;// ensures that bulletlimiter won't reach large
								// numbers greater than int can take
			return true;
		}
		return false;
	}

	private void bulletsUpdate() {
		for (int i = 0; i < bulletsHolder.size(); i++) {
			bulletsHolder.get(i).update();
			bulletsBoundCheck(bulletsHolder.get(i));
		}
	}

	private void bulletsBoundCheck(playerbullet pb) {
		if (pb.getY() < 0) {
			bulletsHolder.remove(pb);
		}
	}
}
