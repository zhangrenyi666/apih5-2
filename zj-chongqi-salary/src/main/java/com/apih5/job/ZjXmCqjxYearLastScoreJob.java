package com.apih5.job;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apih5.framework.utils.LoggerUtils;
import com.apih5.service.ZjXmCqjxYearAssistantService;
/**
 *  重庆绩效考核年度最终评分
 *  每秒执行一次
 */
@DisallowConcurrentExecution
public class ZjXmCqjxYearLastScoreJob implements  Job,Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired(required = true)
    private ZjXmCqjxYearAssistantService zjXmCqjxYearAssistantService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException { 
//      LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--start-");
      zjXmCqjxYearAssistantService.jobZjXmCqjxYearAssistantLastScore(null);
//    LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--end-" + " " + LocalDateTime.now());
  }
}
