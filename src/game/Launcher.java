package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import singletons.DisplaySingleton;

// Classe com main que lanca a aplicacao
public class Launcher {
	public static void main(String[] args) {
		// Configuration da aplicacao
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Mario™";
		configuration.useGL20 = false ;
		configuration.width = DisplaySingleton.getInstance().getWidth() ;
		configuration.height = DisplaySingleton.getInstance().getHeight() ;
		
		new LwjglApplication(new MarioForever(), configuration); // Inicializa a aplicacao
	}
}
