package com.sean.game.factory;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.UBJsonReader;

public class ModelInstanceFactory {

	public Model wallModel;
	public Model floorModel;
	public Model ceilingModel;
	public Model magicModel;
	public Model blenderWallModel;

	float s = 1.0f;
	float hs = 0.5f;
	float qs = 0.10f;

	Map<String, Texture> textures = new HashMap<String, Texture>();

	public ModelInstanceFactory() {
		ModelBuilder modelBuilder = new ModelBuilder();
		wallModel = modelBuilder.createBox(s, s, s, new Material(ColorAttribute.createDiffuse(Color.WHITE)), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		floorModel = modelBuilder.createRect(-hs, -hs, -hs, -hs, -hs, hs, hs, -hs, hs, hs, -hs, -hs, 0, 1, 0, new Material(ColorAttribute.createDiffuse(Color.WHITE)), Usage.Position | Usage.Normal
				| Usage.TextureCoordinates);
		ceilingModel = modelBuilder.createRect(-hs, hs, -hs, hs, hs, -hs, hs, hs, hs, -hs, hs, hs, 0, -1, 0, new Material(ColorAttribute.createDiffuse(Color.WHITE)), Usage.Position | Usage.Normal
				| Usage.TextureCoordinates);
		magicModel = modelBuilder.createRect(-qs, -qs, 0, -qs, qs, 0, qs, qs, 0, qs, -qs, 0, 0, 0, 1,
				new Material(IntAttribute.createCullFace(GL20.GL_NONE), ColorAttribute.createDiffuse(new Color(1.0f, 1.0f, 1.0f, 0.5f)), new BlendingAttribute(GL20.GL_SRC_ALPHA,
						GL20.GL_ONE_MINUS_SRC_ALPHA)), Usage.Position | Usage.TextureCoordinates);

		UBJsonReader jsonReader = new UBJsonReader();
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		blenderWallModel = modelLoader.loadModel(Gdx.files.getFileHandle("wall.g3db", FileType.Internal));
	}

	public ModelInstance getFloorInstance(String texture) {
		ModelInstance mi = new ModelInstance(floorModel);
		mi.materials.get(0).set(TextureAttribute.createDiffuse(getTexture(texture)));
		return mi;
	}

	public ModelInstance getCeilingInstance(String texture) {
		ModelInstance mi = new ModelInstance(ceilingModel);
		mi.materials.get(0).set(TextureAttribute.createDiffuse(getTexture(texture)));
		return mi;
	}

	public ModelInstance getWallInstance(String texture) {
		ModelInstance mi = new ModelInstance(blenderWallModel);
		mi.materials.get(0).set(TextureAttribute.createDiffuse(getTexture(texture)));
		return mi;
	}

	public ModelInstance getMagicInstance(String texture) {
		ModelInstance mi = new ModelInstance(magicModel);
		mi.materials.get(0).set(TextureAttribute.createDiffuse(getTexture(texture)));
		return mi;
	}

	public Texture getTexture(String location) {
		if (textures.containsKey(location)) {
			return textures.get(location);
		} else {
			Texture texture = new Texture(Gdx.files.internal(location));
			textures.put(location, texture);
			return texture;
		}
	}
}
