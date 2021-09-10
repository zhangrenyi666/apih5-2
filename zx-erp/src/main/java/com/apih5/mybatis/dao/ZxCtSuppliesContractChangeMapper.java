package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChange;

public interface ZxCtSuppliesContractChangeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContractChange record);

    int insertSelective(ZxCtSuppliesContractChange record);

    ZxCtSuppliesContractChange selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContractChange record);

    int updateByPrimaryKey(ZxCtSuppliesContractChange record);

    List<ZxCtSuppliesContractChange> selectByZxCtSuppliesContractChangeList(ZxCtSuppliesContractChange record);

    int batchDeleteUpdateZxCtSuppliesContractChange(List<ZxCtSuppliesContractChange> recordList, ZxCtSuppliesContractChange record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
