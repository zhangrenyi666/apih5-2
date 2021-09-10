package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkResourceMaterialsMapper;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import com.apih5.service.ZxSkResourceMaterialsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResourceMaterialsService")
public class ZxSkResourceMaterialsServiceImpl implements ZxSkResourceMaterialsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Override
    public ResponseEntity getZxSkResourceMaterialsListByCondition(ZxSkResourceMaterials zxSkResourceMaterials) {
        if (zxSkResourceMaterials == null) {
            zxSkResourceMaterials = new ZxSkResourceMaterials();
        }
        // 分页查询
        PageHelper.startPage(zxSkResourceMaterials.getPage(),zxSkResourceMaterials.getLimit());

        //todo:数据迁移前 使用物资分类mt
//        zxSkResourceMaterials.setResStyle("mt");
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> p = new PageInfo<>(zxSkResourceMaterialsList);

        return repEntity.okList(zxSkResourceMaterialsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResourceMaterialsDetails(ZxSkResourceMaterials zxSkResourceMaterials) {
        if (zxSkResourceMaterials == null) {
            zxSkResourceMaterials = new ZxSkResourceMaterials();
        }
        zxSkResourceMaterials.setResStyle("mt");
        // 获取数据
        ZxSkResourceMaterials dbZxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(zxSkResourceMaterials.getId());
        // 数据存在
        if (dbZxSkResourceMaterials != null) {
            return repEntity.ok(dbZxSkResourceMaterials);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResourceMaterials(ZxSkResourceMaterials zxSkResourceMaterials) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResourceMaterials.setId(UuidUtil.generate());
        zxSkResourceMaterials.setCreateUserInfo(userKey, realName);
        int flag = zxSkResourceMaterialsMapper.insert(zxSkResourceMaterials);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkResourceMaterials);
        }
    }

    @Override
    public ResponseEntity updateZxSkResourceMaterials(ZxSkResourceMaterials zxSkResourceMaterials) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResourceMaterials dbzxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(zxSkResourceMaterials.getId());
        if (dbzxSkResourceMaterials != null && StrUtil.isNotEmpty(dbzxSkResourceMaterials.getId())) {
           // 资源分类
           dbzxSkResourceMaterials.setCategoryID(zxSkResourceMaterials.getCategoryID());
           // 资源编号
           dbzxSkResourceMaterials.setResCode(zxSkResourceMaterials.getResCode());
           // 资源名称
           dbzxSkResourceMaterials.setResName(zxSkResourceMaterials.getResName());
           // 计量单位
           dbzxSkResourceMaterials.setUnit(zxSkResourceMaterials.getUnit());
           // 规格型号
           dbzxSkResourceMaterials.setSpec(zxSkResourceMaterials.getSpec());
           // 临时编码
           dbzxSkResourceMaterials.setTempNo(zxSkResourceMaterials.getTempNo());
           // 资源类型
           dbzxSkResourceMaterials.setResStyle(zxSkResourceMaterials.getResStyle());
           // 业务类型
           dbzxSkResourceMaterials.setBizType(zxSkResourceMaterials.getBizType());
           // 启用标志
           dbzxSkResourceMaterials.setEnableFlag(zxSkResourceMaterials.getEnableFlag());
           // 
           dbzxSkResourceMaterials.setOrgLevel(zxSkResourceMaterials.getOrgLevel());
           // 隶属机构ID
           dbzxSkResourceMaterials.setOrgID(zxSkResourceMaterials.getOrgID());
           // 隶属机构
           dbzxSkResourceMaterials.setOrgName(zxSkResourceMaterials.getOrgName());
           // 参考单价
           dbzxSkResourceMaterials.setRefPrice(zxSkResourceMaterials.getRefPrice());
           // 参考计价方式
           dbzxSkResourceMaterials.setRefpriceType(zxSkResourceMaterials.getRefpriceType());
           // 
           dbzxSkResourceMaterials.setIsComplex(zxSkResourceMaterials.getIsComplex());
           // 备注
           dbzxSkResourceMaterials.setRemark(zxSkResourceMaterials.getRemark());
           // 明细
           dbzxSkResourceMaterials.setCombProp(zxSkResourceMaterials.getCombProp());
           // 资源代码
           dbzxSkResourceMaterials.setSourceCode(zxSkResourceMaterials.getSourceCode());
           // ABC分类
           dbzxSkResourceMaterials.setAbcCategory(zxSkResourceMaterials.getAbcCategory());
           // 是否启用(局下达资源)
           dbzxSkResourceMaterials.setIsGroup(zxSkResourceMaterials.getIsGroup());
           // 
           dbzxSkResourceMaterials.setSendTime(zxSkResourceMaterials.getSendTime());
           // 
           dbzxSkResourceMaterials.setIsMainMaterial(zxSkResourceMaterials.getIsMainMaterial());
           // 
           dbzxSkResourceMaterials.setIsSporadicMaterial(zxSkResourceMaterials.getIsSporadicMaterial());
           // 共通
           dbzxSkResourceMaterials.setModifyUserInfo(userKey, realName);
           flag = zxSkResourceMaterialsMapper.updateByPrimaryKey(dbzxSkResourceMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkResourceMaterials);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResourceMaterialsList != null && zxSkResourceMaterialsList.size() > 0) {
           ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
           zxSkResourceMaterials.setModifyUserInfo(userKey, realName);
           flag = zxSkResourceMaterialsMapper.batchDeleteUpdateZxSkResourceMaterials(zxSkResourceMaterialsList, zxSkResourceMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkResourceMaterialsList);
        }
    }

    @Override
    public ResponseEntity batchStartUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResourceMaterialsList != null && zxSkResourceMaterialsList.size() > 0) {
            ZxSkResourceMaterials resourceMaterials = new ZxSkResourceMaterials();
            resourceMaterials.setModifyUserInfo(userKey, realName);
            flag = zxSkResourceMaterialsMapper.batchStartUpdateZxSkResourceMaterials(zxSkResourceMaterialsList, resourceMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update"
                    + "",zxSkResourceMaterialsList);
        }
    }

    @Override
    public ResponseEntity batchStopUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResourceMaterialsList != null && zxSkResourceMaterialsList.size() > 0) {
            ZxSkResourceMaterials resourceMaterials = new ZxSkResourceMaterials();
            resourceMaterials.setModifyUserInfo(userKey, realName);
            flag = zxSkResourceMaterialsMapper.batchStopUpdateZxSkResourceMaterials(zxSkResourceMaterialsList, resourceMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update"
                    + "",zxSkResourceMaterialsList);
        }
    }

    @Override
    public ResponseEntity getZxSkResourceMaterialsListNameJoin(ZxSkResourceMaterials zxSkResourceMaterials) {
        if (zxSkResourceMaterials == null) {
            zxSkResourceMaterials = new ZxSkResourceMaterials();
        }
        if(StrUtil.isEmpty(zxSkResourceMaterials.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }else {
            //todo: 数据迁移时注意
            zxSkResourceMaterials.setOrgID("");
        }
//        zxSkResourceMaterials.setResStyle("mt");
        // 分页查询
        PageHelper.startPage(zxSkResourceMaterials.getPage(),zxSkResourceMaterials.getLimit());
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);

        for (ZxSkResourceMaterials SkResourceMaterials : zxSkResourceMaterialsList) {
            SkResourceMaterials.setRefpriceType("移动平均");
        }
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> p = new PageInfo<>(zxSkResourceMaterialsList);
        return repEntity.okList(zxSkResourceMaterialsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResourceMaterialsListNameJoinNotRevolve(ZxSkResourceMaterials zxSkResourceMaterials) {
        if (zxSkResourceMaterials == null) {
            zxSkResourceMaterials = new ZxSkResourceMaterials();
        }
        if(StrUtil.isEmpty(zxSkResourceMaterials.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }else {
            //todo: 数据迁移时注意
            zxSkResourceMaterials.setOrgID("");
        }
//        zxSkResourceMaterials.setResStyle("mt");
        // 分页查询
        PageHelper.startPage(zxSkResourceMaterials.getPage(),zxSkResourceMaterials.getLimit());
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResourceMaterialsMapper.getZxSkResourceMaterialsListNameJoinNotRevolve(zxSkResourceMaterials);

        for (ZxSkResourceMaterials SkResourceMaterials : zxSkResourceMaterialsList) {
            SkResourceMaterials.setRefpriceType("移动平均");
        }
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> p = new PageInfo<>(zxSkResourceMaterialsList);
        return repEntity.okList(zxSkResourceMaterialsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResourceMaterialsListNotRevolving(ZxSkResourceMaterials zxSkResourceMaterials) {
        if (zxSkResourceMaterials == null) {
            zxSkResourceMaterials = new ZxSkResourceMaterials();
        }
        // 分页查询
        PageHelper.startPage(zxSkResourceMaterials.getPage(),zxSkResourceMaterials.getLimit());

        //todo:数据迁移前 使用物资分类mt
//        zxSkResourceMaterials.setResStyle("mt");
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResourceMaterialsMapper.getZxSkResourceMaterialsListNotRevolving(zxSkResourceMaterials);
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> p = new PageInfo<>(zxSkResourceMaterialsList);

        return repEntity.okList(zxSkResourceMaterialsList, p.getTotal());
    }
}
