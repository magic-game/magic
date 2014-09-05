package com.sean.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;
import com.sean.game.magic.Event;
import com.sean.game.magic.EventType;

public class PlayerEntity extends BasicEntity {

	public int health;
	public int energy;
	float energyRegeneration = 0.0f;
	float healthReductionCoolDown = 0.0f;

	public PlayerEntity(Decal decal, LightHolder lightHolder, Body body) {
		super(decal, lightHolder, body);
		this.health = 20;
		this.energy = 20;
	}

	@Override
	public void update() {
		super.update();
		energyRegeneration += Gdx.graphics.getDeltaTime();
		if (energyRegeneration > 4.0f && energy < 10) {
			energy++;
			energyRegeneration = 0;
		}
		if (healthReductionCoolDown > 0) {
			healthReductionCoolDown = healthReductionCoolDown - Gdx.graphics.getDeltaTime();
		}
	}

	@Override
	public void notify(Event event) {
		if (event.getType() == EventType.COLLISION) {
			if (event.getSource() != null && event.getSource() instanceof PersonEntity) {
				getHurt(1);
			}
		}
	}

	@Override
	public void getHurt(int damage) {
		if (!(healthReductionCoolDown > 0)) {
			health = health - damage;
			healthReductionCoolDown = 0.5f;
		}
	}
}
