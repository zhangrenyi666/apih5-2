package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;

public interface ZxBuYgjResTechnicsService {

    public ResponseEntity getZxBuYgjResTechnicsListByCondition(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity getZxBuYgjResTechnicsDetail(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity saveZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity updateZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity batchDeleteUpdateZxBuYgjResTechnics(List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity relevanceZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity removeRelevanceZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics);

    public ResponseEntity updateZxBuYgjResTechnicsList(List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList);

    public ResponseEntity getZxBuYgjResTechnicsAndQDList(ZxBuYgjResTechnics zxBuYgjResTechnics);

}
