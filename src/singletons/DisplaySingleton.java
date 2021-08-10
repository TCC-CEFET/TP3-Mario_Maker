package singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class DisplaySingleton {
	private static DisplaySingleton instance;
	private final int Width=800, Height=480 ;
	private float stateTime ;
	
	private DisplaySingleton() {
		stateTime = 0 ;
	}

	public static synchronized DisplaySingleton getInstance() {
		if(instance == null) {
			instance = new DisplaySingleton();
		}
		return instance;
	}

	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}
	
	public float getStateTime() {
		return stateTime ;
	}
	
	public void increaseStateTime() {
		this.stateTime += Gdx.graphics.getDeltaTime() ;
	}
}
