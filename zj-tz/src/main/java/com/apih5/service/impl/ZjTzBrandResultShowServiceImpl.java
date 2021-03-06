package com.apih5.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzBrandResultShowMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzBrandResultShow;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzBrandResultShowService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;

@Service("zjTzBrandResultShowService")
public class ZjTzBrandResultShowServiceImpl implements ZjTzBrandResultShowService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzBrandResultShowMapper zjTzBrandResultShowMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzBrandResultShowListByCondition(ZjTzBrandResultShow zjTzBrandResultShow) {
        if (zjTzBrandResultShow == null) {
            zjTzBrandResultShow = new ZjTzBrandResultShow();
        }
        // ????????????
        PageHelper.startPage(zjTzBrandResultShow.getPage(),zjTzBrandResultShow.getLimit());
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	
        	if(StrUtil.equals("2", ext1)) {
        		zjTzBrandResultShow.setCompanyId("");
        		zjTzBrandResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}else {
        		zjTzBrandResultShow.setCompanyId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}
        	
        } else {
            // ???????????????
        	if(StrUtil.equals("all", zjTzBrandResultShow.getCompanyId(), true)) {
        		zjTzBrandResultShow.setComId("");
        		zjTzBrandResultShow.setCompanyId("");
            }else {
            	zjTzBrandResultShow.setCompanyId("");
            	zjTzBrandResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
            }
        }
       
        // ????????????
        List<ZjTzBrandResultShow> zjTzBrandResultShowList = zjTzBrandResultShowMapper.selectByZjTzBrandResultShowList(zjTzBrandResultShow);
        for (ZjTzBrandResultShow zjTzBrandResultShow2 : zjTzBrandResultShowList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzBrandResultShow2.getResultShowId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzBrandResultShow2.setZjTzFileList(zjTzFileList);
        	
        	List<JSONObject> projectList = new ArrayList<>();
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.accumulate("value", zjTzBrandResultShow2.getCompanyId());
        	jsonObject.accumulate("label", zjTzBrandResultShow2.getCompanyName());
        	projectList.add(jsonObject);
        	zjTzBrandResultShow2.setProjectList(projectList);
        	
		}
        
        // ??????????????????
        PageInfo<ZjTzBrandResultShow> p = new PageInfo<>(zjTzBrandResultShowList);

        return repEntity.okList(zjTzBrandResultShowList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzBrandResultShowDetails(ZjTzBrandResultShow zjTzBrandResultShow) {
        if (zjTzBrandResultShow == null) {
            zjTzBrandResultShow = new ZjTzBrandResultShow();
        }
        // ????????????
        ZjTzBrandResultShow dbZjTzBrandResultShow = zjTzBrandResultShowMapper.selectByPrimaryKey(zjTzBrandResultShow.getResultShowId());
        // ????????????
        if (dbZjTzBrandResultShow != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzBrandResultShow.getResultShowId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzBrandResultShow.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzBrandResultShow);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String companyId = TokenUtils.getCompanyId(request);
        zjTzBrandResultShow.setResultShowId(UuidUtil.generate());
        zjTzBrandResultShow.setComId(companyId);
        //????????????
        if (StrUtil.isNotEmpty(zjTzBrandResultShow.getResultTypeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("chengGuoLeiXing", zjTzBrandResultShow.getResultTypeId());
        	zjTzBrandResultShow.setResultTypeName(openBankName);
        }
		List<JSONObject> approve1List = zjTzBrandResultShow.getProjectList();
		if(approve1List != null && approve1List.size()>0) {
			Object object = approve1List.get(0);
			JSONObject jsonObject = new JSONObject(object);
			zjTzBrandResultShow.setCompanyId(jsonObject.getStr("value"));
			zjTzBrandResultShow.setCompanyName(jsonObject.getStr("label"));
		}
        zjTzBrandResultShow.setReleaseId("0");
        zjTzBrandResultShow.setReleaseName("?????????");
        zjTzBrandResultShow.setHomeShow("0");
        zjTzBrandResultShow.setCreateUserInfo(userKey, realName);
        int flag = zjTzBrandResultShowMapper.insert(zjTzBrandResultShow);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzBrandResultShow.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzBrandResultShow.getResultShowId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzBrandResultShow);
        }
    }

    @Override
    public ResponseEntity updateZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzBrandResultShow dbzjTzBrandResultShow = zjTzBrandResultShowMapper.selectByPrimaryKey(zjTzBrandResultShow.getResultShowId());
        if (dbzjTzBrandResultShow != null && StrUtil.isNotEmpty(dbzjTzBrandResultShow.getResultShowId())) {
           // ??????
           dbzjTzBrandResultShow.setTitle(zjTzBrandResultShow.getTitle());
//           // ????????????=??????id
//           dbzjTzBrandResultShow.setCompanyId(zjTzBrandResultShow.getCompanyId());
//           // ????????????=??????name
//           dbzjTzBrandResultShow.setCompanyName(zjTzBrandResultShow.getCompanyName());
           // ????????????id
           dbzjTzBrandResultShow.setResultTypeId(zjTzBrandResultShow.getResultTypeId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzBrandResultShow.getResultTypeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("chengGuoLeiXing", zjTzBrandResultShow.getResultTypeId());
           	dbzjTzBrandResultShow.setResultTypeName(openBankName);
           }
           // ????????????
           dbzjTzBrandResultShow.setContent(zjTzBrandResultShow.getContent());
           dbzjTzBrandResultShow.setGetSubjectId(zjTzBrandResultShow.getGetSubjectId());
           dbzjTzBrandResultShow.setResultLevelId(zjTzBrandResultShow.getResultLevelId());
           dbzjTzBrandResultShow.setGetTime(zjTzBrandResultShow.getGetTime());
           dbzjTzBrandResultShow.setBz(zjTzBrandResultShow.getBz());
           dbzjTzBrandResultShow.setResultUnit(zjTzBrandResultShow.getResultUnit());
           // ??????
           dbzjTzBrandResultShow.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandResultShowMapper.updateByPrimaryKey(dbzjTzBrandResultShow);
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzBrandResultShow.getResultShowId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzBrandResultShow.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzBrandResultShow.getResultShowId());
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
            return repEntity.ok("sys.data.update",zjTzBrandResultShow);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandResultShowList != null && zjTzBrandResultShowList.size() > 0) {
        	for (ZjTzBrandResultShow zjTzBrandResultShow : zjTzBrandResultShowList) {
        		if(StrUtil.equals(zjTzBrandResultShow.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "??????????????????????????????");
        		}
        	}
        	// ????????????
        	for (ZjTzBrandResultShow zjTzBrandResultShow : zjTzBrandResultShowList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzBrandResultShow.getResultShowId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzBrandResultShow zjTzBrandResultShow = new ZjTzBrandResultShow();
           zjTzBrandResultShow.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandResultShowMapper.batchDeleteUpdateZjTzBrandResultShow(zjTzBrandResultShowList, zjTzBrandResultShow);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzBrandResultShowList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzBrandResultShow dbzjTzBrandResultShow = zjTzBrandResultShowMapper.selectByPrimaryKey(zjTzBrandResultShow.getResultShowId());
		if (dbzjTzBrandResultShow != null && StrUtil.isNotEmpty(dbzjTzBrandResultShow.getResultShowId())) {
			if(StrUtil.equals(dbzjTzBrandResultShow.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
			dbzjTzBrandResultShow.setHomeShow("1");
			dbzjTzBrandResultShow.setHomeShowTime(new Date());
			dbzjTzBrandResultShow.setHomeShowStartDate(zjTzBrandResultShow.getHomeShowStartDate());
			dbzjTzBrandResultShow.setHomeShowEndDate(zjTzBrandResultShow.getHomeShowEndDate());
			dbzjTzBrandResultShow.setModifyUserInfo(userKey, realName);
			flag = zjTzBrandResultShowMapper.updateByPrimaryKey(dbzjTzBrandResultShow);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzBrandResultShow);
		}
	}

	@Override
	public ResponseEntity batchReleaseZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandResultShowList != null && zjTzBrandResultShowList.size() > 0) {
        	for (ZjTzBrandResultShow zjTzRules : zjTzBrandResultShowList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "??????????????????????????????????????????");
				}
			}
        	ZjTzBrandResultShow zjTzRules = new ZjTzBrandResultShow();
           zjTzRules.setReleaseName("??????");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzBrandResultShowMapper.batchReleaseZjTzBrandResultShow(zjTzBrandResultShowList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandResultShowList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzBrandResultShowList != null && zjTzBrandResultShowList.size() > 0) {
        	for (ZjTzBrandResultShow zjTzRules : zjTzBrandResultShowList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????");
        		}
        	}
        	ZjTzBrandResultShow zjTzRules = new ZjTzBrandResultShow();
        	zjTzRules.setReleaseName("?????????");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzBrandResultShowMapper.batchRecallZjTzBrandResultShow(zjTzBrandResultShowList, zjTzRules);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzBrandResultShowList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzBrandResultShowFile(List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// ?????????????????????????????????????????????
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzBrandResultShow total : zjTzBrandResultShowList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getResultShowId());
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
	public ResponseEntity getHomeAdvertZjTzBrandResultShow(ZjTzBrandResultShow zjTzBrandResultShow) {
		if (zjTzBrandResultShow == null) {
			zjTzBrandResultShow = new ZjTzBrandResultShow();
		}
		List<ZjTzBrandResultShow> zjTzBrandResultShowList = zjTzBrandResultShowMapper.selectZjTzHomeBrandResultShow(zjTzBrandResultShow);
		return repEntity.ok(zjTzBrandResultShowList);
	}

	@Override
	public ResponseEntity exportZjTzBrandResultShowList(ZjTzBrandResultShow zjTzBrandResultShow, HttpServletResponse response) {
		if (zjTzBrandResultShow == null) {
            zjTzBrandResultShow = new ZjTzBrandResultShow();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	
        	if(StrUtil.equals("2", ext1)) {
        		zjTzBrandResultShow.setCompanyId("");
        		zjTzBrandResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}else {
        		zjTzBrandResultShow.setCompanyId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
        	}
        	
        } else {
            // ???????????????
        	if(StrUtil.equals("all", zjTzBrandResultShow.getCompanyId(), true)) {
        		zjTzBrandResultShow.setComId("");
        		zjTzBrandResultShow.setCompanyId("");
            }else {
            	zjTzBrandResultShow.setCompanyId("");
            	zjTzBrandResultShow.setComId(zjTzPermissionService.getZjtzSysDepartmentIdType(new ZjTzPermission()));
            }
        }
        List<ZjTzBrandResultShow> zjTzBrandResultShowList = zjTzBrandResultShowMapper.exportZjTzBrandResultShowList(zjTzBrandResultShow);
     // ??????
        List<List<?>> rowsList = Lists.newArrayList();
//        List<?> row1 = CollUtil.newArrayList("????????????????????????????????????????????????");
        List<?> row2 = CollUtil.newArrayList("??????", "????????????","????????????", "????????????","??????","??????","????????????",
                "????????????","????????????");
//        rowsList.add(row1);
        rowsList.add(row2);
        
        
        // ??????????????????????????????????????????
        int i = 0;
        if(zjTzBrandResultShowList != null && zjTzBrandResultShowList.size()>0) {
            for(ZjTzBrandResultShow dbzjTzBrandResultShow:zjTzBrandResultShowList) {
                rowsList.add(CollUtil.newArrayList(
                		i = i + 1,
                		dbzjTzBrandResultShow.getManageUnit(),
                		dbzjTzBrandResultShow.getCompanyName(),
                		dbzjTzBrandResultShow.getTitle(),
                		dbzjTzBrandResultShow.getResultTypeName(),
                		dbzjTzBrandResultShow.getResultLevelId(),
                		DateUtil.format(dbzjTzBrandResultShow.getGetTime(), DatePattern.NORM_DATE_PATTERN),
                		dbzjTzBrandResultShow.getGetSubjectId(),
                		dbzjTzBrandResultShow.getResultUnit()
                        )
                );
            }

            // ????????????
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
            String fileName = "????????????????????????????????????????????????.xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //???????????????
            int index = 2;
            int index2 = 2;
          //??????????????????????????????????????????
            Map<String, List<ZjTzBrandResultShow>> companyNameMaps =
            		zjTzBrandResultShowList.stream().collect(Collectors.groupingBy(dbzjTzBrandResultShow->dbzjTzBrandResultShow.getManageUnit(),LinkedHashMap::new,Collectors.toList()));
            for (Map.Entry<String, List<ZjTzBrandResultShow>> listEntry : companyNameMaps.entrySet()) {
            	List<ZjTzBrandResultShow> companyNameList = listEntry.getValue();
            	String compnameKey = listEntry.getKey();
            	if (StrUtil.isNotBlank(compnameKey)) {
            		//?????????????????????????????????????????????
            		if (companyNameList.size() == 1) {
            			//?????????????????????
            			index = index + companyNameList.size();
            		}else {
            			//????????????
            			writer.merge(index, index + companyNameList.size() - 1, 1, 1, null, true);
            			index = index + companyNameList.size();
            			//??????????????????????????????
            		}
				}
            	Map<String, List<ZjTzBrandResultShow>> projectNameMaps =
            			companyNameList.stream().collect(Collectors.groupingBy(dbzjTzBrandResultShow->dbzjTzBrandResultShow.getCompanyName(),LinkedHashMap::new,Collectors.toList()));
            	for (Map.Entry<String, List<ZjTzBrandResultShow>> list2Entry : projectNameMaps.entrySet()) {
            		List<ZjTzBrandResultShow> projectNameList = list2Entry.getValue();
            		String projectKey = list2Entry.getKey();
            		if (StrUtil.isNotBlank(projectKey)) {
            			//?????????????????????????????????????????????
            			if(projectNameList.size() == 1){//?????????????????????
            				index2 = index2 + projectNameList.size();
            			}else {
            				//????????????
            				writer.merge(index2, index2 + projectNameList.size() - 1, 2, 2,
            						null, true);
            				index2 = index2 + projectNameList.size();
            				
            			}
            		}
            	}
            }
            writer.merge(row2.size() - 1, "????????????????????????????????????????????????");
            writer.setColumnWidth(0, 5);//??????
            writer.setColumnWidth(1, 10);//????????????
            writer.setColumnWidth(2, 60);//????????????
            writer.setColumnWidth(3, 60);//????????????
            writer.setColumnWidth(4, 12);//??????
            writer.setColumnWidth(5, 12);//??????
            writer.setColumnWidth(6, 17);//????????????
            writer.setColumnWidth(7, 12);//????????????
            writer.setColumnWidth(8, 60);//????????????
            
            writer.setRowHeight(0, 25);
            // ????????????
            StyleSet style = writer.getStyleSet();
            CellStyle cellStyle = style.getCellStyle();
            cellStyle.setWrapText(true);
            CellStyle headCellStyle = style.getHeadCellStyle();
            //????????????
            headCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headCellStyle.setWrapText(true);
            
            //??????????????????
            Font font = writer.createFont();
            //??????
            font.setBold(true);
            //????????????????????????
            font.setFontHeightInPoints((short) 16);
            
            headCellStyle.setFont(font);
            writer.setStyleSet(style);
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename="+ fileName);
//                + fileName);
//                        + "\"" + fileName + "\";");
//                new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // ???????????????????????????????????????????????????????????????
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // ??????writer???????????????
                if (writer != null) {
                    writer.close();
                }
                // ????????????????????????Servlet???
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("?????????");
        }
	}
}
