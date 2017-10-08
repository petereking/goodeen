package com.goodeen.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Permission implements Serializable {
  private static final long serialVersionUID = -1688252506014864685L;
  private Integer id;
  /** 资源名称 **/
  private String name;
  /** 当前是否有效。 **/
  private Boolean enabled = Boolean.TRUE;
  /** 编码，可以通过编码看出继承层次和级别 **/
  private String code;
  /** 层次级别 **/
  private Integer level;
  /** 父菜单 **/
  private Permission parent;
  /** 在父节点下面的排列顺序,避免用名『sort』，以防止MVC数据绑定出错 **/
  private Integer orderNumber;
  /** 是否是菜单。如果是菜单：1.会在菜单树中显示，2.可以建子节点。 **/
  private Boolean isLeaf = Boolean.TRUE;
  /** 菜单叶子节点URL **/
  private String url;
  /** 备注说明 **/
  private String summary;
}
