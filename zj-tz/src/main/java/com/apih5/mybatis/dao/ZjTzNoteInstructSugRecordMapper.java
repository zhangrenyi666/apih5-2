package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSugRecord;

public interface ZjTzNoteInstructSugRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzNoteInstructSugRecord record);

    int insertSelective(ZjTzNoteInstructSugRecord record);

    ZjTzNoteInstructSugRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzNoteInstructSugRecord record);

    int updateByPrimaryKey(ZjTzNoteInstructSugRecord record);

    List<ZjTzNoteInstructSugRecord> selectByZjTzNoteInstructSugRecordList(ZjTzNoteInstructSugRecord record);

    int batchDeleteUpdateZjTzNoteInstructSugRecord(List<ZjTzNoteInstructSugRecord> recordList, ZjTzNoteInstructSugRecord record);

}

