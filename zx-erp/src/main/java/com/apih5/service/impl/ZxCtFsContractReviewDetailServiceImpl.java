package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.dao.ZxCtFsContractReviewMapper;
import com.apih5.mybatis.pojo.ZxCtFsContract;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractReviewDetailMapper;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import com.apih5.service.ZxCtFsContractReviewDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

@Service("zxCtFsContractReviewDetailService")
public class ZxCtFsContractReviewDetailServiceImpl implements ZxCtFsContractReviewDetailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewMapper zxCtFsContractReviewMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Override
    public ResponseEntity getZxCtFsContractReviewDetailListByCondition(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // 获取数据
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectByZxCtFsContractReviewDetailList(zxCtFsContractReviewDetail);
        // 得到分页信息
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractReviewDetailDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // 获取数据
        ZxCtFsContractReviewDetail dbZxCtFsContractReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getConRevDetailId());
        // 数据存在
        if (dbZxCtFsContractReviewDetail != null) {
            return repEntity.ok(dbZxCtFsContractReviewDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContractReviewDetail.setConRevDetailId(UuidUtil.generate());
        zxCtFsContractReviewDetail.setCreateUserInfo(userKey, realName);
        zxCtFsContractReviewDetail.setContractSum(CalcUtils.calcMultiply(zxCtFsContractReviewDetail.getPrice(), BigDecimal.valueOf(zxCtFsContractReviewDetail.getQty())));
        zxCtFsContractReviewDetail.setContractSumNoTax(CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSum(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(zxCtFsContractReviewDetail.getTaxRate()), new BigDecimal("100")))));
        zxCtFsContractReviewDetail.setContractSumTax(CalcUtils.calcSubtract(zxCtFsContractReviewDetail.getContractSum(), zxCtFsContractReviewDetail.getContractSumNoTax()));
        flag = zxCtFsContractReviewDetailMapper.insert(zxCtFsContractReviewDetail);

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            flag = updateZxCtFsContractReviewContractSum(zxCtFsContractReviewDetail);
        }

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsContractReviewDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContractReviewDetail dbZxCtFsContractReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getConRevDetailId());
        if (dbZxCtFsContractReviewDetail != null && StrUtil.isNotEmpty(dbZxCtFsContractReviewDetail.getConRevDetailId())) {
            // 合同ID
            dbZxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContractReviewDetail.getZxCtFsContractId());
            // 清单编号
            dbZxCtFsContractReviewDetail.setWorkNo(zxCtFsContractReviewDetail.getWorkNo());
            // 清单名称
            dbZxCtFsContractReviewDetail.setWorkName(zxCtFsContractReviewDetail.getWorkName());
            // 型号
            dbZxCtFsContractReviewDetail.setSpec(zxCtFsContractReviewDetail.getSpec());
            // 计量单位
            dbZxCtFsContractReviewDetail.setUnit(zxCtFsContractReviewDetail.getUnit());
            // 设备ID(暂用workType)
            dbZxCtFsContractReviewDetail.setWorkType(zxCtFsContractReviewDetail.getWorkType());
            // 层次
            dbZxCtFsContractReviewDetail.setTreenode(zxCtFsContractReviewDetail.getTreenode());
            // 合同数量
            dbZxCtFsContractReviewDetail.setQty(zxCtFsContractReviewDetail.getQty());
            // 含税合同单价
            dbZxCtFsContractReviewDetail.setPrice(zxCtFsContractReviewDetail.getPrice());
            // 界面展现类型
            dbZxCtFsContractReviewDetail.setViewType(zxCtFsContractReviewDetail.getViewType());
            // 计划开始时间
            dbZxCtFsContractReviewDetail.setPlanStartDate(zxCtFsContractReviewDetail.getPlanStartDate());
            // 计划结束时间
            dbZxCtFsContractReviewDetail.setPlanEndDate(zxCtFsContractReviewDetail.getPlanEndDate());
            // 租赁开始时间
            dbZxCtFsContractReviewDetail.setActualStartDate(zxCtFsContractReviewDetail.getActualStartDate());
            // 租赁结束时间
            dbZxCtFsContractReviewDetail.setActualEndDate(zxCtFsContractReviewDetail.getActualEndDate());
            // 评审ID
            dbZxCtFsContractReviewDetail.setConRevDetailId(zxCtFsContractReviewDetail.getConRevDetailId());
            // 租期(台班)
            dbZxCtFsContractReviewDetail.setPp10(zxCtFsContractReviewDetail.getPp10());
            // 变更后数量
            dbZxCtFsContractReviewDetail.setChangeQty(zxCtFsContractReviewDetail.getChangeQty());
            // 变更后含税单价
            dbZxCtFsContractReviewDetail.setChangePrice(zxCtFsContractReviewDetail.getChangePrice());
            // 变更后含税金额
            dbZxCtFsContractReviewDetail.setChangeContractSum(zxCtFsContractReviewDetail.getChangeContractSum());
            // 租赁单位
            dbZxCtFsContractReviewDetail.setRentUnit(zxCtFsContractReviewDetail.getRentUnit());
            // 变更后租赁开始时间
            dbZxCtFsContractReviewDetail.setAlterRentStartDate(zxCtFsContractReviewDetail.getAlterRentStartDate());
            // 变更后租赁结束时间
            dbZxCtFsContractReviewDetail.setAlterRentEndDate(zxCtFsContractReviewDetail.getAlterRentEndDate());
            // 要求修改
            dbZxCtFsContractReviewDetail.setRequestEdit(zxCtFsContractReviewDetail.getRequestEdit());
            // 修改人
            dbZxCtFsContractReviewDetail.setEditUserId(zxCtFsContractReviewDetail.getEditUserId());
            // 修改人
            dbZxCtFsContractReviewDetail.setEditUserName(zxCtFsContractReviewDetail.getEditUserName());
            // 修改日期
            dbZxCtFsContractReviewDetail.setEditDate(zxCtFsContractReviewDetail.getEditDate());
            // 不含税合同单价
            dbZxCtFsContractReviewDetail.setPriceNoTax(zxCtFsContractReviewDetail.getPriceNoTax());
            // 变更后不含税单价
            dbZxCtFsContractReviewDetail.setChangePriceNoTax(zxCtFsContractReviewDetail.getChangePriceNoTax());
            // 变更后不含税金额
            dbZxCtFsContractReviewDetail.setChangeContractSumNoTax(zxCtFsContractReviewDetail.getChangeContractSumNoTax());
            // 税率(%)
            dbZxCtFsContractReviewDetail.setTaxRate(zxCtFsContractReviewDetail.getTaxRate());
            // 备注
            dbZxCtFsContractReviewDetail.setRemarks(zxCtFsContractReviewDetail.getRemarks());
            // 排序
            dbZxCtFsContractReviewDetail.setSort(zxCtFsContractReviewDetail.getSort());
            // 共通
            dbZxCtFsContractReviewDetail.setModifyUserInfo(userKey, realName);

            // 含税合同金额
            dbZxCtFsContractReviewDetail.setContractSum(zxCtFsContractReviewDetail.getContractSum());
            // 不含税合同金额
            dbZxCtFsContractReviewDetail.setContractSumNoTax(zxCtFsContractReviewDetail.getContractSumNoTax());
            flag = zxCtFsContractReviewDetailMapper.updateByPrimaryKey(dbZxCtFsContractReviewDetail);
            if (flag != 0) {
                flag = updateZxCtFsContractReviewContractSum(zxCtFsContractReviewDetail);
                if (flag == 0) {
                    return repEntity.errorSave();
                } else {
                    return repEntity.ok("sys.data.update", zxCtFsContractReviewDetail);
                }
            } else {
                return repEntity.errorUpdate();
            }
        } else {
            return repEntity.layerMessage("no", "要修改的数据不存在");
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContractReviewDetail(List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsContractReviewDetailList != null && zxCtFsContractReviewDetailList.size() > 0) {
            for (ZxCtFsContractReviewDetail zxCtFsContractReviewDetail : zxCtFsContractReviewDetailList
            ) {
                flag = synZxCtFsReviewDelete(zxCtFsContractReviewDetail);
                if (flag == 0) {
                    return repEntity.errorSave();
                }
            }
            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setModifyUserInfo(userKey, realName);

            flag = zxCtFsContractReviewDetailMapper.batchDeleteUpdateZxCtFsContractReviewDetail(zxCtFsContractReviewDetailList, zxCtFsContractReviewDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractReviewDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 从excel模板中导入附属合同评审详情数据到附属合同评审详情表中
     *
     * @param zxCtFsContractReviewDetail
     * @author suncg
     */
    public ResponseEntity importExcel(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // 上传文件为空
        if (zxCtFsContractReviewDetail.getZxErpFileList() == null || zxCtFsContractReviewDetail.getZxErpFileList().size() == 0) {
            return repEntity.layerMessage("no", "没有导入文件!");
        }
        //纯数字（带数）正则校验
        // String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        int i = 0;
        List<ZxCtFsContractReviewDetail> excelImportList = new ArrayList<ZxCtFsContractReviewDetail>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<Map<String, Object>> excelList = null;
        String path = Apih5Properties.getFilePath() + zxCtFsContractReviewDetail.getZxErpFileList().get(0).getRelativeUrl();
        ExcelReader reader = ExcelUtil.getReader(path);
        try {
            //ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
//            reader.addHeaderAlias("清单编号","workNo");
//            reader.addHeaderAlias("清单名称","workName");
//            reader.addHeaderAlias("规格型号","spec");
//            reader.addHeaderAlias("单位","unit");
//            reader.addHeaderAlias("数量","qty");
//            reader.addHeaderAlias("含税合同单价","price");
//            reader.addHeaderAlias("税率(%)","taxRate");
            excelList = reader.readAll();

            for (Map<String, Object> item : excelList) {
                ZxCtFsContractReviewDetail zxCtFsContractReviewDetail12 = new ZxCtFsContractReviewDetail();
                JSONObject json = new JSONObject(item);
                if (StrUtil.isNotEmpty(json.getStr("清单名称"))) {
                    zxCtFsContractReviewDetail12.setWorkName(json.getStr("清单名称"));
                }
                if (StrUtil.isNotEmpty(json.getStr("清单编号"))) {
                    zxCtFsContractReviewDetail12.setWorkNo(json.getStr("清单编号"));
                }
                if (StrUtil.isNotEmpty(json.getStr("单位"))) {
                    zxCtFsContractReviewDetail12.setUnit(json.getStr("单位"));
                }
                if (StrUtil.isNotEmpty(json.getStr("规格型号"))) {
                    zxCtFsContractReviewDetail12.setSpec(json.getStr("规格型号"));
                }
                if (StrUtil.isNotEmpty(json.getStr("数量"))) {
                    zxCtFsContractReviewDetail12.setQty(Integer.parseInt(json.getStr("数量")));
                }
                if (StrUtil.isNotEmpty(json.getStr("含税合同单价"))) {
                    BigDecimal price = new BigDecimal(json.getStr("含税合同单价"));
                    zxCtFsContractReviewDetail12.setPrice(price);
                }
                if (StrUtil.isNotEmpty(json.getStr("税率"))) {
                    zxCtFsContractReviewDetail12.setTaxRate(json.getStr("税率"));
                }
                zxCtFsContractReviewDetail12.setConRevDetailId(UuidUtil.generate());
                zxCtFsContractReviewDetail12.setCreateUserInfo(userKey, realName);
                excelImportList.add(zxCtFsContractReviewDetail12);
                i++;
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, "导入（存储）失败" + e.getMessage());
            logger.info("UuidUtil.generate()【" + excelList.size() + "】行");
            logger.info("导入的数据：" + excelList);
            logger.info("待存储的数据：" + excelImportList);
            logger.info("待存储行数：" + i);
            return repEntity.layerMessage("no", "失败！错误数据" + e.getMessage() + "行" + excelList.size() + "/" + i);
        } finally {
            reader.close();
        }
        int flag = zxCtFsContractReviewDetailMapper.importZxCtFsContractReviewDetailsList(excelImportList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", excelImportList);
        }

    }

    /**
     * 在清单税额、含税金额、不含税金额发生变化时，同步改变对应附属合同中的金额
     *
     * @param zxCtFsContractReviewDetail
     * @author suncg
     */
    private int updateZxCtFsContractReviewContractSum(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        ZxCtFsContractReview zxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getContractReviewId());
        if (zxCtFsContractReview != null) {
            ZxCtFsContractReviewDetail params = new ZxCtFsContractReviewDetail();
            params.setContractReviewId(zxCtFsContractReviewDetail.getContractReviewId());
            List<ZxCtFsContractReviewDetail> list1 = zxCtFsContractReviewDetailMapper.selectByZxCtFsContractReviewDetailList(params);
            BigDecimal contractSum = new BigDecimal("0");
            BigDecimal contractSumNoTax = new BigDecimal("0");
            BigDecimal wan = new BigDecimal("10000");
            if (list1.size() > 0) {
                for (ZxCtFsContractReviewDetail item : list1) {
                    contractSum = CalcUtils.calcAdd(contractSum, CalcUtils.calcDivide(item.getContractSum(), wan, 6));
                    contractSumNoTax = CalcUtils.calcAdd(contractSumNoTax, CalcUtils.calcDivide(item.getContractSumNoTax(), wan, 6));
                }
            }

            BigDecimal contractCostTax = contractSum.subtract(contractSumNoTax);
            zxCtFsContractReview.setContractCost(contractSum);
            zxCtFsContractReview.setContractCostNoTax(contractSumNoTax);
            zxCtFsContractReview.setContractCostTax(contractCostTax);
            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(zxCtFsContractReview);
        }

        return flag;
    }

    private int synZxCtFsReviewDelete(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        int flag = 0;
        ZxCtFsContractReview zxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getContractReviewId());
        BigDecimal contractCostTax = CalcUtils.calcSubtract(zxCtFsContractReviewDetail.getContractSum(), zxCtFsContractReviewDetail.getContractSumNoTax());
        zxCtFsContractReview.setContractCost(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCost(), CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSum(), new BigDecimal("10000"), 6)));
        zxCtFsContractReview.setContractCostNoTax(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCostNoTax(), CalcUtils.calcDivide(zxCtFsContractReviewDetail.getContractSumNoTax(), new BigDecimal("10000"), 6)));
        zxCtFsContractReview.setContractCostTax(CalcUtils.calcSubtract(zxCtFsContractReview.getContractCostTax(), CalcUtils.calcDivide(contractCostTax, new BigDecimal("10000"), 6)));
        flag = zxCtFsContractReviewMapper.updateByPrimaryKey(zxCtFsContractReview);
        return flag;
    }


    @Override
    public ResponseEntity getZxCtFsContractReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // 分页查询
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // 获取数据
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        // 得到分页信息
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


    /**
     * 根据附属合同ID查询评审清单
     *
     * @param zxCtFsContractReviewDetail 导入excel数据
     * @author suncg
     */
    public ResponseEntity getReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        if (zxCtFsContractReviewDetail == null) {
            zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        // 获取数据
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectByFsContractId(zxCtFsContractReviewDetail.getZxCtFsContractId());
        // 得到分页信息
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


    @Override
    public ResponseEntity getAllReviewDetailListByContract(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail) {
        // 分页查询
        PageHelper.startPage(zxCtFsContractReviewDetail.getPage(), zxCtFsContractReviewDetail.getLimit());
        ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContractReviewDetail.getZxCtFsContractId());
        zxCtFsContractReviewDetail.setContractReviewId(zxCtFsContract.getContractReviewId());
        zxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContract.getZxCtFsContractId());
        // 获取数据
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        // 得到分页信息
        PageInfo<ZxCtFsContractReviewDetail> p = new PageInfo<>(zxCtFsContractReviewDetailList);

        return repEntity.okList(zxCtFsContractReviewDetailList, p.getTotal());
    }


}
