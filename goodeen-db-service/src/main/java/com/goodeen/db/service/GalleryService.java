package com.goodeen.db.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.goodeen.mapper.GalleryMapper;
import com.goodeen.model.Gallery;
import com.goodeen.model.Image;
import com.goodeen.utils.DateUtils;
import com.goodeen.utils.FileUtils;

@Service
public class GalleryService {
  @Value("${file.server.photo.path}")
  private String PHOTO_PATH;
  
  @Autowired
  private GalleryMapper galleryMapper;

  /**
   * 根据相册ID取得相册
   * 
   * @param id 相册ID
   * @return
   */
  public Gallery getById(Integer id) {
    return galleryMapper.getById(id);
  }

  /**
   * 根据『用户id』，取得该用户的所有相册,相册中包含用户的所有照片
   * 
   * @param userId 用户ID
   * @return
   */
  public List<Gallery> queryWithImagesByUserId(Integer userId) {
    List<Gallery> gallerys = galleryMapper.queryByUserId(userId);
    for (Gallery gallery : gallerys) {
      List<Image> images = galleryMapper.queryImagesById(gallery.getId());
      gallery.setImages(images);
    }
    return gallerys;
  }

  public Page<Image> pageImagesByGallery(Pageable pageable, Gallery gallery) {
    int pageNumber = pageable.getPageNumber();
    int pageSize = pageable.getPageSize();
    List<Image> content = galleryMapper.pageImagesByGallery(pageable, gallery);
    return new PageImpl(content, new PageRequest(pageNumber, pageSize), pageable.getPageSize());
  }

  /**
   * 添加一个相册
   * 
   * @param gallery
   * @return
   */
  public int createGallery(Gallery gallery) {
    return galleryMapper.create(gallery);
  }

  /**
   * 添加照片到相册,如果相册id为空，则先添加一个默认相册
   * @param gallery
   * @param photos
   * @return 返回添加的所以图片列表
   * @throws IOException
   */
  public List<Image> createPhoto4Gallery(Gallery gallery, MultipartFile[] photos) throws Exception {
    /** 是否是要新创建相册，后面根据这个值修改相册的图片数量 **/
    Boolean isNew = false;
    /** 如果相册ID为空或为『-1』，则创建一个默认相册，相册名为『商品相册』 **/
    if (gallery.getId() == null || gallery.getId() == -1) {
      List<Gallery> gallerys = galleryMapper.queryByUserId(gallery.getCreator());
      if (gallerys.size() > 0) {
        gallery = gallerys.get(0);
      } else {
        isNew = true;
        gallery.setCreator(gallery.getUpdator());
        gallery.setId(null);
        gallery.setImageCount(photos.length);
        if(StringUtils.isEmpty(gallery.getName())){
          gallery.setName("商品相册");
        }
        int galleryId = galleryMapper.create(gallery);
        gallery.setId(galleryId);
      }
    } else {
      gallery = galleryMapper.getById(gallery.getId());
    }
    /** 给相册增加图片 **/
    List<Image> images = new ArrayList<Image>();
    for (MultipartFile photo : photos) {
      String photoUrl = FileUtils.upload(photo, PHOTO_PATH);
      Image image = new Image();
      image.setGallery(gallery);
      image.setUrl(photoUrl.split(PHOTO_PATH)[1]);
      image.setCreator(gallery.getCreator());
      image.setCreateTime(DateUtils.getCurrentDate());
      galleryMapper.createImage(image);
      images.add(image);
    }
    /** 更新相册图片数量 **/
    if (!isNew) {
      gallery.setImageCount(gallery.getImageCount() + images.size());
      galleryMapper.updateImageCount(gallery);
    }
    /** 更新相册封面 **/
    if (gallery.getCover() != null) {
      Image coverImage = images.get(0);
      gallery.setCover(coverImage);
      galleryMapper.updateCover(gallery);
    }
    return images;
  }
}
