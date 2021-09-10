package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;

public interface ZjXmJxIndexLibraryService {

	public ResponseEntity getZjXmJxIndexLibraryListByCondition(ZjXmJxIndexLibrary zjXmJxIndexLibrary);

	public ResponseEntity getZjXmJxIndexLibraryDetails(ZjXmJxIndexLibrary zjXmJxIndexLibrary);

	public ResponseEntity saveZjXmJxIndexLibrary(ZjXmJxIndexLibrary zjXmJxIndexLibrary);

	public ResponseEntity updateZjXmJxIndexLibrary(ZjXmJxIndexLibrary zjXmJxIndexLibrary);

	public ResponseEntity batchDeleteUpdateZjXmJxIndexLibrary(List<ZjXmJxIndexLibrary> zjXmJxIndexLibraryList);

}
