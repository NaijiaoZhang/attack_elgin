package enemies;
import java.awt.Rectangle;

import javafx.scene.image.Image;

public class drone_two extends drone {

	private int bulletLimiter; 
	
	public drone_two(double initX, double initY, double vel, double ang) {
		droneImage = new Image(getClass().getResourceAsStream("../drone_two.png"));
		xPos = initX;
		yPos = initY;
		velocity = vel;
		angle = ang;
		bulletLimiter = 0;
		health = 2;
		hitBox = new Rectangle((int) xPos, (int) yPos, 62, 57);

	}
	
	public boolean canBulletCreate(){
		bulletLimiter++;
		if(bulletLimiter>=240&&bulletLimiter<=360&&bulletLimiter%40==0){
			if(bulletLimiter == 360){
				bulletLimiter = 0;
			}
			return true; 
		}
		return false; 
	}
	
	
}
