package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSaFsPaySettlementDetailMapper;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlementDetail;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaFsPaySettlementMapper;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlement;
import com.apih5.service.ZxSaFsPaySettlementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaFsPaySettlementService")
public class ZxSaFsPaySettlementServiceImpl implements ZxSaFsPaySettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsPaySettlementMapper zxSaFsPaySettlementMapper;

    @Autowired(required = true)
    private ZxSaFsPaySettlementDetailMapper zxSaFsPaySettlementDetailMapper;

    @Override
    public ResponseEntity getZxSaFsPaySettlementListByCondition(ZxSaFsPaySettlement zxSaFsPaySettlement) {
        if (zxSaFsPaySettlement == null) {
            zxSaFsPaySettlement = new ZxSaFsPaySettlement();
        }
        // 分页查询
        PageHelper.startPage(zxSaFsPaySettlement.getPage(), zxSaFsPaySettlement.getLimit());
        // 获取数据
        List<ZxSaFsPaySettlement> zxSaFsPaySettlementList = zxSaFsPaySettlementMapper.selectByZxSaFsPaySettlementList(zxSaFsPaySettlement);
        // 得到分页信息
        PageInfo<ZxSaFsPaySettlement> p = new PageInfo<>(zxSaFsPaySettlementList);

        return repEntity.okList(zxSaFsPaySettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsPaySettlementDetail(ZxSaFsPaySettlement zxSaFsPaySettlement) {
        if (zxSaFsPaySettlement == null) {
            zxSaFsPaySettlement = new ZxSaFsPaySettlement();
        }
        // 获取数据
        ZxSaFsPaySettlement dbZxSaFsPaySettlement = zxSaFsPaySettlementMapper.selectByPrimaryKey(zxSaFsPaySettlement.getZxSaFsPaySettlementId());
        // 数据存在
        if (dbZxSaFsPaySettlement != null) {
            return repEntity.ok(dbZxSaFsPaySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsPaySettlement(ZxSaFsPaySettlement zxSaFsPaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsPaySettlement.setZxSaFsPaySettlementId(UuidUtil.generate());
        zxSaFsPaySettlement.setCreateUserInfo(userKey, realName);
        int flag = zxSaFsPaySettlementMapper.insert(zxSaFsPaySettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaFsPaySettlement);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsPaySettlement(ZxSaFsPaySettlement zxSaFsPaySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsPaySettlement dbZxSaFsPaySettlement = zxSaFsPaySettlementMapper.selectByPrimaryKey(zxSaFsPaySettlement.getZxSaFsPaySettlementId());
        if (dbZxSaFsPaySettlement != null && StrUtil.isNotEmpty(dbZxSaFsPaySettlement.getZxSaFsPaySettlementId())) {
            // 项目ID
            dbZxSaFsPaySettlement.setOrgID(zxSaFsPaySettlement.getOrgID());
            // 项目名称
            dbZxSaFsPaySettlement.setOrgName(zxSaFsPaySettlement.getOrgName());
            // 附属合同ID
            dbZxSaFsPaySettlement.setZxCtFsContractId(zxSaFsPaySettlement.getZxCtFsContractId());
            // 附属类结算表ID
            dbZxSaFsPaySettlement.setZxSaFsSettlementId(zxSaFsPaySettlement.getZxSaFsSettlementId());
            // 结算单编号
            dbZxSaFsPaySettlement.setBillNo(zxSaFsPaySettlement.getBillNo());
            // 结算期次
            dbZxSaFsPaySettlement.setPeriod(zxSaFsPaySettlement.getPeriod());
            // 本期支付项结算金额(元)
            dbZxSaFsPaySettlement.setThisAmt(zxSaFsPaySettlement.getThisAmt());
            // 累计支付项结算金额(元)
            dbZxSaFsPaySettlement.setTotalAmt(zxSaFsPaySettlement.getTotalAmt());
            // 上期末累计支付项结算金额(元)
            dbZxSaFsPaySettlement.setUpAmt(zxSaFsPaySettlement.getUpAmt());
            // 最后编辑时间
            dbZxSaFsPaySettlement.setEditTime(zxSaFsPaySettlement.getEditTime());
            // 所属公司ID
            dbZxSaFsPaySettlement.setComID(zxSaFsPaySettlement.getComID());
            // 所属公司名称
            dbZxSaFsPaySettlement.setComName(zxSaFsPaySettlement.getComName());
            // 所属公司排序
            dbZxSaFsPaySettlement.setComOrders(zxSaFsPaySettlement.getComOrders());
            // 本期进退场费
            dbZxSaFsPaySettlement.setInOutAmt(zxSaFsPaySettlement.getInOutAmt());
            // 上期末进退场费
            dbZxSaFsPaySettlement.setUpInOutAmt(zxSaFsPaySettlement.getUpInOutAmt());
            // 本期奖罚
            dbZxSaFsPaySettlement.setFineAmt(zxSaFsPaySettlement.getFineAmt());
            // 上期末奖罚
            dbZxSaFsPaySettlement.setUpFineAmt(zxSaFsPaySettlement.getUpFineAmt());
            // 本期伙食费
            dbZxSaFsPaySettlement.setFoodAmt(zxSaFsPaySettlement.getFoodAmt());
            // 上期末伙食费
            dbZxSaFsPaySettlement.setUpFoodAmt(zxSaFsPaySettlement.getUpFoodAmt());
            // 本期其它结算支付项
            dbZxSaFsPaySettlement.setOtherAmt(zxSaFsPaySettlement.getOtherAmt());
            // 上期末其它结算支付项
            dbZxSaFsPaySettlement.setUpOtherAmt(zxSaFsPaySettlement.getUpOtherAmt());
            // 合同类型
            dbZxSaFsPaySettlement.setContrType(zxSaFsPaySettlement.getContrType());
            // 本期支付项结算不含税金额(元)
            dbZxSaFsPaySettlement.setThisAmtNoTax(zxSaFsPaySettlement.getThisAmtNoTax());
            // 本期支付项结算税额(元)
            dbZxSaFsPaySettlement.setThisAmtTax(zxSaFsPaySettlement.getThisAmtTax());
            // 备注
            dbZxSaFsPaySettlement.setRemarks(zxSaFsPaySettlement.getRemarks());
            // 排序
            dbZxSaFsPaySettlement.setSort(zxSaFsPaySettlement.getSort());
            // 共通
            dbZxSaFsPaySettlement.setModifyUserInfo(userKey, realName);
            flag = zxSaFsPaySettlementMapper.updateByPrimaryKey(dbZxSaFsPaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaFsPaySettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlement(List<ZxSaFsPaySettlement> zxSaFsPaySettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaFsPaySettlementList != null && zxSaFsPaySettlementList.size() > 0) {
            ZxSaFsPaySettlement zxSaFsPaySettlement = new ZxSaFsPaySettlement();
            zxSaFsPaySettlement.setModifyUserInfo(userKey, realName);
            flag = zxSaFsPaySettlementMapper.batchDeleteUpdateZxSaFsPaySettlement(zxSaFsPaySettlementList, zxSaFsPaySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaFsPaySettlementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询支付项和支付项明细
     *
     * @param
     * @author suncg
     */
    public ResponseEntity getZxSaFsPaySettlementAndPaySettlementDetail(ZxSaFsSettlement zxSaFsSettlement) {

        // 获取数据
        ZxSaFsPaySettlement zxSaFsPaySettlement = zxSaFsPaySettlementMapper.selectBysettlementKey(zxSaFsSettlement.getZxSaFsSettlementId());
        // 数据存在
        if (zxSaFsPaySettlement != null) {
            ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail = new ZxSaFsPaySettlementDetail();
            zxSaFsPaySettlementDetail.setZxSaFsPaySettlementId(zxSaFsPaySettlement.getZxSaFsPaySettlementId());
            PageHelper.startPage(zxSaFsSettlement.getPage(), zxSaFsSettlement.getLimit());
            List<ZxSaFsPaySettlementDetail> zxSaFsPaySettlementDetails = zxSaFsPaySettlementDetailMapper.selectByZxSaFsPaySettlementDetailList(zxSaFsPaySettlementDetail);
            // 得到分页信息
            PageInfo<ZxSaFsPaySettlementDetail> p = new PageInfo<>(zxSaFsPaySettlementDetails);
            zxSaFsPaySettlement.setPayList(zxSaFsPaySettlementDetails);
            zxSaFsPaySettlement.setTotalNumber(p.getTotal());
            return repEntity.ok(zxSaFsPaySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    /**
     * 查询支付项
     *
     * @param zxSaFsSettlement
     * @author suncg
     */
    public ResponseEntity getZxSaFsPaySettlement(ZxSaFsSettlement zxSaFsSettlement) {
        ZxSaFsPaySettlement zxSaFsPaySettlement = zxSaFsPaySettlementMapper.selectBysettlementKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (zxSaFsPaySettlement != null) {
            return repEntity.ok(zxSaFsPaySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    ;

}
