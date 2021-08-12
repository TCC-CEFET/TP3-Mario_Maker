package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PipeSingleton {
	private static PipeSingleton instance;
	private final int Width=64, Height=64 ;
	private Texture texture ;
	
	private PipeSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/pipe.png")) ;
	}
	
	public static synchronized PipeSingleton getInstance() {
		if(instance == null) {
			instance = new PipeSingleton();
		}
		return instance;
	}
	
	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height ;
	}

	public Texture getTexture() {
		return texture;
	}
}
