package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDatum;

public interface ZxCrCustomerInfoDatumMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerInfoDatum record);

    int insertSelective(ZxCrCustomerInfoDatum record);

    ZxCrCustomerInfoDatum selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerInfoDatum record);

    int updateByPrimaryKey(ZxCrCustomerInfoDatum record);

    List<ZxCrCustomerInfoDatum> selectByZxCrCustomerInfoDatumList(ZxCrCustomerInfoDatum record);

    int batchDeleteUpdateZxCrCustomerInfoDatum(List<ZxCrCustomerInfoDatum> recordList, ZxCrCustomerInfoDatum record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
