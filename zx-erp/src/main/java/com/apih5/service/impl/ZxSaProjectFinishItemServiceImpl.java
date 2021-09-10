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
import com.apih5.mybatis.dao.ZxSaProjectFinishItemMapper;
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;
import com.apih5.service.ZxSaProjectFinishItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectFinishItemService")
public class ZxSaProjectFinishItemServiceImpl implements ZxSaProjectFinishItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectFinishItemMapper zxSaProjectFinishItemMapper;

    @Override
    public ResponseEntity getZxSaProjectFinishItemListByCondition(ZxSaProjectFinishItem zxSaProjectFinishItem) {
        if (zxSaProjectFinishItem == null) {
            zxSaProjectFinishItem = new ZxSaProjectFinishItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectFinishItem.getPage(),zxSaProjectFinishItem.getLimit());
        // 获取数据
        List<ZxSaProjectFinishItem> zxSaProjectFinishItemList = zxSaProjectFinishItemMapper.selectByZxSaProjectFinishItemList(zxSaProjectFinishItem);
        // 得到分页信息
        PageInfo<ZxSaProjectFinishItem> p = new PageInfo<>(zxSaProjectFinishItemList);

        return repEntity.okList(zxSaProjectFinishItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectFinishItemDetails(ZxSaProjectFinishItem zxSaProjectFinishItem) {
        if (zxSaProjectFinishItem == null) {
            zxSaProjectFinishItem = new ZxSaProjectFinishItem();
        }
        // 获取数据
        ZxSaProjectFinishItem dbZxSaProjectFinishItem = zxSaProjectFinishItemMapper.selectByPrimaryKey(zxSaProjectFinishItem.getId());
        // 数据存在
        if (dbZxSaProjectFinishItem != null) {
            return repEntity.ok(dbZxSaProjectFinishItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSaProjectFinishItem(ZxSaProjectFinishItem zxSaProjectFinishItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectFinishItem.setId(UuidUtil.generate());
        zxSaProjectFinishItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectFinishItemMapper.insert(zxSaProjectFinishItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSaProjectFinishItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectFinishItem(ZxSaProjectFinishItem zxSaProjectFinishItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectFinishItem dbzxSaProjectFinishItem = zxSaProjectFinishItemMapper.selectByPrimaryKey(zxSaProjectFinishItem.getId());
        if (dbzxSaProjectFinishItem != null && StrUtil.isNotEmpty(dbzxSaProjectFinishItem.getId())) {
           // 主表id
           dbzxSaProjectFinishItem.setMainID(zxSaProjectFinishItem.getMainID());
           // 合同id
           dbzxSaProjectFinishItem.setContractID(zxSaProjectFinishItem.getContractID());
           // 序号
           dbzxSaProjectFinishItem.setOrderNum(zxSaProjectFinishItem.getOrderNum());
           // 关闭结算类型编码
           dbzxSaProjectFinishItem.setSettleTypeCode(zxSaProjectFinishItem.getSettleTypeCode());
           // 关闭结算类型
           dbzxSaProjectFinishItem.setSettleType(zxSaProjectFinishItem.getSettleType());
           // 计算结算关闭日期
           dbzxSaProjectFinishItem.setPlanSettleCloseDate(zxSaProjectFinishItem.getPlanSettleCloseDate());
           // 实际结算关闭日期
           dbzxSaProjectFinishItem.setRealSettleCloseDate(zxSaProjectFinishItem.getRealSettleCloseDate());
           // 延期原因
           dbzxSaProjectFinishItem.setDelayReason(zxSaProjectFinishItem.getDelayReason());
           // 填报人
           dbzxSaProjectFinishItem.setReportPerson(zxSaProjectFinishItem.getReportPerson());
           // 填报日期
           dbzxSaProjectFinishItem.setReportDate(zxSaProjectFinishItem.getReportDate());
           // 备注
           dbzxSaProjectFinishItem.setRemark(zxSaProjectFinishItem.getRemark());
           // 所属公司id
           dbzxSaProjectFinishItem.setComID(zxSaProjectFinishItem.getComID());
           // 所属公司名称
           dbzxSaProjectFinishItem.setComName(zxSaProjectFinishItem.getComName());
           // 所属公司排序
           dbzxSaProjectFinishItem.setComOrders(zxSaProjectFinishItem.getComOrders());
           // 
           dbzxSaProjectFinishItem.setCombProp(zxSaProjectFinishItem.getCombProp());
           // 
           dbzxSaProjectFinishItem.setPp1(zxSaProjectFinishItem.getPp1());
           // 
           dbzxSaProjectFinishItem.setPp2(zxSaProjectFinishItem.getPp2());
           // 
           dbzxSaProjectFinishItem.setPp3(zxSaProjectFinishItem.getPp3());
           // 
           dbzxSaProjectFinishItem.setPp4(zxSaProjectFinishItem.getPp4());
           // 
           dbzxSaProjectFinishItem.setPp5(zxSaProjectFinishItem.getPp5());
           // 
           dbzxSaProjectFinishItem.setPp6(zxSaProjectFinishItem.getPp6());
           // 
           dbzxSaProjectFinishItem.setPp7(zxSaProjectFinishItem.getPp7());
           // 
           dbzxSaProjectFinishItem.setPp8(zxSaProjectFinishItem.getPp8());
           // 
           dbzxSaProjectFinishItem.setPp9(zxSaProjectFinishItem.getPp9());
           // 
           dbzxSaProjectFinishItem.setPp10(zxSaProjectFinishItem.getPp10());
           // 共通
           dbzxSaProjectFinishItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectFinishItemMapper.updateByPrimaryKey(dbzxSaProjectFinishItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSaProjectFinishItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectFinishItem(List<ZxSaProjectFinishItem> zxSaProjectFinishItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectFinishItemList != null && zxSaProjectFinishItemList.size() > 0) {
           ZxSaProjectFinishItem zxSaProjectFinishItem = new ZxSaProjectFinishItem();
           zxSaProjectFinishItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectFinishItemMapper.batchDeleteUpdateZxSaProjectFinishItem(zxSaProjectFinishItemList, zxSaProjectFinishItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSaProjectFinishItemList);
        }
    }
}
