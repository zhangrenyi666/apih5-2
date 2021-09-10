package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageCompany;

public interface ZxEqVehicleUsageCompanyService {

    public ResponseEntity getZxEqVehicleUsageCompanyListByCondition(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);

    public ResponseEntity getZxEqVehicleUsageCompanyDetail(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);

    public ResponseEntity saveZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);

    public ResponseEntity updateZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);

    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageCompany(List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxEqVehicleUsageCompany> ureportZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);
    
    public ResponseEntity ureportZxEqVehicleUsageCompanyIdle(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany);
}
