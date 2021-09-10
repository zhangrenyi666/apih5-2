package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseFlowPersonMapper;
import com.apih5.mybatis.dao.BaseFlowSponsorChooserMapper;
import com.apih5.mybatis.pojo.BaseFlowPerson;
import com.apih5.mybatis.pojo.BaseFlowSponsorChooser;
import com.apih5.service.BaseFlowSponsorChooserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("baseFlowSponsorChooserService")
public class BaseFlowSponsorChooserServiceImpl implements BaseFlowSponsorChooserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseFlowSponsorChooserMapper baseFlowSponsorChooserMapper;
    
    @Autowired(required = true)
    private BaseFlowPersonMapper baseFlowPersonMapper;

    @Override
    public ResponseEntity getBaseFlowSponsorChooserListByCondition(BaseFlowSponsorChooser baseFlowSponsorChooser) {
        if (baseFlowSponsorChooser == null) {
            baseFlowSponsorChooser = new BaseFlowSponsorChooser();
        }
        // 分页查询
        PageHelper.startPage(baseFlowSponsorChooser.getPage(),baseFlowSponsorChooser.getLimit());
        // 获取数据
        List<BaseFlowSponsorChooser> baseFlowSponsorChooserList = baseFlowSponsorChooserMapper.selectBySponsorChooserList(baseFlowSponsorChooser);
        for(BaseFlowSponsorChooser bean : baseFlowSponsorChooserList){
        	//被发起者
        	List<TreeNodeEntity> canNodeList = new ArrayList<>();
        	String[] strArr = bean.getChooserIds().split("U_");
        	for(int i = 1; i<strArr.length; i++){
        		String personId = strArr[i].substring(0, strArr[i].length()-1);
        		BaseFlowPerson per = new BaseFlowPerson();
        		per.setPersonId(personId);
        		List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(per);
        		TreeNodeEntity node = new TreeNodeEntity();
        		Object label = list.get(0).getPersonName();
        		node.setLabel(label);
        		node.setValue(list.get(0).getPersonId());
        		node.setValuePid(list.get(0).getPersonParentId());
        		canNodeList.add(node);
        	}
        	bean.setCandidateList(canNodeList);
        	//发起者
        	List<TreeNodeEntity> lunNodeList = new ArrayList<>();
        	String[] strArr1 = bean.getSponsorIds().split("U_");
        	for(int j = 1; j<strArr1.length; j++){
        		String personId = strArr1[j].substring(0, strArr1[j].length()-1);
        		BaseFlowPerson per = new BaseFlowPerson();
        		per.setPersonId(personId);
        		List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(per);
        		TreeNodeEntity node = new TreeNodeEntity();
        		Object label = list.get(0).getPersonName();
        		node.setLabel(label);
        		node.setValue(list.get(0).getPersonId());
        		node.setValuePid(list.get(0).getPersonParentId());
        		lunNodeList.add(node);
        	}
        	bean.setLauncherList(lunNodeList);
        }
        // 得到分页信息
        PageInfo<BaseFlowSponsorChooser> p = new PageInfo<>(baseFlowSponsorChooserList);

        return repEntity.okList(baseFlowSponsorChooserList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseFlowSponsorChooser(BaseFlowSponsorChooser baseFlowSponsorChooser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseFlowSponsorChooser.setSponsorChooserId(UuidUtil.generate());
        baseFlowSponsorChooser.setCreateUserInfo(userKey, realName);
        int flag = baseFlowSponsorChooserMapper.insert(baseFlowSponsorChooser);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseFlowSponsorChooser);
        }
    }

    @Override
    public ResponseEntity updateBaseFlowSponsorChooser(BaseFlowSponsorChooser baseFlowSponsorChooser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseFlowSponsorChooser dbbaseFlowSponsorChooser = baseFlowSponsorChooserMapper.selectByPrimaryKey(baseFlowSponsorChooser.getSponsorChooserId());
        if (dbbaseFlowSponsorChooser != null && StrUtil.isNotEmpty(dbbaseFlowSponsorChooser.getSponsorChooserId())) {
           // 共通
           dbbaseFlowSponsorChooser.setModifyUserInfo(userKey, realName);
           flag = baseFlowSponsorChooserMapper.updateByPrimaryKey(dbbaseFlowSponsorChooser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseFlowSponsorChooser);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseFlowSponsorChooser(List<BaseFlowSponsorChooser> baseFlowSponsorChooserList) {
        int flag = 0;
        if (baseFlowSponsorChooserList != null && baseFlowSponsorChooserList.size() > 0) {
           flag = baseFlowSponsorChooserMapper.batchDeleteUpdateBaseFlowSponsorChooser(baseFlowSponsorChooserList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseFlowSponsorChooserList);
        }
   }

	@Override
	public ResponseEntity addBaseFlowSponsorChooserByList(BaseFlowSponsorChooser baseFlowSponsorChooser) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        baseFlowSponsorChooser.setSponsorChooserId(UuidUtil.generate());
	        baseFlowSponsorChooser.setCreateUserInfo(userKey, realName);
	        String candidate = "";
	        String launcher = "";
	        if(baseFlowSponsorChooser.getCandidateList() != null && baseFlowSponsorChooser.getCandidateList().size()>0) {
	            for(TreeNodeEntity bean : baseFlowSponsorChooser.getCandidateList()){
	                BaseFlowPerson flowPerson = new BaseFlowPerson(); 
	                flowPerson.setPersonId(bean.getValue());
	                List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(flowPerson);
	                if(list.size()==0){
	                    flowPerson.setFlowPersonId(UuidUtil.generate());
	                    flowPerson.setCreateUserInfo(userKey, realName);
	                    flowPerson.setPersonId(bean.getValue());
	                    flowPerson.setPersonParentId(bean.getValuePid());
	                    flowPerson.setPersonName(bean.getLabel().toString());
	                    baseFlowPersonMapper.insert(flowPerson);
	                }
	                candidate = candidate+"U_"+bean.getValue()+";";
	            }
	        }
	        if(baseFlowSponsorChooser.getLauncherList() != null && baseFlowSponsorChooser.getLauncherList().size()>0) {
	            for(TreeNodeEntity bean : baseFlowSponsorChooser.getLauncherList()){
	                BaseFlowPerson flowPerson = new BaseFlowPerson(); 
	                flowPerson.setPersonId(bean.getValue());
	                List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(flowPerson);
	                if(list.size()==0){
	                    flowPerson.setFlowPersonId(UuidUtil.generate());
	                    flowPerson.setCreateUserInfo(userKey, realName);
	                    flowPerson.setPersonId(bean.getValue());
	                    flowPerson.setPersonParentId(bean.getValuePid());
	                    flowPerson.setPersonName(bean.getLabel().toString());
	                    baseFlowPersonMapper.insert(flowPerson);
	                }
	                launcher = launcher+"U_"+bean.getValue()+";";
	            }
	        }
	        baseFlowSponsorChooser.setChooserIds(candidate);
	        baseFlowSponsorChooser.setSponsorIds(launcher);
	        int flag = baseFlowSponsorChooserMapper.insert(baseFlowSponsorChooser);
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.sava", baseFlowSponsorChooser);
	        }
	}

	@Override
	public ResponseEntity updateBaseFlowSponsorChooserByList(BaseFlowSponsorChooser baseFlowSponsorChooser) {
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        String candidate = "";
	        String launcher = "";
	        int flag = 0;
	        BaseFlowSponsorChooser dbbaseFlowSponsorChooser = baseFlowSponsorChooserMapper.selectByPrimaryKey(baseFlowSponsorChooser.getSponsorChooserId());
	        dbbaseFlowSponsorChooser.setApih5FlowId(baseFlowSponsorChooser.getApih5FlowId());
	        dbbaseFlowSponsorChooser.setFlowNodeCustom(baseFlowSponsorChooser.getFlowNodeCustom());
	        dbbaseFlowSponsorChooser.setStartNodeId(baseFlowSponsorChooser.getStartNodeId());
	        dbbaseFlowSponsorChooser.setEndNodeId(baseFlowSponsorChooser.getEndNodeId());
	        if (dbbaseFlowSponsorChooser != null && StrUtil.isNotEmpty(dbbaseFlowSponsorChooser.getSponsorChooserId())) {
		        for(TreeNodeEntity bean : baseFlowSponsorChooser.getCandidateList()){
		        	BaseFlowPerson flowPerson = new BaseFlowPerson(); 
		        	flowPerson.setPersonId(bean.getValue());
		        	List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(flowPerson);
		        	if(list.size()==0){
			        	flowPerson.setFlowPersonId(UuidUtil.generate());
			            flowPerson.setCreateUserInfo(userKey, realName);
			        	flowPerson.setPersonId(bean.getValue());
			        	flowPerson.setPersonParentId(bean.getValuePid());
			        	flowPerson.setPersonName(bean.getLabel().toString());
			        	baseFlowPersonMapper.insert(flowPerson);
		        	}
		        	candidate = candidate+"U_"+bean.getValue()+";";
		        }
		        for(TreeNodeEntity bean : baseFlowSponsorChooser.getLauncherList()){
		        	BaseFlowPerson flowPerson = new BaseFlowPerson(); 
		        	flowPerson.setPersonId(bean.getValue());
		        	List<BaseFlowPerson> list = baseFlowPersonMapper.selectByBaseFlowPersonList(flowPerson);
		        	if(list.size()==0){
		        	flowPerson.setFlowPersonId(UuidUtil.generate());
		            flowPerson.setCreateUserInfo(userKey, realName);
		        	flowPerson.setPersonId(bean.getValue());
		        	flowPerson.setPersonParentId(bean.getValuePid());
		        	flowPerson.setPersonName(bean.getLabel().toString());
		        	baseFlowPersonMapper.insert(flowPerson);
		        	}
		        	launcher = launcher+"U_"+bean.getValue()+";";
		        }
		        dbbaseFlowSponsorChooser.setFlowNodeCustom(baseFlowSponsorChooser.getFlowNodeCustom());
		        dbbaseFlowSponsorChooser.setChooserIds(candidate);
		        dbbaseFlowSponsorChooser.setSponsorIds(launcher);
	           // 共通
	           dbbaseFlowSponsorChooser.setModifyUserInfo(userKey, realName);
	           flag = baseFlowSponsorChooserMapper.updateByPrimaryKey(dbbaseFlowSponsorChooser);
	        }
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update",baseFlowSponsorChooser);
	        }
	}
}
