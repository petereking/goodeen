package com.goodeen.solr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.goodeen.solr.model.TripCore;
import com.goodeen.solr.model.UserCore;

@Mapper
public interface IndexMapper {
	public List<UserCore> queryForUsers(@Param("minutes") Integer minutes);

	public List<TripCore> queryForTrips(@Param("minutes") Integer minutes);
}