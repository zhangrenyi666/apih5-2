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
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishShopDetailMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopDetail;
import com.apih5.service.ZxCtSuppliesContrReplenishShopDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrReplenishShopDetailService")
public class ZxCtSuppliesContrReplenishShopDetailServiceImpl implements ZxCtSuppliesContrReplenishShopDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopDetailMapper zxCtSuppliesContrReplenishShopDetailMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailListByCondition(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        if (zxCtSuppliesContrReplenishShopDetail == null) {
            zxCtSuppliesContrReplenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishShopDetail.getPage(),zxCtSuppliesContrReplenishShopDetail.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrReplenishShopDetail> zxCtSuppliesContrReplenishShopDetailList = zxCtSuppliesContrReplenishShopDetailMapper.selectByZxCtSuppliesContrReplenishShopDetailList(zxCtSuppliesContrReplenishShopDetail);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishShopDetail> p = new PageInfo<>(zxCtSuppliesContrReplenishShopDetailList);

        return repEntity.okList(zxCtSuppliesContrReplenishShopDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        if (zxCtSuppliesContrReplenishShopDetail == null) {
            zxCtSuppliesContrReplenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
        }
        // 获取数据
        ZxCtSuppliesContrReplenishShopDetail dbZxCtSuppliesContrReplenishShopDetail = zxCtSuppliesContrReplenishShopDetailMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishShopDetail.getZxCtSuppliesContrReplenishShopDetailId());
        // 数据存在
        if (dbZxCtSuppliesContrReplenishShopDetail != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishShopDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrReplenishShopDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrReplenishShopDetail.setZxCtSuppliesContrReplenishShopDetailId(UuidUtil.generate());
        zxCtSuppliesContrReplenishShopDetail.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrReplenishShopDetailMapper.insert(zxCtSuppliesContrReplenishShopDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishShopDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishShopDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishShopDetail dbZxCtSuppliesContrReplenishShopDetail = zxCtSuppliesContrReplenishShopDetailMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishShopDetail.getZxCtSuppliesContrReplenishShopDetailId());
        if (dbZxCtSuppliesContrReplenishShopDetail != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishShopDetail.getZxCtSuppliesContrReplenishShopDetailId())) {
           // 租期单位
           dbZxCtSuppliesContrReplenishShopDetail.setRentUnit(zxCtSuppliesContrReplenishShopDetail.getRentUnit());
           // 租期
           dbZxCtSuppliesContrReplenishShopDetail.setContrTrrm(zxCtSuppliesContrReplenishShopDetail.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrReplenishShopDetail.setRequestEdit(zxCtSuppliesContrReplenishShopDetail.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrReplenishShopDetail.setEditDate(zxCtSuppliesContrReplenishShopDetail.getEditDate());
           // 修改人
           dbZxCtSuppliesContrReplenishShopDetail.setEditUserID(zxCtSuppliesContrReplenishShopDetail.getEditUserID());
           // 修改人
           dbZxCtSuppliesContrReplenishShopDetail.setEditUserName(zxCtSuppliesContrReplenishShopDetail.getEditUserName());
           // 物资名称
           dbZxCtSuppliesContrReplenishShopDetail.setWorkName(zxCtSuppliesContrReplenishShopDetail.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrReplenishShopDetail.setWorkTypeID(zxCtSuppliesContrReplenishShopDetail.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrReplenishShopDetail.setWorkType(zxCtSuppliesContrReplenishShopDetail.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrReplenishShopDetail.setSpec(zxCtSuppliesContrReplenishShopDetail.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrReplenishShopDetail.setWorkID(zxCtSuppliesContrReplenishShopDetail.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrReplenishShopDetail.setWorkNo(zxCtSuppliesContrReplenishShopDetail.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrReplenishShopDetail.setTaxRate(zxCtSuppliesContrReplenishShopDetail.getTaxRate());
           // 数量
           dbZxCtSuppliesContrReplenishShopDetail.setQty(zxCtSuppliesContrReplenishShopDetail.getQty());
           // 是否网价
           dbZxCtSuppliesContrReplenishShopDetail.setIsNetPrice(zxCtSuppliesContrReplenishShopDetail.getIsNetPrice());
           // 实际开始时间
           dbZxCtSuppliesContrReplenishShopDetail.setActualStartDate(zxCtSuppliesContrReplenishShopDetail.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrReplenishShopDetail.setActualEndDate(zxCtSuppliesContrReplenishShopDetail.getActualEndDate());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishShopDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishShopDetail.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishShopDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishShopDetail.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishShopDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishShopDetail.getUpAlterContractSum());
           // 界面展现类型
           dbZxCtSuppliesContrReplenishShopDetail.setViewType(zxCtSuppliesContrReplenishShopDetail.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrReplenishShopDetail.setPlanStartDate(zxCtSuppliesContrReplenishShopDetail.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrReplenishShopDetail.setPlanEndDate(zxCtSuppliesContrReplenishShopDetail.getPlanEndDate());
           // 合同明细ID
           dbZxCtSuppliesContrReplenishShopDetail.setContrItemID(zxCtSuppliesContrReplenishShopDetail.getContrItemID());
           // 合同变更ID
           dbZxCtSuppliesContrReplenishShopDetail.setContrAlterID(zxCtSuppliesContrReplenishShopDetail.getContrAlterID());
           // 含税合同金额
           dbZxCtSuppliesContrReplenishShopDetail.setContractSum(zxCtSuppliesContrReplenishShopDetail.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrReplenishShopDetail.setPrice(zxCtSuppliesContrReplenishShopDetail.getPrice());
           // 单位
           dbZxCtSuppliesContrReplenishShopDetail.setUnit(zxCtSuppliesContrReplenishShopDetail.getUnit());
           // 单位
           dbZxCtSuppliesContrReplenishShopDetail.setTreenode(zxCtSuppliesContrReplenishShopDetail.getTreenode());
           // 不含税税额
           dbZxCtSuppliesContrReplenishShopDetail.setContractSumTax(zxCtSuppliesContrReplenishShopDetail.getContractSumTax());
           // 不含税合同金额
           dbZxCtSuppliesContrReplenishShopDetail.setContractSumNoTax(zxCtSuppliesContrReplenishShopDetail.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrReplenishShopDetail.setPriceNoTax(zxCtSuppliesContrReplenishShopDetail.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrReplenishShopDetail.setChangeDate(zxCtSuppliesContrReplenishShopDetail.getChangeDate());
           // 变更类型
           dbZxCtSuppliesContrReplenishShopDetail.setAlterType(zxCtSuppliesContrReplenishShopDetail.getAlterType());
           // 变更后租期
           dbZxCtSuppliesContrReplenishShopDetail.setAlterTrrm(zxCtSuppliesContrReplenishShopDetail.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrReplenishShopDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishShopDetail.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrReplenishShopDetail.setChangeQty(zxCtSuppliesContrReplenishShopDetail.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrReplenishShopDetail.setChangeContractSum(zxCtSuppliesContrReplenishShopDetail.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrReplenishShopDetail.setChangePrice(zxCtSuppliesContrReplenishShopDetail.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrReplenishShopDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishShopDetail.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrReplenishShopDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishShopDetail.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrReplenishShopDetail.setRemarks(zxCtSuppliesContrReplenishShopDetail.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishShopDetail.setSort(zxCtSuppliesContrReplenishShopDetail.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishShopDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishShopDetailMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishShopDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishShopDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(List<ZxCtSuppliesContrReplenishShopDetail> zxCtSuppliesContrReplenishShopDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrReplenishShopDetailList != null && zxCtSuppliesContrReplenishShopDetailList.size() > 0) {
           ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail = new ZxCtSuppliesContrReplenishShopDetail();
           zxCtSuppliesContrReplenishShopDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishShopDetailMapper.batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(zxCtSuppliesContrReplenishShopDetailList, zxCtSuppliesContrReplenishShopDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishShopDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
