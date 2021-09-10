package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfOtherAddFile;

public interface ZxSfOtherAddFileMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfOtherAddFile record);

    int insertSelective(ZxSfOtherAddFile record);

    ZxSfOtherAddFile selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfOtherAddFile record);

    int updateByPrimaryKey(ZxSfOtherAddFile record);

    List<ZxSfOtherAddFile> selectByZxSfOtherAddFileList(ZxSfOtherAddFile record);

    int batchDeleteUpdateZxSfOtherAddFile(List<ZxSfOtherAddFile> recordList, ZxSfOtherAddFile record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
