package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectSet;

public interface ZxSaProjectSetService {

    public ResponseEntity getZxSaProjectSetListByCondition(ZxSaProjectSet zxSaProjectSet);

    public ResponseEntity getZxSaProjectSetDetails(ZxSaProjectSet zxSaProjectSet);

    public ResponseEntity saveZxSaProjectSet(ZxSaProjectSet zxSaProjectSet);

    public ResponseEntity updateZxSaProjectSet(ZxSaProjectSet zxSaProjectSet);

    public ResponseEntity batchDeleteUpdateZxSaProjectSet(List<ZxSaProjectSet> zxSaProjectSetList);

}

