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
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPolicyLocalReplyMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzPolicyLocalMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReply;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzPolicyLocalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzPolicyLocalService")
public class ZjTzPolicyLocalServiceImpl implements ZjTzPolicyLocalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyLocalMapper zjTzPolicyLocalMapper;
    
    @Autowired(required = true)
    private ZjTzPolicyLocalReplyMapper zjTzPolicyLocalReplyMapper;
    
    @Autowired(required = true) 
    private ZjTzFileMapper zjTzFileMapper;
    
	@Autowired(required = true)
	private BaseCodeService baseCodeService;
	
	@Autowired(required = true)
	private ZjTzProManageMapper zjTzProManageMapper;
	
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzPolicyLocalListByCondition(ZjTzPolicyLocal zjTzPolicyLocal) {
        if (zjTzPolicyLocal == null) {
            zjTzPolicyLocal = new ZjTzPolicyLocal();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzPolicyLocal.getProjectId(), true)) {
            	zjTzPolicyLocal.setProjectId("");
            	zjTzPolicyLocal.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }else {
            	
            	zjTzPolicyLocal.setExt1SeeFlag("???????????????????????????????????????????????????");
            	zjTzPolicyLocal.setExt1SeeFlag(zjTzPolicyLocal.getProjectId());
            	zjTzPolicyLocal.setProjectId("");
            }
        } else {
            if(StrUtil.equals("all", zjTzPolicyLocal.getProjectId(), true)) {
            	zjTzPolicyLocal.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzPolicyLocal.getPage(),zjTzPolicyLocal.getLimit());
        // ????????????
        List<ZjTzPolicyLocal> zjTzPolicyLocalList = zjTzPolicyLocalMapper.selectByZjTzPolicyLocalList(zjTzPolicyLocal);
        for(ZjTzPolicyLocal dbZjTzPolicyLocal:zjTzPolicyLocalList) {
            ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbZjTzPolicyLocal.getPolicyId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            dbZjTzPolicyLocal.setZjTzFileList(zjTzFileList);
            
            ZjTzPolicyLocalReply record = new ZjTzPolicyLocalReply();
            record.setPolicyId(dbZjTzPolicyLocal.getPolicyId());
            List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(record);
            dbZjTzPolicyLocal.setZjTzPolicyLocalReplyList(zjTzPolicyLocalReplyList);
        }
        // ??????????????????
        PageInfo<ZjTzPolicyLocal> p = new PageInfo<>(zjTzPolicyLocalList);

        return repEntity.okList(zjTzPolicyLocalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyLocalDetails(ZjTzPolicyLocal zjTzPolicyLocal) {
        if (zjTzPolicyLocal == null) {
            zjTzPolicyLocal = new ZjTzPolicyLocal();
        }
        zjTzPolicyLocal = zjTzPolicyLocalMapper.selectByPrimaryKey(zjTzPolicyLocal.getPolicyId());
        // ????????????
        if (zjTzPolicyLocal != null && StrUtil.isNotEmpty(zjTzPolicyLocal.getPolicyId())) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzPolicyLocal.getPolicyId());
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPolicyLocal.setZjTzFileList(zjTzFileList3);
        	//
        	ZjTzPolicyLocalReply record = new ZjTzPolicyLocalReply();
        	record.setPolicyId(zjTzPolicyLocal.getPolicyId());
        	List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(record);
        	zjTzPolicyLocal.setZjTzPolicyLocalReplyList(zjTzPolicyLocalReplyList);
        	return repEntity.ok(zjTzPolicyLocal);
        }else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyLocal(ZjTzPolicyLocal zjTzPolicyLocal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzPolicyLocal.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzPolicyLocal.setProjectName(manage.getProjectName());
        	zjTzPolicyLocal.setProjectShortName(manage.getProjectShortName());
        }
        
        zjTzPolicyLocal.setPolicyId(UuidUtil.generate());
        if(StrUtil.equals("1", zjTzPolicyLocal.getEffectiveId())) {
            zjTzPolicyLocal.setEffectiveName("???");
        } else {
            zjTzPolicyLocal.setEffectiveName("???");
        }
        //????????????
        if (StrUtil.isNotEmpty(zjTzPolicyLocal.getReleaseRankId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzPolicyLocal.getReleaseRankId());
        	zjTzPolicyLocal.setReleaseRankName(openBankName);
        }
        zjTzPolicyLocal.setStatusId("0");
        zjTzPolicyLocal.setStatusName("?????????");
        zjTzPolicyLocal.setReleaseId("0");
        zjTzPolicyLocal.setReleaseName("?????????");
        zjTzPolicyLocal.setHomeShow("0");
        zjTzPolicyLocal.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyLocalMapper.insert(zjTzPolicyLocal);

        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzPolicyLocal.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzPolicyLocal.getPolicyId());
                zjTzFile.setOtherType("1");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyLocal);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyLocal(ZjTzPolicyLocal zjTzPolicyLocal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyLocal dbzjTzPolicyLocal = zjTzPolicyLocalMapper.selectByPrimaryKey(zjTzPolicyLocal.getPolicyId());
        if (dbzjTzPolicyLocal != null && StrUtil.isNotEmpty(dbzjTzPolicyLocal.getPolicyId())) {
        	if(StrUtil.equals(dbzjTzPolicyLocal.getReleaseId(), "1") || StrUtil.equals(dbzjTzPolicyLocal.getStatusId(), "1")) {
          	   return repEntity.layerMessage("no", "????????????????????????????????????????????????");
             }
         	if(StrUtil.equals("1", zjTzPolicyLocal.getEffectiveId())) {
                zjTzPolicyLocal.setEffectiveName("???");
            } else {
                zjTzPolicyLocal.setEffectiveName("???");
            }
            // ??????
            dbzjTzPolicyLocal.setTitle(zjTzPolicyLocal.getTitle());
            // ??????
            dbzjTzPolicyLocal.setSymbolNo(zjTzPolicyLocal.getSymbolNo());
            //????????????id
            dbzjTzPolicyLocal.setReleaseRankId(zjTzPolicyLocal.getReleaseRankId());
            //????????????name
            if (StrUtil.isNotEmpty(zjTzPolicyLocal.getReleaseRankId())) {
         	   String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzPolicyLocal.getReleaseRankId());
         	   dbzjTzPolicyLocal.setReleaseRankName(openBankName);
            }
            // ??????????????????
            dbzjTzPolicyLocal.setSysDate(zjTzPolicyLocal.getSysDate());
            // ????????????
            dbzjTzPolicyLocal.setDepartmentName(zjTzPolicyLocal.getDepartmentName());
            // ??????????????????
            dbzjTzPolicyLocal.setReleaseDate(zjTzPolicyLocal.getReleaseDate());
            // ??????????????????
            dbzjTzPolicyLocal.setEffectDate(zjTzPolicyLocal.getEffectDate());
            // ????????????
            dbzjTzPolicyLocal.setRegisterUser(zjTzPolicyLocal.getRegisterUser());
            // ????????????
            dbzjTzPolicyLocal.setEffectiveId(zjTzPolicyLocal.getEffectiveId());
            // ??????????????????
            dbzjTzPolicyLocal.setEffectiveName(zjTzPolicyLocal.getEffectiveName());
            // ????????????
            dbzjTzPolicyLocal.setReport(zjTzPolicyLocal.getReport());
            // ??????????????????
            dbzjTzPolicyLocal.setPushInfo(zjTzPolicyLocal.getPushInfo());
            // ??????
            dbzjTzPolicyLocal.setModifyUserInfo(userKey, realName);
            flag = zjTzPolicyLocalMapper.updateByPrimaryKey(dbzjTzPolicyLocal);
            
            // ?????????????????????
            ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbzjTzPolicyLocal.getPolicyId());
            List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
                zjTzFileSelect.setModifyUserInfo(userKey, realName);
                zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
            }
            // ??????list
            List<ZjTzFile> zjTzFileList = zjTzPolicyLocal.getZjTzFileList();
            if(zjTzFileList != null && zjTzFileList.size()>0) {
                for(ZjTzFile zjTzFile:zjTzFileList) {
                    zjTzFile.setUid(UuidUtil.generate());
                    zjTzFile.setOtherId(dbzjTzPolicyLocal.getPolicyId());
                    zjTzFile.setOtherType("1");
                    zjTzFile.setCreateUserInfo(userKey, realName);
                    zjTzFileMapper.insert(zjTzFile);
                }
            }
            
            // ???????????????(????????????????????????????????????????????????????????????????????????????????????
            List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocal.getZjTzPolicyLocalReplyList();
            if(zjTzPolicyLocalReplyList != null && zjTzPolicyLocalReplyList.size()>0) {
                for(ZjTzPolicyLocalReply zjTzPolicyLocalReply:zjTzPolicyLocalReplyList) {
                    zjTzPolicyLocalReply.setPolicyReplyId(UuidUtil.generate());
                    BeanUtil.copyProperties(dbzjTzPolicyLocal, zjTzPolicyLocalReply);
                    zjTzPolicyLocalReply.setCreateUserInfo(userKey, realName);
                    zjTzPolicyLocalReplyMapper.insert(zjTzPolicyLocalReply);
                }
            }
         }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
    		for (ZjTzPolicyLocal zjTzPolicyLocal : zjTzPolicyLocalList) {
    			if(StrUtil.equals(zjTzPolicyLocal.getReleaseId(), "1") || StrUtil.equals(zjTzPolicyLocal.getStatusId(), "1")) {
    				return repEntity.layerMessage("no", "??????????????????????????????????????????");
    			}
    		}
    		//??????
    		for (ZjTzPolicyLocal PolicyLocal : zjTzPolicyLocalList) {
    			//del??????
    			ZjTzFile file = new ZjTzFile();
    			file.setOtherId(PolicyLocal.getPolicyId());
    			List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
    			if(deFileList != null && deFileList.size() >0) {
    				file.setModifyUserInfo(userKey, realName);
    				flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
    			}
    		}
    		ZjTzPolicyLocal zjTzPolicyLocal = new ZjTzPolicyLocal();
			zjTzPolicyLocal.setModifyUserInfo(userKey, realName);
			flag = zjTzPolicyLocalMapper.batchDeleteUpdateZjTzPolicyLocal(zjTzPolicyLocalList, zjTzPolicyLocal);
		
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzPolicyLocalList);
    	}
    }
    
    //--??????
    @Override
    public ResponseEntity updateZjTzPolicyLocalPush(ZjTzPolicyLocal zjTzPolicyLocal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyLocal dbzjTzPolicyLocal = zjTzPolicyLocalMapper.selectByPrimaryKey(zjTzPolicyLocal.getPolicyId());
        if (dbzjTzPolicyLocal != null && StrUtil.isNotEmpty(dbzjTzPolicyLocal.getPolicyId())) {           
           // ???????????????(????????????????????????????????????????????????????????????????????????????????????
           List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocal.getZjTzPolicyLocalReplyList();
          int size = 0;
           
          if(zjTzPolicyLocalReplyList != null && zjTzPolicyLocalReplyList.size()>0) {
        	  //???????????????????????? ????????????????????????????????????????????????
        	  size = zjTzPolicyLocalReplyList.size();
        	  for(ZjTzPolicyLocalReply zjTzPolicyLocalReply:zjTzPolicyLocalReplyList) {
        		  ZjTzPolicyLocalReply reply = new ZjTzPolicyLocalReply();
        		  reply.setValue(zjTzPolicyLocalReply.getValue());
        		  reply.setPolicyId(zjTzPolicyLocal.getPolicyId());
        		  List<ZjTzPolicyLocalReply> replies = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(reply);
        		  if(replies != null && replies.size() >0) {
        			  size = size-1;  
        		  }else {
        			  zjTzPolicyLocalReply.setPolicyReplyId(UuidUtil.generate());
        			  BeanUtil.copyProperties(dbzjTzPolicyLocal, zjTzPolicyLocalReply);
        			  zjTzPolicyLocalReply.setCreateUserInfo(userKey, realName);
        			  zjTzPolicyLocalReplyMapper.insert(zjTzPolicyLocalReply);
        		  }
        	  }
               // ????????????
               int count = dbzjTzPolicyLocal.getPushInfoAll() + size;
               dbzjTzPolicyLocal.setPushInfoAll(count);
               dbzjTzPolicyLocal.setModifyUserInfo(userKey, realName);
               flag = zjTzPolicyLocalMapper.updateByPrimaryKey(dbzjTzPolicyLocal);
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocal);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyLocalHomeShow(ZjTzPolicyLocal zjTzPolicyLocal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyLocal dbzjTzPolicyLocal = zjTzPolicyLocalMapper.selectByPrimaryKey(zjTzPolicyLocal.getPolicyId());
        if (dbzjTzPolicyLocal != null && StrUtil.isNotEmpty(dbzjTzPolicyLocal.getPolicyId())) {   
        	if(StrUtil.equals(dbzjTzPolicyLocal.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
        	dbzjTzPolicyLocal.setHomeShow("1");
            dbzjTzPolicyLocal.setHomeShowStartDate(zjTzPolicyLocal.getHomeShowStartDate());
            dbzjTzPolicyLocal.setHomeShowEndDate(zjTzPolicyLocal.getHomeShowEndDate());
            dbzjTzPolicyLocal.setModifyUserInfo(userKey, realName);
            flag = zjTzPolicyLocalMapper.updateByPrimaryKey(dbzjTzPolicyLocal);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocal);
        }
    }

	@Override
	public ResponseEntity batchDeleteReleaseZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
        	for (ZjTzPolicyLocal zjTzRules : zjTzPolicyLocalList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "??????????????????????????????????????????");
				}
			}
        	for (ZjTzPolicyLocal zjTzPolicyLocal : zjTzPolicyLocalList) {
        		ZjTzPolicyLocalReply record = new ZjTzPolicyLocalReply();
        		record.setPolicyId(zjTzPolicyLocal.getPolicyId());
        		List<ZjTzPolicyLocalReply> LocalReplies = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(record);
        		for (ZjTzPolicyLocalReply Local : LocalReplies) {
        			Local.setReleaseId("1");
        			Local.setReleaseName("??????");
        			zjTzPolicyLocalReplyMapper.updateByPrimaryKey(Local);
				}
        	}
        	ZjTzPolicyLocal zjTzRules = new ZjTzPolicyLocal();
           zjTzRules.setReleaseName("??????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyLocalMapper.batchDeleteReleaseZjTzPolicyLocal(zjTzPolicyLocalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocalList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
        	for (ZjTzPolicyLocal zjTzRules : zjTzPolicyLocalList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	for (ZjTzPolicyLocal zjTzPolicyLocal : zjTzPolicyLocalList) {
        		ZjTzPolicyLocalReply record = new ZjTzPolicyLocalReply();
        		record.setPolicyId(zjTzPolicyLocal.getPolicyId());
        		List<ZjTzPolicyLocalReply> LocalReplies = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(record);
        		for (ZjTzPolicyLocalReply Local : LocalReplies) {
        			Local.setReleaseId("0");
        			Local.setReleaseName("?????????");
        			zjTzPolicyLocalReplyMapper.updateByPrimaryKey(Local);
				}
        	}
        	ZjTzPolicyLocal zjTzRules = new ZjTzPolicyLocal();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPolicyLocalMapper.batchDeleteRecallZjTzPolicyLocal(zjTzPolicyLocalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocalList);
        }
	}
	
	@Override
	public ResponseEntity batchReportZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
        	for (ZjTzPolicyLocal zjTzRules : zjTzPolicyLocalList) {
        		if(StrUtil.equals(zjTzRules.getStatusId(), "1")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}
			}
        	ZjTzPolicyLocal zjTzRules = new ZjTzPolicyLocal();
        	zjTzRules.setStatusId("1");
        	zjTzRules.setStatusName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPolicyLocalMapper.batchReportZjTzPolicyLocal(zjTzPolicyLocalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocalList);
        }
	}

	@Override
	public ResponseEntity batchReturnZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
        	for (ZjTzPolicyLocal zjTzRules : zjTzPolicyLocalList) {
        		if(StrUtil.equals(zjTzRules.getStatusId(), "0") || StrUtil.equals(zjTzRules.getStatusId(), "2")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
				}
        	}
        	ZjTzPolicyLocal zjTzRules = new ZjTzPolicyLocal();
        	zjTzRules.setStatusId("2");
        	zjTzRules.setStatusName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPolicyLocalMapper.batchReportZjTzPolicyLocal(zjTzPolicyLocalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocalList);
        }
	}
	
	@Override
	public ResponseEntity batchExportZjTzPolicyLocalFile(List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName = "????????????" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzPolicyLocal total : zjTzPolicyLocalList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getPolicyId());
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

	
}
