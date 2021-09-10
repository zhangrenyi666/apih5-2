package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.zjoa.common.BusinessZj;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSetupAdminMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;
import com.apih5.service.ZjXmCqjxProjectSetupAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectSetupAdminService")
public class ZjXmCqjxProjectSetupAdminServiceImpl implements ZjXmCqjxProjectSetupAdminService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupAdminMapper zjXmCqjxProjectSetupAdminMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectSetupAdminListByCondition(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        if (zjXmCqjxProjectSetupAdmin == null) {
            zjXmCqjxProjectSetupAdmin = new ZjXmCqjxProjectSetupAdmin();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectSetupAdmin.getPage(),zjXmCqjxProjectSetupAdmin.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectSetupAdmin> zjXmCqjxProjectSetupAdminList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(zjXmCqjxProjectSetupAdmin);
//        for(ZjXmCqjxProjectSetupAdmin deptHead : zjXmCqjxProjectSetupAdminList) {
//        	// 获取管理员
//        	List memberList = new ArrayList();
//        	memberList.add(deptHead);
//        	deptHead.setProjectDeptList(BusinessZj.dbToPageByMember(memberList));           	
//			// 使用单位
//        	deptHead.setOaOrgId(deptHead.getDeptOrgId());
//        	deptHead.setOaUserId(deptHead.getDepartmentId());
//        	deptHead.setOaUserName(deptHead.getDepartmentName());
//        	List deptList = new ArrayList();
//        	deptList.add(deptHead);
//        	deptHead.setProjectDeptList(BusinessZj.dbToPageByDepartment(deptList));
//        }        
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectSetupAdmin> p = new PageInfo<>(zjXmCqjxProjectSetupAdminList);

        return repEntity.okList(zjXmCqjxProjectSetupAdminList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectSetupAdminDetails(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        if (zjXmCqjxProjectSetupAdmin == null) {
            zjXmCqjxProjectSetupAdmin = new ZjXmCqjxProjectSetupAdmin();
        }
        // 获取数据
        ZjXmCqjxProjectSetupAdmin dbZjXmCqjxProjectSetupAdmin = zjXmCqjxProjectSetupAdminMapper.selectByPrimaryKey(zjXmCqjxProjectSetupAdmin.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxProjectSetupAdmin != null) {
            return repEntity.ok(dbZjXmCqjxProjectSetupAdmin);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectSetupAdmin(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjXmCqjxProjectSetupAdmin admin = new ZjXmCqjxProjectSetupAdmin();
        zjXmCqjxProjectSetupAdmin.setZcOaId(UuidUtil.generate());
		if (zjXmCqjxProjectSetupAdmin.getProjectDeptList() != null
				&& zjXmCqjxProjectSetupAdmin.getProjectDeptList().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectSetupAdmin.getProjectDeptList();
			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
				admin.setDepartmentId(oadept.getId());
				List<ZjXmCqjxProjectSetupAdmin> deptList = zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(admin);
				if(deptList.size() == 0){
					zjXmCqjxProjectSetupAdmin.setDepartmentId(oadept.getId());
					zjXmCqjxProjectSetupAdmin.setDepartmentName(oadept.getName());
					zjXmCqjxProjectSetupAdmin.setDeptOrgId(oadept.getOrgId());
				}else{
					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
				}
			}
		}	        
		if (zjXmCqjxProjectSetupAdmin.getProjectMemberList() != null
				&& zjXmCqjxProjectSetupAdmin.getProjectMemberList().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectSetupAdmin.getProjectMemberList();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				admin.setOaUserId(oaMember.getUserid());
				if(zjXmCqjxProjectSetupAdminMapper.selectByZjXmCqjxProjectSetupAdminList(admin).size() == 0) {
					zjXmCqjxProjectSetupAdmin.setOaUserId(oaMember.getUserid());
					zjXmCqjxProjectSetupAdmin.setOaUserName(oaMember.getName());
					zjXmCqjxProjectSetupAdmin.setOaOrgId(oaMember.getOrgId());
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
		}	        
        zjXmCqjxProjectSetupAdmin.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectSetupAdminMapper.insert(zjXmCqjxProjectSetupAdmin);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectSetupAdmin);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectSetupAdmin(ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectSetupAdmin dbzjXmCqjxProjectSetupAdmin = zjXmCqjxProjectSetupAdminMapper.selectByPrimaryKey(zjXmCqjxProjectSetupAdmin.getZcOaId());
        if (dbzjXmCqjxProjectSetupAdmin != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectSetupAdmin.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxProjectSetupAdmin.setOtherId(zjXmCqjxProjectSetupAdmin.getOtherId());
           // 其他类型
           dbzjXmCqjxProjectSetupAdmin.setOtherType(zjXmCqjxProjectSetupAdmin.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxProjectSetupAdmin.setOaDepartmentMemberFlag(zjXmCqjxProjectSetupAdmin.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxProjectSetupAdmin.setOaUserId(zjXmCqjxProjectSetupAdmin.getOaUserId());
           // oaUserName
           dbzjXmCqjxProjectSetupAdmin.setOaUserName(zjXmCqjxProjectSetupAdmin.getOaUserName());
           // oaOrgId
           dbzjXmCqjxProjectSetupAdmin.setOaOrgId(zjXmCqjxProjectSetupAdmin.getOaOrgId());
           // 共通
           dbzjXmCqjxProjectSetupAdmin.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSetupAdminMapper.updateByPrimaryKey(dbzjXmCqjxProjectSetupAdmin);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectSetupAdmin);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupAdmin(List<ZjXmCqjxProjectSetupAdmin> zjXmCqjxProjectSetupAdminList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectSetupAdminList != null && zjXmCqjxProjectSetupAdminList.size() > 0) {
           ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin = new ZjXmCqjxProjectSetupAdmin();
           zjXmCqjxProjectSetupAdmin.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSetupAdminMapper.batchDeleteUpdateZjXmCqjxProjectSetupAdmin(zjXmCqjxProjectSetupAdminList, zjXmCqjxProjectSetupAdmin);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectSetupAdminList);
        }
        }
    }