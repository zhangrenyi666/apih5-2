package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrColResource;

public interface ZxCrColResourceService {

    public ResponseEntity getZxCrColResourceListByCondition(ZxCrColResource zxCrColResource);

    public ResponseEntity getZxCrColResourceDetail(ZxCrColResource zxCrColResource);

    public ResponseEntity saveZxCrColResource(ZxCrColResource zxCrColResource);

    public ResponseEntity updateZxCrColResource(ZxCrColResource zxCrColResource);

    public ResponseEntity batchDeleteUpdateZxCrColResource(List<ZxCrColResource> zxCrColResourceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
