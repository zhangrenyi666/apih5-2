package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysWoaFlowSelectMapper;
import com.apih5.mybatis.pojo.SysWoaFlowSelect;
import com.apih5.service.SysWoaFlowSelectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysWoaFlowSelectService")
public class SysWoaFlowSelectServiceImpl implements SysWoaFlowSelectService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaFlowSelectMapper sysWoaFlowSelectMapper;

    @Override
    public ResponseEntity getSysWoaFlowSelectListByCondition(SysWoaFlowSelect sysWoaFlowSelect) {
        if (sysWoaFlowSelect == null) {
            sysWoaFlowSelect = new SysWoaFlowSelect();
        }
        // 分页查询
        PageHelper.startPage(sysWoaFlowSelect.getPage(),sysWoaFlowSelect.getLimit());
        // 获取数据
        List<SysWoaFlowSelect> sysWoaFlowSelectList = sysWoaFlowSelectMapper.selectBySysWoaFlowSelectList(sysWoaFlowSelect);
        // 得到分页信息
        PageInfo<SysWoaFlowSelect> p = new PageInfo<>(sysWoaFlowSelectList);

        return repEntity.okList(sysWoaFlowSelectList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaFlowSelect(SysWoaFlowSelect sysWoaFlowSelect) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaFlowSelect.setFlowSelectId(UuidUtil.generate());
        sysWoaFlowSelect.setCreateUserInfo(userKey, realName);
        int flag = sysWoaFlowSelectMapper.insert(sysWoaFlowSelect);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaFlowSelect);
        }
    }

    @Override
    public ResponseEntity updateSysWoaFlowSelect(SysWoaFlowSelect sysWoaFlowSelect) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaFlowSelect dbsysWoaFlowSelect = sysWoaFlowSelectMapper.selectByPrimaryKey(sysWoaFlowSelect.getFlowSelectId());
        if (dbsysWoaFlowSelect != null && StrUtil.isNotEmpty(dbsysWoaFlowSelect.getFlowSelectId())) {
           // 公司ID
           dbsysWoaFlowSelect.setCompanyId(sysWoaFlowSelect.getCompanyId());
           // 下拉数据
           dbsysWoaFlowSelect.setSelectValue(sysWoaFlowSelect.getSelectValue());
           // 共通
           dbsysWoaFlowSelect.setModifyUserInfo(userKey, realName);
           flag = sysWoaFlowSelectMapper.updateByPrimaryKey(dbsysWoaFlowSelect);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaFlowSelect);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaFlowSelect(List<SysWoaFlowSelect> sysWoaFlowSelectList) {
        int flag = 0;
        if (sysWoaFlowSelectList != null && sysWoaFlowSelectList.size() > 0) {
           flag = sysWoaFlowSelectMapper.batchDeleteUpdateSysWoaFlowSelect(sysWoaFlowSelectList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaFlowSelectList);
        }
    }

	@Override
	public ResponseEntity getSysWoaFlowSelectDetailByCondition(SysWoaFlowSelect sysWoaFlowSelect) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysWoaFlowSelect == null) {
        	sysWoaFlowSelect = new SysWoaFlowSelect();
        }
        String companyId = TokenUtils.getCompanyId(request);
        sysWoaFlowSelect.setCompanyId(companyId);
        // 获取数据
        List<SysWoaFlowSelect> sysWoaFlowSelectList = sysWoaFlowSelectMapper.selectBySysWoaFlowSelectList(sysWoaFlowSelect);
        if(sysWoaFlowSelectList != null && sysWoaFlowSelectList.size() > 0) {
        	return repEntity.ok(sysWoaFlowSelectList.get(0));
        } else {
        	SysWoaFlowSelect newSysWoaFlowSelect = new SysWoaFlowSelect();
        	newSysWoaFlowSelect.setSelectValue("[{\"flowKey\":\"all\",\"flowValue\":\"全部\"}]");
        	return repEntity.ok(newSysWoaFlowSelect);
        }
	}
}
