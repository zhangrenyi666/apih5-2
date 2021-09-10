package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCmFilterOrg;

public interface ZxCmFilterOrgService {

    public ResponseEntity getZxCmFilterOrgListByCondition(ZxCmFilterOrg zxCmFilterOrg);

    public ResponseEntity getZxCmFilterOrgDetail(ZxCmFilterOrg zxCmFilterOrg);

    public ResponseEntity saveZxCmFilterOrg(ZxCmFilterOrg zxCmFilterOrg);

    public ResponseEntity updateZxCmFilterOrg(ZxCmFilterOrg zxCmFilterOrg);

    public ResponseEntity batchDeleteUpdateZxCmFilterOrg(List<ZxCmFilterOrg> zxCmFilterOrgList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
