package objects.movables.enemies;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objects.* ;
import objects.characteristics.* ;
import objects.movables.* ;
import singletons.* ;

// Classe para o koopa
public class Koopa extends Enemy {
	private boolean isHidden ; // Controla o estado de estar escondido no casco
	
	private Direction lastDirection ; // Marcacao para intercalar direcao
	
	public Koopa(int x, int y, Direction direction) {
		super(x, y, KoopaSingleton.getInstance().getWidth(false), KoopaSingleton.getInstance().getHeight(false), direction) ;
		
		isHidden = false ;
		
		lastDirection = Direction.LEFT ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() == Player.class) { // Verifica colisao caso seja player
			if (hitBox.overlaps(((Player) object).getBottomHitBox()) && !hitBox.overlaps(((Player) object).getLeftHitBox()) && !hitBox.overlaps(((Player) object).getRightHitBox())) {
				remove() ;
			} else if (hitBox.overlaps(object.getHitBox()) && !((Player) object).getPlayerState().isIntangible()) {
				direction = direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT ;
			}
		}
		
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void control() {
		int velocityX = KoopaSingleton.getInstance().getVelocityX(isHidden) ;
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else if (direction == Direction.RIGHT) {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		TextureRegion frame = KoopaSingleton.getInstance().getCurrentFrame(direction, isHidden);
		int width=KoopaSingleton.getInstance().getRunWidth(), height=KoopaSingleton.getInstance().getRunHeight() ;
		batch.draw(frame, hitBox.x, hitBox.y, width, height) ;
	}
	
	public boolean isHidden() {
		return isHidden ;
	}
	
	@Override
	public void remove() {
		// Passa a ficar escondido
		isHidden = true ;
		updateHitBox() ;
		
		// Intercala as direcoes
		direction = direction == Direction.STOP ? (lastDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT) : Direction.STOP ;
		if (direction != Direction.STOP) lastDirection = direction ;
		
		super.remove() ;
	}
}
