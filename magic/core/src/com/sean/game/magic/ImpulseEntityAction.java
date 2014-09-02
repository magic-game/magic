package com.sean.game.magic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.EntityFactory;

public class ImpulseEntityAction implements Action {

	@Override
	public void perform(Event e, Spell s, EntityFactory entityFactory) {
		Body body = e.getSource().getBody();
		moveBody(body, 0.4f);
	}
	
	private void moveBody(Body body, float amount) {
		Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
		body.applyLinearImpulse(direction.scl(amount), body.getPosition(), true);
	}
	
}
