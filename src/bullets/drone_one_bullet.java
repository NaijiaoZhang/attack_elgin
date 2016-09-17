package bullets;
import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class drone_one_bullet extends dronebullet {
	

	public drone_one_bullet(double initX, double initY, double velo, double ang) {
		bulletImage = new Image(getClass().getResourceAsStream("../drone_bullet_one.png"));
		xPos = initX;
		yPos = initY;
		velocity = velo;
		angle = ang;
		hitBox = new Rectangle((int) xPos, (int) yPos, 25, 25);
	}


}
