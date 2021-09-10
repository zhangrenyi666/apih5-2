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
import com.apih5.mybatis.dao.ZxEqToEquipCategoryQuantityQueryPageMapper;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQuantityQueryPage;
import com.apih5.service.ZxEqToEquipCategoryQuantityQueryPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqToEquipCategoryQuantityQueryPageService")
public class ZxEqToEquipCategoryQuantityQueryPageServiceImpl implements ZxEqToEquipCategoryQuantityQueryPageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqToEquipCategoryQuantityQueryPageMapper zxEqToEquipCategoryQuantityQueryPageMapper;

    @Override
    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageListByCondition(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToEquipCategoryQuantityQueryPage.getPage(),zxEqToEquipCategoryQuantityQueryPage.getLimit());
        // 获取数据
        List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList = zxEqToEquipCategoryQuantityQueryPageMapper.selectByZxEqToEquipCategoryQuantityQueryPageList(zxEqToEquipCategoryQuantityQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToEquipCategoryQuantityQueryPage> p = new PageInfo<>(zxEqToEquipCategoryQuantityQueryPageList);

        return repEntity.okList(zxEqToEquipCategoryQuantityQueryPageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageDetail(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
        // 获取数据
        ZxEqToEquipCategoryQuantityQueryPage dbZxEqToEquipCategoryQuantityQueryPage = zxEqToEquipCategoryQuantityQueryPageMapper.selectByPrimaryKey(zxEqToEquipCategoryQuantityQueryPage.getZxEqToEquipCategoryQuantityQueryPageId());
        // 数据存在
        if (dbZxEqToEquipCategoryQuantityQueryPage != null) {
            return repEntity.ok(dbZxEqToEquipCategoryQuantityQueryPage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqToEquipCategoryQuantityQueryPage(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqToEquipCategoryQuantityQueryPage.setZxEqToEquipCategoryQuantityQueryPageId(UuidUtil.generate());
        zxEqToEquipCategoryQuantityQueryPage.setCreateUserInfo(userKey, realName);
        int flag = zxEqToEquipCategoryQuantityQueryPageMapper.insert(zxEqToEquipCategoryQuantityQueryPage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqToEquipCategoryQuantityQueryPage);
        }
    }

    @Override
    public ResponseEntity updateZxEqToEquipCategoryQuantityQueryPage(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqToEquipCategoryQuantityQueryPage dbZxEqToEquipCategoryQuantityQueryPage = zxEqToEquipCategoryQuantityQueryPageMapper.selectByPrimaryKey(zxEqToEquipCategoryQuantityQueryPage.getZxEqToEquipCategoryQuantityQueryPageId());
        if (dbZxEqToEquipCategoryQuantityQueryPage != null && StrUtil.isNotEmpty(dbZxEqToEquipCategoryQuantityQueryPage.getZxEqToEquipCategoryQuantityQueryPageId())) {
           // 机构
           dbZxEqToEquipCategoryQuantityQueryPage.setDepartmentName(zxEqToEquipCategoryQuantityQueryPage.getDepartmentName());
           // 取得日期
           dbZxEqToEquipCategoryQuantityQueryPage.setQueryDate(zxEqToEquipCategoryQuantityQueryPage.getQueryDate());
           // 土方、准备作业机械自由
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum1(zxEqToEquipCategoryQuantityQueryPage.getSelfNum1());
           // 土方、准备作业机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum1(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum1());
           // 土方、准备作业机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum());
           // 压实机械自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum2(zxEqToEquipCategoryQuantityQueryPage.getSelfNum2());
           // 压实机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum2(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum2());
           // 压实机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum2(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum2());
           // 路面机械自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum3(zxEqToEquipCategoryQuantityQueryPage.getSelfNum3());
           // 路面机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum3(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum3());
           // 路面机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum3(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum3());
           // 石方开采及加工机械
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum4(zxEqToEquipCategoryQuantityQueryPage.getSelfNum4());
           // 石方开采及加工机械
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum4(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum4());
           // 石方开采及加工机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum4(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum4());
           // 桥涵机械自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum5(zxEqToEquipCategoryQuantityQueryPage.getSelfNum5());
           // 桥涵机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum5(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum5());
           // 桥涵机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum5(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum5());
           // 运输机械自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum6(zxEqToEquipCategoryQuantityQueryPage.getSelfNum6());
           // 运输机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum6(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum6());
           // 运输机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum6(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum6());
           // 装卸起重机械自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum7(zxEqToEquipCategoryQuantityQueryPage.getSelfNum7());
           // 装卸起重机械外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum7(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum7());
           // 装卸起重机械协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum7(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum7());
           // 动力设备自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum8(zxEqToEquipCategoryQuantityQueryPage.getSelfNum8());
           // 动力设备外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum8(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum8());
           // 动力设备协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum8(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum8());
           // 维修设备自有
           dbZxEqToEquipCategoryQuantityQueryPage.setSelfNum9(zxEqToEquipCategoryQuantityQueryPage.getSelfNum9());
           // 维修设备外租
           dbZxEqToEquipCategoryQuantityQueryPage.setLeaseNum9(zxEqToEquipCategoryQuantityQueryPage.getLeaseNum9());
           // 维修设备协作单位自带
           dbZxEqToEquipCategoryQuantityQueryPage.setOutBringNum9(zxEqToEquipCategoryQuantityQueryPage.getOutBringNum9());
           // 备注
           dbZxEqToEquipCategoryQuantityQueryPage.setRemarks(zxEqToEquipCategoryQuantityQueryPage.getRemarks());
           // 排序
           dbZxEqToEquipCategoryQuantityQueryPage.setSort(zxEqToEquipCategoryQuantityQueryPage.getSort());
           // 共通
           dbZxEqToEquipCategoryQuantityQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipCategoryQuantityQueryPageMapper.updateByPrimaryKey(dbZxEqToEquipCategoryQuantityQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqToEquipCategoryQuantityQueryPage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqToEquipCategoryQuantityQueryPageList != null && zxEqToEquipCategoryQuantityQueryPageList.size() > 0) {
           ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
           zxEqToEquipCategoryQuantityQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipCategoryQuantityQueryPageMapper.batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(zxEqToEquipCategoryQuantityQueryPageList, zxEqToEquipCategoryQuantityQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqToEquipCategoryQuantityQueryPageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
    	if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
    	 // 获取数据
        List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList = zxEqToEquipCategoryQuantityQueryPageMapper.selectZxEqToEquipCategoryQuantityQueryPage(zxEqToEquipCategoryQuantityQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQuantityQueryPageList, zxEqToEquipCategoryQuantityQueryPageList.size());
    }
    //自有设备
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageChartIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
    	if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
    	 // 获取数据
        List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList = zxEqToEquipCategoryQuantityQueryPageMapper.selectZxEqToEquipCategoryQuantityQueryPageChart(zxEqToEquipCategoryQuantityQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQuantityQueryPageList, zxEqToEquipCategoryQuantityQueryPageList.size());
    }
    //外租设备
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageRentOutIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
    	if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
    	 // 获取数据
        List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList = zxEqToEquipCategoryQuantityQueryPageMapper.selectZxEqToEquipCategoryQuantityQueryPageRentOut(zxEqToEquipCategoryQuantityQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQuantityQueryPageList, zxEqToEquipCategoryQuantityQueryPageList.size());
    }
    
   //协作单位自带设备
    @Override
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageCooperationUnitIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage) {
    	if (zxEqToEquipCategoryQuantityQueryPage == null) {
            zxEqToEquipCategoryQuantityQueryPage = new ZxEqToEquipCategoryQuantityQueryPage();
        }
    	 // 获取数据
        List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList = zxEqToEquipCategoryQuantityQueryPageMapper.selectZxEqToEquipCategoryQuantityQueryPageCooperationUnit(zxEqToEquipCategoryQuantityQueryPage);

        return repEntity.okList(zxEqToEquipCategoryQuantityQueryPageList, zxEqToEquipCategoryQuantityQueryPageList.size());
    }
}
