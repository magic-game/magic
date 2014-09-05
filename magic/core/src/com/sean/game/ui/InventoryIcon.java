package com.sean.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InventoryIcon {

	public Sprite sprite;
	
	public InventoryIcon(String textureLocation) {
		Texture testTexture = new Texture(Gdx.files.internal(textureLocation));
		testTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		sprite = new Sprite(testTexture);
		sprite.scale(6.0f);
	}
}
