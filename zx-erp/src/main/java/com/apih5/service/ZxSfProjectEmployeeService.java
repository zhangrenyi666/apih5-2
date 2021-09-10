package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfProjectEmployee;

public interface ZxSfProjectEmployeeService {

    public ResponseEntity getZxSfProjectEmployeeListByCondition(ZxSfProjectEmployee zxSfProjectEmployee);

    public ResponseEntity getZxSfProjectEmployeeDetail(ZxSfProjectEmployee zxSfProjectEmployee);

    public ResponseEntity saveZxSfProjectEmployee(ZxSfProjectEmployee zxSfProjectEmployee);

    public ResponseEntity updateZxSfProjectEmployee(ZxSfProjectEmployee zxSfProjectEmployee);

    public ResponseEntity batchDeleteUpdateZxSfProjectEmployee(List<ZxSfProjectEmployee> zxSfProjectEmployeeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getuReportFormList(ZxSfProjectEmployee zxSfProjectEmployee);

    public List<ZxSfProjectEmployee> uReportForm(ZxSfProjectEmployee zxSfProjectEmployee);

    public ResponseEntity getuReportFormComList(ZxSfProjectEmployee zxSfProjectEmployee);

    public List<ZxSfProjectEmployee> uReportFormCom(ZxSfProjectEmployee zxSfProjectEmployee);


}
