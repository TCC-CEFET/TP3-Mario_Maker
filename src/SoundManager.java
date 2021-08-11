import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private ArrayList<Integer> ids ;
	
	public SoundManager() {
		ids = new ArrayList<Integer>() ;
	}
	
	public void playBackground() {
		Sound background = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ThemeSong.mp3")) ;
		ids.add((int) background.loop()) ;
	}
	
	public void dispose() {
		for (int id: ids) {
			Gdx.audio.newSound(Gdx.files.internal("assets/sounds/ThemeSong.mp3")).stop(id) ;
		}
	}
}
