package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEAccident;

public interface ZxSfEAccidentService {

    public ResponseEntity getZxSfEAccidentListByCondition(ZxSfEAccident zxSfEAccident);

    public ResponseEntity getZxSfEAccidentDetail(ZxSfEAccident zxSfEAccident);

    public ResponseEntity saveZxSfEAccident(ZxSfEAccident zxSfEAccident);

    public ResponseEntity updateZxSfEAccident(ZxSfEAccident zxSfEAccident);

    public ResponseEntity batchDeleteUpdateZxSfEAccident(List<ZxSfEAccident> zxSfEAccidentList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
