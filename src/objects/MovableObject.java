package objects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import objects.characteristics.* ;
import objects.movables.* ;
import objects.statics.* ;

// Classe para os objetos moveis serem
public abstract class MovableObject extends GameObject {
	protected Direction direction ; // Direcao
	protected Rectangle middle ; // hitBox do meio
	
	// Informacoes para hitBox do meio
	protected int extraMiddleWidthSize=6 ;
	protected int lackMiddleHeightSize=32 ;
	
	public MovableObject(int x, int y, int width, int height, Direction direction) {
		super(x, y, width, height) ;
		this.direction = direction ;
		
		middle = new Rectangle(x-extraMiddleWidthSize/2, y+lackMiddleHeightSize/2, width+extraMiddleWidthSize, height-lackMiddleHeightSize) ;
	}
	
	// Funcao controla para mover o objeto
	public abstract void control() ;
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if (object.getSuperClass() == GameObject.class && this.getClass() != Player.class) { // Verifica colisao caso seja apenas um GameObject e não sea da classe player
			if (middle.overlaps(object.getHitBox())) {
				direction = direction == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT ;
			}
			if (hitBox.overlaps(object.getHitBox())) {
				setPosition(null, object.getHitBox().y + object.getHitBox().height) ;
			}
			if (object.getClass() == Pipe.class) { // Verifica colisao caso seja um pipe
				if (middle.overlaps(((Pipe) object).getHitBoxDestination())) {
					direction = direction == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT ;
				}
				if (hitBox.overlaps(((Pipe) object).getHitBoxDestination())) {
					setPosition(null, ((Pipe) object).getHitBoxDestination().y + ((Pipe) object).getHitBoxDestination().height) ;
				}
			}
		}
		
		// Verifica se não caiu da tela
		if (hitBox.y < 0-hitBox.getHeight()-10) {
			return true ;
		}
		
		return false ;
	}
	
	@Override
	public void setPosition(Float x, Float y) {
		super.setPosition(x, y) ;
		
		middle.x = hitBox.x-extraMiddleWidthSize/2 ;
		middle.y = hitBox.y+lackMiddleHeightSize/2 ;
	}
	
	// Funcao para atualizar as hitBoxes para os tamanhos
	public abstract void updateHitBox() ;
	
	// Atualiza a superclasse para ser movel
	@Override
	public Class<?> getSuperClass() {
		return MovableObject.class ;
	}
	
	public Direction getDirection()  {
		return direction ;
	}
}
