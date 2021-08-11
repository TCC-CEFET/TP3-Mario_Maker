package objects.collectables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Mushroom extends MovableObject {
	public Mushroom(int x, int y) {
		super(Direction.RIGHT) ;
		
		int width=MushroomSingleton.getInstance().getWidth(), height=MushroomSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
	}

	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) {
			if(hitBox.overlaps(object.getHitBox())) {
				remove() ;
				return true ;
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
		int velocityY = MushroomSingleton.getInstance().getVelocityY() ;
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
		int velocityX = MushroomSingleton.getInstance().getVelocityX() ;
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = MushroomSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
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
