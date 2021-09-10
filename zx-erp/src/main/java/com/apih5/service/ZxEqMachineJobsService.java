package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqMachineJobs;

public interface ZxEqMachineJobsService {

    public ResponseEntity getZxEqMachineJobsListByCondition(ZxEqMachineJobs zxEqMachineJobs);

    public ResponseEntity getZxEqMachineJobsDetails(ZxEqMachineJobs zxEqMachineJobs);

    public ResponseEntity saveZxEqMachineJobs(ZxEqMachineJobs zxEqMachineJobs);

    public ResponseEntity updateZxEqMachineJobs(ZxEqMachineJobs zxEqMachineJobs);

    public ResponseEntity batchDeleteUpdateZxEqMachineJobs(List<ZxEqMachineJobs> zxEqMachineJobsList);

}

