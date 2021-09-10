package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWebBg;

public interface SysWebBgMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWebBg record);

    int insertSelective(SysWebBg record);

    SysWebBg selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWebBg record);

    int updateByPrimaryKey(SysWebBg record);

    List<SysWebBg> selectBySysWebBgList(SysWebBg record);

    int batchDeleteUpdateSysWebBg(List<SysWebBg> recordList, SysWebBg record);

}

