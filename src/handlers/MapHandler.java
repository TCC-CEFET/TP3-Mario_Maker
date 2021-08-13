package handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.enemies.* ;
import objects.statics.* ;
import singletons.* ;

// Classe que gerencia o arquivo de texto do mapa
public class MapHandler {
	// Funcao para carregar os objetos a partir do arquivo de texto
	public void loadMap(ArrayList<GameObject> objectsList, ArrayList<MovableObject> movableList) {
		try {
			// Trabalha com os arquivos
			File map = new File("assets\\maps\\map.txt") ;
			InputStream in = new FileInputStream(map);
			Scanner scan = new Scanner(in) ;
			
			// Faz a leituro atew acabar
			while (scan.hasNext()) {
				String type = scan.next() ; // Le a primeira palavra chave
				
				if (type.equals("//")) {
					String comment = scan.nextLine() ;
				} else if (type.equals("Floor")) {
					String subType = scan.next() ; // Le a segunda
					int x0=scan.nextInt(), x1=scan.nextInt(), y=scan.nextInt() ; // Le os dados
					floorGenerator(subType, objectsList, x0, x1, y) ;
				} else if (type.equals("Wall")) {
					String subType = scan.next() ;
					int x=scan.nextInt(), y0=scan.nextInt(), y1=scan.nextInt();
					wallGenerator(subType, objectsList, x, y0, y1) ;
				} else if (type.equals("Stairs")) {
					String subType = scan.next() ;
					int x0=scan.nextInt(), x1=scan.nextInt(), y=scan.nextInt();
					char d=scan.next().charAt(0) ;
					stairsGenerator(subType, objectsList, x0, x1, y, d) ;
				} else if (type.equals("Brick")) {
				 	objectsList.add(new Brick(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Goomba")) {
					movableList.add(new Goomba(scan.nextInt(), scan.nextInt(), Direction.fromChar(scan.next().charAt(0))));
				} else if (type.equals("Koopa")) {
					movableList.add(new Koopa(scan.nextInt(), scan.nextInt(), Direction.fromChar(scan.next().charAt(0))));
				} else if (type.equals("Ground")) {
					objectsList.add(new Ground(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Coin")) {
					objectsList.add(new Coin(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Mushroom")) {
					movableList.add(new Mushroom(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("LuckyBlock")) {
					objectsList.add(new LuckyBlock(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Pipe")) {
					objectsList.add(new Pipe(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt())) ;
				}
			}
			
			scan.close() ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Gerador de chao
	private void floorGenerator(String subType, ArrayList<GameObject> objectsList, int x0, int x1, int y) {
		if (subType.equals("Ground")) {
			for(int i=0; i < (x1-x0)/(GroundSingleton.getInstance().getWidth()); i++) {
				objectsList.add(new Ground(x0+(GroundSingleton.getInstance().getWidth()*i), y));
			}
		} else if (subType.equals("Brick")) {
			for(int i=0; i < (x1-x0)/BrickSingleton.getInstance().getWidth(); i++) {
				objectsList.add(new Brick(x0+(BrickSingleton.getInstance().getWidth()*i), y));
			}
		}
	}
	
	// Gerador de parede
	private void wallGenerator(String subType, ArrayList<GameObject> objectsList, int x, int y0, int y1) {
		if (subType.equals("Ground")) {
			for(int i=0; i < (y1-y0)/(GroundSingleton.getInstance().getHeightFull()); i++) {
				objectsList.add(new Ground(x, y0+(GroundSingleton.getInstance().getHeightFull()*i)));
			}
		} else if (subType.equals("Brick")) {
			for(int i=0; i < (y1-y0)/BrickSingleton.getInstance().getHeight(); i++) {
				objectsList.add(new Brick(x, y0+(BrickSingleton.getInstance().getHeight()*i)));
			}
		}
	}
	
	// Gerador de escada
	private void stairsGenerator(String subType, ArrayList<GameObject> objectsList, int x0, int x1, int y, char d) {
		if (subType.equals("Ground")) {
			for(int i=0; i < (x1-x0)/(GroundSingleton.getInstance().getWidth()); i++) {
				floorGenerator(subType, objectsList, d == 'U' ? x0+(i*GroundSingleton.getInstance().getWidth()) : x0, d == 'D' ? x1-(i*GroundSingleton.getInstance().getWidth()) : x1, y+(i*GroundSingleton.getInstance().getHeightFull())) ;
			}
		} else if (subType.equals("Brick")) {
			for(int i=0; i < (x1-x0)/BrickSingleton.getInstance().getWidth(); i++) {
				floorGenerator(subType, objectsList, d == 'U' ? x0+(i*GroundSingleton.getInstance().getWidth()) : x0, d == 'D' ? x1-(i*GroundSingleton.getInstance().getWidth()) : x1, y+(i*BrickSingleton.getInstance().getHeight())) ;
			}
		}
	}
}
