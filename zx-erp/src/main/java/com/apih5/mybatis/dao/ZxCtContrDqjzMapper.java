package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrDqjz;

public interface ZxCtContrDqjzMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrDqjz record);

    int insertSelective(ZxCtContrDqjz record);

    ZxCtContrDqjz selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrDqjz record);

    int updateByPrimaryKey(ZxCtContrDqjz record);

    List<ZxCtContrDqjz> selectByZxCtContrDqjzList(ZxCtContrDqjz record);

    int batchDeleteUpdateZxCtContrDqjz(List<ZxCtContrDqjz> recordList, ZxCtContrDqjz record);

	List<ZxCtContrDqjz> selectByZxCtContrDqjzUnFinishList(ZxCtContrDqjz dqjz);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
