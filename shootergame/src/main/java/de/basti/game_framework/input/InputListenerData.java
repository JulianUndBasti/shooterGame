package de.basti.game_framework.input;

import de.basti.game_framework.controls.Updatable;
import javafx.scene.Scene;

public class InputListenerData implements Updatable{
	private KeyInputListenerData keyData = new KeyInputListenerData();
	private MouseInputListenerData mouseData = new MouseInputListenerData();
	
	
	public InputListenerData(Scene scene) {
		keyData.listenTo(scene);
		mouseData.listenTo(scene);
	}


	public KeyInputListenerData getKeyData() {
		return keyData;
	}

	public MouseInputListenerData getMouseData() {
		return mouseData;
	}

	
	public void update(long deltaMillis) {
		this.keyData.update(deltaMillis);
		this.mouseData.update(deltaMillis);
	}
	
	
}
