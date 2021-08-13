package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class CoinSingleton {
	private static CoinSingleton instance;
	private final int Width=24, Height=24;
	private int points=200 ;
	private Texture texture;
	
	private CoinSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/coin.png")) ;
	}
	
	public static synchronized CoinSingleton getInstance() {
		if(instance == null) {
			instance = new CoinSingleton();
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
