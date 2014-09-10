package com.sean.game.magic.actions;


import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.Action;
import com.sean.game.magic.Event;
import com.sean.game.magic.Param;
import com.sean.game.magic.Spell;

public class ImpulseEntityAction implements Action {

	public float amount;
	
	public ImpulseEntityAction(List<Param> params) {
		for (Param param : params) {
			if (param.name.equals("speed")) {
				this.amount = Float.valueOf(param.value);
			}
		}
	}
	
	@Override
	public void perform(Event e, Spell s, FactoryFacade entityFactory) {
		Body body = e.getSource().getBody();
		moveBody(body, amount);
	}
	
	private void moveBody(Body body, float amount) {
		Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
		body.applyLinearImpulse(direction.scl(amount), body.getPosition(), true);
	}
}
