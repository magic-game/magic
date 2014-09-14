package com.sean.game.magic;

public enum EventType {

	NONE(CostItem.NONE),
	COLLISION(CostItem.BLUE_ORB),
	INIT(CostItem.RED_CRYSTAL),
	CREATE(CostItem.GREEN_SKULL),
	DELAY(CostItem.NONE);
	
	public CostItem costItem;
	
	private EventType(CostItem costItem) {
		this.costItem = costItem;
	}
	
	
}
