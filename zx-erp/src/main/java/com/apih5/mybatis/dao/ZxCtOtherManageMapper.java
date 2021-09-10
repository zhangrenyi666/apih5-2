package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOtherManage;

public interface ZxCtOtherManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOtherManage record);

    int insertSelective(ZxCtOtherManage record);

    ZxCtOtherManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOtherManage record);

    int updateByPrimaryKey(ZxCtOtherManage record);

    List<ZxCtOtherManage> selectByZxCtOtherManageList(ZxCtOtherManage record);

    int batchDeleteUpdateZxCtOtherManage(List<ZxCtOtherManage> recordList, ZxCtOtherManage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtOtherManage> selectByOrgIdZxCtOtherManageList(ZxCtOtherManage dbZxCtOtherManage);
}
