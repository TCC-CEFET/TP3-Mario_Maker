package objects.statics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import objects.* ;
import objects.collectables.* ;
import objects.movables.* ;
import singletons.* ;

// Classe do luckyblock (bloco ?)
public class LuckyBlock extends GameObject {
	private boolean hitted ; // Marca se ele ja foi acertado
	
	public LuckyBlock(int x, int y) {
		super(x, y, BrickSingleton.getInstance().getWidth(), BrickSingleton.getInstance().getHeight()) ;
		
		hitted = false ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) { // Verifica colisao caso seja um player
			if(hitBox.overlaps(((Player) object).getTopHitBox()) && !hitted && ((Player) object).getPlayerState().isJumping()) {
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
	public void draw(SpriteBatch batch) {
		Texture texture = LuckyBlockSingleton.getInstance().getTexture(hitted) ;
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void remove() {
		hitted = true ;
	}
}
