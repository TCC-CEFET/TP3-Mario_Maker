package singletons ;

public class GroundSingleton {
	private static GroundSingleton instance;
	private final int Width=32, Height=32;
	
	private GroundSingleton() {
		
	}
	
	public static synchronized GroundSingleton getInstance() {
		if(instance == null) {
			instance = new GroundSingleton();
		}
		return instance;
	}
	
	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}
}
