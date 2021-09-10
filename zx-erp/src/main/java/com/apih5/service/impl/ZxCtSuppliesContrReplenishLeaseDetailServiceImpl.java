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
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishLeaseDetailMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseDetail;
import com.apih5.service.ZxCtSuppliesContrReplenishLeaseDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrReplenishLeaseDetailService")
public class ZxCtSuppliesContrReplenishLeaseDetailServiceImpl implements ZxCtSuppliesContrReplenishLeaseDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseDetailMapper zxCtSuppliesContrReplenishLeaseDetailMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailListByCondition(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        if (zxCtSuppliesContrReplenishLeaseDetail == null) {
            zxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishLeaseDetail.getPage(),zxCtSuppliesContrReplenishLeaseDetail.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrReplenishLeaseDetail> zxCtSuppliesContrReplenishLeaseDetailList = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByZxCtSuppliesContrReplenishLeaseDetailList(zxCtSuppliesContrReplenishLeaseDetail);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishLeaseDetail> p = new PageInfo<>(zxCtSuppliesContrReplenishLeaseDetailList);

        return repEntity.okList(zxCtSuppliesContrReplenishLeaseDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        if (zxCtSuppliesContrReplenishLeaseDetail == null) {
            zxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
        }
        // 获取数据
        ZxCtSuppliesContrReplenishLeaseDetail dbZxCtSuppliesContrReplenishLeaseDetail = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishLeaseDetail.getZxCtSuppliesContrReplenishLeaseDetailId());
        // 数据存在
        if (dbZxCtSuppliesContrReplenishLeaseDetail != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishLeaseDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrReplenishLeaseDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrReplenishLeaseDetail.setZxCtSuppliesContrReplenishLeaseDetailId(UuidUtil.generate());
        zxCtSuppliesContrReplenishLeaseDetail.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrReplenishLeaseDetailMapper.insert(zxCtSuppliesContrReplenishLeaseDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishLeaseDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishLeaseDetail dbZxCtSuppliesContrReplenishLeaseDetail = zxCtSuppliesContrReplenishLeaseDetailMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishLeaseDetail.getZxCtSuppliesContrReplenishLeaseDetailId());
        if (dbZxCtSuppliesContrReplenishLeaseDetail != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishLeaseDetail.getZxCtSuppliesContrReplenishLeaseDetailId())) {
           // 租期单位
           dbZxCtSuppliesContrReplenishLeaseDetail.setRentUnit(zxCtSuppliesContrReplenishLeaseDetail.getRentUnit());
           // 租期
           dbZxCtSuppliesContrReplenishLeaseDetail.setContrTrrm(zxCtSuppliesContrReplenishLeaseDetail.getContrTrrm());
           // 要求修改
           dbZxCtSuppliesContrReplenishLeaseDetail.setRequestEdit(zxCtSuppliesContrReplenishLeaseDetail.getRequestEdit());
           // 修改日期
           dbZxCtSuppliesContrReplenishLeaseDetail.setEditDate(zxCtSuppliesContrReplenishLeaseDetail.getEditDate());
           // 修改人
           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserID(zxCtSuppliesContrReplenishLeaseDetail.getEditUserID());
           // 修改人
           dbZxCtSuppliesContrReplenishLeaseDetail.setEditUserName(zxCtSuppliesContrReplenishLeaseDetail.getEditUserName());
           // 物资名称
           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkName(zxCtSuppliesContrReplenishLeaseDetail.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkTypeID(zxCtSuppliesContrReplenishLeaseDetail.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkType(zxCtSuppliesContrReplenishLeaseDetail.getWorkType());
           // 物资规格
           dbZxCtSuppliesContrReplenishLeaseDetail.setSpec(zxCtSuppliesContrReplenishLeaseDetail.getSpec());
           // 物资编码ID
           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkID(zxCtSuppliesContrReplenishLeaseDetail.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrReplenishLeaseDetail.setWorkNo(zxCtSuppliesContrReplenishLeaseDetail.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrReplenishLeaseDetail.setTaxRate(zxCtSuppliesContrReplenishLeaseDetail.getTaxRate());
           // 数量
           dbZxCtSuppliesContrReplenishLeaseDetail.setQty(zxCtSuppliesContrReplenishLeaseDetail.getQty());
           // 是否网价
           dbZxCtSuppliesContrReplenishLeaseDetail.setIsNetPrice(zxCtSuppliesContrReplenishLeaseDetail.getIsNetPrice());
           // 实际开始时间
           dbZxCtSuppliesContrReplenishLeaseDetail.setActualStartDate(zxCtSuppliesContrReplenishLeaseDetail.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrReplenishLeaseDetail.setActualEndDate(zxCtSuppliesContrReplenishLeaseDetail.getActualEndDate());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumTax(zxCtSuppliesContrReplenishLeaseDetail.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishLeaseDetail.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishLeaseDetail.setUpAlterContractSum(zxCtSuppliesContrReplenishLeaseDetail.getUpAlterContractSum());
           // 界面展现类型
           dbZxCtSuppliesContrReplenishLeaseDetail.setViewType(zxCtSuppliesContrReplenishLeaseDetail.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanStartDate(zxCtSuppliesContrReplenishLeaseDetail.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrReplenishLeaseDetail.setPlanEndDate(zxCtSuppliesContrReplenishLeaseDetail.getPlanEndDate());
           // 合同明细ID
           dbZxCtSuppliesContrReplenishLeaseDetail.setContrItemID(zxCtSuppliesContrReplenishLeaseDetail.getContrItemID());
           // 合同变更ID
           dbZxCtSuppliesContrReplenishLeaseDetail.setContrAlterID(zxCtSuppliesContrReplenishLeaseDetail.getContrAlterID());
           // 含税合同金额
           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSum(zxCtSuppliesContrReplenishLeaseDetail.getContractSum());
           // 含税合同单价
           dbZxCtSuppliesContrReplenishLeaseDetail.setPrice(zxCtSuppliesContrReplenishLeaseDetail.getPrice());
           // 单位
           dbZxCtSuppliesContrReplenishLeaseDetail.setUnit(zxCtSuppliesContrReplenishLeaseDetail.getUnit());
           // 单位
           dbZxCtSuppliesContrReplenishLeaseDetail.setTreenode(zxCtSuppliesContrReplenishLeaseDetail.getTreenode());
           // 不含税税额
           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumTax(zxCtSuppliesContrReplenishLeaseDetail.getContractSumTax());
           // 不含税合同金额
           dbZxCtSuppliesContrReplenishLeaseDetail.setContractSumNoTax(zxCtSuppliesContrReplenishLeaseDetail.getContractSumNoTax());
           // 不含税合同单价
           dbZxCtSuppliesContrReplenishLeaseDetail.setPriceNoTax(zxCtSuppliesContrReplenishLeaseDetail.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeDate(zxCtSuppliesContrReplenishLeaseDetail.getChangeDate());
           // 变更类型
           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterType(zxCtSuppliesContrReplenishLeaseDetail.getAlterType());
           // 变更后租期
           dbZxCtSuppliesContrReplenishLeaseDetail.setAlterTrrm(zxCtSuppliesContrReplenishLeaseDetail.getAlterTrrm());
           // 变更后税额
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumTax(zxCtSuppliesContrReplenishLeaseDetail.getChangeContractSumTax());
           // 变更后数量
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeQty(zxCtSuppliesContrReplenishLeaseDetail.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSum(zxCtSuppliesContrReplenishLeaseDetail.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePrice(zxCtSuppliesContrReplenishLeaseDetail.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangeContractSumNoTax(zxCtSuppliesContrReplenishLeaseDetail.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrReplenishLeaseDetail.setChangePriceNoTax(zxCtSuppliesContrReplenishLeaseDetail.getChangePriceNoTax());
           // 备注
           dbZxCtSuppliesContrReplenishLeaseDetail.setRemarks(zxCtSuppliesContrReplenishLeaseDetail.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishLeaseDetail.setSort(zxCtSuppliesContrReplenishLeaseDetail.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishLeaseDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishLeaseDetailMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishLeaseDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishLeaseDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(List<ZxCtSuppliesContrReplenishLeaseDetail> zxCtSuppliesContrReplenishLeaseDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrReplenishLeaseDetailList != null && zxCtSuppliesContrReplenishLeaseDetailList.size() > 0) {
           ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail = new ZxCtSuppliesContrReplenishLeaseDetail();
           zxCtSuppliesContrReplenishLeaseDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishLeaseDetailMapper.batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(zxCtSuppliesContrReplenishLeaseDetailList, zxCtSuppliesContrReplenishLeaseDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishLeaseDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
