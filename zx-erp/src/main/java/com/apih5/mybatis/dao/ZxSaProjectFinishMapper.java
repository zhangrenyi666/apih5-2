package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectFinish;

public interface ZxSaProjectFinishMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectFinish record);

    int insertSelective(ZxSaProjectFinish record);

    ZxSaProjectFinish selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectFinish record);

    int updateByPrimaryKey(ZxSaProjectFinish record);

    List<ZxSaProjectFinish> selectByZxSaProjectFinishList(ZxSaProjectFinish record);

    int batchDeleteUpdateZxSaProjectFinish(List<ZxSaProjectFinish> recordList, ZxSaProjectFinish record);

	List<ZxSaProjectFinish> getZxSaProjectUnFinishList(ZxSaProjectFinish zxSaProjectFinish);

}

