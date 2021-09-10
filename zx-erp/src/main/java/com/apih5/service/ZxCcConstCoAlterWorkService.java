package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCcConstCoAlterWork;

public interface ZxCcConstCoAlterWorkService {

    public ResponseEntity getZxCcConstCoAlterWorkListByCondition(ZxCcConstCoAlterWork zxCcConstCoAlterWork);

    public ResponseEntity getZxCcConstCoAlterWorkDetail(ZxCcConstCoAlterWork zxCcConstCoAlterWork);

    public ResponseEntity saveZxCcConstCoAlterWork(ZxCcConstCoAlterWork zxCcConstCoAlterWork);

    public ResponseEntity updateZxCcConstCoAlterWork(ZxCcConstCoAlterWork zxCcConstCoAlterWork);

    public ResponseEntity batchDeleteUpdateZxCcConstCoAlterWork(List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
