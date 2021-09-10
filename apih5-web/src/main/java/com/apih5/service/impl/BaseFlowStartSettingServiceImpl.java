package com.apih5.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseFlowCodeMapper;
import com.apih5.mybatis.dao.BaseFlowStartSettingMapper;
import com.apih5.mybatis.pojo.BaseFlowCode;
import com.apih5.mybatis.pojo.BaseFlowStartSetting;
import com.apih5.service.BaseFlowStartSettingService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("baseFlowStartSettingService")
public class BaseFlowStartSettingServiceImpl implements BaseFlowStartSettingService {

    @Autowired(required = true)
    private ResponseEntity repEntity;
	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;
    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseFlowStartSettingMapper baseFlowStartSettingMapper;
    @Autowired(required = true)
    private BaseFlowCodeMapper baseFlowCodeMapper;

    @Override
    public ResponseEntity getBaseFlowStartSettingListByCondition(BaseFlowStartSetting baseFlowStartSetting) {
        if (baseFlowStartSetting == null) {
            baseFlowStartSetting = new BaseFlowStartSetting();
        }
        // 分页查询
        PageHelper.startPage(baseFlowStartSetting.getPage(),baseFlowStartSetting.getLimit());
        // 获取数据
        List<BaseFlowStartSetting> baseFlowStartSettingList = baseFlowStartSettingMapper.selectByBaseFlowStartSettingList(baseFlowStartSetting);
        // 得到分页信息
        PageInfo<BaseFlowStartSetting> p = new PageInfo<>(baseFlowStartSettingList);

        return repEntity.okList(baseFlowStartSettingList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseFlowStartSetting(BaseFlowStartSetting baseFlowStartSetting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseFlowStartSetting.setStartSettingId(UuidUtil.generate());
        baseFlowStartSetting.setCreateUserInfo(userKey, realName);
        int flag = baseFlowStartSettingMapper.insert(baseFlowStartSetting);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseFlowStartSetting);
        }
    }

    @Override
    public ResponseEntity updateBaseFlowStartSetting(BaseFlowStartSetting baseFlowStartSetting) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseFlowStartSetting dbbaseFlowStartSetting = baseFlowStartSettingMapper.selectByPrimaryKey(baseFlowStartSetting.getStartSettingId());
        if (dbbaseFlowStartSetting != null && StrUtil.isNotEmpty(dbbaseFlowStartSetting.getStartSettingId())) {
           // 流程Id
           dbbaseFlowStartSetting.setApih5FlowId(baseFlowStartSetting.getApih5FlowId());
           // workId
           dbbaseFlowStartSetting.setApih5WorkId(baseFlowStartSetting.getApih5WorkId());
           // 流程名称
           dbbaseFlowStartSetting.setApih5FlowName(baseFlowStartSetting.getApih5FlowName());
           // 节点ID
           dbbaseFlowStartSetting.setApih5NodeId(baseFlowStartSetting.getApih5NodeId());
           // 节点名称
           dbbaseFlowStartSetting.setApih5NodeName(baseFlowStartSetting.getApih5NodeName());
           // 备注
           dbbaseFlowStartSetting.setRemarks(baseFlowStartSetting.getRemarks());
           // 共通
           dbbaseFlowStartSetting.setModifyUserInfo(userKey, realName);
           flag = baseFlowStartSettingMapper.updateByPrimaryKey(dbbaseFlowStartSetting);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseFlowStartSetting);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseFlowStartSetting(List<BaseFlowStartSetting> baseFlowStartSettingList) {
        int flag = 0;
        if (baseFlowStartSettingList != null && baseFlowStartSettingList.size() > 0) {
           flag = baseFlowStartSettingMapper.batchDeleteUpdateBaseFlowStartSetting(baseFlowStartSettingList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseFlowStartSettingList);
        }
    }

