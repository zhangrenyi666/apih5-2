package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;

public interface ZxSaProjectWorkSettleService {

    public ResponseEntity getZxSaProjectWorkSettleListByCondition(ZxSaProjectWorkSettle zxSaProjectWorkSettle);

    public ResponseEntity getZxSaProjectWorkSettleDetail(ZxSaProjectWorkSettle zxSaProjectWorkSettle);

    public ResponseEntity saveZxSaProjectWorkSettle(ZxSaProjectWorkSettle zxSaProjectWorkSettle);

    public ResponseEntity updateZxSaProjectWorkSettle(ZxSaProjectWorkSettle zxSaProjectWorkSettle);

    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettle(List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
