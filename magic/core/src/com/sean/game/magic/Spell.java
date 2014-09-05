package com.sean.game.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sean.game.entity.Listener;
import com.sean.game.factory.FactoryFacade;

public class Spell implements Listener {
	
	Map<EventType, List<Action>> events;
	FactoryFacade entityFactory;
	
	public Spell(Map<EventType, List<Action>> events, FactoryFacade entityFactory) {
		this.events = events;
		this.entityFactory = entityFactory;
	}

	public void addEventActionPair(EventType eventType, Action action) {
		List<Action> actions = events.get(eventType);
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		actions.add(action);
		events.put(eventType, actions);
	}
	
	public void handleEvent(Event event) {
		List<Action> actions = events.get(event.getType());
		if (actions != null) {
			for (Action action : actions) {
				action.perform(event, this, entityFactory);
			}			
		}
	}
}
