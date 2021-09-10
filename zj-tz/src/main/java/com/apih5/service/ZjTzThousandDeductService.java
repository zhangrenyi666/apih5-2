package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;

public interface ZjTzThousandDeductService {

    public ResponseEntity getZjTzThousandDeductListByCondition(ZjTzThousandDeduct zjTzThousandDeduct);

    public ResponseEntity getZjTzThousandDeductDetails(ZjTzThousandDeduct zjTzThousandDeduct);

    public ResponseEntity saveZjTzThousandDeduct(ZjTzThousandDeduct zjTzThousandDeduct);

    public ResponseEntity updateZjTzThousandDeduct(ZjTzThousandDeduct zjTzThousandDeduct);

    public ResponseEntity batchDeleteUpdateZjTzThousandDeduct(List<ZjTzThousandDeduct> zjTzThousandDeductList);

}

