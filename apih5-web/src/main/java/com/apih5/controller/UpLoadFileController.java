package com.apih5.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.FileMetaEntity;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CodecUtil;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.OtherUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.utils.ApkInfoUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import net.coobird.thumbnailator.Thumbnails;

@RestController
public class UpLoadFileController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	private ResponseEntity repEntity;
	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;
	
//	LinkedList<FileMetaEntity> files = new LinkedList<FileMetaEntity>();
//	FileMetaEntity fileMeta = null;
	/***************************************************
	 * URL: /rest/controller/upload  
	 * upload(): receives files
	 * @param request : MultipartHttpServletRequest auto passed
	 * @param response : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequireToken
	@PostMapping("/upload")
	public ResponseEntity upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		LinkedList<FileMetaEntity> files = new LinkedList<FileMetaEntity>();
		FileMetaEntity fileMeta = null;
		String projectName = request.getParameter("projectName");
		//1. build an iterator
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = null;

		 //2. get each file
		 while(itr.hasNext()){
			 mpf = request.getFile(itr.next()); 

			 // ?????????????????????
			 String fileName = mpf.getOriginalFilename();
			 if(!OtherUtils.isUploadFileType(fileName)) {
				 return repEntity.layerMessage("no", "??????????????????");
			 }
			 
			 //2.2 if files > 10 remove the first from the list
			 if(files.size() >= 10)
				 files.pop();

			 
			 if(mpf.getOriginalFilename().indexOf(".") <0) {
				return repEntity.layerMessage("no", "???????????????" + mpf.getOriginalFilename() + "??????????????????");
			 }
			 String uid = UuidUtil.generate();
			 String newOriginFileName = uid + mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
			 //2.3 create new fileMeta
			 fileMeta = new FileMetaEntity();
			 fileMeta.setUid(UuidUtil.createUUID());
			 fileMeta.setUid("");
			 fileMeta.setName(mpf.getOriginalFilename());
			 fileMeta.setSize(String.valueOf(mpf.getSize()/1024));
			 fileMeta.setType(mpf.getContentType());
			 
			 // http://xxx.com/upload/yyy.png
			 String httpUrl = HttpUtil.getUploadPathWeb(request, projectName) + newOriginFileName;
			 
			 // ??????????????????
			 if(fileMeta.getType().indexOf("jpg") >=0
					 || fileMeta.getType().indexOf("png") >=0
					 || fileMeta.getType().indexOf("gif") >=0
					 || fileMeta.getType().indexOf("bmp") >=0
					 || fileMeta.getType().indexOf("image/") >=0
					 || fileMeta.getType().indexOf("audio/") >=0
					 || fileMeta.getType().indexOf("video/") >=0
					 || fileMeta.getType().indexOf("jpeg") >=0){
				 fileMeta.setUrl(httpUrl);
			 } else {
				 fileMeta.setUrl(Apih5Properties.getWebUrl() +"downloadFile?filePath="+ HttpUtil.getUploadPathRelative(projectName) 
				 + URLUtil.encode(newOriginFileName) +"&downName=" + RandomUtil.randomInt(100) + RandomUtil.randomInt(100) + CodecUtil.encodeURL(fileMeta.getName()));
			 }
			 fileMeta.setThumbUrl(httpUrl);
			 
			 if(OtherUtils.isWeb365File(mpf.getOriginalFilename())) {
				 // ??????web??????[office365]
				 fileMeta.setMobileUrl(HttpUtil.getOffice365(httpUrl));
			 } else {
				 // ??????web??????
				 fileMeta.setMobileUrl(fileMeta.getUrl());
			 }
			 fileMeta.setMobileThumbUrl(fileMeta.getMobileUrl());
			 
			 // ????????????????????????
			 fileMeta.setRelativeUrl(HttpUtil.getUploadPathRelative(projectName) + newOriginFileName);
			 fileMeta.setRelativeThumbUrl(fileMeta.getRelativeUrl());
			 try {
//				 fileMeta.setBytes(mpf.getBytes());
				 File file = new File(HttpUtil.getUploadPath(projectName));
				 if (!file.exists() && !file.isDirectory()) {
					 file.mkdirs();
				 }
				 // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
//				 System.out.println("-----"+mpf.getBytes().length);
				 FileUtil.writeFromStream(mpf.getInputStream(), HttpUtil.getUploadPath(projectName) + newOriginFileName);
//                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(HttpUtil.getUploadPath(projectName) + newOriginFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return repEntity.error("sys.exception");
			}
			 //2.4 add to files
			 files.add(fileMeta);
		 }
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
//		return files;
		return repEntity.ok(fileMeta);
	}
	
	/**
	 * ?????????upload???????????????????????????
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireToken
	@PostMapping("/uploadList")
	public ResponseEntity uploadList(MultipartHttpServletRequest request, HttpServletResponse response) {
		LinkedList<FileMetaEntity> files = new LinkedList<FileMetaEntity>();
		FileMetaEntity fileMeta = null;
		String projectName = request.getParameter("projectName");
		//1. build an iterator
		Iterator<String> itr =  request.getFileNames();
		MultipartFile mpf = null;
		
		//2. get each file
		while(itr.hasNext()){
			mpf = request.getFile(itr.next()); 
			
			// ?????????????????????
			String fileName = mpf.getOriginalFilename();
			if(!OtherUtils.isUploadFileType(fileName)) {
				return repEntity.layerMessage("no", "??????????????????");
			}
			
			//2.2 if files > 10 remove the first from the list
			if(files.size() >= 10)
				files.pop();
			
			
			if(mpf.getOriginalFilename().indexOf(".") <0) {
				return repEntity.layerMessage("no", "???????????????" + mpf.getOriginalFilename() + "??????????????????");
			}
			String uid = UuidUtil.generate();
			String newOriginFileName = uid + mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
			//2.3 create new fileMeta
			fileMeta = new FileMetaEntity();
			fileMeta.setUid(UuidUtil.createUUID());
			fileMeta.setUid("");
			fileMeta.setName(mpf.getOriginalFilename());
			fileMeta.setSize(String.valueOf(mpf.getSize()/1024));
			fileMeta.setType(mpf.getContentType());
			
			// http://xxx.com/upload/yyy.png
			String httpUrl = HttpUtil.getUploadPathWeb(request, projectName) + newOriginFileName;
			
			// ??????????????????
			if(fileMeta.getType().indexOf("jpg") >=0
					|| fileMeta.getType().indexOf("png") >=0
					|| fileMeta.getType().indexOf("gif") >=0
					|| fileMeta.getType().indexOf("bmp") >=0
					|| fileMeta.getType().indexOf("image/") >=0
					|| fileMeta.getType().indexOf("audio/") >=0
					|| fileMeta.getType().indexOf("video/") >=0
					|| fileMeta.getType().indexOf("jpeg") >=0){
				fileMeta.setUrl(httpUrl);
			} else {
				fileMeta.setUrl(Apih5Properties.getWebUrl() +"downloadFile?filePath="+ HttpUtil.getUploadPathRelative(projectName) 
				+ URLUtil.encode(newOriginFileName) +"&downName=" + RandomUtil.randomInt(100) + CodecUtil.encodeURL(fileMeta.getName()));
			}
			fileMeta.setThumbUrl(httpUrl);
			
			if(OtherUtils.isWeb365File(mpf.getOriginalFilename())) {
				// ??????web??????[office365]
				fileMeta.setMobileUrl(HttpUtil.getOffice365(httpUrl));
			} else {
				// ??????web??????
				fileMeta.setMobileUrl(fileMeta.getUrl());
			}
			fileMeta.setMobileThumbUrl(fileMeta.getMobileUrl());
			
			// ????????????????????????
			fileMeta.setRelativeUrl(HttpUtil.getUploadPathRelative(projectName) + newOriginFileName);
			fileMeta.setRelativeThumbUrl(fileMeta.getRelativeUrl());
			try {
				fileMeta.setBytes(mpf.getBytes());
				File file = new File(HttpUtil.getUploadPath(projectName));
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				// copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
				FileUtil.writeFromStream(mpf.getInputStream(), HttpUtil.getUploadPath(projectName) + newOriginFileName);
//				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(HttpUtil.getUploadPath(projectName) + newOriginFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return repEntity.error("sys.exception");
			}
			//2.4 add to files
			files.add(fileMeta);
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
//		return files;
		return repEntity.ok(files);
	}
	/***************************************************
	 * URL: /rest/controller/get/{value}
	 * get(): get file as an attachment
	 * @param response : passed by the server
	 * @param value : value from the URL
	 * @return void
	 ****************************************************/
