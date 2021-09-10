package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCmFilterOrg;

public interface ZxCmFilterOrgMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCmFilterOrg record);

    int insertSelective(ZxCmFilterOrg record);

    ZxCmFilterOrg selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCmFilterOrg record);

    int updateByPrimaryKey(ZxCmFilterOrg record);

    List<ZxCmFilterOrg> selectByZxCmFilterOrgList(ZxCmFilterOrg record);

    int batchDeleteUpdateZxCmFilterOrg(List<ZxCmFilterOrg> recordList, ZxCmFilterOrg record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
