package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	private final int Width=32, Height=32;
	private Sprite sprite ;
	private Texture texture ;
	private PlayerSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/mario.png"));
		int imageWidth=2048, imageHeight=2048 ;
		sprite = new Sprite(texture, 0, 0, imageWidth, imageHeight);
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
	public Sprite getSprite() {
		return sprite;
	}

	public Texture getTexture() {
		return texture;
	}
}
