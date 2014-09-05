package com.sean.game.magic;

import com.sean.game.entity.Entity;

public class Event {
	
	private EventType type;
	private Entity source;
	
	public Event(EventType type, Entity source) {
		this.type = type;
		this.source = source;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
