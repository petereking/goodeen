package com.goodeen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.goodeen.db.service.GalleryService;
import com.goodeen.model.Gallery;
import com.goodeen.model.Image;
import com.goodeen.model.User;

@Controller
@RequestMapping("/gallerys")
public class GalleryController {
  @Autowired
  private GalleryService galleryService;

  @GetMapping
  public ModelAndView gallerys(HttpSession session) {
    User currentUser = (User) session.getAttribute("currentUser");
    return this.gallerys(session, currentUser.getId());
  }

  @GetMapping(value = "/user/{userId:\\d{1,10}}")
  public ModelAndView gallerys(HttpSession session, @PathVariable Integer userId) {
    ModelMap map = new ModelMap();
    List<Gallery> gallerys = galleryService.queryWithImagesByUserId(userId);
    map.put("gallerys", gallerys);
    Boolean isOwner = false;
    User currentUser = (User) session.getAttribute("currentUser");
    if (userId != null && currentUser != null) {
      isOwner = currentUser.getId().equals(userId);
    }
    map.put("isOwner", isOwner);
    return new ModelAndView("gallerys/list", map);
  }

  /**
   * 获取指定相册下的所有图片列表
   * 
   * @param galleryId
   * @return
   */
  @GetMapping(value = "/{galleryId:\\d{1,10}}/images")
  public ModelAndView galleryImages(HttpSession session, @PathVariable Integer galleryId,
      @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
      @RequestParam(value = "size", defaultValue = "20") Integer size) {
    Pageable pageable = new PageRequest(pageNumber - 1, size);
    Gallery gallery = galleryService.getById(galleryId);
    ModelMap map = new ModelMap();
    map.put("page", pageable.getPageNumber() + 1);
    map.put("size", pageable.getPageSize());
    map.put("total", 0);
    if (gallery != null) {
      Page<Image> page = galleryService.pageImagesByGallery(pageable, gallery);
      map.put("gallery", gallery);
      map.put("images", page.getContent());
      map.put("total", page.getTotalPages());
      User currentUser = (User) session.getAttribute("currentUser");
      map.put("isOwner", currentUser.getId() == gallery.getCreator());
    }
    return new ModelAndView("gallerys/images-list", map);
  }

  /**
   * 增加一个相册
   * 
   * @param gallery
   * @return
   */
  @ResponseBody
  @PostMapping(value = "/createGallery")
  public Gallery createGallery(HttpSession session, Gallery gallery) {
    Integer userId = ((User) session.getAttribute("currentUser")).getId();
    gallery.setCreator(userId);
    gallery.setImageCount(0);
    galleryService.createGallery(gallery);
    return gallery;
  }

  /**
   * 往指定相册上传照片
   * 
   * @param photos 图片文件
   * @param gallery 图片所属相册
   * @return
   * @throws IOException
   */
  @ResponseBody
  @PostMapping(value = "/createPhoto4Gallery")
  public List<Image> createPhoto4Gallery(HttpSession session, MultipartFile[] photos,
      Gallery gallery) throws Exception {
    Integer userId = ((User) session.getAttribute("currentUser")).getId();
    gallery.setUpdator(userId);
    List<Image> images = galleryService.createPhoto4Gallery(gallery, photos);
    return images;
  }
}
