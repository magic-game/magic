package com.sean.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sean.game.entity.Entity;
import com.sean.game.entity.EntityState;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicEntity;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.entity.PersonEntity;
import com.sean.game.level.BasicMap;
import com.sean.game.level.MapLoader;
import com.sean.game.magic.Action;
import com.sean.game.magic.CreateMagicBallAction;
import com.sean.game.magic.Event;
import com.sean.game.magic.HurtPersonAction;
import com.sean.game.magic.ImpulseEntityAction;
import com.sean.game.magic.Spell;
import com.sean.game.magic.SpellBuilder;

public class MagicGame implements ApplicationListener {

	private World world;
	public Environment environment;
	public PerspectiveCamera camera;
	public UserInput input;
	public ModelBatch modelBatch;
	public ModelAssets modelAssets;
	List<ModelInstance> instances;
	List<PointLight> lights;
	DecalBatch decalBatch;
	List<LightHolder> lightHolders;
	List<Decal> decals;
	BasicMap map;
	Player player;
	BodyFactory bodyFactory;
	List<Entity> entities;
	public Shader shader;
	float spinner;
	List<MagicExplosion> explosions;
	List<Spell> spells;

	private static final int MAX_LIGHTS = 5;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new LoggingContactListener(this));
		entities = new ArrayList<Entity>();
		modelBatch = new ModelBatch();
		modelAssets = new ModelAssets();
		bodyFactory = new BodyFactory();
		camera = CameraFactory.createCamera(new Vector3(4, 0, 4)); // start position
		decals = new ArrayList<Decal>();
		decalBatch = new DecalBatch();
		decalBatch.setGroupStrategy(new CameraGroupStrategy(camera));
		player = new Player(bodyFactory.createPlayerBody(world), this);
		input = new UserInput(player);
		Gdx.input.setInputProcessor(input);
		environment = new Environment();

		map = new MapLoader().loadJson("../core/assets/map.json", modelAssets, world);
		instances = map.getModelInstances();
		lightHolders = map.getLightHolders();
		List<Entity> ents = map.getEntities();
		for (Entity entity : ents) {
			decals.add(entity.getDecal());
		}
		entities.addAll(map.getEntities());
		
		lights = new ArrayList<PointLight>();
		for (int i = 0; i < MAX_LIGHTS; i++) {
			PointLight light = new PointLight();
			light.set(0, 0, 0, MagicConstants.HIDDEN_LIGHT_POS, 0);
			lights.add(light);
			environment.add(light);
		}
		explosions = new ArrayList<MagicExplosion>();
		spells = new ArrayList<Spell>();
	}

	@Override
	public void render() {
		updateAll();
		renderAll();
	}
	
	public void updateAll() {
		updateEntities();
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		cleanUpBodies();
		updateCamera();
		input.update();
		updateLights();
	}
	
	public void renderAll() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(camera);
		for (ModelInstance instance : instances) {
			modelBatch.render(instance, environment);
		}
		modelBatch.end();
		for (Decal decal : decals) {
			decal.lookAt(camera.position, camera.up);
			decalBatch.add(decal);
		}
		for (MagicExplosion me : explosions) {
			for (ExplosionParticle ep : me.particles) {
				ep.decal.lookAt(camera.position, camera.up);
				decalBatch.add(ep.decal);
			}
		}
		decalBatch.flush();
	}
	
	public void updateEntities() {
		for (Entity entity : entities) {
			if (entity.getState() == EntityState.ALIVE) {
				entity.update();
			} else {
				lightHolders.remove(entity.getLightHolder());
				decals.remove(entity.getDecal());
			}
		}
		for (MagicExplosion me : explosions) {
			for (ExplosionParticle ep : me.particles) {
				ep.update();
			}
			me.update();
		}
		Iterator<MagicExplosion> mei = explosions.iterator();
		while (mei.hasNext()) {
			MagicExplosion me = mei.next();
			if (!me.alive) {
				lightHolders.remove(me.lightHolder);
				mei.remove();
			}
		}
	}
	
	public void cleanUpBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			Entity data = (Entity) body.getUserData();
			if (data != null && data.getState() == EntityState.DEAD) {
				if (data instanceof PersonEntity) {
					createExplosion(data, new Vector3(1.0f,0.1f,0.1f));
				} else {
					createExplosion(data, new Vector3(1.0f,1.0f,1.0f));
				}
				world.destroyBody(body);
			}
		}
	}

	public void updateLights() {
		for (LightHolder lightHolder : lightHolders) {
			lightHolder.distance = lightHolder.pos.dst2(camera.position);
		}
		Collections.sort(lightHolders, new Comparator<LightHolder>() {
			@Override
			public int compare(LightHolder lh1, LightHolder lh2) {
				return Float.compare(lh1.distance, lh2.distance);
			}
		});
		int index = 0;
		for (PointLight light : lights) {
			if (index >= lightHolders.size()) {
				break;
			}
			LightHolder holder = lightHolders.get(index);
			light.set(holder.color.x, holder.color.y, holder.color.z, holder.pos, holder.intensity);
			index++;
		}
	}

	private void updateCamera() {
		Vector2 pos = player.body.getPosition();
		float angle = player.body.getAngle();
		camera.position.set(new Vector3(pos.x, 0, pos.y));
		camera.direction.set(new Vector3((float) Math.cos(angle), 0f, (float) Math.sin(angle)));
		camera.update();
	}
	
	public Spell createSpell() {
		Event initEvent = new Event("Init", this.player);
		Action action = new CreateMagicBallAction();
		Event onCreateEvent = new Event("CreateMagicBall", null);
		Action impulseAction = new ImpulseEntityAction();
		Event collideEvent = new Event("OnCollide", null);
		Action hurtPersonAction = new HurtPersonAction(1);
		Spell spell = new SpellBuilder()
							.init(this)
							.addEventActionPair(initEvent, action)
							.addEventActionPair(onCreateEvent, impulseAction)
							.addEventActionPair(collideEvent, hurtPersonAction)
							.build();
		spell.handleEvent(initEvent);
		spells.add(spell);
		return spell;
	}
	
	public Entity createEntity(Vector3 position) {
		position.add(camera.direction.scl(1.0f));
		Vector2 angle = new Vector2(camera.direction.x, camera.direction.z);
		LightHolder lightHolder = new LightHolder(new Vector3(0.5f, 1.0f, 0.7f), position.cpy(), 3.0f);
		Texture image = new Texture(Gdx.files.internal("ball.png"));
		Decal decal = Decal.newDecal(0.19f, 0.19f, new TextureRegion(image), true);
		decal.setPosition(position);
		Body entityBody = BodyFactory.createMagicEntityBody(world, position, angle.angleRad());
		Entity entity = new MagicEntity(world, decal, lightHolder, 1.0f, entityBody);
		entityBody.setUserData(entity);
		entities.add(entity);
		decals.add(decal);
		lightHolders.add(lightHolder);
		return entity;
	}

	public void moveBody(Body body, float amount) {
		Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
		body.applyLinearImpulse(direction.scl(amount), body.getPosition(), true);
	}
	 
	public void createExplosion(Entity entity, Vector3 color) {
		Vector3 position = new Vector3(entity.getLastPosition());
		List<ExplosionParticle> eps = new ArrayList<ExplosionParticle>();
		int particles = (int)(Math.random() * 10) + 20;
		
		for (int index = 0; index < particles; index++) {
			float x = 0.07f - ((float)Math.random() * 0.14f);
			float y = 0.04f - ((float)Math.random() * 0.08f);
			float z = 0.07f - ((float)Math.random() * 0.14f);
			Vector3 pos = position.cpy();
			Vector3 vel = new Vector3(x, y, z);
			
			ExplosionParticle ep = new ExplosionParticle(pos, getExplosionDecal(pos), vel, color, 1.0f);
			eps.add(ep);
		}
		
		
		LightHolder lightHolder = new LightHolder(color, position.cpy(), 12.0f);
		MagicExplosion me = new MagicExplosion(eps, lightHolder);
		lightHolders.add(lightHolder);
		explosions.add(me);
	}
	
	private Decal getExplosionDecal(Vector3 pos) {
		Texture image = new Texture(Gdx.files.internal("particle.png"));
		float x = ((float) Math.random() % 0.12f) + 0.03f;
		Decal decal = Decal.newDecal(x, x, new TextureRegion(image), true);
		decal.setPosition(pos.cpy());
		return decal;
	}
	
	@Override
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		decalBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
