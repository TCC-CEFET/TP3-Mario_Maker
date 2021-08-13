package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BrickSingleton {
	private static BrickSingleton instance;
	private final int Width=32, Height=32;
	private int points=50 ;
	private Texture texture;
	
	private BrickSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/brick.png")) ;
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

	public Texture getTexture() {
		return texture;
	}
	
	public int getPoints() {
		return points ;
	}
}
