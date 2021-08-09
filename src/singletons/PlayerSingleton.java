package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	private int width=32, height=32; // 32 e 56
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
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public void setHeight(boolean isBig) {
		if (isBig) height = 56 ;
		else height = 32 ;
	}
}
