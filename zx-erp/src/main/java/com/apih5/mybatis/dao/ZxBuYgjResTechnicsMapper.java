package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;
import org.apache.ibatis.annotations.Param;

public interface ZxBuYgjResTechnicsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuYgjResTechnics record);

    int insertSelective(ZxBuYgjResTechnics record);

    ZxBuYgjResTechnics selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuYgjResTechnics record);

    int updateByPrimaryKey(ZxBuYgjResTechnics record);

    List<ZxBuYgjResTechnics> selectByZxBuYgjResTechnicsList(ZxBuYgjResTechnics record);

    int batchDeleteUpdateZxBuYgjResTechnics(List<ZxBuYgjResTechnics> recordList, ZxBuYgjResTechnics record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxBuWorks getWorkNoArrWorkNameArr(String id);

    int batchDeleteUpdateRemoveRelevanceZxBuYgjResTechnics(List<String> recordList, ZxBuYgjResTechnics record);

    int batchDeleteUpdateZxBuYgjResTechnicsByBillID(List<ZxBuYgjResTechnics> recordList, ZxBuYgjResTechnics record);

    List<ZxBuYgjResTechnics> selectByBaseZxBuYgjResTechnics(@Param("zxBuYgjResTechnicsListNew") List<ZxBuYgjResTechnics> zxBuYgjResTechnicsListNew);

}
