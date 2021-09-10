package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrDetail;

public interface ZxCtContrDetailService {

    public ResponseEntity getZxCtContrDetailListByCondition(ZxCtContrDetail zxCtContrDetail);

    public ResponseEntity getZxCtContrDetailDetail(ZxCtContrDetail zxCtContrDetail);

    public ResponseEntity saveZxCtContrDetail(ZxCtContrDetail zxCtContrDetail);

    public ResponseEntity updateZxCtContrDetail(ZxCtContrDetail zxCtContrDetail);

    public ResponseEntity batchDeleteUpdateZxCtContrDetail(List<ZxCtContrDetail> zxCtContrDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
