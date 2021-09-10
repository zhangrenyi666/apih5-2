package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEWork;

public interface ZxEqEWorkService {

    public ResponseEntity getZxEqEWorkListByCondition(ZxEqEWork zxEqEWork);

    public ResponseEntity getZxEqEWorkDetails(ZxEqEWork zxEqEWork);

    public ResponseEntity saveZxEqEWork(ZxEqEWork zxEqEWork);

    public ResponseEntity updateZxEqEWork(ZxEqEWork zxEqEWork);

    public ResponseEntity batchDeleteUpdateZxEqEWork(List<ZxEqEWork> zxEqEWorkList);

	public ResponseEntity getZxEqEWorkListForReport(ZxEqEWork zxEqEWork);

	public List<ZxEqEWork> ureportZxEqEWorkListForReport(ZxEqEWork zxEqEWork);

}

