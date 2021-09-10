package com.apih5.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeOpinionMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleOpinionMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzLifeCycle;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;
import com.apih5.mybatis.pojo.ZjTzLifeCycleOpinion;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzLifeCycleService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzLifeCycleService")
public class ZjTzLifeCycleServiceImpl implements ZjTzLifeCycleService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzLifeCycleMapper zjTzLifeCycleMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleOpinionMapper zjTzLifeCycleOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleChangeMapper zjTzLifeCycleChangeMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleChangeOpinionMapper zjTzLifeCycleChangeOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    public ResponseEntity getZjTzLifeCycleListByCondition(ZjTzLifeCycle zjTzLifeCycle) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzLifeCycle == null) {
    		zjTzLifeCycle = new ZjTzLifeCycle();
    	}
        
        // 分页查询
        PageHelper.startPage(zjTzLifeCycle.getPage(),zjTzLifeCycle.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzLifeCycle.getProjectId(), true)) {
            	zjTzLifeCycle.setProjectId("");
            	zjTzLifeCycle.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzLifeCycle.getProjectId(), true)) {
            	zjTzLifeCycle.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzLifeCycle> zjTzLifeCycleList = zjTzLifeCycleMapper.selectByZjTzLifeCycleList(zjTzLifeCycle);
        for (ZjTzLifeCycle zjTzLifeCycle2 : zjTzLifeCycleList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzLifeCycle2.getLifeCycleId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzLifeCycle2.setZjTzFileList(zjTzFileList);
        	
        	zjTzLifeCycle2.setShowFlag("0");
        	ZjTzLifeCycleChange cycleChange = new ZjTzLifeCycleChange();
			cycleChange.setProjectId(zjTzLifeCycle2.getProjectId());
			List<ZjTzLifeCycleChange> cycleChangeList = zjTzLifeCycleChangeMapper.selectByZjTzLifeCycleChangeList(cycleChange);
        	if(cycleChangeList != null &&cycleChangeList.size() >0) {
        		ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion = new ZjTzLifeCycleChangeOpinion();
        		zjTzLifeCycleChangeOpinion.setLifeCycleChangeId(cycleChangeList.get(0).getLifeCycleChangeId());
				zjTzLifeCycleChangeOpinion.setGroupByFlagReviewNumber("分组啊");
				List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyGroupByList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(zjTzLifeCycleChangeOpinion);
				for (ZjTzLifeCycleChangeOpinion termReply : zjTzPppTermReplyGroupByList) {
					ZjTzLifeCycleChangeOpinion PppTermReply = new ZjTzLifeCycleChangeOpinion();
					PppTermReply.setLifeCycleChangeId(zjTzLifeCycleChangeOpinion.getLifeCycleChangeId());
					PppTermReply.setReviewNumber(termReply.getReviewNumber());
					List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(PppTermReply);
					if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
						zjTzLifeCycle2.setShowFlag("1");
					}else {
						zjTzLifeCycle2.setShowFlag("0");
					}
				}
        	}else {
        		zjTzLifeCycle2.setShowFlag("0");
        	}
		}
        // 得到分页信息
        PageInfo<ZjTzLifeCycle> p = new PageInfo<>(zjTzLifeCycleList);

        return repEntity.okList(zjTzLifeCycleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzLifeCycleDetails(ZjTzLifeCycle zjTzLifeCycle) {
        if (zjTzLifeCycle == null) {
            zjTzLifeCycle = new ZjTzLifeCycle();
        }
        if(StrUtil.isNotEmpty(zjTzLifeCycle.getWorkId())){
        	ZjTzLifeCycle cycle = new ZjTzLifeCycle();
        	cycle.setWorkId(zjTzLifeCycle.getWorkId());
        	List<ZjTzLifeCycle> lifeCycleList = zjTzLifeCycleMapper.selectByZjTzLifeCycleList(cycle);
        	if(lifeCycleList != null && lifeCycleList.size() >0) {
        		zjTzLifeCycle = lifeCycleList.get(0);
        	}
        }else if(StrUtil.isNotEmpty(zjTzLifeCycle.getLifeCycleId())){
        	zjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(zjTzLifeCycle.getLifeCycleId());
        }
        if (zjTzLifeCycle != null && StrUtil.isNotEmpty(zjTzLifeCycle.getLifeCycleId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzLifeCycle.getLifeCycleId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzLifeCycle.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzLifeCycle);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzLifeCycle(ZjTzLifeCycle zjTzLifeCycle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
//        ZjTzLifeCycle zjTzLifeCycleChangeSelect = new ZjTzLifeCycle();
//        zjTzLifeCycleChangeSelect.setProjectId(zjTzLifeCycle.getProjectId());
//        List<ZjTzLifeCycle> changeList = zjTzLifeCycleMapper.selectByZjTzLifeCycleList(zjTzLifeCycleChangeSelect);
//        if(changeList != null && changeList.size() >0){
//        	return repEntity.layerMessage("no", "该项目已存在。请重新选择！");
//        }
        
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzLifeCycle.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzLifeCycle.setProjectName(manage.getProjectName());
        }
        zjTzLifeCycle.setLifeCycleId(UuidUtil.generate());
        zjTzLifeCycle.setStatusId("0");
        zjTzLifeCycle.setStatusName("未上报");
        zjTzLifeCycle.setCreateUserInfo(userKey, realName);
        int flag = zjTzLifeCycleMapper.insert(zjTzLifeCycle);
     // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzLifeCycle.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzLifeCycle.getLifeCycleId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzLifeCycle);
        }
    }

    @Override
    public ResponseEntity updateZjTzLifeCycle(ZjTzLifeCycle zjTzLifeCycle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzLifeCycle dbzjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(zjTzLifeCycle.getLifeCycleId());
        if (dbzjTzLifeCycle != null && StrUtil.isNotEmpty(dbzjTzLifeCycle.getLifeCycleId())) {
           // 0(未上报),1(已上报),3(已退回),2(已通过),4(评审中)
        	if(StrUtil.equals(dbzjTzLifeCycle.getStatusId(), "0") || StrUtil.equals(dbzjTzLifeCycle.getStatusId(), "3")) {
        	   
           }else {
        	   return repEntity.layerMessage("no", "退回的数据才可以修改"); 
           }
        	// 备注
           dbzjTzLifeCycle.setRemark(zjTzLifeCycle.getRemark());
           // 登记日期
           dbzjTzLifeCycle.setRegisterDate(zjTzLifeCycle.getRegisterDate());
           // 登记用户
           dbzjTzLifeCycle.setRegisterPerson(zjTzLifeCycle.getRegisterPerson());
           // 共通
           dbzjTzLifeCycle.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleMapper.updateByPrimaryKey(dbzjTzLifeCycle);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzLifeCycle.getLifeCycleId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzLifeCycle.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzLifeCycle.getLifeCycleId());
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
            return repEntity.ok("sys.data.update",zjTzLifeCycle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzLifeCycle(List<ZjTzLifeCycle> zjTzLifeCycleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zjTzLifeCycleList != null && zjTzLifeCycleList.size() > 0) {
        	for (ZjTzLifeCycle zjTzLifeCycle : zjTzLifeCycleList) {
				zjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(zjTzLifeCycle.getLifeCycleId());
				if(StrUtil.isEmpty(zjTzLifeCycle.getApih5FlowStatus())){
				
				}else if(zjTzLifeCycle.getApih5FlowStatus().equals("0")){
					
				}else{
					// 0(未上报),1(已上报),3(已退回),2(已通过),4(评审中)
					if(StrUtil.equals(zjTzLifeCycle.getStatusId(), "0") || StrUtil.equals(zjTzLifeCycle.getStatusId(), "3")) {

					}else {
						return repEntity.layerMessage("no", "退回的数据才可以删除"); 
					}
				}
			}
        	//删除
			JSONArray jsonArray = new JSONArray();
			for (ZjTzLifeCycle lifeCycle : zjTzLifeCycleList) {
				jsonArray.add(lifeCycle.getWorkId());
				//del附件
				ZjTzFile file = new ZjTzFile();
				file.setOtherId(lifeCycle.getLifeCycleId());
				List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
				if(deFileList != null && deFileList.size() >0) {
					file.setModifyUserInfo(userKey, realName);
					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
				}
				//del意见
				ZjTzLifeCycleOpinion opinion = new ZjTzLifeCycleOpinion();
				opinion.setLifeCycleId(lifeCycle.getLifeCycleId());
				List<ZjTzLifeCycleOpinion> delOpinionList = zjTzLifeCycleOpinionMapper.selectByZjTzLifeCycleOpinionList(opinion);
				if(delOpinionList != null && delOpinionList.size() >0) {
					opinion.setModifyUserInfo(userKey, realName);
					for (ZjTzLifeCycleOpinion cycleOpinion : delOpinionList) {
						ZjTzFile opinionFile = new ZjTzFile();
						opinionFile.setOtherId(cycleOpinion.getLifeCycleOpinionId());
						List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
						if(delOpinionFileList != null && delOpinionFileList.size() >0) {
							opinionFile.setModifyUserInfo(userKey, realName);
							zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
						}
						zjTzLifeCycleOpinionMapper.batchDeleteUpdateZjTzLifeCycleOpinion(delOpinionList, opinion);
					}
				}
			}
			String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
			String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
			if(StrUtil.isNotEmpty(delFlowResult)){
				JSONObject resultJson = new JSONObject(delFlowResult);
				if(resultJson.getBool("success")){
					 ZjTzLifeCycle zjTzLifeCycle = new ZjTzLifeCycle();
			           zjTzLifeCycle.setModifyUserInfo(userKey, realName);
			           flag = zjTzLifeCycleMapper.batchDeleteUpdateZjTzLifeCycle(zjTzLifeCycleList, zjTzLifeCycle);
				}
			}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzLifeCycleList);
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
	public ResponseEntity updateZjTzLifeCycleFroFlow(ZjTzLifeCycle zjTzLifeCycle) {
		    HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZjTzLifeCycle dbzjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(zjTzLifeCycle.getLifeCycleId());
	        if (dbzjTzLifeCycle != null && StrUtil.isNotEmpty(dbzjTzLifeCycle.getLifeCycleId())) {
	        	if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "0")) {
//	        		dbzjTzLifeCycle.setStatusId("0");
//	        		dbzjTzLifeCycle.setStatusName("未上报");
	        	}else if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "1")) {
	        		dbzjTzLifeCycle.setStatusId("1");
	        		dbzjTzLifeCycle.setStatusName("已上报");
	        	}else if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "2")) {
	        		dbzjTzLifeCycle.setStatusId("2");
	        		dbzjTzLifeCycle.setStatusName("已通过");
	        	}else if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "3")) {
	        		dbzjTzLifeCycle.setStatusId("3");
	        		dbzjTzLifeCycle.setStatusName("已退回");
	        	}
	        
	        	if(StrUtil.equals(zjTzLifeCycle.getApih5FlowNodeId(), "Node5")) {
	        		if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "3")) {
	        		}else if(StrUtil.equals(zjTzLifeCycle.getApih5FlowStatus(), "2")) {
	        			dbzjTzLifeCycle.setStatusId("2");
	        			dbzjTzLifeCycle.setStatusName("已通过");
	        		}else {
	        			dbzjTzLifeCycle.setStatusId("4");
	        			dbzjTzLifeCycle.setStatusName("评审中");
	        		}
	        		ZjTzLifeCycleOpinion record = new ZjTzLifeCycleOpinion();
	        		record.setLifeCycleOpinionId(UuidUtil.generate());
	        		record.setLifeCycleId(dbzjTzLifeCycle.getLifeCycleId());

	        		//计算次数
	        		ZjTzLifeCycle cycle = new ZjTzLifeCycle();
	        		cycle.setWorkId(dbzjTzLifeCycle.getWorkId());
	        		cycle.setApih5FlowNodeId(zjTzLifeCycle.getApih5FlowNodeId());
	        		int count =	zjTzLifeCycleMapper.selectLifeCycleWorkListCount(cycle);
	        		
	        		int reviewNumber = 1;
	        		ZjTzLifeCycleOpinion cycleOpinion = new ZjTzLifeCycleOpinion();
	        		cycleOpinion.setLifeCycleId(dbzjTzLifeCycle.getLifeCycleId());
	        		cycleOpinion.setReviewFlag("end");
	        		cycleOpinion.setGroupByFlagReviewNumber("根据次数分组并且根据次数排序");
	        		
	        		List<ZjTzLifeCycleOpinion> cycleOpinionList = zjTzLifeCycleOpinionMapper.selectByZjTzLifeCycleOpinionList(cycleOpinion);
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
	        		record.setReviewerDeptName(zjTzLifeCycle.getOpinionDeptName()); 
	        		record.setOpinion(zjTzLifeCycle.getOpinionContent());
	        		record.setReviewStartTime(new Date());
	        		record.setReviewEndTime(new Date());
	        		record.setCreateUserInfo(userKey, realName);
	        		zjTzLifeCycleOpinionMapper.insert(record);
	        		
	         		
	        		// 删除附件再新增
	        		ZjTzFile zjTzFileSelect = new ZjTzFile();
	        		zjTzFileSelect.setOtherId(record.getLifeCycleOpinionId());
	        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
	        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
	        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
	        		}
	        		List<ZjTzFile> zjTzFileList = zjTzLifeCycle.getZjTzFileOpinionList();
	        		if(zjTzFileList != null && zjTzFileList.size()>0) {
	        			for(ZjTzFile zjTzFile:zjTzFileList) {
	        				zjTzFile.setUid(UuidUtil.generate());
	        				zjTzFile.setOtherId(record.getLifeCycleOpinionId());
	        				zjTzFile.setCreateUserInfo(userKey, realName);
	        				zjTzFileMapper.insert(zjTzFile);
	        			}
	        		}
	        		
	        	}
	        	//发起意见
