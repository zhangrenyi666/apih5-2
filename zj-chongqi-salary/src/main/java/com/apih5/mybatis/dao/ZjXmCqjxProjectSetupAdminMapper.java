package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;

public interface ZjXmCqjxProjectSetupAdminMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmCqjxProjectSetupAdmin record);

    int insertSelective(ZjXmCqjxProjectSetupAdmin record);

    ZjXmCqjxProjectSetupAdmin selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmCqjxProjectSetupAdmin record);

    int updateByPrimaryKey(ZjXmCqjxProjectSetupAdmin record);

    List<ZjXmCqjxProjectSetupAdmin> selectByZjXmCqjxProjectSetupAdminList(ZjXmCqjxProjectSetupAdmin record);

    int batchDeleteUpdateZjXmCqjxProjectSetupAdmin(List<ZjXmCqjxProjectSetupAdmin> recordList, ZjXmCqjxProjectSetupAdmin record);

}

