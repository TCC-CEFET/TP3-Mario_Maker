import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.BrickSingleton;
import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Brick extends GameObject {
	public Brick(int x, int y) {
		int width=BrickSingleton.getInstance().getWidth(), height=BrickSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
	}
	
	@Override
	public boolean verifyCollision(GameObject object) {
		if(object.getClass() == Player.class) {
			if(hitBox.overlaps(((Player) object).getTopHitBox())) {
				remove() ;
				return true ;
			}
		}
		
		return false ;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y) ;
		
		x = hitBox.x  ;
		y = hitBox.y ;
	}

	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = BrickSingleton.getInstance().getTexture();
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
		// TODO Auto-generated method stub
		return 1 ;
	}
	
	@Override
	public void remove() {
		
	}
}
