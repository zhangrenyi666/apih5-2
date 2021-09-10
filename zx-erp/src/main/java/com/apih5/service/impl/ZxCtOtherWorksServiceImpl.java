package com.apih5.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtOtherMapper;
import com.apih5.mybatis.pojo.ZxCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxCtOther;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtOtherWorksMapper;
import com.apih5.mybatis.pojo.ZxCtOtherWorks;
import com.apih5.service.ZxCtOtherWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherWorksService")
public class ZxCtOtherWorksServiceImpl implements ZxCtOtherWorksService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;


    @Override
    public ResponseEntity getZxCtOtherWorksListByCondition(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        // 分页查询
        PageHelper.startPage(zxCtOtherWorks.getPage(),zxCtOtherWorks.getLimit());
        // 获取数据
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(zxCtOtherWorks);
        // 得到分页信息
        PageInfo<ZxCtOtherWorks> p = new PageInfo<>(zxCtOtherWorksList);

        return repEntity.okList(zxCtOtherWorksList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherWorksDetail(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        // 获取数据
        ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherWorksId());
        // 数据存在
        if (dbZxCtOtherWorks != null) {
            return repEntity.ok(dbZxCtOtherWorks);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
        zxCtOtherWorks.setCreateUserInfo(userKey, realName);
        // 其他合同评审id不能为空
        if (StrUtil.isEmpty(zxCtOtherWorks.getZxCtOtherId())) {
            return repEntity.layerMessage("no", "其他合同评审ID不能为空！");
        }
        // 合同评审id
        zxCtOtherWorks.setZxCtOtherId(zxCtOtherWorks.getZxCtOtherId());
        // 不含税单价 含税单价 / （1 + 税率百分比）
        BigDecimal rate = CalcUtils.calcDivide(new BigDecimal(zxCtOtherWorks.getTaxRate()), new BigDecimal(100), 6);
        zxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(zxCtOtherWorks.getPrice(), CalcUtils.calcAdd(new BigDecimal(1), rate), 6));
        // 含税合同金额  含税单价 * 数量
        BigDecimal contractSum = CalcUtils.calcMultiply(zxCtOtherWorks.getPrice(), zxCtOtherWorks.getQty());
        zxCtOtherWorks.setContractSum(contractSum);
        // 不含税合同金额 含税金额 / (1 + 税率百分比)
        BigDecimal contractSumNoTax = CalcUtils.calcDivide(contractSum, CalcUtils.calcAdd(new BigDecimal(1), rate), 6);
        zxCtOtherWorks.setContractSumNoTax(contractSumNoTax);
        // 税额  含税合同金额减去不含税合同金额
        BigDecimal contractSumTax = CalcUtils.calcSubtract(contractSum, contractSumNoTax);
        zxCtOtherWorks.setContractSumTax(contractSumTax);
        // 变更后数量
        zxCtOtherWorks.setChangeQty(null);
        // 变更后含税单价
        zxCtOtherWorks.setChangePrice(null);
        // 变更后含税金额
        zxCtOtherWorks.setChangeContractSum(null);
        // 变更后不含税单价
        zxCtOtherWorks.setChangePriceNoTax(null);
        // 变更后不含税金额
        zxCtOtherWorks.setChangeContractSumNoTax(null);
        // 变更后税额
        zxCtOtherWorks.setChangeContractSumTax(null);
        int flag = zxCtOtherWorksMapper.insert(zxCtOtherWorks);
        // 添加其他合同明细表数据后，更新合同评审主表数据
        this.sumZxCtOtherWorksAmount(zxCtOtherWorks);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherWorks);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherWorksId());
        if (dbZxCtOtherWorks != null && StrUtil.isNotEmpty(dbZxCtOtherWorks.getZxCtOtherWorksId())) {
            // 清单编号
            dbZxCtOtherWorks.setWorkNo(zxCtOtherWorks.getWorkNo());
            // 清单名称
            dbZxCtOtherWorks.setWorkName(zxCtOtherWorks.getWorkName());
            // 计量单位
            dbZxCtOtherWorks.setUnit(zxCtOtherWorks.getUnit());
            // 合同数量
            dbZxCtOtherWorks.setQty(zxCtOtherWorks.getQty());
            // 含税合同单价
            dbZxCtOtherWorks.setPrice(zxCtOtherWorks.getPrice());
            // 不含税单价 含税单价 / （1 + 税率百分比）
            BigDecimal rate = CalcUtils.calcDivide(new BigDecimal(zxCtOtherWorks.getTaxRate()), new BigDecimal(100), 6);
            dbZxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(zxCtOtherWorks.getPrice(), CalcUtils.calcAdd(new BigDecimal(1), rate), 6));
            // 税率(%)
            dbZxCtOtherWorks.setTaxRate(zxCtOtherWorks.getTaxRate());
            // 是否抵扣
            dbZxCtOtherWorks.setIsDeduct(zxCtOtherWorks.getIsDeduct());
            // 含税合同金额  含税单价 * 数量
            BigDecimal contractSum = CalcUtils.calcMultiply(zxCtOtherWorks.getPrice(), zxCtOtherWorks.getQty());
            dbZxCtOtherWorks.setContractSum(contractSum);
            // 不含税合同金额 含税金额 / (1 + 税率百分比)
            BigDecimal contractSumNoTax = CalcUtils.calcDivide(contractSum, CalcUtils.calcAdd(new BigDecimal(1), rate), 6);
            dbZxCtOtherWorks.setContractSumNoTax(contractSumNoTax);
            // 共通
            dbZxCtOtherWorks.setModifyUserInfo(userKey, realName);
            // 备注
            dbZxCtOtherWorks.setRemark(zxCtOtherWorks.getRemark());
            // 税额  含税合同金额减去不含税合同金额
            BigDecimal contractSumTax = CalcUtils.calcSubtract(contractSum, contractSumNoTax);
            dbZxCtOtherWorks.setContractSumTax(contractSumTax);
            flag = zxCtOtherWorksMapper.updateByPrimaryKey(dbZxCtOtherWorks);
            // 更新其他合同明细表数据后，更新合同评审主表数据
            this.sumZxCtOtherWorksAmount(dbZxCtOtherWorks);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherWorks);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherWorks(List<ZxCtOtherWorks> zxCtOtherWorksList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
           ZxCtOtherWorks zxCtOtherWorks = new ZxCtOtherWorks();
           zxCtOtherWorks.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherWorksMapper.batchDeleteUpdateZxCtOtherWorks(zxCtOtherWorksList, zxCtOtherWorks);
            // 删除其他合同明细表数据后，更新合同评审主表数据
           for (ZxCtOtherWorks zxCtOtherWorks1 : zxCtOtherWorksList) {
               this.sumZxCtOtherWorksAmount(zxCtOtherWorks1);
           }
        }

        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherWorksList);
        }
    }

    /**
     * 其他类合同评审清单明细新增和更新时汇总含税合同金额和不含税合同金额，并给主表赋值
     *
     * @param zxCtOtherWorks 默认
     *
     * */
    private void sumZxCtOtherWorksAmount(ZxCtOtherWorks zxCtOtherWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
        dbZxCtOtherWorks.setZxCtOtherId(zxCtOtherWorks.getZxCtOtherId());
        // 查询当前其他合同评审zxCtOtherId在明细表中的数据
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
        // 查询其他合同评审主表数据
        ZxCtOther zxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOtherWorks.getZxCtOtherId());
        // 含税合同金额(万元)
        BigDecimal totalContractSum = null;
        // 不含税合同金额(万元)
        BigDecimal totalContractSumNoTax = null;
        // 合同税额(万元)
        BigDecimal totalContractCostTax = null;
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
            for (ZxCtOtherWorks zxCtOtherWorks1 : zxCtOtherWorksList) {
                // 清单明细的含税合同金额
                totalContractSum = CalcUtils.calcAdd(totalContractSum, zxCtOtherWorks1.getContractSum());
                // 清单明细的不含税合同金额
                totalContractSumNoTax = CalcUtils.calcAdd(totalContractSumNoTax, zxCtOtherWorks1.getContractSumNoTax());
                // 清单明细的合同税额
                totalContractCostTax = CalcUtils.calcAdd(totalContractCostTax, zxCtOtherWorks1.getContractSumTax());
            }
            // 其他合同评审的含税合同金额
            zxCtOther.setContractCost(CalcUtils.calcDivide(totalContractSum, new BigDecimal("10000"), 6));
            // 其他合同评审的不含税合同金额
            zxCtOther.setContractCostNoTax(CalcUtils.calcDivide(totalContractSumNoTax, new BigDecimal("10000"), 6));
            // 其他合同评审的合同税额
            zxCtOther.setContractCostTax(CalcUtils.calcDivide(totalContractCostTax, new BigDecimal("10000"), 6));
            zxCtOther.setModifyUserInfo(userKey, realName);
            zxCtOtherMapper.updateByPrimaryKey(zxCtOther);
        } else {
            zxCtOther.setContractCost(new BigDecimal(0));
            zxCtOther.setContractCostNoTax(new BigDecimal(0));
            zxCtOther.setContractCostTax(new BigDecimal(0));
            zxCtOther.setModifyUserInfo(userKey, realName);
            zxCtOtherMapper.updateByPrimaryKey(zxCtOther);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 其他类合同评审清单明细导入
     *
     * @param zxCtOtherWorks 默认
     * @ return 列表
     * */
    @Override
    public ResponseEntity importZxCtOtherWorks(ZxCtOtherWorks zxCtOtherWorks) {
        if (zxCtOtherWorks == null) {
            zxCtOtherWorks = new ZxCtOtherWorks();
        }
        int flag = 0;
        // 上传文件为空
        if (zxCtOtherWorks.getZxErpFileList() == null || zxCtOtherWorks.getZxErpFileList().size() == 0) {
            return repEntity.layerMessage("no", "没有导入文件!");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<ZxCtOtherWorks> returnWorksList = new ArrayList<>();
        int i = 0;
        String workNo = "";
        try {
            String path = Apih5Properties.getFilePath() + zxCtOtherWorks.getZxErpFileList().get(0).getRelativeUrl();
            ExcelReader reader = ExcelUtil.getReader(path);

            List<Map<String, Object>> importExcelList = reader.readAll();
            if (importExcelList == null || importExcelList.size() == 0) {
                return repEntity.layerMessage("no", "导入失败，无导入数据!");
            }

            if (importExcelList != null && importExcelList.size() > 0) {
                for(Map<String,Object> map:importExcelList) {
                    JSONObject json = new JSONObject(map);
                    // 清单编号
                    if (StrUtil.isEmpty(json.getStr("清单编号"))) {
                        continue;
                    }
                    zxCtOtherWorks.setWorkNo(json.getStr("清单编号"));
                    workNo = zxCtOtherWorks.getWorkNo();
                    
                    if(StrUtil.isNotEmpty(json.getStr("清单名称"))){
                        // 清单名称
                        zxCtOtherWorks.setWorkName(json.getStr("清单名称"));
                    }
                    if(StrUtil.isNotEmpty(json.getStr("规格型号"))){
                        // 规格型号
                        zxCtOtherWorks.setSpec(json.getStr("规格型号"));
                    }
                    if(StrUtil.isNotEmpty(json.getStr("单位"))){
                        // 单位
                        zxCtOtherWorks.setSpec(json.getStr("单位"));
                    }
                    if (StrUtil.isNotEmpty(json.getStr("数量"))){
                        BigDecimal qty1 = new BigDecimal(json.getStr("数量"));
                        zxCtOtherWorks.setQty(qty1);
                    }
                    // 单价
                    if (StrUtil.isNotEmpty(json.getStr("含税合同单价"))) {
                        BigDecimal price1 = new BigDecimal(json.getStr("含税合同单价"));
                        zxCtOtherWorks.setPrice(price1);
                        // 税率
                        if (StrUtil.isEmpty(json.getStr("税率(%)")) || StrUtil.equals("空", (json.getStr("税率(%)")))) {
                            zxCtOtherWorks.setPriceNoTax(new BigDecimal(0));
                        } else {
                            zxCtOtherWorks.setTaxRate(json.getStr("税率(%)"));
                            zxCtOtherWorks.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(json.getStr("含税合同单价")), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(json.getStr("税率(%)"))), new BigDecimal(0.01)), 2));
                        }
                    }
                    zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
                    zxCtOtherWorks.setCreateUserInfo(userKey, realName);
                    flag = zxCtOtherWorksMapper.insert(zxCtOtherWorks);
                    if (flag == 0) {
                        repEntity.layerMessage("no", "第" + i + "行【清单编号为：" + workNo + "】导入失败！(数据操作失败)");
                    } else {
                        returnWorksList.add(zxCtOtherWorks);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, e.getMessage());
            repEntity.layerMessage("no", "第" + i + "行【清单编号为：" + workNo + "】导入失败！  " + e.getMessage());
        }
        // 得到分页信息
        return repEntity.okList(returnWorksList, returnWorksList.size());
    }
}
