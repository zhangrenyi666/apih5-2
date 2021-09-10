package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDatum;

public interface ZxCrCustomerInfoDatumService {

    public ResponseEntity getZxCrCustomerInfoDatumListByCondition(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum);

    public ResponseEntity getZxCrCustomerInfoDatumDetail(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum);

    public ResponseEntity saveZxCrCustomerInfoDatum(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum);

    public ResponseEntity updateZxCrCustomerInfoDatum(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum);

    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDatum(List<ZxCrCustomerInfoDatum> zxCrCustomerInfoDatumList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
