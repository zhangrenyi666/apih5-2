package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehRebarSupermarket;

public interface ZjLzehRebarSupermarketMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehRebarSupermarket record);

    int insertSelective(ZjLzehRebarSupermarket record);

    ZjLzehRebarSupermarket selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehRebarSupermarket record);

    int updateByPrimaryKey(ZjLzehRebarSupermarket record);

    List<ZjLzehRebarSupermarket> selectByZjLzehRebarSupermarketList(ZjLzehRebarSupermarket record);

    int batchDeleteUpdateZjLzehRebarSupermarket(List<ZjLzehRebarSupermarket> recordList, ZjLzehRebarSupermarket record);

}

