package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;
import com.apih5.mybatis.pojo.ZjTzProManage;

public interface ZjTzProManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProManage record);

    int insertSelective(ZjTzProManage record);

    ZjTzProManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProManage record);

    int updateByPrimaryKey(ZjTzProManage record);

    List<ZjTzProManage> selectByZjTzProManageList(ZjTzProManage record);

    int batchDeleteUpdateZjTzProManage(List<ZjTzProManage> recordList, ZjTzProManage record);
    
//    ZjTzProManage selectProTotal();
    
    List<ZjTzProManage> selectHomeRegionalOverviewProcessAndType(ZjTzProManage record);
    
    List<ZjTzProManage> selectHomeRegionalOverviewProList(ZjTzProManage record);
    
    //建设页工期状态
    ZjTzProManage selectHomeProjectStatusByProjectId(ZjTzProManage record);
    //所有项目工期状态
    List<ZjTzProManage> selectHomeProjectStatus(ZjTzProManage record);
    
    List<ZjTzProManage> selectHomeProgressPlaningComname(ZjTzProManage record);

	List<ZjTzProManage> synZjTzProManageList(ZjTzProManage zjTzProManage);

	List<ZjTzProManage> selectZjTzProManageListNotUpdated(ZjTzProManage zjTzProManage);
	//导出首页工期状态
    List<ZjTzProManage> exportHomeProjectStatus(ZjTzProManage record);
}

