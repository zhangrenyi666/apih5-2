package com.apih5.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzVideoHistoryMapper;
import com.apih5.mybatis.dao.ZjTzVideoMapper;
import com.apih5.mybatis.dao.ZjTzVideoNoteMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzVideo;
import com.apih5.mybatis.pojo.ZjTzVideoHistory;
import com.apih5.mybatis.pojo.ZjTzVideoNote;
import com.apih5.service.ZjTzVideoService;
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

@Service("zjTzVideoService")
public class ZjTzVideoServiceImpl implements ZjTzVideoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzVideoMapper zjTzVideoMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzVideoHistoryMapper zjTzVideoHistoryMapper;

    @Autowired(required = true)
    private ZjTzVideoNoteMapper zjTzVideoNoteMapper;
    
	@Autowired(required = true)
	private BaseCodeService baseCodeService;
	
	MinioClient client = new MinioClient.Builder()
//	            .endpoint("http://218.60.94.30:9000")
	            .endpoint("http://10.11.59.56:9000")
	            .credentials("admin", "admin123456")
//	            .credentials("admin", "admin@123456!")
	            .build();
    
	@Override
    public ResponseEntity getZjTzVideoListByCondition(ZjTzVideo zjTzVideo) {
        if (zjTzVideo == null) {
            zjTzVideo = new ZjTzVideo();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 增加权限查询条件
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzVideo.setReleaseId("1");
        }
        // 分页查询
        PageHelper.startPage(zjTzVideo.getPage(),zjTzVideo.getLimit());
        // 获取数据
        List<ZjTzVideo> zjTzVideoList = zjTzVideoMapper.selectByZjTzVideoList(zjTzVideo);
        for (ZjTzVideo Video : zjTzVideoList) {
        	
        	ZjTzFile zjTzFileCover= new ZjTzFile();
        	zjTzFileCover.setOtherType("0");
        	zjTzFileCover.setOtherId(Video.getVideoId());
        	List<ZjTzFile> zjTzCoverList = zjTzFileMapper.selectByZjTzFileList(zjTzFileCover);
        	List<JSONObject> imageList = new ArrayList();
        	for (ZjTzFile zjTzFile1 : zjTzCoverList) {
        		JSONObject imageObject = new JSONObject();
            	imageObject.set("fileName", zjTzFile1.getName());
            	imageObject.set("url", zjTzFile1.getUrl());
            	imageObject.set("thumburl", zjTzFile1.getThumbUrl());
            	imageObject.set("type", zjTzFile1.getType());
            	imageObject.set("size", zjTzFile1.getSize());
            	imageObject.set("mobileUrl", zjTzFile1.getMobileUrl());
            	imageObject.set("mobileThumbUrl", zjTzFile1.getMobileThumbUrl());
            	imageObject.set("relativeUrl", zjTzFile1.getRelativeUrl());
            	imageObject.set("relativeThumbUrl", zjTzFile1.getRelativeThumbUrl());
            	imageList.add(imageObject);
			}
        	Video.setImageList(imageList);
        	
//        	Video.setZjTzCoverList(zjTzCoverList);
        	
        	
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherType("1");
        	zjTzFileSelect.setOtherId(Video.getVideoId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	List<JSONObject> videoList = new ArrayList();
        	for (ZjTzFile zjTzFile2 : zjTzFileList) {
        		JSONObject videoObject = new JSONObject();
            	videoObject.set("name", zjTzFile2.getName());
            	videoObject.set("size", zjTzFile2.getSize());
            	videoObject.set("type", zjTzFile2.getType());
            	videoObject.set("uid", zjTzFile2.getUid());
            	videoObject.set("uploadURL", zjTzFile2.getUrl());
            	videoObject.set("downUrl", zjTzFile2.getUrl());
            	
            	String objectName =  getObjectName(zjTzFile2.getUrl());
            	videoObject.set("url", getObjectUrl("video", objectName)+"");
//            	videoObject.set("url", "http://192.168.1.33:1080/files/1299b704c2260f08ab62cd5b0130869b+90c87e48-d175-4325-ad80-8a7aebd73e94");
            	
            	videoList.add(videoObject);
			}
        	Video.setVideoList(videoList);
        	
        	
//        	Video.setZjTzFileList(zjTzFileList);
        }
        // 得到分页信息
        PageInfo<ZjTzVideo> p = new PageInfo<>(zjTzVideoList);

        return repEntity.okList(zjTzVideoList, p.getTotal());
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
    public ResponseEntity getZjTzVideoDetails(ZjTzVideo zjTzVideo) {
    	if (zjTzVideo == null) {
    		zjTzVideo = new ZjTzVideo();
    	}
    	// 获取数据
    	ZjTzVideo dbZjTzVideo = zjTzVideoMapper.selectByPrimaryKey(zjTzVideo.getVideoId());
    	// 数据存在
    	if (dbZjTzVideo != null) {
    		//封面
    		ZjTzFile zjTzFileCover= new ZjTzFile();
    		zjTzFileCover.setOtherType("0");
    		zjTzFileCover.setOtherId(dbZjTzVideo.getVideoId());
    		List<ZjTzFile> zjTzCoverList = zjTzFileMapper.selectByZjTzFileList(zjTzFileCover);
    		dbZjTzVideo.setZjTzCoverList(zjTzCoverList);
    		//视频
    		ZjTzFile zjTzFileSelect = new ZjTzFile();
    		zjTzFileSelect.setOtherType("1");
    		zjTzFileSelect.setOtherId(dbZjTzVideo.getVideoId());
    		List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    		dbZjTzVideo.setZjTzFileList(zjTzFileList);
    		return repEntity.ok(dbZjTzVideo);
    	}
    	else {
    		return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzVideo(ZjTzVideo zjTzVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzVideo.setVideoId(UuidUtil.generate());
        //发文层级
        if (StrUtil.isNotEmpty(zjTzVideo.getReleaseRankId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzVideo.getReleaseRankId());
        	zjTzVideo.setReleaseRankName(openBankName);
        }
        zjTzVideo.setReleaseId("0");
        zjTzVideo.setReleaseName("未发布");
        
        zjTzVideo.setHomeShow("0");
        zjTzVideo.setStatusId("0");
        zjTzVideo.setStatusName("未审核");
        zjTzVideo.setCreateUserInfo(userKey, realName);
        int flag = zjTzVideoMapper.insert(zjTzVideo);
        
//        // 封面list
//        List<ZjTzFile> zjTzCoverList = zjTzVideo.getZjTzCoverList();
//        if(zjTzCoverList != null && zjTzCoverList.size()>0) {
//            for(ZjTzFile zjTzFile:zjTzCoverList) {
//                zjTzFile.setUid(UuidUtil.generate());
//                zjTzFile.setOtherId(zjTzVideo.getVideoId());
//                zjTzFile.setOtherType("0");//封面
//                zjTzFile.setCreateUserInfo(userKey, realName);
//                zjTzFileMapper.insert(zjTzFile);
//            }
//        }
//        //视频
//        List<ZjTzFile> zjTzFileList = zjTzVideo.getZjTzFileList();
//        if(zjTzFileList != null && zjTzFileList.size()>0) {
//            for(ZjTzFile zjTzFile:zjTzFileList) {
//                zjTzFile.setUid(UuidUtil.generate());
//                zjTzFile.setOtherId(zjTzVideo.getVideoId());
//                zjTzFile.setOtherType("1");//封面
//                zjTzFile.setCreateUserInfo(userKey, realName);
//                zjTzFileMapper.insert(zjTzFile);
//            }
//        }
        ZjTzFile zjTzFile = new ZjTzFile();
//        if(zjTzVideo.getZjTzCoverList() != null && zjTzVideo.getZjTzCoverList().size() >0) {
//			JSONObject imageObject = zjTzVideo.getZjTzCoverList().get(0);
//			zjTzFile.setUid(UuidUtil.generate());
//			zjTzFile.setOtherId(zjTzVideo.getVideoId());
//			zjTzFile.setOtherType("1");// 视频
//			zjTzFile.setName(imageObject.getStr("name"));
//			zjTzFile.setSize(imageObject.getStr("size"));
//			zjTzFile.setType(imageObject.getStr("type"));
//			String imageUrl = imageObject.getStr("url");
//			zjTzFile.setUrl(imageUrl);
//			zjTzFile.setThumbUrl(imageUrl);
//			zjTzFile.setMobileUrl(imageUrl);
//			zjTzFile.setMobileThumbUrl(imageUrl);
//			zjTzFile.setRelativeUrl(imageUrl.substring(imageUrl.indexOf("f")));
//			zjTzFile.setRelativeThumbUrl(imageUrl.substring(imageUrl.indexOf("f")));
//			zjTzFile.setCreateUserInfo(userKey, realName);
//			zjTzFileMapper.insert(zjTzFile);
//        	
//        }
        if(zjTzVideo.getVideoList() != null && zjTzVideo.getVideoList().size() >0) {
        	JSONObject videoObject = zjTzVideo.getVideoList().get(0);
        	zjTzFile.setUid(UuidUtil.generate());
			zjTzFile.setOtherId(zjTzVideo.getVideoId());
			zjTzFile.setOtherType("1");// 视频
			zjTzFile.setName(videoObject.getStr("name"));
			zjTzFile.setSize(videoObject.getStr("size"));
			zjTzFile.setType(videoObject.getStr("type"));
			String url = videoObject.getStr("url");
			zjTzFile.setUrl(url);
			zjTzFile.setThumbUrl(url);
			zjTzFile.setMobileUrl(url);
			zjTzFile.setMobileThumbUrl(url);
			zjTzFile.setRelativeUrl(url.substring(url.indexOf("files")));
			zjTzFile.setRelativeThumbUrl(url.substring(url.indexOf("files")));
			zjTzFile.setCreateUserInfo(userKey, realName);
			zjTzFileMapper.insert(zjTzFile);
        }
        
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzVideo);
        }
    }

    @Override
    public ResponseEntity updateZjTzVideo(ZjTzVideo zjTzVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzVideo dbzjTzVideo = zjTzVideoMapper.selectByPrimaryKey(zjTzVideo.getVideoId());
        if (dbzjTzVideo != null && StrUtil.isNotEmpty(dbzjTzVideo.getVideoId())) {
        	 if(StrUtil.equals(dbzjTzVideo.getReleaseId(), "1")) {
          	   return repEntity.layerMessage("no", "该视频已经发布，不能修改！");
             }
        	 
        	// 标题
        	 dbzjTzVideo.setTitle(zjTzVideo.getTitle());
        	 //发文层级id
        	 dbzjTzVideo.setReleaseRankId(zjTzVideo.getReleaseRankId());
        	 //发文层级name
        	 if (StrUtil.isNotEmpty(zjTzVideo.getReleaseRankId())) {
        		 String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzVideo.getReleaseRankId());
        		 dbzjTzVideo.setReleaseRankName(openBankName);
        	 }
           
           // 发布日期
           dbzjTzVideo.setReleaseDate(zjTzVideo.getReleaseDate());
           // 主讲人
           dbzjTzVideo.setKeynoteSpeaker(zjTzVideo.getKeynoteSpeaker());
           // 内容简介
           dbzjTzVideo.setContent(zjTzVideo.getContent());
           // 登记日期
           dbzjTzVideo.setRegisteredDate(zjTzVideo.getRegisteredDate());
           // 共通
           dbzjTzVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoMapper.updateByPrimaryKey(dbzjTzVideo);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzVideo.getVideoId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           
//           if(zjTzVideo.getImageList() != null && zjTzVideo.getImageList().size() >0) {
//              	JSONObject imageObject = zjTzVideo.getImageList().get(0);
//              	ZjTzFile dbzjTzFile1 = new ZjTzFile();
//              	dbzjTzFile1.setUid(UuidUtil.generate());
//              	dbzjTzFile1.setOtherId(zjTzVideo.getVideoId());
//              	dbzjTzFile1.setOtherType("0");// 封面
//              	dbzjTzFile1.setName(imageObject.get("fileName") + "");
//              	dbzjTzFile1.setSize(imageObject.get("size") + "");
//              	dbzjTzFile1.setType(imageObject.get("type") + "");
//              	dbzjTzFile1.setUrl(imageObject.get("url") + "");
//              	dbzjTzFile1.setThumbUrl(imageObject.get("thumbUrl") + "");
//              	dbzjTzFile1.setMobileUrl(imageObject.get("mobileUrl") + "");
//              	dbzjTzFile1.setMobileThumbUrl(imageObject.get("mobileThumbUrl") + "");
//              	dbzjTzFile1.setRelativeUrl(imageObject.get("relativeUrl") + "");
//              	dbzjTzFile1.setRelativeThumbUrl(imageObject.get("relativeThumbUrl")+"");
//              	dbzjTzFile1.setCreateUserInfo(userKey, realName);
//                zjTzFileMapper.insert(dbzjTzFile1);
//           	
//              }
              if(zjTzVideo.getVideoList() != null && zjTzVideo.getVideoList().size() >0) {
              	JSONObject videoObject = zjTzVideo.getVideoList().get(0);
              	ZjTzFile dbzjTzFile2 = new ZjTzFile();
              	dbzjTzFile2.setUid(UuidUtil.generate());
              	dbzjTzFile2.setOtherId(zjTzVideo.getVideoId());
              	dbzjTzFile2.setOtherType("1");// 视频
              	dbzjTzFile2.setName(videoObject.getStr("name"));
              	dbzjTzFile2.setSize(videoObject.getStr("size"));
              	dbzjTzFile2.setType(videoObject.getStr("type"));
              	String url = videoObject.getStr("uploadURL");
              	dbzjTzFile2.setUrl(url);
              	dbzjTzFile2.setThumbUrl(url);
              	dbzjTzFile2.setMobileUrl(url);
              	dbzjTzFile2.setMobileThumbUrl(url);
              	dbzjTzFile2.setRelativeUrl(url.substring(url.indexOf("files")));
              	dbzjTzFile2.setRelativeThumbUrl(url.substring(url.indexOf("files")));
              	dbzjTzFile2.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(dbzjTzFile2);
              }
              
//           List<ZjTzFile> zjTzCoverList = zjTzVideo.getZjTzCoverList();
//           if(zjTzCoverList != null && zjTzCoverList.size()>0) {
//               for(ZjTzFile zjTzFile:zjTzCoverList) {
//                   zjTzFile.setUid(UuidUtil.generate());
//                   zjTzFile.setOtherId(zjTzVideo.getVideoId());
//                   zjTzFile.setOtherType("0");//封面
//                   zjTzFile.setCreateUserInfo(userKey, realName);
//                   zjTzFileMapper.insert(zjTzFile);
//               }
//           }
//           // 视频list
//           List<ZjTzFile> zjTzFileList = zjTzVideo.getZjTzFileList();
//           if(zjTzFileList != null && zjTzFileList.size()>0) {
//               for(ZjTzFile zjTzFile:zjTzFileList) {
//                   zjTzFile.setUid(UuidUtil.generate());
//                   zjTzFile.setOtherId(zjTzVideo.getVideoId());
//                   zjTzFile.setOtherType("1");
//                   zjTzFile.setCreateUserInfo(userKey, realName);
//                   zjTzFileMapper.insert(zjTzFile);
//               }
//           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzVideo(List<ZjTzVideo> zjTzVideoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoList != null && zjTzVideoList.size() > 0) {
        	for (ZjTzVideo zjTzVideo : zjTzVideoList) {
        		if(StrUtil.equals(zjTzVideo.getReleaseId(), "1")) {
               	   return repEntity.layerMessage("no", "该视频已经发布，不能删除！");
                  }
			}
        	
        	for (ZjTzVideo zjTzVideo : zjTzVideoList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzVideo.getVideoId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//删除浏览记录
        		ZjTzVideoHistory videoHistorySelect = new ZjTzVideoHistory();
        		videoHistorySelect.setVideoId(zjTzVideo.getVideoId());
        		List<ZjTzVideoHistory> delVideoHistoryList = zjTzVideoHistoryMapper.selectByZjTzVideoHistoryList(videoHistorySelect);
        		if(delVideoHistoryList != null && delVideoHistoryList.size() >0){
        			videoHistorySelect.setModifyUserInfo(userKey, realName);
        			zjTzVideoHistoryMapper.batchDeleteUpdateZjTzVideoHistory(delVideoHistoryList, videoHistorySelect);
        		}
        		//删除留言
        		ZjTzVideoNote videoNoteSelect = new ZjTzVideoNote();
        		videoNoteSelect.setVideoId(zjTzVideo.getVideoId());
        		List<ZjTzVideoNote> delVideoNoteList = zjTzVideoNoteMapper.selectByZjTzVideoNoteList(videoNoteSelect);
        		if(delVideoNoteList != null && delVideoNoteList.size() >0) {
        			zjTzVideoNoteMapper.batchDeleteUpdateZjTzVideoNote(delVideoNoteList, videoNoteSelect);
        		}
        	}
           ZjTzVideo zjTzVideo = new ZjTzVideo();
           zjTzVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoMapper.batchDeleteUpdateZjTzVideo(zjTzVideoList, zjTzVideo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzVideoList);
        }
    }

    @Override
    public ResponseEntity toHomeShowZjTzVideo(ZjTzVideo zjTzVideo) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzVideo dbzjTzVideo = zjTzVideoMapper.selectByPrimaryKey(zjTzVideo.getVideoId());
    	if (dbzjTzVideo != null && StrUtil.isNotEmpty(dbzjTzVideo.getVideoId())) {
    		if(StrUtil.equals(dbzjTzVideo.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的视频不能广而告之，请先发布");
    		}
    		dbzjTzVideo.setHomeShow("1");
    		dbzjTzVideo.setHomeShowTime(new Date());
    		dbzjTzVideo.setHomeShowStartDate(zjTzVideo.getHomeShowStartDate());
    		dbzjTzVideo.setHomeShowEndDate(zjTzVideo.getHomeShowEndDate());
    		dbzjTzVideo.setModifyUserInfo(userKey, realName);
    		flag = zjTzVideoMapper.updateByPrimaryKey(dbzjTzVideo);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzVideo);
    	}
    }

	@Override
	public ResponseEntity batchApproveAgreeZjTzVideo(List<ZjTzVideo> zjTzVideoList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoList != null && zjTzVideoList.size() > 0) {
           ZjTzVideo zjTzVideo = new ZjTzVideo();
           zjTzVideo.setStatusName("审核通过");
           zjTzVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoMapper.batchApproveAgreeZjTzVideo(zjTzVideoList, zjTzVideo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoList);
        }
	}

	@Override
	public ResponseEntity batchApproveRejectZjTzVideo(List<ZjTzVideo> zjTzVideoList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoList != null && zjTzVideoList.size() > 0) {
           ZjTzVideo zjTzVideo = new ZjTzVideo();
           zjTzVideo.setStatusName("驳回");
           zjTzVideo.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoMapper.batchApproveRejectZjTzVideo(zjTzVideoList, zjTzVideo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoList);
        }
	}

	@Override
	public ResponseEntity batchDeleteReleaseZjTzVideo(List<ZjTzVideo> zjTzVideoList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoList != null && zjTzVideoList.size() > 0) {
        	for (ZjTzVideo zjTzRules : zjTzVideoList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的制度，请重新选择！");
				}
			}
        	ZjTzVideo zjTzRules = new ZjTzVideo();
           zjTzRules.setReleaseName("发布");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoMapper.batchDeleteReleaseZjTzVideo(zjTzVideoList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzVideo(List<ZjTzVideo> zjTzVideoList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoList != null && zjTzVideoList.size() > 0) {
        	for (ZjTzVideo zjTzRules : zjTzVideoList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的视频，请重新选择！");
        		}
        	}
        	ZjTzVideo zjTzRules = new ZjTzVideo();
        	zjTzRules.setReleaseName("未发布");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzVideoMapper.batchDeleteRecallZjTzVideo(zjTzVideoList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoList);
        }
	}

	@Override
	public ResponseEntity getZjTzVideoHome(ZjTzVideo zjTzVideo) {
		if (zjTzVideo == null) {
			zjTzVideo = new ZjTzVideo();
		}
		ZjTzVideo dbzjTzVideo = zjTzVideoMapper.selectHomeZjTzVideo(zjTzVideo);
		if (dbzjTzVideo != null) {
			String objectName =  getObjectName(dbzjTzVideo.getVideoUrl());
			dbzjTzVideo.setVideoUrl(getObjectUrl("video", objectName)+"");
		}else {
			dbzjTzVideo = new ZjTzVideo();
		}
		return repEntity.ok(dbzjTzVideo);
	}
	
}
