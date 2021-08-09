
public abstract class MovableObject extends GameObject {
	protected Direction direction ;
	
	public MovableObject(Direction direction) {
		this.direction = direction ;
	}
	
	public abstract void control() ;
}
