package singletons;

import com.badlogic.gdx.Gdx;

public class DisplaySingleton {
	private static DisplaySingleton instance;
	private final int Width=800, Height=480 ; // Proporcao da tela
	private float stateTime ; // State time do jogo
	
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
	
	public void resetStateTime() {
		this.stateTime = 0 ;
	}
}
