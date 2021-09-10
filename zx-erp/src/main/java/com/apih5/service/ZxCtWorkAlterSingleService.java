package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtWorkAlterSingle;

public interface ZxCtWorkAlterSingleService {

    public ResponseEntity getZxCtWorkAlterSingleListByCondition(ZxCtWorkAlterSingle zxCtWorkAlterSingle);

    public ResponseEntity getZxCtWorkAlterSingleDetail(ZxCtWorkAlterSingle zxCtWorkAlterSingle);

    public ResponseEntity saveZxCtWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle);

    public ResponseEntity updateZxCtWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle);

    public ResponseEntity batchDeleteUpdateZxCtWorkAlterSingle(List<ZxCtWorkAlterSingle> zxCtWorkAlterSingleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity zxCtCancelWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle);
}
