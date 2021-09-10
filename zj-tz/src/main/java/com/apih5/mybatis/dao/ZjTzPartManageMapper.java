package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPartManage;

public interface ZjTzPartManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPartManage record);

    int insertSelective(ZjTzPartManage record);

    ZjTzPartManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPartManage record);

    int updateByPrimaryKey(ZjTzPartManage record);

    List<ZjTzPartManage> selectByZjTzPartManageList(ZjTzPartManage record);

    int batchDeleteUpdateZjTzPartManage(List<ZjTzPartManage> recordList, ZjTzPartManage record);

    List<ZjTzPartManage> selectByZjTzPartManageListGreaterOrderNum(ZjTzPartManage record);
   
    List<ZjTzPartManage> selectByZjTzPartManageListLessOrderNum(ZjTzPartManage record);
    
    int batchUpdateZjTzPartManageAddOne(List<ZjTzPartManage> recordList, ZjTzPartManage record);
   
    int batchUpdateZjTzPartManageSubOne(List<ZjTzPartManage> recordList, ZjTzPartManage record);

	int batchLockUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList, ZjTzPartManage zjTzPartManage);

	int batchClearUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList, ZjTzPartManage zjTzPartManage);
}

