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
import com.apih5.mybatis.dao.ZjTzProPreApprovalMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzProPreApproval;
import com.apih5.service.ZjTzProPreApprovalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzProPreApprovalService")
public class ZjTzProPreApprovalServiceImpl implements ZjTzProPreApprovalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProPreApprovalMapper zjTzProPreApprovalMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Override
    public ResponseEntity getZjTzProPreApprovalListByCondition(ZjTzProPreApproval zjTzProPreApproval) {
        if (zjTzProPreApproval == null) {
            zjTzProPreApproval = new ZjTzProPreApproval();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // ????????????????????????
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzProPreApproval.setReleaseId("1");
        }
        // ????????????
        PageHelper.startPage(zjTzProPreApproval.getPage(),zjTzProPreApproval.getLimit());
        // ????????????
        List<ZjTzProPreApproval> zjTzProPreApprovalList = zjTzProPreApprovalMapper.selectByZjTzProPreApprovalList(zjTzProPreApproval);
        for (ZjTzProPreApproval zjTzProPreApproval2 : zjTzProPreApprovalList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzProPreApproval2.getProPreApprovalId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzProPreApproval2.setZjTzFileList(zjTzFileList);
		}
        // ??????????????????
        PageInfo<ZjTzProPreApproval> p = new PageInfo<>(zjTzProPreApprovalList);

        return repEntity.okList(zjTzProPreApprovalList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProPreApprovalDetails(ZjTzProPreApproval zjTzProPreApproval) {
        if (zjTzProPreApproval == null) {
            zjTzProPreApproval = new ZjTzProPreApproval();
        }
        // ????????????
        ZjTzProPreApproval dbZjTzProPreApproval = zjTzProPreApprovalMapper.selectByPrimaryKey(zjTzProPreApproval.getProPreApprovalId());
        // ????????????
        if (dbZjTzProPreApproval != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzProPreApproval.getProPreApprovalId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProPreApproval.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzProPreApproval);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzProPreApproval(ZjTzProPreApproval zjTzProPreApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzProPreApproval.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzProPreApproval.setProjectName(manage.getProjectName());
        }
        zjTzProPreApproval.setProPreApprovalId(UuidUtil.generate());
        zjTzProPreApproval.setReleaseId("0");
        zjTzProPreApproval.setReleaseName("?????????");
        zjTzProPreApproval.setCreateUserInfo(userKey, realName);
        int flag = zjTzProPreApprovalMapper.insert(zjTzProPreApproval);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzProPreApproval.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzProPreApproval.getProPreApprovalId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProPreApproval);
        }
    }

    @Override
    public ResponseEntity updateZjTzProPreApproval(ZjTzProPreApproval zjTzProPreApproval) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProPreApproval dbzjTzProPreApproval = zjTzProPreApprovalMapper.selectByPrimaryKey(zjTzProPreApproval.getProPreApprovalId());
        if (dbzjTzProPreApproval != null && StrUtil.isNotEmpty(dbzjTzProPreApproval.getProPreApprovalId())) {
           // ??????
           dbzjTzProPreApproval.setTitle(zjTzProPreApproval.getTitle());
           // ????????????
           dbzjTzProPreApproval.setContent(zjTzProPreApproval.getContent());
           // ??????
           dbzjTzProPreApproval.setModifyUserInfo(userKey, realName);
           flag = zjTzProPreApprovalMapper.updateByPrimaryKey(dbzjTzProPreApproval);
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzProPreApproval.getProPreApprovalId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzProPreApproval.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzProPreApproval.getProPreApprovalId());
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
            return repEntity.ok("sys.data.update",zjTzProPreApproval);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProPreApprovalList != null && zjTzProPreApprovalList.size() > 0) {
//        	for (ZjTzProPreApproval zjTzProPreApproval : zjTzProPreApprovalList) {
//        		if(StrUtil.equals(zjTzProPreApproval.getReleaseId(), "1")) {
//        			return repEntity.layerMessage("no", "???????????????????????????");
//        		}
//        	}
        	// ????????????
        	for (ZjTzProPreApproval zjTzProPreApproval : zjTzProPreApprovalList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzProPreApproval.getProPreApprovalId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzProPreApproval zjTzProPreApproval = new ZjTzProPreApproval();
           zjTzProPreApproval.setModifyUserInfo(userKey, realName);
           flag = zjTzProPreApprovalMapper.batchDeleteUpdateZjTzProPreApproval(zjTzProPreApprovalList, zjTzProPreApproval);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProPreApprovalList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProPreApprovalList != null && zjTzProPreApprovalList.size() > 0) {
        	for (ZjTzProPreApproval zjTzRules : zjTzProPreApprovalList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
				}
			}
        	ZjTzProPreApproval zjTzRules = new ZjTzProPreApproval();
           zjTzRules.setReleaseName("?????????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzProPreApprovalMapper.batchReleaseZjTzProPreApproval(zjTzProPreApprovalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProPreApprovalList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProPreApprovalList != null && zjTzProPreApprovalList.size() > 0) {
        	for (ZjTzProPreApproval zjTzRules : zjTzProPreApprovalList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	ZjTzProPreApproval zjTzRules = new ZjTzProPreApproval();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzProPreApprovalMapper.batchRecallZjTzProPreApproval(zjTzProPreApprovalList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProPreApprovalList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzProPreApprovalFile(List<ZjTzProPreApproval> zjTzProPreApprovalList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzProPreApproval total : zjTzProPreApprovalList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getProPreApprovalId());
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
