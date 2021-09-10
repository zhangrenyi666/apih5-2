package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopEndorseDetail;

public interface ZxCtSuppliesShopEndorseDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopEndorseDetail record);

    int insertSelective(ZxCtSuppliesShopEndorseDetail record);

    ZxCtSuppliesShopEndorseDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopEndorseDetail record);

    int updateByPrimaryKey(ZxCtSuppliesShopEndorseDetail record);

    List<ZxCtSuppliesShopEndorseDetail> selectByZxCtSuppliesShopEndorseDetailList(ZxCtSuppliesShopEndorseDetail record);

    int batchDeleteUpdateZxCtSuppliesShopEndorseDetail(List<ZxCtSuppliesShopEndorseDetail> recordList, ZxCtSuppliesShopEndorseDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
