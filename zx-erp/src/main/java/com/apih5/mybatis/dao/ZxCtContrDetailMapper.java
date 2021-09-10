package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrDetail;

public interface ZxCtContrDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrDetail record);

    int insertSelective(ZxCtContrDetail record);

    ZxCtContrDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrDetail record);

    int updateByPrimaryKey(ZxCtContrDetail record);

    List<ZxCtContrDetail> selectByZxCtContrDetailList(ZxCtContrDetail record);

    int batchDeleteUpdateZxCtContrDetail(List<ZxCtContrDetail> recordList, ZxCtContrDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
