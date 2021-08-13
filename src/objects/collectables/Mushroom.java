package objects.collectables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.SoundHandler;
import objects.* ;
import objects.characteristics.* ;
import objects.movables.* ;
import singletons.* ;

// Classe do mushroom
public class Mushroom extends MovableObject {
	public Mushroom(int x, int y) {
		super(x, y, MushroomSingleton.getInstance().getWidth(), MushroomSingleton.getInstance().getHeight(), Direction.RIGHT) ;
		
		SoundHandler.getInstance().playPowerupAppears() ;
	}

	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) { // Verifica colisao caso seja um player
			if(hitBox.overlaps(object.getHitBox())) {
				remove() ;
				return true ;
			}
		}
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}

	@Override
	public void update() {
		// Atualiza o Y
		int velocityY = MushroomSingleton.getInstance().getVelocityY() ;
		hitBox.y -= velocityY * Gdx.graphics.getDeltaTime() > 3 ? 3 : velocityY * Gdx.graphics.getDeltaTime()  ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void updateHitBox() {
		
	}
	
	@Override
	public void control() {
		int velocityX = MushroomSingleton.getInstance().getVelocityX() ;
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = MushroomSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void remove() {
		SoundHandler.getInstance().playPowerup() ;
	}
}
