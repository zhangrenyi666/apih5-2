package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCcConstCoAlter;
import com.apih5.mybatis.pojo.ZxCtContrApply;
import com.apih5.mybatis.pojo.ZxCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxSkStockTransferSalesReturn;
import org.springframework.web.multipart.MultipartFile;

public interface ZxCtContrApplyService {

    public ResponseEntity getZxCtContrApplyListByCondition(ZxCtContrApply zxCtContrApply);

    public ResponseEntity getZxCtContrApplyDetail(ZxCtContrApply zxCtContrApply);

    public ResponseEntity saveZxCtContrApply(ZxCtContrApply zxCtContrApply);

    public ResponseEntity updateZxCtContrApply(ZxCtContrApply zxCtContrApply);

    public ResponseEntity batchDeleteUpdateZxCtContrApply(List<ZxCtContrApply> zxCtContrApplyList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxCtContrApplyWorksDetailList(ZxCtContrApply zxCtContrApply);

    public ResponseEntity importZxCtContrApplyWorks(ZxCtContrApplyWorks zxCtContrApplyWorks);

    public ResponseEntity getZxCtContrApplyWorksTree(ZxCtContrApplyWorks zxCtContrApplyWorks);

    public ResponseEntity getZxCtContrApplyWorksTreeData(ZxCtContrApplyWorks zxCtContrApplyWorks);

    public ResponseEntity getZxCtContrApplyFirstName(ZxCtContrApply zxCtContrApply);
}
