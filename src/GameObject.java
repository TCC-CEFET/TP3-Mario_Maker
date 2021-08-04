import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
	public abstract void hits(Rectangle rectangle) ;
	public abstract void action(Float x, Float y) ;
	public abstract void update() ;
	public abstract void setPosition(float x, float y) ;
	public abstract void moveLeft() ;
	public abstract void moveRight() ;
	public abstract void draw(SpriteBatch batch) ;
	public abstract void jump() ;
	public abstract Rectangle getHitBox() ;
	public abstract int hitAction(int side) ;
}
