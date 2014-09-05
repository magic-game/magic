package com.sean.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.sean.game.entity.Entity;
import com.sean.game.entity.EntityState;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.level.BasicMap;
import com.sean.game.magic.Spell;

public class MapSimulation {

	public List<MagicExplosion> explosions;
	public List<Spell> spells;
	public List<Decal> decals;
	public List<Entity> entities;
	public List<LightHolder> lightHolders;
	public List<ModelInstance> instances;
	
	public MapSimulation(BasicMap map) {
		explosions = new ArrayList<MagicExplosion>();
		spells = new ArrayList<Spell>();
		decals = new ArrayList<Decal>();
		entities = new ArrayList<Entity>();
		lightHolders = new ArrayList<LightHolder>();
		instances = map.getModelInstances();
		List<Entity> mapEntities = map.getEntities();
		for (Entity entity : mapEntities) {
			decals.add(entity.getDecal());
		}
		entities.addAll(mapEntities);
		lightHolders.addAll(map.getLightHolders());
	}
	
	public void update() {
		updateEntities();
	}
	
	private void updateEntities() {
		for (Entity entity : entities) {
			if (entity.getState() == EntityState.ALIVE) {
				entity.update();
			} else {
				lightHolders.remove(entity.getLightHolder());
				decals.remove(entity.getDecal());
			}
		}
		for (MagicExplosion me : explosions) {
			for (ExplosionParticle ep : me.particles) {
				ep.update();
			}
			me.update();
		}
		Iterator<MagicExplosion> mei = explosions.iterator();
		while (mei.hasNext()) {
			MagicExplosion me = mei.next();
			if (!me.alive) {
				lightHolders.remove(me.lightHolder);
				mei.remove();
			}
		}
	}
	
	public void dispose() {
		explosions.clear();
		spells.clear();
		decals.clear();
		entities.clear();
		lightHolders.clear();
		instances.clear();
	}
	
}
