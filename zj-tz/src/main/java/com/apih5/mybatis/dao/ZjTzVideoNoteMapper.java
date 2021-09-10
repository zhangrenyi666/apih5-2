package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzVideoNote;

public interface ZjTzVideoNoteMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzVideoNote record);

    int insertSelective(ZjTzVideoNote record);

    ZjTzVideoNote selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzVideoNote record);

    int updateByPrimaryKey(ZjTzVideoNote record);

    List<ZjTzVideoNote> selectByZjTzVideoNoteList(ZjTzVideoNote record);

    int batchDeleteUpdateZjTzVideoNote(List<ZjTzVideoNote> recordList, ZjTzVideoNote record);

}

