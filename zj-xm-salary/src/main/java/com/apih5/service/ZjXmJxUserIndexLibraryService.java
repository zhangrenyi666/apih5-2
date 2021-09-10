package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxUserIndexLibrary;

public interface ZjXmJxUserIndexLibraryService {

	public ResponseEntity getZjXmJxUserIndexLibraryListByCondition(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary);

	public ResponseEntity getZjXmJxUserIndexLibraryDetails(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary);

	public ResponseEntity saveZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary);

	public ResponseEntity updateZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary);

	public ResponseEntity batchDeleteUpdateZjXmJxUserIndexLibrary(
			List<ZjXmJxUserIndexLibrary> zjXmJxUserIndexLibraryList);

	public ResponseEntity oneClickPullZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary);

	public ResponseEntity batchSubmitZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibraryList);
}
