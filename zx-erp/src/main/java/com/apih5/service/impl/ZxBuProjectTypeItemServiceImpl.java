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
import com.apih5.mybatis.dao.ZxBuProjectTypeItemMapper;
import com.apih5.mybatis.pojo.ZxBuProjectTypeItem;
import com.apih5.service.ZxBuProjectTypeItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuProjectTypeItemService")
public class ZxBuProjectTypeItemServiceImpl implements ZxBuProjectTypeItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuProjectTypeItemMapper zxBuProjectTypeItemMapper;

    @Override
    public ResponseEntity getZxBuProjectTypeItemListByCondition(ZxBuProjectTypeItem zxBuProjectTypeItem) {
        if (zxBuProjectTypeItem == null) {
            zxBuProjectTypeItem = new ZxBuProjectTypeItem();
        }
        // 分页查询
        PageHelper.startPage(zxBuProjectTypeItem.getPage(),zxBuProjectTypeItem.getLimit());
        // 获取数据
        List<ZxBuProjectTypeItem> zxBuProjectTypeItemList = zxBuProjectTypeItemMapper.selectByZxBuProjectTypeItemList(zxBuProjectTypeItem);
        // 得到分页信息
        PageInfo<ZxBuProjectTypeItem> p = new PageInfo<>(zxBuProjectTypeItemList);

        return repEntity.okList(zxBuProjectTypeItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuProjectTypeItemDetail(ZxBuProjectTypeItem zxBuProjectTypeItem) {
        if (zxBuProjectTypeItem == null) {
            zxBuProjectTypeItem = new ZxBuProjectTypeItem();
        }
        // 获取数据
        ZxBuProjectTypeItem dbZxBuProjectTypeItem = zxBuProjectTypeItemMapper.selectByPrimaryKey(zxBuProjectTypeItem.getZxBuProjectTypeItemId());
        // 数据存在
        if (dbZxBuProjectTypeItem != null) {
            return repEntity.ok(dbZxBuProjectTypeItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuProjectTypeItem(ZxBuProjectTypeItem zxBuProjectTypeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuProjectTypeItem.setZxBuProjectTypeItemId(UuidUtil.generate());
        zxBuProjectTypeItem.setCreateUserInfo(userKey, realName);
        int flag = zxBuProjectTypeItemMapper.insert(zxBuProjectTypeItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuProjectTypeItem);
        }
    }

    @Override
    public ResponseEntity updateZxBuProjectTypeItem(ZxBuProjectTypeItem zxBuProjectTypeItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuProjectTypeItem dbZxBuProjectTypeItem = zxBuProjectTypeItemMapper.selectByPrimaryKey(zxBuProjectTypeItem.getZxBuProjectTypeItemId());
        if (dbZxBuProjectTypeItem != null && StrUtil.isNotEmpty(dbZxBuProjectTypeItem.getZxBuProjectTypeItemId())) {
           // 项目工程类别ID
           dbZxBuProjectTypeItem.setMainID(zxBuProjectTypeItem.getMainID());
           // 类别ID类别ID
           dbZxBuProjectTypeItem.setCheckLevel2ID(zxBuProjectTypeItem.getCheckLevel2ID());
           // 类别
           dbZxBuProjectTypeItem.setCheckLevel2Name(zxBuProjectTypeItem.getCheckLevel2Name());
           // 子类别ID
           dbZxBuProjectTypeItem.setCheckLevel3ID(zxBuProjectTypeItem.getCheckLevel3ID());
           // 子类别
           dbZxBuProjectTypeItem.setCheckLevel3Name(zxBuProjectTypeItem.getCheckLevel3Name());
           // 系数
           dbZxBuProjectTypeItem.setRate(zxBuProjectTypeItem.getRate());
           // 关系一
           dbZxBuProjectTypeItem.setOperate1(zxBuProjectTypeItem.getOperate1());
           // 参数一
           dbZxBuProjectTypeItem.setRate1(zxBuProjectTypeItem.getRate1());
           // 关系二
           dbZxBuProjectTypeItem.setOperate2(zxBuProjectTypeItem.getOperate2());
           // 参数二
           dbZxBuProjectTypeItem.setRate2(zxBuProjectTypeItem.getRate2());
           // 参考系数
           dbZxBuProjectTypeItem.setDispRate(zxBuProjectTypeItem.getDispRate());
           // 最后修改时间
           dbZxBuProjectTypeItem.setEditTime(zxBuProjectTypeItem.getEditTime());
           // combProp
           dbZxBuProjectTypeItem.setCombProp(zxBuProjectTypeItem.getCombProp());
           // pp1
           dbZxBuProjectTypeItem.setPp1(zxBuProjectTypeItem.getPp1());
           // pp2
           dbZxBuProjectTypeItem.setPp2(zxBuProjectTypeItem.getPp2());
           // pp3
           dbZxBuProjectTypeItem.setPp3(zxBuProjectTypeItem.getPp3());
           // pp4
           dbZxBuProjectTypeItem.setPp4(zxBuProjectTypeItem.getPp4());
           // pp5
           dbZxBuProjectTypeItem.setPp5(zxBuProjectTypeItem.getPp5());
           // pp6
           dbZxBuProjectTypeItem.setPp6(zxBuProjectTypeItem.getPp6());
           // pp7
           dbZxBuProjectTypeItem.setPp7(zxBuProjectTypeItem.getPp7());
           // pp8
           dbZxBuProjectTypeItem.setPp8(zxBuProjectTypeItem.getPp8());
           // pp9
           dbZxBuProjectTypeItem.setPp9(zxBuProjectTypeItem.getPp9());
           // pp10
           dbZxBuProjectTypeItem.setPp10(zxBuProjectTypeItem.getPp10());
           // 备注
           dbZxBuProjectTypeItem.setRemarks(zxBuProjectTypeItem.getRemarks());
           // 排序
           dbZxBuProjectTypeItem.setSort(zxBuProjectTypeItem.getSort());
           // 共通
           dbZxBuProjectTypeItem.setModifyUserInfo(userKey, realName);
           flag = zxBuProjectTypeItemMapper.updateByPrimaryKey(dbZxBuProjectTypeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuProjectTypeItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuProjectTypeItem(List<ZxBuProjectTypeItem> zxBuProjectTypeItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuProjectTypeItemList != null && zxBuProjectTypeItemList.size() > 0) {
           ZxBuProjectTypeItem zxBuProjectTypeItem = new ZxBuProjectTypeItem();
           zxBuProjectTypeItem.setModifyUserInfo(userKey, realName);
           flag = zxBuProjectTypeItemMapper.batchDeleteUpdateZxBuProjectTypeItem(zxBuProjectTypeItemList, zxBuProjectTypeItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuProjectTypeItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
