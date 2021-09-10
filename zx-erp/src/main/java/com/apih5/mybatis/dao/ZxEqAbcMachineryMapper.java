package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqAbcMachinery;

public interface ZxEqAbcMachineryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqAbcMachinery record);

    int insertSelective(ZxEqAbcMachinery record);

    ZxEqAbcMachinery selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqAbcMachinery record);

    int updateByPrimaryKey(ZxEqAbcMachinery record);

    List<ZxEqAbcMachinery> selectByZxEqAbcMachineryList(ZxEqAbcMachinery record);

    int batchDeleteUpdateZxEqAbcMachinery(List<ZxEqAbcMachinery> recordList, ZxEqAbcMachinery record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqAbcMachinery> selectZxEqAbcMachinery(ZxEqAbcMachinery record);
}
