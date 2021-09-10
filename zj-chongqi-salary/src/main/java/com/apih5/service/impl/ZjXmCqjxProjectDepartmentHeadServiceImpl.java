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
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadDetailMapper;
import com.apih5.mybatis.dao.ZjXmCqjxProjectDepartmentHeadMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.service.ZjXmCqjxProjectDepartmentHeadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDepartmentHeadService")
public class ZjXmCqjxProjectDepartmentHeadServiceImpl implements ZjXmCqjxProjectDepartmentHeadService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadMapper zjXmCqjxProjectDepartmentHeadMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadDetailMapper zjXmCqjxProjectDepartmentHeadDetailMapper;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadListByCondition(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        if (zjXmCqjxProjectDepartmentHead == null) {
            zjXmCqjxProjectDepartmentHead = new ZjXmCqjxProjectDepartmentHead();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        zjXmCqjxProjectDepartmentHead.setCreateUser(userKey);
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectDepartmentHead.getPage(),zjXmCqjxProjectDepartmentHead.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectDepartmentHead> zjXmCqjxProjectDepartmentHeadList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(zjXmCqjxProjectDepartmentHead);
        for(ZjXmCqjxProjectDepartmentHead deptHead : zjXmCqjxProjectDepartmentHeadList) {
			// 使用单位
        	deptHead.setOaOrgId(deptHead.getDepartmentOrgId());
        	deptHead.setOaUserId(deptHead.getDepartmentId());
        	deptHead.setOaUserName(deptHead.getDepartmentName());
        	List zcDeptList = new ArrayList();
        	zcDeptList.add(deptHead);
        	deptHead.setDepartment(BusinessZj.dbToPageByDepartment(zcDeptList));
			// 获取管理员
        	ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
        	detail.setOtherType("0");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	List detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);        	
        	deptHead.setDepartmentHead(BusinessZj.dbToPageByMember(detailList));
        	// 分管领导
        	detail.setOtherType("1");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setChargeLeader(BusinessZj.dbToPageByMember(detailList));
        	// 主管领导
        	detail.setOtherType("2");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setExecutiveLeader(BusinessZj.dbToPageByMember(detailList));        	
        	// 公司分管领导
        	detail.setOtherType("3");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setComChargeLeader(BusinessZj.dbToPageByMember(detailList));        	
        	// 公司主管领导
        	detail.setOtherType("4");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setComExecutiveLeader(BusinessZj.dbToPageByMember(detailList));        	
        	// 项目中层干部
        	detail.setOtherType("5");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setProectCadre(BusinessZj.dbToPageByMember(detailList));        	
        	// 项目员工
        	detail.setOtherType("6");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setProjectStaff(BusinessZj.dbToPageByMember(detailList));        	
        	// 项目办公室
        	detail.setOtherType("7");
        	detail.setOtherId(deptHead.getDepartmentHeadId());
        	detailList = zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
        	deptHead.setProjectOffice(BusinessZj.dbToPageByMember(detailList));        	
        }        
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectDepartmentHead> p = new PageInfo<>(zjXmCqjxProjectDepartmentHeadList);

        return repEntity.okList(zjXmCqjxProjectDepartmentHeadList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetails(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        if (zjXmCqjxProjectDepartmentHead == null) {
            zjXmCqjxProjectDepartmentHead = new ZjXmCqjxProjectDepartmentHead();
        }
        // 获取数据
        ZjXmCqjxProjectDepartmentHead dbZjXmCqjxProjectDepartmentHead = zjXmCqjxProjectDepartmentHeadMapper.selectByPrimaryKey(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
        // 数据存在
        if (dbZjXmCqjxProjectDepartmentHead != null) {
            return repEntity.ok(dbZjXmCqjxProjectDepartmentHead);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDepartmentHead(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String name = "";
        String parentId = "";
        ZjXmCqjxProjectDepartmentHead cqjxDepartmentHead = new ZjXmCqjxProjectDepartmentHead();
        zjXmCqjxProjectDepartmentHead.setDepartmentHeadId(UuidUtil.generate());
        ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();
		//项目
		if (zjXmCqjxProjectDepartmentHead.getDepartment() != null
				&& zjXmCqjxProjectDepartmentHead.getDepartment().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getDepartment();
			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
//				cqjxDepartmentHead.setUserKey("");
				cqjxDepartmentHead.setDepartmentId(oadept.getId());
				List<ZjXmCqjxProjectDepartmentHead> deptList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(cqjxDepartmentHead);
				if(deptList.size() == 0){
					zjXmCqjxProjectDepartmentHead.setDepartmentId(oadept.getId());
					zjXmCqjxProjectDepartmentHead.setDepartmentName(oadept.getName());
					zjXmCqjxProjectDepartmentHead.setDepartmentOrgId(oadept.getParentid());
					parentId = oadept.getParentid();
				}else{
					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
				}
			}
		}	        
        //项目办公室
		if (zjXmCqjxProjectDepartmentHead.getProjectOffice() != null
				&& zjXmCqjxProjectDepartmentHead.getProjectOffice().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProjectOffice();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("7");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("7");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaParentId(parentId);
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxProjectDepartmentHead.setProjectOfficeName(name);			
		}
		//部门负责人
		if (zjXmCqjxProjectDepartmentHead.getDepartmentHead() != null
				&& zjXmCqjxProjectDepartmentHead.getDepartmentHead().getOaMemberList().size() > 0) {
	        name = "";
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getDepartmentHead();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("0");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("0");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaParentId(parentId);
					detail.setOaUserName(oaMember.getName());
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxProjectDepartmentHead.setUserName(name);			
		}
        //部门员工
		if (zjXmCqjxProjectDepartmentHead.getProjectStaff() != null
				&& zjXmCqjxProjectDepartmentHead.getProjectStaff().getOaMemberList().size() > 0) {
			name = "";
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProjectStaff();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("6");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("6");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxProjectDepartmentHead.setStaffName(name);			
		}		
        //项目中层干部
		if (zjXmCqjxProjectDepartmentHead.getProectCadre() != null
				&& zjXmCqjxProjectDepartmentHead.getProectCadre().getOaMemberList().size() > 0) {
			name = "";
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProectCadre();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();				
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("5");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("5");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);			
			zjXmCqjxProjectDepartmentHead.setCadreName(name);			
		}		
		//项目分管领导
		if (zjXmCqjxProjectDepartmentHead.getChargeLeader() != null
				&& zjXmCqjxProjectDepartmentHead.getChargeLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getChargeLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("1");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";					
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("1");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);	
			zjXmCqjxProjectDepartmentHead.setChargeLeaderName(name);
		}
		//项目主管领导
		if (zjXmCqjxProjectDepartmentHead.getExecutiveLeader() != null
				&& zjXmCqjxProjectDepartmentHead.getExecutiveLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getExecutiveLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("2");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";						
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("2");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);				
			zjXmCqjxProjectDepartmentHead.setExecutiveLeaderName(name);
		}
		//公司分管领导
		if (zjXmCqjxProjectDepartmentHead.getComChargeLeader() != null
				&& zjXmCqjxProjectDepartmentHead.getComChargeLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getComChargeLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("3");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";						
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("3");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);				
			zjXmCqjxProjectDepartmentHead.setComChargeLeaderName(name);
		}	
		//公司主管领导
		if (zjXmCqjxProjectDepartmentHead.getComExecutiveLeader() != null
				&& zjXmCqjxProjectDepartmentHead.getComExecutiveLeader().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getComExecutiveLeader();
			name = "";
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOaUserId(oaMember.getUserid());
				detail.setOtherType("4");
				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
					name = name +oaMember.getName()+",";						
					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
					detail.setDeptDetailId(UuidUtil.generate());
					detail.setOtherType("4");
					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
					detail.setOaUserId(oaMember.getUserid());
					detail.setOaUserName(oaMember.getName());
					detail.setOaParentId(parentId);
					detail.setOaOrgId(oaMember.getOrgId());
					detail.setCreateUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
			name = name.substring(0,name.length()-1);				
			zjXmCqjxProjectDepartmentHead.setComExecutiveLeaderName(name);
		}			
		zjXmCqjxProjectDepartmentHead.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDepartmentHeadMapper.insert(zjXmCqjxProjectDepartmentHead);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDepartmentHead);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDepartmentHead(ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String name = "";
        String parentId = "";
        int flag = 0;
        ZjXmCqjxProjectDepartmentHead cqjxDepartmentHead = new ZjXmCqjxProjectDepartmentHead();        
        ZjXmCqjxProjectDepartmentHead dbzjXmCqjxDepartmentHead = zjXmCqjxProjectDepartmentHeadMapper.selectByPrimaryKey(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
        
        if (dbzjXmCqjxDepartmentHead != null && StrUtil.isNotEmpty(dbzjXmCqjxDepartmentHead.getDepartmentHeadId())) {
    		if (zjXmCqjxProjectDepartmentHead.getDepartment() != null
    				&& zjXmCqjxProjectDepartmentHead.getDepartment().getOaDepartmentList().size() > 0) {
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getDepartment();
    			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
//    				cqjxDepartmentHead.setUserKey("");
    				cqjxDepartmentHead.setDepartmentId(oadept.getId());
    				List<ZjXmCqjxProjectDepartmentHead> deptList = zjXmCqjxProjectDepartmentHeadMapper.selectByZjXmCqjxProjectDepartmentHeadList(cqjxDepartmentHead);
    				if(deptList.size()>0) {
    	   				if(!StrUtil.equals(deptList.get(0).getDepartmentHeadId(), zjXmCqjxProjectDepartmentHead.getDepartmentHeadId()) && deptList.size()>0){
        					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
        				}else {
        					dbzjXmCqjxDepartmentHead.setDepartmentId(oadept.getId());
        					dbzjXmCqjxDepartmentHead.setDepartmentName(oadept.getName());
        					dbzjXmCqjxDepartmentHead.setDepartmentOrgId(oadept.getParentid());  
        					parentId = oadept.getParentid();
        				}    					
    				}else {
    					dbzjXmCqjxDepartmentHead.setDepartmentId(oadept.getId());
    					dbzjXmCqjxDepartmentHead.setDepartmentName(oadept.getName());
    					dbzjXmCqjxDepartmentHead.setDepartmentOrgId(oadept.getParentid());       					
    				}
    			}
    		}	        	
        	//项目办公室
    		if (zjXmCqjxProjectDepartmentHead.getProjectOffice() != null
    				&& zjXmCqjxProjectDepartmentHead.getProjectOffice().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("7");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if(detailList.size()>0) {
					detail.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProjectOffice();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("7");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("7");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setProjectOfficeName(name);    			
    		}        	
        	//部门负责人
    		if (zjXmCqjxProjectDepartmentHead.getDepartmentHead() != null
    				&& zjXmCqjxProjectDepartmentHead.getDepartmentHead().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("0");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if(detailList.size()>0) {
					detail.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getDepartmentHead();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("0");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("0");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaParentId(parentId);
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setUserName(name);    			
    		}
        	//项目中层干部
    		if (zjXmCqjxProjectDepartmentHead.getProectCadre() != null
    				&& zjXmCqjxProjectDepartmentHead.getProectCadre().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("5");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if(detailList.size()>0) {
					detail.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProectCadre();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("5");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("5");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setCadreName(name);    			
    		}    	
        	//项目员工
    		if (zjXmCqjxProjectDepartmentHead.getProjectStaff() != null
    				&& zjXmCqjxProjectDepartmentHead.getProjectStaff().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("6");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				if(detailList.size()>0) {
					detail.setModifyUserInfo(userKey, realName);
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getProjectStaff();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
//    				ZjXmCqjxDepartmentHeadDetail detail = new ZjXmCqjxDepartmentHeadDetail();
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("6");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("6");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setStaffName(name);    			
    		}    		
    		if (zjXmCqjxProjectDepartmentHead.getChargeLeader() != null
    				&& zjXmCqjxProjectDepartmentHead.getChargeLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("1");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0){
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);    			
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getChargeLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("1");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("1");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setChargeLeaderName(name);    
    		}
    		if (zjXmCqjxProjectDepartmentHead.getExecutiveLeader() != null
    				&& zjXmCqjxProjectDepartmentHead.getExecutiveLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("2");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {					
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);       			
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getExecutiveLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("2");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("2");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setExecutiveLeaderName(name);    
    		}
    		if (zjXmCqjxProjectDepartmentHead.getComChargeLeader() != null
    				&& zjXmCqjxProjectDepartmentHead.getComChargeLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("3");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {					
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);       			
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getComChargeLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("3");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("3");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setComChargeLeaderName(name);    
    		}    
    		if (zjXmCqjxProjectDepartmentHead.getComExecutiveLeader() != null
    				&& zjXmCqjxProjectDepartmentHead.getComExecutiveLeader().getOaMemberList().size() > 0) {
    			name = "";
    			ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
				detail.setOtherType("4");
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {					
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);       			
				}
    			OADepartmentMember departmentHead = zjXmCqjxProjectDepartmentHead.getComExecutiveLeader();
    			for(OAMember oaMember : departmentHead.getOaMemberList()){
    				detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
    				detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    				detail.setOaUserId(oaMember.getUserid());
    				detail.setOtherType("4");
    				if(zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail).size() == 0) {
    					name = name +oaMember.getName()+",";					
    					detail = new ZjXmCqjxProjectDepartmentHeadDetail();
    					detail.setDeptDetailId(UuidUtil.generate());
    					detail.setOtherType("4");
    					detail.setOtherId(zjXmCqjxProjectDepartmentHead.getDepartmentHeadId());
    					detail.setOaUserId(oaMember.getUserid());
    					detail.setOaParentId(parentId);
    					detail.setOaUserName(oaMember.getName());
    					detail.setOaOrgId(oaMember.getOrgId());
    					detail.setCreateUserInfo(userKey, realName);
    					zjXmCqjxProjectDepartmentHeadDetailMapper.insert(detail);					
    				}else {
    					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
    				}
    			}
    			name = name.substring(0,name.length()-1);				
    			dbzjXmCqjxDepartmentHead.setComExecutiveLeaderName(name);    
    		}       		
           // 共通
           dbzjXmCqjxDepartmentHead.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDepartmentHeadMapper.updateByPrimaryKey(dbzjXmCqjxDepartmentHead);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDepartmentHead);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHead(List<ZjXmCqjxProjectDepartmentHead> zjXmCqjxProjectDepartmentHeadList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDepartmentHeadList != null && zjXmCqjxProjectDepartmentHeadList.size() > 0) {
        	ZjXmCqjxProjectDepartmentHead zjXmCqjxDepartmentHead = new ZjXmCqjxProjectDepartmentHead();
           zjXmCqjxDepartmentHead.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDepartmentHeadMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHead(zjXmCqjxProjectDepartmentHeadList, zjXmCqjxDepartmentHead);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            for(ZjXmCqjxProjectDepartmentHead head : zjXmCqjxProjectDepartmentHeadList) {
            	ZjXmCqjxProjectDepartmentHeadDetail detail = new ZjXmCqjxProjectDepartmentHeadDetail();					
				detail.setOtherId(head.getDepartmentHeadId());
				List<ZjXmCqjxProjectDepartmentHeadDetail> detailList =  zjXmCqjxProjectDepartmentHeadDetailMapper.selectByZjXmCqjxProjectDepartmentHeadDetailList(detail);
				detail.setModifyUserInfo(userKey, realName);
				if(detailList.size()>0) {					
					zjXmCqjxProjectDepartmentHeadDetailMapper.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(detailList, detail);            	
				}
            }        	
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDepartmentHeadList);
        }
    }
}