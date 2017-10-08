package com.goodeen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.goodeen.model.Trip;
import com.goodeen.model.User;

@Mapper
public interface TripMapper extends BaseMapper<Integer, Trip> {
	public Boolean isPlused(@Param("trip") Trip trip, @Param("user") User user);
	
	public Boolean isFavorited(@Param("trip") Trip trip, @Param("user") User user);
	
	public int getCommentStat(Trip trip);

	public int getPlusedStat(Trip trip);

	public int getFavoritedStat(Trip trip);
	
	public void setCommentStat(Trip trip);
	
	public void setPlusedStat(Trip trip);
	
	public void setFavoritedStat(Trip trip);
	
	public Integer plus(@Param("trip") Trip trip, @Param("user") User user);

	public Integer unPlus(@Param("trip") Trip trip, @Param("user") User user);

	public Integer favorite(@Param("trip") Trip trip, @Param("user") User user);

	public Integer unFavorite(@Param("trip") Trip trip, @Param("user") User user);
}
