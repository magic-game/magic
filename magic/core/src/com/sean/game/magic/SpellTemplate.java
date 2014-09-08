package com.sean.game.magic;

import java.util.ArrayList;
import java.util.List;

public class SpellTemplate {

	public List<EventActionStep> steps;
	public String name;
	public String description;
	
	public SpellTemplate(List<EventActionStep> steps, String name, String description) {
		this.steps = new ArrayList<EventActionStep>(steps);
		this.name = name;
		this.description = description;
	}
}
