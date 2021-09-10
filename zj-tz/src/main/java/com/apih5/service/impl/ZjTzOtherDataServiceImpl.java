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
import com.apih5.mybatis.dao.ZjTzOtherDataMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzOtherData;
import com.apih5.service.ZjTzOtherDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzOtherDataService")
public class ZjTzOtherDataServiceImpl implements ZjTzOtherDataService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzOtherDataMapper zjTzOtherDataMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzOtherDataListByCondition(ZjTzOtherData zjTzOtherData) {
        if (zjTzOtherData == null) {
            zjTzOtherData = new ZjTzOtherData();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 增加权限查询条件
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzOtherData.setReleaseId("1");
        }
        // 分页查询
        PageHelper.startPage(zjTzOtherData.getPage(),zjTzOtherData.getLimit());
        // 获取数据
        List<ZjTzOtherData> zjTzOtherDataList = zjTzOtherDataMapper.selectByZjTzOtherDataList(zjTzOtherData);
        for (ZjTzOtherData OtherData : zjTzOtherDataList) {
       	 ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(OtherData.getOtherDataId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            OtherData.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzOtherData> p = new PageInfo<>(zjTzOtherDataList);

        return repEntity.okList(zjTzOtherDataList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzOtherDataDetails(ZjTzOtherData zjTzOtherData) {
        if (zjTzOtherData == null) {
            zjTzOtherData = new ZjTzOtherData();
        }
        // 获取数据
        ZjTzOtherData dbZjTzOtherData = zjTzOtherDataMapper.selectByPrimaryKey(zjTzOtherData.getOtherDataId());
        // 数据存在
        if (dbZjTzOtherData != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbZjTzOtherData.getOtherDataId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            dbZjTzOtherData.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzOtherData);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzOtherData(ZjTzOtherData zjTzOtherData) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzOtherData.setOtherDataId(UuidUtil.generate());
        zjTzOtherData.setReleaseId("0");
        zjTzOtherData.setReleaseName("未发布");
        zjTzOtherData.setHomeShow("0");
        zjTzOtherData.setCreateUserInfo(userKey, realName);
        int flag = zjTzOtherDataMapper.insert(zjTzOtherData);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzOtherData.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzOtherData.getOtherDataId());
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzOtherData);
        }
    }

