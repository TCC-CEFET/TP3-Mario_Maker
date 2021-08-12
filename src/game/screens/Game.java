package game.screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.states.EnumGameState;
import game.states.GameState;
import handlers.*;
import objects.GameObject;
import objects.MovableObject;
import objects.characteristics.Direction;
import objects.movables.Player;
import objects.statics.Pipe;
import singletons.DisplaySingleton;
import singletons.PlayerSingleton;

public class Game implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Player player ;
	private ArrayList<GameObject> objectsList ;
	private ArrayList<MovableObject> movableList ;
	private MapHandler mapHandler ;
	
	private int finalX ;
	private int maximumTime ;
	
	private boolean themeSongIsPlaying ;
	
	public Game(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		create() ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
		
		finalX = 1500 ;
		maximumTime = 400 ;
	}
	
	public void create() {
		objectsList = new ArrayList<GameObject>() ;
		movableList = new ArrayList<MovableObject>() ;
		
		player = new Player(33, 32, Direction.RIGHT);
		mapHandler = new MapHandler();
		mapHandler.loadMap(objectsList, movableList);
		
		
		themeSongIsPlaying = false ;
	}
	
	@Override
	public void render(float arg0) {
		if (!themeSongIsPlaying) {
			SoundHandler.getInstance().playBackground() ;
			themeSongIsPlaying = true ;
		}
		
		DisplaySingleton.getInstance().increaseStateTime() ;
		
		Gdx.gl.glClearColor(150/255f, 118/255f, 214/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch);
		for (GameObject object: objectsList) {
			if (verifyDistance(object)) {
				object.draw(batch) ;
			}
		}
		for (MovableObject movable: movableList) {
			if (verifyDistance(movable)) {
				movable.draw(batch) ;
			}
		}
		drawTime() ;
		batch.end();
		
		// Controls
		player.control() ;
		for (MovableObject movable: movableList) {
			if (verifyDistance(movable)) {
				movable.control() ;
			}
		}
		
		// Updates
		player.update() ;
		Iterator<GameObject> iterObjects = objectsList.iterator() ;
		while (iterObjects.hasNext()) {
			GameObject object = iterObjects.next() ;
			
			if (verifyDistance(object)) {
				object.update() ;
				boolean toRemove = object.verifyPosition(player, movableList) ;
				if (player.verifyPosition(object, movableList)) {
					resetGame(EnumGameState.GAMEOVER) ;
					return ;
				}
				if (toRemove) iterObjects.remove() ;
			}
		}
		Iterator<MovableObject> iterMovables = movableList.iterator() ;
		while (iterMovables.hasNext()) {
			MovableObject movable = iterMovables.next() ;
			
			if (verifyDistance(movable)) {
				movable.update() ;
				boolean toRemove = movable.verifyPosition(player, movableList) ;
				if (player.verifyPosition(movable, movableList)) {
					resetGame(EnumGameState.GAMEOVER) ;
					return ;
				}
				for (GameObject object: objectsList) {
					if (movable.verifyPosition(object, movableList)) {
						toRemove = true ;
						break ;
					}
				}
				for (MovableObject movable2: movableList) {
					if (movable != movable2) {
						if (movable.verifyPosition(movable2, movableList)) {
							toRemove = true ;
							break ;
						}
					}
				}
				if (toRemove) {
					iterMovables.remove() ;
				}
			}
		}
		updateCamera(null) ;
		
		// Verifica fins de jogo
		if (camera.position.x > finalX) {
			resetGame(EnumGameState.WON) ;
		}
		
		if (DisplaySingleton.getInstance().getStateTime() > maximumTime) {
			resetGame(EnumGameState.GAMEOVER) ;
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
	
	public boolean verifyDistance(GameObject object) {
		float x = object.getHitBox().x ;
		float renderProportion = 1.85f ;
		if (object.getClass() != Pipe.class) {
			return Math.abs(camera.position.x - (x+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion ;
		} else {
			float xD = ((Pipe) object).getHitBoxDestination().x ;
			return Math.abs(camera.position.x - (x+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion || Math.abs(camera.position.x - (xD+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion ;
		}
	}
	
	public void updateCamera(Integer y) {
		int width=DisplaySingleton.getInstance().getWidth(), height=DisplaySingleton.getInstance().getHeight() ;
		if ((player.getHitBox().x < (width/2) - player.getHitBox().width/2) && player.getHitBox().x >= -10)
			camera.position.x = width/2 ;
		else
			camera.position.x = player.getHitBox().x + player.getHitBox().width/2 ;
		
		if (y != null) {
			camera.position.y = y ;
		}
		
		camera.update() ;
	}
	
	public void resetGame(EnumGameState gameState) {
		this.dispose() ;
		this.create() ;
		DisplaySingleton.getInstance().resetStateTime() ;
		this.gameState.setState(gameState) ;
	}
	
	public void drawTime() {
		int displayWidth=DisplaySingleton.getInstance().getWidth(), displayHeight=DisplaySingleton.getInstance().getHeight() ;
		int time = (int) (maximumTime - DisplaySingleton.getInstance().getStateTime()) ;
		BitmapFont font = new BitmapFont() ;
		font.setColor(1, 1, 1, 1) ;
		font.setScale(1.7f) ;
		float xTime=camera.position.x+(displayWidth/2)-100, yTime=displayHeight-40 ;
		font.draw(batch, "TIME", xTime-10, yTime+30) ;
		font.draw(batch, String.valueOf(time), xTime, yTime) ;
	}
	
	@Override
	public void dispose() {
		PlayerSingleton.dispose() ;
		SoundHandler.dispose() ;
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
