package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzMonthlyMeet;

public interface ZjTzMonthlyMeetService {

    public ResponseEntity getZjTzMonthlyMeetListByCondition(ZjTzMonthlyMeet zjTzMonthlyMeet);

    public ResponseEntity getZjTzMonthlyMeetDetails(ZjTzMonthlyMeet zjTzMonthlyMeet);

    public ResponseEntity saveZjTzMonthlyMeet(ZjTzMonthlyMeet zjTzMonthlyMeet);

    public ResponseEntity updateZjTzMonthlyMeet(ZjTzMonthlyMeet zjTzMonthlyMeet);

    public ResponseEntity batchDeleteUpdateZjTzMonthlyMeet(List<ZjTzMonthlyMeet> zjTzMonthlyMeetList);

}

