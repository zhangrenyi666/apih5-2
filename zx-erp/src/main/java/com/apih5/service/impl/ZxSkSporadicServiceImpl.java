package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSkSporadicItemMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkSporadicMapper;
import com.apih5.mybatis.pojo.ZxSkSporadic;
import com.apih5.service.ZxSkSporadicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkSporadicService")
public class ZxSkSporadicServiceImpl implements ZxSkSporadicService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkSporadicMapper zxSkSporadicMapper;

    @Autowired(required = true)
    private ZxSkSporadicItemMapper zxSkSporadicItemMapper;


    @Override
    public ResponseEntity getZxSkSporadicListByCondition(ZxSkSporadic zxSkSporadic) {
        if (zxSkSporadic == null) {
            zxSkSporadic = new ZxSkSporadic();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkSporadic.setCompanyId("");
            zxSkSporadic.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkSporadic.getOrgID2())){
                zxSkSporadic.setProjectID(zxSkSporadic.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkSporadic.setCompanyId(zxSkSporadic.getProjectID());
            zxSkSporadic.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkSporadic.getOrgID2())){
                zxSkSporadic.setProjectID(zxSkSporadic.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkSporadic.setProjectID(zxSkSporadic.getProjectID());
            if(StrUtil.isNotEmpty(zxSkSporadic.getOrgID2())){
                zxSkSporadic.setProjectID(zxSkSporadic.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkSporadic.getPage(),zxSkSporadic.getLimit());
        // 获取数据
        List<ZxSkSporadic> zxSkSporadicList = zxSkSporadicMapper.selectByZxSkSporadicList(zxSkSporadic);
        //查询明细
        for (ZxSkSporadic zxSkSporadic1 : zxSkSporadicList) {
            ZxSkSporadicItem zxSkSporadicItem = new ZxSkSporadicItem();
            zxSkSporadicItem.setSporadicID(zxSkSporadic1.getId());
            List<ZxSkSporadicItem> zxSkSporadicItems = zxSkSporadicItemMapper.selectByZxSkSporadicItemList(zxSkSporadicItem);
            zxSkSporadic1.setZxSkSporadicItemList(zxSkSporadicItems);
        }
        // 得到分页信息
        PageInfo<ZxSkSporadic> p = new PageInfo<>(zxSkSporadicList);

        return repEntity.okList(zxSkSporadicList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkSporadicDetails(ZxSkSporadic zxSkSporadic) {
        if (zxSkSporadic == null) {
            zxSkSporadic = new ZxSkSporadic();
        }
        // 获取数据
        ZxSkSporadic dbZxSkSporadic = zxSkSporadicMapper.selectByPrimaryKey(zxSkSporadic.getId());
        // 数据存在
        if (dbZxSkSporadic != null) {
            ZxSkSporadicItem zxSkSporadicItem = new ZxSkSporadicItem();
            zxSkSporadicItem.setSporadicID(dbZxSkSporadic.getId());
            List<ZxSkSporadicItem> zxSkSporadicItems = zxSkSporadicItemMapper.selectByZxSkSporadicItemList(zxSkSporadicItem);
            dbZxSkSporadic.setZxSkSporadicItemList(zxSkSporadicItems);
            return repEntity.ok(dbZxSkSporadic);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkSporadic(ZxSkSporadic zxSkSporadic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkSporadic.setId(UuidUtil.generate());
        zxSkSporadic.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkSporadic.setStatus("0");
        int flag = zxSkSporadicMapper.insert(zxSkSporadic);
        //创建明细
        List<ZxSkSporadicItem> zxSkSporadicItemList = zxSkSporadic.getZxSkSporadicItemList();
        if(zxSkSporadicItemList != null && zxSkSporadicItemList.size()>0){
            for (ZxSkSporadicItem zxSkSporadicItem : zxSkSporadicItemList) {
                zxSkSporadicItem.setSporadicID(zxSkSporadic.getId());
                zxSkSporadicItem.setId((UuidUtil.generate()));
                zxSkSporadicItem.setCreateUserInfo(userKey, realName);
                zxSkSporadicItemMapper.insert(zxSkSporadicItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkSporadic);
        }
    }

    @Override
    public ResponseEntity updateZxSkSporadic(ZxSkSporadic zxSkSporadic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkSporadic dbzxSkSporadic = zxSkSporadicMapper.selectByPrimaryKey(zxSkSporadic.getId());
        if (dbzxSkSporadic != null && StrUtil.isNotEmpty(dbzxSkSporadic.getId())) {
           // 项目ID
           dbzxSkSporadic.setProjectID(zxSkSporadic.getProjectID());
           // 项目名称
           dbzxSkSporadic.setProjectName(zxSkSporadic.getProjectName());
           // 日期
           dbzxSkSporadic.setCreateDate(zxSkSporadic.getCreateDate());
           // 金额
           dbzxSkSporadic.setTotalMoney(zxSkSporadic.getTotalMoney());
           // 状态
           dbzxSkSporadic.setStatus(zxSkSporadic.getStatus());
           // 备注
           dbzxSkSporadic.setRemark(zxSkSporadic.getRemark());
           // 明细
           dbzxSkSporadic.setCombProp(zxSkSporadic.getCombProp());
           // 计划编号
           dbzxSkSporadic.setProjectNumber(zxSkSporadic.getProjectNumber());
           // 编制人
           dbzxSkSporadic.setAurhorizedPersonnel(zxSkSporadic.getAurhorizedPersonnel());
           // 公司id
           dbzxSkSporadic.setCompanyId(zxSkSporadic.getCompanyId());
           // 公司名称
           dbzxSkSporadic.setCompanyName(zxSkSporadic.getCompanyName());
           // 共通
           dbzxSkSporadic.setModifyUserInfo(userKey, realName);
           flag = zxSkSporadicMapper.updateByPrimaryKey(dbzxSkSporadic);

            //删除在新增
            ZxSkSporadicItem zxSkSporadicItem = new ZxSkSporadicItem();
            zxSkSporadicItem.setSporadicID(dbzxSkSporadic.getId());
            List<ZxSkSporadicItem> zxSkSporadicItems = zxSkSporadicItemMapper.selectByZxSkSporadicItemList(zxSkSporadicItem);
            if(zxSkSporadicItems != null && zxSkSporadicItems.size()>0) {
                zxSkSporadicItem.setModifyUserInfo(userKey, realName);
                zxSkSporadicItemMapper.batchDeleteUpdateZxSkSporadicItem(zxSkSporadicItems, zxSkSporadicItem);
            }
            //明细list
            List<ZxSkSporadicItem> zxSkSporadicItemList = zxSkSporadic.getZxSkSporadicItemList();
            if(zxSkSporadicItemList != null && zxSkSporadicItemList.size()>0) {
                for(ZxSkSporadicItem zxSkMmReqPlanItem1 : zxSkSporadicItemList) {
                    zxSkMmReqPlanItem1.setId(UuidUtil.generate());
                    zxSkMmReqPlanItem1.setSporadicID(dbzxSkSporadic.getId());
                    zxSkMmReqPlanItem1.setCreateUserInfo(userKey, realName);
                    zxSkSporadicItemMapper.insert(zxSkMmReqPlanItem1);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkSporadic);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkSporadic(List<ZxSkSporadic> zxSkSporadicList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkSporadicList != null && zxSkSporadicList.size() > 0) {
            for (ZxSkSporadic zxSkSporadic : zxSkSporadicList) {
                // 删除明细
                ZxSkSporadicItem zxSkSporadicItem = new ZxSkSporadicItem();
                zxSkSporadicItem.setSporadicID(zxSkSporadic.getId());
                List<ZxSkSporadicItem> zxSkSporadicItems = zxSkSporadicItemMapper.selectByZxSkSporadicItemList(zxSkSporadicItem);
                if(zxSkSporadicItems != null && zxSkSporadicItems.size()>0) {
                    zxSkSporadicItem.setModifyUserInfo(userKey, realName);
                    zxSkSporadicItemMapper.batchDeleteUpdateZxSkSporadicItem(zxSkSporadicItems, zxSkSporadicItem);
                }
            }
           ZxSkSporadic zxSkSporadic = new ZxSkSporadic();
           zxSkSporadic.setModifyUserInfo(userKey, realName);
           flag = zxSkSporadicMapper.batchDeleteUpdateZxSkSporadic(zxSkSporadicList, zxSkSporadic);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkSporadicList);
        }
    }
}
