package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazardMain;

public interface ZxSfHazardMainService {

    public ResponseEntity getZxSfHazardMainListByCondition(ZxSfHazardMain zxSfHazardMain);

    public ResponseEntity getZxSfHazardMainDetail(ZxSfHazardMain zxSfHazardMain);

    public ResponseEntity saveZxSfHazardMain(ZxSfHazardMain zxSfHazardMain);

    public ResponseEntity updateZxSfHazardMain(ZxSfHazardMain zxSfHazardMain);

    public ResponseEntity batchDeleteUpdateZxSfHazardMain(List<ZxSfHazardMain> zxSfHazardMainList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
