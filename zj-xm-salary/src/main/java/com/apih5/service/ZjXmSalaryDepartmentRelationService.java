package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentRelation;

public interface ZjXmSalaryDepartmentRelationService {

    public ResponseEntity getZjXmSalaryDepartmentRelationListByCondition(ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation);

    public ResponseEntity getZjXmSalaryDepartmentRelationDetails(ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation);

    public ResponseEntity saveZjXmSalaryDepartmentRelation(ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation);

    public ResponseEntity updateZjXmSalaryDepartmentRelation(ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation);

    public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentRelation(List<ZjXmSalaryDepartmentRelation> zjXmSalaryDepartmentRelationList);

}

