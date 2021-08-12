package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Mario Forever";
		cfg.useGL20 = false ;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new MarioForever(), cfg);
		
		System.out.println() ;
	}
}
