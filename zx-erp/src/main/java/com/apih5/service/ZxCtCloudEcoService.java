package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtCloudEco;

public interface ZxCtCloudEcoService {

	public ResponseEntity getZxCtCloudEcoListByCondition(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity getZxCtCloudEcoDetails(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity saveZxCtCloudEco(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity updateZxCtCloudEco(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity batchDeleteUpdateZxCtCloudEco(List<ZxCtCloudEco> zxCtCloudEcoList);

	public ResponseEntity batchInputZxCtCloudEco(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity gcsgGetZxCtCloudEcoSelect(ZxCtCloudEco zxCtCloudEco);

	public ResponseEntity synZxCtCloudEco();

}
