package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysProjectDeptConfMapper;
import com.apih5.mybatis.pojo.SysProjectDeptConf;
import com.apih5.service.SysProjectDeptConfService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("sysProjectDeptConfService")
public class SysProjectDeptConfServiceImpl implements SysProjectDeptConfService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysProjectDeptConfMapper sysProjectDeptConfMapper;

    @Override
    public ResponseEntity getSysProjectDeptConfListByCondition(SysProjectDeptConf sysProjectDeptConf) {
        if (sysProjectDeptConf == null) {
            sysProjectDeptConf = new SysProjectDeptConf();
        }
        // 分页查询
        PageHelper.startPage(sysProjectDeptConf.getPage(),sysProjectDeptConf.getLimit());
        // 获取数据
        List<SysProjectDeptConf> sysProjectDeptConfList = sysProjectDeptConfMapper.selectBySysProjectDeptConfList(sysProjectDeptConf);
        // 得到分页信息
        PageInfo<SysProjectDeptConf> p = new PageInfo<>(sysProjectDeptConfList);

        return repEntity.okList(sysProjectDeptConfList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysProjectDeptConfDetail(SysProjectDeptConf sysProjectDeptConf) {
        if (sysProjectDeptConf == null) {
            sysProjectDeptConf = new SysProjectDeptConf();
        }
        // 获取数据
        SysProjectDeptConf dbSysProjectDeptConf = sysProjectDeptConfMapper.selectByPrimaryKey(sysProjectDeptConf.getSysProjectDeptConfId());
        // 数据存在
        if (dbSysProjectDeptConf != null) {
            return repEntity.ok(dbSysProjectDeptConf);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveSysProjectDeptConf(SysProjectDeptConf sysProjectDeptConf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysProjectDeptConf.setSysProjectDeptConfId(UuidUtil.generate());
        sysProjectDeptConf.setCreateUserInfo(userKey, realName);
        int flag = sysProjectDeptConfMapper.insert(sysProjectDeptConf);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysProjectDeptConf);
        }
    }

    @Override
    public ResponseEntity updateSysProjectDeptConf(SysProjectDeptConf sysProjectDeptConf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysProjectDeptConf dbSysProjectDeptConf = sysProjectDeptConfMapper.selectByPrimaryKey(sysProjectDeptConf.getSysProjectDeptConfId());
        if (dbSysProjectDeptConf != null && StrUtil.isNotEmpty(dbSysProjectDeptConf.getSysProjectDeptConfId())) {
           // 部门ID
           dbSysProjectDeptConf.setDepartmentId(sysProjectDeptConf.getDepartmentId());
           // 岗位ID
           dbSysProjectDeptConf.setJobType(sysProjectDeptConf.getJobType());
           // 人数类型
           dbSysProjectDeptConf.setNumType(sysProjectDeptConf.getNumType());
           // 人数最小
           dbSysProjectDeptConf.setNumMin(sysProjectDeptConf.getNumMin());
           // 人数最大
           dbSysProjectDeptConf.setNumMax(sysProjectDeptConf.getNumMax());
           // 是否兼职
           dbSysProjectDeptConf.setJobFlag(sysProjectDeptConf.getJobFlag());
           // 备注
           dbSysProjectDeptConf.setRemarks(sysProjectDeptConf.getRemarks());
           // 排序
           dbSysProjectDeptConf.setSort(sysProjectDeptConf.getSort());
           // 共通
           dbSysProjectDeptConf.setModifyUserInfo(userKey, realName);
           flag = sysProjectDeptConfMapper.updateByPrimaryKey(dbSysProjectDeptConf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProjectDeptConf);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysProjectDeptConf(List<SysProjectDeptConf> sysProjectDeptConfList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysProjectDeptConfList != null && sysProjectDeptConfList.size() > 0) {
           SysProjectDeptConf sysProjectDeptConf = new SysProjectDeptConf();
           sysProjectDeptConf.setModifyUserInfo(userKey, realName);
           flag = sysProjectDeptConfMapper.batchDeleteUpdateSysProjectDeptConf(sysProjectDeptConfList, sysProjectDeptConf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysProjectDeptConfList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
