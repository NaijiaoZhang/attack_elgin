package enemy_generation;
import java.util.ArrayList;
import java.util.TimerTask;

import enemies.drone;
import enemies.drone_one;

public class creation_task extends TimerTask {
	
	private ArrayList<drone> enemyHolder; 
	
	public creation_task(ArrayList<drone> holder){
		enemyHolder = holder;
	}
	
	@Override
	public void run() {
		for(int i=0;i<4;i++){
			drone_one temp = new drone_one(Math.random()*400,Math.random()*400,4+Math.random()*2,Math.random()*2*Math.PI);
			enemyHolder.add(temp);		
		}
	}
}
