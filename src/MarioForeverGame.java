import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MarioForeverGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player ;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		player = new Player();
		player.setPosition(200, 100);
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.35f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch);
		batch.end();
		
		// Updates
		player.update();
		Rectangle temp = new Rectangle(0, 0, 800, 10);
		if (player.hits(temp) != -1) {
			player.action(1, 0, 10);
		}
			
		// Controls
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.jump();
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	 
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
