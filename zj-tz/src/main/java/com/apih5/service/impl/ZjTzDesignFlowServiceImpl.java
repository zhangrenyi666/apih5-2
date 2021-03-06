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
import com.apih5.mybatis.dao.ZjTzDesignFlowMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPartManageMapper;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.pojo.ZjTzDesignChange;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;
import com.apih5.mybatis.pojo.ZjTzDesignFlow;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPartManage;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzDesignFlowService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignFlowService")
public class ZjTzDesignFlowServiceImpl implements ZjTzDesignFlowService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzDesignFlowMapper zjTzDesignFlowMapper;
    
    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzPartManageMapper zjTzPartManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    
    @Override
    public ResponseEntity getZjTzDesignFlowListByCondition(ZjTzDesignFlow zjTzDesignFlow) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzDesignFlow == null) {
            zjTzDesignFlow = new ZjTzDesignFlow();
        }
    	// ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzDesignFlow.getProjectId(), true)) {
            	zjTzDesignFlow.setProjectId("");
            	zjTzDesignFlow.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
//        	zjTzDesignFlow.setExt1SeeFlag("??????????????????????????????????????????????????????");
            // ???????????????
            if(StrUtil.equals("all", zjTzDesignFlow.getProjectId(), true)) {
            	zjTzDesignFlow.setProjectId("");
            }
        }
        
        // ????????????????????????????????????????????????????????????
        if (StrUtil.equals("3", ext1)) {
            // ???????????????????????????????????????sql?????????
        	zjTzDesignFlow.setExt1Flag1("????????????????????????????????????????????????????????????");
        //	???????????????????????????????????????
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzDesignFlow.setExt1Flag2("???????????????????????????????????????");;
        // ???????????????????????????????????????????????????????????????	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzDesignFlow.setExt1Flag3("???????????????????????????????????????????????????????????????");
		}
        // ????????????
        PageHelper.startPage(zjTzDesignFlow.getPage(),zjTzDesignFlow.getLimit());
        // ????????????
        List<ZjTzDesignFlow> zjTzDesignFlowList = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlow);
        for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
        	String warnFlag = "";
        	//????????????????????????????????????????????????????????????
        	ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
        	zjTzPartManage.setDesignFlowId(designFlow.getDesignFlowId());
        	//11.????????? ?????????        
        	//22.????????????   ==?????????????????????  1. ????????????>=??????   ??????    
        	                      //2. ????????????<??????     ??????      
        	             //== ????????????????????????  ??????
            List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
           
            boolean bidPartIdFalg = false;//????????????????????????
            int orderNum = 0;
            
            for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
            	if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
            		bidPartIdFalg = true;
            		orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
            	}
            }
            
            if(bidPartIdFalg) {
            	//?????????
            	ZjTzPartManage lessRecord = new ZjTzPartManage();
            	lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
            	lessRecord.setOrderNum(orderNum);
            	List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
            	for (ZjTzPartManage zjTzPartManage3 : partLessList) {
            		zjTzPartManage3.setColorFlag("yellow");
            		warnFlag = "yellow";
            				
            	}
            	//?????????
            	ZjTzPartManage greaterRecord = new ZjTzPartManage();
            	greaterRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
            	greaterRecord.setOrderNum(orderNum);
            	List<ZjTzPartManage> partGreaterList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(greaterRecord);
            	for (ZjTzPartManage zjTzPartManage3 : partGreaterList) {
            		if(StrUtil.equals(zjTzPartManage3.getFileFlag(), "1")) {
            			zjTzPartManage3.setColorFlag("blue");
                     }else {
                     	if(zjTzPartManage3.getPlanDate() != null) {
                     		if(DateUtil.compare(zjTzPartManage3.getPlanDate(), new Date()) >=0) {
                     			zjTzPartManage3.setColorFlag("white");
                     		}else {
                     			zjTzPartManage3.setColorFlag("red");
                     		}
                     	}else {
                     		zjTzPartManage3.setColorFlag("red");
                     	}
                     }
    			}
            	for (ZjTzPartManage part : partGreaterList) {
            		if(StrUtil.equals(part.getColorFlag(), "red")) {
            			warnFlag = "red";
            		}
            	}
            }else {
            	for (ZjTzPartManage zjTzPartManage2 : zjTzPartManageList) {
            		zjTzPartManage2.setColorFlag("yellow");
    			}
            	warnFlag = "yellow";
            }
            designFlow.setWarnFlag(warnFlag);
		}
        // ??????????????????
        PageInfo<ZjTzDesignFlow> p = new PageInfo<>(zjTzDesignFlowList);

        return repEntity.okList(zjTzDesignFlowList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignFlowDetails(ZjTzDesignFlow zjTzDesignFlow) {
        if (zjTzDesignFlow == null) {
            zjTzDesignFlow = new ZjTzDesignFlow();
        }
        // ????????????
        ZjTzDesignFlow dbZjTzDesignFlow = zjTzDesignFlowMapper.selectByPrimaryKey(zjTzDesignFlow.getDesignFlowId());
        // ????????????
        if (dbZjTzDesignFlow != null) {
            return repEntity.ok(dbZjTzDesignFlow);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        ZjTzDesignFlow zjTzDesignFlowSelect = new ZjTzDesignFlow();
        zjTzDesignFlowSelect.setProjectId(zjTzDesignFlow.getProjectId());
        //????????????id????????????id   ????????????
        if(StrUtil.isNotEmpty(zjTzDesignFlow.getSubprojectInfoId())) {
        	zjTzDesignFlowSelect.setSubprojectInfoId(zjTzDesignFlow.getSubprojectInfoId());
        	List<ZjTzDesignFlow> sizeControls = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlowSelect);
        	if(sizeControls != null && sizeControls.size() >0) {
        		return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        	}
        	//???????????????
        	ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignFlow.getSubprojectInfoId());
        	zjTzDesignFlow.setSubprojectName(info.getSubprojectName());
        }else {
        	//????????????????????????  = ?????????id??????
        	zjTzDesignFlowSelect.setSubprojectInfoId("???");
        	List<ZjTzDesignFlow> sizeControls = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlowSelect);
        	if(sizeControls != null && sizeControls.size() >0) {
        		return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        	}
        	zjTzDesignFlow.setSubprojectInfoId("???");
        }
        zjTzDesignFlow.setDesignFlowId(UuidUtil.generate());
        // ??????????????????
        if (StrUtil.isNotEmpty(zjTzDesignFlow.getSkillTypeId())) {//11,12,13,14,15,16,1,10,4,6
        	String[] skillTypeNameList = zjTzDesignFlow.getSkillTypeId().split(",");
        	String skillTypeName = "";
        	for (String string : skillTypeNameList) {
        		skillTypeName += baseCodeService.getBaseCodeItemName("ZhuanYeJiShuLeiBie", string) + ",";
        	}
        	if(skillTypeName.indexOf(",")>0) {
        		skillTypeName = skillTypeName.substring(0, skillTypeName.length()-1);
        	}
        	zjTzDesignFlow.setSkillTypeName(skillTypeName);
        }
        zjTzDesignFlow.setReleaseId("0");
        zjTzDesignFlow.setReleaseName("?????????");
        zjTzDesignFlow.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignFlowMapper.insert(zjTzDesignFlow);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignFlow);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignFlow dbzjTzDesignFlow = zjTzDesignFlowMapper.selectByPrimaryKey(zjTzDesignFlow.getDesignFlowId());
        if (dbzjTzDesignFlow != null && StrUtil.isNotEmpty(dbzjTzDesignFlow.getDesignFlowId())) {
        	if(StrUtil.equals(dbzjTzDesignFlow.getReleaseId(), "1")) {
        		return repEntity.layerMessage("no", "??????????????????????????????");
        	}
        	// ??????????????????id
        	dbzjTzDesignFlow.setSkillTypeId(zjTzDesignFlow.getSkillTypeId());
        	// ??????????????????name
        	if (StrUtil.isNotEmpty(zjTzDesignFlow.getSkillTypeId())) {//11,12,13,14,15,16,1,10,4,6
           	String[] skillTypeNameList = zjTzDesignFlow.getSkillTypeId().split(",");
           	String skillTypeName = "";
           	for (String string : skillTypeNameList) {
           		skillTypeName += baseCodeService.getBaseCodeItemName("ZhuanYeJiShuLeiBie", string) + ",";
           	}
           	if(skillTypeName.indexOf(",")>0) {
           		skillTypeName = skillTypeName.substring(0, skillTypeName.length()-1);
           	}
           	dbzjTzDesignFlow.setSkillTypeName(skillTypeName);
           }
           // ??????
           dbzjTzDesignFlow.setRemarks(zjTzDesignFlow.getRemarks());
           // ??????
           dbzjTzDesignFlow.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignFlowMapper.updateByPrimaryKey(dbzjTzDesignFlow);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignFlow);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignFlowList != null && zjTzDesignFlowList.size() > 0) {
        	for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
        		if(StrUtil.equals(designFlow.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????");
        		}
        	}
        	for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
				//
        		ZjTzPartManage part = new ZjTzPartManage();
        		part.setDesignFlowId(designFlow.getDesignFlowId());
        		List<ZjTzPartManage> partManages = zjTzPartManageMapper.selectByZjTzPartManageList(part);
        		if(partManages != null && partManages.size() >0) {
        			//
        			for (ZjTzPartManage partManage : partManages) {
        				ZjTzFile file = new ZjTzFile();
        				file.setOtherId(partManage.getPartManageId());
        				List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
        				if(files != null && files.size() >0) {
        					file.setModifyUserInfo(userKey, realName);
        					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(files, file);
        				}
					}
        			part.setModifyUserInfo(userKey, realName);
        			flag = zjTzPartManageMapper.batchDeleteUpdateZjTzPartManage(partManages, part);
        		}
			}
           ZjTzDesignFlow zjTzDesignFlow = new ZjTzDesignFlow();
           zjTzDesignFlow.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignFlowMapper.batchDeleteUpdateZjTzDesignFlow(zjTzDesignFlowList, zjTzDesignFlow);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignFlowList);
        }
    }

    @Override
	public ResponseEntity batchReleaseZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        int flag = 0;
        if (zjTzDesignFlowList != null && zjTzDesignFlowList.size() > 0) {
        	for (ZjTzDesignFlow zjTzRules : zjTzDesignFlowList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}else if (StrUtil.equals(zjTzRules.getReleaseId(), "3") && StrUtil.equals("4", ext1)) {
					return repEntity.layerMessage("no", "??????????????????????????????!!");
				}
			}
        	ZjTzDesignFlow zjTzRules = new ZjTzDesignFlow();
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setReleaseId("3");;
        		zjTzRules.setReleaseName("??????????????????");;
			}else {
				zjTzRules.setReleaseId("1");
				zjTzRules.setReleaseName("?????????");
			}
        	
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setRenew3("0");
        		zjTzRules.setRenew1("");
			}else if (StrUtil.equals("3", ext1)) {
				zjTzRules.setRenew4("0");
				zjTzRules.setRenew3("");
				zjTzRules.setRenew2("");
			}else if (StrUtil.equals("2", ext1)) {
				zjTzRules.setRenew4("0");
				zjTzRules.setRenew3("");
				zjTzRules.setRenew2("");
			}
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignFlowMapper.batchRecallZjTzDesignFlow(zjTzDesignFlowList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignFlowList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignFlowList != null && zjTzDesignFlowList.size() > 0) {
        	for (ZjTzDesignFlow zjTzRules : zjTzDesignFlowList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "2")) {
        			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????!!");
        		}
        	}
        	ZjTzDesignFlow zjTzRules = new ZjTzDesignFlow();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setRenew1("0");
        	zjTzRules.setRenew2("0");
        	zjTzRules.setRenew3("");
        	zjTzRules.setRenew4("");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignFlowMapper.batchRecallZjTzDesignFlow(zjTzDesignFlowList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignFlowList);
        }
	}
	
    @Override
    public ResponseEntity saveZjTzPartManageAllList(ZjTzDesignFlow zjTzDesignFlow) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzDesignFlow != null && StrUtil.isNotEmpty(zjTzDesignFlow.getDesignFlowId())) {
    		if(zjTzDesignFlow.getZjTzPartManageList() != null && zjTzDesignFlow.getZjTzPartManageList().size() >0) {
    			//??????????????????
    			ZjTzPartManage delManage = new ZjTzPartManage();
    			delManage.setDesignFlowId(zjTzDesignFlow.getDesignFlowId());
    			List<ZjTzPartManage> delManageList = zjTzPartManageMapper.selectByZjTzPartManageList(delManage);
    			if(delManageList != null && delManageList.size() >0) {
    				delManage.setModifyUserInfo(userKey, realName);
    				flag = zjTzPartManageMapper.batchDeleteUpdateZjTzPartManage(delManageList, delManage);
    			}
    			int i = 1;
    			
    			if(zjTzDesignFlow.getZjTzPartManageList() != null && zjTzDesignFlow.getZjTzPartManageList().size() >0) {
    				for (ZjTzPartManage partManageDetail : zjTzDesignFlow.getZjTzPartManageList()) {
    					//?????????????????????
        				ZjTzDesignFlow designFlow = zjTzDesignFlowMapper.selectByPrimaryKey(partManageDetail.getDesignFlowId());
        				// ???????????????name
        				if(StrUtil.equals(partManageDetail.getBidPartId(), "1")) {
        					if(designFlow != null && StrUtil.isNotEmpty(designFlow.getDesignFlowId())) {
        						designFlow.setDesignPartId(partManageDetail.getDesignPartId());
        						designFlow.setDesignPartName(partManageDetail.getDesignPartName());
        						designFlow.setModifyUserInfo(userKey, realName);
        						flag = zjTzDesignFlowMapper.updateByPrimaryKey(designFlow);
        						break;
        					}
        				}else {
        					if(designFlow != null && StrUtil.isNotEmpty(designFlow.getDesignFlowId())) {
        						designFlow.setDesignPartId("");
        						designFlow.setDesignPartName("");
        						designFlow.setModifyUserInfo(userKey, realName);
        						flag = zjTzDesignFlowMapper.updateByPrimaryKey(designFlow);
        					}
        				}
        				
					}
    			}
    			
    			
    			for (ZjTzPartManage partManageDetail : zjTzDesignFlow.getZjTzPartManageList()) {
    				ZjTzPartManage dbzjTzPartManage = new ZjTzPartManage();
    				dbzjTzPartManage.setPartManageId(UuidUtil.generate());
    				//????????????id
    				dbzjTzPartManage.setDesignFlowId(partManageDetail.getDesignFlowId());
    				// ??????????????????id
    				dbzjTzPartManage.setDesignFlowTempId(partManageDetail.getDesignFlowTempId());
    				// ??????????????????name
    				dbzjTzPartManage.setDesignFlowTempName(partManageDetail.getDesignFlowTempName());
    				// ????????????id
    				dbzjTzPartManage.setDesignPartId(partManageDetail.getDesignPartId());
    				// ????????????name
    				dbzjTzPartManage.setDesignPartName(partManageDetail.getDesignPartName());
    				if(StrUtil.equals(partManageDetail.getBidPartId(), "1")) {
    					if(StrUtil.equals(partManageDetail.getBidPartId(), dbzjTzPartManage.getBidPartId())) {

    					}else {
    						ZjTzPartManage partManage = new ZjTzPartManage();
    						partManage.setBidPartId("1");
    						partManage.setDesignFlowId(dbzjTzPartManage.getDesignFlowId());
    						List<ZjTzPartManage> partManages = zjTzPartManageMapper.selectByZjTzPartManageList(partManage);
    						if(partManages != null &&partManages.size() >0) {
    							return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
    						}
    					}
    				}
    				// ???????????????id
    				dbzjTzPartManage.setBidPartId(partManageDetail.getBidPartId());
    				// ???????????????name
    				if(StrUtil.equals(partManageDetail.getBidPartId(), "1")) {
    					dbzjTzPartManage.setBidPartName("???");
    				}else {
    					dbzjTzPartManage.setBidPartName("???");
    				}
    				// ???????????????id
    				dbzjTzPartManage.setBuId(partManageDetail.getBuId());
    				// ???????????????name
    				if(StrUtil.equals(partManageDetail.getBuId(), "1")) {
    					dbzjTzPartManage.setBuName("???");
    				}else {
    					dbzjTzPartManage.setBuName("???");
    				}
    				// ??????????????????
    				dbzjTzPartManage.setPlanDate(partManageDetail.getPlanDate());
    				// ??????????????????
					dbzjTzPartManage.setActualDate(partManageDetail.getActualDate());
    				//?????????????????????????????????
    				dbzjTzPartManage.setUpdateFlag("1");
    				//
    				dbzjTzPartManage.setOrderNum(i);
    				i++;
    				dbzjTzPartManage.setLockFlag("0");//?????????
    				// ??????
    				dbzjTzPartManage.setCreateUserInfo(userKey, realName);

    				//????????????????????????
    				ZjTzFile file = new ZjTzFile();
    				file.setOtherId(partManageDetail.getPartManageId());
    				List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
    				if(files != null && files.size() >0) {
    					file.setModifyUserInfo(userKey, realName);
    					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(files, file);
    				}
    				List<ZjTzFile> ZjTzFileList = partManageDetail.getZjTzFileList();
    				if(ZjTzFileList != null && ZjTzFileList.size()>0) {
    					for(ZjTzFile zjTzFile:ZjTzFileList) {
    						zjTzFile.setUid(UuidUtil.generate());
    						zjTzFile.setOtherId(dbzjTzPartManage.getPartManageId());
    						zjTzFile.setCreateUserInfo(userKey, realName);
    						zjTzFileMapper.insert(zjTzFile);
    						// ??????????????????
//    						dbzjTzPartManage.setActualDate(zjTzFile.getModifyTime());
    					}
    					// ??????????????????????????????
    					dbzjTzPartManage.setFileFlag("1");
    				}else {
    					// ??????????????????
    					dbzjTzPartManage.setActualDate(null);
    					// ??????????????????????????????
    					dbzjTzPartManage.setFileFlag("0");
    				}
    				flag = zjTzPartManageMapper.insert(dbzjTzPartManage);
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzDesignFlow);
    	}
    }

	@Override
	public ResponseEntity checkAndFinishZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignFlow dbzjTzDesignFlow = zjTzDesignFlowMapper.selectByPrimaryKey(zjTzDesignFlow.getDesignFlowId());
    	if (dbzjTzDesignFlow != null && StrUtil.isNotEmpty(dbzjTzDesignFlow.getDesignFlowId())) {
    			//???????????????????????????????????????
	    		dbzjTzDesignFlow.setRenew1("");
	    		dbzjTzDesignFlow.setRenew2("");
	    		dbzjTzDesignFlow.setRenew3("");
	    		dbzjTzDesignFlow.setRenew4("");
	    		dbzjTzDesignFlow.setModifyUserInfo(userKey, realName);
    			flag = zjTzDesignFlowMapper.updateByPrimaryKey(dbzjTzDesignFlow);
    	}
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignFlow);
        }
	
	}

	
    
}