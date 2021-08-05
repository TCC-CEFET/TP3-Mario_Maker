import java.util.ArrayList;
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

public class MarioForeverGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player ;
	private ArrayList<GameObject> objectsList = new ArrayList<GameObject>() ;
	private MapManager mapManager;
	
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		player = new Player(0, 33);
		mapManager = new MapManager();
		mapManager.loadMap(objectsList);
		
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
		for (GameObject object: objectsList) {
			object.draw(batch) ;
		}
		batch.end();
		
		// Updates
		player.update() ;
		for (GameObject object: objectsList) {
			player.hits(object) ;
			object.hits(player) ;
		}
		updateCamera() ;
			
		// Controls
		player.control() ;
		for (GameObject object: objectsList) {
			if (object.getClass() == Enemy.class) ((Movel) object).control() ;
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
	
	public void updateCamera() {
		camera.position.x = player.getHitBox().x + player.getHitBox().width/2 ;
		camera.update() ;
	}
}
