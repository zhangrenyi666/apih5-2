package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSugRecord;

public interface ZjTzNoteInstructSugRecordService {

    public ResponseEntity getZjTzNoteInstructSugRecordListByCondition(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord);

    public ResponseEntity getZjTzNoteInstructSugRecordDetails(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord);

    public ResponseEntity saveZjTzNoteInstructSugRecord(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord);

    public ResponseEntity updateZjTzNoteInstructSugRecord(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord);

    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSugRecord(List<ZjTzNoteInstructSugRecord> zjTzNoteInstructSugRecordList);

}

