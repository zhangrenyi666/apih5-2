package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqResCategoryMapper;
import com.apih5.mybatis.pojo.ZxEqResCategory;
import com.apih5.service.ZxEqResCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxEqResCategoryService")
public class ZxEqResCategoryServiceImpl implements ZxEqResCategoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqResCategoryMapper zxEqResCategoryMapper;

    @Override
    public ResponseEntity getZxEqResCategoryListByCondition(ZxEqResCategory zxEqResCategory) {
        if (zxEqResCategory == null) {
            zxEqResCategory = new ZxEqResCategory();
        }
        // 分页查询
        PageHelper.startPage(zxEqResCategory.getPage(),zxEqResCategory.getLimit());
        // 获取数据
        List<ZxEqResCategory> zxEqResCategoryList = zxEqResCategoryMapper.selectByZxEqResCategoryList(zxEqResCategory);
        for (ZxEqResCategory zxEqResCategory2 : zxEqResCategoryList) {
        	ZxEqResCategory category = new ZxEqResCategory();
        	category.setParentID(zxEqResCategory2.getId());
        	List<ZxEqResCategory> childrenList = zxEqResCategoryMapper.selectByZxEqResCategoryList(category);
        	zxEqResCategory2.setChildrenList(childrenList);
		}
        // 得到分页信息
        PageInfo<ZxEqResCategory> p = new PageInfo<>(zxEqResCategoryList);

        return repEntity.okList(zxEqResCategoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqResCategoryDetails(ZxEqResCategory zxEqResCategory) {
        if (zxEqResCategory == null) {
            zxEqResCategory = new ZxEqResCategory();
        }
        // 获取数据
        ZxEqResCategory dbZxEqResCategory = zxEqResCategoryMapper.selectByPrimaryKey(zxEqResCategory.getId());
        // 数据存在
        if (dbZxEqResCategory != null) {
            return repEntity.ok(dbZxEqResCategory);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqResCategory(ZxEqResCategory zxEqResCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        zxEqResCategory.setId(UuidUtil.generate());
        
    	// 上一节点的pid_all(包含上一节点id)
		if (StrUtil.isNotEmpty(zxEqResCategory.getBsid())) {
			String pidAll = zxEqResCategory.getBsid();
			zxEqResCategory.setBsid(pidAll + "," + zxEqResCategory.getId());
		} else {
//			// 新增根节点时情况
//			zxEqResCategory.setBsid(zxEqResCategory.getParentID());
		}
	
        zxEqResCategory.setCreateUserInfo(userKey, realName);
        int flag = zxEqResCategoryMapper.insert(zxEqResCategory);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqResCategory);
        }
    }

    @Override
    public ResponseEntity updateZxEqResCategory(ZxEqResCategory zxEqResCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqResCategory dbzxEqResCategory = zxEqResCategoryMapper.selectByPrimaryKey(zxEqResCategory.getId());
        if (dbzxEqResCategory != null && StrUtil.isNotEmpty(dbzxEqResCategory.getId())) {
           // pid
           dbzxEqResCategory.setParentID(zxEqResCategory.getParentID());
//           // bsid
//           dbzxEqResCategory.setBsid(zxEqResCategory.getBsid());
           // 编码
           dbzxEqResCategory.setCatCode(zxEqResCategory.getCatCode());
           // 名称
           dbzxEqResCategory.setCatName(zxEqResCategory.getCatName());
           // 单位
           dbzxEqResCategory.setUnit(zxEqResCategory.getUnit());
           // 规格型号
           dbzxEqResCategory.setSpec(zxEqResCategory.getSpec());
           // 单价
           dbzxEqResCategory.setPrice(zxEqResCategory.getPrice());
           // 
           dbzxEqResCategory.setResStyle(zxEqResCategory.getResStyle());
           // 
           dbzxEqResCategory.setBizType(zxEqResCategory.getBizType());
           // 
           dbzxEqResCategory.setRemark(zxEqResCategory.getRemark());
           // 
           dbzxEqResCategory.setCombProp(zxEqResCategory.getCombProp());
           // 
           dbzxEqResCategory.setPp1(zxEqResCategory.getPp1());
           // 
           dbzxEqResCategory.setPp2(zxEqResCategory.getPp2());
           // 
           dbzxEqResCategory.setPp3(zxEqResCategory.getPp3());
           // 
           dbzxEqResCategory.setPp4(zxEqResCategory.getPp4());
           // 
           dbzxEqResCategory.setPp5(zxEqResCategory.getPp5());
           // 
           dbzxEqResCategory.setPp6(zxEqResCategory.getPp6());
           // 
           dbzxEqResCategory.setPp7(zxEqResCategory.getPp7());
           // 
           dbzxEqResCategory.setPp8(zxEqResCategory.getPp8());
           // 
           dbzxEqResCategory.setPp9(zxEqResCategory.getPp9());
           // 
           dbzxEqResCategory.setPp10(zxEqResCategory.getPp10());
           // 
           dbzxEqResCategory.setEditTime(zxEqResCategory.getEditTime());
           // 
           dbzxEqResCategory.setIsGroup(zxEqResCategory.getIsGroup());
           // 
           dbzxEqResCategory.setOrgID(zxEqResCategory.getOrgID());
           // 
           dbzxEqResCategory.setSendTime(zxEqResCategory.getSendTime());
           // 共通
           dbzxEqResCategory.setModifyUserInfo(userKey, realName);
           flag = zxEqResCategoryMapper.updateByPrimaryKey(dbzxEqResCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqResCategory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqResCategoryList != null && zxEqResCategoryList.size() > 0) {
        	for (ZxEqResCategory zxEqResCategory : zxEqResCategoryList) {
        		ZxEqResCategory category = new ZxEqResCategory();
        		category.setParentID(zxEqResCategory.getId());
        		List<ZxEqResCategory> categories = zxEqResCategoryMapper.selectByZxEqResCategoryList(category);
        		if(categories != null && categories.size() >0) {
        			return repEntity.layerMessage("no", "请先删除子节点");
        		}
        	}
        	ZxEqResCategory zxEqResCategory = new ZxEqResCategory();
           zxEqResCategory.setModifyUserInfo(userKey, realName);
           flag = zxEqResCategoryMapper.batchDeleteUpdateZxEqResCategory(zxEqResCategoryList, zxEqResCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqResCategoryList);
        }
    }

	@Override
	public ResponseEntity batchStartUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        if (zxEqResCategoryList != null && zxEqResCategoryList.size() > 0) {
	           ZxEqResCategory zxEqResCategory = new ZxEqResCategory();
	           zxEqResCategory.setModifyUserInfo(userKey, realName);
	           flag = zxEqResCategoryMapper.batchStartUpdateZxEqResCategory(zxEqResCategoryList, zxEqResCategory);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update"
	            		+ "",zxEqResCategoryList);
	        }
	}

	@Override
	public ResponseEntity batchStopUpdateZxEqResCategory(List<ZxEqResCategory> zxEqResCategoryList) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        if (zxEqResCategoryList != null && zxEqResCategoryList.size() > 0) {
	           ZxEqResCategory zxEqResCategory = new ZxEqResCategory();
	           zxEqResCategory.setModifyUserInfo(userKey, realName);
	           flag = zxEqResCategoryMapper.batchStopUpdateZxEqResCategory(zxEqResCategoryList, zxEqResCategory);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update",zxEqResCategoryList);
	        }
	}

	@Override
	public ResponseEntity getZxEqResCategoryTree(ZxEqResCategory zxEqResCategory) {
		if(zxEqResCategory == null) {
			zxEqResCategory = new ZxEqResCategory();
		}
		List<ZxEqResCategory> baseCodeList = zxEqResCategoryMapper.selectByZxEqResCategoryList(zxEqResCategory);
		JSONArray jsonArray = listToTree(new JSONArray(baseCodeList), "id", "parentID", "catName");
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
				newJSONObject.put(id, json.get(id));
				newJSONObject.put(parentID, json.get(parentID));
				newJSONObject.put(catName, json.get(catName));
				newJSONObject.put("bsid", json.get("bsid"));
				newJSONObject.put("catCode", json.get("catCode"));
				newJSONObject.put("catName", json.get("catName"));
				newJSONObject.put("unit", json.get("unit"));
				newJSONObject.put("spec", json.get("spec"));
				newJSONObject.put("price", json.get("price"));
				newJSONObject.put("resStyle", json.get("resStyle"));
				newJSONObject.put("resStyle", json.get("resStyle"));
				newJSONObject.put("bizType", json.get("bizType"));
				newJSONObject.put("remark", json.get("remark"));
				newJSONObject.put("combProp", json.get("combProp"));
				newJSONObject.put("pp1", json.get("pp1"));
				newJSONObject.put("pp2", json.get("pp2"));
				newJSONObject.put("pp3", json.get("pp3"));
				newJSONObject.put("pp4", json.get("pp4"));
				newJSONObject.put("pp5", json.get("pp5"));
				newJSONObject.put("pp6", json.get("pp6"));
				newJSONObject.put("pp7", json.get("pp7"));
				newJSONObject.put("pp8", json.get("pp8"));
				newJSONObject.put("pp9", json.get("pp9"));
				newJSONObject.put("pp10", json.get("pp10"));
				newJSONObject.put("editTime", json.get("editTime"));
				newJSONObject.put("isGroup", json.get("isGroup"));
				newJSONObject.put("orgID", json.get("orgID"));
				newJSONObject.put("sendTime", json.get("sendTime"));
				hash.put(json.getStr(id), newJSONObject);
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
		public ResponseEntity getZxEqResCategoryItemList(ZxEqResCategory zxEqResCategory) {
			if (zxEqResCategory == null) {
				zxEqResCategory = new ZxEqResCategory();
			}
			// 分页查询
			PageHelper.startPage(zxEqResCategory.getPage(),zxEqResCategory.getLimit());
			// 获取数据
			List<ZxEqResCategory> zxEqResCategoryList = zxEqResCategoryMapper.getZxEqResCategoryItemList(zxEqResCategory);
			// 得到分页信息
			PageInfo<ZxEqResCategory> p = new PageInfo<>(zxEqResCategoryList);

			return repEntity.okList(zxEqResCategoryList, p.getTotal());
		}

}
