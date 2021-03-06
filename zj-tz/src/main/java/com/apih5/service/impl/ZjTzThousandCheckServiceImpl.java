package com.apih5.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzThousandCheckBaseMapper;
import com.apih5.mybatis.dao.ZjTzThousandCheckMapper;
import com.apih5.mybatis.dao.ZjTzThousandDeductMapper;
import com.apih5.mybatis.pojo.ZjTzThousandCheck;
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThousandCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzThousandCheckService")
public class ZjTzThousandCheckServiceImpl implements ZjTzThousandCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThousandCheckMapper zjTzThousandCheckMapper;
    
    @Autowired(required = true)
    private ZjTzThousandDeductMapper zjTzThousandDeductMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzThousandCheckBaseMapper zjTzThousandCheckBaseMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzThousandCheckListByCondition(ZjTzThousandCheck zjTzThousandCheck) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThousandCheck == null) {
    		zjTzThousandCheck = new ZjTzThousandCheck();
        }
    	 // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	zjTzThousandCheck.setUnExt1SeeFlag("???????????????????????????????????????????????????????????????");
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzThousandCheck.getProjectId(), true)) {
            	zjTzThousandCheck.setProjectId("");
            	zjTzThousandCheck.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzThousandCheck.getProjectId(), true)) {
            	zjTzThousandCheck.setProjectId("");
            }
        }
        // ????????????
        PageHelper.startPage(zjTzThousandCheck.getPage(),zjTzThousandCheck.getLimit());
        // ????????????
        List<ZjTzThousandCheck> zjTzThousandCheckList = zjTzThousandCheckMapper.selectByZjTzThousandCheckList(zjTzThousandCheck);
        for (ZjTzThousandCheck zjTzThousandCheck2 : zjTzThousandCheckList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThousandCheck2.getThousandCheckId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThousandCheck2.setZjTzFileList(zjTzFileList);
        	
        	//?????????????????????(???????????????)
        	BigDecimal detailMarks = new BigDecimal("0");
        	//????????????
        	BigDecimal proScore = new BigDecimal("0");
        	ZjTzThousandDeduct deduct = new ZjTzThousandDeduct();
        	deduct.setThousandCheckId(zjTzThousandCheck2.getThousandCheckId());
        	List<ZjTzThousandDeduct> deducts = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(deduct);
        	for (ZjTzThousandDeduct actual : deducts) {
        		detailMarks = CalcUtils.calcAdd(detailMarks, actual.getActualScore());
        		proScore = CalcUtils.calcAdd(proScore, actual.getActualScore());
        	}
        	//????????????=??????+?????????????????????
        	//????????????=?????????????????????+???????????????-???????????????
        	if(zjTzThousandCheck2.getAddMarks() != null) {
        		proScore = CalcUtils.calcAdd(zjTzThousandCheck2.getAddMarks(), proScore);
        	}
        	if(zjTzThousandCheck2.getReduceMarks() != null) {
        		proScore = CalcUtils.calcSubtract(proScore, zjTzThousandCheck2.getReduceMarks());
        	}
        	zjTzThousandCheck2.setProScore(proScore);
        	zjTzThousandCheck2.setDetailMarks(detailMarks);
        	
		}
        // ??????????????????
        PageInfo<ZjTzThousandCheck> p = new PageInfo<>(zjTzThousandCheckList);

        return repEntity.okList(zjTzThousandCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThousandCheckDetails(ZjTzThousandCheck zjTzThousandCheck) {
        if (zjTzThousandCheck == null) {
            zjTzThousandCheck = new ZjTzThousandCheck();
        }
        // ????????????
        ZjTzThousandCheck dbZjTzThousandCheck = zjTzThousandCheckMapper.selectByPrimaryKey(zjTzThousandCheck.getThousandCheckId());
        // ????????????
        if (dbZjTzThousandCheck != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzThousandCheck.getThousandCheckId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzThousandCheck.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzThousandCheck);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //?????????????????????(???????????????)
    	BigDecimal detailMarks = new BigDecimal("0");
    	//????????????
    	BigDecimal proScore = new BigDecimal("0");
       
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzThousandCheck.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())){
        	zjTzThousandCheck.setProjectName(manage.getProjectName());
        }
        zjTzThousandCheck.setThousandCheckId(UuidUtil.generate());
        if(StrUtil.equals(zjTzThousandCheck.getVoteDownId(), "1")) {
        	zjTzThousandCheck.setVoteDownName("???");
        }else {
        	zjTzThousandCheck.setVoteDownName("???");
        }
        zjTzThousandCheck.setReleaseId("0");
        zjTzThousandCheck.setReleaseName("?????????");
        zjTzThousandCheck.setCreateUserInfo(userKey, realName);
        //???????????????
        ZjTzThousandCheckBase base = new ZjTzThousandCheckBase();
        List<ZjTzThousandCheckBase> baseItemList = zjTzThousandCheckBaseMapper.getZjTzThousandCheckBaseItemList(base);
        if(baseItemList != null && baseItemList.size() >0) {
        	for (ZjTzThousandCheckBase zjTzThousandCheckBase : baseItemList) {
        		detailMarks = CalcUtils.calcAdd(detailMarks, zjTzThousandCheckBase.getScore());
        		ZjTzThousandDeduct record = new ZjTzThousandDeduct();
        		record.setThousandDeductId(UuidUtil.generate());
        		record.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
        		record.setThousandCheckBaseId(zjTzThousandCheckBase.getCodePid());
        		record.setEvalPro(zjTzThousandCheckBase.getEvalProName());
        		record.setEvalIndex(zjTzThousandCheckBase.getEvalIndex());
        		record.setEvalContent(zjTzThousandCheckBase.getEvalContent());
        		record.setScore(zjTzThousandCheckBase.getScore());
        		record.setOrderFlag(zjTzThousandCheckBase.getOrderFlag());
        		record.setActualScore(zjTzThousandCheckBase.getScore());
        		record.setDeduct(new BigDecimal("0"));
        		record.setCreateUserInfo(userKey, realName);
        		zjTzThousandDeductMapper.insert(record);
        	}
        }
    	if(zjTzThousandCheck.getAddMarks() != null) {
    		proScore = CalcUtils.calcAdd(zjTzThousandCheck.getAddMarks(), proScore);
    	}
    	if(zjTzThousandCheck.getReduceMarks() != null) {
    		proScore = CalcUtils.calcSubtract(proScore, zjTzThousandCheck.getReduceMarks());
    	}
    	zjTzThousandCheck.setProScore(proScore);
    	
        int flag = zjTzThousandCheckMapper.insert(zjTzThousandCheck);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzThousandCheck.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzThousandCheck.getThousandCheckId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        

        if (flag == 0) {
        	return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThousandCheck);
        }
    }

    @Override
    public ResponseEntity updateZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThousandCheck dbzjTzThousandCheck = zjTzThousandCheckMapper.selectByPrimaryKey(zjTzThousandCheck.getThousandCheckId());
        if (dbzjTzThousandCheck != null && StrUtil.isNotEmpty(dbzjTzThousandCheck.getThousandCheckId())) {
//           // ??????id
//           dbzjTzThousandCheck.setProjectId(zjTzThousandCheck.getProjectId());
//           // ??????name
//           dbzjTzThousandCheck.setProjectName(zjTzThousandCheck.getProjectName());
           // ????????????
           dbzjTzThousandCheck.setCheckDate(zjTzThousandCheck.getCheckDate());
           // ?????????
           dbzjTzThousandCheck.setCheckPerson(zjTzThousandCheck.getCheckPerson());
           // ????????????Id
           dbzjTzThousandCheck.setVoteDownId(zjTzThousandCheck.getVoteDownId());
           // ????????????name
           if(StrUtil.equals(zjTzThousandCheck.getVoteDownId(), "1")) {
        	   dbzjTzThousandCheck.setVoteDownName("???");
           }else {
        	   dbzjTzThousandCheck.setVoteDownName("???");
           }
           // ??????????????????
           dbzjTzThousandCheck.setScoreTotal(zjTzThousandCheck.getScoreTotal());
           // ????????????
           dbzjTzThousandCheck.setRegisterDate(zjTzThousandCheck.getRegisterDate());
           // ????????????
           dbzjTzThousandCheck.setRegisterPerson(zjTzThousandCheck.getRegisterPerson());
           //??????
           dbzjTzThousandCheck.setAddMarks(zjTzThousandCheck.getAddMarks());
           //????????????
           dbzjTzThousandCheck.setAddMarksReason(zjTzThousandCheck.getAddMarksReason());
           //??????
           dbzjTzThousandCheck.setReduceMarks(zjTzThousandCheck.getReduceMarks());
           //????????????
           dbzjTzThousandCheck.setReduceMarksReason(zjTzThousandCheck.getReduceMarksReason());
           // ????????????==??????+?????????????????????-??????
           dbzjTzThousandCheck.setProScore(CalcUtils.calcDivide(CalcUtils.calcAdd(zjTzThousandCheck.getAddMarks(), dbzjTzThousandCheck.getProScore()), zjTzThousandCheck.getReduceMarks()));
           // ??????
           dbzjTzThousandCheck.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandCheckMapper.updateByPrimaryKey(dbzjTzThousandCheck);
           
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzThousandCheck.getThousandCheckId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzThousandCheck.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzThousandCheck.getThousandCheckId());
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
            return repEntity.ok("sys.data.update",zjTzThousandCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
    		for (ZjTzThousandCheck zjTzThousandCheck : zjTzThousandCheckList) {
    			if(StrUtil.equals(zjTzThousandCheck.getReleaseId(), "1")) {
    				return repEntity.layerMessage("no", "????????????????????????????????????");
    			}
    		}
    		for (ZjTzThousandCheck zjTzThousandCheck : zjTzThousandCheckList) {
    			// ????????????
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			//????????????
    			ZjTzThousandDeduct deduct = new ZjTzThousandDeduct();
    			deduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzThousandDeduct> delDeductList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(deduct);
    			if(delDeductList != null && delDeductList.size() >0) {
    				deduct.setModifyUserInfo(userKey, realName);
    				zjTzThousandDeductMapper.batchDeleteUpdateZjTzThousandDeduct(delDeductList, deduct);
    			}
    		}
    		ZjTzThousandCheck zjTzThousandCheck = new ZjTzThousandCheck();
    		zjTzThousandCheck.setModifyUserInfo(userKey, realName);
    		flag = zjTzThousandCheckMapper.batchDeleteUpdateZjTzThousandCheck(zjTzThousandCheckList, zjTzThousandCheck);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThousandCheckList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
        	for (ZjTzThousandCheck zjTzRules : zjTzThousandCheckList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "???????????????????????????????????????");
				}
			}
        	ZjTzThousandCheck zjTzRules = new ZjTzThousandCheck();
           zjTzRules.setReleaseName("?????????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandCheckMapper.batchReleaseZjTzThousandCheck(zjTzThousandCheckList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandCheckList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
        	for (ZjTzThousandCheck zjTzRules : zjTzThousandCheckList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	ZjTzThousandCheck zjTzRules = new ZjTzThousandCheck();
        	zjTzRules.setReleaseName("?????????");//?????????
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzThousandCheckMapper.batchRecallZjTzThousandCheck(zjTzThousandCheckList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandCheckList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzThousandCheckFile(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzThousandCheck total : zjTzThousandCheckList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getThousandCheckId());
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
	public ResponseEntity saveZjTzThousandCheckAllDeduct(ZjTzThousandCheck zjTzThousandCheck) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheck != null && StrUtil.isNotEmpty(zjTzThousandCheck.getThousandCheckId())) {
    		if (zjTzThousandCheck.getZjTzThousandDeductList() != null && zjTzThousandCheck.getZjTzThousandDeductList().size() >0) {
    			for (ZjTzThousandDeduct termBase : zjTzThousandCheck.getZjTzThousandDeductList()) {
    				//?????????????????????????????????????????? ??????==????????????
    				if(termBase.getActualScore().compareTo(termBase.getScore()) >0) {
    					return repEntity.layerMessage("no", "??????????????????????????????????????????");
    				}
    				if(termBase.getDeduct().compareTo(termBase.getScore()) >0) {
    					return repEntity.layerMessage("no", "????????????????????????????????????");
    				}
    			}
    			//??????????????????
    			ZjTzThousandDeduct delDeduct = new ZjTzThousandDeduct();
    			delDeduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzThousandDeduct> delRelpyList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(delDeduct);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				delDeduct.setModifyUserInfo(userKey, realName);
    				flag = zjTzThousandDeductMapper.batchDeleteUpdateZjTzThousandDeduct(delRelpyList, delDeduct);
    			}
    			for (ZjTzThousandDeduct termBase : zjTzThousandCheck.getZjTzThousandDeductList()) {
    				ZjTzThousandDeduct dbzjTzThousandDeduct = new ZjTzThousandDeduct();
    				// ??????id
    				dbzjTzThousandDeduct.setThousandDeductId(UuidUtil.generate());
    				// id
    				dbzjTzThousandDeduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    				//baseid
    				dbzjTzThousandDeduct.setThousandCheckBaseId(termBase.getThousandCheckBaseId());
    				//
    				dbzjTzThousandDeduct.setEvalPro(termBase.getEvalPro());
    				dbzjTzThousandDeduct.setEvalIndex(termBase.getEvalIndex());
    				dbzjTzThousandDeduct.setEvalContent(termBase.getEvalContent());
    				dbzjTzThousandDeduct.setDeductDesc(termBase.getDeductDesc());
    				dbzjTzThousandDeduct.setScore(termBase.getScore());
    				dbzjTzThousandDeduct.setActualScore(termBase.getActualScore());
    				dbzjTzThousandDeduct.setDeduct(termBase.getDeduct());
    				//??????
    				dbzjTzThousandDeduct.setOrderFlag(termBase.getOrderFlag());
    				// ??????
    				dbzjTzThousandDeduct.setCreateUserInfo(userKey, realName);
    				flag = zjTzThousandDeductMapper.insert(dbzjTzThousandDeduct);
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzThousandCheck);
    	}
	}
}
