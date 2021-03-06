package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeOpinionMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzLifeCycleChangeService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzLifeCycleChangeService")
public class ZjTzLifeCycleChangeServiceImpl implements ZjTzLifeCycleChangeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzLifeCycleChangeMapper zjTzLifeCycleChangeMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleChangeOpinionMapper zjTzLifeCycleChangeOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzLifeCycleChangeListByCondition(ZjTzLifeCycleChange zjTzLifeCycleChange) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzLifeCycleChange == null) {
            zjTzLifeCycleChange = new ZjTzLifeCycleChange();
        }
        // ????????????
        PageHelper.startPage(zjTzLifeCycleChange.getPage(),zjTzLifeCycleChange.getLimit());
     // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzLifeCycleChange.getProjectId(), true)) {
            	zjTzLifeCycleChange.setProjectId("");
            	zjTzLifeCycleChange.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzLifeCycleChange.getProjectId(), true)) {
            	zjTzLifeCycleChange.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzLifeCycleChange> zjTzLifeCycleChangeList = zjTzLifeCycleChangeMapper.selectByZjTzLifeCycleChangeList(zjTzLifeCycleChange);
        for (ZjTzLifeCycleChange zjTzLifeCycleChange2 : zjTzLifeCycleChangeList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzLifeCycleChange2.getLifeCycleChangeId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzLifeCycleChange2.setZjTzFileList(zjTzFileList);
		}
        // ??????????????????
        PageInfo<ZjTzLifeCycleChange> p = new PageInfo<>(zjTzLifeCycleChangeList);

        return repEntity.okList(zjTzLifeCycleChangeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzLifeCycleChangeDetails(ZjTzLifeCycleChange zjTzLifeCycleChange) {
        if (zjTzLifeCycleChange == null) {
            zjTzLifeCycleChange = new ZjTzLifeCycleChange();
        }
        if(StrUtil.isNotEmpty(zjTzLifeCycleChange.getWorkId())){
        	ZjTzLifeCycleChange cycle = new ZjTzLifeCycleChange();
        	cycle.setWorkId(zjTzLifeCycleChange.getWorkId());
        	List<ZjTzLifeCycleChange> lifeCycleList = zjTzLifeCycleChangeMapper.selectByZjTzLifeCycleChangeList(zjTzLifeCycleChange);
        	if(lifeCycleList != null && lifeCycleList.size() >0) {
        		zjTzLifeCycleChange = lifeCycleList.get(0);
        	}
        }else if(StrUtil.isNotEmpty(zjTzLifeCycleChange.getLifeCycleChangeId())){
        	zjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(zjTzLifeCycleChange.getLifeCycleChangeId());
        }
        // ????????????
        if (zjTzLifeCycleChange != null && StrUtil.isNotEmpty(zjTzLifeCycleChange.getLifeCycleChangeId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzLifeCycleChange.getLifeCycleChangeId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzLifeCycleChange.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzLifeCycleChange);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzLifeCycleChange(ZjTzLifeCycleChange zjTzLifeCycleChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
//        ZjTzLifeCycleChange zjTzLifeCycleChangeSelect = new ZjTzLifeCycleChange();
//        zjTzLifeCycleChangeSelect.setProjectId(zjTzLifeCycleChange.getProjectId());
//        List<ZjTzLifeCycleChange> changeList = zjTzLifeCycleChangeMapper.selectByZjTzLifeCycleChangeList(zjTzLifeCycleChangeSelect);
//        if(changeList != null && changeList.size() >0){
//        	return repEntity.layerMessage("no", "???????????????????????????????????????");
//        }
        	
        
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzLifeCycleChange.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzLifeCycleChange.setProjectName(manage.getProjectName());
        }
        
        zjTzLifeCycleChange.setLifeCycleChangeId(UuidUtil.generate());
        zjTzLifeCycleChange.setStatusId("0");
        zjTzLifeCycleChange.setStatusName("?????????");
        zjTzLifeCycleChange.setCreateUserInfo(userKey, realName);
        int flag = zjTzLifeCycleChangeMapper.insert(zjTzLifeCycleChange);
        // ??????list
    	List<ZjTzFile> zjTzFileList = zjTzLifeCycleChange.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzLifeCycleChange.getLifeCycleChangeId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzLifeCycleChange);
        }
    }

    @Override
    public ResponseEntity updateZjTzLifeCycleChange(ZjTzLifeCycleChange zjTzLifeCycleChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzLifeCycleChange dbzjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(zjTzLifeCycleChange.getLifeCycleChangeId());
        if (dbzjTzLifeCycleChange != null && StrUtil.isNotEmpty(dbzjTzLifeCycleChange.getLifeCycleChangeId())) {
        	// 0(?????????),1(?????????),3(?????????),2(?????????),4(?????????)
        	if(StrUtil.equals(dbzjTzLifeCycleChange.getStatusId(), "0") || StrUtil.equals(dbzjTzLifeCycleChange.getStatusId(), "3")) {

        	}else {
        		return repEntity.layerMessage("no", "??????????????????????????????"); 
        	}
        	// ??????
           dbzjTzLifeCycleChange.setRemark(zjTzLifeCycleChange.getRemark());
           // ????????????
           dbzjTzLifeCycleChange.setRegisterDate(zjTzLifeCycleChange.getRegisterDate());
           // ????????????
           dbzjTzLifeCycleChange.setRegisterPerson(zjTzLifeCycleChange.getRegisterPerson());
           // ??????
           dbzjTzLifeCycleChange.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleChangeMapper.updateByPrimaryKey(dbzjTzLifeCycleChange);
           
        // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzLifeCycleChange.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzLifeCycleChange);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChange(List<ZjTzLifeCycleChange> zjTzLifeCycleChangeList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	String token = TokenUtils.getToken(request);
    	int flag = 0;
    	if (zjTzLifeCycleChangeList != null && zjTzLifeCycleChangeList.size() > 0) {
    		for (ZjTzLifeCycleChange zjTzLifeCycleChange : zjTzLifeCycleChangeList) {
    			zjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(zjTzLifeCycleChange.getLifeCycleChangeId());
    			if(StrUtil.isEmpty(zjTzLifeCycleChange.getApih5FlowStatus())){

    			}else if(zjTzLifeCycleChange.getApih5FlowStatus().equals("0")){

    			}else{
    				// 0(?????????),1(?????????),3(?????????),2(?????????),4(?????????)
					if(StrUtil.equals(zjTzLifeCycleChange.getStatusId(), "0") || StrUtil.equals(zjTzLifeCycleChange.getStatusId(), "3")) {

					}else {
						return repEntity.layerMessage("no", "??????????????????????????????"); 
					}
    			}
    		}
    		//??????
    		JSONArray jsonArray = new JSONArray();
    		for (ZjTzLifeCycleChange lifeCycleChange : zjTzLifeCycleChangeList) {
    			jsonArray.add(lifeCycleChange.getWorkId());
    			
    			//del??????
				ZjTzFile file = new ZjTzFile();
				file.setOtherId(lifeCycleChange.getLifeCycleChangeId());
				List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
				if(deFileList != null && deFileList.size() >0) {
					file.setModifyUserInfo(userKey, realName);
					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
				}
				//del??????
				ZjTzLifeCycleChangeOpinion opinion = new ZjTzLifeCycleChangeOpinion();
				opinion.setLifeCycleChangeId(lifeCycleChange.getLifeCycleChangeId());
				List<ZjTzLifeCycleChangeOpinion> delOpinionList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(opinion);
				if(delOpinionList != null && delOpinionList.size() >0) {
					opinion.setModifyUserInfo(userKey, realName);
					for (ZjTzLifeCycleChangeOpinion cycleOpinion : delOpinionList) {
						ZjTzFile opinionFile = new ZjTzFile();
						opinionFile.setOtherId(cycleOpinion.getLifeCycleChangeOpinionId());
						List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
						if(delOpinionFileList != null && delOpinionFileList.size() >0) {
							opinionFile.setModifyUserInfo(userKey, realName);
							zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
						}
						zjTzLifeCycleChangeOpinionMapper.batchDeleteUpdateZjTzLifeCycleChangeOpinion(delOpinionList, opinion);
					}
				}

    		}
    		String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
    		String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
    		if(StrUtil.isNotEmpty(delFlowResult)){
    			JSONObject resultJson = new JSONObject(delFlowResult);
    			if(resultJson.getBool("success")){
    				ZjTzLifeCycleChange zjTzLifeCycleChange = new ZjTzLifeCycleChange();
    				zjTzLifeCycleChange.setModifyUserInfo(userKey, realName);
    				flag = zjTzLifeCycleChangeMapper.batchDeleteUpdateZjTzLifeCycleChange(zjTzLifeCycleChangeList, zjTzLifeCycleChange);
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzLifeCycleChangeList);
    	}
    }
    
    private String getOpinionContent(String userName, String dbOpinionContent, String opinionContent){
  		if(StrUtil.isNotEmpty(opinionContent)){
  			opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
  			opinionContent += "<br/>" + userName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
  		}
  		return opinionContent;
  	}

	@Override
	public ResponseEntity updateZjTzLifeCycleChangeForFlow(ZjTzLifeCycleChange zjTzLifeCycleChange) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzLifeCycleChange dbzjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(zjTzLifeCycleChange.getLifeCycleChangeId());
		if (dbzjTzLifeCycleChange != null && StrUtil.isNotEmpty(dbzjTzLifeCycleChange.getLifeCycleChangeId())) {
		
		   	if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "0")) {
//        		dbzjTzLifeCycleChange.setStatusId("0");
//        		dbzjTzLifeCycleChange.setStatusName("?????????");
        	}else if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "1")) {
        		dbzjTzLifeCycleChange.setStatusId("1");
        		dbzjTzLifeCycleChange.setStatusName("?????????");
        	}else if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "2")) {
        		dbzjTzLifeCycleChange.setStatusId("2");
        		dbzjTzLifeCycleChange.setStatusName("?????????");
        	}else if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "3")) {
        		dbzjTzLifeCycleChange.setStatusId("3");
        		dbzjTzLifeCycleChange.setStatusName("?????????");
        	}
			
		   	if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowNodeId(), "Node5")) {
		   		if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "3")) {
		   		}else if(StrUtil.equals(zjTzLifeCycleChange.getApih5FlowStatus(), "2")) {
		   			dbzjTzLifeCycleChange.setStatusId("2");
		   			dbzjTzLifeCycleChange.setStatusName("?????????");
		   		}else {
		   			dbzjTzLifeCycleChange.setStatusId("4");
		   			dbzjTzLifeCycleChange.setStatusName("?????????");
		   		}
		   	
		   		ZjTzLifeCycleChangeOpinion record = new ZjTzLifeCycleChangeOpinion();
		   		record.setLifeCycleChangeOpinionId(UuidUtil.generate());
		   		record.setLifeCycleChangeId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
		   		
		   		
		   	//????????????
		   		ZjTzLifeCycleChange cycle = new ZjTzLifeCycleChange();
        		cycle.setWorkId(dbzjTzLifeCycleChange.getWorkId());
        		cycle.setApih5FlowNodeId(zjTzLifeCycleChange.getApih5FlowNodeId());
        		int count =	zjTzLifeCycleChangeMapper.selectLifeCycleChangeWorkListCount(cycle);
        		
        		int reviewNumber = 1;
        		ZjTzLifeCycleChangeOpinion cycleOpinion = new ZjTzLifeCycleChangeOpinion();
        		cycleOpinion.setLifeCycleChangeId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
        		cycleOpinion.setReviewFlag("end");
        		cycleOpinion.setGroupByFlagReviewNumber("??????????????????????????????????????????");
        		
        		List<ZjTzLifeCycleChangeOpinion> cycleOpinionList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(cycleOpinion);
        		if(cycleOpinionList != null && cycleOpinionList.size() >0) {
        			//??????
    				reviewNumber = cycleOpinionList.get(cycleOpinionList.size()-1).getReviewNumber()+1;
        		}
        		if(count ==0) {
        			record.setReviewFlag("end");
        		}else {
        			record.setReviewFlag("start");
        		}
		   		
		   		
		   		record.setReviewNumber(reviewNumber);
		   		record.setReviewerKey(userKey);
		   		record.setReviewer(realName);
		   		record.setReviewerDeptName(zjTzLifeCycleChange.getOpinionDeptName());
		   		record.setOpinion(zjTzLifeCycleChange.getOpinionContent());
		   		record.setReviewStartTime(new Date());
		   		record.setReviewEndTime(new Date());
		   		record.setCreateUserInfo(userKey, realName);
		   		zjTzLifeCycleChangeOpinionMapper.insert(record);
		   		// ?????????????????????
		   		ZjTzFile zjTzFileSelect = new ZjTzFile();
		   		zjTzFileSelect.setOtherId(record.getLifeCycleChangeOpinionId());
		   		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
		   		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
		   			zjTzFileSelect.setModifyUserInfo(userKey, realName);
		   			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
		   		}
		   		List<ZjTzFile> zjTzFileList = zjTzLifeCycleChange.getZjTzFileOpinionList();
		   		if(zjTzFileList != null && zjTzFileList.size()>0) {
		   			for(ZjTzFile zjTzFile:zjTzFileList) {
		   				zjTzFile.setUid(UuidUtil.generate());
		   				zjTzFile.setOtherId(record.getLifeCycleChangeOpinionId());
		   				zjTzFile.setCreateUserInfo(userKey, realName);
		   				zjTzFileMapper.insert(zjTzFile);
		   			}
		   		}

		   	}
			
			//????????????
