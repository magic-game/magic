package com.sean.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player {

	Body body;
	World world;
	MagicGame magicGame;
	boolean lockLeft;
	
	public Player(Body body, MagicGame magicGame) {
		this.body = body;
		this.magicGame = magicGame;
	}
	
	public void useLeft(boolean down) {
		if (down && !lockLeft) {
			magicGame.createEntity(new Vector3(body.getWorldCenter().x, 0, body.getWorldCenter().y));
			lockLeft = true;
		}
		if (!down) {
			lockLeft = false;
		}
		
	}
	
	public void useRight() {
		
	}
}
