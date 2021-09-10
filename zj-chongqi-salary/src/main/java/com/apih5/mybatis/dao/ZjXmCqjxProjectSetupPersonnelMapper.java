package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;

public interface ZjXmCqjxProjectSetupPersonnelMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectSetupPersonnel record);

    int insertSelective(ZjXmCqjxProjectSetupPersonnel record);

    ZjXmCqjxProjectSetupPersonnel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectSetupPersonnel record);

    int updateByPrimaryKey(ZjXmCqjxProjectSetupPersonnel record);

    List<ZjXmCqjxProjectSetupPersonnel> selectByZjXmCqjxProjectSetupPersonnelList(ZjXmCqjxProjectSetupPersonnel record);

    int batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(List<ZjXmCqjxProjectSetupPersonnel> recordList, ZjXmCqjxProjectSetupPersonnel record);

}

