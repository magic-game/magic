package com.sean.game.magic;

import com.sean.game.MagicGame;

public interface Action {
	public void perform(Event e, Spell s, MagicGame m);
}
