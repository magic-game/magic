package com.sean.game.magic.actions;

import com.sean.game.entity.Entity;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.Action;
import com.sean.game.magic.Event;
import com.sean.game.magic.Spell;

public class HurtPersonAction implements Action {

	int damage;
	
	public HurtPersonAction(int damage) {
		this.damage = damage;
	}
	
	@Override
	public void perform(Event e, Spell s, FactoryFacade entityFactory) {
		Entity entity = e.getSource();
		if (entity != null) {
			entity.getHurt(damage);			
		}
	}
}
