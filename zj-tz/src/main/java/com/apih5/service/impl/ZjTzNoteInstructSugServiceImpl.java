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
import com.apih5.mybatis.dao.ZjTzNoteInstructSugMapper;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSug;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzNoteInstructSugService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzNoteInstructSugService")
public class ZjTzNoteInstructSugServiceImpl implements ZjTzNoteInstructSugService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzNoteInstructSugMapper zjTzNoteInstructSugMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzNoteInstructSugListByCondition(ZjTzNoteInstructSug zjTzNoteInstructSug) {
        if (zjTzNoteInstructSug == null) {
            zjTzNoteInstructSug = new ZjTzNoteInstructSug();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 增加权限查询条件
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzNoteInstructSug.setReleaseId("1");
        }
        // 分页查询
        PageHelper.startPage(zjTzNoteInstructSug.getPage(),zjTzNoteInstructSug.getLimit());
        // 获取数据
        List<ZjTzNoteInstructSug> zjTzNoteInstructSugList = zjTzNoteInstructSugMapper.selectByZjTzNoteInstructSugList(zjTzNoteInstructSug);
        for (ZjTzNoteInstructSug zjTzNoteInstructSug2 : zjTzNoteInstructSugList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzNoteInstructSug2.getNoteInstructSugId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzNoteInstructSug2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzNoteInstructSug> p = new PageInfo<>(zjTzNoteInstructSugList);

        return repEntity.okList(zjTzNoteInstructSugList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzNoteInstructSugDetails(ZjTzNoteInstructSug zjTzNoteInstructSug) {
        if (zjTzNoteInstructSug == null) {
            zjTzNoteInstructSug = new ZjTzNoteInstructSug();
        }
        // 获取数据
        ZjTzNoteInstructSug dbZjTzNoteInstructSug = zjTzNoteInstructSugMapper.selectByPrimaryKey(zjTzNoteInstructSug.getNoteInstructSugId());
        // 数据存在
        if (dbZjTzNoteInstructSug != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzNoteInstructSug.getNoteInstructSugId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzNoteInstructSug.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzNoteInstructSug);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzNoteInstructSug.setNoteInstructSugId(UuidUtil.generate());
        zjTzNoteInstructSug.setReleaseId("0");
        zjTzNoteInstructSug.setReleaseName("未发布");
        zjTzNoteInstructSug.setHomeShow("0");
        zjTzNoteInstructSug.setCreateUserInfo(userKey, realName);
        int flag = zjTzNoteInstructSugMapper.insert(zjTzNoteInstructSug);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzNoteInstructSug.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzNoteInstructSug.getNoteInstructSugId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzNoteInstructSug);
        }
    }

