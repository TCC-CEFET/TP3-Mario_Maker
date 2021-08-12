package objects.movables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx ;
import com.badlogic.gdx.Input ;
import com.badlogic.gdx.InputProcessor ;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture ;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch ;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle ;

import game.screens.Game;
import handlers.SoundHandler;
import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Player extends MovableObject implements InputProcessor {
	private Rectangle bottom, left, right, top ;
	private float velocityY ;
	
	private State state ;
	
	private int points ;
	private int coins ;
	
	private boolean pressingJump ;
	
	private boolean isInvisible ;
	private float invencibleStart, invencibleMaxTime ;
	
	private int tocaSomPulo ;
	
	public Player(int x, int y, Direction direction) {
		super(x, y, PlayerSingleton.getInstance().getWidth(), PlayerSingleton.getInstance().getHeight(), direction) ;
		
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		bottom = new Rectangle(x+((width - PlayerSingleton.getInstance().getBottomWidth())/2), y, PlayerSingleton.getInstance().getBottomWidth(), height/10) ;
		left = new Rectangle(x, y+bottom.height, width/2, height-(bottom.height*2)) ;
		right = new Rectangle(x+width/2, y+bottom.height, width/2, height-(bottom.height*2)) ;
		top = new Rectangle(bottom.x, y+(height-bottom.height), bottom.width, bottom.height) ;
		
		this.setPosition(hitBox.x, hitBox.y) ;
		state = new State(direction) ;
		this.setHeight(false) ;
		velocityY = 0 ;
		points = 0 ;
		coins = 0 ;
		
		pressingJump = false ;
		
		isInvisible = false ;
		invencibleMaxTime = 1.5f ;
		
		tocaSomPulo = 1 ;
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Enemy.class) { // Verifica colisao caso seja inimigo
			if (hitBox.overlaps(object.getHitBox()) && !bottom.overlaps(object.getHitBox())) {
				if (state.isBig() && !isInvisible) {
					setHeight(false) ;
					isInvisible = true ;
					invencibleStart = DisplaySingleton.getInstance().getStateTime() ;
				} else if (!isInvisible) {
					remove() ;
					return true ;
				}
			} else if (bottom.overlaps(object.getHitBox())) {
				velocityY = 3 ;
				state.setJumping(true) ;
				points += EnemySingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == Brick.class) { // Verifica colisao caso seja tijolo
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				state.setJumping(false) ;
				velocityY = 0 ;
			}
			
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				pressingJump = false ;
				if (state.isBig()) points += BrickSingleton.getInstance().getPoints() ;
			}
			
			if (right.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x - hitBox.width - 1, null) ;
			}
			
			if (left.overlaps(object.getHitBox())) {
				setPosition(object.getHitBox().x + object.getHitBox().width + 1, null) ;
			}
		} else if (object.getClass() == Ground.class) { // Verifica colisao caso seja chao
			if (right.overlaps(object.getHitBox()) && left.overlaps(object.getHitBox())) {
				if (bottom.overlaps(((Ground)object).getTopHitBox())) {
					setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
					velocityY = 0 ;
					pressingJump = false ;
				}
			} else {
				if (bottom.overlaps(object.getHitBox())) {
					setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
					state.setJumping(false) ;
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
		} else if (object.getClass() == Mushroom.class) {
			if (hitBox.overlaps(object.getHitBox())) {
				setHeight(true) ;
				points += MushroomSingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == LuckyBlock.class) {
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				state.setJumping(false) ;
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
		} else if (object.getClass() == Pipe.class) {
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				state.setJumping(false) ;
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
				state.setJumping(false) ;
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
		if (DisplaySingleton.getInstance().getStateTime() > invencibleStart+invencibleMaxTime) isInvisible = false ;
		velocityY -= 10 * Gdx.graphics.getDeltaTime() > 0.5 ? 0.5 : 10 * Gdx.graphics.getDeltaTime() ;
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
		state.setDirection(Direction.STOP) ;
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			setCrouch(true) ;
		} else {
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				state.setDirection(Direction.LEFT) ;
				hitBox.x -= 100 * Gdx.graphics.getDeltaTime();
			}
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				state.setDirection(Direction.RIGHT) ;
				hitBox.x += 100 * Gdx.graphics.getDeltaTime() ;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (pressingJump) {
					if (velocityY < 6) {
						velocityY += 2 ;
					} else {
						pressingJump = false ;
					}
				}
				if (state.isJumping()) return ;
				
				pressingJump = true ;
				state.setJumping(true) ;
				
				if (tocaSomPulo <= 1) {
					SoundHandler.getInstance().playJump(state.isBig()) ;
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
		TextureRegion frame = PlayerSingleton.getInstance().getActualFrame(state) ;
		int width=PlayerSingleton.getInstance().getBigWidth(), height=PlayerSingleton.getInstance().getBigHeight() ;
		batch.draw(frame, hitBox.x, hitBox.y, width, height) ;
		
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

	@Override
	public Rectangle getHitBox() {
		return hitBox ;
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
	
	public State getState() {
		return state ;
	}
	
	public void setHeight(boolean isBig) {
		state.setBig(isBig) ;
		PlayerSingleton.getInstance().setHeight(state.isBig()) ;
		
		updateHitBox() ;
	}
	
	public void setCrouch(boolean isCrouched) {
		if (state.isBig()) {
			state.setCrouched(isCrouched) ;
		} else if (!state.isBig()) {
			state.setCrouched(false) ;
		}
		PlayerSingleton.getInstance().setCrouched(state.isCrouched(), state) ;
		
		updateHitBox() ;
	}
	
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
	
