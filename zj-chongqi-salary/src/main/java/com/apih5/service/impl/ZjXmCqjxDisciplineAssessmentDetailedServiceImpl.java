package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentDetailedMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmCqjxDisciplineAssessmentMemberMapper;
import com.apih5.mybatis.dao.ZjXmCqjxExecutiveAssistantMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;

import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxDisciplineAssessmentDetailedService")
public class ZjXmCqjxDisciplineAssessmentDetailedServiceImpl implements ZjXmCqjxDisciplineAssessmentDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentDetailedMapper zjXmCqjxDisciplineAssessmentDetailedMapper;

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMemberMapper zjXmCqjxDisciplineAssessmentMemberMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMapper zjXmCqjxDisciplineAssessmentMapper;
    
    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantMapper zjXmCqjxExecutiveAssistantMapper;
    
    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedListByCondition(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        if (zjXmCqjxDisciplineAssessmentDetailed == null) {
            zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxDisciplineAssessmentDetailed.getPage(),zjXmCqjxDisciplineAssessmentDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
        // 得到分页信息
        PageInfo<ZjXmCqjxDisciplineAssessmentDetailed> p = new PageInfo<>(zjXmCqjxDisciplineAssessmentDetailedList);

        return repEntity.okList(zjXmCqjxDisciplineAssessmentDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedDetails(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        if (zjXmCqjxDisciplineAssessmentDetailed == null) {
            zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
        }
        // 获取数据
        ZjXmCqjxDisciplineAssessmentDetailed dbZjXmCqjxDisciplineAssessmentDetailed = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentDetailed.getDisciplineDetailedId());
        // 数据存在
        if (dbZjXmCqjxDisciplineAssessmentDetailed != null) {
            return repEntity.ok(dbZjXmCqjxDisciplineAssessmentDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxDisciplineAssessmentDetailed(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedId(UuidUtil.generate());
        zjXmCqjxDisciplineAssessmentDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxDisciplineAssessmentDetailedMapper.insert(zjXmCqjxDisciplineAssessmentDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxDisciplineAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxDisciplineAssessmentDetailed(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxDisciplineAssessmentDetailed dbzjXmCqjxDisciplineAssessmentDetailed = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentDetailed.getDisciplineDetailedId());
        if (dbzjXmCqjxDisciplineAssessmentDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxDisciplineAssessmentDetailed.getDisciplineDetailedId())) {
           // 纪律性考核ID
           dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(zjXmCqjxDisciplineAssessmentDetailed.getDisciplineId());
           // 员工ID
           dbzjXmCqjxDisciplineAssessmentDetailed.setUserKey(zjXmCqjxDisciplineAssessmentDetailed.getUserKey());
           // 员工名称
           dbzjXmCqjxDisciplineAssessmentDetailed.setUserName(zjXmCqjxDisciplineAssessmentDetailed.getUserName());
           // 所在部门
           dbzjXmCqjxDisciplineAssessmentDetailed.setDepartmentName(zjXmCqjxDisciplineAssessmentDetailed.getDepartmentName());
           // 手机号
           dbzjXmCqjxDisciplineAssessmentDetailed.setMobile(zjXmCqjxDisciplineAssessmentDetailed.getMobile());
           // 评分
           dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(zjXmCqjxDisciplineAssessmentDetailed.getDisciplineDetailedScore());
           // 共通
           dbzjXmCqjxDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxDisciplineAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxDisciplineAssessmentDetailedList != null && zjXmCqjxDisciplineAssessmentDetailedList.size() > 0) {
           ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
           zjXmCqjxDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxDisciplineAssessmentDetailedMapper.batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(zjXmCqjxDisciplineAssessmentDetailedList, zjXmCqjxDisciplineAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssessmentDetailedList);
        }
    }

	@Override
	public ResponseEntity batchUpdateZjXmCqjxDisciplineAssessmentDetailed(
			List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        double taskScore = 0;//任务得分
        double quarterScore = 0;//任务得分        
   	    BigDecimal executiveScore;//明细得分
   	    BigDecimal executiveScoreSum = null;//明细得分合计
   	    BigDecimal cooperationScore = null;//协作性得分
   	    BigDecimal disciplineScore = null;//纪律性得分
   	    BigDecimal quarterScoreDem = null;//纪律性得分        
        int flag = 0;
        //当前所有人评分完成后，将每个人的纪律性得分插入到个人考核表中
        ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember = new ZjXmCqjxDisciplineAssessmentMember();
        zjXmCqjxDisciplineAssessmentMember.setOtherId(zjXmCqjxDisciplineAssessmentDetailedList.get(0).getDisciplineId());
        zjXmCqjxDisciplineAssessmentMember.setAssessmentFlag("0");
        ZjXmCqjxDisciplineAssessment assessment = zjXmCqjxDisciplineAssessmentMapper.selectByPrimaryKey(zjXmCqjxDisciplineAssessmentMember.getOtherId());
        //考核结束，计算分数
        for(ZjXmCqjxDisciplineAssessmentDetailed detailed : zjXmCqjxDisciplineAssessmentDetailedList) {
            double value = Double.valueOf(detailed.getDisciplineDetailedScore());
        	if(value > 5.0) {
        		return repEntity.layerMessage("NO", "评分不得超过5分！");
        	}        	
            ZjXmCqjxDisciplineAssessmentDetailed dbzjXmCqjxDisciplineAssessmentDetailed = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByPrimaryKey(detailed.getDisciplineDetailedId());
               dbzjXmCqjxDisciplineAssessmentDetailed.setDisciplineDetailedScore(detailed.getDisciplineDetailedScore());
               dbzjXmCqjxDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
               flag = zjXmCqjxDisciplineAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxDisciplineAssessmentDetailed);    
               if(StrUtil.equals(assessment.getDisciplineState(), "1")) {
            	   ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
           		assistant.setManagerId(assessment.getManagerId());
           		assistant.setCreateUser(detailed.getUserKey());
           		ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByManagerId(assistant);
           		if(dbZjXmCqjxExecutiveAssistant != null) {
           			dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
           			dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
                    //如果各项评分完成，则计算季度得分
                    if(StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getCooperationFlag(), "1") && StrUtil.equals(dbZjXmCqjxExecutiveAssistant.getTaskFlag(), "1")) {
                    	executiveScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getDisciplineScore());
                    	cooperationScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getCooperationScore());
                    	disciplineScore = new BigDecimal(dbZjXmCqjxExecutiveAssistant.getTaskScore());
                    	quarterScoreDem = cooperationScore.add(disciplineScore).add(executiveScore);        	
                    	quarterScore = quarterScoreDem.doubleValue();
                    	dbZjXmCqjxExecutiveAssistant.setQuarterScore(quarterScore);
                    }        
           			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
           		}            	   
            	   
               }
        }

//        	ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed = new ZjXmCqjxDisciplineAssessmentDetailed();
//        	zjXmCqjxDisciplineAssessmentDetailed.setDisciplineId(zjXmCqjxDisciplineAssessmentMember.getOtherId());
//        	List<ZjXmCqjxDisciplineAssessmentDetailed> detailedList = zjXmCqjxDisciplineAssessmentDetailedMapper.selectByZjXmCqjxDisciplineAssessmentDetailedList(zjXmCqjxDisciplineAssessmentDetailed);
//        	if(assessment != null) {
//        		ZjXmCqjxExecutiveAssistant assistant = new ZjXmCqjxExecutiveAssistant();
//            	for(ZjXmCqjxDisciplineAssessmentDetailed detailed : detailedList) {
//            		assistant.setManagerId(assessment.getManagerId());
//            		assistant.setCreateUser(detailed.getUserKey());
//            		ZjXmCqjxExecutiveAssistant dbZjXmCqjxExecutiveAssistant = zjXmCqjxExecutiveAssistantMapper.selectByManagerId(assistant);
//            		if(dbZjXmCqjxExecutiveAssistant != null) {
//            			dbZjXmCqjxExecutiveAssistant.setDisciplineFlag("1");
//            			dbZjXmCqjxExecutiveAssistant.setDisciplineScore(Double.parseDouble(detailed.getDisciplineDetailedScore()));
//            			zjXmCqjxExecutiveAssistantMapper.updateByPrimaryKeySelective(dbZjXmCqjxExecutiveAssistant);
//            		}
//            	}   
//        	}
//        }        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxDisciplineAssessmentDetailedList);
        }
	}
}
