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
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleChangeOpinionMapper;
import com.apih5.mybatis.dao.ZjTzLifeCycleMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzLifeCycle;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;
import com.apih5.service.ZjTzLifeCycleChangeOpinionService;
import cn.hutool.core.util.StrUtil;

@Service("zjTzLifeCycleChangeOpinionService")
public class ZjTzLifeCycleChangeOpinionServiceImpl implements ZjTzLifeCycleChangeOpinionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzLifeCycleChangeOpinionMapper zjTzLifeCycleChangeOpinionMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleMapper zjTzLifeCycleMapper;
    
    @Autowired(required = true)
    private ZjTzLifeCycleChangeMapper zjTzLifeCycleChangeMapper;
    

    @Override
    public ResponseEntity getZjTzLifeCycleChangeOpinionListByCondition(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        if (zjTzLifeCycleChangeOpinion == null) {
            zjTzLifeCycleChangeOpinion = new ZjTzLifeCycleChangeOpinion();
        }
        if(StrUtil.isEmpty(zjTzLifeCycleChangeOpinion.getLifeCycleChangeId())) {
        	return repEntity.okList(null, 0);
        }
        String count = "0";
        
        List<ZjTzLifeCycleChangeOpinion> zjTzLifeCycleOpinionItemList = new ArrayList<>();
        zjTzLifeCycleChangeOpinion.setGroupByFlagReviewNumber("分组啊");
    	List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyGroupByList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(zjTzLifeCycleChangeOpinion);
    	for (ZjTzLifeCycleChangeOpinion termReply : zjTzPppTermReplyGroupByList) {
    		ZjTzLifeCycleChangeOpinion PppTermReply = new ZjTzLifeCycleChangeOpinion();
    		PppTermReply.setLifeCycleChangeId(zjTzLifeCycleChangeOpinion.getLifeCycleChangeId());
    		PppTermReply.setReviewNumber(termReply.getReviewNumber());
    		List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(PppTermReply);
    		if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
    			for (ZjTzLifeCycleChangeOpinion zjTzPppTermReply2 : zjTzPppTermReplyList) {
    				zjTzPppTermReply2.setCount(count);
    			}
    			zjTzPppTermReplyList.get(0).setCount(zjTzPppTermReplyList.size()+"");
    			zjTzLifeCycleOpinionItemList.addAll(zjTzPppTermReplyList);
    		}
    	}
        for (ZjTzLifeCycleChangeOpinion opinion : zjTzLifeCycleOpinionItemList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(opinion.getLifeCycleChangeOpinionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	opinion.setZjTzFileOpinionList(zjTzFileList);
		}
        

        return repEntity.ok(zjTzLifeCycleOpinionItemList);
    }

    @Override
    public ResponseEntity getZjTzLifeCycleChangeOpinionDetails(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        if (zjTzLifeCycleChangeOpinion == null) {
            zjTzLifeCycleChangeOpinion = new ZjTzLifeCycleChangeOpinion();
        }
        // 获取数据
        ZjTzLifeCycleChangeOpinion dbZjTzLifeCycleChangeOpinion = zjTzLifeCycleChangeOpinionMapper.selectByPrimaryKey(zjTzLifeCycleChangeOpinion.getLifeCycleChangeOpinionId());
        // 数据存在
        if (dbZjTzLifeCycleChangeOpinion != null) {
            return repEntity.ok(dbZjTzLifeCycleChangeOpinion);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzLifeCycleChangeOpinion(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzLifeCycleChangeOpinion.setLifeCycleChangeOpinionId(UuidUtil.generate());
        zjTzLifeCycleChangeOpinion.setCreateUserInfo(userKey, realName);
        int flag = zjTzLifeCycleChangeOpinionMapper.insert(zjTzLifeCycleChangeOpinion);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzLifeCycleChangeOpinion);
        }
    }

    @Override
    public ResponseEntity updateZjTzLifeCycleChangeOpinion(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzLifeCycleChangeOpinion dbzjTzLifeCycleChangeOpinion = zjTzLifeCycleChangeOpinionMapper.selectByPrimaryKey(zjTzLifeCycleChangeOpinion.getLifeCycleChangeOpinionId());
        if (dbzjTzLifeCycleChangeOpinion != null && StrUtil.isNotEmpty(dbzjTzLifeCycleChangeOpinion.getLifeCycleChangeOpinionId())) {
           // 全生命周期策划变更id
           dbzjTzLifeCycleChangeOpinion.setLifeCycleChangeId(zjTzLifeCycleChangeOpinion.getLifeCycleChangeId());
           // 评审次数
           dbzjTzLifeCycleChangeOpinion.setReviewNumber(zjTzLifeCycleChangeOpinion.getReviewNumber());
           // 评审人key
           dbzjTzLifeCycleChangeOpinion.setReviewerKey(zjTzLifeCycleChangeOpinion.getReviewerKey());
           // 评审人name
           dbzjTzLifeCycleChangeOpinion.setReviewer(zjTzLifeCycleChangeOpinion.getReviewer());
           // 评审意见
           dbzjTzLifeCycleChangeOpinion.setOpinion(zjTzLifeCycleChangeOpinion.getOpinion());
           // 评审发起时间
           dbzjTzLifeCycleChangeOpinion.setReviewStartTime(zjTzLifeCycleChangeOpinion.getReviewStartTime());
           // 评审结束时间
           dbzjTzLifeCycleChangeOpinion.setReviewEndTime(zjTzLifeCycleChangeOpinion.getReviewEndTime());
           // 共通
           dbzjTzLifeCycleChangeOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleChangeOpinionMapper.updateByPrimaryKey(dbzjTzLifeCycleChangeOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzLifeCycleChangeOpinion);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChangeOpinion(List<ZjTzLifeCycleChangeOpinion> zjTzLifeCycleChangeOpinionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzLifeCycleChangeOpinionList != null && zjTzLifeCycleChangeOpinionList.size() > 0) {
           ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion = new ZjTzLifeCycleChangeOpinion();
           zjTzLifeCycleChangeOpinion.setModifyUserInfo(userKey, realName);
           flag = zjTzLifeCycleChangeOpinionMapper.batchDeleteUpdateZjTzLifeCycleChangeOpinion(zjTzLifeCycleChangeOpinionList, zjTzLifeCycleChangeOpinion);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzLifeCycleChangeOpinionList);
        }
    }

	@Override
	public ResponseEntity getZjTzLifeCycleChangeOpinionAndProIdList(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion) {
		if (zjTzLifeCycleChangeOpinion == null) {
			zjTzLifeCycleChangeOpinion = new ZjTzLifeCycleChangeOpinion();
		}
		String count = "0";
		List<ZjTzLifeCycleChangeOpinion> zjTzLifeCycleOpinionItemList = new ArrayList<>();
		if(StrUtil.isEmpty(zjTzLifeCycleChangeOpinion.getLifeCycleId())) {
			return repEntity.okList(null, 0);
		}
		ZjTzLifeCycle zjTzLifeCycle = zjTzLifeCycleMapper.selectByPrimaryKey(zjTzLifeCycleChangeOpinion.getLifeCycleId());
		if(zjTzLifeCycle != null && StrUtil.isNotEmpty(zjTzLifeCycleChangeOpinion.getLifeCycleId())) {
			ZjTzLifeCycleChange cycleChange = new ZjTzLifeCycleChange();
			cycleChange.setProjectId(zjTzLifeCycle.getProjectId());
			List<ZjTzLifeCycleChange> cycleChangeList = zjTzLifeCycleChangeMapper.selectByZjTzLifeCycleChangeList(cycleChange);
			if(cycleChangeList != null && cycleChangeList.size() >0) {
				zjTzLifeCycleChangeOpinion.setLifeCycleChangeId(cycleChangeList.get(0).getLifeCycleChangeId());
				zjTzLifeCycleChangeOpinion.setGroupByFlagReviewNumber("分组啊");
				List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyGroupByList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(zjTzLifeCycleChangeOpinion);
				for (ZjTzLifeCycleChangeOpinion termReply : zjTzPppTermReplyGroupByList) {
					ZjTzLifeCycleChangeOpinion PppTermReply = new ZjTzLifeCycleChangeOpinion();
					PppTermReply.setLifeCycleChangeId(zjTzLifeCycleChangeOpinion.getLifeCycleChangeId());
					PppTermReply.setReviewNumber(termReply.getReviewNumber());
					List<ZjTzLifeCycleChangeOpinion> zjTzPppTermReplyList = zjTzLifeCycleChangeOpinionMapper.selectByZjTzLifeCycleChangeOpinionList(PppTermReply);
					if(zjTzPppTermReplyList != null && zjTzPppTermReplyList.size() >0) {
						for (ZjTzLifeCycleChangeOpinion zjTzPppTermReply2 : zjTzPppTermReplyList) {
							zjTzPppTermReply2.setCount(count);
						}
						zjTzPppTermReplyList.get(0).setCount(zjTzPppTermReplyList.size()+"");
						zjTzLifeCycleOpinionItemList.addAll(zjTzPppTermReplyList);
					}
				}
			}
		}
		for (ZjTzLifeCycleChangeOpinion opinion : zjTzLifeCycleOpinionItemList) {
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherId(opinion.getLifeCycleChangeOpinionId());
			List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			opinion.setZjTzFileOpinionList(zjTzFileList);
		}
		return repEntity.ok(zjTzLifeCycleOpinionItemList);
	}
}
