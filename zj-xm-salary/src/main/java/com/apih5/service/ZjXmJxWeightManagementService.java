package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;

public interface ZjXmJxWeightManagementService {

	public ResponseEntity getZjXmJxWeightManagementListByCondition(ZjXmJxWeightManagement zjXmJxWeightManagement);

	public ResponseEntity getZjXmJxWeightManagementDetails(ZjXmJxWeightManagement zjXmJxWeightManagement);

	public ResponseEntity saveZjXmJxWeightManagement(ZjXmJxWeightManagement zjXmJxWeightManagement);

	public ResponseEntity updateZjXmJxWeightManagement(ZjXmJxWeightManagement zjXmJxWeightManagement);

	public ResponseEntity batchDeleteUpdateZjXmJxWeightManagement(
			List<ZjXmJxWeightManagement> zjXmJxWeightManagementList);

}
