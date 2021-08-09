import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.BrickSingleton;
import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Ground extends GameObject {
	private Rectangle top ;
	
	public Ground(int x, int y) {
		int width=GroundSingleton.getInstance().getWidth(), height=GroundSingleton.getInstance().getHeightFull(), heightTop=GroundSingleton.getInstance().getHeightTop() ;
		hitBox = new Rectangle(x, y, width, height) ;
		top = new Rectangle(x, y+(height - heightTop), width, heightTop) ;
	}
	
	@Override
	public boolean verifyCollision(GameObject object) {
		return false ;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y);
		
		x = hitBox.x  ;
		y = hitBox.y ;
		
		top.x = x ;
		top.y = y+(hitBox.height - top.height) ;
	}

	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = GroundSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}

	@Override
	public Rectangle getHitBox() {
		return hitBox ;
	}
	
	public Rectangle getTopHitBox() {
		return top ;
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