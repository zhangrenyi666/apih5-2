package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisor;

public interface ZjTzThreeSupervisorService {

    public ResponseEntity getZjTzThreeSupervisorListByCondition(ZjTzThreeSupervisor zjTzThreeSupervisor);

    public ResponseEntity getZjTzThreeSupervisorDetails(ZjTzThreeSupervisor zjTzThreeSupervisor);

    public ResponseEntity saveZjTzThreeSupervisor(ZjTzThreeSupervisor zjTzThreeSupervisor);

    public ResponseEntity updateZjTzThreeSupervisor(ZjTzThreeSupervisor zjTzThreeSupervisor);

    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisor(List<ZjTzThreeSupervisor> zjTzThreeSupervisorList);

}