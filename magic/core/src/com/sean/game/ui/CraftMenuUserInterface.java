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
	private Skin skin;
	public Stage stage;
	
	public CraftMenuUserInterface(final MagicGame magicGame) {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();

		final TextButton button = new TextButton("Click me", skin, "default");
		button.setWidth(200f);
		button.setHeight(20f);
		button.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 10f);

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				button.setText("You clicked the button");
			}
		});

		final TextButton exitButton = new TextButton("Back", skin, "default");
		exitButton.setWidth(200f);
		exitButton.setHeight(20f);
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 100f);

		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				exitButton.setText("exiting");
				magicGame.setGamePlay(GamePlay.FIRST_PERSON);
			}
		});
		
		stage.addActor(button);
		stage.addActor(exitButton);
		stage.getBatch().setColor(0, 0, 0, 0);
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
