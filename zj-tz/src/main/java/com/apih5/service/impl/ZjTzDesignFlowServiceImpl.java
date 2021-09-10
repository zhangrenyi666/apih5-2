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
    	// 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzDesignFlow.getProjectId(), true)) {
            	zjTzDesignFlow.setProjectId("");
            	zjTzDesignFlow.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
//        	zjTzDesignFlow.setExt1SeeFlag("投资事业部只可见已上报和被退回的数据");
            // 集团人员时
            if(StrUtil.equals("all", zjTzDesignFlow.getProjectId(), true)) {
            	zjTzDesignFlow.setProjectId("");
            }
        }
        
        // 直管项目用户不可见托管项目上报状态的数据
        if (StrUtil.equals("3", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
        	zjTzDesignFlow.setExt1Flag1("直管项目用户不可见托管项目上报状态的数据");
        //	托管公司不可见未上报的数据
        }else if (StrUtil.equals("2", ext1)) {
        	zjTzDesignFlow.setExt1Flag2("托管公司不可见未上报的数据");;
        // 局用户不可见未上报和托管项目上报状态的数据	
		}else if (StrUtil.equals("1", ext1)) {
			zjTzDesignFlow.setExt1Flag3("局用户不可见未上报和托管项目上报状态的数据");
		}
        // 分页查询
        PageHelper.startPage(zjTzDesignFlow.getPage(),zjTzDesignFlow.getLimit());
        // 获取数据
        List<ZjTzDesignFlow> zjTzDesignFlowList = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlow);
        for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
        	String warnFlag = "";
        	//根据里层的数据状态返给前台外层的预警状态
        	ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
        	zjTzPartManage.setDesignFlowId(designFlow.getDesignFlowId());
        	//11.中标前 均为黄        
        	//22.中标后：   ==实际日期为空时  1. 计划日期>=今日   白色    
        	                      //2. 计划日期<今日     红色      
        	             //== 实际日期不为空时  蓝色
            List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
           
            boolean bidPartIdFalg = false;//是否有中标时状态
            int orderNum = 0;
            
            for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
            	if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
            		bidPartIdFalg = true;
            		orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
            	}
            }
            
            if(bidPartIdFalg) {
            	//中标前
            	ZjTzPartManage lessRecord = new ZjTzPartManage();
            	lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
            	lessRecord.setOrderNum(orderNum);
            	List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
            	for (ZjTzPartManage zjTzPartManage3 : partLessList) {
            		zjTzPartManage3.setColorFlag("yellow");
            		warnFlag = "yellow";
            				
            	}
            	//中标后
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
        // 得到分页信息
        PageInfo<ZjTzDesignFlow> p = new PageInfo<>(zjTzDesignFlowList);

        return repEntity.okList(zjTzDesignFlowList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignFlowDetails(ZjTzDesignFlow zjTzDesignFlow) {
        if (zjTzDesignFlow == null) {
            zjTzDesignFlow = new ZjTzDesignFlow();
        }
        // 获取数据
        ZjTzDesignFlow dbZjTzDesignFlow = zjTzDesignFlowMapper.selectByPrimaryKey(zjTzDesignFlow.getDesignFlowId());
        // 数据存在
        if (dbZjTzDesignFlow != null) {
            return repEntity.ok(dbZjTzDesignFlow);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignFlow(ZjTzDesignFlow zjTzDesignFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        ZjTzDesignFlow zjTzDesignFlowSelect = new ZjTzDesignFlow();
        zjTzDesignFlowSelect.setProjectId(zjTzDesignFlow.getProjectId());
        //一、项目id和子项目id   共同判断
        if(StrUtil.isNotEmpty(zjTzDesignFlow.getSubprojectInfoId())) {
        	zjTzDesignFlowSelect.setSubprojectInfoId(zjTzDesignFlow.getSubprojectInfoId());
        	List<ZjTzDesignFlow> sizeControls = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlowSelect);
        	if(sizeControls != null && sizeControls.size() >0) {
        		return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
        	}
        	//子项目名称
        	ZjTzProSubprojectInfo info = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzDesignFlow.getSubprojectInfoId());
        	zjTzDesignFlow.setSubprojectName(info.getSubprojectName());
        }else {
        	//二、不填子项目的  = 用项目id判断
        	zjTzDesignFlowSelect.setSubprojectInfoId("无");
        	List<ZjTzDesignFlow> sizeControls = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlowSelect);
        	if(sizeControls != null && sizeControls.size() >0) {
        		return repEntity.layerMessage("no", "该项目已经添加，请重新添加项目");
        	}
        	zjTzDesignFlow.setSubprojectInfoId("无");
        }
        zjTzDesignFlow.setDesignFlowId(UuidUtil.generate());
        // 专业技术类别
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
        zjTzDesignFlow.setReleaseName("未上报");
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
        		return repEntity.layerMessage("no", "已上报的数据不能修改");
        	}
        	// 专业技术类别id
        	dbzjTzDesignFlow.setSkillTypeId(zjTzDesignFlow.getSkillTypeId());
        	// 专业技术类别name
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
           // 备注
           dbzjTzDesignFlow.setRemarks(zjTzDesignFlow.getRemarks());
           // 共通
           dbzjTzDesignFlow.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignFlowMapper.updateByPrimaryKey(dbzjTzDesignFlow);
        }
        // 失败
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
        			return repEntity.layerMessage("no", "已上报的数据不能删除！");
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
        // 失败
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
					 return repEntity.layerMessage("no", "包含已上报的数据，请重新选择！");
				}else if (StrUtil.equals(zjTzRules.getReleaseId(), "3") && StrUtil.equals("4", ext1)) {
					return repEntity.layerMessage("no", "无法上报，请重新选择!!");
				}
			}
        	ZjTzDesignFlow zjTzRules = new ZjTzDesignFlow();
        	if (StrUtil.equals("4", ext1)) {
        		zjTzRules.setReleaseId("3");;
        		zjTzRules.setReleaseName("托管项目上报");;
			}else {
				zjTzRules.setReleaseId("1");
				zjTzRules.setReleaseName("已上报");
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
        // 失败
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
        			return repEntity.layerMessage("no", "包含未上报或被撤回的数据，请重新选择!!");
        		}
        	}
        	ZjTzDesignFlow zjTzRules = new ZjTzDesignFlow();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("被退回");
        	zjTzRules.setRenew1("0");
        	zjTzRules.setRenew2("0");
        	zjTzRules.setRenew3("");
        	zjTzRules.setRenew4("");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignFlowMapper.batchRecallZjTzDesignFlow(zjTzDesignFlowList, zjTzRules);
        }
        // 失败
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
    			//先删除再新增
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
    					//更新外层的数据
        				ZjTzDesignFlow designFlow = zjTzDesignFlowMapper.selectByPrimaryKey(partManageDetail.getDesignFlowId());
        				// 中标时环节name
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
    				//设计流程id
    				dbzjTzPartManage.setDesignFlowId(partManageDetail.getDesignFlowId());
    				// 设计流程模板id
    				dbzjTzPartManage.setDesignFlowTempId(partManageDetail.getDesignFlowTempId());
    				// 设计流程模板name
    				dbzjTzPartManage.setDesignFlowTempName(partManageDetail.getDesignFlowTempName());
    				// 设计环节id
    				dbzjTzPartManage.setDesignPartId(partManageDetail.getDesignPartId());
    				// 设计环节name
    				dbzjTzPartManage.setDesignPartName(partManageDetail.getDesignPartName());
    				if(StrUtil.equals(partManageDetail.getBidPartId(), "1")) {
    					if(StrUtil.equals(partManageDetail.getBidPartId(), dbzjTzPartManage.getBidPartId())) {

    					}else {
    						ZjTzPartManage partManage = new ZjTzPartManage();
    						partManage.setBidPartId("1");
    						partManage.setDesignFlowId(dbzjTzPartManage.getDesignFlowId());
    						List<ZjTzPartManage> partManages = zjTzPartManageMapper.selectByZjTzPartManageList(partManage);
    						if(partManages != null &&partManages.size() >0) {
    							return repEntity.layerMessage("no", "已有设计环节为中标时环节，不能再次选中！");
    						}
    					}
    				}
    				// 中标时环节id
    				dbzjTzPartManage.setBidPartId(partManageDetail.getBidPartId());
    				// 中标时环节name
    				if(StrUtil.equals(partManageDetail.getBidPartId(), "1")) {
    					dbzjTzPartManage.setBidPartName("是");
    				}else {
    					dbzjTzPartManage.setBidPartName("否");
    				}
    				// 事业部介入id
    				dbzjTzPartManage.setBuId(partManageDetail.getBuId());
    				// 事业部介入name
    				if(StrUtil.equals(partManageDetail.getBuId(), "1")) {
    					dbzjTzPartManage.setBuName("是");
    				}else {
    					dbzjTzPartManage.setBuName("否");
    				}
    				// 计划完成日期
    				dbzjTzPartManage.setPlanDate(partManageDetail.getPlanDate());
    				// 实际完成日期
					dbzjTzPartManage.setActualDate(partManageDetail.getActualDate());
    				//是否进行了更新操作标识
    				dbzjTzPartManage.setUpdateFlag("1");
    				//
    				dbzjTzPartManage.setOrderNum(i);
    				i++;
    				dbzjTzPartManage.setLockFlag("0");//未锁定
    				// 共通
    				dbzjTzPartManage.setCreateUserInfo(userKey, realName);

    				//附件先删除再新增
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
    						// 实际完成日期
//    						dbzjTzPartManage.setActualDate(zjTzFile.getModifyTime());
    					}
    					// 证明材料附件上传标示
    					dbzjTzPartManage.setFileFlag("1");
    				}else {
    					// 实际完成日期
    					dbzjTzPartManage.setActualDate(null);
    					// 证明材料附件上传标示
    					dbzjTzPartManage.setFileFlag("0");
    				}
    				flag = zjTzPartManageMapper.insert(dbzjTzPartManage);
    			}
    		}
    	}
    	// 失败
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
    			//是最新的一条数据，可以上报
	    		dbzjTzDesignFlow.setRenew1("");
	    		dbzjTzDesignFlow.setRenew2("");
	    		dbzjTzDesignFlow.setRenew3("");
	    		dbzjTzDesignFlow.setRenew4("");
	    		dbzjTzDesignFlow.setModifyUserInfo(userKey, realName);
    			flag = zjTzDesignFlowMapper.updateByPrimaryKey(dbzjTzDesignFlow);
    	}
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignFlow);
        }
	
	}

	
    
}