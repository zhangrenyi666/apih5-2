package com.apih5.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzSpecialYearPointMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.mybatis.pojo.ZjTzSpecialYearPoint;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzSpecialYearPointService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;

@Service("zjTzSpecialYearPointService")
public class ZjTzSpecialYearPointServiceImpl implements ZjTzSpecialYearPointService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSpecialYearPointMapper zjTzSpecialYearPointMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzSpecialYearPointListByCondition(ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        if (zjTzSpecialYearPoint == null) {
            zjTzSpecialYearPoint = new ZjTzSpecialYearPoint();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	if(StrUtil.equals("2", ext1)) {
        		zjTzSpecialYearPoint.setCompanyId("");
        		zjTzSpecialYearPoint.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}else {
        		zjTzSpecialYearPoint.setCompanyId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}
        } else {
            // ???????????????
        	if(StrUtil.equals("all", zjTzSpecialYearPoint.getCompanyId(), true)) {
        		zjTzSpecialYearPoint.setComId("");
        		zjTzSpecialYearPoint.setCompanyId("");
            }else {
            	zjTzSpecialYearPoint.setCompanyId("");
            	zjTzSpecialYearPoint.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
            }
        }
        // ????????????
        PageHelper.startPage(zjTzSpecialYearPoint.getPage(),zjTzSpecialYearPoint.getLimit());
        // ????????????
        List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList = zjTzSpecialYearPointMapper.selectByZjTzSpecialYearPointList(zjTzSpecialYearPoint);
        for (ZjTzSpecialYearPoint zjTzSpecialYearPoint2 : zjTzSpecialYearPointList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzSpecialYearPoint2.getYearPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzSpecialYearPoint2.setZjTzFileList(zjTzFileList);
        	
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", zjTzSpecialYearPoint2.getCompanyId());
        	jsonObject.accumulate("label", zjTzSpecialYearPoint2.getCompanyName());
        	projectList.add(jsonObject);
        	zjTzSpecialYearPoint2.setProjectList(projectList);
		}
        // ??????????????????
        PageInfo<ZjTzSpecialYearPoint> p = new PageInfo<>(zjTzSpecialYearPointList);

        return repEntity.okList(zjTzSpecialYearPointList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSpecialYearPointDetails(ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        if (zjTzSpecialYearPoint == null) {
            zjTzSpecialYearPoint = new ZjTzSpecialYearPoint();
        }
        // ????????????
        ZjTzSpecialYearPoint dbZjTzSpecialYearPoint = zjTzSpecialYearPointMapper.selectByPrimaryKey(zjTzSpecialYearPoint.getYearPointId());
        // ????????????
        if (dbZjTzSpecialYearPoint != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzSpecialYearPoint.getYearPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzSpecialYearPoint.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzSpecialYearPoint);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String companyId = TokenUtils.getCompanyId(request);
        if(zjTzSpecialYearPoint.getYearDate() == null) {
          	 return repEntity.layerMessage("no", "??????????????????");	
          }
           //????????????=????????????
           ZjTzSpecialYearPoint SpecialYearPoint = new ZjTzSpecialYearPoint();
           SpecialYearPoint.setYearName(DateUtil.format(zjTzSpecialYearPoint.getYearDate(), "yyyy"));
       	List<ZjTzSpecialYearPoint> SpecialYearPoints = zjTzSpecialYearPointMapper.selectByZjTzSpecialYearPointList(SpecialYearPoint);
       	if(SpecialYearPoints != null && SpecialYearPoints.size() >0) {
       		 return repEntity.layerMessage("no", "????????????????????????????????????");
       	}
       	zjTzSpecialYearPoint.setComId(companyId);
        List<JSONObject> approve1List = zjTzSpecialYearPoint.getProjectList();
		if(approve1List != null && approve1List.size()>0) {
			Object object = approve1List.get(0);
			JSONObject jsonObject = new JSONObject(object);
			zjTzSpecialYearPoint.setCompanyId(jsonObject.getStr("value"));
			zjTzSpecialYearPoint.setCompanyName(jsonObject.getStr("label"));
		}
        zjTzSpecialYearPoint.setYearPointId(UuidUtil.generate());
        zjTzSpecialYearPoint.setReleaseId("0");
        zjTzSpecialYearPoint.setReleaseName("?????????");
        zjTzSpecialYearPoint.setHomeShow("0");
        zjTzSpecialYearPoint.setCreateUserInfo(userKey, realName);
        int flag = zjTzSpecialYearPointMapper.insert(zjTzSpecialYearPoint);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzSpecialYearPoint.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzSpecialYearPoint.getYearPointId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSpecialYearPoint);
        }
    }

    @Override
    public ResponseEntity updateZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSpecialYearPoint dbzjTzSpecialYearPoint = zjTzSpecialYearPointMapper.selectByPrimaryKey(zjTzSpecialYearPoint.getYearPointId());
        if (dbzjTzSpecialYearPoint != null && StrUtil.isNotEmpty(dbzjTzSpecialYearPoint.getYearPointId())) {
//           // ????????????=??????id
//           dbzjTzSpecialYearPoint.setCompanyId(zjTzSpecialYearPoint.getCompanyId());
//           // ????????????=??????name
//           dbzjTzSpecialYearPoint.setCompanyName(zjTzSpecialYearPoint.getCompanyName());
//           // ???????????????????????????
//           dbzjTzSpecialYearPoint.setYearName(zjTzSpecialYearPoint.getYearName());
//           // ????????????????????????
//           dbzjTzSpecialYearPoint.setYearDate(zjTzSpecialYearPoint.getYearDate());
           // ????????????
           dbzjTzSpecialYearPoint.setRegisterDate(zjTzSpecialYearPoint.getRegisterDate());
           // ????????????
           dbzjTzSpecialYearPoint.setRegisterPerson(zjTzSpecialYearPoint.getRegisterPerson());
           // ????????????
           dbzjTzSpecialYearPoint.setContent(zjTzSpecialYearPoint.getContent());
           // ??????
           dbzjTzSpecialYearPoint.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialYearPointMapper.updateByPrimaryKey(dbzjTzSpecialYearPoint);
           
        // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzSpecialYearPoint.getYearPointId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzSpecialYearPoint.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzSpecialYearPoint.getYearPointId());
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
            return repEntity.ok("sys.data.update",zjTzSpecialYearPoint);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialYearPointList != null && zjTzSpecialYearPointList.size() > 0) {
        	for (ZjTzSpecialYearPoint zjTzSpecialYearPoint : zjTzSpecialYearPointList) {
        		if(StrUtil.equals(zjTzSpecialYearPoint.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "??????????????????????????????");
        		}
        	}
        	// ????????????
        	for (ZjTzSpecialYearPoint zjTzSpecialYearPoint : zjTzSpecialYearPointList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzSpecialYearPoint.getYearPointId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzSpecialYearPoint zjTzSpecialYearPoint = new ZjTzSpecialYearPoint();
           zjTzSpecialYearPoint.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialYearPointMapper.batchDeleteUpdateZjTzSpecialYearPoint(zjTzSpecialYearPointList, zjTzSpecialYearPoint);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSpecialYearPointList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzSpecialYearPoint(ZjTzSpecialYearPoint zjTzSpecialYearPoint) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzSpecialYearPoint dbzjTzSpecialYearPoint = zjTzSpecialYearPointMapper.selectByPrimaryKey(zjTzSpecialYearPoint.getYearPointId());
		if (dbzjTzSpecialYearPoint != null && StrUtil.isNotEmpty(dbzjTzSpecialYearPoint.getYearPointId())) {
			if(StrUtil.equals(dbzjTzSpecialYearPoint.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
			dbzjTzSpecialYearPoint.setHomeShow("1");
			dbzjTzSpecialYearPoint.setHomeShowTime(new Date());
			dbzjTzSpecialYearPoint.setHomeShowStartDate(zjTzSpecialYearPoint.getHomeShowStartDate());
			dbzjTzSpecialYearPoint.setHomeShowEndDate(zjTzSpecialYearPoint.getHomeShowEndDate());
			dbzjTzSpecialYearPoint.setModifyUserInfo(userKey, realName);
			flag = zjTzSpecialYearPointMapper.updateByPrimaryKey(dbzjTzSpecialYearPoint);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzSpecialYearPoint);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialYearPointList != null && zjTzSpecialYearPointList.size() > 0) {
        	for (ZjTzSpecialYearPoint zjTzRules : zjTzSpecialYearPointList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "??????????????????????????????????????????");
				}
			}
        	ZjTzSpecialYearPoint zjTzRules = new ZjTzSpecialYearPoint();
           zjTzRules.setReleaseName("??????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzSpecialYearPointMapper.batchReleaseZjTzSpecialYearPoint(zjTzSpecialYearPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSpecialYearPointList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzSpecialYearPoint(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSpecialYearPointList != null && zjTzSpecialYearPointList.size() > 0) {
        	for (ZjTzSpecialYearPoint zjTzRules : zjTzSpecialYearPointList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	ZjTzSpecialYearPoint zjTzRules = new ZjTzSpecialYearPoint();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzSpecialYearPointMapper.batchRecallZjTzSpecialYearPoint(zjTzSpecialYearPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSpecialYearPointList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzSpecialYearPointFile(List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName = "??????????????????" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzSpecialYearPoint total : zjTzSpecialYearPointList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getYearPointId());
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
