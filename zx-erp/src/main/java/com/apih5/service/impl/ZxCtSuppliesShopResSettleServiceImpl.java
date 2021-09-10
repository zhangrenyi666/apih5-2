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
import com.apih5.mybatis.dao.ZxCtSuppliesShopResSettleMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;
import com.apih5.service.ZxCtSuppliesShopResSettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesShopResSettleService")
public class ZxCtSuppliesShopResSettleServiceImpl implements ZxCtSuppliesShopResSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleMapper zxCtSuppliesShopResSettleMapper;

    @Override
    public ResponseEntity getZxCtSuppliesShopResSettleListByCondition(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        if (zxCtSuppliesShopResSettle == null) {
            zxCtSuppliesShopResSettle = new ZxCtSuppliesShopResSettle();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesShopResSettle.getPage(),zxCtSuppliesShopResSettle.getLimit());
        // 获取数据
        List<ZxCtSuppliesShopResSettle> zxCtSuppliesShopResSettleList = zxCtSuppliesShopResSettleMapper.selectByZxCtSuppliesShopResSettleList(zxCtSuppliesShopResSettle);
        // 得到分页信息
        PageInfo<ZxCtSuppliesShopResSettle> p = new PageInfo<>(zxCtSuppliesShopResSettleList);

        return repEntity.okList(zxCtSuppliesShopResSettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesShopResSettleDetail(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        if (zxCtSuppliesShopResSettle == null) {
            zxCtSuppliesShopResSettle = new ZxCtSuppliesShopResSettle();
        }
        // 获取数据
        ZxCtSuppliesShopResSettle dbZxCtSuppliesShopResSettle = zxCtSuppliesShopResSettleMapper.selectByPrimaryKey(zxCtSuppliesShopResSettle.getZxCtSuppliesShopResSettleId());
        // 数据存在
        if (dbZxCtSuppliesShopResSettle != null) {
            return repEntity.ok(dbZxCtSuppliesShopResSettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesShopResSettle(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesShopResSettle.setZxCtSuppliesShopResSettleId(UuidUtil.generate());
        zxCtSuppliesShopResSettle.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesShopResSettleMapper.insert(zxCtSuppliesShopResSettle);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesShopResSettle);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesShopResSettle(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesShopResSettle dbZxCtSuppliesShopResSettle = zxCtSuppliesShopResSettleMapper.selectByPrimaryKey(zxCtSuppliesShopResSettle.getZxCtSuppliesShopResSettleId());
        if (dbZxCtSuppliesShopResSettle != null && StrUtil.isNotEmpty(dbZxCtSuppliesShopResSettle.getZxCtSuppliesShopResSettleId())) {
           // 结算期次
           dbZxCtSuppliesShopResSettle.setPeriod(zxCtSuppliesShopResSettle.getPeriod());
           // 结算单编号
           dbZxCtSuppliesShopResSettle.setBillNo(zxCtSuppliesShopResSettle.getBillNo());
           // 上期末结算金额
           dbZxCtSuppliesShopResSettle.setUpAmt(zxCtSuppliesShopResSettle.getUpAmt());
           // 物资结算表ID
           dbZxCtSuppliesShopResSettle.setBillID(zxCtSuppliesShopResSettle.getBillID());
           // 累计物资细目含税结算金额(元)
           dbZxCtSuppliesShopResSettle.setTotalAmt(zxCtSuppliesShopResSettle.getTotalAmt());
           // 所属公司排序
           dbZxCtSuppliesShopResSettle.setComOrders(zxCtSuppliesShopResSettle.getComOrders());
           // 本期物资细目不含税结算金额(元)
           dbZxCtSuppliesShopResSettle.setThisAmtNoTax(zxCtSuppliesShopResSettle.getThisAmtNoTax());
           // 本期物资细目含税结算税额(元)
           dbZxCtSuppliesShopResSettle.setThisAmtTax(zxCtSuppliesShopResSettle.getThisAmtTax());
           // 项目ID
           dbZxCtSuppliesShopResSettle.setOrgID(zxCtSuppliesShopResSettle.getOrgID());
           // 所属公司名称
           dbZxCtSuppliesShopResSettle.setComName(zxCtSuppliesShopResSettle.getComName());
           // 项目名称
           dbZxCtSuppliesShopResSettle.setOrgName(zxCtSuppliesShopResSettle.getOrgName());
           // 变更后含税合同金额(万元)
           dbZxCtSuppliesShopResSettle.setChangeAmt(zxCtSuppliesShopResSettle.getChangeAmt());
           // 所属公司ID
           dbZxCtSuppliesShopResSettle.setComID(zxCtSuppliesShopResSettle.getComID());
           // 签认单编号
           dbZxCtSuppliesShopResSettle.setSignedNos(zxCtSuppliesShopResSettle.getSignedNos());
           // 本期物资细目含税结算金额(元)
           dbZxCtSuppliesShopResSettle.setThisAmt(zxCtSuppliesShopResSettle.getThisAmt());
           // 合同ID
           dbZxCtSuppliesShopResSettle.setContractID(zxCtSuppliesShopResSettle.getContractID());
           // 最后编辑时间
           dbZxCtSuppliesShopResSettle.setEditTime(zxCtSuppliesShopResSettle.getEditTime());
           // 含税合同金额(万元)
           dbZxCtSuppliesShopResSettle.setContractAmt(zxCtSuppliesShopResSettle.getContractAmt());
           // 备注
           dbZxCtSuppliesShopResSettle.setRemarks(zxCtSuppliesShopResSettle.getRemarks());
           // 排序
           dbZxCtSuppliesShopResSettle.setSort(zxCtSuppliesShopResSettle.getSort());
           // 共通
           dbZxCtSuppliesShopResSettle.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopResSettleMapper.updateByPrimaryKey(dbZxCtSuppliesShopResSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesShopResSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettle(List<ZxCtSuppliesShopResSettle> zxCtSuppliesShopResSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesShopResSettleList != null && zxCtSuppliesShopResSettleList.size() > 0) {
           ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle = new ZxCtSuppliesShopResSettle();
           zxCtSuppliesShopResSettle.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesShopResSettleMapper.batchDeleteUpdateZxCtSuppliesShopResSettle(zxCtSuppliesShopResSettleList, zxCtSuppliesShopResSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesShopResSettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
