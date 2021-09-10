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
import com.apih5.mybatis.dao.ZxSkWornOutListMapper;
import com.apih5.mybatis.pojo.ZxSkWornOutList;
import com.apih5.service.ZxSkWornOutListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkWornOutListService")
public class ZxSkWornOutListServiceImpl implements ZxSkWornOutListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkWornOutListMapper zxSkWornOutListMapper;

    @Override
    public ResponseEntity getZxSkWornOutListListByCondition(ZxSkWornOutList zxSkWornOutList) {
        if (zxSkWornOutList == null) {
            zxSkWornOutList = new ZxSkWornOutList();
        }
        // 分页查询
        PageHelper.startPage(zxSkWornOutList.getPage(),zxSkWornOutList.getLimit());
        // 获取数据
        List<ZxSkWornOutList> zxSkWornOutListList = zxSkWornOutListMapper.selectByZxSkWornOutListList(zxSkWornOutList);
        // 得到分页信息
        PageInfo<ZxSkWornOutList> p = new PageInfo<>(zxSkWornOutListList);

        return repEntity.okList(zxSkWornOutListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkWornOutListDetail(ZxSkWornOutList zxSkWornOutList) {
        if (zxSkWornOutList == null) {
            zxSkWornOutList = new ZxSkWornOutList();
        }
        // 获取数据
        ZxSkWornOutList dbZxSkWornOutList = zxSkWornOutListMapper.selectByPrimaryKey(zxSkWornOutList.getZxSkWornOutListId());
        // 数据存在
        if (dbZxSkWornOutList != null) {
            return repEntity.ok(dbZxSkWornOutList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkWornOutList(ZxSkWornOutList zxSkWornOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkWornOutList.setZxSkWornOutListId(UuidUtil.generate());
        zxSkWornOutList.setCreateUserInfo(userKey, realName);
        int flag = zxSkWornOutListMapper.insert(zxSkWornOutList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkWornOutList);
        }
    }

    @Override
    public ResponseEntity updateZxSkWornOutList(ZxSkWornOutList zxSkWornOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkWornOutList dbZxSkWornOutList = zxSkWornOutListMapper.selectByPrimaryKey(zxSkWornOutList.getZxSkWornOutListId());
        if (dbZxSkWornOutList != null && StrUtil.isNotEmpty(dbZxSkWornOutList.getZxSkWornOutListId())) {
           // 项目名称
           dbZxSkWornOutList.setOrgName(zxSkWornOutList.getOrgName());
           // 单据编号
           dbZxSkWornOutList.setBillNo(zxSkWornOutList.getBillNo());
           // 物资名称
           dbZxSkWornOutList.setResName(zxSkWornOutList.getResName());
           // 单位
           dbZxSkWornOutList.setUnit(zxSkWornOutList.getUnit());
           // 数量
           dbZxSkWornOutList.setStockQty(zxSkWornOutList.getStockQty());
           // 拟处理金额（万元）
           dbZxSkWornOutList.setOriginalAmt(zxSkWornOutList.getOriginalAmt());
           // 上报审批日期
           dbZxSkWornOutList.setReportDate(zxSkWornOutList.getReportDate());
           // 评审通过日期
           dbZxSkWornOutList.setModifyTime(zxSkWornOutList.getModifyTime());
           // 备注
           dbZxSkWornOutList.setRemarks(zxSkWornOutList.getRemarks());
           // 排序
           dbZxSkWornOutList.setSort(zxSkWornOutList.getSort());
           // 共通
           dbZxSkWornOutList.setModifyUserInfo(userKey, realName);
           flag = zxSkWornOutListMapper.updateByPrimaryKey(dbZxSkWornOutList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkWornOutList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkWornOutList(List<ZxSkWornOutList> zxSkWornOutListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkWornOutListList != null && zxSkWornOutListList.size() > 0) {
           ZxSkWornOutList zxSkWornOutList = new ZxSkWornOutList();
           zxSkWornOutList.setModifyUserInfo(userKey, realName);
           flag = zxSkWornOutListMapper.batchDeleteUpdateZxSkWornOutList(zxSkWornOutListList, zxSkWornOutList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkWornOutListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkWornOutList> ureportZxSkWornOutList(ZxSkWornOutList zxSkWornOutList) {
    	if (zxSkWornOutList == null) {
            zxSkWornOutList = new ZxSkWornOutList();
        }
    	List<ZxSkWornOutList> zxSkWornOutListList = zxSkWornOutListMapper.selectZxSkWornOutList(zxSkWornOutList);
    	return zxSkWornOutListList;
    }
    
    @Override
    public ResponseEntity ureportZxSkWornOutListIdle(ZxSkWornOutList zxSkWornOutList) {
        if (zxSkWornOutList == null) {
            zxSkWornOutList = new ZxSkWornOutList();
        }
        // 分页查询
        PageHelper.startPage(zxSkWornOutList.getPage(),zxSkWornOutList.getLimit());
        // 获取数据
        List<ZxSkWornOutList> zxSkWornOutListList = zxSkWornOutListMapper.selectZxSkWornOutList(zxSkWornOutList);
        // 得到分页信息
        PageInfo<ZxSkWornOutList> p = new PageInfo<>(zxSkWornOutListList);

        return repEntity.okList(zxSkWornOutListList, p.getTotal());
    }
}
