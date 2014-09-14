package com.sean.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.entity.AiState;
import com.sean.game.entity.Entity;
import com.sean.game.entity.EntityState;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.entity.PersonEntity;
import com.sean.game.entity.Player;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.level.BasicMap;
import com.sean.game.level.Tile;
import com.sean.game.magic.DelayItem;
import com.sean.game.magic.Spell;

public class MapSimulation {

	public List<MagicExplosion> explosions;
	public List<Spell> spells;
	public List<Decal> decals;
	public List<Entity> entities;
	public List<LightHolder> lightHolders;
	public List<ModelInstance> instances;
	public List<DelayItem> delayItems;
	
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
		delayItems = new ArrayList<DelayItem>();
	}
	
	public void update(World world, Player player, FactoryFacade entityFactory) {
		updateEntities(player, entityFactory);
		checkForLineOfSight(world, player);
	}
	
	public Vector2 conv(Vector3 vec3) {
		return new Vector2(vec3.x, vec3.z);
	}
	
	private void checkForLineOfSight(World world, Player player) {
		Vector2 playerPosition = conv(player.entity.getPosition());
		for (Entity entity : entities) {
			if (entity instanceof PersonEntity) {
				final PersonEntity person = (PersonEntity)entity;
				person.aiState = AiState.ATTACKING;
				world.rayCast(new RayCastCallback() {
					@Override
					public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
						if ( fixture.getBody().getUserData() instanceof Tile ) {
							person.aiState = AiState.IDLE;
							return 0;
						}
						return 1;
					}
				}, conv(person.getPosition()), playerPosition);
			}
		}
	}
	
	private void updateEntities(Player player, FactoryFacade entityFactory) {
		float delta = Gdx.graphics.getDeltaTime();
		List<PersonEntity> castingEntities = new ArrayList<PersonEntity>();
		for (Entity entity : entities) {
			if (entity.getState() == EntityState.ALIVE) {
				entity.update(player);
				if (entity instanceof PersonEntity) {
					if (((PersonEntity) entity).aiState == AiState.CASTING) {
						castingEntities.add((PersonEntity) entity);
					}
				}
			} else {
				lightHolders.remove(entity.getLightHolder());
				decals.remove(entity.getDecal());
			}
		}
		for (PersonEntity person : castingEntities) {
			entityFactory.createSpell(person, person.spell);
		}
		castingEntities.clear();
		for (DelayItem delayItem : delayItems) {
			delayItem.update(delta);
		}
		Iterator<DelayItem> delayIterator = delayItems.iterator();
		while (delayIterator.hasNext()) {
			DelayItem delayItem = delayIterator.next();
			if (delayItem.isDone) {
				delayIterator.remove();
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
		delayItems.clear();
	}
	
}
