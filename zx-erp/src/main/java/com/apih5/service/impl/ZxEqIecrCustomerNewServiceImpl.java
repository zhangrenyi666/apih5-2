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
import com.apih5.mybatis.dao.ZxEqIecrCustomerNewMapper;
import com.apih5.mybatis.pojo.ZxEqIecrCustomerNew;
import com.apih5.service.ZxEqIecrCustomerNewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqIecrCustomerNewService")
public class ZxEqIecrCustomerNewServiceImpl implements ZxEqIecrCustomerNewService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqIecrCustomerNewMapper zxEqIecrCustomerNewMapper;

    @Override
    public ResponseEntity getZxEqIecrCustomerNewListByCondition(ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        if (zxEqIecrCustomerNew == null) {
            zxEqIecrCustomerNew = new ZxEqIecrCustomerNew();
        }
        // 分页查询
        PageHelper.startPage(zxEqIecrCustomerNew.getPage(),zxEqIecrCustomerNew.getLimit());
        // 获取数据
        List<ZxEqIecrCustomerNew> zxEqIecrCustomerNewList = zxEqIecrCustomerNewMapper.selectByZxEqIecrCustomerNewList(zxEqIecrCustomerNew);
        // 得到分页信息
        PageInfo<ZxEqIecrCustomerNew> p = new PageInfo<>(zxEqIecrCustomerNewList);

        return repEntity.okList(zxEqIecrCustomerNewList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqIecrCustomerNewDetails(ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        if (zxEqIecrCustomerNew == null) {
            zxEqIecrCustomerNew = new ZxEqIecrCustomerNew();
        }
        // 获取数据
        ZxEqIecrCustomerNew dbZxEqIecrCustomerNew = zxEqIecrCustomerNewMapper.selectByPrimaryKey(zxEqIecrCustomerNew.getCustomerNewID());
        // 数据存在
        if (dbZxEqIecrCustomerNew != null) {
            return repEntity.ok(dbZxEqIecrCustomerNew);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqIecrCustomerNew(ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqIecrCustomerNew.setCustomerNewID(UuidUtil.generate());
        zxEqIecrCustomerNew.setCreateUserInfo(userKey, realName);
        int flag = zxEqIecrCustomerNewMapper.insert(zxEqIecrCustomerNew);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqIecrCustomerNew);
        }
    }

    @Override
    public ResponseEntity updateZxEqIecrCustomerNew(ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqIecrCustomerNew dbzxEqIecrCustomerNew = zxEqIecrCustomerNewMapper.selectByPrimaryKey(zxEqIecrCustomerNew.getCustomerNewID());
        if (dbzxEqIecrCustomerNew != null && StrUtil.isNotEmpty(dbzxEqIecrCustomerNew.getCustomerNewID())) {
           // 供应商名称
           dbzxEqIecrCustomerNew.setCustomerName(zxEqIecrCustomerNew.getCustomerName());
           // 供应商编号
           dbzxEqIecrCustomerNew.setCustomerNo(zxEqIecrCustomerNew.getCustomerNo());
           // 法人
           dbzxEqIecrCustomerNew.setCorparation(zxEqIecrCustomerNew.getCorparation());
           // 组织机构代码证
           dbzxEqIecrCustomerNew.setOrgCertificate(zxEqIecrCustomerNew.getOrgCertificate());
           // 省份
           dbzxEqIecrCustomerNew.setProvince(zxEqIecrCustomerNew.getProvince());
           // 市
           dbzxEqIecrCustomerNew.setDistrict(zxEqIecrCustomerNew.getDistrict());
           // 地址
           dbzxEqIecrCustomerNew.setAddress(zxEqIecrCustomerNew.getAddress());
           // 电话
           dbzxEqIecrCustomerNew.setTelephone(zxEqIecrCustomerNew.getTelephone());
           // 传真
           dbzxEqIecrCustomerNew.setFax(zxEqIecrCustomerNew.getFax());
           // 邮箱
           dbzxEqIecrCustomerNew.setEmail(zxEqIecrCustomerNew.getEmail());
           // 主页
           dbzxEqIecrCustomerNew.setHomePage(zxEqIecrCustomerNew.getHomePage());
           // 供货单位类型
           dbzxEqIecrCustomerNew.setSupplierType(zxEqIecrCustomerNew.getSupplierType());
           // 经营范围
           dbzxEqIecrCustomerNew.setServices(zxEqIecrCustomerNew.getServices());
           // 更新时间
           dbzxEqIecrCustomerNew.setEditTime(zxEqIecrCustomerNew.getEditTime());
           // 注册日期
           dbzxEqIecrCustomerNew.setRegDate(zxEqIecrCustomerNew.getRegDate());
           // 注册资金
           dbzxEqIecrCustomerNew.setRegMoney(zxEqIecrCustomerNew.getRegMoney());
           // 共通
           dbzxEqIecrCustomerNew.setModifyUserInfo(userKey, realName);
           flag = zxEqIecrCustomerNewMapper.updateByPrimaryKey(dbzxEqIecrCustomerNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqIecrCustomerNew);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqIecrCustomerNew(List<ZxEqIecrCustomerNew> zxEqIecrCustomerNewList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqIecrCustomerNewList != null && zxEqIecrCustomerNewList.size() > 0) {
           ZxEqIecrCustomerNew zxEqIecrCustomerNew = new ZxEqIecrCustomerNew();
           zxEqIecrCustomerNew.setModifyUserInfo(userKey, realName);
           flag = zxEqIecrCustomerNewMapper.batchDeleteUpdateZxEqIecrCustomerNew(zxEqIecrCustomerNewList, zxEqIecrCustomerNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqIecrCustomerNewList);
        }
    }
}
