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
			return params;
		}
	},
	IMPULSE {
		@Override
		public Action getAction(List<Param> params) {
			return new ImpulseEntityAction();
		}
		@Override
		public List<Param> getParams() {
			return null;
		}
	},
	HURT {
		@Override
		public Action getAction(List<Param> params) {
			return new HurtPersonAction(1);
		}
		@Override
		public List<Param> getParams() {
			return null;
		}
	};
	
	public abstract Action getAction(List<Param> params);
	public abstract List<Param> getParams();
}
