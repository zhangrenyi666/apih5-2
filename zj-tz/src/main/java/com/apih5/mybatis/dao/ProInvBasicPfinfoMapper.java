package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ProInvBasicPfinfo;

public interface ProInvBasicPfinfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInvBasicPfinfo record);

    int insertSelective(ProInvBasicPfinfo record);

    ProInvBasicPfinfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInvBasicPfinfo record);

    int updateByPrimaryKey(ProInvBasicPfinfo record);

    List<ProInvBasicPfinfo> selectByProInvBasicPfinfoList(ProInvBasicPfinfo record);

    int batchDeleteUpdateProInvBasicPfinfo(List<ProInvBasicPfinfo> recordList, ProInvBasicPfinfo record);

}

