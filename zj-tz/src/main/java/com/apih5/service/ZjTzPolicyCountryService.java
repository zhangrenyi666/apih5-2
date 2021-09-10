package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyCountry;

public interface ZjTzPolicyCountryService {

    public ResponseEntity getZjTzPolicyCountryListByCondition(ZjTzPolicyCountry zjTzPolicyCountry);

    public ResponseEntity getZjTzPolicyCountryDetails(ZjTzPolicyCountry zjTzPolicyCountry);

    public ResponseEntity saveZjTzPolicyCountry(ZjTzPolicyCountry zjTzPolicyCountry);

    public ResponseEntity updateZjTzPolicyCountry(ZjTzPolicyCountry zjTzPolicyCountry);

    public ResponseEntity batchDeleteUpdateZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList);

    //--扩展
    public ResponseEntity updateZjTzPolicyCountryPush(ZjTzPolicyCountry zjTzPolicyCountry);

    public ResponseEntity updateZjTzPolicyCountryHomeShow(ZjTzPolicyCountry zjTzPolicyCountry);

	public ResponseEntity batchDeleteReleaseZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList);

	public ResponseEntity batchDeleteRecallZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList);

	public ResponseEntity batchExportZjTzPolicyCountryFile(List<ZjTzPolicyCountry> zjTzPolicyCountryList);

	public ResponseEntity getZjTzPolicyCountryHomeAdvertMacro(ZjTzPolicyCountry zjTzPolicyCountry);
}

