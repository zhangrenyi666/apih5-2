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
import com.apih5.mybatis.dao.ZxEqMachineJobsMapper;
import com.apih5.mybatis.pojo.ZxEqMachineJobs;
import com.apih5.service.ZxEqMachineJobsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqMachineJobsService")
public class ZxEqMachineJobsServiceImpl implements ZxEqMachineJobsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqMachineJobsMapper zxEqMachineJobsMapper;

    @Override
    public ResponseEntity getZxEqMachineJobsListByCondition(ZxEqMachineJobs zxEqMachineJobs) {
        if (zxEqMachineJobs == null) {
            zxEqMachineJobs = new ZxEqMachineJobs();
        }
        // 分页查询
        PageHelper.startPage(zxEqMachineJobs.getPage(),zxEqMachineJobs.getLimit());
        // 获取数据
        List<ZxEqMachineJobs> zxEqMachineJobsList = zxEqMachineJobsMapper.selectByZxEqMachineJobsList(zxEqMachineJobs);
        // 得到分页信息
        PageInfo<ZxEqMachineJobs> p = new PageInfo<>(zxEqMachineJobsList);

        return repEntity.okList(zxEqMachineJobsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMachineJobsDetails(ZxEqMachineJobs zxEqMachineJobs) {
        if (zxEqMachineJobs == null) {
            zxEqMachineJobs = new ZxEqMachineJobs();
        }
        // 获取数据
        ZxEqMachineJobs dbZxEqMachineJobs = zxEqMachineJobsMapper.selectByPrimaryKey(zxEqMachineJobs.getId());
        // 数据存在
        if (dbZxEqMachineJobs != null) {
            return repEntity.ok(dbZxEqMachineJobs);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqMachineJobs(ZxEqMachineJobs zxEqMachineJobs) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqMachineJobs.setId(UuidUtil.generate());
        if(StrUtil.isNotEmpty(zxEqMachineJobs.getIsEnabled())) {
        
        }else {
        	zxEqMachineJobs.setIsEnabled("1");
        }
        zxEqMachineJobs.setCreateUserInfo(userKey, realName);
        int flag = zxEqMachineJobsMapper.insert(zxEqMachineJobs);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqMachineJobs);
        }
    }

    @Override
    public ResponseEntity updateZxEqMachineJobs(ZxEqMachineJobs zxEqMachineJobs) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMachineJobs dbzxEqMachineJobs = zxEqMachineJobsMapper.selectByPrimaryKey(zxEqMachineJobs.getId());
        if (dbzxEqMachineJobs != null && StrUtil.isNotEmpty(dbzxEqMachineJobs.getId())) {
           // 岗位编码
           dbzxEqMachineJobs.setJobCode(zxEqMachineJobs.getJobCode());
           // 岗位名
           dbzxEqMachineJobs.setJobName(zxEqMachineJobs.getJobName());
           // 是否启用
           dbzxEqMachineJobs.setIsEnabled(zxEqMachineJobs.getIsEnabled());
           // 添加日期
           dbzxEqMachineJobs.setAddTime(zxEqMachineJobs.getAddTime());
           // 备注
           dbzxEqMachineJobs.setRemark(zxEqMachineJobs.getRemark());
           // 添加机构
           dbzxEqMachineJobs.setOrgID(zxEqMachineJobs.getOrgID());
           // 
           dbzxEqMachineJobs.setCombProp(zxEqMachineJobs.getCombProp());
           // 
           dbzxEqMachineJobs.setPp1(zxEqMachineJobs.getPp1());
           // 
           dbzxEqMachineJobs.setPp2(zxEqMachineJobs.getPp2());
           // 
           dbzxEqMachineJobs.setPp3(zxEqMachineJobs.getPp3());
           // 
           dbzxEqMachineJobs.setPp4(zxEqMachineJobs.getPp4());
           // 
           dbzxEqMachineJobs.setPp5(zxEqMachineJobs.getPp5());
           // 
           dbzxEqMachineJobs.setPp6(zxEqMachineJobs.getPp6());
           // 
           dbzxEqMachineJobs.setPp7(zxEqMachineJobs.getPp7());
           // 
           dbzxEqMachineJobs.setPp8(zxEqMachineJobs.getPp8());
           // 
           dbzxEqMachineJobs.setPp9(zxEqMachineJobs.getPp9());
           // 
           dbzxEqMachineJobs.setPp10(zxEqMachineJobs.getPp10());
           // 编制时间
           dbzxEqMachineJobs.setEditTime(zxEqMachineJobs.getEditTime());
           // 共通
           dbzxEqMachineJobs.setModifyUserInfo(userKey, realName);
           flag = zxEqMachineJobsMapper.updateByPrimaryKey(dbzxEqMachineJobs);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMachineJobs);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqMachineJobs(List<ZxEqMachineJobs> zxEqMachineJobsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMachineJobsList != null && zxEqMachineJobsList.size() > 0) {
           ZxEqMachineJobs zxEqMachineJobs = new ZxEqMachineJobs();
           zxEqMachineJobs.setModifyUserInfo(userKey, realName);
           flag = zxEqMachineJobsMapper.batchDeleteUpdateZxEqMachineJobs(zxEqMachineJobsList, zxEqMachineJobs);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMachineJobsList);
        }
    }
}
