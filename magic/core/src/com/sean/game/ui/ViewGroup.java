package com.sean.game.ui;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sean.game.MagicGame;
import com.sean.game.magic.EventActionStep;
import com.sean.game.magic.Param;
import com.sean.game.magic.SpellTemplate;

public class ViewGroup {

	private Group spellList;
	private Group spellDetail;
	private Group paramList;
	
	public Group group;
	
	private Skin skin;
	private MagicGame magicGame;
	
	public ViewGroup(Skin skin, MagicGame magicGame) {
		this.skin = skin;
		List<SpellTemplate> spells = magicGame.player.spellTemplates;
		spellList = new Group();
		spellDetail = new Group();
		paramList = new Group();
		updateSpellTemplateList(spells);
		updateSpellDetail(spells.isEmpty() ? null : spells.get(0));
		group = new Group();
		group.addActor(spellList);
		group.addActor(spellDetail);
		group.addActor(paramList);
		this.magicGame = magicGame;
	}
	
	public void update() {
		List<SpellTemplate> spells = magicGame.player.spellTemplates;
		updateSpellTemplateList(spells);
		updateSpellDetail(spells.isEmpty() ? null : spells.get(0));
	}
	
	private void updateSpellTemplateList(List<SpellTemplate> templates) {
		spellList.clear();
		Vector2 pos = new Vector2(100, 600);
		for (SpellTemplate template : templates) {
			Group button = getSpellTemplateItem(template);
			pos.y -= (button.getHeight() + 10f);
			button.setPosition(pos.x, pos.y);
			spellList.addActor(button);
		}
	}
	
	private Group getSpellTemplateItem(final SpellTemplate spellTemplate) {
		Group spellItemGroup = new Group();
		spellItemGroup.setWidth(200f);
		spellItemGroup.setHeight(40f);
		
		final TextButton button = new TextButton(spellTemplate.name, skin, "default");
		button.setWidth(200f);
		button.setHeight(40f);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				updateSpellDetail(spellTemplate);
				updateParamList(null);
			}
		});
		
		final TextButton removeButton = new TextButton("X", skin, "default");
		removeButton.setWidth(30f);
		removeButton.setHeight(30f);
		removeButton.setPosition(200f, 0f);
		removeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				magicGame.player.spellTemplates.remove(spellTemplate);
				update();
				updateParamList(null);
			}
		});
		
		spellItemGroup.addActor(button);
		spellItemGroup.addActor(removeButton);
		return spellItemGroup;
	}
	
	private void updateSpellDetail(SpellTemplate spellTemplate) {
		spellDetail.clear();
		String content = "";
		if (spellTemplate != null && spellTemplate.description != null) {
			content = spellTemplate.description;
		}
		TextArea textArea = new TextArea(content, skin, "default");
		textArea.setWidth(300f);
		textArea.setHeight(200f);
		textArea.setPosition(400, 400);
		spellDetail.addActor(textArea);

		Group eventActionStepsGroup = new Group();
		Vector2 stepsPos = new Vector2(400, 400);
		if (spellTemplate != null) {
			for (EventActionStep step : spellTemplate.steps) {
				stepsPos.y -= 40;
				eventActionStepsGroup.addActor(createEventActionStepButton(step, stepsPos));
			}
		}
		spellDetail.addActor(eventActionStepsGroup);
		
	}
	
	private TextButton createEventActionStepButton(final EventActionStep step, Vector2 pos) {
		TextButton button = new TextButton(getEventActionStepDescription(step), skin, "default");
		button.setWidth(300f);
		button.setHeight(30f);
		button.setPosition(pos.x, pos.y);
		
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				updateParamList(step);
			}
		});
		
		return button;
	}
	
	private void updateParamList(EventActionStep step) {
		paramList.clear();
		if (step != null) {
			paramList = createParamView(paramList, step);
		}
	}
	
	private Group createParamView(Group group, EventActionStep step) {
		Vector2 pos = new Vector2(700, 400);
		for (Param param : step.params) {
			pos.y -= 40f;
			group.addActor(createParamButton(param, pos));
		}
		return group;
	}
	
	private TextButton createParamButton(final Param param, Vector2 pos) {
		TextButton button = new TextButton(getParamDescription(param), skin, "default");
		button.setWidth(300f);
		button.setHeight(30f);
		button.setPosition(pos.x, pos.y);
		return button;
	}
	
	private String getParamDescription(Param param) {
		return String.format("%s : %s", param.name, param.value);
	}
	
	private String getEventActionStepDescription(EventActionStep step) {
		return String.format("%s -> %s", step.eventType.toString(), step.actionType.toString());
	}

}
