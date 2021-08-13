package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GroundSingleton {
	private static GroundSingleton instance;
	private final int Width=32, HeightFull=32, HeightTop=HeightFull/10;
	private Texture texture ;
	
	private GroundSingleton() {
		texture = new Texture(Gdx.files.internal("assets/sprites/ground.png")) ;
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

	public int getHeightFull() {
		return HeightFull ;
	}

	public int getHeightTop() {
		return HeightTop;
	}

	public Texture getTexture() {
		return texture;
	}
}
