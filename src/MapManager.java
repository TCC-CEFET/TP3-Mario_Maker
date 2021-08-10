import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import objects.* ;
import objects.characteristics.* ;
import objects.collectables.* ;
import objects.movables.* ;
import objects.statics.* ;
import singletons.* ;

public class MapManager {
	
	public MapManager() {
		
	}
	
	public void loadMap(ArrayList<GameObject> objectsList, ArrayList<MovableObject> movableList) {
		try {
			File map = new File("assets\\maps\\map.txt") ;
			InputStream in = new FileInputStream(map);
			Scanner scan = new Scanner(in) ;
			
			while (scan.hasNext()) {
				String type = scan.next() ;
				
				if (type.equals("//")) {
					String comment = scan.nextLine() ;
				} else if (type.equals("Floor")) {
					String subType = scan.next() ;
					int x0=scan.nextInt(), x1=scan.nextInt(), y=scan.nextInt() ;
					floorGenerator(subType, objectsList, x0, x1, y) ;
				} else if (type.equals("Wall")) {
					String subType = scan.next() ;
					int x=scan.nextInt(), y0=scan.nextInt(), y1=scan.nextInt();
					wallGenerator(subType, objectsList, x, y0, y1) ;
				} else if (type.equals("Stairs")) {
					
				} else if (type.equals("Brick")) {
				 	objectsList.add(new Brick(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Enemy")) {
					movableList.add(new Enemy(scan.nextInt(), scan.nextInt(), Direction.fromChar(scan.next().charAt(0))));
				} else if (type.equals("Ground")) {
					objectsList.add(new Ground(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Coin")) {
					objectsList.add(new Coin(scan.nextInt(), scan.nextInt()));
				} else if (type.equals("Mushroom")) {
					movableList.add(new Mushroom(scan.nextInt(), scan.nextInt()));
				}
			}
			
			scan.close() ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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
}
