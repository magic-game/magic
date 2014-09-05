package com.sean.game;

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
import com.sean.game.entity.Player;
import com.sean.game.factory.BodyFactory;
import com.sean.game.factory.CameraFactory;
import com.sean.game.factory.FactoryFacade;
import com.sean.game.factory.ModelInstanceFactory;
import com.sean.game.level.BasicMap;
import com.sean.game.level.MapLoader;
import com.sean.game.ui.CraftMenuUserInterface;
import com.sean.game.ui.InventoryIcon;
import com.sean.game.ui.FirstPersonUserInterfaceManager;

public class MagicGame implements ApplicationListener {

	private World world;
	public Environment environment;
	public PerspectiveCamera camera;
	public UserInput input;
	public ModelBatch modelBatch;
	public ModelInstanceFactory modelAssets;
	DecalBatch decalBatch;
	BasicMap map;
	Player player;
	BodyFactory bodyFactory;
	public Shader shader;
	float spinner;
	public MapSimulation simulation;
	public FactoryFacade entityFactory;
	public LightManager lightManager;
	public FirstPersonUserInterfaceManager userInterfaceManager;
	public CraftMenuUserInterface craftUI;
	public GamePlay gamePlay;
	
	@Override
	public void create() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new CollisionContactListener());
		modelBatch = new ModelBatch();
		modelAssets = new ModelInstanceFactory();
		bodyFactory = new BodyFactory();
		camera = CameraFactory.createCamera(new Vector3(4, 0, 4));
		camera.lookAt(10, 0, 4);
		decalBatch = new DecalBatch();
		decalBatch.setGroupStrategy(new CameraGroupStrategy(camera));
		environment = new Environment();
		map = new MapLoader().loadJson("../core/assets/map.json", modelAssets, world);
		simulation = new MapSimulation(map);
		entityFactory = new FactoryFacade(simulation, world);
		player = new Player(entityFactory, camera.position.cpy(), camera.direction.cpy());
		input = new UserInput(player, this);
		Gdx.input.setInputProcessor(input);
		lightManager = new LightManager(simulation, camera);
		for (BaseLight light : lightManager.lights) {
			environment.add(light);
		}
		userInterfaceManager = new FirstPersonUserInterfaceManager(player);
		userInterfaceManager.icons.add(new InventoryIcon("fireballitem.png"));
		gamePlay = GamePlay.FIRST_PERSON;
		craftUI = new CraftMenuUserInterface(this);
	}

	@Override
	public void render() {
		if (gamePlay == GamePlay.FIRST_PERSON) {
			updateAll();
		}
		renderAll();
		if (gamePlay == GamePlay.CRAFT_MENU) {
			craftUI.render();
		}
	}

	public void setGamePlay(GamePlay type) {
		if (type == GamePlay.CRAFT_MENU) {
			gamePlay = type;
			Gdx.input.setInputProcessor(craftUI.stage);
		}
		if (type == GamePlay.FIRST_PERSON) {
			gamePlay = type;
			Gdx.input.setInputProcessor(input);
		}
	}
	
	public void updateAll() {
		simulation.update();
		userInterfaceManager.update();
		player.entity.update();
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
		userInterfaceManager.render();
	}

	public void cleanUpBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			Entity data = (Entity) body.getUserData();
			if (data != null && data.getState() == EntityState.DEAD) {
				if (data instanceof PersonEntity) {
					entityFactory.createExplosion(data, new Vector3(1.0f, 0.1f, 0.1f));
				} else {
					entityFactory.createExplosion(data, new Vector3(1.0f, 1.0f, 1.0f));
				}
				world.destroyBody(body);
			}
		}
	}

	private void updateCamera() {
		Vector3 pos = player.entity.getPosition();
		float angle = player.entity.getBody().getAngle();
		camera.position.set(new Vector3(pos.x, 0, pos.z));
		camera.direction.set(new Vector3((float) Math.cos(angle), 0f, (float) Math.sin(angle)));
		camera.update();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		simulation.dispose();
		decalBatch.dispose();
		craftUI.dispose();
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
