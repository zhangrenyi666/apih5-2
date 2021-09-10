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
 * 暂时设置为10~20分钟 发送通知后3天~6天催打分者打分定时任务
 */
@DisallowConcurrentExecution
public class ZjXmJxAssessmentUserScoreReviewerJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreService zjXmJxAssessmentUserScoreService;

	/**
	 * 发送通知后3天~6天催打分者打分定时任务
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger, "发送通知后3天~6天催打分者打分 ZjXmJxAssessmentUserScoreReviewerJob-start");
		try {
		    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            int startValue = dataMap.getInt("startValue");
            int endValue = dataMap.getInt("endValue");
            ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
            zjXmJxAssessmentUserScore.setStartValue(startValue);
            zjXmJxAssessmentUserScore.setEndValue(endValue);
			zjXmJxAssessmentUserScoreService
					.timingToSendMessageZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger, "发送通知后3天~6天催打分者打分 ZjXmJxAssessmentUserScoreReviewerJob-end");
	}

}
