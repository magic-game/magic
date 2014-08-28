package com.sean.game.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;

public class PersonEntity implements Entity {

	public Body body;
	public Decal decal;
	public EntityState state;
	public int health;
	public LightHolder lightHolder;
	
	public PersonEntity(Body body, Decal decal, LightHolder lightHolder, int health) {
		this.state = EntityState.ALIVE;
		this.body = body;
		this.decal = decal;
		this.health = health;
		this.lightHolder = lightHolder;
	}
	
	@Override
	public void update() {
		if (health <= 0) {
			state = EntityState.DEAD;
		}
		decal.setPosition(getPosition());
		if (lightHolder != null) {
			lightHolder.pos = getPosition();			
		}
	}

	@Override
	public EntityState getState() {
		return state;
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
	public void handleCollision() {
		health--;
	}

}
