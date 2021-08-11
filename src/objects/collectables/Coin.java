package objects.collectables;

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

public class Coin extends GameObject {
	public Coin(int x, int y) {
		int width=CoinSingleton.getInstance().getWidth(), height=CoinSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
	}

	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) {
			if(hitBox.overlaps(object.getHitBox())) {
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
		Texture texture = CoinSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}

	@Override
	public Rectangle getHitBox() {
		return hitBox ;
	}

	@Override
	public int hitAction(int side) {
		return 3 ;
	}
	
	@Override
	public void remove() {
		
	}
}
