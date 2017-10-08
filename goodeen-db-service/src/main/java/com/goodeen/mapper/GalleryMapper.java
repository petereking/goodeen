package com.goodeen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.goodeen.model.Gallery;
import com.goodeen.model.Image;

@Mapper
public interface GalleryMapper extends BaseMapper<Integer, Gallery> {
  public List<Gallery> queryByUserId(Integer userId);

  public List<Image> queryImagesById(Integer id);

  public List<Image> pageImagesByGallery(@Param("pageable") Pageable pageable, @Param("t") Gallery gallery);
  
  public int createImage(Image image);

  public int updateImageCount(Gallery gallery);

  public int updateCover(Gallery gallery);
}
