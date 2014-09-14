package com.sean.game.magic.actions;

import java.util.List;

import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.Action;
import com.sean.game.magic.Event;
import com.sean.game.magic.Param;
import com.sean.game.magic.Spell;

public class DelayAction implements Action {

	int times;
	float delay;
	
	public DelayAction(List<Param> params) {
		for (Param param : params) {
			if (param.name.equals("times")) {
				this.times = Integer.valueOf(param.value);
			}
			if (param.name.equals("delay")) {
				this.delay = Float.valueOf(param.value);
			}
		}
	}
	
	@Override
	public void perform(Event event, Spell spell, FactoryFacade entityFactory) {
		entityFactory.createDelay(times, delay, spell, event.getSource());
	}

}
