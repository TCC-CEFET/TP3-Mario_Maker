import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.EnemySingleton;
import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Enemy extends GameObject implements Movel {
	public Enemy(int x, int y) {
		int width=EnemySingleton.getInstance().getWidth(), height=EnemySingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		
		this.setPosition((float) x, (float) y) ;
	}
	
	@Override
	public boolean verifyCollision(GameObject object) {
		if (object.getClass() == Player.class) {
			if (hitBox.overlaps(((Player) object).getBottomHitBox())) {
				remove() ;
				return true ;
			}
		} else {
			if (hitBox.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + hitBox.height) ;
			}
		}
		
		return false ;
	}
	
	@Override
	public void update() {
		hitBox.y -= 100 * Gdx.graphics.getDeltaTime() ;
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
		moveLeft() ;
	}
	
	@Override
	public void moveLeft() {
		hitBox.x -= 50 * Gdx.graphics.getDeltaTime();
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void moveRight() {
		hitBox.x += 100 * Gdx.graphics.getDeltaTime() ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = EnemySingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void jump() {
		// TODO Auto-generated method stub
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
