package com.sean.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Util {

	public static Vector2 toVector2(Vector3 vector3) {
		return new Vector2(vector3.x, vector3.z);
	}
}
