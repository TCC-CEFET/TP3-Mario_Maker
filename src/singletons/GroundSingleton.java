package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GroundSingleton {
	private static GroundSingleton instance;
	private final int Width=32, Height=32;
	private Sprite sprite ;
	private Texture texture ;
	
	private GroundSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/floor.png")) ;
		
		sprite = new Sprite(texture, 0, 0, Width, Height) ;
	}
	
	public static synchronized GroundSingleton getInstance() {
		if(instance == null) {
			instance = new GroundSingleton();
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
