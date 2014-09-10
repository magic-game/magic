package com.sean.game.magic.actions;

import java.util.List;

import com.sean.game.entity.Entity;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.Action;
import com.sean.game.magic.Event;
import com.sean.game.magic.Param;
import com.sean.game.magic.Spell;

public class HurtPersonAction implements Action {

	int damage;
	
	public HurtPersonAction(List<Param> params) {
		for (Param param : params) {
			if (param.name.equals("damage")) {
				this.damage = Integer.valueOf(param.value);				
			}
		}
	}
	
	@Override
	public void perform(Event e, Spell s, FactoryFacade entityFactory) {
		Entity entity = e.getSource();
		if (entity != null) {
			entity.getHurt(damage);			
		}
	}
}
