package bullets;

import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class dronebullet {
	protected Image bulletImage;
	protected double xPos, yPos;
	protected double velocity, angle;
	protected Rectangle hitBox;
	
	public void update() {
		yPos += velocity * Math.sin(angle);
		xPos += velocity * Math.cos(angle);
		updateHitBox();
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(bulletImage, xPos, yPos);
	}

	public double getY() {
		return yPos;
	}

	public double getX() {
		return xPos;
	}
	
	public void setVelocity(double velo){
		velocity = velo; 
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	protected void updateHitBox() {
		hitBox.setLocation((int) xPos, (int) yPos);
	}
}
