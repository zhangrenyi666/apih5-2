package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeLanguage;

public interface ZjTzEmployeeLanguageService {

    public ResponseEntity getZjTzEmployeeLanguageListByCondition(ZjTzEmployeeLanguage zjTzEmployeeLanguage);

    public ResponseEntity getZjTzEmployeeLanguageDetails(ZjTzEmployeeLanguage zjTzEmployeeLanguage);

    public ResponseEntity saveZjTzEmployeeLanguage(ZjTzEmployeeLanguage zjTzEmployeeLanguage);

    public ResponseEntity updateZjTzEmployeeLanguage(ZjTzEmployeeLanguage zjTzEmployeeLanguage);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeLanguage(List<ZjTzEmployeeLanguage> zjTzEmployeeLanguageList);

}

