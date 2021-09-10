package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipScrape;

public interface ZxEqEquipScrapeService {

    public ResponseEntity getZxEqEquipScrapeListByCondition(ZxEqEquipScrape zxEqEquipScrape);

    public ResponseEntity getZxEqEquipScrapeDetails(ZxEqEquipScrape zxEqEquipScrape);

    public ResponseEntity saveZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

    public ResponseEntity updateZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

    public ResponseEntity batchDeleteUpdateZxEqEquipScrape(List<ZxEqEquipScrape> zxEqEquipScrapeList);

	public ResponseEntity reportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

	public ZxEqEquipScrape ureportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

	public ResponseEntity agreeZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

	public ResponseEntity refuseZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape);

}

