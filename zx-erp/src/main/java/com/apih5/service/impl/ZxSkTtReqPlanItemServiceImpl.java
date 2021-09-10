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
import com.apih5.mybatis.dao.ZxSkTtReqPlanItemMapper;
import com.apih5.mybatis.pojo.ZxSkTtReqPlanItem;
import com.apih5.service.ZxSkTtReqPlanItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTtReqPlanItemService")
public class ZxSkTtReqPlanItemServiceImpl implements ZxSkTtReqPlanItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTtReqPlanItemMapper zxSkTtReqPlanItemMapper;

    @Override
    public ResponseEntity getZxSkTtReqPlanItemListByCondition(ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        if (zxSkTtReqPlanItem == null) {
            zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkTtReqPlanItem.getPage(),zxSkTtReqPlanItem.getLimit());
        // 获取数据
        List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
        // 得到分页信息
        PageInfo<ZxSkTtReqPlanItem> p = new PageInfo<>(zxSkTtReqPlanItemList);

        return repEntity.okList(zxSkTtReqPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTtReqPlanItemDetails(ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        if (zxSkTtReqPlanItem == null) {
            zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
        }
        // 获取数据
        ZxSkTtReqPlanItem dbZxSkTtReqPlanItem = zxSkTtReqPlanItemMapper.selectByPrimaryKey(zxSkTtReqPlanItem.getId());
        // 数据存在
        if (dbZxSkTtReqPlanItem != null) {
            return repEntity.ok(dbZxSkTtReqPlanItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkTtReqPlanItem(ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTtReqPlanItem.setId(UuidUtil.generate());
        zxSkTtReqPlanItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkTtReqPlanItemMapper.insert(zxSkTtReqPlanItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkTtReqPlanItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkTtReqPlanItem(ZxSkTtReqPlanItem zxSkTtReqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTtReqPlanItem dbzxSkTtReqPlanItem = zxSkTtReqPlanItemMapper.selectByPrimaryKey(zxSkTtReqPlanItem.getId());
        if (dbzxSkTtReqPlanItem != null && StrUtil.isNotEmpty(dbzxSkTtReqPlanItem.getId())) {
           // 主表id
           dbzxSkTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlanItem.getTtReqPlanID());
           // 分部分项ID
           dbzxSkTtReqPlanItem.setCbsID(zxSkTtReqPlanItem.getCbsID());
           // 分部分项
           dbzxSkTtReqPlanItem.setCbsName(zxSkTtReqPlanItem.getCbsName());
           // 物资ID
           dbzxSkTtReqPlanItem.setResID(zxSkTtReqPlanItem.getResID());
           // 物资编码
           dbzxSkTtReqPlanItem.setResCode(zxSkTtReqPlanItem.getResCode());
           // 物资名称
           dbzxSkTtReqPlanItem.setResName(zxSkTtReqPlanItem.getResName());
           // 计量单位
           dbzxSkTtReqPlanItem.setUnit(zxSkTtReqPlanItem.getUnit());
           // 规格型号
           dbzxSkTtReqPlanItem.setSpec(zxSkTtReqPlanItem.getSpec());
           // 单价
           dbzxSkTtReqPlanItem.setPrice(zxSkTtReqPlanItem.getPrice());
           // 损耗率
           dbzxSkTtReqPlanItem.setLossRate(zxSkTtReqPlanItem.getLossRate());
           // 编号图纸设计量
           dbzxSkTtReqPlanItem.setDesignNum(zxSkTtReqPlanItem.getDesignNum());
           // 总需用计划量
           dbzxSkTtReqPlanItem.setTotalNum(zxSkTtReqPlanItem.getTotalNum());
           // 变更量
           dbzxSkTtReqPlanItem.setChangeNum(zxSkTtReqPlanItem.getChangeNum());
           // 金额
           dbzxSkTtReqPlanItem.setTotalMoney(zxSkTtReqPlanItem.getTotalMoney());
           // 备注
           dbzxSkTtReqPlanItem.setRemark(zxSkTtReqPlanItem.getRemark());
           // 明细
           dbzxSkTtReqPlanItem.setCombProp(zxSkTtReqPlanItem.getCombProp());
           // 共通
           dbzxSkTtReqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTtReqPlanItemMapper.updateByPrimaryKey(dbzxSkTtReqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTtReqPlanItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTtReqPlanItem(List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTtReqPlanItemList != null && zxSkTtReqPlanItemList.size() > 0) {
           ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
           zxSkTtReqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkTtReqPlanItemMapper.batchDeleteUpdateZxSkTtReqPlanItem(zxSkTtReqPlanItemList, zxSkTtReqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkTtReqPlanItemList);
        }
    }
}
