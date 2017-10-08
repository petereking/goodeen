package com.goodeen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.goodeen.enums.Transport;
import com.goodeen.enums.TripMode;

import lombok.Getter;
import lombok.Setter;

/**
 * 出行实体类
 * @author peter.e.king
 */
@Getter
@Setter
public class Trip extends BaseModel<Integer> {
	private static final long serialVersionUID = 7360083060666648499L;
  /** 主题 **/
	private String title;
	/** 封面 **/
	private String cover;
	/** 标签 **/
	private String tags;
	/** 出发地 **/
	private Region departure;
	/** 目的地 **/
	private Region destination;
	/** 出发时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date departureTime = new Date();
	/** 抵达时间 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date arrivalTime = new Date();
	/** 交通工具 **/
	private Transport transport;
	/** 出行方式 **/
	private TripMode tripMode;
	/** 参加人数 **/
	private Integer plusedStat = 0;
	/** 收藏人数 **/
	private Integer favoritedStat = 0;
	/** 评论数 **/
	private Integer commentStat = 0;
	
	// 以下是关联数据
	/** 出行人 **/
	private User user;
	/** 是否已经加入 **/
	private Boolean plused = false;	
	/** 是否已经收藏 **/
	private Boolean favorited = false;	
	/** 参与者 **/
	private Set<Participant> participants;
	/** 参加的人员列表 **/
	private List<User> plusedUsers = new ArrayList<User>();
	/** 收藏的人员列表 **/
	private List<User> favoritedUsers = new ArrayList<User>();
	/** 评论 **/
	private List<Comment<Trip>> comments = new ArrayList<Comment<Trip>>();
}
