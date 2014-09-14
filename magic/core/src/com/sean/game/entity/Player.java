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
	public SpellTemplate leftSlotSpell;
	public SpellTemplate rightSlotSpell;
	private int leftSlotSpellIndex = 0;
	private int rightSlotSpellIndex = 0;
	boolean lockCycle = false;
	private boolean lockRight = false;
	
	public Player(FactoryFacade entityFactory, Vector3 position, Vector3 direction) {
		this.entityFactory = entityFactory;
		this.entity = entityFactory.createPlayerEntity(position, direction);
		loadSpellTemplates("../core/assets/playerData/player.json");
	}
	
	public void loadSpellTemplates(String location) {
		spellTemplates = SpellTemplateLoader.loadSpellTemplatesFile(location);
		leftSlotSpell = spellTemplates.get(leftSlotSpellIndex);
		rightSlotSpell = spellTemplates.get(rightSlotSpellIndex);
	}
	
	public void useLeft(boolean down) {
		if (down && !lockLeft) {
			if (entity.energy > 0) {
				entityFactory.createSpell(entity, leftSlotSpell);
				entity.energy--;				
			}
			lockLeft = true;
		}
		if (!down) {
			lockLeft = false;
		}
	}
		
	public void useRight(boolean down) {
		if (down && !lockRight) {
			if (entity.energy > 0) {
				entityFactory.createSpell(entity, rightSlotSpell);
				entity.energy--;				
			}
			lockRight = true;
		}
		if (!down) {
			lockRight = false;
		}
	}
	
	public void cycleNextSpell(boolean down) {
		if (down && !lockCycle) {
			leftSlotSpellIndex++;
			if (leftSlotSpellIndex >= spellTemplates.size()) {
				leftSlotSpellIndex = 0;
			}
			leftSlotSpell = spellTemplates.get(leftSlotSpellIndex);
			
//			SpellTemplateLoader.saveSpellTemplates(spellTemplates, "../core/assets/saveTest.json");
			
			lockCycle = true;
		}
		if (!down) {
			lockCycle = false;
		}
		
	}
}
