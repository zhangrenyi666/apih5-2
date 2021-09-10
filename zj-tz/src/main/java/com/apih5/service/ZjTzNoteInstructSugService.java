package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSug;

public interface ZjTzNoteInstructSugService {

    public ResponseEntity getZjTzNoteInstructSugListByCondition(ZjTzNoteInstructSug zjTzNoteInstructSug);

    public ResponseEntity getZjTzNoteInstructSugDetails(ZjTzNoteInstructSug zjTzNoteInstructSug);

    public ResponseEntity saveZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug);

    public ResponseEntity updateZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug);

    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList);

	public ResponseEntity toHomeShowZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug);

	public ResponseEntity batchReleaseZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList);

	public ResponseEntity batchRecallZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList);

	public ResponseEntity batchExportZjTzNoteInstructSugFile(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList);

	public ResponseEntity getHomeZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug);

}

