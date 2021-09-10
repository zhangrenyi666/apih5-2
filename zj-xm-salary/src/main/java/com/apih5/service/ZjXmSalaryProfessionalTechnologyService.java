package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryProfessionalTechnology;

public interface ZjXmSalaryProfessionalTechnologyService {

    public ResponseEntity getZjXmSalaryProfessionalTechnologyListByCondition(ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology);

    public ResponseEntity getZjXmSalaryProfessionalTechnologyDetails(ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology);

    public ResponseEntity saveZjXmSalaryProfessionalTechnology(ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology);

    public ResponseEntity updateZjXmSalaryProfessionalTechnology(ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology);

    public ResponseEntity batchDeleteUpdateZjXmSalaryProfessionalTechnology(List<ZjXmSalaryProfessionalTechnology> zjXmSalaryProfessionalTechnologyList);

}

