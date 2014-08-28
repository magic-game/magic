package com.sean.game.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.sean.game.LightHolder;
import com.sean.game.entity.Entity;

public class BasicMap {

	public List<Level> levels;
	
	public BasicMap(List<Level> levels) {
		this.levels = levels;
	}
	
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Level level : levels) {
			for (Tile tile : level.tiles) {
				if (tile.entity != null) {
					entities.add(tile.entity);					
				}
			}
		}
		return entities;
	}
	
	public List<ModelInstance> getModelInstances() {
		List<ModelInstance> models = new ArrayList<ModelInstance>();
		for (Level level : levels) {
			for (Tile tile : level.tiles) {
				models.addAll(tile.models);
			}
		}
		return models;
	}

	public List<LightHolder> getLightHolders() {
		List<LightHolder> lights = new ArrayList<LightHolder>();
		for (Level level : levels) {
			for (Tile tile : level.tiles) {
				if (tile.lightHolder != null) {
					lights.add(tile.lightHolder);
				}
			}
		}
		return lights;
	}
}
