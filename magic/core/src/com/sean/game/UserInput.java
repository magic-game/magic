package com.sean.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.IntIntMap;
import com.sean.game.entity.Player;

public class UserInput extends InputAdapter {
	
	private final Body body;
	private Player player;
	private final IntIntMap keys = new IntIntMap();
	private float TURN_SPEED = 1.6f;
	private float MOVE_SPEED = 0.8f;
	private MagicGame magicGame;
	private float degreesPerPixel = 0.3f;
	private final Vector3 tmp = new Vector3();
	
	public UserInput (Player player, MagicGame magicGame) {
		this.body = player.entity.getBody();
		this.player = player;
		this.magicGame = magicGame;
		
	}

	@Override
	public boolean keyDown (int keycode) {
		keys.put(keycode, keycode);
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		keys.remove(keycode, 0);
		return true;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		keys.put(button, button);
		return true;
	}
	
	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		keys.remove(button, 0);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenx, int screeny){
		
		float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
		float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;
		magicGame.camera.direction.rotate(magicGame.camera.up, deltaX);
		tmp.set(magicGame.camera.direction).crs(magicGame.camera.up).nor();
		magicGame.camera.direction.rotate(tmp, deltaY);
		float angleRads = (float)Math.atan2(magicGame.camera.direction.x, -magicGame.camera.direction.z);
		body.setTransform(body.getPosition(), angleRads - (float)Math.toRadians(90));
		return true;
		
	}
	
	public void update () {
		update(Gdx.graphics.getDeltaTime());
	}

	public void update (float deltaTime) {
		
		float turnSpeed = deltaTime * TURN_SPEED * 100;
		float moveSpeed = deltaTime * MOVE_SPEED * 100;
		
//		if (keys.containsKey(Keys.LEFT)) {
//			body.setAngularVelocity(-turnSpeed);
//		} 
//		if (keys.containsKey(Keys.RIGHT)) {
//			body.setAngularVelocity(turnSpeed);
//		}
		if (keys.containsKey(Keys.A)) {
			Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
			direction = direction.rotate90(-1);
			direction = direction.scl(moveSpeed);
			body.applyLinearImpulse(direction, body.getPosition(), true);
		} 
		if (keys.containsKey(Keys.D)) {
			Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
			direction = direction.rotate90(1);
			direction = direction.scl(moveSpeed);
			body.applyLinearImpulse(direction, body.getPosition(), true);
		}
		if (keys.containsKey(Keys.W)) {
			Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
			direction = direction.scl(moveSpeed);
			body.applyLinearImpulse(direction, body.getPosition(), true);
		}
		if (keys.containsKey(Keys.S)) {
			Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
			direction = direction.scl(-moveSpeed);
			body.applyLinearImpulse(direction, body.getPosition(), true);
		}
		if (keys.containsKey(Keys.SPACE) || keys.containsKey(Input.Buttons.LEFT)) {
			player.useLeft(true);
		} else {
			player.useLeft(false);
		}
		if (keys.containsKey(Input.Buttons.RIGHT)) {
			player.useRight(true);
		} else {
			player.useRight(false);
		}
		if (keys.containsKey(Keys.TAB)) {
			magicGame.setGamePlay(GamePlay.CRAFT_MENU);
			clearInput();
		}
		if (keys.containsKey(Keys.Q)) {
			player.cycleNextSpell(true);
		} else {
			player.cycleNextSpell(false);
		}
		if (keys.containsKey(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	private void clearInput() {
		keys.clear();
	}
}
