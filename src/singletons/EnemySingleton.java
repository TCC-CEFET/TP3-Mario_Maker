package singletons;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.characteristics.Direction;

public class EnemySingleton {
	private static EnemySingleton instance;
	private int points=100 ;
	private int velocityX ;
	private int velocityY ;
	
	private EnemySingleton() {
		points=100 ;
		velocityX=50 ;
		velocityY=100 ;
	}

	public static synchronized EnemySingleton getInstance() {
		if(instance == null) {
			instance = new EnemySingleton();
		}
		return instance;
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
