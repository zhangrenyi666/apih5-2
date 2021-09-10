package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDepartmentHeadMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.service.ZjXmCqjxDepartmentHeadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDepartmentHeadService")
public class ZjXmCqjxDepartmentHeadServiceImpl implements ZjXmCqjxDepartmentHeadService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadMapper zjXmCqjxDepartmentHeadMapper;

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailMapper zjXmCqjxDepartmentHeadDetailMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxDepartmentHeadListByCondition(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        if (zjXmCqjxDepartmentHead == null) {
            zjXmCqjxDepartmentHead = new ZjXmCqjxDepartmentHead();
        }
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		if(!StrUtil.equals("chongqgs_suyan", userId) && !StrUtil.equals("chongqgs_admin", userId)) {
			List<ZjXmCqjxDepartmentHead> zjXmCqjxAssessmentManageList = new ArrayList<ZjXmCqjxDepartmentHead>();
			return repEntity.okList(zjXmCqjxAssessmentManageList, 0);	
		}
        // 分页查询
        PageHelper.startPage(zjXmCqjxDepartmentHead.getPage(),zjXmCqjxDepartmentHead.getLimit());
        // 获取数据
        List<ZjXmCqjxDepartmentHead> zjXmCqjxDepartmentHeadList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(zjXmCqjxDepartmentHead);
        for(ZjXmCqjxDepartmentHead deptHead : zjXmCqjxDepartmentHeadList) {
			// 使用单位
        	deptHead.setOaOrgId(deptHead.getDepartmentOrgId());
        	deptHead.setOaUserId(deptHead.getDepartmentId());
        	deptHead.setOaUserName(deptHead.getDepartmentName());
        	List zcDeptList = new ArrayList();
        	zcDeptList.add(deptHead);
        	deptHead.setDepartment(BusinessZj.dbToPageByDepartment(zcDeptList));
			// 获取管理员
        	ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
        	detail.setOtherType("0");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	List detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);        	
        	deptHead.setDepartmentHead(BusinessZj.dbToPageByMember(detailList));
        	// 分管领导
        	detail.setOtherType("1");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        	deptHead.setChargeLeader(BusinessZj.dbToPageByMember(detailList));
        	// 主管领导
        	detail.setOtherType("2");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        	deptHead.setExecutiveLeader(BusinessZj.dbToPageByMember(detailList));        	
        	// 副总师、经理助理和部门正、副职
        	detail.setOtherType("3");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        	deptHead.setDepartmentLeader(BusinessZj.dbToPageByMember(detailList));        	
        	// 所属项目正职
        	detail.setOtherType("4");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
        	deptHead.setProjectLeader(BusinessZj.dbToPageByMember(detailList));        	
        }
        // 得到分页信息
        PageInfo<ZjXmCqjxDepartmentHead> p = new PageInfo<>(zjXmCqjxDepartmentHeadList);

        return repEntity.okList(zjXmCqjxDepartmentHeadList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDepartmentHeadDetails(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        if (zjXmCqjxDepartmentHead == null) {
            zjXmCqjxDepartmentHead = new ZjXmCqjxDepartmentHead();
        }
        // 获取数据
        ZjXmCqjxDepartmentHead dbZjXmCqjxDepartmentHead = zjXmCqjxDepartmentHeadMapper.selectByPrimaryKey(zjXmCqjxDepartmentHead.getDepartmentHeadId());
        // 数据存在
        if (dbZjXmCqjxDepartmentHead != null) {
            return repEntity.ok(dbZjXmCqjxDepartmentHead);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDepartmentHead(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String name = "";
        ZjXmCqjxDepartmentHead cqjxDepartmentHead = new ZjXmCqjxDepartmentHead();
        zjXmCqjxDepartmentHead.setDepartmentHeadId(UuidUtil.generate());
        ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
        //副总师、经理助理和部门正、副职
		if (zjXmCqjxDepartmentHead.getDepartmentLeader() != null
				&& zjXmCqjxDepartmentHead.getDepartmentLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartmentLeader();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("3");
				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("3");
					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxDepartmentHead.setDepartmentLeaderName(name);			
		}   
        //所属项目正职
		if (zjXmCqjxDepartmentHead.getProjectLeader() != null
				&& zjXmCqjxDepartmentHead.getProjectLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getProjectLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("4");
				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("4");
					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxDepartmentHead.setProjectLeaderName(name);			
		} 		
        //部门负责人
		if (zjXmCqjxDepartmentHead.getDepartmentHead() != null
				&& zjXmCqjxDepartmentHead.getDepartmentHead().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartmentHead();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("0");
				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("0");
					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxDepartmentHead.setUserName(name);			
		}
		if (zjXmCqjxDepartmentHead.getChargeLeader() != null
				&& zjXmCqjxDepartmentHead.getChargeLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getChargeLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
//				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
				detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("1");
				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";					
					detail = new ZjXmCqjxDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("1");
					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);	
			zjXmCqjxDepartmentHead.setChargeLeaderName(name);
		}
		if (zjXmCqjxDepartmentHead.getExecutiveLeader() != null
				&& zjXmCqjxDepartmentHead.getExecutiveLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getExecutiveLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
//				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
				detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("2");
				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";						
					detail = new ZjXmCqjxDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("2");
					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);				
			zjXmCqjxDepartmentHead.setExecutiveLeaderName(name);
		}
		if (zjXmCqjxDepartmentHead.getDepartment() != null
				&& zjXmCqjxDepartmentHead.getDepartment().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartment();
			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
				cqjxDepartmentHead.setUserKey("");
				cqjxDepartmentHead.setDepartmentId(oadept.getId());
				List<ZjXmCqjxDepartmentHead> deptList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(cqjxDepartmentHead);
				if(deptList.size() == 0){
					zjXmCqjxDepartmentHead.setDepartmentId(oadept.getId());
					zjXmCqjxDepartmentHead.setDepartmentName(oadept.getName());
					zjXmCqjxDepartmentHead.setDepartmentOrgId(oadept.getOrgId());
				}else{
					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
				}
			}
		}		
        zjXmCqjxDepartmentHead.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDepartmentHeadMapper.insert(zjXmCqjxDepartmentHead);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDepartmentHead);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDepartmentHead(ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String name = "";
        int flag = 0; 
        ZjXmCqjxDepartmentHead cqjxDepartmentHead = new ZjXmCqjxDepartmentHead();        
        ZjXmCqjxDepartmentHead dbzjXmCqjxDepartmentHead = zjXmCqjxDepartmentHeadMapper.selectByPrimaryKey(zjXmCqjxDepartmentHead.getDepartmentHeadId());
        if (dbzjXmCqjxDepartmentHead != null && StrUtil.isNotEmpty(dbzjXmCqjxDepartmentHead.getDepartmentHeadId())) {
        	//副总师、经理助理和部门正、副职
    		if (zjXmCqjxDepartmentHead.getDepartmentLeader() != null
    				&& zjXmCqjxDepartmentHead.getDepartmentLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("3");
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {
					zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartmentLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("3");
    				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("3");
    					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setDepartmentLeaderName(name);    			
    		}
        	//所属项目正职
    		if (zjXmCqjxDepartmentHead.getProjectLeader() != null
    				&& zjXmCqjxDepartmentHead.getProjectLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("4");
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {
					zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getProjectLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("4");
    				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("4");
    					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setProjectLeaderName(name);    			
    		}    		
        	//部门负责人
    		if (zjXmCqjxDepartmentHead.getDepartmentHead() != null
    				&& zjXmCqjxDepartmentHead.getDepartmentHead().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("0");
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartmentHead();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("0");
    				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("0");
    					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setUserName(name);    			
    		}
    		if (zjXmCqjxDepartmentHead.getChargeLeader() != null
    				&& zjXmCqjxDepartmentHead.getChargeLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("1");
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0){
					zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);    			
				}
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getChargeLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("1");
    				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("1");
    					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setChargeLeaderName(name);    
    		}
    		if (zjXmCqjxDepartmentHead.getExecutiveLeader() != null
    				&& zjXmCqjxDepartmentHead.getExecutiveLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("2");
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);       			
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getExecutiveLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("2");
    				if(zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("2");
    					detail.setOtherId(zjXmCqjxDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setExecutiveLeaderName(name);    
    		}
    		if (zjXmCqjxDepartmentHead.getDepartment() != null
    				&& zjXmCqjxDepartmentHead.getDepartment().getOaDepartmentList().size() > 0) {
    			OADepartmentMember departmentHead = zjXmCqjxDepartmentHead.getDepartment();
    			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
    				cqjxDepartmentHead.setUserKey("");
    				cqjxDepartmentHead.setDepartmentId(oadept.getId());
    				List<ZjXmCqjxDepartmentHead> deptList = zjXmCqjxDepartmentHeadMapper.selectByZjXmCqjxDepartmentHeadList(cqjxDepartmentHead);
    				if(deptList.size()>0) {
    	   				if(!StrUtil.equals(deptList.get(0).getDepartmentHeadId(), zjXmCqjxDepartmentHead.getDepartmentHeadId()) && deptList.size()>0){
        					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
        				}else {
        					dbzjXmCqjxDepartmentHead.setDepartmentId(oadept.getId());
        					dbzjXmCqjxDepartmentHead.setDepartmentName(oadept.getName());
        					dbzjXmCqjxDepartmentHead.setDepartmentOrgId(oadept.getOrgId());    	    					
        				}    					
    				}else {
    					dbzjXmCqjxDepartmentHead.setDepartmentId(oadept.getId());
    					dbzjXmCqjxDepartmentHead.setDepartmentName(oadept.getName());
    					dbzjXmCqjxDepartmentHead.setDepartmentOrgId(oadept.getOrgId());       					
    				}
 
    			}
    		}	
           // 共通
           dbzjXmCqjxDepartmentHead.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDepartmentHeadMapper.updateByPrimaryKey(dbzjXmCqjxDepartmentHead);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDepartmentHead);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHead(List<ZjXmCqjxDepartmentHead> zjXmCqjxDepartmentHeadList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDepartmentHeadList != null && zjXmCqjxDepartmentHeadList.size() > 0) {
           ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead = new ZjXmCqjxDepartmentHead();
           zjXmCqjxDepartmentHead.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDepartmentHeadMapper.batchDeleteUpdateZjXmCqjxDepartmentHead(zjXmCqjxDepartmentHeadList, zjXmCqjxDepartmentHead);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            for(ZjXmCqjxDepartmentHead head : zjXmCqjxDepartmentHeadList) {
    			ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();					
				detail.setOtherId(head.getDepartmentHeadId());
				List<ZjXmCqjxDepartmentHeadDetail> detailList =  zjXmCqjxDepartmentHeadDetailMapper.selectByZjXmCqjxDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				zjXmCqjxDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(detailList, detail);            	
            }        	
            return repEntity.ok("sys.data.delete",zjXmCqjxDepartmentHeadList);
        }
    }
}
