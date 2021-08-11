import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class MarioForever implements ApplicationListener {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Menu menu ;
	private Game game ;
	private End end ;
	
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

	@Override
	public void render() {
		switch (gameState.getState()) {
			case MENU:
				menu.render(0) ;
				break ;
			case GAME:
				game.render(0) ;
				break ;
			case END:
				end.render(0) ;
				break ;
		}
	}

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
