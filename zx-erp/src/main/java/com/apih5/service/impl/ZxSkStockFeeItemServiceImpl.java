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
import com.apih5.mybatis.dao.ZxSkStockFeeItemMapper;
import com.apih5.mybatis.pojo.ZxSkStockFeeItem;
import com.apih5.service.ZxSkStockFeeItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockFeeItemService")
public class ZxSkStockFeeItemServiceImpl implements ZxSkStockFeeItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockFeeItemMapper zxSkStockFeeItemMapper;

    @Override
    public ResponseEntity getZxSkStockFeeItemListByCondition(ZxSkStockFeeItem zxSkStockFeeItem) {
        if (zxSkStockFeeItem == null) {
            zxSkStockFeeItem = new ZxSkStockFeeItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockFeeItem.getPage(),zxSkStockFeeItem.getLimit());
        // 获取数据
        List<ZxSkStockFeeItem> zxSkStockFeeItemList = zxSkStockFeeItemMapper.selectByZxSkStockFeeItemList(zxSkStockFeeItem);
        // 得到分页信息
        PageInfo<ZxSkStockFeeItem> p = new PageInfo<>(zxSkStockFeeItemList);

        return repEntity.okList(zxSkStockFeeItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockFeeItemDetail(ZxSkStockFeeItem zxSkStockFeeItem) {
        if (zxSkStockFeeItem == null) {
            zxSkStockFeeItem = new ZxSkStockFeeItem();
        }
        // 获取数据
        ZxSkStockFeeItem dbZxSkStockFeeItem = zxSkStockFeeItemMapper.selectByPrimaryKey(zxSkStockFeeItem.getZxSkStockFeeItemId());
        // 数据存在
        if (dbZxSkStockFeeItem != null) {
            return repEntity.ok(dbZxSkStockFeeItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockFeeItem(ZxSkStockFeeItem zxSkStockFeeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockFeeItem.setZxSkStockFeeItemId(UuidUtil.generate());
        zxSkStockFeeItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockFeeItemMapper.insert(zxSkStockFeeItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockFeeItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockFeeItem(ZxSkStockFeeItem zxSkStockFeeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockFeeItem dbZxSkStockFeeItem = zxSkStockFeeItemMapper.selectByPrimaryKey(zxSkStockFeeItem.getZxSkStockFeeItemId());
        if (dbZxSkStockFeeItem != null && StrUtil.isNotEmpty(dbZxSkStockFeeItem.getZxSkStockFeeItemId())) {
           // 
           dbZxSkStockFeeItem.setMainID(zxSkStockFeeItem.getMainID());
           // 清单编号
           dbZxSkStockFeeItem.setWorkNo(zxSkStockFeeItem.getWorkNo());
           // 清单名称
           dbZxSkStockFeeItem.setWorkName(zxSkStockFeeItem.getWorkName());
           // 型号
           dbZxSkStockFeeItem.setSpec(zxSkStockFeeItem.getSpec());
           // 计量单位
           dbZxSkStockFeeItem.setUnit(zxSkStockFeeItem.getUnit());
           // 
           dbZxSkStockFeeItem.setWorkType(zxSkStockFeeItem.getWorkType());
           // 层次
           dbZxSkStockFeeItem.setTreenode(zxSkStockFeeItem.getTreenode());
           // 合同数量
           dbZxSkStockFeeItem.setQty(zxSkStockFeeItem.getQty());
           // 含税合同单价
           dbZxSkStockFeeItem.setPrice(zxSkStockFeeItem.getPrice());
           // 含税合同金额
           dbZxSkStockFeeItem.setContractSum(zxSkStockFeeItem.getContractSum());
           // 
           dbZxSkStockFeeItem.setSystemType(zxSkStockFeeItem.getSystemType());
           // 界面展现类型
           dbZxSkStockFeeItem.setViewType(zxSkStockFeeItem.getViewType());
           // 计划开始时间
           dbZxSkStockFeeItem.setPlanStartDate(zxSkStockFeeItem.getPlanStartDate());
           // 计划结束时间
           dbZxSkStockFeeItem.setPlanEndDate(zxSkStockFeeItem.getPlanEndDate());
           // 租赁开始时间
           dbZxSkStockFeeItem.setActualStartDate(zxSkStockFeeItem.getActualStartDate());
           // 租赁结束时间
           dbZxSkStockFeeItem.setActualEndDate(zxSkStockFeeItem.getActualEndDate());
           // 
           dbZxSkStockFeeItem.setCombProp(zxSkStockFeeItem.getCombProp());
           // 
           dbZxSkStockFeeItem.setPp1(zxSkStockFeeItem.getPp1());
           // 
           dbZxSkStockFeeItem.setPp2(zxSkStockFeeItem.getPp2());
           // 
           dbZxSkStockFeeItem.setPp3(zxSkStockFeeItem.getPp3());
           // 
           dbZxSkStockFeeItem.setPp4(zxSkStockFeeItem.getPp4());
           // 评审ID
           dbZxSkStockFeeItem.setPp5(zxSkStockFeeItem.getPp5());
           // 
           dbZxSkStockFeeItem.setPp6(zxSkStockFeeItem.getPp6());
           // 
           dbZxSkStockFeeItem.setPp7(zxSkStockFeeItem.getPp7());
           // 
           dbZxSkStockFeeItem.setPp8(zxSkStockFeeItem.getPp8());
           // 
           dbZxSkStockFeeItem.setPp9(zxSkStockFeeItem.getPp9());
           // 租期(台班)
           dbZxSkStockFeeItem.setPp10(zxSkStockFeeItem.getPp10());
           // 
           dbZxSkStockFeeItem.setEditTime(zxSkStockFeeItem.getEditTime());
           // 设备ID(暂用workType)
           dbZxSkStockFeeItem.setWorkTypeID(zxSkStockFeeItem.getWorkTypeID());
           // 
           dbZxSkStockFeeItem.setWorkID(zxSkStockFeeItem.getWorkID());
           // 数量
           dbZxSkStockFeeItem.setChangeQty(zxSkStockFeeItem.getChangeQty());
           // 变更后含税单价
           dbZxSkStockFeeItem.setChangePrice(zxSkStockFeeItem.getChangePrice());
           // 金额
           dbZxSkStockFeeItem.setChangeContractSum(zxSkStockFeeItem.getChangeContractSum());
           // null
           dbZxSkStockFeeItem.setChangeDate(zxSkStockFeeItem.getChangeDate());
           // 租赁单位
           dbZxSkStockFeeItem.setRentUnit(zxSkStockFeeItem.getRentUnit());
           // 变更后租赁开始时间
           dbZxSkStockFeeItem.setAlterRentStartDate(zxSkStockFeeItem.getAlterRentStartDate());
           // 变更后租赁结束时间
           dbZxSkStockFeeItem.setAlterRentEndDate(zxSkStockFeeItem.getAlterRentEndDate());
           // 1
           dbZxSkStockFeeItem.setAlterTrrm(zxSkStockFeeItem.getAlterTrrm());
           // 1
           dbZxSkStockFeeItem.setAlterPrice(zxSkStockFeeItem.getAlterPrice());
           // 1
           dbZxSkStockFeeItem.setAlterAmt(zxSkStockFeeItem.getAlterAmt());
           // 1
           dbZxSkStockFeeItem.setContrTrrm(zxSkStockFeeItem.getContrTrrm());
           // null
           dbZxSkStockFeeItem.setIsNetPrice(zxSkStockFeeItem.getIsNetPrice());
           // 要求修改
           dbZxSkStockFeeItem.setRequestEdit(zxSkStockFeeItem.getRequestEdit());
           // 修改人
           dbZxSkStockFeeItem.setEditUserID(zxSkStockFeeItem.getEditUserID());
           // 修改人
           dbZxSkStockFeeItem.setEditUserName(zxSkStockFeeItem.getEditUserName());
           // 修改日期
           dbZxSkStockFeeItem.setEditDate(zxSkStockFeeItem.getEditDate());
           // 不含税合同单价
           dbZxSkStockFeeItem.setPriceNoTax(zxSkStockFeeItem.getPriceNoTax());
           // 不含税合同金额
           dbZxSkStockFeeItem.setContractSumNoTax(zxSkStockFeeItem.getContractSumNoTax());
           // 变更后不含税单价
           dbZxSkStockFeeItem.setChangePriceNoTax(zxSkStockFeeItem.getChangePriceNoTax());
           // 入账金额
           dbZxSkStockFeeItem.setChangeContractSumNoTax(zxSkStockFeeItem.getChangeContractSumNoTax());
           // null
           dbZxSkStockFeeItem.setAlterPriceNoPrice(zxSkStockFeeItem.getAlterPriceNoPrice());
           // null
           dbZxSkStockFeeItem.setAlterAmtNoTax(zxSkStockFeeItem.getAlterAmtNoTax());
           // null
           dbZxSkStockFeeItem.setAlterAmtTax(zxSkStockFeeItem.getAlterAmtTax());
           // null
           dbZxSkStockFeeItem.setAlterPriceNoTax(zxSkStockFeeItem.getAlterPriceNoTax());
           // 合同ID
           dbZxSkStockFeeItem.setContractID(zxSkStockFeeItem.getContractID());
           // 税率(%)
           dbZxSkStockFeeItem.setTaxRate(zxSkStockFeeItem.getTaxRate());
           // 备注
           dbZxSkStockFeeItem.setRemarks(zxSkStockFeeItem.getRemarks());
           // 排序
           dbZxSkStockFeeItem.setSort(zxSkStockFeeItem.getSort());
           // 共通
           dbZxSkStockFeeItem.setModifyUserInfo(userKey, realName);
           flag = zxSkStockFeeItemMapper.updateByPrimaryKey(dbZxSkStockFeeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockFeeItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockFeeItem(List<ZxSkStockFeeItem> zxSkStockFeeItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockFeeItemList != null && zxSkStockFeeItemList.size() > 0) {
           ZxSkStockFeeItem zxSkStockFeeItem = new ZxSkStockFeeItem();
           zxSkStockFeeItem.setModifyUserInfo(userKey, realName);
           flag = zxSkStockFeeItemMapper.batchDeleteUpdateZxSkStockFeeItem(zxSkStockFeeItemList, zxSkStockFeeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockFeeItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
