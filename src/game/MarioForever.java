package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.screens.*;
import game.states.EnumGameState;
import game.states.GameState;
import singletons.* ;

// Classe que controla os estados do jogo e coloca a tela
public class MarioForever implements ApplicationListener {
	private GameState gameState ; // Estado do jogo
	
	// Camera e batch
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Telas
	private Menu menu ;
	private Game game ;
	private End end ;
	
	//inicializa as telas do jogo
	@Override
	public void create() {
		gameState = new GameState() ; 
		gameState.setState(EnumGameState.MENU) ;
		
		int width=DisplaySingleton.getInstance().getWidth(), height=DisplaySingleton.getInstance().getHeight() ;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		batch = new SpriteBatch();
		
		menu = new Menu(batch, camera, gameState) ;
		game = new Game(batch, camera, gameState) ;
		end = new End(batch, camera, gameState) ;
	}

	//renderiza cada tela de acordo com o estado do jogo
	@Override
	public void render() {
		switch (gameState.getState()) {
			case MENU:
				menu.render(0) ;
				break;
			case GAME:
				game.render(0) ;
				break;
			case WON:
				end.render(1) ;
				break;
			case GAMEOVER:
				end.render(0);
				break;
		}
	}

	//libera as variaveis de tela do jogo
	@Override
	public void dispose() {
		menu.dispose() ;
		game.dispose() ;
		batch.dispose() ;
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
	
}
