package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqResCategory;

public interface ZxEqResCategoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqResCategory record);

    int insertSelective(ZxEqResCategory record);

    ZxEqResCategory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqResCategory record);

    int updateByPrimaryKey(ZxEqResCategory record);

    List<ZxEqResCategory> selectByZxEqResCategoryList(ZxEqResCategory record);

    int batchDeleteUpdateZxEqResCategory(List<ZxEqResCategory> recordList, ZxEqResCategory record);

	int batchStopUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList, ZxEqResCategory zxEqResCategory);

	int batchStartUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList, ZxEqResCategory zxEqResCategory);

	List<ZxEqResCategory> getZxEqResCategoryItemList(ZxEqResCategory zxEqResCategory);

//	List<ZxEqResCategory> getZxEqResCategoryTree(ZxEqResCategory zxEqResCategory);

}

