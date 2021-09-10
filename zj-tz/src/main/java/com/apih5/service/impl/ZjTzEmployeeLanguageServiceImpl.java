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
import com.apih5.mybatis.dao.ZjTzEmployeeLanguageMapper;
import com.apih5.mybatis.pojo.ZjTzEmployeeLanguage;
import com.apih5.service.ZjTzEmployeeLanguageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzEmployeeLanguageService")
public class ZjTzEmployeeLanguageServiceImpl implements ZjTzEmployeeLanguageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeLanguageMapper zjTzEmployeeLanguageMapper;

    @Override
    public ResponseEntity getZjTzEmployeeLanguageListByCondition(ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        if (zjTzEmployeeLanguage == null) {
            zjTzEmployeeLanguage = new ZjTzEmployeeLanguage();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeLanguage.getPage(),zjTzEmployeeLanguage.getLimit());
        // 获取数据
        List<ZjTzEmployeeLanguage> zjTzEmployeeLanguageList = zjTzEmployeeLanguageMapper.selectByZjTzEmployeeLanguageList(zjTzEmployeeLanguage);
        // 得到分页信息
        PageInfo<ZjTzEmployeeLanguage> p = new PageInfo<>(zjTzEmployeeLanguageList);

        return repEntity.okList(zjTzEmployeeLanguageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzEmployeeLanguageDetails(ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        if (zjTzEmployeeLanguage == null) {
            zjTzEmployeeLanguage = new ZjTzEmployeeLanguage();
        }
        // 获取数据
        ZjTzEmployeeLanguage dbZjTzEmployeeLanguage = zjTzEmployeeLanguageMapper.selectByPrimaryKey(zjTzEmployeeLanguage.getLanguageId());
        // 数据存在
        if (dbZjTzEmployeeLanguage != null) {
            return repEntity.ok(dbZjTzEmployeeLanguage);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzEmployeeLanguage(ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzEmployeeLanguage.setLanguageId(UuidUtil.generate());
        zjTzEmployeeLanguage.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeLanguageMapper.insert(zjTzEmployeeLanguage);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeLanguage);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeLanguage(ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeLanguage dbzjTzEmployeeLanguage = zjTzEmployeeLanguageMapper.selectByPrimaryKey(zjTzEmployeeLanguage.getLanguageId());
        if (dbzjTzEmployeeLanguage != null && StrUtil.isNotEmpty(dbzjTzEmployeeLanguage.getLanguageId())) {
           // 员工信息id
           dbzjTzEmployeeLanguage.setEmployeeInfoId(zjTzEmployeeLanguage.getEmployeeInfoId());
           // 语种
           dbzjTzEmployeeLanguage.setLanguage(zjTzEmployeeLanguage.getLanguage());
           // 语言熟练程度
           dbzjTzEmployeeLanguage.setLanguageProficiency(zjTzEmployeeLanguage.getLanguageProficiency());
           // 证书名称
           dbzjTzEmployeeLanguage.setCertificateName(zjTzEmployeeLanguage.getCertificateName());
           // 获证日期
           dbzjTzEmployeeLanguage.setGetTime(zjTzEmployeeLanguage.getGetTime());
           // 共通
           dbzjTzEmployeeLanguage.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeLanguageMapper.updateByPrimaryKey(dbzjTzEmployeeLanguage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeLanguage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeLanguage(List<ZjTzEmployeeLanguage> zjTzEmployeeLanguageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeLanguageList != null && zjTzEmployeeLanguageList.size() > 0) {
           ZjTzEmployeeLanguage zjTzEmployeeLanguage = new ZjTzEmployeeLanguage();
           zjTzEmployeeLanguage.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeLanguageMapper.batchDeleteUpdateZjTzEmployeeLanguage(zjTzEmployeeLanguageList, zjTzEmployeeLanguage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeLanguageList);
        }
    }
}