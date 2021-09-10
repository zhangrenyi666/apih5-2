package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSupervisorMonthlyMeeting;

public interface ZjTzSupervisorMonthlyMeetingService {

    public ResponseEntity getZjTzSupervisorMonthlyMeetingListByCondition(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);

    public ResponseEntity getZjTzSupervisorMonthlyMeetingDetails(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);

    public ResponseEntity saveZjTzSupervisorMonthlyMeeting(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);
    
    public ResponseEntity saveZjTzSupervisorMonthlyMeetingAddFile(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);

    public ResponseEntity updateZjTzSupervisorMonthlyMeeting(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);
    
    public ResponseEntity updateZjTzSupervisorMonthlyMeetingAddFile(ZjTzSupervisorMonthlyMeeting zjTzSupervisorMonthlyMeeting);

    public ResponseEntity batchDeleteUpdateZjTzSupervisorMonthlyMeeting(List<ZjTzSupervisorMonthlyMeeting> zjTzSupervisorMonthlyMeetingList);

}

