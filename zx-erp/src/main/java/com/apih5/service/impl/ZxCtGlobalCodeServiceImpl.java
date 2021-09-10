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
import com.apih5.mybatis.dao.ZxCtGlobalCodeMapper;
import com.apih5.mybatis.pojo.ZxCtGlobalCode;
import com.apih5.service.ZxCtGlobalCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtGlobalCodeService")
public class ZxCtGlobalCodeServiceImpl implements ZxCtGlobalCodeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtGlobalCodeMapper zxCtGlobalCodeMapper;

    @Override
    public ResponseEntity getZxCtGlobalCodeListByCondition(ZxCtGlobalCode zxCtGlobalCode) {
        if (zxCtGlobalCode == null) {
            zxCtGlobalCode = new ZxCtGlobalCode();
        }
        // 分页查询
        PageHelper.startPage(zxCtGlobalCode.getPage(),zxCtGlobalCode.getLimit());
        // 获取数据
        List<ZxCtGlobalCode> zxCtGlobalCodeList = zxCtGlobalCodeMapper.selectByZxCtGlobalCodeList(zxCtGlobalCode);
        // 得到分页信息
        PageInfo<ZxCtGlobalCode> p = new PageInfo<>(zxCtGlobalCodeList);

        return repEntity.okList(zxCtGlobalCodeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtGlobalCodeDetails(ZxCtGlobalCode zxCtGlobalCode) {
        if (zxCtGlobalCode == null) {
            zxCtGlobalCode = new ZxCtGlobalCode();
        }
        // 获取数据
        ZxCtGlobalCode dbZxCtGlobalCode = zxCtGlobalCodeMapper.selectByPrimaryKey(zxCtGlobalCode.getId());
        // 数据存在
        if (dbZxCtGlobalCode != null) {
            return repEntity.ok(dbZxCtGlobalCode);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxCtGlobalCode(ZxCtGlobalCode zxCtGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtGlobalCode.setId(UuidUtil.generate());
        zxCtGlobalCode.setCreateUserInfo(userKey, realName);
        int flag = zxCtGlobalCodeMapper.insert(zxCtGlobalCode);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxCtGlobalCode);
        }
    }

    @Override
    public ResponseEntity updateZxCtGlobalCode(ZxCtGlobalCode zxCtGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtGlobalCode dbzxCtGlobalCode = zxCtGlobalCodeMapper.selectByPrimaryKey(zxCtGlobalCode.getId());
        if (dbzxCtGlobalCode != null && StrUtil.isNotEmpty(dbzxCtGlobalCode.getId())) {
           // 
           dbzxCtGlobalCode.setCategoryID(zxCtGlobalCode.getCategoryID());
           // 代码编号
           dbzxCtGlobalCode.setGlobalCode(zxCtGlobalCode.getGlobalCode());
           // 代码描述
           dbzxCtGlobalCode.setGlobalDesc(zxCtGlobalCode.getGlobalDesc());
           // 是否启用
           dbzxCtGlobalCode.setEnable(zxCtGlobalCode.getEnable());
           // 是否默认选项
           dbzxCtGlobalCode.setSelected(zxCtGlobalCode.getSelected());
           // 备注
           dbzxCtGlobalCode.setRemark(zxCtGlobalCode.getRemark());
           // 
           dbzxCtGlobalCode.setEditTime(zxCtGlobalCode.getEditTime());
           // 
           dbzxCtGlobalCode.setPercentage(zxCtGlobalCode.getPercentage());
           // 
           dbzxCtGlobalCode.setPp1(zxCtGlobalCode.getPp1());
           // 
           dbzxCtGlobalCode.setPp2(zxCtGlobalCode.getPp2());
           // 
           dbzxCtGlobalCode.setPp3(zxCtGlobalCode.getPp3());
           // 
           dbzxCtGlobalCode.setPp4(zxCtGlobalCode.getPp4());
           // 共通
           dbzxCtGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxCtGlobalCodeMapper.updateByPrimaryKey(dbzxCtGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxCtGlobalCode);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtGlobalCode(List<ZxCtGlobalCode> zxCtGlobalCodeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtGlobalCodeList != null && zxCtGlobalCodeList.size() > 0) {
           ZxCtGlobalCode zxCtGlobalCode = new ZxCtGlobalCode();
           zxCtGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxCtGlobalCodeMapper.batchDeleteUpdateZxCtGlobalCode(zxCtGlobalCodeList, zxCtGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxCtGlobalCodeList);
        }
    }
}
