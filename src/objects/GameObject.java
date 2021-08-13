package objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// Classe para os objetos do jogo
public abstract class GameObject {
	protected Rectangle hitBox ; // hitBox do objeto
	
	public GameObject(int x, int y, int width, int height) {
		hitBox = new Rectangle(x, y, width, height) ;
	}
	
	// Verifica a posicao e executa determinadas funcoes
	public abstract boolean verifyPosition(GameObject object, ArrayList<MovableObject> movableList) ;
	
	// Atualiza o que precisar
	public abstract void update() ;
	
	// Seta a posicao
	public void setPosition(Float x, Float y) {
		if (x == null && y == null) {
			x = hitBox.x ;
			y = hitBox.y ;
		} else if (x == null) {
			x = hitBox.x ;
		} else if (y == null) {
			y = hitBox.y ;
		}
		
		hitBox.x = x ;
		hitBox.y = y ;
	}
	
	// Desenha em tela
	public abstract void draw(SpriteBatch batch) ;
	
	public Rectangle getHitBox() {
		return hitBox ;
	}
	
	// Funcao para ser chamada quando remover
	public abstract void remove() ;
	
	// Funcao para categorizar o superClass como gameObject
	public Class<?> getSuperClass() {
		return GameObject.class ;
	}
}
