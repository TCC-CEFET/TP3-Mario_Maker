package objects.movables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx ;
import com.badlogic.gdx.Input ;
import com.badlogic.gdx.InputProcessor ;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch ;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle ;

import handlers.SoundHandler;
import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.enemies.* ;
import objects.statics.* ;
import singletons.* ;

// Classe do jogador
public class Player extends MovableObject implements InputProcessor {
	private Rectangle bottom, left, right, top ; // HitBoxes
	private float velocityY ; // Velocidade do eixo y
	
	private PlayerState playerState ; // Estado
	
	private int points ;
	private int coins ;
	
	// Variaveis de marcacao
	private boolean pressingJump ;
	
	private float invencibleStart ;
	private final float invencibleMaxTime ;
	private float startInvisibleTime, startVisibleTime ;
	private final float blinkTime ;
	private boolean isVisible ;
	
	private int tocaSomPulo ;
	
	public Player(int x, int y, Direction direction) {
		super(x, y, PlayerSingleton.getInstance().getWidth(), PlayerSingleton.getInstance().getHeight(), direction) ;
		
		bottom = new Rectangle() ;
		left = new Rectangle() ;
		right = new Rectangle() ;
		top = new Rectangle() ;
		
		this.setPosition(hitBox.x, hitBox.y) ;
		playerState = new PlayerState(direction) ;
		this.setHeight(false) ;
		velocityY = 0 ;
		points = 0 ;
		coins = 0 ;
		
		pressingJump = false ;
		invencibleMaxTime = 2f ;
		blinkTime = 0.2f ;
		startInvisibleTime=0 ; startVisibleTime=0 ;
		isVisible = true ;
		
		tocaSomPulo = 1 ;
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Goomba.class && !playerState.isIntangible()) { // Verifica colisao caso seja goomba
			if (hitBox.overlaps(object.getHitBox()) && !bottom.overlaps(object.getHitBox())) {
				if (playerState.isBig()) {
					setHeight(false) ;
					playerState.setIntangible(true) ;
					invencibleStart = DisplaySingleton.getInstance().getStateTime() ;
				} else if (!playerState.isIntangible()) {
					remove() ;
					return true ;
				}
			} else if (bottom.overlaps(object.getHitBox())) {
				velocityY = 4 ;
				playerState.setJumping(true) ;
				points += GoombaSingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == Koopa.class && !playerState.isIntangible()) { // Verifica colisao caso seja koopa
			if (hitBox.overlaps(object.getHitBox()) && !bottom.overlaps(object.getHitBox()) && ((Koopa) object).getDirection() != Direction.STOP) {
				if (playerState.isBig()) {
					setHeight(false) ;
					playerState.setIntangible(true) ;
					invencibleStart = DisplaySingleton.getInstance().getStateTime() ;
				} else if (!playerState.isIntangible()) {
					remove() ;
					return true ;
				}
			} else if (bottom.overlaps(object.getHitBox())) {
				velocityY = 3 ;
				playerState.setJumping(true) ;
				if (((Koopa) object).getDirection() == Direction.STOP) points += GoombaSingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == Brick.class) { // Verifica colisao caso seja brick
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				playerState.setJumping(false) ;
				velocityY = 0 ;
			}
			
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				pressingJump = false ;
				if (playerState.isBig()) points += BrickSingleton.getInstance().getPoints() ;
			}
			
			if (right.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x - hitBox.width - 1, null) ;
			}
			
