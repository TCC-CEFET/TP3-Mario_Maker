package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MushroomSingleton {
	private static MushroomSingleton instance;
	private final int Width=32, Height=32;
	private int points=1000 ;
	private int velocityX=50 ;
	private int velocityY=100 ;
	private Texture texture;
	
	private MushroomSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/mushroom.png"));
	}

	public static synchronized MushroomSingleton getInstance() {
		if(instance == null) {
			instance = new MushroomSingleton();
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
	
	public int getVelocityX() {
		return velocityX ;
	}
	
	public int getVelocityY() {
		return velocityY ;
	}
}
