package com.goodeen.model;

import java.util.List;

import com.goodeen.enums.CommentLevel;

import lombok.Getter;
import lombok.Setter;

/**
 * 评论实体类，数据结构类似贴吧和QQ空间评论的结构。
 * @author peter.e.king
 */
@Getter
@Setter
public class Comment<T> extends BaseModel<Integer> {
  private static final long serialVersionUID = 8916324974724168495L;
  /** 对谁评论 **/
  private User upUser;
  /** 评论内容 **/
  private String content;
  /** 评论层级，这里最多只有两层 **/
  private CommentLevel commentLevel;
  /** 楼主 **/
  private T landLord;
  /** 层主，如果是一级评论，此值为自己 **/
  private Comment<T> storeyLord;
  /** 评论人 **/
  private User user;
  /** 子评论 **/
  private List<Comment<T>> subComments;
}
