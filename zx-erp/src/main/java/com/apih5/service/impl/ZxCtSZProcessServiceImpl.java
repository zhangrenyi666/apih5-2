package com.apih5.service.impl;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrProcessMapper;
import com.apih5.mybatis.dao.ZxCtSZProcessMapper;
import com.apih5.mybatis.pojo.ZxCtContrProcess;
import com.apih5.mybatis.pojo.ZxCtSZProcess;
import com.apih5.service.ZxCtSZProcessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCtSZProcessService")
public class ZxCtSZProcessServiceImpl implements ZxCtSZProcessService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxCtSZProcessMapper zxCtSZProcessMapper;
	@Autowired(required = true)
	private ZxCtContrProcessMapper zxCtContrProcessMapper;

	@Override
	public ResponseEntity getZxCtSZProcessListByCondition(ZxCtSZProcess zxCtSZProcess) {
		if (zxCtSZProcess == null) {
			zxCtSZProcess = new ZxCtSZProcess();
		}
		// 分页查询
		PageHelper.startPage(zxCtSZProcess.getPage(), zxCtSZProcess.getLimit());
		// 获取数据
		List<ZxCtSZProcess> zxCtSZProcessList = zxCtSZProcessMapper.selectByZxCtSZProcessList(zxCtSZProcess);
		// 得到分页信息
		PageInfo<ZxCtSZProcess> p = new PageInfo<>(zxCtSZProcessList);

		return repEntity.okList(zxCtSZProcessList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtSZProcessDetails(ZxCtSZProcess zxCtSZProcess) {
		if (zxCtSZProcess == null) {
			zxCtSZProcess = new ZxCtSZProcess();
		}
		// 获取数据
		ZxCtSZProcess dbZxCtSZProcess = zxCtSZProcessMapper.selectByPrimaryKey(zxCtSZProcess.getId());
		// 数据存在
		if (dbZxCtSZProcess != null) {
			return repEntity.ok(dbZxCtSZProcess);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtSZProcess(ZxCtSZProcess zxCtSZProcess) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		zxCtSZProcess.setId(UuidUtil.generate());
		// 上一节点的pid_all(包含上一节点id)
		if (StrUtil.isNotEmpty(zxCtSZProcess.getTreeNode())) {
			String pidAll = zxCtSZProcess.getTreeNode();
			zxCtSZProcess.setTreeNode(pidAll + "," + zxCtSZProcess.getId());
			//问题票4560
			String[] treeNode = pidAll.split(",");
			if(treeNode != null &&treeNode.length >0) {
				ZxCtSZProcess process = zxCtSZProcessMapper.selectByPrimaryKey(treeNode[treeNode.length-1]);
				if(process != null && StrUtil.isNotEmpty(process.getId())) {
					process.setIsLeaf("0");
					process.setModifyUserInfo(userKey, realName);
					flag = zxCtSZProcessMapper.updateByPrimaryKey(process);
				}
			}
		} else {
// 			// 新增根节点时情况
// 			zxCtSZProcess.setTreeNode(zxCtSZProcess.getParentID());
		}
		zxCtSZProcess.setIsLeaf("1");
		zxCtSZProcess.setCreateUserInfo(userKey, realName);
		 flag = zxCtSZProcessMapper.insert(zxCtSZProcess);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtSZProcess);
		}
	}

	@Override
	public ResponseEntity updateZxCtSZProcess(ZxCtSZProcess zxCtSZProcess) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtSZProcess dbzxCtSZProcess = zxCtSZProcessMapper.selectByPrimaryKey(zxCtSZProcess.getId());
		if (dbzxCtSZProcess != null && StrUtil.isNotEmpty(dbzxCtSZProcess.getId())) {
//           // parentid
//           dbzxCtSZProcess.setParentID(zxCtSZProcess.getParentID());
//           // treenode
//           dbzxCtSZProcess.setTreeNode(zxCtSZProcess.getTreeNode());
//           // oldTreeNode
//           dbzxCtSZProcess.setOldTreeNode(zxCtSZProcess.getOldTreeNode());
			// 编码
			dbzxCtSZProcess.setProcessNo(zxCtSZProcess.getProcessNo());
			// 标准工序名称
			dbzxCtSZProcess.setProcessName(zxCtSZProcess.getProcessName());
			// 单位
			dbzxCtSZProcess.setProcessUnit(zxCtSZProcess.getProcessUnit());
			// 施工内容
			dbzxCtSZProcess.setContent(zxCtSZProcess.getContent());
			// 计价规则
			dbzxCtSZProcess.setPriceType(zxCtSZProcess.getPriceType());
			// 备注
			dbzxCtSZProcess.setRemark(zxCtSZProcess.getRemark());
			// 是否禁用
			dbzxCtSZProcess.setDeleted(zxCtSZProcess.getDeleted());
			// 是否局数据
			dbzxCtSZProcess.setIsGroup(zxCtSZProcess.getIsGroup());
			//
			dbzxCtSZProcess.setEditUserID(zxCtSZProcess.getEditUserID());
			//
			dbzxCtSZProcess.setEditUserName(zxCtSZProcess.getEditUserName());
			//
			dbzxCtSZProcess.setComID(zxCtSZProcess.getComID());
			//
			dbzxCtSZProcess.setComName(zxCtSZProcess.getComName());
			// 编制时间
			dbzxCtSZProcess.setEditTime(zxCtSZProcess.getEditTime());
			// 工序库类型
			dbzxCtSZProcess.setBaseType(zxCtSZProcess.getBaseType());
			// 是否工序
			dbzxCtSZProcess.setIsProcess(zxCtSZProcess.getIsProcess());
			// 编码
			dbzxCtSZProcess.setOldProcessNo(zxCtSZProcess.getOldProcessNo());
			//
			dbzxCtSZProcess.setIsCustom(zxCtSZProcess.getIsCustom());
			// 共通
			dbzxCtSZProcess.setModifyUserInfo(userKey, realName);
			flag = zxCtSZProcessMapper.updateByPrimaryKey(dbzxCtSZProcess);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCtSZProcess);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtSZProcess(List<ZxCtSZProcess> zxCtSZProcessList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtSZProcessList != null && zxCtSZProcessList.size() > 0) {
			for (ZxCtSZProcess zxCtSZProcess : zxCtSZProcessList) {
				ZxCtSZProcess category = new ZxCtSZProcess();
				category.setParentID(zxCtSZProcess.getId());
				List<ZxCtSZProcess> categories = zxCtSZProcessMapper.selectByZxCtSZProcessList(category);
				if (categories != null && categories.size() > 0) {
					return repEntity.layerMessage("no", "请先删除子节点");
				}
			}
			ZxCtSZProcess zxCtSZProcess = new ZxCtSZProcess();
			zxCtSZProcess.setModifyUserInfo(userKey, realName);
			flag = zxCtSZProcessMapper.batchDeleteUpdateZxCtSZProcess(zxCtSZProcessList, zxCtSZProcess);
		
			if (StrUtil.isNotEmpty(zxCtSZProcessList.get(0).getTreeNode())) {
				String pidAll = zxCtSZProcessList.get(0).getTreeNode();
				//问题票4560
				String[] treeNode = pidAll.split(",");
				if(treeNode != null && treeNode.length >0) {
					ZxCtSZProcess process = zxCtSZProcessMapper.selectByPrimaryKey(treeNode[treeNode.length-2]);
					if(process != null && StrUtil.isNotEmpty(process.getId())) {

						ZxCtSZProcess record = new ZxCtSZProcess();
						record.setParentID(process.getId());
						List<ZxCtSZProcess> recordList = zxCtSZProcessMapper.selectByZxCtSZProcessList(record);
						if(recordList != null && recordList.size() >0) {

						}else {
							process.setIsLeaf("1");
							process.setModifyUserInfo(userKey, realName);
							flag = zxCtSZProcessMapper.updateByPrimaryKey(process);
						}
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCtSZProcessList);
		}
	}

	@Override
	public ResponseEntity getZxCtSZProcessTree(ZxCtSZProcess zxCtSZProcess) {
		if (zxCtSZProcess == null) {
			zxCtSZProcess = new ZxCtSZProcess();
		}
		List<ZxCtSZProcess> baseCodeList = zxCtSZProcessMapper.selectByZxCtSZProcessList(zxCtSZProcess);
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
			newJSONObject.put("oldTreeNode", json.get("oldTreeNode"));
			newJSONObject.put("content", json.get("content"));
			newJSONObject.put("deleted", json.get("deleted"));
			newJSONObject.put("oldProcessNo", json.get("oldProcessNo"));
			newJSONObject.put("baseType", json.get("baseType"));
			newJSONObject.put("isCustom", json.get("isCustom"));
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
	public ResponseEntity getZxCtSZProcessItemList(ZxCtSZProcess zxCtSZProcess) {
		if (zxCtSZProcess == null) {
			zxCtSZProcess = new ZxCtSZProcess();
		}
		List<ZxCtSZProcess> baseCodeList = zxCtSZProcessMapper.getZxCtSZProcessItemList(zxCtSZProcess);
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
			newJSONObject.set(id, json.get(id));
			newJSONObject.set(parentID, json.get(parentID));
			newJSONObject.set(processName, json.get(processName));
			newJSONObject.set("treeNode", json.get("treeNode"));
			newJSONObject.set("processUnit", json.get("processUnit"));
			newJSONObject.set("remark", json.get("remark"));
			newJSONObject.set("isLeaf", json.get("isLeaf"));
			newJSONObject.set("oldTreeNode", json.get("oldTreeNode"));
			newJSONObject.set("isProcess", json.get("isProcess"));
			newJSONObject.set("priceType", json.get("priceType"));
			newJSONObject.set("processNo", json.get("processNo"));
			newJSONObject.set("deleted", json.get("deleted"));
			newJSONObject.set("isGroup", json.get("isGroup"));
			newJSONObject.set("oldTreeNode", json.get("oldTreeNode"));
			newJSONObject.set("content", json.get("content"));
			newJSONObject.set("deleted", json.get("deleted"));
			newJSONObject.set("oldProcessNo", json.get("oldProcessNo"));
			newJSONObject.set("baseType", json.get("baseType"));
			newJSONObject.set("isCustom", json.get("isCustom"));
			newJSONObject.set("ctContrProcessGuajieId", json.get("ctContrProcessGuajieId"));
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
	public ResponseEntity gcsgGetZxCtSZProcessList(ZxCtSZProcess zxCtSZProcess) {
		if (zxCtSZProcess == null) {
			zxCtSZProcess = new ZxCtSZProcess();
		}
		// 根节点时获取全部数据
		if (StrUtil.equals("-1", zxCtSZProcess.getParentID())) {
			zxCtSZProcess.setTreeNode("");
		}
		// 参数清空
		zxCtSZProcess.setParentID("");
		// 分页查询
		PageHelper.startPage(zxCtSZProcess.getPage(), zxCtSZProcess.getLimit());
		// 获取数据
		List<ZxCtSZProcess> zxCtSZProcessList = Lists.newArrayList();
		if (StrUtil.equals("contr", zxCtSZProcess.getBaseType())) {
			ZxCtContrProcess contrProcess = new ZxCtContrProcess();
			// contrProcess.setParentID(zxCtSZProcess.getParentID());
			contrProcess.setTreeNode(zxCtSZProcess.getTreeNode());
			contrProcess.setApplyAlterWorksId(zxCtSZProcess.getApplyAlterWorksId());
			contrProcess.setCtContrApplyId(zxCtSZProcess.getCtContrApplyId());
			contrProcess.setCcWorksId(zxCtSZProcess.getCcWorksId());
			contrProcess.setCtContractId(zxCtSZProcess.getCtContractId());
			List<ZxCtContrProcess> zxCtContrProcessList = zxCtContrProcessMapper
					.gcsgGetZxCtContrProcessList(contrProcess);
			if (zxCtContrProcessList.size() > 0) {
				Iterator<ZxCtContrProcess> it = zxCtContrProcessList.iterator();
				while (it.hasNext()) {
					ZxCtContrProcess dbContrProcess = it.next();
					ZxCtSZProcess zxCtSZProcess1 = new ZxCtSZProcess();
					BeanUtil.copyProperties(dbContrProcess, zxCtSZProcess1);
					zxCtSZProcessList.add(zxCtSZProcess1);
				}
			}
		} else {
			zxCtSZProcessList = zxCtSZProcessMapper.gcsgGetZxCtSZProcessList(zxCtSZProcess);
		}
		// 得到分页信息
		PageInfo<ZxCtSZProcess> p = new PageInfo<>(zxCtSZProcessList);
		JSONArray jsonArray = listToTreeItem(new JSONArray(zxCtSZProcessList), "id", "parentID", "processName");
		return repEntity.okList(jsonArray, p.getTotal());
	}

	@Override
	public ResponseEntity gcsgGetZxCtSZProcessSelect(ZxCtSZProcess zxCtSZProcess) {
		// 分包合同标准工序库、铁路分包工序库、市政标准工序库、轨道分包工序库、房建分包工序库
		List<ZxCtSZProcess> selectList = zxCtSZProcessMapper.gcsgGetZxCtSZProcessSelect(new ZxCtSZProcess());
		JSONArray jsonArray = new JSONArray();
		if (selectList.size() > 0) {
			// jsonArray = ConvertUtil.listToTree(new JSONArray(selectList), "id",
			// "parentID", "processName", "baseType");
			jsonArray = listToTreeItem(new JSONArray(selectList), "id", "parentID", "processName");
		}
		return repEntity.okList(jsonArray, jsonArray.size());
	}
}
