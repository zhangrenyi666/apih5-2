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
        // ????????????
        PageHelper.startPage(zjTzPppTerm.getPage(),zjTzPppTerm.getLimit());
        //????????????????????????????????????????????????????????????  ; ????????????????????????
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzPppTerm.getProjectId(), true)) {
            	zjTzPppTerm.setProjectId("");
                zjTzPppTerm.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzPppTerm.getProjectId(), true)) {
                zjTzPppTerm.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzPppTerm> zjTzPppTermList = zjTzPppTermMapper.selectByZjTzPppTermList(zjTzPppTerm);
        for (ZjTzPppTerm zjTzPppTerm2 : zjTzPppTermList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzPppTerm2.getPppTermId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPppTerm2.setZjTzFileList(zjTzFileList);
        	//??????
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
        				riskFlag = "???";
        				if(StrUtil.equals(zjTzPppTerm3.getRiskFlag(), "1")) {
        					riskFlag = "???";
        					break;
        				}
        			}
        		}
        		for (ZjTzPppTermReply zjTzPppTerm3 : recordList) {
    				if(StrUtil.isNotEmpty(zjTzPppTerm3.getNegotiateFlag())) {
        				negotiateFlag = "???";
        				if(StrUtil.equals(zjTzPppTerm3.getNegotiateFlag(), "1")) {
        					negotiateFlag = "???";
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
        // ??????????????????
        PageInfo<ZjTzPppTerm> p = new PageInfo<>(zjTzPppTermList);
        return repEntity.okList(zjTzPppTermList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPppTermDetails(ZjTzPppTerm zjTzPppTerm) {
        if (zjTzPppTerm == null) {
            zjTzPppTerm = new ZjTzPppTerm();
        }
        zjTzPppTerm = zjTzPppTermMapper.selectByPrimaryKey(zjTzPppTerm.getPppTermId());
        // ????????????
        if (zjTzPppTerm != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzPppTerm.getPppTermId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPppTerm.setZjTzFileList(zjTzFileList);
            return repEntity.ok(zjTzPppTerm);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
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
    	
    	//????????????id????????????id   ????????????
    	if(StrUtil.isNotEmpty(zjTzPppTerm.getSubprojectInfoId())) {
    	}else {
    		//????????????????????????  = ?????????id??????
    		zjTzPppTerm.setSubprojectInfoId("???");
    	}
    	zjTzPppTerm.setPppTermId(UuidUtil.generate());
    	//????????????
    	if (StrUtil.isNotEmpty(zjTzPppTerm.getIssueId())) {
    		int number = Integer.parseInt(zjTzPppTerm.getIssueId());
    		ZjTzPppTerm zjTzPppTermSelect = new ZjTzPppTerm();
    		zjTzPppTermSelect.setProjectId(zjTzPppTerm.getProjectId()); 
    		zjTzPppTermSelect.setSubprojectInfoId(zjTzPppTerm.getSubprojectInfoId());
    		zjTzPppTermSelect.setIssueId(zjTzPppTerm.getIssueId());
    		List<ZjTzPppTerm> zjTzPppTermSelectList = zjTzPppTermMapper.selectByZjTzPppTermList(zjTzPppTermSelect);
    		if(zjTzPppTermSelectList != null && zjTzPppTermSelectList.size() >0) {
    			return repEntity.layerMessage("no", "?????????????????????????????????");
    		}
			ZjTzPppTermBase termBaseReply = new ZjTzPppTermBase();
			List<ZjTzPppTermBase> zjTzPppTermBaseReplyList =  zjTzPppTermBaseMapper.getByZjTzPppTermBaseTermList(termBaseReply);
			if (zjTzPppTermBaseReplyList != null && zjTzPppTermBaseReplyList.size() >0) {
				//???????????????
				if(StrUtil.equals(zjTzPppTerm.getIssueId(), "0")) {
					for (ZjTzPppTermBase termBase : zjTzPppTermBaseReplyList) {
						ZjTzPppTermReply dbzjTzPppTermReply = new ZjTzPppTermReply();
						// ppp????????????id
						dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());
						dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
						dbzjTzPppTermReply.setPppTermBaseId(termBase.getPppTermBaseId());
						// ????????????????????????copeid
						dbzjTzPppTermReply.setCodePid(termBase.getCodePid());
						// ????????????
						dbzjTzPppTermReply.setAnalysisKey(termBase.getAnalysisKeyName());
						// ????????????
						dbzjTzPppTermReply.setKeyTerm(termBase.getKeyTerm());
						// ??????????????????
						dbzjTzPppTermReply.setKeyAnalysisContent(termBase.getKeyAnalysisContent());
						//??????
						dbzjTzPppTermReply.setOrderFlag(termBase.getOrderFlag());
						//
						dbzjTzPppTermReply.setChangeFlag1("0");
						dbzjTzPppTermReply.setChangeFlag2("0");
						dbzjTzPppTermReply.setChangeFlag3("0");
						dbzjTzPppTermReply.setChangeFlag4("0");
						dbzjTzPppTermReply.setChangeFlag5("0");
						dbzjTzPppTermReply.setChangeFlag6("0");
						dbzjTzPppTermReply.setChangeFlag7("0");
						// ??????
						dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
						flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
					}
				}else {
					//?????????????????????????????????????????????????????????????????????
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
							dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());// ppp????????????id
							dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
							dbzjTzPppTermReply.setPppTermBaseId(base.getPppTermBaseId());
							dbzjTzPppTermReply.setCodePid(base.getCodePid());// ????????????????????????copeid

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
							if(copyBaseFlag) {//===========================================?????????copy
								// ????????????
								dbzjTzPppTermReply.setAnalysisKey(analysisKey);
								// ????????????
								dbzjTzPppTermReply.setKeyTerm(keyTerm);
								// ??????????????????
								dbzjTzPppTermReply.setKeyAnalysisContent(keyAnalysisContent);

								// ?????????????????????
								dbzjTzPppTermReply.setNumberContent(numberContent);
								// ????????????
								dbzjTzPppTermReply.setTerm(term);
								// ??????????????????
								dbzjTzPppTermReply.setRiskFlag(riskFlag);
								// ????????????
								dbzjTzPppTermReply.setMeasure(measure);
								// ????????????????????????
								dbzjTzPppTermReply.setNegotiateFlag(negotiateFlag);
								// ????????????????????????
								dbzjTzPppTermReply.setDeptAndLeader(deptAndLeader);
								// ??????????????????
								dbzjTzPppTermReply.setImplement(implement);
							}else {//=======================================================?????????base
								// ????????????
								dbzjTzPppTermReply.setAnalysisKey(base.getAnalysisKey());
								// ????????????
								dbzjTzPppTermReply.setKeyTerm(base.getKeyTerm());
								// ??????????????????
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
							// ??????
							dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
							flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
						}
					}else {
						return repEntity.layerMessage("no", "?????????????????????");
					}
				}
			}
    		String openBankName = baseCodeService.getBaseCodeItemName("fenXiQiCi", zjTzPppTerm.getIssueId());
    		zjTzPppTerm.setIssueName(openBankName);
    	}
    	zjTzPppTerm.setStatusId("0");
    	zjTzPppTerm.setStatusName("?????????");
    	zjTzPppTerm.setCreateUserInfo(userKey, realName);
    	flag = zjTzPppTermMapper.insert(zjTzPppTerm);
    	// ??????list
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
        	// 0(?????????),1(?????????)2(?????????)
        	if(StrUtil.equals(dbzjTzPppTerm.getStatusId(), "0") || StrUtil.equals(dbzjTzPppTerm.getStatusId(), "2")) {

        	}else {
        		return repEntity.layerMessage("no", "???????????????????????????????????????"); 
        	}
//           // ??????id
//           dbzjTzPppTerm.setProjectId(zjTzPppTerm.getProjectId());
//           // ??????name
//           dbzjTzPppTerm.setProjectName(zjTzPppTerm.getProjectName());
//           //????????????id
//           dbzjTzPppTerm.setIssueId(zjTzPppTerm.getIssueId());
//           // ????????????name
//           dbzjTzPppTerm.setIssueName(zjTzPppTerm.getIssueName());
//           // ?????????????????????
//           dbzjTzPppTerm.setNumberContent(zjTzPppTerm.getNumberContent());
//           // ????????????
//           dbzjTzPppTerm.setTerm(zjTzPppTerm.getTerm());
//           // ??????????????????
//           dbzjTzPppTerm.setRiskFlag(zjTzPppTerm.getRiskFlag());
//           // ????????????
//           dbzjTzPppTerm.setMeasure(zjTzPppTerm.getMeasure());
//           // ????????????????????????
//           dbzjTzPppTerm.setNegotiateFlag(zjTzPppTerm.getNegotiateFlag());
//           // ????????????????????????
//           dbzjTzPppTerm.setDeptAndLeader(zjTzPppTerm.getDeptAndLeader());
//           // ??????????????????
//           dbzjTzPppTerm.setImplement(zjTzPppTerm.getImplement());
           // ????????????
           dbzjTzPppTerm.setRegisterDate(zjTzPppTerm.getRegisterDate());
           // ????????????
           dbzjTzPppTerm.setRegisterPerson(zjTzPppTerm.getRegisterPerson());
           // ??????
           dbzjTzPppTerm.setModifyUserInfo(userKey, realName);
           flag = zjTzPppTermMapper.updateByPrimaryKey(dbzjTzPppTerm);
           
           // ?????????????????????
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
        // ??????
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
    				return repEntity.layerMessage("no", "?????????????????????????????????????????????"); 
    			}
    		}
    		//??????
    		for (ZjTzPppTerm zjTzPppTerm : zjTzPppTermList) {
    			// ????????????
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherId(zjTzPppTerm.getPppTermId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			//????????????
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
    	// ??????
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
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}
			}
        	ZjTzPppTerm zjTzRules = new ZjTzPppTerm();
        	zjTzRules.setStatusId("1");
        	zjTzRules.setStatusName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPppTermMapper.batchRecallZjTzPppTerm(zjTzPppTermList, zjTzRules);
        }
        // ??????
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
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
        		}
        	}
        	ZjTzPppTerm zjTzRules = new ZjTzPppTerm();
        	zjTzRules.setStatusId("2");
        	zjTzRules.setStatusName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPppTermMapper.batchRecallZjTzPppTerm(zjTzPppTermList, zjTzRules);
        }
        // ??????
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
    			//??????????????????
    			ZjTzPppTermReply delReply = new ZjTzPppTermReply();
    			delReply.setPppTermId(zjTzPppTerm.getPppTermId());
    			List<ZjTzPppTermReply> delRelpyList = zjTzPppTermReplyMapper.selectByZjTzPppTermReplyList(delReply);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				delReply.setModifyUserInfo(userKey, realName);
    				flag = zjTzPppTermReplyMapper.batchDeleteUpdateZjTzPppTermReply(delRelpyList, delReply);
    			}

    			for (ZjTzPppTermReply termBase : zjTzPppTerm.getZjTzPppTermReplyList()) {
    				ZjTzPppTermReply dbzjTzPppTermReply = new ZjTzPppTermReply();
    				// ppp????????????id
    				dbzjTzPppTermReply.setPppTermReplyId(UuidUtil.generate());
    				// ppp????????????id
    				dbzjTzPppTermReply.setPppTermId(zjTzPppTerm.getPppTermId());
    				//
    				dbzjTzPppTermReply.setPppTermBaseId(termBase.getPppTermBaseId());
    				// ????????????????????????copeid
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
    				// ????????????
    				dbzjTzPppTermReply.setAnalysisKey(termBase.getAnalysisKey());
    				// ????????????
    				dbzjTzPppTermReply.setKeyTerm(termBase.getKeyTerm());
    				// ??????????????????
    				dbzjTzPppTermReply.setKeyAnalysisContent(termBase.getKeyAnalysisContent());
    				// ?????????????????????
    				dbzjTzPppTermReply.setNumberContent(termBase.getNumberContent());
    				// ????????????
    				dbzjTzPppTermReply.setTerm(termBase.getTerm());
    				// ??????????????????
    				dbzjTzPppTermReply.setRiskFlag(termBase.getRiskFlag());
    				// ????????????
    				dbzjTzPppTermReply.setMeasure(termBase.getMeasure());
    				// ????????????????????????
    				dbzjTzPppTermReply.setNegotiateFlag(termBase.getNegotiateFlag());
    				// ????????????????????????
    				dbzjTzPppTermReply.setDeptAndLeader(termBase.getDeptAndLeader());
    				// ??????????????????
    				dbzjTzPppTermReply.setImplement(termBase.getImplement());
    				//??????
    				dbzjTzPppTermReply.setOrderFlag(termBase.getOrderFlag());
    				// ??????
    				dbzjTzPppTermReply.setCreateUserInfo(userKey, realName);
    				flag = zjTzPppTermReplyMapper.insert(dbzjTzPppTermReply);
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzPppTerm);
    	}
    }

	

	
    
}