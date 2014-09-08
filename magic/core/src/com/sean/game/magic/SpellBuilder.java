package com.sean.game.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sean.game.factory.FactoryFacade;

public class SpellBuilder {

	private FactoryFacade entityFactory;
	private Map<EventType, List<Action>> events;
	
	public SpellBuilder(FactoryFacade entityFactory) {
		this.events = new HashMap<EventType, List<Action>>();
		this.entityFactory = entityFactory;
	}
	
	public SpellBuilder addEventActionPair(EventType eventType, Action action) {
		List<Action> actions = events.get(eventType);
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		if (action != null) {
			actions.add(action);
		}
		events.put(eventType, actions);
		return this;
	}
	
	public Spell build() {
		Spell spell = new Spell(events, entityFactory);
		return spell;
	}
}
