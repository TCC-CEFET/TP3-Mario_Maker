package singletons ;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.characteristics.* ;

public class KoopaSingleton {
	private static KoopaSingleton instance ;
	private final int RunWidth, RunHeight ;
	private final int ShellWidth, ShellHeight ;
	private int points ;
	private final int VelocityX ;
	private final int VelocityY ;
	private final int VelocityXShell;
	private Animation animationRunning ;
	private Animation animationShell ;
	
	private KoopaSingleton() {
		RunWidth=32; RunHeight=48;
		ShellWidth=32 ; ShellHeight=28 ;
		points=100 ;
		VelocityX=50 ;
		VelocityY=100 ;
		VelocityXShell=VelocityX*3;
		
		buildAnimation() ;
	}

	public static synchronized KoopaSingleton getInstance() {
		if(instance == null) {
			instance = new KoopaSingleton();
		}
		return instance;
	}
	
	public int getWidth(boolean isHidden) {
		if (isHidden)
			return ShellWidth ;
		else
			return RunWidth ;
	}

	public int getHeight(boolean isHidden) {
		if (isHidden)
			return ShellHeight ;
		else
			return RunHeight ;
	}
	
	public int getRunWidth() {
		return RunWidth ;
	}
	
	public int getRunHeight() {
		return RunHeight ;
	}

	public TextureRegion getCurrentFrame(Direction direction, Boolean isHidden) {
		TextureRegion frame = null ;
		if(isHidden) {
			if (direction == Direction.LEFT) {
				if (animationShell.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
					animationShell.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
				}
			} else if (direction == Direction.RIGHT) {
				if (!animationShell.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
					animationShell.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
				}
			}
			
			frame = animationShell.getKeyFrame(direction == Direction.STOP ? 0 : DisplaySingleton.getInstance().getStateTime(), true) ;
		} else {
			if (direction == Direction.LEFT) {
				if (animationRunning.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
					animationRunning.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
				}
			} else {
				if (!animationRunning.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).isFlipX()) {
					animationRunning.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true).flip(true, false);
				}
			}
			
			frame = animationRunning.getKeyFrame(DisplaySingleton.getInstance().getStateTime(), true) ;
		}
		
		return frame ;
	}

	public int getPoints() {
		return points ;
	}
	
	public int getVelocityX(boolean isHidden) {
		if (isHidden)
			return VelocityXShell ;
		else
			return VelocityX ;
	}
	
	public int getVelocityY() {
		return VelocityY ;
	}
	
	public void buildAnimation() {
		String imagePath="assets/sprites/koopa_spritesheet.png";
		int columns=6, rows=2 ;
		int width=16, height=24 ; // Quantidade de pixels
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

		animationRunning = new Animation(0.2f, frames) ;

		// Monta a ordem de texturas e animacoes
		int shellColumns=4 ;
		int firstShell=2;
		frames = new TextureRegion[shellColumns] ;
		for (int i=firstShell; i < firstShell+shellColumns; i++) {
			frames[i-firstShell] = frames2DArray[line][i];
		}
		
		animationShell = new Animation(0.05f, frames) ;
	}
}