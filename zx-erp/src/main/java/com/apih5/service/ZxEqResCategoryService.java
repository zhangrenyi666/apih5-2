package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqResCategory;

public interface ZxEqResCategoryService {

    public ResponseEntity getZxEqResCategoryListByCondition(ZxEqResCategory zxEqResCategory);

    public ResponseEntity getZxEqResCategoryDetails(ZxEqResCategory zxEqResCategory);

    public ResponseEntity saveZxEqResCategory(ZxEqResCategory zxEqResCategory);

    public ResponseEntity updateZxEqResCategory(ZxEqResCategory zxEqResCategory);

    public ResponseEntity batchDeleteUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList);

	public ResponseEntity batchStartUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList);

	public ResponseEntity batchStopUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList);

	public ResponseEntity getZxEqResCategoryTree(ZxEqResCategory zxEqResCategory);

	public ResponseEntity getZxEqResCategoryItemList(ZxEqResCategory zxEqResCategory);

}

