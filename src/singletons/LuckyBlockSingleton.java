package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LuckyBlockSingleton {
	private static LuckyBlockSingleton instance;
	private final int Width=32, Height=32;
	private int points=0 ;
	private Texture texture, textureHitted;
	
	private LuckyBlockSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/luckyBlock.png")) ;
		textureHitted = new Texture(Gdx.files.internal("assets/sprites/metalBlock.png")) ;
	}
	
	public static synchronized LuckyBlockSingleton getInstance() {
		if(instance == null) {
			instance = new LuckyBlockSingleton();
		}
		return instance;
	}
	
	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}

	public Texture getTexture(boolean hitted) {
		return hitted ? textureHitted : texture ;
	}
	
	public int getPoints() {
		return points ;
	}
}
