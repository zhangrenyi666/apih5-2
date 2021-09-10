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
import com.apih5.mybatis.dao.ZxEqToCompareAnalyseQueryPageMapper;
import com.apih5.mybatis.pojo.ZxEqToCompareAnalyseQueryPage;
import com.apih5.service.ZxEqToCompareAnalyseQueryPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqToCompareAnalyseQueryPageService")
public class ZxEqToCompareAnalyseQueryPageServiceImpl implements ZxEqToCompareAnalyseQueryPageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqToCompareAnalyseQueryPageMapper zxEqToCompareAnalyseQueryPageMapper;

    @Override
    public ResponseEntity getZxEqToCompareAnalyseQueryPageListByCondition(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        if (zxEqToCompareAnalyseQueryPage == null) {
            zxEqToCompareAnalyseQueryPage = new ZxEqToCompareAnalyseQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCompareAnalyseQueryPage.getPage(),zxEqToCompareAnalyseQueryPage.getLimit());
        // 获取数据
        List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList = zxEqToCompareAnalyseQueryPageMapper.selectByZxEqToCompareAnalyseQueryPageList(zxEqToCompareAnalyseQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToCompareAnalyseQueryPage> p = new PageInfo<>(zxEqToCompareAnalyseQueryPageList);

        return repEntity.okList(zxEqToCompareAnalyseQueryPageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqToCompareAnalyseQueryPageDetail(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        if (zxEqToCompareAnalyseQueryPage == null) {
            zxEqToCompareAnalyseQueryPage = new ZxEqToCompareAnalyseQueryPage();
        }
        // 获取数据
        ZxEqToCompareAnalyseQueryPage dbZxEqToCompareAnalyseQueryPage = zxEqToCompareAnalyseQueryPageMapper.selectByPrimaryKey(zxEqToCompareAnalyseQueryPage.getZxEqToCompareAnalyseQueryPageId());
        // 数据存在
        if (dbZxEqToCompareAnalyseQueryPage != null) {
            return repEntity.ok(dbZxEqToCompareAnalyseQueryPage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqToCompareAnalyseQueryPage(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqToCompareAnalyseQueryPage.setZxEqToCompareAnalyseQueryPageId(UuidUtil.generate());
        zxEqToCompareAnalyseQueryPage.setCreateUserInfo(userKey, realName);
        int flag = zxEqToCompareAnalyseQueryPageMapper.insert(zxEqToCompareAnalyseQueryPage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqToCompareAnalyseQueryPage);
        }
    }

    @Override
    public ResponseEntity updateZxEqToCompareAnalyseQueryPage(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqToCompareAnalyseQueryPage dbZxEqToCompareAnalyseQueryPage = zxEqToCompareAnalyseQueryPageMapper.selectByPrimaryKey(zxEqToCompareAnalyseQueryPage.getZxEqToCompareAnalyseQueryPageId());
        if (dbZxEqToCompareAnalyseQueryPage != null && StrUtil.isNotEmpty(dbZxEqToCompareAnalyseQueryPage.getZxEqToCompareAnalyseQueryPageId())) {
           // 机构ID
           dbZxEqToCompareAnalyseQueryPage.setDepartmentId(zxEqToCompareAnalyseQueryPage.getDepartmentId());
           // 机构名称
           dbZxEqToCompareAnalyseQueryPage.setDepartmentName(zxEqToCompareAnalyseQueryPage.getDepartmentName());
           // 设备ID
           dbZxEqToCompareAnalyseQueryPage.setResCatalogID(zxEqToCompareAnalyseQueryPage.getResCatalogID());
           // 设备名称
           dbZxEqToCompareAnalyseQueryPage.setResCatalogName(zxEqToCompareAnalyseQueryPage.getResCatalogName());
           // ABC分类
           dbZxEqToCompareAnalyseQueryPage.setAbcType(zxEqToCompareAnalyseQueryPage.getAbcType());
           // 上期数量（台）
           dbZxEqToCompareAnalyseQueryPage.setCount1(zxEqToCompareAnalyseQueryPage.getCount1());
           // 上期原值（万元）
           dbZxEqToCompareAnalyseQueryPage.setOrginalValue1(zxEqToCompareAnalyseQueryPage.getOrginalValue1());
           // 本期数量（台）
           dbZxEqToCompareAnalyseQueryPage.setCount2(zxEqToCompareAnalyseQueryPage.getCount2());
           // 本期原值（万元）
           dbZxEqToCompareAnalyseQueryPage.setOrginalValue2(zxEqToCompareAnalyseQueryPage.getOrginalValue2());
           // 同比增长
           dbZxEqToCompareAnalyseQueryPage.setCompareAddRate(zxEqToCompareAnalyseQueryPage.getCompareAddRate());
           // 备注
           dbZxEqToCompareAnalyseQueryPage.setRemarks(zxEqToCompareAnalyseQueryPage.getRemarks());
           // 排序
           dbZxEqToCompareAnalyseQueryPage.setSort(zxEqToCompareAnalyseQueryPage.getSort());
           // 共通
           dbZxEqToCompareAnalyseQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToCompareAnalyseQueryPageMapper.updateByPrimaryKey(dbZxEqToCompareAnalyseQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqToCompareAnalyseQueryPage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqToCompareAnalyseQueryPage(List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqToCompareAnalyseQueryPageList != null && zxEqToCompareAnalyseQueryPageList.size() > 0) {
           ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage = new ZxEqToCompareAnalyseQueryPage();
           zxEqToCompareAnalyseQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToCompareAnalyseQueryPageMapper.batchDeleteUpdateZxEqToCompareAnalyseQueryPage(zxEqToCompareAnalyseQueryPageList, zxEqToCompareAnalyseQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqToCompareAnalyseQueryPageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        if (zxEqToCompareAnalyseQueryPage == null) {
            zxEqToCompareAnalyseQueryPage = new ZxEqToCompareAnalyseQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCompareAnalyseQueryPage.getPage(),zxEqToCompareAnalyseQueryPage.getLimit());
        // 获取数据
        List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList = zxEqToCompareAnalyseQueryPageMapper.selectZxEqToCompareAnalyseQueryPage(zxEqToCompareAnalyseQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToCompareAnalyseQueryPage> p = new PageInfo<>(zxEqToCompareAnalyseQueryPageList);

        return repEntity.okList(zxEqToCompareAnalyseQueryPageList, p.getTotal());
    }
    
    @Override
    public ResponseEntity ureportZxEqEquipIntegratedQueryPeriodIdle(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage) {
        if (zxEqToCompareAnalyseQueryPage == null) {
            zxEqToCompareAnalyseQueryPage = new ZxEqToCompareAnalyseQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCompareAnalyseQueryPage.getPage(),zxEqToCompareAnalyseQueryPage.getLimit());
        // 获取数据
        List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList = zxEqToCompareAnalyseQueryPageMapper.selectZxEqToCompareAnalyseQueryPeriod(zxEqToCompareAnalyseQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToCompareAnalyseQueryPage> p = new PageInfo<>(zxEqToCompareAnalyseQueryPageList);

        return repEntity.okList(zxEqToCompareAnalyseQueryPageList, p.getTotal());
    }

}
