package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSkTtReqPlanItemMapper;
import com.apih5.mybatis.pojo.ZxSkHttreqPlanItem;
import com.apih5.mybatis.pojo.ZxSkTtReqPlanItem;
import com.apih5.service.ZxSkHttreqPlanItemService;
import com.apih5.service.ZxSkTtReqPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTtReqPlanMapper;
import com.apih5.mybatis.pojo.ZxSkTtReqPlan;
import com.apih5.service.ZxSkTtReqPlanService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTtReqPlanService")
public class ZxSkTtReqPlanServiceImpl implements ZxSkTtReqPlanService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTtReqPlanMapper zxSkTtReqPlanMapper;

    @Autowired(required = true)
    private ZxSkTtReqPlanItemMapper zxSkTtReqPlanItemMapper;

    @Autowired(required = true)
    private ZxSkHttreqPlanItemService zxSkHttreqPlanItemService;

    @Autowired(required = true)
    private ZxSkTtReqPlanItemService zxSkTtReqPlanItemService;

    @Override
    public ResponseEntity getZxSkTtReqPlanListByCondition(ZxSkTtReqPlan zxSkTtReqPlan) {
        if (zxSkTtReqPlan == null) {
            zxSkTtReqPlan = new ZxSkTtReqPlan();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTtReqPlan.setCompanyId("");
            zxSkTtReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSkTtReqPlan.setCompanyId(zxSkTtReqPlan.getProjectID());
            zxSkTtReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getProjectID());
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        }
        // ????????????
        PageHelper.startPage(zxSkTtReqPlan.getPage(),zxSkTtReqPlan.getLimit());
        // ????????????
        List<ZxSkTtReqPlan> zxSkTtReqPlanList = zxSkTtReqPlanMapper.selectByZxSkTtReqPlanList(zxSkTtReqPlan);
        //????????????
        for (ZxSkTtReqPlan zxSkTtReqPlan1 : zxSkTtReqPlanList) {
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan1.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            for (ZxSkTtReqPlanItem skTtReqPlanItem : zxSkTtReqPlanItemList) {
                skTtReqPlanItem.setAddData("true");
            }
            zxSkTtReqPlan1.setZxSkTtReqPlanItemList(zxSkTtReqPlanItemList);
        }
        // ??????????????????
        PageInfo<ZxSkTtReqPlan> p = new PageInfo<>(zxSkTtReqPlanList);

        return repEntity.okList(zxSkTtReqPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTtReqPlanDetails(ZxSkTtReqPlan zxSkTtReqPlan) {
        if (zxSkTtReqPlan == null) {
            zxSkTtReqPlan = new ZxSkTtReqPlan();
        }
        // ????????????
        ZxSkTtReqPlan dbZxSkTtReqPlan = zxSkTtReqPlanMapper.selectByPrimaryKey(zxSkTtReqPlan.getId());
        // ????????????
        if (dbZxSkTtReqPlan != null) {
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(dbZxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItems = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            dbZxSkTtReqPlan.setZxSkTtReqPlanItemList(zxSkTtReqPlanItems);
            return repEntity.ok(dbZxSkTtReqPlan);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSkTtReqPlan(ZxSkTtReqPlan zxSkTtReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTtReqPlan.setId(UuidUtil.generate());
        zxSkTtReqPlan.setCreateUserInfo(userKey, realName);
        //??????????????????: 0:?????????
        zxSkTtReqPlan.setStatus("0");
        int flag = zxSkTtReqPlanMapper.insert(zxSkTtReqPlan);
        //????????????
        List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlan.getZxSkTtReqPlanItemList();
        if(zxSkTtReqPlanItemList != null && zxSkTtReqPlanItemList.size()>0) {
                    for (ZxSkTtReqPlanItem zxSkTtReqPlanItem : zxSkTtReqPlanItemList) {
                        zxSkTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                        zxSkTtReqPlanItem.setId((UuidUtil.generate()));
                zxSkTtReqPlanItem.setCreateUserInfo(userKey, realName);
                zxSkTtReqPlanItemMapper.insert(zxSkTtReqPlanItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkTtReqPlan);
        }
    }

    @Override
    public ResponseEntity updateZxSkTtReqPlan(ZxSkTtReqPlan zxSkTtReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTtReqPlan dbzxSkTtReqPlan = zxSkTtReqPlanMapper.selectByPrimaryKey(zxSkTtReqPlan.getId());
        if (dbzxSkTtReqPlan != null && StrUtil.isNotEmpty(dbzxSkTtReqPlan.getId())) {
           // ??????id
           dbzxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getProjectID());
           // ????????????
           dbzxSkTtReqPlan.setProjectName(zxSkTtReqPlan.getProjectName());
           // ??????
           dbzxSkTtReqPlan.setCreateDate(zxSkTtReqPlan.getCreateDate());
           // ??????
           dbzxSkTtReqPlan.setTotalMoney(zxSkTtReqPlan.getTotalMoney());
           // ??????
           dbzxSkTtReqPlan.setStatus(zxSkTtReqPlan.getStatus());
           // ??????
           dbzxSkTtReqPlan.setRemark(zxSkTtReqPlan.getRemark());
           // ??????
           dbzxSkTtReqPlan.setCombProp(zxSkTtReqPlan.getCombProp());
           // ????????????
           dbzxSkTtReqPlan.setProjectNumber(zxSkTtReqPlan.getProjectNumber());
           // ?????????
           dbzxSkTtReqPlan.setAurhorizedPersonnel(zxSkTtReqPlan.getAurhorizedPersonnel());
           // ??????id
           dbzxSkTtReqPlan.setCompanyId(zxSkTtReqPlan.getCompanyId());
           // ????????????
           dbzxSkTtReqPlan.setCompanyName(zxSkTtReqPlan.getCompanyName());
           // ??????
           dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxSkTtReqPlanMapper.updateByPrimaryKey(dbzxSkTtReqPlan);

           //???????????????
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(dbzxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItems = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            if(zxSkTtReqPlanItems != null && zxSkTtReqPlanItems.size()>0) {
                zxSkTtReqPlanItem.setModifyUserInfo(userKey, realName);
                zxSkTtReqPlanItemMapper.batchDeleteUpdateZxSkTtReqPlanItem(zxSkTtReqPlanItems, zxSkTtReqPlanItem);
            }
            //??????list
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlan.getZxSkTtReqPlanItemList();
            if(zxSkTtReqPlanItemList != null && zxSkTtReqPlanItemList.size()>0) {
                for(ZxSkTtReqPlanItem zxSkTtReqPlanItem1 : zxSkTtReqPlanItemList) {
                    zxSkTtReqPlanItem1.setId(UuidUtil.generate());
                    zxSkTtReqPlanItem1.setTtReqPlanID(dbzxSkTtReqPlan.getId());
                    zxSkTtReqPlanItem1.setCreateUserInfo(userKey, realName);
                    zxSkTtReqPlanItemMapper.insert(zxSkTtReqPlanItem1);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTtReqPlan);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTtReqPlan(List<ZxSkTtReqPlan> zxSkTtReqPlanList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTtReqPlanList != null && zxSkTtReqPlanList.size() > 0) {
            for (ZxSkTtReqPlan zxSkTtReqPlan : zxSkTtReqPlanList) {
                // ????????????
                ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
                zxSkTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                List<ZxSkTtReqPlanItem> deleteZxSkTtReqPlanItems = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
                if(deleteZxSkTtReqPlanItems != null && deleteZxSkTtReqPlanItems.size()>0) {
                    zxSkTtReqPlanItem.setModifyUserInfo(userKey, realName);
                    zxSkTtReqPlanItemMapper.batchDeleteUpdateZxSkTtReqPlanItem(deleteZxSkTtReqPlanItems, zxSkTtReqPlanItem);
                }
            }
           ZxSkTtReqPlan zxSkTtReqPlan = new ZxSkTtReqPlan();
           zxSkTtReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxSkTtReqPlanMapper.batchDeleteUpdateZxSkTtReqPlan(zxSkTtReqPlanList, zxSkTtReqPlan);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkTtReqPlanList);
        }
    }

    @Override
    public synchronized ResponseEntity checkZxSkTtReqPlanList(ZxSkTtReqPlan zxSkTtReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTtReqPlan dbzxSkTtReqPlan = zxSkTtReqPlanMapper.selectByPrimaryKey(zxSkTtReqPlan.getId());
        if(StrUtil.equals(dbzxSkTtReqPlan.getStatus(), "1")) {
            return repEntity.layerMessage("no", "??????????????????????????????????????????");
        }
        dbzxSkTtReqPlan.setStatus("1");
        dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
        flag = zxSkTtReqPlanMapper.checkZxSkTtReqPlanList(dbzxSkTtReqPlan);
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            //???????????????????????????????????????????????????

            //????????????
            ZxSkTtReqPlanItem zxSkTtReqPlanItem1 = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem1.setTtReqPlanID(dbzxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem1);

            if(CollUtil.isNotEmpty(zxSkTtReqPlanItemList)){
                for (ZxSkTtReqPlanItem zxSkTtReqPlanItem : zxSkTtReqPlanItemList) {

                    ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                    //??????id
                    zxSkHttreqPlanItem.setTtReqPlanID(dbzxSkTtReqPlan.getId());
                    //??????id
                    zxSkHttreqPlanItem.setTtReqPlanItemID(zxSkTtReqPlanItem.getId());
                    //?????????
                    zxSkHttreqPlanItem.setOper(realName);
                    //????????????
                    zxSkHttreqPlanItem.setOpTime(new Date());
                    //?????????
                    zxSkHttreqPlanItem.setSourceNum(zxSkTtReqPlanItem.getTotalNum());
                    //?????????
                    zxSkHttreqPlanItem.setChangeNum(new BigDecimal("0"));
                    //?????? ????????????
                    zxSkHttreqPlanItem.setOp("??????");
                    zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);
                }
            }
            return repEntity.ok("sys.data.update",dbzxSkTtReqPlan);
        }
    }

    @Override
    public ResponseEntity updateZxSkTtReqPlanCheckOver(ZxSkTtReqPlan zxSkTtReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTtReqPlan dbzxSkTtReqPlan = zxSkTtReqPlanMapper.selectByPrimaryKey(zxSkTtReqPlan.getId());
        if (dbzxSkTtReqPlan != null && StrUtil.isNotEmpty(dbzxSkTtReqPlan.getId())) {
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(dbzxSkTtReqPlan.getId());
            //????????? ??????
            List<ZxSkTtReqPlanItem> dbzxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            if(dbzxSkTtReqPlanItemList!=null && dbzxSkTtReqPlanItemList.size()>0){
                //????????????
                List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlan.getZxSkTtReqPlanItemList();
                if(zxSkTtReqPlanItemList!=null && zxSkTtReqPlanItemList.size()>0){
                    //??????????????????
                    for (ZxSkTtReqPlanItem dbskTtReqPlanItem : dbzxSkTtReqPlanItemList) {
                        for (ZxSkTtReqPlanItem ttReqPlanItem : zxSkTtReqPlanItemList) {
                            //????????????
                            //???????????????????????????????????????????????????????????????
                            if(StrUtil.equals(dbskTtReqPlanItem.getId(),ttReqPlanItem.getId())){
                                if(CalcUtils.compareTo(dbskTtReqPlanItem.getChangeNum(),ttReqPlanItem.getChangeNum()) != 0){
                                    ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                                    //??????id
                                    zxSkHttreqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                                    //??????id
                                    zxSkHttreqPlanItem.setTtReqPlanItemID(dbskTtReqPlanItem.getId());
                                    //?????????
                                    zxSkHttreqPlanItem.setOper(realName);
                                    //????????????
                                    zxSkHttreqPlanItem.setOpTime(new Date());
                                    //?????????
                                    zxSkHttreqPlanItem.setSourceNum(ttReqPlanItem.getTotalNum());
                                    //?????????
                                    zxSkHttreqPlanItem.setChangeNum(ttReqPlanItem.getChangeNum());
                                    //?????? ????????????
                                    zxSkHttreqPlanItem.setOp("??????");
                                    zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);

                                    //????????????
                                    dbskTtReqPlanItem.setChangeNum(ttReqPlanItem.getChangeNum());
                                    zxSkTtReqPlanItemService.updateZxSkTtReqPlanItem(dbskTtReqPlanItem);
                                }
                                break;
                            }
                        }
                    }

                    //?????????????????????????????????????????????.??????filter?????????????????????id?????????
                    //List<Long> aIds=a.stream().map(A::getaId).collect(Collectors.toList());//id????????????????????????????????????A???list??????id
                    //List<B> newB=b.stream().filter(B ->!aIds.contains(B.getbId())).collect(Collectors.toList());//B????????????A?????????????????????
                    //?????????<?????????
                    if(CalcUtils.compareTo(BigDecimal.valueOf(dbzxSkTtReqPlanItemList.size()),BigDecimal.valueOf(zxSkTtReqPlanItemList.size()))<0){
                        //????????????????????????.
                        List<String> dbIdList = dbzxSkTtReqPlanItemList.stream().map(ZxSkTtReqPlanItem::getId).collect(Collectors.toList());
                        //List.contains => id???????????????????????????,????????????true
                        List<ZxSkTtReqPlanItem> collect = zxSkTtReqPlanItemList.stream().filter(o -> !dbIdList.contains(o.getId())).collect(Collectors.toList());
                        //??????????????????
                        for (ZxSkTtReqPlanItem skTtReqPlanItem : collect) {
                            skTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                            skTtReqPlanItem.setId((UuidUtil.generate()));
                            skTtReqPlanItem.setCreateUserInfo(userKey, realName);
                            zxSkTtReqPlanItemMapper.insert(skTtReqPlanItem);

                            //??????????????????,??????
                            ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                            //??????id
                            zxSkHttreqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                            //??????id
                            zxSkHttreqPlanItem.setTtReqPlanItemID(skTtReqPlanItem.getId());
                            //?????????
                            zxSkHttreqPlanItem.setOper(realName);
                            //????????????
                            zxSkHttreqPlanItem.setOpTime(new Date());
                            //?????????
                            zxSkHttreqPlanItem.setSourceNum(new BigDecimal("0"));
                            //?????????
                            zxSkHttreqPlanItem.setChangeNum(skTtReqPlanItem.getChangeNum());
                            //?????? ????????????
                            zxSkHttreqPlanItem.setOp("??????");
                            zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);
                        }
                    }
                }
            }
            // ??????
            dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
            flag = zxSkTtReqPlanMapper.updateByPrimaryKey(dbzxSkTtReqPlan);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTtReqPlan);
        }
    }
}
