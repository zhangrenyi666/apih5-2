package com.apih5.service.impl;

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
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzRiskDetailMapper;
import com.apih5.mybatis.dao.ZjTzRiskListBaseMapper;
import com.apih5.mybatis.dao.ZjTzRiskMapper;
import com.apih5.mybatis.pojo.ZjTzRisk;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzRiskDetail;
import com.apih5.mybatis.pojo.ZjTzRiskListBase;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzRiskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzRiskService")
public class ZjTzRiskServiceImpl implements ZjTzRiskService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRiskMapper zjTzRiskMapper;
    
    @Autowired(required = true)
    private ZjTzRiskListBaseMapper zjTzRiskListBaseMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzRiskDetailMapper zjTzRiskDetailMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzRiskListByCondition(ZjTzRisk zjTzRisk) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzRisk == null) {
            zjTzRisk = new ZjTzRisk();
        }
        
    	 // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzRisk.getProjectId(), true)) {
            	zjTzRisk.setProjectId("");
            	zjTzRisk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
//        	zjTzRisk.setExt1SeeFlag("??????????????????????????????????????????????????????");
            if(StrUtil.equals("all", zjTzRisk.getProjectId(), true)) {
            	zjTzRisk.setProjectId("");
            }
        }
        
        // ????????????
        PageHelper.startPage(zjTzRisk.getPage(),zjTzRisk.getLimit());
        // ????????????
        List<ZjTzRisk> zjTzRiskList = zjTzRiskMapper.selectByZjTzRiskList(zjTzRisk);
        for (ZjTzRisk zjTzRisk2 : zjTzRiskList) {
        	String colourFlag = "";
        	int num1 = 0;//??????????????????
            int num2 = 0;//?????????????????????
            int num3 = 0;//????????????????????????
            int num4 = 0;//???????????????????????????
        	ZjTzRiskDetail riskDetail = new ZjTzRiskDetail();
        	riskDetail.setRiskId(zjTzRisk2.getRiskId());
        	List<ZjTzRiskDetail> riskDetails = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(riskDetail);
        	for (ZjTzRiskDetail zjTzRiskDetail : riskDetails) {
//        		1==????????????????????????????????????????????????
//        		2== 1-3
//        		3==???????????????????????????
//        		4==????????????????????????????????????????????????????????????????????????????????????????????????2020/6/27 ???????????????2020/6/29
        		if(StrUtil.isNotEmpty(zjTzRiskDetail.getExistRiskFlag()) && StrUtil.equals(zjTzRiskDetail.getExistRiskFlag(), "1")) {
        			num1 = num1+1;
            		//??????
            		if(zjTzRiskDetail.getActualTime() != null) {
            			num3 = num3 + 1;
            		}else {
            			if(zjTzRiskDetail.getPlanTime() != null) {
                			if(zjTzRiskDetail.getPlanTime().compareTo(new Date()) <0) {
                				num4 = num4+1;
                				colourFlag = "red";
                			}
                		}
            		}
        		}
			}
        	zjTzRisk2.setColourFlag(colourFlag);
        	zjTzRisk2.setNum1(num1);
        	zjTzRisk2.setNum2(num1-num3);
        	zjTzRisk2.setNum3(num3);
        	zjTzRisk2.setNum4(num4);
		}
        // ??????????????????
        PageInfo<ZjTzRisk> p = new PageInfo<>(zjTzRiskList);
        return repEntity.okList(zjTzRiskList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRiskDetails(ZjTzRisk zjTzRisk) {
        if (zjTzRisk == null) {
            zjTzRisk = new ZjTzRisk();
        }
        // ????????????
        ZjTzRisk dbZjTzRisk = zjTzRiskMapper.selectByPrimaryKey(zjTzRisk.getRiskId());
        // ????????????
        if (dbZjTzRisk != null) {
            return repEntity.ok(dbZjTzRisk);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzRisk(ZjTzRisk zjTzRisk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //????????????id????????????id   ????????????
        ZjTzRisk risk = new ZjTzRisk();
        risk.setProjectId(zjTzRisk.getProjectId());
      	if(StrUtil.isNotEmpty(zjTzRisk.getSubprojectInfoId())) {
             risk.setSubprojectInfoId(zjTzRisk.getSubprojectInfoId());
             List<ZjTzRisk> riskList =  zjTzRiskMapper.selectByZjTzRiskList(risk);
             if(riskList != null && riskList.size() >0) {
             	return repEntity.layerMessage("no", "?????????????????????????????????????????????");
             }
      	}else {
      		//????????????????????????  = ?????????id??????
      		risk.setSubprojectInfoId("???");
      		List<ZjTzRisk> sizeControls = zjTzRiskMapper.selectByZjTzRiskList(risk);
      		if(sizeControls != null && sizeControls.size() >0) {
      			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
      		}
      		zjTzRisk.setSubprojectInfoId("???");
      	}
        
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzRisk.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzRisk.setProjectName(manage.getProjectName());
        }
        zjTzRisk.setRiskId(UuidUtil.generate());
        zjTzRisk.setReleaseId("0");
        zjTzRisk.setReleaseName("?????????");
        zjTzRisk.setCreateUserInfo(userKey, realName);
        int flag = zjTzRiskMapper.insert(zjTzRisk);
        
        
       List<ZjTzRiskListBase> baseList = zjTzRiskListBaseMapper.selectByZjTzRiskListBaseList(new ZjTzRiskListBase());
        for (ZjTzRiskListBase zjTzRiskListBase : baseList) {
        	ZjTzRiskDetail dbzjTzRiskDetail = new ZjTzRiskDetail();
        	dbzjTzRiskDetail.setRiskDetailId(UuidUtil.generate());
        	// ??????id
            dbzjTzRiskDetail.setRiskId(zjTzRisk.getRiskId());
            // ??????????????????id
            dbzjTzRiskDetail.setTypeId(zjTzRiskListBase.getTypeId());
            // ??????????????????name
            dbzjTzRiskDetail.setTypeName(zjTzRiskListBase.getTypeName());
            // ????????????
            dbzjTzRiskDetail.setRiskName(zjTzRiskListBase.getRiskName());
            // ????????????
            dbzjTzRiskDetail.setManagLever(zjTzRiskListBase.getManagLever());
            // ??????????????????
            dbzjTzRiskDetail.setApplicableItemType(zjTzRiskListBase.getApplicableItemType());
            // ??????
            dbzjTzRiskDetail.setRemarks(zjTzRiskListBase.getRemarks());
            //????????????
            dbzjTzRiskDetail.setLockFlag("0");
            dbzjTzRiskDetail.setCreateUserInfo(userKey, realName);
        	flag = zjTzRiskDetailMapper.insert(dbzjTzRiskDetail);
		}
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRisk);
        }
    }

    @Override
    public ResponseEntity updateZjTzRisk(ZjTzRisk zjTzRisk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRisk dbzjTzRisk = zjTzRiskMapper.selectByPrimaryKey(zjTzRisk.getRiskId());
        if (dbzjTzRisk != null && StrUtil.isNotEmpty(dbzjTzRisk.getRiskId())) {
//           // ??????id
//           dbzjTzRisk.setProjectId(zjTzRisk.getProjectId());
//           // ??????name
//           dbzjTzRisk.setProjectName(zjTzRisk.getProjectName());
//           // ??????????????????
//           dbzjTzRisk.setNum1(zjTzRisk.getNum1());
//           // ?????????????????????
//           dbzjTzRisk.setNum2(zjTzRisk.getNum2());
//           // ????????????????????????
//           dbzjTzRisk.setNum3(zjTzRisk.getNum3());
//           // ???????????????????????????
//           dbzjTzRisk.setNum4(zjTzRisk.getNum4());
           // ??????
           dbzjTzRisk.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskMapper.updateByPrimaryKey(dbzjTzRisk);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRisk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRisk(List<ZjTzRisk> zjTzRiskList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
        	for (ZjTzRisk zjTzRisk : zjTzRiskList) {
        		if(StrUtil.equals(zjTzRisk.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????");
        		}
        	}
        	for (ZjTzRisk zjTzRisk : zjTzRiskList) {
				
        		ZjTzRiskDetail detail = new ZjTzRiskDetail();
        		detail.setRiskId(zjTzRisk.getRiskId());
        		List<ZjTzRiskDetail> details = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(detail);
        		if(details != null && details.size() >0) {
        			for (ZjTzRiskDetail riskDetail : details) {
        				ZjTzFile file = new ZjTzFile();
        				file.setOtherId(riskDetail.getRiskDetailId());
        				List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
        				if(files != null && files.size() >0) {
        					file.setModifyUserInfo(userKey, realName);
        					zjTzFileMapper.batchDeleteUpdateZjTzFile(files, file);
        				}
					}
        			detail.setModifyUserInfo(userKey, realName);
        			zjTzRiskDetailMapper.batchDeleteUpdateZjTzRiskDetail(details, detail);
        		}
			}
           ZjTzRisk zjTzRisk = new ZjTzRisk();
           zjTzRisk.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskMapper.batchDeleteUpdateZjTzRisk(zjTzRiskList, zjTzRisk);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRiskList);
        }
    }

	@Override
	public ResponseEntity saveZjTzRiskAllDetail(ZjTzRisk zjTzRisk) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzRisk != null && StrUtil.isNotEmpty(zjTzRisk.getRiskId())) {
    		if (zjTzRisk.getZjTzRiskDetailList() != null && zjTzRisk.getZjTzRiskDetailList().size() >0) {
    			//??????????????????
    			ZjTzRiskDetail delDeduct = new ZjTzRiskDetail();
    			delDeduct.setRiskId(zjTzRisk.getRiskId());
    			List<ZjTzRiskDetail> delRelpyList = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(delDeduct);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				for (ZjTzRiskDetail zjTzRiskDetail2 : delRelpyList) {
    					// ?????????????????????
     		           ZjTzFile zjTzFileSelect = new ZjTzFile();
     		           zjTzFileSelect.setOtherId(zjTzRiskDetail2.getRiskDetailId());
     		           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
     		           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
     		               zjTzFileSelect.setModifyUserInfo(userKey, realName);
     		               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
     		           }
					}
    				delDeduct.setModifyUserInfo(userKey, realName);
    				flag = zjTzRiskDetailMapper.batchDeleteUpdateZjTzRiskDetail(delRelpyList, delDeduct);
    			}
    			for (ZjTzRiskDetail termBase : zjTzRisk.getZjTzRiskDetailList()) {
    				if(termBase.getChildren() != null && termBase.getChildren().size() >0) {
    					for (ZjTzRiskDetail addDetail : termBase.getChildren()) {
    						ZjTzRiskDetail dbzjTzRiskDetail = new ZjTzRiskDetail();
    	    				// ??????id
    	    				dbzjTzRiskDetail.setRiskDetailId(UuidUtil.generate());
    	    				// id
    	    				dbzjTzRiskDetail.setRiskId(zjTzRisk.getRiskId());
    	    				// ??????????????????id
    	    				dbzjTzRiskDetail.setTypeId(addDetail.getTypeId());
    	    				// ??????????????????name
    	    				if (StrUtil.isNotEmpty(addDetail.getTypeId())) {
    	    					String openBankName = baseCodeService.getBaseCodeItemName("suoSuFengXianLeiBie", addDetail.getTypeId());
    	    					dbzjTzRiskDetail.setTypeName(openBankName);
    	    				}
    	    				//????????????
    	    				dbzjTzRiskDetail.setRiskName(addDetail.getRiskName());
    	    				// ????????????
    	    				dbzjTzRiskDetail.setManagLever(addDetail.getManagLever());
    	    				// ??????????????????
    	    				dbzjTzRiskDetail.setApplicableItemType(addDetail.getApplicableItemType());
    	    				// ??????
    	    				dbzjTzRiskDetail.setRemarks(addDetail.getRemarks());
    	    				// ??????????????????
    	    				dbzjTzRiskDetail.setExistRiskFlag(addDetail.getExistRiskFlag());
    	    				// ??????????????????
    	    				dbzjTzRiskDetail.setSpecificDesc(addDetail.getSpecificDesc());
    	    				// ??????????????????
    	    				dbzjTzRiskDetail.setPlanTime(addDetail.getPlanTime());
    	    				// ??????????????????
    	    				dbzjTzRiskDetail.setActualTime(addDetail.getActualTime());
    	    				// ????????????
    	    				dbzjTzRiskDetail.setLockFlag(addDetail.getLockFlag());
    	    				dbzjTzRiskDetail.setAddFlag(addDetail.getAddFlag());
    	    				//????????????
    	    				dbzjTzRiskDetail.setSolution(addDetail.getSolution());
    	    				//?????????
    	    				dbzjTzRiskDetail.setPersonInCharge(addDetail.getPersonInCharge());
    	    				//?????????????????????
    	    				dbzjTzRiskDetail.setUncompletedAnalysis(addDetail.getUncompletedAnalysis());
    	    				//????????????
    	    				dbzjTzRiskDetail.setResultAnalysis(addDetail.getResultAnalysis());
    	    				// ??????list
    	    				List<ZjTzFile> zjTzFileList = addDetail.getZjTzFileList();
    	    				if(zjTzFileList != null && zjTzFileList.size()>0) {
    	    					for(ZjTzFile zjTzFile:zjTzFileList) {
    	    						zjTzFile.setUid(UuidUtil.generate());
    	    						zjTzFile.setOtherId(dbzjTzRiskDetail.getRiskDetailId());
    	    						zjTzFile.setCreateUserInfo(userKey, realName);
    	    						zjTzFileMapper.insert(zjTzFile);
    	    					}
    	    				}
    	    				// ??????
    	    				dbzjTzRiskDetail.setCreateUserInfo(userKey, realName);
    	    				flag = zjTzRiskDetailMapper.insert(dbzjTzRiskDetail);
						}
    				}
    			}
    		}
    		ZjTzRisk risk = zjTzRiskMapper.selectByPrimaryKey(zjTzRisk.getRiskId());
    		if(risk != null && StrUtil.isNotEmpty(risk.getRiskId())) {
    			risk.setModifyUserInfo(userKey, realName);
    			flag = zjTzRiskMapper.updateByPrimaryKey(risk);
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzRisk);
    	}
	}

	@Override
	public ResponseEntity batchReleaseZjTzRisk(List<ZjTzRisk> zjTzRiskList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
        	for (ZjTzRisk zjTzRules : zjTzRiskList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}
			}
        	ZjTzRisk zjTzRules = new ZjTzRisk();
        	zjTzRules.setReleaseId("1");
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzRiskMapper.batchRecallZjTzRisk(zjTzRiskList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzRisk(List<ZjTzRisk> zjTzRiskList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
        	for (ZjTzRisk zjTzRules : zjTzRiskList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        		}
        	}
        	ZjTzRisk zjTzRules = new ZjTzRisk();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzRiskMapper.batchRecallZjTzRisk(zjTzRiskList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskList);
        }
	}
}
