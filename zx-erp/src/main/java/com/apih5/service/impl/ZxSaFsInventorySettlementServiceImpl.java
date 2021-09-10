package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSaFsInventorySettlementDetailMapper;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlementDetail;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaFsInventorySettlementMapper;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlement;
import com.apih5.service.ZxSaFsInventorySettlementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaFsInventorySettlementService")
public class ZxSaFsInventorySettlementServiceImpl implements ZxSaFsInventorySettlementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementMapper zxSaFsInventorySettlementMapper;

    @Autowired(required = true)
    private ZxSaFsInventorySettlementDetailMapper zxSaFsInventorySettlementDetailMapper;

    @Override
    public ResponseEntity getZxSaFsInventorySettlementListByCondition(ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        if (zxSaFsInventorySettlement == null) {
            zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
        }
        // 分页查询
        PageHelper.startPage(zxSaFsInventorySettlement.getPage(), zxSaFsInventorySettlement.getLimit());
        // 获取数据
        List<ZxSaFsInventorySettlement> zxSaFsInventorySettlementList = zxSaFsInventorySettlementMapper.selectByZxSaFsInventorySettlementList(zxSaFsInventorySettlement);
        // 得到分页信息
        PageInfo<ZxSaFsInventorySettlement> p = new PageInfo<>(zxSaFsInventorySettlementList);

        return repEntity.okList(zxSaFsInventorySettlementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        if (zxSaFsInventorySettlement == null) {
            zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
        }
        // 获取数据
        ZxSaFsInventorySettlement dbZxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsInventorySettlement.getZxSaFsInventorySettlementId());
        // 数据存在
        if (dbZxSaFsInventorySettlement != null) {
            return repEntity.ok(dbZxSaFsInventorySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsInventorySettlement(ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsInventorySettlement.setZxSaFsInventorySettlementId(UuidUtil.generate());
        zxSaFsInventorySettlement.setCreateUserInfo(userKey, realName);
        int flag = zxSaFsInventorySettlementMapper.insert(zxSaFsInventorySettlement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaFsInventorySettlement);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsInventorySettlement(ZxSaFsInventorySettlement zxSaFsInventorySettlement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsInventorySettlement dbZxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectByPrimaryKey(zxSaFsInventorySettlement.getZxSaFsInventorySettlementId());
        if (dbZxSaFsInventorySettlement != null && StrUtil.isNotEmpty(dbZxSaFsInventorySettlement.getZxSaFsInventorySettlementId())) {
            // 项目ID
            dbZxSaFsInventorySettlement.setOrgID(zxSaFsInventorySettlement.getOrgID());
            // 项目名称
            dbZxSaFsInventorySettlement.setOrgName(zxSaFsInventorySettlement.getOrgName());
            // 附属合同ID
            dbZxSaFsInventorySettlement.setZxCtFsContractId(zxSaFsInventorySettlement.getZxCtFsContractId());
            // 附属类结算表ID
            dbZxSaFsInventorySettlement.setZxSaFsSettlementId(zxSaFsInventorySettlement.getZxSaFsSettlementId());
            // 结算单编号
            dbZxSaFsInventorySettlement.setBillNo(zxSaFsInventorySettlement.getBillNo());
            // 结算期次
            dbZxSaFsInventorySettlement.setPeriod(zxSaFsInventorySettlement.getPeriod());
            // 签认单ID
            dbZxSaFsInventorySettlement.setSignedOrders(zxSaFsInventorySettlement.getSignedOrders());
            // 签认单编号
            dbZxSaFsInventorySettlement.setSignedNos(zxSaFsInventorySettlement.getSignedNos());
            // 含税合同金额(万元)
            dbZxSaFsInventorySettlement.setContractAmt(zxSaFsInventorySettlement.getContractAmt());
            // 变更后含税合同金额(万元)
            dbZxSaFsInventorySettlement.setChangeAmt(zxSaFsInventorySettlement.getChangeAmt());
            // 本期清单结算含税金额(元)
            dbZxSaFsInventorySettlement.setThisAmt(zxSaFsInventorySettlement.getThisAmt());
            // 累计清单结算含税金额(元)
            dbZxSaFsInventorySettlement.setTotalAmt(zxSaFsInventorySettlement.getTotalAmt());
            // 上期末清单结算金额(元)
            dbZxSaFsInventorySettlement.setUpAmt(zxSaFsInventorySettlement.getUpAmt());
            // 最后编辑时间
            dbZxSaFsInventorySettlement.setEditTime(zxSaFsInventorySettlement.getEditTime());
            // 所属公司ID
            dbZxSaFsInventorySettlement.setComID(zxSaFsInventorySettlement.getComID());
            // 所属公司名称
            dbZxSaFsInventorySettlement.setComName(zxSaFsInventorySettlement.getComName());
            // 所属公司排序
            dbZxSaFsInventorySettlement.setComOrders(zxSaFsInventorySettlement.getComOrders());
            // 合同类型
            dbZxSaFsInventorySettlement.setContrType(zxSaFsInventorySettlement.getContrType());
            // 本期清单结算不含税金额(元)
            dbZxSaFsInventorySettlement.setThisAmtNoTax(zxSaFsInventorySettlement.getThisAmtNoTax());
            // 本期清单结算税额(元)
            dbZxSaFsInventorySettlement.setThisAmtTax(zxSaFsInventorySettlement.getThisAmtTax());
            // 是否抵扣
            dbZxSaFsInventorySettlement.setIsDeduct(zxSaFsInventorySettlement.getIsDeduct());
            // 税率(%)
            dbZxSaFsInventorySettlement.setTaxRate(zxSaFsInventorySettlement.getTaxRate());
            // 是否首次结算
            dbZxSaFsInventorySettlement.setIsFirst(zxSaFsInventorySettlement.getIsFirst());
            // 备注
            dbZxSaFsInventorySettlement.setRemarks(zxSaFsInventorySettlement.getRemarks());
            // 排序
            dbZxSaFsInventorySettlement.setSort(zxSaFsInventorySettlement.getSort());
            // 共通
            dbZxSaFsInventorySettlement.setModifyUserInfo(userKey, realName);
            flag = zxSaFsInventorySettlementMapper.updateByPrimaryKey(dbZxSaFsInventorySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaFsInventorySettlement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlement(List<ZxSaFsInventorySettlement> zxSaFsInventorySettlementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaFsInventorySettlementList != null && zxSaFsInventorySettlementList.size() > 0) {
            ZxSaFsInventorySettlement zxSaFsInventorySettlement = new ZxSaFsInventorySettlement();
            zxSaFsInventorySettlement.setModifyUserInfo(userKey, realName);
            flag = zxSaFsInventorySettlementMapper.batchDeleteUpdateZxSaFsInventorySettlement(zxSaFsInventorySettlementList, zxSaFsInventorySettlement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaFsInventorySettlementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询清单主从表
     *
     * @param zxSaFsInventorySettlement
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSaFsInventoryAndInventoryDetail(ZxSaFsInventorySettlement zxSaFsInventorySettlement) {

        // 获取数据
        ZxSaFsInventorySettlement dbZxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectBysettlementKey(zxSaFsInventorySettlement.getZxSaFsSettlementId());
        // 数据存在
        if (dbZxSaFsInventorySettlement != null) {
            ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail = new ZxSaFsInventorySettlementDetail();
            zxSaFsInventorySettlementDetail.setZxSaFsInventorySettlementId(dbZxSaFsInventorySettlement.getZxSaFsInventorySettlementId());
            dbZxSaFsInventorySettlement.setDetailList(zxSaFsInventorySettlementDetailMapper.selectByZxSaFsInventorySettlementDetailList(zxSaFsInventorySettlementDetail));
            return repEntity.ok(dbZxSaFsInventorySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    /**
     * 查询清单
     *
     * @param zxSaFsSettlement
     * @author
     */
    public ResponseEntity getZxSaFsInventory(ZxSaFsSettlement zxSaFsSettlement) {
        // 获取数据
        ZxSaFsInventorySettlement dbZxSaFsInventorySettlement = zxSaFsInventorySettlementMapper.selectBysettlementKey(zxSaFsSettlement.getZxSaFsSettlementId());
        if (dbZxSaFsInventorySettlement != null) {
            return repEntity.ok(dbZxSaFsInventorySettlement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    ;

}
