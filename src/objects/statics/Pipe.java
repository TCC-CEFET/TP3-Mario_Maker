package objects.statics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import handlers.SoundHandler;
import objects.GameObject;
import objects.MovableObject;
import objects.movables.Player;
import singletons.DisplaySingleton;
import singletons.PipeSingleton;

// Classe para os dois canos conectados
public class Pipe extends GameObject {
	private Rectangle hitBoxDestination ; // hitBox do segundo cano
	
	// Variaveis de marcacao de tempo
	private float lastUsedTime ;
	private final float TimeBetweenUses ;
	
	public Pipe(int x0, int y0, int x1, int y1) {
		super(x0, y0, PipeSingleton.getInstance().getWidth(), PipeSingleton.getInstance().getHeight()) ;
		
		int width=PipeSingleton.getInstance().getWidth(), height=PipeSingleton.getInstance().getHeight() ;
		hitBoxDestination = new Rectangle(x1, y1, width, height) ;
		
		lastUsedTime = 0 ;
		TimeBetweenUses = 2 ;
	}
	
	@Override
	public boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) {
		if(object.getClass() == Player.class) { // Verifica colisao caso seja um player
			if (DisplaySingleton.getInstance().getStateTime() > lastUsedTime + TimeBetweenUses) { // Verifica se ja pode usar
				// Faz o teleporte caso esteja em cima do cano e clicado para baixo
				if(hitBox.overlaps(((Player) object).getBottomHitBox()) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
					object.setPosition(hitBoxDestination.x + hitBoxDestination.width/2 - object.getHitBox().width/2, hitBoxDestination.y + hitBoxDestination.height);
					lastUsedTime = DisplaySingleton.getInstance().getStateTime() ;
					SoundHandler.getInstance().playPipe() ;
				} else if(hitBoxDestination.overlaps(((Player) object).getBottomHitBox()) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
					object.setPosition(hitBox.x + hitBox.width/2 - object.getHitBox().width/2, hitBox.y + hitBox.height);
					lastUsedTime = DisplaySingleton.getInstance().getStateTime() ;
					SoundHandler.getInstance().playPipe() ;
				}
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
		Texture texture = PipeSingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
		batch.draw(texture, hitBoxDestination.x, hitBoxDestination.y, hitBoxDestination.width, hitBoxDestination.height) ;
	}
	
	public Rectangle getHitBoxDestination() {
		return hitBoxDestination ;
	}
	
	@Override
	public void remove() {
		
	}
}
