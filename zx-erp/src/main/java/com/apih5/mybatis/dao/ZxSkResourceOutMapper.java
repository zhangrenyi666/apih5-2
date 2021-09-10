package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResourceOut;

public interface ZxSkResourceOutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResourceOut record);

    int insertSelective(ZxSkResourceOut record);

    ZxSkResourceOut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResourceOut record);

    int updateByPrimaryKey(ZxSkResourceOut record);

    List<ZxSkResourceOut> selectByZxSkResourceOutList(ZxSkResourceOut record);

    int batchDeleteUpdateZxSkResourceOut(List<ZxSkResourceOut> recordList, ZxSkResourceOut record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkResourceOut> selectZxSkResourceOut(ZxSkResourceOut record);
}
