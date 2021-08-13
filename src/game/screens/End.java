package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import game.states.EnumGameState;
import game.states.GameState;
import handlers.SoundHandler;
import singletons.DisplaySingleton;

// Tela de fim de jogo
public class End implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Botoes
	private Texture playAgainButtonTexture ;
	private Texture menuButtonTexture ;
	private Rectangle playAgainButton ;
	private Rectangle menuButton ;
	private Texture backgroundWin ;
	private Texture backgroundGameOver ;
	
	// Variaveis de marcacao
	private boolean gameOverSoundAlredyPlayed ;
	private boolean stageClearSoundAlredyPlayed ;
	
	public End(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		playAgainButtonTexture = new Texture(Gdx.files.internal("assets/sprites/playAgainButton.png")) ;
		menuButtonTexture = new Texture(Gdx.files.internal("assets/sprites/mainMenuButton.png")) ;
		backgroundWin = new Texture(Gdx.files.internal("assets/sprites/youWinScreen.png")) ;
		backgroundGameOver = new Texture(Gdx.files.internal("assets/sprites/gameOverScreen.png")) ;
		
		playAgainButton = new Rectangle(272, 175, 256, 32) ;
		menuButton = new Rectangle(280, 75, 240, 32) ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
		
		gameOverSoundAlredyPlayed = false ;
		stageClearSoundAlredyPlayed = false ;
	}

	@Override
	public void render(float arg0) {
		// Verifica se ganhou ou perdeu para tocar musica
		if (!gameOverSoundAlredyPlayed && arg0 == 0) {
			SoundHandler.getInstance().playGameOver() ;
			gameOverSoundAlredyPlayed = true ;
		}
		if (!stageClearSoundAlredyPlayed && arg0 == 1) {
			SoundHandler.getInstance().playStageClear() ;
			stageClearSoundAlredyPlayed = true ;
		}
		
		Gdx.gl.glClearColor(0f, 0.55f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Mantem a camera no inicio da tela
		camera.position.x = 0 + DisplaySingleton.getInstance().getWidth()/2 ;
		camera.position.y = 0 + DisplaySingleton.getInstance().getHeight()/2 ;
		camera.update() ;
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Verifica ganhou ou perdeu para mensagem em tela
		if (arg0 == 1) {
			batch.draw(backgroundWin, 0, 0);
		} else {
			batch.draw(backgroundGameOver, 0, 0);
		}
		
		// Desenha os botoes
		batch.draw(playAgainButtonTexture, playAgainButton.x, playAgainButton.y, playAgainButton.width, playAgainButton.height) ;
		batch.draw(menuButtonTexture, menuButton.x, menuButton.y, menuButton.width, menuButton.height) ;
		
		batch.end();
		
		// Verifica se foram clicados
		if (Gdx.input.isTouched()) {
			Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0) ;
			camera.unproject(touchPosition) ;
			Rectangle touch = new Rectangle(touchPosition.x-0.5f, touchPosition.y-0.5f, 1f, 1f) ;
			if (touch.overlaps(playAgainButton)) {
				gameState.setState(EnumGameState.GAME) ;
				stageClearSoundAlredyPlayed = false ;
			} else if (touch.overlaps(menuButton)) {
				gameState.setState(EnumGameState.MENU) ;
				stageClearSoundAlredyPlayed = false ;
			}
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
