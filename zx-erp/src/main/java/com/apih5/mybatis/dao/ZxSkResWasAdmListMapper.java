package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResWasAdmList;

public interface ZxSkResWasAdmListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResWasAdmList record);

    int insertSelective(ZxSkResWasAdmList record);

    ZxSkResWasAdmList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResWasAdmList record);

    int updateByPrimaryKey(ZxSkResWasAdmList record);

    List<ZxSkResWasAdmList> selectByZxSkResWasAdmListList(ZxSkResWasAdmList record);

    int batchDeleteUpdateZxSkResWasAdmList(List<ZxSkResWasAdmList> recordList, ZxSkResWasAdmList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
