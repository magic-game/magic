package com.sean.game.factory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.sean.game.MagicConstants;

public class BodyFactory {
	
	public Body createPersonBody(World world, Vector3 pos) {
		CircleShape boxShape = new CircleShape();
		boxShape.setRadius(0.4f);
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.position.set(pos.x, pos.z);
		boxBodyDef.angle = MathUtils.PI / 32;
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.fixedRotation = false;
		Body boxBody = world.createBody(boxBodyDef);
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = boxShape;
		boxFixtureDef.friction = 0f;
		boxFixtureDef.restitution = 1.0f;
		boxFixtureDef.density = 10.0f;
		boxBody.createFixture(boxFixtureDef);
		boxShape.dispose();
		
		return boxBody;
	}
	
	public Body createWallBody(World world, Vector3 pos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos.x, pos.z);
		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(MagicConstants.HALF_UNIT, MagicConstants.HALF_UNIT);
		body.createFixture(shape, 0.0f);
		shape.dispose();
		return body;
	}
	
	public static Body createEntityBody(World world, Vector3 pos, float angle, float radius, float density, float restitution) {
		CircleShape boxShape = new CircleShape();
		boxShape.setRadius(radius);
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.position.set(pos.x, pos.z);
		boxBodyDef.angle = MathUtils.PI / 32;
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.fixedRotation = false;
		boxBodyDef.angle = angle;
		Body boxBody = world.createBody(boxBodyDef);
		FixtureDef boxFixtureDef = new FixtureDef();
		boxFixtureDef.shape = boxShape;
		boxFixtureDef.friction = 0f;
		boxFixtureDef.restitution = restitution;
		boxFixtureDef.density = density;
		boxBody.createFixture(boxFixtureDef);
		boxShape.dispose();
		return boxBody;
	}
}