    @Override
    public ResponseEntity getBaseFlowStartSettingListByFlow(BaseFlowStartSetting baseFlowStartSetting) {
        if (baseFlowStartSetting == null) {
            baseFlowStartSetting = new BaseFlowStartSetting();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        JSONArray jsonArray = new JSONArray();
        BaseFlowCode baseFlowCode = new BaseFlowCode();
        baseFlowCode.setApih5FlowId(baseFlowStartSetting.getApih5FlowId());
        List<BaseFlowCode> baseFlowCodeList = baseFlowCodeMapper.selectByBaseFlowCodeList(baseFlowCode);
        for(BaseFlowCode dbBaseFlowCode:baseFlowCodeList) {
        	JSONObject jsonObject = new JSONObject();
    		JSONObject jsonObjectAuthors = new JSONObject();
    		
    		JSONObject jsonObjectAuthor = new JSONObject();
    		jsonObjectAuthor.put("authorId", "Author");
    		jsonObjectAuthor.put("authorName", "主办");
    		jsonObjectAuthor.put("initAuthorApih5Old", new JSONArray());
    		jsonObjectAuthor.put("initAuthorApih5SearchApi", "getSysDepartmentUserAllTreeByZj");
    		jsonObjectAuthor.put("isDefUser", "false");
    		jsonObjectAuthor.put("isFree", "true");
    		jsonObjectAuthor.put("isSelect", "true");
    		jsonObjectAuthor.put("isUser", "true");
    		jsonObjectAuthor.put("selectType", "checkbox");
    		
    		BaseFlowStartSetting baseFlowStartSettingSelect = new BaseFlowStartSetting();
    		baseFlowStartSettingSelect.setApih5FlowId(baseFlowStartSetting.getApih5FlowId());
    		baseFlowStartSettingSelect.setApih5WorkId(baseFlowStartSetting.getApih5WorkId());
    		baseFlowStartSettingSelect.setApih5NodeId(dbBaseFlowCode.getApih5NodeId());
    		List<BaseFlowStartSetting> baseFlowStartSettingList = baseFlowStartSettingMapper.selectByBaseFlowStartSettingList(baseFlowStartSettingSelect);
    		if(baseFlowStartSettingList != null && baseFlowStartSettingList.size()>0) {
    			List<TreeNodeEntity> reviewUserObjectList = Lists.newArrayList();
    			for(BaseFlowStartSetting dbBaseFlowStartSetting:baseFlowStartSettingList){
    				TreeNodeEntity treeNodeEntity = new TreeNodeEntity();
        			treeNodeEntity.setType("2");
        			treeNodeEntity.setValue(dbBaseFlowStartSetting.getValue());
        			treeNodeEntity.setLabel(dbBaseFlowStartSetting.getLabel());
        			treeNodeEntity.setValuePid("99999");
        			treeNodeEntity.setShowData("");
        			treeNodeEntity.setTitle("");
        			reviewUserObjectList.add(treeNodeEntity);
        		}
    			
        		// 节点已选择办理人 ID
        		jsonObjectAuthor.put("selectAuthorNewApih5", reviewUserObjectList);
    		} else {
    			// 节点已选择办理人 ID
        		jsonObjectAuthor.put("selectAuthorNewApih5", new JSONArray());
    		}
    		
    		// 节点初始化办理人 ID
    		String getSysDepartmentUserAllTree = publicConfig.getProperty("getSysDepartmentUserAllTree", "getSysDepartmentUserAllTree");
			String url = Apih5Properties.getWebUrl() + getSysDepartmentUserAllTree;
			String resultStr = HttpUtil.sendPostToken(url, "", token);
			JSONObject jsonObjectResult = new JSONObject(resultStr);
			TreeNodeEntity treeNode = JSONUtil.toBean(jsonObjectResult.getJSONObject("data"), TreeNodeEntity.class);
			jsonObjectAuthor.put("initAuthorApih5", treeNode);
			
    		// Author内容
    		jsonObjectAuthors.put("Author", jsonObjectAuthor);
    		
    		
    		/**------↓↓↓Reader内容  固定格式↓↓↓--------*/
    		JSONObject jsonObjectReader = new JSONObject();
    		jsonObjectReader.put("authorId", "Reader");
    		jsonObjectReader.put("authorName", "传阅");
    		jsonObjectReader.put("initAuthorApih5", new JSONObject());
    		jsonObjectReader.put("initAuthorApih5Old", new JSONArray());
    		jsonObjectReader.put("initAuthorApih5SearchApi", "getSysDepartmentUserAllTreeByZj");
    		jsonObjectReader.put("isDefUser", "false");
    		jsonObjectReader.put("isFree", "false");
    		jsonObjectReader.put("isSelect", "false");
    		jsonObjectReader.put("isUser", "false");
    		jsonObjectReader.put("selectType", "checkbox");
    		// put  Reader内容
    		jsonObjectAuthors.put("Reader", jsonObjectReader);
    		/**------↑↑↑Reader内容  固定格式↑↑↑--------*/
    		
    		/**------↓↓↓SecondAuthor内容  固定格式↓↓↓--------*/
    		JSONObject jsonObjectSecondAuthor = new JSONObject();
    		jsonObjectSecondAuthor.put("authorId", "SecondAuthor");
    		jsonObjectSecondAuthor.put("authorName", "办理人");
    		jsonObjectSecondAuthor.put("initAuthorApih5", new JSONObject());
    		jsonObjectSecondAuthor.put("initAuthorApih5Old", new JSONArray());
    		jsonObjectSecondAuthor.put("initAuthorApih5SearchApi", "getSysDepartmentUserAllTreeByZj");
    		jsonObjectSecondAuthor.put("isDefUser", "false");
    		jsonObjectSecondAuthor.put("isFree", "false");
    		jsonObjectSecondAuthor.put("isSelect", "false");
    		jsonObjectSecondAuthor.put("isUser", "false");
    		jsonObjectSecondAuthor.put("selectType", "checkbox");
    		// put  SecondAuthor内容
    		jsonObjectAuthors.put("SecondAuthor", jsonObjectSecondAuthor);
    		/**------↑↑↑SecondAuthor内容  固定格式↑↑↑--------*/
    		
    		jsonObject.put("isDone", "false");
    		jsonObject.put("isSelect", "false");
    		jsonObject.put("nodeId", dbBaseFlowCode.getApih5NodeId());
    		jsonObject.put("nodeName", dbBaseFlowCode.getApih5NodeName());
    		jsonObject.put("nodeGroup", "");
    		jsonObject.put("nodeKey", dbBaseFlowCode.getApih5NodeId());
    		jsonObject.put("nodeStatus", "");
    		jsonObject.put("selectId", dbBaseFlowCode.getApih5NodeId());
    		jsonObject.put("selectName", dbBaseFlowCode.getApih5NodeName());
    		jsonObject.put("timeLimit", "0");
    		jsonObject.put("timeLimitType", "0");
    		// authors内容
    		jsonObject.put("authors", jsonObjectAuthors);
    		jsonArray.add(jsonObject);
        }
        return repEntity.ok(jsonArray);
    }

    @Override
	public ResponseEntity saveBaseFlowStartSettingByFlow(BaseFlowStartSetting baseFlowStartSetting) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		 String userKey = TokenUtils.getUserKey(request);
		 String realName = TokenUtils.getRealName(request);
		 JSONArray nextNodesJSONArray = baseFlowStartSetting.getNextNodes();
		 if(nextNodesJSONArray != null){
			 for (Iterator<Object> iterator = nextNodesJSONArray.iterator(); iterator.hasNext();) {
				 JSONObject jsonObject = (JSONObject)iterator.next();
				 String nodeId = jsonObject.getStr("nodeId");
				 String nodeName = jsonObject.getStr("nodeName");
				 
				 JSONObject authorsJSONObject = jsonObject.getJSONObject("authors");
				 JSONObject authorJSONObject = authorsJSONObject.getJSONObject("Author");
				 JSONArray selectAuthorNewApih5JSONArray = authorJSONObject.getJSONArray("selectAuthorNewApih5");
				 if(selectAuthorNewApih5JSONArray != null){
					 for (Iterator<Object> iteratorselectAuthor = selectAuthorNewApih5JSONArray.iterator(); iteratorselectAuthor.hasNext();) {
						 JSONObject jsonObjectUser = (JSONObject)iteratorselectAuthor.next();
						 BaseFlowStartSetting dbBaseFlowStartSetting = new BaseFlowStartSetting();
						 dbBaseFlowStartSetting.setStartSettingId(UuidUtil.generate());
						 dbBaseFlowStartSetting.setApih5FlowId(baseFlowStartSetting.getApih5FlowId());
						 dbBaseFlowStartSetting.setApih5FlowName(baseFlowStartSetting.getApih5FlowName());
						 dbBaseFlowStartSetting.setApih5WorkId(baseFlowStartSetting.getApih5WorkId());
						 dbBaseFlowStartSetting.setApih5NodeId(nodeId);
						 dbBaseFlowStartSetting.setApih5NodeName(nodeName);
						 dbBaseFlowStartSetting.setLabel(jsonObjectUser.getStr("label"));
						 dbBaseFlowStartSetting.setValue(jsonObjectUser.getStr("value"));
						 dbBaseFlowStartSetting.setCreateUserInfo(userKey, realName);
						 baseFlowStartSettingMapper.insert(dbBaseFlowStartSetting);
					 }
				 }
			 }
		 }
		 return repEntity.ok("sys.data.sava", "");
	}
}
