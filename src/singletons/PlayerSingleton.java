package singletons ;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	private final int Width=32, Height=56;
	
	private PlayerSingleton() {
		
	}
	
	public static synchronized PlayerSingleton getInstance() {
		if(instance == null) {
			instance = new PlayerSingleton();
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
