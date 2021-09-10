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
import com.apih5.mybatis.dao.ZjTzSpecialResultShowMapper;
import com.apih5.mybatis.pojo.ZjTzSpecialResultShow;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSpecialResultShowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;

@Service("zjTzSpecialResultShowService")
public class ZjTzSpecialResultShowServiceImpl implements ZjTzSpecialResultShowService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSpecialResultShowMapper zjTzSpecialResultShowMapper;

    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzSpecialResultShowListByCondition(ZjTzSpecialResultShow zjTzSpecialResultShow) {
        if (zjTzSpecialResultShow == null) {
            zjTzSpecialResultShow = new ZjTzSpecialResultShow();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	
        	if(StrUtil.equals("2", ext1)) {
        		zjTzSpecialResultShow.setCompanyId("");
        		zjTzSpecialResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}else {
        		zjTzSpecialResultShow.setCompanyId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}
        } else {
            // 集团人员时
        	if(StrUtil.equals("all", zjTzSpecialResultShow.getCompanyId(), true)) {
        		zjTzSpecialResultShow.setComId("");
        		zjTzSpecialResultShow.setCompanyId("");
            }else {
            	zjTzSpecialResultShow.setCompanyId("");
            	zjTzSpecialResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
            }
        }
       
        // 分页查询
        PageHelper.startPage(zjTzSpecialResultShow.getPage(),zjTzSpecialResultShow.getLimit());
        // 获取数据
        List<ZjTzSpecialResultShow> zjTzSpecialResultShowList = zjTzSpecialResultShowMapper.selectByZjTzSpecialResultShowList(zjTzSpecialResultShow);
        for (ZjTzSpecialResultShow zjTzSpecialResultShow2 : zjTzSpecialResultShowList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzSpecialResultShow2.getResultShowId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSpecialResultShow2.setZjTzFileList(zjTzFileList);
        	
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", zjTzSpecialResultShow2.getCompanyId());
        	jsonObject.accumulate("label", zjTzSpecialResultShow2.getCompanyName());
        	projectList.add(jsonObject);
        	zjTzSpecialResultShow2.setProjectList(projectList);
		}
        // 得到分页信息
        PageInfo<ZjTzSpecialResultShow> p = new PageInfo<>(zjTzSpecialResultShowList);

        return repEntity.okList(zjTzSpecialResultShowList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSpecialResultShowDetails(ZjTzSpecialResultShow zjTzSpecialResultShow) {
        if (zjTzSpecialResultShow == null) {
            zjTzSpecialResultShow = new ZjTzSpecialResultShow();
        }
        // 获取数据
        ZjTzSpecialResultShow dbZjTzSpecialResultShow = zjTzSpecialResultShowMapper.selectByPrimaryKey(zjTzSpecialResultShow.getResultShowId());
        // 数据存在
        if (dbZjTzSpecialResultShow != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzSpecialResultShow.getResultShowId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzSpecialResultShow.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzSpecialResultShow);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String companyId = TokenUtils.getCompanyId(request);
        
        zjTzSpecialResultShow.setResultShowId(UuidUtil.generate());
        zjTzSpecialResultShow.setComId(companyId);
        //成果类型
        if (StrUtil.isNotEmpty(zjTzSpecialResultShow.getResultTypeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("chengGuoLeiXing", zjTzSpecialResultShow.getResultTypeId());
        	zjTzSpecialResultShow.setResultTypeName(openBankName);
        }
        
        List<JSONObject> approve1List = zjTzSpecialResultShow.getProjectList();
		if(approve1List != null && approve1List.size()>0) {
			Object object = approve1List.get(0);
			JSONObject jsonObject = new JSONObject(object);
			zjTzSpecialResultShow.setCompanyId(jsonObject.getStr("value"));
			zjTzSpecialResultShow.setCompanyName(jsonObject.getStr("label"));
		}
        zjTzSpecialResultShow.setReleaseId("0");
        zjTzSpecialResultShow.setReleaseName("未发布");
        zjTzSpecialResultShow.setHomeShow("0");
        zjTzSpecialResultShow.setCreateUserInfo(userKey, realName);
        int flag = zjTzSpecialResultShowMapper.insert(zjTzSpecialResultShow);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzSpecialResultShow.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzSpecialResultShow.getResultShowId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSpecialResultShow);
        }
    }

    @Override
    public ResponseEntity updateZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSpecialResultShow dbzjTzSpecialResultShow = zjTzSpecialResultShowMapper.selectByPrimaryKey(zjTzSpecialResultShow.getResultShowId());
        if (dbzjTzSpecialResultShow != null && StrUtil.isNotEmpty(dbzjTzSpecialResultShow.getResultShowId())) {
           // 标题
           dbzjTzSpecialResultShow.setTitle(zjTzSpecialResultShow.getTitle());
//           // 成果权属=公司id
//           dbzjTzSpecialResultShow.setCompanyId(zjTzSpecialResultShow.getCompanyId());
//           // 成果权属=公司name
//           dbzjTzSpecialResultShow.setCompanyName(zjTzSpecialResultShow.getCompanyName());
           // 成果类型id
           dbzjTzSpecialResultShow.setResultTypeId(zjTzSpecialResultShow.getResultTypeId());
           // 成果类型name
           if (StrUtil.isNotEmpty(zjTzSpecialResultShow.getResultTypeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("chengGuoLeiXing", zjTzSpecialResultShow.getResultTypeId());
           	dbzjTzSpecialResultShow.setResultTypeName(openBankName);
           }
           // 内容简介
           dbzjTzSpecialResultShow.setContent(zjTzSpecialResultShow.getContent());
           // 共通
           dbzjTzSpecialResultShow.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialResultShowMapper.updateByPrimaryKey(dbzjTzSpecialResultShow);
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSpecialResultShow.getResultShowId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzSpecialResultShow.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzSpecialResultShow.getResultShowId());
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
            return repEntity.ok("sys.data.update",zjTzSpecialResultShow);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialResultShowList != null && zjTzSpecialResultShowList.size() > 0) {
        	for (ZjTzSpecialResultShow zjTzSpecialResultShow : zjTzSpecialResultShowList) {
        		if(StrUtil.equals(zjTzSpecialResultShow.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "已经发布，不能删除！");
        		}
        	}
        	// 删除附件
        	for (ZjTzSpecialResultShow zjTzSpecialResultShow : zjTzSpecialResultShowList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzSpecialResultShow.getResultShowId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzSpecialResultShow zjTzSpecialResultShow = new ZjTzSpecialResultShow();
           zjTzSpecialResultShow.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialResultShowMapper.batchDeleteUpdateZjTzSpecialResultShow(zjTzSpecialResultShowList, zjTzSpecialResultShow);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSpecialResultShowList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzSpecialResultShow dbzjTzSpecialResultShow = zjTzSpecialResultShowMapper.selectByPrimaryKey(zjTzSpecialResultShow.getResultShowId());
		if (dbzjTzSpecialResultShow != null && StrUtil.isNotEmpty(dbzjTzSpecialResultShow.getResultShowId())) {
			if(StrUtil.equals(dbzjTzSpecialResultShow.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的不能广而告之，请先发布");
    		}
			dbzjTzSpecialResultShow.setHomeShow("1");
			dbzjTzSpecialResultShow.setHomeShowTime(new Date());
			dbzjTzSpecialResultShow.setHomeShowStartDate(zjTzSpecialResultShow.getHomeShowStartDate());
			dbzjTzSpecialResultShow.setHomeShowEndDate(zjTzSpecialResultShow.getHomeShowEndDate());
			dbzjTzSpecialResultShow.setModifyUserInfo(userKey, realName);
			flag = zjTzSpecialResultShowMapper.updateByPrimaryKey(dbzjTzSpecialResultShow);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzSpecialResultShow);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialResultShowList != null && zjTzSpecialResultShowList.size() > 0) {
        	for (ZjTzSpecialResultShow zjTzRules : zjTzSpecialResultShowList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的，请重新选择！");
				}
			}
        	ZjTzSpecialResultShow zjTzRules = new ZjTzSpecialResultShow();
           zjTzRules.setReleaseName("发布");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialResultShowMapper.batchReleaseZjTzSpecialResultShow(zjTzSpecialResultShowList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSpecialResultShowList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialResultShowList != null && zjTzSpecialResultShowList.size() > 0) {
        	for (ZjTzSpecialResultShow zjTzRules : zjTzSpecialResultShowList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的，请重新选择！");
        		}
        	}
        	ZjTzSpecialResultShow zjTzRules = new ZjTzSpecialResultShow();
        	zjTzRules.setReleaseName("未发布");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzSpecialResultShowMapper.batchRecallZjTzSpecialResultShow(zjTzSpecialResultShowList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSpecialResultShowList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzSpecialResultShowFile(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzSpecialResultShow total : zjTzSpecialResultShowList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getResultShowId());
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
