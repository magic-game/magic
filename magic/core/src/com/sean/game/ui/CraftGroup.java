package com.sean.game.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sean.game.MagicGame;
import com.sean.game.magic.ActionType;
import com.sean.game.magic.EventType;
import com.sean.game.magic.Param;
import com.sean.game.magic.SpellTemplateLoader;
import com.sean.game.magic.json.EventActionMapping;
import com.sean.game.magic.json.ParamMapping;
import com.sean.game.magic.json.SpellTemplateMapping;

public class CraftGroup {

	public Group group;
	
	private Skin skin;
	private Group info;
	private Group eventActionList;
	private Group eventActionDetail;
	private Group paramList;
	
	private MagicGame magicGame;
	private SpellTemplateMapping spell;
	private EventActionMapping currentEventActionMapping = null;
	
	public CraftGroup(Skin skin, MagicGame magicGame) {
		group = new Group();
		info = new Group();
		eventActionList = new Group();
		eventActionDetail = new Group();
		paramList = new Group();
		this.skin = skin;
		group.addActor(info);
		group.addActor(eventActionList);
		group.addActor(createCraftButton());
		group.addActor(eventActionDetail);
		group.addActor(paramList);
		spell = new SpellTemplateMapping();
		spell.eventActionMappings = new ArrayList<EventActionMapping>();
		updateInfoGroup();
		updateEventActionList();
		updateEventActionDetail();
		updateParamList();
		this.magicGame = magicGame;
	}
	
	private void updateParamList() {
		paramList.clear();
		paramList.setPosition(750, 660);
		Vector2 pos = new Vector2(0,0);
		
		pos.y -= 30;
		Label label = new Label("Params", skin, "default");
		label.setBounds(pos.x, pos.y, 120, 30);
		pos.y -= 30;
		Label nameLabel = new Label("Name", skin, "default");
		nameLabel.setBounds(pos.x, pos.y, 120, 30);
		Label valueLabel = new Label("Value", skin, "default");
		valueLabel.setBounds(pos.x + 80, pos.y, 120, 30);
		paramList.addActor(nameLabel);
		paramList.addActor(valueLabel);
		
		if (currentEventActionMapping != null) {
			if (currentEventActionMapping.paramMappings != null) {
				pos.y -= 30;
				Group paramListGroup = getParamListGroup(currentEventActionMapping.paramMappings);
				paramListGroup.setPosition(pos.x, pos.y);
				paramList.addActor(paramListGroup);
			}
		}
		
		paramList.addActor(label);
	}
	
	private Group getParamListGroup(List<ParamMapping> paramMappings) {
		Group group = new Group();
		Vector2 pos = new Vector2(0,0);
		for (final ParamMapping paramMapping : paramMappings) {
			Label name = new Label(paramMapping.name, skin, "default");
			name.setBounds(pos.x, pos.y, 60, 30);
			group.addActor(name);
			TextField value = new TextField(paramMapping.value, skin, "default");
			value.setBounds(pos.x + 60, pos.y, 60, 30);
			value.addListener(new InputListener() {
				@Override
				public boolean keyUp (InputEvent event, int keycode) {
					String text = ((TextField)event.getListenerActor()).getText();
					paramMapping.value = text;
					return false;
				}
			});
			pos.y -= 40;
			group.addActor(value);
		}
		
		return group;
	}
	
