package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrClaim;

public interface ZxCtContrClaimMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrClaim record);

    int insertSelective(ZxCtContrClaim record);

    ZxCtContrClaim selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrClaim record);

    int updateByPrimaryKey(ZxCtContrClaim record);

    List<ZxCtContrClaim> selectByZxCtContrClaimList(ZxCtContrClaim record);

    int batchDeleteUpdateZxCtContrClaim(List<ZxCtContrClaim> recordList, ZxCtContrClaim record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
