package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfAccident;

public interface ZxSfAccidentService {

    public ResponseEntity getZxSfAccidentListByCondition(ZxSfAccident zxSfAccident);

    public ResponseEntity getZxSfAccidentDetail(ZxSfAccident zxSfAccident);

    public ResponseEntity saveZxSfAccident(ZxSfAccident zxSfAccident);

    public ResponseEntity updateZxSfAccident(ZxSfAccident zxSfAccident);

    public ResponseEntity batchDeleteUpdateZxSfAccident(List<ZxSfAccident> zxSfAccidentList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
