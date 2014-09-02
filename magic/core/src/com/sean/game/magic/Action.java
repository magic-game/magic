package com.sean.game.magic;

import com.sean.game.EntityFactory;

public interface Action {
	public void perform(Event e, Spell s, EntityFactory entityFactory);
}
