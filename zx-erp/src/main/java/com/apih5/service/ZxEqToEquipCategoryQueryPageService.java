package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQueryPage;

public interface ZxEqToEquipCategoryQueryPageService {

    public ResponseEntity getZxEqToEquipCategoryQueryPageListByCondition(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);

    public ResponseEntity getZxEqToEquipCategoryQueryPageDetail(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);

    public ResponseEntity saveZxEqToEquipCategoryQueryPage(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);

    public ResponseEntity updateZxEqToEquipCategoryQueryPage(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);

    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQueryPage(List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageCountIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageOrginalValueIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageleftValueIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage);
}
