package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;

public interface ZjTzExecutivePersonnelMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzExecutivePersonnel record);

    int insertSelective(ZjTzExecutivePersonnel record);

    ZjTzExecutivePersonnel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzExecutivePersonnel record);

    int updateByPrimaryKey(ZjTzExecutivePersonnel record);

    List<ZjTzExecutivePersonnel> selectByZjTzExecutivePersonnelList(ZjTzExecutivePersonnel record);

    int batchDeleteUpdateZjTzExecutivePersonnel(List<ZjTzExecutivePersonnel> recordList, ZjTzExecutivePersonnel record);

	List<ZjTzExecutivePersonnel> uReportZjTzExecutivePersonnelList(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

}

