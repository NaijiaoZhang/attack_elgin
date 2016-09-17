package enemy_generation;
import java.util.ArrayList;
import java.util.TimerTask;

import enemies.drone;
import enemies.drone_one;
import enemies.drone_two;

public class creation_task_three extends TimerTask {
	
	private ArrayList<drone> enemyHolder;

	public creation_task_three(ArrayList<drone> holder){
		enemyHolder = holder;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			drone_one temp = new drone_one(Math.random() * 400, Math.random() * 400, 4 + Math.random() * 2,
					Math.random() * 2 * Math.PI);
			enemyHolder.add(temp);
		}
		drone_two temp = new drone_two(Math.random() * 400, Math.random() * 400, 4 + Math.random() * 2,
				Math.random() * 2 * Math.PI);
		enemyHolder.add(temp);
	}
}
