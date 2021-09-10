package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfSpecialEmp;

public interface ZxSfSpecialEmpService {

    public ResponseEntity getZxSfSpecialEmpListByCondition(ZxSfSpecialEmp zxSfSpecialEmp);

    public ResponseEntity getZxSfSpecialEmpDetail(ZxSfSpecialEmp zxSfSpecialEmp);

    public ResponseEntity saveZxSfSpecialEmp(ZxSfSpecialEmp zxSfSpecialEmp);

    public ResponseEntity updateZxSfSpecialEmp(ZxSfSpecialEmp zxSfSpecialEmp);

    public ResponseEntity batchDeleteUpdateZxSfSpecialEmp(List<ZxSfSpecialEmp> zxSfSpecialEmpList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
