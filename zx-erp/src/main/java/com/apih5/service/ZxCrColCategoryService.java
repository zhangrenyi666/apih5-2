package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrColCategory;

public interface ZxCrColCategoryService {

    public ResponseEntity getZxCrColCategoryListByCondition(ZxCrColCategory zxCrColCategory);

    public ResponseEntity getZxCrColCategoryDetail(ZxCrColCategory zxCrColCategory);

    public ResponseEntity saveZxCrColCategory(ZxCrColCategory zxCrColCategory);

    public ResponseEntity updateZxCrColCategory(ZxCrColCategory zxCrColCategory);

    public ResponseEntity batchDeleteUpdateZxCrColCategory(List<ZxCrColCategory> zxCrColCategoryList);

	public ResponseEntity getZxCrColCategoryTree(ZxCrColCategory zxCrColCategory);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
