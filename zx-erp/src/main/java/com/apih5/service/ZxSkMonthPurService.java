package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMonthPur;

public interface ZxSkMonthPurService {

    public ResponseEntity getZxSkMonthPurListByCondition(ZxSkMonthPur zxSkMonthPur);

    public ResponseEntity getZxSkMonthPurDetails(ZxSkMonthPur zxSkMonthPur);

    public ResponseEntity saveZxSkMonthPur(ZxSkMonthPur zxSkMonthPur);

    public ResponseEntity updateZxSkMonthPur(ZxSkMonthPur zxSkMonthPur);

    public ResponseEntity batchDeleteUpdateZxSkMonthPur(List<ZxSkMonthPur> zxSkMonthPurList);




}

