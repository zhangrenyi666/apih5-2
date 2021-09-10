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
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzImportDoc;
import com.apih5.service.ZjTzImportDocService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzImportDocService")
public class ZjTzImportDocServiceImpl implements ZjTzImportDocService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzImportDocMapper zjTzImportDocMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;


    @Override
    public ResponseEntity getZjTzImportDocListByCondition(ZjTzImportDoc zjTzImportDoc) {
        if (zjTzImportDoc == null) {
            zjTzImportDoc = new ZjTzImportDoc();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 增加权限查询条件
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzImportDoc.setReleaseId("1");
        }
        // 分页查询
        PageHelper.startPage(zjTzImportDoc.getPage(),zjTzImportDoc.getLimit());
        // 获取数据
        List<ZjTzImportDoc> zjTzImportDocList = zjTzImportDocMapper.selectByZjTzImportDocList(zjTzImportDoc);
        for (ZjTzImportDoc ImportDoc : zjTzImportDocList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(ImportDoc.getImportDocId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	ImportDoc.setZjTzFileList(zjTzFileList);
        }
        // 得到分页信息
        PageInfo<ZjTzImportDoc> p = new PageInfo<>(zjTzImportDocList);

        return repEntity.okList(zjTzImportDocList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzImportDocDetails(ZjTzImportDoc zjTzImportDoc) {
        if (zjTzImportDoc == null) {
            zjTzImportDoc = new ZjTzImportDoc();
        }
        // 获取数据
        ZjTzImportDoc dbZjTzImportDoc = zjTzImportDocMapper.selectByPrimaryKey(zjTzImportDoc.getImportDocId());
        // 数据存在
        if (dbZjTzImportDoc != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbZjTzImportDoc.getImportDocId());
            List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            dbZjTzImportDoc.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzImportDoc);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzImportDoc(ZjTzImportDoc zjTzImportDoc) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzImportDoc.setImportDocId(UuidUtil.generate());
        zjTzImportDoc.setImportDocId(UuidUtil.generate());
        if(StrUtil.equals("1", zjTzImportDoc.getEffectiveId())) {
        	zjTzImportDoc.setEffectiveName("是");
        } else {
        	zjTzImportDoc.setEffectiveName("否");
        }
        //发文层级
        if (StrUtil.isNotEmpty(zjTzImportDoc.getReleaseRankId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzImportDoc.getReleaseRankId());
        	zjTzImportDoc.setReleaseRankName(openBankName);
        }
        zjTzImportDoc.setReleaseId("0");
        zjTzImportDoc.setReleaseName("未发布");
        zjTzImportDoc.setHomeShow("0");
        zjTzImportDoc.setCreateUserInfo(userKey, realName);
        int flag = zjTzImportDocMapper.insert(zjTzImportDoc);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzImportDoc.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzImportDoc.getImportDocId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzImportDoc);
        }
    }

    @Override
    public ResponseEntity updateZjTzImportDoc(ZjTzImportDoc zjTzImportDoc) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzImportDoc dbzjTzImportDoc = zjTzImportDocMapper.selectByPrimaryKey(zjTzImportDoc.getImportDocId());
        if (dbzjTzImportDoc != null && StrUtil.isNotEmpty(dbzjTzImportDoc.getImportDocId())) {
        	if(StrUtil.equals(dbzjTzImportDoc.getReleaseId(), "1")) {
         	   return repEntity.layerMessage("no", "已经发布，不能修改！");
            }   
        	// 标题
        	dbzjTzImportDoc.setTitle(zjTzImportDoc.getTitle());
        	// 文号
        	dbzjTzImportDoc.setSymbolNo(zjTzImportDoc.getSymbolNo());
        	//发文层级id
        	dbzjTzImportDoc.setReleaseRankId(zjTzImportDoc.getReleaseRankId());
        	//发文层级name
        	if (StrUtil.isNotEmpty(zjTzImportDoc.getReleaseRankId())) {
        		String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzImportDoc.getReleaseRankId());
        		dbzjTzImportDoc.setReleaseRankName(openBankName);
        	}
        	// 发文部门
        	dbzjTzImportDoc.setDepartmentName(zjTzImportDoc.getDepartmentName());
        	// 发文日期
        	dbzjTzImportDoc.setReleaseDate(zjTzImportDoc.getReleaseDate());
        	// 由此废除的相关文件号
        	dbzjTzImportDoc.setAbolishSymbolNo(zjTzImportDoc.getAbolishSymbolNo());
        	// 是否有效id
        	dbzjTzImportDoc.setEffectiveId(zjTzImportDoc.getEffectiveId());
        	// 是否有效名称
        	if(StrUtil.equals("1", zjTzImportDoc.getEffectiveId())) {
        		dbzjTzImportDoc.setEffectiveName("是");
        	} else {
        		dbzjTzImportDoc.setEffectiveName("否");
        	}
        	// 登记用户
        	dbzjTzImportDoc.setRegisteredUser(zjTzImportDoc.getRegisteredUser());
        	// 备注
        	dbzjTzImportDoc.setRemarks(zjTzImportDoc.getRemarks());
           // 共通
           dbzjTzImportDoc.setModifyUserInfo(userKey, realName);
           flag = zjTzImportDocMapper.updateByPrimaryKey(dbzjTzImportDoc);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzImportDoc.getImportDocId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzImportDoc.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzImportDoc.getImportDocId());
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
            return repEntity.ok("sys.data.update",zjTzImportDoc);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzImportDocList != null && zjTzImportDocList.size() > 0) {
        	for (ZjTzImportDoc zjTzImportDoc : zjTzImportDocList) {
        		if(StrUtil.equals(zjTzImportDoc.getReleaseId(), "1")) {
              	   return repEntity.layerMessage("no", "已经发布，不能删除！");
                 }
			}
        	// 删除附件
        	for (ZjTzImportDoc zjTzImportDoc : zjTzImportDocList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzImportDoc.getImportDocId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
           ZjTzImportDoc zjTzImportDoc = new ZjTzImportDoc();
           zjTzImportDoc.setModifyUserInfo(userKey, realName);
           flag = zjTzImportDocMapper.batchDeleteUpdateZjTzImportDoc(zjTzImportDocList, zjTzImportDoc);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzImportDocList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzImportDoc(ZjTzImportDoc zjTzImportDoc) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzImportDoc dbzjTzImportDoc = zjTzImportDocMapper.selectByPrimaryKey(zjTzImportDoc.getImportDocId());
		if (dbzjTzImportDoc != null && StrUtil.isNotEmpty(dbzjTzImportDoc.getImportDocId())) {
			if(StrUtil.equals(dbzjTzImportDoc.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的不能广而告之，请先发布");
    		}
			dbzjTzImportDoc.setHomeShow("1");
			dbzjTzImportDoc.setHomeShowTime(new Date());
			dbzjTzImportDoc.setHomeShowStartDate(zjTzImportDoc.getHomeShowStartDate());
			dbzjTzImportDoc.setHomeShowEndDate(zjTzImportDoc.getHomeShowEndDate());
			dbzjTzImportDoc.setModifyUserInfo(userKey, realName);
			flag = zjTzImportDocMapper.updateByPrimaryKey(dbzjTzImportDoc);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzImportDoc);
		}
	}

	@Override
	public ResponseEntity batchDeleteReleaseZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzImportDocList != null && zjTzImportDocList.size() > 0) {
        	for (ZjTzImportDoc zjTzImportDoc : zjTzImportDocList) {
				if(StrUtil.equals(zjTzImportDoc.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的，请重新选择！");
				}
			}
        	ZjTzImportDoc zjTzImportDoc = new ZjTzImportDoc();
           zjTzImportDoc.setReleaseName("发布");
           zjTzImportDoc.setModifyUserInfo(userKey, realName);
           flag = zjTzImportDocMapper.batchDeleteReleaseZjTzImportDoc(zjTzImportDocList, zjTzImportDoc);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzImportDocList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzImportDocList != null && zjTzImportDocList.size() > 0) {
        	for (ZjTzImportDoc zjTzImportDoc : zjTzImportDocList) {
        		if(StrUtil.equals(zjTzImportDoc.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的，请重新选择！");
        		}
        	}
        	ZjTzImportDoc zjTzImportDoc = new ZjTzImportDoc();
        	zjTzImportDoc.setReleaseName("未发布");
        	zjTzImportDoc.setHomeShow("0");
        	zjTzImportDoc.setModifyUserInfo(userKey, realName);
        	flag = zjTzImportDocMapper.batchDeleteRecallZjTzImportDoc(zjTzImportDocList, zjTzImportDoc);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzImportDocList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzImportDocFile(List<ZjTzImportDoc> zjTzImportDocList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName = "规章制度" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzImportDoc total : zjTzImportDocList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getImportDocId());
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