    @Override
    public ResponseEntity updateZjTzOtherData(ZjTzOtherData zjTzOtherData) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzOtherData dbzjTzOtherData = zjTzOtherDataMapper.selectByPrimaryKey(zjTzOtherData.getOtherDataId());
        if (dbzjTzOtherData != null && StrUtil.isNotEmpty(dbzjTzOtherData.getOtherDataId())) {
        	if(StrUtil.equals(dbzjTzOtherData.getReleaseId(), "1")) {
         	   return repEntity.layerMessage("no", "该其他资料已经发布，不能修改！");
            }
        	// 标题
           dbzjTzOtherData.setTitle(zjTzOtherData.getTitle());
           // 发布人
           dbzjTzOtherData.setPublisher(zjTzOtherData.getPublisher());
           // 发布日期
           dbzjTzOtherData.setReleaseDate(zjTzOtherData.getReleaseDate());
           // 主要内容
           dbzjTzOtherData.setContent(zjTzOtherData.getContent());
           // 备注
           dbzjTzOtherData.setRemarks(zjTzOtherData.getRemarks());
           //文件类型
           dbzjTzOtherData.setFileType(zjTzOtherData.getFileType());
           // 共通
           dbzjTzOtherData.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDataMapper.updateByPrimaryKey(dbzjTzOtherData);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzOtherData.getOtherDataId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzOtherData.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzOtherData.getOtherDataId());
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
            return repEntity.ok("sys.data.update",zjTzOtherData);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzOtherDataList != null && zjTzOtherDataList.size() > 0) {
        	for (ZjTzOtherData zjTzOtherData : zjTzOtherDataList) {
        		if(StrUtil.equals(zjTzOtherData.getReleaseId(), "1")) {
              	   return repEntity.layerMessage("no", "该其他资料已经发布，不能删除！");
                 }
			}
        	// 删除附件
        	for (ZjTzOtherData zjTzOtherData : zjTzOtherDataList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzOtherData.getOtherDataId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	
        	ZjTzOtherData zjTzOtherData = new ZjTzOtherData();
           zjTzOtherData.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDataMapper.batchDeleteUpdateZjTzOtherData(zjTzOtherDataList, zjTzOtherData);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzOtherDataList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzOtherData(ZjTzOtherData zjTzOtherData) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzOtherData dbzjTzOtherData = zjTzOtherDataMapper.selectByPrimaryKey(zjTzOtherData.getOtherDataId());
    	if (dbzjTzOtherData != null && StrUtil.isNotEmpty(dbzjTzOtherData.getOtherDataId())) {
    		if(StrUtil.equals(dbzjTzOtherData.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的其他资料不能广而告之，请先发布");
    		}
    		dbzjTzOtherData.setHomeShow("1");
    		dbzjTzOtherData.setHomeShowTime(new Date());
    		dbzjTzOtherData.setHomeShowStartDate(zjTzOtherData.getHomeShowStartDate());
    		dbzjTzOtherData.setHomeShowEndDate(zjTzOtherData.getHomeShowEndDate());
    		dbzjTzOtherData.setModifyUserInfo(userKey, realName);
    		flag = zjTzOtherDataMapper.updateByPrimaryKey(dbzjTzOtherData);
    		
    	}
    	// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zjTzOtherData);
		}
		
	}

	@Override
	public ResponseEntity batchDeleteReleaseZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzOtherDataList != null && zjTzOtherDataList.size() > 0) {
        	for (ZjTzOtherData zjTzOtherData : zjTzOtherDataList) {
				if(StrUtil.equals(zjTzOtherData.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的其他资料，请重新选择！");
				}
			}
           ZjTzOtherData zjTzOtherData = new ZjTzOtherData();
           zjTzOtherData.setReleaseName("发布");
           zjTzOtherData.setModifyUserInfo(userKey, realName);
           flag = zjTzOtherDataMapper.batchDeleteReleaseZjTzOtherData(zjTzOtherDataList, zjTzOtherData);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzOtherDataList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzOtherDataList != null && zjTzOtherDataList.size() > 0) {
        	for (ZjTzOtherData zjTzOtherData : zjTzOtherDataList) {
        		if(StrUtil.equals(zjTzOtherData.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的其他资料，请重新选择！");
        		}
        	}
        	ZjTzOtherData zjTzOtherData = new ZjTzOtherData();
        	zjTzOtherData.setReleaseName("未发布");
        	zjTzOtherData.setHomeShow("0");
        	zjTzOtherData.setModifyUserInfo(userKey, realName);
        	flag = zjTzOtherDataMapper.batchDeleteRecallZjTzOtherData(zjTzOtherDataList, zjTzOtherData);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzOtherDataList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzOtherDataFile(List<ZjTzOtherData> zjTzOtherDataList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName = "其他资料" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzOtherData total : zjTzOtherDataList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getOtherDataId());
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
	 * 其他资料
	 */
	@Override
	public ResponseEntity getZjTzOtherDataHome(ZjTzOtherData zjTzOtherData) {
		if (zjTzOtherData == null) {
			zjTzOtherData = new ZjTzOtherData();
		}
		List<Object> returnList = Lists.newArrayList();
		List<ZjTzOtherData> zjTzOtherDataList = zjTzOtherDataMapper.selectZjTzHomeOtherData(zjTzOtherData);
		if (zjTzOtherDataList != null && zjTzOtherDataList.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				returnList.add(zjTzOtherDataList.get(i));
			}
		}else {
			returnList.addAll(zjTzOtherDataList);
		}
		return repEntity.ok(returnList);
	}
}
