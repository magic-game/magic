package com.sean.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.LightHolder;
import com.sean.game.MagicConstants;
import com.sean.game.entity.Entity;
import com.sean.game.entity.MagicEntity;
import com.sean.game.entity.PlayerEntity;

public class EntityFactory {

	private float PLAYER_SIZE = MagicConstants.PIXEL * 3;
	private Texture IMAGE = new Texture(Gdx.files.internal("ball.png"));
	private Vector3 COLOR = new Vector3(0.5f, 1.0f, 0.7f);
	private float INTENSITY = 3.0f;
	private float COLLISION_BUFFER = 4.0f;
	private float PLAYER_DENSITY = 10.0f;
	
	private World world;
	
	public EntityFactory(World world) {
		this.world = world;
	}
	
	public Entity createEntity(Vector3 position, Vector3 direction, float ttl, float size) {
		float radius = (size / 3.0f) * 2.0f;
		position.add(direction.cpy().scl(radius + (radius / COLLISION_BUFFER)));
		Vector2 angle = new Vector2(direction.x, direction.z);
		LightHolder lightHolder = new LightHolder(COLOR, position.cpy(), INTENSITY);
		Decal decal = Decal.newDecal(size, size, new TextureRegion(IMAGE), true);
		decal.setPosition(position);
		Body entityBody = BodyFactory.createEntityBody(world, position, angle.angleRad(), radius, 1.0f, 1.0f);
		entityBody.setBullet(true);
		Entity entity = new MagicEntity(decal, lightHolder, ttl, entityBody);
		entity.getBody().setUserData(entity);
		return entity;
	}
	
	public PlayerEntity createPlayerEntity(Vector3 position, Vector3 direction) {
		Vector2 angle = new Vector2(direction.x, direction.z);
		LightHolder lightHolder = new LightHolder(COLOR, position.cpy(), INTENSITY);
		Decal decal = Decal.newDecal(PLAYER_SIZE, PLAYER_SIZE, new TextureRegion(IMAGE), true);
		decal.setPosition(position);
		Body entityBody = BodyFactory.createEntityBody(world, position, angle.angleRad(), 0.2f, PLAYER_DENSITY, 0);
		entityBody.setAngularDamping(10f);
		entityBody.setLinearDamping(20f);
		PlayerEntity entity = new PlayerEntity(decal, lightHolder, entityBody);
		entity.getBody().setUserData(entity);
		return entity;
	}
}
