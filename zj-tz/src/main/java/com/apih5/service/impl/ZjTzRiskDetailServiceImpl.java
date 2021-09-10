package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzRiskDetailMapper;
import com.apih5.mybatis.dao.ZjTzRiskMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzRisk;
import com.apih5.mybatis.pojo.ZjTzRiskDetail;
import com.apih5.service.ZjTzRiskDetailService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzRiskDetailService")
public class ZjTzRiskDetailServiceImpl implements ZjTzRiskDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRiskDetailMapper zjTzRiskDetailMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzRiskMapper zjTzRiskMapper;

    @Override
    public ResponseEntity getZjTzRiskDetailListByCondition(ZjTzRiskDetail zjTzRiskDetail) {
        if (zjTzRiskDetail == null) {
            zjTzRiskDetail = new ZjTzRiskDetail();
        }
        List<ZjTzRiskDetail> returnList = new ArrayList<>();
        if(StrUtil.isEmpty(zjTzRiskDetail.getRiskId())) {
        	return repEntity.okList(null, 0);
        }
        int mainNumber = 1;
        String mainNumberStr = "一";
        zjTzRiskDetail.setGroupByFlagTypeId("分组啊");
        // 获取数据
        List<ZjTzRiskDetail> zjTzRiskDetailList = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(zjTzRiskDetail);
        for (ZjTzRiskDetail mainDetail : zjTzRiskDetailList) {
        	if(mainNumber == 1) {
        		mainNumberStr = "一";
        	}else if(mainNumber == 2) {
        		mainNumberStr = "二";
        	}else if(mainNumber == 3) {
        		mainNumberStr = "三";
        	}else if(mainNumber == 4) {
        		mainNumberStr = "四";
        	}else if(mainNumber == 5) {
        		mainNumberStr = "五";
        	}else if(mainNumber == 6) {
        		mainNumberStr = "六";
        	}else if(mainNumber == 7) {
        		mainNumberStr = "七";
        	}else if(mainNumber == 8) {
        		mainNumberStr = "八";
        	} 
        	
        	ZjTzRiskDetail detail = new ZjTzRiskDetail();
        	detail.setRiskId(zjTzRiskDetail.getRiskId());
        	detail.setTypeId(mainDetail.getTypeId());
        	List<ZjTzRiskDetail> detailList = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(detail);
        	int subNumber = 1;
        	for (ZjTzRiskDetail subDetail : detailList) {
        		//附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
            	zjTzFileSelect.setOtherId(subDetail.getRiskDetailId());
            	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            	subDetail.setZjTzFileList(zjTzFileList);
//        		1==风险管理详情里存在风险的数据累加
//        		2== 1-3
//        		3==实际解除时间不为空
//        		4==实际解除时间为空，且预计解除时间小于当前时间，例：预计解除时间：2020/6/27 当前时间：2020/6/29
            	if(StrUtil.isNotEmpty(subDetail.getExistRiskFlag()) && StrUtil.equals(subDetail.getExistRiskFlag(), "1")) {
            		//颜色
                	if(subDetail.getActualTime() != null) {
                	}else {
                		 if(subDetail.getPlanTime() != null) {
                        	 if(subDetail.getPlanTime().compareTo(new Date()) <0) {
                        		 subDetail.setColourFlag("red"); 
                        	 }
                         }
                	}
            	}
        		subDetail.setNumber((subNumber++)+" .    "+subDetail.getRiskName());
        		subDetail.setMainFlag("sub");
			}
        	mainDetail.setChildren(detailList);
        	mainDetail.setNumber(mainNumberStr+"、"+mainDetail.getTypeName());
        	mainDetail.setRiskDetailId(mainNumberStr+"、"+mainDetail.getTypeName());
        	mainDetail.setManagLever("");
        	mainDetail.setApplicableItemType("");
        	mainDetail.setRemarks("");
        	mainDetail.setLockFlag("");
        	mainDetail.setMainFlag("main");
        	mainDetail.setColourFlag("");
        	mainDetail.setExistRiskFlag("");
        	mainDetail.setSpecificDesc("");
        	mainDetail.setSolution("");
        	mainDetail.setPersonInCharge("");
        	mainDetail.setUncompletedAnalysis("");
        	mainDetail.setResultAnalysis("");
        	mainDetail.setPlanTime(null);
        	mainDetail.setActualTime(null);
        	mainDetail.setZjTzFileList(null);
        	mainNumber ++;
		}
        ZjTzRiskDetail detail = new ZjTzRiskDetail();
        detail.setRiskId(zjTzRiskDetail.getRiskId());
        returnList.add(detail);
        returnList.get(0).setZjTzRiskDetailList(zjTzRiskDetailList);
        return repEntity.ok(returnList);
    }

    @Override
    public ResponseEntity getZjTzRiskDetailDetails(ZjTzRiskDetail zjTzRiskDetail) {
        if (zjTzRiskDetail == null) {
            zjTzRiskDetail = new ZjTzRiskDetail();
        }
        // 获取数据
        ZjTzRiskDetail dbZjTzRiskDetail = zjTzRiskDetailMapper.selectByPrimaryKey(zjTzRiskDetail.getRiskDetailId());
        // 数据存在
        if (dbZjTzRiskDetail != null) {
            return repEntity.ok(dbZjTzRiskDetail);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRiskDetail(ZjTzRiskDetail zjTzRiskDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
     // 所属风险类别
        if (StrUtil.isNotEmpty(zjTzRiskDetail.getTypeId())) {
        	if(StrUtil.equals(zjTzRiskDetail.getTypeId(), "8")) {
        		String openBankName = baseCodeService.getBaseCodeItemName("suoSuFengXianLeiBie", zjTzRiskDetail.getTypeId());
        		zjTzRiskDetail.setTypeName(openBankName);
        		
        	}else {
        		return repEntity.layerMessage("no", "只可以添加其他风险类型，请重新添加！");
        	}
        }
        zjTzRiskDetail.setRiskDetailId(UuidUtil.generate());
        zjTzRiskDetail.setRiskId(zjTzRiskDetail.getRiskId());
        zjTzRiskDetail.setLockFlag("0");
        zjTzRiskDetail.setCreateUserInfo(userKey, realName);
        int flag = zjTzRiskDetailMapper.insert(zjTzRiskDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRiskDetail);
        }
    }

    @Override
    public ResponseEntity updateZjTzRiskDetail(ZjTzRiskDetail zjTzRiskDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRiskDetail dbzjTzRiskDetail = zjTzRiskDetailMapper.selectByPrimaryKey(zjTzRiskDetail.getRiskDetailId());
        if (dbzjTzRiskDetail != null && StrUtil.isNotEmpty(dbzjTzRiskDetail.getRiskDetailId())) {
           // 是否存在风险
           dbzjTzRiskDetail.setExistRiskFlag(zjTzRiskDetail.getExistRiskFlag());
           // 具体内容描述
           dbzjTzRiskDetail.setSpecificDesc(zjTzRiskDetail.getSpecificDesc());
           // 预计解除时间
           dbzjTzRiskDetail.setPlanTime(zjTzRiskDetail.getPlanTime());
           // 实际解除时间
           dbzjTzRiskDetail.setActualTime(zjTzRiskDetail.getActualTime());
           // 是否锁定
           dbzjTzRiskDetail.setLockFlag(zjTzRiskDetail.getLockFlag());
           //解决措施
           dbzjTzRiskDetail.setSolution(zjTzRiskDetail.getSolution());
           //责任人
           dbzjTzRiskDetail.setPersonInCharge(zjTzRiskDetail.getPersonInCharge());
           // 共通
           dbzjTzRiskDetail.setModifyUserInfo(userKey, realName);
           //未完成原因分析
           dbzjTzRiskDetail.setUncompletedAnalysis(zjTzRiskDetail.getUncompletedAnalysis());
           //成果分析
           dbzjTzRiskDetail.setResultAnalysis(zjTzRiskDetail.getResultAnalysis());
           flag = zjTzRiskDetailMapper.updateByPrimaryKey(dbzjTzRiskDetail);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRiskDetail.getRiskDetailId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzRiskDetail.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzRiskDetail.getRiskDetailId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskDetailList != null && zjTzRiskDetailList.size() > 0) {
           ZjTzRiskDetail zjTzRiskDetail = new ZjTzRiskDetail();
           zjTzRiskDetail.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskDetailMapper.batchDeleteUpdateZjTzRiskDetail(zjTzRiskDetailList, zjTzRiskDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRiskDetailList);
        }
    }

	@Override
	public ResponseEntity batchLockUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzRiskDetailList != null && zjTzRiskDetailList.size() > 0) {
    		ZjTzRiskDetail zjTzRiskDetail = new ZjTzRiskDetail();
    		zjTzRiskDetail.setModifyUserInfo(userKey, realName);
    		flag = zjTzRiskDetailMapper.batchLockUpdateZjTzRiskDetail(zjTzRiskDetailList, zjTzRiskDetail);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzRiskDetailList);
    	}
	}

	@Override
	public ResponseEntity batchClearUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzRiskDetailList != null && zjTzRiskDetailList.size() > 0) {
    		ZjTzRiskDetail zjTzRiskDetail = new ZjTzRiskDetail();
    		zjTzRiskDetail.setModifyUserInfo(userKey, realName);
    		flag = zjTzRiskDetailMapper.batchClearUpdateZjTzRiskDetail(zjTzRiskDetailList, zjTzRiskDetail);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzRiskDetailList);
    	}
	}

	@Override
	public List<ZjTzRiskDetail> ureportZjTzRiskDetailList(ZjTzRiskDetail zjTzRiskDetail) {
		if (zjTzRiskDetail == null) {
			zjTzRiskDetail = new ZjTzRiskDetail();
		}
		List<ZjTzRiskDetail> zjTzRiskDetails = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(zjTzRiskDetail);
		if(zjTzRiskDetails != null && zjTzRiskDetails.size() >0) {
    		ZjTzRisk risk = zjTzRiskMapper.selectByPrimaryKey(zjTzRiskDetails.get(0).getRiskId());
    		if(risk != null && StrUtil.isNotEmpty(risk.getRiskId())) {
    			for (ZjTzRiskDetail zjTzRiskDetailReply2 : zjTzRiskDetails) {
    				zjTzRiskDetailReply2.setProjectNameAndSub(risk.getProjectName()+"-"+risk.getSubprojectInfoName()+"风险清单分析");
    			
    			if(zjTzRiskDetailReply2.getActualTime() != null) {
    				zjTzRiskDetailReply2.setActualTimeName(DateUtil.format(zjTzRiskDetailReply2.getActualTime(), "yyyy-MM-dd"));
    			}
    			if(zjTzRiskDetailReply2.getPlanTime() != null) {
    				zjTzRiskDetailReply2.setPlanTimeName(DateUtil.format(zjTzRiskDetailReply2.getPlanTime(), "yyyy-MM-dd"));
    			}
    			
    			
    			}
    		}
    	}
		return zjTzRiskDetails;
	}
}
