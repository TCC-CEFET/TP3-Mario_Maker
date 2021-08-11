package objects.statics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class LuckyBlock extends GameObject {
	private boolean hitted ;
	
	public LuckyBlock(int x, int y) {
		int width=BrickSingleton.getInstance().getWidth(), height=BrickSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		hitted = false ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) {
			if(hitBox.overlaps(((Player) object).getTopHitBox()) && !hitted && ((Player) object).getState().isJumping()) {
				movableList.add(new Mushroom((int) hitBox.x, (int) (hitBox.y+hitBox.height))) ;
				remove() ;
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
		Texture texture = LuckyBlockSingleton.getInstance().getTexture(hitted) ;
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
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
		hitted = true ;
	}
}
