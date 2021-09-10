package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfOtherAddFileReport;

public interface ZxSfOtherAddFileReportService {

    public ResponseEntity getZxSfOtherAddFileReportListByCondition(ZxSfOtherAddFileReport zxSfOtherAddFileReport);

    public ResponseEntity getZxSfOtherAddFileReportDetail(ZxSfOtherAddFileReport zxSfOtherAddFileReport);

    public ResponseEntity saveZxSfOtherAddFileReport(ZxSfOtherAddFileReport zxSfOtherAddFileReport);

    public ResponseEntity updateZxSfOtherAddFileReport(ZxSfOtherAddFileReport zxSfOtherAddFileReport);

    public ResponseEntity batchDeleteUpdateZxSfOtherAddFileReport(List<ZxSfOtherAddFileReport> zxSfOtherAddFileReportList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
