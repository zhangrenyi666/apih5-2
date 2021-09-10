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
import com.apih5.mybatis.dao.ZxSkResMoveMonthSMPMapper;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthSMP;
import com.apih5.service.ZxSkResMoveMonthSMPService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResMoveMonthSMPService")
public class ZxSkResMoveMonthSMPServiceImpl implements ZxSkResMoveMonthSMPService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResMoveMonthSMPMapper zxSkResMoveMonthSMPMapper;

    @Override
    public ResponseEntity getZxSkResMoveMonthSMPListByCondition(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        if (zxSkResMoveMonthSMP == null) {
            zxSkResMoveMonthSMP = new ZxSkResMoveMonthSMP();
        }
        // 分页查询
        PageHelper.startPage(zxSkResMoveMonthSMP.getPage(),zxSkResMoveMonthSMP.getLimit());
        // 获取数据
        List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList = zxSkResMoveMonthSMPMapper.selectByZxSkResMoveMonthSMPList(zxSkResMoveMonthSMP);
        // 得到分页信息
        PageInfo<ZxSkResMoveMonthSMP> p = new PageInfo<>(zxSkResMoveMonthSMPList);

        return repEntity.okList(zxSkResMoveMonthSMPList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResMoveMonthSMPDetail(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        if (zxSkResMoveMonthSMP == null) {
            zxSkResMoveMonthSMP = new ZxSkResMoveMonthSMP();
        }
        // 获取数据
        ZxSkResMoveMonthSMP dbZxSkResMoveMonthSMP = zxSkResMoveMonthSMPMapper.selectByPrimaryKey(zxSkResMoveMonthSMP.getZxSkResMoveMonthSMPId());
        // 数据存在
        if (dbZxSkResMoveMonthSMP != null) {
            return repEntity.ok(dbZxSkResMoveMonthSMP);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResMoveMonthSMP.setZxSkResMoveMonthSMPId(UuidUtil.generate());
        zxSkResMoveMonthSMP.setCreateUserInfo(userKey, realName);
        int flag = zxSkResMoveMonthSMPMapper.insert(zxSkResMoveMonthSMP);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkResMoveMonthSMP);
        }
    }

    @Override
    public ResponseEntity updateZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResMoveMonthSMP dbZxSkResMoveMonthSMP = zxSkResMoveMonthSMPMapper.selectByPrimaryKey(zxSkResMoveMonthSMP.getZxSkResMoveMonthSMPId());
        if (dbZxSkResMoveMonthSMP != null && StrUtil.isNotEmpty(dbZxSkResMoveMonthSMP.getZxSkResMoveMonthSMPId())) {
           // 物资编号
           dbZxSkResMoveMonthSMP.setResCode(zxSkResMoveMonthSMP.getResCode());
           // 物资名称
           dbZxSkResMoveMonthSMP.setResName(zxSkResMoveMonthSMP.getResName());
           // 上月结存数量
           dbZxSkResMoveMonthSMP.setSpce(zxSkResMoveMonthSMP.getSpce());
           // 上月结存数量
           dbZxSkResMoveMonthSMP.setStockQty(zxSkResMoveMonthSMP.getStockQty());
           // 上月结存平均单价（元）
           dbZxSkResMoveMonthSMP.setResUnit(zxSkResMoveMonthSMP.getResUnit());
           // 上月结存平均单价（元）
           dbZxSkResMoveMonthSMP.setStockPrice(zxSkResMoveMonthSMP.getStockPrice());
           // 上月结存金额（元）
           dbZxSkResMoveMonthSMP.setStockAmt(zxSkResMoveMonthSMP.getStockAmt());
           // 甲供
           dbZxSkResMoveMonthSMP.setOrsQty(zxSkResMoveMonthSMP.getOrsQty());
           // 其他
           dbZxSkResMoveMonthSMP.setOtrQty(zxSkResMoveMonthSMP.getOtrQty());
           // 自购
           dbZxSkResMoveMonthSMP.setSerQty(zxSkResMoveMonthSMP.getSerQty());
           // 预收
           dbZxSkResMoveMonthSMP.setObuQty(zxSkResMoveMonthSMP.getObuQty());
           // 甲控
           dbZxSkResMoveMonthSMP.setOcsQty(zxSkResMoveMonthSMP.getOcsQty());
           // 本月收入合计数量
           dbZxSkResMoveMonthSMP.setInQty(zxSkResMoveMonthSMP.getInQty());
           // 本月收入合计金额
           dbZxSkResMoveMonthSMP.setInAmt(zxSkResMoveMonthSMP.getInAmt());
           // 本月收入合计平均单价（元）
           dbZxSkResMoveMonthSMP.setInPrice(zxSkResMoveMonthSMP.getInPrice());
           // 工程耗用数量
           dbZxSkResMoveMonthSMP.setOswQty(zxSkResMoveMonthSMP.getOswQty());
           // 工程耗用平均单价（元）
           dbZxSkResMoveMonthSMP.setOswPrice(zxSkResMoveMonthSMP.getOswPrice());
           // 调拨
           dbZxSkResMoveMonthSMP.setOtkQty(zxSkResMoveMonthSMP.getOtkQty());
           // 调拨金额
           dbZxSkResMoveMonthSMP.setOswAmt(zxSkResMoveMonthSMP.getOswAmt());
           // 调拨金额
           dbZxSkResMoveMonthSMP.setOtkAmt(zxSkResMoveMonthSMP.getOtkAmt());
           // 调拨平均单价（元）
           dbZxSkResMoveMonthSMP.setOtkPrice(zxSkResMoveMonthSMP.getOtkPrice());
           // 盈亏数量
           dbZxSkResMoveMonthSMP.setVinQty(zxSkResMoveMonthSMP.getVinQty());
           // 盈亏金额
           dbZxSkResMoveMonthSMP.setVinAmt(zxSkResMoveMonthSMP.getVinAmt());
           // 本月结存数量
           dbZxSkResMoveMonthSMP.setThisQty(zxSkResMoveMonthSMP.getThisQty());
           // 本月结存金额
           dbZxSkResMoveMonthSMP.setThisAmt(zxSkResMoveMonthSMP.getThisAmt());
           // 本月结存平均单价（元）
           dbZxSkResMoveMonthSMP.setThisPrice(zxSkResMoveMonthSMP.getThisPrice());
           // 备注
           dbZxSkResMoveMonthSMP.setRemarks(zxSkResMoveMonthSMP.getRemarks());
           // 排序
           dbZxSkResMoveMonthSMP.setSort(zxSkResMoveMonthSMP.getSort());
           // 共通
           dbZxSkResMoveMonthSMP.setModifyUserInfo(userKey, realName);
           flag = zxSkResMoveMonthSMPMapper.updateByPrimaryKey(dbZxSkResMoveMonthSMP);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkResMoveMonthSMP);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthSMP(List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResMoveMonthSMPList != null && zxSkResMoveMonthSMPList.size() > 0) {
           ZxSkResMoveMonthSMP zxSkResMoveMonthSMP = new ZxSkResMoveMonthSMP();
           zxSkResMoveMonthSMP.setModifyUserInfo(userKey, realName);
           flag = zxSkResMoveMonthSMPMapper.batchDeleteUpdateZxSkResMoveMonthSMP(zxSkResMoveMonthSMPList, zxSkResMoveMonthSMP);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkResMoveMonthSMPList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxSkResMoveMonthSMP> ureportZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
    	if(zxSkResMoveMonthSMP.getResID().equals("null")) {
    		zxSkResMoveMonthSMP.setResID(null);
    	}
    	if(zxSkResMoveMonthSMP.getIsFinish().equals("null")) {
    		zxSkResMoveMonthSMP.setIsFinish("");
    	}
    	List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList = zxSkResMoveMonthSMPMapper.selectZxSkResMoveMonthSMP(zxSkResMoveMonthSMP);
    	return zxSkResMoveMonthSMPList;
    }
    
    @Override
    public ResponseEntity ureportZxSkResMoveMonthSMPIdle(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        // 分页查询
        PageHelper.startPage(zxSkResMoveMonthSMP.getPage(),zxSkResMoveMonthSMP.getLimit());
    	List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList = zxSkResMoveMonthSMPMapper.selectZxSkResMoveMonthSMP(zxSkResMoveMonthSMP);
        // 得到分页信息
        PageInfo<ZxSkResMoveMonthSMP> p = new PageInfo<>(zxSkResMoveMonthSMPList);
        return repEntity.okList(zxSkResMoveMonthSMPList, p.getTotal());
    }




}
