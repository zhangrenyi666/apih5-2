package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCcResCategory;

public interface ZxCcResCategoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCcResCategory record);

    int insertSelective(ZxCcResCategory record);

    ZxCcResCategory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCcResCategory record);

    int updateByPrimaryKey(ZxCcResCategory record);

    List<ZxCcResCategory> selectByZxCcResCategoryList(ZxCcResCategory record);

    int batchDeleteUpdateZxCcResCategory(List<ZxCcResCategory> recordList, ZxCcResCategory record);

	List<ZxCcResCategory> getZxCcResCategoryItemList(ZxCcResCategory zxCcResCategory);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
