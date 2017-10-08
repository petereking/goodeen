package com.goodeen.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodeen.mapper.TripCommentMapper;
import com.goodeen.mapper.TripMapper;
import com.goodeen.model.Comment;
import com.goodeen.model.Trip;
import com.goodeen.model.User;

@Service("tripService")
public class TripService {
	@Autowired
	private TripMapper tripMapper;
	@Autowired
	private TripCommentMapper tripCommentMapper;

	public Trip create(Trip trip) {
		tripMapper.create(trip);
		return trip;
	}
	
	public Trip getTripWithCommentsMap(Integer id, User user) {
		Trip trip = tripMapper.getById(id);
		Comment<Trip> comment = new Comment<Trip>();
		comment.setLandLord(trip);
    List<Comment<Trip>> comments = tripCommentMapper.query(comment);
    trip.setComments(comments);
		trip.setFavorited(tripMapper.isFavorited(trip, user));
		trip.setPlused(tripMapper.isPlused(trip, user));
		return trip;
	}
	
	public Integer plus(Trip trip, User user) {
		if (!tripMapper.isPlused(trip, user)) {
			tripMapper.plus(trip, user);
			Integer plusedStat = tripMapper.getPlusedStat(trip);
			trip.setPlusedStat(plusedStat);
			tripMapper.setPlusedStat(trip);
			return plusedStat;
		}
		return tripMapper.getPlusedStat(trip);
	}	

	public Integer unPlus(Trip trip, User user) {
		if (tripMapper.isPlused(trip, user)) {
			tripMapper.unPlus(trip, user);
			Integer plusedStat = tripMapper.getPlusedStat(trip);
			trip.setPlusedStat(plusedStat);
			tripMapper.setPlusedStat(trip);
			return plusedStat;
		}
		return tripMapper.getPlusedStat(trip);
	}	

	public Integer favorite(Trip trip, User user) {
		if (!tripMapper.isFavorited(trip, user)) {
			tripMapper.favorite(trip, user);
			Integer favoritedStat = tripMapper.getFavoritedStat(trip);
			trip.setFavoritedStat(favoritedStat);
			tripMapper.setFavoritedStat(trip);
			return favoritedStat;
		}
		return tripMapper.getFavoritedStat(trip);		
	}	

	public Integer unFavorite(Trip trip, User user) {
		if (tripMapper.isFavorited(trip, user)) {
			tripMapper.unFavorite(trip, user);
			Integer favoritedStat = tripMapper.getFavoritedStat(trip);
			trip.setFavoritedStat(favoritedStat);
			tripMapper.setFavoritedStat(trip);
			return favoritedStat;
		}
		return tripMapper.getFavoritedStat(trip);		
	}

  public Integer createComment(Comment<Trip> comment) {
    return tripCommentMapper.create(comment);
  }	
}
