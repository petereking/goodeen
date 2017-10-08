package com.goodeen.solr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.goodeen.solr.mapper.IndexMapper;
import com.goodeen.solr.model.TripCore;
import com.goodeen.solr.model.UserCore;
import com.goodeen.solr.repository.TripRepository;
import com.goodeen.solr.repository.UserRepository;


@Component
public class SolrScheduledTasks {
  @Value("${solr.update.time:1}")
  private Integer INIT_TIME;

  @Autowired
  private IndexMapper indexMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TripRepository tripRepository;

  /**
   * 默认每隔1分钟增量更新索引
   */
  @Scheduled(cron = "0 0/${solr.update.time:1} * * * ?")
  public void updateSolrIndex() {
    System.err.println(".............................开始增量重新生成solr索引任务.............................");
    List<UserCore> users = indexMapper.queryForUsers(INIT_TIME);
    if (users != null && users.size() > 0) {
      userRepository.save(users);
    }
    List<TripCore> trips = indexMapper.queryForTrips(INIT_TIME);
    if (trips != null && trips.size() > 0) {
      tripRepository.save(trips);
    }
    System.err.println(".............................完成增量重新生成solr索引任务.............................");
  }
  
  /**
   * 默认每天的00：01:30，和03:01:30秒全量更新索引
   */
  @Scheduled(cron = "${solr.reload.cron:0/5 * * * * ?}")
  public void reloadSolrIndex() {
    System.err.println(".............................开始全量重新生成solr索引任务.............................");
    userRepository.deleteAll();
    tripRepository.deleteAll();
    List<UserCore> users = indexMapper.queryForUsers(0);
    if (users != null && users.size() > 0) {
      userRepository.save(users);
    }
    List<TripCore> trips = indexMapper.queryForTrips(0);
    if (trips != null && trips.size() > 0) {
      tripRepository.save(trips);
    }
    System.err.println(".............................完成全量重新生成solr索引任务.............................");
  }
}
