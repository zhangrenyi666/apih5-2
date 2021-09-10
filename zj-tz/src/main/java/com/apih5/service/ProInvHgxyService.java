package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvHgxy;

public interface ProInvHgxyService {

    public ResponseEntity getProInvHgxyListByCondition(ProInvHgxy proInvHgxy);

    public ResponseEntity getProInvHgxyDetails(ProInvHgxy proInvHgxy);

    public ResponseEntity saveProInvHgxy(ProInvHgxy proInvHgxy);

    public ResponseEntity updateProInvHgxy(ProInvHgxy proInvHgxy);

    public ResponseEntity batchDeleteUpdateProInvHgxy(List<ProInvHgxy> proInvHgxyList);

}

