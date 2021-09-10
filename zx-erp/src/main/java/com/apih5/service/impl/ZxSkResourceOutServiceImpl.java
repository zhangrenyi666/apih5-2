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
import com.apih5.mybatis.dao.ZxSkResourceOutMapper;
import com.apih5.mybatis.pojo.ZxSkResourceOut;
import com.apih5.service.ZxSkResourceOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResourceOutService")
public class ZxSkResourceOutServiceImpl implements ZxSkResourceOutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResourceOutMapper zxSkResourceOutMapper;

    @Override
    public ResponseEntity getZxSkResourceOutListByCondition(ZxSkResourceOut zxSkResourceOut) {
        if (zxSkResourceOut == null) {
            zxSkResourceOut = new ZxSkResourceOut();
        }
        // 分页查询
        PageHelper.startPage(zxSkResourceOut.getPage(),zxSkResourceOut.getLimit());
        // 获取数据
        List<ZxSkResourceOut> zxSkResourceOutList = zxSkResourceOutMapper.selectByZxSkResourceOutList(zxSkResourceOut);
        // 得到分页信息
        PageInfo<ZxSkResourceOut> p = new PageInfo<>(zxSkResourceOutList);

        return repEntity.okList(zxSkResourceOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResourceOutDetail(ZxSkResourceOut zxSkResourceOut) {
        if (zxSkResourceOut == null) {
            zxSkResourceOut = new ZxSkResourceOut();
        }
        // 获取数据
        ZxSkResourceOut dbZxSkResourceOut = zxSkResourceOutMapper.selectByPrimaryKey(zxSkResourceOut.getZxSkResourceOutId());
        // 数据存在
        if (dbZxSkResourceOut != null) {
            return repEntity.ok(dbZxSkResourceOut);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResourceOut(ZxSkResourceOut zxSkResourceOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResourceOut.setZxSkResourceOutId(UuidUtil.generate());
        zxSkResourceOut.setCreateUserInfo(userKey, realName);
        int flag = zxSkResourceOutMapper.insert(zxSkResourceOut);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkResourceOut);
        }
    }

    @Override
    public ResponseEntity updateZxSkResourceOut(ZxSkResourceOut zxSkResourceOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResourceOut dbZxSkResourceOut = zxSkResourceOutMapper.selectByPrimaryKey(zxSkResourceOut.getZxSkResourceOutId());
        if (dbZxSkResourceOut != null && StrUtil.isNotEmpty(dbZxSkResourceOut.getZxSkResourceOutId())) {
           // 出库类型
           dbZxSkResourceOut.setOutType(zxSkResourceOut.getOutType());
           // 出库日期
           dbZxSkResourceOut.setBusDate(zxSkResourceOut.getBusDate());
           // 领料/调拨单位
           dbZxSkResourceOut.setInOrgName(zxSkResourceOut.getInOrgName());
           // 物资类别
           dbZxSkResourceOut.setResourceName(zxSkResourceOut.getResourceName());
           // 物资编号
           dbZxSkResourceOut.setResCode(zxSkResourceOut.getResCode());
           // 物资名称
           dbZxSkResourceOut.setResName(zxSkResourceOut.getResName());
           // 规格
           dbZxSkResourceOut.setSpec(zxSkResourceOut.getSpec());
           // 单位
           dbZxSkResourceOut.setResUnit(zxSkResourceOut.getResUnit());
           // 数量
           dbZxSkResourceOut.setOutQty(zxSkResourceOut.getOutQty());
           // 出库单价
           dbZxSkResourceOut.setStdPrice(zxSkResourceOut.getStdPrice());
           // 金额
           dbZxSkResourceOut.setOutAmt(zxSkResourceOut.getOutAmt());
           // 备注
           dbZxSkResourceOut.setRemarks(zxSkResourceOut.getRemarks());
           // 排序
           dbZxSkResourceOut.setSort(zxSkResourceOut.getSort());
           // 共通
           dbZxSkResourceOut.setModifyUserInfo(userKey, realName);
           flag = zxSkResourceOutMapper.updateByPrimaryKey(dbZxSkResourceOut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkResourceOut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResourceOut(List<ZxSkResourceOut> zxSkResourceOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResourceOutList != null && zxSkResourceOutList.size() > 0) {
           ZxSkResourceOut zxSkResourceOut = new ZxSkResourceOut();
           zxSkResourceOut.setModifyUserInfo(userKey, realName);
           flag = zxSkResourceOutMapper.batchDeleteUpdateZxSkResourceOut(zxSkResourceOutList, zxSkResourceOut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkResourceOutList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkResourceOut> ureportZxSkResourceOut(ZxSkResourceOut zxSkResourceOut) {
    	List<ZxSkResourceOut> zxSkResourceOutList = zxSkResourceOutMapper.selectZxSkResourceOut(zxSkResourceOut);
    	return zxSkResourceOutList;
    }
    
    @Override
    public ResponseEntity ureportZxSkResourceOutIdle(ZxSkResourceOut zxSkResourceOut) {
        if (zxSkResourceOut == null) {
            zxSkResourceOut = new ZxSkResourceOut();
        }
        // 分页查询
        PageHelper.startPage(zxSkResourceOut.getPage(),zxSkResourceOut.getLimit());
        // 获取数据
        List<ZxSkResourceOut> zxSkResourceOutList = zxSkResourceOutMapper.selectZxSkResourceOut(zxSkResourceOut);
        // 得到分页信息
        PageInfo<ZxSkResourceOut> p = new PageInfo<>(zxSkResourceOutList);

        return repEntity.okList(zxSkResourceOutList, p.getTotal());
    }
}
