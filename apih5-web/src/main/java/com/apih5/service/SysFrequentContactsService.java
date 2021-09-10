package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysFrequentContacts;

public interface SysFrequentContactsService {

	public ResponseEntity getSysFrequentContactsListByCondition(SysFrequentContacts sysFrequentContacts);

	public ResponseEntity saveSysFrequentContacts(SysFrequentContacts sysFrequentContacts);

	public ResponseEntity updateSysFrequentContacts(SysFrequentContacts sysFrequentContacts);

	public ResponseEntity appGetSysFrequentContactsList(SysFrequentContacts sysFrequentContacts);

	public ResponseEntity appRemoveSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList);

	public ResponseEntity appAddSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList);

	public ResponseEntity batchDeleteUpdateSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList);

	public ResponseEntity jobForAddSysFrequentContacts() throws Exception;

}
