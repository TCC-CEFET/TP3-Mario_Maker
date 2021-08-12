package objects.statics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import handlers.SoundHandler;
import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class Brick extends GameObject {
	public Brick(int x, int y) {
		int width=BrickSingleton.getInstance().getWidth(), height=BrickSingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) {
			if(hitBox.overlaps(((Player) object).getTopHitBox()) && ((Player) object).getPlayerState().isBig() && ((Player) object).getPlayerState().isJumping()) {
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
	public void draw(SpriteBatch batch) {
		Texture texture = BrickSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void remove() {
		SoundHandler.getInstance().playBreakBlock() ;
	}
}
