package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtWorkAlterSingle;

public interface ZxCtWorkAlterSingleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtWorkAlterSingle record);

    int insertSelective(ZxCtWorkAlterSingle record);

    ZxCtWorkAlterSingle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtWorkAlterSingle record);

    int updateByPrimaryKey(ZxCtWorkAlterSingle record);

    List<ZxCtWorkAlterSingle> selectByZxCtWorkAlterSingleList(ZxCtWorkAlterSingle record);

    int batchDeleteUpdateZxCtWorkAlterSingle(List<ZxCtWorkAlterSingle> recordList, ZxCtWorkAlterSingle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int zxCtCancelWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle);

	ZxCtWorkAlterSingle zxCtWorkAlterSingleLast(ZxCtWorkAlterSingle zxCtWorkAlterSingle);
}
