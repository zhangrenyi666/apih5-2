package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfSpecialEmpItem;

public interface ZxSfSpecialEmpItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfSpecialEmpItem record);

    int insertSelective(ZxSfSpecialEmpItem record);

    ZxSfSpecialEmpItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfSpecialEmpItem record);

    int updateByPrimaryKey(ZxSfSpecialEmpItem record);

    List<ZxSfSpecialEmpItem> selectByZxSfSpecialEmpItemList(ZxSfSpecialEmpItem record);

    int batchDeleteUpdateZxSfSpecialEmpItem(List<ZxSfSpecialEmpItem> recordList, ZxSfSpecialEmpItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfSpecialEmpItem> UreportForm(ZxSfSpecialEmpItem record);

    List<ZxSfSpecialEmpItem> UreportFormCom(ZxSfSpecialEmpItem record);
}
