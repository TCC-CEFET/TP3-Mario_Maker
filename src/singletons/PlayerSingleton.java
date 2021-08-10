package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	private final int smallWidth=28, smallHeight=38 ;
	private final int bigWidth=32, bigHeight=54 ;
	private int width=smallWidth, height=smallHeight;
	private Animation smallRunningAnimation ;
	private Animation bigRunningAnimation ;
	private Animation stopAnimation ;
	
	private PlayerSingleton() {
		buildAnimation() ;
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
	
	public int getBigWidth() {
		return bigWidth;
	}

	public int getBigHeight() {
		return bigHeight;
	}

	public TextureRegion getActualFrame(State state) {
		TextureRegion frame = null ;
		
		if (state.getDirection() == Direction.STOP) {
			frame = stopAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
		} else {
			if (state.isBig()) {
				if (state.getDirection() == Direction.LEFT) {
					if (bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (!bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				}
				
				frame = bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			} else {
				if (state.getDirection() == Direction.LEFT) {
					if (smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (!smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				}
				frame = smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			}
		}
		
		return frame ;
	}
	
	public void setHeight(boolean isBig) {
		if (isBig) {
			width = bigWidth ;
			height = bigHeight ;
		} else {
			width = smallWidth ;
			height = smallHeight ;
		}
	}
	
	public void buildAnimation() {
		String imagePath="assets/sprites/mario_spritesheet.png";
		int columns=4, rows=4 ;
		int width=16, height=27 ; // Quantidade de pixels
		int sheetWidth=width*columns, sheetHeight=height*rows ;
		
		// Pega o sprite sheet
		Texture texture = new Texture(Gdx.files.internal(imagePath)) ;
		
		// Monta uma matriz de regiao
		TextureRegion[][] frames2DArray = TextureRegion.split(texture, width, height) ;
		
		// Monta a ordem de texturas e animacoes
		int runningColumns=3 ;
		TextureRegion[] frames = new TextureRegion[runningColumns] ;
		for (int i=0; i < runningColumns; i++) {
			frames[i] = frames2DArray[1][i];
		}
		
		smallRunningAnimation = new Animation(0.1f, frames) ;
		
		frames = new TextureRegion[runningColumns] ;
		for (int i=0; i < runningColumns; i++) {
			frames[i] = frames2DArray[2][i];
		}
		
		bigRunningAnimation = new Animation(0.15f, frames) ;
		
		int stopedColumns=2 ;
		frames = new TextureRegion[stopedColumns] ;
		for (int i=0; i < stopedColumns; i++) {
			frames[i] = frames2DArray[0][i];
		}
		
		stopAnimation = new Animation(1.5f, frames) ;
	}
}
