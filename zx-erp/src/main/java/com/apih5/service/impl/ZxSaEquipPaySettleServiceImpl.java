package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleMapper;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;
import com.apih5.service.ZxSaEquipPaySettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaEquipPaySettleService")
public class ZxSaEquipPaySettleServiceImpl implements ZxSaEquipPaySettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipPaySettleMapper zxSaEquipPaySettleMapper;

    @Override
    public ResponseEntity getZxSaEquipPaySettleListByCondition(ZxSaEquipPaySettle zxSaEquipPaySettle) {
        if (zxSaEquipPaySettle == null) {
            zxSaEquipPaySettle = new ZxSaEquipPaySettle();
        }
        // 分页查询
        PageHelper.startPage(zxSaEquipPaySettle.getPage(),zxSaEquipPaySettle.getLimit());
        // 获取数据
        List<ZxSaEquipPaySettle> zxSaEquipPaySettleList = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettleList(zxSaEquipPaySettle);
        // 得到分页信息
        PageInfo<ZxSaEquipPaySettle> p = new PageInfo<>(zxSaEquipPaySettleList);

        return repEntity.okList(zxSaEquipPaySettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipPaySettleDetail(ZxSaEquipPaySettle zxSaEquipPaySettle) {
        if (zxSaEquipPaySettle == null) {
            zxSaEquipPaySettle = new ZxSaEquipPaySettle();
        }
        // 获取数据
        ZxSaEquipPaySettle dbZxSaEquipPaySettle = zxSaEquipPaySettleMapper.selectByPrimaryKey(zxSaEquipPaySettle.getZxSaEquipPaySettleId());
        // 数据存在
        if (dbZxSaEquipPaySettle != null) {
            return repEntity.ok(dbZxSaEquipPaySettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipPaySettle(ZxSaEquipPaySettle zxSaEquipPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaEquipPaySettle.setZxSaEquipPaySettleId(UuidUtil.generate());
        zxSaEquipPaySettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaEquipPaySettleMapper.insert(zxSaEquipPaySettle);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaEquipPaySettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipPaySettle(ZxSaEquipPaySettle zxSaEquipPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaEquipPaySettle dbZxSaEquipPaySettle = zxSaEquipPaySettleMapper.selectByPrimaryKey(zxSaEquipPaySettle.getZxSaEquipPaySettleId());
        if (dbZxSaEquipPaySettle != null && StrUtil.isNotEmpty(dbZxSaEquipPaySettle.getZxSaEquipPaySettleId())) {
           // 结算单id
           dbZxSaEquipPaySettle.setZxSaEquipSettleAuditId(zxSaEquipPaySettle.getZxSaEquipSettleAuditId());
           // 项目id
           dbZxSaEquipPaySettle.setOrgID(zxSaEquipPaySettle.getOrgID());
           // 项目名称
           dbZxSaEquipPaySettle.setOrgName(zxSaEquipPaySettle.getOrgName());
           // 合同id
           dbZxSaEquipPaySettle.setContractID(zxSaEquipPaySettle.getContractID());
           // 结算单编号
           dbZxSaEquipPaySettle.setBillNo(zxSaEquipPaySettle.getBillNo());
           // 结算期次
           dbZxSaEquipPaySettle.setPeriod(zxSaEquipPaySettle.getPeriod());
           // 本期支付项结算金额(元)
           dbZxSaEquipPaySettle.setThisAmt(zxSaEquipPaySettle.getThisAmt());
           // 累计支付项结算金额(元)
           dbZxSaEquipPaySettle.setTotalAmt(zxSaEquipPaySettle.getTotalAmt());
           // 上期末累计支付项结算金额(元)
           dbZxSaEquipPaySettle.setUpAmt(zxSaEquipPaySettle.getUpAmt());
           // 所属公司id
           dbZxSaEquipPaySettle.setComID(zxSaEquipPaySettle.getComID());
           // 所属公司名称
           dbZxSaEquipPaySettle.setComName(zxSaEquipPaySettle.getComName());
           // 本期进退场费
           dbZxSaEquipPaySettle.setInOutAmt(zxSaEquipPaySettle.getInOutAmt());
           // 上期末进退场费
           dbZxSaEquipPaySettle.setUpInOutAmt(zxSaEquipPaySettle.getUpInOutAmt());
           // 本期奖罚
           dbZxSaEquipPaySettle.setFineAmt(zxSaEquipPaySettle.getFineAmt());
           // 上期末奖罚
           dbZxSaEquipPaySettle.setUpFineAmt(zxSaEquipPaySettle.getUpFineAmt());
           // 本期伙食费
           dbZxSaEquipPaySettle.setFoodAmt(zxSaEquipPaySettle.getFoodAmt());
           // 上期末伙食费
           dbZxSaEquipPaySettle.setUpFoodAmt(zxSaEquipPaySettle.getUpFoodAmt());
           // 本期其他款项
           dbZxSaEquipPaySettle.setOtherAmt(zxSaEquipPaySettle.getOtherAmt());
           // 上期末其他款项
           dbZxSaEquipPaySettle.setUpOtherAmt(zxSaEquipPaySettle.getUpOtherAmt());
           // 合同类型
           dbZxSaEquipPaySettle.setContrType(zxSaEquipPaySettle.getContrType());
           // 本期末支付项结算不含税金额(元）
           dbZxSaEquipPaySettle.setThisAmtNoTax(zxSaEquipPaySettle.getThisAmtNoTax());
           // 本期末支付项结算金额(元)
           dbZxSaEquipPaySettle.setThisAmtTax(zxSaEquipPaySettle.getThisAmtTax());
           // 共通
           dbZxSaEquipPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipPaySettleMapper.updateByPrimaryKey(dbZxSaEquipPaySettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipPaySettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettle(List<ZxSaEquipPaySettle> zxSaEquipPaySettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaEquipPaySettleList != null && zxSaEquipPaySettleList.size() > 0) {
           ZxSaEquipPaySettle zxSaEquipPaySettle = new ZxSaEquipPaySettle();
           zxSaEquipPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipPaySettleMapper.batchDeleteUpdateZxSaEquipPaySettle(zxSaEquipPaySettleList, zxSaEquipPaySettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipPaySettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
