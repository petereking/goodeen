package com.goodeen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.goodeen.db.RebackTasks;
import com.goodeen.solr.SolrScheduledTasks;

@SpringBootApplication
@EnableScheduling
public class Scheduling implements CommandLineRunner {
  @Autowired
  private SolrScheduledTasks solrTasks;
  
  @Autowired
  private RebackTasks dbRebackTask;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Scheduling.class, args);
  }
  
  @Override
  public void run(String... args) throws Exception {
    System.out.println(".............................开始启动任务.............................");
    solrTasks.reloadSolrIndex();
    dbRebackTask.rebackDb();
    System.out.println(".............................任务启动成功.............................");
  }  
}
