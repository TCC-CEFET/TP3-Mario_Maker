package singletons ;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.characteristics.* ;

public class GoombaSingleton {
	private static GoombaSingleton instance;
	private final int Width, Height ;
	private int points ;
	private int velocityX ;
	private int velocityY ;
	private Animation animation ;
	
	private GoombaSingleton() {
		Width=32; Height=32;
		points=100 ;
		velocityX=50 ;
		velocityY=100 ;
		
		buildAnimation() ;
	}

	public static synchronized GoombaSingleton getInstance() {
		if(instance == null) {
			instance = new GoombaSingleton();
		}
		return instance;
	}
	
	public int getWidth() {
		return Width;
	}

	public int getHeight() {
		return Height;
	}

	public TextureRegion getCurrentFrame(Direction direction) {
		TextureRegion frame = null ;
		
		frame = animation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
		
		return frame ;
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
	
	public void buildAnimation() {
		String imagePath="assets/sprites/goomba_spritesheet.png";
		int columns=3, rows=2 ;
		int width=16, height=16 ; // Quantidade de pixels
		int sheetWidth=width*columns, sheetHeight=height*rows ;
		
		// Pega o sprite sheet
		Texture texture = new Texture(Gdx.files.internal(imagePath)) ;
		
		// Monta uma matriz de regiao
		TextureRegion[][] frames2DArray = TextureRegion.split(texture, width, height) ;
		
		// Monta a ordem de texturas e animacoes
		int runningColumns=2 ;
		int line=new Random().nextInt(2) ;
		TextureRegion[] frames = new TextureRegion[runningColumns] ;
		for (int i=0; i < runningColumns; i++) {
			frames[i] = frames2DArray[line][i];
		}
		
		animation = new Animation(0.2f, frames) ;
	}
}
