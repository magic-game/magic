package com.sean.game.factory;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.LightHolder;
import com.sean.game.MapSimulation;
import com.sean.game.entity.Entity;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.entity.PlayerEntity;
import com.sean.game.magic.DelayItem;
import com.sean.game.magic.Spell;
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
	
	public Entity createEntity(Vector3 position, Vector3 direction, float ttl, float size) {
		Entity entity = entityFactory.createEntity(position, direction, ttl, size);
		simulation.entities.add(entity);
		simulation.decals.add(entity.getDecal());
		LightHolder lightHolder = entity.getLightHolder();
		if (lightHolder != null) {
			simulation.lightHolders.add(lightHolder);
		}
		return entity;
	}
	
	public PlayerEntity createPlayerEntity(Vector3 position, Vector3 direction) {
		PlayerEntity entity = entityFactory.createPlayerEntity(position, direction);
//		simulation.entities.add(entity);
		return entity;
	}
	
	public void createDelay(int times, float delay, Spell spell, Entity entity) {
		simulation.delayItems.add(new DelayItem(times, delay, spell, entity));
	}
}
