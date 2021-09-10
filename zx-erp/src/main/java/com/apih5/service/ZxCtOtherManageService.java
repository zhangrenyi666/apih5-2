package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOtherManage;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtOtherManageService {

    public ResponseEntity getZxCtOtherManageListByCondition(ZxCtOtherManage zxCtOtherManage);

    public ResponseEntity getZxCtOtherManageDetail(ZxCtOtherManage zxCtOtherManage);

    public ResponseEntity saveZxCtOtherManage(ZxCtOtherManage zxCtOtherManage);

    public ResponseEntity updateZxCtOtherManage(ZxCtOtherManage zxCtOtherManage);

    public ResponseEntity batchDeleteUpdateZxCtOtherManage(List<ZxCtOtherManage> zxCtOtherManageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity zxCtOtherManageRegainExecute(ZxCtOtherManage zxCtOtherManage);

    public void exportZxCtOtherManage(ZxCtOtherManage zxCtOtherManage, HttpServletResponse response);
}
