import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Player extends GameObject implements Movel, InputProcessor {
	private Rectangle bottom, left, right, top, full ;
	private int action ;
	private float velocityY ;
	private boolean pulando ;
	private boolean pressingJump ;
	
	public Player(float x, float y) {
		int width=PlayerSingleton.getInstance().getWidth(), height=PlayerSingleton.getInstance().getHeight() ;
		full = new Rectangle(x, y, width, height) ;
		bottom = new Rectangle(x+(width/20), y, width-(width/10), height/8) ;
		left = new Rectangle(x, y+height/8, width/2, height-(bottom.height*2)) ;
		right = new Rectangle(x+width/2, y+bottom.height, width/2, height-(bottom.height*2)) ;
		top = new Rectangle(x+(width/20), y+(height-(height/8)), width-(width/10), height/8) ;
		
		this.setPosition(x, y) ;
		velocityY = 0 ;
		
		pulando = false ;
		pressingJump = false ;
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void hits(GameObject object) {
		if(object.getClass() == Enemy.class) {
			if(full.overlaps(object.getHitBox())) {
				action(0f, 33f);
			}
			
		} else if (object.getClass() == Brick.class) {
			if (top.overlaps(object.getHitBox())) {
				action(null, object.getHitBox().y - full.height) ;
			}
			
			if (bottom.overlaps(object.getHitBox())) {
				action(null, object.getHitBox().y + object.getHitBox().height) ;
				pulando = false ;
			}
			
			if (right.overlaps(object.getHitBox())) {
				action(object.getHitBox().x - full.width - 1, null) ;
			}
			
			if (left.overlaps(object.getHitBox())) {
				action(object.getHitBox().x + object.getHitBox().width + 1, null) ;
			}
		} else if (object.getClass() == Ground.class) {
			if (right.overlaps(object.getHitBox()) && left.overlaps(object.getHitBox())) {
				if (bottom.overlaps(((Ground)object).getTopHitBox())) {
					action(null, object.getHitBox().y + object.getHitBox().height) ;
				}
			} else {
				if (bottom.overlaps(object.getHitBox())) {
					action(null, object.getHitBox().y + object.getHitBox().height) ;
					pulando = false ;
				}
				
				if (right.overlaps(object.getHitBox())) {
					action(object.getHitBox().x - full.width - 1, null) ;
				}
				
				if (left.overlaps(object.getHitBox())) {
					action(object.getHitBox().x + object.getHitBox().width + 1, null) ;
				}
			}
		}
		
	}
	
	@Override
	public void action(Float x, Float y) {
		if (x == null && y == null) {
			
		} else if (x == null) {
			velocityY = 0 ;
			setPosition(full.x, y) ;
		} else if (y == null) {
			setPosition(x, full.y) ;
		} else {
			setPosition(x, y) ;
		}
	}
	
	@Override
	public void update() {
		velocityY -= 10 * Gdx.graphics.getDeltaTime() ;
		full.y += velocityY ;
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void setPosition(float x, float y) {
		full.x = x ;
		full.y = y ;
		
		bottom.x = x+(full.width/20) ;
		bottom.y = y ;
		
		left.x = x ;
		left.y = y + bottom.height ;
		
		right.x = x + (full.width/2) ;
		right.y = y + bottom.height ;

		top.x = x + (full.width/20) ;
		top.y = y + (full.height-(full.height/8)) ;
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
		full.x -= 100 * Gdx.graphics.getDeltaTime();
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void moveRight() {
		full.x += 100 * Gdx.graphics.getDeltaTime() ;
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = PlayerSingleton.getInstance().getTexture();
		batch.draw(texture, full.x, full.y, full.width, full.height) ;
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
		return full ;
	}
	
	public Rectangle getTopHitBox() {
		return top ;
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
	
