package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;

public interface ZjTzThousandCheckBaseService {

    public ResponseEntity getZjTzThousandCheckBaseListByCondition(ZjTzThousandCheckBase zjTzThousandCheckBase);

    public ResponseEntity getZjTzThousandCheckBaseDetails(ZjTzThousandCheckBase zjTzThousandCheckBase);

    public ResponseEntity saveZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase);

    public ResponseEntity updateZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase);

    public ResponseEntity batchDeleteUpdateZjTzThousandCheckBase(List<ZjTzThousandCheckBase> zjTzThousandCheckBaseList);

	public ResponseEntity batchImportZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase);

}

