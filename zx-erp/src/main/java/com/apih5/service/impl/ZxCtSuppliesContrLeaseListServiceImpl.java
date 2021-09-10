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
import com.apih5.mybatis.dao.ZxCtSuppliesContrLeaseListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.service.ZxCtSuppliesContrLeaseListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrLeaseListService")
public class ZxCtSuppliesContrLeaseListServiceImpl implements ZxCtSuppliesContrLeaseListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListMapper zxCtSuppliesContrLeaseListMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContrLeaseListListByCondition(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        if (zxCtSuppliesContrLeaseList == null) {
            zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrLeaseList.getPage(),zxCtSuppliesContrLeaseList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrLeaseList> zxCtSuppliesContrLeaseListList = zxCtSuppliesContrLeaseListMapper.selectByZxCtSuppliesContrLeaseListList(zxCtSuppliesContrLeaseList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrLeaseList> p = new PageInfo<>(zxCtSuppliesContrLeaseListList);

        return repEntity.okList(zxCtSuppliesContrLeaseListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrLeaseListDetail(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        if (zxCtSuppliesContrLeaseList == null) {
            zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
        }
        // 获取数据
        ZxCtSuppliesContrLeaseList dbZxCtSuppliesContrLeaseList = zxCtSuppliesContrLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrLeaseList.getZxCtSuppliesContrLeaseListId());
        // 数据存在
        if (dbZxCtSuppliesContrLeaseList != null) {
            return repEntity.ok(dbZxCtSuppliesContrLeaseList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrLeaseList(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrLeaseList.setZxCtSuppliesContrLeaseListId(UuidUtil.generate());
        zxCtSuppliesContrLeaseList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrLeaseListMapper.insert(zxCtSuppliesContrLeaseList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrLeaseList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrLeaseList(ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrLeaseList dbZxCtSuppliesContrLeaseList = zxCtSuppliesContrLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrLeaseList.getZxCtSuppliesContrLeaseListId());
        if (dbZxCtSuppliesContrLeaseList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrLeaseList.getZxCtSuppliesContrLeaseListId())) {
           // 租期单位
           dbZxCtSuppliesContrLeaseList.setRentUnit(zxCtSuppliesContrLeaseList.getRentUnit());
           // 租期
           dbZxCtSuppliesContrLeaseList.setContrTrrm(zxCtSuppliesContrLeaseList.getContrTrrm());
           // 物资名称
           dbZxCtSuppliesContrLeaseList.setWorkName(zxCtSuppliesContrLeaseList.getWorkName());
           // 物资类别ID
           dbZxCtSuppliesContrLeaseList.setWorkTypeID(zxCtSuppliesContrLeaseList.getWorkTypeID());
           // 物资类别
           dbZxCtSuppliesContrLeaseList.setWorkType(zxCtSuppliesContrLeaseList.getWorkType());
           // 物资编码ID
           dbZxCtSuppliesContrLeaseList.setWorkID(zxCtSuppliesContrLeaseList.getWorkID());
           // 物资编码
           dbZxCtSuppliesContrLeaseList.setWorkNo(zxCtSuppliesContrLeaseList.getWorkNo());
           // 税率(%)
           dbZxCtSuppliesContrLeaseList.setTaxRate(zxCtSuppliesContrLeaseList.getTaxRate());
           // 数量
           dbZxCtSuppliesContrLeaseList.setQty(zxCtSuppliesContrLeaseList.getQty());
           // 是否网价
           dbZxCtSuppliesContrLeaseList.setIsNetPrice(zxCtSuppliesContrLeaseList.getIsNetPrice());
           // 实际开始时间
           dbZxCtSuppliesContrLeaseList.setActualStartDate(zxCtSuppliesContrLeaseList.getActualStartDate());
           // 实际结束时间
           dbZxCtSuppliesContrLeaseList.setActualEndDate(zxCtSuppliesContrLeaseList.getActualEndDate());
           // 评审ID
           dbZxCtSuppliesContrLeaseList.setPp5(zxCtSuppliesContrLeaseList.getPp5());
           // 界面展现类型
           dbZxCtSuppliesContrLeaseList.setViewType(zxCtSuppliesContrLeaseList.getViewType());
           // 计划开始时间
           dbZxCtSuppliesContrLeaseList.setPlanStartDate(zxCtSuppliesContrLeaseList.getPlanStartDate());
           // 计划结束时间
           dbZxCtSuppliesContrLeaseList.setPlanEndDate(zxCtSuppliesContrLeaseList.getPlanEndDate());
           // 合同ID
           dbZxCtSuppliesContrLeaseList.setContractID(zxCtSuppliesContrLeaseList.getContractID());
           // 含税金额
           dbZxCtSuppliesContrLeaseList.setContractSum(zxCtSuppliesContrLeaseList.getContractSum());
           // 含税单价
           dbZxCtSuppliesContrLeaseList.setPrice(zxCtSuppliesContrLeaseList.getPrice());
           // 规格型号
           dbZxCtSuppliesContrLeaseList.setSpec(zxCtSuppliesContrLeaseList.getSpec());
           // 单位
           dbZxCtSuppliesContrLeaseList.setTreenode(zxCtSuppliesContrLeaseList.getTreenode());
           // 单位
           dbZxCtSuppliesContrLeaseList.setUnit(zxCtSuppliesContrLeaseList.getUnit());
           // 不含税金额
           dbZxCtSuppliesContrLeaseList.setContractSumNoTax(zxCtSuppliesContrLeaseList.getContractSumNoTax());
           // 不含税单价
           dbZxCtSuppliesContrLeaseList.setPriceNoTax(zxCtSuppliesContrLeaseList.getPriceNoTax());
           // 变更日期
           dbZxCtSuppliesContrLeaseList.setChangeDate(zxCtSuppliesContrLeaseList.getChangeDate());
           // 变更后租期
           dbZxCtSuppliesContrLeaseList.setAlterTrrm(zxCtSuppliesContrLeaseList.getAlterTrrm());
           // 变更后数量
           dbZxCtSuppliesContrLeaseList.setChangeQty(zxCtSuppliesContrLeaseList.getChangeQty());
           // 变更后含税金额
           dbZxCtSuppliesContrLeaseList.setChangeContractSum(zxCtSuppliesContrLeaseList.getChangeContractSum());
           // 变更后含税单价
           dbZxCtSuppliesContrLeaseList.setChangePrice(zxCtSuppliesContrLeaseList.getChangePrice());
           // 变更后不含税金额
           dbZxCtSuppliesContrLeaseList.setChangeContractSumNoTax(zxCtSuppliesContrLeaseList.getChangeContractSumNoTax());
           // 变更后不含税单价
           dbZxCtSuppliesContrLeaseList.setChangePriceNoTax(zxCtSuppliesContrLeaseList.getChangePriceNoTax());
           // 变更ID
           dbZxCtSuppliesContrLeaseList.setPp6(zxCtSuppliesContrLeaseList.getPp6());
           // 备注
           dbZxCtSuppliesContrLeaseList.setRemarks(zxCtSuppliesContrLeaseList.getRemarks());
           // 排序
           dbZxCtSuppliesContrLeaseList.setSort(zxCtSuppliesContrLeaseList.getSort());
           // 共通
           dbZxCtSuppliesContrLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrLeaseList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrLeaseList(List<ZxCtSuppliesContrLeaseList> zxCtSuppliesContrLeaseListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrLeaseListList != null && zxCtSuppliesContrLeaseListList.size() > 0) {
           ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
           zxCtSuppliesContrLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrLeaseListMapper.batchDeleteUpdateZxCtSuppliesContrLeaseList(zxCtSuppliesContrLeaseListList, zxCtSuppliesContrLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrLeaseListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
