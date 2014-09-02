package com.sean.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;

public class LightManager {

	private MapSimulation simulation;
	private PerspectiveCamera camera;
	public List<PointLight> lights;
	private static final int MAX_LIGHTS = 5;
	
	public LightManager(MapSimulation simulation, PerspectiveCamera camera) {
		this.simulation = simulation;
		this.camera = camera;
		lights = new ArrayList<PointLight>();
		for (int i = 0; i < MAX_LIGHTS; i++) {
			PointLight light = new PointLight();
			light.set(0, 0, 0, MagicConstants.HIDDEN_LIGHT_POS, 0);
			lights.add(light);
		}
	}

	public void update() {
		for (LightHolder lightHolder : simulation.lightHolders) {
			lightHolder.distance = lightHolder.pos.dst2(camera.position);
		}
		Collections.sort(simulation.lightHolders, new Comparator<LightHolder>() {
			@Override
			public int compare(LightHolder lh1, LightHolder lh2) {
				return Float.compare(lh1.distance, lh2.distance);
			}
		});
		int index = 0;
		for (PointLight light : lights) {
			if (index >= simulation.lightHolders.size()) {
				break;
			}
			LightHolder holder = simulation.lightHolders.get(index);
			light.set(holder.color.x, holder.color.y, holder.color.z, holder.pos, holder.intensity);
			index++;
		}
	}
}
