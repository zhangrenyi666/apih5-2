package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;

public interface ZxSkCustomerOutResMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkCustomerOutRes record);

    int insertSelective(ZxSkCustomerOutRes record);

    ZxSkCustomerOutRes selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkCustomerOutRes record);

    int updateByPrimaryKey(ZxSkCustomerOutRes record);

    List<ZxSkCustomerOutRes> selectByZxSkCustomerOutResList(ZxSkCustomerOutRes record);

    int batchDeleteUpdateZxSkCustomerOutRes(List<ZxSkCustomerOutRes> recordList, ZxSkCustomerOutRes record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkCustomerOutRes> selectZxSkCustomerOutResList(ZxSkCustomerOutRes record);
}
