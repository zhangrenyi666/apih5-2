package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzComplianceBaseMapper;
import com.apih5.mybatis.pojo.ZjTzComplianceBase;
import com.apih5.service.ZjTzComplianceBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzComplianceBaseService")
public class ZjTzComplianceBaseServiceImpl implements ZjTzComplianceBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzComplianceBaseMapper zjTzComplianceBaseMapper;

    @Override
    public ResponseEntity getZjTzComplianceBaseListByCondition(ZjTzComplianceBase zjTzComplianceBase) {
        if (zjTzComplianceBase == null) {
            zjTzComplianceBase = new ZjTzComplianceBase();
        }
        // 分页查询
        PageHelper.startPage(zjTzComplianceBase.getPage(),zjTzComplianceBase.getLimit());
        // 获取数据
        List<ZjTzComplianceBase> zjTzComplianceBaseList = zjTzComplianceBaseMapper.selectByZjTzComplianceBaseList(zjTzComplianceBase);
        // 得到分页信息
        PageInfo<ZjTzComplianceBase> p = new PageInfo<>(zjTzComplianceBaseList);

        return repEntity.okList(zjTzComplianceBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzComplianceBaseDetails(ZjTzComplianceBase zjTzComplianceBase) {
        if (zjTzComplianceBase == null) {
            zjTzComplianceBase = new ZjTzComplianceBase();
        }
        // 获取数据
        ZjTzComplianceBase dbZjTzComplianceBase = zjTzComplianceBaseMapper.selectByPrimaryKey(zjTzComplianceBase.getComplianceBaseId());
        // 数据存在
        if (dbZjTzComplianceBase != null) {
            return repEntity.ok(dbZjTzComplianceBase);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzComplianceBase(ZjTzComplianceBase zjTzComplianceBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        ZjTzComplianceBase complianceBase = new ZjTzComplianceBase();
        complianceBase.setNum(zjTzComplianceBase.getNum());
        List<ZjTzComplianceBase> baseList = zjTzComplianceBaseMapper.selectByZjTzComplianceBaseList(complianceBase);
        if(baseList != null && baseList.size() >0) {
        	return repEntity.layerMessage("no", "该编号已经存在。请重新输入！");
        }
        zjTzComplianceBase.setComplianceBaseId(UuidUtil.generate());
        zjTzComplianceBase.setCreateUserInfo(userKey, realName);
        int flag = zjTzComplianceBaseMapper.insert(zjTzComplianceBase);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzComplianceBase);
        }
    }

    @Override
    public ResponseEntity updateZjTzComplianceBase(ZjTzComplianceBase zjTzComplianceBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzComplianceBase dbzjTzComplianceBase = zjTzComplianceBaseMapper.selectByPrimaryKey(zjTzComplianceBase.getComplianceBaseId());
        if (dbzjTzComplianceBase != null && StrUtil.isNotEmpty(dbzjTzComplianceBase.getComplianceBaseId())) {
           // 环节编号
           dbzjTzComplianceBase.setNum(zjTzComplianceBase.getNum());
           // 合规环节名称
           dbzjTzComplianceBase.setComplianceBanseName(zjTzComplianceBase.getComplianceBanseName());
           // 共通
           dbzjTzComplianceBase.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceBaseMapper.updateByPrimaryKey(dbzjTzComplianceBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzComplianceBase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzComplianceBase(List<ZjTzComplianceBase> zjTzComplianceBaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzComplianceBaseList != null && zjTzComplianceBaseList.size() > 0) {
           ZjTzComplianceBase zjTzComplianceBase = new ZjTzComplianceBase();
           zjTzComplianceBase.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceBaseMapper.batchDeleteUpdateZjTzComplianceBase(zjTzComplianceBaseList, zjTzComplianceBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzComplianceBaseList);
        }
    }
}