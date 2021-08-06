package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	private final int Width=32, Height=32; // 32 e 56
	private Texture texture ;
	
	private PlayerSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/mario.png")) ;
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

	public Texture getTexture() {
		return texture;
	}
}
