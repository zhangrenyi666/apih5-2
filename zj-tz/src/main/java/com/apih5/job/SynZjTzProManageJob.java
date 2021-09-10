package com.apih5.job;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzProManageService;

@DisallowConcurrentExecution
public class SynZjTzProManageJob implements  Job,Serializable {
    private static final long serialVersionUID = 1L;
    
    @Autowired(required = true)
    private ZjTzProManageService zjTzProManageService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("------" + LocalDateTime.now());
        zjTzProManageService.synZjTzProManage();
    }
}
