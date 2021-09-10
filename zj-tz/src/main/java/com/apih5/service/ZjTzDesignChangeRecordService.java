package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;

public interface ZjTzDesignChangeRecordService {

    public ResponseEntity getZjTzDesignChangeRecordListByCondition(ZjTzDesignChangeRecord zjTzDesignChangeRecord);

    public ResponseEntity getZjTzDesignChangeRecordDetails(ZjTzDesignChangeRecord zjTzDesignChangeRecord);

    public ResponseEntity saveZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord);

    public ResponseEntity updateZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord);

    public ResponseEntity batchDeleteUpdateZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList);

	public ResponseEntity batchReleaseZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList);

	public ResponseEntity batchRecallZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList);

	public ResponseEntity checkAndFinishZjTzDesignChangeRecord(ZjTzDesignChangeRecord zjTzDesignChangeRecord);
}