	private void updateEventActionDetail() {
		eventActionDetail.clear();
		eventActionDetail.setPosition(480, 660);
		Vector2 pos = new Vector2(0,0);
		pos.y -= 30;
		Label label = new Label("Detail", skin, "default");
		label.setBounds(pos.x, pos.y, 120, 30);
		pos.y -= 30;
		Label eventLabel = new Label("EVENT", skin, "default");
		eventLabel.setBounds(pos.x, pos.y, 120, 30);
		Label actionLabel = new Label("ACTION", skin, "default");
		actionLabel.setBounds(pos.x + 80, pos.y, 120, 30);
		
		eventActionDetail.addActor(eventLabel);
		eventActionDetail.addActor(actionLabel);
		
		final Group actualDetailGroup = new Group();
		
		if (currentEventActionMapping != null) {
			pos.y -= 40;
			TextButton eventButton = new TextButton(currentEventActionMapping.eventType, skin, "default");
			eventButton.setBounds(pos.x, pos.y, 120, 30);
			eventButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Group eventsListGroup = getEventsListGroup();
					actualDetailGroup.clear();
					actualDetailGroup.addActor(eventsListGroup);
				}
			});
			eventActionDetail.addActor(eventButton);
			
			TextButton actionButton = new TextButton(currentEventActionMapping.actionType, skin, "default");
			actionButton.setBounds(pos.x + 140, pos.y, 120, 30);
			actionButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Group actionsListGroup = getActionsListGroup();
					actualDetailGroup.clear();
					actualDetailGroup.addActor(actionsListGroup);
				}
			});
			eventActionDetail.addActor(actionButton);
		}
		
		pos.y -= 40;
		actualDetailGroup.setPosition(pos.x, pos.y);
		eventActionDetail.addActor(actualDetailGroup);
		eventActionDetail.addActor(label);
		updateParamList();
	}
	
	private Group getActionsListGroup() {
		Group group = new Group();
		Vector2 pos = new Vector2(0,0);
		
		pos.y -= 40;
		Label actionLabel = new Label("type", skin, "default");
		actionLabel.setBounds(pos.x, pos.y, 120, 30);
		Label requiresLabel = new Label("requires", skin, "default");
		requiresLabel.setBounds(pos.x + 140, pos.y, 120, 30);
		
		group.addActor(actionLabel);
		group.addActor(requiresLabel);
		
		for (final ActionType actionType : ActionType.values()) {
			pos.y -= 40;
			TextButton button = new TextButton(actionType.toString(), skin, "default");
			button.setBounds(pos.x, pos.y, 120, 30);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					currentEventActionMapping.actionType = actionType.toString();
					currentEventActionMapping.paramMappings = convert(ActionType.valueOf(currentEventActionMapping.actionType).getParams());
					updateEventActionDetail();
					updateEventActionList();
					updateParamList();
				}
			});
			group.addActor(button);
			
		}
		
		
		return group;
	}
	
	private List<ParamMapping> convert(List<Param> params) {
		List<ParamMapping> mappings = new ArrayList<ParamMapping>();
		if (params != null) {
			for (Param param : params) {
				ParamMapping mapping = new ParamMapping();
				mapping.name = param.name;
				mapping.type = param.type;
				mapping.value = param.value;
				mappings.add(mapping);
			}
		}
		return mappings;
	}
	
	private Group getEventsListGroup() {
		Group group = new Group();
		Vector2 pos = new Vector2(0,0);
		
		pos.y -= 40;
		Label eventLabel = new Label("type", skin, "default");
		eventLabel.setBounds(pos.x, pos.y, 120, 30);
		Label requiresLabel = new Label("requires", skin, "default");
		requiresLabel.setBounds(pos.x + 140, pos.y, 120, 30);
		
		group.addActor(eventLabel);
		group.addActor(requiresLabel);
		
		for (final EventType eventType : EventType.values()) {
			pos.y -= 40;
			TextButton button = new TextButton(eventType.toString(), skin, "default");
			button.setBounds(pos.x, pos.y, 120, 30);
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					currentEventActionMapping.eventType = eventType.toString();
					updateEventActionDetail();
					updateEventActionList();
				}
			});
			group.addActor(button);
			
			TextButton costItem = new TextButton(eventType.costItem.toString(), skin, "default");
			costItem.setBounds(pos.x + 140, pos.y, 120, 30);
			group.addActor(costItem);
		}
		
		
		return group;
	}
	
	private void updateEventActionList() {
		eventActionList.clear();
		eventActionList.setPosition(240, 660);
		Vector2 pos = new Vector2();
		Label label = new Label("Event Action List", skin, "default");
		pos.y -= 30;
		label.setBounds(pos.x, pos.y, 150, 30);
		pos.y -= 40;
		TextButton addButton = new TextButton("Add EventAction", skin, "default");
		addButton.setBounds(pos.x, pos.y, 150, 30);
		addButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				EventActionMapping mapping = new EventActionMapping();
				mapping.eventType = EventType.NONE.toString();
				mapping.actionType = EventType.NONE.toString();
				spell.eventActionMappings.add(mapping);
				updateEventActionList();
				updateEventActionDetail();
			}
		});
		for (final EventActionMapping mapping : spell.eventActionMappings) {
			Group itemGroup = createEventActionItem(mapping);
			pos.y -= 40;
			itemGroup.setPosition(pos.x, pos.y);
			eventActionList.addActor(itemGroup);
		}
		eventActionList.addActor(label);
		eventActionList.addActor(addButton);
	}
	
	private Group createEventActionItem(final EventActionMapping mapping) {
		Group group = new Group();
		Vector2 pos= new Vector2(0,0);
		pos.y -= 30;
		TextButton eventActionButton = new TextButton(mapping.eventType + " - " + mapping.actionType, skin, "default");
		eventActionButton.setBounds(pos.x, pos.y, 180, 30);
		eventActionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				currentEventActionMapping = mapping;
				updateEventActionDetail();
			}
		});
		TextButton removeItemButton = new TextButton("X", skin, "default");
		removeItemButton.setBounds(pos.x + 190, pos.y, 30, 30);
		removeItemButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				spell.eventActionMappings.remove(mapping);
				updateEventActionList();
			}
		});
		group.addActor(eventActionButton);
		group.addActor(removeItemButton);
		return group;		
	}
	
	private void updateInfoGroup() {
		info.clear();
		Vector2 pos = new Vector2(60, 600);
		info.setPosition(pos.x, pos.y);
		String name = spell.name == null ? "" : spell.name;
		InputListener nameil = new InputListener() {
			@Override
			public boolean keyUp (InputEvent event, int keycode) {
				String text = ((TextArea)event.getListenerActor()).getText();
				spell.name = text;
				return false;
			}
		};
		Group nameEdit = createEditFieldGroup("name", name, nameil, 150, 30);
		nameEdit.setPosition(0, 0);
		
		String description = spell.description == null ? "" : spell.description;
		InputListener descil = new InputListener() {
			@Override
			public boolean keyUp (InputEvent event, int keycode) {
				String text = ((TextArea)event.getListenerActor()).getText();
				spell.description = text;
				return false;
			}
		};
		Group descEdit = createEditFieldGroup("description", description, descil, 150, 150);
		descEdit.setPosition(0, -210);
		
		info.addActor(nameEdit);
		info.addActor(descEdit);
	}
	
	public Group createEditFieldGroup(String name, String value, InputListener listener, float width, float height) {
		Group group = new Group();
		Label label = new Label(name, skin, "default");
		label.setBounds(0, height, width, 30);
		final TextArea edit = new TextArea(value, skin, "default");
		edit.setBounds(0, 0, width, height);
		
		edit.addListener(listener);
		group.addActor(label);
		group.addActor(edit);
		return group;
	}
	
	private TextButton createCraftButton() {
		TextButton button = new TextButton("Craft!", skin, "default");
		button.setWidth(60f);
		button.setHeight(30f);
		button.setPosition(60, 60);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				magicGame.player.spellTemplates.add(SpellTemplateLoader.createSpellTemplate(spell));
				spell = new SpellTemplateMapping();
				spell.eventActionMappings = new ArrayList<EventActionMapping>();
				currentEventActionMapping = null;
				updateInfoGroup();
				updateEventActionList();
				updateEventActionDetail();
				
			}
		});
		return button;
	}
	
}
