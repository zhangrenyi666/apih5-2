package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;
import com.apih5.framework.api.wechatutils.StringUtil;
import com.apih5.mybatis.dao.ZxSkResourceMaterialsMapper;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkResCategoryMaterialsMapper;
import com.apih5.mybatis.pojo.ZxSkResCategoryMaterials;
import com.apih5.service.ZxSkResCategoryMaterialsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResCategoryMaterialsService")
public class ZxSkResCategoryMaterialsServiceImpl implements ZxSkResCategoryMaterialsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsListByCondition(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        // 分页查询
        PageHelper.startPage(zxSkResCategoryMaterials.getPage(),zxSkResCategoryMaterials.getLimit());
        // 获取数据
        List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(zxSkResCategoryMaterials);
        for (ZxSkResCategoryMaterials zxSkResCategoryMaterials1 : zxSkResCategoryMaterialsList) {
            ZxSkResCategoryMaterials zxSkResCategoryMaterials2 = new ZxSkResCategoryMaterials();
            zxSkResCategoryMaterials2.setParentID(zxSkResCategoryMaterials1.getId());
            List<ZxSkResCategoryMaterials> zxSkResCategoryMaterials3 = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(zxSkResCategoryMaterials2);
            zxSkResCategoryMaterials1.setChildrenList(zxSkResCategoryMaterials3);
        }
        // 得到分页信息
        PageInfo<ZxSkResCategoryMaterials> p = new PageInfo<>(zxSkResCategoryMaterialsList);

        return repEntity.okList(zxSkResCategoryMaterialsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsDetails(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        // 获取数据
        ZxSkResCategoryMaterials dbZxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(zxSkResCategoryMaterials.getId());
        // 数据存在
        if (dbZxSkResCategoryMaterials != null) {
            return repEntity.ok(dbZxSkResCategoryMaterials);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResCategoryMaterials(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResCategoryMaterials.setId(UuidUtil.generate());
        // 上一节点的pid_all(包含上一节点id)
        if (StrUtil.isNotEmpty(zxSkResCategoryMaterials.getBsid())) {
            String pidAll = zxSkResCategoryMaterials.getBsid();
            zxSkResCategoryMaterials.setBsid(pidAll + "," + zxSkResCategoryMaterials.getId());
        } else {
//			// 新增根节点时情况
//			zxSkResCategory.setBsid(zxSkResCategory.getParentID());
        }
        zxSkResCategoryMaterials.setCreateUserInfo(userKey, realName);
        int flag = zxSkResCategoryMaterialsMapper.insert(zxSkResCategoryMaterials);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkResCategoryMaterials);
        }
    }

    @Override
    public ResponseEntity updateZxSkResCategoryMaterials(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResCategoryMaterials dbzxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(zxSkResCategoryMaterials.getId());
        if (dbzxSkResCategoryMaterials != null && StrUtil.isNotEmpty(dbzxSkResCategoryMaterials.getId())) {
           // 父节点id
           dbzxSkResCategoryMaterials.setParentID(zxSkResCategoryMaterials.getParentID());
           // bsid
           dbzxSkResCategoryMaterials.setBsid(zxSkResCategoryMaterials.getBsid());
           // 编码
           dbzxSkResCategoryMaterials.setCatCode(zxSkResCategoryMaterials.getCatCode());
           // 名称
           dbzxSkResCategoryMaterials.setCatName(zxSkResCategoryMaterials.getCatName());
           // 单位
           dbzxSkResCategoryMaterials.setUnit(zxSkResCategoryMaterials.getUnit());
           // 规格型号
           dbzxSkResCategoryMaterials.setSpec(zxSkResCategoryMaterials.getSpec());
           // 单价
           dbzxSkResCategoryMaterials.setPrice(zxSkResCategoryMaterials.getPrice());
           // 分类id
           dbzxSkResCategoryMaterials.setResStyle(zxSkResCategoryMaterials.getResStyle());
           // 业务类型
           dbzxSkResCategoryMaterials.setBizType(zxSkResCategoryMaterials.getBizType());
           // 备注
           dbzxSkResCategoryMaterials.setRemark(zxSkResCategoryMaterials.getRemark());
           // 明细
           dbzxSkResCategoryMaterials.setCombProp(zxSkResCategoryMaterials.getCombProp());
           // 计价方式
           dbzxSkResCategoryMaterials.setPriceType(zxSkResCategoryMaterials.getPriceType());
           // 资源类别
           dbzxSkResCategoryMaterials.setOrgResBizType(zxSkResCategoryMaterials.getOrgResBizType());
           // 是否启用(局下达资源)
           dbzxSkResCategoryMaterials.setIsGroup(zxSkResCategoryMaterials.getIsGroup());
           // 
           dbzxSkResCategoryMaterials.setOrgID(zxSkResCategoryMaterials.getOrgID());
           // 
           dbzxSkResCategoryMaterials.setSendTime(zxSkResCategoryMaterials.getSendTime());
           // 共通
           dbzxSkResCategoryMaterials.setModifyUserInfo(userKey, realName);
           flag = zxSkResCategoryMaterialsMapper.updateByPrimaryKey(dbzxSkResCategoryMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkResCategoryMaterials);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResCategoryMaterialsList != null && zxSkResCategoryMaterialsList.size() > 0) {
            for (ZxSkResCategoryMaterials zxSkResCategoryMaterials : zxSkResCategoryMaterialsList) {
                ZxSkResCategoryMaterials categoryMaterial = new ZxSkResCategoryMaterials();
                categoryMaterial.setParentID(zxSkResCategoryMaterials.getId());
                List<ZxSkResCategoryMaterials> categoryMaterials = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(categoryMaterial);
                //子节点
                if(categoryMaterials != null && categoryMaterials.size() >0) {
                    return repEntity.layerMessage("no", "请先删除子节点");
                }
                //物资编码
                ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
                zxSkResourceMaterials.setCategoryID(zxSkResCategoryMaterials.getId());
                List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
                if(zxSkResourceMaterialsList!=null&&zxSkResourceMaterialsList.size()>0){
                    return repEntity.layerMessage("no", "请先删除分类下的物资编码");
                }
            }
           ZxSkResCategoryMaterials zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
           zxSkResCategoryMaterials.setModifyUserInfo(userKey, realName);
           flag = zxSkResCategoryMaterialsMapper.batchDeleteUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList, zxSkResCategoryMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkResCategoryMaterialsList);
        }
    }

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsTree(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if(zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(zxSkResCategoryMaterials);
        JSONArray jsonArray = listToTree(new JSONArray(zxSkResCategoryMaterialsList), "id", "parentID", "catName");
        return repEntity.ok(jsonArray);
    }



    // --内部私有方法
    /**
     * list转tree
     *
     * @param arr
     * @param id
     * @param pid
     * @param name
     * @return tree
     */
    private static JSONArray listToTree(JSONArray arr, String id, String parentID, String catName) {
        String child = "childrenList";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            JSONObject newJSONObject = new JSONObject();
            newJSONObject.set(id, json.get(id));
            newJSONObject.set(parentID, json.get(parentID));
            newJSONObject.set(catName, json.get(catName));
            newJSONObject.set("bsid", json.get("bsid"));
            newJSONObject.set("catCode", json.get("catCode"));
            newJSONObject.set("catName", json.get("catName"));
            newJSONObject.set("unit", json.get("unit"));
            newJSONObject.set("spec", json.get("spec"));
            newJSONObject.set("price", json.get("price"));
            newJSONObject.set("resStyle", json.get("resStyle"));
            newJSONObject.set("bizType", json.get("bizType"));
            newJSONObject.set("remark", json.get("remark"));
            newJSONObject.set("combProp", json.get("combProp"));
            newJSONObject.set("sourceCode", json.get("sourceCode"));
            newJSONObject.set("abcCategory", json.get("abcCategory"));
            newJSONObject.set("editTime", json.get("editTime"));
            newJSONObject.set("isGroup", json.get("isGroup"));
            newJSONObject.set("orgID", json.get("orgID"));
            newJSONObject.set("sendTime", json.get("sendTime"));
            hash.set(json.getStr(id), newJSONObject);
            newArr.add(newJSONObject);
        }

        for (int j = 0; j < newArr.size(); j++) {
            JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
            JSONObject hashVP = hash.getJSONObject(aVal.getStr(parentID));
            if (hashVP != null) {
                if (hashVP.get(child) != null) {
                    JSONArray ch = hashVP.getJSONArray(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else
                r.add(aVal);
        }
        return r;
    }

    @Override
    public ResponseEntity batchStartUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResCategoryMaterialsList != null && zxSkResCategoryMaterialsList.size() > 0) {
            ZxSkResCategoryMaterials resCategoryMaterials = new ZxSkResCategoryMaterials();
            resCategoryMaterials.setModifyUserInfo(userKey, realName);
            flag = zxSkResCategoryMaterialsMapper.batchStartUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList, resCategoryMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update"
                    + "",zxSkResCategoryMaterialsList);
        }
    }

    @Override
    public ResponseEntity batchStopUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResCategoryMaterialsList != null && zxSkResCategoryMaterialsList.size() > 0) {
            ZxSkResCategoryMaterials zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
            zxSkResCategoryMaterials.setModifyUserInfo(userKey, realName);
            flag = zxSkResCategoryMaterialsMapper.batchStopUpdateZxSkResCategoryMaterials(zxSkResCategoryMaterialsList, zxSkResCategoryMaterials);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkResCategoryMaterialsList);
        }
    }

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsListResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        if(StrUtil.isEmpty(zxSkResCategoryMaterials.getParentOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        zxSkResCategoryMaterials.setParentID("0002");
        if(StringUtil.isNotEmpty(zxSkResCategoryMaterials.getSearch())){
            zxSkResCategoryMaterials.setCatName(zxSkResCategoryMaterials.getSearch());
        }
        // 分页查询
        PageHelper.startPage(zxSkResCategoryMaterials.getPage(),zxSkResCategoryMaterials.getLimit());
        // 获取数据
        List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(zxSkResCategoryMaterials);
        // 得到分页信息
        PageInfo<ZxSkResCategoryMaterials> p = new PageInfo<>(zxSkResCategoryMaterialsList);
        return repEntity.okList(zxSkResCategoryMaterialsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResourceMaterialsListNameJoinResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if(StrUtil.isEmpty(zxSkResCategoryMaterials.getId())){
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkResCategoryMaterials.getPage(),zxSkResCategoryMaterials.getLimit());
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResCategoryMaterialsMapper.selectZxSkResourceMaterialsListNameJoinResource(zxSkResCategoryMaterials);
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> p = new PageInfo<>(zxSkResourceMaterialsList);
        return repEntity.okList(zxSkResourceMaterialsList, p.getTotal());
    }

    //大类和明细的数据
    @Override
    public ResponseEntity getZxSkResCategoryMaterialsAll(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        zxSkResCategoryMaterials.setParentID("0002");
        // 获取数据
        List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList = zxSkResCategoryMaterialsMapper.selectByZxSkResCategoryMaterialsList(zxSkResCategoryMaterials);
        for (ZxSkResCategoryMaterials SkResCategoryMaterials : zxSkResCategoryMaterialsList) {
            SkResCategoryMaterials.setLabel(SkResCategoryMaterials.getCatName());
            SkResCategoryMaterials.setValue(SkResCategoryMaterials.getId());
            List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResCategoryMaterialsMapper.selectZxSkResourceMaterialsListNameJoinResource(SkResCategoryMaterials);
            for (ZxSkResourceMaterials SkResourceMaterials : zxSkResourceMaterialsList) {
//                SkResourceMaterials.set(SkResourceMaterials.getResCode());
                SkResourceMaterials.setValue(SkResourceMaterials.getId());
                SkResourceMaterials.setLabel(
                        SkResourceMaterials.getResCode()                //编号
                                +"-"+ SkResourceMaterials.getResName()          //名称
                                +"-"+ SkResourceMaterials.getSpec()             //规格编号
                                +"-"+ SkResourceMaterials.getUnit()             //单位
//                    +"-"+ SkResourceMaterials.getRefpriceType()   //计价方式/参考计价方式
//                    +"-"+ SkResourceMaterials.getOrgName()        //编制机构/隶属机构
                );
            }
            SkResCategoryMaterials.setZxSkResourceMaterialsList(zxSkResourceMaterialsList);
        }
        return repEntity.ok(zxSkResCategoryMaterialsList);
    }

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsAllResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkResCategoryMaterials.getPage(),zxSkResCategoryMaterials.getLimit());
        // 获取数据
        List<ZxSkResourceMaterials> zxSkResourceMaterialsList = zxSkResCategoryMaterialsMapper.getZxSkResCategoryMaterialsAllResource(zxSkResCategoryMaterials);
        // 得到分页信息
        PageInfo<ZxSkResourceMaterials> pageInfo = new PageInfo<>(zxSkResourceMaterialsList);
        return repEntity.okList(zxSkResourceMaterialsList,pageInfo.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResCategoryMaterialsListResourceNotRevolve(ZxSkResCategoryMaterials zxSkResCategoryMaterials) {
        if (zxSkResCategoryMaterials == null) {
            zxSkResCategoryMaterials = new ZxSkResCategoryMaterials();
        }
        if(StrUtil.isEmpty(zxSkResCategoryMaterials.getParentOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        zxSkResCategoryMaterials.setParentID("0002");
        // 分页查询
        PageHelper.startPage(zxSkResCategoryMaterials.getPage(),zxSkResCategoryMaterials.getLimit());
        // 获取数据
        List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList = zxSkResCategoryMaterialsMapper.getZxSkResCategoryMaterialsListResourceNotRevolve(zxSkResCategoryMaterials);
        // 得到分页信息
        PageInfo<ZxSkResCategoryMaterials> p = new PageInfo<>(zxSkResCategoryMaterialsList);
        return repEntity.okList(zxSkResCategoryMaterialsList, p.getTotal());
    }
}
