package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTempTaskCommunicateMapper;
import com.apih5.mybatis.pojo.ZjLzehTempTaskCommunicate;
import com.apih5.service.ZjLzehTempTaskCommunicateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTempTaskCommunicateService")
public class ZjLzehTempTaskCommunicateServiceImpl implements ZjLzehTempTaskCommunicateService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTempTaskCommunicateMapper zjLzehTempTaskCommunicateMapper;

    @Override
    public ResponseEntity getZjLzehTempTaskCommunicateListByCondition(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        if (zjLzehTempTaskCommunicate == null) {
            zjLzehTempTaskCommunicate = new ZjLzehTempTaskCommunicate();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTempTaskCommunicate.getPage(), zjLzehTempTaskCommunicate.getLimit());
        // 获取数据
        List<ZjLzehTempTaskCommunicate> zjLzehTempTaskCommunicateList = zjLzehTempTaskCommunicateMapper.selectByZjLzehTempTaskCommunicateList(zjLzehTempTaskCommunicate);
        // 得到分页信息
        PageInfo<ZjLzehTempTaskCommunicate> p = new PageInfo<>(zjLzehTempTaskCommunicateList);

        return repEntity.okList(zjLzehTempTaskCommunicateList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTempTaskCommunicateDetail(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        if (zjLzehTempTaskCommunicate == null) {
            zjLzehTempTaskCommunicate = new ZjLzehTempTaskCommunicate();
        }
        // 获取数据
        ZjLzehTempTaskCommunicate dbZjLzehTempTaskCommunicate = zjLzehTempTaskCommunicateMapper.selectByPrimaryKey(zjLzehTempTaskCommunicate.getZjLzehTempTaskCommunicateId());
        // 数据存在
        if (dbZjLzehTempTaskCommunicate != null) {
            return repEntity.ok(dbZjLzehTempTaskCommunicate);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTempTaskCommunicate(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTempTaskCommunicate.setZjLzehTempTaskCommunicateId(UuidUtil.generate());
        zjLzehTempTaskCommunicate.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTempTaskCommunicateMapper.insert(zjLzehTempTaskCommunicate);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTempTaskCommunicate);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTempTaskCommunicate(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTempTaskCommunicate dbZjLzehTempTaskCommunicate = zjLzehTempTaskCommunicateMapper.selectByPrimaryKey(zjLzehTempTaskCommunicate.getZjLzehTempTaskCommunicateId());
        if (dbZjLzehTempTaskCommunicate != null && StrUtil.isNotEmpty(dbZjLzehTempTaskCommunicate.getZjLzehTempTaskCommunicateId())) {
            // 临时任务ID
            dbZjLzehTempTaskCommunicate.setZjLzehTempTaskManageId(zjLzehTempTaskCommunicate.getZjLzehTempTaskManageId());
            // 发送人ID
            dbZjLzehTempTaskCommunicate.setSendPersonId(zjLzehTempTaskCommunicate.getSendPersonId());
            // 接收人ID
            dbZjLzehTempTaskCommunicate.setReceivePersoId(zjLzehTempTaskCommunicate.getReceivePersoId());
            // 发送人
            dbZjLzehTempTaskCommunicate.setSendPerson(zjLzehTempTaskCommunicate.getSendPerson());
            // 接收人
            dbZjLzehTempTaskCommunicate.setReceivePerson(zjLzehTempTaskCommunicate.getReceivePerson());
            // 消息内容
            dbZjLzehTempTaskCommunicate.setMessageContent(zjLzehTempTaskCommunicate.getMessageContent());
            // 父级ID
            dbZjLzehTempTaskCommunicate.setParentId(zjLzehTempTaskCommunicate.getParentId());
            // 备注
            dbZjLzehTempTaskCommunicate.setRemarks(zjLzehTempTaskCommunicate.getRemarks());
            // 排序
            dbZjLzehTempTaskCommunicate.setSort(zjLzehTempTaskCommunicate.getSort());
            // 共通
            dbZjLzehTempTaskCommunicate.setModifyUserInfo(userKey, realName);
            flag = zjLzehTempTaskCommunicateMapper.updateByPrimaryKey(dbZjLzehTempTaskCommunicate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zjLzehTempTaskCommunicate);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTempTaskCommunicate(List<ZjLzehTempTaskCommunicate> zjLzehTempTaskCommunicateList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTempTaskCommunicateList != null && zjLzehTempTaskCommunicateList.size() > 0) {
            ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate = new ZjLzehTempTaskCommunicate();
            zjLzehTempTaskCommunicate.setModifyUserInfo(userKey, realName);
            flag = zjLzehTempTaskCommunicateMapper.batchDeleteUpdateZjLzehTempTaskCommunicate(zjLzehTempTaskCommunicateList, zjLzehTempTaskCommunicate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zjLzehTempTaskCommunicateList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询任务沟通树
     *
     * @param zjLzehTempTaskCommunicate
     * @author suncg
     */
    @Override
    public ResponseEntity getZjLzehTempTaskCommunicateTreeByCondition(ZjLzehTempTaskCommunicate zjLzehTempTaskCommunicate) {
        // 获取数据
        List<ZjLzehTempTaskCommunicate> zjLzehTempTaskCommunicateList = zjLzehTempTaskCommunicateMapper.selectByZjLzehTempTaskCommunicateList(zjLzehTempTaskCommunicate);
        JSONArray jsonArray = listToTree(new JSONArray(zjLzehTempTaskCommunicateList), "zjLzehTempTaskCommunicateId", "parentId", "sendPerson", "createTime","messageContent");
        return repEntity.ok(jsonArray);
    }


    private  JSONArray listToTree(JSONArray arr, String id, String pid, String name, String sendDate,String context) {
        String child = "children";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();

        int j;
        JSONObject json;
        JSONObject newJSONObject;
        JSONArray jsonArray;
        for(j = 0; j < arr.size(); ++j) {
            json = (JSONObject)arr.get(j);
            newJSONObject = new JSONObject();
            newJSONObject.put("value", json.get(id));
            newJSONObject.put("valuePid", json.get(pid));
            newJSONObject.put("context", json.get(context));
            newJSONObject.put("sendDate", json.get(sendDate));
            newJSONObject.put("title", json.get(name));
            newJSONObject.put("showData", new JSONArray());
            hash.put(json.getStr(id), newJSONObject);
            newArr.add(newJSONObject);
        }

        pid = "valuePid";

        for(j = 0; j < newArr.size(); ++j) {
            json = newArr.getJSONObject(j);
            newJSONObject = hash.getJSONObject(json.getStr(pid));
            if (newJSONObject != null) {
                if (newJSONObject.get(child) != null) {
                    jsonArray = newJSONObject.getJSONArray(child);
                    jsonArray.add(json);
                    newJSONObject.put(child, jsonArray);
                } else {
                    jsonArray = new JSONArray();
                    jsonArray.add(json);
                    newJSONObject.put(child, jsonArray);
                }
            } else {
                r.add(json);
            }
        }

        return r;
    }
}
