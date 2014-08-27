package com.sean.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.LightHolder;

public class MagicEntity implements Entity {

	public Body body;
	public LightHolder lightHolder;
	public Decal decal;
	public float ttl;
	public EntityState state;
	
	public MagicEntity(World world, Decal decal, LightHolder lightHolder, float ttl, Body body) {
		this.body = body;
		this.ttl = ttl;
		this.state = EntityState.ALIVE;
		this.decal = decal;
		this.lightHolder = lightHolder;
	}
	
	@Override
	public void update() {
		ttl = ttl - Gdx.graphics.getDeltaTime();
		if (ttl < 0) {
			state = EntityState.DEAD;
		}
		lightHolder.pos = getPosition();
		decal.setPosition(getPosition());
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public Vector3 getPosition() {
		return new Vector3(body.getWorldCenter().x, 0, body.getWorldCenter().y);
	}
	
	@Override
	public LightHolder getLightHolder() {
		return lightHolder;
	}
	
	@Override
	public Decal getDecal() {
		return decal;
	}
	
	@Override
	public EntityState getState() {
		return state;
	}
}
