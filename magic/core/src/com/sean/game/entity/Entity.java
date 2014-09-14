package com.sean.game.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;
import com.sean.game.magic.Event;

public interface Entity {
	public void update(Player player);
	public EntityState getState();
	public Body getBody();
	public Vector3 getPosition();
	public LightHolder getLightHolder();
	public Decal getDecal();
	public Vector3 getLastPosition();
	public void getHurt(int damage);
	public void addEnergy(int energy);
	public int getEnergy();
	public void addListener(Listener listener);
	public void notify(Event event);
	public Vector3 getDirection();
}
