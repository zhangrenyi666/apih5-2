package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrap;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrapItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnOverScrapMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverScrap record);

    int insertSelective(ZxSkTurnOverScrap record);

    ZxSkTurnOverScrap selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverScrap record);

    int updateByPrimaryKey(ZxSkTurnOverScrap record);

    List<ZxSkTurnOverScrap> selectByZxSkTurnOverScrapList(ZxSkTurnOverScrap record);

    int batchDeleteUpdateZxSkTurnOverScrap(List<ZxSkTurnOverScrap> recordList, ZxSkTurnOverScrap record);

    List<ZxSkTurnOverScrapItem> getZxSkTurnOverScrapResourceList(ZxSkTurnOverScrap zxSkTurnOverScrap);

    int getZxSkTurnOverScrapCount(@Param("date") String date, @Param("orgID")String orgID);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
