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
import com.apih5.mybatis.dao.ZxCtSuppliesShopEndorseDetailMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopEndorseDetail;
import com.apih5.service.ZxCtSuppliesShopEndorseDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopEndorseDetailService")
public class ZxCtSuppliesShopEndorseDetailServiceImpl implements ZxCtSuppliesShopEndorseDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopEndorseDetailMapper zxCtSuppliesShopEndorseDetailMapper;

    @Override
    public ResponseEntity getZxCtSuppliesShopEndorseDetailListByCondition(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        if (zxCtSuppliesShopEndorseDetail == null) {
            zxCtSuppliesShopEndorseDetail = new ZxCtSuppliesShopEndorseDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopEndorseDetail.getPage(),zxCtSuppliesShopEndorseDetail.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopEndorseDetail> zxCtSuppliesShopEndorseDetailList = zxCtSuppliesShopEndorseDetailMapper.selectByZxCtSuppliesShopEndorseDetailList(zxCtSuppliesShopEndorseDetail);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopEndorseDetail> p = new PageInfo<>(zxCtSuppliesShopEndorseDetailList);

        return repEntity.okList(zxCtSuppliesShopEndorseDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopEndorseDetailDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        if (zxCtSuppliesShopEndorseDetail == null) {
            zxCtSuppliesShopEndorseDetail = new ZxCtSuppliesShopEndorseDetail();
        }
        // 获取数据
        ZxCtSuppliesShopEndorseDetail dbZxCtSuppliesShopEndorseDetail = zxCtSuppliesShopEndorseDetailMapper.selectByPrimaryKey(zxCtSuppliesShopEndorseDetail.getZxCtSuppliesShopEndorseDetailId());
        // 数据存在
        if (dbZxCtSuppliesShopEndorseDetail != null) {
            return repEntity.ok(dbZxCtSuppliesShopEndorseDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopEndorseDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopEndorseDetail.setZxCtSuppliesShopEndorseDetailId(UuidUtil.generate());
        zxCtSuppliesShopEndorseDetail.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopEndorseDetailMapper.insert(zxCtSuppliesShopEndorseDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopEndorseDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopEndorseDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopEndorseDetail dbZxCtSuppliesShopEndorseDetail = zxCtSuppliesShopEndorseDetailMapper.selectByPrimaryKey(zxCtSuppliesShopEndorseDetail.getZxCtSuppliesShopEndorseDetailId());
        if (dbZxCtSuppliesShopEndorseDetail != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopEndorseDetail.getZxCtSuppliesShopEndorseDetailId())) {
           // 最后编辑时间
           dbZxCtSuppliesShopEndorseDetail.setEditTime(zxCtSuppliesShopEndorseDetail.getEditTime());
           // 主表ID
           dbZxCtSuppliesShopEndorseDetail.setMainID(zxCtSuppliesShopEndorseDetail.getMainID());
           // 选择单据批次
           dbZxCtSuppliesShopEndorseDetail.setSaveNumbers(zxCtSuppliesShopEndorseDetail.getSaveNumbers());
           // 序号
           dbZxCtSuppliesShopEndorseDetail.setOrderNum(zxCtSuppliesShopEndorseDetail.getOrderNum());
           // 物资名称
           dbZxCtSuppliesShopEndorseDetail.setResName(zxCtSuppliesShopEndorseDetail.getResName());
           // 物资编码
           dbZxCtSuppliesShopEndorseDetail.setResCode(zxCtSuppliesShopEndorseDetail.getResCode());
           // 物资ID
           dbZxCtSuppliesShopEndorseDetail.setResID(zxCtSuppliesShopEndorseDetail.getResID());
           // 所属公司排序
           dbZxCtSuppliesShopEndorseDetail.setComOrders(zxCtSuppliesShopEndorseDetail.getComOrders());
           // 所属公司名称
           dbZxCtSuppliesShopEndorseDetail.setComName(zxCtSuppliesShopEndorseDetail.getComName());
           // 所属公司ID
           dbZxCtSuppliesShopEndorseDetail.setComID(zxCtSuppliesShopEndorseDetail.getComID());
           // 收料单(或退货单)编号
           dbZxCtSuppliesShopEndorseDetail.setStockBillNo(zxCtSuppliesShopEndorseDetail.getStockBillNo());
           // 收料单(或退货单)ID
           dbZxCtSuppliesShopEndorseDetail.setStockBillID(zxCtSuppliesShopEndorseDetail.getStockBillID());
           // 是否预收
           dbZxCtSuppliesShopEndorseDetail.setPrecollecte(zxCtSuppliesShopEndorseDetail.getPrecollecte());
           // 上期末累计结算数量
           dbZxCtSuppliesShopEndorseDetail.setUpTotalQty(zxCtSuppliesShopEndorseDetail.getUpTotalQty());
           // 结算数量
           dbZxCtSuppliesShopEndorseDetail.setQty(zxCtSuppliesShopEndorseDetail.getQty());
           // 结算期次
           dbZxCtSuppliesShopEndorseDetail.setPeriod(zxCtSuppliesShopEndorseDetail.getPeriod());
           // 合同数量
           dbZxCtSuppliesShopEndorseDetail.setContractQty(zxCtSuppliesShopEndorseDetail.getContractQty());
           // 合同清单ID
           dbZxCtSuppliesShopEndorseDetail.setContrItemID(zxCtSuppliesShopEndorseDetail.getContrItemID());
           // 合同ID
           dbZxCtSuppliesShopEndorseDetail.setContractID(zxCtSuppliesShopEndorseDetail.getContractID());
           // 规格型号
           dbZxCtSuppliesShopEndorseDetail.setSpec(zxCtSuppliesShopEndorseDetail.getSpec());
           // 单位
           dbZxCtSuppliesShopEndorseDetail.setUnit(zxCtSuppliesShopEndorseDetail.getUnit());
           // 单据日期
           dbZxCtSuppliesShopEndorseDetail.setBillDate(zxCtSuppliesShopEndorseDetail.getBillDate());
           // 单据明细ID
           dbZxCtSuppliesShopEndorseDetail.setStockBillItemID(zxCtSuppliesShopEndorseDetail.getStockBillItemID());
           // 本期金额
           dbZxCtSuppliesShopEndorseDetail.setThisAmt(zxCtSuppliesShopEndorseDetail.getThisAmt());
           // 本期单价
           dbZxCtSuppliesShopEndorseDetail.setThisPrice(zxCtSuppliesShopEndorseDetail.getThisPrice());
           // 备注
           dbZxCtSuppliesShopEndorseDetail.setRemarks(zxCtSuppliesShopEndorseDetail.getRemarks());
           // 排序
           dbZxCtSuppliesShopEndorseDetail.setSort(zxCtSuppliesShopEndorseDetail.getSort());
           // 共通
           dbZxCtSuppliesShopEndorseDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopEndorseDetailMapper.updateByPrimaryKey(dbZxCtSuppliesShopEndorseDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopEndorseDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopEndorseDetail(List<ZxCtSuppliesShopEndorseDetail> zxCtSuppliesShopEndorseDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopEndorseDetailList != null && zxCtSuppliesShopEndorseDetailList.size() > 0) {
           ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail = new ZxCtSuppliesShopEndorseDetail();
           zxCtSuppliesShopEndorseDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopEndorseDetailMapper.batchDeleteUpdateZxCtSuppliesShopEndorseDetail(zxCtSuppliesShopEndorseDetailList, zxCtSuppliesShopEndorseDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopEndorseDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
