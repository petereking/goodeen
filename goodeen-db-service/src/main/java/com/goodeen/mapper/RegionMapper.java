package com.goodeen.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodeen.model.Region;

@Mapper
public interface RegionMapper extends BaseMapper<Integer, Region> {
	public int batchCreate(List<Region> regionList);

	public int batchUpdate(List<Region> regionList);

	public List<Region> queryThreeLevelRegions();
	
	public List<Map> queryRegionsJson();
}
