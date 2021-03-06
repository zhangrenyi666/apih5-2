package com.apih5.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzComprehensiveSupMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzSupContentMapper;
import com.apih5.mybatis.pojo.ZjTzComprehensiveSup;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzSupContent;
import com.apih5.service.ZjTzComprehensiveSupService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzComprehensiveSupService")
public class ZjTzComprehensiveSupServiceImpl implements ZjTzComprehensiveSupService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzComprehensiveSupMapper zjTzComprehensiveSupMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzSupContentMapper zjTzSupContentMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzComprehensiveSupListByCondition(ZjTzComprehensiveSup zjTzComprehensiveSup) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userId = TokenUtils.getUserId(request);
         String ext1 = TokenUtils.getExt1(request);
    	if (zjTzComprehensiveSup == null) {
            zjTzComprehensiveSup = new ZjTzComprehensiveSup();
        }
        
    	 // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//?????????????????????????????????????????????
        	if(StrUtil.equals("2", ext1)) {
        		
        	}else {
        		//??????  ??????????????????????????????
        		zjTzComprehensiveSup.setUnExt1SeeFlag("??????  ??????????????????????????????");
        	}
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
            	zjTzComprehensiveSup.setProjectId("");
            	zjTzComprehensiveSup.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
            	zjTzComprehensiveSup.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzComprehensiveSup.getPage(),zjTzComprehensiveSup.getLimit());
        // ????????????
        List<ZjTzComprehensiveSup> zjTzComprehensiveSupList = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(zjTzComprehensiveSup);
        for (ZjTzComprehensiveSup zjTzComprehensiveSup2 : zjTzComprehensiveSupList) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzComprehensiveSup2.getComprehensiveSupId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setZjTzFileList(zjTzFileList);
        	//???????????????
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setReplyFileList(replyFileList);
        	//????????????
        	ZjTzSupContent zjTzSupContent = new ZjTzSupContent();
        	zjTzSupContent.setComprehensiveSupId(zjTzComprehensiveSup2.getComprehensiveSupId());
        	List<ZjTzSupContent> zjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(zjTzSupContent);
        	for (ZjTzSupContent zjTzSupContent2 : zjTzSupContentList) {
            	ZjTzFile zjTzFileSupContent = new ZjTzFile();
            	zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
            	List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
            	zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);
    		}
        	zjTzComprehensiveSup2.setZjTzSupContentList(zjTzSupContentList);
        }
        // ??????????????????
        PageInfo<ZjTzComprehensiveSup> p = new PageInfo<>(zjTzComprehensiveSupList);

        return repEntity.okList(zjTzComprehensiveSupList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzComprehensiveSupDetails(ZjTzComprehensiveSup zjTzComprehensiveSup) {
        if (zjTzComprehensiveSup == null) {
            zjTzComprehensiveSup = new ZjTzComprehensiveSup();
        }
        // ????????????
        ZjTzComprehensiveSup dbZjTzComprehensiveSup = zjTzComprehensiveSupMapper.selectByPrimaryKey(zjTzComprehensiveSup.getComprehensiveSupId());
        // ????????????
        if (dbZjTzComprehensiveSup != null) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzComprehensiveSup.getComprehensiveSupId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzComprehensiveSup.setZjTzFileList(zjTzFileList);
        	//???????????????
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzComprehensiveSup.setReplyFileList(replyFileList);
        	//????????????
        	ZjTzSupContent zjTzSupContent = new ZjTzSupContent();
        	zjTzSupContent.setComprehensiveSupId(dbZjTzComprehensiveSup.getComprehensiveSupId());
        	List<ZjTzSupContent> zjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(zjTzSupContent);
        	for (ZjTzSupContent zjTzSupContent2 : zjTzSupContentList) {
            	ZjTzFile zjTzFileSupContent = new ZjTzFile();
            	zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
            	List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
            	zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);
    		}
        	dbZjTzComprehensiveSup.setZjTzSupContentList(zjTzSupContentList);
            return repEntity.ok(dbZjTzComprehensiveSup);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        
        ZjTzProManage manage =  zjTzProManageMapper.selectByPrimaryKey(zjTzComprehensiveSup.getProjectId());
        if(manage != null) {
        	zjTzComprehensiveSup.setProjectName(manage.getProjectName());
        }
        //????????????????????????
        ZjTzComprehensiveSup advistoryUnitStandard = new ZjTzComprehensiveSup();
    	if(zjTzComprehensiveSup.getCheckDate() != null) {
    		advistoryUnitStandard.setCheckDate(zjTzComprehensiveSup.getCheckDate());
    	}
        advistoryUnitStandard.setProjectId(zjTzComprehensiveSup.getProjectId());
    	List<ZjTzComprehensiveSup> advistoryUnitStandards = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(advistoryUnitStandard);
    	if(advistoryUnitStandards != null && advistoryUnitStandards.size() >0) {
    		 return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????");
    	}
        zjTzComprehensiveSup.setComprehensiveSupId(UuidUtil.generate());
        // ??????????????????id
        zjTzComprehensiveSup.setCorrectiveId("0");
        // ??????????????????name
        zjTzComprehensiveSup.setCorrectiveName("?????????");
//        // ??????????????????id
//        zjTzComprehensiveSup.setCorrectiveCompleteId("0");
//        // ??????????????????name
//        zjTzComprehensiveSup.setCorrectiveCompleteName("???????????????");
        zjTzComprehensiveSup.setCreateUserInfo(userKey, realName);
        int flag = zjTzComprehensiveSupMapper.insert(zjTzComprehensiveSup);
        // ??????list
    	List<ZjTzFile> zjTzFileList = zjTzComprehensiveSup.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherType("1");
    			zjTzFile.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//????????????list
    	if(zjTzComprehensiveSup.getZjTzSupContentList() != null && zjTzComprehensiveSup.getZjTzSupContentList().size() >0) {
    		for (ZjTzSupContent zjTzSupContent : zjTzComprehensiveSup.getZjTzSupContentList()) {
        		zjTzSupContent.setSupContentId(UuidUtil.generate());
        		// ?????????????????????????????????
        		zjTzSupContent.setComprehensiveSupId(zjTzComprehensiveSup.getComprehensiveSupId());
        	    //??????
            	if (StrUtil.isNotEmpty(zjTzSupContent.getTypeId())) {
            		String openBankName = baseCodeService.getBaseCodeItemName("leiXing", zjTzSupContent.getTypeId());
            		zjTzSupContent.setTypeName(openBankName);
            	}
                // ??????????????????id
            	if(StrUtil.equals(zjTzSupContent.getNeedCorrectiveId(),"1")) {
            		zjTzSupContent.setNeedCorrectiveName("???");
            	}else {
            		zjTzSupContent.setNeedCorrectiveName("???");
            	}
        		zjTzSupContent.setCreateUserInfo(userKey, realName);
        		flag = zjTzSupContentMapper.insert(zjTzSupContent);
        	}
    	}
    	
    	
    	
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzComprehensiveSup);
        }
    }

    @Override
    public ResponseEntity updateZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzComprehensiveSup dbzjTzComprehensiveSup = zjTzComprehensiveSupMapper.selectByPrimaryKey(zjTzComprehensiveSup.getComprehensiveSupId());
    	if (dbzjTzComprehensiveSup != null && StrUtil.isNotEmpty(dbzjTzComprehensiveSup.getComprehensiveSupId())) {
    		if(StrUtil.equals(dbzjTzComprehensiveSup.getCorrectiveId(), "0")){			
    			// ?????? 
    			dbzjTzComprehensiveSup.setTitle(zjTzComprehensiveSup.getTitle());
    			// ????????????
    			dbzjTzComprehensiveSup.setCheckDate(zjTzComprehensiveSup.getCheckDate());
    			// ????????????id
    			dbzjTzComprehensiveSup.setTrusteeCompanyId(zjTzComprehensiveSup.getTrusteeCompanyId());
    			// ????????????name
    			dbzjTzComprehensiveSup.setTrusteeCompanyName(zjTzComprehensiveSup.getTrusteeCompanyName());
    			// ?????????????????????
    			dbzjTzComprehensiveSup.setInspectDeptAndPerson(zjTzComprehensiveSup.getInspectDeptAndPerson());
    			// ????????????
    			dbzjTzComprehensiveSup.setRegisterDate(zjTzComprehensiveSup.getRegisterDate());
    			// ????????????
    			dbzjTzComprehensiveSup.setRegisterPerson(zjTzComprehensiveSup.getRegisterPerson());
    			// ??????
    			dbzjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
    			flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(dbzjTzComprehensiveSup);
    			// ?????????????????????
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherType("1");
    			zjTzFileSelect.setOtherId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			// ??????list
    			List<ZjTzFile> zjTzFileList = zjTzComprehensiveSup.getZjTzFileList();
    			if(zjTzFileList != null && zjTzFileList.size()>0) {
    				for(ZjTzFile zjTzFile:zjTzFileList) {
    					zjTzFile.setOtherType("1");
    					zjTzFile.setUid(UuidUtil.generate());
    					zjTzFile.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
    					zjTzFile.setCreateUserInfo(userKey, realName);
    					zjTzFileMapper.insert(zjTzFile);
    				}
    			}
    			//????????????list=??????????????????
    					ZjTzSupContent delZjTzSupContent = new ZjTzSupContent();
    					delZjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    					List<ZjTzSupContent> delZjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(delZjTzSupContent);
    					if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
    						delZjTzSupContent.setModifyUserInfo(userKey, realName);
    						zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(delZjTzSupContentList, delZjTzSupContent);
    					}
    					//????????????list
    					List<ZjTzSupContent> zjTzSupContentList = zjTzComprehensiveSup.getZjTzSupContentList();
    					for (ZjTzSupContent zjTzSupContent : zjTzSupContentList) {
    						zjTzSupContent.setSupContentId(UuidUtil.generate());
    						// ?????????????????????????????????
    						zjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    						//??????
    						if (StrUtil.isNotEmpty(zjTzSupContent.getTypeId())) {
    							String openBankName = baseCodeService.getBaseCodeItemName("leiXing", zjTzSupContent.getTypeId());
    							zjTzSupContent.setTypeName(openBankName);
    						}
    						// ??????????????????id
    						if(StrUtil.equals(zjTzSupContent.getNeedCorrectiveId(),"1")) {
    							zjTzSupContent.setNeedCorrectiveName("???");
    						}else {
    							zjTzSupContent.setNeedCorrectiveName("???");
    						}
    						zjTzSupContent.setCreateUserInfo(userKey, realName);
    						flag = zjTzSupContentMapper.insert(zjTzSupContent);
    					}
    		}else {
    			return repEntity.layerMessage("no", "??????????????????????????????????????????????????????");
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzComprehensiveSup);
    	}
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzComprehensiveSup(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzComprehensiveSupList != null && zjTzComprehensiveSupList.size() > 0) {
        	for (ZjTzComprehensiveSup zjTzRules : zjTzComprehensiveSupList) {
				if(StrUtil.equals(zjTzRules.getCorrectiveId(), "1") ) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
				}
			}
        	for (ZjTzComprehensiveSup zjTzComprehensiveSup : zjTzComprehensiveSupList) {
        		// ????????????
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//??????????????????
        		ZjTzSupContent supContent = new ZjTzSupContent();
        		supContent.setComprehensiveSupId(zjTzComprehensiveSup.getComprehensiveSupId());
        		List<ZjTzSupContent> zjTzQualities = zjTzSupContentMapper.selectByZjTzSupContentList(supContent);
        		if(zjTzQualities != null && zjTzQualities.size() >0) {
        			supContent.setModifyUserInfo(userKey, realName);
        			zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(zjTzQualities, supContent);
        		}
        	}
        	ZjTzComprehensiveSup zjTzComprehensiveSup = new ZjTzComprehensiveSup();
        	zjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
        	flag = zjTzComprehensiveSupMapper.batchDeleteUpdateZjTzComprehensiveSup(zjTzComprehensiveSupList, zjTzComprehensiveSup);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzComprehensiveSupList);
        }
    }

	@Override
	public ResponseEntity batchRecallZjTzComprehensiveSup(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzComprehensiveSupList != null && zjTzComprehensiveSupList.size() > 0) {
        	for (ZjTzComprehensiveSup sup : zjTzComprehensiveSupList) {
        		if(StrUtil.equals(sup.getCorrectiveId(), "0") || StrUtil.equals(sup.getCorrectiveId(), "2")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????????????????");
        		}
        		if(StrUtil.equals(sup.getCorrectiveId(), "1")) {
        			sup.setCorrectiveId("0");
        			sup.setCorrectiveName("?????????");
        			sup.setCorrectiveUserKey("");
        			sup.setCorrectiveUserName("");
        			zjTzComprehensiveSupMapper.updateByPrimaryKey(sup);
        			
        			ZjTzSupContent record = new ZjTzSupContent();
        			record.setComprehensiveSupId(sup.getComprehensiveSupId());
        			List<ZjTzSupContent> contentList = zjTzSupContentMapper.selectByZjTzSupContentList(record);
        			for (ZjTzSupContent content : contentList) {
        				content.setCorrectiveDate(null);
        				content.setCorrectiveCase("");
        				content.setModifyUserInfo(userKey, realName);
        				flag = zjTzSupContentMapper.updateByPrimaryKey(content);
					}
        		}
        	}
        	ZjTzComprehensiveSup zjTzComprehensiveSup = new ZjTzComprehensiveSup();
        	zjTzComprehensiveSup.setCorrectiveName("?????????");
        	zjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
        	flag = zjTzComprehensiveSupMapper.batchRecallZjTzComprehensiveSup(zjTzComprehensiveSupList, zjTzComprehensiveSup);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzComprehensiveSupList);
        }
	}

	@Override
	public ResponseEntity correctiveZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzComprehensiveSup dbzjTzComprehensiveSup = zjTzComprehensiveSupMapper.selectByPrimaryKey(zjTzComprehensiveSup.getComprehensiveSupId());
		if (dbzjTzComprehensiveSup != null && StrUtil.isNotEmpty(dbzjTzComprehensiveSup.getComprehensiveSupId())) {
			if(StrUtil.equals(dbzjTzComprehensiveSup.getCorrectiveId(), "1")) {
				return repEntity.layerMessage("no", "???????????????????????????????????????");
    		}else {
				// ??????????????????id
				dbzjTzComprehensiveSup.setCorrectiveId("1");
				// ??????????????????name
				dbzjTzComprehensiveSup.setCorrectiveName("?????????");
				dbzjTzComprehensiveSup.setContentDesc(zjTzComprehensiveSup.getContentDesc());
				dbzjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
				flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(dbzjTzComprehensiveSup);
    		}
			
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorUpdate();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzComprehensiveSup);
		}
	}

	@Override
	public ResponseEntity getZjTzComprehensiveSupReplyList(ZjTzComprehensiveSup zjTzComprehensiveSup) {
		if (zjTzComprehensiveSup == null) {
			zjTzComprehensiveSup = new ZjTzComprehensiveSup();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		String ext1 = TokenUtils.getExt1(request);
		// ????????????????????????
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// ???????????????????????????????????????sql?????????
			if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
				zjTzComprehensiveSup.setProjectId("");
				zjTzComprehensiveSup.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// ???????????????
			if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
				zjTzComprehensiveSup.setProjectId("");
			}
		}
		
		
		zjTzComprehensiveSup.setCorrectiveId("1");//??????????????????????????????????????????
		// ????????????
		PageHelper.startPage(zjTzComprehensiveSup.getPage(),zjTzComprehensiveSup.getLimit());
		// ????????????
		List<ZjTzComprehensiveSup> zjTzComprehensiveSupList = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(zjTzComprehensiveSup);
		for (ZjTzComprehensiveSup zjTzComprehensiveSup2 : zjTzComprehensiveSupList) {
			//??????
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherId(zjTzComprehensiveSup2.getComprehensiveSupId());
			zjTzFileSelect.setOtherType("1");
			List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			zjTzComprehensiveSup2.setZjTzFileList(zjTzFileList);
			//???????????????
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setReplyFileList(replyFileList);
			//????????????
			ZjTzSupContent zjTzSupContent = new ZjTzSupContent();
			zjTzSupContent.setComprehensiveSupId(zjTzComprehensiveSup2.getComprehensiveSupId());
			List<ZjTzSupContent> zjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(zjTzSupContent);
			for (ZjTzSupContent zjTzSupContent2 : zjTzSupContentList) {
				ZjTzFile zjTzFileSupContent = new ZjTzFile();
				zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
				List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
				zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);
			}
			zjTzComprehensiveSup2.setZjTzSupContentList(zjTzSupContentList);
		}
		// ??????????????????
		PageInfo<ZjTzComprehensiveSup> p = new PageInfo<>(zjTzComprehensiveSupList);

		return repEntity.okList(zjTzComprehensiveSupList, p.getTotal());
	}

	@Override
	public ResponseEntity batchExportZjTzComprehensiveSupFile(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzComprehensiveSup total : zjTzComprehensiveSupList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getComprehensiveSupId());
			List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
			if(files != null && files.size() >0) {
				fileList.addAll(files);
			}
		}
		if(fileList != null && fileList.size() >0) {
		    String uuidFolder = "temp/" + UuidUtil.generate();
		    // ????????????
            File[] files = new File[fileList.size()];
            for (int i=0; i<fileList.size(); i++) {
                // ??????????????????????????????????????????????????????
                Random rd = new Random();
                int r = rd.nextInt(200)%(111) + 100;
                String fileUrl = fileList.get(i).getRelativeUrl();
                String pathFile = Apih5Properties.getFilePath() + fileUrl;
                FileUtil.mkdir(Apih5Properties.getFilePath() + uuidFolder);
                String newPathFile = Apih5Properties.getFilePath() + uuidFolder + "/"  + r + "-" + fileList.get(i).getName();
                try {
                    FileUtil.copy(pathFile, newPathFile, true);
                    files[i] = FileUtil.file(newPathFile);
                }catch (Exception e) {
                	
                }
            }
            // zip????????????
            ZipUtil.zip(FileUtil.file(HttpUtil.getUploadPath(uuidFolder) + zipName), false, files);
            String returnPath = HttpUtil.getUploadPathWeb(request, uuidFolder) + zipName;
            return repEntity.ok(returnPath);
		}else {
			return repEntity.layerMessage("no", "??????????????????");
		}
	}

	@Override
	public ResponseEntity replyZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzComprehensiveSup dbzjTzComprehensiveSup = zjTzComprehensiveSupMapper.selectByPrimaryKey(zjTzComprehensiveSup.getComprehensiveSupId());
		if (dbzjTzComprehensiveSup != null && StrUtil.isNotEmpty(dbzjTzComprehensiveSup.getComprehensiveSupId())) {
			//????????????list=??????????????????
			ZjTzSupContent delZjTzSupContent = new ZjTzSupContent();
			delZjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
			List<ZjTzSupContent> delZjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(delZjTzSupContent);
			if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
				delZjTzSupContent.setModifyUserInfo(userKey, realName);
				zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(delZjTzSupContentList, delZjTzSupContent);
			}
			//????????????list
			List<ZjTzSupContent> zjTzSupContentList = zjTzComprehensiveSup.getZjTzSupContentList();
			for (ZjTzSupContent zjTzSupContent : zjTzSupContentList) {
				zjTzSupContent.setSupContentId(UuidUtil.generate());
				// ?????????????????????????????????
				zjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
				// ??????????????????
				zjTzSupContent.setCorrectiveCase(zjTzSupContent.getCorrectiveCase());
				// ??????????????????
				zjTzSupContent.setCorrectiveDate(zjTzSupContent.getCorrectiveDate());
				zjTzSupContent.setCreateUserInfo(userKey, realName);
				flag = zjTzSupContentMapper.insert(zjTzSupContent);
			}
			//?????????????????????????????????
			ZjTzComprehensiveSup updateSup =zjTzComprehensiveSupMapper.selectByPrimaryKey(dbzjTzComprehensiveSup.getComprehensiveSupId());
			if(updateSup != null && StrUtil.isNotEmpty(updateSup.getComprehensiveSupId())) {
				updateSup.setCorrectiveId("2");
				updateSup.setCorrectiveName("?????????");
				updateSup.setModifyUserInfo(userKey, realName);
				flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(updateSup);
			}
			//???????????????????????????= ????????????????????????
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherType("2");//???????????????
			zjTzFileSelect.setOtherId(dbzjTzComprehensiveSup.getComprehensiveSupId());
			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
				zjTzFileSelect.setModifyUserInfo(userKey, realName);
				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
			}
			// ??????list
			if(zjTzComprehensiveSup.getReplyFileList() != null && zjTzComprehensiveSup.getReplyFileList().size()>0) {
				for(ZjTzFile zjTzFile:zjTzComprehensiveSup.getReplyFileList()) {
					zjTzFile.setUid(UuidUtil.generate());
					zjTzFile.setOtherType("2");//???????????????
					zjTzFile.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
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
			return repEntity.ok("sys.data.update",zjTzComprehensiveSup);
		}
	}
	
}