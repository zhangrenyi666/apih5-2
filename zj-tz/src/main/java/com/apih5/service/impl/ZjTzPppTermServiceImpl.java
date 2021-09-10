package com.apih5.service.impl;

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
import com.apih5.mybatis.dao.ZjTzPppTermBaseMapper;
import com.apih5.mybatis.dao.ZjTzPppTermMapper;
import com.apih5.mybatis.dao.ZjTzPppTermReplyMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPppTerm;
import com.apih5.mybatis.pojo.ZjTzPppTermBase;
import com.apih5.mybatis.pojo.ZjTzPppTermReply;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzPppTermService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPppTermService")
public class ZjTzPppTermServiceImpl implements ZjTzPppTermService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPppTermMapper zjTzPppTermMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzPppTermReplyMapper zjTzPppTermReplyMapper;
    
    @Autowired(required = true)
    private ZjTzPppTermBaseMapper zjTzPppTermBaseMapper;

    @Autowired(required = true)
    private BaseCodeService baseCodeService;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzPppTermListByCondition(ZjTzPppTerm zjTzPppTerm) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzPppTerm == null) {
            zjTzPppTerm = new ZjTzPppTerm();
        } 
        // 分页查询
        PageHelper.startPage(zjTzPppTerm.getPage(),zjTzPppTerm.getLimit());
        //托管公司可以看见他管辖下的所有状态的数据  ; 局什么数据都可见
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzPppTerm.getProjectId(), true)) {
            	zjTzPppTerm.setProjectId("");
                zjTzPppTerm.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzPppTerm.getProjectId(), true)) {
                zjTzPppTerm.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzPppTerm> zjTzPppTermList = zjTzPppTermMapper.selectByZjTzPppTermList(zjTzPppTerm);
        for (ZjTzPppTerm zjTzPppTerm2 : zjTzPppTermList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzPppTerm2.getPppTermId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPppTerm2.setZjTzFileList(zjTzFileList);
        	//遍历
        	String numberContent = "";
        	String term = "";
        	String riskFlag = "";
        	String measure = "";
        	String negotiateFlag = "";
        	String deptAndLeader = "";
        	String implement = "";
        	ZjTzPppTermReply record = new ZjTzPppTermReply();
        	record.setPppTermId(zjTzPppTerm2.getPppTermId());
        	List<ZjTzPppTermReply> recordList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(record);
        	if(recordList != null && recordList.size() >0) {
        		for (ZjTzPppTermReply zjTzPppTerm3 : recordList) {
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getRiskFlag())) {
        				riskFlag = "否";
        				if(StrUtil.equals(zjTzPppTerm3.getRiskFlag(), "1")) {
        					riskFlag = "是";
        					break;
        				}
        			}
        		}
        		for (ZjTzPppTermReply zjTzPppTerm3 : recordList) {
    				if(StrUtil.isNotEmpty(zjTzPppTerm3.getNegotiateFlag())) {
        				negotiateFlag = "否";
        				if(StrUtil.equals(zjTzPppTerm3.getNegotiateFlag(), "1")) {
        					negotiateFlag = "是";
        					break;
        				}
        			}
    			}
        		for (ZjTzPppTermReply zjTzPppTerm3 : recordList) {
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getNumberContent())) {
        				numberContent = zjTzPppTerm3.getNumberContent()+","+numberContent;
        			}
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getTerm())) {
        				term = zjTzPppTerm3.getTerm()+","+term;
        			}
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getMeasure())) {
        				measure = zjTzPppTerm3.getMeasure()+","+measure;
        			}
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getDeptAndLeader())) {
        				deptAndLeader = zjTzPppTerm3.getDeptAndLeader()+","+deptAndLeader;
        			}
        			if(StrUtil.isNotEmpty(zjTzPppTerm3.getImplement())) {
        				implement = zjTzPppTerm3.getImplement()+","+implement;
        			}
        		}
        	}
        	zjTzPppTerm2.setNumberContent(numberContent);
        	zjTzPppTerm2.setTerm(term);
        	zjTzPppTerm2.setMeasure(measure);
        	zjTzPppTerm2.setRiskFlag(riskFlag);
        	zjTzPppTerm2.setNegotiateFlag(negotiateFlag);
        	zjTzPppTerm2.setDeptAndLeader(deptAndLeader);
        	zjTzPppTerm2.setImplement(implement);
		}
        // 得到分页信息
        PageInfo<ZjTzPppTerm> p = new PageInfo<>(zjTzPppTermList);
        return repEntity.okList(zjTzPppTermList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPppTermDetails(ZjTzPppTerm zjTzPppTerm) {
        if (zjTzPppTerm == null) {
            zjTzPppTerm = new ZjTzPppTerm();
        }
        zjTzPppTerm = zjTzPppTermMapper.selectByPrimaryKey(zjTzPppTerm.getPppTermId());
        // 数据存在
        if (zjTzPppTerm != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzPppTerm.getPppTermId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPppTerm.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzPppTerm);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPppTerm(ZjTzPppTerm zjTzPppTerm) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzPppTerm.getProjectId());
    	if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())){
    		zjTzPppTerm.setProjectName(manage.getProjectName());
    	}
    	
    	//一、项目id和子项目id   共同判断
    	if(StrUtil.isNotEmpty(zjTzPppTerm.getSubprojectInfoId())) {
    	}else {
    		//二、不填子项目的  = 用项目id判断
    		zjTzPppTerm.setSubprojectInfoId("无");
    	}
    	zjTzPppTerm.setPppTermId(UuidUtil.generate());
    	//分析期次
    	if (StrUtil.isNotEmpty(zjTzPppTerm.getIssueId())) {
    		int number = Integer.parseInt(zjTzPppTerm.getIssueId());
    		ZjTzPppTerm zjTzPppTermSelect = new ZjTzPppTerm();
    		zjTzPppTermSelect.setProjectId(zjTzPppTerm.getProjectId()); 
    		zjTzPppTermSelect.setSubprojectInfoId(zjTzPppTerm.getSubprojectInfoId());
    		zjTzPppTermSelect.setIssueId(zjTzPppTerm.getIssueId());
    		List<ZjTzPppTerm> zjTzPppTermSelectList = zjTzPppTermMapper.selectByZjTzPppTermList(zjTzPppTermSelect);
    		if(zjTzPppTermSelectList != null && zjTzPppTermSelectList.size() >0) {
    			return repEntity.layerMessage("no", "已经添加，请重新选择！");
    		}
			ZjTzPppTermBase termBaseReply = new ZjTzPppTermBase();
			List<ZjTzPppTermBase> zjTzPppTermBaseReplyList =  zjTzPppTermBaseMapper.getByZjTzPppTermBaseTermList(termBaseReply);
			if (zjTzPppTermBaseReplyList != null && zjTzPppTermBaseReplyList.size() >0) {
				//新增回复表
				if(StrUtil.equals(zjTzPppTerm.getIssueId(), "0")) {
					for (ZjTzPppTermBase termBase : zjTzPppTermBaseReplyList) {
						ZjTzPppTermReply dbzjTzPppTermReply = new ZjTzPppTermReply();
						// ppp合同分析id
						dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());
						dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
						dbzjTzPppTermReply.setPppTermBaseId(termBase.getPppTermBaseId());
						// 合同风险条款设置copeid
						dbzjTzPppTermReply.setCodePid(termBase.getCodePid());
						// 分析重点
						dbzjTzPppTermReply.setAnalysisKey(termBase.getAnalysisKeyName());
						// 重点条款
						dbzjTzPppTermReply.setKeyTerm(termBase.getKeyTerm());
						// 重点分析内容
						dbzjTzPppTermReply.setKeyAnalysisContent(termBase.getKeyAnalysisContent());
						//排序
						dbzjTzPppTermReply.setOrderFlag(termBase.getOrderFlag());
						//
						dbzjTzPppTermReply.setChangeFlag1("0");
						dbzjTzPppTermReply.setChangeFlag2("0");
						dbzjTzPppTermReply.setChangeFlag3("0");
						dbzjTzPppTermReply.setChangeFlag4("0");
						dbzjTzPppTermReply.setChangeFlag5("0");
						dbzjTzPppTermReply.setChangeFlag6("0");
						dbzjTzPppTermReply.setChangeFlag7("0");
						// 共通
						dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
						flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
					}
				}else {
					//新增的不是首次分析的时候要把回复的记录表引过来
					zjTzPppTermSelect.setIssueId(number-1+"");
					List<ZjTzPppTerm> zjTzPppTermReduceList = zjTzPppTermMapper.selectByZjTzPppTermList(zjTzPppTermSelect);
					if(zjTzPppTermReduceList != null && zjTzPppTermReduceList.size() >0) {
						ZjTzPppTermReply termReplyCopy = new ZjTzPppTermReply();
						termReplyCopy.setPppTermId(zjTzPppTermReduceList.get(0).getPppTermId());

						List<ZjTzPppTermReply> zjTzPppTermReplyCopyList =  zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(termReplyCopy);
						for (ZjTzPppTermBase base : zjTzPppTermBaseReplyList) {
							boolean copyBaseFlag = false;
							String analysisKey = "";
							String keyTerm = "";
							String keyAnalysisContent = "";
							String numberContent = "";
							String term = "";
							String riskFlag = "";
							String measure = "";
							String negotiateFlag = "";
							String deptAndLeader = "";
							String implement = "";
							int orderFlag = 0;

							ZjTzPppTermReply dbzjTzPppTermReply = new ZjTzPppTermReply();
							dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());// ppp合同分析id
							dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
							dbzjTzPppTermReply.setPppTermBaseId(base.getPppTermBaseId());
							dbzjTzPppTermReply.setCodePid(base.getCodePid());// 合同风险条款设置copeid

							for (ZjTzPppTermReply copyReply : zjTzPppTermReplyCopyList) {
								if(StrUtil.equals(copyReply.getPppTermBaseId(), base.getPppTermBaseId())) {
									copyBaseFlag = true;
									analysisKey = base.getAnalysisKey();
									keyTerm = base.getKeyTerm();
									keyAnalysisContent = base.getKeyAnalysisContent();
									numberContent = copyReply.getNumberContent();
									term = copyReply.getTerm();	
									riskFlag = copyReply.getRiskFlag();
									measure = copyReply.getMeasure();
									negotiateFlag = copyReply.getNegotiateFlag();
									deptAndLeader = copyReply.getDeptAndLeader();
									implement = copyReply.getImplement();
									orderFlag = copyReply.getOrderFlag();
									break;
								}
							}
							if(copyBaseFlag) {//===========================================没变是copy
								// 分析重点
								dbzjTzPppTermReply.setAnalysisKey(analysisKey);
								// 重点条款
								dbzjTzPppTermReply.setKeyTerm(keyTerm);
								// 重点分析内容
								dbzjTzPppTermReply.setKeyAnalysisContent(keyAnalysisContent);

								// 条款编号与内容
								dbzjTzPppTermReply.setNumberContent(numberContent);
								// 条款分析
								dbzjTzPppTermReply.setTerm(term);
								// 是否存在风险
								dbzjTzPppTermReply.setRiskFlag(riskFlag);
								// 应错措施
								dbzjTzPppTermReply.setMeasure(measure);
								// 是否需要二次谈判
								dbzjTzPppTermReply.setNegotiateFlag(negotiateFlag);
								// 责任部门与负责人
								dbzjTzPppTermReply.setDeptAndLeader(deptAndLeader);
								// 措施落实情况
								dbzjTzPppTermReply.setImplement(implement);
							}else {//=======================================================变了是base
								// 分析重点
								dbzjTzPppTermReply.setAnalysisKey(base.getAnalysisKey());
								// 重点条款
								dbzjTzPppTermReply.setKeyTerm(base.getKeyTerm());
								// 重点分析内容
								dbzjTzPppTermReply.setKeyAnalysisContent(base.getKeyAnalysisContent());
							}
							//
							dbzjTzPppTermReply.setOrderFlag(orderFlag);
							dbzjTzPppTermReply.setChangeFlag1("0");
							dbzjTzPppTermReply.setChangeFlag2("0");
							dbzjTzPppTermReply.setChangeFlag3("0");
							dbzjTzPppTermReply.setChangeFlag4("0");
							dbzjTzPppTermReply.setChangeFlag5("0");
							dbzjTzPppTermReply.setChangeFlag6("0");
							dbzjTzPppTermReply.setChangeFlag7("0");
							// 共通
							dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
							flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
						}
					}else {
						return repEntity.layerMessage("no", "请按顺序添加！");
					}
				}
			}
    		String openBankName = baseCodeService.getBaseCodeItemName("fenXiQiCi", zjTzPppTerm.getIssueId());
    		zjTzPppTerm.setIssueName(openBankName);
    	}
    	zjTzPppTerm.setStatusId("0");
    	zjTzPppTerm.setStatusName("未上报");
    	zjTzPppTerm.setCreateUserInfo(userKey, realName);
    	flag = zjTzPppTermMapper.insert(zjTzPppTerm);
    	// 附件list
    	List<ZjTzFile> zjTzFileList = zjTzPppTerm.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzPppTerm.getPppTermId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzPppTerm);
    	}
    }

    @Override
    public ResponseEntity updateZjTzPppTerm(ZjTzPppTerm zjTzPppTerm) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPppTerm dbzjTzPppTerm = zjTzPppTermMapper.selectByPrimaryKey(zjTzPppTerm.getPppTermId());
        if (dbzjTzPppTerm != null && StrUtil.isNotEmpty(dbzjTzPppTerm.getPppTermId())) {
        	// 0(未上报),1(已上报)2(被退回)
        	if(StrUtil.equals(dbzjTzPppTerm.getStatusId(), "0") || StrUtil.equals(dbzjTzPppTerm.getStatusId(), "2")) {

        	}else {
        		return repEntity.layerMessage("no", "未上报退回的数据才可以修改"); 
        	}
//           // 项目id
//           dbzjTzPppTerm.setProjectId(zjTzPppTerm.getProjectId());
//           // 项目name
//           dbzjTzPppTerm.setProjectName(zjTzPppTerm.getProjectName());
//           //分析期次id
//           dbzjTzPppTerm.setIssueId(zjTzPppTerm.getIssueId());
//           // 分析期次name
//           dbzjTzPppTerm.setIssueName(zjTzPppTerm.getIssueName());
//           // 条款编号与内容
//           dbzjTzPppTerm.setNumberContent(zjTzPppTerm.getNumberContent());
//           // 条款分析
//           dbzjTzPppTerm.setTerm(zjTzPppTerm.getTerm());
//           // 是否存在风险
//           dbzjTzPppTerm.setRiskFlag(zjTzPppTerm.getRiskFlag());
//           // 应错措施
//           dbzjTzPppTerm.setMeasure(zjTzPppTerm.getMeasure());
//           // 是否需要二次谈判
//           dbzjTzPppTerm.setNegotiateFlag(zjTzPppTerm.getNegotiateFlag());
//           // 责任部门与负责人
//           dbzjTzPppTerm.setDeptAndLeader(zjTzPppTerm.getDeptAndLeader());
//           // 措施落实情况
//           dbzjTzPppTerm.setImplement(zjTzPppTerm.getImplement());
           // 登记时间
           dbzjTzPppTerm.setRegisterDate(zjTzPppTerm.getRegisterDate());
           // 登记用户
           dbzjTzPppTerm.setRegisterPerson(zjTzPppTerm.getRegisterPerson());
           // 共通
           dbzjTzPppTerm.setModifyUserInfo(userKey, realName);
           flag = zjTzPppTermMapper.updateByPrimaryKey(dbzjTzPppTerm);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzPppTerm.getPppTermId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzPppTerm.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzPppTerm.getPppTermId());
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
            return repEntity.ok("sys.data.update",zjTzPppTerm);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPppTermList != null && zjTzPppTermList.size() > 0) {
    		for (ZjTzPppTerm termSelect : zjTzPppTermList) {
    			if(StrUtil.equals(termSelect.getStatusId(), "1")) {
    				return repEntity.layerMessage("no", "包含已上报的数据，请重新选择！"); 
    			}
    		}
    		//删除
    		for (ZjTzPppTerm zjTzPppTerm : zjTzPppTermList) {
    			// 删除附件
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherId(zjTzPppTerm.getPppTermId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			//删除回复
    			ZjTzPppTermReply reply = new ZjTzPppTermReply();
    			reply.setPppTermId(zjTzPppTerm.getPppTermId());
    			List<ZjTzPppTermReply> replies = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(reply);
    			if(replies != null && replies.size() >0) {
    				reply.setModifyUserInfo(userKey, realName);
    				zjTzPppTermReplyMapper.batchDeleteUpdateZjTzPppTermReply(replies, reply);
    			}
    		}
    		ZjTzPppTerm zjTzPppTerm = new ZjTzPppTerm();
    		zjTzPppTerm.setModifyUserInfo(userKey, realName);
    		flag = zjTzPppTermMapper.batchDeleteUpdateZjTzPppTerm(zjTzPppTermList, zjTzPppTerm);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzPppTermList);
    	}
    }

    @Override
	public ResponseEntity batchReleaseZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPppTermList != null && zjTzPppTermList.size() > 0) {
        	for (ZjTzPppTerm zjTzRules : zjTzPppTermList) {
				if(StrUtil.equals(zjTzRules.getStatusId(), "1")) {
					 return repEntity.layerMessage("no", "包含已上报的数据，请重新选择！");
				}
			}
        	ZjTzPppTerm zjTzRules = new ZjTzPppTerm();
        	zjTzRules.setStatusId("1");
        	zjTzRules.setStatusName("已上报");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPppTermMapper.batchRecallZjTzPppTerm(zjTzPppTermList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPppTermList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPppTermList != null && zjTzPppTermList.size() > 0) {
        	for (ZjTzPppTerm zjTzRules : zjTzPppTermList) {
        		if(StrUtil.equals(zjTzRules.getStatusId(), "0") || StrUtil.equals(zjTzRules.getStatusId(), "2")) {
        			return repEntity.layerMessage("no", "包含未上报或被退回的数据，请重新选择！");
        		}
        	}
        	ZjTzPppTerm zjTzRules = new ZjTzPppTerm();
        	zjTzRules.setStatusId("2");
        	zjTzRules.setStatusName("被退回");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPppTermMapper.batchRecallZjTzPppTerm(zjTzPppTermList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPppTermList);
        }
	}
    
    @Override
    public ResponseEntity saveZjTzPppTermAllReply(ZjTzPppTerm zjTzPppTerm) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPppTerm != null && StrUtil.isNotEmpty(zjTzPppTerm.getPppTermId())) {
    		if (zjTzPppTerm.getZjTzPppTermReplyList() != null && zjTzPppTerm.getZjTzPppTermReplyList().size() >0) {
    			//先删除再新增
    			ZjTzPppTermReply delReply = new ZjTzPppTermReply();
    			delReply.setPppTermId(zjTzPppTerm.getPppTermId());
    			List<ZjTzPppTermReply> delRelpyList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(delReply);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				delReply.setModifyUserInfo(userKey, realName);
    				flag = zjTzPppTermReplyMapper.batchDeleteUpdateZjTzPppTermReply(delRelpyList, delReply);
    			}

    			for (ZjTzPppTermReply termBase : zjTzPppTerm.getZjTzPppTermReplyList()) {
    				ZjTzPppTermReply dbzjTzPppTermReply = new ZjTzPppTermReply();
    				// ppp合同分析id
    				dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());
    				// ppp合同分析id
    				dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
    				//
    				dbzjTzPppTermReply.setPppTermBaseId(termBase.getPppTermBaseId());
    				// 合同风险条款设置copeid
    				dbzjTzPppTermReply.setCodePid(termBase.getCodePid());
    				
    				ZjTzPppTermReply record = new ZjTzPppTermReply();
    				record.setPppTermId(zjTzPppTerm.getPppTermId());
    				record.setPppTermBaseId(termBase.getPppTermBaseId());
    				record.setCodePid(termBase.getCodePid());
    				List<ZjTzPppTermReply> recordList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyListDelFlag(record);
    				if(recordList != null && recordList.size() >0) {
    					ZjTzPppTermReply replySelect = recordList.get(0);
    					//
    					if(StrUtil.equals(replySelect.getNumberContent(), termBase.getNumberContent())) {
    						dbzjTzPppTermReply.setChangeFlag1("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag1("1");
    					}
    					if(StrUtil.equals(replySelect.getTerm(), termBase.getTerm())) {
    						dbzjTzPppTermReply.setChangeFlag2("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag2("1");
    					}
    					if(StrUtil.equals(replySelect.getRiskFlag(), termBase.getRiskFlag())) {
    						dbzjTzPppTermReply.setChangeFlag3("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag3("1");
    					}
    					if(StrUtil.equals(replySelect.getMeasure(), termBase.getMeasure())) {
    						dbzjTzPppTermReply.setChangeFlag4("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag4("1");
    					}
    					if(StrUtil.equals(replySelect.getNegotiateFlag(), termBase.getNegotiateFlag())) {
    						dbzjTzPppTermReply.setChangeFlag5("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag5("1");
    					}
    					if(StrUtil.equals(replySelect.getDeptAndLeader(), termBase.getDeptAndLeader())) {
    						dbzjTzPppTermReply.setChangeFlag6("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag6("1");
    					}
    					if(StrUtil.equals(replySelect.getImplement(), termBase.getImplement())) {
    						dbzjTzPppTermReply.setChangeFlag7("0");
    					}else {
    						dbzjTzPppTermReply.setChangeFlag7("1");
    					}
    				}
    				// 分析重点
    				dbzjTzPppTermReply.setAnalysisKey(termBase.getAnalysisKey());
    				// 重点条款
    				dbzjTzPppTermReply.setKeyTerm(termBase.getKeyTerm());
    				// 重点分析内容
    				dbzjTzPppTermReply.setKeyAnalysisContent(termBase.getKeyAnalysisContent());
    				// 条款编号与内容
    				dbzjTzPppTermReply.setNumberContent(termBase.getNumberContent());
    				// 条款分析
    				dbzjTzPppTermReply.setTerm(termBase.getTerm());
    				// 是否存在风险
    				dbzjTzPppTermReply.setRiskFlag(termBase.getRiskFlag());
    				// 应错措施
    				dbzjTzPppTermReply.setMeasure(termBase.getMeasure());
    				// 是否需要二次谈判
    				dbzjTzPppTermReply.setNegotiateFlag(termBase.getNegotiateFlag());
    				// 责任部门与负责人
    				dbzjTzPppTermReply.setDeptAndLeader(termBase.getDeptAndLeader());
    				// 措施落实情况
    				dbzjTzPppTermReply.setImplement(termBase.getImplement());
    				//排序
    				dbzjTzPppTermReply.setOrderFlag(termBase.getOrderFlag());
    				// 共通
    				dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
    				flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzPppTerm);
    	}
    }

	

	
    
}