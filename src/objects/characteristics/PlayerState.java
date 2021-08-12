package objects.characteristics;

import java.util.Random;

public class PlayerState {
	private Direction direction ;
	private boolean isJumping ;
	private boolean isBig ;
	private boolean isCrouched ;
	private boolean isIntangible ;
	
	public PlayerState(Direction direction) {
		this.direction = direction ;
		isJumping = false ;
		isBig = false ;
		isIntangible = false ;
		setCrouched(false) ;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}

	public boolean isCrouched() {
		return isCrouched;
	}

	public void setCrouched(boolean isCrouched) {
		this.isCrouched = isCrouched;
	}

	public boolean isIntangible() {
		return isIntangible;
	}

	public void setIntangible(boolean isInvisible) {
		this.isIntangible = isInvisible;
	}
}
