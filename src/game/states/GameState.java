package game.states;

// Classe para manusear o enum do estado do jogo
public class GameState {
	private EnumGameState state ; // Estado do jogo

	public EnumGameState getState() {
		return state;
	}

	public void setState(EnumGameState state) {
		this.state = state;
	}
}
