import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.BrickSingleton;
import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Ground extends GameObject {
	private Rectangle full, top ;
	
	public Ground(int x, int y) {
		int width=GroundSingleton.getInstance().getWidth(), height=GroundSingleton.getInstance().getHeightFull(), heightTop=GroundSingleton.getInstance().getHeightTop() ;
		full = new Rectangle(x, y, width, height) ;
		top = new Rectangle(x, y+(height - heightTop), width, heightTop) ;
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
		full.x = x ;
		full.y = y ;
		
		top.x = x ;
		top.y = y+(full.height - top.height) ;
	}

	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = GroundSingleton.getInstance().getTexture();
		batch.draw(texture, full.x, full.y, full.width, full.height) ;
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return 1 ;
	}

}