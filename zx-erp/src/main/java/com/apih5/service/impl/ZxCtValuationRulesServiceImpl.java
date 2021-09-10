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
import com.apih5.mybatis.dao.ZxCtValuationRulesMapper;
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.service.ZxCtValuationRulesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtValuationRulesService")
public class ZxCtValuationRulesServiceImpl implements ZxCtValuationRulesService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtValuationRulesMapper zxCtValuationRulesMapper;

    @Override
    public ResponseEntity getZxCtValuationRulesListByCondition(ZxCtValuationRules zxCtValuationRules) {
        if (zxCtValuationRules == null) {
            zxCtValuationRules = new ZxCtValuationRules();
        }
        // 分页查询
        PageHelper.startPage(zxCtValuationRules.getPage(),zxCtValuationRules.getLimit());
        // 获取数据
        List<ZxCtValuationRules> zxCtValuationRulesList = zxCtValuationRulesMapper.selectByZxCtValuationRulesList(zxCtValuationRules);
        // 得到分页信息
        PageInfo<ZxCtValuationRules> p = new PageInfo<>(zxCtValuationRulesList);

        return repEntity.okList(zxCtValuationRulesList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtValuationRulesDetails(ZxCtValuationRules zxCtValuationRules) {
        if (zxCtValuationRules == null) {
            zxCtValuationRules = new ZxCtValuationRules();
        }
        // 获取数据
        ZxCtValuationRules dbZxCtValuationRules = zxCtValuationRulesMapper.selectByPrimaryKey(zxCtValuationRules.getId());
        // 数据存在
        if (dbZxCtValuationRules != null) {
            return repEntity.ok(dbZxCtValuationRules);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxCtValuationRules(ZxCtValuationRules zxCtValuationRules) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtValuationRules.setId(UuidUtil.generate());
        zxCtValuationRules.setCreateUserInfo(userKey, realName);
        int flag = zxCtValuationRulesMapper.insert(zxCtValuationRules);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxCtValuationRules);
        }
    }

    @Override
    public ResponseEntity updateZxCtValuationRules(ZxCtValuationRules zxCtValuationRules) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtValuationRules dbzxCtValuationRules = zxCtValuationRulesMapper.selectByPrimaryKey(zxCtValuationRules.getId());
        if (dbzxCtValuationRules != null && StrUtil.isNotEmpty(dbzxCtValuationRules.getId())) {
           // 编码
           dbzxCtValuationRules.setOrderNum(zxCtValuationRules.getOrderNum());
           // 计价规则名称
           dbzxCtValuationRules.setRuleName(zxCtValuationRules.getRuleName());
           // 备注
           dbzxCtValuationRules.setRemark(zxCtValuationRules.getRemark());
           // 编制时间
           dbzxCtValuationRules.setEditTime(zxCtValuationRules.getEditTime());
           // 共通
           dbzxCtValuationRules.setModifyUserInfo(userKey, realName);
           flag = zxCtValuationRulesMapper.updateByPrimaryKey(dbzxCtValuationRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxCtValuationRules);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtValuationRules(List<ZxCtValuationRules> zxCtValuationRulesList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtValuationRulesList != null && zxCtValuationRulesList.size() > 0) {
           ZxCtValuationRules zxCtValuationRules = new ZxCtValuationRules();
           zxCtValuationRules.setModifyUserInfo(userKey, realName);
           flag = zxCtValuationRulesMapper.batchDeleteUpdateZxCtValuationRules(zxCtValuationRulesList, zxCtValuationRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxCtValuationRulesList);
        }
    }
}
