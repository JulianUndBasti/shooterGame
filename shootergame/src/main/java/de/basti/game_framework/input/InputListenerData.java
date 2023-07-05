package de.basti.game_framework.input;

import javafx.scene.Scene;

public class InputListenerData {
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

	
	public void pressesAndReleasesConsumed() {
		this.keyData.pressesAndReleasesConsumed();
		this.mouseData.pressesAndReleasesConsumed();
	}
	
	
}
