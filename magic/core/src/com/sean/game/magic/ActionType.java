package com.sean.game.magic;

import java.util.ArrayList;
import java.util.List;

import com.sean.game.magic.actions.CreateMagicBallAction;
import com.sean.game.magic.actions.HurtPersonAction;
import com.sean.game.magic.actions.ImpulseEntityAction;

public enum ActionType {
	
	NONE {
		@Override
		public Action getAction(List<Param> params) {
			return null;
		}
		@Override
		public List<Param> getParams() {
			return null;
		}
	},
	CREATE_BALL {
		@Override
		public Action getAction(List<Param> params) {
			return new CreateMagicBallAction(params);
		}
		@Override
		public List<Param> getParams() {
			List<Param> params = new ArrayList<Param>();
			params.add(new Param("TTL", "0", "Float"));
			params.add(new Param("size", "0.18", "Float"));
			return params;
		}
	},
	IMPULSE {
		@Override
		public Action getAction(List<Param> params) {
			return new ImpulseEntityAction(params);
		}
		@Override
		public List<Param> getParams() {
			List<Param> params = new ArrayList<Param>();
			params.add(new Param("speed", "0.1f", "Float"));
			return params;
		}
	},
	HURT {
		@Override
		public Action getAction(List<Param> params) {
			return new HurtPersonAction(params);
		}
		@Override
		public List<Param> getParams() {
			List<Param> params = new ArrayList<Param>();
			params.add(new Param("damage", "1", "Int"));
			return params;
		}
	};
	
	public abstract Action getAction(List<Param> params);
	public abstract List<Param> getParams();
}
