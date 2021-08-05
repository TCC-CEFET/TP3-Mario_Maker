import java.util.ArrayList;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MapManager {
	
	public MapManager() {
		
	}
	
	public void loadMap(ArrayList<GameObject> objectsList) {
		FileHandle file = Gdx.files.internal("assets/maps/map1.txt");
		StringTokenizer tokens = new StringTokenizer(file.readString());
		while(tokens.hasMoreTokens()) {
			String type = tokens.nextToken();
			if(type.equals("Floor")){
				int x0=Integer.parseInt(tokens.nextToken()), x1=Integer.parseInt(tokens.nextToken()), y=Integer.parseInt(tokens.nextToken());
				for(int i = 0; i < (x1-x0)/Singleton.getInstance().getBrickWidth(); i++) {
					objectsList.add(new Floor(x0+(Singleton.getInstance().getBrickWidth()*i), y));
				}
			} else if (type.equals("Brick")) {
			 	objectsList.add(new Brick(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));	
			} else if (type.equals("Enemy")) {
				objectsList.add(new Enemy(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
			}
		}
	}
}
