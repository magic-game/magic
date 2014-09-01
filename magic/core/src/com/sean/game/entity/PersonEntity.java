package com.sean.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;
import com.sean.game.magic.Event;

public class PersonEntity implements Entity {

	public Body body;
	public Decal decal;
	public EntityState state;
	public int health;
	public LightHolder lightHolder;
	public float heightWave;
	public List<Listener> listeners;
	
	public PersonEntity(Body body, Decal decal, LightHolder lightHolder, int health) {
		this.state = EntityState.ALIVE;
		this.body = body;
		this.decal = decal;
		this.health = health;
		this.lightHolder = lightHolder;
		heightWave = 0;
		listeners = new ArrayList<Listener>();
	}
	
	@Override
	public void update() {
		if (health <= 0) {
			state = EntityState.DEAD;
			lightHolder.intensity = 0;
		}
		decal.setPosition(getPosition());
		if (lightHolder != null) {
			lightHolder.pos = getPosition();
			lightHolder.intensity = ((float)Math.sin((double)heightWave) * 1.0f) + 1.5f;
		}
		heightWave = heightWave + Gdx.graphics.getDeltaTime();
		
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
		float height = ((float)Math.sin((double)heightWave) / 10f);
		return new Vector3(body.getWorldCenter().x, height, body.getWorldCenter().y);
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
	public Vector3 getLastPosition() {
		return getPosition();
	}

	@Override
	public void getHurt(int damage) {
		health = health - damage;
	}

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void notify(Event event) {
		for (Listener listener : listeners) {
			listener.handleEvent(event);
		}
	}

}
