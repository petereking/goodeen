package com.goodeen.enums;

public enum TripMode {
	SELF("独自"), GROUP("组队"), DEPENDS("随意");
	
	private String display;

	TripMode(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}
}