package com.goodeen.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goodeen.enums.Relation;
import com.goodeen.enums.State;
import com.goodeen.exception.NotFoundException;
import com.goodeen.mapper.UserMapper;
import com.goodeen.model.Permission;
import com.goodeen.model.Trip;
import com.goodeen.model.User;

@Service("userService")
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public User create(User user) {
		userMapper.create(user);
		userMapper.permissionRole(user.getId(), 1);
		return user;
	}

	public Boolean screenNameAvailable(String screenName) {
		return userMapper.getByScreenName(screenName) == null;
	}

	public void activate(String key) {
		userMapper.activate(key);
	}

	public User getByEmail(String email) {
		return userMapper.getByEmail(email);
	}

	public User updateAccount(User user) {
		userMapper.updateAccount(user);
		return user;
	}

	public int follow(User user, User follower) {
		if (userMapper.getRelation(user, follower) == Relation.NOT_FOLLOWING) {
			userMapper.follow(user, follower);
			setFollowerStat(user);
			setFollowerStat(follower);
		}
		return userMapper.getFollowerStat(user);
	}

	public int unFollow(User user, User follower) {
		if (userMapper.getRelation(user, follower) == Relation.FOLLOWING) {
			userMapper.unFollow(user, follower);
			setFollowerStat(user);
			setFollowerStat(follower);
		}
		return userMapper.getFollowerStat(user);
	}

	private void setFollowerStat(User user) {
		int followerStat = userMapper.getFollowerStat(user);
		int followingStat = userMapper.getFollowingStat(user);
		user.setFollowerStat(followerStat);
		user.setFollowingStat(followingStat);
		userMapper.setFollowStat(user);
	}

	private User getByScreenName(String screenName) throws NotFoundException {
		User user = userMapper.getByScreenName(screenName);
		if (user == null) {
			throw new NotFoundException(screenName + "不存在！");
		} else {
			return user;
		}
	}

	public User getUserWithRelation(Boolean isWithRelation, User currentUser,
			String screenName) throws NotFoundException {
		User user = getByScreenName(screenName);
		if (isWithRelation) {
			user.setRelation(userMapper.getRelation(user, currentUser));
		}
		return user;
	}
	
	public void trips(Pageable pageable, User user) throws NotFoundException {
	  List<Trip> trips = userMapper.pageTrips(pageable, user);
	  user.setTrips(trips);
	}
	
	public List<Trip> followingTrips(Pageable pageable, User user) throws NotFoundException {
	  return userMapper.pageFollowingTrips(pageable, user);
	}

	public void following(Pageable pageable, User currentUser, User user)
			throws NotFoundException {
		List<Integer> ids = userMapper.pageFollowing(pageable, user);
		if (ids.size() > 0) {
			user.setFollowing(userMapper.queryByIdsWithRelation(currentUser, ids));
		}
	}

	public void followers(Pageable pageable, User currentUser, User user)
			throws NotFoundException {
		List<Integer> ids = userMapper.pageFollowers(pageable, user);
		if (ids.size() > 0) {
			user.setFollowers(userMapper.queryByIdsWithRelation(currentUser, ids));
		}
	}
	
	public Boolean isActivated(String key) {
		User user = userMapper.getByKey(key);
		return user != null && user.getState() == State.ACTIVATED;
	}

	public void updateDesign(User user) {
		userMapper.updateDesign(user);
	}

	public void updateSecurity(User user) {

	}

	public void updateNotifications(User user) {

	}

	public void updateProfile(User user) {
		userMapper.updateProfile(user);
	}
	
  public List<String> queryRoleCodes(String username) {
    return userMapper.queryRoleCodes(username);
  }

  public List<Permission> queryAllPermissions() {
    return userMapper.queryAllPermissions();
  }
  
  public List<String> queryPermissionCodes(String username) {
    return userMapper.queryPermissionCodes(username);
  }

  public void createForgetPasswordRequest(User user) {
    userMapper.createForgetPasswordRequest(user);
  }

  public User getByForgetPasswordKey(String key) {
    return userMapper.getByForgetPasswordKey(key);
  }

  public List<User> queryLastestUsers(User currentUser) {
    return userMapper.queryLastestUsers(currentUser);
  }
}
