package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageCompany;

public interface ZxEqVehicleUsageCompanyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqVehicleUsageCompany record);

    int insertSelective(ZxEqVehicleUsageCompany record);

    ZxEqVehicleUsageCompany selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqVehicleUsageCompany record);

    int updateByPrimaryKey(ZxEqVehicleUsageCompany record);

    List<ZxEqVehicleUsageCompany> selectByZxEqVehicleUsageCompanyList(ZxEqVehicleUsageCompany record);

    int batchDeleteUpdateZxEqVehicleUsageCompany(List<ZxEqVehicleUsageCompany> recordList, ZxEqVehicleUsageCompany record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqVehicleUsageCompany> selectZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany record);
}
