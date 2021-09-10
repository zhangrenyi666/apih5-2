package com.apih5.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjTzAerialVideoMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzAerialVideo;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzAerialVideoService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import flex.messaging.io.ArrayList;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;

@Service("zjTzAerialVideoService")
public class ZjTzAerialVideoServiceImpl implements ZjTzAerialVideoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzAerialVideoMapper zjTzAerialVideoMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    
    
    
    MinioClient client = new MinioClient.Builder()
//            .endpoint("http://218.60.94.30:9000")
            .endpoint("http://10.11.59.56:9000")
            .credentials("admin", "admin123456")
//            .credentials("admin", "admin@123456!")
            .build();
        	 
    
    

    @Override
    public ResponseEntity getZjTzAerialVideoListByCondition(ZjTzAerialVideo zjTzAerialVideo) {
        if (zjTzAerialVideo == null) {
            zjTzAerialVideo = new ZjTzAerialVideo();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
        	if(StrUtil.equals("all", zjTzAerialVideo.getProjectId(), true)) {
        		zjTzAerialVideo.setProjectId("");
        		zjTzAerialVideo.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
        	}
        } else {
        	// 集团人员时
        	if(StrUtil.equals("all", zjTzAerialVideo.getProjectId(), true)) {
        		zjTzAerialVideo.setProjectId("");
        	}
        }
        
        // 分页查询
        PageHelper.startPage(zjTzAerialVideo.getPage(),zjTzAerialVideo.getLimit());
        // 获取数据
        List<ZjTzAerialVideo> zjTzAerialVideoList = zjTzAerialVideoMapper.selectByZjTzAerialVideoList(zjTzAerialVideo);
        for (ZjTzAerialVideo zjTzAerialVideo2 : zjTzAerialVideoList) {
        	
        	JSONObject imageObject = new JSONObject();
        	imageObject.set("fileName", zjTzAerialVideo2.getImageName());
        	imageObject.set("url", zjTzAerialVideo2.getImageUrl());
        	imageObject.set("thumburl", zjTzAerialVideo2.getThumbUrl());
        	imageObject.set("type", zjTzAerialVideo2.getType());
        	imageObject.set("size", zjTzAerialVideo2.getSize());
        	imageObject.set("mobileUrl", zjTzAerialVideo2.getMobileUrl());
        	imageObject.set("mobileThumbUrl", zjTzAerialVideo2.getMobileThumbUrl());
        	imageObject.set("relativeUrl", zjTzAerialVideo2.getRelativeUrl());
        	imageObject.set("relativeThumbUrl", zjTzAerialVideo2.getRelativeThumbUrl());
        	List<JSONObject> imageList = new ArrayList();
        	imageList.add(imageObject);
        	//
        	JSONObject videoObject = new JSONObject();
        	videoObject.set("name", zjTzAerialVideo2.getVideoName());
        	videoObject.set("size", zjTzAerialVideo2.getVideoSize());
        	videoObject.set("type", zjTzAerialVideo2.getVideoType());
        	videoObject.set("uid", zjTzAerialVideo2.getVideoUid());
        	videoObject.set("uploadURL", zjTzAerialVideo2.getVideoDownUrl());
        	videoObject.set("downUrl", zjTzAerialVideo2.getVideoDownUrl());
        	
        	String objectName =  getObjectName(zjTzAerialVideo2.getVideoDownUrl());
        	videoObject.set("url", getObjectUrl("video", objectName)+"");
        	
        	
        	List<JSONObject> videoList = new ArrayList();
        	videoList.add(videoObject);
        	zjTzAerialVideo2.setImageList(imageList);
        	zjTzAerialVideo2.setVideoList(videoList);
        	//
//        	zjTzAerialVideo2.setVideoPreviewUrl(getObjectUrl("video", objectName));
		}
        // 得到分页信息
        PageInfo<ZjTzAerialVideo> p = new PageInfo<>(zjTzAerialVideoList);
        return repEntity.okList(zjTzAerialVideoList, p.getTotal());
    }
    
    private String getObjectName(String url){
        if(StrUtil.isBlank(url)){
            return "";
        }
        return url.substring(url.lastIndexOf("/") + 1, url.indexOf("+"));
    }
    
    public String getObjectUrl(String bucketName, String objectName) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectName)
                .expiry(7, TimeUnit.DAYS)
                .build();

        try {
			return client.getPresignedObjectUrl(args);
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
    }

    @Override
    public ResponseEntity getZjTzAerialVideoDetails(ZjTzAerialVideo zjTzAerialVideo) {
        if (zjTzAerialVideo == null) {
            zjTzAerialVideo = new ZjTzAerialVideo();
        }
        // 获取数据
        ZjTzAerialVideo dbZjTzAerialVideo = zjTzAerialVideoMapper.selectByPrimaryKey(zjTzAerialVideo.getAerialVideo());
        // 数据存在
        if (dbZjTzAerialVideo != null) {
            return repEntity.ok(dbZjTzAerialVideo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");

        
        
        
        }
    
    }
    @Override
    public ResponseEntity saveZjTzAerialVideo(ZjTzAerialVideo zjTzAerialVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzAerialVideo.setAerialVideo(UuidUtil.generate());
        zjTzAerialVideo.setCreateUserInfo(userKey, realName);
        
        
        ZjTzProManage proManage = zjTzProManageMapper.selectByPrimaryKey(zjTzAerialVideo.getProjectId());
        if(proManage != null && StrUtil.isNotEmpty(proManage.getProjectId()) ) {
        	zjTzAerialVideo.setKeyFlag(proManage.getKeyFlag());
        	zjTzAerialVideo.setProNo(proManage.getProNo());
        	
        	zjTzAerialVideo.setCompanyId(proManage.getCompanyId());
        	zjTzAerialVideo.setCompanyName(proManage.getCompanyName());
        }
        
        
        if(zjTzAerialVideo.getImageList() != null && zjTzAerialVideo.getImageList().size() >0) {
        	JSONObject imageObject = zjTzAerialVideo.getImageList().get(0);
        	zjTzAerialVideo.setImageName(imageObject.get("fileName")+"");
        	zjTzAerialVideo.setImageUrl(imageObject.get("url")+"");
        	zjTzAerialVideo.setThumbUrl(imageObject.get("thumbUrl")+"");
        	zjTzAerialVideo.setType(imageObject.get("type")+"");
        	zjTzAerialVideo.setSize(imageObject.get("size")+"");
        	zjTzAerialVideo.setMobileUrl(imageObject.get("mobileUrl")+"");
        	zjTzAerialVideo.setMobileThumbUrl(imageObject.get("mobileThumbUrl")+"");
        	zjTzAerialVideo.setRelativeUrl(imageObject.get("relativeUrl")+"");
        	zjTzAerialVideo.setRelativeThumbUrl(imageObject.get("relativeThumbUrl")+"");
        	
        }
        if(zjTzAerialVideo.getVideoList() != null && zjTzAerialVideo.getVideoList().size() >0) {
        	JSONObject videoObject = zjTzAerialVideo.getVideoList().get(0);
        	zjTzAerialVideo.setVideoDownUrl(videoObject.getStr("url"));
        	zjTzAerialVideo.setVideoType(videoObject.getStr("type"));
        	zjTzAerialVideo.setVideoUid(videoObject.getStr("uid"));
        	zjTzAerialVideo.setVideoSize(videoObject.getStr("size"));
        }
        
        
        int flag = zjTzAerialVideoMapper.insert(zjTzAerialVideo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzAerialVideo);
        }
    }

    @Override
    public ResponseEntity updateZjTzAerialVideo(ZjTzAerialVideo zjTzAerialVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzAerialVideo dbzjTzAerialVideo = zjTzAerialVideoMapper.selectByPrimaryKey(zjTzAerialVideo.getAerialVideo());
        if (dbzjTzAerialVideo != null && StrUtil.isNotEmpty(dbzjTzAerialVideo.getAerialVideo())) {
           // 排序
           dbzjTzAerialVideo.setSort(zjTzAerialVideo.getSort());
           // 项目id
           dbzjTzAerialVideo.setProjectId(zjTzAerialVideo.getProjectId());
           // 项目name
           dbzjTzAerialVideo.setProjectName(zjTzAerialVideo.getProjectName());
           ZjTzProManage proManage = zjTzProManageMapper.selectByPrimaryKey(zjTzAerialVideo.getProjectId());
           if(proManage != null && StrUtil.isNotEmpty(proManage.getProjectId()) ) {
        	   dbzjTzAerialVideo.setKeyFlag(proManage.getKeyFlag());
        	   dbzjTzAerialVideo.setProNo(proManage.getProNo());
        	   
        	   dbzjTzAerialVideo.setCompanyId(proManage.getCompanyId());
        	   dbzjTzAerialVideo.setCompanyName(proManage.getCompanyName());
           }
           // 年月date
           dbzjTzAerialVideo.setYearDate(zjTzAerialVideo.getYearDate());
           // 年月str
           dbzjTzAerialVideo.setYearStr(zjTzAerialVideo.getYearStr());
           // 期次
           dbzjTzAerialVideo.setIssue(zjTzAerialVideo.getIssue());
           // 视频name
           dbzjTzAerialVideo.setVideoName(zjTzAerialVideo.getVideoName());
           
           if(zjTzAerialVideo.getImageList() != null && zjTzAerialVideo.getImageList().size() >0) {
           	JSONObject imageObject = zjTzAerialVideo.getImageList().get(0);
           	dbzjTzAerialVideo.setImageName(imageObject.get("fileName")+"");
           	dbzjTzAerialVideo.setImageUrl(imageObject.get("url")+"");
           	dbzjTzAerialVideo.setThumbUrl(imageObject.get("thumbUrl")+"");
           	dbzjTzAerialVideo.setType(imageObject.get("type")+"");
           	dbzjTzAerialVideo.setSize(imageObject.get("size")+"");
           	dbzjTzAerialVideo.setMobileUrl(imageObject.get("mobileUrl")+"");
           	dbzjTzAerialVideo.setMobileThumbUrl(imageObject.get("mobileThumbUrl")+"");
           	dbzjTzAerialVideo.setRelativeUrl(imageObject.get("relativeUrl")+"");
           	dbzjTzAerialVideo.setRelativeThumbUrl(imageObject.get("relativeThumbUrl")+"");
        	
           }
           if(zjTzAerialVideo.getVideoList() != null && zjTzAerialVideo.getVideoList().size() >0) {
           	JSONObject videoObject = zjTzAerialVideo.getVideoList().get(0);
           	dbzjTzAerialVideo.setVideoDownUrl(videoObject.getStr("uploadURL"));
           	dbzjTzAerialVideo.setVideoType(videoObject.getStr("type"));
           	dbzjTzAerialVideo.setVideoUid(videoObject.getStr("uid"));
           	dbzjTzAerialVideo.setVideoSize(videoObject.getStr("size"));
           }
           
           // 共通
           dbzjTzAerialVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzAerialVideoMapper.updateByPrimaryKey(dbzjTzAerialVideo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzAerialVideo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzAerialVideo(List<ZjTzAerialVideo> zjTzAerialVideoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzAerialVideoList != null && zjTzAerialVideoList.size() > 0) {
           ZjTzAerialVideo zjTzAerialVideo = new ZjTzAerialVideo();
           zjTzAerialVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzAerialVideoMapper.batchDeleteUpdateZjTzAerialVideo(zjTzAerialVideoList, zjTzAerialVideo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzAerialVideoList);
        }
    }
}
