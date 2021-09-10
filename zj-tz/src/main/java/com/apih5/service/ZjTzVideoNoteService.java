package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzVideoNote;

public interface ZjTzVideoNoteService {

    public ResponseEntity getZjTzVideoNoteListByCondition(ZjTzVideoNote zjTzVideoNote);

    public ResponseEntity getZjTzVideoNoteDetails(ZjTzVideoNote zjTzVideoNote);

    public ResponseEntity saveZjTzVideoNote(ZjTzVideoNote zjTzVideoNote);

    public ResponseEntity updateZjTzVideoNote(ZjTzVideoNote zjTzVideoNote);

    public ResponseEntity batchDeleteUpdateZjTzVideoNote(List<ZjTzVideoNote> zjTzVideoNoteList);

}

