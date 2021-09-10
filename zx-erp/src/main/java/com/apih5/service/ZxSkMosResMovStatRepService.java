package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMosResMovStatRep;

public interface ZxSkMosResMovStatRepService {

    public ResponseEntity getZxSkMosResMovStatRepListByCondition(ZxSkMosResMovStatRep zxSkMosResMovStatRep);

    public ResponseEntity getZxSkMosResMovStatRepDetail(ZxSkMosResMovStatRep zxSkMosResMovStatRep);

    public ResponseEntity saveZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep);

    public ResponseEntity updateZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep);

    public ResponseEntity batchDeleteUpdateZxSkMosResMovStatRep(List<ZxSkMosResMovStatRep> zxSkMosResMovStatRepList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity filtrateZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep);
}
