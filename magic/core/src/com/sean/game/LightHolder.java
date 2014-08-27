package com.sean.game;

import com.badlogic.gdx.math.Vector3;

public class LightHolder {

	public Vector3 color;
	public Vector3 pos;
	public float intensity;
	public float distance;
	
	public LightHolder (Vector3 color, Vector3 pos, float intensity) {
		this.color = color;
		this.pos = pos;
		this.intensity = intensity;
		this.distance = 0f;
	}
}
