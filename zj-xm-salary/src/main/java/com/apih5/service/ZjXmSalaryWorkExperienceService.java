package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryWorkExperience;

public interface ZjXmSalaryWorkExperienceService {

    public ResponseEntity getZjXmSalaryWorkExperienceListByCondition(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience);

    public ResponseEntity getZjXmSalaryWorkExperienceDetails(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience);

    public ResponseEntity saveZjXmSalaryWorkExperience(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience);

    public ResponseEntity updateZjXmSalaryWorkExperience(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience);

    public ResponseEntity batchDeleteUpdateZjXmSalaryWorkExperience(List<ZjXmSalaryWorkExperience> zjXmSalaryWorkExperienceList);

}

