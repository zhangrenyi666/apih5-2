package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProEvent;

public interface ZjTzProEventService {

    public ResponseEntity getZjTzProEventListByCondition(ZjTzProEvent zjTzProEvent);

    public ResponseEntity getZjTzProEventDetails(ZjTzProEvent zjTzProEvent);

    public ResponseEntity saveZjTzProEvent(ZjTzProEvent zjTzProEvent);

    public ResponseEntity updateZjTzProEvent(ZjTzProEvent zjTzProEvent);

    public ResponseEntity batchDeleteUpdateZjTzProEvent(List<ZjTzProEvent> zjTzProEventList);
    
    public ResponseEntity toHomeShowZjTzProEvent(ZjTzProEvent zjTzProEvent);
    
    public ResponseEntity batchRecallZjTzProEvent(List<ZjTzProEvent> zjTzProEventList);
    
    public ResponseEntity exportZjTzProEventList(ZjTzProEvent zjTzProEvent, HttpServletResponse response);
}

