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
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzAnnualAssessMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzAnnualAssess;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzAnnualAssessService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzAnnualAssessService")
public class ZjTzAnnualAssessServiceImpl implements ZjTzAnnualAssessService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzAnnualAssessMapper zjTzAnnualAssessMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzAnnualAssessListByCondition(ZjTzAnnualAssess zjTzAnnualAssess) {
        if (zjTzAnnualAssess == null) {
            zjTzAnnualAssess = new ZjTzAnnualAssess();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzAnnualAssess.getProjectId(), true)) {
            	zjTzAnnualAssess.setProjectId("");
            	zjTzAnnualAssess.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzAnnualAssess.getProjectId(), true)) {
            	zjTzAnnualAssess.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzAnnualAssess.getPage(),zjTzAnnualAssess.getLimit());
        // 获取数据
        List<ZjTzAnnualAssess> zjTzAnnualAssessList = zjTzAnnualAssessMapper.selectByZjTzAnnualAssessList(zjTzAnnualAssess);
        for (ZjTzAnnualAssess zjTzAnnualAssess2 : zjTzAnnualAssessList) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzAnnualAssess2.getAnnualAssessId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzAnnualAssess2.setZjTzFileList(zjTzFileList);
        }
        // 得到分页信息
        PageInfo<ZjTzAnnualAssess> p = new PageInfo<>(zjTzAnnualAssessList);

        return repEntity.okList(zjTzAnnualAssessList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzAnnualAssessDetails(ZjTzAnnualAssess zjTzAnnualAssess) {
        if (zjTzAnnualAssess == null) {
            zjTzAnnualAssess = new ZjTzAnnualAssess();
        }
        // 获取数据
        ZjTzAnnualAssess dbZjTzAnnualAssess = zjTzAnnualAssessMapper.selectByPrimaryKey(zjTzAnnualAssess.getAnnualAssessId());
        // 数据存在
        if (dbZjTzAnnualAssess != null) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzAnnualAssess.getAnnualAssessId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzAnnualAssess.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzAnnualAssess);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzAnnualAssess(ZjTzAnnualAssess zjTzAnnualAssess) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zjTzAnnualAssess.getYearDate() == null) {
        	 return repEntity.layerMessage("no", "请选择年度！");	
        }
        ZjTzProManage manage =  zjTzProManageMapper.selectByPrimaryKey(zjTzAnnualAssess.getProjectId());
        if(manage != null) {
        	zjTzAnnualAssess.setProjectName(manage.getProjectName());
        }
        //年度重复和托管项目=重复校验
        ZjTzAnnualAssess advistoryUnitStandard = new ZjTzAnnualAssess();
        advistoryUnitStandard.setYearStr(DateUtil.format(zjTzAnnualAssess.getYearDate(), "yyyy"));
    	advistoryUnitStandard.setProjectId(zjTzAnnualAssess.getProjectId());
    	List<ZjTzAnnualAssess> advistoryUnitStandards = zjTzAnnualAssessMapper.selectByZjTzAnnualAssessList(advistoryUnitStandard);
    	if(advistoryUnitStandards != null && advistoryUnitStandards.size() >0) {
    		 return repEntity.layerMessage("no", "托管项目已存在，请重新输入！");
    	}
        zjTzAnnualAssess.setAnnualAssessId(UuidUtil.generate());
        zjTzAnnualAssess.setYearStr(DateUtil.format(zjTzAnnualAssess.getYearDate(), "yyyy"));
        // 是否下达id
        zjTzAnnualAssess.setReleaseId("0");
        // 是否下达name
        zjTzAnnualAssess.setReleaseName("未上报");
        zjTzAnnualAssess.setCreateUserInfo(userKey, realName);
        int flag = zjTzAnnualAssessMapper.insert(zjTzAnnualAssess);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzAnnualAssess.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzAnnualAssess.getAnnualAssessId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzAnnualAssess);
        }
    }

    @Override
    public ResponseEntity updateZjTzAnnualAssess(ZjTzAnnualAssess zjTzAnnualAssess) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzAnnualAssess dbzjTzAnnualAssess = zjTzAnnualAssessMapper.selectByPrimaryKey(zjTzAnnualAssess.getAnnualAssessId());
        if (dbzjTzAnnualAssess != null && StrUtil.isNotEmpty(dbzjTzAnnualAssess.getAnnualAssessId())) {
        	if(StrUtil.equals(dbzjTzAnnualAssess.getReleaseId(), "1")){			
				 return repEntity.layerMessage("no", "已上报的不能修改，请先撤回！");
			}
        	
//           // 考核年度（我自己用的）
//           dbzjTzAnnualAssess.setYearStr(zjTzAnnualAssess.getYearStr());
//           // 考核年度
//           dbzjTzAnnualAssess.setYearDate(zjTzAnnualAssess.getYearDate());
//           // 托管公司id
//           dbzjTzAnnualAssess.setTrusteeCompanyId(zjTzAnnualAssess.getTrusteeCompanyId());
//           // 托管公司name
//           dbzjTzAnnualAssess.setTrusteeCompanyName(zjTzAnnualAssess.getTrusteeCompanyName());
//           // 托管项目id
//           dbzjTzAnnualAssess.setProjectId(zjTzAnnualAssess.getProjectId());
//           // 托管项目name
//           dbzjTzAnnualAssess.setProjectName(zjTzAnnualAssess.getProjectName());
           // 考核结果简述
           dbzjTzAnnualAssess.setResult(zjTzAnnualAssess.getResult());
           // 登记日期
           dbzjTzAnnualAssess.setRegisterDate(zjTzAnnualAssess.getRegisterDate());
           // 登记用户
           dbzjTzAnnualAssess.setRegisterPerson(zjTzAnnualAssess.getRegisterPerson());
           // 流程id
           dbzjTzAnnualAssess.setWorkId(zjTzAnnualAssess.getWorkId());
           // 流程状态
           dbzjTzAnnualAssess.setApih5FlowStatus(zjTzAnnualAssess.getApih5FlowStatus());
           if (StrUtil.equals("opinionField2", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField2(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField2(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField3", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField3(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField3(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField4", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField4(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField4(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField5", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField5(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField5(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField6", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField6(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField6(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField7", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField7(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField7(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField8", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField8(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField8(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField9", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField9(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField9(), zjTzAnnualAssess.getOpinionContent()));
           }
           if (StrUtil.equals("opinionField10", zjTzAnnualAssess.getOpinionField(), true)) {
        	   dbzjTzAnnualAssess.setOpinionField10(getOpinionContent(realName, dbzjTzAnnualAssess.getOpinionField10(), zjTzAnnualAssess.getOpinionContent()));
           }
           // 共通
           dbzjTzAnnualAssess.setModifyUserInfo(userKey, realName);
           flag = zjTzAnnualAssessMapper.updateByPrimaryKey(dbzjTzAnnualAssess);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzAnnualAssess.getAnnualAssessId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzAnnualAssess.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(dbzjTzAnnualAssess.getAnnualAssessId());
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
            return repEntity.ok("sys.data.update",zjTzAnnualAssess);
        }
    }
    
    private String getOpinionContent(String userName, String dbOpinionContent, String opinionContent){
  		if(StrUtil.isNotEmpty(opinionContent)){
  			opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
  			opinionContent += "<br/>" + userName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
  		}
  		return opinionContent;
  	}

    @Override
    public ResponseEntity batchDeleteUpdateZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zjTzAnnualAssessList != null && zjTzAnnualAssessList.size() > 0) {
        	for (ZjTzAnnualAssess zjTzRules : zjTzAnnualAssessList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经上报的，请重新选择！");
				}
				zjTzRules = zjTzAnnualAssessMapper.selectByPrimaryKey(zjTzRules.getAnnualAssessId());
    			if(StrUtil.isEmpty(zjTzRules.getApih5FlowStatus())){

    			}else if(zjTzRules.getApih5FlowStatus().equals("0")){

    			}else{
    				return repEntity.layerMessage("no", "审核中的数据不让删除，请重新选择");
    			}
			} 
        	//删除
        	JSONArray jsonArray = new JSONArray();
        	for (ZjTzAnnualAssess zjTzAnnualAssess : zjTzAnnualAssessList) {
        		jsonArray.add(zjTzAnnualAssess.getWorkId());
        		
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzAnnualAssess.getAnnualAssessId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
    		String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
    		if(StrUtil.isNotEmpty(delFlowResult)){
    			JSONObject resultJson = new JSONObject(delFlowResult);
    			if(resultJson.getBool("success")){
    				ZjTzAnnualAssess zjTzAnnualAssess = new ZjTzAnnualAssess();
    				zjTzAnnualAssess.setModifyUserInfo(userKey, realName);
    				flag = zjTzAnnualAssessMapper.batchDeleteUpdateZjTzAnnualAssess(zjTzAnnualAssessList, zjTzAnnualAssess);
    			}
    		}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzAnnualAssessList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzAnnualAssessList != null && zjTzAnnualAssessList.size() > 0) {
        	for (ZjTzAnnualAssess zjTzRules : zjTzAnnualAssessList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已上报的，请重新选择！");
				}
				
			}
        	ZjTzAnnualAssess zjTzRules = new ZjTzAnnualAssess();
           zjTzRules.setReleaseName("已上报");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzAnnualAssessMapper.batchReleaseZjTzAnnualAssess(zjTzAnnualAssessList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzAnnualAssessList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzAnnualAssessList != null && zjTzAnnualAssessList.size() > 0) {
        	for (ZjTzAnnualAssess zjTzRules : zjTzAnnualAssessList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "2")) {
        			return repEntity.layerMessage("no", "包含未上报和被退回的数据，请重新选择！");
        		}
        	}
        	ZjTzAnnualAssess zjTzRules = new ZjTzAnnualAssess();
        	zjTzRules.setReleaseName("被退回");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzAnnualAssessMapper.batchRecallZjTzAnnualAssess(zjTzAnnualAssessList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzAnnualAssessList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzAnnualAssessFile(List<ZjTzAnnualAssess> zjTzAnnualAssessList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzAnnualAssess total : zjTzAnnualAssessList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getAnnualAssessId());
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
}
