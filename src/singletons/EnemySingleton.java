package singletons;

public class EnemySingleton {
	private static EnemySingleton instance;
	private int points=100 ;
	private int velocityX ;
	private int velocityY ;
	
	private EnemySingleton() {
		points=100 ;
		velocityX=50 ;
		velocityY=100 ;
	}

	public static synchronized EnemySingleton getInstance() {
		if(instance == null) {
			instance = new EnemySingleton();
		}
		return instance;
	}

	public int getPoints() {
		return points ;
	}
	
	public int getVelocityX() {
		return velocityX ;
	}
	
	public int getVelocityY() {
		return velocityY ;
	}
}
