package objects.movables.enemies;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.* ;
import objects.characteristics.* ;
import objects.movables.* ;
import singletons.* ;

// Classe do goomba
public class Goomba extends Enemy {
	public Goomba(int x, int y, Direction direction) {
		super(x, y, GoombaSingleton.getInstance().getWidth(), GoombaSingleton.getInstance().getHeight(), direction) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() == Player.class) { // Verifica colisao caso seja um player
			if (hitBox.overlaps(((Player) object).getBottomHitBox()) && !hitBox.overlaps(((Player) object).getLeftHitBox()) && !hitBox.overlaps(((Player) object).getRightHitBox())) { // Morto pelo player
				remove() ;
				return true ;
			} else if (hitBox.overlaps(object.getHitBox()) && !((Player) object).getPlayerState().isIntangible()) { // Troca de direcao ao bater no player tangivel
				direction = direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT ;
			}
		}
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		TextureRegion frame = GoombaSingleton.getInstance().getCurrentFrame(direction);
		batch.draw(frame, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
}
