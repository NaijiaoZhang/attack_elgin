package bullets;

import java.awt.Rectangle;

import javafx.scene.image.Image;

public class drone_two_bullet extends dronebullet {
	
	public double distanceTraveled; 
	
	public drone_two_bullet(double initX, double initY, double velo, double ang) {
		bulletImage = new Image(getClass().getResourceAsStream("../drone_bullet_two.png"));
		xPos = initX;
		yPos = initY;
		velocity = velo;
		angle = ang;
		hitBox = new Rectangle((int) xPos, (int) yPos, 21, 22);
	}
	
	public void update() {
		yPos += velocity * Math.sin(angle);
		xPos += velocity * Math.cos(angle);
		distanceTraveled += velocity; 
		updateHitBox();
	}

	public boolean triggerMultiBullets(){
		if(distanceTraveled>=100){
			return true; 
		}
		return false; 
	}
	
}
