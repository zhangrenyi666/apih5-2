package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCcOrgResource;

public interface ZxCcOrgResourceService {

    public ResponseEntity getZxCcOrgResourceListByCondition(ZxCcOrgResource zxCcOrgResource);

    public ResponseEntity getZxCcOrgResourceDetail(ZxCcOrgResource zxCcOrgResource);

    public ResponseEntity saveZxCcOrgResource(ZxCcOrgResource zxCcOrgResource);

    public ResponseEntity updateZxCcOrgResource(ZxCcOrgResource zxCcOrgResource);

    public ResponseEntity batchDeleteUpdateZxCcOrgResource(List<ZxCcOrgResource> zxCcOrgResourceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
