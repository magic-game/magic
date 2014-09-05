package com.sean.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sean.game.entity.Entity;
import com.sean.game.magic.Event;
import com.sean.game.magic.EventType;

public class CollisionContactListener implements ContactListener {
	
	public CollisionContactListener() {
		super();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
		
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		Object objA = bodyA.getUserData();
		Object objB = bodyB.getUserData();
		Entity entityA = objA instanceof Entity ? ((Entity)objA) : null;
		Entity entityB = objB instanceof Entity ? ((Entity)objB) : null;
		handle(entityA, entityB);
		handle(entityB, entityA);
	}

	private void handle(Entity objA, Entity objB) {
		if (objA != null) {
			objA.notify(new Event(EventType.COLLISION, objB));
		}
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
