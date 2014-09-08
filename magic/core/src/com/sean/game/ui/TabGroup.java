package com.sean.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sean.game.MagicGame;

public class TabGroup {

	public Group group;
	public ViewGroup viewTabGroup;
	public Group tabList;
	private Skin skin;
	private Group tabContent;
	private CraftGroup craftGroup;
	private PlayerGroup playerGroup;
	private MagicGame magicGame;

	public TabGroup(Skin skin, MagicGame magicGame) {
		this.skin = skin;
		tabContent = new Group();
		viewTabGroup = new ViewGroup(skin, magicGame);
		craftGroup = new CraftGroup(skin, magicGame);
		playerGroup = new PlayerGroup(skin, magicGame);
		tabList = createTabList();
		group = new Group();
		group.addActor(tabList);
		group.addActor(tabContent);
		tabContent.addActor(viewTabGroup.group);
		this.magicGame = magicGame;
	}

	private Group createTabList() {
		Group group = new Group();
		Vector2 pos = new Vector2(20, 700);
		group.addActor(createTabButton("VIEW", pos));
		pos.x += 160f;
		group.addActor(createTabButton("CRAFT", pos));
		pos.x += 160f;
		group.addActor(createTabButton("ITEMS", pos));
		pos.x += 160f;
		group.addActor(createTabButton("PLAYER", pos));
		return group;
	}

	private TextButton createTabButton(final String text, Vector2 pos) {
		TextButton button = new TextButton(text, skin, "default");
		button.setWidth(120f);
		button.setHeight(30f);
		button.setPosition(pos.x, pos.y);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCurrentTab(text);
			}
		});
		return button;
	}

	private void setCurrentTab(String tab) {
		tabContent.clear();
		if (group != null) {
			if (tab.equals("VIEW")) {
				viewTabGroup.update();
				tabContent.addActor(viewTabGroup.group);
				
			}
			if (tab.equals("CRAFT")) {
				tabContent.addActor(craftGroup.group);
			}
			if (tab.equals("ITEMS")) {
//				tabContent.addActor(.group);
			}
			if (tab.equals("PLAYER")) {
				tabContent.addActor(playerGroup.group);
			}
		}
	}
}
