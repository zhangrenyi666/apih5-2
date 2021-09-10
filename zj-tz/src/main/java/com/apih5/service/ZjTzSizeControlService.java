package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSizeControl;

public interface ZjTzSizeControlService {

    public ResponseEntity getZjTzSizeControlListByCondition(ZjTzSizeControl zjTzSizeControl);

    public ResponseEntity getZjTzSizeControlDetails(ZjTzSizeControl zjTzSizeControl);

    public ResponseEntity saveZjTzSizeControl(ZjTzSizeControl zjTzSizeControl);

    public ResponseEntity updateZjTzSizeControl(ZjTzSizeControl zjTzSizeControl);

    public ResponseEntity batchDeleteUpdateZjTzSizeControl(List<ZjTzSizeControl> zjTzSizeControlList);

}

