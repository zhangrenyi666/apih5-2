package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtDaywork;

public interface ZxCtDayworkService {

    public ResponseEntity getZxCtDayworkListByCondition(ZxCtDaywork zxCtDaywork);

    public ResponseEntity getZxCtDayworkDetail(ZxCtDaywork zxCtDaywork);

    public ResponseEntity saveZxCtDaywork(ZxCtDaywork zxCtDaywork);

    public ResponseEntity updateZxCtDaywork(ZxCtDaywork zxCtDaywork);

    public ResponseEntity batchDeleteUpdateZxCtDaywork(List<ZxCtDaywork> zxCtDayworkList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
