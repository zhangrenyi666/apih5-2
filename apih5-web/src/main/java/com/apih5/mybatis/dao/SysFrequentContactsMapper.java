package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysFrequentContacts;

public interface SysFrequentContactsMapper {
	int deleteByPrimaryKey(String key);

	int insert(SysFrequentContacts record);

	int insertSelective(SysFrequentContacts record);

	SysFrequentContacts selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(SysFrequentContacts record);

	int updateByPrimaryKey(SysFrequentContacts record);

	List<SysFrequentContacts> selectBySysFrequentContactsList(SysFrequentContacts record);

	List<SysFrequentContacts> getSysUserInfoListByUserKeyList(List<SysFrequentContacts> recordList);

	List<SysFrequentContacts> getContactsListFromTwHzWorklistDone(SysFrequentContacts record);

	int batchDeleteUpdateSysFrequentContacts(List<SysFrequentContacts> recordList);
	
	int batchUpdateContactsCountByCondition(List<SysFrequentContacts> recordList);

	int batchInsertSysFrequentContacts(List<SysFrequentContacts> recordList);

	int deleteSysFrequentContactsByCondition(SysFrequentContacts record);

	int deleteSysFrequentContactsByCondition2(List<SysFrequentContacts> recordList);

	int updateContactsCountByCondition(SysFrequentContacts record);

	int countSysFrequentContactsList(SysFrequentContacts record);

	int countSysFrequentContactsList2(SysFrequentContacts record);

}