			if (left.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x + object.getHitBox().width + 1, null) ;
			}
		} else if (object.getClass() == Ground.class) { // Verifica colisao caso seja ground
			if (right.overlaps(object.getHitBox()) && left.overlaps(object.getHitBox())) {
				if (bottom.overlaps(((Ground)object).getTopHitBox())) {
					setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
					velocityY = 0 ;
					pressingJump = false ;
				}
			} else {
				if (bottom.overlaps(object.getHitBox())) {
					setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
					playerState.setJumping(false) ;
					velocityY = 0 ;
				}
				
				if (right.overlaps(object.getHitBox())) {
					setPosition(object.getHitBox().x - hitBox.width - 1, null) ;
				}
				
				if (left.overlaps(object.getHitBox())) {
					setPosition(object.getHitBox().x + object.getHitBox().width + 1, null) ;
				}
			}
		} else if (object.getClass() == Coin.class) { // Verifica colisao caso seja moeda
			if (hitBox.overlaps(object.getHitBox())) {
				points += CoinSingleton.getInstance().getPoints() ;
				coins++ ;
			}
		} else if (object.getClass() == Mushroom.class) { // Verifica colisao caso seja cogumelo
			if (hitBox.overlaps(object.getHitBox())) {
				setHeight(true) ;
				points += MushroomSingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == LuckyBlock.class) { // Verifica colisao caso seja luckyblock
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				playerState.setJumping(false) ;
				velocityY = 0 ;
			}
			
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				pressingJump = false ;
				points += LuckyBlockSingleton.getInstance().getPoints() ;
			}
			
			if (right.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x - hitBox.width - 1, null) ;
			}
			
			if (left.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x + object.getHitBox().width + 1, null) ;
			}
		} else if (object.getClass() == Pipe.class) { // Verifica colisao caso seja pipe
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				playerState.setJumping(false) ;
				velocityY = 0 ;
			}
			
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				pressingJump = false ;
			}
			
			if (right.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x - hitBox.width - 1, null) ;
			}
			
			if (left.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x + object.getHitBox().width + 1, null) ;
			}
			
			if (bottom.overlaps(((Pipe) object).getHitBoxDestination())) {
				setPosition(null, ((Pipe) object).getHitBoxDestination().y + ((Pipe) object).getHitBoxDestination().height) ;
				playerState.setJumping(false) ;
				velocityY = 0 ;
			}
			
			if (top.overlaps(((Pipe) object).getHitBoxDestination())) {
				setPosition(null, ((Pipe) object).getHitBoxDestination().y - hitBox.height) ;
				velocityY = 0 ;
				pressingJump = false ;
			}
			
			if (right.overlaps(((Pipe) object).getHitBoxDestination())) {
				setPosition(((Pipe) object).getHitBoxDestination().x - hitBox.width - 1, null) ;
			}
			
			if (left.overlaps(((Pipe) object).getHitBoxDestination())) {
				setPosition(((Pipe) object).getHitBoxDestination().x + ((Pipe) object).getHitBoxDestination().width + 1, null) ;
			}
		}
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void update() {
		// Atualiza variaveis de marcacao
		if (DisplaySingleton.getInstance().getStateTime() > invencibleStart+invencibleMaxTime) playerState.setIntangible(false) ;
		if (isVisible && DisplaySingleton.getInstance().getStateTime() > startVisibleTime+blinkTime) {
			isVisible = false ;
			startInvisibleTime = DisplaySingleton.getInstance().getStateTime() ;
		} else if (!isVisible && DisplaySingleton.getInstance().getStateTime() > startInvisibleTime+blinkTime) {
			isVisible = true ;
			startVisibleTime = DisplaySingleton.getInstance().getStateTime() ;
		}
		
		// Atualiza a posicao y
		velocityY -= 10 * Gdx.graphics.getDeltaTime() > 0.5 ? 0.5 : 10 * Gdx.graphics.getDeltaTime() ; // Poe um maximo na queda entre frames caso comece a cair fps
		hitBox.y += velocityY ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y);
		
		x = hitBox.x  ;
		y = hitBox.y ;
		
		bottom.x = x+((hitBox.width - PlayerSingleton.getInstance().getBottomWidth())/2) ;
		bottom.y = y ;
		
		left.x = x ;
		left.y = y + bottom.height ;
		
		right.x = x + (hitBox.width/2) ;
		right.y = y + bottom.height ;

		top.x = bottom.x ;
		top.y = y + (hitBox.height-(hitBox.height/8)) ;
	}
	
	@Override
	public void control() {
		playerState.setDirection(Direction.STOP) ;
		
		int velocityX = 150 ;
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			setCrouch(true) ;
		} else {
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				playerState.setDirection(Direction.LEFT) ;
				hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				playerState.setDirection(Direction.RIGHT) ;
				hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (pressingJump) {
					if (velocityY < 6) {
						velocityY += 2 ;
					} else {
						pressingJump = false ;
					}
				}
				if (playerState.isJumping()) return ;
				
				pressingJump = true ;
				playerState.setJumping(true) ;
				
				if (tocaSomPulo <= 1) {
					SoundHandler.getInstance().playJump(playerState.isBig()) ;
					tocaSomPulo++ ;
				} else {
					tocaSomPulo=1 ;
				}
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		// Mario
		if (playerState.isIntangible()) { // Verifica a tangibilidade para piscar
			if (isVisible) {
				TextureRegion frame = PlayerSingleton.getInstance().getCurrentFrame(playerState) ;
				int width=PlayerSingleton.getInstance().getBigWidth(), height=PlayerSingleton.getInstance().getBigHeight() ;
				batch.draw(frame, hitBox.x, hitBox.y, width, height) ;
			}
		} else {
			TextureRegion frame = PlayerSingleton.getInstance().getCurrentFrame(playerState) ;
			int width=PlayerSingleton.getInstance().getBigWidth(), height=PlayerSingleton.getInstance().getBigHeight() ;
			batch.draw(frame, hitBox.x, hitBox.y, width, height) ;
		}
		
		// Escreve pontuacao
		int displayWidth=DisplaySingleton.getInstance().getWidth(), displayHeight=DisplaySingleton.getInstance().getHeight() ;
		BitmapFont font = new BitmapFont() ;
		font.setColor(1, 1, 1, 1) ;
		font.setScale(1.7f) ;
		float xPointDist=(displayWidth/2)-50, yPoint=displayHeight-40 ;
		if ((hitBox.x < (displayWidth/2) - hitBox.width/2) && hitBox.x > 0) {
			font.draw(batch, "POINTS", (displayWidth/2)-xPointDist, yPoint+30) ;
			font.draw(batch, String.valueOf(points), (displayWidth/2)-xPointDist, yPoint) ;
		} else {
			font.draw(batch, "POINTS", (hitBox.x + hitBox.width/2) - xPointDist, yPoint+30) ;
			font.draw(batch, String.valueOf(points), (hitBox.x + hitBox.width/2) - xPointDist, yPoint) ;
		}
		
		// Quantidade de moedas
		font.setColor(1, 1, 0, 1) ;
		float xCoinDist=150, yCoin=DisplaySingleton.getInstance().getHeight()-30 ;
		if ((hitBox.x < (displayWidth/2) - hitBox.width/2) && hitBox.x > 0)
			font.draw(batch, "COINS x " + String.valueOf(coins), (displayWidth/2)-xCoinDist, yCoin) ;
		else
			font.draw(batch, "COINS x " + String.valueOf(coins), (hitBox.x + hitBox.width/2) - xCoinDist, yCoin) ;
	}

	public Rectangle getTopHitBox() {
		return top ;
	}
	
	public Rectangle getBottomHitBox() {
		return bottom ;
	}
	
	public Rectangle getLeftHitBox() {
		return left ;
	}
	
	public Rectangle getRightHitBox() {
		return right ;
	}
	
	public PlayerState getPlayerState() {
		return playerState ;
	}
	
	public void setHeight(boolean isBig) {
		playerState.setBig(isBig) ;
		PlayerSingleton.getInstance().setHeight(playerState.isBig()) ;
		
		updateHitBox() ;
	}
	
	public void setCrouch(boolean isCrouched) {
		if (playerState.isBig()) {
			playerState.setCrouched(isCrouched) ;
		} else if (!playerState.isBig()) {
			playerState.setCrouched(false) ;
		}
		PlayerSingleton.getInstance().setCrouched(playerState.isCrouched(), playerState) ;
		
		updateHitBox() ;
	}
	
	@Override
	public void updateHitBox() {
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		
		hitBox.width = width ;
		hitBox.height = height ;
		
		bottom.width = PlayerSingleton.getInstance().getBottomWidth() ;
		bottom.height = height/10 ;
		
		left.width = width/2 ;
		left.height = height-(bottom.height*2) ;
		
		right.width = width/2 ;
		right.height = height-(bottom.height*2) ;
		
		top.width = bottom.width ;
		top.height = bottom.height ;
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		if (arg0 == Input.Keys.UP) {
			pressingJump = false ;
		} else if (arg0 == Input.Keys.DOWN) {
			setCrouch(false) ;
		}
		
		return false;
	}
	
	@Override
	public void remove() {
		SoundHandler.getInstance().playPlayerDie() ;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
	
