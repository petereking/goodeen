package com.goodeen.enums;


public enum Transport {
	WALK("徒步"), BICYCLE("自行车"), CAR("自驾"), PUBLIC("公共交通"), MIXED_TRAFFIC("混合交通工具");
	private String display;

	Transport(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}
}