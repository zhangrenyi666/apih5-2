package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;

public interface ZxSkCustomerOutResService {

    public ResponseEntity getZxSkCustomerOutResListByCondition(ZxSkCustomerOutRes zxSkCustomerOutRes);

    public ResponseEntity getZxSkCustomerOutResDetail(ZxSkCustomerOutRes zxSkCustomerOutRes);

    public ResponseEntity saveZxSkCustomerOutRes(ZxSkCustomerOutRes zxSkCustomerOutRes);

    public ResponseEntity updateZxSkCustomerOutRes(ZxSkCustomerOutRes zxSkCustomerOutRes);

    public ResponseEntity batchDeleteUpdateZxSkCustomerOutRes(List<ZxSkCustomerOutRes> zxSkCustomerOutResList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkCustomerOutRes> ureportZxSkReceivingDynamic(ZxSkCustomerOutRes zxSkCustomerOutRes);
    
    public ResponseEntity ureportZxSkReceivingDynamicIdle(ZxSkCustomerOutRes zxSkCustomerOutRes);
}
