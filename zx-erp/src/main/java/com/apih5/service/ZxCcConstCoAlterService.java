package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCcConstCoAlter;

public interface ZxCcConstCoAlterService {

    public ResponseEntity getZxCcConstCoAlterListByCondition(ZxCcConstCoAlter zxCcConstCoAlter);

    public ResponseEntity getZxCcConstCoAlterDetail(ZxCcConstCoAlter zxCcConstCoAlter);

    public ResponseEntity saveZxCcConstCoAlter(ZxCcConstCoAlter zxCcConstCoAlter);

    public ResponseEntity updateZxCcConstCoAlter(ZxCcConstCoAlter zxCcConstCoAlter);

    public ResponseEntity batchDeleteUpdateZxCcConstCoAlter(List<ZxCcConstCoAlter> zxCcConstCoAlterList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxCcConstCoAlterWorkDetail(ZxCcConstCoAlter zxCcConstCoAlter);

    public ResponseEntity updateZxCcConstCoAlterAndWorkDetail(ZxCcConstCoAlter zxCcConstCoAlter);
}
