package com.sean.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.sean.game.entity.MagicEntity;
import com.sean.game.level.BasicMap;
import com.sean.game.level.MapLoader;

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

	private static final int MAX_LIGHTS = 5;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new LoggingContactListener());
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
	}
	
	public void cleanUpBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			Entity data = (Entity) body.getUserData();
			if (data != null && data.getState() == EntityState.DEAD) {
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

	public void createEntity(Vector3 position) {
		position.add(camera.direction.scl(1.0f));
		Vector2 angle = new Vector2(camera.direction.x, camera.direction.z);
		LightHolder lightHolder = new LightHolder(new Vector3(0.5f, 1.5f, 0.5f), position.cpy(), 4.0f);
		Texture image = new Texture(Gdx.files.internal("ball.png"));
		Decal decal = Decal.newDecal(0.19f, 0.19f, new TextureRegion(image), true);
		decal.setPosition(position);
		Body entityBody = BodyFactory.createMagicEntityBody(world, position, angle.angleRad());
		Entity entity = new MagicEntity(world, decal, lightHolder, 1.0f, entityBody);
		entityBody.setUserData(entity);
		moveBody(entityBody, 0.4f);
		entities.add(entity);
		decals.add(decal);
		lightHolders.add(lightHolder);
	}

	private void moveBody(Body body, float amount) {
		Vector2 direction = new Vector2((float)Math.cos(body.getAngle()), (float)Math.sin(body.getAngle()));
		body.applyLinearImpulse(direction.scl(amount), body.getPosition(), true);
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
