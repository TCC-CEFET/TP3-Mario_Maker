package objects.movables;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import handlers.SoundHandler;
import objects.GameObject;
import objects.MovableObject;
import objects.characteristics.Direction;
import objects.movables.enemies.Koopa;
import singletons.EnemySingleton;
import singletons.GoombaSingleton;
import singletons.KoopaSingleton;

// Classe abstrata para os inimigos serem
public abstract class Enemy extends MovableObject {
	public Enemy(int x, int y, int width, int height, Direction direction) {
		super(x, y, width, width, direction) ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getClass() == Koopa.class) { // VErifica se eh um casco de koopa
			if (((Koopa) object).isHidden() && ((Koopa) object).getDirection() != Direction.STOP) {
				if (hitBox.overlaps(object.getHitBox())) {
					remove() ;
					return true ;
				}
			}
		}
		
		if (super.verifyPosition(object, movableList)) return true ;
		
		return false ;
	}
	
	@Override
	public void update() {
		// Atualiza o y do inimigo
		int velocityY = EnemySingleton.getInstance().getVelocityY() ;
		hitBox.y -= velocityY * Gdx.graphics.getDeltaTime() > 3 ? 3 : velocityY * Gdx.graphics.getDeltaTime()  ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void updateHitBox() {
		int width, height ;
		if (this.getClass() == Koopa.class) {
			width=KoopaSingleton.getInstance().getWidth(((Koopa) this).isHidden()) ;
			height=KoopaSingleton.getInstance().getHeight(((Koopa) this).isHidden()) ;
		} else {
			width=GoombaSingleton.getInstance().getWidth() ;
			height=GoombaSingleton.getInstance().getHeight() ;
		}
		
		hitBox.width = width ;
		hitBox.height = height ;
		
		middle.width = width + this.extraMiddleWidthSize ;
		middle.height = height - this.lackMiddleHeightSize ;
	}
	
	public void control() {
		int velocityX = this.getClass() == Koopa.class ? KoopaSingleton.getInstance().getVelocityX(((Koopa) this).isHidden()) : EnemySingleton.getInstance().getVelocityX() ;
		
		if (direction == Direction.LEFT) {
			hitBox.x -= velocityX * Gdx.graphics.getDeltaTime();
		} else if (direction == Direction.RIGHT) {
			hitBox.x += velocityX * Gdx.graphics.getDeltaTime() ;
		}
	}
	
	@Override
	public void remove() {
		SoundHandler.getInstance().playKick() ;
	}
}
