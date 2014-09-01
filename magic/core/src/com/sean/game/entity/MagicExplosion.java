package com.sean.game.entity;

import java.util.List;

import com.sean.game.LightHolder;

public class MagicExplosion {

	public List<ExplosionParticle> particles;
	public LightHolder lightHolder;
	public boolean alive;
	
	public MagicExplosion(List<ExplosionParticle> particles, LightHolder lightHolder) {
		this.particles = particles;
		this.lightHolder = lightHolder;
		alive = true;
	}
	
	public void update() {
		this.lightHolder.intensity = this.lightHolder.intensity * 0.95f;
		boolean allDead = true;
		for (ExplosionParticle ep : particles) {
			if (ep.alive) {
				allDead = false;
			}
		}
		alive = !allDead;
	}
	
	
}
