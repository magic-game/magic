package com.sean.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sean.game.GamePlay;
import com.sean.game.MagicGame;

public class CraftMenuUserInterface {
	private SpriteBatch batch;
	public Skin skin;
	public Stage stage;
	private TabGroup tabGroup;
	
	public CraftMenuUserInterface(final MagicGame magicGame) {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		tabGroup = new TabGroup(skin, magicGame);
		stage.addActor(tabGroup.group);
		stage.addActor(createExitButton(magicGame));
	}
	
	private TextButton createExitButton(final MagicGame magicGame) {
		final TextButton exitButton = new TextButton("Back", skin, "default");
		exitButton.setWidth(100f);
		exitButton.setHeight(30f);
		exitButton.setPosition(450, 40);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				magicGame.setGamePlay(GamePlay.FIRST_PERSON);
			}
		});
		return exitButton;
	}

	public void render() {
		batch.begin();
		stage.draw();
		batch.end();
	}

	public void dispose() {
		batch.dispose();
	}
}
