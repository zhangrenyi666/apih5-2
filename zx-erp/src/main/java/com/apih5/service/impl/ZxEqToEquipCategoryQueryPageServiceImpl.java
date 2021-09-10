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
import com.apih5.mybatis.dao.ZxEqToEquipCategoryQueryPageMapper;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQueryPage;
import com.apih5.service.ZxEqToEquipCategoryQueryPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqToEquipCategoryQueryPageService")
public class ZxEqToEquipCategoryQueryPageServiceImpl implements ZxEqToEquipCategoryQueryPageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqToEquipCategoryQueryPageMapper zxEqToEquipCategoryQueryPageMapper;

    @Override
    public ResponseEntity getZxEqToEquipCategoryQueryPageListByCondition(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToEquipCategoryQueryPage.getPage(),zxEqToEquipCategoryQueryPage.getLimit());
        // 获取数据
        List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList = zxEqToEquipCategoryQueryPageMapper.selectByZxEqToEquipCategoryQueryPageList(zxEqToEquipCategoryQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToEquipCategoryQueryPage> p = new PageInfo<>(zxEqToEquipCategoryQueryPageList);

        return repEntity.okList(zxEqToEquipCategoryQueryPageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqToEquipCategoryQueryPageDetail(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 获取数据
        ZxEqToEquipCategoryQueryPage dbZxEqToEquipCategoryQueryPage = zxEqToEquipCategoryQueryPageMapper.selectByPrimaryKey(zxEqToEquipCategoryQueryPage.getZxEqToEquipCategoryQueryPageId());
        // 数据存在
        if (dbZxEqToEquipCategoryQueryPage != null) {
            return repEntity.ok(dbZxEqToEquipCategoryQueryPage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqToEquipCategoryQueryPage(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqToEquipCategoryQueryPage.setZxEqToEquipCategoryQueryPageId(UuidUtil.generate());
        zxEqToEquipCategoryQueryPage.setCreateUserInfo(userKey, realName);
        int flag = zxEqToEquipCategoryQueryPageMapper.insert(zxEqToEquipCategoryQueryPage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqToEquipCategoryQueryPage);
        }
    }

    @Override
    public ResponseEntity updateZxEqToEquipCategoryQueryPage(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqToEquipCategoryQueryPage dbZxEqToEquipCategoryQueryPage = zxEqToEquipCategoryQueryPageMapper.selectByPrimaryKey(zxEqToEquipCategoryQueryPage.getZxEqToEquipCategoryQueryPageId());
        if (dbZxEqToEquipCategoryQueryPage != null && StrUtil.isNotEmpty(dbZxEqToEquipCategoryQueryPage.getZxEqToEquipCategoryQueryPageId())) {
           // 机构ID
           dbZxEqToEquipCategoryQueryPage.setDepartmentID(zxEqToEquipCategoryQueryPage.getDepartmentID());
           // 机构名称
           dbZxEqToEquipCategoryQueryPage.setDepartmentName(zxEqToEquipCategoryQueryPage.getDepartmentName());
           // 截止时间
           dbZxEqToEquipCategoryQueryPage.setYear(zxEqToEquipCategoryQueryPage.getYear());
           // 土方、准备作业机械
           dbZxEqToEquipCategoryQueryPage.setCount1(zxEqToEquipCategoryQueryPage.getCount1());
           // 土方、准备作业机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue1(zxEqToEquipCategoryQueryPage.getOrginalValue1());
           // 压实机械数量
           dbZxEqToEquipCategoryQueryPage.setCount2(zxEqToEquipCategoryQueryPage.getCount2());
           // 压实机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue2(zxEqToEquipCategoryQueryPage.getOrginalValue2());
           // 压实机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue2(zxEqToEquipCategoryQueryPage.getLeftValue2());
           // 路面机械数量
           dbZxEqToEquipCategoryQueryPage.setCount3(zxEqToEquipCategoryQueryPage.getCount3());
           // 路面机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue3(zxEqToEquipCategoryQueryPage.getOrginalValue3());
           // 路面机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue3(zxEqToEquipCategoryQueryPage.getLeftValue3());
           // 石方开采及加工机械数量
           dbZxEqToEquipCategoryQueryPage.setCount4(zxEqToEquipCategoryQueryPage.getCount4());
           // 石方开采及加工机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue4(zxEqToEquipCategoryQueryPage.getOrginalValue4());
           // 石方开采及加工机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue1(zxEqToEquipCategoryQueryPage.getLeftValue1());
           // 石方开采及加工机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue4(zxEqToEquipCategoryQueryPage.getLeftValue4());
           // 桥涵机械数量
           dbZxEqToEquipCategoryQueryPage.setCount5(zxEqToEquipCategoryQueryPage.getCount5());
           // 桥涵机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue5(zxEqToEquipCategoryQueryPage.getOrginalValue5());
           // 桥涵机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue5(zxEqToEquipCategoryQueryPage.getLeftValue5());
           // 运输机械数量
           dbZxEqToEquipCategoryQueryPage.setCount6(zxEqToEquipCategoryQueryPage.getCount6());
           // 运输机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue6(zxEqToEquipCategoryQueryPage.getOrginalValue6());
           // 运输机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue6(zxEqToEquipCategoryQueryPage.getLeftValue6());
           // 装卸起重机械数量
           dbZxEqToEquipCategoryQueryPage.setCount7(zxEqToEquipCategoryQueryPage.getCount7());
           // 装卸起重机械原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue7(zxEqToEquipCategoryQueryPage.getOrginalValue7());
           // 装卸起重机械净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue7(zxEqToEquipCategoryQueryPage.getLeftValue7());
           // 动力设备数量
           dbZxEqToEquipCategoryQueryPage.setCount8(zxEqToEquipCategoryQueryPage.getCount8());
           // 动力设备原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue8(zxEqToEquipCategoryQueryPage.getOrginalValue8());
           // 动力设备净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue8(zxEqToEquipCategoryQueryPage.getLeftValue8());
           // 维修设备数量
           dbZxEqToEquipCategoryQueryPage.setCount9(zxEqToEquipCategoryQueryPage.getCount9());
           // 维修设备原值
           dbZxEqToEquipCategoryQueryPage.setOrginalValue9(zxEqToEquipCategoryQueryPage.getOrginalValue9());
           // 维修设备净值
           dbZxEqToEquipCategoryQueryPage.setLeftValue9(zxEqToEquipCategoryQueryPage.getLeftValue9());
           // 备注
           dbZxEqToEquipCategoryQueryPage.setRemarks(zxEqToEquipCategoryQueryPage.getRemarks());
           // 排序
           dbZxEqToEquipCategoryQueryPage.setSort(zxEqToEquipCategoryQueryPage.getSort());
           // 共通
           dbZxEqToEquipCategoryQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipCategoryQueryPageMapper.updateByPrimaryKey(dbZxEqToEquipCategoryQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqToEquipCategoryQueryPage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQueryPage(List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqToEquipCategoryQueryPageList != null && zxEqToEquipCategoryQueryPageList.size() > 0) {
           ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
           zxEqToEquipCategoryQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipCategoryQueryPageMapper.batchDeleteUpdateZxEqToEquipCategoryQueryPage(zxEqToEquipCategoryQueryPageList, zxEqToEquipCategoryQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqToEquipCategoryQueryPageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 获取数据
        List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList = zxEqToEquipCategoryQueryPageMapper.selectZxEqToEquipCategoryQueryPage(zxEqToEquipCategoryQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQueryPageList, zxEqToEquipCategoryQueryPageList.size());
    }
    //根据数量图表
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageCountIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 获取数据
        List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList = zxEqToEquipCategoryQueryPageMapper.selectZxEqToEquipCategoryQueryPageCount(zxEqToEquipCategoryQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQueryPageList, zxEqToEquipCategoryQueryPageList.size());
    }
    //根据原值比图表
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageOrginalValueIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 获取数据
        List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList = zxEqToEquipCategoryQueryPageMapper.selectZxEqToEquipCategoryQueryPageOrginalValue(zxEqToEquipCategoryQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQueryPageList, zxEqToEquipCategoryQueryPageList.size());
    }
    //根据净值比图表
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQueryPageleftValueIdle(ZxEqToEquipCategoryQueryPage zxEqToEquipCategoryQueryPage) {
        if (zxEqToEquipCategoryQueryPage == null) {
            zxEqToEquipCategoryQueryPage = new ZxEqToEquipCategoryQueryPage();
        }
        // 获取数据
        List<ZxEqToEquipCategoryQueryPage> zxEqToEquipCategoryQueryPageList = zxEqToEquipCategoryQueryPageMapper.selectZxEqToEquipCategoryQueryPageleftValue(zxEqToEquipCategoryQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQueryPageList, zxEqToEquipCategoryQueryPageList.size());
    }
}
