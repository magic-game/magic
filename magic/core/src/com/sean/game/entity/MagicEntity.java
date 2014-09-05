package com.sean.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;

public class MagicEntity extends BasicEntity {

	public float ttl;
	
	public MagicEntity(Decal decal, LightHolder lightHolder, float ttl, Body body) {
		super(decal, lightHolder, body);
		this.ttl = ttl;
	}
	
	@Override
	public void update() {
		ttl = ttl - Gdx.graphics.getDeltaTime();
		if (ttl < 0) {
			state = EntityState.DEAD;
		}
		lightHolder.pos = getPosition();
		decal.setPosition(getPosition());
		lastPosOther = lastPos;
		lastPos = getPosition();
	}
}
