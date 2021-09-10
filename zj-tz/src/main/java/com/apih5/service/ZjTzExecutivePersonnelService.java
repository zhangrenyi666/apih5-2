package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;

public interface ZjTzExecutivePersonnelService {

    public ResponseEntity getZjTzExecutivePersonnelListByCondition(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

    public ResponseEntity getZjTzExecutivePersonnelDetails(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

    public ResponseEntity saveZjTzExecutivePersonnel(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

    public ResponseEntity updateZjTzExecutivePersonnel(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

    public ResponseEntity batchDeleteUpdateZjTzExecutivePersonnel(List<ZjTzExecutivePersonnel> zjTzExecutivePersonnelList);

	public List<ZjTzExecutivePersonnel> uReportZjTzExecutivePersonnelList(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

	public ResponseEntity getZjTzExecutivePersonnelListForReport(ZjTzExecutivePersonnel zjTzExecutivePersonnel);

}

