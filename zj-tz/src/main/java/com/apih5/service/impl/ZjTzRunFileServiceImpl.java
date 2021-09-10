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
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzRunFileMapper;
import com.apih5.mybatis.pojo.ZjTzRunFile;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzRunFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzRunFileService")
public class ZjTzRunFileServiceImpl implements ZjTzRunFileService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRunFileMapper zjTzRunFileMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    

    @Override
    public ResponseEntity getZjTzRunFileListByCondition(ZjTzRunFile zjTzRunFile) {
        if (zjTzRunFile == null) {
            zjTzRunFile = new ZjTzRunFile();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzRunFile.getProjectId(), true)) {
            	zjTzRunFile.setProjectId("");
            	zjTzRunFile.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            if(StrUtil.equals("all", zjTzRunFile.getProjectId(), true)) {
            	zjTzRunFile.setProjectId("");
            }
        }
        
        // 分页查询
        PageHelper.startPage(zjTzRunFile.getPage(),zjTzRunFile.getLimit());
        // 获取数据
        List<ZjTzRunFile> zjTzRunFileList = zjTzRunFileMapper.selectByZjTzRunFileList(zjTzRunFile);
        for (ZjTzRunFile zjTzRunFile2 : zjTzRunFileList) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzRunFile2.getRunFileId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzRunFile2.setZjTzFileList(zjTzFileList);
        }
        // 得到分页信息
        PageInfo<ZjTzRunFile> p = new PageInfo<>(zjTzRunFileList);

        return repEntity.okList(zjTzRunFileList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRunFileDetails(ZjTzRunFile zjTzRunFile) {
        if (zjTzRunFile == null) {
            zjTzRunFile = new ZjTzRunFile();
        }
        // 获取数据
        ZjTzRunFile dbZjTzRunFile = zjTzRunFileMapper.selectByPrimaryKey(zjTzRunFile.getRunFileId());
        // 数据存在
        if (dbZjTzRunFile != null) {
            return repEntity.ok(dbZjTzRunFile);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRunFile(ZjTzRunFile zjTzRunFile) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	//项目名称
    	if(StrUtil.isNotEmpty(zjTzRunFile.getProjectId())) {
    		ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzRunFile.getProjectId());
    		if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
    			zjTzRunFile.setProjectName(manage.getProjectName());
    		}
    	}
        zjTzRunFile.setRunFileId(UuidUtil.generate());
        zjTzRunFile.setCreateUserInfo(userKey, realName);
        int flag = zjTzRunFileMapper.insert(zjTzRunFile);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzRunFile.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzRunFile.getRunFileId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRunFile);
        }
    }

    @Override
    public ResponseEntity updateZjTzRunFile(ZjTzRunFile zjTzRunFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRunFile dbzjTzRunFile = zjTzRunFileMapper.selectByPrimaryKey(zjTzRunFile.getRunFileId());
        if (dbzjTzRunFile != null && StrUtil.isNotEmpty(dbzjTzRunFile.getRunFileId())) {
           // 项目id
           dbzjTzRunFile.setProjectId(zjTzRunFile.getProjectId());
           // 项目name
           if(StrUtil.isNotEmpty(zjTzRunFile.getProjectId())) {
        	   ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzRunFile.getProjectId());
        	   if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        		   dbzjTzRunFile.setProjectName(manage.getProjectName());
        	   }
           }
           // 文件类别
           dbzjTzRunFile.setFileType(zjTzRunFile.getFileType());
           //文件类型
           dbzjTzRunFile.setFileForm(zjTzRunFile.getFileForm());
           // 文号
           dbzjTzRunFile.setReferenceNumber(zjTzRunFile.getReferenceNumber());
           // 文件名称
           dbzjTzRunFile.setFileName(zjTzRunFile.getFileName());
           // 发布时间
           dbzjTzRunFile.setReleaseDate(zjTzRunFile.getReleaseDate());
           // 是否有效
           dbzjTzRunFile.setEffectFlag(zjTzRunFile.getEffectFlag());
           // 主要内容或变化说明
           dbzjTzRunFile.setContent(zjTzRunFile.getContent());
           // 共通
           dbzjTzRunFile.setModifyUserInfo(userKey, realName);
           flag = zjTzRunFileMapper.updateByPrimaryKey(dbzjTzRunFile);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRunFile.getRunFileId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzRunFile.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(dbzjTzRunFile.getRunFileId());
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
            return repEntity.ok("sys.data.update",zjTzRunFile);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRunFile(List<ZjTzRunFile> zjTzRunFileList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRunFileList != null && zjTzRunFileList.size() > 0) {
        	for (ZjTzRunFile zjTzRunFile : zjTzRunFileList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzRunFile.getRunFileId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzRunFile zjTzRunFile = new ZjTzRunFile();
           zjTzRunFile.setModifyUserInfo(userKey, realName);
           flag = zjTzRunFileMapper.batchDeleteUpdateZjTzRunFile(zjTzRunFileList, zjTzRunFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRunFileList);
        }
    }

	@Override
	public ResponseEntity batchExportZjTzRunFile(List<ZjTzRunFile> zjTzRunFileList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName = "运营文件管理" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzRunFile total : zjTzRunFileList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getRunFileId());
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
