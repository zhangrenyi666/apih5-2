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
 * 任务考核发送通知超过6天(144小时)未打分,自动打分并将部门负责人扣分
 */
@DisallowConcurrentExecution
public class ZjXmJxAssessmentUserScoreTaskReviewerJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreService zjXmJxAssessmentUserScoreService;

	/**
	 * 暂时为20分钟 任务考核发送通知超过6天(144小时)未打分,自动打零分并将部门负责人扣拾分
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger,
				"任务考核发送通知超过6天(144小时)未打分,自动打零分并将部门负责人扣拾分 ZjXmJxAssessmentUserScoreTaskReviewerJob-start");
		try {
		    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            int startValue = dataMap.getInt("startValue");
            ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
            zjXmJxAssessmentUserScore.setStartValue(startValue);
			zjXmJxAssessmentUserScoreService
					.timingToDealWithTaskZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger,
				"任务考核发送通知超过6天(144小时)未打分,自动打零分并将部门负责人扣拾分 ZjXmJxAssessmentUserScoreTaskReviewerJob-end");
	}

}