////	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
//	@GetMapping("/get/{value}")
//	 public void get(HttpServletResponse response,@PathVariable String value){
//		 FileMetaEntity getFile = files.get(Integer.parseInt(value));
//		 try {		
//			 	response.setContentType(getFile.getFileType());
//			 	response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
//		        FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
//		 }catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//		 }
//	 }
	
	/**
	 * ???????????????????????????ng???ng???????????????????????????????????????????????????????????????
	 * ??????????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param fileId
	 * @param response
	 * @throws IOException
	 */
	// @RequireToken
	@GetMapping("/downloadFile")
	public void downloadFile(@RequestParam("filePath") String filePath, @RequestParam("downName") String downName,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(Apih5Properties.getFilePath() + filePath);
		if (file == null || !file.exists()) {
			response.sendError(404);
		} else {
			// ??????????????????
			if (file.exists()) {
				response.setContentType("multipart/form-data");

				String userAgent = request.getHeader("User-Agent");
				String formFileName = downName;

				// ??????IE?????????IE????????????????????????
				if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
					formFileName = java.net.URLEncoder.encode(formFileName, "UTF-8");
				} else {
					// ???IE?????????????????????
					formFileName = new String(formFileName.getBytes("UTF-8"), "ISO-8859-1");
				}

				response.setContentType("application/force-download");// ???????????????????????????
				// response.addHeader("Content-Disposition",
				// "attachment;fileName=" + downName);// ???????????????
				response.setHeader("X-Accel-Charset", "utf-8");
				response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", formFileName));
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setCharacterEncoding("UTF-8");

				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				OutputStream os = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("success");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * ????????????
	 * 
	 * @return Map
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadFileApp", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFileApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String apkPath = ApkInfoUtil.getFilePath(Apih5Properties.getFilePath() + "upload/apk/");
		String fileName = apkPath.substring(apkPath.lastIndexOf("\\") + 1);
		File file = new File(apkPath);
		if (file.exists()) {
			FileInputStream inputStream = new FileInputStream(file);
			ServletOutputStream outstr = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			byte[] buffer = new byte[1024];
			int b = inputStream.read(buffer);
			while (b != -1) {
				outstr.write(buffer, 0, b);
				b = inputStream.read(buffer);
			}
			inputStream.close();
			outstr.flush();
			outstr.close();
		}
	}
	
	/**
	 * apk????????????
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkVersion", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkVersion() {
		JSONObject versionBean = new JSONObject();
		versionBean.put("success", true);
		versionBean.put("message", "??????");
		String apkPath = ApkInfoUtil.getFilePath(Apih5Properties.getFilePath() + "upload/apk/");
		String versionCode = ApkInfoUtil.readAPK(apkPath);
		if (StrUtil.isEmpty(versionCode)) {
			versionBean.put("version", "0");
		} else {
			versionBean.put("version", versionCode);
			File file = new File(apkPath);
			versionBean.put("fileLength", file.length());
		}
		return versionBean;
	}
    
//	@GetMapping("/downloadFile2/{id}")
//	public org.springframework.http.ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
//	    File file = new File("D://-2018-6-4.txt");
//	    byte[] body = null;
//	    InputStream is = new FileInputStream(file);
//	    body = new byte[is.available()];
//	    is.read(body);
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Content-Disposition", "attchement;filename=" + file.getName());
//	    HttpStatus statusCode = HttpStatus.OK;
//	    org.springframework.http.ResponseEntity<byte[]> entity = new  org.springframework.http.ResponseEntity<byte[]>(body, headers, statusCode);
//	    return entity;
//	}
	
	// @RequestMapping(value = "/uploadFiles")
	@PostMapping("/uploadFiles")
	// @RequireToken
	public void uploadFiles(@RequestParam(value = "filesName") MultipartFile filesName, HttpServletRequest request,
			HttpServletResponse response) {
//		ApplicationHome home = new ApplicationHome(getClass());
//		File jarFile = home.getSource();
//		jarFile.getParentFile();
		String projectName = request.getParameter("projectName");
		// String url = HttpUtil.getUrl(request);
		// String url2 = "http://" + request.getServerName() + ":" +
		// request.getServerPort() + request.getContextPath();

		// File path2;
		// try {
		// path2 = new File(ResourceUtils.getURL("classpath:").getPath());
		// System.out.println("path:"+path2.getAbsolutePath());
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		JSONObject JSONObject = new JSONObject();
		// ????????????????????????../??????????????????
		String path = HttpUtil.getUploadPath(projectName);
		// ???????????????
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		if (filesName.isEmpty()) {
			// ???????????????
			// resMap.put("status", "parm_is_empty");
		} else {
			String fileName = filesName.getOriginalFilename();
			if(!OtherUtils.isUploadFileType(fileName)) {
				return ;
			}
			 
			LoggerUtils.printDebugLogger(logger, "fileName--="+fileName);

			if(StrUtil.lastIndexOfIgnoreCase(fileName, "\\")>0){
				fileName = StrUtil.subAfter(fileName, "\\", true);
			}
			// ???????????????
			String originFileName = UuidUtil.generate()
					+ fileName.substring(fileName.lastIndexOf("."));
			try {
				// ????????????Apache???FileUtils?????????????????????
				FileUtils.copyInputStreamToFile(filesName.getInputStream(), new File(path, originFileName));

				String newPath = "";
				String oldPath = "";
				String convertFileName = "";
				// docx xlsx ????????????doc xls
				int index = originFileName.indexOf(".docx");
				int index1 = originFileName.indexOf(".xlsx");
				if (index > -1) {
					convertFileName = originFileName.substring(0, index) + ".doc";
					copy(path + originFileName, path + convertFileName);
					newPath = "upload/" + projectName + "/" + convertFileName;
					oldPath = path + convertFileName;
				} else if (index1 > -1) {
					convertFileName = originFileName.substring(0, index1) + ".xls";
					copy(path + originFileName, path + convertFileName);
					newPath = "upload/" + projectName + "/" + convertFileName;
					oldPath = path + convertFileName;
				} else {
					newPath = "upload/" + projectName + "/" + originFileName;
					oldPath = path + originFileName;
				}

				String thumbPath = "";
				if ("sxquality".equals(projectName)) {
					long compareSize = filesName.getSize();
					double scale = 1.0d;
					if (compareSize >= 100 * 1024) {
						if (compareSize > 0) {
							scale = (100 * 1024f) / compareSize;
						}
					}
					if (compareSize >= 100 * 1024) {
						String thumbName = UuidUtil.generate() + ".jpg";
						Thumbnails.of(oldPath).scale(1f).outputQuality(scale).toFile(path + thumbName);
						thumbPath = "upload/" + projectName + "/" + thumbName;
					} else {
						thumbPath = newPath;
					}
				}

				// ??????????????????
				String size = "";
				File fileSize = new File(oldPath);
				if (fileSize.exists() && fileSize.isFile()) {
					size = getPrintSize(fileSize.length());
				}
				JSONObject.put("thumbPath", thumbPath);
				JSONObject.put("fileSize", size);
				JSONObject.put("realUrl", oldPath);
				JSONObject.put("success", "true");
				JSONObject.put("message", "OK");
				JSONObject.put("fileUrl", newPath);
				JSONObject.put("url", Apih5Properties.getWebUrl() + newPath);
				JSONObject.put("fileName", fileName);
			} catch (IOException e) {
				LoggerUtils.printExceptionLogger(logger, "fileName-??????????????????-="+e.getMessage());
				JSONObject.put("success", "false");
				JSONObject.put("message", "ERROR");
			}
		}
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(JSONObject.toString());
			writer.flush();
		} catch (IOException e) {
			// log.warn(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					// log.debug(e);
				}
			}
		}
	}

	/**
	 * ?????????word?????????????????????
	 * 
	 * @param filesName
	 * @param request
	 * @param response
	 */
	@PostMapping("/uploadFiles2")
	public void uploadFiles2(@RequestParam(value = "filesName") MultipartFile filesName, HttpServletRequest request,
			HttpServletResponse response) {
//		ApplicationHome home = new ApplicationHome(getClass());
//		File jarFile = home.getSource();
//		jarFile.getParentFile();
		String projectName = request.getParameter("projectName");
		// String url = HttpUtil.getUrl(request);
		// String url2 = "http://" + request.getServerName() + ":" +
		// request.getServerPort() + request.getContextPath();
		
		// File path2;
		// try {
		// path2 = new File(ResourceUtils.getURL("classpath:").getPath());
		// System.out.println("path:"+path2.getAbsolutePath());
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		JSONObject JSONObject = new JSONObject();
		// ????????????????????????../??????????????????
		String path = HttpUtil.getUploadPath(projectName);
		// ???????????????
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		if (filesName.isEmpty()) {
			// ???????????????
			// resMap.put("status", "parm_is_empty");
		} else {
			if(!OtherUtils.isUploadFileType(filesName.getOriginalFilename())) {
				 return;
			 }
			// ???????????????
			String originFileName = UuidUtil.generate()
					+ filesName.getOriginalFilename().substring(filesName.getOriginalFilename().lastIndexOf("."));
			try {
				// ????????????Apache???FileUtils?????????????????????
				FileUtils.copyInputStreamToFile(filesName.getInputStream(), new File(path, originFileName));
				
				String newPath = "";
				String oldPath = "";
				String convertFileName = "";
				// docx xlsx ????????????doc xls
				int index = originFileName.indexOf(".sssssssssssss");
				int index1 = originFileName.indexOf(".sssssssssssss");
				if (index > -1) {
					convertFileName = originFileName.substring(0, index) + ".doc";
					copy(path + originFileName, path + convertFileName);
					newPath = "upload/" + projectName + "/" + convertFileName;
					oldPath = path + convertFileName;
				} else if (index1 > -1) {
					convertFileName = originFileName.substring(0, index1) + ".xls";
					copy(path + originFileName, path + convertFileName);
					newPath = "upload/" + projectName + "/" + convertFileName;
					oldPath = path + convertFileName;
				} else {
					newPath = "upload/" + projectName + "/" + originFileName;
					oldPath = path + originFileName;
				}
				
				String thumbPath = "";
				if ("sxquality".equals(projectName)) {
					long compareSize = filesName.getSize();
					double scale = 1.0d;
					if (compareSize >= 100 * 1024) {
						if (compareSize > 0) {
							scale = (100 * 1024f) / compareSize;
						}
					}
					if (compareSize >= 100 * 1024) {
						String thumbName = UuidUtil.generate() + ".jpg";
						Thumbnails.of(oldPath).scale(1f).outputQuality(scale).toFile(path + thumbName);
						thumbPath = "upload/" + projectName + "/" + thumbName;
					} else {
						thumbPath = newPath;
					}
				}
				
				// ??????????????????
				String size = "";
				File fileSize = new File(oldPath);
				if (fileSize.exists() && fileSize.isFile()) {
					size = getPrintSize(fileSize.length());
				}
				JSONObject.put("thumbPath", thumbPath);
				JSONObject.put("fileSize", size);
				JSONObject.put("realUrl", oldPath);
				JSONObject.put("success", "true");
				JSONObject.put("message", "OK");
				JSONObject.put("fileUrl", newPath);
				JSONObject.put("fileName", filesName.getOriginalFilename());
			} catch (IOException e) {
				System.out.println("??????????????????");
				JSONObject.put("success", "false");
				JSONObject.put("message", "ERROR");
			}
		}
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(JSONObject.toString());
			writer.flush();
		} catch (IOException e) {
			// log.warn(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					// log.debug(e);
				}
			}
		}
	}
	
	/**
	 * ?????????word?????????????????????(?????????????????????????????????
	 * 
	 * @param filesName
	 * @param request
	 * @param response
	 */
	@PostMapping("/uploadFilesByFileName")
	public void uploadFilesByFileName(@RequestParam(value = "filesName") MultipartFile filesName, HttpServletRequest request,
			HttpServletResponse response) {
//		ApplicationHome home = new ApplicationHome(getClass());
//		File jarFile = home.getSource();
//		jarFile.getParentFile();
		String projectName = request.getParameter("projectName");
		JSONObject JSONObject = new JSONObject();
		// ????????????????????????../??????????????????
		String path = HttpUtil.getUploadPath(projectName);
		// ???????????????
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		if (filesName.isEmpty()) {
			// ???????????????
			// resMap.put("status", "parm_is_empty");
		} else {
			if(!OtherUtils.isUploadFileType(filesName.getOriginalFilename())) {
				 return;
			 }
			// ???????????????
			String originFileName = UuidUtil.generate()
					+ filesName.getOriginalFilename().substring(filesName.getOriginalFilename().lastIndexOf("."));
			try {
				// ????????????Apache???FileUtils?????????????????????
				FileUtils.copyInputStreamToFile(filesName.getInputStream(), new File(path, originFileName));
				
				String fileUrl = "";
				String realUrl = path + originFileName;
				String thumbPath = "";
				//???????????????????????????name
				String fileName1 = filesName.getOriginalFilename().replace("\\", "/");
				fileName1 = fileName1.contains("/") ? fileName1.substring(fileName1.lastIndexOf("/") + 1) : fileName1;
				
				// ????????????????????????????????????????????????????????????????????????downloadFile
				if(originFileName.indexOf(".jpg") >=0
                        || originFileName.indexOf(".png") >=0
                        || originFileName.indexOf(".gif") >=0
                        || originFileName.indexOf(".bmp") >=0
                        || originFileName.indexOf(".jpeg") >=0){
					fileUrl = HttpUtil.getUploadPathWeb(request, projectName) + originFileName;
				 } else {
					 fileUrl = Apih5Properties.getWebUrl() + "downloadFile?filePath=" + HttpUtil.getUploadPathRelative(projectName)
						+ URLUtil.encode(originFileName) +"&downName=" + RandomUtil.randomInt(100) + CodecUtil.encodeURL(fileName1);
				 }
				
				// ??????????????????
				String size = "";
				File fileSize = new File(realUrl);
				if (fileSize.exists() && fileSize.isFile()) {
					size = getPrintSize(fileSize.length());
				}
				JSONObject.set("thumbPath", thumbPath);
				JSONObject.set("realUrl", realUrl);
				JSONObject.set("fileUrl", fileUrl);
				JSONObject.set("fileName", fileName1);
				JSONObject.set("fileSize", size);
				JSONObject.set("success", "true");
				JSONObject.set("message", "OK");
			} catch (IOException e) {
				System.out.println("??????????????????");
				JSONObject.set("success", "false");
				JSONObject.set("message", "ERROR");
			}
		}
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(JSONObject.toString());
			writer.flush();
		} catch (IOException e) {
			// log.warn(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					// log.debug(e);
				}
			}
		}
	}

	// office 2007???????????????2003??????
	private void copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				if (inStream != null) {
					inStream.close();
				}
				if (fs != null) {
					fs.close();
				}
			}
		} catch (Exception e) {
//			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	private String getPrintSize(long size) {
		// ?????????????????????1024???????????????B???????????????????????????1024??????3?????????????????????
		double value = (double) size;
		if (value < 1024) {
			return String.valueOf(value) + "B";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		// ????????????????????????1024???????????????1024?????????????????????KB????????????
		// ??????????????????????????????????????????????????????
		// ?????????????????????
		if (value < 1024) {
			return String.valueOf(value) + "KB";
		} else {
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		}
		if (value < 1024) {
			return String.valueOf(value) + "MB";
		} else {
			// ??????????????????GB????????????????????????1024?????????????????????
			value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			return String.valueOf(value) + "GB";
		}
	}
	
	/**
	 *  ?????????????????????
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadimage")
	@ResponseBody
	public String uploadimage(HttpServletRequest request, MultipartFile file) {
		String action = request.getParameter("action");
		if(StrUtil.equals("config", action)) {
			String callback = request.getParameter("callback");
			if(StrUtil.isNotEmpty(callback)) {
				// ???????????????callback
				return callback + "("+publicConfig.getProperty("baidu.ueditor.image", "")+")";
			} else {
				return publicConfig.getProperty("baidu.ueditor.image", "");
			}
			// ????????????php?????????????????????
			// return callback + "({\"imageActionName\":\"uploadimage\",\"imageFieldName\":\"upfile\",\"imageMaxSize\":2048000,\"imageAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"imageCompressEnable\":true,\"imageCompressBorder\":1600,\"imageInsertAlign\":\"none\",\"imageUrlPrefix\":\"\",\"imagePathFormat\":\"/upload\",\"scrawlActionName\":\"uploadscrawl\",\"scrawlFieldName\":\"upfile\",\"scrawlPathFormat\":\"/upload\",\"scrawlMaxSize\":2048000,\"scrawlUrlPrefix\":\"\",\"scrawlInsertAlign\":\"none\",\"snapscreenActionName\":\"uploadimage\",\"snapscreenPathFormat\":\"/upload\",\"snapscreenUrlPrefix\":\"\",\"snapscreenInsertAlign\":\"none\",\"catcherLocalDomain\":[\"127.0.0.1\",\"localhost\",\"img.baidu.com\"],\"catcherActionName\":\"catchimage\",\"catcherFieldName\":\"source\",\"catcherPathFormat\":\"/upload\",\"catcherUrlPrefix\":\"\",\"catcherMaxSize\":2048000,\"catcherAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"videoActionName\":\"uploadvideo\",\"videoFieldName\":\"upfile\",\"videoPathFormat\":\"/upload\",\"videoUrlPrefix\":\"\",\"videoMaxSize\":102400000,\"videoAllowFiles\":[\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\"],\"fileActionName\":\"uploadfile\",\"fileFieldName\":\"upfile\",\"filePathFormat\":\"/upload\",\"fileUrlPrefix\":\"\",\"fileMaxSize\":51200000,\"fileAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"],\"imageManagerActionName\":\"listimage\",\"imageManagerListPath\":\"/upload\",\"imageManagerListSize\":20,\"imageManagerUrlPrefix\":\"\",\"imageManagerInsertAlign\":\"none\",\"imageManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"fileManagerActionName\":\"listfile\",\"fileManagerListPath\":\"/upload\",\"fileManagerUrlPrefix\":\"\",\"fileManagerListSize\":20,\"fileManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\",\".flv\",\".swf\",\".mkv\",\".avi\",\".rm\",\".rmvb\",\".mpeg\",\".mpg\",\".ogg\",\".ogv\",\".mov\",\".wmv\",\".mp4\",\".webm\",\".mp3\",\".wav\",\".mid\",\".rar\",\".zip\",\".tar\",\".gz\",\".7z\",\".bz2\",\".cab\",\".iso\",\".doc\",\".docx\",\".xls\",\".xlsx\",\".ppt\",\".pptx\",\".pdf\",\".txt\",\".md\",\".xml\"]})";
		}
		
	    if (file == null || file.isEmpty()) {
	        return "error";
	    }
	    if(!OtherUtils.isUploadFileType(file.getOriginalFilename())) {
	    	 return "error";
		 }

	    // ???????????????
	    String fileName = file.getOriginalFilename();
	    // ????????????????????????
	    String suffixName = fileName.substring(fileName.lastIndexOf("."));
	    // ???????????????????????????????????????????????????
	    String newfileName = UuidUtil.generate() + suffixName;
	    // ????????????
	    File dest = new File(HttpUtil.getUploadPath("ueditor")+ newfileName);
	    // ????????????????????????
	    if (!dest.getParentFile().exists()) {
	        dest.getParentFile().mkdirs();
	    }
	    try {
	        file.transferTo(dest);
	        //??????????????????
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("state", "SUCCESS");
	        // ????????????
	        jsonObject.put("original", fileName);
	        jsonObject.put("title", newfileName);
	        jsonObject.put("url", HttpUtil.getUploadPathWeb(null, "ueditor") + newfileName);
	        jsonObject.put("type", suffixName);
	        jsonObject.put("size", file.getSize());
//	        String config = "{\"state\": \"SUCCESS\"," +
//	                "\"url\": \"" + HttpUtil.getUploadPathWeb(null, "ueditor") + newfileName + "\"," +
//	                "\"title\": \"" + newfileName + "\"," +
//	                "\"type\": \"" + ".png" + "\"," +
//	                "\"size\": \"" + file.getSize()+ "\"," +
//	                "\"original\": \"" + fileName + "\"}";
	        return jsonObject.toString();
	    } catch (IllegalStateException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "error";
	}

}
