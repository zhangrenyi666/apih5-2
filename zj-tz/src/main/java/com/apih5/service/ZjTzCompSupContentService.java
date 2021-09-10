package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzCompSupContent;

public interface ZjTzCompSupContentService {

    public ResponseEntity getZjTzCompSupContentListByCondition(ZjTzCompSupContent zjTzCompSupContent);

    public ResponseEntity getZjTzCompSupContentDetails(ZjTzCompSupContent zjTzCompSupContent);

    public ResponseEntity saveZjTzCompSupContent(ZjTzCompSupContent zjTzCompSupContent);

    public ResponseEntity updateZjTzCompSupContent(ZjTzCompSupContent zjTzCompSupContent);

    public ResponseEntity batchDeleteUpdateZjTzCompSupContent(List<ZjTzCompSupContent> zjTzCompSupContentList);

}

