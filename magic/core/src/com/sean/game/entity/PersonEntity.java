package com.sean.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;

public class PersonEntity extends BasicEntity {

	public int health;
	public float heightWave;
	
	public PersonEntity(Body body, Decal decal, LightHolder lightHolder, int health) {
		super(decal, lightHolder, body);
		this.health = health;
		heightWave = 0;
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
	public Vector3 getPosition() {
		float height = ((float)Math.sin((double)heightWave) / 10f);
		return new Vector3(body.getWorldCenter().x, height, body.getWorldCenter().y);
	}

	@Override
	public void getHurt(int damage) {
		health = health - damage;
	}
}
