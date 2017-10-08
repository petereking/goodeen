package com.goodeen.enums;

public enum Relation {
	SELF("self"), FOLLOWING("following"), NOT_FOLLOWING("not-following");
	
	private final String name;

	private Relation(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}