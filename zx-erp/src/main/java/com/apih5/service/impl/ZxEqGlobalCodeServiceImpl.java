package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqGlobalCodeMapper;
import com.apih5.mybatis.pojo.ZxEqGlobalCode;
import com.apih5.service.ZxEqGlobalCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqGlobalCodeService")
public class ZxEqGlobalCodeServiceImpl implements ZxEqGlobalCodeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqGlobalCodeMapper zxEqGlobalCodeMapper;

    @Override
    public ResponseEntity getZxEqGlobalCodeListByCondition(ZxEqGlobalCode zxEqGlobalCode) {
        if (zxEqGlobalCode == null) {
            zxEqGlobalCode = new ZxEqGlobalCode();
        }
        // 分页查询
        PageHelper.startPage(zxEqGlobalCode.getPage(),zxEqGlobalCode.getLimit());
        // 获取数据
        List<ZxEqGlobalCode> zxEqGlobalCodeList = zxEqGlobalCodeMapper.selectByZxEqGlobalCodeList(zxEqGlobalCode);
        // 得到分页信息
        PageInfo<ZxEqGlobalCode> p = new PageInfo<>(zxEqGlobalCodeList);

        return repEntity.okList(zxEqGlobalCodeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqGlobalCodeDetails(ZxEqGlobalCode zxEqGlobalCode) {
        if (zxEqGlobalCode == null) {
            zxEqGlobalCode = new ZxEqGlobalCode();
        }
        // 获取数据
        ZxEqGlobalCode dbZxEqGlobalCode = zxEqGlobalCodeMapper.selectByPrimaryKey(zxEqGlobalCode.getId());
        // 数据存在
        if (dbZxEqGlobalCode != null) {
            return repEntity.ok(dbZxEqGlobalCode);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqGlobalCode(ZxEqGlobalCode zxEqGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqGlobalCode.setId(UuidUtil.generate());
        zxEqGlobalCode.setCreateUserInfo(userKey, realName);
        int flag = zxEqGlobalCodeMapper.insert(zxEqGlobalCode);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqGlobalCode);
        }
    }

    @Override
    public ResponseEntity updateZxEqGlobalCode(ZxEqGlobalCode zxEqGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqGlobalCode dbzxEqGlobalCode = zxEqGlobalCodeMapper.selectByPrimaryKey(zxEqGlobalCode.getId());
        if (dbzxEqGlobalCode != null && StrUtil.isNotEmpty(dbzxEqGlobalCode.getId())) {
           // categoryID
           dbzxEqGlobalCode.setCategoryID(zxEqGlobalCode.getCategoryID());
           // 代码编号
           dbzxEqGlobalCode.setGlobalCode(zxEqGlobalCode.getGlobalCode());
           // 代码描述
           dbzxEqGlobalCode.setGlobalDesc(zxEqGlobalCode.getGlobalDesc());
           // 是否启用
           dbzxEqGlobalCode.setEnable(zxEqGlobalCode.getEnable());
           // 是否默认选项
           dbzxEqGlobalCode.setSelected(zxEqGlobalCode.getSelected());
           // 备注
           dbzxEqGlobalCode.setRemark(zxEqGlobalCode.getRemark());
           // 
           dbzxEqGlobalCode.setEditTime(zxEqGlobalCode.getEditTime());
           // 
           dbzxEqGlobalCode.setPercentage(zxEqGlobalCode.getPercentage());
           // pp1
           dbzxEqGlobalCode.setPp1(zxEqGlobalCode.getPp1());
           // pp2
           dbzxEqGlobalCode.setPp2(zxEqGlobalCode.getPp2());
           // pp3
           dbzxEqGlobalCode.setPp3(zxEqGlobalCode.getPp3());
           // pp4
           dbzxEqGlobalCode.setPp4(zxEqGlobalCode.getPp4());
           // 共通
           dbzxEqGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxEqGlobalCodeMapper.updateByPrimaryKey(dbzxEqGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqGlobalCode);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqGlobalCode(List<ZxEqGlobalCode> zxEqGlobalCodeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqGlobalCodeList != null && zxEqGlobalCodeList.size() > 0) {
           ZxEqGlobalCode zxEqGlobalCode = new ZxEqGlobalCode();
           zxEqGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxEqGlobalCodeMapper.batchDeleteUpdateZxEqGlobalCode(zxEqGlobalCodeList, zxEqGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqGlobalCodeList);
        }
    }
}
