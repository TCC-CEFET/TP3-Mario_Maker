import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {
	private int width=44, height=44 ;
	private Rectangle bottom, left, right, top, full ;
	private Sprite sprite ;
	private Texture texture ;
	private int action ;
	private float velocityY ;
	private boolean pulando ;
	
	public Player(float x, float y) {
		full = new Rectangle(x, y, width, height);
		bottom = new Rectangle(x+(width/20), y, width-(width/10), height/8) ;
		left = new Rectangle(x, y+height/8, width/2, height-(bottom.height*2)) ;
		right = new Rectangle(x+width/2, y+bottom.height, width/2, height-(bottom.height*2)) ;
		top = new Rectangle(x+(width/20), y+(height-(height/8)), width-(width/10), height/8) ;
		
		int imageWidth=2048, imageHeight=2048 ;
		texture = new Texture(Gdx.files.internal("assets/sprites/mario.png"));
		
		sprite = new Sprite(texture, 0, 0, imageWidth, imageHeight);
		this.setPosition(x, y);
		velocityY = 0 ;
		pulando = false ;
	}
	
	@Override
	public void hits(Rectangle rectangle) {
		if (top.overlaps(rectangle)) {
			action(null, rectangle.y - full.height);
		}
		
		if (bottom.overlaps(rectangle)) {
			action(null, rectangle.y + rectangle.height);
			pulando = false ;
		}
		
		if (right.overlaps(rectangle)) {
			action(rectangle.x - full.width - 1, null);
		}
		
		if (left.overlaps(rectangle)) {
			action(rectangle.x + rectangle.width + 1, null);
		}
	}
	
	@Override
	public void action(Float x, Float y) {
		velocityY = 0 ;
		if(x == null) {
			setPosition(full.x, y) ;
		} else if (y == null) {
			setPosition(x, full.y) ;
		} else {
			setPosition(x, y) ;
		}
	}
	
	@Override
	public void update() {
		velocityY -= 10 * Gdx.graphics.getDeltaTime() ;
		full.y += velocityY ;
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void setPosition(float x, float y) {
		full.x = x ;
		full.y = y ;
		
		bottom.x = x+(width/20) ;
		bottom.y = y ;
		
		left.x = x ;
		left.y = y + bottom.height ;
		
		right.x = x + (width/2) ;
		right.y = y + bottom.height ;

		top.x = x+(width/20) ;
		top.y = y + (height-(height/8)) ;
		
		sprite.setPosition(x, y);
	}
	
	public void control() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			jump();
		}
	}
	
	@Override
	public void moveLeft() {
		full.x -= 100 * Gdx.graphics.getDeltaTime();
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void moveRight() {
		full.x += 100 * Gdx.graphics.getDeltaTime() ;
		this.setPosition(full.x, full.y) ;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), width, height);
	}
	
	@Override
	public void jump() {
		if (pulando) return ;
		
		velocityY = 7 ;
		pulando = true ;
	}

	@Override
	public Rectangle getHitBox() {
		return full ;
	}
}
	
