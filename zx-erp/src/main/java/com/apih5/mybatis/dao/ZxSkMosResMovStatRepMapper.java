package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMosResMovStatRep;
import org.apache.ibatis.annotations.Param;

public interface ZxSkMosResMovStatRepMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMosResMovStatRep record);

    int insertSelective(ZxSkMosResMovStatRep record);

    ZxSkMosResMovStatRep selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMosResMovStatRep record);

    int updateByPrimaryKey(ZxSkMosResMovStatRep record);

    List<ZxSkMosResMovStatRep> selectByZxSkMosResMovStatRepList(ZxSkMosResMovStatRep record);

    int batchDeleteUpdateZxSkMosResMovStatRep(List<ZxSkMosResMovStatRep> recordList, ZxSkMosResMovStatRep record);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSkMosResMovStatRep> filtrateZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep);

}
