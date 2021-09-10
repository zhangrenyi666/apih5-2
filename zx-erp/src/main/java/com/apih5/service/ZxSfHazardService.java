package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazard;

public interface ZxSfHazardService {

    public ResponseEntity getZxSfHazardListByCondition(ZxSfHazard zxSfHazard);

    public ResponseEntity getZxSfHazardDetail(ZxSfHazard zxSfHazard);

    public ResponseEntity saveZxSfHazard(ZxSfHazard zxSfHazard);

    public ResponseEntity updateZxSfHazard(ZxSfHazard zxSfHazard);

    public ResponseEntity batchDeleteUpdateZxSfHazard(List<ZxSfHazard> zxSfHazardList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
