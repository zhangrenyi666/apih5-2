package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMake;
import com.apih5.mybatis.pojo.ZxSkStock;

public interface ZxSkMakeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMake record);

    int insertSelective(ZxSkMake record);

    ZxSkMake selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMake record);

    int updateByPrimaryKey(ZxSkMake record);

    List<ZxSkMake> selectByZxSkMakeList(ZxSkMake record);

    int batchDeleteUpdateZxSkMake(List<ZxSkMake> recordList, ZxSkMake record);

    int checkZxSkMake(ZxSkMake zxSkMake);

    List<ZxSkStock> selectResource(ZxSkMake zxSkMake);



    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
