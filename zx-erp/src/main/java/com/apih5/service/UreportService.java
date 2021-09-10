package com.apih5.service;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;

import java.util.List;
import java.util.Map;

public interface UreportService {
    public List ureportList(Map paramMap);
    public Object ureportObject(Map paramMap);
}
