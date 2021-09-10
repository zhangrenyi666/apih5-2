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
import com.apih5.mybatis.dao.ZxEqEquipIntegratedQueryMapper;
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;
import com.apih5.service.ZxEqEquipIntegratedQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipIntegratedQueryService")
public class ZxEqEquipIntegratedQueryServiceImpl implements ZxEqEquipIntegratedQueryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipIntegratedQueryMapper zxEqEquipIntegratedQueryMapper;

    @Override
    public ResponseEntity getZxEqEquipIntegratedQueryListByCondition(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        if (zxEqEquipIntegratedQuery == null) {
            zxEqEquipIntegratedQuery = new ZxEqEquipIntegratedQuery();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipIntegratedQuery.getPage(),zxEqEquipIntegratedQuery.getLimit());
        // 获取数据
        List<ZxEqEquipIntegratedQuery> zxEqEquipIntegratedQueryList = zxEqEquipIntegratedQueryMapper.selectByZxEqEquipIntegratedQueryList(zxEqEquipIntegratedQuery);
        // 得到分页信息
        PageInfo<ZxEqEquipIntegratedQuery> p = new PageInfo<>(zxEqEquipIntegratedQueryList);

        return repEntity.okList(zxEqEquipIntegratedQueryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipIntegratedQueryDetail(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        if (zxEqEquipIntegratedQuery == null) {
            zxEqEquipIntegratedQuery = new ZxEqEquipIntegratedQuery();
        }
        // 获取数据
        ZxEqEquipIntegratedQuery dbZxEqEquipIntegratedQuery = zxEqEquipIntegratedQueryMapper.selectByPrimaryKey(zxEqEquipIntegratedQuery.getZxEqEquipIntegratedQueryId());
        // 数据存在
        if (dbZxEqEquipIntegratedQuery != null) {
            return repEntity.ok(dbZxEqEquipIntegratedQuery);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqEquipIntegratedQuery(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipIntegratedQuery.setZxEqEquipIntegratedQueryId(UuidUtil.generate());
        zxEqEquipIntegratedQuery.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipIntegratedQueryMapper.insert(zxEqEquipIntegratedQuery);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqEquipIntegratedQuery);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipIntegratedQuery(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipIntegratedQuery dbZxEqEquipIntegratedQuery = zxEqEquipIntegratedQueryMapper.selectByPrimaryKey(zxEqEquipIntegratedQuery.getZxEqEquipIntegratedQueryId());
        if (dbZxEqEquipIntegratedQuery != null && StrUtil.isNotEmpty(dbZxEqEquipIntegratedQuery.getZxEqEquipIntegratedQueryId())) {
           // 机构名称
           dbZxEqEquipIntegratedQuery.setDepartmentName(zxEqEquipIntegratedQuery.getDepartmentName());
           // 机构ID
           dbZxEqEquipIntegratedQuery.setDepartmentId(zxEqEquipIntegratedQuery.getDepartmentId());
           // 排顺
           dbZxEqEquipIntegratedQuery.setSort(zxEqEquipIntegratedQuery.getSort());
           // A类台数
           dbZxEqEquipIntegratedQuery.setCount1(zxEqEquipIntegratedQuery.getCount1());
           // A类原值
           dbZxEqEquipIntegratedQuery.setOrginalValue1(zxEqEquipIntegratedQuery.getOrginalValue1());
           // A类净值
           dbZxEqEquipIntegratedQuery.setLeftValue1(zxEqEquipIntegratedQuery.getLeftValue1());
           // B类台数
           dbZxEqEquipIntegratedQuery.setCount2(zxEqEquipIntegratedQuery.getCount2());
           // B类原值
           dbZxEqEquipIntegratedQuery.setOrginalValue2(zxEqEquipIntegratedQuery.getOrginalValue2());
           // B类净值
           dbZxEqEquipIntegratedQuery.setLeftValue2(zxEqEquipIntegratedQuery.getLeftValue2());
           // C类台数
           dbZxEqEquipIntegratedQuery.setCount3(zxEqEquipIntegratedQuery.getCount3());
           // C类原值
           dbZxEqEquipIntegratedQuery.setOrginalValue3(zxEqEquipIntegratedQuery.getOrginalValue3());
           // C类净值
           dbZxEqEquipIntegratedQuery.setLeftValue3(zxEqEquipIntegratedQuery.getLeftValue3());
           // D类台数
           dbZxEqEquipIntegratedQuery.setCount4(zxEqEquipIntegratedQuery.getCount4());
           // D类原值
           dbZxEqEquipIntegratedQuery.setOrginalValue4(zxEqEquipIntegratedQuery.getOrginalValue4());
           // D类净值
           dbZxEqEquipIntegratedQuery.setLeftValue4(zxEqEquipIntegratedQuery.getLeftValue4());
           // 备注
           dbZxEqEquipIntegratedQuery.setRemarks(zxEqEquipIntegratedQuery.getRemarks());
           // 共通
           dbZxEqEquipIntegratedQuery.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipIntegratedQueryMapper.updateByPrimaryKey(dbZxEqEquipIntegratedQuery);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqEquipIntegratedQuery);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipIntegratedQuery(List<ZxEqEquipIntegratedQuery> zxEqEquipIntegratedQueryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipIntegratedQueryList != null && zxEqEquipIntegratedQueryList.size() > 0) {
           ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery = new ZxEqEquipIntegratedQuery();
           zxEqEquipIntegratedQuery.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipIntegratedQueryMapper.batchDeleteUpdateZxEqEquipIntegratedQuery(zxEqEquipIntegratedQueryList, zxEqEquipIntegratedQuery);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqEquipIntegratedQueryList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(ZxEqEquipIntegratedQuery zxEqEquipIntegratedQuery) {
    	if (zxEqEquipIntegratedQuery == null) {
            zxEqEquipIntegratedQuery = new ZxEqEquipIntegratedQuery();
        }
      
        // 获取数据
        List<ZxEqEquipIntegratedQuery> zxEqEquipIntegratedQueryList = zxEqEquipIntegratedQueryMapper.selectZxEqEquipIntegratedQuery(zxEqEquipIntegratedQuery);
        
        return repEntity.okList(zxEqEquipIntegratedQueryList,zxEqEquipIntegratedQueryList.size());
    }
}
