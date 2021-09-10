package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehLaborRealName;

public interface ZjLzehLaborRealNameService {

    public ResponseEntity getZjLzehLaborRealNameListByCondition(ZjLzehLaborRealName zjLzehLaborRealName);

    public ResponseEntity getZjLzehLaborRealNameDetails(ZjLzehLaborRealName zjLzehLaborRealName);

    public ResponseEntity saveZjLzehLaborRealName(ZjLzehLaborRealName zjLzehLaborRealName);

    public ResponseEntity updateZjLzehLaborRealName(ZjLzehLaborRealName zjLzehLaborRealName);

    public ResponseEntity batchDeleteUpdateZjLzehLaborRealName(List<ZjLzehLaborRealName> zjLzehLaborRealNameList);

}

