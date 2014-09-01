package com.sean.game.magic;

import com.badlogic.gdx.math.Vector3;
import com.sean.game.MagicGame;
import com.sean.game.entity.Entity;

public class CreateMagicBallAction implements Action {

	public void perform(Event event, Spell spell, MagicGame magicGame) {
		Vector3 position = event.getSource().getPosition();
		final Entity entity = magicGame.createEntity(position);
		entity.addListener(spell);
		spell.handleEvent(getCreateEvent(entity));
		spell.addEventActionPair(new Event("OnCollide", null), new Action() {
			@Override
			public void perform(Event e, Spell s, MagicGame m) {
				entity.getHurt(1);
			}
		});
	}
	
	private Event getCreateEvent(final Entity entity) {
		Event event = new Event("CreateMagicBall", entity);
		return event;
	}
}
