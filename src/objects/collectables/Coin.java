package objects.collectables;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.SoundHandler;
import objects.* ;
import objects.movables.* ;
import singletons.* ;

// Classe da moeda
public class Coin extends GameObject {
	public Coin(int x, int y) {
		super(x, y, CoinSingleton.getInstance().getWidth(), CoinSingleton.getInstance().getHeight()) ;
	}

	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) { // Verifica colisao caso seja player
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
	public void draw(SpriteBatch batch) {
		Texture texture = CoinSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void remove() {
		SoundHandler.getInstance().playCoin() ;
	}
}
