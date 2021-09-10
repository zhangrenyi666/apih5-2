package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryWageBasicLibrary;

public interface ZjXmSalaryWageBasicLibraryService {

    public ResponseEntity getZjXmSalaryWageBasicLibraryListByCondition(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary);

    public ResponseEntity getZjXmSalaryWageBasicLibraryDetails(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary);

    public ResponseEntity saveZjXmSalaryWageBasicLibrary(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary);

    public ResponseEntity updateZjXmSalaryWageBasicLibrary(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary);

    public ResponseEntity batchDeleteUpdateZjXmSalaryWageBasicLibrary(List<ZjXmSalaryWageBasicLibrary> zjXmSalaryWageBasicLibraryList);

}

