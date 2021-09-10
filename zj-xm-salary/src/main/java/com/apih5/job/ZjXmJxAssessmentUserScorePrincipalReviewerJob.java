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
import com.apih5.service.ZjXmJxAssessmentUserScoreService;

/**
 * 暂时设置为10分钟正职评分发送通知超过3天(72小时)未打分,自动算分(有一个打分算分，一个都没有算零分)
 */
@DisallowConcurrentExecution
public class ZjXmJxAssessmentUserScorePrincipalReviewerJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreService zjXmJxAssessmentUserScoreService;

	/**
	 * 正职评分发送通知超过3天(72小时)未打分,自动算分
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger,
				"正职评分发送通知超过3天(72小时)未打分,自动算分 ZjXmJxAssessmentUserScorePrincipalReviewerJob-start");
		try {
		    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            int startValue = dataMap.getInt("startValue");
            ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
            zjXmJxAssessmentUserScore.setStartValue(startValue);
			zjXmJxAssessmentUserScoreService
					.timingToDealWithPrincipalZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger, "正职评分发送通知超过3天(72小时)未打分,自动算分 ZjXmJxAssessmentUserScorePrincipalReviewerJob-end");
	}

}
