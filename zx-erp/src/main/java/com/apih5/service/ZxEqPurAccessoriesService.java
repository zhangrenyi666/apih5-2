package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqPurAccessories;

public interface ZxEqPurAccessoriesService {

    public ResponseEntity getZxEqPurAccessoriesListByCondition(ZxEqPurAccessories zxEqPurAccessories);

    public ResponseEntity getZxEqPurAccessoriesDetails(ZxEqPurAccessories zxEqPurAccessories);

    public ResponseEntity saveZxEqPurAccessories(ZxEqPurAccessories zxEqPurAccessories);

    public ResponseEntity updateZxEqPurAccessories(ZxEqPurAccessories zxEqPurAccessories);

    public ResponseEntity batchDeleteUpdateZxEqPurAccessories(List<ZxEqPurAccessories> zxEqPurAccessoriesList);

	public List<ZxEqPurAccessories> ureportZxEqPurAccessoriesList(ZxEqPurAccessories zxEqPurAccessories);

}

