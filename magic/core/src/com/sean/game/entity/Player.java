package com.sean.game.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.magic.SpellTemplate;
import com.sean.game.magic.SpellTemplateLoader;

public class Player {

	FactoryFacade entityFactory;
	boolean lockLeft;
	public PlayerEntity entity;
	public List<SpellTemplate> spellTemplates;
	public SpellTemplate currentSpellTemplate;
	
	public Player(FactoryFacade entityFactory, Vector3 position, Vector3 direction) {
		this.entityFactory = entityFactory;
		this.entity = entityFactory.createPlayerEntity(position, direction);
		spellTemplates = SpellTemplateLoader.loadSpellTemplatesFile("../core/assets/playerSpellTemplates.json");
		currentSpellTemplate = spellTemplates.get(0);
	}
	
	public void useLeft(boolean down) {
		if (down && !lockLeft) {
			if (entity.energy > 0) {
				entityFactory.createSpell(entity, currentSpellTemplate);
				entity.energy--;				
			}
			lockLeft = true;
		}
		if (!down) {
			lockLeft = false;
		}
	}
		
	public void useRight() {
		
	}
}
