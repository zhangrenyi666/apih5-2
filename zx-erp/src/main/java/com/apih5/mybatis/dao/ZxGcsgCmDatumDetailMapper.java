package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCmDatumDetail;

public interface ZxGcsgCmDatumDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxGcsgCmDatumDetail record);

    int insertSelective(ZxGcsgCmDatumDetail record);

    ZxGcsgCmDatumDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxGcsgCmDatumDetail record);

    int updateByPrimaryKey(ZxGcsgCmDatumDetail record);

    List<ZxGcsgCmDatumDetail> selectByZxGcsgCmDatumDetailList(ZxGcsgCmDatumDetail record);

    int batchDeleteUpdateZxGcsgCmDatumDetail(List<ZxGcsgCmDatumDetail> recordList, ZxGcsgCmDatumDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
