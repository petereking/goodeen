package com.goodeen.db.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodeen.mapper.RegionMapper;
import com.goodeen.model.Region;

@Service("regionService")
public class RegionService {
	@Autowired
	private RegionMapper regionMapper;

	public Region create(Region region) {
		regionMapper.create(region);
		return region;
	}
	
	public int batchCreate(List<Region> regionList) {
		return regionMapper.batchCreate(regionList);
	}
	
	public int batchUpdate(List<Region> regionList) {
		return regionMapper.batchUpdate(regionList);
	}
	
	public List<Map> queryRegionsJson() {
		return regionMapper.queryRegionsJson();
	}	

	public List<Region> queryThreeLevelRegions() {
		return regionMapper.queryThreeLevelRegions();
	}	
}
