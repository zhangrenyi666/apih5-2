package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeShareholder;

public interface ZjTzThreeShareholderService {

    public ResponseEntity getZjTzThreeShareholderListByCondition(ZjTzThreeShareholder zjTzThreeShareholder);

    public ResponseEntity getZjTzThreeShareholderDetails(ZjTzThreeShareholder zjTzThreeShareholder);

    public ResponseEntity saveZjTzThreeShareholder(ZjTzThreeShareholder zjTzThreeShareholder);

    public ResponseEntity updateZjTzThreeShareholder(ZjTzThreeShareholder zjTzThreeShareholder);

    public ResponseEntity batchDeleteUpdateZjTzThreeShareholder(List<ZjTzThreeShareholder> zjTzThreeShareholderList);

}