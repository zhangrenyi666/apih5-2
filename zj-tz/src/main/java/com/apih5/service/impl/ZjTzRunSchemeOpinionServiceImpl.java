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
import com.apih5.mybatis.dao.ZjTzRunSchemeOpinionMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzRunSchemeOpinion;
import com.apih5.service.ZjTzRunSchemeOpinionService;
import cn.hutool.core.util.StrUtil;

@Service("zjTzRunSchemeOpinionService")
public class ZjTzRunSchemeOpinionServiceImpl implements ZjTzRunSchemeOpinionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRunSchemeOpinionMapper zjTzRunSchemeOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzRunSchemeOpinionListByCondition(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        if (zjTzRunSchemeOpinion == null) {
            zjTzRunSchemeOpinion = new ZjTzRunSchemeOpinion();
        }
        if(StrUtil.isEmpty(zjTzRunSchemeOpinion.getRunSchemeId())) {
        	return repEntity.okList(null, 0);
        }
        String count = "0";
        List<ZjTzRunSchemeOpinion> zjTzRunSchemeOpinionItemList = new ArrayList<>();
        zjTzRunSchemeOpinion.setGroupByFlagReviewNumber("分组啊");
    	List<ZjTzRunSchemeOpinion> zjTzPppTermReplyGroupByList = zjTzRunSchemeOpinionMapper.selectByZjTzRunSchemeOpinionList(zjTzRunSchemeOpinion);
    	for (ZjTzRunSchemeOpinion termReply : zjTzPppTermReplyGroupByList) {
    		ZjTzRunSchemeOpinion PppTermReply = new ZjTzRunSchemeOpinion();
    		PppTermReply.setRunSchemeId(zjTzRunSchemeOpinion.getRunSchemeId());
    		PppTermReply.setReviewNumber(termReply.getReviewNumber());
    		List<ZjTzRunSchemeOpinion> zjTzPppTermReplyList = zjTzRunSchemeOpinionMapper.selectByZjTzRunSchemeOpinionList(PppTermReply);
    		if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
    			for (ZjTzRunSchemeOpinion zjTzPppTermReply2 : zjTzPppTermReplyList) {
    				zjTzPppTermReply2.setCount(count);
    			}
    			zjTzPppTermReplyList.get(0).setCount(zjTzPppTermReplyList.size()+"");
    			zjTzRunSchemeOpinionItemList.addAll(zjTzPppTermReplyList);
    		}
    	}
    	
    	for (ZjTzRunSchemeOpinion opinion : zjTzRunSchemeOpinionItemList) {
    		ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(opinion.getRunSchemeOpinionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	opinion.setZjTzFileOpinionList(zjTzFileList);
		}
        return repEntity.ok(zjTzRunSchemeOpinionItemList);
    }

    @Override
    public ResponseEntity getZjTzRunSchemeOpinionDetails(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        if (zjTzRunSchemeOpinion == null) {
            zjTzRunSchemeOpinion = new ZjTzRunSchemeOpinion();
        }
        // 获取数据
        ZjTzRunSchemeOpinion dbZjTzRunSchemeOpinion = zjTzRunSchemeOpinionMapper.selectByPrimaryKey(zjTzRunSchemeOpinion.getRunSchemeOpinionId());
        // 数据存在
        if (dbZjTzRunSchemeOpinion != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzRunSchemeOpinion.getRunSchemeOpinionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzRunSchemeOpinion.setZjTzFileOpinionList(zjTzFileList);
            return repEntity.ok(dbZjTzRunSchemeOpinion);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRunSchemeOpinion(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzRunSchemeOpinion.setRunSchemeOpinionId(UuidUtil.generate());
        zjTzRunSchemeOpinion.setCreateUserInfo(userKey, realName);
        int flag = zjTzRunSchemeOpinionMapper.insert(zjTzRunSchemeOpinion);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRunSchemeOpinion);
        }
    }

    @Override
    public ResponseEntity updateZjTzRunSchemeOpinion(ZjTzRunSchemeOpinion zjTzRunSchemeOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRunSchemeOpinion dbzjTzRunSchemeOpinion = zjTzRunSchemeOpinionMapper.selectByPrimaryKey(zjTzRunSchemeOpinion.getRunSchemeOpinionId());
        if (dbzjTzRunSchemeOpinion != null && StrUtil.isNotEmpty(dbzjTzRunSchemeOpinion.getRunSchemeOpinionId())) {
           // 运营方案id
           dbzjTzRunSchemeOpinion.setRunSchemeId(zjTzRunSchemeOpinion.getRunSchemeId());
           // 评审次数
           dbzjTzRunSchemeOpinion.setReviewNumber(zjTzRunSchemeOpinion.getReviewNumber());
           // 评审人key
           dbzjTzRunSchemeOpinion.setReviewerKey(zjTzRunSchemeOpinion.getReviewerKey());
           // 评审人name
           dbzjTzRunSchemeOpinion.setReviewer(zjTzRunSchemeOpinion.getReviewer());
           // 评审意见
           dbzjTzRunSchemeOpinion.setOpinion(zjTzRunSchemeOpinion.getOpinion());
           // 评审发起时间
           dbzjTzRunSchemeOpinion.setReviewStartTime(zjTzRunSchemeOpinion.getReviewStartTime());
           // 评审结束时间
           dbzjTzRunSchemeOpinion.setReviewEndTime(zjTzRunSchemeOpinion.getReviewEndTime());
           // 共通
           dbzjTzRunSchemeOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzRunSchemeOpinionMapper.updateByPrimaryKey(dbzjTzRunSchemeOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRunSchemeOpinion);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRunSchemeOpinion(List<ZjTzRunSchemeOpinion> zjTzRunSchemeOpinionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRunSchemeOpinionList != null && zjTzRunSchemeOpinionList.size() > 0) {
           ZjTzRunSchemeOpinion zjTzRunSchemeOpinion = new ZjTzRunSchemeOpinion();
           zjTzRunSchemeOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzRunSchemeOpinionMapper.batchDeleteUpdateZjTzRunSchemeOpinion(zjTzRunSchemeOpinionList, zjTzRunSchemeOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRunSchemeOpinionList);
        }
    }
}
