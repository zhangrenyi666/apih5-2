package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRunScheme;

public interface ZjTzRunSchemeService {

    public ResponseEntity getZjTzRunSchemeListByCondition(ZjTzRunScheme zjTzRunScheme);

    public ResponseEntity getZjTzRunSchemeDetails(ZjTzRunScheme zjTzRunScheme);

    public ResponseEntity saveZjTzRunScheme(ZjTzRunScheme zjTzRunScheme);

    public ResponseEntity updateZjTzRunScheme(ZjTzRunScheme zjTzRunScheme);

    public ResponseEntity batchDeleteUpdateZjTzRunScheme(List<ZjTzRunScheme> zjTzRunSchemeList);

	public ResponseEntity updateZjTzRunSchemeForFlow(ZjTzRunScheme zjTzRunScheme);

}

