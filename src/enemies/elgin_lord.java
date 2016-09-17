package enemies;

import java.awt.Rectangle;

import javafx.scene.image.Image;

public class elgin_lord extends drone {

	private int bulletLimiter;
	private int timeKeeper; 

	public elgin_lord(double initX, double initY, double vel, double ang) {
		droneImage = new Image(getClass().getResourceAsStream("../elgin_lord.png"));
		xPos = initX;
		yPos = initY;
		velocity = vel;
		angle = ang;
		bulletLimiter = 0;
		health = 450;
		timeKeeper = 0; 
		hitBox = new Rectangle((int) xPos, (int) yPos, 288, 153);

	}

	protected void checkBounds() {
		if (xPos <= 0 || xPos >= 625) {
			angle = (angle + Math.PI) % (2 * Math.PI);
		}
	}
	
	public void resetTime(){
		timeKeeper = 0;
	}
	
	public int getTime(){
		return timeKeeper; 
	}
	
	public boolean canBulletCreate(){
		bulletLimiter++;
		timeKeeper++;
		if(bulletLimiter>=120&&bulletLimiter<=360&&bulletLimiter%20==0){
			if(bulletLimiter == 360){
				bulletLimiter = 0;
			}
			return true; 
		}
		return false; 
	}
	

}
