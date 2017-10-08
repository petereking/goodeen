package com.goodeen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import com.goodeen.enums.Relation;
import com.goodeen.model.Permission;
import com.goodeen.model.Tag;
import com.goodeen.model.Trip;
import com.goodeen.model.User;

@Mapper
public interface UserMapper extends BaseMapper<Integer, User> {
	public void permissionRole(@Param("user") Integer user, @Param("role") Integer role);

	public User getByEmail(String email);

	public int activate(String key);

	public int updateAccount(User user);

	public User getByScreenName(String screenName);

	public List<Tag> getTagsById(Integer id);

	public Relation getRelation(@Param("user") User user, @Param("follower") User follower);

	public int getFollowerStat(User user);

	public int getFollowingStat(User user);

	public void follow(@Param("user") User user, @Param("follower") User follower);

	public void unFollow(@Param("user") User user, @Param("follower") User follower);

	public void setFollowStat(User user);

	public List<Integer> pageFollowers(@Param("pageable") Pageable pageable, @Param("user") User user);

	public List<Integer> pageFollowing(@Param("pageable") Pageable pageable, @Param("user") User user);
	
	public List<Trip> pageTrips(@Param("pageable") Pageable pageable, @Param("user") User user);
	
	public List<Trip> pageFollowingTrips(@Param("pageable") Pageable pageable, @Param("user") User user);
	
	public User getByKey(String key);

	public void updateDesign(User user);

	public void updateProfile(User user);

	public List<User> queryByIdsWithRelation(@Param("currentUser") User currentUser,@Param("ids") List<Integer> ids);
	
  public List<String> queryRoleCodes(String email);
  
  public List<Permission> queryAllPermissions();
  
  public List<String> queryPermissionCodes(String email);

  public int createForgetPasswordRequest(User user);

  public User getByForgetPasswordKey(String key);

  public List<User> queryLastestUsers(@Param("currentUser") User currentUser);
}
