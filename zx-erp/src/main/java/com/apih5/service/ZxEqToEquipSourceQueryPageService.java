package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqToEquipSourceQueryPage;

public interface ZxEqToEquipSourceQueryPageService {

    public ResponseEntity getZxEqToEquipSourceQueryPageListByCondition(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage);

    public ResponseEntity getZxEqToEquipSourceQueryPageDetail(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage);

    public ResponseEntity saveZxEqToEquipSourceQueryPage(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage);

    public ResponseEntity updateZxEqToEquipSourceQueryPage(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage);

    public ResponseEntity batchDeleteUpdateZxEqToEquipSourceQueryPage(List<ZxEqToEquipSourceQueryPage> zxEqToEquipSourceQueryPageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity ureportZxEqToEquipSourceQueryPageIdle(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage);
}
