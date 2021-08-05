import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Floor extends GameObject {
	private int width=32, height=32 ;
	private Rectangle hitBox;
	private Sprite sprite;
	private Texture texture;
	
	public Floor(int x, int y) {
		hitBox = new Rectangle(x, y, width, height) ;
		texture = new Texture(Gdx.files.internal("assets/sprites/floor.png")) ;
		
		sprite = new Sprite(texture, 0, 0, width, height) ;
		setPosition(x, y) ;
	}
	
	@Override
	public void hits(GameObject object) {
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
		sprite.setPosition(x, y) ;
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch) ;
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
		// TODO Auto-generated method stub
		return 1 ;
	}

}