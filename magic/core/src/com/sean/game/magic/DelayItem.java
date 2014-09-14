package com.sean.game.magic;

import com.sean.game.entity.Entity;

public class DelayItem {

	int times;
	float delay;
	float currentDelay;
	public boolean isDone;
	Spell spell;
	Entity entity;
	
	public DelayItem(int times, float delay, Spell spell, Entity entity) {
		this.times = times;
		this.delay = delay;
		currentDelay = delay;
		isDone = false;
		this.spell = spell;
		this.entity = entity;
	}
	
	public void update(float delta) {
		if (!isDone) {
			currentDelay -= delta;
			if (currentDelay < 0) {
				spell.handleEvent(new Event(EventType.DELAY, entity));
				times--;
				if (times <= 0) {
					isDone = true;
				}
				currentDelay = delay;
			}
		}
	}
}
