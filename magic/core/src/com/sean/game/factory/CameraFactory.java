package com.sean.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class CameraFactory {

	private static final int FOV = 67;
	
	public static PerspectiveCamera createCamera(Vector3 pos) {
		PerspectiveCamera camera = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(pos);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();
		return camera;
	}
}
