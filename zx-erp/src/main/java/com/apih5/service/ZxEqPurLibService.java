package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqPurLib;

public interface ZxEqPurLibService {

    public ResponseEntity getZxEqPurLibListByCondition(ZxEqPurLib zxEqPurLib);

    public ResponseEntity getZxEqPurLibDetails(ZxEqPurLib zxEqPurLib);

    public ResponseEntity saveZxEqPurLib(ZxEqPurLib zxEqPurLib);

    public ResponseEntity updateZxEqPurLib(ZxEqPurLib zxEqPurLib);

    public ResponseEntity batchDeleteUpdateZxEqPurLib(List<ZxEqPurLib> zxEqPurLibList);

}

