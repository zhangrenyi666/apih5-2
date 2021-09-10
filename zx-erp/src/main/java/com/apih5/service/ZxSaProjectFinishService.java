package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectFinish;

public interface ZxSaProjectFinishService {

    public ResponseEntity getZxSaProjectFinishListByCondition(ZxSaProjectFinish zxSaProjectFinish);

    public ResponseEntity getZxSaProjectFinishDetails(ZxSaProjectFinish zxSaProjectFinish);

    public ResponseEntity saveZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish);

    public ResponseEntity updateZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish);

    public ResponseEntity batchDeleteUpdateZxSaProjectFinish(List<ZxSaProjectFinish> zxSaProjectFinishList);

	public ResponseEntity syncZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish);

	public ResponseEntity getZxSaProjectUnFinishList(ZxSaProjectFinish zxSaProjectFinish);

}

