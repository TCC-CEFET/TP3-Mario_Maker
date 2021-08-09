
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

public class Player extends GameObject implements Movel, InputProcessor {
	private Rectangle bottom, left, right, top ;
	private int action ;
	private float velocityY ;
	
	private int points ;
	private int coins ;
	
	private boolean pulando ;
	private boolean pressingJump ;
	
	public Player(float x, float y) {
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		bottom = new Rectangle(x+(width/20), y, width-(width/10), height/8) ;
		left = new Rectangle(x, y+height/8, width/2, height-(bottom.height*2)) ;
		right = new Rectangle(x+width/2, y+bottom.height, width/2, height-(bottom.height*2)) ;
		top = new Rectangle(x+(width/20), y+(height-(height/8)), width-(width/10), height/8) ;
		
		this.setPosition(x, y) ;
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
				setPosition(0f, 33f);
			} else if (bottom.overlaps(object.getHitBox())) {
				points += EnemySingleton.getInstance().getPoints() ;
			}
		} else if (object.getClass() == Brick.class) { // Verifica colisao caso seja tijolo
			if (top.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y - hitBox.height) ;
				velocityY = 0 ;
				points += BrickSingleton.getInstance().getPoints() ;
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
			moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			jump();
		}
	}
	
	@Override
	public void moveLeft() {
		hitBox.x -= 100 * Gdx.graphics.getDeltaTime();
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void moveRight() {
		hitBox.x += 100 * Gdx.graphics.getDeltaTime() ;
		this.setPosition(hitBox.x, hitBox.y) ;
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
	public void jump() {
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
	
