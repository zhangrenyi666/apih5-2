package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkReturns;

public interface ZxSkReturnsService {

    public ResponseEntity getZxSkReturnsListByCondition(ZxSkReturns zxSkReturns);

    public ResponseEntity getZxSkReturnsDetail(ZxSkReturns zxSkReturns);

    public ResponseEntity saveZxSkReturns(ZxSkReturns zxSkReturns);

    public ResponseEntity updateZxSkReturns(ZxSkReturns zxSkReturns);

    public ResponseEntity batchDeleteUpdateZxSkReturns(List<ZxSkReturns> zxSkReturnsList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkReturnsNo(ZxSkReturns zxSkReturns);

    public ResponseEntity getZxSkReturnsResourceList(ZxSkReturns zxSkReturns);

    public ResponseEntity checkZxSkReturns(ZxSkReturns zxSkReturns);

}
