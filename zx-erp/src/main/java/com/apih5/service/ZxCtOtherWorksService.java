package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOtherWorks;

public interface ZxCtOtherWorksService {

    public ResponseEntity getZxCtOtherWorksListByCondition(ZxCtOtherWorks zxCtOtherWorks);

    public ResponseEntity getZxCtOtherWorksDetail(ZxCtOtherWorks zxCtOtherWorks);

    public ResponseEntity saveZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks);

    public ResponseEntity updateZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks);

    public ResponseEntity batchDeleteUpdateZxCtOtherWorks(List<ZxCtOtherWorks> zxCtOtherWorksList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity importZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks);

}
