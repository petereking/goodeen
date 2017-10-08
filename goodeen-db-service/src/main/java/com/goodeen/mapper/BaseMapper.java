package com.goodeen.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

public interface BaseMapper<K extends Serializable,T> {
  public T getById(K id);
  
  public List<T> query(T t);
  
  public List<T> queryByIds(@Param("ids") List<K> ids);
  
  public List<T> page(@Param("page") Pageable page, @Param("t") T model);
  
  public int create(T t);
  
  public int update(T t);
  
  public int delete(K id);
}
