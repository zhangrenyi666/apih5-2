package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverIn;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnoverInMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverIn record);

    int insertSelective(ZxSkTurnoverIn record);

    ZxSkTurnoverIn selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverIn record);

    int updateByPrimaryKey(ZxSkTurnoverIn record);

    List<ZxSkTurnoverIn> selectByZxSkTurnoverInList(ZxSkTurnoverIn record);

    int batchDeleteUpdateZxSkTurnoverIn(List<ZxSkTurnoverIn> recordList, ZxSkTurnoverIn record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int checkZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn);

    List<ZxSkTurnoverInItem> getZxSkTurnoverInResourceList(ZxSkTurnoverIn zxSkTurnoverIn);

    List<ZxSkTurnoverIn> getZxSkTurnoverInListForReport(ZxSkTurnoverIn zxSkTurnoverIn);

    int getZxSkTurnoverInCount(@Param("date")String date,@Param("orgID") String orgID);
}
