package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;
import com.apih5.mybatis.pojo.ZjTzEmployeeInfo;
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;

public interface ZjTzEmployeeInfoService {

    public ResponseEntity getZjTzEmployeeInfoListByCondition(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity getZjTzEmployeeInfoDetails(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity saveZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity updateZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeInfo(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList);

    public ZjTzEmployeeInfo exportZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public List<ZjTzEmployeeWork> exportZjTzEmployeeInfoWork(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public List<ZjTzEmployeeEducation> exportZjTzEmployeeInfoEducation(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity printZjTzEmployeeInfo(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList);

    public ResponseEntity getZjTzEmployeeInfoListSelectPerson(ZjTzEmployeeInfo zjTzEmployeeInfo);

	public ResponseEntity updateZjTzEmployeeInfoPersonDepart(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity getZjTzEmployeeInfoByIdCard(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity importProjectZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo);

    public ResponseEntity importProjectZjTzEmployeeInfoList(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList);

    public ResponseEntity leaveOfficeZjTzEmployeeInfoList(List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList);
}

