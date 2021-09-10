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
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentDetailed;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentDetailedService;
/**
 *  重庆绩效考核员工协作性-同步 
 *  每秒执行一次
 */
@DisallowConcurrentExecution
public class ZjXmCqjxCollaborationJob  implements  Job,Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentDetailedService zjXmCqjxCollaborationAssessmentDetailedService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--start-");
        ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed = new ZjXmCqjxCollaborationAssessmentDetailed();
        zjXmCqjxCollaborationAssessmentDetailedService.zjXmCqjxCollaborationAssessmentDetailedTask(zjXmCqjxCollaborationAssessmentDetailed); 
//        LoggerUtils.printDebugLogger(logger, "--定时任务结束 wechatToken-同步--end-" + " " + LocalDateTime.now());
    }
}
