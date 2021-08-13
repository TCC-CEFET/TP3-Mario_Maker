package objects.characteristics;

// Classe que guarda os estado possíveis do player
public class PlayerState {
	private Direction direction ; // Direcao
	private boolean isJumping ; // Pulando
	private boolean isBig ; // Grande
	private boolean isCrouched ; // Agachado
	private boolean isIntangible ; // Intangivel
	
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
