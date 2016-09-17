package enemies;
import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class drone {
	protected Image droneImage; 
	protected Rectangle hitBox; 
	protected double xPos, yPos; 
	protected double velocity,angle;
	protected int health;
	
	public void update(){
		xPos += velocity*Math.cos(angle);
		yPos += velocity*Math.sin(angle);
		updateHitBox();
		checkBounds(); 
	}
	
	public Rectangle getHitBox(){
		return hitBox; 
	}
	
	public void loseHealth(){
		health--;
	}
	
	public int getHealth(){
		return health; 
	}
	
	public double getX(){
		return xPos; 
	}
	
	public double getY(){
		return yPos; 
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(droneImage, xPos, yPos);
	}
	
	protected void checkBounds(){
		if (xPos <= 0 || xPos >= 800) {
			angle = (angle - Math.PI / 2) % (2 * Math.PI);
		}
		else if(yPos<=0||yPos>=400){
			angle = (angle-Math.PI/2)%(2*Math.PI);  
		}	
	}
	
	protected void updateHitBox(){
		hitBox.setLocation((int)xPos, (int)yPos);
	}
}
