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
import com.apih5.mybatis.dao.ZxSkSporadicItemMapper;
import com.apih5.mybatis.pojo.ZxSkSporadicItem;
import com.apih5.service.ZxSkSporadicItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkSporadicItemService")
public class ZxSkSporadicItemServiceImpl implements ZxSkSporadicItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkSporadicItemMapper zxSkSporadicItemMapper;

    @Override
    public ResponseEntity getZxSkSporadicItemListByCondition(ZxSkSporadicItem zxSkSporadicItem) {
        if (zxSkSporadicItem == null) {
            zxSkSporadicItem = new ZxSkSporadicItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkSporadicItem.getPage(),zxSkSporadicItem.getLimit());
        // 获取数据
        List<ZxSkSporadicItem> zxSkSporadicItemList = zxSkSporadicItemMapper.selectByZxSkSporadicItemList(zxSkSporadicItem);
        // 得到分页信息
        PageInfo<ZxSkSporadicItem> p = new PageInfo<>(zxSkSporadicItemList);

        return repEntity.okList(zxSkSporadicItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkSporadicItemDetails(ZxSkSporadicItem zxSkSporadicItem) {
        if (zxSkSporadicItem == null) {
            zxSkSporadicItem = new ZxSkSporadicItem();
        }
        // 获取数据
        ZxSkSporadicItem dbZxSkSporadicItem = zxSkSporadicItemMapper.selectByPrimaryKey(zxSkSporadicItem.getId());
        // 数据存在
        if (dbZxSkSporadicItem != null) {
            return repEntity.ok(dbZxSkSporadicItem);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkSporadicItem(ZxSkSporadicItem zxSkSporadicItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkSporadicItem.setId(UuidUtil.generate());
        zxSkSporadicItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkSporadicItemMapper.insert(zxSkSporadicItem);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkSporadicItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkSporadicItem(ZxSkSporadicItem zxSkSporadicItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkSporadicItem dbzxSkSporadicItem = zxSkSporadicItemMapper.selectByPrimaryKey(zxSkSporadicItem.getId());
        if (dbzxSkSporadicItem != null && StrUtil.isNotEmpty(dbzxSkSporadicItem.getId())) {
           // 主表ID
           dbzxSkSporadicItem.setSporadicID(zxSkSporadicItem.getSporadicID());
           // 分部分项ID
           dbzxSkSporadicItem.setCbsID(zxSkSporadicItem.getCbsID());
           // 分部分项
           dbzxSkSporadicItem.setCbsName(zxSkSporadicItem.getCbsName());
           // 物资ID
           dbzxSkSporadicItem.setResID(zxSkSporadicItem.getResID());
           // 物资编码
           dbzxSkSporadicItem.setResCode(zxSkSporadicItem.getResCode());
           // 物资名称
           dbzxSkSporadicItem.setResName(zxSkSporadicItem.getResName());
           // 计量单位
           dbzxSkSporadicItem.setUnit(zxSkSporadicItem.getUnit());
           // 规格型号
           dbzxSkSporadicItem.setSpec(zxSkSporadicItem.getSpec());
           // 采购数量
           dbzxSkSporadicItem.setPurNum(zxSkSporadicItem.getPurNum());
           // 单价
           dbzxSkSporadicItem.setPrice(zxSkSporadicItem.getPrice());
           // 金额
           dbzxSkSporadicItem.setTotalMoney(zxSkSporadicItem.getTotalMoney());
           // 备注
           dbzxSkSporadicItem.setRemark(zxSkSporadicItem.getRemark());
           // 明细
           dbzxSkSporadicItem.setCombProp(zxSkSporadicItem.getCombProp());
           // 共通
           dbzxSkSporadicItem.setModifyUserInfo(userKey, realName);
           flag = zxSkSporadicItemMapper.updateByPrimaryKey(dbzxSkSporadicItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkSporadicItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkSporadicItem(List<ZxSkSporadicItem> zxSkSporadicItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkSporadicItemList != null && zxSkSporadicItemList.size() > 0) {
           ZxSkSporadicItem zxSkSporadicItem = new ZxSkSporadicItem();
           zxSkSporadicItem.setModifyUserInfo(userKey, realName);
           flag = zxSkSporadicItemMapper.batchDeleteUpdateZxSkSporadicItem(zxSkSporadicItemList, zxSkSporadicItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkSporadicItemList);
        }
    }
}
