package com.sean.game;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sean.game.entity.Entity;
import com.sean.game.entity.EntityState;
import com.sean.game.entity.Listener;
import com.sean.game.magic.Event;

public class Player implements Entity {

	Body body;
	World world;
	EntityFactory entityFactory;
	boolean lockLeft;
	int health;
	
	public Player(Body body, EntityFactory entityFactory) {
		this.body = body;
		this.entityFactory = entityFactory;
		this.health = 10;
	}
	
	public void useLeft(boolean down) {
		if (down && !lockLeft) {
			entityFactory.createSpell(this);			
			lockLeft = true;
		}
		if (!down) {
			lockLeft = false;
		}
		
	}
	
	public void useRight() {
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityState getState() {
		// TODO Auto-generated method stub
		return EntityState.ALIVE;
	}

	@Override
	public Body getBody() {
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public Vector3 getPosition() {
		// TODO Auto-generated method stub
		return new Vector3(body.getWorldCenter().x, 0, body.getWorldCenter().y);
	}

	@Override
	public LightHolder getLightHolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Decal getDecal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3 getLastPosition() {
		// TODO Auto-generated method stub
		return getPosition();
	}

	@Override
	public void getHurt(int damage) {
		health = health - damage;
	}

	@Override
	public void addListener(Listener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(Event event) {
		// TODO Auto-generated method stub
		
	}
}
