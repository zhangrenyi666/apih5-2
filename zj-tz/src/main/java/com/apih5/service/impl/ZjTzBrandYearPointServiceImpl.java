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
import com.apih5.mybatis.dao.ZjTzBrandYearPointMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzSpecialYearPointMapper;
import com.apih5.mybatis.pojo.ZjTzBrandImplementPoint;
import com.apih5.mybatis.pojo.ZjTzBrandYearPoint;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzSpecialYearPoint;
import com.apih5.service.ZjTzBrandYearPointService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;

@Service("zjTzBrandYearPointService")
public class ZjTzBrandYearPointServiceImpl implements ZjTzBrandYearPointService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzBrandYearPointMapper zjTzBrandYearPointMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzBrandImplementPointMapper zjTzBrandImplementPointMapper;
    
    @Autowired(required = true)
    private ZjTzSpecialYearPointMapper zjTzSpecialYearPointMapper;

    @Override
    public ResponseEntity getZjTzBrandYearPointListByCondition(ZjTzBrandYearPoint zjTzBrandYearPoint) {
        if (zjTzBrandYearPoint == null) {
            zjTzBrandYearPoint = new ZjTzBrandYearPoint();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // ????????????????????????
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzBrandYearPoint.setReleaseId("1");
        }
        // ????????????
        PageHelper.startPage(zjTzBrandYearPoint.getPage(),zjTzBrandYearPoint.getLimit());
        // ????????????
        List<ZjTzBrandYearPoint> zjTzBrandYearPointList = zjTzBrandYearPointMapper.selectByZjTzBrandYearPointList(zjTzBrandYearPoint);
        for (ZjTzBrandYearPoint zjTzBrandYearPoint2 : zjTzBrandYearPointList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzBrandYearPoint2.getYearPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzBrandYearPoint2.setZjTzFileList(zjTzFileList);
        	
        	
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", zjTzBrandYearPoint2.getCompanyId());
        	jsonObject.accumulate("label", zjTzBrandYearPoint2.getCompanyName());
        	projectList.add(jsonObject);
        	zjTzBrandYearPoint2.setProjectList(projectList);
        	
		}
        // ??????????????????
        PageInfo<ZjTzBrandYearPoint> p = new PageInfo<>(zjTzBrandYearPointList);

        return repEntity.okList(zjTzBrandYearPointList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzBrandYearPointDetails(ZjTzBrandYearPoint zjTzBrandYearPoint) {
        if (zjTzBrandYearPoint == null) {
            zjTzBrandYearPoint = new ZjTzBrandYearPoint();
        }
        // ????????????
        ZjTzBrandYearPoint dbZjTzBrandYearPoint = zjTzBrandYearPointMapper.selectByPrimaryKey(zjTzBrandYearPoint.getYearPointId());
        // ????????????
        if (dbZjTzBrandYearPoint != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzBrandYearPoint.getYearPointId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzBrandYearPoint.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzBrandYearPoint);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zjTzBrandYearPoint.getYearDate() == null) {
       	 return repEntity.layerMessage("no", "??????????????????");	
       }
        //????????????=????????????
        ZjTzBrandYearPoint brandYearPoint = new ZjTzBrandYearPoint();
        brandYearPoint.setYearName(DateUtil.format(zjTzBrandYearPoint.getYearDate(), "yyyy"));
    	List<ZjTzBrandYearPoint> brandYearPoints = zjTzBrandYearPointMapper.selectByZjTzBrandYearPointList(brandYearPoint);
    	if(brandYearPoints != null && brandYearPoints.size() >0) {
    		 return repEntity.layerMessage("no", "????????????????????????????????????");
    	}
    	
    	List<JSONObject> approve1List = zjTzBrandYearPoint.getProjectList();
		if(approve1List != null && approve1List.size()>0) {
			Object object = approve1List.get(0);
			JSONObject jsonObject = new JSONObject(object);
			zjTzBrandYearPoint.setCompanyId(jsonObject.getStr("value"));
			zjTzBrandYearPoint.setCompanyName(jsonObject.getStr("label"));
		}
    	
        zjTzBrandYearPoint.setYearPointId(UuidUtil.generate());
        zjTzBrandYearPoint.setYearName(DateUtil.format(zjTzBrandYearPoint.getYearDate(), "yyyy"));
        zjTzBrandYearPoint.setReleaseId("0");
        zjTzBrandYearPoint.setReleaseName("?????????");
        zjTzBrandYearPoint.setHomeShow("0");
        zjTzBrandYearPoint.setCreateUserInfo(userKey, realName);
        int flag = zjTzBrandYearPointMapper.insert(zjTzBrandYearPoint);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzBrandYearPoint.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzBrandYearPoint.getYearPointId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzBrandYearPoint);
        }
    }

    @Override
    public ResponseEntity updateZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzBrandYearPoint dbzjTzBrandYearPoint = zjTzBrandYearPointMapper.selectByPrimaryKey(zjTzBrandYearPoint.getYearPointId());
        if (dbzjTzBrandYearPoint != null && StrUtil.isNotEmpty(dbzjTzBrandYearPoint.getYearPointId())) {
//           // ???????????????????????????
//           dbzjTzBrandYearPoint.setYearName(zjTzBrandYearPoint.getYearName());
//           // ????????????????????????
//           dbzjTzBrandYearPoint.setYearDate(zjTzBrandYearPoint.getYearDate());
           // ????????????
           dbzjTzBrandYearPoint.setRegisterDate(zjTzBrandYearPoint.getRegisterDate());
           // ????????????
           dbzjTzBrandYearPoint.setRegisterPerson(zjTzBrandYearPoint.getRegisterPerson());
           // ????????????
           dbzjTzBrandYearPoint.setContent(zjTzBrandYearPoint.getContent());
           // ??????
           dbzjTzBrandYearPoint.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandYearPointMapper.updateByPrimaryKey(dbzjTzBrandYearPoint);
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzBrandYearPoint.getYearPointId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzBrandYearPoint.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzBrandYearPoint.getYearPointId());
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
            return repEntity.ok("sys.data.update",zjTzBrandYearPoint);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandYearPointList != null && zjTzBrandYearPointList.size() > 0) {
        	for (ZjTzBrandYearPoint zjTzBrandYearPoint : zjTzBrandYearPointList) {
        		if(StrUtil.equals(zjTzBrandYearPoint.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "??????????????????????????????");
        		}
        	}
        	// ????????????
        	for (ZjTzBrandYearPoint zjTzBrandYearPoint : zjTzBrandYearPointList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzBrandYearPoint.getYearPointId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzBrandYearPoint zjTzBrandYearPoint = new ZjTzBrandYearPoint();
        	zjTzBrandYearPoint.setModifyUserInfo(userKey, realName);
        	flag = zjTzBrandYearPointMapper.batchDeleteUpdateZjTzBrandYearPoint(zjTzBrandYearPointList, zjTzBrandYearPoint);
        }
        // ??????
        if (flag == 0) {
        	return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzBrandYearPointList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzBrandYearPoint(ZjTzBrandYearPoint zjTzBrandYearPoint) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzBrandYearPoint dbzjTzBrandYearPoint = zjTzBrandYearPointMapper.selectByPrimaryKey(zjTzBrandYearPoint.getYearPointId());
		if (dbzjTzBrandYearPoint != null && StrUtil.isNotEmpty(dbzjTzBrandYearPoint.getYearPointId())) {
			if(StrUtil.equals(dbzjTzBrandYearPoint.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
			dbzjTzBrandYearPoint.setHomeShow("1");
			dbzjTzBrandYearPoint.setHomeShowTime(new Date());
			dbzjTzBrandYearPoint.setHomeShowStartDate(zjTzBrandYearPoint.getHomeShowStartDate());
			dbzjTzBrandYearPoint.setHomeShowEndDate(zjTzBrandYearPoint.getHomeShowEndDate());
			dbzjTzBrandYearPoint.setModifyUserInfo(userKey, realName);
			flag = zjTzBrandYearPointMapper.updateByPrimaryKey(dbzjTzBrandYearPoint);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzBrandYearPoint);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandYearPointList != null && zjTzBrandYearPointList.size() > 0) {
        	for (ZjTzBrandYearPoint zjTzRules : zjTzBrandYearPointList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "??????????????????????????????????????????");
				}
			}
        	ZjTzBrandYearPoint zjTzRules = new ZjTzBrandYearPoint();
           zjTzRules.setReleaseName("??????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandYearPointMapper.batchReleaseZjTzBrandYearPoint(zjTzBrandYearPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandYearPointList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzBrandYearPoint(List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandYearPointList != null && zjTzBrandYearPointList.size() > 0) {
        	for (ZjTzBrandYearPoint zjTzRules : zjTzBrandYearPointList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	ZjTzBrandYearPoint zjTzRules = new ZjTzBrandYearPoint();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzBrandYearPointMapper.batchRecallZjTzBrandYearPoint(zjTzBrandYearPointList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandYearPointList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzBrandYearPointFile(List<ZjTzBrandYearPoint> zjTzBrandYearPointList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName ="????????????-??????????????????" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzBrandYearPoint total : zjTzBrandYearPointList) {
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
	/**
	 * 
	 * ????????????
	 */
	@Override
	public ResponseEntity getZjTzBrandYearPointHome(ZjTzBrandYearPoint zjTzBrandYearPoint) {
		if (zjTzBrandYearPoint == null) {
			zjTzBrandYearPoint = new ZjTzBrandYearPoint();
		}
		List<Object> returnList = Lists.newArrayList();
		List<Object> dbreturnList = Lists.newArrayList();
		List<ZjTzBrandYearPoint> zjTzBrandYearPointList = zjTzBrandYearPointMapper.selectZjTzHomeBrandYearPoint(zjTzBrandYearPoint);
		if (zjTzBrandYearPointList != null && zjTzBrandYearPointList.size() > 0) {
			for (ZjTzBrandYearPoint dbzjTzBrandYearPoint : zjTzBrandYearPointList) {
				dbzjTzBrandYearPoint.setTypeName("????????????");
			}
			returnList.addAll(zjTzBrandYearPointList);
		}
		List<ZjTzSpecialYearPoint> zjTzSpecialYearPointList = zjTzSpecialYearPointMapper.selectZjTzHomeSpecialYearPoint(zjTzBrandYearPoint.getZjTzSpecialYearPoint());
		if (zjTzSpecialYearPointList != null && zjTzSpecialYearPointList.size() > 0) {
			for (ZjTzSpecialYearPoint dbzjTzSpecialYearPoint : zjTzSpecialYearPointList) {
				dbzjTzSpecialYearPoint.setTypeName("????????????");
			}
			returnList.addAll(zjTzSpecialYearPointList);
		}
		List<ZjTzBrandImplementPoint> zjTzBrandImplementPointList = zjTzBrandImplementPointMapper.selectZjTzHomeBrandImplementPoint(zjTzBrandYearPoint.getZjTzBrandImplementPoint());
		if (zjTzBrandImplementPointList != null && zjTzBrandImplementPointList.size() > 0) {
			for (ZjTzBrandImplementPoint dbzjTzBrandImplementPoint : zjTzBrandImplementPointList) {
				dbzjTzBrandImplementPoint.setTypeName("??????/????????????????????????");
			}
			returnList.addAll(zjTzBrandImplementPointList);
		}
		if (returnList != null && returnList.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				dbreturnList.add(returnList.get(i));
			}
		}else {
			dbreturnList.addAll(returnList);
		}
		return repEntity.ok(dbreturnList);
	}
}
