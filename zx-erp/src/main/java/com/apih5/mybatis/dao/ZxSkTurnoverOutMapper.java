package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverOut;
import com.apih5.mybatis.pojo.ZxSkTurnoverOutItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnoverOutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverOut record);

    int insertSelective(ZxSkTurnoverOut record);

    ZxSkTurnoverOut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverOut record);

    int updateByPrimaryKey(ZxSkTurnoverOut record);

    List<ZxSkTurnoverOut> selectByZxSkTurnoverOutList(ZxSkTurnoverOut record);

    int batchDeleteUpdateZxSkTurnoverOut(List<ZxSkTurnoverOut> recordList, ZxSkTurnoverOut record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSkTurnoverOutItem> getZxSkTurnoverOutResourceList(ZxSkTurnoverOut zxSkTurnoverOut);

    int checkZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut);

    int getZxSkTurnoverOutCount(@Param("date") String date,@Param("orgID") String orgID);
}
