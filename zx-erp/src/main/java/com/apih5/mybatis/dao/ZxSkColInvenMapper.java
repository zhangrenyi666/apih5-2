package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkColInven;
import com.apih5.mybatis.pojo.ZxSkColInvenItem;

public interface ZxSkColInvenMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkColInven record);

    int insertSelective(ZxSkColInven record);

    ZxSkColInven selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkColInven record);

    int updateByPrimaryKey(ZxSkColInven record);

    List<ZxSkColInven> selectByZxSkColInvenList(ZxSkColInven record);

    int batchDeleteUpdateZxSkColInven(List<ZxSkColInven> recordList, ZxSkColInven record);

    List<ZxSkColInven> getZxSkColInvenInOrgList(ZxSkColInven zxSkColInven);

    List<ZxSkColInvenItem> getZxSkColInvenResourceList(ZxSkColInven zxSkColInven);



    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
