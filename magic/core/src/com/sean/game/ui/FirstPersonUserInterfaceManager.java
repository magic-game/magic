package com.sean.game.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sean.game.entity.Player;

public class FirstPersonUserInterfaceManager {

	public OrthographicCamera cameraUI;
	public SpriteBatch spriteBatch;
	public Sprite spriteUI;
	public EnergyBar energyBar;
	public EnergyBar healthBar;
	private Player player;
	public List<InventoryIcon> icons;
	
	public FirstPersonUserInterfaceManager(Player player) {
		this.cameraUI = new OrthographicCamera(1024, 768);
		this.spriteBatch = new SpriteBatch();
		this.energyBar = new EnergyBar(20, 20, false, "energybar2.png");
		this.healthBar = new EnergyBar(20, 20, true, "healthbar.png");
		this.player = player;
		icons = new ArrayList<InventoryIcon>();
	}
	
	public void update() {
		energyBar.setCurrentEnergy(player.entity.energy);
		healthBar.setCurrentEnergy(player.entity.health);
	}
	
	public void render() {
		spriteBatch.setProjectionMatrix(cameraUI.combined);
		spriteBatch.begin();
		Vector2 energyBarPos = new Vector2(-480, -360);
		for (Sprite sprite : energyBar.getSprites()) {
			sprite.setPosition(energyBarPos.x, energyBarPos.y);
			sprite.draw(spriteBatch);
			energyBarPos.x = energyBarPos.x + 7;
		}
		Vector2 healthBarPos = new Vector2(490, -360);
		for (Sprite sprite : healthBar.getSprites()) {
			sprite.setPosition(healthBarPos.x, healthBarPos.y);
			sprite.draw(spriteBatch);
			healthBarPos.x = healthBarPos.x - 7;
		}
		Vector2 iconPos = new Vector2(0, -340);
		for (InventoryIcon icon : icons) {
			icon.sprite.setPosition(iconPos.x, iconPos.y);
			icon.sprite.draw(spriteBatch);
//			iconPos.x += 108;
		}
		spriteBatch.end();
	}
}
