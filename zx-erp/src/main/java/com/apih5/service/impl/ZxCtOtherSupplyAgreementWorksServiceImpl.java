package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxCtOtherSupplyAgreementMapper;
import com.apih5.mybatis.dao.ZxCtOtherWorksMapper;
import com.apih5.mybatis.pojo.ZxCtOther;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreement;
import com.apih5.mybatis.pojo.ZxCtOtherWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtOtherSupplyAgreementWorksMapper;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreementWorks;
import com.apih5.service.ZxCtOtherSupplyAgreementWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service("zxCtOtherSupplyAgreementWorksService")
public class ZxCtOtherSupplyAgreementWorksServiceImpl implements ZxCtOtherSupplyAgreementWorksService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementWorksMapper zxCtOtherSupplyAgreementWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementMapper zxCtOtherSupplyAgreementMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementWorksListByCondition(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        if (zxCtOtherSupplyAgreementWorks == null) {
            zxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
        }
        // 分页查询
        PageHelper.startPage(zxCtOtherSupplyAgreementWorks.getPage(),zxCtOtherSupplyAgreementWorks.getLimit());
        // 获取数据
        List<ZxCtOtherSupplyAgreementWorks> zxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(zxCtOtherSupplyAgreementWorks);
        // 得到分页信息
        PageInfo<ZxCtOtherSupplyAgreementWorks> p = new PageInfo<>(zxCtOtherSupplyAgreementWorksList);

        return repEntity.okList(zxCtOtherSupplyAgreementWorksList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementWorksDetail(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        if (zxCtOtherSupplyAgreementWorks == null) {
            zxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
        }
        // 获取数据
        ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = zxCtOtherSupplyAgreementWorksMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementWorksId());
        // 数据存在
        if (dbZxCtOtherSupplyAgreementWorks != null) {
            return repEntity.ok(dbZxCtOtherSupplyAgreementWorks);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherSupplyAgreementWorks(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 其他合同补充协议id不能为空
        if (StrUtil.isEmpty(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementId())) {
            return repEntity.layerMessage("no", "其他合同补充协议ID不能为空！");
        }
        // 变更类型为修改时
        if("2".equals(zxCtOtherSupplyAgreementWorks.getAlterType())){
            // 合同管理明细表id不能为空
            if (StrUtil.isEmpty(zxCtOtherSupplyAgreementWorks.getZxCtOtherWorksId())) {
                return repEntity.layerMessage("no", "合同管理明细表ID不能为空！");
            } else {
                ZxCtOtherSupplyAgreementWorks dbWorks = new ZxCtOtherSupplyAgreementWorks();
                dbWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementId());
                dbWorks.setZxCtOtherWorksId(zxCtOtherSupplyAgreementWorks.getZxCtOtherWorksId());
                List<ZxCtOtherSupplyAgreementWorks> dbWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbWorks);
                if (dbWorksList != null && dbWorksList.size() > 0) {
                    return repEntity.layerMessage("no", "数据已存在，不能重复添加！");
                }
            }
            // 其他合同明细
            ZxCtOtherWorks otherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks.getZxCtOtherWorksId());
            // 上期末变更后金额
            zxCtOtherSupplyAgreementWorks.setUpAlterContractSum(otherWorks.getChangeContractSum());
            // 上期末变更后金额不含税
            zxCtOtherSupplyAgreementWorks.setUpAlterContractSumNoTax(otherWorks.getChangeContractSumNoTax());
            // 上期末变更后税额
            zxCtOtherSupplyAgreementWorks.setUpAlterContractSumTax(otherWorks.getChangeContractSumTax());
            // 税率
            BigDecimal rate = CalcUtils.calcDivide(new BigDecimal(zxCtOtherSupplyAgreementWorks.getTaxRate()), new BigDecimal(100), 6);
            // 变更后不含税单价
            zxCtOtherSupplyAgreementWorks.setChangePriceNoTax(CalcUtils.calcDivide(zxCtOtherSupplyAgreementWorks.getChangePrice(), CalcUtils.calcAdd(BigDecimal.ONE, rate), 6));
            // 变更类型为新增时
        } else {
            // 变更后不含税单价 变更后含税单价 / （1 + 税率百分比）
            BigDecimal ChangePriceNoTax = CalcUtils.calcDivide(zxCtOtherSupplyAgreementWorks.getChangePrice(),CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils.calcDivide(new BigDecimal(zxCtOtherSupplyAgreementWorks.getTaxRate()), new BigDecimal(100))), 6);
            zxCtOtherSupplyAgreementWorks.setChangePriceNoTax(ChangePriceNoTax);
            // 数量
            zxCtOtherSupplyAgreementWorks.setQty(null);
            // 含税单价
            zxCtOtherSupplyAgreementWorks.setPrice(null);
            // 不含税单价
            zxCtOtherSupplyAgreementWorks.setPriceNoTax(null);
            // 含税金额
            zxCtOtherSupplyAgreementWorks.setContractSum(null);
            // 不含税金额
            zxCtOtherSupplyAgreementWorks.setContractSumNoTax(null);
            // 税额
            zxCtOtherSupplyAgreementWorks.setContractSumTax(null);
        }
        zxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementId());
        zxCtOtherSupplyAgreementWorks.setZxCtOtherWorksId(zxCtOtherSupplyAgreementWorks.getZxCtOtherWorksId());
        zxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementWorksId(UuidUtil.generate());
        zxCtOtherSupplyAgreementWorks.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherSupplyAgreementWorksMapper.insert(zxCtOtherSupplyAgreementWorks);
        // 添加其他合同补充协议明细表数据后，更新主表数据
        this.sumZxCtOtherSupplyAgreementWorksAmount(zxCtOtherSupplyAgreementWorks);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherSupplyAgreementWorks);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherSupplyAgreementWorks(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = zxCtOtherSupplyAgreementWorksMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementWorksId());
        if (dbZxCtOtherSupplyAgreementWorks != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementWorksId())) {
            if("1".equals(zxCtOtherSupplyAgreementWorks.getAlterType())){
                BigDecimal zero = new BigDecimal(0);
                // 数量
                zxCtOtherSupplyAgreementWorks.setQty(null);
                // 含税单价
                zxCtOtherSupplyAgreementWorks.setPrice(null);
                // 不含税单价
                zxCtOtherSupplyAgreementWorks.setPriceNoTax(null);
                // 含税金额
                zxCtOtherSupplyAgreementWorks.setContractSum(null);
                // 不含税金额
                zxCtOtherSupplyAgreementWorks.setContractSumNoTax(null);
                // 税额
                zxCtOtherSupplyAgreementWorks.setContractSumTax(null);
                // 变更后单价
                dbZxCtOtherSupplyAgreementWorks.setChangePrice(zxCtOtherSupplyAgreementWorks.getChangePrice());
                // 变更后不含税单价
                dbZxCtOtherSupplyAgreementWorks.setChangePriceNoTax(zxCtOtherSupplyAgreementWorks.getChangePriceNoTax());
                // 税率(%)
                dbZxCtOtherSupplyAgreementWorks.setTaxRate(zxCtOtherSupplyAgreementWorks.getTaxRate());
                dbZxCtOtherSupplyAgreementWorks.setZxCtOtherWorksId("");
            }
            // 单位
            dbZxCtOtherSupplyAgreementWorks.setUnit(zxCtOtherSupplyAgreementWorks.getUnit());
            // 清单编号
            dbZxCtOtherSupplyAgreementWorks.setWorkNo(zxCtOtherSupplyAgreementWorks.getWorkNo());
            // 清单名称
            dbZxCtOtherSupplyAgreementWorks.setWorkName(zxCtOtherSupplyAgreementWorks.getWorkName());
            // 变更后数量
            dbZxCtOtherSupplyAgreementWorks.setChangeQty(zxCtOtherSupplyAgreementWorks.getChangeQty());
            // 变更后含税金额
            dbZxCtOtherSupplyAgreementWorks.setChangeContractSum(zxCtOtherSupplyAgreementWorks.getChangeContractSum());
            // 变更后不含税金额
            dbZxCtOtherSupplyAgreementWorks.setChangeContractSumNoTax(zxCtOtherSupplyAgreementWorks.getChangeContractSumNoTax());
            // 变更后税额
            dbZxCtOtherSupplyAgreementWorks.setChangeContractSumTax(zxCtOtherSupplyAgreementWorks.getChangeContractSumTax());
            // 变更类型
            dbZxCtOtherSupplyAgreementWorks.setAlterType(zxCtOtherSupplyAgreementWorks.getAlterType());
            // 备注
            dbZxCtOtherSupplyAgreementWorks.setRemark(zxCtOtherSupplyAgreementWorks.getRemark());
            // 共通
            dbZxCtOtherSupplyAgreementWorks.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherSupplyAgreementWorksMapper.updateByPrimaryKey(dbZxCtOtherSupplyAgreementWorks);
            // 更新其他合同补充协议明细表数据后，更新主表数据
            this.sumZxCtOtherSupplyAgreementWorksAmount(dbZxCtOtherSupplyAgreementWorks);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherSupplyAgreementWorks);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreementWorks(List<ZxCtOtherSupplyAgreementWorks> zxCtOtherSupplyAgreementWorksList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherSupplyAgreementWorksList != null && zxCtOtherSupplyAgreementWorksList.size() > 0) {
           ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
           zxCtOtherSupplyAgreementWorks.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherSupplyAgreementWorksMapper.batchDeleteUpdateZxCtOtherSupplyAgreementWorks(zxCtOtherSupplyAgreementWorksList, zxCtOtherSupplyAgreementWorks);
           // 删除其他合同补充协议明细表数据后，更新主表数据
           for (ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks1 : zxCtOtherSupplyAgreementWorksList) {
              this.sumZxCtOtherSupplyAgreementWorksAmount(zxCtOtherSupplyAgreementWorks1);
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherSupplyAgreementWorksList);
        }
    }

    /**
     * 其他类合同补充协议清单明细汇总金额
     *
     * @param zxCtOtherSupplyAgreementWorks 默认
     *
     * */
    private void sumZxCtOtherSupplyAgreementWorksAmount(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
        dbZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementId());
        // 查询当前其他合同补充协议zxCtOtherSupplyAgreementId在明细表中的数据
        List<ZxCtOtherSupplyAgreementWorks> zxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbZxCtOtherSupplyAgreementWorks);
        // 查询其他合同补充协议主表数据
        ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks.getZxCtOtherSupplyAgreementId());
        BigDecimal zero = new BigDecimal(0);
        BigDecimal tenThousand = new BigDecimal(10000);
        if (!CollectionUtils.isEmpty(zxCtOtherSupplyAgreementWorksList)) {
            // 其他合同明细
            ZxCtOtherWorks query = new ZxCtOtherWorks();
            query.setZxCtOtherManageId(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
            List<ZxCtOtherWorks> otherWorks = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(query);
            BigDecimal totalAlterContractSum = null;
            BigDecimal totalAlterContractSumNoTax = null;
            BigDecimal totalAlterContractSumTax = null;
            // 循环原合同明细  与当前补充协议对比
            for(ZxCtOtherWorks zxCtOtherWorks : otherWorks) {
                Optional<ZxCtOtherSupplyAgreementWorks> optional = zxCtOtherSupplyAgreementWorksList.stream().filter(a -> a.getZxCtOtherWorksId().equals(zxCtOtherWorks.getZxCtOtherWorksId())).findAny();
                // 有修改明细
                if (optional.isPresent()) {
                    totalAlterContractSum = CalcUtils.calcAdd(totalAlterContractSum, optional.get().getChangeContractSum());
                    totalAlterContractSumNoTax = CalcUtils.calcAdd(totalAlterContractSumNoTax, optional.get().getChangeContractSumNoTax());
                    totalAlterContractSumTax = CalcUtils.calcAdd(totalAlterContractSumTax, optional.get().getChangeContractSumTax());
                } else {
                    if (zxCtOtherWorks.getChangeQty() != null && zxCtOtherWorks.getChangeQty().compareTo(zero) > 0) {
                        // 之前有补充协议更改过 这次补充没有更改
                        totalAlterContractSum = CalcUtils.calcAdd(totalAlterContractSum, zxCtOtherWorks.getChangeContractSum());
                        totalAlterContractSumNoTax = CalcUtils.calcAdd(totalAlterContractSumNoTax, zxCtOtherWorks.getChangeContractSumNoTax());
                        totalAlterContractSumTax = CalcUtils.calcAdd(totalAlterContractSumTax, zxCtOtherWorks.getChangeContractSumTax());
                    } else {
                        // 该明细一直没有更改
                        totalAlterContractSum = CalcUtils.calcAdd(totalAlterContractSum, zxCtOtherWorks.getContractSum());
                        totalAlterContractSumNoTax = CalcUtils.calcAdd(totalAlterContractSumNoTax, zxCtOtherWorks.getContractSumNoTax());
                        totalAlterContractSumTax = CalcUtils.calcAdd(totalAlterContractSumTax, zxCtOtherWorks.getContractSumTax());
                    }
                }
            }
            // 筛选出本次协议新增明细  汇总明细金额
            List<ZxCtOtherSupplyAgreementWorks> addOtherWorks = zxCtOtherSupplyAgreementWorksList.stream().filter(a -> "1".equals(a.getAlterType())).collect(Collectors.toList());
            if (addOtherWorks.size() > 0) {
                for (ZxCtOtherSupplyAgreementWorks agreementWorks : addOtherWorks) {
                    // 清单明细变更后含税金额汇总
                    totalAlterContractSum = CalcUtils.calcAdd(totalAlterContractSum, agreementWorks.getChangeContractSum());
                    // 清单明细变更后不含税金额汇总
                    totalAlterContractSumNoTax = CalcUtils.calcAdd(totalAlterContractSumNoTax, agreementWorks.getChangeContractSumNoTax());
                    // 清单明细变更后税额汇总
                    totalAlterContractSumTax = CalcUtils.calcAdd(totalAlterContractSumTax, agreementWorks.getChangeContractSumTax());
                }
            }
            // 其他合同补充协议主表的变更后含税合同金额（万元）
            BigDecimal alterContractSum = CalcUtils.calcDivide(totalAlterContractSum,tenThousand,6);
            zxCtOtherSupplyAgreement.setAlterContractSum(alterContractSum);
            // 其他合同补充协议主表的变更后不含税合同金额（万元）
            BigDecimal alterContractSumNoTax = CalcUtils.calcDivide(totalAlterContractSumNoTax,tenThousand,6);
            zxCtOtherSupplyAgreement.setAlterContractSumNoTax(alterContractSumNoTax);
            // 其他合同补充协议主表的变更后合同税额（万元）
            BigDecimal alterContractSumTax = CalcUtils.calcDivide(totalAlterContractSumTax,tenThousand,6);
            // zxCtOtherSupplyAgreement.setAlterContractSumTax(alterContractSumTax);
            zxCtOtherSupplyAgreement.setAlterContractSumTax(CalcUtils.calcSubtract(alterContractSum, alterContractSumNoTax));
            if(zxCtOtherSupplyAgreement.getUpAlterContractSum() == null || zxCtOtherSupplyAgreement.getUpAlterContractSum().compareTo(zero) == 0){
                // 变更后金额为0时，是当前合同的第一次补充协议，变更增减等于变更后减去变更前
                // 其他合同补充协议主表的本期含税变更增减金额（万元）  变更后含税合同金额 - 含税合同金额
                zxCtOtherSupplyAgreement.setApplyAmount(CalcUtils.calcSubtract(alterContractSum,zxCtOtherSupplyAgreement.getContractCost()));
                // 其他合同补充协议主表的本期不含税变更增减金额（万元） 变更后不含税合同金额 - 不含税合同金额
                zxCtOtherSupplyAgreement.setApplyAmountNoTax(CalcUtils.calcSubtract(alterContractSumNoTax,zxCtOtherSupplyAgreement.getContractCostNoTax()));
            } else {
                // 变更后金额不为0时，不是第一次补充协议，变更增减等于变更后减去上期末变更后
                // 其他合同补充协议主表的本期含税变更增减金额（万元）  变更后含税合同金额 - 上期末变更后合同金额
                zxCtOtherSupplyAgreement.setApplyAmount(CalcUtils.calcSubtract(alterContractSum,zxCtOtherSupplyAgreement.getUpAlterContractSum()));
                // 其他合同补充协议主表的本期不含税变更增减金额（万元） 变更后不含税合同金额 - 上期末变更后金额不含税
                zxCtOtherSupplyAgreement.setApplyAmountNoTax(CalcUtils.calcSubtract(alterContractSumNoTax,zxCtOtherSupplyAgreement.getUpAlterContractSumNoTax()));
            }
            // 增减税额  =  本期增减含税金额 - 本期增减不含税金额
            zxCtOtherSupplyAgreement.setApplyAmountTax(CalcUtils.calcSubtract(zxCtOtherSupplyAgreement.getApplyAmount(), zxCtOtherSupplyAgreement.getApplyAmountNoTax()));
        } else {
            //其他合同补充协议主表的变更后含税合同金额（万元）
            zxCtOtherSupplyAgreement.setAlterContractSum(zxCtOtherSupplyAgreement.getUpAlterContractSum());
            //其他合同补充协议主表的变更后不含税合同金额（万元）
            zxCtOtherSupplyAgreement.setAlterContractSumNoTax(zxCtOtherSupplyAgreement.getUpAlterContractSumNoTax());
            //其他合同补充协议主表的变更后合同税额（万元）
            zxCtOtherSupplyAgreement.setAlterContractSumTax(zxCtOtherSupplyAgreement.getUpAlterContractSumTax());
            // 没有变更明细时 本期增减额都是0
            zxCtOtherSupplyAgreement.setApplyAmount(zero);
            zxCtOtherSupplyAgreement.setApplyAmountNoTax(zero);
            zxCtOtherSupplyAgreement.setApplyAmountTax(zero);
        }
        zxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
        zxCtOtherSupplyAgreementMapper.updateByPrimaryKey(zxCtOtherSupplyAgreement);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

}
