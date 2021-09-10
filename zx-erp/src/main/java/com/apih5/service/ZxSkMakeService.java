package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMake;

public interface ZxSkMakeService {

    public ResponseEntity getZxSkMakeListByCondition(ZxSkMake zxSkMake);

    public ResponseEntity getZxSkMakeDetail(ZxSkMake zxSkMake);

    public ResponseEntity saveZxSkMake(ZxSkMake zxSkMake);

    public ResponseEntity updateZxSkMake(ZxSkMake zxSkMake);

    public ResponseEntity batchDeleteUpdateZxSkMake(List<ZxSkMake> zxSkMakeList);

    public ResponseEntity checkZxSkMake(ZxSkMake zxSkMake);

    public ResponseEntity startZxSkMake(ZxSkMake zxSkMake);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
