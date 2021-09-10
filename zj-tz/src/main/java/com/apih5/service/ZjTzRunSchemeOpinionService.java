package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRunSchemeOpinion;

public interface ZjTzRunSchemeOpinionService {

    public ResponseEntity getZjTzRunSchemeOpinionListByCondition(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion);

    public ResponseEntity getZjTzRunSchemeOpinionDetails(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion);

    public ResponseEntity saveZjTzRunSchemeOpinion(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion);

    public ResponseEntity updateZjTzRunSchemeOpinion(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion);

    public ResponseEntity batchDeleteUpdateZjTzRunSchemeOpinion(List<ZjTzRunSchemeOpinion> zjTzRunSchemeOpinionList);

}

