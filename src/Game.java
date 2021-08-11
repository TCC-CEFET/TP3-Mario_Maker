import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import objects.GameObject;
import objects.MovableObject;
import objects.characteristics.Direction;
import objects.movables.Player;
import singletons.DisplaySingleton;
import singletons.PlayerSingleton;

public class Game implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Player player ;
	private ArrayList<GameObject> objectsList ;
	private ArrayList<MovableObject> movableList ;
	private MapManager mapManager ;
	private SoundManager soundManager ;
	
	public Game(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		create() ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
	}
	
	public void create() {
		objectsList = new ArrayList<GameObject>() ;
		movableList = new ArrayList<MovableObject>() ;
		
		player = new Player(33, 32, Direction.RIGHT);
		mapManager = new MapManager();
		mapManager.loadMap(objectsList, movableList);
		
		soundManager = new SoundManager() ;
		soundManager.playBackground() ;
	}
	
	@Override
	public void render(float arg0) {
		DisplaySingleton.getInstance().increaseStateTime() ;
		
		Gdx.gl.glClearColor(0.35f, 0.7f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (GameObject object: objectsList) {
			if (verifyDistance(object.getHitBox().x)) {
				object.draw(batch) ;
			}
		}
		for (MovableObject movable: movableList) {
			if (verifyDistance(movable.getHitBox().x)) {
				movable.draw(batch) ;
			}
		}
		player.draw(batch);
		batch.end();
		
		// Updates
		player.update() ;
		Iterator<GameObject> iterObjects = objectsList.iterator() ;
		while (iterObjects.hasNext()) {
			GameObject object = iterObjects.next() ;
			
			if (verifyDistance(object.getHitBox().x)) {
				object.update() ;
				boolean toRemove = object.verifyPosition(player, movableList) ;
				if (player.verifyPosition(object, movableList)) {
					resetGame() ;
					return ;
				}
				if (toRemove) iterObjects.remove() ;
			}
		}
		Iterator<MovableObject> iterMovables = movableList.iterator() ;
		while (iterMovables.hasNext()) {
			MovableObject movable = iterMovables.next() ;
			
			if (verifyDistance(movable.getHitBox().x)) {
				movable.update() ;
				boolean toRemove = movable.verifyPosition(player, movableList) ;
				if (player.verifyPosition(movable, movableList)) {
					resetGame() ;
					return ;
				}
				for (GameObject object: objectsList) {
					if (movable.verifyPosition(object, movableList)) {
						toRemove = true ;
						break ;
					}
				}
				if (toRemove) {
					iterMovables.remove() ;
				}
			}
		}
		updateCamera(null) ;
			
		// Controls
		player.control() ;
		for (MovableObject movable: movableList) {
			if (verifyDistance(movable.getHitBox().x)) {
				movable.control() ;
			}
		}
		
		if (camera.position.x > 800) {
			resetGame() ;
			gameState.setState(EnumGameState.END) ;
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
	
	public boolean verifyDistance(float x) {
		float renderProportion = 1.85f ;
		return Math.abs(camera.position.x - x) < DisplaySingleton.getInstance().getWidth()/renderProportion ;
	}
	
	public void updateCamera(Integer y) {
		int width=DisplaySingleton.getInstance().getWidth(), height=DisplaySingleton.getInstance().getHeight() ;
		if (player.getHitBox().x < (width/2) - player.getHitBox().width/2)
			camera.position.x = width/2 ;
		else
			camera.position.x = player.getHitBox().x + player.getHitBox().width/2 ;
		
		if (y != null) {
			camera.position.y = y ;
		}
		
		camera.update() ;
	}
	
	public void resetGame() {
		this.dispose() ;
		this.create() ;
		DisplaySingleton.getInstance().resetStateTime() ;
		gameState.setState(EnumGameState.MENU) ;
	}
	
	@Override
	public void dispose() {
		soundManager.dispose() ;
		PlayerSingleton.dispose() ;
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
