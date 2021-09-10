package com.apih5.job;

import java.io.Serializable;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;
import com.apih5.service.ZjXmJxAssessmentUserScoreService;

/**
 * 任务考核超3天自动确认定时任务
 */
@DisallowConcurrentExecution
public class ZjXmJxAssessmentUserScoreAuditeeJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreService zjXmJxAssessmentUserScoreService;

	/**
	 * 暂时改为10分钟被考核人任务考核超3天自动确认
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger, "被考核人任务考核超3天自动确认 ZjXmJxAssessmentUserScoreAuditeeJob-start");
		try {
		    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		    int startValue = dataMap.getInt("startValue");
		    ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
            zjXmJxAssessmentUserScore.setStartValue(startValue);
			zjXmJxAssessmentUserScoreService.timingToConfirmZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger, "被考核人任务考核超3天自动确认 ZjXmJxAssessmentUserScoreAuditeeJob-end");
	}

}
