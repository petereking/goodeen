package com.goodeen.enums;

/**
 * @author peter.e.king
 * 本系统其实只用了两个评论层级：
 * LANDLORD(楼主)是占位符，STOREYLORD是层主(第一层评论)，ROOMLORD是第二层评论
 */
public enum CommentLevel {
	LANDLORD, STOREYLORD, ROOMLORD;
}