    @Override
    public ResponseEntity updateZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzNoteInstructSug dbzjTzNoteInstructSug = zjTzNoteInstructSugMapper.selectByPrimaryKey(zjTzNoteInstructSug.getNoteInstructSugId());
        if (dbzjTzNoteInstructSug != null && StrUtil.isNotEmpty(dbzjTzNoteInstructSug.getNoteInstructSugId())) {
           // 标题
           dbzjTzNoteInstructSug.setTitle(zjTzNoteInstructSug.getTitle());
           // 发布人
           dbzjTzNoteInstructSug.setPublisher(zjTzNoteInstructSug.getPublisher());
           // 发布日期
           dbzjTzNoteInstructSug.setReleaseDate(zjTzNoteInstructSug.getReleaseDate());
           // 主要内容
           dbzjTzNoteInstructSug.setRegisterPerson(zjTzNoteInstructSug.getRegisterPerson());
           // 共通
           dbzjTzNoteInstructSug.setModifyUserInfo(userKey, realName);
           flag = zjTzNoteInstructSugMapper.updateByPrimaryKey(dbzjTzNoteInstructSug);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzNoteInstructSug.getNoteInstructSugId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzNoteInstructSug.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzNoteInstructSug.getNoteInstructSugId());
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
            return repEntity.ok("sys.data.update",zjTzNoteInstructSug);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzNoteInstructSugList != null && zjTzNoteInstructSugList.size() > 0) {
        	for (ZjTzNoteInstructSug zjTzNoteInstructSug : zjTzNoteInstructSugList) {
        		if(StrUtil.equals(zjTzNoteInstructSug.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "已经发布，不能删除！");
        		}
        	}
        	// 删除附件
        	for (ZjTzNoteInstructSug zjTzNoteInstructSug : zjTzNoteInstructSugList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzNoteInstructSug.getNoteInstructSugId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzNoteInstructSug zjTzNoteInstructSug = new ZjTzNoteInstructSug();
           zjTzNoteInstructSug.setModifyUserInfo(userKey, realName);
           flag = zjTzNoteInstructSugMapper.batchDeleteUpdateZjTzNoteInstructSug(zjTzNoteInstructSugList, zjTzNoteInstructSug);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzNoteInstructSugList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzNoteInstructSug dbzjTzNoteInstructSug = zjTzNoteInstructSugMapper.selectByPrimaryKey(zjTzNoteInstructSug.getNoteInstructSugId());
		if (dbzjTzNoteInstructSug != null && StrUtil.isNotEmpty(dbzjTzNoteInstructSug.getNoteInstructSugId())) {
			if(StrUtil.equals(dbzjTzNoteInstructSug.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的不能广而告之，请先发布");
    		}
			dbzjTzNoteInstructSug.setHomeShow("1");
			dbzjTzNoteInstructSug.setHomeShowTime(new Date());
			dbzjTzNoteInstructSug.setHomeShowStartDate(zjTzNoteInstructSug.getHomeShowStartDate());
			dbzjTzNoteInstructSug.setHomeShowEndDate(zjTzNoteInstructSug.getHomeShowEndDate());
			dbzjTzNoteInstructSug.setModifyUserInfo(userKey, realName);
			flag = zjTzNoteInstructSugMapper.updateByPrimaryKey(dbzjTzNoteInstructSug);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzNoteInstructSug);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzNoteInstructSugList != null && zjTzNoteInstructSugList.size() > 0) {
        	for (ZjTzNoteInstructSug zjTzRules : zjTzNoteInstructSugList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的，请重新选择！");
				}
			}
        	ZjTzNoteInstructSug zjTzRules = new ZjTzNoteInstructSug();
           zjTzRules.setReleaseName("发布");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzNoteInstructSugMapper.batchReleaseZjTzNoteInstructSug(zjTzNoteInstructSugList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzNoteInstructSugList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzNoteInstructSug(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzNoteInstructSugList != null && zjTzNoteInstructSugList.size() > 0) {
        	for (ZjTzNoteInstructSug zjTzRules : zjTzNoteInstructSugList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的，请重新选择！");
        		}
        	}
        	ZjTzNoteInstructSug zjTzRules = new ZjTzNoteInstructSug();
        	zjTzRules.setReleaseName("未发布");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzNoteInstructSugMapper.batchRecallZjTzNoteInstructSug(zjTzNoteInstructSugList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzNoteInstructSugList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzNoteInstructSugFile(List<ZjTzNoteInstructSug> zjTzNoteInstructSugList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzNoteInstructSug total : zjTzNoteInstructSugList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getNoteInstructSugId());
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
	/**
	 * 
	 * 首页公告栏
	 */
	@Override
	public ResponseEntity getHomeZjTzNoteInstructSug(ZjTzNoteInstructSug zjTzNoteInstructSug) {
		if (zjTzNoteInstructSug == null) {
			zjTzNoteInstructSug = new ZjTzNoteInstructSug();
		}
		List<ZjTzNoteInstructSug> zjTzNoteInstructSugList = zjTzNoteInstructSugMapper.selectHomeZjTzNoteInstructSug(zjTzNoteInstructSug);
		return repEntity.ok(zjTzNoteInstructSugList);
	}
}
