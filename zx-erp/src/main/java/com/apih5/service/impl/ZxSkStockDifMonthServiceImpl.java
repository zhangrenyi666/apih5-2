package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.mybatis.dao.ZxSkStockDifMonthItemMapper;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.mybatis.pojo.ZxSkWornOutItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockDifMonthMapper;
import com.apih5.mybatis.pojo.ZxSkStockDifMonth;
import com.apih5.service.ZxSkStockDifMonthService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockDifMonthService")
public class ZxSkStockDifMonthServiceImpl implements ZxSkStockDifMonthService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockDifMonthMapper zxSkStockDifMonthMapper;

    @Autowired(required = true)
    private ZxSkStockDifMonthItemMapper zxSkStockDifMonthItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Override
    public ResponseEntity getZxSkStockDifMonthListByCondition(ZxSkStockDifMonth zxSkStockDifMonth) {
        if (zxSkStockDifMonth == null) {
            zxSkStockDifMonth = new ZxSkStockDifMonth();
        }
        if(zxSkStockDifMonth.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkStockDifMonth.getPeriodDate());
            zxSkStockDifMonth.setPeriod(result);
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkStockDifMonth.setComID("");
            zxSkStockDifMonth.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkStockDifMonth.setComID(zxSkStockDifMonth.getProjectID());
            zxSkStockDifMonth.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkStockDifMonth.setProjectID(zxSkStockDifMonth.getProjectID());
        }
        // 分页查询
        PageHelper.startPage(zxSkStockDifMonth.getPage(),zxSkStockDifMonth.getLimit());
        // 获取数据
        List<ZxSkStockDifMonth> zxSkStockDifMonthList = zxSkStockDifMonthMapper.selectByZxSkStockDifMonthList(zxSkStockDifMonth);
        //查询明细
        for (ZxSkStockDifMonth zxSkStockDifMonth1 : zxSkStockDifMonthList) {
            //期次
            if(zxSkStockDifMonth1.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(zxSkStockDifMonth1.getPeriod().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkStockDifMonth1.setPeriodDate(date);
            }
            ZxSkStockDifMonthItem zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
            zxSkStockDifMonthItem.setMainID(zxSkStockDifMonth1.getZxSkStockDifMonthId());
            List<ZxSkStockDifMonthItem> zxSkStockDifMonthItems = zxSkStockDifMonthItemMapper.selectByZxSkStockDifMonthItemList(zxSkStockDifMonthItem);
            zxSkStockDifMonth1.setZxSkStockDifMonthItemList(zxSkStockDifMonthItems);
        }
        // 得到分页信息
        PageInfo<ZxSkStockDifMonth> p = new PageInfo<>(zxSkStockDifMonthList);

        return repEntity.okList(zxSkStockDifMonthList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockDifMonthDetail(ZxSkStockDifMonth zxSkStockDifMonth) {
        if (zxSkStockDifMonth == null) {
            zxSkStockDifMonth = new ZxSkStockDifMonth();
        }
        // 获取数据
        ZxSkStockDifMonth dbZxSkStockDifMonth = zxSkStockDifMonthMapper.selectByPrimaryKey(zxSkStockDifMonth.getZxSkStockDifMonthId());
        // 数据存在
        if (dbZxSkStockDifMonth != null) {
            ZxSkStockDifMonthItem zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
            zxSkStockDifMonthItem.setMainID(dbZxSkStockDifMonth.getZxSkStockDifMonthId());
            List<ZxSkStockDifMonthItem> zxSkStockDifMonthItems = zxSkStockDifMonthItemMapper.selectByZxSkStockDifMonthItemList(zxSkStockDifMonthItem);
            dbZxSkStockDifMonth.setZxSkStockDifMonthItemList(zxSkStockDifMonthItems);
            return repEntity.ok(dbZxSkStockDifMonth);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        if(zxSkStockDifMonth.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkStockDifMonth.getPeriodDate());
            zxSkStockDifMonth.setPeriod(result);
        }
        zxSkStockDifMonth.setZxSkStockDifMonthId(UuidUtil.generate());
        zxSkStockDifMonth.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkStockDifMonth.setStatus("0");
        //创建明细
        List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList = zxSkStockDifMonth.getZxSkStockDifMonthItemList();
        if (zxSkStockDifMonthItemList != null && zxSkStockDifMonthItemList.size() > 0) {
            for (ZxSkStockDifMonthItem zxSkStockDifMonthItem : zxSkStockDifMonthItemList) {
                zxSkStockDifMonthItem.setMainID(zxSkStockDifMonth.getZxSkStockDifMonthId());
                zxSkStockDifMonthItem.setZxSkStockDifMonthItemId((UuidUtil.generate()));
                zxSkStockDifMonthItem.setCreateUserInfo(userKey, realName);
                zxSkStockDifMonthItemMapper.insert(zxSkStockDifMonthItem);
            }
        }
        int flag = zxSkStockDifMonthMapper.insert(zxSkStockDifMonth);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockDifMonth);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockDifMonth dbZxSkStockDifMonth = zxSkStockDifMonthMapper.selectByPrimaryKey(zxSkStockDifMonth.getZxSkStockDifMonthId());
        if (dbZxSkStockDifMonth != null && StrUtil.isNotEmpty(dbZxSkStockDifMonth.getZxSkStockDifMonthId())) {
           // 公司名称ID
           dbZxSkStockDifMonth.setComID(zxSkStockDifMonth.getComID());
           // 公司名称
           dbZxSkStockDifMonth.setComName(zxSkStockDifMonth.getComName());
           // 项目名称ID
           dbZxSkStockDifMonth.setProjectID(zxSkStockDifMonth.getProjectID());
           // 项目名称
           dbZxSkStockDifMonth.setProjectName(zxSkStockDifMonth.getProjectName());
           // 期次
            String result = new SimpleDateFormat("yyyyMM").format(zxSkStockDifMonth.getPeriodDate());
           dbZxSkStockDifMonth.setPeriod(result);
           // 审核状态
           dbZxSkStockDifMonth.setStatus(zxSkStockDifMonth.getStatus());
           // 填报人
           dbZxSkStockDifMonth.setReporter(zxSkStockDifMonth.getReporter());
           // 填报时间
           dbZxSkStockDifMonth.setReportDate(zxSkStockDifMonth.getReportDate());
           // 备注
           dbZxSkStockDifMonth.setRemarks(zxSkStockDifMonth.getRemarks());
           // 排序
           dbZxSkStockDifMonth.setSort(zxSkStockDifMonth.getSort());
           // 共通
           dbZxSkStockDifMonth.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkStockDifMonthItem zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
            zxSkStockDifMonthItem.setMainID(zxSkStockDifMonth.getZxSkStockDifMonthId());
            List<ZxSkStockDifMonthItem> zxSkStockDifMonthItems = zxSkStockDifMonthItemMapper.selectByZxSkStockDifMonthItemList(zxSkStockDifMonthItem);
            if (zxSkStockDifMonthItems != null && zxSkStockDifMonthItems.size() > 0) {
                zxSkStockDifMonthItem.setModifyUserInfo(userKey, realName);
                zxSkStockDifMonthItemMapper.batchDeleteUpdateZxSkStockDifMonthItem(zxSkStockDifMonthItems, zxSkStockDifMonthItem);
            }
            //明细list
            List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList = zxSkStockDifMonth.getZxSkStockDifMonthItemList();
            if (zxSkStockDifMonthItemList != null && zxSkStockDifMonthItemList.size() > 0) {
                for (ZxSkStockDifMonthItem zxSkStockDifMonthItem1 : zxSkStockDifMonthItemList) {
                    zxSkStockDifMonthItem1.setZxSkStockDifMonthItemId(UuidUtil.generate());
                    zxSkStockDifMonthItem1.setMainID(zxSkStockDifMonth.getZxSkStockDifMonthId());
                    zxSkStockDifMonthItem1.setCreateUserInfo(userKey, realName);
                    zxSkStockDifMonthItemMapper.insert(zxSkStockDifMonthItem1);
                }
            }
           flag = zxSkStockDifMonthMapper.updateByPrimaryKey(dbZxSkStockDifMonth);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockDifMonth);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockDifMonth(List<ZxSkStockDifMonth> zxSkStockDifMonthList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockDifMonthList != null && zxSkStockDifMonthList.size() > 0) {
            for (ZxSkStockDifMonth zxSkStockDifMonth : zxSkStockDifMonthList) {
                // 删除明细
                ZxSkStockDifMonthItem zxSkStockDifMonthItem = new ZxSkStockDifMonthItem();
                zxSkStockDifMonthItem.setMainID(zxSkStockDifMonth.getZxSkStockDifMonthId());
                List<ZxSkStockDifMonthItem> zxSkStockDifMonthItems = zxSkStockDifMonthItemMapper.selectByZxSkStockDifMonthItemList(zxSkStockDifMonthItem);
                if (zxSkStockDifMonthItems != null && zxSkStockDifMonthItems.size() > 0) {
                    zxSkStockDifMonthItem.setModifyUserInfo(userKey, realName);
                    zxSkStockDifMonthItemMapper.batchDeleteUpdateZxSkStockDifMonthItem(zxSkStockDifMonthItems, zxSkStockDifMonthItem);
                }
            }
           ZxSkStockDifMonth zxSkStockDifMonth = new ZxSkStockDifMonth();
           zxSkStockDifMonth.setModifyUserInfo(userKey, realName);
           flag = zxSkStockDifMonthMapper.batchDeleteUpdateZxSkStockDifMonth(zxSkStockDifMonthList, zxSkStockDifMonth);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockDifMonthList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @Override
    public ResponseEntity checkZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(StrUtil.equals(zxSkStockDifMonth.getStatus(), "1")) {
            return repEntity.layerMessage("no", "已经审核的，请重新选择！");
        }
        ZxSkStockDifMonth zxSkStockDifMonth1 = new ZxSkStockDifMonth();
        zxSkStockDifMonth1.setStatus("1");
        zxSkStockDifMonth1.setZxSkStockDifMonthId(zxSkStockDifMonth.getZxSkStockDifMonthId());
        zxSkStockDifMonth1.setModifyUserInfo(userKey, realName);
        flag = zxSkStockDifMonthMapper.checkZxSkStockDifMonth(zxSkStockDifMonth1);
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }else {
            return repEntity.ok("sys.data.update",zxSkStockDifMonth1);
        }
    }

    @Override
    public ResponseEntity counterCheckZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(StrUtil.equals(zxSkStockDifMonth.getStatus(), "0")) {
            return repEntity.layerMessage("no", "未审核的，请重新选择！");
        }
        ZxSkStockDifMonth zxSkStockDifMonth1 = new ZxSkStockDifMonth();
        zxSkStockDifMonth1.setStatus("0");
        zxSkStockDifMonth1.setModifyUserInfo(userKey, realName);
        zxSkStockDifMonth1.setZxSkStockDifMonthId(zxSkStockDifMonth.getZxSkStockDifMonthId());
        flag = zxSkStockDifMonthMapper.checkZxSkStockDifMonth(zxSkStockDifMonth1);
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }else {
            return repEntity.ok("sys.data.update",zxSkStockDifMonth1);
        }
    }
}
