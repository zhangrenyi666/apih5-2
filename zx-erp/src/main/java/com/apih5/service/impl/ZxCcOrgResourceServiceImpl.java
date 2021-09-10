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
import com.apih5.mybatis.dao.ZxCcOrgResourceMapper;
import com.apih5.mybatis.pojo.ZxCcOrgResource;
import com.apih5.service.ZxCcOrgResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCcOrgResourceService")
public class ZxCcOrgResourceServiceImpl implements ZxCcOrgResourceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCcOrgResourceMapper zxCcOrgResourceMapper;

    @Override
    public ResponseEntity getZxCcOrgResourceListByCondition(ZxCcOrgResource zxCcOrgResource) {
        if (zxCcOrgResource == null) {
            zxCcOrgResource = new ZxCcOrgResource();
        }
        // 分页查询
        PageHelper.startPage(zxCcOrgResource.getPage(),zxCcOrgResource.getLimit());
        // 获取数据
        List<ZxCcOrgResource> zxCcOrgResourceList = zxCcOrgResourceMapper.selectByZxCcOrgResourceList(zxCcOrgResource);
        // 得到分页信息
        PageInfo<ZxCcOrgResource> p = new PageInfo<>(zxCcOrgResourceList);

        return repEntity.okList(zxCcOrgResourceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCcOrgResourceDetail(ZxCcOrgResource zxCcOrgResource) {
        if (zxCcOrgResource == null) {
            zxCcOrgResource = new ZxCcOrgResource();
        }
        // 获取数据
        ZxCcOrgResource dbZxCcOrgResource = zxCcOrgResourceMapper.selectByPrimaryKey(zxCcOrgResource.getId());
        // 数据存在
        if (dbZxCcOrgResource != null) {
            return repEntity.ok(dbZxCcOrgResource);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCcOrgResource(ZxCcOrgResource zxCcOrgResource) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCcOrgResource.setId(UuidUtil.generate());
        zxCcOrgResource.setCreateUserInfo(userKey, realName);
        int flag = zxCcOrgResourceMapper.insert(zxCcOrgResource);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCcOrgResource);
        }
    }

    @Override
    public ResponseEntity updateZxCcOrgResource(ZxCcOrgResource zxCcOrgResource) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCcOrgResource dbZxCcOrgResource = zxCcOrgResourceMapper.selectByPrimaryKey(zxCcOrgResource.getId());
        if (dbZxCcOrgResource != null && StrUtil.isNotEmpty(dbZxCcOrgResource.getId())) {
           // 资源ID
           dbZxCcOrgResource.setResID(zxCcOrgResource.getResID());
           // 资源编号
           dbZxCcOrgResource.setResCode(zxCcOrgResource.getResCode());
           // 等级
           dbZxCcOrgResource.setResName(zxCcOrgResource.getResName());
           // 规格型号
           dbZxCcOrgResource.setSpec(zxCcOrgResource.getSpec());
           // 单位
           dbZxCcOrgResource.setUnit(zxCcOrgResource.getUnit());
           // 机构ID
           dbZxCcOrgResource.setOrgID(zxCcOrgResource.getOrgID());
           // 标准单价
           dbZxCcOrgResource.setUnitPrice(zxCcOrgResource.getUnitPrice());
           // 预算单价
           dbZxCcOrgResource.setBudgPrice(zxCcOrgResource.getBudgPrice());
           // 资源类别
           dbZxCcOrgResource.setBizType(zxCcOrgResource.getBizType());
           // 计价方式
           dbZxCcOrgResource.setPriceType(zxCcOrgResource.getPriceType());
           // 是否是具体资源
           dbZxCcOrgResource.setIsRes(zxCcOrgResource.getIsRes());
           // 资源类型
           dbZxCcOrgResource.setResStyle(zxCcOrgResource.getResStyle());
           // 编制机构
           dbZxCcOrgResource.setOrgName(zxCcOrgResource.getOrgName());
           // 是否局下达
           dbZxCcOrgResource.setIsGroup(zxCcOrgResource.getIsGroup());
           // 备注
           dbZxCcOrgResource.setRemark(zxCcOrgResource.getRemark());
           // projectID
           dbZxCcOrgResource.setProjectID(zxCcOrgResource.getProjectID());
           // isComplex
           dbZxCcOrgResource.setIsComplex(zxCcOrgResource.getIsComplex());
           // pp1
           dbZxCcOrgResource.setPp1(zxCcOrgResource.getPp1());
           // pp2
           dbZxCcOrgResource.setPp2(zxCcOrgResource.getPp2());
           // pp3
           dbZxCcOrgResource.setPp3(zxCcOrgResource.getPp3());
           // pp4
           dbZxCcOrgResource.setPp4(zxCcOrgResource.getPp4());
           // pp5
           dbZxCcOrgResource.setPp5(zxCcOrgResource.getPp5());
           // pp6
           dbZxCcOrgResource.setPp6(zxCcOrgResource.getPp6());
           // pp7
           dbZxCcOrgResource.setPp7(zxCcOrgResource.getPp7());
           // pp8
           dbZxCcOrgResource.setPp8(zxCcOrgResource.getPp8());
           // pp9
           dbZxCcOrgResource.setPp9(zxCcOrgResource.getPp9());
           // pp10
           dbZxCcOrgResource.setPp10(zxCcOrgResource.getPp10());
           // resABC
           dbZxCcOrgResource.setResABC(zxCcOrgResource.getResABC());
           // sendTime
           dbZxCcOrgResource.setSendTime(zxCcOrgResource.getSendTime());
           // isMainMaterial
           dbZxCcOrgResource.setIsMainMaterial(zxCcOrgResource.getIsMainMaterial());
           // isSporadicMaterial
           dbZxCcOrgResource.setIsSporadicMaterial(zxCcOrgResource.getIsSporadicMaterial());
           // isUse
           dbZxCcOrgResource.setIsUse(zxCcOrgResource.getIsUse());
           // combProp
           dbZxCcOrgResource.setCombProp(zxCcOrgResource.getCombProp());
           // 共通
           dbZxCcOrgResource.setModifyUserInfo(userKey, realName);
           flag = zxCcOrgResourceMapper.updateByPrimaryKey(dbZxCcOrgResource);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCcOrgResource);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCcOrgResource(List<ZxCcOrgResource> zxCcOrgResourceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCcOrgResourceList != null && zxCcOrgResourceList.size() > 0) {
           ZxCcOrgResource zxCcOrgResource = new ZxCcOrgResource();
           zxCcOrgResource.setModifyUserInfo(userKey, realName);
           flag = zxCcOrgResourceMapper.batchDeleteUpdateZxCcOrgResource(zxCcOrgResourceList, zxCcOrgResource);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCcOrgResourceList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
