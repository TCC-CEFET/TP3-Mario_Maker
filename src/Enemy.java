import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import singletons.EnemySingleton;
import singletons.GroundSingleton;
import singletons.PlayerSingleton;

public class Enemy extends GameObject implements Movel {
	private Rectangle hitBox;
	
	
	public Enemy(int x, int y) {
		int width=EnemySingleton.getInstance().getWidth(), height=EnemySingleton.getInstance().getHeight() ;
		hitBox = new Rectangle(x, y, width, height) ;
		
		this.setPosition(x, y);
	}
	
	@Override
	public void hits(GameObject object) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void action(Float x, Float y) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setPosition(float x, float y) {
		hitBox.x = x ;
		hitBox.y = y ;
	}
	
	@Override
	public void control() {
		moveLeft() ;
	}
	
	@Override
	public void moveLeft() {
		hitBox.x -= 50 * Gdx.graphics.getDeltaTime();
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void moveRight() {
		hitBox.x += 100 * Gdx.graphics.getDeltaTime() ;
		this.setPosition(hitBox.x, hitBox.y) ;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		Texture texture = EnemySingleton.getInstance().getTexture();
		batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height) ;
	}
	
	@Override
	public void jump() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Rectangle getHitBox() {
		return hitBox ;
	}
	
	@Override
	public int hitAction(int side) {
		if(side == 1) {
			return 2;
		}
		return 1 ;
	}
}
