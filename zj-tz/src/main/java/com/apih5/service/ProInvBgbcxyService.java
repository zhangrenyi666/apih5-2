package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvBgbcxy;

public interface ProInvBgbcxyService {

    public ResponseEntity getProInvBgbcxyListByCondition(ProInvBgbcxy proInvBgbcxy);

    public ResponseEntity getProInvBgbcxyDetails(ProInvBgbcxy proInvBgbcxy);

    public ResponseEntity saveProInvBgbcxy(ProInvBgbcxy proInvBgbcxy);

    public ResponseEntity updateProInvBgbcxy(ProInvBgbcxy proInvBgbcxy);

    public ResponseEntity batchDeleteUpdateProInvBgbcxy(List<ProInvBgbcxy> proInvBgbcxyList);

}

