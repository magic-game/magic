package com.sean.game.factory;

import com.sean.game.entity.Entity;
import com.sean.game.magic.Action;
import com.sean.game.magic.Event;
import com.sean.game.magic.EventActionStep;
import com.sean.game.magic.EventType;
import com.sean.game.magic.Spell;
import com.sean.game.magic.SpellBuilder;
import com.sean.game.magic.SpellTemplate;

public class SpellFactory {

	private FactoryFacade entityFactory;
	
	public SpellFactory(FactoryFacade entityFactory) {
		this.entityFactory = entityFactory;
	}
	
	public Spell createSpell(Entity entity, SpellTemplate spellTemplate) {
		SpellBuilder spellBuilder = new SpellBuilder(entityFactory);
		for (EventActionStep step : spellTemplate.steps) {
			Action action = step.actionType.getAction(step.params);
			spellBuilder.addEventActionPair(step.eventType, action);
		}
		Spell spell = spellBuilder.build();
		spell.handleEvent(new Event(EventType.INIT, entity));
		return spell;
	}
}
