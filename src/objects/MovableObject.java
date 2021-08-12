package objects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public abstract class MovableObject extends GameObject {
	protected Direction direction ;
	protected Rectangle middle ;
	protected int extraMiddleWidthSize=6 ;
	protected int lackMiddleHeightSize=32 ;
	
	public MovableObject(int x, int y, int width, int height, Direction direction) {
		this.direction = direction ;
		middle = new Rectangle(x-extraMiddleWidthSize/2, y+lackMiddleHeightSize/2, width+extraMiddleWidthSize, height-lackMiddleHeightSize) ;
	}
	
	public abstract void control() ;
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() != Player.class && this.getClass() != Player.class) {
			if (middle.overlaps(object.getHitBox())) {
				direction = direction == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT ;
			}
			if (hitBox.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
			}
			if (object.getClass() == Pipe.class) {
				if (middle.overlaps(((Pipe) object).getHitBoxDestination())) {
					direction = direction == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT ;
				}
				if (hitBox.overlaps(((Pipe) object).getHitBoxDestination())) {
					setPosition(null, ((Pipe) object).getHitBoxDestination().y + ((Pipe) object).getHitBoxDestination().height) ;
				}
			}
		}
		
		if (hitBox.y < 0-hitBox.getHeight()-10) {
			return true ;
		}
		
		return false ;
	}
	
	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y) ;
		
		middle.x = hitBox.x-extraMiddleWidthSize/2 ;
		middle.y = hitBox.y+lackMiddleHeightSize/2 ;
	}
}
