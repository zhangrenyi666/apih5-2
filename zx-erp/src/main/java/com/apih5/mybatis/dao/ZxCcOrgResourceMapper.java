package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCcOrgResource;

public interface ZxCcOrgResourceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCcOrgResource record);

    int insertSelective(ZxCcOrgResource record);

    ZxCcOrgResource selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCcOrgResource record);

    int updateByPrimaryKey(ZxCcOrgResource record);

    List<ZxCcOrgResource> selectByZxCcOrgResourceList(ZxCcOrgResource record);

    int batchDeleteUpdateZxCcOrgResource(List<ZxCcOrgResource> recordList, ZxCcOrgResource record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
