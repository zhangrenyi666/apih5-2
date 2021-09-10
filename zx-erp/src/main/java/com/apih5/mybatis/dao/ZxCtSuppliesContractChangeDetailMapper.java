package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChangeDetail;

public interface ZxCtSuppliesContractChangeDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContractChangeDetail record);

    int insertSelective(ZxCtSuppliesContractChangeDetail record);

    ZxCtSuppliesContractChangeDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContractChangeDetail record);

    int updateByPrimaryKey(ZxCtSuppliesContractChangeDetail record);

    List<ZxCtSuppliesContractChangeDetail> selectByZxCtSuppliesContractChangeDetailList(ZxCtSuppliesContractChangeDetail record);

    int batchDeleteUpdateZxCtSuppliesContractChangeDetail(List<ZxCtSuppliesContractChangeDetail> recordList, ZxCtSuppliesContractChangeDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
