package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeDirector;

public interface ZjTzThreeDirectorService {

    public ResponseEntity getZjTzThreeDirectorListByCondition(ZjTzThreeDirector zjTzThreeDirector);

    public ResponseEntity getZjTzThreeDirectorDetails(ZjTzThreeDirector zjTzThreeDirector);

    public ResponseEntity saveZjTzThreeDirector(ZjTzThreeDirector zjTzThreeDirector);

    public ResponseEntity updateZjTzThreeDirector(ZjTzThreeDirector zjTzThreeDirector);

    public ResponseEntity batchDeleteUpdateZjTzThreeDirector(List<ZjTzThreeDirector> zjTzThreeDirectorList);

}

