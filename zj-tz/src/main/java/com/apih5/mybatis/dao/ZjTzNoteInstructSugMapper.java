package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSug;

public interface ZjTzNoteInstructSugMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzNoteInstructSug record);

    int insertSelective(ZjTzNoteInstructSug record);

    ZjTzNoteInstructSug selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzNoteInstructSug record);

    int updateByPrimaryKey(ZjTzNoteInstructSug record);

    List<ZjTzNoteInstructSug> selectByZjTzNoteInstructSugList(ZjTzNoteInstructSug record);

    int batchDeleteUpdateZjTzNoteInstructSug(List<ZjTzNoteInstructSug> recordList, ZjTzNoteInstructSug record);

	int batchReleaseZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList,
			ZjTzNoteInstructSug zjTzRules);

	int batchRecallZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList,
			ZjTzNoteInstructSug zjTzRules);
	//公告栏
	List<ZjTzNoteInstructSug> selectHomeZjTzNoteInstructSug(ZjTzNoteInstructSug record);
}

