package objects;

import java.util.ArrayList;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public abstract class MovableObject extends GameObject {
	protected Direction direction ;
	
	public MovableObject(Direction direction) {
		this.direction = direction ;
	}
	
	public abstract void control() ;
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (hitBox.y < 0-hitBox.getHeight()-10 || hitBox.x < 0-3) {
			remove() ;
			return true ;
		}
		
		return false ;
	}
}
