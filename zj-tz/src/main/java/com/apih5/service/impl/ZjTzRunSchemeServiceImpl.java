package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzRunSchemeMapper;
import com.apih5.mybatis.dao.ZjTzRunSchemeOpinionMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzRunScheme;
import com.apih5.mybatis.pojo.ZjTzRunSchemeOpinion;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzRunSchemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzRunSchemeService")
public class ZjTzRunSchemeServiceImpl implements ZjTzRunSchemeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRunSchemeMapper zjTzRunSchemeMapper;
    
    @Autowired(required = true)
    private ZjTzRunSchemeOpinionMapper zjTzRunSchemeOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzRunSchemeListByCondition(ZjTzRunScheme zjTzRunScheme) {
        if (zjTzRunScheme == null) {
            zjTzRunScheme = new ZjTzRunScheme();
        }
        // 分页查询
        PageHelper.startPage(zjTzRunScheme.getPage(),zjTzRunScheme.getLimit());
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzRunScheme.getProjectId(), true)) {
            	zjTzRunScheme.setProjectId("");
            	zjTzRunScheme.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzRunScheme.getProjectId(), true)) {
            	zjTzRunScheme.setProjectId("");
            }
        }
        
        
        // 获取数据
        List<ZjTzRunScheme> zjTzRunSchemeList = zjTzRunSchemeMapper.selectByZjTzRunSchemeList(zjTzRunScheme);
        for (ZjTzRunScheme zjTzRunScheme2 : zjTzRunSchemeList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzRunScheme2.getRunSchemeId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzRunScheme2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzRunScheme> p = new PageInfo<>(zjTzRunSchemeList);

        return repEntity.okList(zjTzRunSchemeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRunSchemeDetails(ZjTzRunScheme zjTzRunScheme) {
        if (zjTzRunScheme == null) {
            zjTzRunScheme = new ZjTzRunScheme();
        }
        if(StrUtil.isNotEmpty(zjTzRunScheme.getWorkId())){
        	ZjTzRunScheme cycle = new ZjTzRunScheme();
        	cycle.setWorkId(zjTzRunScheme.getWorkId());
        	List<ZjTzRunScheme> lifeCycleList = zjTzRunSchemeMapper.selectByZjTzRunSchemeList(zjTzRunScheme);
        	if(lifeCycleList != null && lifeCycleList.size() >0) {
        		zjTzRunScheme = lifeCycleList.get(0);
        	}
        }else if(StrUtil.isNotEmpty(zjTzRunScheme.getRunSchemeId())){
        	zjTzRunScheme = zjTzRunSchemeMapper.selectByPrimaryKey(zjTzRunScheme.getRunSchemeId());
        }
        // 数据存在
        if (zjTzRunScheme != null && StrUtil.isNotEmpty(zjTzRunScheme.getRunSchemeId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzRunScheme.getRunSchemeId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzRunScheme.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzRunScheme);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRunScheme(ZjTzRunScheme zjTzRunScheme) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzRunScheme zjTzRunSchemeSelect = new ZjTzRunScheme();
        zjTzRunSchemeSelect.setProjectId(zjTzRunScheme.getProjectId());
        List<ZjTzRunScheme> changeList = zjTzRunSchemeMapper.selectByZjTzRunSchemeList(zjTzRunSchemeSelect);
        if(changeList != null && changeList.size() >0){
        	return repEntity.layerMessage("no", "该项目已存在。请重新选择！");
        }
        	
        
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzRunScheme.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzRunScheme.setProjectName(manage.getProjectName());
        }
        
        zjTzRunScheme.setRunSchemeId(UuidUtil.generate());
        zjTzRunScheme.setStatusId("0");
        zjTzRunScheme.setStatusName("未评审");
        zjTzRunScheme.setCreateUserInfo(userKey, realName);
        int flag = zjTzRunSchemeMapper.insert(zjTzRunScheme);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzRunScheme.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzRunScheme.getRunSchemeId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	
    	if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRunScheme);
        }
    }

    @Override
    public ResponseEntity updateZjTzRunScheme(ZjTzRunScheme zjTzRunScheme) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRunScheme dbzjTzRunScheme = zjTzRunSchemeMapper.selectByPrimaryKey(zjTzRunScheme.getRunSchemeId());
        if (dbzjTzRunScheme != null && StrUtil.isNotEmpty(dbzjTzRunScheme.getRunSchemeId())) {
           // 备注
           dbzjTzRunScheme.setRemark(zjTzRunScheme.getRemark());
           // 登记日期
           dbzjTzRunScheme.setRegisterDate(zjTzRunScheme.getRegisterDate());
           // 登记用户
           dbzjTzRunScheme.setRegisterPerson(zjTzRunScheme.getRegisterPerson());
           // 共通
           dbzjTzRunScheme.setModifyUserInfo(userKey, realName);
           flag = zjTzRunSchemeMapper.updateByPrimaryKey(dbzjTzRunScheme);

           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRunScheme.getRunSchemeId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzRunScheme.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzRunScheme.getRunSchemeId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRunScheme);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRunScheme(List<ZjTzRunScheme> zjTzRunSchemeList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	String token = TokenUtils.getToken(request);
    	int flag = 0;
    	if (zjTzRunSchemeList != null && zjTzRunSchemeList.size() > 0) {
    		for (ZjTzRunScheme zjTzRunScheme : zjTzRunSchemeList) {
    			zjTzRunScheme = zjTzRunSchemeMapper.selectByPrimaryKey(zjTzRunScheme.getRunSchemeId());
    			if(StrUtil.isEmpty(zjTzRunScheme.getApih5FlowStatus())){

    			}else if(zjTzRunScheme.getApih5FlowStatus().equals("0")){

    			}else{
    				return repEntity.layerMessage("no", "审核中的数据不让删除，请重新选择");
    			}
    		}
    		//删除
    		JSONArray jsonArray = new JSONArray();
    		for (ZjTzRunScheme RunScheme : zjTzRunSchemeList) {
    			jsonArray.add(RunScheme.getWorkId());
    			
    			//del附件
				ZjTzFile file = new ZjTzFile();
				file.setOtherId(RunScheme.getRunSchemeId());
				List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
				if(deFileList != null && deFileList.size() >0) {
					file.setModifyUserInfo(userKey, realName);
					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
				}
				//del意见
				ZjTzRunSchemeOpinion opinion = new ZjTzRunSchemeOpinion();
				opinion.setRunSchemeId(RunScheme.getRunSchemeId());
				List<ZjTzRunSchemeOpinion> delOpinionList = zjTzRunSchemeOpinionMapper.selectByZjTzRunSchemeOpinionList(opinion);
				if(delOpinionList != null && delOpinionList.size() >0) {
					opinion.setModifyUserInfo(userKey, realName);
					for (ZjTzRunSchemeOpinion cycleOpinion : delOpinionList) {
						ZjTzFile opinionFile = new ZjTzFile();
						opinionFile.setOtherId(cycleOpinion.getRunSchemeOpinionId());
						List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
						if(delOpinionFileList != null && delOpinionFileList.size() >0) {
							opinionFile.setModifyUserInfo(userKey, realName);
							zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
						}
						zjTzRunSchemeOpinionMapper.batchDeleteUpdateZjTzRunSchemeOpinion(delOpinionList, opinion);
					}
				}

    		}
    		String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
    		String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
    		if(StrUtil.isNotEmpty(delFlowResult)){
    			JSONObject resultJson = new JSONObject(delFlowResult);
    			if(resultJson.getBool("success")){
    				ZjTzRunScheme zjTzRunScheme = new ZjTzRunScheme();
    				zjTzRunScheme.setModifyUserInfo(userKey, realName);
    				flag = zjTzRunSchemeMapper.batchDeleteUpdateZjTzRunScheme(zjTzRunSchemeList, zjTzRunScheme);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzRunSchemeList);
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
	public ResponseEntity updateZjTzRunSchemeForFlow(ZjTzRunScheme zjTzRunScheme) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzRunScheme dbzjTzRunScheme = zjTzRunSchemeMapper.selectByPrimaryKey(zjTzRunScheme.getRunSchemeId());
		if (dbzjTzRunScheme != null && StrUtil.isNotEmpty(dbzjTzRunScheme.getRunSchemeId())) {
			
			if(StrUtil.equals(zjTzRunScheme.getApih5FlowNodeId(), "Node5")) {
				dbzjTzRunScheme.setStatusId("1");
				dbzjTzRunScheme.setStatusName("评审中");
				
        		
        		
        		
        		ZjTzRunSchemeOpinion record = new ZjTzRunSchemeOpinion();
        		record.setRunSchemeOpinionId(UuidUtil.generate());
        		record.setRunSchemeId(dbzjTzRunScheme.getRunSchemeId());
        		
        		//计算次数
        		ZjTzRunScheme cycle = new ZjTzRunScheme();
        		cycle.setWorkId(dbzjTzRunScheme.getWorkId());
        		cycle.setApih5FlowNodeId(zjTzRunScheme.getApih5FlowNodeId());
        		int count =	zjTzRunSchemeMapper.selectRunSchemeWorkListCount(cycle);
        		
        		int reviewNumber = 1;
        		ZjTzRunSchemeOpinion cycleOpinion = new ZjTzRunSchemeOpinion();
        		cycleOpinion.setRunSchemeId(dbzjTzRunScheme.getRunSchemeId());
        		cycleOpinion.setReviewFlag("end");
        		cycleOpinion.setGroupByFlagReviewNumber("根据次数分组并且根据次数排序");
        		List<ZjTzRunSchemeOpinion> cycleOpinionList = zjTzRunSchemeOpinionMapper.selectByZjTzRunSchemeOpinionList(cycleOpinion);
        		if(cycleOpinionList != null && cycleOpinionList.size() >0) {
        			//多次
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
        		record.setReviewerDeptName(zjTzRunScheme.getOpinionDeptName());
        		record.setOpinion(zjTzRunScheme.getOpinionContent());
        		record.setReviewStartTime(new Date());
        		record.setReviewEndTime(new Date());
        		record.setCreateUserInfo(userKey, realName);
        		zjTzRunSchemeOpinionMapper.insert(record);
        		// 删除附件再新增
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(record.getRunSchemeOpinionId());
                List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
             	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
             	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
                }
                List<ZjTzFile> zjTzFileList = zjTzRunScheme.getZjTzFileOpinionList();
                if(zjTzFileList != null && zjTzFileList.size()>0) {
             	   for(ZjTzFile zjTzFile:zjTzFileList) {
             		   zjTzFile.setUid(UuidUtil.generate());
             		   zjTzFile.setOtherId(record.getRunSchemeOpinionId());
             		   zjTzFile.setCreateUserInfo(userKey, realName);
             		   zjTzFileMapper.insert(zjTzFile);
             	   }
                }
        	}
			
			//发起意见
//			dbzjTzLifeCycle.setOpinionField1(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField1(), zjTzLifeCycle.getOpinionField1()));
			dbzjTzRunScheme.setOpinionField1(zjTzRunScheme.getOpinionField1());
			// 流程id
			dbzjTzRunScheme.setWorkId(zjTzRunScheme.getWorkId());
			// 流程状态
			dbzjTzRunScheme.setApih5FlowStatus(zjTzRunScheme.getApih5FlowStatus());
			if (StrUtil.equals("opinionField2", zjTzRunScheme.getOpinionField(), true)) {
				dbzjTzRunScheme.setOpinionField2(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField2(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField3", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField3(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField3(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField4", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField4(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField4(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField5", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField5(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField5(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField6", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField6(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField6(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField7", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField7(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField7(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField8", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField8(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField8(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField9", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField9(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField9(), zjTzRunScheme.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField10", zjTzRunScheme.getOpinionField(), true)) {
	        	   dbzjTzRunScheme.setOpinionField10(getOpinionContent(realName, dbzjTzRunScheme.getOpinionField10(), zjTzRunScheme.getOpinionContent()));
	           }
	           // 备注
	           dbzjTzRunScheme.setRemark(zjTzRunScheme.getRemark());
	           // 删除附件再新增
	           ZjTzFile zjTzFileSelect = new ZjTzFile();
	           zjTzFileSelect.setOtherId(dbzjTzRunScheme.getRunSchemeId());
	           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
	        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
	        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
	           }
	           List<ZjTzFile> zjTzFileList = zjTzRunScheme.getZjTzFileList();
	           if(zjTzFileList != null && zjTzFileList.size()>0) {
	        	   for(ZjTzFile zjTzFile:zjTzFileList) {
	        		   zjTzFile.setUid(UuidUtil.generate());
	        		   zjTzFile.setOtherId(dbzjTzRunScheme.getRunSchemeId());
	        		   zjTzFile.setCreateUserInfo(userKey, realName);
	        		   zjTzFileMapper.insert(zjTzFile);
	        	   }
	           }
			dbzjTzRunScheme.setModifyUserInfo(userKey, realName);
			if(StrUtil.equals(zjTzRunScheme.getApih5FlowStatus(), "2")) {
				dbzjTzRunScheme.setStatusId("2");
				dbzjTzRunScheme.setStatusName("评审结束");
			}
			flag = zjTzRunSchemeMapper.updateByPrimaryKey(dbzjTzRunScheme);

		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzRunScheme);
		}
	}
}
