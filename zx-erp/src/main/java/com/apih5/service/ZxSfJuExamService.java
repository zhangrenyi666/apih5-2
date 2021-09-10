package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfJuExam;

public interface ZxSfJuExamService {

    public ResponseEntity getZxSfJuExamListByCondition(ZxSfJuExam zxSfJuExam);

    public ResponseEntity getZxSfJuExamDetail(ZxSfJuExam zxSfJuExam);

    public ResponseEntity saveZxSfJuExam(ZxSfJuExam zxSfJuExam);

    public ResponseEntity updateZxSfJuExam(ZxSfJuExam zxSfJuExam);

    public ResponseEntity batchDeleteUpdateZxSfJuExam(List<ZxSfJuExam> zxSfJuExamList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
