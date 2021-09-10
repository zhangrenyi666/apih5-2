package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;
import org.apache.ibatis.annotations.Param;

public interface ZxBuWorksMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuWorks record);

    int insertSelective(ZxBuWorks record);

    ZxBuWorks selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuWorks record);

    int updateByPrimaryKey(ZxBuWorks record);

    List<ZxBuWorks> selectByZxBuWorksList(ZxBuWorks record);

    int batchDeleteUpdateZxBuWorks(List<ZxBuWorks> recordList, ZxBuWorks record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxBuWorks> getZxBuWorksTreeList(ZxBuWorks zxBuWorks);

    List<ZxBuYgjResTechnics> selectByZxBuWorksAndzxBuYgjResTechnicsList(@Param("zxBuYgjResTechnicsListNew") List<ZxBuYgjResTechnics> zxBuYgjResTechnicsListNew);

}
