package com.sean.game.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnergyBar {

	private int currentEnergy;
	private int maxEnergy;
	private Sprite startBarSprite;
	private Sprite endBarSprite;
	private Sprite fullBarSprite;
	private Sprite emptyBarSprite;
	private List<Sprite> sprites;
	private boolean reversed;
	
	public EnergyBar(int currentEnergy, int maxEnergy, boolean reversed, String textureLocation) {
		this.currentEnergy = currentEnergy;
		this.maxEnergy = maxEnergy;
		this.reversed = reversed;
		Texture testTexture = new Texture(Gdx.files.internal(textureLocation));
		testTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		startBarSprite = new Sprite(testTexture);
		startBarSprite.scale(6.0f);
		startBarSprite.setRegion(0, 0, 1, 5);
		startBarSprite.setSize(1, 5);
		
		fullBarSprite = new Sprite(testTexture);
		fullBarSprite.scale(6.0f);
		fullBarSprite.setRegion(1, 0, 1, 5);
		fullBarSprite.setSize(1, 5);
		
		emptyBarSprite = new Sprite(testTexture);
		emptyBarSprite.scale(6.0f);
		emptyBarSprite.setRegion(2, 0, 1, 5);
		emptyBarSprite.setSize(1, 5);
		
		endBarSprite = new Sprite(testTexture);
		endBarSprite.scale(6.0f);
		endBarSprite.setRegion(3, 0, 1, 5);
		endBarSprite.setSize(1, 5);
		
		updateSprites();
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
		updateSprites();
	}
	
	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
		updateSprites();
	}
	
	private void updateSprites() {
		List<Sprite> newSprites = new ArrayList<Sprite>();
		newSprites.add(reversed ? endBarSprite : startBarSprite);
		for (int index = 0; index < maxEnergy ; index++) {
			if (index < currentEnergy) {
				newSprites.add(fullBarSprite);
			} else {
				newSprites.add(emptyBarSprite);
			}
		}
		newSprites.add(reversed ? startBarSprite : endBarSprite);
		sprites = newSprites;
	}

	public List<Sprite> getSprites() {
		return sprites;
	}	
}
