package com.goodeen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodeen.model.Comment;
import com.goodeen.model.Trip;

@Mapper
public interface TripCommentMapper extends BaseMapper<Integer, Comment<Trip>> {
  public List<Comment<Trip>> query(Comment<Trip> comment);
}
