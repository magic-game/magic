package com.sean.game.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.sean.game.MagicGame;
import com.sean.game.magic.SpellTemplateLoader;

public class PlayerGroup {

	public Group group;
	
	private Group fileList;
	private Group saveLoad;
	private Group newFile;
	private Skin skin;
	private String currentFileName;
	private MagicGame magicGame;
	
	private static final String DIRECTORY = "../core/assets/playerData";
	
	public PlayerGroup(Skin skin, MagicGame magicGame) {
		this.magicGame = magicGame;
		currentFileName = "";
		group = new Group();
		fileList = new Group();
		saveLoad = new Group();
		newFile = new Group();
		this.skin = skin;
		
		group.addActor(fileList);
		group.addActor(saveLoad);
		updateFileList();
		updateSaveLoad();
	}
	
	private void updateSaveLoad() {
		saveLoad.clear();
		saveLoad.setPosition(420, 600);
		Vector2 pos = new Vector2(0,0);
		
		final TextButton saveButton = new TextButton("Save current to File", skin, "default");
		pos.y -= 60;
		saveButton.setBounds(pos.x, pos.y, 210, 30);
		saveButton.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if (currentFileName != null && !currentFileName.equals("")) {
					SpellTemplateLoader.saveSpellTemplates(magicGame.player.spellTemplates, DIRECTORY + "/" + currentFileName);
					saveButton.setText("Saved!");
				}
			}
		});
		
		final TextButton loadButton = new TextButton("Load current from file", skin, "default");
		pos.y -= 60;
		loadButton.setBounds(pos.x, pos.y, 210, 30);
		loadButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				if (currentFileName != null && !currentFileName.equals("")) {
					magicGame.player.loadSpellTemplates(DIRECTORY + "/" + currentFileName);
					loadButton.setText("Loaded!");					
				}
			}
			
		});
		
		saveLoad.addActor(saveButton);
		saveLoad.addActor(loadButton);
	}
	
	private void updateFileList() {
		fileList.clear();
		fileList.setPosition(60, 600);
		List<String> fileNames = listFolder(DIRECTORY);
		Vector2 pos = new Vector2(0,0);
		Label label = new Label("Player Data Files", skin, "default");
		pos.y -= 36;
		label.setBounds(pos.x, pos.y, 300, 30);
		fileList.addActor(label);
		for (final String name : fileNames) {
			pos.y -= 36;
			TextButton button = new TextButton(name, skin, "default");
			button.setBounds(pos.x, pos.y, 300, 30);
			button.addListener(new ClickListener() {
				@Override
				public void clicked (InputEvent event, float x, float y) {
					clearSelection();
					event.getListenerActor().setColor(1.0f, 0.0f, 0.1f, 1.0f);
					currentFileName = name;
				}
			});
			fileList.addActor(button);
		}
		clearSelection();
	}
	
	private void clearSelection() {
		for (Actor actor : fileList.getChildren()) {
			actor.setColor(0.5f, 0.5f, 0.5f, 1.0f);
		}
	}
	
	public List<String> listFolder(String directoryName) {
	    File directory = new File(directoryName);
	    List<String> fileNames = new ArrayList<String>();
	    File[] fileList = directory.listFiles();
	    for (File file : fileList) {
	        if (file.isFile()) {
	        	fileNames.add(file.getName());
	        }
	    }
	    return fileNames;
	}
	
}
