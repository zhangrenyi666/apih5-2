package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOther;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtOtherService {

    public ResponseEntity getZxCtOtherListByCondition(ZxCtOther zxCtOther);

    public ResponseEntity getZxCtOtherDetail(ZxCtOther zxCtOther);

    public ResponseEntity saveZxCtOther(ZxCtOther zxCtOther);

    public ResponseEntity updateZxCtOther(ZxCtOther zxCtOther);

    public ResponseEntity batchDeleteUpdateZxCtOther(List<ZxCtOther> zxCtOtherList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity zxCtOtherReviewApply(ZxCtOther zxCtOther);

    public ResponseEntity getZxCtOtherContractNo(ZxCtOther zxCtOther);

    public void exportZxCtOther(ZxCtOther zxCtOther, HttpServletResponse response);

    public void downLoadZxCtOtherFile(ZxCtOther zxCtOther, HttpServletResponse response);

    public ResponseEntity zxCtOtherReviewApplyCheck(ZxCtOther zxCtOther);
}
