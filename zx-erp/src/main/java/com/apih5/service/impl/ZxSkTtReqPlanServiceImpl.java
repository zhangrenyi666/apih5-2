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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTtReqPlan.setCompanyId("");
            zxSkTtReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTtReqPlan.setCompanyId(zxSkTtReqPlan.getProjectID());
            zxSkTtReqPlan.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getProjectID());
            if(StrUtil.isNotEmpty(zxSkTtReqPlan.getOrgID2())){
                zxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkTtReqPlan.getPage(),zxSkTtReqPlan.getLimit());
        // 获取数据
        List<ZxSkTtReqPlan> zxSkTtReqPlanList = zxSkTtReqPlanMapper.selectByZxSkTtReqPlanList(zxSkTtReqPlan);
        //查询明细
        for (ZxSkTtReqPlan zxSkTtReqPlan1 : zxSkTtReqPlanList) {
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan1.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            for (ZxSkTtReqPlanItem skTtReqPlanItem : zxSkTtReqPlanItemList) {
                skTtReqPlanItem.setAddData("true");
            }
            zxSkTtReqPlan1.setZxSkTtReqPlanItemList(zxSkTtReqPlanItemList);
        }
        // 得到分页信息
        PageInfo<ZxSkTtReqPlan> p = new PageInfo<>(zxSkTtReqPlanList);

        return repEntity.okList(zxSkTtReqPlanList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTtReqPlanDetails(ZxSkTtReqPlan zxSkTtReqPlan) {
        if (zxSkTtReqPlan == null) {
            zxSkTtReqPlan = new ZxSkTtReqPlan();
        }
        // 获取数据
        ZxSkTtReqPlan dbZxSkTtReqPlan = zxSkTtReqPlanMapper.selectByPrimaryKey(zxSkTtReqPlan.getId());
        // 数据存在
        if (dbZxSkTtReqPlan != null) {
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(dbZxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItems = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            dbZxSkTtReqPlan.setZxSkTtReqPlanItemList(zxSkTtReqPlanItems);
            return repEntity.ok(dbZxSkTtReqPlan);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTtReqPlan(ZxSkTtReqPlan zxSkTtReqPlan) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTtReqPlan.setId(UuidUtil.generate());
        zxSkTtReqPlan.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkTtReqPlan.setStatus("0");
        int flag = zxSkTtReqPlanMapper.insert(zxSkTtReqPlan);
        //创建明细
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
           // 项目id
           dbzxSkTtReqPlan.setProjectID(zxSkTtReqPlan.getProjectID());
           // 项目名称
           dbzxSkTtReqPlan.setProjectName(zxSkTtReqPlan.getProjectName());
           // 日期
           dbzxSkTtReqPlan.setCreateDate(zxSkTtReqPlan.getCreateDate());
           // 金额
           dbzxSkTtReqPlan.setTotalMoney(zxSkTtReqPlan.getTotalMoney());
           // 状态
           dbzxSkTtReqPlan.setStatus(zxSkTtReqPlan.getStatus());
           // 备注
           dbzxSkTtReqPlan.setRemark(zxSkTtReqPlan.getRemark());
           // 明细
           dbzxSkTtReqPlan.setCombProp(zxSkTtReqPlan.getCombProp());
           // 计划编号
           dbzxSkTtReqPlan.setProjectNumber(zxSkTtReqPlan.getProjectNumber());
           // 编制人
           dbzxSkTtReqPlan.setAurhorizedPersonnel(zxSkTtReqPlan.getAurhorizedPersonnel());
           // 公司id
           dbzxSkTtReqPlan.setCompanyId(zxSkTtReqPlan.getCompanyId());
           // 公司名称
           dbzxSkTtReqPlan.setCompanyName(zxSkTtReqPlan.getCompanyName());
           // 共通
           dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
           flag = zxSkTtReqPlanMapper.updateByPrimaryKey(dbzxSkTtReqPlan);

           //删除在新增
            ZxSkTtReqPlanItem zxSkTtReqPlanItem = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem.setTtReqPlanID(dbzxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItems = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            if(zxSkTtReqPlanItems != null && zxSkTtReqPlanItems.size()>0) {
                zxSkTtReqPlanItem.setModifyUserInfo(userKey, realName);
                zxSkTtReqPlanItemMapper.batchDeleteUpdateZxSkTtReqPlanItem(zxSkTtReqPlanItems, zxSkTtReqPlanItem);
            }
            //明细list
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
        // 失败
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
                // 删除明细
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
        // 失败
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
            return repEntity.layerMessage("no", "包含已经审核的，请重新选择！");
        }
        dbzxSkTtReqPlan.setStatus("1");
        dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
        flag = zxSkTtReqPlanMapper.checkZxSkTtReqPlanList(dbzxSkTtReqPlan);
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            //在这里做新增物资总需用计划新增历史

            //查询明细
            ZxSkTtReqPlanItem zxSkTtReqPlanItem1 = new ZxSkTtReqPlanItem();
            zxSkTtReqPlanItem1.setTtReqPlanID(dbzxSkTtReqPlan.getId());
            List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem1);

            if(CollUtil.isNotEmpty(zxSkTtReqPlanItemList)){
                for (ZxSkTtReqPlanItem zxSkTtReqPlanItem : zxSkTtReqPlanItemList) {

                    ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                    //主表id
                    zxSkHttreqPlanItem.setTtReqPlanID(dbzxSkTtReqPlan.getId());
                    //子表id
                    zxSkHttreqPlanItem.setTtReqPlanItemID(zxSkTtReqPlanItem.getId());
                    //操作人
                    zxSkHttreqPlanItem.setOper(realName);
                    //操作时间
                    zxSkHttreqPlanItem.setOpTime(new Date());
                    //原始量
                    zxSkHttreqPlanItem.setSourceNum(zxSkTtReqPlanItem.getTotalNum());
                    //变更量
                    zxSkHttreqPlanItem.setChangeNum(new BigDecimal("0"));
                    //操作 默认新增
                    zxSkHttreqPlanItem.setOp("新增");
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
            //数据库 明细
            List<ZxSkTtReqPlanItem> dbzxSkTtReqPlanItemList = zxSkTtReqPlanItemMapper.selectByZxSkTtReqPlanItemList(zxSkTtReqPlanItem);
            if(dbzxSkTtReqPlanItemList!=null && dbzxSkTtReqPlanItemList.size()>0){
                //传进来的
                List<ZxSkTtReqPlanItem> zxSkTtReqPlanItemList = zxSkTtReqPlan.getZxSkTtReqPlanItemList();
                if(zxSkTtReqPlanItemList!=null && zxSkTtReqPlanItemList.size()>0){
                    //修改无新增的
                    for (ZxSkTtReqPlanItem dbskTtReqPlanItem : dbzxSkTtReqPlanItemList) {
                        for (ZxSkTtReqPlanItem ttReqPlanItem : zxSkTtReqPlanItemList) {
                            //变更记录
                            //数据库的变更量和传入的变更量不同时添加数据
                            if(StrUtil.equals(dbskTtReqPlanItem.getId(),ttReqPlanItem.getId())){
                                if(CalcUtils.compareTo(dbskTtReqPlanItem.getChangeNum(),ttReqPlanItem.getChangeNum()) != 0){
                                    ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                                    //主表id
                                    zxSkHttreqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                                    //子表id
                                    zxSkHttreqPlanItem.setTtReqPlanItemID(dbskTtReqPlanItem.getId());
                                    //操作人
                                    zxSkHttreqPlanItem.setOper(realName);
                                    //操作时间
                                    zxSkHttreqPlanItem.setOpTime(new Date());
                                    //原始量
                                    zxSkHttreqPlanItem.setSourceNum(ttReqPlanItem.getTotalNum());
                                    //变更量
                                    zxSkHttreqPlanItem.setChangeNum(ttReqPlanItem.getChangeNum());
                                    //操作 默认新增
                                    zxSkHttreqPlanItem.setOp("修改");
                                    zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);

                                    //修改明细
                                    dbskTtReqPlanItem.setChangeNum(ttReqPlanItem.getChangeNum());
                                    zxSkTtReqPlanItemService.updateZxSkTtReqPlanItem(dbskTtReqPlanItem);
                                }
                                break;
                            }
                        }
                    }

                    //如果变更明细比数据库明细大的话.就用filter过滤一下不一样id的数据
                    //List<Long> aIds=a.stream().map(A::getaId).collect(Collectors.toList());//id为两个列表相同属性，取出A的list中的id
                    //List<B> newB=b.stream().filter(B ->!aIds.contains(B.getbId())).collect(Collectors.toList());//B列表去除A列表已有的数据
                    //数据库<传入的
                    if(CalcUtils.compareTo(BigDecimal.valueOf(dbzxSkTtReqPlanItemList.size()),BigDecimal.valueOf(zxSkTtReqPlanItemList.size()))<0){
                        //明细去除数据库的.
                        List<String> dbIdList = dbzxSkTtReqPlanItemList.stream().map(ZxSkTtReqPlanItem::getId).collect(Collectors.toList());
                        //List.contains => id是否存在这个集合中,存在返回true
                        List<ZxSkTtReqPlanItem> collect = zxSkTtReqPlanItemList.stream().filter(o -> !dbIdList.contains(o.getId())).collect(Collectors.toList());
                        //创建这个明细
                        for (ZxSkTtReqPlanItem skTtReqPlanItem : collect) {
                            skTtReqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                            skTtReqPlanItem.setId((UuidUtil.generate()));
                            skTtReqPlanItem.setCreateUserInfo(userKey, realName);
                            zxSkTtReqPlanItemMapper.insert(skTtReqPlanItem);

                            //明细的变动表,新增
                            ZxSkHttreqPlanItem zxSkHttreqPlanItem = new ZxSkHttreqPlanItem();
                            //主表id
                            zxSkHttreqPlanItem.setTtReqPlanID(zxSkTtReqPlan.getId());
                            //子表id
                            zxSkHttreqPlanItem.setTtReqPlanItemID(skTtReqPlanItem.getId());
                            //操作人
                            zxSkHttreqPlanItem.setOper(realName);
                            //操作时间
                            zxSkHttreqPlanItem.setOpTime(new Date());
                            //原始量
                            zxSkHttreqPlanItem.setSourceNum(new BigDecimal("0"));
                            //变更量
                            zxSkHttreqPlanItem.setChangeNum(skTtReqPlanItem.getChangeNum());
                            //操作 默认新增
                            zxSkHttreqPlanItem.setOp("新增");
                            zxSkHttreqPlanItemService.saveZxSkHttreqPlanItem(zxSkHttreqPlanItem);
                        }
                    }
                }
            }
            // 共通
            dbzxSkTtReqPlan.setModifyUserInfo(userKey, realName);
            flag = zxSkTtReqPlanMapper.updateByPrimaryKey(dbzxSkTtReqPlan);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTtReqPlan);
        }
    }
}
