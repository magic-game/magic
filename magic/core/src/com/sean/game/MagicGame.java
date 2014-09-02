package com.sean.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sean.game.entity.Entity;
import com.sean.game.entity.EntityState;
import com.sean.game.entity.ExplosionParticle;
import com.sean.game.entity.MagicExplosion;
import com.sean.game.entity.PersonEntity;
import com.sean.game.level.BasicMap;
import com.sean.game.level.MapLoader;

public class MagicGame implements ApplicationListener {

	private World world;
	public Environment environment;
	public PerspectiveCamera camera;
	public UserInput input;
	public ModelBatch modelBatch;
	public ModelAssets modelAssets;
	DecalBatch decalBatch;
	BasicMap map;
	Player player;
	BodyFactory bodyFactory;
	public Shader shader;
	float spinner;
	public MapSimulation simulation;
	public EntityFactory entityFactory;
	public LightManager lightManager;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new CollisionContactListener());
		modelBatch = new ModelBatch();
		modelAssets = new ModelAssets();
		bodyFactory = new BodyFactory();
		camera = CameraFactory.createCamera(new Vector3(4, 0, 4)); // start position
		decalBatch = new DecalBatch();
		decalBatch.setGroupStrategy(new CameraGroupStrategy(camera));
		environment = new Environment();
		map = new MapLoader().loadJson("../core/assets/map.json", modelAssets, world);
		simulation = new MapSimulation(map);
		entityFactory = new EntityFactory(simulation, camera, world);
		player = new Player(bodyFactory.createPlayerBody(world), entityFactory);
		input = new UserInput(player);
		Gdx.input.setInputProcessor(input);
		lightManager = new LightManager(simulation, camera);
		for (BaseLight light : lightManager.lights) {
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
		lightManager.update();
	}
	
	public void renderAll() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(camera);
		for (ModelInstance instance : simulation.instances) {
			modelBatch.render(instance, environment);
		}
		modelBatch.end();
		for (Decal decal : simulation.decals) {
			decal.lookAt(camera.position, camera.up);
			decalBatch.add(decal);
		}
		for (MagicExplosion me : simulation.explosions) {
			for (ExplosionParticle ep : me.particles) {
				ep.decal.lookAt(camera.position, camera.up);
				decalBatch.add(ep.decal);
			}
		}
		decalBatch.flush();
	}
	
	public void updateEntities() {
		for (Entity entity : simulation.entities) {
			if (entity.getState() == EntityState.ALIVE) {
				entity.update();
			} else {
				simulation.lightHolders.remove(entity.getLightHolder());
				simulation.decals.remove(entity.getDecal());
			}
		}
		for (MagicExplosion me : simulation.explosions) {
			for (ExplosionParticle ep : me.particles) {
				ep.update();
			}
			me.update();
		}
		Iterator<MagicExplosion> mei = simulation.explosions.iterator();
		while (mei.hasNext()) {
			MagicExplosion me = mei.next();
			if (!me.alive) {
				simulation.lightHolders.remove(me.lightHolder);
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
					entityFactory.createExplosion(data, new Vector3(1.0f,0.1f,0.1f));
				} else {
					entityFactory.createExplosion(data, new Vector3(1.0f,1.0f,1.0f));
				}
				world.destroyBody(body);
			}
		}
	}

	

	private void updateCamera() {
		Vector2 pos = player.body.getPosition();
		float angle = player.body.getAngle();
		camera.position.set(new Vector3(pos.x, 0, pos.y));
		camera.direction.set(new Vector3((float) Math.cos(angle), 0f, (float) Math.sin(angle)));
		camera.update();
	}
	
	@Override
	public void dispose() {
		modelBatch.dispose();
		simulation.dispose();
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
