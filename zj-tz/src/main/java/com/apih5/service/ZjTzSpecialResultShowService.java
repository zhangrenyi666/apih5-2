package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSpecialResultShow;

public interface ZjTzSpecialResultShowService {

    public ResponseEntity getZjTzSpecialResultShowListByCondition(ZjTzSpecialResultShow zjTzSpecialResultShow);

    public ResponseEntity getZjTzSpecialResultShowDetails(ZjTzSpecialResultShow zjTzSpecialResultShow);

    public ResponseEntity saveZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow);

    public ResponseEntity updateZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow);

    public ResponseEntity batchDeleteUpdateZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList);

	public ResponseEntity toHomeShowZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow);

	public ResponseEntity batchReleaseZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList);

	public ResponseEntity batchRecallZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList);

	public ResponseEntity batchExportZjTzSpecialResultShowFile(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList);

}

