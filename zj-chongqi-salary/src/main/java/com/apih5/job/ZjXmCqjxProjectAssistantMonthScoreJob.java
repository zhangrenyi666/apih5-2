package com.apih5.job;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.service.ZjXmCqjxProjectExecutiveAssistantService;

public class ZjXmCqjxProjectAssistantMonthScoreJob implements  Job,Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantService zjXmCqjxProjectExecutiveAssistantService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException { 
//        LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--start-");
//      String url = Apih5Properties.getWebUrl() + "zjXmCqjxCollaborationAssessmentDetailedTask";
//      String result = HttpUtil.sendPostJson(url, "");
        ZjXmCqjxProjectExecutiveAssistant zjXmCqjxExecutiveAssistant = new ZjXmCqjxProjectExecutiveAssistant();
        zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxProjectAssistantMonthScoreTask(zjXmCqjxExecutiveAssistant);
//      LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--end-" + " " + LocalDateTime.now());
    }

}
