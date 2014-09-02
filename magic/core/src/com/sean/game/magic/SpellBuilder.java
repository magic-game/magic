package com.sean.game.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sean.game.EntityFactory;

public class SpellBuilder {

	private EntityFactory entityFactory;
	private Map<Event, List<Action>> events;
	
	public SpellBuilder init(EntityFactory entityFactory) {
		this.events = new HashMap<Event, List<Action>>();
		this.entityFactory = entityFactory;
		return this;
	}
	
	public SpellBuilder addEventActionPair(Event event, Action action) {
		List<Action> actions = events.get(event);
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		actions.add(action);
		events.put(event, actions);
		return this;
	}
	
	public Spell build() {
		Spell spell = new Spell(events, entityFactory);
		return spell;
	}
}
