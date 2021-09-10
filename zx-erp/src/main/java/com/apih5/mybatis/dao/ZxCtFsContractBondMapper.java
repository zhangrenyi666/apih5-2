package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsContractBond;

public interface ZxCtFsContractBondMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsContractBond record);

    int insertSelective(ZxCtFsContractBond record);

    ZxCtFsContractBond selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtFsContractBond record);

    int updateByPrimaryKey(ZxCtFsContractBond record);

    List<ZxCtFsContractBond> selectByZxCtFsContractBondList(ZxCtFsContractBond record);

    int batchDeleteUpdateZxCtFsContractBond(List<ZxCtFsContractBond> recordList, ZxCtFsContractBond record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
