package com.sean.game.magic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.sean.game.magic.json.EventActionMapping;
import com.sean.game.magic.json.ParamMapping;
import com.sean.game.magic.json.SpellTemplateMapping;
import com.sean.game.magic.json.UserMapping;

public class SpellTemplateLoader {

	public static List<SpellTemplate> loadSpellTemplatesFile(String location) {
		List<SpellTemplate> spellTemplates = new ArrayList<SpellTemplate>();
		
		String line = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(location));
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
		
		UserMapping userMapping = json.fromJson(UserMapping.class, line);
		
		for (SpellTemplateMapping templateMappings : userMapping.spellTemplateMappings) {
			List<EventActionStep> steps = new ArrayList<EventActionStep>();
			for (EventActionMapping eventMapping : templateMappings.eventActionMappings) {
				steps.add(new EventActionStep(EventType.valueOf(eventMapping.eventType), ActionType.valueOf(eventMapping.actionType), getParams(eventMapping.paramMappings)));
			}
			spellTemplates.add(new SpellTemplate(steps));
		}
		
		return spellTemplates;
	}
	
	private static List<Param> getParams(List<ParamMapping> paramMappings) {
		List<Param> params = new ArrayList<Param>();
		for (ParamMapping paramMapping : paramMappings) {
			params.add(new Param(paramMapping.name, paramMapping.value, paramMapping.type));
		}
		return params;
	}
	
//	public SpellTemplate getSpellTemplate() {
//		List<Param> params = new ArrayList<Param>();
//		params.add(new Param("TTL", "0.8", "Float"));
//		SpellTemplate st = new SpellTemplate();
//		st.steps.add(new EventActionStep(EventType.INIT, ActionType.CREATE_MAGIC_BALL, params));
//		st.steps.add(new EventActionStep(EventType.CREATE, ActionType.IMPULSE_ENTITY, params));
//		st.steps.add(new EventActionStep(EventType.COLLISION, ActionType.HURT_PERSON, params));
//		
//		return st;
//	}
}
