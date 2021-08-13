package singletons ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.characteristics.* ;

public class PlayerSingleton {
	private static PlayerSingleton instance;
	
	// Caracteristicas do player
	private final int smallWidth, smallHeight ;
	private final int bigWidth, bigHeight ;
	private final int crouchedHeight ;
	private final float BottomWidth ;
	private int width, height ;
	
	// Animacoes montadas
	private Animation smallRunningAnimation ;
	private Animation bigRunningAnimation ;
	private Animation smallStopAnimation ;
	private Animation bigStopAnimation ;
	private Animation smallJumpingAnimation ;
	private Animation bigJumpingAnimation ;
	private Animation crouchedAnimation ;
	
	private PlayerSingleton() {
		smallWidth=32 ; smallHeight=32 ;
		bigWidth=32 ; bigHeight=60 ;
		crouchedHeight=44 ;
		width=smallWidth ; height=smallHeight ;
		BottomWidth= width - (width/5) ;
		
		buildAnimation() ;
	}
	
	public static synchronized PlayerSingleton getInstance() {
		if(instance == null) {
			instance = new PlayerSingleton();
		}
		return instance;
	}
	
	public static synchronized void dispose() {
		instance = null ;
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
	
	public float getBottomWidth() {
		return BottomWidth ;
	}
	
	// Verifica os e retorna o frame ideal
	public TextureRegion getCurrentFrame(PlayerState playerState) {
		TextureRegion frame = null ;
		
		if (playerState.isCrouched()) {
			frame = crouchedAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
		} else if (playerState.isJumping()) {
			if (playerState.isBig()) {
				if (playerState.getDirection() == Direction.LEFT) {
					if (!bigJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) { // Verifica o flip para o lado
						bigJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (bigJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						bigJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				}
				
				frame = bigJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			} else {
				if (playerState.getDirection() == Direction.LEFT) {
					if (!smallJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						smallJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (smallJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						smallJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				}
				
				frame = smallJumpingAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			}
		} else if (playerState.getDirection() == Direction.STOP) {
			if (playerState.isBig()) {
				frame = bigStopAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			} else {
				frame = smallStopAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			}
		} else {
			if (playerState.isBig()) {
				if (playerState.getDirection() == Direction.LEFT) {
					if (!bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				}
				
				frame = bigRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
			} else {
				if (playerState.getDirection() == Direction.LEFT) {
					if (!smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
						smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
					}
				} else {
					if (smallRunningAnimation.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
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
	
	public void setCrouched(boolean isCrouched, PlayerState playerState) {
		if (isCrouched) {
			height = crouchedHeight ;
		} else {
			height = playerState.isBig() ? bigHeight : smallHeight ;
		}
	}
	
	public void buildAnimation() {
		String imagePath="assets/sprites/mario_spritesheet.png";
		int columns=7, rows=3 ;
		int width=16, height=30 ; // Quantidade de pixels
		int sheetWidth=width*columns, sheetHeight=height*rows ;
		
		// Pega o sprite sheet
		Texture texture = new Texture(Gdx.files.internal(imagePath)) ;
		
		// Monta uma matriz de regiao
		TextureRegion[][] frames2DArray = TextureRegion.split(texture, width, height) ;
		
		// Monta a ordem de texturas e animacoes
		int runningColumns=3 ;
		int smallLine=0, bigLine=1, fireLine=2 ;
		TextureRegion[] frames = new TextureRegion[runningColumns] ;
		for (int i=0; i < runningColumns; i++) {
			frames[i] = frames2DArray[smallLine][i];
		}
		
		smallRunningAnimation = new Animation(0.08f, frames) ;
		
		frames = new TextureRegion[runningColumns] ;
		for (int i=0; i < runningColumns; i++) {
			frames[i] = frames2DArray[bigLine][i];
		}
		
		bigRunningAnimation = new Animation(0.1f, frames) ;
		
		int stopedColumns=2 ;
		int firstStopped=4 ;
		frames = new TextureRegion[stopedColumns] ;
		for (int i=firstStopped; i < firstStopped+stopedColumns; i++) {
			frames[i-firstStopped] = frames2DArray[smallLine][i];
		}
		
		smallStopAnimation = new Animation(3f, frames) ;
		
		frames = new TextureRegion[stopedColumns] ;
		for (int i=firstStopped; i < firstStopped+stopedColumns; i++) {
			frames[i-firstStopped] = frames2DArray[bigLine][i];
		}
		
		bigStopAnimation = new Animation(3f, frames) ;
		
		int jumpingColumns=1 ;
		int firstJumpping=3 ;
		frames = new TextureRegion[jumpingColumns] ;
		for (int i=firstJumpping; i < firstJumpping+jumpingColumns; i++) {
			frames[i-firstJumpping] = frames2DArray[smallLine][i];
		}
		
		smallJumpingAnimation = new Animation(1f, frames) ;
		
		frames = new TextureRegion[jumpingColumns] ;
		for (int i=firstJumpping; i < firstJumpping+jumpingColumns; i++) {
			frames[i-firstJumpping] = frames2DArray[bigLine][i];
		}
		
		bigJumpingAnimation = new Animation(1f, frames) ;
		
		int crouchedColumns=1 ;
		int firstCrouched=6 ;
		frames = new TextureRegion[crouchedColumns] ;
		for (int i=firstCrouched; i < firstCrouched+crouchedColumns; i++) {
			frames[i-firstCrouched] = frames2DArray[bigLine][i];
		}
		
		crouchedAnimation = new Animation(1f, frames) ;
	}
}
