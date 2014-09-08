package com.sean.game.magic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
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
			spellTemplates.add(createSpellTemplate(templateMappings));
		}
		
		return spellTemplates;
	}
	
	public static SpellTemplate createSpellTemplate(SpellTemplateMapping templateMappings) {
		List<EventActionStep> steps = new ArrayList<EventActionStep>();
		for (EventActionMapping eventMapping : templateMappings.eventActionMappings) {
			steps.add(new EventActionStep(EventType.valueOf(eventMapping.eventType), ActionType.valueOf(eventMapping.actionType), getParams(eventMapping.paramMappings)));
		}
		return new SpellTemplate(steps, templateMappings.name, templateMappings.description);
	}
	
	private static List<Param> getParams(List<ParamMapping> paramMappings) {
		List<Param> params = new ArrayList<Param>();
		if (params != null) {
			for (ParamMapping paramMapping : paramMappings) {
				params.add(new Param(paramMapping.name, paramMapping.value, paramMapping.type));
			}
		}
		return params;
	}
	
	public static void saveSpellTemplates(List<SpellTemplate> templates, String location) {
		
		
		Json json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		json.setIgnoreUnknownFields(true);
		json.setOutputType(OutputType.json);
		
		UserMapping userMapping = new UserMapping();
		
		List<SpellTemplateMapping> mappings = new ArrayList<SpellTemplateMapping>();
		for (SpellTemplate spellTemplate : templates) {
			SpellTemplateMapping spellTemplateMapping = new SpellTemplateMapping();
			spellTemplateMapping.description = spellTemplate.description;
			spellTemplateMapping.name = spellTemplate.name;
			spellTemplateMapping.eventActionMappings = getEventActionMappings(spellTemplate);
			mappings.add(spellTemplateMapping);
		}
		userMapping.spellTemplateMappings = mappings;
		json.toJson(userMapping, new FileHandle(new File(location)));
	}
	
	private static List<EventActionMapping> getEventActionMappings(SpellTemplate spellTemplate) {
		List<EventActionMapping> eventActionMappings = new ArrayList<EventActionMapping>();
		for (EventActionStep step : spellTemplate.steps) {
			EventActionMapping mapping = new EventActionMapping();
			mapping.actionType = step.actionType.toString();
			mapping.eventType = step.eventType.toString();
			mapping.paramMappings = getParams(step);	
			eventActionMappings.add(mapping);
		}
		return eventActionMappings;
	}
	
	private static List<ParamMapping> getParams(EventActionStep step) {
		List<ParamMapping> params = new ArrayList<ParamMapping>();
		for (Param param : step.params) {
			ParamMapping paramMapping = new ParamMapping();
			paramMapping.name = param.name;
			paramMapping.type = param.type;
			paramMapping.value = param.value;
			params.add(paramMapping);
		}
		return params;
	}
}
