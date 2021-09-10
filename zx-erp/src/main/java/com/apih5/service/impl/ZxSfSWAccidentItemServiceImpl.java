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
import com.apih5.mybatis.dao.ZxSfSWAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;
import com.apih5.service.ZxSfSWAccidentItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfSWAccidentItemService")
public class ZxSfSWAccidentItemServiceImpl implements ZxSfSWAccidentItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfSWAccidentItemMapper zxSfSWAccidentItemMapper;

    @Override
    public ResponseEntity getZxSfSWAccidentItemListByCondition(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfSWAccidentItem.getPage(), zxSfSWAccidentItem.getLimit());
        // 获取数据
        List<ZxSfSWAccidentItem> zxSfSWAccidentItemList = zxSfSWAccidentItemMapper.selectByZxSfSWAccidentItemList(zxSfSWAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfSWAccidentItem> p = new PageInfo<>(zxSfSWAccidentItemList);

        return repEntity.okList(zxSfSWAccidentItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSWAccidentItemDetail(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 获取数据
        ZxSfSWAccidentItem dbZxSfSWAccidentItem = zxSfSWAccidentItemMapper.selectByPrimaryKey(zxSfSWAccidentItem.getZxSfSWAccidentItemId());
        // 数据存在
        if (dbZxSfSWAccidentItem != null) {
            return repEntity.ok(dbZxSfSWAccidentItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfSWAccidentItem(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfSWAccidentItem.setZxSfSWAccidentItemId(UuidUtil.generate());
        zxSfSWAccidentItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfSWAccidentItemMapper.insert(zxSfSWAccidentItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfSWAccidentItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfSWAccidentItem(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfSWAccidentItem dbZxSfSWAccidentItem = zxSfSWAccidentItemMapper.selectByPrimaryKey(zxSfSWAccidentItem.getZxSfSWAccidentItemId());
        if (dbZxSfSWAccidentItem != null && StrUtil.isNotEmpty(dbZxSfSWAccidentItem.getZxSfSWAccidentItemId())) {
            // 自沉
            dbZxSfSWAccidentItem.setA13(zxSfSWAccidentItem.getA13());
            // 事故件数（合计）
            dbZxSfSWAccidentItem.setA3(zxSfSWAccidentItem.getA3());
            // 总艘数（艘）
            dbZxSfSWAccidentItem.setA17(zxSfSWAccidentItem.getA17());
            // 浪损
            dbZxSfSWAccidentItem.setA10(zxSfSWAccidentItem.getA10());
            // 功率（千瓦）
            dbZxSfSWAccidentItem.setA19(zxSfSWAccidentItem.getA19());
            // 事故直接经济损失（万元）
            dbZxSfSWAccidentItem.setA24(zxSfSWAccidentItem.getA24());
            // 触礁
            dbZxSfSWAccidentItem.setA8(zxSfSWAccidentItem.getA8());
            // 触损
            dbZxSfSWAccidentItem.setA9(zxSfSWAccidentItem.getA9());
            // 碰撞
            dbZxSfSWAccidentItem.setA6(zxSfSWAccidentItem.getA6());
            // 机动船艘数
            dbZxSfSWAccidentItem.setA20(zxSfSWAccidentItem.getA20());
            // 主表ID
            dbZxSfSWAccidentItem.setSwaID(zxSfSWAccidentItem.getSwaID());
            // 火灾爆炸
            dbZxSfSWAccidentItem.setA11(zxSfSWAccidentItem.getA11());
            // 总吨数（吨）
            dbZxSfSWAccidentItem.setA18(zxSfSWAccidentItem.getA18());
            // 搁浅
            dbZxSfSWAccidentItem.setA7(zxSfSWAccidentItem.getA7());
            // 事故件数（重大）
            dbZxSfSWAccidentItem.setA4(zxSfSWAccidentItem.getA4());
            // 风灾
            dbZxSfSWAccidentItem.setA12(zxSfSWAccidentItem.getA12());
            // 船舶种类
            dbZxSfSWAccidentItem.setA1(zxSfSWAccidentItem.getA1());
            // 事故件数（大）
            dbZxSfSWAccidentItem.setA5(zxSfSWAccidentItem.getA5());
            // 受伤
            dbZxSfSWAccidentItem.setA15(zxSfSWAccidentItem.getA15());
            // 备注
            dbZxSfSWAccidentItem.setNotes(zxSfSWAccidentItem.getNotes());
            // 其它
            dbZxSfSWAccidentItem.setA14(zxSfSWAccidentItem.getA14());
            // 统计期内船舶总数（艘）
            dbZxSfSWAccidentItem.setA2(zxSfSWAccidentItem.getA2());
            // 非机动船总吨数
            dbZxSfSWAccidentItem.setA23(zxSfSWAccidentItem.getA23());
            // 机动船总吨数
            dbZxSfSWAccidentItem.setA21(zxSfSWAccidentItem.getA21());
            // 非机动船艘数
            dbZxSfSWAccidentItem.setA22(zxSfSWAccidentItem.getA22());
            // 死亡失踪
            dbZxSfSWAccidentItem.setA16(zxSfSWAccidentItem.getA16());
            // 排序
            dbZxSfSWAccidentItem.setSort(zxSfSWAccidentItem.getSort());
            // 共通
            dbZxSfSWAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfSWAccidentItemMapper.updateByPrimaryKey(dbZxSfSWAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfSWAccidentItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfSWAccidentItem(List<ZxSfSWAccidentItem> zxSfSWAccidentItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfSWAccidentItemList != null && zxSfSWAccidentItemList.size() > 0) {
            ZxSfSWAccidentItem zxSfSWAccidentItem = new ZxSfSWAccidentItem();
            zxSfSWAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfSWAccidentItemMapper.batchDeleteUpdateZxSfSWAccidentItem(zxSfSWAccidentItemList, zxSfSWAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfSWAccidentItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    /**
     * 查询项目报表
     *
     * @param zxSfSWAccidentItem
     * @author suncg
     */
    @Override
    public ResponseEntity getUReportFormList(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 分页查询
        //PageHelper.startPage(zxSfSWAccidentItem.getPage(),zxSfSWAccidentItem.getLimit());
        // 获取数据
        List<ZxSfSWAccidentItem> UReportFormList = zxSfSWAccidentItemMapper.uReportForm(zxSfSWAccidentItem);
        // 得到分页信息
        //PageInfo<ZxSfSWAccidentItem> p = new PageInfo<>(UReportFormList);

        return repEntity.ok(UReportFormList);
    }

    /**
     * 查询项目报表（导出）
     *
     * @param zxSfSWAccidentItem
     * @author suncg
     */

    @Override
    public List<ZxSfSWAccidentItem> UReportForm(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 分页查询
        //PageHelper.startPage(zxSfSWAccidentItem.getPage(),zxSfSWAccidentItem.getLimit());
        // 获取数据
        List<ZxSfSWAccidentItem> UReportFormList = zxSfSWAccidentItemMapper.uReportForm(zxSfSWAccidentItem);
        // 得到分页信息
        //PageInfo<ZxSfSWAccidentItem> p = new PageInfo<>(UReportFormList);

        return UReportFormList;
    }

    /**
     * 查询公司报表
     *
     * @param zxSfSWAccidentItem
     * @author suncg
     */
    @Override
    public ResponseEntity getUReportFormComList(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 分页查询
        //PageHelper.startPage(zxSfSWAccidentItem.getPage(),zxSfSWAccidentItem.getLimit());
        // 获取数据
        List<ZxSfSWAccidentItem> UReportFormList = zxSfSWAccidentItemMapper.uReportFormCom(zxSfSWAccidentItem);
        // 得到分页信息
        //PageInfo<ZxSfSWAccidentItem> p = new PageInfo<>(UReportFormList);

        return repEntity.ok(UReportFormList);
    }

    /**
     * 查询公司报表（导出）
     *
     * @param zxSfSWAccidentItem
     * @author suncg
     */
    @Override
    public List<ZxSfSWAccidentItem> UReportFormCom(ZxSfSWAccidentItem zxSfSWAccidentItem) {
        if (zxSfSWAccidentItem == null) {
            zxSfSWAccidentItem = new ZxSfSWAccidentItem();
        }
        // 分页查询
        //PageHelper.startPage(zxSfSWAccidentItem.getPage(),zxSfSWAccidentItem.getLimit());
        // 获取数据
        List<ZxSfSWAccidentItem> UReportFormList = zxSfSWAccidentItemMapper.uReportFormCom(zxSfSWAccidentItem);
        // 得到分页信息
        //PageInfo<ZxSfSWAccidentItem> p = new PageInfo<>(UReportFormList);

        return UReportFormList;
    }


}
