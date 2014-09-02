package com.sean.game.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sean.game.EntityFactory;
import com.sean.game.entity.Listener;

public class Spell implements Listener {
	
	Map<Event, List<Action>> events;
	EntityFactory entityFactory;
	
	public Spell(Map<Event, List<Action>> events, EntityFactory entityFactory) {
		this.events = events;
		this.entityFactory = entityFactory;
	}

	public void addEventActionPair(Event event, Action action) {
		List<Action> actions = events.get(event);
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		actions.add(action);
		events.put(event, actions);
	}
	
	public void handleEvent(Event event) {
		List<Action> actions = events.get(event);
		if (actions != null) {
			for (Action action : actions) {
				action.perform(event, this, entityFactory);
			}			
		}
	}
}
