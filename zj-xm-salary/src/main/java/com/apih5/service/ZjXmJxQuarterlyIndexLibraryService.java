package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyIndexLibrary;

public interface ZjXmJxQuarterlyIndexLibraryService {

    public ResponseEntity getZjXmJxQuarterlyIndexLibraryListByCondition(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary);

    public ResponseEntity getZjXmJxQuarterlyIndexLibraryDetail(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary);

    public ResponseEntity saveZjXmJxQuarterlyIndexLibrary(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary);

    public ResponseEntity updateZjXmJxQuarterlyIndexLibrary(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary);

    public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(List<ZjXmJxQuarterlyIndexLibrary> zjXmJxQuarterlyIndexLibraryList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
