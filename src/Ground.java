import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Ground extends GameObject {
	private Rectangle hitBox ;
	
	public Ground(int x, int y) {
		int width=GroundSingleton.getInstance().getWidth(), height=GroundSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		setPosition(x, y) ;
	}
	
	@Override
	public void hits(GameObject object) {
	}

	@Override
	public void action(Float x, Float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(float x, float y) {
		Sprite sprite = GroundSingleton.getInstance().getSprite();
		hitBox.x = x ;
		hitBox.y = y ;
		sprite.setPosition(x, y) ;
	}

	@Override
	public void draw(SpriteBatch batch) {
		Sprite sprite = GroundSingleton.getInstance().getSprite();
		int width=GroundSingleton.getInstance().getWidth(), height=GroundSingleton.getInstance().getHeight() ;
		batch.draw(sprite, hitBox.x, hitBox.y, width, height);
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

}