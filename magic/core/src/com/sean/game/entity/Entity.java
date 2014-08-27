package com.sean.game.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;

public interface Entity {
	public void update();
	public EntityState getState();
	public Body getBody();
	public Vector3 getPosition();
	public LightHolder getLightHolder();
	public Decal getDecal();
}
