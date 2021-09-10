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
import com.apih5.mybatis.dao.ZxSkHttreqPlanItemMapper;
import com.apih5.mybatis.pojo.ZxSkHttreqPlanItem;
import com.apih5.service.ZxSkHttreqPlanItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkHttreqPlanItemService")
public class ZxSkHttreqPlanItemServiceImpl implements ZxSkHttreqPlanItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkHttreqPlanItemMapper zxSkHttreqPlanItemMapper;

    @Override
    public ResponseEntity getZxSkHttreqPlanItemListByCondition(ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        if (zxSkHttreqPlanItem == null) {
            zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkHttreqPlanItem.getPage(),zxSkHttreqPlanItem.getLimit());
        // 获取数据
        List<ZxSkHttreqPlanItem> zxSkHttreqPlanItemList = zxSkHttreqPlanItemMapper.selectByZxSkHttreqPlanItemList(zxSkHttreqPlanItem);
        // 得到分页信息
        PageInfo<ZxSkHttreqPlanItem> p = new PageInfo<>(zxSkHttreqPlanItemList);

        return repEntity.okList(zxSkHttreqPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkHttreqPlanItemDetail(ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        if (zxSkHttreqPlanItem == null) {
            zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
        }
        // 获取数据
        ZxSkHttreqPlanItem dbZxSkHttreqPlanItem = zxSkHttreqPlanItemMapper.selectByPrimaryKey(zxSkHttreqPlanItem.getZxSkHttreqPlanItemId());
        // 数据存在
        if (dbZxSkHttreqPlanItem != null) {
            return repEntity.ok(dbZxSkHttreqPlanItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkHttreqPlanItem(ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkHttreqPlanItem.setZxSkHttreqPlanItemId(UuidUtil.generate());
        zxSkHttreqPlanItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkHttreqPlanItemMapper.insert(zxSkHttreqPlanItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkHttreqPlanItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkHttreqPlanItem(ZxSkHttreqPlanItem zxSkHttreqPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkHttreqPlanItem dbZxSkHttreqPlanItem = zxSkHttreqPlanItemMapper.selectByPrimaryKey(zxSkHttreqPlanItem.getZxSkHttreqPlanItemId());
        if (dbZxSkHttreqPlanItem != null && StrUtil.isNotEmpty(dbZxSkHttreqPlanItem.getZxSkHttreqPlanItemId())) {
           // 主表ID
           dbZxSkHttreqPlanItem.setTtReqPlanID(zxSkHttreqPlanItem.getTtReqPlanID());
           // 总需用计划明细ID
           dbZxSkHttreqPlanItem.setTtReqPlanItemID(zxSkHttreqPlanItem.getTtReqPlanItemID());
           // 操作人
           dbZxSkHttreqPlanItem.setOper(zxSkHttreqPlanItem.getOper());
           // 操作时间
           dbZxSkHttreqPlanItem.setOpTime(zxSkHttreqPlanItem.getOpTime());
           // 原始量
           dbZxSkHttreqPlanItem.setSourceNum(zxSkHttreqPlanItem.getSourceNum());
           // 变更量
           dbZxSkHttreqPlanItem.setChangeNum(zxSkHttreqPlanItem.getChangeNum());
           // 操作
           dbZxSkHttreqPlanItem.setOp(zxSkHttreqPlanItem.getOp());
           // null
           dbZxSkHttreqPlanItem.setCombProp(zxSkHttreqPlanItem.getCombProp());
           // null
           dbZxSkHttreqPlanItem.setPp1(zxSkHttreqPlanItem.getPp1());
           // null
           dbZxSkHttreqPlanItem.setPp2(zxSkHttreqPlanItem.getPp2());
           // null
           dbZxSkHttreqPlanItem.setPp3(zxSkHttreqPlanItem.getPp3());
           // null
           dbZxSkHttreqPlanItem.setPp4(zxSkHttreqPlanItem.getPp4());
           // null
           dbZxSkHttreqPlanItem.setPp5(zxSkHttreqPlanItem.getPp5());
           // null
           dbZxSkHttreqPlanItem.setPp6(zxSkHttreqPlanItem.getPp6());
           // null
           dbZxSkHttreqPlanItem.setPp7(zxSkHttreqPlanItem.getPp7());
           // null
           dbZxSkHttreqPlanItem.setPp8(zxSkHttreqPlanItem.getPp8());
           // null
           dbZxSkHttreqPlanItem.setPp9(zxSkHttreqPlanItem.getPp9());
           // null
           dbZxSkHttreqPlanItem.setPp10(zxSkHttreqPlanItem.getPp10());
           // null
           dbZxSkHttreqPlanItem.setEditTime(zxSkHttreqPlanItem.getEditTime());
           // 备注
           dbZxSkHttreqPlanItem.setRemarks(zxSkHttreqPlanItem.getRemarks());
           // 排序
           dbZxSkHttreqPlanItem.setSort(zxSkHttreqPlanItem.getSort());
           // 共通
           dbZxSkHttreqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkHttreqPlanItemMapper.updateByPrimaryKey(dbZxSkHttreqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkHttreqPlanItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkHttreqPlanItem(List<ZxSkHttreqPlanItem> zxSkHttreqPlanItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkHttreqPlanItemList != null && zxSkHttreqPlanItemList.size() > 0) {
           ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
           zxSkHttreqPlanItem.setModifyUserInfo(userKey, realName);
           flag = zxSkHttreqPlanItemMapper.batchDeleteUpdateZxSkHttreqPlanItem(zxSkHttreqPlanItemList, zxSkHttreqPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkHttreqPlanItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
