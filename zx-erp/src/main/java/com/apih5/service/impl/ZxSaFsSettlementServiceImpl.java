package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.*;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.*;
import com.apih5.utils.DigitalConversionUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxSaFsSettlementService")
public class ZxSaFsSettlementServiceImpl implements ZxSaFsSettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementDetailService zxSaFsInventorySettlementDetailService;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementService zxSaFsInventorySettlementService;

    @Autowired(required = true)
    private ZxSaFsPaySettlementService zxSaFsPaySettlementService;

    @Autowired(required = true)
    private ZxSaFsPaySettlementDetailService zxSaFsPaySettlementDetailService;

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailService zxSaFsStatisticsDetailService;

    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    ZxSaFsInventorySettlementDetailMapper zxSaFsInventorySettlementDetailMapper;

    @Autowired(required = true)
    ZxSaFsInventorySettlementMapper zxSaFsInventorySettlementMapper;

    @Autowired(required = true)
    ZxSaFsPaySettlementMapper zxSaFsPaySettlementMapper;

    @Autowired(required = true)
    ZxSaFsStatisticsDetailMapper zxSaFsStatisticsDetailMapper;

    @Autowired(required = true)
    ZxCtFsContractBondMapper zxCtFsContractBondMapper;

    @Autowired(required = true)
    ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    ZxSaFsPaySettlementDetailMapper zxSaFsPaySettlementDetailMapper;


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public ResponseEntity getZxSaFsSettlementListByCondition(ZxSaFsSettlement zxSaFsSettlement) {
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSaFsSettlement.setComID("");
            zxSaFsSettlement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSaFsSettlement.setComID(zxSaFsSettlement.getOrgID());
            zxSaFsSettlement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSaFsSettlement.setOrgID(zxSaFsSettlement.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSaFsSettlement.getPage(), zxSaFsSettlement.getLimit());
        // 获取数据
        List<ZxSaFsSettlement> zxSaFsSettlementList = zxSaFsSettlementMapper.selectByZxSaFsSettlementList(zxSaFsSettlement);
        for (ZxSaFsSettlement zxSaFsSettlement1 : zxSaFsSettlementList
        ) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxSaFsSettlement1.getZxSaFsSettlementId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxSaFsSettlement1.setZxErpFileList(zxErpFileList);
            if (zxSaFsSettlement1.getPeriod() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(zxSaFsSettlement1.getPeriod().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long ts = date.getTime();
                zxSaFsSettlement1.setPeriodTime(ts);
            }

        }
        // 得到分页信息
        PageInfo<ZxSaFsSettlement> p = new PageInfo<>(zxSaFsSettlementList);

        return repEntity.okList(zxSaFsSettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsSettlementDetail(ZxSaFsSettlement zxSaFsSettlement) {
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }
        ZxSaFsSettlement dbZxSaFsSettlement = new ZxSaFsSettlement();
        // 获取数据
        if (zxSaFsSettlement.getZxSaFsSettlementId() != "") {
            dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        } else if (StrUtil.isNotEmpty(zxSaFsSettlement.getWorkId())) {
            dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByWorkId(zxSaFsSettlement.getWorkId());
        }


        // 数据存在
        if (dbZxSaFsSettlement != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxSaFsSettlement.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> ZhengWen = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxSaFsSettlement.setZxZhengWenFileList(ZhengWen);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(dbZxSaFsSettlement.getPeriod().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long ts = date.getTime();
            dbZxSaFsSettlement.setPeriodTime(ts);
            return repEntity.ok(dbZxSaFsSettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsSettlement.setZxSaFsSettlementId(UuidUtil.generate());
        zxSaFsSettlement.setCreateUserInfo(userKey, realName);
        // 变更期次类型
        String result = new SimpleDateFormat("yyyyMM").format(zxSaFsSettlement.getPeriodTime());
        zxSaFsSettlement.setPeriod(result);
        zxSaFsSettlement.setApih5FlowStatus("-1");
        List<ZxSaFsSettlement> zxSaFsSettlements = zxSaFsSettlementMapper.selectMaxPeriod(zxSaFsSettlement);
        if (zxSaFsSettlements.size() > 0) {
            return repEntity.layerMessage("no", "该期次不能小于已存在的结算期次！");
        }
        //同步初始化结算清单
        syncAddinventorySettlement(zxSaFsSettlement);
        //同步初始化支付项
        syncInitZxPaySettlement(zxSaFsSettlement);

        ZxSaFsInventorySettlement zxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectBysettlementKey(zxSaFsSettlement.getZxSaFsSettlementId());
        ZxSaFsSettlement zxSaFsSettlement1 = zxSaFsSettlementMapper.selectLeijiInfo(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId());
//        zxSaFsSettlement.setThisAmt(zxSaFsInventorySettlement.getThisAmt());
//        zxSaFsSettlement.setThisAmtNoTax(zxSaFsInventorySettlement.getThisAmtNoTax());
//        zxSaFsSettlement.setThisAmtTax(zxSaFsInventorySettlement.getThisAmtTax());
        if (zxSaFsSettlement1 != null) {
            zxSaFsSettlement.setTotalAmt(CalcUtils.calcAdd(zxSaFsSettlement1.getTotalAmt(), zxSaFsSettlement.getThisAmt()));
            zxSaFsSettlement.setTotalAmtNoTax(CalcUtils.calcAdd(zxSaFsSettlement1.getTotalAmtNoTax(), zxSaFsSettlement.getThisAmtNoTax()));
        }
        ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = new ZxSaFsStatisticsDetail();
        zxSaFsStatisticsDetail1.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsStatisticsDetail1.setStatisticsType("100700");
        zxSaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail1);

        zxSaFsSettlement.setThisPayAmt(new BigDecimal(zxSaFsStatisticsDetail1.getThisAmt()));
        zxSaFsSettlement.setTotalPayAmt(new BigDecimal(zxSaFsStatisticsDetail1.getTotalAmt()));
        int flag = zxSaFsSettlementMapper.insert(zxSaFsSettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 附件
            List<ZxErpFile> zxErpFileList = zxSaFsSettlement.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "附件上传失败！");
                    }
                }
            }
            if ("1".equals(zxSaFsSettlement.getIsFirst())) {
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                zxCtFsContract.setSettleType("结算开始执行");
                zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
            }


            return repEntity.ok("sys.data.sava", zxSaFsSettlement);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (dbZxSaFsSettlement != null && StrUtil.isNotEmpty(dbZxSaFsSettlement.getZxSaFsSettlementId())) {
            // 项目ID
            dbZxSaFsSettlement.setOrgID(zxSaFsSettlement.getOrgID());
            // 项目名称
            dbZxSaFsSettlement.setOrgName(zxSaFsSettlement.getOrgName());
            // 签认单编号
            dbZxSaFsSettlement.setSignedNo(zxSaFsSettlement.getSignedNo());
            // 结算期次
            String result = new SimpleDateFormat("yyyyMM").format(zxSaFsSettlement.getPeriodTime());
            dbZxSaFsSettlement.setPeriod(result);
            // 附属合同ID
            dbZxSaFsSettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
            // 合同编号
            dbZxSaFsSettlement.setContractNo(zxSaFsSettlement.getContractNo());
            // 合同名称
            dbZxSaFsSettlement.setContractName(zxSaFsSettlement.getContractName());
            // 乙方ID
            dbZxSaFsSettlement.setSecondID(zxSaFsSettlement.getSecondID());
            // 乙方名称
            dbZxSaFsSettlement.setSecondName(zxSaFsSettlement.getSecondName());
            // 填报日期
            dbZxSaFsSettlement.setReportDate(zxSaFsSettlement.getReportDate());
            // 是否首次结算
            dbZxSaFsSettlement.setIsFirst(zxSaFsSettlement.getIsFirst());
            // 签认单初始化顺序号
            dbZxSaFsSettlement.setInitSerialNumber(zxSaFsSettlement.getInitSerialNumber());
            // 完成下列内容的开始日期
            dbZxSaFsSettlement.setStartDate(zxSaFsSettlement.getStartDate());
            // 填报人
            dbZxSaFsSettlement.setReportPerson(zxSaFsSettlement.getReportPerson());
            // 附图及附表(可另附页计算)
            dbZxSaFsSettlement.setOtherInfo(zxSaFsSettlement.getOtherInfo());
            // 验收情况
            dbZxSaFsSettlement.setAppraisal(zxSaFsSettlement.getAppraisal());
            // 审核状态
            dbZxSaFsSettlement.setAuditStatus(zxSaFsSettlement.getAuditStatus());
            // 流水号
            dbZxSaFsSettlement.setSerialNumber(zxSaFsSettlement.getSerialNumber());
            // 最后编辑时间
            dbZxSaFsSettlement.setEditTime(zxSaFsSettlement.getEditTime());
            // 所属公司ID
            dbZxSaFsSettlement.setComID(zxSaFsSettlement.getComID());
            // 所属公司名称
            dbZxSaFsSettlement.setComName(zxSaFsSettlement.getComName());
            // 所属公司排序
            dbZxSaFsSettlement.setComOrders(zxSaFsSettlement.getComOrders());
            // 是否被引用
            dbZxSaFsSettlement.setUseCount(zxSaFsSettlement.getUseCount());
            // 是否最大期次
            dbZxSaFsSettlement.setIsMaxPeriod(zxSaFsSettlement.getIsMaxPeriod());
            // 结算单编号
            dbZxSaFsSettlement.setBillNo(zxSaFsSettlement.getBillNo());
            // 业务日期
            dbZxSaFsSettlement.setBusinessDate(zxSaFsSettlement.getBusinessDate());
            // 含税合同金额(万元)
            dbZxSaFsSettlement.setContractAmt(zxSaFsSettlement.getContractAmt());
            // 变更后含税合同金额(万元)
            dbZxSaFsSettlement.setChangeAmt(zxSaFsSettlement.getChangeAmt());
            // 结算类型
            dbZxSaFsSettlement.setBillType(zxSaFsSettlement.getBillType());
            // 本期结算含税金额(元)
            dbZxSaFsSettlement.setThisAmt(zxSaFsSettlement.getThisAmt());
            // 开累结算含税金额(元)
            dbZxSaFsSettlement.setTotalAmt(zxSaFsSettlement.getTotalAmt());
            // 本期清单结算金额(元)
            dbZxSaFsSettlement.setThisEquipAmt(zxSaFsSettlement.getThisEquipAmt());
            // 开累清单结算金额(元)
            dbZxSaFsSettlement.setTotalEquipAmt(zxSaFsSettlement.getTotalEquipAmt());
            // 本期支付金额(元)
            dbZxSaFsSettlement.setThisPayAmt(zxSaFsSettlement.getThisPayAmt());
            // 开累支付金额(元)
            dbZxSaFsSettlement.setTotalPayAmt(zxSaFsSettlement.getTotalPayAmt());
            // 是否完工后结算
            dbZxSaFsSettlement.setIsFished(zxSaFsSettlement.getIsFished());
            // 本期结算不含税金额
            dbZxSaFsSettlement.setThisAmtNoTax(zxSaFsSettlement.getThisAmtNoTax());
            // 本期结算税额
            dbZxSaFsSettlement.setThisAmtTax(zxSaFsSettlement.getThisAmtTax());
            // 引用签认单未审核数量
            dbZxSaFsSettlement.setUseSignedOrder(zxSaFsSettlement.getUseSignedOrder());
            // 税率(%)
            dbZxSaFsSettlement.setTaxRate(zxSaFsSettlement.getTaxRate());
            // 是否抵扣
            dbZxSaFsSettlement.setIsDeduct(zxSaFsSettlement.getIsDeduct());
            // 物资结算表ID
            dbZxSaFsSettlement.setBillID(zxSaFsSettlement.getBillID());
            // 合同类型
            dbZxSaFsSettlement.setContrType(zxSaFsSettlement.getContrType());
            // 结算期限开始时间
            dbZxSaFsSettlement.setBeginDate(zxSaFsSettlement.getBeginDate());
            // 结算期限结束时间
            dbZxSaFsSettlement.setEndDate(zxSaFsSettlement.getEndDate());
            // 备注
            dbZxSaFsSettlement.setRemarks(zxSaFsSettlement.getRemarks());
            // 排序
            dbZxSaFsSettlement.setSort(zxSaFsSettlement.getSort());
            dbZxSaFsSettlement.setCountPerson(zxSaFsSettlement.getCountPerson());
            dbZxSaFsSettlement.setReCountPerson(zxSaFsSettlement.getCountPerson());
            dbZxSaFsSettlement.setContent(zxSaFsSettlement.getContent());

            dbZxSaFsSettlement.setApih5FlowStatus(zxSaFsSettlement.getApih5FlowStatus());
            dbZxSaFsSettlement.setApih5FlowNodeStatus(zxSaFsSettlement.getApih5FlowNodeStatus());
            dbZxSaFsSettlement.setApih5FlowId(zxSaFsSettlement.getApih5FlowId());
            dbZxSaFsSettlement.setWorkId(zxSaFsSettlement.getWorkId());

            if (StrUtil.equals("opinionField1", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField1(zxSaFsSettlement.getOpinionContent(realName,
                        dbZxSaFsSettlement.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField2(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField3(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField4(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField5(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField6(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField7(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField8(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField9(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxSaFsSettlement.getOpinionField(), true)) {
                dbZxSaFsSettlement.setOpinionField10(zxSaFsSettlement.getOpinionContent(realName, dbZxSaFsSettlement.getOpinionField10()));
            }


            // 共通
            dbZxSaFsSettlement.setModifyUserInfo(userKey, realName);

            flag = zxSaFsSettlementMapper.updateByPrimaryKey(dbZxSaFsSettlement);
            //附件先删除再新增
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxSaFsSettlement.getZxErpFileList() != null && zxSaFsSettlement.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxSaFsSettlement.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxSaFsSettlement.getZxSaFsSettlementId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            if ("2".equals(zxSaFsSettlement.getApih5FlowStatus()) && "1".equals(zxSaFsSettlement.getBillType())) {
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                zxCtFsContract.setSettleType("已最终结算");
                zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
            }
            return repEntity.ok("sys.data.update", zxSaFsSettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsSettlement(List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSaFsSettlementList != null && zxSaFsSettlementList.size() > 0) {
            for (ZxSaFsSettlement zxSaFsSett : zxSaFsSettlementList) {
                if (StrUtil.isNotEmpty(zxSaFsSett.getWorkId())) {
                    jsonArr.add(zxSaFsSett.getWorkId());
                }
            }
            ZxSaFsSettlement zxSaFsSettlement = new ZxSaFsSettlement();
            zxSaFsSettlement.setModifyUserInfo(userKey, realName);
            synDelete(zxSaFsSettlementList);
            flag = zxSaFsSettlementMapper.batchDeleteUpdateZxSaFsSettlement(zxSaFsSettlementList, zxSaFsSettlement);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            for (ZxSaFsSettlement zxSaFsSettlement : zxSaFsSettlementList
            ) {
                if ("1".equals(zxSaFsSettlement.getIsFirst())) {
                    ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
                    zxCtFsContract.setSettleType("实际无结算");
                    zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
                }
            }
            return repEntity.ok("sys.data.delete", zxSaFsSettlementList);
        }
    }

    //↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 新增附属结算的同时拉取附属合同清单数据和明细数据
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncAddinventorySettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxSaFsSettlement.getZxCtFsContractId());
        ZxSaFsInventorySettlement zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
        ZxCtFsContractReviewDetail zxCtFsContractReviewDetail = new ZxCtFsContractReviewDetail();
        zxCtFsContractReviewDetail.setZxCtFsContractId(zxCtFsContract.getZxCtFsContractId());
        zxCtFsContractReviewDetail.setContractReviewId(zxCtFsContract.getContractReviewId());
        List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetails = zxCtFsContractReviewDetailMapper.selectReviewDetailList(zxCtFsContractReviewDetail);
        zxSaFsInventorySettlement.setContractAmt(zxCtFsContract.getContractCost());
        zxSaFsInventorySettlement.setChangeAmt(zxCtFsContract.getAlterContractSum());
        zxSaFsInventorySettlement.setZxSaFsInventorySettlementId(UuidUtil.generate());
        zxSaFsInventorySettlement.setCreateUserInfo(userKey, realName);
        BigDecimal thisAmt = new BigDecimal("0");
        BigDecimal thisAmtNoTax = new BigDecimal("0");
        BigDecimal thisAmtTax = new BigDecimal("0");
        BigDecimal upAmt = new BigDecimal("0");
        BigDecimal contractSum = new BigDecimal("0");
        BigDecimal changeContractSum = new BigDecimal("0");
        int flag = 0;
        for (ZxCtFsContractReviewDetail item : zxCtFsContractReviewDetails) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            thisAmt = CalcUtils.compareToZero(item.getChangeContractSum()) == 0 ?
                    CalcUtils.calcAdd(item.getContractSum(), thisAmt) : CalcUtils.calcAdd(item.getChangeContractSum(), thisAmt);
            thisAmtNoTax = CalcUtils.compareToZero(item.getChangeContractSumNoTax()) == 0 ?
                    CalcUtils.calcAdd(item.getContractSumNoTax(), thisAmtNoTax) : CalcUtils.calcAdd(item.getChangeContractSumNoTax(), thisAmt);

            thisAmtTax = CalcUtils.compareToZero(item.getChangeContractSum()) == 0 ?
                    CalcUtils.calcAdd(CalcUtils.calcSubtract(item.getContractSum(), item.getContractSumNoTax()), thisAmtTax) :
                    CalcUtils.calcAdd(CalcUtils.calcSubtract(item.getChangeContractSum(), item.getChangeContractSumNoTax()), thisAmtTax);

            contractSum = CalcUtils.calcAdd(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"), 6), contractSum);
            changeContractSum = CalcUtils.calcAdd(CalcUtils.calcDivide(item.getChangeContractSum(), new BigDecimal("10000"), 6), changeContractSum);

            //没有序号 没有签认单 没有签认单那明细ID 起租日期 本期结算数量 本期结算单价(元) 本期结算含税金额(元) 本期结算不含税金额(元) 本期结算税额(元)

            zxSaFsInventorySettlementDetail.setZxSaFsEnumerationSettlementDetailedId(UuidUtil.generate());
            zxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(zxSaFsInventorySettlement.getZxSaFsInventorySettlementId());//清单主表id
            zxSaFsInventorySettlementDetail.setPeriod(zxSaFsSettlement.getPeriod());// 结算期次
            zxSaFsInventorySettlementDetail.setBillNo(zxSaFsSettlement.getBillNo());// 结算单编号
            zxSaFsInventorySettlementDetail.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());//附属合同ID
            zxSaFsInventorySettlementDetail.setSignedNo(zxSaFsSettlement.getSignedNo());//签认单编号
            zxSaFsInventorySettlementDetail.setConRevDetailId(item.getConRevDetailId());//清单ID(合同明细ID)
            zxSaFsInventorySettlementDetail.setEquipCode(item.getWorkNo());//清单编号
            zxSaFsInventorySettlementDetail.setEquipName(item.getWorkName());//清单名称
            zxSaFsInventorySettlementDetail.setSpec(item.getSpec());//型号
            zxSaFsInventorySettlementDetail.setUnit(item.getUnit());// 计量单位
            zxSaFsInventorySettlementDetail.setContractPrice(item.getPrice());// 含税单价(元)
            zxSaFsInventorySettlementDetail.setContractQty(item.getQty()==null?null:new BigDecimal(item.getQty())); // 合同数量
            zxSaFsInventorySettlementDetail.setContractAmt(item.getContractSum());//含税合同金额
            if (item.getChangeQty() != null) {
                zxSaFsInventorySettlementDetail.setChangedQty(item.getChangeQty()==null?null:new BigDecimal((item.getChangeQty()))); // 变更后数量
            }
            zxSaFsInventorySettlementDetail.setChangedAmt(item.getChangeContractSum()); // 变更后含税金额(元)
            zxSaFsInventorySettlementDetail.setDelFlag("0");
            ZxSaFsInventorySettlementDetail SettlementDetail = new ZxSaFsInventorySettlementDetail();

            SettlementDetail = zxSaFsInventorySettlementDetailMapper.selectUpInfo(zxSaFsSettlement.getPeriod(), item.getConRevDetailId());

            if (SettlementDetail != null) {
                zxSaFsInventorySettlementDetail.setUpQty(SettlementDetail.getThisQty());// 至上期末累计结算数量
                zxSaFsInventorySettlementDetail.setUpAmt(SettlementDetail.getThisAmt());// 至上期末累计结算含税金额(元)
                zxSaFsInventorySettlementDetail.setTotalQty(SettlementDetail.getThisQty());// 至本期末累计结算数量
                zxSaFsInventorySettlementDetail.setTotalAmt(SettlementDetail.getThisAmt());// 至本期末累计结算含税金额(元)
            }
            zxSaFsInventorySettlementDetail.setComID(zxSaFsSettlement.getComID());// 所属公司ID
            zxSaFsInventorySettlementDetail.setComName(zxSaFsSettlement.getComName());//所属公司名称
            zxSaFsInventorySettlementDetail.setComOrders(zxSaFsSettlement.getComOrders());// 所属公司排序
            zxSaFsInventorySettlementDetail.setAlterPrice(item.getChangePrice());// 变更后单价(元)
            zxSaFsInventorySettlementDetail.setTaxRate(item.getTaxRate());// 税率(%)
            flag = zxSaFsInventorySettlementDetailMapper.insert(zxSaFsInventorySettlementDetail);
            if (flag == 0) {
                throw new Apih5Exception("拉取附属合同清单明细失败!");
            }

            upAmt = CalcUtils.calcAdd(zxSaFsInventorySettlementDetail.getUpAmt(), upAmt);

        }
        //  签认单ID
        //zxSaFsInventorySettlement.setThisAmt(thisAmt);// 本期清单结算含税金额(元)
        //zxSaFsInventorySettlement.setThisAmtNoTax(thisAmtNoTax);//本期清单结算不含税金额(元)
        //zxSaFsInventorySettlement.setThisAmtTax(thisAmtTax);//本期清单结算税额(元)
        ZxSaFsInventorySettlement zxSaFsInventorySettlement1 = new ZxSaFsInventorySettlement();
        zxSaFsInventorySettlement1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsInventorySettlement1.setPeriod(zxSaFsSettlement.getPeriod());
        zxSaFsInventorySettlement1 = zxSaFsInventorySettlementMapper.countTotalAmt(zxSaFsInventorySettlement1);
        if (zxSaFsInventorySettlement1 != null) {
            zxSaFsInventorySettlement.setTotalAmt(zxSaFsInventorySettlement1.getTotalAmt());//累计清单结算金额(元)
            zxSaFsInventorySettlement.setUpAmt(zxSaFsInventorySettlement1.getTotalAmt());//上期末清单结算金额
        }


        zxSaFsInventorySettlement.setOrgID(zxSaFsSettlement.getOrgID());// 项目ID
        zxSaFsInventorySettlement.setOrgName(zxSaFsSettlement.getOrgName());// 项目名称
        zxSaFsInventorySettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());//附属合同ID
        zxSaFsInventorySettlement.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());//附属类结算表ID
        zxSaFsInventorySettlement.setBillNo(zxSaFsSettlement.getBillNo());// 结算单编号
        zxSaFsInventorySettlement.setPeriod(zxSaFsSettlement.getPeriod());// 结算期次
        zxSaFsInventorySettlement.setSignedNos(zxSaFsSettlement.getSignedNo());// 签认单编号
        zxSaFsInventorySettlement.setContractAmt(contractSum);// 含税合同金额(万元)
        //zxSaFsInventorySettlement.setChangeAmt(changeContractSum);// 变更后含税合同金额(万元)
        zxSaFsInventorySettlement.setComID(zxSaFsSettlement.getComID()); // 所属公司ID
        zxSaFsInventorySettlement.setComName(zxSaFsSettlement.getComName());// 所属公司名称
        zxSaFsInventorySettlement.setComOrders(zxSaFsSettlement.getComOrders());// 所属公司排序
        zxSaFsInventorySettlement.setContrType(zxSaFsSettlement.getContrType());// 合同类型
        zxSaFsInventorySettlement.setIsDeduct(zxSaFsSettlement.getIsDeduct());// 是否抵扣
        zxSaFsInventorySettlement.setTaxRate(zxSaFsSettlement.getTaxRate());// 税率(%)
        zxSaFsInventorySettlement.setIsFirst(zxSaFsSettlement.getIsFirst());// 是否首次结算


        flag = zxSaFsInventorySettlementMapper.insert(zxSaFsInventorySettlement);
        if (flag == 0) {
            throw new Apih5Exception("拉取附属合同清单失败!");
        }
        ZxCtFsContractBond zxCtFsContractBond = new ZxCtFsContractBond();
        zxCtFsContractBond.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        List<ZxCtFsContractBond> zxCtFsContractBondlist = zxCtFsContractBondMapper.selectByZxCtFsContractBondList(zxCtFsContractBond);
        synInitStatistics(zxSaFsSettlement, "100100", "合计结算含税金额（小写）", "100100", null, null, 1);
        synInitStatistics(zxSaFsSettlement, "100110", "合计结算不含税金额（小写）", "100110", null, null, 2);
        synInitStatistics(zxSaFsSettlement, "100120", "合计结算税额（小写）", "100120", null, null, 3);

        synInitStatistics(zxSaFsSettlement, "100200", "合计结算含税金额（大写）", "100200", null, null, 4);
        synInitStatistics(zxSaFsSettlement, "100210", "合计结算不含税金额（大写）", "100210", null, null, 5);
        synInitStatistics(zxSaFsSettlement, "100220", "合计结算税额（大写）", "100220", null, null, 6);

        synInitStatistics(zxSaFsSettlement, "100300", "其中扣除保证金合计", "100300", null, null, 7);
        int i = 8;
        if (zxCtFsContractBondlist.size() != 0) {
            for (ZxCtFsContractBond Bond : zxCtFsContractBondlist) {
                synInitStatistics(zxSaFsSettlement, "", "", "", Bond, 1, i);
                i++;
            }

        }
        synInitStatistics(zxSaFsSettlement, "100300", "返还保证金合计", "100500", null, null, i);
        int j = i + 1;
        if (zxCtFsContractBondlist.size() != 0) {
            for (ZxCtFsContractBond Bond : zxCtFsContractBondlist) {
                synInitStatistics(zxSaFsSettlement, "", "", "", Bond, 2, j);
                j++;
            }
        }
        synInitStatistics(zxSaFsSettlement, "100700", "应付合同款（小写）", "100700", null, null, j);
        synInitStatistics(zxSaFsSettlement, "100800", "应付合同款（大写）", "100800", null, null, j + 1);

    }

    /**
     * 新增附属结算的同时初始化支付项
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncInitZxPaySettlement(ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        //通过结算单ID查询支付项数据
        BigDecimal totalAmt = zxSaFsPaySettlementMapper.sumThisAmt(zxSaFsSettlement.getZxCtFsContractId(), zxSaFsSettlement.getPeriod());
        totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;

        ZxSaFsPaySettlement zxSaFsPaySettlement = new ZxSaFsPaySettlement();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsPaySettlement.setZxSaFsPaySettlementId(UuidUtil.generate());
        zxSaFsPaySettlement.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsPaySettlement.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsPaySettlement.setCreateUserInfo(userKey, realName);
        zxSaFsPaySettlement.setTotalAmt(totalAmt);
        int flag = 0;
        flag = zxSaFsPaySettlementMapper.insert(zxSaFsPaySettlement);
        if (flag == 0) {
            throw new Apih5Exception("拉取附属合同清单失败!");
        }
    }

    /**
     * 新增附属结算的同时初始化统计项
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void synInitStatistics(ZxSaFsSettlement zxSaFsSettlement, String statisticsNo, String statisticsName, String statisticsTyp, ZxCtFsContractBond zxCtFsContractBond, Integer type, int sortFlag) throws Exception {
        int flag = 0;

        ZxSaFsStatisticsDetail zxSaFsStatisticsDetails = new ZxSaFsStatisticsDetail();
        ZxSaFsStatisticsDetail zxSaFsStatisticsDetails1 = new ZxSaFsStatisticsDetail();
        zxSaFsStatisticsDetails.setZxSaFsStatisticsDetailId(UuidUtil.generate());
        zxSaFsStatisticsDetails.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
        zxSaFsStatisticsDetails.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
        zxSaFsStatisticsDetails.setPeriod(zxSaFsSettlement.getPeriod());
        zxSaFsStatisticsDetails.setOrgID(zxSaFsSettlement.getOrgID());
        zxSaFsStatisticsDetails.setComID(zxSaFsSettlement.getComID());
        zxSaFsStatisticsDetails.setComName(zxSaFsSettlement.getComName());
        zxSaFsStatisticsDetails.setComOrders(zxSaFsSettlement.getComOrders());
        zxSaFsStatisticsDetails.setDelFlag("0");
        BigDecimal thisAmt = new BigDecimal("0");
        zxSaFsStatisticsDetails.setTotalAmt("0");
        if (zxCtFsContractBond == null) {
            zxSaFsStatisticsDetails.setStatisticsNo(statisticsNo);
            if ("100300".equals(statisticsTyp)) {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo);
                zxSaFsStatisticsDetails1.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails1.setStatisticsType("100300");
                zxSaFsStatisticsDetails1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
                //zxSaFsStatisticsDetails1.setSort(1);
                zxSaFsStatisticsDetails.setThisAmt("0");
                zxSaFsStatisticsDetails.setTotalAmt("0");
                ZxSaFsStatisticsDetail SaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetails1);
                if (SaFsStatisticsDetail1 != null) {
                    if (SaFsStatisticsDetail1.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(SaFsStatisticsDetail1.getAmt()));
                    }
                }

            }
            if ("100500".equals(statisticsTyp)) {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo + "_RETURN");
                zxSaFsStatisticsDetails1.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails1.setStatisticsType("100500");
                zxSaFsStatisticsDetails1.setZxCtFsContractId(zxSaFsSettlement.getZxCtFsContractId());
                // zxSaFsStatisticsDetails1.setSort(2);
                zxSaFsStatisticsDetails.setThisAmt("0");
                zxSaFsStatisticsDetails.setTotalAmt("0");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = zxSaFsStatisticsDetailMapper.selectBaoZhengJinSum(zxSaFsStatisticsDetails1);
                if (zxSaFsStatisticsDetail1 != null) {
                    if (zxSaFsStatisticsDetail1.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(zxSaFsStatisticsDetail1.getAmt()));
                    }
                }

            } else {
                zxSaFsStatisticsDetails.setStatisticsID(zxSaFsSettlement.getZxCtFsContractId() + statisticsNo);
                if ("100200".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmt() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmt();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100100");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100210".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtNoTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtNoTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100110");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100220".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), "100120");
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100100".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmt() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmt();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100110".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtNoTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtNoTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100120".equals(statisticsTyp)) {
                    thisAmt = zxSaFsSettlement.getThisAmtTax() == null ? new BigDecimal("0") : zxSaFsSettlement.getThisAmtTax();
                    BigDecimal totalAmt = zxSaFsStatisticsDetailMapper.selectKaiLei(zxSaFsSettlement.getPeriod(), zxSaFsSettlement.getZxCtFsContractId(), statisticsTyp);
                    totalAmt = totalAmt == null ? new BigDecimal("0") : totalAmt;
                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(thisAmt));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, thisAmt)));
                } else if ("100700".equals(statisticsTyp)) {
                    ZxSaFsStatisticsDetail amt = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail kouChu = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail fanhuan = new ZxSaFsStatisticsDetail();
                    fanhuan.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    fanhuan.setPeriod(zxSaFsSettlement.getPeriod());
                    fanhuan.setStatisticsType("100500");
                    kouChu.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    kouChu.setPeriod(zxSaFsSettlement.getPeriod());
                    kouChu.setStatisticsType("100300");
                    amt.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    amt.setPeriod(zxSaFsSettlement.getPeriod());
                    amt.setStatisticsType("100100");
                    amt = zxSaFsStatisticsDetailMapper.selectByContractId(amt);
                    kouChu = zxSaFsStatisticsDetailMapper.selectByContractId(kouChu);
                    fanhuan = zxSaFsStatisticsDetailMapper.selectByContractId(fanhuan);

                    zxSaFsStatisticsDetails.setThisAmt(String.valueOf(CalcUtils.calcSubtract(new BigDecimal(amt.getThisAmt()), new BigDecimal(kouChu.getThisAmt()))));
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(amt.getTotalAmt()), new BigDecimal(kouChu.getTotalAmt())), new BigDecimal(fanhuan.getTotalAmt()))));
                } else if ("100800".equals(statisticsTyp)) {
                    ZxSaFsStatisticsDetail amt = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail kouChu = new ZxSaFsStatisticsDetail();
                    ZxSaFsStatisticsDetail fanhuan = new ZxSaFsStatisticsDetail();
                    fanhuan.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    fanhuan.setPeriod(zxSaFsSettlement.getPeriod());
                    fanhuan.setStatisticsType("100500");
                    kouChu.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    kouChu.setPeriod(zxSaFsSettlement.getPeriod());
                    kouChu.setStatisticsType("100300");
                    amt.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                    amt.setPeriod(zxSaFsSettlement.getPeriod());
                    amt.setStatisticsType("100100");
                    amt = zxSaFsStatisticsDetailMapper.selectByContractId(amt);
                    kouChu = zxSaFsStatisticsDetailMapper.selectByContractId(kouChu);
                    fanhuan = zxSaFsStatisticsDetailMapper.selectByContractId(fanhuan);

                    zxSaFsStatisticsDetails.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(new BigDecimal(amt.getThisAmt()), new BigDecimal(kouChu.getThisAmt()))));
                    zxSaFsStatisticsDetails.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(amt.getTotalAmt()), new BigDecimal(kouChu.getTotalAmt())), new BigDecimal(fanhuan.getTotalAmt()))));
                }
            }
            zxSaFsStatisticsDetails.setStatisticsName(statisticsName);
            zxSaFsStatisticsDetails.setStatisticsType(statisticsTyp);
        } else {
            //扣除保证金及扣除合计
            if (type == 1) {
                zxSaFsStatisticsDetails.setThisAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getThisAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                //统计开累保证金
                ZxSaFsStatisticsDetail baoZhengJin = new ZxSaFsStatisticsDetail();
                baoZhengJin.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());
                baoZhengJin.setSort(1);
                baoZhengJin.setPeriod(zxSaFsSettlement.getPeriod());

                baoZhengJin = zxSaFsStatisticsDetailMapper.selectBzjTotal(baoZhengJin);
                if (baoZhengJin != null) {
                    if (baoZhengJin.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcAdd(baoZhengJin.getAmt(), CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6)))));
                    } else {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                    }
                } else {
                    zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(CalcUtils.calcMultiply(zxSaFsSettlement.getTotalAmt(), CalcUtils.calcDivide(zxCtFsContractBond.getStatisticsRate(), new BigDecimal("100"), 6))));
                }

                zxSaFsStatisticsDetails.setRate(zxCtFsContractBond.getStatisticsRate());
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails2 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                zxSaFsStatisticsDetails2.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails2.setStatisticsType("100300");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails3 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails3 = zxSaFsStatisticsDetailMapper.selectByContractId(zxSaFsStatisticsDetails2);

                BigDecimal thisAmt1 = zxSaFsStatisticsDetails3.getThisAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getThisAmt());
                BigDecimal totalAmt1 = zxSaFsStatisticsDetails3.getTotalAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getTotalAmt());
                zxSaFsStatisticsDetails3.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getThisAmt()), thisAmt1)));
                zxSaFsStatisticsDetails3.setTotalAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getTotalAmt()), totalAmt1)));
                zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetails3);

            }
            //返还保证金以及返还合计
            if (type == 2) {
                //统计开累保证金
                ZxSaFsStatisticsDetail baoZhengJin = new ZxSaFsStatisticsDetail();
                baoZhengJin.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());
                baoZhengJin.setSort(2);
                baoZhengJin.setPeriod(zxSaFsSettlement.getPeriod());
                baoZhengJin = zxSaFsStatisticsDetailMapper.selectBzjTotal(baoZhengJin);

                if (baoZhengJin != null) {
                    if (baoZhengJin.getAmt() != null) {
                        zxSaFsStatisticsDetails.setTotalAmt(String.valueOf(baoZhengJin.getAmt()));
                    }
                }

                zxSaFsStatisticsDetails.setRate(zxCtFsContractBond.getStatisticsRate());
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails2 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails2.setZxSaFsSettlementId(zxSaFsSettlement.getZxSaFsSettlementId());
                zxSaFsStatisticsDetails2.setPeriod(zxSaFsSettlement.getPeriod());
                zxSaFsStatisticsDetails2.setStatisticsType("100500");
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetails3 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetails3 = zxSaFsStatisticsDetailMapper.selectByContractId(zxSaFsStatisticsDetails2);

                BigDecimal thisAmt1 = zxSaFsStatisticsDetails3.getThisAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getThisAmt());
                BigDecimal totalAmt1 = zxSaFsStatisticsDetails3.getTotalAmt() == null ? new BigDecimal("0") : new BigDecimal(zxSaFsStatisticsDetails3.getTotalAmt());
                //zxSaFsStatisticsDetails3.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getThisAmt()),thisAmt1)));
                zxSaFsStatisticsDetails3.setTotalAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetails.getTotalAmt()), totalAmt1)));
                zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetails3);
            }
            zxSaFsStatisticsDetails.setSort(type);
            zxSaFsStatisticsDetails.setStatisticsNo(zxCtFsContractBond.getStatisticsNo());
            zxSaFsStatisticsDetails.setStatisticsName(zxCtFsContractBond.getStatisticsName());
            zxSaFsStatisticsDetails.setStatisticsID(zxCtFsContractBond.getZxCtFsContractBondId());

        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsStatisticsDetails.setCreateUserInfo(userKey, realName);
        zxSaFsStatisticsDetails.setSortFlag(sortFlag);
        flag = zxSaFsStatisticsDetailMapper.insert(zxSaFsStatisticsDetails);
        if (flag == 0) {
            throw new Apih5Exception("更新统计项失败!");
        }
    }


    /**
     * 导出附属合同补充协议列表
     *
     * @param response
     * @author suncg
     */

    @Override
    public ResponseEntity exportZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxSaFsSettlement == null) {
            zxSaFsSettlement = new ZxSaFsSettlement();
        }

        // 获取数据
        List<ZxSaFsSettlement> zxSaFsSettlementList = zxSaFsSettlementMapper.selectByZxSaFsSettlementList(zxSaFsSettlement);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("结算编号", "结算期次", "结算类型", "审核状态", "本期结算含税金额", "开累结算含税金额",
                "本期应支付含税金额", "开累应支付含税金额");
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxSaFsSettlementList != null && zxSaFsSettlementList.size() > 0) {
            for (ZxSaFsSettlement zxSaFsSettlement1 : zxSaFsSettlementList) {
                rowsList.add(CollUtil.newArrayList(zxSaFsSettlement1.getContractNo(),
                        zxSaFsSettlement1.getContractNo(),
                        zxSaFsSettlement1.getPeriod(),
                        zxSaFsSettlement1.getBillType(),
                        zxSaFsSettlement1.getApih5FlowStatus(),
                        zxSaFsSettlement1.getThisAmt(),
                        zxSaFsSettlement1.getTotalAmt(),
                        zxSaFsSettlement1.getThisPayAmt(),
                        zxSaFsSettlement1.getTotalPayAmt()
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

    /**
     * @suncg 查询初始化顺序号
     */
    @Override
    public ResponseEntity countByContract(ZxSaFsSettlement zxSaFsSettlement) {
        ZxSaFsSettlement zxSaFsSettlement1 = zxSaFsSettlementMapper.selectCountByContract(zxSaFsSettlement);
        if (zxSaFsSettlement1 == null) {
            zxSaFsSettlement1 = new ZxSaFsSettlement();
            zxSaFsSettlement1.setInitSerialNumber("0");
        } else {
            if ("".equals(zxSaFsSettlement1.getInitSerialNumber())) {
                zxSaFsSettlement1.setInitSerialNumber("0");
            }
        }
        return repEntity.ok(zxSaFsSettlement1);
    }

    /**
     * 删除结算单时同步删除其他表数据
     *
     * @param zxSaFsSettlementList
     * @suncg
     */

    @Transactional(rollbackFor = Exception.class)
    public void synDelete(List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception {
        for (ZxSaFsSettlement zx : zxSaFsSettlementList
        ) {
            //删除清单主表和子表
            ZxSaFsInventorySettlement zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
            zxSaFsInventorySettlement.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsInventorySettlement> inventoryList = zxSaFsInventorySettlementMapper.selectByZxSaFsInventorySettlementList(zxSaFsInventorySettlement);
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            if (inventoryList.size() > 0) {
                zxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(inventoryList.get(0).getZxSaFsInventorySettlementId());
                List<ZxSaFsInventorySettlementDetail> InvertoryDetailList = zxSaFsInventorySettlementDetailMapper.selectByZxSaFsInventorySettlementDetailList(zxSaFsInventorySettlementDetail);
                if (InvertoryDetailList.size() > 0) {
                    zxSaFsInventorySettlementDetailService.batchDeleteUpdateZxSaFsInventorySettlementDetail(InvertoryDetailList);
                }
                zxSaFsInventorySettlementService.batchDeleteUpdateZxSaFsInventorySettlement(inventoryList);
            }


            //删除支付项主表和子表
            ZxSaFsPaySettlement zxSaFsPaySettlement = new ZxSaFsPaySettlement();
            zxSaFsPaySettlement.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsPaySettlement> zxSaFsPaySettlementList = zxSaFsPaySettlementMapper.selectByZxSaFsPaySettlementList(zxSaFsPaySettlement);

            ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
            if (zxSaFsPaySettlementList.size() > 0) {
                zxSaFsPaySettlementDetail.setZxSaFsPaySettlementId(zxSaFsPaySettlementList.get(0).getZxSaFsPaySettlementId());
                List<ZxSaFsPaySettlementDetail> payDetailList = zxSaFsPaySettlementDetailMapper.selectByZxSaFsPaySettlementDetailList(zxSaFsPaySettlementDetail);
                if (payDetailList.size() > 0) {
                    zxSaFsPaySettlementDetailService.batchDeleteUpdateZxSaFsPaySettlementDetail(payDetailList);
                }
                zxSaFsPaySettlementService.batchDeleteUpdateZxSaFsPaySettlement(zxSaFsPaySettlementList);
            }
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = new ZxSaFsStatisticsDetail();
            zxSaFsStatisticsDetail1.setZxSaFsSettlementId(zx.getZxSaFsSettlementId());
            List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList = zxSaFsStatisticsDetailMapper.selectByZxSaFsStatisticsDetailList(zxSaFsStatisticsDetail1);
            if (zxSaFsStatisticsDetailList.size() > 0) {
                zxSaFsStatisticsDetailService.batchDeleteUpdateZxSaFsStatisticsDetail(zxSaFsStatisticsDetailList);
            }
        }
    }

    @Override
    public ResponseEntity getZxSaFsSettlementReport(ZxSaFsSettlement zxSaFsSettlement) {

        // 获取数据
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());

        // 数据存在
        if (dbZxSaFsSettlement != null) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            zxSaFsInventorySettlementDetail.setZxSaFsSettlementId(dbZxSaFsSettlement.getZxSaFsSettlementId());
            zxSaFsInventorySettlementDetail.setZxCtFsContractId(dbZxSaFsSettlement.getZxCtFsContractId());
            List<ZxSaFsInventorySettlementDetail> ygzList=zxSaFsInventorySettlementDetailMapper.YgzYiLan(zxSaFsInventorySettlementDetail);
            ZxSaFsInventorySettlementDetail xiaoji = new ZxSaFsInventorySettlementDetail();
            ZxSaFsInventorySettlementDetail heji = new ZxSaFsInventorySettlementDetail();
            BigDecimal contractSum =new BigDecimal("0");
            BigDecimal changeContractSum =new BigDecimal("0");
            BigDecimal upAmt =new BigDecimal("0");
            BigDecimal thisAmt =new BigDecimal("0");
            for (ZxSaFsInventorySettlementDetail zd: ygzList
                 ) {
                contractSum=CalcUtils.calcAdd(contractSum,zd.getContractSum()) ;
                changeContractSum=CalcUtils.calcAdd(changeContractSum,zd.getChangeContractSum()) ;
                upAmt=CalcUtils.calcAdd(upAmt,zd.getUpAmt()) ;
                thisAmt=CalcUtils.calcAdd(thisAmt,zd.getThisAmt()) ;

            }
            xiaoji.setEquipCode("小计");
            xiaoji.setContractSum(contractSum);
            xiaoji.setChangeContractSum(changeContractSum);
            xiaoji.setUpAmt(upAmt);
            xiaoji.setThisAmt(thisAmt);

            List<ZxSaFsInventorySettlementDetail> DzInfo = zxSaFsInventorySettlementDetailMapper.getDzInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> JfInfo = zxSaFsInventorySettlementDetailMapper.getJfInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> QtInfo = zxSaFsInventorySettlementDetailMapper.getQtInfo(zxSaFsInventorySettlementDetail);
            List<ZxSaFsInventorySettlementDetail> YzInfo = zxSaFsInventorySettlementDetailMapper.getYzInfo(zxSaFsInventorySettlementDetail);

            heji.setEquipCode("合计");

            BigDecimal contractSumhj =new BigDecimal("0");
            BigDecimal changeContractSumhj =new BigDecimal("0");
            BigDecimal upAmthj =new BigDecimal("0");
            BigDecimal thisAmthj =new BigDecimal("0");
            for (ZxSaFsInventorySettlementDetail Dz:DzInfo
                 ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Dz.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Dz.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Dz.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Dz.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail jf:JfInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,jf.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,jf.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,jf.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,jf.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail Qt:QtInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Qt.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Qt.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Qt.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Qt.getThisAmt()) ;
            }
            for (ZxSaFsInventorySettlementDetail Yz:YzInfo
            ) {
                contractSumhj=CalcUtils.calcAdd(contractSumhj,Yz.getContractSum());
                changeContractSumhj=CalcUtils.calcAdd(changeContractSumhj,Yz.getChangeContractSum()) ;
                upAmthj=CalcUtils.calcAdd(upAmthj,Yz.getUpAmt()) ;
                thisAmthj=CalcUtils.calcAdd(thisAmthj,Yz.getThisAmt()) ;
            }

            heji.setContractSum(contractSumhj);
            heji.setChangeContractSum(changeContractSumhj);
            heji.setUpAmt(upAmthj);
            heji.setThisAmt(thisAmthj);

            ygzList.add(xiaoji);
            ygzList.addAll(DzInfo);
            ygzList.addAll(JfInfo);
            ygzList.addAll(QtInfo);
            ygzList.addAll(YzInfo);
            ygzList.add(heji);
            dbZxSaFsSettlement.setuReportList(ygzList);
            return repEntity.ok(dbZxSaFsSettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity addZxSaFsSettlementApplyFlow(ZxSaFsSettlement zxSaFsSettlement) {


        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        System.out.println("1111");
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZxSaFsSettlement dbZxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (dbZxSaFsSettlement != null && StrUtil.isNotEmpty(dbZxSaFsSettlement.getZxSaFsSettlementId())) {
            dbZxSaFsSettlement.setApih5FlowStatus(zxSaFsSettlement.getApih5FlowStatus());
            //未评审:0;正在评审:1;评审通过:2;评审不通过:3;重新评审:4;评审终止:5;评审通过:6
            dbZxSaFsSettlement.setWorkId(zxSaFsSettlement.getWorkId());
            dbZxSaFsSettlement.setModifyUserInfo(userKey, realName);
            zxSaFsSettlementMapper.updateByPrimaryKey(dbZxSaFsSettlement);
            if (zxSaFsSettlement.getZxZhengWenFileList() != null && zxSaFsSettlement.getZxZhengWenFileList().size() > 0) {
                ZxErpFile file = new ZxErpFile();
                file.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
                file.setOtherType("2");
                List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
                file.setModifyUserInfo(userKey, realName);
                if (fileList.size() > 0) {
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
                }
                for (ZxErpFile zxErpFile : zxSaFsSettlement.getZxZhengWenFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxSaFsSettlement.getZxSaFsSettlementId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFile.setOtherType("2");
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        return repEntity.ok("sys.data.update", zxSaFsSettlement);
    }

}
