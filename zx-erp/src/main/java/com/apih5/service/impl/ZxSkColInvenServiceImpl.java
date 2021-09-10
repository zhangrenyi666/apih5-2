package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSkColInvenItemMapper;
import com.apih5.mybatis.pojo.ZxSkColInvenItem;
import com.apih5.mybatis.pojo.ZxSkMakeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkColInvenMapper;
import com.apih5.mybatis.pojo.ZxSkColInven;
import com.apih5.service.ZxSkColInvenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkColInvenService")
public class ZxSkColInvenServiceImpl implements ZxSkColInvenService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkColInvenMapper zxSkColInvenMapper;

    @Autowired(required = true)
    private ZxSkColInvenItemMapper zxSkColInvenItemMapper;

    @Override
    public ResponseEntity getZxSkColInvenListByCondition(ZxSkColInven zxSkColInven) {
        if (zxSkColInven == null) {
            zxSkColInven = new ZxSkColInven();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkColInven.setComID("");
            zxSkColInven.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkColInven.getOrgID2())){
                zxSkColInven.setOrgID(zxSkColInven.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkColInven.setComID(zxSkColInven.getOrgID());
            zxSkColInven.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkColInven.getOrgID2())){
                zxSkColInven.setOrgID(zxSkColInven.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkColInven.setOrgID(zxSkColInven.getOrgID());
            if(StrUtil.isNotEmpty(zxSkColInven.getOrgID2())){
                zxSkColInven.setOrgID(zxSkColInven.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkColInven.getPage(),zxSkColInven.getLimit());
        // 获取数据
        List<ZxSkColInven> zxSkColInvenList = zxSkColInvenMapper.selectByZxSkColInvenList(zxSkColInven);
        //查询明细
        for (ZxSkColInven zxSkColInven1 : zxSkColInvenList) {
            //期次
            if(zxSkColInven1.getMakeInvPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(zxSkColInven1.getMakeInvPeriod().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkColInven1.setMakeInvPeriodDate(date);
            }

            ZxSkColInvenItem zxSkColInvenItem = new ZxSkColInvenItem();
            zxSkColInvenItem.setMainID(zxSkColInven1.getId());
            List<ZxSkColInvenItem> zxSkColInvenItems = zxSkColInvenItemMapper.selectByZxSkColInvenItemList(zxSkColInvenItem);
            for (ZxSkColInvenItem skColInvenItem : zxSkColInvenItems) {
                skColInvenItem.setId(zxSkColInvenItem.getResID()+","+zxSkColInvenItem.getPrice());
            }
            zxSkColInven1.setZxSkColInvenItemList(zxSkColInvenItems);
        }
        // 得到分页信息
        PageInfo<ZxSkColInven> p = new PageInfo<>(zxSkColInvenList);

        return repEntity.okList(zxSkColInvenList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkColInvenDetail(ZxSkColInven zxSkColInven) {
        if (zxSkColInven == null) {
            zxSkColInven = new ZxSkColInven();
        }
        // 获取数据
        ZxSkColInven dbZxSkColInven = zxSkColInvenMapper.selectByPrimaryKey(zxSkColInven.getId());
        // 数据存在
        if (dbZxSkColInven != null) {
            ZxSkColInvenItem zxSkColInvenItem = new ZxSkColInvenItem();
            zxSkColInvenItem.setMainID(dbZxSkColInven.getId());
            List<ZxSkColInvenItem> zxSkColInvenItems = zxSkColInvenItemMapper.selectByZxSkColInvenItemList(zxSkColInvenItem);
            dbZxSkColInven.setZxSkColInvenItemList(zxSkColInvenItems);
            return repEntity.ok(dbZxSkColInven);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkColInven(ZxSkColInven zxSkColInven) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkColInven.setId(UuidUtil.generate());
        zxSkColInven.setCreateUserInfo(userKey, realName);
        if(zxSkColInven.getMakeInvPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkColInven.getMakeInvPeriodDate());
            zxSkColInven.setMakeInvPeriod(result);
        }
        //默认审核状态: 0:未审核
        zxSkColInven.setStatus("0");
        //创建明细
        List<ZxSkColInvenItem> zxSkColInvenItemList = zxSkColInven.getZxSkColInvenItemList();
        if(zxSkColInvenItemList != null && zxSkColInvenItemList.size()>0) {
            for (ZxSkColInvenItem zxSkColInvenItem : zxSkColInvenItemList) {
                zxSkColInvenItem.setMainID(zxSkColInven.getId());
                zxSkColInvenItem.setId((UuidUtil.generate()));
                zxSkColInvenItem.setCreateUserInfo(userKey, realName);
                zxSkColInvenItemMapper.insert(zxSkColInvenItem);
            }
        }
        int flag = zxSkColInvenMapper.insert(zxSkColInven);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkColInven);
        }
    }

    @Override
    public ResponseEntity updateZxSkColInven(ZxSkColInven zxSkColInven) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;

        ZxSkColInven dbZxSkColInven = zxSkColInvenMapper.selectByPrimaryKey(zxSkColInven.getId());
        if (dbZxSkColInven != null && StrUtil.isNotEmpty(dbZxSkColInven.getId())) {
           // 项目名称ID
           dbZxSkColInven.setOrgID(zxSkColInven.getOrgID());
           // 项目名称
           dbZxSkColInven.setOrgName(zxSkColInven.getOrgName());
           // 协作队伍名称ID
           dbZxSkColInven.setCustomerOrgID(zxSkColInven.getCustomerOrgID());
           // 协作队伍名称
           dbZxSkColInven.setCustomerOrgName(zxSkColInven.getCustomerOrgName());
           // 登记日期
           dbZxSkColInven.setMakeInvDate(zxSkColInven.getMakeInvDate());
           // 经办人
           dbZxSkColInven.setIntransactor(zxSkColInven.getIntransactor());
           // 协作队伍负责人
           dbZxSkColInven.setReporter(zxSkColInven.getReporter());
           // 状态
           dbZxSkColInven.setStatus(zxSkColInven.getStatus());
           // 审核员
           dbZxSkColInven.setAuditor(zxSkColInven.getAuditor());
           // 所属公司ID
           dbZxSkColInven.setComID(zxSkColInven.getComID());
           // 所属公司名称
           dbZxSkColInven.setComName(zxSkColInven.getComName());
           // 盘点期次
            String result = new SimpleDateFormat("yyyyMM").format(zxSkColInven.getMakeInvPeriodDate());
           dbZxSkColInven.setMakeInvPeriod(result);
           // 备注
           dbZxSkColInven.setRemarks(zxSkColInven.getRemarks());
           // 排序
           dbZxSkColInven.setSort(zxSkColInven.getSort());
           // 共通
           dbZxSkColInven.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxSkColInvenItem zxSkColInvenItem = new ZxSkColInvenItem();
            zxSkColInvenItem.setMainID(zxSkColInven.getId());
            List<ZxSkColInvenItem> zxSkColInvenItems = zxSkColInvenItemMapper.selectByZxSkColInvenItemList(zxSkColInvenItem);
            if(zxSkColInvenItems != null && zxSkColInvenItems.size()>0) {
                zxSkColInvenItem.setModifyUserInfo(userKey, realName);
                zxSkColInvenItemMapper.batchDeleteUpdateZxSkColInvenItem(zxSkColInvenItems, zxSkColInvenItem);
            }
            //明细list
            List<ZxSkColInvenItem> zxSkColInvenItemList = zxSkColInven.getZxSkColInvenItemList();
            if(zxSkColInvenItemList != null && zxSkColInvenItemList.size()>0) {
                for(ZxSkColInvenItem zxSkColInvenItem1 : zxSkColInvenItemList) {
                    zxSkColInvenItem1.setId(UuidUtil.generate());
                    zxSkColInvenItem1.setMainID(zxSkColInven.getId());
                    zxSkColInvenItem1.setCreateUserInfo(userKey, realName);
                    zxSkColInvenItemMapper.insert(zxSkColInvenItem1);
                }
            }

           flag = zxSkColInvenMapper.updateByPrimaryKey(dbZxSkColInven);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkColInven);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkColInven(List<ZxSkColInven> zxSkColInvenList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkColInvenList != null && zxSkColInvenList.size() > 0) {
            for (ZxSkColInven zxSkColInven : zxSkColInvenList) {
                // 删除明细
                ZxSkColInvenItem zxSkColInvenItem = new ZxSkColInvenItem();
                zxSkColInvenItem.setMainID(zxSkColInven.getId());
                List<ZxSkColInvenItem> zxSkColInvenItems = zxSkColInvenItemMapper.selectByZxSkColInvenItemList(zxSkColInvenItem);
                if(zxSkColInvenItems != null && zxSkColInvenItems.size()>0) {
                    zxSkColInvenItem.setModifyUserInfo(userKey, realName);
                    zxSkColInvenItemMapper.batchDeleteUpdateZxSkColInvenItem(zxSkColInvenItems, zxSkColInvenItem);
                }
            }
           ZxSkColInven zxSkColInven = new ZxSkColInven();
           zxSkColInven.setModifyUserInfo(userKey, realName);
           flag = zxSkColInvenMapper.batchDeleteUpdateZxSkColInven(zxSkColInvenList, zxSkColInven);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkColInvenList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    //获取领过料的物资
    //领料单位orgID  内部单位customerOrgID(筛选条件)
    @Override
    public ResponseEntity getZxSkColInvenResourceList(ZxSkColInven zxSkColInven) {
        if (StrUtil.isEmpty(zxSkColInven.getCustomerOrgID())
                ||StrUtil.isEmpty(zxSkColInven.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkColInven.getPage(),zxSkColInven.getLimit());
        // 获取数据
        List<ZxSkColInvenItem> zxSkColInvenItems = zxSkColInvenMapper.getZxSkColInvenResourceList(zxSkColInven);
        for (ZxSkColInvenItem zxSkColInvenItem : zxSkColInvenItems) {
            zxSkColInvenItem.setId(zxSkColInvenItem.getResID()+","+zxSkColInvenItem.getPrice());
        }
        // 得到分页信息
        PageInfo<ZxSkColInvenItem> p = new PageInfo<>(zxSkColInvenItems);
        return repEntity.okList(zxSkColInvenItems, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkColInvenInOrgList(ZxSkColInven zxSkColInven) {
        if (StrUtil.isEmpty(zxSkColInven.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
            //return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkColInven.getPage(),zxSkColInven.getLimit());
        // 获取数据
        List<ZxSkColInven> zxSkColInvenList = zxSkColInvenMapper.getZxSkColInvenInOrgList(zxSkColInven);
        // 得到分页信息
        PageInfo<ZxSkColInven> p = new PageInfo<>(zxSkColInvenList);
        return repEntity.okList(zxSkColInvenList, p.getTotal());
    }



}
