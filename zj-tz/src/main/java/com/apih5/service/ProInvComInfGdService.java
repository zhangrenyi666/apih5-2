package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvComInfGd;

public interface ProInvComInfGdService {

    public ResponseEntity getProInvComInfGdListByCondition(ProInvComInfGd proInvComInfGd);

    public ResponseEntity getProInvComInfGdDetails(ProInvComInfGd proInvComInfGd);

    public ResponseEntity saveProInvComInfGd(ProInvComInfGd proInvComInfGd);

    public ResponseEntity updateProInvComInfGd(ProInvComInfGd proInvComInfGd);

    public ResponseEntity batchDeleteUpdateProInvComInfGd(List<ProInvComInfGd> proInvComInfGdList);

}

