package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemySingleton {
	private static EnemySingleton instance;
	private final int Width=32, Height=32;
	private Sprite sprite;
	private Texture texture;
	
	private EnemySingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/enemyBlock.png"));
		
		sprite = new Sprite(texture, 0, 0, Width, Height);
	}

	public static synchronized EnemySingleton getInstance() {
		if(instance == null) {
			instance = new EnemySingleton();
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
