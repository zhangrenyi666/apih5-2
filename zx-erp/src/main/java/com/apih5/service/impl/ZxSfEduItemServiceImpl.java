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
import com.apih5.mybatis.dao.ZxSfEduItemMapper;
import com.apih5.mybatis.pojo.ZxSfEduItem;
import com.apih5.service.ZxSfEduItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfEduItemService")
public class ZxSfEduItemServiceImpl implements ZxSfEduItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEduItemMapper zxSfEduItemMapper;

    @Override
    public ResponseEntity getZxSfEduItemListByCondition(ZxSfEduItem zxSfEduItem) {
        if (zxSfEduItem == null) {
            zxSfEduItem = new ZxSfEduItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfEduItem.getPage(), zxSfEduItem.getLimit());
        // 获取数据
        List<ZxSfEduItem> zxSfEduItemList = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItem);
        // 得到分页信息
        PageInfo<ZxSfEduItem> p = new PageInfo<>(zxSfEduItemList);

        return repEntity.okList(zxSfEduItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEduItemDetail(ZxSfEduItem zxSfEduItem) {
        if (zxSfEduItem == null) {
            zxSfEduItem = new ZxSfEduItem();
        }
        // 获取数据
        ZxSfEduItem dbZxSfEduItem = zxSfEduItemMapper.selectByPrimaryKey(zxSfEduItem.getZxSfEduItemId());
        // 数据存在
        if (dbZxSfEduItem != null) {
            return repEntity.ok(dbZxSfEduItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfEduItem(ZxSfEduItem zxSfEduItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEduItem.setZxSfEduItemId(UuidUtil.generate());
        zxSfEduItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfEduItemMapper.insert(zxSfEduItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEduItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfEduItem(ZxSfEduItem zxSfEduItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfEduItem dbZxSfEduItem = zxSfEduItemMapper.selectByPrimaryKey(zxSfEduItem.getZxSfEduItemId());
        if (dbZxSfEduItem != null && StrUtil.isNotEmpty(dbZxSfEduItem.getZxSfEduItemId())) {
            // 主表ID
            dbZxSfEduItem.setEduID(zxSfEduItem.getEduID());
            // 姓名
            dbZxSfEduItem.setName(zxSfEduItem.getName());
            // 工种
            dbZxSfEduItem.setEmpType(zxSfEduItem.getEmpType());
            // 身份证号
            dbZxSfEduItem.setIdCard(zxSfEduItem.getIdCard());
            // 教育类型
            dbZxSfEduItem.setTranContext(zxSfEduItem.getTranContext());
            // 编辑时间
            dbZxSfEduItem.setEditTime(zxSfEduItem.getEditTime());
            // 备注
            dbZxSfEduItem.setRemarks(zxSfEduItem.getRemarks());
            // 排序
            dbZxSfEduItem.setSort(zxSfEduItem.getSort());
            // 共通
            dbZxSfEduItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEduItemMapper.updateByPrimaryKey(dbZxSfEduItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfEduItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEduItem(List<ZxSfEduItem> zxSfEduItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEduItemList != null && zxSfEduItemList.size() > 0) {
            ZxSfEduItem zxSfEduItem = new ZxSfEduItem();
            zxSfEduItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEduItemMapper.batchDeleteUpdateZxSfEduItem(zxSfEduItemList, zxSfEduItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfEduItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getUReportFormList(ZxSfEduItem zxSfEduItem) {
        if (zxSfEduItem == null) {
            zxSfEduItem = new ZxSfEduItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfEduItem.getPage(), zxSfEduItem.getLimit());
        // 获取数据
        List<ZxSfEduItem> zxSfEduItemList = zxSfEduItemMapper.uReportForm(zxSfEduItem);
        // 得到分页信息
        PageInfo<ZxSfEduItem> p = new PageInfo<>(zxSfEduItemList);

        return repEntity.okList(zxSfEduItemList, p.getTotal());
    }


    @Override
    public List<ZxSfEduItem> uReportForm(ZxSfEduItem zxSfEduItem) {
//        if (zxSfEduItem == null) {
//            zxSfEduItem = new ZxSfEduItem();
//        }
        // 分页查询
        // PageHelper.startPage(zxSfEduItem.getPage(),zxSfEduItem.getLimit());
        // 获取数据
        List<ZxSfEduItem> zxSfEduItemList = zxSfEduItemMapper.uReportForm(zxSfEduItem);
        // 得到分页信息
        // PageInfo<ZxSfEduItem> p = new PageInfo<>(zxSfEduItemList);

        return zxSfEduItemList;
    }


}
