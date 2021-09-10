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
import com.apih5.mybatis.dao.ZxSkShopGoodsItemMapper;
import com.apih5.mybatis.pojo.ZxSkShopGoodsItem;
import com.apih5.service.ZxSkShopGoodsItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkShopGoodsItemService")
public class ZxSkShopGoodsItemServiceImpl implements ZxSkShopGoodsItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkShopGoodsItemMapper zxSkShopGoodsItemMapper;

    @Override
    public ResponseEntity getZxSkShopGoodsItemListByCondition(ZxSkShopGoodsItem zxSkShopGoodsItem) {
        if (zxSkShopGoodsItem == null) {
            zxSkShopGoodsItem = new ZxSkShopGoodsItem();
        }
        // 分页查询
        PageHelper.startPage(zxSkShopGoodsItem.getPage(),zxSkShopGoodsItem.getLimit());
        // 获取数据
        List<ZxSkShopGoodsItem> zxSkShopGoodsItemList = zxSkShopGoodsItemMapper.selectByZxSkShopGoodsItemList(zxSkShopGoodsItem);
        // 得到分页信息
        PageInfo<ZxSkShopGoodsItem> p = new PageInfo<>(zxSkShopGoodsItemList);

        return repEntity.okList(zxSkShopGoodsItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkShopGoodsItemDetail(ZxSkShopGoodsItem zxSkShopGoodsItem) {
        if (zxSkShopGoodsItem == null) {
            zxSkShopGoodsItem = new ZxSkShopGoodsItem();
        }
        // 获取数据
        ZxSkShopGoodsItem dbZxSkShopGoodsItem = zxSkShopGoodsItemMapper.selectByPrimaryKey(zxSkShopGoodsItem.getId());
        // 数据存在
        if (dbZxSkShopGoodsItem != null) {
            return repEntity.ok(dbZxSkShopGoodsItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkShopGoodsItem(ZxSkShopGoodsItem zxSkShopGoodsItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkShopGoodsItem.setId(UuidUtil.generate());
        zxSkShopGoodsItem.setCreateUserInfo(userKey, realName);
        int flag = zxSkShopGoodsItemMapper.insert(zxSkShopGoodsItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkShopGoodsItem);
        }
    }

    @Override
    public ResponseEntity updateZxSkShopGoodsItem(ZxSkShopGoodsItem zxSkShopGoodsItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkShopGoodsItem dbZxSkShopGoodsItem = zxSkShopGoodsItemMapper.selectByPrimaryKey(zxSkShopGoodsItem.getId());
        if (dbZxSkShopGoodsItem != null && StrUtil.isNotEmpty(dbZxSkShopGoodsItem.getId())) {
           // mainID
           dbZxSkShopGoodsItem.setMainID(zxSkShopGoodsItem.getMainID());
           // 物资ID
           dbZxSkShopGoodsItem.setResID(zxSkShopGoodsItem.getResID());
           // 物资编号
           dbZxSkShopGoodsItem.setResCode(zxSkShopGoodsItem.getResCode());
           // 物资名称
           dbZxSkShopGoodsItem.setResName(zxSkShopGoodsItem.getResName());
           // 规格型号
           dbZxSkShopGoodsItem.setSpec(zxSkShopGoodsItem.getSpec());
           // 计量单位
           dbZxSkShopGoodsItem.setUnit(zxSkShopGoodsItem.getUnit());
           // 进货日期
           dbZxSkShopGoodsItem.setInDate(zxSkShopGoodsItem.getInDate());
           // 进货数量
           dbZxSkShopGoodsItem.setInQty(zxSkShopGoodsItem.getInQty());
           // 存放地点
           dbZxSkShopGoodsItem.setPlace(zxSkShopGoodsItem.getPlace());
           // 供应商名称ID
           dbZxSkShopGoodsItem.setCustomerID(zxSkShopGoodsItem.getCustomerID());
           // 供应商名称
           dbZxSkShopGoodsItem.setCustomerName(zxSkShopGoodsItem.getCustomerName());
           // 收料员
           dbZxSkShopGoodsItem.setReceClerk(zxSkShopGoodsItem.getReceClerk());
           // 产地或品牌
           dbZxSkShopGoodsItem.setProduction(zxSkShopGoodsItem.getProduction());
           // 存放地点ID
           dbZxSkShopGoodsItem.setWhOrgID(zxSkShopGoodsItem.getWhOrgID());
           // 备注
           dbZxSkShopGoodsItem.setRemarks(zxSkShopGoodsItem.getRemarks());
           // 排序
           dbZxSkShopGoodsItem.setSort(zxSkShopGoodsItem.getSort());
           // 共通
           dbZxSkShopGoodsItem.setModifyUserInfo(userKey, realName);
           flag = zxSkShopGoodsItemMapper.updateByPrimaryKey(dbZxSkShopGoodsItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkShopGoodsItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkShopGoodsItem(List<ZxSkShopGoodsItem> zxSkShopGoodsItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkShopGoodsItemList != null && zxSkShopGoodsItemList.size() > 0) {
           ZxSkShopGoodsItem zxSkShopGoodsItem = new ZxSkShopGoodsItem();
           zxSkShopGoodsItem.setModifyUserInfo(userKey, realName);
           flag = zxSkShopGoodsItemMapper.batchDeleteUpdateZxSkShopGoodsItem(zxSkShopGoodsItemList, zxSkShopGoodsItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkShopGoodsItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
