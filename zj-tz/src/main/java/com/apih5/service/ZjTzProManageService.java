package com.apih5.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProManage;

public interface ZjTzProManageService {

    public ResponseEntity getZjTzProManageListByCondition(ZjTzProManage zjTzProManage);

    public ResponseEntity getZjTzProManageDetails(ZjTzProManage zjTzProManage);

    public ResponseEntity saveZjTzProManage(ZjTzProManage zjTzProManage);

    public ResponseEntity updateZjTzProManage(ZjTzProManage zjTzProManage);

    public ResponseEntity batchDeleteUpdateZjTzProManage(List<ZjTzProManage> zjTzProManageList);

	public ResponseEntity getZjTzProManageListForHard(ZjTzProManage zjTzProManage);

	public ResponseEntity getHomeRegionalOverviewProcessAndType(ZjTzProManage zjTzProManage);

	public ResponseEntity getHomeRegionalOverviewProList(ZjTzProManage zjTzProManage);
	//建设页工期状态
	public ResponseEntity getHomeProjectStatusByProjectId(ZjTzProManage zjTzProManage);
	//所有项目工期状态
	public ResponseEntity getHomeProjectStatusAllProject(ZjTzProManage zjTzProManage);
	
	public ResponseEntity getHomeProgressPlaningComname(ZjTzProManage zjTzProManage);

	public ResponseEntity exportHomeRegionalOverviewProList(ZjTzProManage zjTzProManage, HttpServletResponse response);

	public ResponseEntity exportHomeProjectStatus(ZjTzProManage zjTzProManage, HttpServletResponse response);

	public ResponseEntity changeZjTzProManageToSign(ZjTzProManage zjTzProManage);

	public ResponseEntity synZjTzProManage();

	public ResponseEntity getZjTzProManageListNotUpdated(ZjTzProManage zjTzProManage);

	public ResponseEntity getHomeProjectStatusAllProjectAlertPage(ZjTzProManage zjTzProManage);


}

