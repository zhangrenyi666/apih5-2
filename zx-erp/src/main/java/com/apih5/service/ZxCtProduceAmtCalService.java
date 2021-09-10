package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtProduceAmtCal;

public interface ZxCtProduceAmtCalService {

    public ResponseEntity getZxCtProduceAmtCalListByCondition(ZxCtProduceAmtCal zxCtProduceAmtCal);

    public ResponseEntity getZxCtProduceAmtCalDetail(ZxCtProduceAmtCal zxCtProduceAmtCal);

    public ResponseEntity saveZxCtProduceAmtCal(ZxCtProduceAmtCal zxCtProduceAmtCal);

    public ResponseEntity updateZxCtProduceAmtCal(ZxCtProduceAmtCal zxCtProduceAmtCal);

    public ResponseEntity batchDeleteUpdateZxCtProduceAmtCal(List<ZxCtProduceAmtCal> zxCtProduceAmtCalList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
