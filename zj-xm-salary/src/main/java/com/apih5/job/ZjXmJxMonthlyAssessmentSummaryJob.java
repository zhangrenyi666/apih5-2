package com.apih5.job;

import java.io.Serializable;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;
import com.apih5.service.ZjXmJxAnnualAssessmentSummaryService;
import com.apih5.service.ZjXmJxAssessmentUserScoreService;
import com.apih5.service.ZjXmJxMonthlyAssessmentSummaryService;

import cn.hutool.core.util.StrUtil;

/**
 * 定时统计项目员工月度考核评分汇总(应该每次定时按照上个月，暂时每隔半小时定时一次)
 */
@DisallowConcurrentExecution
public class ZjXmJxMonthlyAssessmentSummaryJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
    private ZjXmJxMonthlyAssessmentSummaryService zjXmJxMonthlyAssessmentSummaryService;

    @Autowired(required = true)
    private ZjXmJxAnnualAssessmentSummaryService zjXmJxAnnualAssessmentSummaryService;
	
    /**
	 * 定时统计项目员工月度考核评分汇总(应该每次定时按照上个月，暂时每隔半小时定时一次)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger, "定时统计项目员工月度考核评分汇总 ZjXmJxMonthlyAssessmentSummaryJob-Start");
		try {
		    ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	        String flag = dataMap.getString("flag");
	        // flag==1每隔半个小时执行一次，设置时间
	        if(StrUtil.equals("1", flag)) {
	            zjXmJxMonthlyAssessmentSummary.setYearMonth(new Date());
	        } else {
	            // 年度每个月的1号执行一次，全年计算
	            zjXmJxAnnualAssessmentSummaryService.timingToCountZjXmJxAnnualAssessmentSummary(null);
	        }
		    zjXmJxMonthlyAssessmentSummaryService.timingToCountZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger, "定时统计项目员工月度考核评分汇总 ZjXmJxMonthlyAssessmentSummaryJob-End");
	}

}
