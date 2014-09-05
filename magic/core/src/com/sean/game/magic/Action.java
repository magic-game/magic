package com.sean.game.magic;

import com.sean.game.factory.FactoryFacade;

public interface Action {
	public void perform(Event e, Spell s, FactoryFacade entityFactory);
}
