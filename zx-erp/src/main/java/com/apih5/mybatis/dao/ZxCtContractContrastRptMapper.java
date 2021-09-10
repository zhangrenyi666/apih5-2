package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContractContrastRpt;

public interface ZxCtContractContrastRptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContractContrastRpt record);

    int insertSelective(ZxCtContractContrastRpt record);

    ZxCtContractContrastRpt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContractContrastRpt record);

    int updateByPrimaryKey(ZxCtContractContrastRpt record);

    List<ZxCtContractContrastRpt> selectByZxCtContractContrastRptList(ZxCtContractContrastRpt record);

    int batchDeleteUpdateZxCtContractContrastRpt(List<ZxCtContractContrastRpt> recordList, ZxCtContractContrastRpt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCtContractContrastRpt> selectZxCtContractContrastRpt(ZxCtContractContrastRpt record);
}
