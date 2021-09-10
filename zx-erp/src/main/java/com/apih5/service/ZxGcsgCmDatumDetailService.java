package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCmDatumDetail;

public interface ZxGcsgCmDatumDetailService {

    public ResponseEntity getZxGcsgCmDatumDetailListByCondition(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail);

    public ResponseEntity getZxGcsgCmDatumDetailDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail);

    public ResponseEntity saveZxGcsgCmDatumDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail);

    public ResponseEntity updateZxGcsgCmDatumDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail);

    public ResponseEntity batchDeleteUpdateZxGcsgCmDatumDetail(List<ZxGcsgCmDatumDetail> zxGcsgCmDatumDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
