package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCcResCategoryMapper;
import com.apih5.mybatis.pojo.ZxCcResCategory;
import com.apih5.service.ZxCcResCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCcResCategoryService")
public class ZxCcResCategoryServiceImpl implements ZxCcResCategoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCcResCategoryMapper zxCcResCategoryMapper;

    @Override
    public ResponseEntity getZxCcResCategoryListByCondition(ZxCcResCategory zxCcResCategory) {
        if (zxCcResCategory == null) {
            zxCcResCategory = new ZxCcResCategory();
        }
        // 分页查询
        PageHelper.startPage(zxCcResCategory.getPage(),zxCcResCategory.getLimit());
        // 获取数据
        List<ZxCcResCategory> zxCcResCategoryList = zxCcResCategoryMapper.selectByZxCcResCategoryList(zxCcResCategory);
        for (ZxCcResCategory zxCcResCategory2 : zxCcResCategoryList) {
        	ZxCcResCategory category = new ZxCcResCategory();
        	category.setParentID(zxCcResCategory2.getId());
        	List<ZxCcResCategory> childrenList = zxCcResCategoryMapper.selectByZxCcResCategoryList(category);
        	zxCcResCategory2.setChildrenList(childrenList);
		}
        // 得到分页信息
        PageInfo<ZxCcResCategory> p = new PageInfo<>(zxCcResCategoryList);

        return repEntity.okList(zxCcResCategoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCcResCategoryDetail(ZxCcResCategory zxCcResCategory) {
        if (zxCcResCategory == null) {
            zxCcResCategory = new ZxCcResCategory();
        }
        // 获取数据
        ZxCcResCategory dbZxCcResCategory = zxCcResCategoryMapper.selectByPrimaryKey(zxCcResCategory.getId());
        // 数据存在
        if (dbZxCcResCategory != null) {
            return repEntity.ok(dbZxCcResCategory);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCcResCategory(ZxCcResCategory zxCcResCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCcResCategory.setId(UuidUtil.generate());
    	// 上一节点的pid_all(包含上一节点id)
		if (StrUtil.isNotEmpty(zxCcResCategory.getBsid())) {
			String pidAll = zxCcResCategory.getBsid();
			zxCcResCategory.setBsid(pidAll + "," + zxCcResCategory.getId());
		} else {
//			// 新增根节点时情况
//			zxCcResCategory.setBsid(zxCcResCategory.getParentID());
		}
	
        zxCcResCategory.setCreateUserInfo(userKey, realName);
        int flag = zxCcResCategoryMapper.insert(zxCcResCategory);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCcResCategory);
        }
    }

    @Override
    public ResponseEntity updateZxCcResCategory(ZxCcResCategory zxCcResCategory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCcResCategory dbZxCcResCategory = zxCcResCategoryMapper.selectByPrimaryKey(zxCcResCategory.getId());
        if (dbZxCcResCategory != null && StrUtil.isNotEmpty(dbZxCcResCategory.getId())) {
           // parentID
           dbZxCcResCategory.setParentID(zxCcResCategory.getParentID());
//           // bsid
//           dbZxCcResCategory.setBsid(zxCcResCategory.getBsid());
           // 编号
           dbZxCcResCategory.setCatCode(zxCcResCategory.getCatCode());
           // 资质名称
           dbZxCcResCategory.setCatName(zxCcResCategory.getCatName());
           // 单位
           dbZxCcResCategory.setUnit(zxCcResCategory.getUnit());
           // 规格型号
           dbZxCcResCategory.setSpec(zxCcResCategory.getSpec());
           // 单价
           dbZxCcResCategory.setPrice(zxCcResCategory.getPrice());
           // 资源类型
           dbZxCcResCategory.setResStyle(zxCcResCategory.getResStyle());
           // 业务类型
           dbZxCcResCategory.setBizType(zxCcResCategory.getBizType());
           // 备注
           dbZxCcResCategory.setRemarks(zxCcResCategory.getRemarks());
           // combProp
           dbZxCcResCategory.setCombProp(zxCcResCategory.getCombProp());
           // pp1
           dbZxCcResCategory.setPp1(zxCcResCategory.getPp1());
           // pp2
           dbZxCcResCategory.setPp2(zxCcResCategory.getPp2());
           // pp3
           dbZxCcResCategory.setPp3(zxCcResCategory.getPp3());
           // pp4
           dbZxCcResCategory.setPp4(zxCcResCategory.getPp4());
           // pp5
           dbZxCcResCategory.setPp5(zxCcResCategory.getPp5());
           // pp1
           dbZxCcResCategory.setPp6(zxCcResCategory.getPp6());
           // pp7
           dbZxCcResCategory.setPp7(zxCcResCategory.getPp7());
           // pp8
           dbZxCcResCategory.setPp8(zxCcResCategory.getPp8());
           // pp9
           dbZxCcResCategory.setPp9(zxCcResCategory.getPp9());
           // pp10
           dbZxCcResCategory.setPp10(zxCcResCategory.getPp10());
           // 是否已下达
           dbZxCcResCategory.setIsGroup(zxCcResCategory.getIsGroup());
           // orgID
           dbZxCcResCategory.setOrgID(zxCcResCategory.getOrgID());
           // 共通
           dbZxCcResCategory.setModifyUserInfo(userKey, realName);
           flag = zxCcResCategoryMapper.updateByPrimaryKey(dbZxCcResCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCcResCategory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCcResCategory(List<ZxCcResCategory> zxCcResCategoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCcResCategoryList != null && zxCcResCategoryList.size() > 0) {
        	for (ZxCcResCategory zxCcResCategory : zxCcResCategoryList) {
        		ZxCcResCategory category = new ZxCcResCategory();
        		category.setParentID(zxCcResCategory.getId());
        		List<ZxCcResCategory> categories = zxCcResCategoryMapper.selectByZxCcResCategoryList(category);
        		if(categories != null && categories.size() >0) {
        			return repEntity.layerMessage("no", "请先删除子节点");
        		}
        	}
        	ZxCcResCategory zxCcResCategory = new ZxCcResCategory();
           zxCcResCategory.setModifyUserInfo(userKey, realName);
           flag = zxCcResCategoryMapper.batchDeleteUpdateZxCcResCategory(zxCcResCategoryList, zxCcResCategory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCcResCategoryList);
        }
    }

	@Override
	public ResponseEntity getZxCcResCategoryTree(ZxCcResCategory zxCcResCategory) {
		if(zxCcResCategory == null) {
			zxCcResCategory = new ZxCcResCategory();
		}
		List<ZxCcResCategory> baseCodeList = zxCcResCategoryMapper.selectByZxCcResCategoryList(zxCcResCategory);
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
	public ResponseEntity getZxCcResCategoryItemList(ZxCcResCategory zxCcResCategory) {
		if (zxCcResCategory == null) {
			zxCcResCategory = new ZxCcResCategory();
		}
		// 分页查询
		PageHelper.startPage(zxCcResCategory.getPage(),zxCcResCategory.getLimit());
		// 获取数据
		List<ZxCcResCategory> zxCcResCategoryList = zxCcResCategoryMapper.getZxCcResCategoryItemList(zxCcResCategory);
		// 得到分页信息
		PageInfo<ZxCcResCategory> p = new PageInfo<>(zxCcResCategoryList);

		return repEntity.okList(zxCcResCategoryList, p.getTotal());
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
