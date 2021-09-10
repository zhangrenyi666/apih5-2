package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSupContent;

public interface ZjTzSupContentService {

    public ResponseEntity getZjTzSupContentListByCondition(ZjTzSupContent zjTzSupContent);

    public ResponseEntity getZjTzSupContentDetails(ZjTzSupContent zjTzSupContent);

    public ResponseEntity saveZjTzSupContent(ZjTzSupContent zjTzSupContent);

    public ResponseEntity updateZjTzSupContent(ZjTzSupContent zjTzSupContent);

    public ResponseEntity batchDeleteUpdateZjTzSupContent(List<ZjTzSupContent> zjTzSupContentList);

}

