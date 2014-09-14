package com.sean.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sean.game.LightHolder;
import com.sean.game.Util;
import com.sean.game.magic.SpellTemplate;
import com.sean.game.magic.SpellTemplateLoader;

public class PersonEntity extends BasicEntity {

	public int health;
	public float heightWave;
	public AiState aiState;
	private float MOVE_SPEED = 0.01f;
	float moveCoolDown = 0;
	Vector3 target;
	public SpellTemplate spell;
	float shootCoolDown;
	
	public PersonEntity(Body body, Decal decal, LightHolder lightHolder, int health) {
		super(decal, lightHolder, body);
		this.health = health;
		heightWave = 0;
		aiState = AiState.IDLE;
		target = this.getPosition();
		spell = SpellTemplateLoader.loadSpellTemplatesFile("../core/assets/playerData/joe.json").get(0);
	}
	
	@Override
	public void update(Player player) {
		if (health <= 0) {
			state = EntityState.DEAD;
			lightHolder.intensity = 0;
		}
		decal.setPosition(getPosition());
		if (lightHolder != null) {
			lightHolder.pos = getPosition();
			lightHolder.intensity = ((float)Math.sin((double)heightWave) * 1.0f) + 1.5f;
		}
		heightWave = heightWave + Gdx.graphics.getDeltaTime();
		think(player);
	}
	
	public void think(Player player) {
		
		
		// if state is sees player 
		if (aiState.equals(AiState.ATTACKING)) {
			target = player.entity.getPosition();
			shootCoolDown -= Gdx.graphics.getDeltaTime();
			if (shootCoolDown < 0) {
				shootCoolDown = 3f;
				aiState = AiState.CASTING;
			}
		}
		
		if (target.dst2(getPosition()) > 1) {
			float moveSpeed = Gdx.graphics.getDeltaTime() * MOVE_SPEED * 100;
			Vector2 entityToPlayerDirection = Util.toVector2(target.cpy().sub(this.getPosition()));
			Vector2 impulse = entityToPlayerDirection.scl(moveSpeed);
			this.body.applyLinearImpulse(impulse, this.body.getWorldCenter(), true);
			this.body.setTransform(body.getPosition(), (float)Math.atan2(impulse.y, impulse.x));
		}
		
	}
	
	@Override
	public Vector3 getPosition() {
		float height = ((float)Math.sin((double)heightWave) / 10f);
		return new Vector3(body.getWorldCenter().x, height, body.getWorldCenter().y);
	}

	@Override
	public void getHurt(int damage) {
		health = health - damage;
	}
}
