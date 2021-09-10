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
import com.apih5.mybatis.dao.ZxSkStockTransferRptMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferRpt;
import com.apih5.service.ZxSkStockTransferRptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferRptService")
public class ZxSkStockTransferRptServiceImpl implements ZxSkStockTransferRptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferRptMapper zxSkStockTransferRptMapper;

    @Override
    public ResponseEntity getZxSkStockTransferRptListByCondition(ZxSkStockTransferRpt zxSkStockTransferRpt) {
        if (zxSkStockTransferRpt == null) {
            zxSkStockTransferRpt = new ZxSkStockTransferRpt();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferRpt.getPage(),zxSkStockTransferRpt.getLimit());
        // 获取数据
        List<ZxSkStockTransferRpt> zxSkStockTransferRptList = zxSkStockTransferRptMapper.selectByZxSkStockTransferRptList(zxSkStockTransferRpt);
        // 得到分页信息
        PageInfo<ZxSkStockTransferRpt> p = new PageInfo<>(zxSkStockTransferRptList);

        return repEntity.okList(zxSkStockTransferRptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferRptDetail(ZxSkStockTransferRpt zxSkStockTransferRpt) {
        if (zxSkStockTransferRpt == null) {
            zxSkStockTransferRpt = new ZxSkStockTransferRpt();
        }
        // 获取数据
        ZxSkStockTransferRpt dbZxSkStockTransferRpt = zxSkStockTransferRptMapper.selectByPrimaryKey(zxSkStockTransferRpt.getZxSkStockTransferRptId());
        // 数据存在
        if (dbZxSkStockTransferRpt != null) {
            return repEntity.ok(dbZxSkStockTransferRpt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferRpt.setZxSkStockTransferRptId(UuidUtil.generate());
        zxSkStockTransferRpt.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransferRptMapper.insert(zxSkStockTransferRpt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferRpt);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferRpt dbZxSkStockTransferRpt = zxSkStockTransferRptMapper.selectByPrimaryKey(zxSkStockTransferRpt.getZxSkStockTransferRptId());
        if (dbZxSkStockTransferRpt != null && StrUtil.isNotEmpty(dbZxSkStockTransferRpt.getZxSkStockTransferRptId())) {
           // 序号
           dbZxSkStockTransferRpt.setNum(zxSkStockTransferRpt.getNum());
           // 物资名称
           dbZxSkStockTransferRpt.setResName(zxSkStockTransferRpt.getResName());
           // 计量单位数量
           dbZxSkStockTransferRpt.setSpec(zxSkStockTransferRpt.getSpec());
           // 计量单位金额
           dbZxSkStockTransferRpt.setUnit(zxSkStockTransferRpt.getUnit());
           // 期初结存数量
           dbZxSkStockTransferRpt.setQcinQty(zxSkStockTransferRpt.getQcinQty());
           // 期初结存金额
           dbZxSkStockTransferRpt.setQcinAmt(zxSkStockTransferRpt.getQcinAmt());
           // 本季收入甲供数量
           dbZxSkStockTransferRpt.setC1Qty(zxSkStockTransferRpt.getC1Qty());
           // 本季收入甲供金额
           dbZxSkStockTransferRpt.setC1Amt(zxSkStockTransferRpt.getC1Amt());
           // 本季收入甲供平均单价
           dbZxSkStockTransferRpt.setC1Price(zxSkStockTransferRpt.getC1Price());
           // 本季收入自采数量
           dbZxSkStockTransferRpt.setC2Qty(zxSkStockTransferRpt.getC2Qty());
           // 本季收入自采金额
           dbZxSkStockTransferRpt.setC2Amt(zxSkStockTransferRpt.getC2Amt());
           // 本季收入自采平均单价
           dbZxSkStockTransferRpt.setC2Price(zxSkStockTransferRpt.getC2Price());
           // 本季收入其他数量
           dbZxSkStockTransferRpt.setC3Qty(zxSkStockTransferRpt.getC3Qty());
           // 本季收入其他金额
           dbZxSkStockTransferRpt.setC3Amt(zxSkStockTransferRpt.getC3Amt());
           // 本季收入其他平均单价
           dbZxSkStockTransferRpt.setC3Price(zxSkStockTransferRpt.getC3Price());
           // 合计数量
           dbZxSkStockTransferRpt.setHQty(zxSkStockTransferRpt.getHQty());
           // 合计金额
           dbZxSkStockTransferRpt.setHAmt(zxSkStockTransferRpt.getHAmt());
           // 自年初收入合计数量
           dbZxSkStockTransferRpt.setTotalQtyS(zxSkStockTransferRpt.getTotalQtyS());
           // 自年初收入合计金额
           dbZxSkStockTransferRpt.setTotalAmtS(zxSkStockTransferRpt.getTotalAmtS());
           // 本季发出消耗数量
           dbZxSkStockTransferRpt.setF1outQty(zxSkStockTransferRpt.getF1outQty());
           // 本季发出消耗金额
           dbZxSkStockTransferRpt.setF1outAmt(zxSkStockTransferRpt.getF1outAmt());
           // 本季发出其他数量
           dbZxSkStockTransferRpt.setF1outQtyQ(zxSkStockTransferRpt.getF1outQtyQ());
           // 本季发出其他金额
           dbZxSkStockTransferRpt.setF1outAmtQ(zxSkStockTransferRpt.getF1outAmtQ());
           // 本季发出合计数量
           dbZxSkStockTransferRpt.setQtyT(zxSkStockTransferRpt.getQtyT());
           // 本季发出合计金额
           dbZxSkStockTransferRpt.setAmtT(zxSkStockTransferRpt.getAmtT());
           // 自年初发出合计数量
           dbZxSkStockTransferRpt.setF1outQtyN(zxSkStockTransferRpt.getF1outQtyN());
           // 自年初发出合计金额
           dbZxSkStockTransferRpt.setF1outAmtN(zxSkStockTransferRpt.getF1outAmtN());
           // 本季结存数量
           dbZxSkStockTransferRpt.setTQty(zxSkStockTransferRpt.getTQty());
           // 本季结存金额
           dbZxSkStockTransferRpt.setTAmt(zxSkStockTransferRpt.getTAmt());
           // 备注
           dbZxSkStockTransferRpt.setRemarks(zxSkStockTransferRpt.getRemarks());
           // 排序
           dbZxSkStockTransferRpt.setSort(zxSkStockTransferRpt.getSort());
           // 共通
           dbZxSkStockTransferRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferRptMapper.updateByPrimaryKey(dbZxSkStockTransferRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockTransferRpt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferRpt(List<ZxSkStockTransferRpt> zxSkStockTransferRptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferRptList != null && zxSkStockTransferRptList.size() > 0) {
           ZxSkStockTransferRpt zxSkStockTransferRpt = new ZxSkStockTransferRpt();
           zxSkStockTransferRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferRptMapper.batchDeleteUpdateZxSkStockTransferRpt(zxSkStockTransferRptList, zxSkStockTransferRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferRptList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxSkStockTransferRpt> ureportZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt) {
    	List<ZxSkStockTransferRpt> zxSkStockTransferRptList = zxSkStockTransferRptMapper.selectZxSkStockTransferRpt(zxSkStockTransferRpt);
    	return zxSkStockTransferRptList;
    }
    
    @Override
    public ResponseEntity ureportZxSkStockTransferRptIdle(ZxSkStockTransferRpt zxSkStockTransferRpt) {
        if (zxSkStockTransferRpt == null) {
            zxSkStockTransferRpt = new ZxSkStockTransferRpt();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferRpt.getPage(),zxSkStockTransferRpt.getLimit());
        // 获取数据
        List<ZxSkStockTransferRpt> zxSkStockTransferRptList = zxSkStockTransferRptMapper.selectZxSkStockTransferRpt(zxSkStockTransferRpt);
        // 得到分页信息
        PageInfo<ZxSkStockTransferRpt> p = new PageInfo<>(zxSkStockTransferRptList);

        return repEntity.okList(zxSkStockTransferRptList, p.getTotal());
    }
}
