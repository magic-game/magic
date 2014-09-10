package com.sean.game.magic.actions;

import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.sean.game.entity.Entity;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.Action;
import com.sean.game.magic.ActionType;
import com.sean.game.magic.Event;
import com.sean.game.magic.EventType;
import com.sean.game.magic.Param;
import com.sean.game.magic.ParamManager;
import com.sean.game.magic.Spell;

public class CreateMagicBallAction implements Action {

	List<Param> params;
	
	public CreateMagicBallAction(List<Param> updatedParams) {
		this.params = ActionType.CREATE_BALL.getParams();
		for (Param updatedParam : updatedParams) {
			for (Param param : params) {
				if (param.name.equals(updatedParam.name)) {
					param.value = updatedParam.value;
				}
			}
		}
	}
	
	public void perform(Event event, Spell spell, FactoryFacade entityFactory) {
		Entity source = event.getSource();
		Vector3 pos = source.getPosition().add(source.getDirection().cpy().scl(source.getBody().getFixtureList().get(0).getShape().getRadius()));
		float ttl = ParamManager.getFloatParam("TTL", params);
		float size = ParamManager.getFloatParam("size", params);
		final Entity entity = entityFactory.createEntity(pos, source.getDirection(), ttl, size);
		entity.addListener(spell);
		spell.handleEvent(getCreateEvent(entity));
		spell.addEventActionPair(EventType.COLLISION, new Action() {
			@Override
			public void perform(Event e, Spell s, FactoryFacade entityFactory) {
				entity.getHurt(1);
			}
		});
	}
	
	private Event getCreateEvent(final Entity entity) {
		Event event = new Event(EventType.CREATE, entity);
		return event;
	}
}
