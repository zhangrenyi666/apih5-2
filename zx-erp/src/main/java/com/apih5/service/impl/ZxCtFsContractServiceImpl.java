package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxCtFsContractReviewDetailMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.pojo.ZxCtFsContract;
import com.apih5.service.ZxCtFsContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtFsContractService")
public class ZxCtFsContractServiceImpl implements ZxCtFsContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Override
    public ResponseEntity getZxCtFsContractListByCondition(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsContract.setComID("");
            zxCtFsContract.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtFsContract.setComID(zxCtFsContract.getOrgID());
            zxCtFsContract.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtFsContract.setOrgID(zxCtFsContract.getOrgID());
        }

        // 分页查询
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // 获取数据
        List<ZxCtFsContract> zxCtFsContractList = zxCtFsContractMapper.selectByZxCtFsContractList(zxCtFsContract);

        for (ZxCtFsContract zxCtFsContract1 : zxCtFsContractList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsContract1.getZxCtFsContractId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsContract1.setZxErpFileList(zxErpFileList);
        }
        // 得到分页信息
        PageInfo<ZxCtFsContract> p = new PageInfo<>(zxCtFsContractList);

        return repEntity.okList(zxCtFsContractList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractDetail(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // 获取数据
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContract.getZxCtFsContractId());
        // 数据存在
        if (dbZxCtFsContract != null) {
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContract(ZxCtFsContract zxCtFsContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContract.setZxCtFsContractId(UuidUtil.generate());
        zxCtFsContract.setCreateUserInfo(userKey, realName);
        int flag = zxCtFsContractMapper.insert(zxCtFsContract);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContract(ZxCtFsContract zxCtFsContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsContract.getZxCtFsContractId());
        if (dbZxCtFsContract != null && StrUtil.isNotEmpty(dbZxCtFsContract.getZxCtFsContractId())) {
            // 合同编号
            dbZxCtFsContract.setContractNo(zxCtFsContract.getContractNo());
            // 合同名称
            dbZxCtFsContract.setContractName(zxCtFsContract.getContractName());
            // 合同类型
            dbZxCtFsContract.setContractType(zxCtFsContract.getContractType());
            // 摘要
            dbZxCtFsContract.setSubject(zxCtFsContract.getSubject());
            // 合同内容
            dbZxCtFsContract.setContent(zxCtFsContract.getContent());
            // 所属项目ID
            dbZxCtFsContract.setOrgID(zxCtFsContract.getOrgID());
            // 甲方ID
            dbZxCtFsContract.setFirstId(zxCtFsContract.getFirstId());
            // 合同甲方
            dbZxCtFsContract.setFirstName(zxCtFsContract.getFirstName());
            // 项目经理姓名
            dbZxCtFsContract.setFirstPrincipal(zxCtFsContract.getFirstPrincipal());
            // 甲方代表身份证
            dbZxCtFsContract.setFirstPrincipalIDCard(zxCtFsContract.getFirstPrincipalIDCard());
            // 甲方联系电话
            dbZxCtFsContract.setFirstUnitTel(zxCtFsContract.getFirstUnitTel());
            // 乙方ID
            dbZxCtFsContract.setSecondID(zxCtFsContract.getSecondID());
            // 合同乙方
            dbZxCtFsContract.setSecondName(zxCtFsContract.getSecondName());
            // 乙方代表
            dbZxCtFsContract.setSecondPrincipal(zxCtFsContract.getSecondPrincipal());
            // 乙方代表身份证
            dbZxCtFsContract.setSecondPrincipalIDCard(zxCtFsContract.getSecondPrincipalIDCard());
            // 乙方(固话)
            dbZxCtFsContract.setSecondUnitTel(zxCtFsContract.getSecondUnitTel());
            // 丙方
            dbZxCtFsContract.setThirdName(zxCtFsContract.getThirdName());
            // 收付类型
            dbZxCtFsContract.setPayType(zxCtFsContract.getPayType());
            // 所属处部ID
            dbZxCtFsContract.setLocationInDeprId(zxCtFsContract.getLocationInDeprId());
            // 登记日期
            dbZxCtFsContract.setRegisterDate(zxCtFsContract.getRegisterDate());
            // 中标日期
            dbZxCtFsContract.setBidDate(zxCtFsContract.getBidDate());
            // 签订时间
            dbZxCtFsContract.setSignatureDate(zxCtFsContract.getSignatureDate());
            // 含税合同总价(万元)
            dbZxCtFsContract.setContractCost(zxCtFsContract.getContractCost());
            // 合同工期(天)
            dbZxCtFsContract.setCsTimeLimit(zxCtFsContract.getCsTimeLimit());
            // 合同开始时间
            dbZxCtFsContract.setPlanStartDate(zxCtFsContract.getPlanStartDate());
            // 实际开始时间
            dbZxCtFsContract.setActualStartDate(zxCtFsContract.getActualStartDate());
            // 合同结束时间
            dbZxCtFsContract.setPlanEndDate(zxCtFsContract.getPlanEndDate());
            // 实际结束时间
            dbZxCtFsContract.setActualEndDate(zxCtFsContract.getActualEndDate());
            // 监理单位
            dbZxCtFsContract.setConsultative(zxCtFsContract.getConsultative());
            // 监理单位电话
            dbZxCtFsContract.setConsultativeTel(zxCtFsContract.getConsultativeTel());
            // 业主联系电话
            dbZxCtFsContract.setOwnerLinkTel(zxCtFsContract.getOwnerLinkTel());
            // 项目经理
            dbZxCtFsContract.setProjectManager(zxCtFsContract.getProjectManager());
            // 项目经理电话
            dbZxCtFsContract.setProjectManagerTel(zxCtFsContract.getProjectManagerTel());
            // 项目总工姓名
            dbZxCtFsContract.setProjectChiefName(zxCtFsContract.getProjectChiefName());
            // 项目总工电话
            dbZxCtFsContract.setProjectChiefTel(zxCtFsContract.getProjectChiefTel());
            // 合同状态
            dbZxCtFsContract.setContractStatus(zxCtFsContract.getContractStatus());
            // 项目编号
            dbZxCtFsContract.setProjectNo(zxCtFsContract.getProjectNo());
            // 项目全称
            dbZxCtFsContract.setProjectName(zxCtFsContract.getProjectName());
            // 项目简称
            dbZxCtFsContract.setShortName(zxCtFsContract.getShortName());
            // 风险抵押金
            dbZxCtFsContract.setVentureGuaranty(zxCtFsContract.getVentureGuaranty());
            // 内部承包总价
            dbZxCtFsContract.setInnerContractSum(zxCtFsContract.getInnerContractSum());
            // 税率
            dbZxCtFsContract.setFaxRate(zxCtFsContract.getFaxRate());
            // 管理费率
            dbZxCtFsContract.setManageRate(zxCtFsContract.getManageRate());
            // 质保费率
            dbZxCtFsContract.setQuanlityFeeRate(zxCtFsContract.getQuanlityFeeRate());
            // 供货总额
            dbZxCtFsContract.setGoodsSum(zxCtFsContract.getGoodsSum());
            // 付款约定
            dbZxCtFsContract.setPaymentAssumpsit(zxCtFsContract.getPaymentAssumpsit());
            // 装运方式约定
            dbZxCtFsContract.setTransferAssumpsit(zxCtFsContract.getTransferAssumpsit());
            // 其他条款
            dbZxCtFsContract.setOtherAssumpsit(zxCtFsContract.getOtherAssumpsit());
            // 乙方性质
            dbZxCtFsContract.setAgent(zxCtFsContract.getAgent());
            // 经营指标
            dbZxCtFsContract.setManageIndex(zxCtFsContract.getManageIndex());
            // 施工单位
            dbZxCtFsContract.setImplementUnit(zxCtFsContract.getImplementUnit());
            // 设计单位
            dbZxCtFsContract.setDesignUnit(zxCtFsContract.getDesignUnit());
            // 乙方(手机)
            dbZxCtFsContract.setPp1(zxCtFsContract.getPp1());
            // 乙方(传真)
            dbZxCtFsContract.setPp2(zxCtFsContract.getPp2());
            // 合同类型
            dbZxCtFsContract.setPp3(zxCtFsContract.getPp3());
            // 累计结算金额
            dbZxCtFsContract.setPp4(zxCtFsContract.getPp4());
            // 累计支付金额
            dbZxCtFsContract.setPp5(zxCtFsContract.getPp5());
            // pp9
            dbZxCtFsContract.setPp9(zxCtFsContract.getPp9());
            // 资金流向
            dbZxCtFsContract.setPp10(zxCtFsContract.getPp10());
            // name
            dbZxCtFsContract.setCode(zxCtFsContract.getCode());
            // 机构编码
            dbZxCtFsContract.setCode1(zxCtFsContract.getCode1());
            // 承建单位简称
            dbZxCtFsContract.setCode2(zxCtFsContract.getCode2());
            // 中标单位简称
            dbZxCtFsContract.setCode3(zxCtFsContract.getCode3());
            // 项目所在省份简称
            dbZxCtFsContract.setCode4(zxCtFsContract.getCode4());
            // 项目简称
            dbZxCtFsContract.setCode5(zxCtFsContract.getCode5());
            // 标段号
            dbZxCtFsContract.setCode6(zxCtFsContract.getCode6());
            // 合同类别
            //dbZxCtFsContract.setCode7(zxCtFsContract.getCode7());
            // 合同序号
            dbZxCtFsContract.setCode8(zxCtFsContract.getCode8());
            // 合同评审ID
            dbZxCtFsContract.setFromApplyID(zxCtFsContract.getFromApplyID());
            // 业主合同功能码
            dbZxCtFsContract.setCodeP1(zxCtFsContract.getCodeP1());
            // 同一公司
            dbZxCtFsContract.setCode2T3(zxCtFsContract.getCode2T3());
            // 变更后含税合同金额（万元）
            dbZxCtFsContract.setAlterContractSum(zxCtFsContract.getAlterContractSum());
            // 结算情况
            dbZxCtFsContract.setSettleType(zxCtFsContract.getSettleType());
            // 税率(%)
            dbZxCtFsContract.setTaxRate(zxCtFsContract.getTaxRate());
            // 是否抵扣
            dbZxCtFsContract.setIsDeduct(zxCtFsContract.getIsDeduct());
            // 不含税合同总价(万元)
            dbZxCtFsContract.setContractCostNoTax(zxCtFsContract.getContractCostNoTax());
            // 合同税额
            dbZxCtFsContract.setContractCostTax(zxCtFsContract.getContractCostTax());
            // 变更后不含税合同金额(万元)
            dbZxCtFsContract.setAlterContractSumNoTax(zxCtFsContract.getAlterContractSumNoTax());
            // 变更后合同税额(万元)
            dbZxCtFsContract.setAlterContractSumTax(zxCtFsContract.getAlterContractSumTax());
            // comID
            dbZxCtFsContract.setComID(zxCtFsContract.getComID());
            // 物资结算引用数
            dbZxCtFsContract.setStockSettleUseCount(zxCtFsContract.getStockSettleUseCount());
            // 所属项目
            dbZxCtFsContract.setOrgName(zxCtFsContract.getOrgName());
            // 甲方
            dbZxCtFsContract.setFirstIdFormula(zxCtFsContract.getFirstIdFormula());
            // 机械及其他结算引用数
            dbZxCtFsContract.setEquipSettleUseCount(zxCtFsContract.getEquipSettleUseCount());
            // 工程结算引用数
            dbZxCtFsContract.setProjectSettleUseCount(zxCtFsContract.getProjectSettleUseCount());
            // 乙方
            dbZxCtFsContract.setSecondIDFormula(zxCtFsContract.getSecondIDFormula());
            // 所属处部
            dbZxCtFsContract.setLocationInDeprFormula(zxCtFsContract.getLocationInDeprFormula());
            // 变更审核次数
            dbZxCtFsContract.setAuditContrAlterCount(zxCtFsContract.getAuditContrAlterCount());
            // 录入类型
            dbZxCtFsContract.setAudit(zxCtFsContract.getAudit());
            // 附属合同评审ID
            dbZxCtFsContract.setContractReviewId(zxCtFsContract.getContractReviewId());
            // 备注
            dbZxCtFsContract.setRemarks(zxCtFsContract.getRemarks());
            // 排序
            dbZxCtFsContract.setSort(zxCtFsContract.getSort());
            // 共通
            dbZxCtFsContract.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractMapper.updateByPrimaryKey(dbZxCtFsContract);

            //附件先删除再新增
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxCtFsContract.getZxCtFsContractId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxCtFsContract.getZxErpFileList() != null && zxCtFsContract.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContract.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContract.getZxCtFsContractId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContract(List<ZxCtFsContract> zxCtFsContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsContractList != null && zxCtFsContractList.size() > 0) {
            ZxCtFsContract zxCtFsContract = new ZxCtFsContract();
            zxCtFsContract.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractMapper.batchDeleteUpdateZxCtFsContract(zxCtFsContractList, zxCtFsContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getOrg(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // 获取数据
        List<ZxCtFsContract> OrgList = zxCtFsContractMapper.selectOrg(zxCtFsContract);
        // 得到分页信息
        PageInfo<ZxCtFsContract> p = new PageInfo<>(OrgList);

        return repEntity.okList(OrgList, p.getTotal());
    }

    /**
     * 导出附属合同评审列表
     *
     * @param zxCtFsContract
     * @author suncg
     */

    @Override
    public ResponseEntity exportContract(ZxCtFsContract zxCtFsContract, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }

        // 获取数据
        List<ZxCtFsContract> zxCtFsContractList = zxCtFsContractMapper.selectByZxCtFsContractList(zxCtFsContract);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("附属合同编号", "附属合同名称", "合同类型", "合同甲方", "合同乙方", "合同录入类型",
                "含税合同总价（万元）", "变更后含税合同金额（万元）", "是否抵扣", "结算情况", "监理单位", "合同类别", "备注");
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtFsContractList != null && zxCtFsContractList.size() > 0) {
            for (ZxCtFsContract zxCtFsContract1 : zxCtFsContractList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsContract1.getContractNo(),
                        zxCtFsContract1.getContractName(),
                        zxCtFsContract1.getContractType(),
                        zxCtFsContract1.getFirstName(),
                        zxCtFsContract1.getSecondName(),
                        zxCtFsContract1.getAudit(),
                        zxCtFsContract1.getContractCost(),
                        zxCtFsContract1.getAlterContractSum(),
                        zxCtFsContract1.getIsDeduct(),
                        zxCtFsContract1.getSettleType(),
                        zxCtFsContract1.getConsultative(),
                        zxCtFsContract1.getCode7(),
                        zxCtFsContract1.getRemarks()
                        )
                );
            }

            // 报表名称
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("fdsafdsa".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
            return null;
        } else {
            return repEntity.ok("无数据");
        }
    }

    @Override
    public ResponseEntity getZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsContract.getPage(), zxCtFsContract.getLimit());
        // 获取数据
        List<ZxCtFsContract> dbZxCtFsContractlsit = zxCtFsContractMapper.selectByOrg(zxCtFsContract);

        // 得到分页信息
        PageInfo<ZxCtFsContract> p = new PageInfo<>(dbZxCtFsContractlsit);

        // 数据存在
        if (dbZxCtFsContractlsit.size() > 0) {
            return repEntity.okList(dbZxCtFsContractlsit, p.getTotal());
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getYunShuZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // 获取数据
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectYunShu(zxCtFsContract);

        // 数据存在
        if (dbZxCtFsContract != null) {

            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setContractReviewId(dbZxCtFsContract.getContractReviewId());
            zxCtFsContractReviewDetail.setZxCtFsContractId(dbZxCtFsContract.getZxCtFsContractId());
            dbZxCtFsContract.setContractCost(new BigDecimal("0"));
            dbZxCtFsContract.setContractCostNoTax(new BigDecimal("0"));

            List<ZxCtFsContractReviewDetail> reviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
            for (ZxCtFsContractReviewDetail zx : reviewDetails
            ) {
                if (zx.getChangeContractSum() != null) {
                    zx.setContractSum(zx.getChangeContractSum());
                    zx.setChangeContractSum(null);
                }
                if (zx.getChangeContractSumNoTax() != null) {
                    zx.setContractSumNoTax(zx.getChangeContractSumNoTax());
                    zx.setChangeContractSumNoTax(null);
                }
                if (zx.getChangeQty() != null) {
                    zx.setQty(zx.getChangeQty());
                    zx.setChangeQty(null);
                }
            }

            dbZxCtFsContract.setReviewDetailList(reviewDetails);
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getQiTaZxCtFsContractList(ZxCtFsContract zxCtFsContract) {
        if (zxCtFsContract == null) {
            zxCtFsContract = new ZxCtFsContract();
        }
        // 获取数据
        ZxCtFsContract dbZxCtFsContract = zxCtFsContractMapper.selectQiTa(zxCtFsContract);
        // 数据存在
        if (dbZxCtFsContract != null) {
            ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
            zxCtFsContractReviewDetail.setContractReviewId(dbZxCtFsContract.getContractReviewId());
            zxCtFsContractReviewDetail.setZxCtFsContractId(dbZxCtFsContract.getZxCtFsContractId());
            dbZxCtFsContract.setContractCost(new BigDecimal("0"));
            dbZxCtFsContract.setContractCostNoTax(new BigDecimal("0"));
            List<ZxCtFsContractReviewDetail> reviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
            for (ZxCtFsContractReviewDetail zx : reviewDetails
            ) {
                if (zx.getChangeContractSum() != null) {
                    zx.setContractSum(zx.getChangeContractSum());
                    zx.setChangeContractSum(null);
                }
                if (zx.getChangeContractSumNoTax() != null) {
                    zx.setContractSumNoTax(zx.getChangeContractSumNoTax());
                    zx.setChangeContractSumNoTax(null);
                }
                if (zx.getChangeQty() != null) {
                    zx.setQty(zx.getChangeQty());
                    zx.setChangeQty(null);
                }
            }
            dbZxCtFsContract.setReviewDetailList(reviewDetails);
            return repEntity.ok(dbZxCtFsContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }


}
