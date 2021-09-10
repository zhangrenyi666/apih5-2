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
import com.apih5.mybatis.dao.ZxSfCheckItemMapper;
import com.apih5.mybatis.pojo.ZxSfCheckItem;
import com.apih5.service.ZxSfCheckItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfCheckItemService")
public class ZxSfCheckItemServiceImpl implements ZxSfCheckItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfCheckItemMapper zxSfCheckItemMapper;

    @Override
    public ResponseEntity getZxSfCheckItemListByCondition(ZxSfCheckItem zxSfCheckItem) {
        if (zxSfCheckItem == null) {
            zxSfCheckItem = new ZxSfCheckItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfCheckItem.getPage(),zxSfCheckItem.getLimit());
        // 获取数据
        List<ZxSfCheckItem> zxSfCheckItemList = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItem);
        // 得到分页信息
        PageInfo<ZxSfCheckItem> p = new PageInfo<>(zxSfCheckItemList);

        return repEntity.okList(zxSfCheckItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfCheckItemDetail(ZxSfCheckItem zxSfCheckItem) {
        if (zxSfCheckItem == null) {
            zxSfCheckItem = new ZxSfCheckItem();
        }
        // 获取数据
        ZxSfCheckItem dbZxSfCheckItem = zxSfCheckItemMapper.selectByPrimaryKey(zxSfCheckItem.getZxSfCheckItemId());
        // 数据存在
        if (dbZxSfCheckItem != null) {
            return repEntity.ok(dbZxSfCheckItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfCheckItem(ZxSfCheckItem zxSfCheckItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfCheckItem.setZxSfCheckItemId(UuidUtil.generate());
        zxSfCheckItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfCheckItemMapper.insert(zxSfCheckItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfCheckItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfCheckItem(ZxSfCheckItem zxSfCheckItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheckItem dbZxSfCheckItem = zxSfCheckItemMapper.selectByPrimaryKey(zxSfCheckItem.getZxSfCheckItemId());
        if (dbZxSfCheckItem != null && StrUtil.isNotEmpty(dbZxSfCheckItem.getZxSfCheckItemId())) {
           // 主表ID
           dbZxSfCheckItem.setCheckID(zxSfCheckItem.getCheckID());
           // 存在隐患
           dbZxSfCheckItem.setRiskInfo(zxSfCheckItem.getRiskInfo());
           // 拟整改措施
           dbZxSfCheckItem.setChangeInfo(zxSfCheckItem.getChangeInfo());
           // 复查日期
           dbZxSfCheckItem.setCheckDate(zxSfCheckItem.getCheckDate());
           // 复查人员
           dbZxSfCheckItem.setChecker(zxSfCheckItem.getChecker());
           // 复查结论
           dbZxSfCheckItem.setCheckResult(zxSfCheckItem.getCheckResult());
           // 编辑时间
           dbZxSfCheckItem.setEditTime(zxSfCheckItem.getEditTime());
           // 整改期限
           dbZxSfCheckItem.setLastCheckDate(zxSfCheckItem.getLastCheckDate());
           // 备注
           dbZxSfCheckItem.setRemarks(zxSfCheckItem.getRemarks());
           // 排序
           dbZxSfCheckItem.setSort(zxSfCheckItem.getSort());
           // 共通
           dbZxSfCheckItem.setModifyUserInfo(userKey, realName);
           flag = zxSfCheckItemMapper.updateByPrimaryKey(dbZxSfCheckItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfCheckItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfCheckItem(List<ZxSfCheckItem> zxSfCheckItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfCheckItemList != null && zxSfCheckItemList.size() > 0) {
           ZxSfCheckItem zxSfCheckItem = new ZxSfCheckItem();
           zxSfCheckItem.setModifyUserInfo(userKey, realName);
           flag = zxSfCheckItemMapper.batchDeleteUpdateZxSfCheckItem(zxSfCheckItemList, zxSfCheckItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfCheckItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
