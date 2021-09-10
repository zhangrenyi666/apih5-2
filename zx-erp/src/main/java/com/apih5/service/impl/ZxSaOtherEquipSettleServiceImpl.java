package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.api.wechatutils.StringUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.ibm.icu.text.SimpleDateFormat;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSaOtherEquipSettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxSaOtherEquipSettleService")
public class ZxSaOtherEquipSettleServiceImpl implements ZxSaOtherEquipSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleMapper zxSaOtherEquipSettleMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleMapper zxSaOtherEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemMapper zxSaOtherEquipResSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleMapper zxSaOtherEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleItemMapper zxSaOtherEquipPaySettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemMapper zxSaOtherEquipSettleItemMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemServiceImpl zxSaOtherEquipSettleItemServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Override
    public ResponseEntity getZxSaOtherEquipSettleListByCondition(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSaOtherEquipSettle.setCompanyId("");
            zxSaOtherEquipSettle.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSaOtherEquipSettle.setCompanyId(zxSaOtherEquipSettle.getOrgId());
            zxSaOtherEquipSettle.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSaOtherEquipSettle.setOrgId(zxSaOtherEquipSettle.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipSettle.getPage(), zxSaOtherEquipSettle.getLimit());
        // 获取数据
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
        //查询附件
        for (ZxSaOtherEquipSettle zxSaOtherEquipSettle1 : zxSaOtherEquipSettleList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSaOtherEquipSettle1.getZxSaOtherEquipSettleId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSaOtherEquipSettle1.setZxErpFileList(zxErpFiles);
        }

        // 得到分页信息
        PageInfo<ZxSaOtherEquipSettle> p = new PageInfo<>(zxSaOtherEquipSettleList);

        return repEntity.okList(zxSaOtherEquipSettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleDetail(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        if(StrUtil.isNotEmpty(zxSaOtherEquipSettle.getWorkId())) {
            ZxSaOtherEquipSettle zxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
            zxSaOtherEquipSettle1.setWorkId(zxSaOtherEquipSettle.getWorkId());
            List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle1);
            if(zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() >0) {
                dbZxSaOtherEquipSettle = zxSaOtherEquipSettleList.get(0);
            }
        }else {
            // 获取数据
            dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        }
        // 数据存在
        if (dbZxSaOtherEquipSettle != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> fj = new ArrayList<>();
            List<ZxErpFile> zw = new ArrayList<>();
            for (ZxErpFile file : zxErpFiles) {
                //添加附件 0附件 1正文
                if ("0".equals(file.getOtherType())) {
                    fj.add(file);
                } else if ("1".equals(file.getOtherType())) {
                    zw.add(file);
                }
            }
            dbZxSaOtherEquipSettle.setZxErpFileList(fj);
            dbZxSaOtherEquipSettle.setDocumentFileList(zw);
            return repEntity.ok(dbZxSaOtherEquipSettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // 获取只有年月的时间
        String period = new SimpleDateFormat("yyyyMM").format(zxSaOtherEquipSettle.getPeriodDate());
        // 结算期次
        dbZxSaOtherEquipSettle.setPeriod(period);
        List<ZxSaOtherEquipSettle> checkPeriodList = zxSaOtherEquipSettleMapper.checkPeriod(dbZxSaOtherEquipSettle);
        if (checkPeriodList != null && checkPeriodList.size() > 0) {
            return repEntity.layerMessage("no", "该合同已存在大于本期的结算数据，请选择其他期次！");
        }

        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle1.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle1);
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            for (ZxSaOtherEquipSettle zxSaOtherEquipSettle1 : zxSaOtherEquipSettleList) {
                if (!"2".equals(zxSaOtherEquipSettle1.getApih5FlowStatus())) {
                    return repEntity.layerMessage("no", "该合同存在未审核的结算单数据，请先审核！");
                }
            }
        }
        // 其他合同管理表ID不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "其他合同管理表ID不能为空！");
        }
        zxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // 结算期次时间戳
        zxSaOtherEquipSettle.setPeriodDate(zxSaOtherEquipSettle.getPeriodDate());
        // 结算期次
        zxSaOtherEquipSettle.setPeriod(period);
        // 评审状态
        zxSaOtherEquipSettle.setApih5FlowStatus("-1");
        // 填报人
        zxSaOtherEquipSettle.setReportPerson(realName);
        // 发起人
        zxSaOtherEquipSettle.setFlowBeginPerson(realName);
        zxSaOtherEquipSettle.setZxSaOtherEquipSettleId(UuidUtil.generate());
        zxSaOtherEquipSettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipSettleMapper.insert(zxSaOtherEquipSettle);

        // 查询当前合同管理id对应的合同管理数据
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxCtOtherManageId());

        // 合同首次建立一条结算单时，保存后把对应的合同管理中的结算情况更新为“结算开始执行”
        ZxSaOtherEquipSettle isFirstZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        isFirstZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> isFirstZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(isFirstZxSaOtherEquipSettle);
        // 当前合同在结算单里有结算数据，并且只有一条时，是首次建立结算单
        if (isFirstZxSaOtherEquipSettleList != null && isFirstZxSaOtherEquipSettleList.size() == 1) {
            dbZxCtOtherManage.setSettleType("结算开始执行");
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }

        BigDecimal zero = new BigDecimal(0);
        // 添加细目结算主表数据
        ZxSaOtherEquipResSettle zxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
        BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipResSettle);
        // 含税合同金额
        zxSaOtherEquipResSettle.setContractAmt(dbZxCtOtherManage.getContractCost());
        // 变更后含税合同金额
        zxSaOtherEquipResSettle.setChangeAmt(dbZxCtOtherManage.getAlterContractSum());
        // 本期清单结算含税金额(元)
        zxSaOtherEquipResSettle.setThisAmt(zero);
        // 本期清单结算不含税金额(元)
        zxSaOtherEquipResSettle.setThisAmtNoTax(zero);
        // 本期清单结算税额(元)
        zxSaOtherEquipResSettle.setThisAmtTax(zero);
        // 签认单编号
        zxSaOtherEquipResSettle.setSignedNos(zxSaOtherEquipSettle.getSignedNo());

        // 细目结算主表上期末清单结算金额(元) 累计清单结算含税金额(元)
        if (zxSaOtherEquipSettleList == null || zxSaOtherEquipSettleList.size() == 0) {
            zxSaOtherEquipResSettle.setUpAmt(zero);
            zxSaOtherEquipResSettle.setTotalAmt(zero);
        } else {
            // 累计清单结算含税金额(元)
            ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
            dbZxSaOtherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            // 根据合同id查询当前合同的往期所有细目结算主表数据
            List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbZxSaOtherEquipResSettle);
            if (dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size() > 0) {
                BigDecimal totalThisAmt = null;
                for (ZxSaOtherEquipResSettle zxSaOtherEquipResSettle1 : dbZxSaOtherEquipResSettleList) {
                    // 往期所有细目结算主表的本期清单结算金额(元)值汇总
                    totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipResSettle1.getThisAmt());
                }
                // 累计清单结算含税金额(元)初始化赋值
                zxSaOtherEquipResSettle.setTotalAmt(totalThisAmt);
            }

            // 上期末清单结算金额(元)
            ZxSaOtherEquipResSettle otherEquipResSettle = new ZxSaOtherEquipResSettle();
            otherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            otherEquipResSettle.setPeriod(period);
            ZxSaOtherEquipResSettle selectItem = zxSaOtherEquipResSettleMapper.selectTotalAmt(otherEquipResSettle);
            zxSaOtherEquipResSettle.setUpAmt(selectItem.getUpAmt());
        }
        // 结算单主表id
        zxSaOtherEquipResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        // 其他合同管理id
        zxSaOtherEquipResSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // 细目结算主表id
        zxSaOtherEquipResSettle.setZxSaOtherEquipResSettleId(UuidUtil.generate());
        zxSaOtherEquipResSettle.setCreateUserInfo(userKey, realName);
        // 添加细目结算主表数据
        zxSaOtherEquipResSettleMapper.insert(zxSaOtherEquipResSettle);

        // 添加细目结算清单明细表数据
        ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
        dbZxCtOtherWorks.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
        List<ZxCtOtherWorks> zxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
        if (zxCtOtherWorksList != null && zxCtOtherWorksList.size() > 0) {
            ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
            BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipResSettleItem);
            for (ZxCtOtherWorks zxCtOtherWorks : zxCtOtherWorksList) {
                // 清单编号，原型上是清单细目
                zxSaOtherEquipResSettleItem.setEquipCode(zxCtOtherWorks.getWorkNo());
                // 清单名称
                zxSaOtherEquipResSettleItem.setEquipName(zxCtOtherWorks.getWorkName());
                // 计量单位
                zxSaOtherEquipResSettleItem.setUnit(zxCtOtherWorks.getUnit());
                // 含税单价（元）
                zxSaOtherEquipResSettleItem.setContractPrice(zxCtOtherWorks.getPrice() == null ? zxCtOtherWorks.getChangePrice() : zxCtOtherWorks.getPrice());
                // 合同数量
                zxSaOtherEquipResSettleItem.setContractQty(zxCtOtherWorks.getQty());
                // 含税合同金额
                zxSaOtherEquipResSettleItem.setContractAmt(zxCtOtherWorks.getContractSum());
                // 税率
                zxSaOtherEquipResSettleItem.setTaxRate(zxCtOtherWorks.getTaxRate());
                // 变更后合同数量
                zxSaOtherEquipResSettleItem.setChangedQty(zxCtOtherWorks.getChangeQty());
                // 变更后含税合同金额
                zxSaOtherEquipResSettleItem.setChangedAmt(zxCtOtherWorks.getChangeContractSum());
                // 至本期末累计结算数量
                zxSaOtherEquipResSettleItem.setTotalQty(zero);
                // 至本期末累计结算含税金额(元)
                zxSaOtherEquipResSettleItem.setTotalAmt(zero);
                // 至上期末累计结算数量
                zxSaOtherEquipResSettleItem.setUpAmt(zero);
                // 至本期末累计结算含税金额(元)
                zxSaOtherEquipResSettleItem.setUpQty(zero);
                // 细目结算清单明细至上期末清单结算金额(元) 至上期末累计结算数量
                if (!CollectionUtils.isEmpty(zxSaOtherEquipSettleList)) {
                    ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                    dbZxSaOtherEquipResSettleItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                    // 根据合同管理清单明细id查询当前合同的往期所有细目结算清单明细数据
                    List<ZxSaOtherEquipResSettleItem> dbZxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem);
                    if (!CollectionUtils.isEmpty(dbZxSaOtherEquipResSettleItemList)) {
                        BigDecimal totalThisQty = null;
                        BigDecimal totalThisAmt = null;
                        for (ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem1 : dbZxSaOtherEquipResSettleItemList) {
                            // 往期所有细目结算清单明细的本期结算数量值汇总
                            totalThisQty = CalcUtils.calcAdd(totalThisQty, zxSaOtherEquipResSettleItem1.getThisQty());
                            // 往期所有细目结算清单明细的本期结算含税金额（元）值汇总
                            totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipResSettleItem1.getThisAmt());
                        }
                        // 至本期末累计结算数量
                        zxSaOtherEquipResSettleItem.setTotalQty(totalThisQty);
                        // 至本期末累计结算含税金额(元)
                        zxSaOtherEquipResSettleItem.setTotalAmt(totalThisAmt);
                    }
                    ZxSaOtherEquipResSettleItem resItem = new ZxSaOtherEquipResSettleItem();
                    resItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
                    resItem.setPeriod(period);
                    resItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                    ZxSaOtherEquipResSettleItem selectItem = zxSaOtherEquipResSettleItemMapper.selectTotalQtyAmt(resItem);
                    if (selectItem != null) {
                        zxSaOtherEquipResSettleItem.setUpAmt(selectItem.getUpAmt());
                        zxSaOtherEquipResSettleItem.setUpQty(selectItem.getUpQty());
                    }
                }
                // 细目结算主表id
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
                // 合同管理id
                zxSaOtherEquipResSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
                // 合同管理清单明细id
                zxSaOtherEquipResSettleItem.setZxCtOtherWorksId(zxCtOtherWorks.getZxCtOtherWorksId());
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleItemId(UuidUtil.createUUID());
                zxSaOtherEquipResSettleItem.setCreateUserInfo(userKey, realName);
                //添加细目结算清单明细表数据
                zxSaOtherEquipResSettleItemMapper.insert(zxSaOtherEquipResSettleItem);
            }
        }
        //  添加支付项结算主表数据，初始化累计支付项结算含税金额(元)
        ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        BeanUtil.copyProperties(zxSaOtherEquipSettle, zxSaOtherEquipPaySettle);
        // 支付项主表上期末累计支付项结算金额(元) 累计支付项结算含税金额(元)
        if (zxSaOtherEquipSettleList == null || zxSaOtherEquipSettleList.size() == 0) {
            zxSaOtherEquipPaySettle.setUpAmt(zero);
            zxSaOtherEquipPaySettle.setTotalAmt(zero);
        } else {
            ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
            dbZxSaOtherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            // 根据合同id查询当前合同的往期所有支付项结算主表数据
            List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbZxSaOtherEquipPaySettle);
            if (dbZxSaOtherEquipPaySettleList != null && dbZxSaOtherEquipPaySettleList.size() > 0) {
                BigDecimal totalThisAmt = null;
                for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : dbZxSaOtherEquipPaySettleList) {
                    // 往期所有支付项结算主表的本期支付项结算含税金额(元)值汇总
                    totalThisAmt = CalcUtils.calcAdd(totalThisAmt, zxSaOtherEquipPaySettle1.getThisAmt());
                }
                // 累计支付项结算含税金额(元)初始化赋值
                zxSaOtherEquipPaySettle.setTotalAmt(totalThisAmt);
            }
            // 支付项主表上期末累计支付项结算金额(元)
            ZxSaOtherEquipPaySettle otherEquipPaySettle = new ZxSaOtherEquipPaySettle();
            otherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            otherEquipPaySettle.setPeriod(period);
            ZxSaOtherEquipPaySettle selectItem = zxSaOtherEquipPaySettleMapper.selectTotalAmt(otherEquipPaySettle);
            zxSaOtherEquipPaySettle.setUpAmt(selectItem.getUpAmt());
        }
        // 本期支付项结算金额(元)
        zxSaOtherEquipPaySettle.setThisAmt(zero);
        // 本期支付项结算不含税金额(元)
        zxSaOtherEquipPaySettle.setThisAmtNoTax(zero);
        // 本期支付项结算税额(元)
        zxSaOtherEquipPaySettle.setThisAmtTax(zero);
        // 其他合同管理id
        zxSaOtherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        // 结算单主表id
        zxSaOtherEquipPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        // 支付项结算主表id
        zxSaOtherEquipPaySettle.setZxSaOtherEquipPaySettleId(UuidUtil.createUUID());
        zxSaOtherEquipPaySettle.setCreateUserInfo(userKey, realName);
        // 添加支付项结算主表数据
        zxSaOtherEquipPaySettleMapper.insert(zxSaOtherEquipPaySettle);
        // 添加统计项明细
        ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        zxSaOtherEquipSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        zxSaOtherEquipSettleItem.setPeriod(period);
        List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = (List<ZxSaOtherEquipSettleItem>) zxSaOtherEquipSettleItemServiceImpl.getZxSaOtherEquipSettleItemByContractId(zxSaOtherEquipSettleItem).getData();
        if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
            for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem1 : zxSaOtherEquipSettleItemList) {
                zxSaOtherEquipSettleItem1.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                zxSaOtherEquipSettleItem1.setBillNo(zxSaOtherEquipSettle.getBillNo());
                zxSaOtherEquipSettleItem1.setOrgId(zxSaOtherEquipSettle.getOrgId());
                zxSaOtherEquipSettleItem1.setComId(zxSaOtherEquipSettle.getCompanyId());
                zxSaOtherEquipSettleItem1.setComName(zxSaOtherEquipSettle.getCompanyName());
                zxSaOtherEquipSettleItem1.setComOrders(zxSaOtherEquipSettle.getComOrders());
                zxSaOtherEquipSettleItem1.setZxSaOtherEquipSettleItemId(UuidUtil.generate());
                zxSaOtherEquipSettleItem1.setCreateUserInfo(userKey, realName);
                zxSaOtherEquipSettleItemMapper.insert(zxSaOtherEquipSettleItem1);
                // 初始化结算单主表相关金额
                if ("100100".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // 本期结算金额 是统计项的合计结算含税金额本期
                    zxSaOtherEquipSettle.setThisAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                    // 开累结算金额 是统计项的合计结算含税金额开累
                    zxSaOtherEquipSettle.setTotalAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getTotalAmt()));
                    // 截止到上期累计金额(元)
                    zxSaOtherEquipSettle.setUpTotalAmt(zxSaOtherEquipSettleItem1.getUpAmt());
                }
                if ("100110".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // 本期结算不含税金额(元) 是统计项的合计结算不含税金额
                    zxSaOtherEquipSettle.setThisAmtNoTax(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                }
                if ("100120".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // 本期结算税额(元)  是统计项的合计结算税额
                    zxSaOtherEquipSettle.setThisAmtTax(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                }
                if ("100700".equals(zxSaOtherEquipSettleItem1.getStatisticsNo())) {
                    // 本期应付金额
                    zxSaOtherEquipSettle.setThisPayAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt()));
                    // 开累应付金额
                    zxSaOtherEquipSettle.setTotalPayAmt(new BigDecimal(zxSaOtherEquipSettleItem1.getTotalAmt()));
                }
                zxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
                zxSaOtherEquipSettleMapper.updateByPrimaryKey(zxSaOtherEquipSettle);
            }
        }
        //添加附件 0附件 1正文
        List<ZxErpFile> fileList = zxSaOtherEquipSettle.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                zxErpFile.setOtherType("0");
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        if (dbZxSaOtherEquipSettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId())) {
            // 是否抵扣
            dbZxSaOtherEquipSettle.setIsDeduct(zxSaOtherEquipSettle.getIsDeduct());
            // 结算内容
            dbZxSaOtherEquipSettle.setContent(zxSaOtherEquipSettle.getContent());
            // 调差后累计结算金额
            dbZxSaOtherEquipSettle.setTchljjsje(zxSaOtherEquipSettle.getTchljjsje());
            // 本期调整后结算金额
            dbZxSaOtherEquipSettle.setBqtchjsje(zxSaOtherEquipSettle.getBqtchjsje());
            // 结算期次时间戳
            dbZxSaOtherEquipSettle.setPeriodDate(zxSaOtherEquipSettle.getPeriodDate());
            // 获取只有年月的时间
            String period = DateUtil.format(zxSaOtherEquipSettle.getPeriodDate(), "yyyyMM");
            // 结算期次
            dbZxSaOtherEquipSettle.setPeriod(period);
            // 结算期限开始时间
            dbZxSaOtherEquipSettle.setBeginDate(zxSaOtherEquipSettle.getBeginDate());
            // 填报日期
            dbZxSaOtherEquipSettle.setReportDate(zxSaOtherEquipSettle.getReportDate());
            // 结算单编号
            dbZxSaOtherEquipSettle.setBillNo(zxSaOtherEquipSettle.getBillNo());
            // 合同编号
            dbZxSaOtherEquipSettle.setContractNo(zxSaOtherEquipSettle.getContractNo());
            // 合同名称
            dbZxSaOtherEquipSettle.setContractName(zxSaOtherEquipSettle.getContractName());
            // 乙方ID
            dbZxSaOtherEquipSettle.setSecondId(zxSaOtherEquipSettle.getSecondId());
            // 合同乙方
            dbZxSaOtherEquipSettle.setSecondName(zxSaOtherEquipSettle.getSecondName());
            // 结算类型
            dbZxSaOtherEquipSettle.setBillType(zxSaOtherEquipSettle.getBillType());
            // 结算期限结束时间
            dbZxSaOtherEquipSettle.setEndDate(zxSaOtherEquipSettle.getEndDate());
            // 业务日期
            dbZxSaOtherEquipSettle.setBusinessDate(zxSaOtherEquipSettle.getBusinessDate());
            // 填报人
            dbZxSaOtherEquipSettle.setReportPerson(zxSaOtherEquipSettle.getReportPerson());
            // 填报人电话
            dbZxSaOtherEquipSettle.setReportPersonPhone(zxSaOtherEquipSettle.getReportPersonPhone());
            // 计算人
            dbZxSaOtherEquipSettle.setCountPerson(zxSaOtherEquipSettle.getCountPerson());
            // 复核人
            dbZxSaOtherEquipSettle.setReCountPerson(zxSaOtherEquipSettle.getReCountPerson());
            // 是否首次结算
            dbZxSaOtherEquipSettle.setIsFirst(zxSaOtherEquipSettle.getIsFirst());
            // 流程ID
            dbZxSaOtherEquipSettle.setWorkItemId(zxSaOtherEquipSettle.getWorkItemId());
            // 流程进度ID
            dbZxSaOtherEquipSettle.setInstProcessId(zxSaOtherEquipSettle.getInstProcessId());
            // 流程开始时间
            dbZxSaOtherEquipSettle.setFlowBeginDate(zxSaOtherEquipSettle.getFlowBeginDate());
            // 结算表初始化顺序号
            dbZxSaOtherEquipSettle.setInitSerialNumber(zxSaOtherEquipSettle.getInitSerialNumber());
            // 签认单编号
            dbZxSaOtherEquipSettle.setSignedNo(zxSaOtherEquipSettle.getSignedNo());
            // 验收情况
            dbZxSaOtherEquipSettle.setAppraisal(zxSaOtherEquipSettle.getAppraisal());
            // workId
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // 流程id
            dbZxSaOtherEquipSettle.setApih5FlowId(zxSaOtherEquipSettle.getApih5FlowId());
            // 审核状态
            dbZxSaOtherEquipSettle.setApih5FlowStatus(zxSaOtherEquipSettle.getApih5FlowStatus());
            // 审核节点状态
            dbZxSaOtherEquipSettle.setApih5FlowNodeStatus(zxSaOtherEquipSettle.getApih5FlowNodeStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField1(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField1(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField2(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField2(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField3(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField3(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField4(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField4(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField5(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField5(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField6(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField6(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField7(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField7(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField8(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField8(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField9(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField9(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField10(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField10(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            // 流程id
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // 意见1
            dbZxSaOtherEquipSettle.setOpinionField1(zxSaOtherEquipSettle.getOpinionField1());
            // 意见2
            dbZxSaOtherEquipSettle.setOpinionField2(zxSaOtherEquipSettle.getOpinionField2());
            // 意见3
            dbZxSaOtherEquipSettle.setOpinionField3(zxSaOtherEquipSettle.getOpinionField3());
            // 意见4
            dbZxSaOtherEquipSettle.setOpinionField4(zxSaOtherEquipSettle.getOpinionField4());
            // 意见5
            dbZxSaOtherEquipSettle.setOpinionField5(zxSaOtherEquipSettle.getOpinionField5());
            // 意见6
            dbZxSaOtherEquipSettle.setOpinionField6(zxSaOtherEquipSettle.getOpinionField6());
            // 意见7
            dbZxSaOtherEquipSettle.setOpinionField7(zxSaOtherEquipSettle.getOpinionField7());
            // 意见8
            dbZxSaOtherEquipSettle.setOpinionField8(zxSaOtherEquipSettle.getOpinionField8());
            // 意见9
            dbZxSaOtherEquipSettle.setOpinionField9(zxSaOtherEquipSettle.getOpinionField9());
            // 意见10
            dbZxSaOtherEquipSettle.setOpinionField10(zxSaOtherEquipSettle.getOpinionField10());
            // 备注
            dbZxSaOtherEquipSettle.setRemark(zxSaOtherEquipSettle.getRemark());
            // 共通
            dbZxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipSettle);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
            zxErpFileSelect.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxSaOtherEquipSettle.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            // 评审通过
//            if(StrUtil.equals(zxSaOtherEquipSettle.getApih5FlowStatus(), "2")) {
//                this.zxSaOtherEquipSettleReviewApply(zxSaOtherEquipSettle);
//            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettle(List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxSaOtherEquipSettle zxSaOtherEquipSettle : zxSaOtherEquipSettleList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // 查询此结算单的细目结算主表数据
                ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
                dbZxSaOtherEquipResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbZxSaOtherEquipResSettle);
                if (dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size() > 0) {
                    // 删除细目结算主表数据
                    ZxSaOtherEquipResSettle delZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
                    delZxSaOtherEquipResSettle.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipResSettleMapper.batchDeleteUpdateZxSaOtherEquipResSettle(dbZxSaOtherEquipResSettleList, delZxSaOtherEquipResSettle);
                    // 删除细目结算清单明细列表list
                    ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                    dbZxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(dbZxSaOtherEquipResSettleList.get(0).getZxSaOtherEquipResSettleId());
                    List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem);
                    if (zxSaOtherEquipResSettleItemList != null && zxSaOtherEquipResSettleItemList.size() > 0) {
                        ZxSaOtherEquipResSettleItem delZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
                        delZxSaOtherEquipResSettleItem.setModifyUserInfo(userKey, realName);
                        zxSaOtherEquipResSettleItemMapper.batchDeleteUpdateZxSaOtherEquipResSettleItem(zxSaOtherEquipResSettleItemList, delZxSaOtherEquipResSettleItem);
                    }
                }
                // 查询此结算单的支付项结算主表数据
                ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
                dbZxSaOtherEquipPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbZxSaOtherEquipPaySettle);
                if (zxSaOtherEquipPaySettleList != null && zxSaOtherEquipPaySettleList.size() > 0) {
                    // 删除支付项结算主表数据
                    ZxSaOtherEquipPaySettle delZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
                    delZxSaOtherEquipPaySettle.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipPaySettleMapper.batchDeleteUpdateZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettleList, delZxSaOtherEquipPaySettle);
                    // 删除支付项结算清单明细列表list
                    ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
                    dbZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettleList.get(0).getZxSaOtherEquipPaySettleId());
                    List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbZxSaOtherEquipPaySettleItem);
                    if (zxSaOtherEquipPaySettleItemList != null && zxSaOtherEquipPaySettleItemList.size() > 0) {
                        ZxSaOtherEquipPaySettleItem delZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
                        delZxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
                        zxSaOtherEquipPaySettleItemMapper.batchDeleteUpdateZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItemList, delZxSaOtherEquipPaySettleItem);
                    }
                }
                ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                dbZxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(dbZxSaOtherEquipSettleItem);
                if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
                    ZxSaOtherEquipSettleItem delZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                    delZxSaOtherEquipSettleItem.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.batchDeleteUpdateZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItemList, delZxSaOtherEquipSettleItem);
                    // 合同管理保证金表的是否被结算引用字段值减去1
                    for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem : zxSaOtherEquipSettleItemList) {
                        if (StringUtil.isNotEmpty(zxSaOtherEquipSettleItem.getZxCtOtherManageAmtRateId())) {
                            ZxCtOtherManageAmtRate dbZxCtOtherManageAmtRate = zxCtOtherManageAmtRateMapper.selectByPrimaryKey(zxSaOtherEquipSettleItem.getZxCtOtherManageAmtRateId());
                            dbZxCtOtherManageAmtRate.setUseCount(CalcUtils.calcSubtract(dbZxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                            dbZxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
                            zxCtOtherManageAmtRateMapper.updateByPrimaryKey(dbZxCtOtherManageAmtRate);
                        }
                    }
                }

                if(StrUtil.isNotEmpty(zxSaOtherEquipSettle.getWorkId())) {
                    jsonArray.add(zxSaOtherEquipSettle.getWorkId());
                }
            }
            String zxCtOtherManageId = zxSaOtherEquipSettleList.get(0).getZxCtOtherManageId();
            ZxSaOtherEquipSettle zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
            zxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.batchDeleteUpdateZxSaOtherEquipSettle(zxSaOtherEquipSettleList, zxSaOtherEquipSettle);

            // 查询当前合同管理id对应的合同管理数据
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManageId);

            // 当前合同首次结算单被删除时在结算单里无结算数据，把对应合同管理中的结算情况恢复为“实际无结算”
            ZxSaOtherEquipSettle isFirstZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
            isFirstZxSaOtherEquipSettle.setZxCtOtherManageId(zxCtOtherManageId);
            List<ZxSaOtherEquipSettle> isFirstZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(isFirstZxSaOtherEquipSettle);
            // 删除后当前合同在结算单里无结算数据
            if (isFirstZxSaOtherEquipSettleList == null || isFirstZxSaOtherEquipSettleList.size() == 0) {
                dbZxCtOtherManage.setSettleType("实际无结算");
                zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
            }

            // 删除流程后台数据
            String url = Apih5Properties.getWebUrl() + "batchDeleteFlow";
            if(jsonArray.size()>0) {
                HttpUtil.sendPostToken(url, jsonArray.toString(), token);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaOtherEquipSettleList);
        }
    }

    /**
     *
     * @param realName ==姓名
     * @param dbOpinionContent==数据库里的值
     * @param opinionContent===过来的值
     * @return
     */
    private String getOpinionContent(String realName, String dbOpinionContent, String opinionContent){
        if(StrUtil.isNotEmpty(opinionContent)){
            opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
            opinionContent += "<br/>" + realName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return opinionContent;
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxSaOtherEquipSettleContractNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getOrgId())) {
            return repEntity.layerMessage("no", "orgId不能为空！");
        }
        ZxCtOtherManage dbZxCtOtherManage = new ZxCtOtherManage();
        dbZxCtOtherManage.setOrgId(zxSaOtherEquipSettle.getOrgId());
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipSettle.getPage(), zxSaOtherEquipSettle.getLimit());
        // 根据项目id查询其他合同管理表里数据
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByOrgIdZxCtOtherManageList(dbZxCtOtherManage);

        // 得到分页信息
        PageInfo<ZxCtOtherManage> p = new PageInfo<>(zxCtOtherManageList);
        return repEntity.okList(zxCtOtherManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getIsFirstByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        // 其他合同管理表ID不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "其他合同管理表ID不能为空！");
        }
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
        // 当前合同在结算单里有结算数据，不是首次结算
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            zxSaOtherEquipSettle.setIsFirst("0");
        } else {
            zxSaOtherEquipSettle.setIsFirst("1");
        }
        return repEntity.ok(zxSaOtherEquipSettle);
    }

    @Override
    public ResponseEntity getSettleTypeByContractId(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        // 其他合同管理表ID不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "其他合同管理表ID不能为空！");
        }
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxCtOtherManageId());
        if("已最终结算".equals(dbZxCtOtherManage.getSettleType())){
            return repEntity.ok("1");
        } else {
            return repEntity.ok("0");
        }
    }

    @Override
    public ResponseEntity zxSaOtherEquipSettleReviewApply(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        if (dbZxSaOtherEquipSettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId())) {
            // 评审通过后修改结算单的评审状态
            // workId
            dbZxSaOtherEquipSettle.setWorkId(zxSaOtherEquipSettle.getWorkId());
            // 流程ID
            dbZxSaOtherEquipSettle.setWorkItemId(zxSaOtherEquipSettle.getWorkItemId());
            // 流程进度ID
            dbZxSaOtherEquipSettle.setInstProcessId(zxSaOtherEquipSettle.getInstProcessId());
            // 流程开始时间
            dbZxSaOtherEquipSettle.setFlowBeginDate(zxSaOtherEquipSettle.getFlowBeginDate());
            // 签认单编号
            // dbZxSaOtherEquipSettle.setSignedNo(zxSaOtherEquipSettle.getSignedNo());
            // 验收情况
            // dbZxSaOtherEquipSettle.setAppraisal(zxSaOtherEquipSettle.getAppraisal());
            // 流程id
            dbZxSaOtherEquipSettle.setApih5FlowId(zxSaOtherEquipSettle.getApih5FlowId());
            // 审核状态
            dbZxSaOtherEquipSettle.setApih5FlowStatus(zxSaOtherEquipSettle.getApih5FlowStatus());
            // 审核节点状态
            dbZxSaOtherEquipSettle.setApih5FlowNodeStatus(zxSaOtherEquipSettle.getApih5FlowNodeStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField1(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField1(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField2(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField2(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField3(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField3(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField4(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField4(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField5(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField5(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField6(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField6(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField7(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField7(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField8(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField8(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField9(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField9(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxSaOtherEquipSettle.getOpinionField(), true)){
                dbZxSaOtherEquipSettle.setOpinionField10(getOpinionContent(realName, dbZxSaOtherEquipSettle.getOpinionField10(), zxSaOtherEquipSettle.getOpinionContent()));
            }
            // 意见1
            dbZxSaOtherEquipSettle.setOpinionField1(zxSaOtherEquipSettle.getOpinionField1());
            // 意见2
            dbZxSaOtherEquipSettle.setOpinionField2(zxSaOtherEquipSettle.getOpinionField2());
            // 意见3
            dbZxSaOtherEquipSettle.setOpinionField3(zxSaOtherEquipSettle.getOpinionField3());
            // 意见4
            dbZxSaOtherEquipSettle.setOpinionField4(zxSaOtherEquipSettle.getOpinionField4());
            // 意见5
            dbZxSaOtherEquipSettle.setOpinionField5(zxSaOtherEquipSettle.getOpinionField5());
            // 意见6
            dbZxSaOtherEquipSettle.setOpinionField6(zxSaOtherEquipSettle.getOpinionField6());
            // 意见7
            dbZxSaOtherEquipSettle.setOpinionField7(zxSaOtherEquipSettle.getOpinionField7());
            // 意见8
            dbZxSaOtherEquipSettle.setOpinionField8(zxSaOtherEquipSettle.getOpinionField8());
            // 意见9
            dbZxSaOtherEquipSettle.setOpinionField9(zxSaOtherEquipSettle.getOpinionField9());
            // 意见10
            dbZxSaOtherEquipSettle.setOpinionField10(zxSaOtherEquipSettle.getOpinionField10());
            // 备注
            // dbZxSaOtherEquipSettle.setRemark(zxSaOtherEquipSettle.getRemark());

            dbZxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipSettle);
            if(!CollectionUtils.isEmpty(zxSaOtherEquipSettle.getDocumentFileList())) {
                ZxErpFile file = new ZxErpFile();
                file.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                file.setOtherType("1");
                List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
                file.setModifyUserInfo(userKey, realName);
                if(fileList.size() > 0) {
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
                }
                for(ZxErpFile zxErpFile : zxSaOtherEquipSettle.getDocumentFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFile.setOtherType("1");
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // 查询结算单表中已评审通过的结算数据，如果结算单类型为最终结算，把对应合同管理中的结算情况更新为“已最终结算”
            // 因为刚评审通过，所以数据库不存在没有审核通过的数据，审核不通过本来也不能新增结算单
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(dbZxSaOtherEquipSettle.getZxCtOtherManageId());
            // 如果结算单的结算类型为最终，更新合同管理表的结算情况为已最终结算
            if ("1".equals(dbZxSaOtherEquipSettle.getBillType())) {
                dbZxCtOtherManage.setSettleType("已最终结算");
            }
            // 其他合同管理的累计结算金额 是结算单的开累结算金额 也就是统计项的合计结算金额开累
            dbZxCtOtherManage.setTotalSettleAmount(dbZxSaOtherEquipSettle.getTotalAmt());
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipSettle);
        }
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleBillNo(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
//        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getContractNo())) {
//            return repEntity.layerMessage("no", "合同编号不能为空！");
//        }
//        if ("".equals(zxSaOtherEquipSettle.getPeriodDate()) || zxSaOtherEquipSettle.getPeriodDate() == null) {
//            return repEntity.layerMessage("no", "结算期次时间戳不能为空！");
//        }
//        // 合同编号
//        String contractNo = zxSaOtherEquipSettle.getContractNo();
//        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
//        dbZxSaOtherEquipSettle.setContractNo(contractNo);
//        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
//        // 结算期次
//        String period = new SimpleDateFormat("yyyyMM").format(zxSaOtherEquipSettle.getPeriodDate());
//        // 结算单初始化顺序号
//        String initSerialNumber = String.format("%02d", zxSaOtherEquipSettleList.size()+1);
//
//        ZxSaOtherEquipSettle zxSaOtherEquipSettle1 = new ZxSaOtherEquipSettle();
//        // 结算单初始化顺序号赋值
//        zxSaOtherEquipSettle1.setInitSerialNumber(initSerialNumber);
//        // 结算单编号 合同编号 + 结算期次后四位 + 顺序号
//        String billNo = contractNo + "-" + period.substring(period.length() - 4) + "-" + initSerialNumber;
//        zxSaOtherEquipSettle1.setBillNo(billNo);
//        // 结算签认单编号
//        String signedNo = contractNo + "-SL-" + period.substring(period.length() - 4) + "-" + initSerialNumber;
//        zxSaOtherEquipSettle1.setSignedNo(signedNo);
//        return repEntity.ok(zxSaOtherEquipSettle1);
        if (StrUtil.isEmpty(zxSaOtherEquipSettle.getContractNo())) {
            return repEntity.layerMessage("no", "合同编号不能为空！");
        }
        ZxSaOtherEquipSettle otherEquipSettle = zxSaOtherEquipSettleMapper.getTheLastZxSaOtherEquipSettle(zxSaOtherEquipSettle.getContractNo());
        if (otherEquipSettle == null) {
            return repEntity.ok(0);
        } else {
            return repEntity.ok(Integer.parseInt(otherEquipSettle.getInitSerialNumber()));
        }
    }

    @Override
    public void exportZxSaOtherEquipSettle(ZxSaOtherEquipSettle zxSaOtherEquipSettle,HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // 获取数据
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("公司名称",
                "结算编号",
                "项目名称",
                "结算期次",
                "结算类型",
                "合同名称",
                "合同乙方",
                "本期结算含税金额（元）",
                "开累结算含税金额（元）",
                "本期应支付含税金额（元）",
                "开累应支付含税金额（元）",
                "结算期限开始时间",
                "结算期限结束时间",
                "业务日期",
                "填报人",
                "评审状态"
        );
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxSaOtherEquipSettleList != null && zxSaOtherEquipSettleList.size() > 0) {
            for (ZxSaOtherEquipSettle dbZxSaOtherEquipSettle : zxSaOtherEquipSettleList) {
                rowsList.add(CollUtil.newArrayList(dbZxSaOtherEquipSettle.getCompanyName(),
                        dbZxSaOtherEquipSettle.getBillNo(),
                        dbZxSaOtherEquipSettle.getOrgName(),
                        dbZxSaOtherEquipSettle.getPeriod(),
                        dbZxSaOtherEquipSettle.getBillType(),
                        dbZxSaOtherEquipSettle.getContractName(),
                        dbZxSaOtherEquipSettle.getSecondName(),
                        dbZxSaOtherEquipSettle.getThisAmt(),
                        dbZxSaOtherEquipSettle.getTotalAmt(),
                        dbZxSaOtherEquipSettle.getThisPayAmt(),
                        dbZxSaOtherEquipSettle.getTotalPayAmt(),
                        dbZxSaOtherEquipSettle.getBeginDate(),
                        dbZxSaOtherEquipSettle.getEndDate(),
                        dbZxSaOtherEquipSettle.getBusinessDate(),
                        dbZxSaOtherEquipSettle.getReportPerson(),
                        dbZxSaOtherEquipSettle.getApih5FlowStatus()
                        )
                );
            }

            // 报表名称
            //String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            //String fileName = "结算单报表-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);

            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("其他结算单报表".getBytes("utf-8"), "iso-8859-1") + "\"");
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
        }
    }

    @Override
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // 本期清单+小计
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListInfo(zxSaOtherEquipSettle);
        BigDecimal thisAmt_t = BigDecimal.ZERO;
        BigDecimal upAmt_t = BigDecimal.ZERO;
        BigDecimal totalAmt_t = BigDecimal.ZERO;
        Optional<ZxSaOtherEquipSettle> optional = zxSaOtherEquipSettleList.stream().filter(e -> "小计".equals(e.getEquipCode())).findAny();
        if (optional.isPresent()) {
            thisAmt_t = CalcUtils.calcAdd(thisAmt_t, optional.get().getThisAmt());
            upAmt_t = CalcUtils.calcAdd(upAmt_t, optional.get().getUpAmt());
            totalAmt_t = CalcUtils.calcAdd(totalAmt_t, optional.get().getTotalAmt());
        }
        // 支付项
        List<ZxSaOtherEquipPaySettleItem> paySettleItems = zxSaOtherEquipSettleMapper.getZxSaOtherEquipPaySettleItemList(zxSaOtherEquipSettle);
        Map<String, List<ZxSaOtherEquipPaySettleItem>> maps = paySettleItems.stream().collect(Collectors.groupingBy(ZxSaOtherEquipPaySettleItem::getPayType));
        List<String> typeList = maps.keySet().stream().sorted().collect(Collectors.toList());
        // 按支付项类型遍历
        String zxSaOtherEquipSettleId = zxSaOtherEquipSettle.getZxSaOtherEquipSettleId();
        for (String type : typeList) {
            // 该类型支付项总计
            ZxSaOtherEquipSettle pay = new ZxSaOtherEquipSettle();
            pay.setEquipCode(type);
            BigDecimal thisQty = BigDecimal.ZERO;
            BigDecimal thisAmt = BigDecimal.ZERO;
            BigDecimal upQty = BigDecimal.ZERO;
            BigDecimal upAmt = BigDecimal.ZERO;
            BigDecimal totalQty = BigDecimal.ZERO;
            BigDecimal totalAmt = BigDecimal.ZERO;
            for (ZxSaOtherEquipPaySettleItem paySettleItem : maps.get(type)) {
                if (paySettleItem.getZxSaOtherEquipSettleId().equals(zxSaOtherEquipSettleId)) {
                    thisQty = CalcUtils.calcAdd(thisQty, paySettleItem.getQty());
                    thisAmt = CalcUtils.calcAdd(thisAmt, paySettleItem.getThisAmt());
                } else {
                    upQty = CalcUtils.calcAdd(upQty, paySettleItem.getUpQty());
                    upAmt = CalcUtils.calcAdd(upAmt, paySettleItem.getUpAmt());
                }
                totalQty = CalcUtils.calcAdd(totalQty, paySettleItem.getQty());
                totalAmt = CalcUtils.calcAdd(totalAmt, paySettleItem.getThisAmt());
            }
            pay.setThisQty(thisQty);
            pay.setThisAmt(thisAmt);
            pay.setUpQty(upQty);
            pay.setUpAmt(upAmt);
            pay.setTotalQty(totalQty);
            pay.setTotalAmt(totalAmt);
            zxSaOtherEquipSettleList.add(pay);
            thisAmt_t = CalcUtils.calcAdd(thisAmt_t, thisAmt);
            upAmt_t = CalcUtils.calcAdd(upAmt_t, upAmt);
            totalAmt_t = CalcUtils.calcAdd(totalAmt_t, totalAmt);
            // 该类型本期支付项
            List<ZxSaOtherEquipPaySettleItem> paySettleItemList_this  = maps.get(type).stream().filter( e -> zxSaOtherEquipSettleId.equals(e.getZxSaOtherEquipSettleId())).collect(Collectors.toList());
            paySettleItemList_this.forEach(p -> {
                ZxSaOtherEquipSettle payItem = new ZxSaOtherEquipSettle();
                payItem.setEquipName(p.getPayName());
                payItem.setContractPrice(p.getPrice());
                payItem.setThisQty(p.getQty());
                payItem.setThisAmt(p.getThisAmt());
                payItem.setUpQty(BigDecimal.ZERO);
                payItem.setUpAmt(BigDecimal.ZERO);
                payItem.setTotalQty(p.getQty());
                payItem.setTotalAmt(p.getThisAmt());
                zxSaOtherEquipSettleList.add(payItem);
            });
        }
        // 合计
        ZxSaOtherEquipSettle totalDate = new ZxSaOtherEquipSettle();
        totalDate.setEquipCode("合计");
        totalDate.setThisAmt(thisAmt_t);
        totalDate.setUpAmt(upAmt_t);
        totalDate.setTotalAmt(totalAmt_t);
        zxSaOtherEquipSettleList.add(totalDate);
        // 结算单主表数据
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettle.getZxSaOtherEquipSettleId());
        zxSaOtherEquipSettleList.forEach(e -> {
            e.setBillNo(dbZxSaOtherEquipSettle.getBillNo());
            e.setContractNo(dbZxSaOtherEquipSettle.getContractNo());
            e.setSecondName(dbZxSaOtherEquipSettle.getSecondName());
            e.setReportPerson(dbZxSaOtherEquipSettle.getReportPerson());
            e.setReCountPerson(dbZxSaOtherEquipSettle.getReCountPerson());
            e.setCompanyName(dbZxSaOtherEquipSettle.getCompanyName());
            e.setSecondName(dbZxSaOtherEquipSettle.getSecondName());
        });
        return zxSaOtherEquipSettleList;
    }

    @Override
    public ResponseEntity getUreportZxSaOtherEquipSettleList(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // 获取数据
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListForReport(zxSaOtherEquipSettle);
        JSONObject result = new JSONObject();
        result.set("settleList", zxSaOtherEquipSettleList);
        return repEntity.ok(result);
    }

    @Override
    public List<ZxSaOtherEquipSettle> ureportZxSaOtherEquipSettleList_YGZ(ZxSaOtherEquipSettle zxSaOtherEquipSettle) {
        if (zxSaOtherEquipSettle == null) {
            zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        }
        // 获取数据
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.getZxSaOtherEquipSettleListForReport(zxSaOtherEquipSettle);
        return zxSaOtherEquipSettleList;
    }

}
