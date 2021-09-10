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
import com.apih5.mybatis.dao.ZxSfAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxSfAccidentItem;
import com.apih5.service.ZxSfAccidentItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfAccidentItemService")
public class ZxSfAccidentItemServiceImpl implements ZxSfAccidentItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfAccidentItemMapper zxSfAccidentItemMapper;

    @Override
    public ResponseEntity getZxSfAccidentItemListByCondition(ZxSfAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccidentItem.getPage(), zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.selectByZxSfAccidentItemList(zxSfAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return repEntity.okList(zxSfAccidentItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccidentItemDetail(ZxSfAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfAccidentItem();
        }
        // 获取数据
        ZxSfAccidentItem dbZxSfAccidentItem = zxSfAccidentItemMapper.selectByPrimaryKey(zxSfAccidentItem.getZxSfAccidentItemId());
        // 数据存在
        if (dbZxSfAccidentItem != null) {
            return repEntity.ok(dbZxSfAccidentItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfAccidentItem(ZxSfAccidentItem zxSfAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfAccidentItem.setZxSfAccidentItemId(UuidUtil.generate());
        zxSfAccidentItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfAccidentItemMapper.insert(zxSfAccidentItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfAccidentItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfAccidentItem(ZxSfAccidentItem zxSfAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccidentItem dbZxSfAccidentItem = zxSfAccidentItemMapper.selectByPrimaryKey(zxSfAccidentItem.getZxSfAccidentItemId());
        if (dbZxSfAccidentItem != null && StrUtil.isNotEmpty(dbZxSfAccidentItem.getZxSfAccidentItemId())) {
            // 事故类别
            dbZxSfAccidentItem.setA6(zxSfAccidentItem.getA6());
            // 轻伤
            dbZxSfAccidentItem.setA10(zxSfAccidentItem.getA10());
            // 事故单位ID
            dbZxSfAccidentItem.setProjId(zxSfAccidentItem.getProjId());
            // 事故原因
            dbZxSfAccidentItem.setA5(zxSfAccidentItem.getA5());
            // 非企业人员死亡
            dbZxSfAccidentItem.setA11(zxSfAccidentItem.getA11());
            // 事故结案批复单位
            dbZxSfAccidentItem.setA16(zxSfAccidentItem.getA16());
            // 主表ID
            dbZxSfAccidentItem.setAccidentId(zxSfAccidentItem.getAccidentId());
            // 操作工作日（工日）
            dbZxSfAccidentItem.setA15(zxSfAccidentItem.getA15());
            // 重伤
            dbZxSfAccidentItem.setA9(zxSfAccidentItem.getA9());
            // 工程分类及等级、建设类型
            dbZxSfAccidentItem.setA4(zxSfAccidentItem.getA4());
            // 非企业人员轻伤
            dbZxSfAccidentItem.setA13(zxSfAccidentItem.getA13());
            // 伤亡人员姓名
            dbZxSfAccidentItem.setA7(zxSfAccidentItem.getA7());
            // 非企业人员重伤
            dbZxSfAccidentItem.setA12(zxSfAccidentItem.getA12());
            // 事故单位名称
            dbZxSfAccidentItem.setA3(zxSfAccidentItem.getA3());
            // 直接经济损失（万元）
            dbZxSfAccidentItem.setA14(zxSfAccidentItem.getA14());
            // 备注
            dbZxSfAccidentItem.setNotes(zxSfAccidentItem.getNotes());
            // 死亡
            dbZxSfAccidentItem.setA8(zxSfAccidentItem.getA8());
            // 发生事故时间
            dbZxSfAccidentItem.setA1(zxSfAccidentItem.getA1());
            // 事故地点
            dbZxSfAccidentItem.setA2(zxSfAccidentItem.getA2());
            // 排序
            dbZxSfAccidentItem.setSort(zxSfAccidentItem.getSort());
            // 共通
            dbZxSfAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfAccidentItemMapper.updateByPrimaryKey(dbZxSfAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfAccidentItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfAccidentItem(List<ZxSfAccidentItem> zxSfAccidentItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfAccidentItemList != null && zxSfAccidentItemList.size() > 0) {
            ZxSfAccidentItem zxSfAccidentItem = new ZxSfAccidentItem();
            zxSfAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfAccidentItemMapper.batchDeleteUpdateZxSfAccidentItem(zxSfAccidentItemList, zxSfAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfAccidentItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity getUReportFormList(ZxSfAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccidentItem.getPage(), zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.uReportForm(zxSfAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return repEntity.okList(zxSfAccidentItemList, p.getTotal());
    }

    @Override
    public List<ZxSfAccidentItem> uReportForm(ZxSfAccidentItem zxSfAccidentItem) {
        // 分页查询
        //PageHelper.startPage(zxSfAccidentItem.getPage(),zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.uReportForm(zxSfAccidentItem);
        // 得到分页信息
        // PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return zxSfAccidentItemList;
    }

    @Override
    public ResponseEntity getUReportFormComList(ZxSfAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccidentItem.getPage(), zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.uReportFormCom(zxSfAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return repEntity.okList(zxSfAccidentItemList, p.getTotal());
    }

    @Override
    public List<ZxSfAccidentItem> uReportFormCom(ZxSfAccidentItem zxSfAccidentItem) {
        // 分页查询
        //PageHelper.startPage(zxSfAccidentItem.getPage(),zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.uReportFormCom(zxSfAccidentItem);
        // 得到分页信息
        // PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return zxSfAccidentItemList;
    }


}
