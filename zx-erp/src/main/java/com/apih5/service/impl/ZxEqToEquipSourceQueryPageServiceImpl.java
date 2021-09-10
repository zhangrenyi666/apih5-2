package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxEqToEquipSourceQueryPageMapper;
import com.apih5.mybatis.pojo.ZxEqToEquipSourceQueryPage;
import com.apih5.service.ZxEqToEquipSourceQueryPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxEqToEquipSourceQueryPageService")
public class ZxEqToEquipSourceQueryPageServiceImpl implements ZxEqToEquipSourceQueryPageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqToEquipSourceQueryPageMapper zxEqToEquipSourceQueryPageMapper;

    @Override
    public ResponseEntity getZxEqToEquipSourceQueryPageListByCondition(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        if (zxEqToEquipSourceQueryPage == null) {
            zxEqToEquipSourceQueryPage = new ZxEqToEquipSourceQueryPage();
        }
        // 分页查询
        PageHelper.startPage(zxEqToEquipSourceQueryPage.getPage(),zxEqToEquipSourceQueryPage.getLimit());
        // 获取数据
        List<ZxEqToEquipSourceQueryPage> zxEqToEquipSourceQueryPageList = zxEqToEquipSourceQueryPageMapper.selectByZxEqToEquipSourceQueryPageList(zxEqToEquipSourceQueryPage);
        // 得到分页信息
        PageInfo<ZxEqToEquipSourceQueryPage> p = new PageInfo<>(zxEqToEquipSourceQueryPageList);

        return repEntity.okList(zxEqToEquipSourceQueryPageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqToEquipSourceQueryPageDetail(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        if (zxEqToEquipSourceQueryPage == null) {
            zxEqToEquipSourceQueryPage = new ZxEqToEquipSourceQueryPage();
        }
        // 获取数据
        ZxEqToEquipSourceQueryPage dbZxEqToEquipSourceQueryPage = zxEqToEquipSourceQueryPageMapper.selectByPrimaryKey(zxEqToEquipSourceQueryPage.getZxEqToEquipSourceQueryPageId());
        // 数据存在
        if (dbZxEqToEquipSourceQueryPage != null) {
            return repEntity.ok(dbZxEqToEquipSourceQueryPage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqToEquipSourceQueryPage(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqToEquipSourceQueryPage.setZxEqToEquipSourceQueryPageId(UuidUtil.generate());
        zxEqToEquipSourceQueryPage.setCreateUserInfo(userKey, realName);
        int flag = zxEqToEquipSourceQueryPageMapper.insert(zxEqToEquipSourceQueryPage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqToEquipSourceQueryPage);
        }
    }

    @Override
    public ResponseEntity updateZxEqToEquipSourceQueryPage(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqToEquipSourceQueryPage dbZxEqToEquipSourceQueryPage = zxEqToEquipSourceQueryPageMapper.selectByPrimaryKey(zxEqToEquipSourceQueryPage.getZxEqToEquipSourceQueryPageId());
        if (dbZxEqToEquipSourceQueryPage != null && StrUtil.isNotEmpty(dbZxEqToEquipSourceQueryPage.getZxEqToEquipSourceQueryPageId())) {
           // 机构名称
           dbZxEqToEquipSourceQueryPage.setDepartmentName(zxEqToEquipSourceQueryPage.getDepartmentName());
           // 租凭数量
           dbZxEqToEquipSourceQueryPage.setCount1(zxEqToEquipSourceQueryPage.getCount1());
           // 租凭金额
           dbZxEqToEquipSourceQueryPage.setAmt1(zxEqToEquipSourceQueryPage.getAmt1());
           // 协作队伍自带数量
           dbZxEqToEquipSourceQueryPage.setCount2(zxEqToEquipSourceQueryPage.getCount2());
           // 协作队伍自带金额
           dbZxEqToEquipSourceQueryPage.setAmt2(zxEqToEquipSourceQueryPage.getAmt2());
           // 自有设备数量
           dbZxEqToEquipSourceQueryPage.setCount3(zxEqToEquipSourceQueryPage.getCount3());
           // 自有设备金额
           dbZxEqToEquipSourceQueryPage.setAmt3(zxEqToEquipSourceQueryPage.getAmt3());
           // 总计数量
           dbZxEqToEquipSourceQueryPage.setCount4(zxEqToEquipSourceQueryPage.getCount4());
           // 总计金额
           dbZxEqToEquipSourceQueryPage.setAmt4(zxEqToEquipSourceQueryPage.getAmt4());
           // 设备分类
           dbZxEqToEquipSourceQueryPage.setResCatalogID(zxEqToEquipSourceQueryPage.getResCatalogID());
           // 开始时间
           dbZxEqToEquipSourceQueryPage.setBeginDate(zxEqToEquipSourceQueryPage.getBeginDate());
           // 结束时间
           dbZxEqToEquipSourceQueryPage.setEndDate(zxEqToEquipSourceQueryPage.getEndDate());
           // 备注
           dbZxEqToEquipSourceQueryPage.setRemarks(zxEqToEquipSourceQueryPage.getRemarks());
           // 排序
           dbZxEqToEquipSourceQueryPage.setSort(zxEqToEquipSourceQueryPage.getSort());
           // 共通
           dbZxEqToEquipSourceQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipSourceQueryPageMapper.updateByPrimaryKey(dbZxEqToEquipSourceQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqToEquipSourceQueryPage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqToEquipSourceQueryPage(List<ZxEqToEquipSourceQueryPage> zxEqToEquipSourceQueryPageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqToEquipSourceQueryPageList != null && zxEqToEquipSourceQueryPageList.size() > 0) {
           ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage = new ZxEqToEquipSourceQueryPage();
           zxEqToEquipSourceQueryPage.setModifyUserInfo(userKey, realName);
           flag = zxEqToEquipSourceQueryPageMapper.batchDeleteUpdateZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPageList, zxEqToEquipSourceQueryPage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqToEquipSourceQueryPageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity ureportZxEqToEquipSourceQueryPageIdle(ZxEqToEquipSourceQueryPage zxEqToEquipSourceQueryPage) {
    	 // 分页查询
        PageHelper.startPage(zxEqToEquipSourceQueryPage.getPage(),zxEqToEquipSourceQueryPage.getLimit());
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqToEquipSourceQueryPage.setCompanyId("");
        	zxEqToEquipSourceQueryPage.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqToEquipSourceQueryPage.setCompanyId(zxEqToEquipSourceQueryPage.getOrgID());
        	zxEqToEquipSourceQueryPage.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqToEquipSourceQueryPage.setOrgID(zxEqToEquipSourceQueryPage.getOrgID());
        }
        //公司
        if(zxEqToEquipSourceQueryPage.getCompanyId()!=null) {
        	zxEqToEquipSourceQueryPage.setParentID(zxEqToEquipSourceQueryPage.getCompanyId());
        }
        //局
        if(zxEqToEquipSourceQueryPage.getCompanyId().equals("")) {
        	if(zxEqToEquipSourceQueryPage.getParentID().equals("")) {
        	zxEqToEquipSourceQueryPage.setParentID("9999999999");
            }
        }
        // 获取数据
        List<ZxEqToEquipSourceQueryPage> zxEqToEquipSourceQueryPageList = zxEqToEquipSourceQueryPageMapper.selectZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPage);
        for(ZxEqToEquipSourceQueryPage ZxEqToEquipSourceQueryPage :zxEqToEquipSourceQueryPageList) {
        	if(ZxEqToEquipSourceQueryPage.getParentID()!="") {
        		zxEqToEquipSourceQueryPage.setParentID(zxEqToEquipSourceQueryPage.getZxEqToEquipSourceQueryPageId());
        		 zxEqToEquipSourceQueryPageList = zxEqToEquipSourceQueryPageMapper.selectZxEqToEquipSourceQueryPage(zxEqToEquipSourceQueryPage);
        	}
        }
        // 得到分页信息
        PageInfo<ZxEqToEquipSourceQueryPage> p = new PageInfo<>(zxEqToEquipSourceQueryPageList);

        return repEntity.okList(zxEqToEquipSourceQueryPageList, p.getTotal());
    }

}
