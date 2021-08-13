package objects.characteristics;

// Estados de direcao
public enum Direction {
	LEFT, RIGHT, STOP ;
	
	// Funcao da classe Direction que recebe um caracter e retorna a direcao equivalente
	static public Direction fromChar(char charDirection) {
		if (charDirection == 'L' || charDirection == 'l') return LEFT ;
		else return RIGHT ;
	}
}
