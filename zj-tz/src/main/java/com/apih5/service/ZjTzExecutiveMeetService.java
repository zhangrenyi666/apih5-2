package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeet;

public interface ZjTzExecutiveMeetService {

    public ResponseEntity getZjTzExecutiveMeetListByCondition(ZjTzExecutiveMeet zjTzExecutiveMeet);

    public ResponseEntity getZjTzExecutiveMeetDetails(ZjTzExecutiveMeet zjTzExecutiveMeet);

    public ResponseEntity saveZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet);

    public ResponseEntity updateZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet);

    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeet(List<ZjTzExecutiveMeet> zjTzExecutiveMeetList);

	public ResponseEntity getZjTzExecutiveMeetListFromProjectShareholder(ZjTzExecutiveMeet zjTzExecutiveMeet);

	public ResponseEntity changeZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet);

	public ResponseEntity correctiveZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet);

}

