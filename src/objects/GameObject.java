package objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public abstract class GameObject {
	protected Rectangle hitBox ;
	
	public abstract boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) ;
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
	public Rectangle getHitBox() {
		return hitBox ;
	}
	public abstract void remove() ;
	
	public Class<?> getSuperClass() {
		return GameObject.class ;
	}
}
