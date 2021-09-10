package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvComInf;

public interface ProInvComInfService {

    public ResponseEntity getProInvComInfListByCondition(ProInvComInf proInvComInf);

    public ResponseEntity getProInvComInfDetails(ProInvComInf proInvComInf);

    public ResponseEntity saveProInvComInf(ProInvComInf proInvComInf);

    public ResponseEntity updateProInvComInf(ProInvComInf proInvComInf);

    public ResponseEntity batchDeleteUpdateProInvComInf(List<ProInvComInf> proInvComInfList);

}

