package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzBrandResultShow;

public interface ZjTzBrandResultShowService {

    public ResponseEntity getZjTzBrandResultShowListByCondition(ZjTzBrandResultShow zjTzBrandResultShow);

    public ResponseEntity getZjTzBrandResultShowDetails(ZjTzBrandResultShow zjTzBrandResultShow);

    public ResponseEntity saveZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow);

    public ResponseEntity updateZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow);

    public ResponseEntity batchDeleteUpdateZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList);

	public ResponseEntity toHomeShowZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow);

	public ResponseEntity batchReleaseZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList);

	public ResponseEntity batchRecallZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList);

	public ResponseEntity batchExportZjTzBrandResultShowFile(List<ZjTzBrandResultShow> zjTzBrandResultShowList);

	public ResponseEntity getHomeAdvertZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow);
	
	public ResponseEntity exportZjTzBrandResultShowList(ZjTzBrandResultShow zjTzBrandResultShow, HttpServletResponse response);

}

