package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzPppTermMapper;
import com.apih5.mybatis.dao.ZjTzPppTermReplyMapper;
import com.apih5.mybatis.pojo.ZjTzPppTerm;
import com.apih5.mybatis.pojo.ZjTzPppTermReply;
import com.apih5.service.ZjTzPppTermReplyService;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPppTermReplyService")
public class ZjTzPppTermReplyServiceImpl implements ZjTzPppTermReplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPppTermReplyMapper zjTzPppTermReplyMapper;
    
    @Autowired(required = true)
    private ZjTzPppTermMapper zjTzPppTermMapper;
    
    @Override
    public ResponseEntity getZjTzPppTermReplyListByCondition(ZjTzPppTermReply zjTzPppTermReply) {
        if (zjTzPppTermReply == null) {
            zjTzPppTermReply = new ZjTzPppTermReply();
        }
     
     
    	String count = "0";
        List<ZjTzPppTermReply> returnList = new ArrayList<>();
        
        List<ZjTzPppTermReply> zjTzThousandCheckBaseItemList = new ArrayList<>();
    	zjTzPppTermReply.setGroupByFlagCodePid("分组啊");
    	List<ZjTzPppTermReply> zjTzPppTermReplyGroupByList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(zjTzPppTermReply);
    	for (ZjTzPppTermReply termReply : zjTzPppTermReplyGroupByList) {
    		ZjTzPppTermReply PppTermReply = new ZjTzPppTermReply();
    		PppTermReply.setPppTermId(zjTzPppTermReply.getPppTermId());
    		PppTermReply.setCodePid(termReply.getCodePid());
    		List<ZjTzPppTermReply> zjTzPppTermReplyList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(PppTermReply);
    		if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
    			for (ZjTzPppTermReply zjTzPppTermReply2 : zjTzPppTermReplyList) {
    				zjTzPppTermReply2.setCount(count);
    			}
    			zjTzPppTermReplyList.get(0).setCount(zjTzPppTermReplyList.size()+"");
    			zjTzThousandCheckBaseItemList.addAll(zjTzPppTermReplyList);
    		}
    	}
    	
    	ZjTzPppTermReply reply = new ZjTzPppTermReply();
    	reply.setPppTermId(zjTzPppTermReply.getPppTermId());
    	returnList.add(reply);
    	returnList.get(0).setZjTzPppTermReplyList(zjTzThousandCheckBaseItemList);
        return repEntity.ok(returnList);
    }

    @Override
    public ResponseEntity getZjTzPppTermReplyDetails(ZjTzPppTermReply zjTzPppTermReply) {
        if (zjTzPppTermReply == null) {
            zjTzPppTermReply = new ZjTzPppTermReply();
        }
        // 获取数据
        ZjTzPppTermReply dbZjTzPppTermReply = zjTzPppTermReplyMapper.selectByPrimaryKey(zjTzPppTermReply.getPppTermReplyId());
        // 数据存在
        if (dbZjTzPppTermReply != null) {
            return repEntity.ok(dbZjTzPppTermReply);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPppTermReply(ZjTzPppTermReply zjTzPppTermReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());
        zjTzPppTermReply.setCreateUserInfo(userKey, realName);
        int flag = zjTzPppTermReplyMapper.insert(zjTzPppTermReply);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPppTermReply);
        }
    }

    @Override
    public ResponseEntity updateZjTzPppTermReply(ZjTzPppTermReply zjTzPppTermReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPppTermReply dbzjTzPppTermReply = zjTzPppTermReplyMapper.selectByPrimaryKey(zjTzPppTermReply.getPppTermReplyId());
        if (dbzjTzPppTermReply != null && StrUtil.isNotEmpty(dbzjTzPppTermReply.getPppTermReplyId())) {
           // ppp合同分析id
           dbzjTzPppTermReply.setPppTermId(zjTzPppTermReply.getPppTermId());
           // 合同风险条款设置copeid
           dbzjTzPppTermReply.setCodePid(zjTzPppTermReply.getCodePid());
           // 分析重点
           dbzjTzPppTermReply.setAnalysisKey(zjTzPppTermReply.getAnalysisKey());
           // 重点条款
           dbzjTzPppTermReply.setKeyTerm(zjTzPppTermReply.getKeyTerm());
           // 重点分析内容
           dbzjTzPppTermReply.setKeyAnalysisContent(zjTzPppTermReply.getKeyAnalysisContent());
           // 条款编号与内容
           dbzjTzPppTermReply.setNumberContent(zjTzPppTermReply.getNumberContent());
           // 条款分析
           dbzjTzPppTermReply.setTerm(zjTzPppTermReply.getTerm());
           // 是否存在风险
           dbzjTzPppTermReply.setRiskFlag(zjTzPppTermReply.getRiskFlag());
           // 应错措施
           dbzjTzPppTermReply.setMeasure(zjTzPppTermReply.getMeasure());
           // 是否需要二次谈判
           dbzjTzPppTermReply.setNegotiateFlag(zjTzPppTermReply.getNegotiateFlag());
           // 责任部门与负责人
           dbzjTzPppTermReply.setDeptAndLeader(zjTzPppTermReply.getDeptAndLeader());
           // 措施落实情况
           dbzjTzPppTermReply.setImplement(zjTzPppTermReply.getImplement());
           // 共通
           dbzjTzPppTermReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPppTermReplyMapper.updateByPrimaryKey(dbzjTzPppTermReply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPppTermReply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPppTermReply(List<ZjTzPppTermReply> zjTzPppTermReplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() > 0) {
           ZjTzPppTermReply zjTzPppTermReply = new ZjTzPppTermReply();
           zjTzPppTermReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPppTermReplyMapper.batchDeleteUpdateZjTzPppTermReply(zjTzPppTermReplyList, zjTzPppTermReply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPppTermReplyList);
        }
    }

    @Override
    public List<ZjTzPppTermReply> ureportZjTzPppTermReplyList(ZjTzPppTermReply zjTzPppTermReply) {
    	if (zjTzPppTermReply == null) {
    		zjTzPppTermReply = new ZjTzPppTermReply();
    	}
    	List<ZjTzPppTermReply> zjTzPppTermReplies = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(zjTzPppTermReply);
    	if(zjTzPppTermReplies != null && zjTzPppTermReplies.size() >0) {
    		ZjTzPppTerm pppTerm = zjTzPppTermMapper.selectByPrimaryKey(zjTzPppTermReplies.get(0).getPppTermId());
    		if(pppTerm != null && StrUtil.isNotEmpty(pppTerm.getPppTermId())) {
    			for (ZjTzPppTermReply zjTzPppTermReply2 : zjTzPppTermReplies) {
    				zjTzPppTermReply2.setProjectNameAndSub(pppTerm.getProjectName()+"-"+pppTerm.getSubprojectInfoName()+"PPP合同条款分析");
    			}
    		}
    	}
    	return zjTzPppTermReplies;
    }
}
