import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
	protected Rectangle hitBox ;
	
	public abstract boolean verifyCollision(GameObject object) ;
	public abstract void update() ;
	public void setPosition(Float x, Float y) {
		if (x == null && y == null) {
			x = hitBox.x ;
			y = hitBox.y ;
		} else if (x == null) {
			x = hitBox.x ;
		} else if (y == null) {
			y = hitBox.y ;
		}
		
		hitBox.x = x ;
		hitBox.y = y ;
	}
	public abstract void draw(SpriteBatch batch) ;
	public abstract Rectangle getHitBox() ;
	public abstract int hitAction(int side) ;
	public abstract void remove() ;
}
