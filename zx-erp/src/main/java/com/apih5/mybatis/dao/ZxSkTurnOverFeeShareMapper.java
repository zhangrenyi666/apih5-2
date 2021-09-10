package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShare;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnOverFeeShareMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverFeeShare record);

    int insertSelective(ZxSkTurnOverFeeShare record);

    ZxSkTurnOverFeeShare selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverFeeShare record);

    int updateByPrimaryKey(ZxSkTurnOverFeeShare record);

    List<ZxSkTurnOverFeeShare> selectByZxSkTurnOverFeeShareList(ZxSkTurnOverFeeShare record);

    int batchDeleteUpdateZxSkTurnOverFeeShare(List<ZxSkTurnOverFeeShare> recordList, ZxSkTurnOverFeeShare record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSkTurnOverFeeShareItem> getZxSkTurnOverFeeShareResourceList(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    int checkZxSkTurnOverFeeShareUpdateNum(@Param("zxSkTurnOverFeeShareItemListNew") List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew);

    int checkZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    int counterCheckZxSkTurnOverFeeShare(@Param("zxSkTurnOverFeeShareItemListNew") List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemListNew);

    int getZxSkTurnOverFeeShareCount(@Param("date") String date,@Param("orgID") String orgID);
}
