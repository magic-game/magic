package com.sean.game.factory;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.sean.game.LightHolder;
import com.sean.game.entity.Entity;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicExplosion;

public class ExplosionFactory {

	
	public MagicExplosion createExplosion(Entity entity, Vector3 color) {
		Vector3 position = new Vector3(entity.getLastPosition());
		List<ExplosionParticle> eps = new ArrayList<ExplosionParticle>();
		int particles = (int)(Math.random() * 10) + 20;
		for (int index = 0; index < particles; index++) {
			float x = 0.07f - ((float)Math.random() * 0.14f);
			float y = 0.04f - ((float)Math.random() * 0.08f);
			float z = 0.07f - ((float)Math.random() * 0.14f);
			Vector3 pos = position.cpy();
			Vector3 vel = new Vector3(x, y, z);
			
			ExplosionParticle ep = new ExplosionParticle(pos, getExplosionDecal(pos), vel, color, 1.0f);
			eps.add(ep);
		}
		LightHolder lightHolder = new LightHolder(color, position.cpy(), 12.0f);
		return new MagicExplosion(eps, lightHolder);
	}
	
	private Decal getExplosionDecal(Vector3 pos) {
		Texture image = new Texture(Gdx.files.internal("ball.png"));
		float x = ((float) Math.random() % 0.12f) + 0.03f;
		Decal decal = Decal.newDecal(x, x, new TextureRegion(image), true);
		decal.setPosition(pos.cpy());
		return decal;
	}
}
