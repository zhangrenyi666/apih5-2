package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSkMmReqPlanItemMapper;
import com.apih5.mybatis.pojo.ZxSkMmReqPlanItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkMmReqPlanMapper;
import com.apih5.mybatis.pojo.ZxSkMmReqPlan;
import com.apih5.service.ZxSkMmReqPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMmReqPlanService")
public class ZxSkMmReqPlanServiceImpl implements ZxSkMmReqPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMmReqPlanMapper zxSkMmReqPlanMapper;

    @Autowired(required = true)
    private ZxSkMmReqPlanItemMapper zxSkMmReqPlanItemMapper;

    @Override
    public ResponseEntity getZxSkMmReqPlanListByCondition(ZxSkMmReqPlan zxSkMmReqPlan) {
        if (zxSkMmReqPlan == null) {
            zxSkMmReqPlan = new ZxSkMmReqPlan();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkMmReqPlan.setCompanyId("");
            zxSkMmReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkMmReqPlan.getOrgID2())){
                zxSkMmReqPlan.setProjectID(zxSkMmReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkMmReqPlan.setCompanyId(zxSkMmReqPlan.getProjectID());
            zxSkMmReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkMmReqPlan.getOrgID2())){
                zxSkMmReqPlan.setProjectID(zxSkMmReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkMmReqPlan.setProjectID(zxSkMmReqPlan.getProjectID());
            if(StrUtil.isNotEmpty(zxSkMmReqPlan.getOrgID2())){
                zxSkMmReqPlan.setProjectID(zxSkMmReqPlan.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkMmReqPlan.getPage(),zxSkMmReqPlan.getLimit());
        // 获取数据
        List<ZxSkMmReqPlan> zxSkMmReqPlanList = zxSkMmReqPlanMapper.selectByZxSkMmReqPlanList(zxSkMmReqPlan);
        //查询明细
        for (ZxSkMmReqPlan zxSkMmReqPlan1 : zxSkMmReqPlanList) {
            if(zxSkMmReqPlan1.getPeriod()!=null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(zxSkMmReqPlan1.getPeriod().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                zxSkMmReqPlan1.setPeriodDate(date);
            }
            ZxSkMmReqPlanItem zxSkTtReqPlanItem = new ZxSkMmReqPlanItem();
            zxSkTtReqPlanItem.setMmReqPlanID(zxSkMmReqPlan1.getId());
            List<ZxSkMmReqPlanItem> zxSkMmReqPlanItems = zxSkMmReqPlanItemMapper.selectByZxSkMmReqPlanItemList(zxSkTtReqPlanItem);
            zxSkMmReqPlan1.setZxSkMmReqPlanItemList(zxSkMmReqPlanItems);
        }

        // 得到分页信息
        PageInfo<ZxSkMmReqPlan> p = new PageInfo<>(zxSkMmReqPlanList);

        return repEntity.okList(zxSkMmReqPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMmReqPlanDetails(ZxSkMmReqPlan zxSkMmReqPlan) {
        if (zxSkMmReqPlan == null) {
            zxSkMmReqPlan = new ZxSkMmReqPlan();
        }
        // 获取数据
        ZxSkMmReqPlan dbZxSkMmReqPlan = zxSkMmReqPlanMapper.selectByPrimaryKey(zxSkMmReqPlan.getId());
        // 数据存在
        if (dbZxSkMmReqPlan != null) {
            ZxSkMmReqPlanItem zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
            zxSkMmReqPlanItem.setMmReqPlanID(dbZxSkMmReqPlan.getId());
            List<ZxSkMmReqPlanItem> zxSkMmReqPlanItems = zxSkMmReqPlanItemMapper.selectByZxSkMmReqPlanItemList(zxSkMmReqPlanItem);
            dbZxSkMmReqPlan.setZxSkMmReqPlanItemList(zxSkMmReqPlanItems);
            return repEntity.ok(dbZxSkMmReqPlan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkMmReqPlan(ZxSkMmReqPlan zxSkMmReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zxSkMmReqPlan.getPeriodDate()!=null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkMmReqPlan.getPeriodDate());
            zxSkMmReqPlan.setPeriod(result);
        }
        zxSkMmReqPlan.setId(UuidUtil.generate());
        zxSkMmReqPlan.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkMmReqPlan.setStatus("0");
        int flag = zxSkMmReqPlanMapper.insert(zxSkMmReqPlan);
        //创建明细
        List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList = zxSkMmReqPlan.getZxSkMmReqPlanItemList();
        if(zxSkMmReqPlanItemList!=null&&zxSkMmReqPlanItemList.size()>0){
            for (ZxSkMmReqPlanItem zxSkMmReqPlanItem : zxSkMmReqPlanItemList) {
                zxSkMmReqPlanItem.setMmReqPlanID(zxSkMmReqPlan.getId());
                zxSkMmReqPlanItem.setId((UuidUtil.generate()));
                zxSkMmReqPlanItem.setCreateUserInfo(userKey, realName);
                zxSkMmReqPlanItemMapper.insert(zxSkMmReqPlanItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkMmReqPlan);
        }
    }

    @Override
    public ResponseEntity updateZxSkMmReqPlan(ZxSkMmReqPlan zxSkMmReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMmReqPlan dbzxSkMmReqPlan = zxSkMmReqPlanMapper.selectByPrimaryKey(zxSkMmReqPlan.getId());
        if (dbzxSkMmReqPlan != null && StrUtil.isNotEmpty(dbzxSkMmReqPlan.getId())) {
           // 期次
            String result = new SimpleDateFormat("yyyyMM").format(zxSkMmReqPlan.getPeriodDate());
           dbzxSkMmReqPlan.setPeriod(result);
           // 计划编号
           dbzxSkMmReqPlan.setPlanCode(zxSkMmReqPlan.getPlanCode());
           // 编制人
           dbzxSkMmReqPlan.setCreator(zxSkMmReqPlan.getCreator());
           // 项目ID
           dbzxSkMmReqPlan.setProjectID(zxSkMmReqPlan.getProjectID());
           // 项目名称
           dbzxSkMmReqPlan.setProjectName(zxSkMmReqPlan.getProjectName());
           // 日期
           dbzxSkMmReqPlan.setCreateDate(zxSkMmReqPlan.getCreateDate());
           // 金额
           dbzxSkMmReqPlan.setTotalMoney(zxSkMmReqPlan.getTotalMoney());
           // 状态
           dbzxSkMmReqPlan.setStatus(zxSkMmReqPlan.getStatus());
           // 备注
           dbzxSkMmReqPlan.setRemark(zxSkMmReqPlan.getRemark());
           // 明细
           dbzxSkMmReqPlan.setCombProp(zxSkMmReqPlan.getCombProp());
           // 公司id
           dbzxSkMmReqPlan.setCompanyId(zxSkMmReqPlan.getCompanyId());
           // 公司名称
           dbzxSkMmReqPlan.setCompanyName(zxSkMmReqPlan.getCompanyName());
           // 共通
           dbzxSkMmReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxSkMmReqPlanMapper.updateByPrimaryKey(dbzxSkMmReqPlan);

            //删除在新增
            ZxSkMmReqPlanItem zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
            zxSkMmReqPlanItem.setMmReqPlanID(dbzxSkMmReqPlan.getId());
            List<ZxSkMmReqPlanItem> zxSkMmReqPlanItems = zxSkMmReqPlanItemMapper.selectByZxSkMmReqPlanItemList(zxSkMmReqPlanItem);
            if(zxSkMmReqPlanItems != null && zxSkMmReqPlanItems.size()>0) {
                zxSkMmReqPlanItem.setModifyUserInfo(userKey, realName);
                zxSkMmReqPlanItemMapper.batchDeleteUpdateZxSkMmReqPlanItem(zxSkMmReqPlanItems, zxSkMmReqPlanItem);
            }
            //明细list
            List<ZxSkMmReqPlanItem> zxSkMmReqPlanItemList = zxSkMmReqPlan.getZxSkMmReqPlanItemList();
            if(zxSkMmReqPlanItemList != null && zxSkMmReqPlanItemList.size()>0) {
                for(ZxSkMmReqPlanItem zxSkMmReqPlanItem1 : zxSkMmReqPlanItemList) {
                    zxSkMmReqPlanItem1.setId(UuidUtil.generate());
                    zxSkMmReqPlanItem1.setMmReqPlanID(dbzxSkMmReqPlan.getId());
                    zxSkMmReqPlanItem1.setCreateUserInfo(userKey, realName);
                    zxSkMmReqPlanItemMapper.insert(zxSkMmReqPlanItem1);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkMmReqPlan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMmReqPlan(List<ZxSkMmReqPlan> zxSkMmReqPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMmReqPlanList != null && zxSkMmReqPlanList.size() > 0) {
            for (ZxSkMmReqPlan zxSkMmReqPlan : zxSkMmReqPlanList) {
                // 删除明细
                ZxSkMmReqPlanItem zxSkMmReqPlanItem = new ZxSkMmReqPlanItem();
                zxSkMmReqPlanItem.setMmReqPlanID(zxSkMmReqPlan.getId());
                List<ZxSkMmReqPlanItem> zxSkMmReqPlanItems = zxSkMmReqPlanItemMapper.selectByZxSkMmReqPlanItemList(zxSkMmReqPlanItem);
                if(zxSkMmReqPlanItems != null && zxSkMmReqPlanItems.size()>0) {
                    zxSkMmReqPlanItem.setModifyUserInfo(userKey, realName);
                    zxSkMmReqPlanItemMapper.batchDeleteUpdateZxSkMmReqPlanItem(zxSkMmReqPlanItems, zxSkMmReqPlanItem);
                }
            }
           ZxSkMmReqPlan zxSkMmReqPlan = new ZxSkMmReqPlan();
           zxSkMmReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxSkMmReqPlanMapper.batchDeleteUpdateZxSkMmReqPlan(zxSkMmReqPlanList, zxSkMmReqPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkMmReqPlanList);
        }
    }


}
