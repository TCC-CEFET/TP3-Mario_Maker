
public class Singleton {
	private static Singleton instance;
	private final int BrickWidth=32, BrickHeight=32;
	private final int PlayerWidth=44, PlayerHeight=44;
	
	private Singleton() {
		
	}
	
	public static synchronized Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
	public int getBrickWidth() {
		return BrickWidth;
	}

	public int getBrickHeight() {
		return BrickHeight;
	}
	
}
