package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkWarehouseMapper;
import com.apih5.mybatis.pojo.ZxSkWarehouse;
import com.apih5.service.ZxSkWarehouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkWarehouseService")
public class ZxSkWarehouseServiceImpl implements ZxSkWarehouseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkWarehouseMapper zxSkWarehouseMapper;

    @Override
    public ResponseEntity getZxSkWarehouseListByCondition(ZxSkWarehouse zxSkWarehouse) {
        if (zxSkWarehouse == null) {
            zxSkWarehouse = new ZxSkWarehouse();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkWarehouse.setCompanyId("");
            zxSkWarehouse.setParentOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkWarehouse.setCompanyId(zxSkWarehouse.getParentOrgID());
            zxSkWarehouse.setParentOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkWarehouse.setParentOrgID(zxSkWarehouse.getParentOrgID());
        }

        // 分页查询
        PageHelper.startPage(zxSkWarehouse.getPage(),zxSkWarehouse.getLimit());
        // 获取数据
        List<ZxSkWarehouse> zxSkWarehouseList = zxSkWarehouseMapper.selectByZxSkWarehouseList(zxSkWarehouse);
        // 得到分页信息
        PageInfo<ZxSkWarehouse> p = new PageInfo<>(zxSkWarehouseList);

        return repEntity.okList(zxSkWarehouseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkWarehouseDetails(ZxSkWarehouse zxSkWarehouse) {
        if (zxSkWarehouse == null) {
            zxSkWarehouse = new ZxSkWarehouse();
        }
        // 获取数据
        ZxSkWarehouse dbZxSkWarehouse = zxSkWarehouseMapper.selectByPrimaryKey(zxSkWarehouse.getId());
        // 数据存在
        if (dbZxSkWarehouse != null) {
            return repEntity.ok(dbZxSkWarehouse);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSkWarehouse(ZxSkWarehouse zxSkWarehouse) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkWarehouse.setId(UuidUtil.generate());
        zxSkWarehouse.setCreateUserInfo(userKey, realName);
        int flag = zxSkWarehouseMapper.insert(zxSkWarehouse);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkWarehouse);
        }
    }

    @Override
    public ResponseEntity updateZxSkWarehouse(ZxSkWarehouse zxSkWarehouse) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkWarehouse dbzxSkWarehouse = zxSkWarehouseMapper.selectByPrimaryKey(zxSkWarehouse.getId());
        if (dbzxSkWarehouse != null && StrUtil.isNotEmpty(dbzxSkWarehouse.getId())) {
           // 仓库编号
           dbzxSkWarehouse.setWarehouseCode(zxSkWarehouse.getWarehouseCode());
           // 仓库名称
           dbzxSkWarehouse.setWarehouseName(zxSkWarehouse.getWarehouseName());
           // 仓库描述
           dbzxSkWarehouse.setWarehouseDesc(zxSkWarehouse.getWarehouseDesc());
           // 计价方式
           dbzxSkWarehouse.setPriceType(zxSkWarehouse.getPriceType());
           // 关联机构
           dbzxSkWarehouse.setOrgName(zxSkWarehouse.getOrgName());
           // 关联机构ID
           dbzxSkWarehouse.setOrgID(zxSkWarehouse.getOrgID());
           // 所属机构
           dbzxSkWarehouse.setParentOrgName(zxSkWarehouse.getParentOrgName());
           // 所属机构ID
           dbzxSkWarehouse.setParentOrgID(zxSkWarehouse.getParentOrgID());
           // 业务类型
           dbzxSkWarehouse.setBizType(zxSkWarehouse.getBizType());
           // 备注
           dbzxSkWarehouse.setRemark(zxSkWarehouse.getRemark());
           // 明细
           dbzxSkWarehouse.setCombProp(zxSkWarehouse.getCombProp());
           // 公司id
           dbzxSkWarehouse.setCompanyId(zxSkWarehouse.getCompanyId());
           // 公司名称
           dbzxSkWarehouse.setCompanyName(zxSkWarehouse.getCompanyName());
           // 共通
           dbzxSkWarehouse.setModifyUserInfo(userKey, realName);
           flag = zxSkWarehouseMapper.updateByPrimaryKey(dbzxSkWarehouse);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkWarehouse);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkWarehouse(List<ZxSkWarehouse> zxSkWarehouseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkWarehouseList != null && zxSkWarehouseList.size() > 0) {
           ZxSkWarehouse zxSkWarehouse = new ZxSkWarehouse();
           zxSkWarehouse.setModifyUserInfo(userKey, realName);
           flag = zxSkWarehouseMapper.batchDeleteUpdateZxSkWarehouse(zxSkWarehouseList, zxSkWarehouse);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkWarehouseList);
        }
    }


    @Override
    public ResponseEntity getZxSkWarehouseByParentOrgIDList(ZxSkWarehouse zxSkWarehouse) {
        if (zxSkWarehouse == null) {
            zxSkWarehouse = new ZxSkWarehouse();
        }
        if(StrUtil.isEmpty(zxSkWarehouse.getParentOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkWarehouse.getPage(),zxSkWarehouse.getLimit());
        // 获取数据
        List<ZxSkWarehouse> zxSkWarehouseList = zxSkWarehouseMapper.selectByZxSkWarehouseList(zxSkWarehouse);
        // 得到分页信息
        PageInfo<ZxSkWarehouse> p = new PageInfo<>(zxSkWarehouseList);

        return repEntity.okList(zxSkWarehouseList, p.getTotal());
    }

}
