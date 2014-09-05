package com.sean.game.magic;

import java.util.List;

public class EventActionStep {
	public final EventType eventType;
	public final ActionType actionType;
	public final List<Param> params;
	
	public EventActionStep(EventType eventType, ActionType actionType, List<Param> params) {
		this.eventType = eventType;
		this.actionType = actionType;
		this.params = params;
	}
}
