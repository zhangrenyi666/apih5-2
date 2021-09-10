package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkSporadic;

public interface ZxSkSporadicService {

    public ResponseEntity getZxSkSporadicListByCondition(ZxSkSporadic zxSkSporadic);

    public ResponseEntity getZxSkSporadicDetails(ZxSkSporadic zxSkSporadic);

    public ResponseEntity saveZxSkSporadic(ZxSkSporadic zxSkSporadic);

    public ResponseEntity updateZxSkSporadic(ZxSkSporadic zxSkSporadic);

    public ResponseEntity batchDeleteUpdateZxSkSporadic(List<ZxSkSporadic> zxSkSporadicList);

}

