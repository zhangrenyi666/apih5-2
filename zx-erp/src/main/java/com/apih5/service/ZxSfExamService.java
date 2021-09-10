package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfExam;

public interface ZxSfExamService {

    public ResponseEntity getZxSfExamListByCondition(ZxSfExam zxSfExam);

    public ResponseEntity getZxSfExamDetail(ZxSfExam zxSfExam);

    public ResponseEntity saveZxSfExam(ZxSfExam zxSfExam);

    public ResponseEntity updateZxSfExam(ZxSfExam zxSfExam);

    public ResponseEntity batchDeleteUpdateZxSfExam(List<ZxSfExam> zxSfExamList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateZxSfExamStatus(ZxSfExam zxSfExam);
    
    public ResponseEntity updateZxSfExamStatusju(ZxSfExam zxSfExam);
}
