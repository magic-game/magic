package com.sean.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.entity.Entity;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicEntity;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.magic.Action;
import com.sean.game.magic.CreateMagicBallAction;
import com.sean.game.magic.Event;
import com.sean.game.magic.HurtPersonAction;
import com.sean.game.magic.ImpulseEntityAction;
import com.sean.game.magic.Spell;
import com.sean.game.magic.SpellBuilder;

public class EntityFactory {

	private MapSimulation simulation;
	private PerspectiveCamera camera;
	private World world;
	
	public EntityFactory(MapSimulation simulation, PerspectiveCamera camera, World world) {
		this.simulation = simulation;
		this.camera = camera;
		this.world = world;
	}
	
	public Spell createSpell(Player player) {
		Event initEvent = new Event("Init", player);
		Action action = new CreateMagicBallAction();
		Event onCreateEvent = new Event("CreateMagicBall", null);
		Action impulseAction = new ImpulseEntityAction();
		Event collideEvent = new Event("OnCollide", null);
		Action hurtPersonAction = new HurtPersonAction(1);
		Spell spell = new SpellBuilder()
							.init(this)
							.addEventActionPair(initEvent, action)
							.addEventActionPair(onCreateEvent, impulseAction)
							.addEventActionPair(collideEvent, hurtPersonAction)
							.build();
		spell.handleEvent(initEvent);
		simulation.spells.add(spell);
		return spell;
	}
	
	public void createExplosion(Entity entity, Vector3 color) {
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
		MagicExplosion me = new MagicExplosion(eps, lightHolder);
		simulation.lightHolders.add(lightHolder);
		simulation.explosions.add(me);
	}
	
	public Entity createEntity(Vector3 position) {
		position.add(camera.direction.scl(1.0f));
		Vector2 angle = new Vector2(camera.direction.x, camera.direction.z);
		LightHolder lightHolder = new LightHolder(new Vector3(0.5f, 1.0f, 0.7f), position.cpy(), 3.0f);
		Texture image = new Texture(Gdx.files.internal("ball.png"));
		Decal decal = Decal.newDecal(0.19f, 0.19f, new TextureRegion(image), true);
		decal.setPosition(position);
		Body entityBody = BodyFactory.createMagicEntityBody(world, position, angle.angleRad());
		Entity entity = new MagicEntity(world, decal, lightHolder, 1.0f, entityBody);
		entityBody.setUserData(entity);
		simulation.entities.add(entity);
		simulation.decals.add(decal);
		simulation.lightHolders.add(lightHolder);
		return entity;
	}
	
	private Decal getExplosionDecal(Vector3 pos) {
		Texture image = new Texture(Gdx.files.internal("ball.png"));
		float x = ((float) Math.random() % 0.12f) + 0.03f;
		Decal decal = Decal.newDecal(x, x, new TextureRegion(image), true);
		decal.setPosition(pos.cpy());
		return decal;
	}
}
