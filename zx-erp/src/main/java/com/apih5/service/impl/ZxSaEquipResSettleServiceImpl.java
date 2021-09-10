package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaEquipResSettleItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleMapper;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;
import com.apih5.service.ZxSaEquipResSettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaEquipResSettleService")
public class ZxSaEquipResSettleServiceImpl implements ZxSaEquipResSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipResSettleMapper zxSaEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaEquipResSettleItemMapper zxSaEquipResSettleItemMapper;
    
    @Override
    public ResponseEntity getZxSaEquipResSettleListByCondition(ZxSaEquipResSettle zxSaEquipResSettle) {
        if (zxSaEquipResSettle == null) {
            zxSaEquipResSettle = new ZxSaEquipResSettle();
        }
        // 分页查询
        PageHelper.startPage(zxSaEquipResSettle.getPage(),zxSaEquipResSettle.getLimit());
        // 获取数据
        List<ZxSaEquipResSettle> zxSaEquipResSettleList = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(zxSaEquipResSettle);
        // 得到分页信息
        PageInfo<ZxSaEquipResSettle> p = new PageInfo<>(zxSaEquipResSettleList);

        return repEntity.okList(zxSaEquipResSettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipResSettleDetail(ZxSaEquipResSettle zxSaEquipResSettle) {
        if (zxSaEquipResSettle == null) {
            zxSaEquipResSettle = new ZxSaEquipResSettle();
        }
        // 获取数据
        ZxSaEquipResSettle dbZxSaEquipResSettle = zxSaEquipResSettleMapper.selectByPrimaryKey(zxSaEquipResSettle.getZxSaEquipResSettleId());
        // 数据存在
        if (dbZxSaEquipResSettle != null) {
            return repEntity.ok(dbZxSaEquipResSettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipResSettle(ZxSaEquipResSettle zxSaEquipResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(StrUtil.isEmpty(zxSaEquipResSettle.getZxSaEquipSettleAuditId())) {
        	 return repEntity.layerMessage("no", "结算单id【zxSaEquipSettleAuditId】不能为空！");
        }
        //add清单结算
        zxSaEquipResSettle.setZxSaEquipResSettleId(UuidUtil.generate());
        zxSaEquipResSettle.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipResSettleMapper.insert(zxSaEquipResSettle);
        //add清单结算明细
        if(zxSaEquipResSettle.getZxSaEquipResSettleItemList() != null && zxSaEquipResSettle.getZxSaEquipResSettleItemList().size() >0) {
        	for (ZxSaEquipResSettleItem zxSaEquipResSettleItem : zxSaEquipResSettle.getZxSaEquipResSettleItemList()) {
        		zxSaEquipResSettleItem.setZxSaEquipResSettleItemId(UuidUtil.generate());
        		zxSaEquipResSettleItem.setZxSaEquipResSettleId(zxSaEquipResSettle.getZxSaEquipResSettleId());
                zxSaEquipResSettleItem.setCreateUserInfo(userKey, realName);
        		flag = zxSaEquipResSettleItemMapper.insert(zxSaEquipResSettleItem);
        	}
        }
        if (flag == 0) {
        	return repEntity.errorSave();
        } else {
        	return repEntity.ok("sys.data.sava", zxSaEquipResSettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipResSettle(ZxSaEquipResSettle zxSaEquipResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaEquipResSettle dbZxSaEquipResSettle = zxSaEquipResSettleMapper.selectByPrimaryKey(zxSaEquipResSettle.getZxSaEquipResSettleId());
        if (dbZxSaEquipResSettle != null && StrUtil.isNotEmpty(dbZxSaEquipResSettle.getZxSaEquipResSettleId())) {
           // 项目id
           dbZxSaEquipResSettle.setOrgID(zxSaEquipResSettle.getOrgID());
           // 项目名称
           dbZxSaEquipResSettle.setOrgName(zxSaEquipResSettle.getOrgName());
           // 合同id
           dbZxSaEquipResSettle.setContractID(zxSaEquipResSettle.getContractID());
           // 结算单编号
           dbZxSaEquipResSettle.setBillNo(zxSaEquipResSettle.getBillNo());
           // 结算期次
           dbZxSaEquipResSettle.setPeriod(zxSaEquipResSettle.getPeriod());
           // 签认单编号
           dbZxSaEquipResSettle.setSignedNos(zxSaEquipResSettle.getSignedNos());
           // 含税合同金额(元)
           dbZxSaEquipResSettle.setContractAmt(zxSaEquipResSettle.getContractAmt());
           // 变更后含税合同金额(元)
           dbZxSaEquipResSettle.setChangeAmt(zxSaEquipResSettle.getChangeAmt());
           // 本期清单结算含税金额(元)
           dbZxSaEquipResSettle.setThisAmt(zxSaEquipResSettle.getThisAmt());
           // 累计清单结算含税金额(元)
           dbZxSaEquipResSettle.setTotalAmt(zxSaEquipResSettle.getTotalAmt());
           // 上期末清单结算金额(元)
           dbZxSaEquipResSettle.setUpAmt(zxSaEquipResSettle.getUpAmt());
           // 所属公司id
           dbZxSaEquipResSettle.setComID(zxSaEquipResSettle.getComID());
           // 所属公司名称
           dbZxSaEquipResSettle.setComName(zxSaEquipResSettle.getComName());
           // 0
           dbZxSaEquipResSettle.setContrType(zxSaEquipResSettle.getContrType());
           // 本期清单结算不含税金额(元)
           dbZxSaEquipResSettle.setThisAmtNoTax(zxSaEquipResSettle.getThisAmtNoTax());
           // 本期清单结算税额(元)
           dbZxSaEquipResSettle.setThisAmtTax(zxSaEquipResSettle.getThisAmtTax());
           // 共通
           dbZxSaEquipResSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipResSettleMapper.updateByPrimaryKey(dbZxSaEquipResSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipResSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipResSettle(List<ZxSaEquipResSettle> zxSaEquipResSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaEquipResSettleList != null && zxSaEquipResSettleList.size() > 0) {
           ZxSaEquipResSettle zxSaEquipResSettle = new ZxSaEquipResSettle();
           zxSaEquipResSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipResSettleMapper.batchDeleteUpdateZxSaEquipResSettle(zxSaEquipResSettleList, zxSaEquipResSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipResSettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
