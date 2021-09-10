package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;

public interface ZxCtSuppliesContractMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContract record);

    int insertSelective(ZxCtSuppliesContract record);

    ZxCtSuppliesContract selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContract record);

    int updateByPrimaryKey(ZxCtSuppliesContract record);

    List<ZxCtSuppliesContract> selectByZxCtSuppliesContractList(ZxCtSuppliesContract record);
    
    List<ZxCtSuppliesContract> selectByZxCtSuppliesContractListAddSettlement(ZxCtSuppliesContract record);

    int batchDeleteUpdateZxCtSuppliesContract(List<ZxCtSuppliesContract> recordList, ZxCtSuppliesContract record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtSuppliesContract> selectByZxCtSuppliesContractListByFirstId(ZxCtSuppliesContract record);
}
