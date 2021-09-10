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
import com.apih5.mybatis.dao.ZxSkStockTransItemDiskFillingStorageMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDiskFillingStorage;
import com.apih5.service.ZxSkStockTransItemDiskFillingStorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransItemDiskFillingStorageService")
public class ZxSkStockTransItemDiskFillingStorageServiceImpl implements ZxSkStockTransItemDiskFillingStorageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransItemDiskFillingStorageMapper zxSkStockTransItemDiskFillingStorageMapper;

    @Override
    public ResponseEntity getZxSkStockTransItemDiskFillingStorageListByCondition(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        if (zxSkStockTransItemDiskFillingStorage == null) {
            zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransItemDiskFillingStorage.getPage(),zxSkStockTransItemDiskFillingStorage.getLimit());
        // 获取数据
        List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList = zxSkStockTransItemDiskFillingStorageMapper.selectByZxSkStockTransItemDiskFillingStorageList(zxSkStockTransItemDiskFillingStorage);
        // 得到分页信息
        PageInfo<ZxSkStockTransItemDiskFillingStorage> p = new PageInfo<>(zxSkStockTransItemDiskFillingStorageList);

        return repEntity.okList(zxSkStockTransItemDiskFillingStorageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransItemDiskFillingStorageDetails(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        if (zxSkStockTransItemDiskFillingStorage == null) {
            zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
        }
        // 获取数据
        ZxSkStockTransItemDiskFillingStorage dbZxSkStockTransItemDiskFillingStorage = zxSkStockTransItemDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransItemDiskFillingStorage.getId());
        // 数据存在
        if (dbZxSkStockTransItemDiskFillingStorage != null) {
            return repEntity.ok(dbZxSkStockTransItemDiskFillingStorage);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkStockTransItemDiskFillingStorage(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransItemDiskFillingStorage.setId(UuidUtil.generate());
        zxSkStockTransItemDiskFillingStorage.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransItemDiskFillingStorageMapper.insert(zxSkStockTransItemDiskFillingStorage);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkStockTransItemDiskFillingStorage);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransItemDiskFillingStorage(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransItemDiskFillingStorage dbzxSkStockTransItemDiskFillingStorage = zxSkStockTransItemDiskFillingStorageMapper.selectByPrimaryKey(zxSkStockTransItemDiskFillingStorage.getId());
        if (dbzxSkStockTransItemDiskFillingStorage != null && StrUtil.isNotEmpty(dbzxSkStockTransItemDiskFillingStorage.getId())) {
           // 单据
           dbzxSkStockTransItemDiskFillingStorage.setBillID(zxSkStockTransItemDiskFillingStorage.getBillID());
           // 物资编码ID
           dbzxSkStockTransItemDiskFillingStorage.setResID(zxSkStockTransItemDiskFillingStorage.getResID());
           // 批次
           dbzxSkStockTransItemDiskFillingStorage.setBatchNo(zxSkStockTransItemDiskFillingStorage.getBatchNo());
           // 物资编码
           dbzxSkStockTransItemDiskFillingStorage.setResCode(zxSkStockTransItemDiskFillingStorage.getResCode());
           // 物资名称
           dbzxSkStockTransItemDiskFillingStorage.setResName(zxSkStockTransItemDiskFillingStorage.getResName());
           // 规格型号
           dbzxSkStockTransItemDiskFillingStorage.setSpec(zxSkStockTransItemDiskFillingStorage.getSpec());
           // 计量单位
           dbzxSkStockTransItemDiskFillingStorage.setResUnit(zxSkStockTransItemDiskFillingStorage.getResUnit());
           // 标准单价
           dbzxSkStockTransItemDiskFillingStorage.setStdPrice(zxSkStockTransItemDiskFillingStorage.getStdPrice());
           // 其它费
           dbzxSkStockTransItemDiskFillingStorage.setInFee(zxSkStockTransItemDiskFillingStorage.getInFee());
           // 数量
           dbzxSkStockTransItemDiskFillingStorage.setInQty(zxSkStockTransItemDiskFillingStorage.getInQty());
           // 单价
           dbzxSkStockTransItemDiskFillingStorage.setInPrice(zxSkStockTransItemDiskFillingStorage.getInPrice());
           // 入账金额
           dbzxSkStockTransItemDiskFillingStorage.setInAmt(zxSkStockTransItemDiskFillingStorage.getInAmt());
           // 管理单元ID
           dbzxSkStockTransItemDiskFillingStorage.setMuID(zxSkStockTransItemDiskFillingStorage.getMuID());
           // 清单项ID
           dbzxSkStockTransItemDiskFillingStorage.setWorkID(zxSkStockTransItemDiskFillingStorage.getWorkID());
           // 管理单元名称
           dbzxSkStockTransItemDiskFillingStorage.setMuName(zxSkStockTransItemDiskFillingStorage.getMuName());
           // 清单名称
           dbzxSkStockTransItemDiskFillingStorage.setWorkName(zxSkStockTransItemDiskFillingStorage.getWorkName());
           // 数量
           dbzxSkStockTransItemDiskFillingStorage.setOutQty(zxSkStockTransItemDiskFillingStorage.getOutQty());
           // 其他费
           dbzxSkStockTransItemDiskFillingStorage.setOutFee(zxSkStockTransItemDiskFillingStorage.getOutFee());
           // 出库成本价
           dbzxSkStockTransItemDiskFillingStorage.setOutCostPrice(zxSkStockTransItemDiskFillingStorage.getOutCostPrice());
           // 出库成本金额
           dbzxSkStockTransItemDiskFillingStorage.setOutCostAmt(zxSkStockTransItemDiskFillingStorage.getOutCostAmt());
           // 单价
           dbzxSkStockTransItemDiskFillingStorage.setOutPrice(zxSkStockTransItemDiskFillingStorage.getOutPrice());
           // 金额
           dbzxSkStockTransItemDiskFillingStorage.setOutAmt(zxSkStockTransItemDiskFillingStorage.getOutAmt());
           // 是否计提折旧
           dbzxSkStockTransItemDiskFillingStorage.setIsDepreciation(zxSkStockTransItemDiskFillingStorage.getIsDepreciation());
           // 备注
           dbzxSkStockTransItemDiskFillingStorage.setRemark(zxSkStockTransItemDiskFillingStorage.getRemark());
           // 金额
           dbzxSkStockTransItemDiskFillingStorage.setInAmtAll(zxSkStockTransItemDiskFillingStorage.getInAmtAll());
           // 不含税总价
           dbzxSkStockTransItemDiskFillingStorage.setInAmtNoTax(zxSkStockTransItemDiskFillingStorage.getInAmtNoTax());
           // 共通
           dbzxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemDiskFillingStorageMapper.updateByPrimaryKey(dbzxSkStockTransItemDiskFillingStorage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkStockTransItemDiskFillingStorage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransItemDiskFillingStorageList != null && zxSkStockTransItemDiskFillingStorageList.size() > 0) {
           ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage = new ZxSkStockTransItemDiskFillingStorage();
           zxSkStockTransItemDiskFillingStorage.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransItemDiskFillingStorageMapper.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorageList, zxSkStockTransItemDiskFillingStorage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkStockTransItemDiskFillingStorageList);
        }
    }
}
