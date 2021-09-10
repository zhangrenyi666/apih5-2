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
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleOpinionMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzLifeCycleOpinion;
import com.apih5.service.ZjTzLifeCycleOpinionService;
import cn.hutool.core.util.StrUtil;

@Service("zjTzLifeCycleOpinionService")
public class ZjTzLifeCycleOpinionServiceImpl implements ZjTzLifeCycleOpinionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzLifeCycleOpinionMapper zjTzLifeCycleOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzLifeCycleOpinionListByCondition(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        if (zjTzLifeCycleOpinion == null) {
            zjTzLifeCycleOpinion = new ZjTzLifeCycleOpinion();
        }
        if(StrUtil.isEmpty(zjTzLifeCycleOpinion.getLifeCycleId())) {
        	return repEntity.okList(null, 0);
        }
        String count = "0";
        List<ZjTzLifeCycleOpinion> zjTzLifeCycleOpinionItemList = new ArrayList<>();
        zjTzLifeCycleOpinion.setGroupByFlagReviewNumber("分组啊");
    	List<ZjTzLifeCycleOpinion> zjTzPppTermReplyGroupByList = zjTzLifeCycleOpinionMapper.selectByZjTzLifeCycleOpinionList(zjTzLifeCycleOpinion);
    	for (ZjTzLifeCycleOpinion termReply : zjTzPppTermReplyGroupByList) {
    		ZjTzLifeCycleOpinion PppTermReply = new ZjTzLifeCycleOpinion();
    		PppTermReply.setLifeCycleId(zjTzLifeCycleOpinion.getLifeCycleId());
    		PppTermReply.setReviewNumber(termReply.getReviewNumber());
    		List<ZjTzLifeCycleOpinion> zjTzPppTermReplyList = zjTzLifeCycleOpinionMapper.selectByZjTzLifeCycleOpinionList(PppTermReply);
    		if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
    			for (ZjTzLifeCycleOpinion zjTzPppTermReply2 : zjTzPppTermReplyList) {
    				zjTzPppTermReply2.setCount(count);
    			}
    			zjTzPppTermReplyList.get(0).setCount(zjTzPppTermReplyList.size()+"");
    			zjTzLifeCycleOpinionItemList.addAll(zjTzPppTermReplyList);
    		}
    	}
    	
    	for (ZjTzLifeCycleOpinion opinion : zjTzLifeCycleOpinionItemList) {
    		ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(opinion.getLifeCycleOpinionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	opinion.setZjTzFileOpinionList(zjTzFileList);
		}
        return repEntity.ok(zjTzLifeCycleOpinionItemList);
    }

    @Override
    public ResponseEntity getZjTzLifeCycleOpinionDetails(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        if (zjTzLifeCycleOpinion == null) {
            zjTzLifeCycleOpinion = new ZjTzLifeCycleOpinion();
        }
        // 获取数据
        ZjTzLifeCycleOpinion dbZjTzLifeCycleOpinion = zjTzLifeCycleOpinionMapper.selectByPrimaryKey(zjTzLifeCycleOpinion.getLifeCycleOpinionId());
        // 数据存在
        if (dbZjTzLifeCycleOpinion != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzLifeCycleOpinion.getLifeCycleOpinionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzLifeCycleOpinion.setZjTzFileOpinionList(zjTzFileList);
        	return repEntity.ok(dbZjTzLifeCycleOpinion);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzLifeCycleOpinion(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzLifeCycleOpinion.setLifeCycleOpinionId(UuidUtil.generate());
        zjTzLifeCycleOpinion.setCreateUserInfo(userKey, realName);
        int flag = zjTzLifeCycleOpinionMapper.insert(zjTzLifeCycleOpinion);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzLifeCycleOpinion);
        }
    }

    @Override
    public ResponseEntity updateZjTzLifeCycleOpinion(ZjTzLifeCycleOpinion zjTzLifeCycleOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzLifeCycleOpinion dbzjTzLifeCycleOpinion = zjTzLifeCycleOpinionMapper.selectByPrimaryKey(zjTzLifeCycleOpinion.getLifeCycleOpinionId());
        if (dbzjTzLifeCycleOpinion != null && StrUtil.isNotEmpty(dbzjTzLifeCycleOpinion.getLifeCycleOpinionId())) {
           // 全生命周期策划id
           dbzjTzLifeCycleOpinion.setLifeCycleId(zjTzLifeCycleOpinion.getLifeCycleId());
           // 评审次数
           dbzjTzLifeCycleOpinion.setReviewNumber(zjTzLifeCycleOpinion.getReviewNumber());
           // 评审人key
           dbzjTzLifeCycleOpinion.setReviewerKey(zjTzLifeCycleOpinion.getReviewerKey());
           // 评审人name
           dbzjTzLifeCycleOpinion.setReviewer(zjTzLifeCycleOpinion.getReviewer());
           // 评审意见
           dbzjTzLifeCycleOpinion.setOpinion(zjTzLifeCycleOpinion.getOpinion());
           // 评审发起时间
           dbzjTzLifeCycleOpinion.setReviewStartTime(zjTzLifeCycleOpinion.getReviewStartTime());
           // 评审结束时间
           dbzjTzLifeCycleOpinion.setReviewEndTime(zjTzLifeCycleOpinion.getReviewEndTime());
           // 共通
           dbzjTzLifeCycleOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleOpinionMapper.updateByPrimaryKey(dbzjTzLifeCycleOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzLifeCycleOpinion);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleOpinion(List<ZjTzLifeCycleOpinion> zjTzLifeCycleOpinionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzLifeCycleOpinionList != null && zjTzLifeCycleOpinionList.size() > 0) {
           ZjTzLifeCycleOpinion zjTzLifeCycleOpinion = new ZjTzLifeCycleOpinion();
           zjTzLifeCycleOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleOpinionMapper.batchDeleteUpdateZjTzLifeCycleOpinion(zjTzLifeCycleOpinionList, zjTzLifeCycleOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzLifeCycleOpinionList);
        }
    }
}
