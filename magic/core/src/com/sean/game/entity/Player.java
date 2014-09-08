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
	private int currentSpellIndex = 0;
	boolean lockCycle = false;
	
	public Player(FactoryFacade entityFactory, Vector3 position, Vector3 direction) {
		this.entityFactory = entityFactory;
		this.entity = entityFactory.createPlayerEntity(position, direction);
		loadSpellTemplates("../core/assets/playerData/player.json");
	}
	
	public void loadSpellTemplates(String location) {
		spellTemplates = SpellTemplateLoader.loadSpellTemplatesFile(location);
		currentSpellTemplate = spellTemplates.get(currentSpellIndex);
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
	
	public void cycleNextSpell(boolean down) {
		if (down && !lockCycle) {
			currentSpellIndex++;
			if (currentSpellIndex >= spellTemplates.size()) {
				currentSpellIndex = 0;
			}
			currentSpellTemplate = spellTemplates.get(currentSpellIndex);
			
//			SpellTemplateLoader.saveSpellTemplates(spellTemplates, "../core/assets/saveTest.json");
			
			lockCycle = true;
		}
		if (!down) {
			lockCycle = false;
		}
		
	}
}
