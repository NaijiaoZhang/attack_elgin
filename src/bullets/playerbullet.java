package bullets;


import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class playerbullet {
	private final Image playerbulletImage = new Image(getClass().getResourceAsStream("../playerbullet.png")); 
	
	private double xPos, yPos; 
	private Rectangle hitBox; 
	
	public playerbullet(double initX, double initY){
		xPos = initX; 
		yPos = initY; 
		hitBox = new Rectangle ((int)xPos,(int)yPos,29,52);
	}
	
	public void update(){
		yPos -= 10; 
		updateHitBox(); 
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(playerbulletImage,xPos,yPos);
	}
	
	public double getY(){
		return yPos; 
	}
	
	public double getX(){
		return xPos; 
	}
	
	public Rectangle getHitBox(){
		return hitBox;
	}
	
	private void updateHitBox(){
		hitBox.setLocation((int)xPos, (int)yPos);
	}
}
