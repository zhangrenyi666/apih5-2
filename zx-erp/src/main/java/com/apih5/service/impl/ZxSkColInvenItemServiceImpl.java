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
import com.apih5.mybatis.dao.ZxSkColInvenItemMapper;
import com.apih5.mybatis.pojo.ZxSkColInvenItem;
import com.apih5.service.ZxSkColInvenItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkColInvenItemService")
public class ZxSkColInvenItemServiceImpl implements ZxSkColInvenItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkColInvenItemMapper zxSkColInvenItemMapper;

    @Override
    public ResponseEntity getZxSkColInvenItemListByCondition(ZxSkColInvenItem zxSkColInvenItem) {
        if (zxSkColInvenItem == null) {
            zxSkColInvenItem = new ZxSkColInvenItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkColInvenItem.getPage(),zxSkColInvenItem.getLimit());
        // 获取数据
        List<ZxSkColInvenItem> zxSkColInvenItemList = zxSkColInvenItemMapper.selectByZxSkColInvenItemList(zxSkColInvenItem);
        // 得到分页信息
        PageInfo<ZxSkColInvenItem> p = new PageInfo<>(zxSkColInvenItemList);

        return repEntity.okList(zxSkColInvenItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkColInvenItemDetail(ZxSkColInvenItem zxSkColInvenItem) {
        if (zxSkColInvenItem == null) {
            zxSkColInvenItem = new ZxSkColInvenItem();
        }
        // 获取数据
        ZxSkColInvenItem dbZxSkColInvenItem = zxSkColInvenItemMapper.selectByPrimaryKey(zxSkColInvenItem.getId());
        // 数据存在
        if (dbZxSkColInvenItem != null) {
            return repEntity.ok(dbZxSkColInvenItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkColInvenItem(ZxSkColInvenItem zxSkColInvenItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkColInvenItem.setId(UuidUtil.generate());
        zxSkColInvenItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkColInvenItemMapper.insert(zxSkColInvenItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkColInvenItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkColInvenItem(ZxSkColInvenItem zxSkColInvenItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkColInvenItem dbZxSkColInvenItem = zxSkColInvenItemMapper.selectByPrimaryKey(zxSkColInvenItem.getId());
        if (dbZxSkColInvenItem != null && StrUtil.isNotEmpty(dbZxSkColInvenItem.getId())) {
           // mainID
           dbZxSkColInvenItem.setMainID(zxSkColInvenItem.getMainID());
           // 物资ID
           dbZxSkColInvenItem.setResID(zxSkColInvenItem.getResID());
           // 物资编码
           dbZxSkColInvenItem.setResCode(zxSkColInvenItem.getResCode());
           // 物资名称
           dbZxSkColInvenItem.setResName(zxSkColInvenItem.getResName());
           // 规格型号
           dbZxSkColInvenItem.setSpec(zxSkColInvenItem.getSpec());
           // 计量单位
           dbZxSkColInvenItem.setUnit(zxSkColInvenItem.getUnit());
           // 盘点数量
           dbZxSkColInvenItem.setQty(zxSkColInvenItem.getQty());
           // 单价
           dbZxSkColInvenItem.setPrice(zxSkColInvenItem.getPrice());
           // 金额
           dbZxSkColInvenItem.setAmt(zxSkColInvenItem.getAmt());
           // 物资类型
           dbZxSkColInvenItem.setResType(zxSkColInvenItem.getResType());
           // 存放地点ID
           dbZxSkColInvenItem.setWhOrgID(zxSkColInvenItem.getWhOrgID());
           // 存放地点
           dbZxSkColInvenItem.setWhOrgName(zxSkColInvenItem.getWhOrgName());
           // 备注
           dbZxSkColInvenItem.setRemarks(zxSkColInvenItem.getRemarks());
           // 排序
           dbZxSkColInvenItem.setSort(zxSkColInvenItem.getSort());
           // 共通
           dbZxSkColInvenItem.setModifyUserInfo(userKey, realName);
           flag = zxSkColInvenItemMapper.updateByPrimaryKey(dbZxSkColInvenItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkColInvenItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkColInvenItem(List<ZxSkColInvenItem> zxSkColInvenItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkColInvenItemList != null && zxSkColInvenItemList.size() > 0) {
           ZxSkColInvenItem zxSkColInvenItem = new ZxSkColInvenItem();
           zxSkColInvenItem.setModifyUserInfo(userKey, realName);
           flag = zxSkColInvenItemMapper.batchDeleteUpdateZxSkColInvenItem(zxSkColInvenItemList, zxSkColInvenItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkColInvenItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
