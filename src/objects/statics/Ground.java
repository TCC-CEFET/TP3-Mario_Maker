package objects.statics ;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Ground extends GameObject {
	private Rectangle top ;
	
	public Ground(int x, int y) {
		super(x, y, GroundSingleton.getInstance().getWidth(), GroundSingleton.getInstance().getHeightFull()) ;
		
		int width=GroundSingleton.getInstance().getWidth(), height=GroundSingleton.getInstance().getHeightFull(), heightTop=GroundSingleton.getInstance().getHeightTop() ;
		top = new Rectangle(x, y+(height - heightTop), width, heightTop) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
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
	
	public Rectangle getTopHitBox() {
		return top ;
	}

	@Override
	public void remove() {
		
	}
}