package objects.movables;


import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import handlers.SoundHandler;
import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Koopa extends MovableObject {
	private boolean isHidden ;
	private Direction lastDirection ;
	
	public Koopa(int x, int y, Direction direction) {
		super(x, y, KoopaSingleton.getInstance().getWidth(false), KoopaSingleton.getInstance().getHeight(false), direction) ;
		
		isHidden = false ;
		
		int width=KoopaSingleton.getInstance().getWidth(isHidden), height=KoopaSingleton.getInstance().getHeight(isHidden) ;
		hitBox = new Rectangle(x, y, width, height) ;
		
		lastDirection = Direction.LEFT ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() == Player.class) {
			if (hitBox.overlaps(((Player) object).getBottomHitBox()) && !hitBox.overlaps(((Player) object).getLeftHitBox()) && !hitBox.overlaps(((Player) object).getRightHitBox())) {
				remove() ;
			} else if (hitBox.overlaps(object.getHitBox()) && !((Player) object).getState().isIntangible()) {
				direction = direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT ;
			}
		} else if (object.getClass() == Koopa.class) {
			if (((Koopa) object).isHidden() && ((Koopa) object) .getDirection() != Direction.STOP) {
				if (hitBox.overlaps(object.getHitBox())) {
					remove() ;
					return true ;
				}
			}
		}
		
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void update() {
		int velocityY = KoopaSingleton.getInstance().getVelocityY() ;
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
	public void updateHitBox() {
		int width=KoopaSingleton.getInstance().getWidth(isHidden), height=KoopaSingleton.getInstance().getHeight(isHidden) ;
		
		hitBox.width = width ;
		hitBox.height = height ;
		
		middle.width = width + this.extraMiddleWidthSize ;
		middle.height = height - this.lackMiddleHeightSize ;
	}
	
	@Override
	public void control() {
		int velocityX = KoopaSingleton.getInstance().getVelocityX(isHidden) ;
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else if (direction == Direction.RIGHT) {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		TextureRegion frame = KoopaSingleton.getInstance().getActualFrame(direction, isHidden);
		int width=KoopaSingleton.getInstance().getRunWidth(), height=KoopaSingleton.getInstance().getRunHeight() ;
		batch.draw(frame, hitBox.x, hitBox.y, width, height) ;
	}
	
	public boolean isHidden() {
		return isHidden ;
	}
	
	@Override
	public void remove() {
		isHidden = true ;
		updateHitBox() ;
		direction = direction == Direction.STOP ? (lastDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT) : Direction.STOP ;
		if (direction != Direction.STOP) lastDirection = direction ;
		
		SoundHandler.getInstance().playKick() ;
	}
}
