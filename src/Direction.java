import java.util.Random;

public enum Direction {
	LEFT, RIGHT, STOP ;
	
	static public Direction fromChar(char charDirection) {
		if (charDirection == 'L' || charDirection == 'l') return LEFT ;
		else return RIGHT ;
	}
} ;
