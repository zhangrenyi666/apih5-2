package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;

public interface ZxGcsgCommonAttachmentService {

    public ResponseEntity getZxGcsgCommonAttachmentListByCondition(ZxGcsgCommonAttachment zxGcsgCommonAttachment);

    public ResponseEntity getZxGcsgCommonAttachmentDetail(ZxGcsgCommonAttachment zxGcsgCommonAttachment);

    public ResponseEntity saveZxGcsgCommonAttachment(ZxGcsgCommonAttachment zxGcsgCommonAttachment);

    public ResponseEntity updateZxGcsgCommonAttachment(ZxGcsgCommonAttachment zxGcsgCommonAttachment);

    public ResponseEntity batchDeleteUpdateZxGcsgCommonAttachment(List<ZxGcsgCommonAttachment> zxGcsgCommonAttachmentList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
