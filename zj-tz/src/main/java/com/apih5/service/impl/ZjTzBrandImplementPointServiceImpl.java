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
import com.apih5.mybatis.dao.ZjTzBrandImplementPointMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzBrandImplementPoint;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.service.ZjTzBrandImplementPointService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzBrandImplementPointService")
public class ZjTzBrandImplementPointServiceImpl implements ZjTzBrandImplementPointService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzBrandImplementPointMapper zjTzBrandImplementPointMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzBrandImplementPointListByCondition(ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
    	if (zjTzBrandImplementPoint == null) {
            zjTzBrandImplementPoint = new ZjTzBrandImplementPoint();
        }
    	 // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	if(StrUtil.equals("2", ext1)) {
        		zjTzBrandImplementPoint.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        		zjTzBrandImplementPoint.setCompanyId("");
        	}else {
        		zjTzBrandImplementPoint.setCompanyId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}
        	zjTzBrandImplementPoint.setUnExt1SeeFlag("???????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        	
        	
        } else {
        	zjTzBrandImplementPoint.setExt1SeeFlag("??????????????????????????????????????????????????????");
            // ???????????????
        	if(StrUtil.equals("all", zjTzBrandImplementPoint.getCompanyId(), true)) {
        		zjTzBrandImplementPoint.setComId("");
        		zjTzBrandImplementPoint.setCompanyId("");
            }else {
            	zjTzBrandImplementPoint.setCompanyId("");
            	zjTzBrandImplementPoint.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
            }
            	
        }
    	
        //????????????????????????
        if(zjTzBrandImplementPoint.getYearDate() != null) {
        	zjTzBrandImplementPoint.setYearName(DateUtil.format(zjTzBrandImplementPoint.getYearDate(), "yyyy"));	
        	zjTzBrandImplementPoint.setYearDate(null);
        }
        // ????????????
        PageHelper.startPage(zjTzBrandImplementPoint.getPage(),zjTzBrandImplementPoint.getLimit());
        // ????????????
        List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList = zjTzBrandImplementPointMapper.selectByZjTzBrandImplementPointList(zjTzBrandImplementPoint);
        for (ZjTzBrandImplementPoint zjTzBrandImplementPoint2 : zjTzBrandImplementPointList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzBrandImplementPoint2.getImplementPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzBrandImplementPoint2.setZjTzFileList(zjTzFileList);
        	
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", zjTzBrandImplementPoint2.getCompanyId());
        	jsonObject.accumulate("label", zjTzBrandImplementPoint2.getCompanyName());
        	projectList.add(jsonObject);
        	zjTzBrandImplementPoint2.setProjectList(projectList);
		}
        // ??????????????????
        PageInfo<ZjTzBrandImplementPoint> p = new PageInfo<>(zjTzBrandImplementPointList);

        return repEntity.okList(zjTzBrandImplementPointList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzBrandImplementPointDetails(ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        if (zjTzBrandImplementPoint == null) {
            zjTzBrandImplementPoint = new ZjTzBrandImplementPoint();
        }
        // ????????????
        ZjTzBrandImplementPoint dbZjTzBrandImplementPoint = zjTzBrandImplementPointMapper.selectByPrimaryKey(zjTzBrandImplementPoint.getImplementPointId());
        // ????????????
        if (dbZjTzBrandImplementPoint != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzBrandImplementPoint.getImplementPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzBrandImplementPoint.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzBrandImplementPoint);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        String companyId = TokenUtils.getCompanyId(request);
        
        if(zjTzBrandImplementPoint.getYearDate() == null) {
        	return repEntity.layerMessage("no", "??????????????????");	
        }
        zjTzBrandImplementPoint.setComId(companyId);
      
        List<JSONObject> approve1List = zjTzBrandImplementPoint.getProjectList();
		if(approve1List != null && approve1List.size()>0) {
			Object object = approve1List.get(0);
			JSONObject jsonObject = new JSONObject(object);
			zjTzBrandImplementPoint.setCompanyId(jsonObject.getStr("value"));
			zjTzBrandImplementPoint.setCompanyName(jsonObject.getStr("label"));
		}
        //????????????=????????????             ???????????????????????????????????????
        ZjTzBrandImplementPoint brandImplementPoint = new ZjTzBrandImplementPoint();
        brandImplementPoint.setYearName(DateUtil.format(zjTzBrandImplementPoint.getYearDate(), "yyyy"));
        brandImplementPoint.setCompanyId(zjTzBrandImplementPoint.getCompanyId());
        List<ZjTzBrandImplementPoint> brandImplementPoints = zjTzBrandImplementPointMapper.selectByZjTzBrandImplementPointList(brandImplementPoint);
        if(brandImplementPoints != null && brandImplementPoints.size() >0) {
        	return repEntity.layerMessage("no", "????????????????????????????????????");
        }
		
        zjTzBrandImplementPoint.setImplementPointId(UuidUtil.generate());
        zjTzBrandImplementPoint.setYearName(DateUtil.format(zjTzBrandImplementPoint.getYearDate(), "yyyy"));
        if(StrUtil.equals("1", ext1)) {
        	zjTzBrandImplementPoint.setReleaseId("4");
        	zjTzBrandImplementPoint.setReleaseName("?????????????????????");
        }else {
        	zjTzBrandImplementPoint.setReleaseId("0");
        	zjTzBrandImplementPoint.setReleaseName("?????????");
        }
        zjTzBrandImplementPoint.setHomeShow("0");
        zjTzBrandImplementPoint.setCreateUserInfo(userKey, realName);
        int flag = zjTzBrandImplementPointMapper.insert(zjTzBrandImplementPoint);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzBrandImplementPoint.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzBrandImplementPoint.getImplementPointId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzBrandImplementPoint);
        }
    }

    @Override
    public ResponseEntity updateZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzBrandImplementPoint dbzjTzBrandImplementPoint = zjTzBrandImplementPointMapper.selectByPrimaryKey(zjTzBrandImplementPoint.getImplementPointId());
        if (dbzjTzBrandImplementPoint != null && StrUtil.isNotEmpty(dbzjTzBrandImplementPoint.getImplementPointId())) {
        	if(StrUtil.equals(dbzjTzBrandImplementPoint.getReleaseId(), "1")) {
        		return repEntity.layerMessage("no", "??????????????????????????????");
        	}
           // ????????????
           dbzjTzBrandImplementPoint.setRegisterDate(zjTzBrandImplementPoint.getRegisterDate());
           // ????????????
           dbzjTzBrandImplementPoint.setRegisterPerson(zjTzBrandImplementPoint.getRegisterPerson());
           // ????????????
           dbzjTzBrandImplementPoint.setContent(zjTzBrandImplementPoint.getContent());
           // ??????
           dbzjTzBrandImplementPoint.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandImplementPointMapper.updateByPrimaryKey(dbzjTzBrandImplementPoint);
           
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzBrandImplementPoint.getImplementPointId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzBrandImplementPoint.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzBrandImplementPoint.getImplementPointId());
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
            return repEntity.ok("sys.data.update",zjTzBrandImplementPoint);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandImplementPointList != null && zjTzBrandImplementPointList.size() > 0) {
        	for (ZjTzBrandImplementPoint zjTzBrandImplementPoint : zjTzBrandImplementPointList) {
        		if(StrUtil.equals(zjTzBrandImplementPoint.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????");
        		}
        	}
        	// ????????????
        	for (ZjTzBrandImplementPoint zjTzBrandImplementPoint : zjTzBrandImplementPointList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzBrandImplementPoint.getImplementPointId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzBrandImplementPoint zjTzBrandImplementPoint = new ZjTzBrandImplementPoint();
           zjTzBrandImplementPoint.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandImplementPointMapper.batchDeleteUpdateZjTzBrandImplementPoint(zjTzBrandImplementPointList, zjTzBrandImplementPoint);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzBrandImplementPointList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzBrandImplementPoint(ZjTzBrandImplementPoint zjTzBrandImplementPoint) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzBrandImplementPoint dbzjTzBrandImplementPoint = zjTzBrandImplementPointMapper.selectByPrimaryKey(zjTzBrandImplementPoint.getImplementPointId());
		if (dbzjTzBrandImplementPoint != null && StrUtil.isNotEmpty(dbzjTzBrandImplementPoint.getImplementPointId())) {
			dbzjTzBrandImplementPoint.setHomeShow("1");
			dbzjTzBrandImplementPoint.setHomeShowTime(new Date());
			dbzjTzBrandImplementPoint.setHomeShowStartDate(zjTzBrandImplementPoint.getHomeShowStartDate());
			dbzjTzBrandImplementPoint.setHomeShowEndDate(zjTzBrandImplementPoint.getHomeShowEndDate());
			dbzjTzBrandImplementPoint.setModifyUserInfo(userKey, realName);
			flag = zjTzBrandImplementPointMapper.updateByPrimaryKey(dbzjTzBrandImplementPoint);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzBrandImplementPoint);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandImplementPointList != null && zjTzBrandImplementPointList.size() > 0) {
        	for (ZjTzBrandImplementPoint zjTzRules : zjTzBrandImplementPointList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1") || StrUtil.equals(zjTzRules.getReleaseId(), "7")) {
					 return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????????????????");
				}
			}
        	ZjTzBrandImplementPoint zjTzRules = new ZjTzBrandImplementPoint();
        	zjTzRules.setReleaseId("1");
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzBrandImplementPointMapper.batchRecallZjTzBrandImplementPoint(zjTzBrandImplementPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandImplementPointList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzBrandImplementPoint(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandImplementPointList != null && zjTzBrandImplementPointList.size() > 0) {
        	for (ZjTzBrandImplementPoint zjTzRules : zjTzBrandImplementPointList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "4")) {
        			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????????????????");
        		}
        	}
        	ZjTzBrandImplementPoint zjTzRules = new ZjTzBrandImplementPoint();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzBrandImplementPointMapper.batchRecallZjTzBrandImplementPoint(zjTzBrandImplementPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandImplementPointList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzBrandImplementPointFile(List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName ="??????/????????????????????????"+DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzBrandImplementPoint total : zjTzBrandImplementPointList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getImplementPointId());
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
