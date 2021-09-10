package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkReturns;
import com.apih5.mybatis.pojo.ZxSkReturnsItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkReturnsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkReturns record);

    int insertSelective(ZxSkReturns record);

    ZxSkReturns selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkReturns record);

    int updateByPrimaryKey(ZxSkReturns record);

    List<ZxSkReturns> selectByZxSkReturnsList(ZxSkReturns record);

    int batchDeleteUpdateZxSkReturns(List<ZxSkReturns> recordList, ZxSkReturns record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSkReturnsItem> getZxSkReturnsResourceList(ZxSkReturns zxSkReturns);

    int checkZxSkReturns(ZxSkReturns zxSkReturns);

    int getZxSkReturnsCount(@Param("date") String date, @Param("orgID")String orgID);
}
