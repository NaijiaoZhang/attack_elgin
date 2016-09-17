package enemies;
import java.awt.Rectangle;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class drone_one extends drone {
	
	private int bulletLimiter; 
	
	public drone_one(double initX, double initY,double vel,double ang){
		droneImage = new Image(getClass().getResourceAsStream("../drone_one.png"));
		xPos = initX;
		yPos = initY; 
		velocity = vel;
		angle = ang; 
		bulletLimiter = 0;
		health = 3; 
		hitBox = new Rectangle((int)xPos,(int)yPos,62,57);
		
	}
	
	public boolean canBulletCreate(){
		bulletLimiter++;
		if(bulletLimiter>=120&&bulletLimiter<=240&&bulletLimiter%20==0){
			if(bulletLimiter == 240){
				bulletLimiter = 0;
			}
			return true; 
		}
		return false; 
	}
	
	
}