//	           dbzjTzLifeCycle.setOpinionField1(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField1(), zjTzLifeCycle.getOpinionField1()));
	           dbzjTzLifeCycle.setOpinionField1(zjTzLifeCycle.getOpinionField1());
	           // 流程id
	           dbzjTzLifeCycle.setWorkId(zjTzLifeCycle.getWorkId());
	           // 流程状态
	           dbzjTzLifeCycle.setApih5FlowStatus(zjTzLifeCycle.getApih5FlowStatus());
	           if (StrUtil.equals("opinionField2", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField2(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField2(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField3", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField3(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField3(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField4", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField4(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField4(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField5", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField5(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField5(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField6", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField6(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField6(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField7", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField7(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField7(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField8", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField8(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField8(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField9", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField9(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField9(), zjTzLifeCycle.getOpinionContent()));
	           }
	           if (StrUtil.equals("opinionField10", zjTzLifeCycle.getOpinionField(), true)) {
	        	   dbzjTzLifeCycle.setOpinionField10(getOpinionContent(realName, dbzjTzLifeCycle.getOpinionField10(), zjTzLifeCycle.getOpinionContent()));
	           }
	           
	           // 备注
	           dbzjTzLifeCycle.setRemark(zjTzLifeCycle.getRemark());
//	           // 登记日期
//	           dbzjTzLifeCycle.setRegisterDate(zjTzLifeCycle.getRegisterDate());
//	           // 登记用户
//	           dbzjTzLifeCycle.setRegisterPerson(zjTzLifeCycle.getRegisterPerson());
	           // 共通
	           dbzjTzLifeCycle.setModifyUserInfo(userKey, realName);
	           flag = zjTzLifeCycleMapper.updateByPrimaryKey(dbzjTzLifeCycle);
	           // 删除附件再新增
	           ZjTzFile zjTzFileSelect = new ZjTzFile();
	           zjTzFileSelect.setOtherId(dbzjTzLifeCycle.getLifeCycleId());
	           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
	        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
	        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
	           }
	           List<ZjTzFile> zjTzFileList = zjTzLifeCycle.getZjTzFileList();
	           if(zjTzFileList != null && zjTzFileList.size()>0) {
	        	   for(ZjTzFile zjTzFile:zjTzFileList) {
	        		   zjTzFile.setUid(UuidUtil.generate());
	        		   zjTzFile.setOtherId(dbzjTzLifeCycle.getLifeCycleId());
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
	            return repEntity.ok("sys.data.update",zjTzLifeCycle);
	        }
	}

	@Override
	public ResponseEntity zjTzLifeCycleMesRemind(ZjTzLifeCycle zjTzLifeCycle) {
		if (zjTzLifeCycle == null) {
			zjTzLifeCycle = new ZjTzLifeCycle();
		}
		List<ZjTzLifeCycle> zjTzLifeCycleList = zjTzLifeCycleMapper.selectLifeCycleWorkList(zjTzLifeCycle);
		if (zjTzLifeCycleList != null && zjTzLifeCycleList.size() > 0) {
			for (ZjTzLifeCycle dbzjTzLifeCycle : zjTzLifeCycleList) {
//			    if(!StrUtil.equals("18612220503", dbzjTzLifeCycle.getMobile())) {
//			        continue;
//			    }
				if(!StrUtil.equals("15123185862", dbzjTzLifeCycle.getMobile())) {
			        continue;
			    }
				Date sendTime = DateUtil.parse(dbzjTzLifeCycle.getSendTime(), "yyyy-MM-dd HH:mm:ss");
				
				String date = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
				Date nowDate = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
				 
				String str1 = DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN);
				Date nowTime = DateUtil.parse(str1);
				Date startTime = DateUtil.parse("08:00:00");
				Date endTime = DateUtil.parse("22:00:00");
				//早上8点到晚上10点
				if (nowTime.after(startTime) && nowTime.before(endTime)) {
//				    LoggerUtils.printLogger(logger, "zjTzLifeCycleMesRemind endTime="+day);
				    Week week = DateUtil.dayOfWeekEnum(new Date());
				    // 除周末外提醒
					if (week.getValue() != Calendar.SUNDAY
					        && week.getValue() != Calendar.SATURDAY) {
					    String url = "http://10.11.51.250:99/apiWoa/sendMsgTz";
					    JSONObject jsonObject = new JSONObject();
					    jsonObject.set("toObject", dbzjTzLifeCycle.getMobile());
					    //提醒时间为null，说明没有提醒过（用收到待办的时间判断），有值，说明提醒过（用最新的提醒时间判断）
					    if (dbzjTzLifeCycle.getRemindTime() == null) {
					    	if (StrUtil.isEmpty(dbzjTzLifeCycle.getRemindFlag())&& DateUtil.between(sendTime, nowDate, DateUnit.HOUR) > 3) {
					    		String msg = "您有新的全生命周期策划需要参与评审，请尽快登录投资管理平台进行评审";
					    		jsonObject.set("content", msg);
    							String str = HttpUtil.sendPostToken(url, jsonObject.toString(), "");
    							LoggerUtils.printLogger(logger, "zjTzLifeCycleMesRemind 1="+str);
//					    		System.out.println(dbzjTzLifeCycle.getRealName()+"1111111111111");
					    		
					    		//判断标识，修改remindFlag状态（1-全生命周期策划，2-全生命周期策划变更）
					    		if (StrUtil.equals("1", dbzjTzLifeCycle.getFlag())) {
					    			ZjTzLifeCycle returnzjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(dbzjTzLifeCycle.getLifeCycleId());
					    			if (returnzjTzLifeCycle != null && StrUtil.isNotEmpty(returnzjTzLifeCycle.getLifeCycleId())) {
					    				returnzjTzLifeCycle.setRemindFlag("0");
					    				returnzjTzLifeCycle.setRemindTime(new Date());
					    				zjTzLifeCycleMapper.updateByPrimaryKeySelective(returnzjTzLifeCycle);
					    			}
					    		}else if (StrUtil.equals("2", dbzjTzLifeCycle.getFlag())) {
					    			ZjTzLifeCycleChange returnzjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(dbzjTzLifeCycle.getLifeCycleId());
					    			if (returnzjTzLifeCycleChange != null && StrUtil.isNotEmpty(returnzjTzLifeCycleChange.getLifeCycleChangeId())) {
					    				returnzjTzLifeCycleChange.setRemindFlag("0");
					    				returnzjTzLifeCycleChange.setRemindTime(new Date());
					    				zjTzLifeCycleChangeMapper.updateByPrimaryKeySelective(returnzjTzLifeCycleChange);
					    			}
					    		}
					    	} 
						} else {
							if (StrUtil.isNotEmpty(dbzjTzLifeCycle.getRemindFlag()) 
							        && DateUtil.between(dbzjTzLifeCycle.getRemindTime(), nowDate, DateUnit.HOUR) > 8) {
								String msg = "您的全生命周期策划已超时，请尽快登录投资管理平台进行评审";
								jsonObject.set("content", msg);
        					    String str = HttpUtil.sendPostToken(url, jsonObject.toString(), "");
        					    LoggerUtils.printLogger(logger, "zjTzLifeCycleMesRemind 2="+str);
        					    
//								System.out.println(dbzjTzLifeCycle.getRealName()+"2222222222222");
								//判断标识，修改remindFlag状态（1-全生命周期策划，2-全生命周期策划变更）
					    		if (StrUtil.equals("1", dbzjTzLifeCycle.getFlag())) {
					    			ZjTzLifeCycle returnzjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(dbzjTzLifeCycle.getLifeCycleId());
					    			if (returnzjTzLifeCycle != null && StrUtil.isNotEmpty(returnzjTzLifeCycle.getLifeCycleId())) {
					    				returnzjTzLifeCycle.setRemindTime(new Date());
					    				zjTzLifeCycleMapper.updateByPrimaryKeySelective(returnzjTzLifeCycle);
					    			}
					    		}else if (StrUtil.equals("2", dbzjTzLifeCycle.getFlag())) {
					    			ZjTzLifeCycleChange returnzjTzLifeCycleChange = zjTzLifeCycleChangeMapper.selectByPrimaryKey(dbzjTzLifeCycle.getLifeCycleId());
					    			if (returnzjTzLifeCycleChange != null && StrUtil.isNotEmpty(returnzjTzLifeCycleChange.getLifeCycleChangeId())) {
					    				returnzjTzLifeCycleChange.setRemindTime(new Date());
					    				zjTzLifeCycleChangeMapper.updateByPrimaryKeySelective(returnzjTzLifeCycleChange);
					    			}
					    		}
							}
						} 
					    
					    
					}
				}
			}
		}
		return repEntity.ok("");
	}
	
	// ↓↓↓----私有方法-（必须是private定义）----↓↓↓
}
