package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;

public interface ZxSaFsStatisticsDetailService {

    public ResponseEntity getZxSaFsStatisticsDetailListByCondition(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail);

    public ResponseEntity getZxSaFsStatisticsDetailDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail);

    public ResponseEntity saveZxSaFsStatisticsDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail);

    public ResponseEntity updateZxSaFsStatisticsDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail);

    public ResponseEntity batchDeleteUpdateZxSaFsStatisticsDetail(List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
