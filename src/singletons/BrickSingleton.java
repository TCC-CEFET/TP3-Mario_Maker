package singletons ;

public class BrickSingleton {
	private static BrickSingleton instance;
	private final int Width=32, Height=32;
	
	private BrickSingleton() {
		
	}
	
	public static synchronized BrickSingleton getInstance() {
		if(instance == null) {
			instance = new BrickSingleton();
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
