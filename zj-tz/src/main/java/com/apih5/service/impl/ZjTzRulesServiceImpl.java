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
import com.apih5.mybatis.dao.ZjTzImportDocMapper;
import com.apih5.mybatis.dao.ZjTzRulesMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzImportDoc;
import com.apih5.mybatis.pojo.ZjTzRules;
import com.apih5.service.ZjTzRulesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzRulesService")
public class ZjTzRulesServiceImpl implements ZjTzRulesService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRulesMapper zjTzRulesMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
	@Autowired(required = true)
	private BaseCodeService baseCodeService;

    @Autowired(required = true)
    private ZjTzImportDocMapper zjTzImportDocMapper;

    @Override
    public ResponseEntity getZjTzRulesListByCondition(ZjTzRules zjTzRules) {
        if (zjTzRules == null) {
            zjTzRules = new ZjTzRules();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // ????????????????????????
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzRules.setReleaseId("1");
        }
        
        // ????????????
        PageHelper.startPage(zjTzRules.getPage(),zjTzRules.getLimit());
        if(zjTzRules.getHomeShowStartDateList() != null && zjTzRules.getHomeShowStartDateList().size() >0){
        	zjTzRules.setHomeShowStartDateSearch(zjTzRules.getHomeShowStartDateList().get(0));
        	zjTzRules.setHomeShowEndDateSearch(zjTzRules.getHomeShowStartDateList().get(1));
        }
        // ????????????
        List<ZjTzRules> zjTzRulesList = zjTzRulesMapper.selectByZjTzRulesList(zjTzRules);
        for (ZjTzRules rules : zjTzRulesList) {
        	 ZjTzFile zjTzFileSelect = new ZjTzFile();
             zjTzFileSelect.setOtherId(rules.getRulesId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             rules.setZjTzFileList(zjTzFileList);
		}
        // ??????????????????
        PageInfo<ZjTzRules> p = new PageInfo<>(zjTzRulesList);
        return repEntity.okList(zjTzRulesList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRulesDetails(ZjTzRules zjTzRules) {
        if (zjTzRules == null) {
            zjTzRules = new ZjTzRules();
        }
        // ????????????
        ZjTzRules dbZjTzRules = zjTzRulesMapper.selectByPrimaryKey(zjTzRules.getRulesId());
        // ????????????
        if (dbZjTzRules != null) {
        	 ZjTzFile zjTzFileSelect = new ZjTzFile();
             zjTzFileSelect.setOtherId(dbZjTzRules.getRulesId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzRules.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzRules);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzRules(ZjTzRules zjTzRules) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzRules.setRulesId(UuidUtil.generate());
        if(StrUtil.equals("1", zjTzRules.getEffectiveId())) {
        	zjTzRules.setEffectiveName("???");
        } else {
        	zjTzRules.setEffectiveName("???");
        }
        //????????????
        if (StrUtil.isNotEmpty(zjTzRules.getReleaseRankId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzRules.getReleaseRankId());
        	zjTzRules.setReleaseRankName(openBankName);
        }
        zjTzRules.setReleaseId("0");
        zjTzRules.setReleaseName("?????????");
        zjTzRules.setHomeShow("0");
        zjTzRules.setCreateUserInfo(userKey, realName);
        int flag = zjTzRulesMapper.insert(zjTzRules);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzRules.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzRules.getRulesId());
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRules);
        }
    }

    @Override
    public ResponseEntity updateZjTzRules(ZjTzRules zjTzRules) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRules dbzjTzRules = zjTzRulesMapper.selectByPrimaryKey(zjTzRules.getRulesId());
        if (dbzjTzRules != null && StrUtil.isNotEmpty(dbzjTzRules.getRulesId())) {
           if(StrUtil.equals(dbzjTzRules.getReleaseId(), "1")) {
        	   return repEntity.layerMessage("no", "???????????????????????????????????????");
           }
        	// ??????
           dbzjTzRules.setTitle(zjTzRules.getTitle());
           // ??????
           dbzjTzRules.setSymbolNo(zjTzRules.getSymbolNo());
           //????????????id
           dbzjTzRules.setReleaseRankId(zjTzRules.getReleaseRankId());
           //????????????name
           if (StrUtil.isNotEmpty(zjTzRules.getReleaseRankId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzRules.getReleaseRankId());
        	   dbzjTzRules.setReleaseRankName(openBankName);
           }
           // ????????????
           dbzjTzRules.setDepartmentName(zjTzRules.getDepartmentName());
           // ????????????
           dbzjTzRules.setReleaseDate(zjTzRules.getReleaseDate());
           // ??????????????????????????????
           dbzjTzRules.setAbolishSymbolNo(zjTzRules.getAbolishSymbolNo());
           // ????????????id
           dbzjTzRules.setEffectiveId(zjTzRules.getEffectiveId());
           // ??????????????????
           if(StrUtil.equals("1", zjTzRules.getEffectiveId())) {
        	   dbzjTzRules.setEffectiveName("???");
           } else {
        	   dbzjTzRules.setEffectiveName("???");
           }
           // ????????????
           dbzjTzRules.setRegisteredUser(zjTzRules.getRegisteredUser());
           // ??????
           dbzjTzRules.setRemarks(zjTzRules.getRemarks());
           // ??????
           dbzjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzRulesMapper.updateByPrimaryKey(dbzjTzRules);
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRules.getRulesId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // ??????list
           List<ZjTzFile> zjTzFileList = zjTzRules.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzRules.getRulesId());
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
            return repEntity.ok("sys.data.update",zjTzRules);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRules(List<ZjTzRules> zjTzRulesList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRulesList != null && zjTzRulesList.size() > 0) {
        	for (ZjTzRules zjTzRules : zjTzRulesList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
              	   return repEntity.layerMessage("no", "???????????????????????????????????????");
                 }
			}
        	// ????????????
        	for (ZjTzRules zjTzRules : zjTzRulesList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzRules.getRulesId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	
           ZjTzRules zjTzRules = new ZjTzRules();
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzRulesMapper.batchDeleteUpdateZjTzRules(zjTzRulesList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRulesList);
        }
    }

    @Override
    public ResponseEntity toHomeShowZjTzRules(ZjTzRules zjTzRules) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzRules dbzjTzRules = zjTzRulesMapper.selectByPrimaryKey(zjTzRules.getRulesId());
    	if (dbzjTzRules != null && StrUtil.isNotEmpty(dbzjTzRules.getRulesId())) {
    		if(StrUtil.equals(dbzjTzRules.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "???????????????????????????????????????????????????");
    		}
    		dbzjTzRules.setHomeShow("1");
    		dbzjTzRules.setHomeShowTime(new Date());
    		dbzjTzRules.setHomeShowStartDate(zjTzRules.getHomeShowStartDate());
    		dbzjTzRules.setHomeShowEndDate(zjTzRules.getHomeShowEndDate());
    		dbzjTzRules.setModifyUserInfo(userKey, realName);
    		flag = zjTzRulesMapper.updateByPrimaryKey(dbzjTzRules);
    		
    	}
    	// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzRules);
		}
		
    }

	@Override
	public ResponseEntity batchDeleteReleaseZjTzRules(List<ZjTzRules> zjTzRulesList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRulesList != null && zjTzRulesList.size() > 0) {
        	for (ZjTzRules zjTzRules : zjTzRulesList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "????????????????????????????????????????????????");
				}
			}
           ZjTzRules zjTzRules = new ZjTzRules();
           zjTzRules.setReleaseName("??????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzRulesMapper.batchDeleteReleaseZjTzRules(zjTzRulesList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRulesList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzRules(List<ZjTzRules> zjTzRulesList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRulesList != null && zjTzRulesList.size() > 0) {
        	for (ZjTzRules zjTzRules : zjTzRulesList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        		}
        	}
        	ZjTzRules zjTzRules = new ZjTzRules();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzRulesMapper.batchDeleteRecallZjTzRules(zjTzRulesList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRulesList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzRulesFile(List<ZjTzRules> zjTzRulesList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName = "????????????" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzRules total : zjTzRulesList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getRulesId());
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
	public ResponseEntity getZjTzRulesHome(ZjTzRules zjTzRules) {
		if (zjTzRules == null) {
            zjTzRules = new ZjTzRules();
        }
		List<Object> returnList = Lists.newArrayList();
		List<Object> dbreturnList = Lists.newArrayList();
		List<ZjTzRules> zjTzRulesList = zjTzRulesMapper.selectZjTzHomeRules(zjTzRules);
		if (zjTzRulesList != null && zjTzRulesList.size() > 0) {
			for (ZjTzRules dbzjTzRules : zjTzRulesList) {
				dbzjTzRules.setTypeName("????????????");
			}
			returnList.addAll(zjTzRulesList);
		}
		List<ZjTzImportDoc> zjTzImportDocList = zjTzImportDocMapper.selectZjTzHomeImportDoc(zjTzRules.getZjTzImportDoc());
		if (zjTzImportDocList != null && zjTzImportDocList.size() > 0) {
			for (ZjTzImportDoc dbzjTzImportDoc : zjTzImportDocList) {
				dbzjTzImportDoc.setTypeName("????????????");
			}
			returnList.addAll(zjTzImportDocList);
		}
		if (returnList != null && returnList.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				dbreturnList.add(returnList.get(i));
			}
		}else {
			dbreturnList.addAll(returnList);
		}
		    return repEntity.ok(dbreturnList);
	}
}
