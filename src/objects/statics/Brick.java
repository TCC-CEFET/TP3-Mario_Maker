package objects.statics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.SoundHandler;
import objects.* ;
import objects.movables.* ;
import singletons.* ;

// Classe do tijolo
public class Brick extends GameObject {
	public Brick(int x, int y) {
		super(x, y, BrickSingleton.getInstance().getWidth(), BrickSingleton.getInstance().getHeight()) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) { // Verifica colisao caso seja player
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
