package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEquManage;

public interface ZxSfEquManageService {

    public ResponseEntity getZxSfEquManageListByCondition(ZxSfEquManage zxSfEquManage);

    public ResponseEntity getZxSfEquManageDetail(ZxSfEquManage zxSfEquManage);

    public ResponseEntity saveZxSfEquManage(ZxSfEquManage zxSfEquManage);

    public ResponseEntity updateZxSfEquManage(ZxSfEquManage zxSfEquManage);

    public ResponseEntity batchDeleteUpdateZxSfEquManage(List<ZxSfEquManage> zxSfEquManageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
