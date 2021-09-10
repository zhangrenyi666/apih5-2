package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrColCategoryMapper;
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import com.apih5.service.ZxCrColCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCrColCategoryService")
public class ZxCrColCategoryServiceImpl implements ZxCrColCategoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrColCategoryMapper zxCrColCategoryMapper;

    @Override
    public ResponseEntity getZxCrColCategoryListByCondition(ZxCrColCategory zxCrColCategory) {
        if (zxCrColCategory == null) {
            zxCrColCategory = new ZxCrColCategory();
        }
        // 分页查询
        PageHelper.startPage(zxCrColCategory.getPage(),zxCrColCategory.getLimit());
        // 获取数据
        List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryList(zxCrColCategory);
        for (ZxCrColCategory zxCrColCategory1 : zxCrColCategoryList) {
            ZxCrColCategory zxCrColCategory2 = new ZxCrColCategory();
            zxCrColCategory2.setParentID(zxCrColCategory1.getId());
            List<ZxCrColCategory> zxCrColCategory3 = zxCrColCategoryMapper.selectByZxCrColCategoryList(zxCrColCategory2);
            zxCrColCategory1.setChildrenList(zxCrColCategory3);
        }
        // 得到分页信息
        PageInfo<ZxCrColCategory> p = new PageInfo<>(zxCrColCategoryList);

        return repEntity.okList(zxCrColCategoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrColCategoryDetail(ZxCrColCategory zxCrColCategory) {
        if (zxCrColCategory == null) {
            zxCrColCategory = new ZxCrColCategory();
        }
        // 获取数据
        ZxCrColCategory dbZxCrColCategory = zxCrColCategoryMapper.selectByPrimaryKey(zxCrColCategory.getId());
        // 数据存在
        if (dbZxCrColCategory != null) {
            return repEntity.ok(dbZxCrColCategory);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrColCategory(ZxCrColCategory zxCrColCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrColCategory.setId(UuidUtil.generate());
     // 上一节点的pid_all(包含上一节点id)
        if (StrUtil.isNotEmpty(zxCrColCategory.getBsid())) {
            String pidAll = zxCrColCategory.getBsid();
            zxCrColCategory.setBsid(pidAll + "," + zxCrColCategory.getId());
        } else {
//			// 新增根节点时情况
//			zxSkResCategory.setBsid(zxSkResCategory.getParentID());
        }
        zxCrColCategory.setCreateUserInfo(userKey, realName);
        int flag = zxCrColCategoryMapper.insert(zxCrColCategory);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrColCategory);
        }
    }

    @Override
    public ResponseEntity updateZxCrColCategory(ZxCrColCategory zxCrColCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrColCategory dbZxCrColCategory = zxCrColCategoryMapper.selectByPrimaryKey(zxCrColCategory.getId());
        if (dbZxCrColCategory != null && StrUtil.isNotEmpty(dbZxCrColCategory.getId())) {
           // 父编号
           dbZxCrColCategory.setParentID(zxCrColCategory.getParentID());
           // 单位工程代码
           dbZxCrColCategory.setCatCode(zxCrColCategory.getCatCode());
           // 单位工程
           dbZxCrColCategory.setCatName(zxCrColCategory.getCatName());
           // bsid
           dbZxCrColCategory.setBsid(zxCrColCategory.getBsid());
           // resStyle
           dbZxCrColCategory.setResStyle(zxCrColCategory.getResStyle());
           // 备注
           dbZxCrColCategory.setRemark(zxCrColCategory.getRemark());
           // 发送时间
           dbZxCrColCategory.setSendTime(zxCrColCategory.getSendTime());
           // bizType
           dbZxCrColCategory.setBizType(zxCrColCategory.getBizType());
           // 子类List
           dbZxCrColCategory.setChildrenList(zxCrColCategory.getChildrenList());
           // 共通
           dbZxCrColCategory.setModifyUserInfo(userKey, realName);
           flag = zxCrColCategoryMapper.updateByPrimaryKey(dbZxCrColCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrColCategory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrColCategory(List<ZxCrColCategory> zxCrColCategoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrColCategoryList != null && zxCrColCategoryList.size() > 0) {
           ZxCrColCategory zxCrColCategory = new ZxCrColCategory();
           zxCrColCategory.setModifyUserInfo(userKey, realName);
           flag = zxCrColCategoryMapper.batchDeleteUpdateZxCrColCategory(zxCrColCategoryList, zxCrColCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrColCategoryList);
        }
    }

	@Override
	public ResponseEntity getZxCrColCategoryTree(ZxCrColCategory zxCrColCategory) {
		if(zxCrColCategory == null) {
			zxCrColCategory = new ZxCrColCategory();
		}
		List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryList(zxCrColCategory);
		JSONArray jsonArray = listToTree(new JSONArray(zxCrColCategoryList), "id", "parentID", "catName");
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
            newJSONObject.set("resStyle", json.get("resStyle"));
            newJSONObject.set("bizType", json.get("bizType"));
            newJSONObject.set("remark", json.get("remark"));
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
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

}
