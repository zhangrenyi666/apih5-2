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
import com.apih5.mybatis.dao.ZxEqVehicleUsageProjectMapper;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageProject;
import com.apih5.service.ZxEqVehicleUsageProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqVehicleUsageProjectService")
public class ZxEqVehicleUsageProjectServiceImpl implements ZxEqVehicleUsageProjectService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqVehicleUsageProjectMapper zxEqVehicleUsageProjectMapper;

    @Override
    public ResponseEntity getZxEqVehicleUsageProjectListByCondition(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        if (zxEqVehicleUsageProject == null) {
            zxEqVehicleUsageProject = new ZxEqVehicleUsageProject();
        }
        // 分页查询
        PageHelper.startPage(zxEqVehicleUsageProject.getPage(),zxEqVehicleUsageProject.getLimit());
        // 获取数据
        List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList = zxEqVehicleUsageProjectMapper.selectByZxEqVehicleUsageProjectList(zxEqVehicleUsageProject);
        // 得到分页信息
        PageInfo<ZxEqVehicleUsageProject> p = new PageInfo<>(zxEqVehicleUsageProjectList);

        return repEntity.okList(zxEqVehicleUsageProjectList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqVehicleUsageProjectDetail(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        if (zxEqVehicleUsageProject == null) {
            zxEqVehicleUsageProject = new ZxEqVehicleUsageProject();
        }
        // 获取数据
        ZxEqVehicleUsageProject dbZxEqVehicleUsageProject = zxEqVehicleUsageProjectMapper.selectByPrimaryKey(zxEqVehicleUsageProject.getZxEqVehicleUsageProjectId());
        // 数据存在
        if (dbZxEqVehicleUsageProject != null) {
            return repEntity.ok(dbZxEqVehicleUsageProject);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqVehicleUsageProject.setZxEqVehicleUsageProjectId(UuidUtil.generate());
        zxEqVehicleUsageProject.setCreateUserInfo(userKey, realName);
        int flag = zxEqVehicleUsageProjectMapper.insert(zxEqVehicleUsageProject);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqVehicleUsageProject);
        }
    }

    @Override
    public ResponseEntity updateZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqVehicleUsageProject dbZxEqVehicleUsageProject = zxEqVehicleUsageProjectMapper.selectByPrimaryKey(zxEqVehicleUsageProject.getZxEqVehicleUsageProjectId());
        if (dbZxEqVehicleUsageProject != null && StrUtil.isNotEmpty(dbZxEqVehicleUsageProject.getZxEqVehicleUsageProjectId())) {
           // 分类
           dbZxEqVehicleUsageProject.setCatName(zxEqVehicleUsageProject.getCatName());
           // 管理编号
           dbZxEqVehicleUsageProject.setEquipNo(zxEqVehicleUsageProject.getEquipNo());
           // 机械名称
           dbZxEqVehicleUsageProject.setEquipName(zxEqVehicleUsageProject.getEquipName());
           // 技术规格
           dbZxEqVehicleUsageProject.setTechrequire(zxEqVehicleUsageProject.getTechrequire());
           // 日历台日
           dbZxEqVehicleUsageProject.setCalendarNumDay(zxEqVehicleUsageProject.getCalendarNumDay());
           // 完美台日
           dbZxEqVehicleUsageProject.setWellDays(zxEqVehicleUsageProject.getWellDays());
           // 完好率
           dbZxEqVehicleUsageProject.setWellPercen(zxEqVehicleUsageProject.getWellPercen());
           // 运转台日
           dbZxEqVehicleUsageProject.setWorkDays(zxEqVehicleUsageProject.getWorkDays());
           // 运转台时
           dbZxEqVehicleUsageProject.setActualQty(zxEqVehicleUsageProject.getActualQty());
           // 利用率
           dbZxEqVehicleUsageProject.setRunDayPercen(zxEqVehicleUsageProject.getRunDayPercen());
           // 行驶里程
           dbZxEqVehicleUsageProject.setUsefulMileage(zxEqVehicleUsageProject.getUsefulMileage());
           // 耗油情况汽油
           dbZxEqVehicleUsageProject.setOilamount(zxEqVehicleUsageProject.getOilamount());
           // 耗油情况柴油
           dbZxEqVehicleUsageProject.setCaioidamout(zxEqVehicleUsageProject.getCaioidamout());
           // 电消耗
           dbZxEqVehicleUsageProject.setConsumption(zxEqVehicleUsageProject.getConsumption());
           // 备注
           dbZxEqVehicleUsageProject.setRemarks(zxEqVehicleUsageProject.getRemarks());
           // 排序
           dbZxEqVehicleUsageProject.setSort(zxEqVehicleUsageProject.getSort());
           // 共通
           dbZxEqVehicleUsageProject.setModifyUserInfo(userKey, realName);
           flag = zxEqVehicleUsageProjectMapper.updateByPrimaryKey(dbZxEqVehicleUsageProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqVehicleUsageProject);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageProject(List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqVehicleUsageProjectList != null && zxEqVehicleUsageProjectList.size() > 0) {
           ZxEqVehicleUsageProject zxEqVehicleUsageProject = new ZxEqVehicleUsageProject();
           zxEqVehicleUsageProject.setModifyUserInfo(userKey, realName);
           flag = zxEqVehicleUsageProjectMapper.batchDeleteUpdateZxEqVehicleUsageProject(zxEqVehicleUsageProjectList, zxEqVehicleUsageProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqVehicleUsageProjectList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxEqVehicleUsageProject> ureportZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
    	if(zxEqVehicleUsageProject==null) {
    		 zxEqVehicleUsageProject = new ZxEqVehicleUsageProject();
    	}
    	List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList =zxEqVehicleUsageProjectMapper.selectZxEqVehicleUsageProject(zxEqVehicleUsageProject);
    	
    	return zxEqVehicleUsageProjectList;
    }
    
    @Override
    public ResponseEntity ureportZxEqVehicleUsageProjectIdle(ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        if (zxEqVehicleUsageProject == null) {
            zxEqVehicleUsageProject = new ZxEqVehicleUsageProject();
        }
        // 分页查询
        PageHelper.startPage(zxEqVehicleUsageProject.getPage(),zxEqVehicleUsageProject.getLimit());
        // 获取数据
        List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList = zxEqVehicleUsageProjectMapper.selectZxEqVehicleUsageProject(zxEqVehicleUsageProject);
        // 得到分页信息
        PageInfo<ZxEqVehicleUsageProject> p = new PageInfo<>(zxEqVehicleUsageProjectList);

        return repEntity.okList(zxEqVehicleUsageProjectList, p.getTotal());
    }
}
