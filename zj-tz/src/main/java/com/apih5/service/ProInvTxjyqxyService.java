package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvTxjyqxy;

public interface ProInvTxjyqxyService {

    public ResponseEntity getProInvTxjyqxyListByCondition(ProInvTxjyqxy proInvTxjyqxy);

    public ResponseEntity getProInvTxjyqxyDetails(ProInvTxjyqxy proInvTxjyqxy);

    public ResponseEntity saveProInvTxjyqxy(ProInvTxjyqxy proInvTxjyqxy);

    public ResponseEntity updateProInvTxjyqxy(ProInvTxjyqxy proInvTxjyqxy);

    public ResponseEntity batchDeleteUpdateProInvTxjyqxy(List<ProInvTxjyqxy> proInvTxjyqxyList);

}

