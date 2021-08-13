package handlers;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

// Calsse Singleton para gerenciar os sons
public class SoundHandler {
	private static SoundHandler instance ;
	
	private ArrayList<Long> ids ; // Array de ids tocando
	
	// Objetos de sounds
	private Sound background ;
	private Sound die ;
	private Sound gameOver ;
	private Sound stageClear ;
	private Sound breakBlock ;
	private Sound coin ;
	private Sound powerupAppears;
	private Sound kick;
	private Sound powerup;
	private Sound pipe;
	private Sound jumpSmall;
	private Sound jumpBig;
	
	private SoundHandler() {
		ids = new ArrayList<Long>() ;
		
		background = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ThemeSong.mp3")) ;
		die = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/marioDie.wav")) ;
		gameOver = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/gameOver.wav")) ;
		stageClear = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/stageClear.wav")) ;
		breakBlock = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/breakBlock.wav")) ;
		coin = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/coin.wav")) ;
		powerupAppears = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/powerupAppears.wav")) ;
		kick = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/kick.wav")) ;
		powerup = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/powerup.wav")) ;
		pipe = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/pipe.wav")) ;
		jumpSmall = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/jumpSmall.wav")) ;
		jumpBig = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/jumpBig.wav")) ;
	}
	
	public static synchronized SoundHandler getInstance() {
		if(instance == null) {
			instance = new SoundHandler();
		}
		return instance;
	}
	
	public void playBackground() {
		for (Long id: ids) { // Para os outros
			background.stop(id) ;
		}
		
		ids.add(background.loop()) ; // Toca e adiciona o id
	}
	
	public void playPlayerDie() {
		for (Long id: ids) {
			die.stop(id) ;
		}
		
		die.play() ;
		try { TimeUnit.MILLISECONDS.sleep(2000) ; } // Pausa por 2s para tocar o som
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public void playGameOver() {
		for (Long id: ids) {
			gameOver.stop(id) ;
		}
		
		ids.add(gameOver.play()) ;
	}
	
	public void playStageClear() {
		for (Long id: ids) {
			stageClear.stop(id) ;
		}
		
		ids.add(stageClear.play()) ;
	}
	
	public void playBreakBlock() {
		breakBlock.play() ;
	}
	
	public void playCoin() {
		coin.play() ;
	}
	
	public void playPowerupAppears() {
		powerupAppears.play() ;
	}
	
	public void playKick() {
		kick.play() ;
	}
	
	public void playJump(boolean isBig) {
		if (isBig) {
			jumpBig.play() ;
		} else {
			jumpSmall.play() ;
		}
	}
	
	public void playPowerup() {
		powerup.play() ;
	}
	
	public void playPipe() {
		pipe.play() ;
	}
	
	// Para todos os sons
	public static synchronized void dispose() {
		for (Long id: SoundHandler.getInstance().ids) {
			SoundHandler.getInstance().background.stop(id) ;
		}
		
		instance = null ;
	}
}
