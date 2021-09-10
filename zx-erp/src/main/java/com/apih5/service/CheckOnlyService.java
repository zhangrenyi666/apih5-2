package com.apih5.service;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.CheckOnly;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;

import java.util.List;

public interface CheckOnlyService {

    ResponseEntity checkOnlyForAspect(CheckOnly checkOnly,Object o);

    ResponseEntity checkOnlyByObj(CheckOnly checkOnly);
}
