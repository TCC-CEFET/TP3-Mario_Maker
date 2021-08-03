import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {
	private Rectangle bottom, left, right, top;
	private Sprite sprite;
	private Texture texture;
	private int action;
	private float velocityY;
	
	public Player() {
		bottom = new Rectangle(0, 0, 128, 128);
		
		texture = new Texture(Gdx.files.internal("assets/sprites/mario.png"));
		
		sprite = new Sprite(texture, 0, 0, 2048, 2048);
		this.setPosition(0, 0);
		velocityY = 0;
		
	}
	
	@Override
	public int hits(Rectangle rectangle) {
		if(bottom.overlaps(rectangle)) {
			return 1;
		}
		return -1;
	}
	
	@Override
	public void action(int type, float x, float y) {
		if(type == 1) {
			velocityY = 0 ;
			setPosition(bottom.x, y);
		}
	}
	
	@Override
	public void update() {
		velocityY -= 30 * Gdx.graphics.getDeltaTime() ;
		bottom.y += velocityY;
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		bottom.x = x;
		bottom.y = y;
		sprite.setPosition(x, y);
	}
	
	@Override
	public void moveLeft() {
		bottom.x -= 100 * Gdx.graphics.getDeltaTime();
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	@Override
	public void moveRight() {
		bottom.x += 100 * Gdx.graphics.getDeltaTime() ;
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), 44, 44);
	}
	
	@Override
	public void jump() {
		velocityY = 20;
	}

	@Override
	public Rectangle getHitBox() {
		return bottom ;
	}
}
	
