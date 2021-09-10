package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuWorks;

public interface ZxBuWorksService {

    public ResponseEntity getZxBuWorksListByCondition(ZxBuWorks zxBuWorks);

    public ResponseEntity getZxBuWorksDetail(ZxBuWorks zxBuWorks);

    public ResponseEntity saveZxBuWorks(ZxBuWorks zxBuWorks);

    public ResponseEntity updateZxBuWorks(ZxBuWorks zxBuWorks);

    public ResponseEntity batchDeleteUpdateZxBuWorks(List<ZxBuWorks> zxBuWorksList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxBuWorksTree(ZxBuWorks zxBuWorks);

    public ResponseEntity getZxBuWorksTreeList(ZxBuWorks zxBuWorks);

    public ResponseEntity getZxBuWorksTreeListAll(ZxBuWorks zxBuWorks);

    public ResponseEntity getZxBuWorksNoName(ZxBuWorks zxBuWorks);

    public ResponseEntity getZxBuWorksResNoName(ZxBuWorks zxBuWorks);

}
