package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzCountAq;

public interface ZjTzCountAqService {

    public ResponseEntity getZjTzCountAqListByCondition(ZjTzCountAq zjTzCountAq);

    public ResponseEntity getZjTzCountAqDetails(ZjTzCountAq zjTzCountAq);

    public ResponseEntity saveZjTzCountAq(ZjTzCountAq zjTzCountAq);

    public ResponseEntity updateZjTzCountAq(ZjTzCountAq zjTzCountAq);

    public ResponseEntity batchDeleteUpdateZjTzCountAq(List<ZjTzCountAq> zjTzCountAqList);

}

