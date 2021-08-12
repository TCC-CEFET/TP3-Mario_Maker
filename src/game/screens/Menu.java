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
import objects.GameObject;
import objects.MovableObject;
import singletons.DisplaySingleton;

public class Menu implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture playButtonTexture ;
	private Texture exitButtonTexture ;
	private Texture background ;
	private Rectangle playButton ;
	private Rectangle exitButton ;
	
	public Menu(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		playButtonTexture = new Texture(Gdx.files.internal("assets/sprites/playButtonInactive.png")) ;
		exitButtonTexture = new Texture(Gdx.files.internal("assets/sprites/exitButtonInactive.png")) ;
		background = new Texture(Gdx.files.internal("assets/sprites/mainMenuScreen.png")) ;
		
		playButton = new Rectangle(320, 186, 108, 32) ;
		exitButton = new Rectangle(320, 130, 112, 32) ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		
		camera.position.x = 0 + DisplaySingleton.getInstance().getWidth()/2 ;
		camera.position.y = 0 + DisplaySingleton.getInstance().getHeight()/2 ;
		camera.update() ;
		
		batch.draw(playButtonTexture, playButton.x, playButton.y, playButton.width, playButton.height) ;
		batch.draw(exitButtonTexture, exitButton.x, exitButton.y, exitButton.width, exitButton.height) ;
		
		batch.end();
		
		if (Gdx.input.isTouched()) {
			Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0) ;
			camera.unproject(touchPosition) ;
			Rectangle touch = new Rectangle(touchPosition.x, touchPosition.y, 0.1f, 0.1f) ;
			Rectangle cursor = new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 0.1f, 0.1f) ;
			
			if (cursor.overlaps(playButton)) {
				playButtonTexture = new Texture(Gdx.files.internal("assets/sprites/playButtonActive.png")) ;
			} else {
				playButtonTexture = new Texture(Gdx.files.internal("assets/sprites/playButtonInactive.png")) ;
				
				if (cursor.overlaps(exitButton)) {
					exitButtonTexture = new Texture(Gdx.files.internal("assets/sprites/exitButtonActive.png")) ;
				} else {
					exitButtonTexture = new Texture(Gdx.files.internal("assets/sprites/exitButtonInactive.png")) ;
				}
			}
			
			if (touch.overlaps(playButton)) {
				gameState.setState(EnumGameState.GAME) ;
			} else if (touch.overlaps(exitButton)) {
				Gdx.app.exit() ;
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
