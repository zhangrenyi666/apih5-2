package com.apih5.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.FileMetaEntity;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.OtherUtils;
import com.apih5.framework.utils.UuidUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

@RestController
public class UploadByNtkoController {
	private static final Logger logger = LoggerFactory.getLogger(UploadByNtkoController.class);
	@Autowired(required = true)
	private ResponseEntity repEntity;
	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;

	// LinkedList<FileMetaEntity> files = new LinkedList<FileMetaEntity>();
	// FileMetaEntity fileMeta = null;
	/***************************************************
	 * URL: /rest/controller/upload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequireToken
	@PostMapping("/ntkoUploadOffice")
	public ResponseEntity ntkoUploadOffice(String fileName, MultipartHttpServletRequest fileFieldName,
			HttpServletResponse response) {
		LinkedList<FileMetaEntity> files = new LinkedList<FileMetaEntity>();
		FileMetaEntity fileMeta = null;
		// String projectName = request.getParameter("projectName");
//		if (StrUtil.isEmpty(projectName)) {
//		}
		String projectName = "ntko_office";
		// 1. build an iterator
		Iterator<String> itr = fileFieldName.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = fileFieldName.getFile(itr.next());
			// System.out.println(mpf.getOriginalFilename() +" uploaded!
			// "+files.size());
			 // 文件夹类型限制
			String originalFilename = mpf.getOriginalFilename();
			if(StrUtil.isNotEmpty(originalFilename)) {
				return repEntity.layerMessage("no", "上传类型错误");
			}

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// String newOriginFileName = uid
			// +
			// mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
			// 写死docx
			String newOriginFileName = UuidUtil.generate() + ".docx";
			// 2.3 create new fileMeta
			fileMeta = new FileMetaEntity();
			// fileMeta.setUid(UuidUtil.createUUID());
			// fileMeta.setUid("");
			// fileMeta.setName(mpf.getOriginalFilename());
			fileMeta.setName(fileName);
			fileMeta.setSize(String.valueOf(mpf.getSize() / 1024));
			fileMeta.setType(mpf.getContentType());
			// 前端显示路径
//			if (fileMeta.getType().indexOf("jpg") >= 0 || fileMeta.getType().indexOf("png") >= 0
//					|| fileMeta.getType().indexOf("gif") >= 0 || fileMeta.getType().indexOf("bmp") >= 0
//					|| fileMeta.getType().indexOf("jpeg") >= 0) {
				fileMeta.setUrl(HttpUtil.getUploadPathWeb(fileFieldName, projectName) + newOriginFileName);
//			} else {
//				fileMeta.setUrl(Apih5Properties.getWebUrl() + "downloadFile?filePath="
//						+ HttpUtil.getUploadPathRelative(projectName) + URLUtil.encode(newOriginFileName) + "&downName="
//						+ fileMeta.getName());
//			}
			fileMeta.setThumbUrl(HttpUtil.getUploadPathWeb(fileFieldName, projectName) + newOriginFileName);
			// 手机web路径
			fileMeta.setMobileUrl(HttpUtil.getOffice365(fileMeta.getUrl()));
			fileMeta.setMobileThumbUrl(fileMeta.getMobileUrl());
			// 相对路径保存路径
			fileMeta.setRelativeUrl(HttpUtil.getUploadPathRelative(projectName) + newOriginFileName);
			fileMeta.setRelativeThumbUrl(fileMeta.getRelativeUrl());
			try {
				fileMeta.setBytes(mpf.getBytes());
				File file = new File(HttpUtil.getUploadPath(projectName));
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				// copy file to local disk (make sure the path "e.g.
				// D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(),
						new FileOutputStream(HttpUtil.getUploadPath(projectName) + newOriginFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return repEntity.error("sys.exception");
			}
			// 2.4 add to files
			files.add(fileMeta);
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8
		// Kb","fileType":"image/png"},...]
		// return files;
		return repEntity.ok(fileMeta);
	}

}
