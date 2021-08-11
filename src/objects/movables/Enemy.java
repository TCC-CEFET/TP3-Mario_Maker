package objects.movables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Enemy extends MovableObject {
	public Enemy(int x, int y, Direction direction) {
		super(direction) ;
		
		int width=EnemySingleton.getInstance().getWidth(), height=EnemySingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() == Player.class) {
			if (hitBox.overlaps(((Player) object).getBottomHitBox()) && !hitBox.overlaps(((Player) object).getLeftHitBox()) && !hitBox.overlaps(((Player) object).getRightHitBox())) {
				remove() ;
				return true ;
			} else if (hitBox.overlaps(object.getHitBox())) {
				direction = direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT ;
			}
		} else {
			if (hitBox.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
			}
		}
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void update() {
		int velocityY = EnemySingleton.getInstance().getVelocityY() ;
		hitBox.y -= velocityY * Gdx.graphics.getDeltaTime() > 3 ? 3 : velocityY * Gdx.graphics.getDeltaTime()  ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y) ;
		
		x = hitBox.x  ;
		y = hitBox.y ;
	}
	
	@Override
	public void control() {
		int velocityX = EnemySingleton.getInstance().getVelocityX() ;
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		TextureRegion frame = EnemySingleton.getInstance().getActualFrame(direction);
		batch.draw(frame, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public Rectangle getHitBox() {
		return hitBox ;
	}
	
	@Override
	public int hitAction(int side) {
		if(side == 1) {
			return 2;
		}
		return 1 ;
	}
	
	@Override
	public void remove() {
		
	}
}
