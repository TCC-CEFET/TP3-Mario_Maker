package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import singletons.DisplaySingleton;

public class Launcher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Mario Forever";
		configuration.useGL20 = false ;
		configuration.width = DisplaySingleton.getInstance().getWidth() ;
		configuration.height = DisplaySingleton.getInstance().getHeight() ;
		
		new LwjglApplication(new MarioForever(), configuration);
		
		System.out.println() ;
	}
}
