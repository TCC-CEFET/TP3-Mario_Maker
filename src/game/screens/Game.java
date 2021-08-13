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

// Classe da tela do jogo principal
public class Game implements Screen {
	private GameState gameState ;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Objetos do jogo
	private Player player ;
	private ArrayList<GameObject> objectsList ; // Objetos estaticos
	private ArrayList<MovableObject> movableList ; // Objetos moveis
	private MapHandler mapHandler ;
	
	// Variaveis de marcacao de fim de jogo
	private final int FinalX, maximumTime ;
	
	// Variavel de marcacao
	private boolean themeSongIsPlaying ;
	
	public Game(SpriteBatch batch, OrthographicCamera camera, GameState gameState) {
		create() ;
		
		this.batch = batch ;
		this.camera = camera ;
		this.gameState = gameState ;
		
		FinalX = 7840 ;
		maximumTime = 400 ;
	}
	
	// Inicia os objetos
	public void create() {
		objectsList = new ArrayList<GameObject>() ;
		movableList = new ArrayList<MovableObject>() ;
		
		player = new Player(300, 32, Direction.RIGHT);
		mapHandler = new MapHandler();
		mapHandler.loadMap(objectsList, movableList);
		
		themeSongIsPlaying = false ;
	}
	
	@Override
	public void render(float arg0) {
		if (!themeSongIsPlaying) { // Se a musica de fundo nao estiver tocando comeca a tocar
			SoundHandler.getInstance().playBackground() ;
			themeSongIsPlaying = true ;
		}
		
		DisplaySingleton.getInstance().increaseStateTime() ; // Atualiza o state time
		
		Gdx.gl.glClearColor(150/255f, 118/255f, 214/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Desenha os objetos
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
		
		// Controla os objetos
		player.control() ;
		for (MovableObject movable: movableList) {
			if (verifyDistance(movable)) {
				movable.control() ;
			}
		}
		
		// Atualiza os objetos
		player.update() ;
		
		// Atualiza os objetos estaticos
		Iterator<GameObject> iterObjects = objectsList.iterator() ;
		while (iterObjects.hasNext()) {
			GameObject object = iterObjects.next() ;
			
			if (verifyDistance(object)) { // Verifica a distancia
				object.update() ;
				boolean toRemove = object.verifyPosition(player, movableList) ; // Verifica o objeto com player
				if (player.verifyPosition(object, movableList)) { // Verifica o player com objeto
					resetGame(EnumGameState.GAMEOVER) ;
					return ;
				}
				if (toRemove) iterObjects.remove() ;
			}
		}
		
		// Atualiza os objetos moveis
		Iterator<MovableObject> iterMovables = movableList.iterator() ;
		while (iterMovables.hasNext()) {
			MovableObject movable = iterMovables.next() ;
			
			if (verifyDistance(movable)) { // Verifica a distancia
				movable.update() ;
				boolean toRemove = movable.verifyPosition(player, movableList) ; // Verifica o movel com player
				if (player.verifyPosition(movable, movableList)) { // Verifica o player com o movel
					resetGame(EnumGameState.GAMEOVER) ;
					return ;
				}
				for (GameObject object: objectsList) { // Verifica o movel com todos os obejtos
					if (movable.verifyPosition(object, movableList)) {
						toRemove = true ;
						break ;
					}
				}
				for (MovableObject movable2: movableList) { // Verifica o movel com todos os moveis
					if (movable != movable2) { // Verifica se os moveis nao sao o mesmo movel
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
		
		updateCamera(null) ; // Atualiza a camera
		
		// Verifica fins de jogo
		if (camera.position.x > FinalX) { // Verifica posicao
			resetGame(EnumGameState.WON) ;
		}
		
		if (DisplaySingleton.getInstance().getStateTime() > maximumTime) { // Verifica tempo
			resetGame(EnumGameState.GAMEOVER) ;
		}
	}
	
	// Verifica a distancia do objeto para saber se esta perto da tela
	public boolean verifyDistance(GameObject object) {
		float x = object.getHitBox().x ;
		float renderProportion = 1.85f ; // Proporcão da distancia em consideracao o tamanho da tela
		if (object.getClass() != Pipe.class) {
			return Math.abs(camera.position.x - (x+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion ;
		} else {
			float xD = ((Pipe) object).getHitBoxDestination().x ;
			return Math.abs(camera.position.x - (x+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion || Math.abs(camera.position.x - (xD+object.getHitBox().width)) < DisplaySingleton.getInstance().getWidth()/renderProportion ;
		}
	}
	
	// Atualiza a camera
	public void updateCamera(Integer y) {
		int width=DisplaySingleton.getInstance().getWidth(), height=DisplaySingleton.getInstance().getHeight() ;
		// Verifica se esta no inicio ou no meio para camera comecar a seguir
		if ((player.getHitBox().x < (width/2) - player.getHitBox().width/2) && player.getHitBox().x >= -10)
			camera.position.x = width/2 ;
		else
			camera.position.x = player.getHitBox().x + player.getHitBox().width/2 ;
		
		// Se foi passado y atualiza
		if (y != null) {
			camera.position.y = y ;
		}
		
		camera.update() ; // Atualiza a camera
	}
	
	// Desenha em tela o tempo de jogo
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
	
	// Reseta o jogo e cria um novo
	public void resetGame(EnumGameState gameState) {
		this.dispose() ;
		this.create() ;
		DisplaySingleton.getInstance().resetStateTime() ;
		this.gameState.setState(gameState) ;
	}
	
	// Libera os itens
	@Override
	public void dispose() {
		PlayerSingleton.dispose() ;
		SoundHandler.dispose() ;
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

	@Override
	public void show() {
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
