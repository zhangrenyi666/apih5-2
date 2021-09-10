package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisor;

public interface ZxEqMoveSupervisorService {

    public ResponseEntity getZxEqMoveSupervisorListByCondition(ZxEqMoveSupervisor zxEqMoveSupervisor);

    public ResponseEntity getZxEqMoveSupervisorDetails(ZxEqMoveSupervisor zxEqMoveSupervisor);

    public ResponseEntity saveZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor);

    public ResponseEntity updateZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor);

    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisor(List<ZxEqMoveSupervisor> zxEqMoveSupervisorList);

	public ResponseEntity outConfirmZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor);

	public ResponseEntity inConfirmZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor);

	public ResponseEntity batchRequestZxEqMoveSupervisorNum(List<ZxEqMoveSupervisor> zxEqMoveSupervisorList);

	public ResponseEntity writeZxEqMoveSupervisorNum(ZxEqMoveSupervisor zxEqMoveSupervisor);

}

