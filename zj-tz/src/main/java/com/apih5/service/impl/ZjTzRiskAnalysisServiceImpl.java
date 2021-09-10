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
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzRiskAnalysisMapper;
import com.apih5.mybatis.pojo.ZjTzRiskAnalysis;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzRiskAnalysisService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzRiskAnalysisService")
public class ZjTzRiskAnalysisServiceImpl implements ZjTzRiskAnalysisService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRiskAnalysisMapper zjTzRiskAnalysisMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzRiskAnalysisListByCondition(ZjTzRiskAnalysis zjTzRiskAnalysis) {
        if (zjTzRiskAnalysis == null) {
            zjTzRiskAnalysis = new ZjTzRiskAnalysis();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzRiskAnalysis.getProjectId(), true)) {
            	zjTzRiskAnalysis.setProjectId("");
            	zjTzRiskAnalysis.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzRiskAnalysis.getProjectId(), true)) {
            	zjTzRiskAnalysis.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzRiskAnalysis.getPage(),zjTzRiskAnalysis.getLimit());
        // 获取数据
        List<ZjTzRiskAnalysis> zjTzRiskAnalysisList = zjTzRiskAnalysisMapper.selectByZjTzRiskAnalysisList(zjTzRiskAnalysis);
        for (ZjTzRiskAnalysis rules : zjTzRiskAnalysisList) {
       	 ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(rules.getRiskAnalysisId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            rules.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzRiskAnalysis> p = new PageInfo<>(zjTzRiskAnalysisList);

        return repEntity.okList(zjTzRiskAnalysisList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRiskAnalysisDetails(ZjTzRiskAnalysis zjTzRiskAnalysis) {
        if (zjTzRiskAnalysis == null) {
            zjTzRiskAnalysis = new ZjTzRiskAnalysis();
        }
        // 获取数据
        ZjTzRiskAnalysis dbZjTzRiskAnalysis = zjTzRiskAnalysisMapper.selectByPrimaryKey(zjTzRiskAnalysis.getRiskAnalysisId());
        // 数据存在
        if (dbZjTzRiskAnalysis != null) {
        	 ZjTzFile zjTzFileSelect = new ZjTzFile();
             zjTzFileSelect.setOtherId(dbZjTzRiskAnalysis.getRiskAnalysisId());
             List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
             dbZjTzRiskAnalysis.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzRiskAnalysis);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRiskAnalysis(ZjTzRiskAnalysis zjTzRiskAnalysis) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzRiskAnalysis.setRiskAnalysisId(UuidUtil.generate());
        zjTzRiskAnalysis.setReleaseId("0");
        zjTzRiskAnalysis.setReleaseName("未发布");
        zjTzRiskAnalysis.setCreateUserInfo(userKey, realName);
        int flag = zjTzRiskAnalysisMapper.insert(zjTzRiskAnalysis);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzRiskAnalysis.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzRiskAnalysis.getRiskAnalysisId());
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRiskAnalysis);
        }
    }

    @Override
    public ResponseEntity updateZjTzRiskAnalysis(ZjTzRiskAnalysis zjTzRiskAnalysis) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRiskAnalysis dbzjTzRiskAnalysis = zjTzRiskAnalysisMapper.selectByPrimaryKey(zjTzRiskAnalysis.getRiskAnalysisId());
        if (dbzjTzRiskAnalysis != null && StrUtil.isNotEmpty(dbzjTzRiskAnalysis.getRiskAnalysisId())) {
           // 项目id
           dbzjTzRiskAnalysis.setProjectId(zjTzRiskAnalysis.getProjectId());
           // 项目名称
           dbzjTzRiskAnalysis.setProjectName(zjTzRiskAnalysis.getProjectName());
           // 年度
           dbzjTzRiskAnalysis.setRiskYear(zjTzRiskAnalysis.getRiskYear());
           // 文件名称
           dbzjTzRiskAnalysis.setFileName(zjTzRiskAnalysis.getFileName());
           // 主要内容
           dbzjTzRiskAnalysis.setMainContent(zjTzRiskAnalysis.getMainContent());
           // 共通
           dbzjTzRiskAnalysis.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskAnalysisMapper.updateByPrimaryKey(dbzjTzRiskAnalysis);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRiskAnalysis.getRiskAnalysisId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzRiskAnalysis.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzRiskAnalysis.getRiskAnalysisId());
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
            return repEntity.ok("sys.data.update",zjTzRiskAnalysis);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskAnalysisList != null && zjTzRiskAnalysisList.size() > 0) {
        	for (ZjTzRiskAnalysis zjTzRiskAnalysis : zjTzRiskAnalysisList) {
        		if(StrUtil.equals(zjTzRiskAnalysis.getReleaseId(), "1")) {
              	   return repEntity.layerMessage("no", "该制度已经发布，不能删除！");
                 }
			}
        	// 删除附件
        	for (ZjTzRiskAnalysis zjTzRiskAnalysis : zjTzRiskAnalysisList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzRiskAnalysis.getRiskAnalysisId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzRiskAnalysis zjTzRiskAnalysis = new ZjTzRiskAnalysis();
           zjTzRiskAnalysis.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskAnalysisMapper.batchDeleteUpdateZjTzRiskAnalysis(zjTzRiskAnalysisList, zjTzRiskAnalysis);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRiskAnalysisList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskAnalysisList != null && zjTzRiskAnalysisList.size() > 0) {
        	for (ZjTzRiskAnalysis zjTzRules : zjTzRiskAnalysisList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已上报的，请重新选择！");
				}
				
			}
        	ZjTzRiskAnalysis zjTzRules = new ZjTzRiskAnalysis();
           zjTzRules.setReleaseName("已上报");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskAnalysisMapper.batchReleaseZjTzRiskAnalysis(zjTzRiskAnalysisList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskAnalysisList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskAnalysisList != null && zjTzRiskAnalysisList.size() > 0) {
        	for (ZjTzRiskAnalysis zjTzRules : zjTzRiskAnalysisList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "2")) {
        			return repEntity.layerMessage("no", "包含未上报和被退回的数据，请重新选择！");
        		}
        	}
        	ZjTzRiskAnalysis zjTzRules = new ZjTzRiskAnalysis();
        	zjTzRules.setReleaseName("被退回");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzRiskAnalysisMapper.batchRecallZjTzRiskAnalysis(zjTzRiskAnalysisList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskAnalysisList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzRiskAnalysisFile(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName ="风险管理分析报告"+DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzRiskAnalysis total : zjTzRiskAnalysisList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getRiskAnalysisId());
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
