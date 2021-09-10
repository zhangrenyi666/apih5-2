package com.apih5.job;

import java.io.Serializable;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.service.ZxCtContractService;

/**
 * 业主合同台账定时任务
 * @author JHJ
 */
@DisallowConcurrentExecution
public class ZxCtContractJob implements Job, Serializable {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ZxCtContractService zxCtContractService;

	/**
	 * 同步业主合同台账是否参与指标考核状态定时任务(次月月初（1号）)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerUtils.printLogger(logger, "同步业主合同台账是否参与指标考核状态定时任务-start");
		try {
			zxCtContractService.jobSyncZxCtContractIsSettle(new ZxCtContract());
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerUtils.printLogger(logger, "同步业主合同台账是否参与指标考核状态定时任务-end");
	}

}
