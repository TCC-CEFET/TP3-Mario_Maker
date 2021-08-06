package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DisplaySingleton {
	private static DisplaySingleton instance;
	private final int Width=800, Height=480 ;
	
	private DisplaySingleton() {
	}

	public static synchronized DisplaySingleton getInstance() {
		if(instance == null) {
			instance = new DisplaySingleton();
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
