package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.mybatis.dao.ZjLzehFileMapper;
import com.apih5.mybatis.pojo.ZjLzehFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTempTaskManageMapper;
import com.apih5.mybatis.pojo.ZjLzehTempTaskManage;
import com.apih5.service.ZjLzehTempTaskManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTempTaskManageService")
public class ZjLzehTempTaskManageServiceImpl implements ZjLzehTempTaskManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTempTaskManageMapper zjLzehTempTaskManageMapper;

    @Autowired(required = true)
    private ZjLzehFileMapper zjLzehFileMapper;

    @Override
    public ResponseEntity getZjLzehTempTaskManageListByCondition(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTempTaskManage.getPage(), zjLzehTempTaskManage.getLimit());
        // 获取数据
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage);

        for (ZjLzehTempTaskManage zjtm : zjLzehTempTaskManageList
        ) {
            List<TreeNodeEntity> list = new ArrayList<TreeNodeEntity>();
            TreeNodeEntity treeNodeEntity =new TreeNodeEntity();
            treeNodeEntity.setValue(zjtm.getImplementPersonId());
            treeNodeEntity.setLabel(zjtm.getImplementPerson());
            list.add(treeNodeEntity);
            zjtm.setLauncherList(list);
            //查询附件
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjtm.getZjLzehTempTaskManageId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjtm.setFileList(files);
        }

        // 得到分页信息
        PageInfo<ZjLzehTempTaskManage> p = new PageInfo<>(zjLzehTempTaskManageList);

        return repEntity.okList(zjLzehTempTaskManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTempTaskManageDetail(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        // 获取数据
        ZjLzehTempTaskManage dbZjLzehTempTaskManage = zjLzehTempTaskManageMapper.selectByPrimaryKey(zjLzehTempTaskManage.getZjLzehTempTaskManageId());


        // 数据存在
        if (dbZjLzehTempTaskManage != null) {

//            //构造空参数对象
            ZjLzehTempTaskManage zjLzehTempTaskManage1 =new ZjLzehTempTaskManage();
//            // 获取数据
            List<ZjLzehTempTaskManage> zjLzehTempTaskManageList1 = zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage1);


            List<TreeNodeEntity> list = new ArrayList<TreeNodeEntity>();
            TreeNodeEntity treeNodeEntity =new TreeNodeEntity();
            treeNodeEntity.setValue(dbZjLzehTempTaskManage.getImplementPersonId());
            treeNodeEntity.setLabel(dbZjLzehTempTaskManage.getImplementPerson());
            list.add(treeNodeEntity);
            dbZjLzehTempTaskManage.setLauncherList(list);
            dbZjLzehTempTaskManage.setChildren(getTreeList(zjLzehTempTaskManageList1,dbZjLzehTempTaskManage,dbZjLzehTempTaskManage.getZjLzehTempTaskManageId()));

            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(dbZjLzehTempTaskManage.getZjLzehTempTaskManageId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            dbZjLzehTempTaskManage.setFileList(files);
            return repEntity.ok(dbZjLzehTempTaskManage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTempTaskManage(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTempTaskManage.setZjLzehTempTaskManageId(UuidUtil.generate());
        zjLzehTempTaskManage.setCreateUserInfo(userKey, realName);
        if(zjLzehTempTaskManage.getLauncherList().size()!=0 ){
            zjLzehTempTaskManage.setImplementPerson(zjLzehTempTaskManage.getLauncherList().get(0).getLabel().toString());
            zjLzehTempTaskManage.setImplementPersonId(zjLzehTempTaskManage.getLauncherList().get(0).getValue());
        }
        int flag = zjLzehTempTaskManageMapper.insert(zjLzehTempTaskManage);

        if(zjLzehTempTaskManage.getFileList() != null && zjLzehTempTaskManage.getFileList().size() >0) {
            for (ZjLzehFile file : zjLzehTempTaskManage.getFileList()) {
                file.setUid(UuidUtil.generate());
                file.setOtherId(zjLzehTempTaskManage.getZjLzehTempTaskManageId());
                file.setCreateUserInfo(userKey, realName);
                zjLzehFileMapper.insert(file);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTempTaskManage);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTempTaskManage(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(zjLzehTempTaskManage.getLauncherList().size()!=0 ){
            zjLzehTempTaskManage.setImplementPerson(zjLzehTempTaskManage.getLauncherList().get(0).getLabel().toString());
            zjLzehTempTaskManage.setImplementPersonId(zjLzehTempTaskManage.getLauncherList().get(0).getValue());
        }
        ZjLzehTempTaskManage dbZjLzehTempTaskManage = zjLzehTempTaskManageMapper.selectByPrimaryKey(zjLzehTempTaskManage.getZjLzehTempTaskManageId());
        if (dbZjLzehTempTaskManage != null && StrUtil.isNotEmpty(dbZjLzehTempTaskManage.getZjLzehTempTaskManageId())) {
            // 主任务ID
            dbZjLzehTempTaskManage.setParentId(zjLzehTempTaskManage.getParentId());
            // 任务名称
            dbZjLzehTempTaskManage.setTaskName(zjLzehTempTaskManage.getTaskName());
            // 任务描述
            dbZjLzehTempTaskManage.setTaskDescribe(zjLzehTempTaskManage.getTaskDescribe());
            // 分配人（创建人）
            dbZjLzehTempTaskManage.setAllotPerson(zjLzehTempTaskManage.getAllotPerson());
            // 开始日期
            dbZjLzehTempTaskManage.setBeginDate(zjLzehTempTaskManage.getBeginDate());
            // 要求完成日期
            dbZjLzehTempTaskManage.setRequireComplateDate(zjLzehTempTaskManage.getRequireComplateDate());
            // 实际完成日期
            dbZjLzehTempTaskManage.setRealCompalteDate(zjLzehTempTaskManage.getRealCompalteDate());
            // 完成情况说明
            dbZjLzehTempTaskManage.setComplateExplain(zjLzehTempTaskManage.getComplateExplain());
            // 状态
            dbZjLzehTempTaskManage.setStatus(zjLzehTempTaskManage.getStatus());
            // 分配人ID
            dbZjLzehTempTaskManage.setAllotPersonId(zjLzehTempTaskManage.getAllotPersonId());
            // 分配对象ID
            dbZjLzehTempTaskManage.setImplementPersonId(zjLzehTempTaskManage.getImplementPersonId());
            // 分配对象
            dbZjLzehTempTaskManage.setImplementPerson(zjLzehTempTaskManage.getImplementPerson());
            // 备注
            dbZjLzehTempTaskManage.setRemarks(zjLzehTempTaskManage.getRemarks());
            // 排序
            dbZjLzehTempTaskManage.setSort(zjLzehTempTaskManage.getSort());
            // 共通
            dbZjLzehTempTaskManage.setModifyUserInfo(userKey, realName);
            if(zjLzehTempTaskManage.getLauncherList().size()!=0 ){
                zjLzehTempTaskManage.setImplementPerson(zjLzehTempTaskManage.getLauncherList().get(0).getLabel().toString());
                zjLzehTempTaskManage.setImplementPersonId(zjLzehTempTaskManage.getLauncherList().get(0).getValue());
            }

            flag = zjLzehTempTaskManageMapper.updateByPrimaryKey(dbZjLzehTempTaskManage);

            ZjLzehFile delFile = new ZjLzehFile();

            if(zjLzehTempTaskManage.getFileList() != null && zjLzehTempTaskManage.getFileList().size() >0) {
                delFile.setOtherId(zjLzehTempTaskManage.getZjLzehTempTaskManageId());
                List<ZjLzehFile> delFileList = zjLzehFileMapper.selectByZjLzehFileList(delFile);
                if(delFileList != null && delFileList.size() >0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zjLzehFileMapper.batchDeleteUpdateZjLzehFile(delFileList, delFile);
                }
                for (ZjLzehFile file : zjLzehTempTaskManage.getFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zjLzehTempTaskManage.getZjLzehTempTaskManageId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zjLzehFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zjLzehTempTaskManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTempTaskManage(List<ZjLzehTempTaskManage> zjLzehTempTaskManageList) {

        for (ZjLzehTempTaskManage task : zjLzehTempTaskManageList
        ) {
            ZjLzehTempTaskManage zjLzehTempTaskManage = new ZjLzehTempTaskManage();
            zjLzehTempTaskManage.setParentId(task.getZjLzehTempTaskManageId());
            List<ZjLzehTempTaskManage> dbList = zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage);
            if (dbList.size() > 0) {
                return repEntity.layerMessage("no", "有子任务的任务不能删除，请先删除子任务");
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTempTaskManageList != null && zjLzehTempTaskManageList.size() > 0) {
            ZjLzehTempTaskManage zjLzehTempTaskManage = new ZjLzehTempTaskManage();
            zjLzehTempTaskManage.setModifyUserInfo(userKey, realName);
            flag = zjLzehTempTaskManageMapper.batchDeleteUpdateZjLzehTempTaskManage(zjLzehTempTaskManageList, zjLzehTempTaskManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zjLzehTempTaskManageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    /**
     * 查询任务树
     *
     * @param zjLzehTempTaskManage
     * @author
     */
    @Override
    public ResponseEntity getZjLzehTempTaskManageTreeByCondition(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        // 获取数据
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage);
        JSONArray jsonArray = listToTree(new JSONArray(zjLzehTempTaskManageList), "zjLzehTempTaskManageId", "parentId", "taskName", "taskName");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZjLzehTempTaskManageListByPerson(ZjLzehTempTaskManage zjLzehTempTaskManage) {

        Comparator<ZjLzehTempTaskManage> comparator = new Comparator<ZjLzehTempTaskManage>(){
            public int compare(ZjLzehTempTaskManage s1, ZjLzehTempTaskManage s2) {
                    return s1.getSort()-s2.getSort();
            }
        };
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = zjLzehTempTaskManageMapper.selectAllByPerson(zjLzehTempTaskManage);
        // 分页查询
        PageHelper.startPage(zjLzehTempTaskManage.getPage(), zjLzehTempTaskManage.getLimit());
        // 获取数据
        List<ZjLzehTempTaskManage> childList = zjLzehTempTaskManageMapper.selectTempTaskManageListByPerson(zjLzehTempTaskManage);
        int sort = 0;
        if(childList.size()>0){
            for (ZjLzehTempTaskManage TaskManage : zjLzehTempTaskManageList
            ) {
                sort = sort + 1;
                for (ZjLzehTempTaskManage cl:childList
                ) {
                    if(cl.getImplementPerson().equals(TaskManage.getImplementPerson())){
                        cl.setSort(sort);
                        BigDecimal persent =TaskManage.getPersent()==null? new BigDecimal("0"):TaskManage.getPersent();
                        cl.setPersent(persent);
                    }

                }
            }
            Collections.sort(childList,comparator);
        }

        // 得到分页信息
        PageInfo<ZjLzehTempTaskManage> p = new PageInfo<>(childList);

        return repEntity.okList(childList, p.getTotal());
    }

    /**
     * 根节点分页查询树列表
     * @author suncg
     * @param zjLzehTempTaskManage
     * */

    @Override
    public ResponseEntity getZjLzehTempTaskManageTreeList(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        //构造空参数对象
        ZjLzehTempTaskManage zjLzehTempTaskManage1 =new ZjLzehTempTaskManage();
        // 获取数据
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList1 = new ArrayList<>();
        if(zjLzehTempTaskManage.getSort()==0){
            zjLzehTempTaskManage1.setAllotPersonId(zjLzehTempTaskManage.getAllotPersonId());
            zjLzehTempTaskManageList1= zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage1);
        }else if(zjLzehTempTaskManage.getSort()==1){
            zjLzehTempTaskManage1.setImplementPerson(zjLzehTempTaskManage.getImplementPerson());
            zjLzehTempTaskManageList1= zjLzehTempTaskManageMapper.selectByZjLzehTempTaskManageList(zjLzehTempTaskManage1);
        }

        for (ZjLzehTempTaskManage zjtm1 : zjLzehTempTaskManageList1
        ) {
            List<TreeNodeEntity> list = new ArrayList<TreeNodeEntity>();
            TreeNodeEntity treeNodeEntity =new TreeNodeEntity();
            treeNodeEntity.setValue(zjtm1.getImplementPersonId());
            treeNodeEntity.setLabel(zjtm1.getImplementPerson());
            list.add(treeNodeEntity);
            zjtm1.setLauncherList(list);

            //查询附件
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjtm1.getZjLzehTempTaskManageId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjtm1.setFileList(files);
        }

        // 分页查询
        PageHelper.startPage(zjLzehTempTaskManage.getPage(), zjLzehTempTaskManage.getLimit());
        // 获取根节点数据
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = new ArrayList<ZjLzehTempTaskManage>();
        if(zjLzehTempTaskManage.getZjLzehTempTaskManageId()==null||zjLzehTempTaskManage.getZjLzehTempTaskManageId().equals("")){
            if(zjLzehTempTaskManage.getSort()==0){
                zjLzehTempTaskManageList= zjLzehTempTaskManageMapper.selectByZjLzehTempTreeRootList(zjLzehTempTaskManage);
            }else if(zjLzehTempTaskManage.getSort()==1){
                zjLzehTempTaskManageList= zjLzehTempTaskManageMapper.selectByZjLzehTempJsRootList(zjLzehTempTaskManage);
            }

        }else {
            ZjLzehTempTaskManage dbZjLzehTempTaskManag=zjLzehTempTaskManageMapper.selectByPrimaryKey(zjLzehTempTaskManage.getZjLzehTempTaskManageId());
            zjLzehTempTaskManageList.add(dbZjLzehTempTaskManag);
        }
        for (ZjLzehTempTaskManage zjtm : zjLzehTempTaskManageList
        ) {
            List<TreeNodeEntity> list = new ArrayList<TreeNodeEntity>();
            TreeNodeEntity treeNodeEntity =new TreeNodeEntity();
            treeNodeEntity.setValue(zjtm.getImplementPersonId());
            treeNodeEntity.setLabel(zjtm.getImplementPerson());
            list.add(treeNodeEntity);
            zjtm.setLauncherList(list);
            zjtm.setChildren(getTreeList(zjLzehTempTaskManageList1,zjtm,zjtm.getZjLzehTempTaskManageId()));

            //查询附件
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjtm.getZjLzehTempTaskManageId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjtm.setFileList(files);
        }

        // 得到分页信息
        PageInfo<ZjLzehTempTaskManage> p = new PageInfo<>(zjLzehTempTaskManageList);

        return repEntity.okList(zjLzehTempTaskManageList, p.getTotal());
    }

    private List<ZjLzehTempTaskManage> getTreeList(List<ZjLzehTempTaskManage> infoList,ZjLzehTempTaskManage parent,String parentId){
        List<ZjLzehTempTaskManage> treeList = new ArrayList<ZjLzehTempTaskManage>();
        for (ZjLzehTempTaskManage zjtask:infoList
             ) {
           if (parentId.equals(zjtask.getParentId())){
               zjtask.setChildren( getTreeList(infoList,zjtask,zjtask.getZjLzehTempTaskManageId()));
               treeList.add(zjtask);
           }
        }
        return treeList;
    }

//    @Override
//    public ResponseEntity getZjLzehTempTaskManageListByPerson(ZjLzehTempTaskManage zjLzehTempTaskManage) {
//        if (zjLzehTempTaskManage == null) {
//            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
//        }
//        // 分页查询
//        PageHelper.startPage(zjLzehTempTaskManage.getPage(), zjLzehTempTaskManage.getLimit());
//        // 获取数据
//        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = zjLzehTempTaskManageMapper.selectAllPersonInfo(zjLzehTempTaskManage);
//
//        // 得到分页信息
//        PageInfo<ZjLzehTempTaskManage> p = new PageInfo<>(zjLzehTempTaskManageList);
//
//        return repEntity.okList(zjLzehTempTaskManageList, p.getTotal());
//    }

    public ResponseEntity selectZjLzehTempTaskManageListByPersonMonth(ZjLzehTempTaskManage zjLzehTempTaskManage) {
        if (zjLzehTempTaskManage == null) {
            zjLzehTempTaskManage = new ZjLzehTempTaskManage();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTempTaskManage.getPage(), zjLzehTempTaskManage.getLimit());
        // 获取数据
        List<ZjLzehTempTaskManage> zjLzehTempTaskManageList = zjLzehTempTaskManageMapper.selectZjLzehTempTaskManageListByPersonMonth(zjLzehTempTaskManage);

        for (ZjLzehTempTaskManage zjtm : zjLzehTempTaskManageList
        ) {
            //查询附件
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjtm.getZjLzehTempTaskManageId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjtm.setFileList(files);
        }
        // 得到分页信息
        PageInfo<ZjLzehTempTaskManage> p = new PageInfo<>(zjLzehTempTaskManageList);

        return repEntity.okList(zjLzehTempTaskManageList, p.getTotal());
    }

    private JSONArray listToTree(JSONArray arr, String id, String pid, String name, String realName) {
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
            newJSONObject.put("key", json.get(id));
            newJSONObject.put("valuePid", json.get(pid));
            if (StrUtil.isNotEmpty(json.getStr("type"))) {
                newJSONObject.put("type", json.get("type"));
            } else {
                newJSONObject.put("type", "1");
            }

            newJSONObject.put("label", json.get(name));
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
