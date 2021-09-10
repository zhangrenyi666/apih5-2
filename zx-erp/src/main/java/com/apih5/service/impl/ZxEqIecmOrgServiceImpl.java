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
import com.apih5.mybatis.dao.ZxEqIecmOrgMapper;
import com.apih5.mybatis.pojo.ZxEqIecmOrg;
import com.apih5.service.ZxEqIecmOrgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqIecmOrgService")
public class ZxEqIecmOrgServiceImpl implements ZxEqIecmOrgService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqIecmOrgMapper zxEqIecmOrgMapper;

    @Override
    public ResponseEntity getZxEqIecmOrgListByCondition(ZxEqIecmOrg zxEqIecmOrg) {
        if (zxEqIecmOrg == null) {
            zxEqIecmOrg = new ZxEqIecmOrg();
        }
        // 分页查询
        PageHelper.startPage(zxEqIecmOrg.getPage(),zxEqIecmOrg.getLimit());
        // 获取数据
        List<ZxEqIecmOrg> zxEqIecmOrgList = zxEqIecmOrgMapper.selectByZxEqIecmOrgList(zxEqIecmOrg);
        // 得到分页信息
        PageInfo<ZxEqIecmOrg> p = new PageInfo<>(zxEqIecmOrgList);

        return repEntity.okList(zxEqIecmOrgList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqIecmOrgDetails(ZxEqIecmOrg zxEqIecmOrg) {
        if (zxEqIecmOrg == null) {
            zxEqIecmOrg = new ZxEqIecmOrg();
        }
        // 获取数据
        ZxEqIecmOrg dbZxEqIecmOrg = zxEqIecmOrgMapper.selectByPrimaryKey(zxEqIecmOrg.getIecmOrgID());
        // 数据存在
        if (dbZxEqIecmOrg != null) {
            return repEntity.ok(dbZxEqIecmOrg);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqIecmOrg(ZxEqIecmOrg zxEqIecmOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqIecmOrg.setIecmOrgID(UuidUtil.generate());
        zxEqIecmOrg.setCreateUserInfo(userKey, realName);
        int flag = zxEqIecmOrgMapper.insert(zxEqIecmOrg);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqIecmOrg);
        }
    }

    @Override
    public ResponseEntity updateZxEqIecmOrg(ZxEqIecmOrg zxEqIecmOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqIecmOrg dbzxEqIecmOrg = zxEqIecmOrgMapper.selectByPrimaryKey(zxEqIecmOrg.getIecmOrgID());
        if (dbzxEqIecmOrg != null && StrUtil.isNotEmpty(dbzxEqIecmOrg.getIecmOrgID())) {
           // 机构类型
           dbzxEqIecmOrg.setOrgType(zxEqIecmOrg.getOrgType());
           // 机构名称
           dbzxEqIecmOrg.setOrgName(zxEqIecmOrg.getOrgName());
           // 对应OA中机构ID
           dbzxEqIecmOrg.setComOrgID(zxEqIecmOrg.getComOrgID());
           // BSID
           dbzxEqIecmOrg.setBsid(zxEqIecmOrg.getBsid());
           // 对应业务中对象ID
           dbzxEqIecmOrg.setBusiID(zxEqIecmOrg.getBusiID());
           // 父节点ID
           dbzxEqIecmOrg.setParentID(zxEqIecmOrg.getParentID());
           // 
           dbzxEqIecmOrg.setOrgNO(zxEqIecmOrg.getOrgNO());
           // 状态
           dbzxEqIecmOrg.setOrgStatus(zxEqIecmOrg.getOrgStatus());
           // 
           dbzxEqIecmOrg.setDescription(zxEqIecmOrg.getDescription());
           // 
           dbzxEqIecmOrg.setEditTime(zxEqIecmOrg.getEditTime());
           // 
           dbzxEqIecmOrg.setOldBsid(zxEqIecmOrg.getOldBsid());
           // 
           dbzxEqIecmOrg.setIsLineOut(zxEqIecmOrg.getIsLineOut());
           // 
           dbzxEqIecmOrg.setContractStatus(zxEqIecmOrg.getContractStatus());
           // 
           dbzxEqIecmOrg.setOrders(zxEqIecmOrg.getOrders());
           // 
           dbzxEqIecmOrg.setAreaName(zxEqIecmOrg.getAreaName());
           // 
           dbzxEqIecmOrg.setProjectType(zxEqIecmOrg.getProjectType());
           // 
           dbzxEqIecmOrg.setOrgCode(zxEqIecmOrg.getOrgCode());
           // 公司Id
           dbzxEqIecmOrg.setComID(zxEqIecmOrg.getComID());
           // 公司名称
           dbzxEqIecmOrg.setComName(zxEqIecmOrg.getComName());
           // 公司排序
           dbzxEqIecmOrg.setComOrders(zxEqIecmOrg.getComOrders());
           // 
           dbzxEqIecmOrg.setBatchNo(zxEqIecmOrg.getBatchNo());
           // 共通
           dbzxEqIecmOrg.setModifyUserInfo(userKey, realName);
           flag = zxEqIecmOrgMapper.updateByPrimaryKey(dbzxEqIecmOrg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqIecmOrg);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqIecmOrg(List<ZxEqIecmOrg> zxEqIecmOrgList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqIecmOrgList != null && zxEqIecmOrgList.size() > 0) {
           ZxEqIecmOrg zxEqIecmOrg = new ZxEqIecmOrg();
           zxEqIecmOrg.setModifyUserInfo(userKey, realName);
           flag = zxEqIecmOrgMapper.batchDeleteUpdateZxEqIecmOrg(zxEqIecmOrgList, zxEqIecmOrg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqIecmOrgList);
        }
    }
}
