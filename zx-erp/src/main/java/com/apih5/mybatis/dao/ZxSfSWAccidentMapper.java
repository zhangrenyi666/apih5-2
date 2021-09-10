package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfSWAccident;

public interface ZxSfSWAccidentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfSWAccident record);

    int insertSelective(ZxSfSWAccident record);

    ZxSfSWAccident selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfSWAccident record);

    int updateByPrimaryKey(ZxSfSWAccident record);

    List<ZxSfSWAccident> selectByZxSfSWAccidentList(ZxSfSWAccident record);

    int batchDeleteUpdateZxSfSWAccident(List<ZxSfSWAccident> recordList, ZxSfSWAccident record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
