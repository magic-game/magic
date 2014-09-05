package com.sean.game.magic;

import java.util.List;

import com.sean.game.magic.actions.CreateMagicBallAction;
import com.sean.game.magic.actions.HurtPersonAction;
import com.sean.game.magic.actions.ImpulseEntityAction;

public enum ActionType {
	CREATE_MAGIC_BALL {
		@Override
		public Action getAction(List<Param> params) {
			return new CreateMagicBallAction(params);
		}
	},
	IMPULSE_ENTITY {
		@Override
		public Action getAction(List<Param> params) {
			return new ImpulseEntityAction();
		}
	},
	HURT_PERSON {
		@Override
		public Action getAction(List<Param> params) {
			return new HurtPersonAction(1);
		}
	};
	
	public abstract Action getAction(List<Param> params);
}
