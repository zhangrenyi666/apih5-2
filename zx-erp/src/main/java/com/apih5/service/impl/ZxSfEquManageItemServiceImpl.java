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
import com.apih5.mybatis.dao.ZxSfEquManageItemMapper;
import com.apih5.mybatis.pojo.ZxSfEquManageItem;
import com.apih5.service.ZxSfEquManageItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfEquManageItemService")
public class ZxSfEquManageItemServiceImpl implements ZxSfEquManageItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEquManageItemMapper zxSfEquManageItemMapper;

    @Override
    public ResponseEntity getZxSfEquManageItemListByCondition(ZxSfEquManageItem zxSfEquManageItem) {
        if (zxSfEquManageItem == null) {
            zxSfEquManageItem = new ZxSfEquManageItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfEquManageItem.getPage(), zxSfEquManageItem.getLimit());
        // 获取数据
        List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManageItemMapper.selectByZxSfEquManageItemList(zxSfEquManageItem);
        // 得到分页信息
        PageInfo<ZxSfEquManageItem> p = new PageInfo<>(zxSfEquManageItemList);

        return repEntity.okList(zxSfEquManageItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEquManageItemDetail(ZxSfEquManageItem zxSfEquManageItem) {
        if (zxSfEquManageItem == null) {
            zxSfEquManageItem = new ZxSfEquManageItem();
        }
        // 获取数据
        ZxSfEquManageItem dbZxSfEquManageItem = zxSfEquManageItemMapper.selectByPrimaryKey(zxSfEquManageItem.getZxSfEquManageItemId());
        // 数据存在
        if (dbZxSfEquManageItem != null) {
            return repEntity.ok(dbZxSfEquManageItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfEquManageItem(ZxSfEquManageItem zxSfEquManageItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEquManageItem.setZxSfEquManageItemId(UuidUtil.generate());
        zxSfEquManageItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfEquManageItemMapper.insert(zxSfEquManageItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEquManageItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfEquManageItem(ZxSfEquManageItem zxSfEquManageItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfEquManageItem dbZxSfEquManageItem = zxSfEquManageItemMapper.selectByPrimaryKey(zxSfEquManageItem.getZxSfEquManageItemId());
        if (dbZxSfEquManageItem != null && StrUtil.isNotEmpty(dbZxSfEquManageItem.getZxSfEquManageItemId())) {
            // 主表ID
            dbZxSfEquManageItem.setEmID(zxSfEquManageItem.getEmID());
            // 设备ID
            dbZxSfEquManageItem.setResID(zxSfEquManageItem.getResID());
            // 机械管理编号
            dbZxSfEquManageItem.setResCode(zxSfEquManageItem.getResCode());
            // 设备名称
            dbZxSfEquManageItem.setResName(zxSfEquManageItem.getResName());
            // 型号
            dbZxSfEquManageItem.setResSpec(zxSfEquManageItem.getResSpec());
            // 合格证书编号
            dbZxSfEquManageItem.setCardNo(zxSfEquManageItem.getCardNo());
            // 发证单位
            dbZxSfEquManageItem.setSendCardUnit(zxSfEquManageItem.getSendCardUnit());
            // 姓名
            dbZxSfEquManageItem.setEmpName(zxSfEquManageItem.getEmpName());
            // 证书编号
            dbZxSfEquManageItem.setEmpCardNo(zxSfEquManageItem.getEmpCardNo());
            // 有效期
            dbZxSfEquManageItem.setEmpValidDate(zxSfEquManageItem.getEmpValidDate());
            // 设备管理人员
            dbZxSfEquManageItem.setEquipManager(zxSfEquManageItem.getEquipManager());
            // 安全管理人员
            dbZxSfEquManageItem.setSafeManager(zxSfEquManageItem.getSafeManager());
            // 编辑时间
            dbZxSfEquManageItem.setEditTime(zxSfEquManageItem.getEditTime());
            // 设备来源
            dbZxSfEquManageItem.setSource(zxSfEquManageItem.getSource());
            // 有效期：起
            dbZxSfEquManageItem.setEffStart(zxSfEquManageItem.getEffStart());
            // 有效期：止
            dbZxSfEquManageItem.setEffEnd(zxSfEquManageItem.getEffEnd());
            // 共计天数
            dbZxSfEquManageItem.setTotalDay(zxSfEquManageItem.getTotalDay());
            // 退场时间
            dbZxSfEquManageItem.setOutDate(zxSfEquManageItem.getOutDate());
            // 备注
            dbZxSfEquManageItem.setRemarks(zxSfEquManageItem.getRemarks());
            // 排序
            dbZxSfEquManageItem.setSort(zxSfEquManageItem.getSort());
            // 共通
            dbZxSfEquManageItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEquManageItemMapper.updateByPrimaryKey(dbZxSfEquManageItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfEquManageItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEquManageItem(List<ZxSfEquManageItem> zxSfEquManageItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEquManageItemList != null && zxSfEquManageItemList.size() > 0) {
            ZxSfEquManageItem zxSfEquManageItem = new ZxSfEquManageItem();
            zxSfEquManageItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEquManageItemMapper.batchDeleteUpdateZxSfEquManageItem(zxSfEquManageItemList, zxSfEquManageItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfEquManageItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getUreportFormList(ZxSfEquManageItem zxSfEquManageItem) {
        if (zxSfEquManageItem == null) {
            zxSfEquManageItem = new ZxSfEquManageItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfEquManageItem.getPage(), zxSfEquManageItem.getLimit());
        // 获取数据
        List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManageItemMapper.ureportForm(zxSfEquManageItem);
        // 得到分页信息
        PageInfo<ZxSfEquManageItem> p = new PageInfo<>(zxSfEquManageItemList);

        return repEntity.okList(zxSfEquManageItemList, p.getTotal());
    }

    public List<ZxSfEquManageItem> UreportForm(ZxSfEquManageItem zxSfEquManageItem) {

        // 获取数据
        List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManageItemMapper.ureportForm(zxSfEquManageItem);
        // 得到分页信息
        //PageInfo<ZxSfEquManageItem> p = new PageInfo<>(zxSfEquManageItemList);

        return zxSfEquManageItemList;
    }


}
