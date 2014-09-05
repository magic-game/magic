package com.sean.game.magic;

import java.util.ArrayList;
import java.util.List;

public class SpellTemplate {

	public List<EventActionStep> steps;
	
	public SpellTemplate() {
		steps = new ArrayList<EventActionStep>();
	}
	
	public SpellTemplate(List<EventActionStep> steps) {
		this.steps = new ArrayList<EventActionStep>(steps);
	}
	
	public void addEventActionStep(EventActionStep eventActionStep) {
		steps.add(eventActionStep);
	}
}
