package enemy_generation;
import java.util.ArrayList;
import java.util.TimerTask;

import enemies.drone;
import enemies.drone_two;

public class creation_task_two extends TimerTask {
	
	private ArrayList<drone> enemyHolder; 
	
	public creation_task_two(ArrayList<drone> holder){
		enemyHolder = holder;
	}
	
	@Override
	public void run() {
		for(int i=0;i<4;i++){
			drone_two temp = new drone_two(Math.random() * 400, Math.random() * 400, 4 + Math.random() * 2,
					Math.random() * 2 * Math.PI);
			enemyHolder.add(temp);
		}
	}
	
}
