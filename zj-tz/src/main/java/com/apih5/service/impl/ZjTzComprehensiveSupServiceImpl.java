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
        
    	 // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//托管公司身份的人，所有状态可见
        	if(StrUtil.equals("2", ext1)) {
        		
        	}else {
        		//项目  可以看已下达和已落实
        		zjTzComprehensiveSup.setUnExt1SeeFlag("项目  可以看已下达和已落实");
        	}
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
            	zjTzComprehensiveSup.setProjectId("");
            	zjTzComprehensiveSup.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
            	zjTzComprehensiveSup.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzComprehensiveSup.getPage(),zjTzComprehensiveSup.getLimit());
        // 获取数据
        List<ZjTzComprehensiveSup> zjTzComprehensiveSupList = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(zjTzComprehensiveSup);
        for (ZjTzComprehensiveSup zjTzComprehensiveSup2 : zjTzComprehensiveSupList) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzComprehensiveSup2.getComprehensiveSupId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setZjTzFileList(zjTzFileList);
        	//回复的附件
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setReplyFileList(replyFileList);
        	//督察内容
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
        // 得到分页信息
        PageInfo<ZjTzComprehensiveSup> p = new PageInfo<>(zjTzComprehensiveSupList);

        return repEntity.okList(zjTzComprehensiveSupList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzComprehensiveSupDetails(ZjTzComprehensiveSup zjTzComprehensiveSup) {
        if (zjTzComprehensiveSup == null) {
            zjTzComprehensiveSup = new ZjTzComprehensiveSup();
        }
        // 获取数据
        ZjTzComprehensiveSup dbZjTzComprehensiveSup = zjTzComprehensiveSupMapper.selectByPrimaryKey(zjTzComprehensiveSup.getComprehensiveSupId());
        // 数据存在
        if (dbZjTzComprehensiveSup != null) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzComprehensiveSup.getComprehensiveSupId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzComprehensiveSup.setZjTzFileList(zjTzFileList);
        	//回复的附件
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzComprehensiveSup.setReplyFileList(replyFileList);
        	//督察内容
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
            return repEntity.layerMessage("no", "无数据！");
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
        //托管项目重复校验
        ZjTzComprehensiveSup advistoryUnitStandard = new ZjTzComprehensiveSup();
    	if(zjTzComprehensiveSup.getCheckDate() != null) {
    		advistoryUnitStandard.setCheckDate(zjTzComprehensiveSup.getCheckDate());
    	}
        advistoryUnitStandard.setProjectId(zjTzComprehensiveSup.getProjectId());
    	List<ZjTzComprehensiveSup> advistoryUnitStandards = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(advistoryUnitStandard);
    	if(advistoryUnitStandards != null && advistoryUnitStandards.size() >0) {
    		 return repEntity.layerMessage("no", "该检查日期下的托管项目已存在，请重新输入！");
    	}
        zjTzComprehensiveSup.setComprehensiveSupId(UuidUtil.generate());
        // 是否整改下达id
        zjTzComprehensiveSup.setCorrectiveId("0");
        // 是否整改下达name
        zjTzComprehensiveSup.setCorrectiveName("未下达");
//        // 是否整改完成id
//        zjTzComprehensiveSup.setCorrectiveCompleteId("0");
//        // 是否整改完成name
//        zjTzComprehensiveSup.setCorrectiveCompleteName("未整改完成");
        zjTzComprehensiveSup.setCreateUserInfo(userKey, realName);
        int flag = zjTzComprehensiveSupMapper.insert(zjTzComprehensiveSup);
        // 附件list
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
    	//整改内容list
    	if(zjTzComprehensiveSup.getZjTzSupContentList() != null && zjTzComprehensiveSup.getZjTzSupContentList().size() >0) {
    		for (ZjTzSupContent zjTzSupContent : zjTzComprehensiveSup.getZjTzSupContentList()) {
        		zjTzSupContent.setSupContentId(UuidUtil.generate());
        		// 项目综合督、查综合督导
        		zjTzSupContent.setComprehensiveSupId(zjTzComprehensiveSup.getComprehensiveSupId());
        	    //类型
            	if (StrUtil.isNotEmpty(zjTzSupContent.getTypeId())) {
            		String openBankName = baseCodeService.getBaseCodeItemName("leiXing", zjTzSupContent.getTypeId());
            		zjTzSupContent.setTypeName(openBankName);
            	}
                // 是否需要整改id
            	if(StrUtil.equals(zjTzSupContent.getNeedCorrectiveId(),"1")) {
            		zjTzSupContent.setNeedCorrectiveName("是");
            	}else {
            		zjTzSupContent.setNeedCorrectiveName("否");
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
    			// 标题 
    			dbzjTzComprehensiveSup.setTitle(zjTzComprehensiveSup.getTitle());
    			// 检查日期
    			dbzjTzComprehensiveSup.setCheckDate(zjTzComprehensiveSup.getCheckDate());
    			// 托管公司id
    			dbzjTzComprehensiveSup.setTrusteeCompanyId(zjTzComprehensiveSup.getTrusteeCompanyId());
    			// 托管公司name
    			dbzjTzComprehensiveSup.setTrusteeCompanyName(zjTzComprehensiveSup.getTrusteeCompanyName());
    			// 检查部门及人员
    			dbzjTzComprehensiveSup.setInspectDeptAndPerson(zjTzComprehensiveSup.getInspectDeptAndPerson());
    			// 登记日期
    			dbzjTzComprehensiveSup.setRegisterDate(zjTzComprehensiveSup.getRegisterDate());
    			// 登记用户
    			dbzjTzComprehensiveSup.setRegisterPerson(zjTzComprehensiveSup.getRegisterPerson());
    			// 共通
    			dbzjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
    			flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(dbzjTzComprehensiveSup);
    			// 删除附件再新增
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherType("1");
    			zjTzFileSelect.setOtherId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			// 附件list
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
    			//督察内容list=先删除再新增
    					ZjTzSupContent delZjTzSupContent = new ZjTzSupContent();
    					delZjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    					List<ZjTzSupContent> delZjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(delZjTzSupContent);
    					if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
    						delZjTzSupContent.setModifyUserInfo(userKey, realName);
    						zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(delZjTzSupContentList, delZjTzSupContent);
    					}
    					//整改内容list
    					List<ZjTzSupContent> zjTzSupContentList = zjTzComprehensiveSup.getZjTzSupContentList();
    					for (ZjTzSupContent zjTzSupContent : zjTzSupContentList) {
    						zjTzSupContent.setSupContentId(UuidUtil.generate());
    						// 项目综合督、查综合督导
    						zjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
    						//类型
    						if (StrUtil.isNotEmpty(zjTzSupContent.getTypeId())) {
    							String openBankName = baseCodeService.getBaseCodeItemName("leiXing", zjTzSupContent.getTypeId());
    							zjTzSupContent.setTypeName(openBankName);
    						}
    						// 是否需要整改id
    						if(StrUtil.equals(zjTzSupContent.getNeedCorrectiveId(),"1")) {
    							zjTzSupContent.setNeedCorrectiveName("是");
    						}else {
    							zjTzSupContent.setNeedCorrectiveName("否");
    						}
    						zjTzSupContent.setCreateUserInfo(userKey, realName);
    						flag = zjTzSupContentMapper.insert(zjTzSupContent);
    					}
    		}else {
    			return repEntity.layerMessage("no", "已下达或已落实的不能修改，请先撤回！");
    		}
    	}
    	// 失败
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
					 return repEntity.layerMessage("no", "包含已下达的数据不能删除，请重新选择！");
				}
			}
        	for (ZjTzComprehensiveSup zjTzComprehensiveSup : zjTzComprehensiveSupList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//删除督察内容
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
        // 失败
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
        			return repEntity.layerMessage("no", "包含未下达和已落实的数据不能撤回，请重新选择！");
        		}
        		if(StrUtil.equals(sup.getCorrectiveId(), "1")) {
        			sup.setCorrectiveId("0");
        			sup.setCorrectiveName("未下达");
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
        	zjTzComprehensiveSup.setCorrectiveName("未下达");
        	zjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
        	flag = zjTzComprehensiveSupMapper.batchRecallZjTzComprehensiveSup(zjTzComprehensiveSupList, zjTzComprehensiveSup);
        }
        // 失败
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
				return repEntity.layerMessage("no", "已经整改下达，请重新选择！");
    		}else {
				// 是否整改下达id
				dbzjTzComprehensiveSup.setCorrectiveId("1");
				// 是否整改下达name
				dbzjTzComprehensiveSup.setCorrectiveName("已下达");
				dbzjTzComprehensiveSup.setContentDesc(zjTzComprehensiveSup.getContentDesc());
				dbzjTzComprehensiveSup.setModifyUserInfo(userKey, realName);
				flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(dbzjTzComprehensiveSup);
    		}
			
		}
		// 失败
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
		// 不是集团的人员时
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// 选择全部项目是，通过拼接的sql去查询
			if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
				zjTzComprehensiveSup.setProjectId("");
				zjTzComprehensiveSup.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// 集团人员时
			if(StrUtil.equals("all", zjTzComprehensiveSup.getProjectId(), true)) {
				zjTzComprehensiveSup.setProjectId("");
			}
		}
		
		
		zjTzComprehensiveSup.setCorrectiveId("1");//回复列表只能查看已下达的数据
		// 分页查询
		PageHelper.startPage(zjTzComprehensiveSup.getPage(),zjTzComprehensiveSup.getLimit());
		// 获取数据
		List<ZjTzComprehensiveSup> zjTzComprehensiveSupList = zjTzComprehensiveSupMapper.selectByZjTzComprehensiveSupList(zjTzComprehensiveSup);
		for (ZjTzComprehensiveSup zjTzComprehensiveSup2 : zjTzComprehensiveSupList) {
			//附件
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherId(zjTzComprehensiveSup2.getComprehensiveSupId());
			zjTzFileSelect.setOtherType("1");
			List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			zjTzComprehensiveSup2.setZjTzFileList(zjTzFileList);
			//回复的附件
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzComprehensiveSup2.setReplyFileList(replyFileList);
			//督察内容
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
		// 得到分页信息
		PageInfo<ZjTzComprehensiveSup> p = new PageInfo<>(zjTzComprehensiveSupList);

		return repEntity.okList(zjTzComprehensiveSupList, p.getTotal());
	}

	@Override
	public ResponseEntity batchExportZjTzComprehensiveSupFile(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
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
		    // 实际文件
            File[] files = new File[fileList.size()];
            for (int i=0; i<fileList.size(); i++) {
                // 随机数，添加到文件后面，防止名称相同
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
            // zip文件输出
            ZipUtil.zip(FileUtil.file(HttpUtil.getUploadPath(uuidFolder) + zipName), false, files);
            String returnPath = HttpUtil.getUploadPathWeb(request, uuidFolder) + zipName;
            return repEntity.ok(returnPath);
		}else {
			return repEntity.layerMessage("no", "无导出数据！");
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
			//督察内容list=先删除再新增
			ZjTzSupContent delZjTzSupContent = new ZjTzSupContent();
			delZjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
			List<ZjTzSupContent> delZjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(delZjTzSupContent);
			if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
				delZjTzSupContent.setModifyUserInfo(userKey, realName);
				zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(delZjTzSupContentList, delZjTzSupContent);
			}
			//整改内容list
			List<ZjTzSupContent> zjTzSupContentList = zjTzComprehensiveSup.getZjTzSupContentList();
			for (ZjTzSupContent zjTzSupContent : zjTzSupContentList) {
				zjTzSupContent.setSupContentId(UuidUtil.generate());
				// 项目综合督、查综合督导
				zjTzSupContent.setComprehensiveSupId(dbzjTzComprehensiveSup.getComprehensiveSupId());
				// 整改落实情况
				zjTzSupContent.setCorrectiveCase(zjTzSupContent.getCorrectiveCase());
				// 整改完成时间
				zjTzSupContent.setCorrectiveDate(zjTzSupContent.getCorrectiveDate());
				zjTzSupContent.setCreateUserInfo(userKey, realName);
				flag = zjTzSupContentMapper.insert(zjTzSupContent);
			}
			//回复完成状态变成已落实
			ZjTzComprehensiveSup updateSup =zjTzComprehensiveSupMapper.selectByPrimaryKey(dbzjTzComprehensiveSup.getComprehensiveSupId());
			if(updateSup != null && StrUtil.isNotEmpty(updateSup.getComprehensiveSupId())) {
				updateSup.setCorrectiveId("2");
				updateSup.setCorrectiveName("已落实");
				updateSup.setModifyUserInfo(userKey, realName);
				flag = zjTzComprehensiveSupMapper.updateByPrimaryKey(updateSup);
			}
			//新增整改回复的附件= 先删除附件再新增
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherType("2");//回复的附件
			zjTzFileSelect.setOtherId(dbzjTzComprehensiveSup.getComprehensiveSupId());
			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
				zjTzFileSelect.setModifyUserInfo(userKey, realName);
				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
			}
			// 附件list
			if(zjTzComprehensiveSup.getReplyFileList() != null && zjTzComprehensiveSup.getReplyFileList().size()>0) {
				for(ZjTzFile zjTzFile:zjTzComprehensiveSup.getReplyFileList()) {
					zjTzFile.setUid(UuidUtil.generate());
					zjTzFile.setOtherType("2");//回复的附件
					zjTzFile.setOtherId(zjTzComprehensiveSup.getComprehensiveSupId());
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
			return repEntity.ok("sys.data.update",zjTzComprehensiveSup);
		}
	}
	
}