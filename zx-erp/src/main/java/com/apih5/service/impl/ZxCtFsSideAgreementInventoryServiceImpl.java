package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtFsSideAgreementMapper;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsSideAgreementInventoryMapper;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreementInventory;
import com.apih5.service.ZxCtFsSideAgreementInventoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxCtFsSideAgreementInventoryService")
public class ZxCtFsSideAgreementInventoryServiceImpl implements ZxCtFsSideAgreementInventoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryMapper zxCtFsSideAgreementInventoryMapper;

    @Autowired(required = true)
    private ZxCtFsSideAgreementMapper zxCtFsSideAgreementMapper;

    @Override
    public ResponseEntity getZxCtFsSideAgreementInventoryListByCondition(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        if (zxCtFsSideAgreementInventory == null) {
            zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsSideAgreementInventory.getPage(), zxCtFsSideAgreementInventory.getLimit());
        // 获取数据
        List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList = zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory);
        // 得到分页信息
        PageInfo<ZxCtFsSideAgreementInventory> p = new PageInfo<>(zxCtFsSideAgreementInventoryList);

        return repEntity.okList(zxCtFsSideAgreementInventoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsSideAgreementInventoryDetail(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        if (zxCtFsSideAgreementInventory == null) {
            zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
        }
        // 获取数据
        ZxCtFsSideAgreementInventory dbZxCtFsSideAgreementInventory = zxCtFsSideAgreementInventoryMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementInventoryId());
        // 数据存在
        if (dbZxCtFsSideAgreementInventory != null) {
            return repEntity.ok(dbZxCtFsSideAgreementInventory);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveZxCtFsSideAgreementInventory(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsSideAgreementInventory.setChangePriceNoTax(CalcUtils.calcDivide(zxCtFsSideAgreementInventory.getChangePrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtFsSideAgreementInventory.getTaxRate()), new BigDecimal("100"))),6));
        zxCtFsSideAgreementInventory.setZxCtFsSideAgreementInventoryId(UuidUtil.generate());
        zxCtFsSideAgreementInventory.setCreateUserInfo(userKey, realName);
        zxCtFsSideAgreementInventory.setChangeContractSum(CalcUtils.calcMultiply(zxCtFsSideAgreementInventory.getChangePrice(), new BigDecimal(zxCtFsSideAgreementInventory.getChangeQty())));
        zxCtFsSideAgreementInventory.setChangeContractSumNoTax(CalcUtils.calcDivide(zxCtFsSideAgreementInventory.getChangeContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtFsSideAgreementInventory.getTaxRate()), new BigDecimal("100")))));
        zxCtFsSideAgreementInventory.setChangeContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSum(), zxCtFsSideAgreementInventory.getChangeContractSumNoTax()));
        if ("2".equals(zxCtFsSideAgreementInventory.getAlterType())) {
            // zxCtFsSideAgreementInventory.setUpAlterContractSum(zxCtFsSideAgreementInventory.getChangeContractSum());
            // zxCtFsSideAgreementInventory.setUpAlterContractSumNoTax(zxCtFsSideAgreementInventory.getChangeContractSumNoTax());
            ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory1 = new ZxCtFsSideAgreementInventory();
            zxCtFsSideAgreementInventory1.setZxCtFsSideAgreementId(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
            zxCtFsSideAgreementInventory1.setConRevDetailId(zxCtFsSideAgreementInventory.getConRevDetailId());
            List<ZxCtFsSideAgreementInventory> list = zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory1);
            if (list.size() > 0) {
                return repEntity.layerMessage("no", "同补充协议中不能重复修改同一清单");
            }
        }

        int flag = updateZxCtFsSideAgreementSave(zxCtFsSideAgreementInventory);
        if (flag == 0) {
            return repEntity.layerMessage("no", "清单对应补充协议不存在或更新协议失败！");
        }
        flag = zxCtFsSideAgreementInventoryMapper.insert(zxCtFsSideAgreementInventory);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsSideAgreementInventory);
        }

    }

    @Override
    public ResponseEntity updateZxCtFsSideAgreementInventory(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxCtFsSideAgreementInventory.setChangeContractSum(CalcUtils.calcMultiply(zxCtFsSideAgreementInventory.getChangePrice(), new BigDecimal(zxCtFsSideAgreementInventory.getChangeQty())));
        zxCtFsSideAgreementInventory.setChangeContractSumNoTax(CalcUtils.calcDivide(zxCtFsSideAgreementInventory.getChangeContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtFsSideAgreementInventory.getTaxRate()), new BigDecimal("100")))));
        zxCtFsSideAgreementInventory.setChangeContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSum(), zxCtFsSideAgreementInventory.getChangeContractSumNoTax()));
        ZxCtFsSideAgreementInventory dbZxCtFsSideAgreementInventory = zxCtFsSideAgreementInventoryMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementInventoryId());

        if ("2".equals(zxCtFsSideAgreementInventory.getAlterType())) {
            // zxCtFsSideAgreementInventory.setUpAlterContractSum(zxCtFsSideAgreementInventory.getChangeContractSum());
            // zxCtFsSideAgreementInventory.setUpAlterContractSumNoTax(zxCtFsSideAgreementInventory.getChangeContractSumNoTax());
            ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory1 = new ZxCtFsSideAgreementInventory();
            zxCtFsSideAgreementInventory1.setZxCtFsSideAgreementId(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
            zxCtFsSideAgreementInventory1.setConRevDetailId(zxCtFsSideAgreementInventory.getConRevDetailId());
            List<ZxCtFsSideAgreementInventory> list = zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory1);
            if (list.size() > 0 && !list.get(0).getConRevDetailId().equals(zxCtFsSideAgreementInventory.getConRevDetailId()) ) {
                return repEntity.layerMessage("no", "同补充协议中不能重复修改同一清单");
            }
        }

        if (dbZxCtFsSideAgreementInventory != null && StrUtil.isNotEmpty(dbZxCtFsSideAgreementInventory.getZxCtFsSideAgreementInventoryId())) {

            // 补充协议ID
            dbZxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
            // 清单编号
            dbZxCtFsSideAgreementInventory.setWorkNo(zxCtFsSideAgreementInventory.getWorkNo());
            // 清单名称
            dbZxCtFsSideAgreementInventory.setWorkName(zxCtFsSideAgreementInventory.getWorkName());
            // 规格型号
            dbZxCtFsSideAgreementInventory.setSpec(zxCtFsSideAgreementInventory.getSpec());
            // 单位
            dbZxCtFsSideAgreementInventory.setUnit(zxCtFsSideAgreementInventory.getUnit());
            // 物资类别
            dbZxCtFsSideAgreementInventory.setWorkType(zxCtFsSideAgreementInventory.getWorkType());
            // 单位
            dbZxCtFsSideAgreementInventory.setTreenode(zxCtFsSideAgreementInventory.getTreenode());

            // 含税单价
            dbZxCtFsSideAgreementInventory.setPrice(zxCtFsSideAgreementInventory.getPrice());
            // 含税金额
            dbZxCtFsSideAgreementInventory.setContractSum(zxCtFsSideAgreementInventory.getContractSum());
            // 界面展现类型
            dbZxCtFsSideAgreementInventory.setViewType(zxCtFsSideAgreementInventory.getViewType());
            // 计划开始时间
            dbZxCtFsSideAgreementInventory.setPlanStartDate(zxCtFsSideAgreementInventory.getPlanStartDate());
            // 计划结束时间
            dbZxCtFsSideAgreementInventory.setPlanEndDate(zxCtFsSideAgreementInventory.getPlanEndDate());
            // 实际开始时间
            dbZxCtFsSideAgreementInventory.setActualStartDate(zxCtFsSideAgreementInventory.getActualStartDate());
            // 实际结束时间
            dbZxCtFsSideAgreementInventory.setActualEndDate(zxCtFsSideAgreementInventory.getActualEndDate());
            // 物资类别ID
            dbZxCtFsSideAgreementInventory.setWorkTypeID(zxCtFsSideAgreementInventory.getWorkTypeID());
            // 物资编码ID
            dbZxCtFsSideAgreementInventory.setWorkID(zxCtFsSideAgreementInventory.getWorkID());
            // 变更后数量
            dbZxCtFsSideAgreementInventory.setChangeQty(zxCtFsSideAgreementInventory.getChangeQty());
            // 变更后单价
            dbZxCtFsSideAgreementInventory.setChangePrice(zxCtFsSideAgreementInventory.getChangePrice());

            // 合同明细ID
            dbZxCtFsSideAgreementInventory.setConRevDetailId(zxCtFsSideAgreementInventory.getConRevDetailId());
            // 变更日期
            dbZxCtFsSideAgreementInventory.setChangeDate(zxCtFsSideAgreementInventory.getChangeDate());
            // 租期单位
            dbZxCtFsSideAgreementInventory.setRentUnit(zxCtFsSideAgreementInventory.getRentUnit());
            // 变更后租期
            dbZxCtFsSideAgreementInventory.setAlterTrrm(zxCtFsSideAgreementInventory.getAlterTrrm());
            // 租期
            dbZxCtFsSideAgreementInventory.setContrTrrm(zxCtFsSideAgreementInventory.getContrTrrm());
            // 变更类型
            dbZxCtFsSideAgreementInventory.setAlterType(zxCtFsSideAgreementInventory.getAlterType());
            // 上期末变更后金额
            dbZxCtFsSideAgreementInventory.setUpAlterContractSum(zxCtFsSideAgreementInventory.getUpAlterContractSum());
            // 不含税单价
            dbZxCtFsSideAgreementInventory.setPriceNoTax(zxCtFsSideAgreementInventory.getPriceNoTax());
            // 不含税金额
            dbZxCtFsSideAgreementInventory.setContractSumNoTax(zxCtFsSideAgreementInventory.getContractSumNoTax());

            // 变更后不含税单价
            dbZxCtFsSideAgreementInventory.setChangePriceNoTax(zxCtFsSideAgreementInventory.getChangePriceNoTax());
            // 税率(%)
            dbZxCtFsSideAgreementInventory.setTaxRate(zxCtFsSideAgreementInventory.getTaxRate());
            // 上期末变更后金额不含税
            dbZxCtFsSideAgreementInventory.setUpAlterContractSumNoTax(zxCtFsSideAgreementInventory.getUpAlterContractSumNoTax());
            // 不含税税额
            dbZxCtFsSideAgreementInventory.setContractSumTax(zxCtFsSideAgreementInventory.getContractSumTax());
            // 变更后税额
            dbZxCtFsSideAgreementInventory.setChangeContractSumTax(zxCtFsSideAgreementInventory.getChangeContractSumTax());
            // 上期末变更后税额
            dbZxCtFsSideAgreementInventory.setUpAlterContractSumTax(zxCtFsSideAgreementInventory.getUpAlterContractSumTax());
            // 备注
            dbZxCtFsSideAgreementInventory.setRemarks(zxCtFsSideAgreementInventory.getRemarks());
            // 排序
            dbZxCtFsSideAgreementInventory.setSort(zxCtFsSideAgreementInventory.getSort());
            // 共通
            dbZxCtFsSideAgreementInventory.setModifyUserInfo(userKey, realName);
            dbZxCtFsSideAgreementInventory.setChangeContractSum(zxCtFsSideAgreementInventory.getChangeContractSum());
            // 变更后不含税金额
            dbZxCtFsSideAgreementInventory.setChangeContractSumNoTax(zxCtFsSideAgreementInventory.getChangeContractSumNoTax());


            flag = zxCtFsSideAgreementInventoryMapper.updateByPrimaryKey(dbZxCtFsSideAgreementInventory);
        }
        // 变更后含税金额

        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            flag = updateZxCtFsSideAgreement(zxCtFsSideAgreementInventory);
            if (flag == 0) {
                return repEntity.layerMessage("no", "清单对应补充协议不存在或更新协议失败！");
            }
            return repEntity.ok("sys.data.update", zxCtFsSideAgreementInventory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreementInventory(List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsSideAgreementInventoryList != null && zxCtFsSideAgreementInventoryList.size() > 0) {
            ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
            zxCtFsSideAgreementInventory.setModifyUserInfo(userKey, realName);
            for (ZxCtFsSideAgreementInventory zxai : zxCtFsSideAgreementInventoryList) {
                zxai = zxCtFsSideAgreementInventoryMapper.selectByPrimaryKey(zxai.getZxCtFsSideAgreementInventoryId());
                flag = updateZxCtFsSideAgreementDelete(zxai);
                if (flag == 0) {
                    return repEntity.layerMessage("no", "清单对应补充协议不存在或更新协议失败！");
                }
            }

            flag = zxCtFsSideAgreementInventoryMapper.batchDeleteUpdateZxCtFsSideAgreementInventory(zxCtFsSideAgreementInventoryList, zxCtFsSideAgreementInventory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsSideAgreementInventoryList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 批量更新附属合同补充协议清单
     *
     * @param zxCtFsSideAgreementInventoryList
     * @auther suncg
     */
    @Override
    public ResponseEntity batchUpdateOrAdd(List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
        if (zxCtFsSideAgreementInventoryList != null && zxCtFsSideAgreementInventoryList.size() > 0) {
            for (ZxCtFsSideAgreementInventory zxInventory : zxCtFsSideAgreementInventoryList
            ) {
                zxCtFsSideAgreementInventory = zxCtFsSideAgreementInventoryMapper.selectByPrimaryKey(zxInventory.getZxCtFsSideAgreementInventoryId());
                if (zxCtFsSideAgreementInventory == null) {
                    flag = zxCtFsSideAgreementInventoryMapper.insert(zxInventory);
                    if (flag == 0) {
                        return repEntity.errorSave();
                    }
                } else {
                    zxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(zxInventory.getZxCtFsSideAgreementId());
                    // 清单编号
                    zxCtFsSideAgreementInventory.setWorkNo(zxInventory.getWorkNo());
                    // 清单名称
                    zxCtFsSideAgreementInventory.setWorkName(zxInventory.getWorkName());
                    // 规格型号
                    zxCtFsSideAgreementInventory.setSpec(zxInventory.getSpec());
                    // 单位
                    zxCtFsSideAgreementInventory.setUnit(zxInventory.getUnit());
                    // 物资类别
                    zxCtFsSideAgreementInventory.setWorkType(zxInventory.getWorkType());
                    // 单位
                    zxCtFsSideAgreementInventory.setTreenode(zxInventory.getTreenode());
                    // 数量
                    zxCtFsSideAgreementInventory.setQty(zxInventory.getQty());
                    // 含税单价
                    zxCtFsSideAgreementInventory.setPrice(zxInventory.getPrice());
                    // 含税金额
                    zxCtFsSideAgreementInventory.setContractSum(zxInventory.getContractSum());
                    // 界面展现类型
                    zxCtFsSideAgreementInventory.setViewType(zxInventory.getViewType());
                    // 计划开始时间
                    zxCtFsSideAgreementInventory.setPlanStartDate(zxInventory.getPlanStartDate());
                    // 计划结束时间
                    zxCtFsSideAgreementInventory.setPlanEndDate(zxInventory.getPlanEndDate());
                    // 实际开始时间
                    zxCtFsSideAgreementInventory.setActualStartDate(zxInventory.getActualStartDate());
                    // 实际结束时间
                    zxCtFsSideAgreementInventory.setActualEndDate(zxInventory.getActualEndDate());
                    // 物资类别ID
                    zxCtFsSideAgreementInventory.setWorkTypeID(zxInventory.getWorkTypeID());
                    // 物资编码ID
                    zxCtFsSideAgreementInventory.setWorkID(zxInventory.getWorkID());
                    // 变更后数量
                    zxCtFsSideAgreementInventory.setChangeQty(zxInventory.getChangeQty());
                    // 变更后单价
                    zxCtFsSideAgreementInventory.setChangePrice(zxInventory.getChangePrice());
                    // 变更后含税金额
                    zxCtFsSideAgreementInventory.setChangeContractSum(zxInventory.getChangeContractSum());
                    // 合同明细ID
                    zxCtFsSideAgreementInventory.setConRevDetailId(zxInventory.getConRevDetailId());
                    // 变更日期
                    zxCtFsSideAgreementInventory.setChangeDate(zxInventory.getChangeDate());
                    // 租期单位
                    zxCtFsSideAgreementInventory.setRentUnit(zxInventory.getRentUnit());
                    // 变更后租期
                    zxCtFsSideAgreementInventory.setAlterTrrm(zxInventory.getAlterTrrm());
                    // 租期
                    zxCtFsSideAgreementInventory.setContrTrrm(zxInventory.getContrTrrm());
                    // 变更类型
                    zxCtFsSideAgreementInventory.setAlterType(zxInventory.getAlterType());
                    // 上期末变更后金额
                    zxCtFsSideAgreementInventory.setUpAlterContractSum(zxInventory.getUpAlterContractSum());
                    // 不含税单价
                    zxCtFsSideAgreementInventory.setPriceNoTax(zxInventory.getPriceNoTax());
                    // 不含税金额
                    zxCtFsSideAgreementInventory.setContractSumNoTax(zxInventory.getContractSumNoTax());
                    // 变更后不含税金额
                    zxCtFsSideAgreementInventory.setChangeContractSumNoTax(zxInventory.getChangeContractSumNoTax());
                    // 变更后不含税单价
                    zxCtFsSideAgreementInventory.setChangePriceNoTax(zxInventory.getChangePriceNoTax());
                    // 税率(%)
                    zxCtFsSideAgreementInventory.setTaxRate(zxInventory.getTaxRate());
                    // 上期末变更后金额不含税
                    zxCtFsSideAgreementInventory.setUpAlterContractSumNoTax(zxInventory.getUpAlterContractSumNoTax());
                    // 不含税税额
                    zxCtFsSideAgreementInventory.setContractSumTax(zxInventory.getContractSumTax());
                    // 变更后税额
                    zxCtFsSideAgreementInventory.setChangeContractSumTax(zxInventory.getChangeContractSumTax());
                    // 上期末变更后税额
                    zxCtFsSideAgreementInventory.setUpAlterContractSumTax(zxInventory.getUpAlterContractSumTax());
                    // 备注
                    zxCtFsSideAgreementInventory.setRemarks(zxInventory.getRemarks());
                    // 排序
                    zxCtFsSideAgreementInventory.setSort(zxInventory.getSort());
                    // 共通
                    zxCtFsSideAgreementInventory.setModifyUserInfo(userKey, realName);
                }
                flag = zxCtFsSideAgreementInventoryMapper.updateByPrimaryKey(zxCtFsSideAgreementInventory);
                if (flag == 0) {
                    return repEntity.errorUpdate();
                }
            }
            return repEntity.ok("sys.data.update", zxCtFsSideAgreementInventoryList);
        }
        // 失败
        else {
            return repEntity.layerMessage("no", "无更新数据！");
        }
    }


    /**
     * 在新增清单，税额、含税金额、不含税金额发生变化时，同步改变对应附属合同补充协议中的金额
     *
     * @param zxCtFsSideAgreementInventory
     * @author suncg
     */
    private int updateZxCtFsSideAgreementSave(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {

        int flag = 0;
        ZxCtFsSideAgreement zxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
        if (zxCtFsSideAgreement != null) {
//          contractCost  含税合同金额(万元) alterContractSum变更后含税金额
//             pp4 本期含税增减金额(万元) contractCostNoTax;不含税合同金额(万元)
//             alterContractSumNoTax;  变更后不含税金额  alterContractSumTax变更后税额
//            pp4NoTax 本期不含税增减金额(万元) pp4Tax 本期增减税额(万元) contractCostTax合同税额(万元)
            BigDecimal wan = new BigDecimal("10000");
            BigDecimal changeAmt = new BigDecimal("0");
            BigDecimal changeAmtNoTax = new BigDecimal("0");

            BigDecimal cost = zxCtFsSideAgreement.getUpAlterContractSum() == null ? zxCtFsSideAgreement.getContractCost() : zxCtFsSideAgreement.getUpAlterContractSum();
            BigDecimal costNoTax = zxCtFsSideAgreement.getUpAlterContractSumNoTax() == null ? zxCtFsSideAgreement.getContractCostNoTax() : zxCtFsSideAgreement.getUpAlterContractSumNoTax();

            if (zxCtFsSideAgreement.getPp4() != null) {
                changeAmt = zxCtFsSideAgreement.getPp4();
            }
            if (zxCtFsSideAgreement.getPp4NoTax() != null) {
                changeAmtNoTax = zxCtFsSideAgreement.getPp4NoTax();
            }

            if ("1".equals(zxCtFsSideAgreementInventory.getAlterType())) {
                zxCtFsSideAgreement.setPp4(CalcUtils.calcAdd(changeAmt, CalcUtils.calcDivide(zxCtFsSideAgreementInventory.getChangeContractSum(), wan, 6)));
                zxCtFsSideAgreement.setAlterContractSum(CalcUtils.calcAdd(cost, zxCtFsSideAgreement.getPp4()));
                zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcAdd(changeAmtNoTax, CalcUtils.calcDivide(zxCtFsSideAgreementInventory.getChangeContractSumNoTax(), wan, 6)));
                zxCtFsSideAgreement.setAlterContractSumNoTax(CalcUtils.calcAdd(costNoTax, zxCtFsSideAgreement.getPp4NoTax()));
                zxCtFsSideAgreement.setPp4Tax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(), zxCtFsSideAgreement.getPp4NoTax()));
                zxCtFsSideAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getAlterContractSum(), zxCtFsSideAgreement.getAlterContractSumNoTax()));
            }
            if ("2".equals(zxCtFsSideAgreementInventory.getAlterType())) {

                BigDecimal upAmt = zxCtFsSideAgreementInventory.getUpAlterContractSum() == null ? zxCtFsSideAgreementInventory.getContractSum() : zxCtFsSideAgreementInventory.getUpAlterContractSum();
                BigDecimal upAmtNoTax = zxCtFsSideAgreementInventory.getUpAlterContractSumNoTax() == null ? zxCtFsSideAgreementInventory.getContractSumNoTax() : zxCtFsSideAgreementInventory.getUpAlterContractSumNoTax();
                zxCtFsSideAgreement.setPp4(CalcUtils.calcAdd(changeAmt, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSum(), upAmt), new BigDecimal("10000"), 6)));
                zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcAdd(changeAmtNoTax, CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSumNoTax(), upAmtNoTax), new BigDecimal("10000"), 6)));
                zxCtFsSideAgreement.setPp4Tax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(), zxCtFsSideAgreement.getPp4NoTax()));
                zxCtFsSideAgreement.setAlterContractSum(CalcUtils.calcAdd(cost, zxCtFsSideAgreement.getPp4()));
                zxCtFsSideAgreement.setAlterContractSumNoTax(CalcUtils.calcAdd(costNoTax, zxCtFsSideAgreement.getPp4NoTax()));
                zxCtFsSideAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getAlterContractSum(), zxCtFsSideAgreement.getAlterContractSumNoTax()));
            }
            flag = zxCtFsSideAgreementMapper.updateByPrimaryKey(zxCtFsSideAgreement);
            return flag;
        } else {
            return 0;
        }
    }

    /**
     * 在修改清单，税额、含税金额、不含税金额发生变化时，同步改变对应附属合同补充协议中的金额
     *
     * @param zxCtFsSideAgreementInventory
     * @author suncg
     */
    private int updateZxCtFsSideAgreement(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {

        ZxCtFsSideAgreement zxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
        ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory1 = new ZxCtFsSideAgreementInventory();
        zxCtFsSideAgreementInventory1.setZxCtFsSideAgreementId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        zxCtFsSideAgreementInventory1 = zxCtFsSideAgreementInventoryMapper.selectSumAmt(zxCtFsSideAgreementInventory1);

        int flag = 0;
        if (zxCtFsSideAgreement != null) {
            BigDecimal wan = new BigDecimal("10000");
            BigDecimal upAmt = new BigDecimal("0");
            BigDecimal upNoTax = new BigDecimal("0");

            BigDecimal cost = zxCtFsSideAgreement.getUpAlterContractSum() == null ? zxCtFsSideAgreement.getContractCost() : zxCtFsSideAgreement.getUpAlterContractSum();
            BigDecimal costNoTax = zxCtFsSideAgreement.getUpAlterContractSumNoTax() == null ? zxCtFsSideAgreement.getContractCostNoTax() : zxCtFsSideAgreement.getUpAlterContractSumNoTax();


            if (zxCtFsSideAgreementInventory1.getUpAlterContractSum() != null) {
                upAmt = zxCtFsSideAgreementInventory1.getUpAlterContractSum();
            }
            if (zxCtFsSideAgreementInventory1.getUpAlterContractSumNoTax() != null) {
                upNoTax = zxCtFsSideAgreementInventory1.getUpAlterContractSumNoTax();
            }


            // contractCost // 含税合同金额(万元) alterContractSum// 变更后含税金额
            // pp4// 本期含税增减金额(万元) contractCostNoTax;// 不含税合同金额(万元)
            // alterContractSumNoTax;  // 变更后不含税金额  alterContractSumTax// 变更后税额
            // pp4NoTax; // 本期不含税增减金额(万元) pp4Tax // 本期增减税额(万元)
            // contractCostTax// 合同税额(万元)
            zxCtFsSideAgreement.setPp4(CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory1.getChangeContractSum(), upAmt), wan, 2));
            zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory1.getChangeContractSumNoTax(), upNoTax), wan, 2));
            //zxCtFsSideAgreement.setPp4( CalcUtils.calcAdd(changeAmt,CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSum(),zxCtFsSideAgreementInventory1.getChangeContractSum()),new BigDecimal("10000"),6)) );
            // zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcAdd(changeAmtNoTax,CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSumNoTax(),zxCtFsSideAgreementInventory1.getChangeContractSumNoTax()),new BigDecimal("10000"),6)) );
            zxCtFsSideAgreement.setPp4Tax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(), zxCtFsSideAgreement.getPp4NoTax()));
            zxCtFsSideAgreement.setAlterContractSum(CalcUtils.calcAdd(zxCtFsSideAgreement.getPp4(), cost));
            zxCtFsSideAgreement.setAlterContractSumNoTax(CalcUtils.calcAdd(zxCtFsSideAgreement.getPp4NoTax(), costNoTax));
            zxCtFsSideAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getAlterContractSum(), zxCtFsSideAgreement.getAlterContractSumNoTax()));
            flag = zxCtFsSideAgreementMapper.updateByPrimaryKey(zxCtFsSideAgreement);
            return flag;
        } else {
            return 0;
        }
    }

    /**
     * 在删除清单时，同步改变对应附属合同补充协议中的金额
     *
     * @param zxCtFsSideAgreementInventory
     * @author suncg
     */
    private int updateZxCtFsSideAgreementDelete(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory) {

        ZxCtFsSideAgreement zxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
        if (zxCtFsSideAgreement != null) {
            BigDecimal wan = new BigDecimal("10000");

            BigDecimal cost = zxCtFsSideAgreement.getUpAlterContractSum() == null ? zxCtFsSideAgreement.getContractCost() : zxCtFsSideAgreement.getUpAlterContractSum();
            BigDecimal costNoTax = zxCtFsSideAgreement.getUpAlterContractSumNoTax() == null ? zxCtFsSideAgreement.getContractCostNoTax() : zxCtFsSideAgreement.getUpAlterContractSumNoTax();

            // contractCost // 含税合同金额(万元) alterContractSum// 变更后含税金额
            // pp4// 本期含税增减金额(万元) contractCostNoTax;// 不含税合同金额(万元)
            // alterContractSumNoTax;  // 变更后不含税金额  alterContractSumTax// 变更后税额
            // pp4NoTax; // 本期不含税增减金额(万元) pp4Tax // 本期增减税额(万元)
            // contractCostTax// 合同税额(万元)
            BigDecimal upAmt = zxCtFsSideAgreementInventory.getUpAlterContractSum() == null ? zxCtFsSideAgreementInventory.getContractSum() : zxCtFsSideAgreementInventory.getUpAlterContractSum();
            BigDecimal upAmtNoTax = zxCtFsSideAgreementInventory.getUpAlterContractSumNoTax() == null ? zxCtFsSideAgreementInventory.getContractSumNoTax() : zxCtFsSideAgreementInventory.getUpAlterContractSumNoTax();
            zxCtFsSideAgreement.setPp4(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSum(), upAmt), wan, 6)));
            zxCtFsSideAgreement.setAlterContractSum(CalcUtils.calcAdd(cost, zxCtFsSideAgreement.getPp4()));
            zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4NoTax(), CalcUtils.calcDivide(CalcUtils.calcSubtract(zxCtFsSideAgreementInventory.getChangeContractSumNoTax(), upAmtNoTax), wan, 6)));
            zxCtFsSideAgreement.setAlterContractSumNoTax(CalcUtils.calcAdd(costNoTax, zxCtFsSideAgreement.getPp4NoTax()));
            zxCtFsSideAgreement.setPp4Tax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(), zxCtFsSideAgreement.getPp4NoTax()));
            zxCtFsSideAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getAlterContractSum(), zxCtFsSideAgreement.getAlterContractSumNoTax()));
            return zxCtFsSideAgreementMapper.updateByPrimaryKey(zxCtFsSideAgreement);
        } else {
            return 0;
        }
    }

