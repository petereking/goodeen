package com.goodeen.model;

import java.io.Serializable;

import com.goodeen.enums.OverlayColor;
import com.goodeen.enums.Position;

import lombok.Data;

@Data
public class Theme implements Serializable {
	private static final long serialVersionUID = 3218480250423326111L;
  private Integer id = 1;
	private String backgroundImage = "/images/themes/theme1/bg.png";
	private Boolean backgroundTiled = false;
	private Position backgroundPosition = Position.LEFT;
	private String backgroundColor = "#C6E2EE";
	private String linkColor = "#1F98C7";
	private OverlayColor overlayColor = OverlayColor.WHITE;

	public Theme(Integer id, String backgroundImage, Boolean backgroundTiled,
			String backgroundColor, String linkColor) {
		super();
		this.id = id;
		this.backgroundImage = backgroundImage;
		this.backgroundTiled = backgroundTiled;
		this.backgroundColor = backgroundColor;
		this.linkColor = linkColor;
	}
}
