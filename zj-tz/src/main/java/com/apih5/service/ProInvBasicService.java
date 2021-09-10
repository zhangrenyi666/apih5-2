package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvBasic;

public interface ProInvBasicService {

    public ResponseEntity getProInvBasicListByCondition(ProInvBasic proInvBasic);

    public ResponseEntity getProInvBasicDetails(ProInvBasic proInvBasic);

    public ResponseEntity saveProInvBasic(ProInvBasic proInvBasic);

    public ResponseEntity updateProInvBasic(ProInvBasic proInvBasic);

    public ResponseEntity batchDeleteUpdateProInvBasic(List<ProInvBasic> proInvBasicList);

}

