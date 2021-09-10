package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignChange;

public interface ZjTzDesignChangeService {

    public ResponseEntity getZjTzDesignChangeListByCondition(ZjTzDesignChange zjTzDesignChange);

    public ResponseEntity getZjTzDesignChangeDetails(ZjTzDesignChange zjTzDesignChange);

    public ResponseEntity saveZjTzDesignChange(ZjTzDesignChange zjTzDesignChange);

    public ResponseEntity updateZjTzDesignChange(ZjTzDesignChange zjTzDesignChange);

    public ResponseEntity batchDeleteUpdateZjTzDesignChange(List<ZjTzDesignChange> zjTzDesignChangeList);

}

