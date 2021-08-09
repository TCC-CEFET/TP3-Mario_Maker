
import com.badlogic.gdx.Gdx ;
import com.badlogic.gdx.Input ;
import com.badlogic.gdx.InputProcessor ;
import com.badlogic.gdx.graphics.Texture ;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch ;
import com.badlogic.gdx.math.Rectangle ;

import singletons.CoinSingleton;
import singletons.DisplaySingleton;
import singletons.EnemySingleton;
import singletons.PlayerSingleton ;
import singletons.BrickSingleton ;
import singletons.MushroomSingleton ;

public class Player extends MovableObject implements InputProcessor {
	private Rectangle bottom, left, right, top ;
	private int action ;
	private float velocityY ;
	
	private int points ;
	private int coins ;
	private boolean isBig ;
	
	private boolean pulando ;
	private boolean pressingJump ;
	
	public Player(float x, float y, Direction direction) {
		super(direction) ;
		
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		bottom = new Rectangle(x+(width/20), y, width-(width/10), height/10) ;
		left = new Rectangle(x, y+bottom.height, width/2, height-(bottom.height*2)) ;
		right = new Rectangle(x+width/2, y+bottom.height, width/2, height-(bottom.height*2)) ;
		top = new Rectangle(x+(width/20), y+(height-bottom.height), width-(width/10), bottom.height) ;
		
		this.setPosition(x, y) ;
		this.setHeight(false) ;
		velocityY = 0 ;
		points = 0 ;
		coins = 0 ;
		
		pulando = false ;
		pressingJump = false ;
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public boolean verifyCollision(GameObject object) {
		if(object.getClass() == Enemy.class) { // Verifica colisao caso seja inimigo
			if (hitBox.overlaps(object.getHitBox()) && !bottom.overlaps(object.getHitBox())) {
				if (isBig) setHeight(false) ;
				else remove() ;
			} else if (bottom.overlaps(object.getHitBox())) {
				points += EnemySingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == Brick.class) { // Verifica colisao caso seja tijolo
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				if (isBig) points += BrickSingleton.getInstance().getPoints() ;
			}
			
			if (bottom.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
				pulando = false ;
				velocityY = 0 ;
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
				}
			} else {
				if (bottom.overlaps(object.getHitBox())) {
					setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
					pulando = false ;
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
		}
		
		return false ;
	}
	
	@Override
	public void update() {
		velocityY -= 10 * Gdx.graphics.getDeltaTime() ;
		hitBox.y += velocityY ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y);
		
		x = hitBox.x  ;
		y = hitBox.y ;
		
		bottom.x = x+(hitBox.width/20) ;
		bottom.y = y ;
		
		left.x = x ;
		left.y = y + bottom.height ;
		
		right.x = x + (hitBox.width/2) ;
		right.y = y + bottom.height ;

		top.x = x + (hitBox.width/20) ;
		top.y = y + (hitBox.height-(hitBox.height/8)) ;
	}
	
	@Override
	public void control() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			hitBox.x -= 100 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
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
			if (pulando) return ;
			
			pressingJump = true ;
			pulando = true ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = PlayerSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
		
		int width=DisplaySingleton.getInstance().getWidth(), height=DisplaySingleton.getInstance().getHeight() ;
		BitmapFont font = new BitmapFont() ;
		font.setColor(1, 1, 1, 1) ;
		font.setScale(1.7f) ;
		float xPontDist=(width/2)-50, yPont=DisplaySingleton.getInstance().getHeight()-30 ;
		if (hitBox.x < (width/2) - hitBox.width/2)
			font.draw(batch, String.valueOf(points), (width/2)-xPontDist, yPont) ;
		else
			font.draw(batch, String.valueOf(points), (hitBox.x + hitBox.width/2) - xPontDist, yPont) ;
		
		float xCoinDist=150, yCoin=DisplaySingleton.getInstance().getHeight()-30 ;
		if (hitBox.x < (width/2) - hitBox.width/2)
			font.draw(batch, "C x " + String.valueOf(coins), (width/2)-xCoinDist, yCoin) ;
		else
			font.draw(batch, "C x " + String.valueOf(coins), (hitBox.x + hitBox.width/2) - xCoinDist, yCoin) ;
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
	
	public boolean getIsBig() {
		return isBig ;
	}
	
	public void setHeight(boolean isBig) {
		this.isBig = isBig ;
		PlayerSingleton.getInstance().setHeight(this.isBig) ;
		
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		
		hitBox.width = width ;
		hitBox.height = height ;
		
		bottom.width = width-(width/10) ;
		bottom.height = height/10 ;
		
		left.width = width/2 ;
		left.height = height-(bottom.height*2) ;
		
		right.width = width/2 ;
		right.height = height-(bottom.height*2) ;
		
		top.width = width-(width/10) ;
		top.height = bottom.height ;
	}

	@Override
	public int hitAction(int side) {
		
		return 0 ;
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
		}
		return false;
	}
	
	@Override
	public void remove() {
		setPosition(0f, 33f) ;
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
	
