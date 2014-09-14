package com.sean.game.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;
import com.sean.game.magic.Event;
import com.sean.game.magic.EventType;

public class EnergyPickupEntity extends BasicEntity {

	public EnergyPickupEntity(Decal decal, LightHolder lightHolder, Body body, final String amount) {
		super(decal, lightHolder, body);
		this.addListener(new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.getType() == EventType.COLLISION) {
					Entity entity = event.getSource();
					if (entity != null) {
						entity.addEnergy(Integer.valueOf(amount));
						getHurt(1);
					}
				}
			}
		});
	}

	
	
}
