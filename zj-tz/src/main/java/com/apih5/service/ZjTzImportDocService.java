package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzImportDoc;

public interface ZjTzImportDocService {

    public ResponseEntity getZjTzImportDocListByCondition(ZjTzImportDoc zjTzImportDoc);

    public ResponseEntity getZjTzImportDocDetails(ZjTzImportDoc zjTzImportDoc);

    public ResponseEntity saveZjTzImportDoc(ZjTzImportDoc zjTzImportDoc);

    public ResponseEntity updateZjTzImportDoc(ZjTzImportDoc zjTzImportDoc);

    public ResponseEntity batchDeleteUpdateZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList);

	public ResponseEntity toHomeShowZjTzImportDoc(ZjTzImportDoc zjTzImportDoc);

	public ResponseEntity batchDeleteReleaseZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList);

	public ResponseEntity batchDeleteRecallZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList);

	public ResponseEntity batchExportZjTzImportDocFile(List<ZjTzImportDoc> zjTzImportDocList);

}

