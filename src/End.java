import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import objects.GameObject;
import objects.MovableObject;
import singletons.DisplaySingleton;

public class End implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture playButtonTexture ;
	private Texture menuButtonTexture ;
	private Rectangle playAgainButton ;
	private Rectangle menuButton ;
	
	public End(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		playButtonTexture = new Texture(Gdx.files.internal("assets/sprites/playButtonActive.png")) ;
		menuButtonTexture = new Texture(Gdx.files.internal("assets/sprites/exitButtonInactive.png")) ;
		
		playAgainButton = new Rectangle(10, 100, 128, 128) ;
		menuButton = new Rectangle(600, 100, 128, 128) ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0f, 0.55f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		camera.position.x = 0 + DisplaySingleton.getInstance().getWidth()/2 ;
		camera.position.y = 0 + DisplaySingleton.getInstance().getHeight()/2 ;
		camera.update() ;
		
		batch.draw(playButtonTexture, playAgainButton.x, playAgainButton.y, playAgainButton.width, playAgainButton.height) ;
		batch.draw(menuButtonTexture, menuButton.x, menuButton.y, menuButton.width, menuButton.height) ;
		
		batch.end();
		
		if (Gdx.input.isTouched()) {
			Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0) ;
			camera.unproject(touchPosition) ;
			Rectangle touch = new Rectangle(touchPosition.x-16, touchPosition.y-16, 32, 32) ;
			if (touch.overlaps(playAgainButton)) {
				gameState.setState(EnumGameState.GAME) ;
			} else if (touch.overlaps(menuButton)) {
				gameState.setState(EnumGameState.MENU) ;
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
