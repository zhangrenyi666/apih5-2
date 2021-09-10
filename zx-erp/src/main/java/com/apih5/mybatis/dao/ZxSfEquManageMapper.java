package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEquManage;

public interface ZxSfEquManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEquManage record);

    int insertSelective(ZxSfEquManage record);

    ZxSfEquManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEquManage record);

    int updateByPrimaryKey(ZxSfEquManage record);

    List<ZxSfEquManage> selectByZxSfEquManageList(ZxSfEquManage record);

    int batchDeleteUpdateZxSfEquManage(List<ZxSfEquManage> recordList, ZxSfEquManage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
