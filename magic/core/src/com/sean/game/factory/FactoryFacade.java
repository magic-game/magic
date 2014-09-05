package com.sean.game.factory;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.MapSimulation;
import com.sean.game.entity.Entity;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.entity.PlayerEntity;
import com.sean.game.magic.SpellTemplate;

public class FactoryFacade {

	private MapSimulation simulation;
	private SpellFactory spellFactory;
	private ExplosionFactory explosionFactory;
	private EntityFactory entityFactory;
	
	public FactoryFacade(MapSimulation simulation, World world) {
		this.simulation = simulation;
		this.spellFactory = new SpellFactory(this);
		this.explosionFactory = new ExplosionFactory();
		this.entityFactory = new EntityFactory(world);
	}
	
	public void createSpell(Entity entity, SpellTemplate spellTemplate) {
		simulation.spells.add(spellFactory.createSpell(entity, spellTemplate));
	}
	
	public void createExplosion(Entity entity, Vector3 color) {
		MagicExplosion me = explosionFactory.createExplosion(entity, color);
		simulation.lightHolders.add(me.lightHolder);
		simulation.explosions.add(me);
	}
	
	public Entity createEntity(Vector3 position, Vector3 direction, float ttl) {
		Entity entity = entityFactory.createEntity(position, direction, ttl);
		simulation.entities.add(entity);
		simulation.decals.add(entity.getDecal());
		simulation.lightHolders.add(entity.getLightHolder());
		return entity;
	}
	
	public PlayerEntity createPlayerEntity(Vector3 position, Vector3 direction) {
		PlayerEntity entity = entityFactory.createPlayerEntity(position, direction);
//		simulation.entities.add(entity);
		return entity;
	}
}
