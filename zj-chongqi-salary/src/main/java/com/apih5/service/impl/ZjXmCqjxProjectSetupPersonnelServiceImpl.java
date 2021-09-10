package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmCqjxProjectSetupPersonnelMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;
import com.apih5.service.ZjXmCqjxProjectSetupPersonnelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectSetupPersonnelService")
public class ZjXmCqjxProjectSetupPersonnelServiceImpl implements ZjXmCqjxProjectSetupPersonnelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupPersonnelMapper zjXmCqjxProjectSetupPersonnelMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectSetupPersonnelListByCondition(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        if (zjXmCqjxProjectSetupPersonnel == null) {
            zjXmCqjxProjectSetupPersonnel = new ZjXmCqjxProjectSetupPersonnel();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectSetupPersonnel.getPage(),zjXmCqjxProjectSetupPersonnel.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectSetupPersonnel> zjXmCqjxProjectSetupPersonnelList = zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(zjXmCqjxProjectSetupPersonnel);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectSetupPersonnel> p = new PageInfo<>(zjXmCqjxProjectSetupPersonnelList);

        return repEntity.okList(zjXmCqjxProjectSetupPersonnelList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectSetupPersonnelDetails(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        if (zjXmCqjxProjectSetupPersonnel == null) {
            zjXmCqjxProjectSetupPersonnel = new ZjXmCqjxProjectSetupPersonnel();
        }
        // 获取数据
        ZjXmCqjxProjectSetupPersonnel dbZjXmCqjxProjectSetupPersonnel = zjXmCqjxProjectSetupPersonnelMapper.selectByPrimaryKey(zjXmCqjxProjectSetupPersonnel.getZcOaId());
        // 数据存在
        if (dbZjXmCqjxProjectSetupPersonnel != null) {
            return repEntity.ok(dbZjXmCqjxProjectSetupPersonnel);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectSetupPersonnel(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjXmCqjxProjectSetupPersonnel admin = new ZjXmCqjxProjectSetupPersonnel();
        zjXmCqjxProjectSetupPersonnel.setZcOaId(UuidUtil.generate());
		if (zjXmCqjxProjectSetupPersonnel.getProjectDeptList() != null
				&& zjXmCqjxProjectSetupPersonnel.getProjectDeptList().getOaDepartmentList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectSetupPersonnel.getProjectDeptList();
			for(OADepartment oadept : departmentHead.getOaDepartmentList()){
				admin.setDepartmentId(oadept.getId());
				List<ZjXmCqjxProjectSetupPersonnel> deptList = zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(admin);
				if(deptList.size() == 0){
					zjXmCqjxProjectSetupPersonnel.setDepartmentId(oadept.getId());
					zjXmCqjxProjectSetupPersonnel.setDepartmentName(oadept.getName());
					zjXmCqjxProjectSetupPersonnel.setDeptOrgId(oadept.getOrgId());
				}else{
					return repEntity.layerMessage("NO", "相同的项目不能选择两次");
				}
			}
		}	        
		if (zjXmCqjxProjectSetupPersonnel.getProjectMemberList() != null
				&& zjXmCqjxProjectSetupPersonnel.getProjectMemberList().getOaMemberList().size() > 0) {
			OADepartmentMember departmentHead = zjXmCqjxProjectSetupPersonnel.getProjectMemberList();
			for(OAMember oaMember : departmentHead.getOaMemberList()){
				admin.setOaUserId(oaMember.getUserid());
				if(zjXmCqjxProjectSetupPersonnelMapper.selectByZjXmCqjxProjectSetupPersonnelList(admin).size() == 0) {
					zjXmCqjxProjectSetupPersonnel.setOaUserId(oaMember.getUserid());
					zjXmCqjxProjectSetupPersonnel.setOaUserName(oaMember.getName());
					zjXmCqjxProjectSetupPersonnel.setOaOrgId(oaMember.getOrgId());
				}else {
					return repEntity.layerMessage("NO", "相同的人员不能选择两次");					
				}
			}
		}	        
        zjXmCqjxProjectSetupPersonnel.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectSetupPersonnelMapper.insert(zjXmCqjxProjectSetupPersonnel);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectSetupPersonnel);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectSetupPersonnel(ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectSetupPersonnel dbzjXmCqjxProjectSetupPersonnel = zjXmCqjxProjectSetupPersonnelMapper.selectByPrimaryKey(zjXmCqjxProjectSetupPersonnel.getZcOaId());
        if (dbzjXmCqjxProjectSetupPersonnel != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectSetupPersonnel.getZcOaId())) {
           // 其他关联表id
           dbzjXmCqjxProjectSetupPersonnel.setOtherId(zjXmCqjxProjectSetupPersonnel.getOtherId());
           // 其他类型
           dbzjXmCqjxProjectSetupPersonnel.setOtherType(zjXmCqjxProjectSetupPersonnel.getOtherType());
           // 组织人员区分标识
           dbzjXmCqjxProjectSetupPersonnel.setOaDepartmentMemberFlag(zjXmCqjxProjectSetupPersonnel.getOaDepartmentMemberFlag());
           // oaUserId
           dbzjXmCqjxProjectSetupPersonnel.setOaUserId(zjXmCqjxProjectSetupPersonnel.getOaUserId());
           // oaUserName
           dbzjXmCqjxProjectSetupPersonnel.setOaUserName(zjXmCqjxProjectSetupPersonnel.getOaUserName());
           // oaOrgId
           dbzjXmCqjxProjectSetupPersonnel.setOaOrgId(zjXmCqjxProjectSetupPersonnel.getOaOrgId());
           // 部门id
           dbzjXmCqjxProjectSetupPersonnel.setDepartmentId(zjXmCqjxProjectSetupPersonnel.getDepartmentId());
           // 部门名称
           dbzjXmCqjxProjectSetupPersonnel.setDepartmentName(zjXmCqjxProjectSetupPersonnel.getDepartmentName());
           // 部门orgId
           dbzjXmCqjxProjectSetupPersonnel.setDeptOrgId(zjXmCqjxProjectSetupPersonnel.getDeptOrgId());
           // 备注
           dbzjXmCqjxProjectSetupPersonnel.setRemarks(zjXmCqjxProjectSetupPersonnel.getRemarks());
           // 共通
           dbzjXmCqjxProjectSetupPersonnel.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSetupPersonnelMapper.updateByPrimaryKey(dbzjXmCqjxProjectSetupPersonnel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectSetupPersonnel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(List<ZjXmCqjxProjectSetupPersonnel> zjXmCqjxProjectSetupPersonnelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectSetupPersonnelList != null && zjXmCqjxProjectSetupPersonnelList.size() > 0) {
           ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel = new ZjXmCqjxProjectSetupPersonnel();
           zjXmCqjxProjectSetupPersonnel.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectSetupPersonnelMapper.batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(zjXmCqjxProjectSetupPersonnelList, zjXmCqjxProjectSetupPersonnel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectSetupPersonnelList);
        }
    }
}