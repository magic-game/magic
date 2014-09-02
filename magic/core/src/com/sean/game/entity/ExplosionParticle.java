package com.sean.game.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;

public class ExplosionParticle {

	public Vector3 position;
	public Decal decal;
	public Vector3 velocity;
	public float alpha;
	public Vector3 color;
	public boolean alive;
	
	
	public ExplosionParticle(Vector3 position, Decal decal, Vector3 velocity, Vector3 color, float alpha) {
		this.position = position;
		this.decal = decal;
		this.velocity = velocity;
		this.alpha = alpha;
		this.color = color;
		this.alive = true;
	}

	public void update() {
		velocity = velocity.scl(0.95f);
		alpha = alpha * 0.95f;
		position.add(velocity);
		decal.setPosition(position);
		decal.setColor(color.x, color.y, color.z, alpha);
		if (alpha < 0.01f) {
			this.alive = false;
		}
	}
}
