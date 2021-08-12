package game.states;

public class GameState {
	private EnumGameState state ;

	public EnumGameState getState() {
		return state;
	}

	public void setState(EnumGameState state) {
		this.state = state;
	}
}
