package com.sean.game.magic;

import java.util.List;

public class ParamManager {
	
	public static Float getFloatParam(String paramName, List<Param> params) {
		Param foundParam = null;
		for (Param param : params) {
			if (param.name.equals(paramName)) {
				foundParam = param;
				break;
			}
		}
		if (foundParam == null) {
			System.out.println("EERORR");
		}
		return Float.valueOf(foundParam.value);
	}
}
