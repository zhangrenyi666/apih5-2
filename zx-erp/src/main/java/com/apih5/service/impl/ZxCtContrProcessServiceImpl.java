package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrProcessMapper;
import com.apih5.mybatis.pojo.ZxCtContrProcess;
import com.apih5.service.ZxCtContrProcessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCtContrProcessService")
public class ZxCtContrProcessServiceImpl implements ZxCtContrProcessService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCtContrProcessMapper zxCtContrProcessMapper;

	@Override
	public ResponseEntity getZxCtContrProcessListByCondition(ZxCtContrProcess zxCtContrProcess) {
		if (zxCtContrProcess == null) {
			zxCtContrProcess = new ZxCtContrProcess();
		}
		// 分页查询
		PageHelper.startPage(zxCtContrProcess.getPage(), zxCtContrProcess.getLimit());
		// 获取数据
		List<ZxCtContrProcess> zxCtContrProcessList = zxCtContrProcessMapper
				.selectByZxCtContrProcessList(zxCtContrProcess);
		// 得到分页信息
		PageInfo<ZxCtContrProcess> p = new PageInfo<>(zxCtContrProcessList);

		return repEntity.okList(zxCtContrProcessList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtContrProcessDetails(ZxCtContrProcess zxCtContrProcess) {
		if (zxCtContrProcess == null) {
			zxCtContrProcess = new ZxCtContrProcess();
		}
		// 获取数据
		ZxCtContrProcess dbZxCtContrProcess = zxCtContrProcessMapper.selectByPrimaryKey(zxCtContrProcess.getId());
		// 数据存在
		if (dbZxCtContrProcess != null) {
			return repEntity.ok(dbZxCtContrProcess);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtContrProcess(ZxCtContrProcess zxCtContrProcess) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtContrProcess.setId(UuidUtil.generate());
		// 上一节点的pid_all(包含上一节点id)
		if (StrUtil.isNotEmpty(zxCtContrProcess.getTreeNode())) {
			String pidAll = zxCtContrProcess.getTreeNode();
			zxCtContrProcess.setTreeNode(pidAll + "," + zxCtContrProcess.getId());
		} else {
//     			// 新增根节点时情况
//     			zxCtContrProcess.setTreeNode(zxCtContrProcess.getParentID());
		}
		zxCtContrProcess.setCreateUserInfo(userKey, realName);
		int flag = zxCtContrProcessMapper.insert(zxCtContrProcess);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtContrProcess);
		}
	}

	@Override
	public ResponseEntity updateZxCtContrProcess(ZxCtContrProcess zxCtContrProcess) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtContrProcess dbzxCtContrProcess = zxCtContrProcessMapper.selectByPrimaryKey(zxCtContrProcess.getId());
		if (dbzxCtContrProcess != null && StrUtil.isNotEmpty(dbzxCtContrProcess.getId())) {
//           // parentid
//           dbzxCtContrProcess.setParentID(zxCtContrProcess.getParentID());
//           // treenode
//           dbzxCtContrProcess.setTreeNode(zxCtContrProcess.getTreeNode());
//           // oldTreeNode
//           dbzxCtContrProcess.setOldTreeNode(zxCtContrProcess.getOldTreeNode());
			// 编码
			dbzxCtContrProcess.setProcessNo(zxCtContrProcess.getProcessNo());
			// 标准工序名称
			dbzxCtContrProcess.setProcessName(zxCtContrProcess.getProcessName());
			// 单位
			dbzxCtContrProcess.setProcessUnit(zxCtContrProcess.getProcessUnit());
			// 施工内容
			dbzxCtContrProcess.setContent(zxCtContrProcess.getContent());
			// 计价规则
			dbzxCtContrProcess.setPriceType(zxCtContrProcess.getPriceType());
			//
			dbzxCtContrProcess.setRemark(zxCtContrProcess.getRemark());
			// 是否禁用
			dbzxCtContrProcess.setDeleted(zxCtContrProcess.getDeleted());
			// isLeaf
			dbzxCtContrProcess.setIsLeaf(zxCtContrProcess.getIsLeaf());
			// 是否局数据
			dbzxCtContrProcess.setIsGroup(zxCtContrProcess.getIsGroup());
			//
			dbzxCtContrProcess.setEditUserID(zxCtContrProcess.getEditUserID());
			//
			dbzxCtContrProcess.setEditUserName(zxCtContrProcess.getEditUserName());
			//
			dbzxCtContrProcess.setComID(zxCtContrProcess.getComID());
			//
			dbzxCtContrProcess.setComName(zxCtContrProcess.getComName());
			// 编制时间
			dbzxCtContrProcess.setEditTime(zxCtContrProcess.getEditTime());
			// 是否工序
			dbzxCtContrProcess.setIsProcess(zxCtContrProcess.getIsProcess());
			// 共通
			dbzxCtContrProcess.setModifyUserInfo(userKey, realName);
			flag = zxCtContrProcessMapper.updateByPrimaryKey(dbzxCtContrProcess);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCtContrProcess);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtContrProcess(List<ZxCtContrProcess> zxCtContrProcessList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtContrProcessList != null && zxCtContrProcessList.size() > 0) {
			for (ZxCtContrProcess zxCtContrProcess : zxCtContrProcessList) {
				ZxCtContrProcess category = new ZxCtContrProcess();
				category.setParentID(zxCtContrProcess.getId());
				List<ZxCtContrProcess> categories = zxCtContrProcessMapper.selectByZxCtContrProcessList(category);
				if (categories != null && categories.size() > 0) {
					return repEntity.layerMessage("no", "请先删除子节点");
				}
			}
			ZxCtContrProcess zxCtContrProcess = new ZxCtContrProcess();
			zxCtContrProcess.setModifyUserInfo(userKey, realName);
			flag = zxCtContrProcessMapper.batchDeleteUpdateZxCtContrProcess(zxCtContrProcessList, zxCtContrProcess);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCtContrProcessList);
		}
	}

	@Override
	public ResponseEntity getZxCtContrProcessTree(ZxCtContrProcess zxCtContrProcess) {
		if (zxCtContrProcess == null) {
			zxCtContrProcess = new ZxCtContrProcess();
		}
		List<ZxCtContrProcess> baseCodeList = zxCtContrProcessMapper.selectByZxCtContrProcessList(zxCtContrProcess);
		JSONArray jsonArray = listToTree(new JSONArray(baseCodeList), "id", "parentID", "processName");
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
	private static JSONArray listToTree(JSONArray arr, String id, String parentID, String processName) {
		String child = "childrenList";
		JSONArray r = new JSONArray();
		JSONArray newArr = new JSONArray();
		JSONObject hash = new JSONObject();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject json = (JSONObject) arr.get(i);
			JSONObject newJSONObject = new JSONObject();
			newJSONObject.put(id, json.get(id));
			newJSONObject.put(parentID, json.get(parentID));
			newJSONObject.put(processName, json.get(processName));
			newJSONObject.put("treeNode", json.get("treeNode"));
			newJSONObject.put("processUnit", json.get("processUnit"));
			newJSONObject.put("remark", json.get("remark"));
			newJSONObject.put("isLeaf", json.get("isLeaf"));
			newJSONObject.put("oldTreeNode", json.get("oldTreeNode"));
			newJSONObject.put("isProcess", json.get("isProcess"));
			newJSONObject.put("priceType", json.get("priceType"));
			newJSONObject.put("processNo", json.get("processNo"));
			newJSONObject.put("deleted", json.get("deleted"));
			newJSONObject.put("isGroup", json.get("isGroup"));
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
	public ResponseEntity getZxCtContrProcessItemList(ZxCtContrProcess zxCtContrProcess) {
		if (zxCtContrProcess == null) {
			zxCtContrProcess = new ZxCtContrProcess();
		}
		List<ZxCtContrProcess> baseCodeList = zxCtContrProcessMapper.getZxCtContrProcessItemList(zxCtContrProcess);
		JSONArray jsonArray = listToTreeItem(new JSONArray(baseCodeList), "id", "parentID", "processName");
		return repEntity.ok(jsonArray);
	}

	private static JSONArray listToTreeItem(JSONArray arr, String id, String parentID, String processName) {
		String child = "children";
		JSONArray r = new JSONArray();
		JSONArray newArr = new JSONArray();
		JSONObject hash = new JSONObject();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject json = (JSONObject) arr.get(i);
			JSONObject newJSONObject = new JSONObject();
			newJSONObject.put(id, json.get(id));
			newJSONObject.put(parentID, json.get(parentID));
			newJSONObject.put(processName, json.get(processName));
			newJSONObject.put("treeNode", json.get("treeNode"));
			newJSONObject.put("processUnit", json.get("processUnit"));
			newJSONObject.put("remark", json.get("remark"));
			newJSONObject.put("isLeaf", json.get("isLeaf"));
			newJSONObject.put("oldTreeNode", json.get("oldTreeNode"));
			newJSONObject.put("isProcess", json.get("isProcess"));
			newJSONObject.put("priceType", json.get("priceType"));
			newJSONObject.put("processNo", json.get("processNo"));
			newJSONObject.put("deleted", json.get("deleted"));
			newJSONObject.put("isGroup", json.get("isGroup"));
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
	public ResponseEntity gcsgGetZxCtContrProcessList(ZxCtContrProcess zxCtContrProcess) {
		if (zxCtContrProcess == null) {
			zxCtContrProcess = new ZxCtContrProcess();
		}
		// 分页查询
		PageHelper.startPage(zxCtContrProcess.getPage(), zxCtContrProcess.getLimit());
		// 获取数据
		List<ZxCtContrProcess> zxCtContrProcessList = zxCtContrProcessMapper
				.gcsgGetZxCtContrProcessList(zxCtContrProcess);
		// 得到分页信息
		PageInfo<ZxCtContrProcess> p = new PageInfo<>(zxCtContrProcessList);

		return repEntity.okList(zxCtContrProcessList, p.getTotal());
	}
}
