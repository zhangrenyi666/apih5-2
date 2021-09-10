package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;

public interface ZxSaProjectPaySettleService {

    public ResponseEntity getZxSaProjectPaySettleListByCondition(ZxSaProjectPaySettle zxSaProjectPaySettle);

    public ResponseEntity getZxSaProjectPaySettleDetail(ZxSaProjectPaySettle zxSaProjectPaySettle);

    public ResponseEntity saveZxSaProjectPaySettle(ZxSaProjectPaySettle zxSaProjectPaySettle);

    public ResponseEntity updateZxSaProjectPaySettle(ZxSaProjectPaySettle zxSaProjectPaySettle);

    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettle(List<ZxSaProjectPaySettle> zxSaProjectPaySettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
