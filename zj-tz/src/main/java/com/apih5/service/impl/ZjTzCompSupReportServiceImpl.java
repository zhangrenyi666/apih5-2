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
import com.apih5.mybatis.dao.ZjTzCompSupContentMapper;
import com.apih5.mybatis.dao.ZjTzCompSupReportMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzCompSupContent;
import com.apih5.mybatis.pojo.ZjTzCompSupReport;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzCompSupReportService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzCompSupReportService")
public class ZjTzCompSupReportServiceImpl implements ZjTzCompSupReportService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzCompSupReportMapper zjTzCompSupReportMapper;
    
    @Autowired(required = true)
    private ZjTzCompSupContentMapper zjTzCompSupContentMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzCompSupReportListByCondition(ZjTzCompSupReport zjTzCompSupReport) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userId = TokenUtils.getUserId(request);
         String ext1 = TokenUtils.getExt1(request);
    	if (zjTzCompSupReport == null) {
            zjTzCompSupReport = new ZjTzCompSupReport();
        }
    	 // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//??????????????????????????????
//        	zjTzCompSupReport.setUnExt1SeeFlag("??????????????????????????????");
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzCompSupReport.getProjectId(), true)) {
            	zjTzCompSupReport.setProjectId("");
            	zjTzCompSupReport.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzCompSupReport.getProjectId(), true)) {
            	zjTzCompSupReport.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzCompSupReport.getPage(),zjTzCompSupReport.getLimit());
        // ????????????
        List<ZjTzCompSupReport> zjTzCompSupReportList = zjTzCompSupReportMapper.selectByZjTzCompSupReportList(zjTzCompSupReport);
        for (ZjTzCompSupReport zjTzCompSupReport2 : zjTzCompSupReportList) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzCompSupReport2.getCompSupReportId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzCompSupReport2.setZjTzFileList(zjTzFileList);
        	//????????????
        	ZjTzCompSupContent zjTzSupContent = new ZjTzCompSupContent();
        	zjTzSupContent.setCompSupReportId(zjTzCompSupReport2.getCompSupReportId());
        	List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(zjTzSupContent);
        	// ??????????????????????????????
        	String detail = "";
        	for (ZjTzCompSupContent zjTzSupContent2 : zjTzSupContentList) {
            	ZjTzFile zjTzFileSupContent = new ZjTzFile();
            	zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
            	List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
            	zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);

            	detail += "" + zjTzSupContent2.getDetail() + ",,";
            	if(detail.indexOf(",")>0) {
            		detail = detail.substring(0, detail.length()-1);
            	}

    		}
        	//??????????????? ??? ????????????????????????????????????????????????????????????
        	if(StrUtil.equals(zjTzCompSupReport2.getCorrectiveId(), "2")) {
        		//???????????????
            	zjTzFileSelect.setOtherType("2");
            	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            	zjTzCompSupReport2.setReplyFileList(replyFileList);	
            	
        	}else {
        		for (ZjTzCompSupContent zjTzSupContent2 : zjTzSupContentList) {
            		zjTzSupContent2.setCorrectiveCase("");
            	}
        	}
        	zjTzCompSupReport2.setZjTzSupContentList(zjTzSupContentList);
        	zjTzCompSupReport2.setDetail(detail);
		}
        // ??????????????????
        PageInfo<ZjTzCompSupReport> p = new PageInfo<>(zjTzCompSupReportList);

        return repEntity.okList(zjTzCompSupReportList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzCompSupReportDetails(ZjTzCompSupReport zjTzCompSupReport) {
        if (zjTzCompSupReport == null) {
            zjTzCompSupReport = new ZjTzCompSupReport();
        }
        // ????????????
        ZjTzCompSupReport dbZjTzCompSupReport = zjTzCompSupReportMapper.selectByPrimaryKey(zjTzCompSupReport.getCompSupReportId());
        // ????????????
        if (dbZjTzCompSupReport != null) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzCompSupReport.getCompSupReportId());
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzCompSupReport.setZjTzFileList(zjTzFileList);
        	//???????????????
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzCompSupReport.setReplyFileList(replyFileList);
        	//????????????
        	ZjTzCompSupContent zjTzSupContent = new ZjTzCompSupContent();
        	zjTzSupContent.setCompSupReportId(dbZjTzCompSupReport.getCompSupReportId());
        	List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(zjTzSupContent);
        	for (ZjTzCompSupContent zjTzSupContent2 : zjTzSupContentList) {
            	ZjTzFile zjTzFileSupContent = new ZjTzFile();
            	zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
            	List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
            	zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);
    		}
        	dbZjTzCompSupReport.setZjTzSupContentList(zjTzSupContentList);
            return repEntity.ok(dbZjTzCompSupReport);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzCompSupReport.setCompSupReportId(UuidUtil.generate());
        //????????????
    	if (StrUtil.isNotEmpty(zjTzCompSupReport.getSupClassId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("duChaFenLei", zjTzCompSupReport.getSupClassId());
    		zjTzCompSupReport.setSupClassName(openBankName);
    	}
        // ??????????????????id
        zjTzCompSupReport.setCorrectiveId("0");
        // ??????????????????name
        zjTzCompSupReport.setCorrectiveName("?????????");
        zjTzCompSupReport.setCreateUserInfo(userKey, realName);
        int flag = zjTzCompSupReportMapper.insert(zjTzCompSupReport);
        // ??????list
    	List<ZjTzFile> zjTzFileList = zjTzCompSupReport.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherType("1");
    			zjTzFile.setOtherId(zjTzCompSupReport.getCompSupReportId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
    	//????????????list
    	List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupReport.getZjTzSupContentList();
    	if(zjTzSupContentList != null && zjTzSupContentList.size() >0) {
    		for (ZjTzCompSupContent zjTzSupContent : zjTzSupContentList) {
        		zjTzSupContent.setSupContentId(UuidUtil.generate());
        		// ?????????????????????????????????
        		zjTzSupContent.setCompSupReportId(zjTzCompSupReport.getCompSupReportId());
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
        		flag = zjTzCompSupContentMapper.insert(zjTzSupContent);
        	}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzCompSupReport);
        }
    }

    @Override
    public ResponseEntity updateZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzCompSupReport dbzjTzCompSupReport = zjTzCompSupReportMapper.selectByPrimaryKey(zjTzCompSupReport.getCompSupReportId());
        if (dbzjTzCompSupReport != null && StrUtil.isNotEmpty(dbzjTzCompSupReport.getCompSupReportId())) {
        	if(StrUtil.equals(dbzjTzCompSupReport.getCorrectiveId(), "0")){			
        		// ??????
            	dbzjTzCompSupReport.setTitle(zjTzCompSupReport.getTitle());
            	// ??????????????????id
            	dbzjTzCompSupReport.setProjectId(zjTzCompSupReport.getProjectId());
            	//????????????
            	dbzjTzCompSupReport.setSupDeptId(zjTzCompSupReport.getSupDeptId());
            	// ????????????
            	dbzjTzCompSupReport.setCheckDate(zjTzCompSupReport.getCheckDate());
            	// ????????????id
            	dbzjTzCompSupReport.setSupClassId(zjTzCompSupReport.getSupClassId());
            	// ????????????name
            	if (StrUtil.isNotEmpty(zjTzCompSupReport.getSupClassId())) {
            		String openBankName = baseCodeService.getBaseCodeItemName("duChaFenLei", zjTzCompSupReport.getSupClassId());
            		dbzjTzCompSupReport.setSupClassName(openBankName);
            	}
            	// ????????????
            	dbzjTzCompSupReport.setRegisterDate(zjTzCompSupReport.getRegisterDate());
               // ????????????
               dbzjTzCompSupReport.setRegisterPerson(zjTzCompSupReport.getRegisterPerson());
               // ????????????
               dbzjTzCompSupReport.setContentDesc(zjTzCompSupReport.getContentDesc());
               // ?????????Key
               dbzjTzCompSupReport.setCorrectiveUserKey(zjTzCompSupReport.getCorrectiveUserKey());
               // ?????????name
               dbzjTzCompSupReport.setCorrectiveUserName(zjTzCompSupReport.getCorrectiveUserName());
               // ??????
               dbzjTzCompSupReport.setModifyUserInfo(userKey, realName);
               flag = zjTzCompSupReportMapper.updateByPrimaryKey(dbzjTzCompSupReport);
               // ?????????????????????
               ZjTzFile zjTzFileSelect = new ZjTzFile();
               zjTzFileSelect.setOtherType("1");
               zjTzFileSelect.setOtherId(dbzjTzCompSupReport.getCompSupReportId());
               List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
               if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
                   zjTzFileSelect.setModifyUserInfo(userKey, realName);
                   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
               }
               // ??????list
               List<ZjTzFile> zjTzFileList = zjTzCompSupReport.getZjTzFileList();
               if(zjTzFileList != null && zjTzFileList.size()>0) {
                   for(ZjTzFile zjTzFile:zjTzFileList) {
                	   zjTzFile.setOtherType("1");
                	   zjTzFile.setUid(UuidUtil.generate());
                       zjTzFile.setOtherId(zjTzCompSupReport.getCompSupReportId());
                       zjTzFile.setCreateUserInfo(userKey, realName);
                       zjTzFileMapper.insert(zjTzFile);
                   }
               }
               
               //????????????list=??????????????????
               ZjTzCompSupContent delZjTzSupContent = new ZjTzCompSupContent();
               delZjTzSupContent.setCompSupReportId(dbzjTzCompSupReport.getCompSupReportId());
               List<ZjTzCompSupContent> delZjTzSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(delZjTzSupContent);
               if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
            	   delZjTzSupContent.setModifyUserInfo(userKey, realName);
            	   zjTzCompSupContentMapper.batchDeleteUpdateZjTzCompSupContent(delZjTzSupContentList, delZjTzSupContent);
               }

               //????????????list
               List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupReport.getZjTzSupContentList();
               for (ZjTzCompSupContent zjTzSupContent : zjTzSupContentList) {
            	   zjTzSupContent.setSupContentId(UuidUtil.generate());
            	   // ?????????????????????????????????
            	   zjTzSupContent.setCompSupReportId(zjTzCompSupReport.getCompSupReportId());
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
            	   flag = zjTzCompSupContentMapper.insert(zjTzSupContent);
               }
        	}else {
				return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
			}
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCompSupReport);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzCompSupReport(List<ZjTzCompSupReport> zjTzCompSupReportList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCompSupReportList != null && zjTzCompSupReportList.size() > 0) {
        	for (ZjTzCompSupReport zjTzRules : zjTzCompSupReportList) {
				if(StrUtil.equals(zjTzRules.getCorrectiveId(), "1") ) {
					 return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
				}
			}
        	for (ZjTzCompSupReport zjTzCompSupReport : zjTzCompSupReportList) {
        		// ????????????
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzCompSupReport.getCompSupReportId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//??????????????????
        		ZjTzCompSupContent supContent = new ZjTzCompSupContent();
        		supContent.setCompSupReportId(zjTzCompSupReport.getCompSupReportId());
        		List<ZjTzCompSupContent> zjTzQualities = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(supContent);
        		if(zjTzQualities != null && zjTzQualities.size() >0) {
        			supContent.setModifyUserInfo(userKey, realName);
        			zjTzCompSupContentMapper.batchDeleteUpdateZjTzCompSupContent(zjTzQualities, supContent);
        		}
        	}
        	ZjTzCompSupReport zjTzCompSupReport = new ZjTzCompSupReport();
        	zjTzCompSupReport.setModifyUserInfo(userKey, realName);
        	flag = zjTzCompSupReportMapper.batchDeleteUpdateZjTzCompSupReport(zjTzCompSupReportList, zjTzCompSupReport);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzCompSupReportList);
        }
    }

	@Override
	public ResponseEntity batchRecallZjTzCompSupReport(List<ZjTzCompSupReport> zjTzCompSupReportList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCompSupReportList != null && zjTzCompSupReportList.size() > 0) {
        	for (ZjTzCompSupReport zjTzRules : zjTzCompSupReportList) {
        		if(StrUtil.equals(zjTzRules.getCorrectiveId(), "0")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
        		}
        		if(StrUtil.equals(zjTzRules.getCorrectiveId(), "1") || StrUtil.equals(zjTzRules.getCorrectiveId(), "2")) {
        			zjTzRules.setCorrectiveId("0");
        			zjTzRules.setCorrectiveName("?????????");
        			zjTzRules.setContentDesc("");
        			zjTzRules.setCorrectiveUserKey("");
        			zjTzRules.setCorrectiveUserName("");
        			zjTzCompSupReportMapper.updateByPrimaryKey(zjTzRules);
        			
        			ZjTzCompSupContent record = new ZjTzCompSupContent();
        			record.setCompSupReportId(zjTzRules.getCompSupReportId());
        			List<ZjTzCompSupContent> contentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(record);
        			for (ZjTzCompSupContent content : contentList) {
        				content.setCorrectiveDate(null);
        				content.setCorrectiveCase("");
        				content.setModifyUserInfo(userKey, realName);
        				flag = zjTzCompSupContentMapper.updateByPrimaryKey(content);
					}
        		}
        	}
        	ZjTzCompSupReport zjTzRules = new ZjTzCompSupReport();
        	zjTzRules.setCorrectiveName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzCompSupReportMapper.batchRecallZjTzCompSupReport(zjTzCompSupReportList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCompSupReportList);
        }
	}

	@Override
	public ResponseEntity correctiveZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzCompSupReport dbzjTzCompSupReport = zjTzCompSupReportMapper.selectByPrimaryKey(zjTzCompSupReport.getCompSupReportId());
		if (dbzjTzCompSupReport != null && StrUtil.isNotEmpty(dbzjTzCompSupReport.getCompSupReportId())) {
			if(StrUtil.equals(dbzjTzCompSupReport.getCorrectiveId(), "1")) {
    			return repEntity.layerMessage("no", "??????????????????????????????");
    		}
			// ??????????????????id
			dbzjTzCompSupReport.setCorrectiveId("1");
			// ??????????????????name
			dbzjTzCompSupReport.setCorrectiveName("?????????");
			dbzjTzCompSupReport.setContentDesc(zjTzCompSupReport.getContentDesc());
			dbzjTzCompSupReport.setModifyUserInfo(userKey, realName);
			flag = zjTzCompSupReportMapper.updateByPrimaryKey(dbzjTzCompSupReport);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorUpdate();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzCompSupReport);
		}
	}

	@Override
	public ResponseEntity getZjTzCompSupReportReplyList(ZjTzCompSupReport zjTzCompSupReport) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		String ext1 = TokenUtils.getExt1(request);
		if (zjTzCompSupReport == null) {
			zjTzCompSupReport = new ZjTzCompSupReport();
		}
		// ????????????????????????
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// ???????????????????????????????????????sql?????????
			if(StrUtil.equals("all", zjTzCompSupReport.getProjectId(), true)) {
				zjTzCompSupReport.setProjectId("");
				zjTzCompSupReport.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// ???????????????
			if(StrUtil.equals("all", zjTzCompSupReport.getProjectId(), true)) {
				zjTzCompSupReport.setProjectId("");
			}
		}
		zjTzCompSupReport.setCorrectiveId("1");
		// ????????????
		PageHelper.startPage(zjTzCompSupReport.getPage(),zjTzCompSupReport.getLimit());
		// ????????????
 		List<ZjTzCompSupReport> zjTzCompSupReportList = zjTzCompSupReportMapper.selectByZjTzCompSupReportList(zjTzCompSupReport);
		for (ZjTzCompSupReport zjTzCompSupReport2 : zjTzCompSupReportList) {
			//??????
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherId(zjTzCompSupReport2.getCompSupReportId());
			zjTzFileSelect.setOtherType("1");
			List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			zjTzCompSupReport2.setZjTzFileList(zjTzFileList);
			//???????????????
        	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> replyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzCompSupReport2.setReplyFileList(replyFileList);
			//????????????
			ZjTzCompSupContent zjTzSupContent = new ZjTzCompSupContent();
			zjTzSupContent.setCompSupReportId(zjTzCompSupReport2.getCompSupReportId());
			List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(zjTzSupContent);
			for (ZjTzCompSupContent zjTzSupContent2 : zjTzSupContentList) {
				ZjTzFile zjTzFileSupContent = new ZjTzFile();
				zjTzFileSupContent.setOtherId(zjTzSupContent2.getSupContentId());
				List<ZjTzFile> zjTzFileSupContentList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSupContent);
				zjTzSupContent2.setZjTzFileList(zjTzFileSupContentList);
			}
			zjTzCompSupReport2.setZjTzSupContentList(zjTzSupContentList);
		}
		// ??????????????????
		PageInfo<ZjTzCompSupReport> p = new PageInfo<>(zjTzCompSupReportList);

		return repEntity.okList(zjTzCompSupReportList, p.getTotal());
	}

	@Override
	public ResponseEntity batchExportZjTzCompSupReportFile(List<ZjTzCompSupReport> zjTzCompSupReportList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzCompSupReport total : zjTzCompSupReportList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getCompSupReportId());
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
	public ResponseEntity replyZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzCompSupReport dbzjTzCompSupReport = zjTzCompSupReportMapper.selectByPrimaryKey(zjTzCompSupReport.getCompSupReportId());
		if (dbzjTzCompSupReport != null && StrUtil.isNotEmpty(dbzjTzCompSupReport.getCompSupReportId())) {
			//????????????list=??????????????????
			ZjTzCompSupContent delZjTzSupContent = new ZjTzCompSupContent();
			delZjTzSupContent.setCompSupReportId(dbzjTzCompSupReport.getCompSupReportId());
			List<ZjTzCompSupContent> delZjTzSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(delZjTzSupContent);
			if(delZjTzSupContentList != null &&delZjTzSupContentList.size() >0) {
				delZjTzSupContent.setModifyUserInfo(userKey, realName);
				zjTzCompSupContentMapper.batchDeleteUpdateZjTzCompSupContent(delZjTzSupContentList, delZjTzSupContent);
			}
			//????????????list
			List<ZjTzCompSupContent> zjTzSupContentList = zjTzCompSupReport.getZjTzSupContentList();
			for (ZjTzCompSupContent zjTzSupContent : zjTzSupContentList) {
				zjTzSupContent.setSupContentId(UuidUtil.generate());
				// ?????????????????????????????????
				zjTzSupContent.setCompSupReportId(dbzjTzCompSupReport.getCompSupReportId());
				// ??????????????????
				zjTzSupContent.setCorrectiveCase(zjTzSupContent.getCorrectiveCase());
				// ??????????????????
				zjTzSupContent.setCorrectiveDate(zjTzSupContent.getCorrectiveDate());
				zjTzSupContent.setCreateUserInfo(userKey, realName);
				flag = zjTzCompSupContentMapper.insert(zjTzSupContent);
			}
//			//?????????????????????????????????
//			ZjTzCompSupReport updateSupReport =zjTzCompSupReportMapper.selectByPrimaryKey(dbzjTzCompSupReport.getCompSupReportId());
//			if(updateSupReport != null && StrUtil.isNotEmpty(updateSupReport.getCompSupReportId())) {
//				updateSupReport.setCorrectiveId("2");
//				updateSupReport.setCorrectiveName("?????????");
//				updateSupReport.setModifyUserInfo(userKey, realName);
//				flag = zjTzCompSupReportMapper.updateByPrimaryKey(updateSupReport);
//			}

			//???????????????????????????= ????????????????????????
			ZjTzFile zjTzFileSelect = new ZjTzFile();
			zjTzFileSelect.setOtherType("2");//???????????????
			zjTzFileSelect.setOtherId(dbzjTzCompSupReport.getCompSupReportId());
			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
				zjTzFileSelect.setModifyUserInfo(userKey, realName);
				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
			}
			// ??????list
			if(zjTzCompSupReport.getReplyFileList() != null && zjTzCompSupReport.getReplyFileList().size()>0) {
				for(ZjTzFile zjTzFile:zjTzCompSupReport.getReplyFileList()) {
					zjTzFile.setUid(UuidUtil.generate());
					zjTzFile.setOtherType("2");//???????????????
					zjTzFile.setOtherId(dbzjTzCompSupReport.getCompSupReportId());
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
			return repEntity.ok("sys.data.update",zjTzCompSupReport);
		}
	}

	@Override
	public ResponseEntity reportZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzCompSupReport dbzjTzCompSupReport = zjTzCompSupReportMapper.selectByPrimaryKey(zjTzCompSupReport.getCompSupReportId());
		if (dbzjTzCompSupReport != null && StrUtil.isNotEmpty(dbzjTzCompSupReport.getCompSupReportId())) {
			//???????????????????????????
			dbzjTzCompSupReport.setCorrectiveId("2");
			dbzjTzCompSupReport.setCorrectiveName("?????????");
			dbzjTzCompSupReport.setModifyUserInfo(userKey, realName);
			flag = zjTzCompSupReportMapper.updateByPrimaryKey(dbzjTzCompSupReport);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzCompSupReport);
		}
	}
}
