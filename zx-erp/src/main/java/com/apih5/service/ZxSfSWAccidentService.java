package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfSWAccident;

public interface ZxSfSWAccidentService {

    public ResponseEntity getZxSfSWAccidentListByCondition(ZxSfSWAccident zxSfSWAccident);

    public ResponseEntity getZxSfSWAccidentDetail(ZxSfSWAccident zxSfSWAccident);

    public ResponseEntity saveZxSfSWAccident(ZxSfSWAccident zxSfSWAccident);

    public ResponseEntity updateZxSfSWAccident(ZxSfSWAccident zxSfSWAccident);

    public ResponseEntity batchDeleteUpdateZxSfSWAccident(List<ZxSfSWAccident> zxSfSWAccidentList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
