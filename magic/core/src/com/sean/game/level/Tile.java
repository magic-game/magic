package com.sean.game.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.sean.game.LightHolder;
import com.sean.game.entity.Entity;

public class Tile {
	
	public List<ModelInstance> models;
	public Vector3 pos;
	public boolean blocking;
	public LightHolder lightHolder;
	public Entity entity;
	
	public Tile () {
		models = new ArrayList<ModelInstance>();
		pos = new Vector3();
	}
	
	public void updatePosition() {
		for (ModelInstance instance : models) {
			instance.transform.idt().translate(pos);
		}
	}
}
