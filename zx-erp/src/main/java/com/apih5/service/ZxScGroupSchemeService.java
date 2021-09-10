package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxScGroupScheme;

public interface ZxScGroupSchemeService {

    public ResponseEntity getZxScGroupSchemeListByCondition(ZxScGroupScheme zxScGroupScheme);

    public ResponseEntity getZxScGroupSchemeDetail(ZxScGroupScheme zxScGroupScheme);

    public ResponseEntity saveZxScGroupScheme(ZxScGroupScheme zxScGroupScheme);

    public ResponseEntity updateZxScGroupScheme(ZxScGroupScheme zxScGroupScheme);

    public ResponseEntity batchDeleteUpdateZxScGroupScheme(List<ZxScGroupScheme> zxScGroupSchemeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxScGroupSchemeSequence(ZxScGroupScheme zxScGroupScheme);

    public ResponseEntity zxScGroupSchemeReviewApply(ZxScGroupScheme zxScGroupScheme);
}
