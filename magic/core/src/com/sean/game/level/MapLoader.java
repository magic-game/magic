package com.sean.game.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.sean.game.BodyFactory;
import com.sean.game.LightHolder;
import com.sean.game.MagicConstants;
import com.sean.game.ModelAssets;
import com.sean.game.level.json.MapTile;
import com.sean.game.level.json.Mappings;
import com.sean.game.level.json.Row;
import com.sean.game.level.json.Template;


public class MapLoader {

	static int MAX_LEVEL_SIZE = 100;
	
	public BasicMap loadJson(String resource, ModelAssets modelAssets, World world) {
		BodyFactory bodyFactory = new BodyFactory();
		
		List<Tile> tiles = new ArrayList<Tile>();
		String line = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(resource));
			while (br.ready()) {
				line = line + br.readLine();				
			}
		} catch (Exception e ) {
			
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		
		Json json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		json.setIgnoreUnknownFields(true);
		json.setOutputType(OutputType.json);
		
		Mappings mappings = json.fromJson(Mappings.class, line);
		
		Map<Integer, Template> templates = new HashMap<Integer, Template>();
		for (Template template : mappings.templates) {
			templates.put(template.value, template);
		}
		
		int j = 0;
		for (Row row : mappings.rows) {
			for (int i = 0; i < mappings.width; i++) {
				Template template = templates.get(row.values[i]);
				Tile tile = new Tile();
				tile.pos = new Vector3((j * MagicConstants.UNIT),0, (i * MagicConstants.UNIT) );
				tile.blocking = template.isSolid;
				for (MapTile mapTile : template.mapTiles) {
					if (mapTile.model.equals("Ceiling")) {
						tile.models.add(modelAssets.getCeilingInstance(mapTile.texture));
					}
					if (mapTile.model.equals("Floor")) {
						tile.models.add(modelAssets.getFloorInstance(mapTile.texture));
					}
					if (mapTile.model.equals("Wall")) {
						tile.models.add(modelAssets.getWallInstance(mapTile.texture));
						bodyFactory.createWallBody(world, tile.pos);
					}
				}
				if (template.hasLight) {
					LightHolder lightHolder = new LightHolder(new Vector3((float) Math.random(), (float) Math.random(), (float) Math.random()), new Vector3(tile.pos.x, tile.pos.y, tile.pos.z), 4.0f);
					tile.lightHolder = lightHolder;
				}
				tile.updatePosition();
				tiles.add(tile);
			}
			j++;
		}
		List<Level> levels = new ArrayList<Level>();
		levels.add(new Level(tiles));
		return new BasicMap(levels);
	}
}
