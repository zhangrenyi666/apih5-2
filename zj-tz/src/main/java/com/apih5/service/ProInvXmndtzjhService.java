package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvXmndtzjh;

public interface ProInvXmndtzjhService {

    public ResponseEntity getProInvXmndtzjhListByCondition(ProInvXmndtzjh proInvXmndtzjh);

    public ResponseEntity getProInvXmndtzjhDetails(ProInvXmndtzjh proInvXmndtzjh);

    public ResponseEntity saveProInvXmndtzjh(ProInvXmndtzjh proInvXmndtzjh);

    public ResponseEntity updateProInvXmndtzjh(ProInvXmndtzjh proInvXmndtzjh);

    public ResponseEntity batchDeleteUpdateProInvXmndtzjh(List<ProInvXmndtzjh> proInvXmndtzjhList);

}

