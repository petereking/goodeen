package com.goodeen.model;

import java.util.ArrayList;
import java.util.List;

import com.goodeen.enums.Relation;
import com.goodeen.enums.State;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 * @author peter.e.king
 */
@Getter
@Setter
public class User extends BaseModel<Integer> {
  private static final long serialVersionUID = 2991640060096296378L;

	// account
	private String email;
	private String screenName;
	private String password;
	private String key;
	private State state = State.UNACTIVATED;

	// profile
	private String image;
	private String headerImage;
	private String name;
	private String location;
	private String url;
	private Theme theme = new Theme(1, "/images/themes/theme1/bg.png", false, "#C6E2EE", "#1F98C7");

	// stat
	private Integer tripStat = 0;
	private Integer followingStat = 0;
	private Integer followerStat = 0;
	private List<User> following = new ArrayList<User>();
	private List<User> followers = new ArrayList<User>();
	private List<Trip> trips = new ArrayList<Trip>();
	private Relation relation;
}
