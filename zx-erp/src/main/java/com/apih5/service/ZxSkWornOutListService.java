package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkWornOutList;

public interface ZxSkWornOutListService {

    public ResponseEntity getZxSkWornOutListListByCondition(ZxSkWornOutList zxSkWornOutList);

    public ResponseEntity getZxSkWornOutListDetail(ZxSkWornOutList zxSkWornOutList);

    public ResponseEntity saveZxSkWornOutList(ZxSkWornOutList zxSkWornOutList);

    public ResponseEntity updateZxSkWornOutList(ZxSkWornOutList zxSkWornOutList);

    public ResponseEntity batchDeleteUpdateZxSkWornOutList(List<ZxSkWornOutList> zxSkWornOutListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkWornOutList> ureportZxSkWornOutList(ZxSkWornOutList zxSkWornOutList);
    
    public ResponseEntity ureportZxSkWornOutListIdle(ZxSkWornOutList zxSkWornOutList);
}
