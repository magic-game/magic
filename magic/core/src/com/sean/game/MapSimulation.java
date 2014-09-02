package com.sean.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.sean.game.entity.Entity;
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
		List<Entity> ents = map.getEntities();
		for (Entity entity : ents) {
			decals.add(entity.getDecal());
		}
		entities.addAll(ents);
		lightHolders.addAll(map.getLightHolders());
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
