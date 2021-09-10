package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;

public interface ZxCrProjCreditBadBehaService {

    public ResponseEntity getZxCrProjCreditBadBehaListByCondition(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha);

    public ResponseEntity getZxCrProjCreditBadBehaDetail(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha);

    public ResponseEntity saveZxCrProjCreditBadBeha(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha);

    public ResponseEntity updateZxCrProjCreditBadBeha(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha);

    public ResponseEntity batchDeleteUpdateZxCrProjCreditBadBeha(List<ZxCrProjCreditBadBeha> zxCrProjCreditBadBehaList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