//    public int synSideAgreement(ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory){
//        ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory1 =new ZxCtFsSideAgreementInventory();
//        zxCtFsSideAgreementInventory1.setZxCtFsSideAgreementId(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
//        ZxCtFsSideAgreementInventory  AgreementInventory=zxCtFsSideAgreementInventoryMapper.selectSumAmt(zxCtFsSideAgreementInventory1);
//        ZxCtFsSideAgreement zxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreementInventory.getZxCtFsSideAgreementId());
//        zxCtFsSideAgreement.setPp4(CalcUtils.calcSubtract(AgreementInventory.getChangeContractSum(),zxCtFsSideAgreement.getUpAlterContractSum()));
//        zxCtFsSideAgreement.setPp4NoTax(CalcUtils.calcSubtract(AgreementInventory.getChangeContractSumNoTax(),zxCtFsSideAgreement.getUpAlterContractSumNoTax()));
//        zxCtFsSideAgreement.setPp4Tax(CalcUtils.calcSubtract(zxCtFsSideAgreement.getPp4(),zxCtFsSideAgreement.getPp4NoTax()));
//        zxCtFsSideAgreement.setAlterContractSum(AgreementInventory.getChangeContractSum());
//        zxCtFsSideAgreement.setAlterContractSumNoTax(AgreementInventory.getUpAlterContractSumNoTax());
//        zxCtFsSideAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(AgreementInventory.getChangeContractSum(),AgreementInventory.getChangeContractSumNoTax()));
//        return zxCtFsSideAgreementMapper.updateByPrimaryKey(zxCtFsSideAgreement);
//    }

}