//			dbzjTzLifeCycle.setOpinionField1(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField1(), zjTzLifeCycle.getOpinionField1()));
			dbzjTzLifeCycleChange.setOpinionField1(zjTzLifeCycleChange.getOpinionField1());
			// ??????id
			dbzjTzLifeCycleChange.setWorkId(zjTzLifeCycleChange.getWorkId());
			// ????????????
			dbzjTzLifeCycleChange.setApih5FlowStatus(zjTzLifeCycleChange.getApih5FlowStatus());
			if (StrUtil.equals("opinionField2", zjTzLifeCycleChange.getOpinionField(), true)) {
				dbzjTzLifeCycleChange.setOpinionField2(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField2(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField3", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField3(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField3(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField4", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField4(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField4(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField5", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField5(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField5(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField6", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField6(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField6(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField7", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField7(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField7(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField8", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField8(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField8(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField9", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField9(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField9(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField10", zjTzLifeCycleChange.getOpinionField(), true)) {
	        	   dbzjTzLifeCycleChange.setOpinionField10(getOpinionContent(realName, dbzjTzLifeCycleChange.getOpinionField10(), zjTzLifeCycleChange.getOpinionContent()));
	           }
	           // ??????
	           dbzjTzLifeCycleChange.setRemark(zjTzLifeCycleChange.getRemark());
	           // ????????????
	           dbzjTzLifeCycleChange.setRegisterDate(zjTzLifeCycleChange.getRegisterDate());
	           // ????????????
	           dbzjTzLifeCycleChange.setRegisterPerson(zjTzLifeCycleChange.getRegisterPerson());
	           // ??????
	           dbzjTzLifeCycleChange.setModifyUserInfo(userKey, realName);
	           flag = zjTzLifeCycleChangeMapper.updateByPrimaryKey(dbzjTzLifeCycleChange);

			// ?????????????????????
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
				zjTzFileSelect.setModifyUserInfo(userKey, realName);
				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
			}
			List<ZjTzFile> zjTzFileList = zjTzLifeCycleChange.getZjTzFileList();
			if(zjTzFileList != null && zjTzFileList.size()>0) {
				for(ZjTzFile zjTzFile:zjTzFileList) {
					zjTzFile.setUid(UuidUtil.generate());
					zjTzFile.setOtherId(dbzjTzLifeCycleChange.getLifeCycleChangeId());
					zjTzFile.setCreateUserInfo(userKey, realName);
					zjTzFileMapper.insert(zjTzFile);
				}
			}
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzLifeCycleChange);
		}
	}

	@Override
	public ResponseEntity getZjTzFlowTitle(ZjTzLifeCycleChange zjTzLifeCycleChange) {
		String title = "";
		String projectName ="";
		String flowName ="";
		ZjTzProManage proManage = zjTzProManageMapper.selectByPrimaryKey(zjTzLifeCycleChange.getProjectId());
		if(proManage != null && StrUtil.isNotEmpty(proManage.getProjectId())){
			projectName = proManage.getProjectName();
		}
		
		if(StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzLifeCycle")) {
			flowName = "?????????????????????";
		}else if (StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzLifeCycle3")) {
			flowName = "?????????????????????";
		}else if (StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzRunScheme3")) {
			flowName = "????????????";
		}else if (StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzRunScheme")) {
			flowName = "????????????";
		}else if (StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzLifeCycleChange")) {
			flowName = "???????????????????????????";
		}else if (StrUtil.equals(zjTzLifeCycleChange.getFlowId(), "zjTzLifeCycleChange3")) {
			flowName = "???????????????????????????";
		}
		title = projectName + flowName;
		return repEntity.ok(title);
	}
    
}
