package com.apih5.service.impl;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProEventMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzBrandResultShow;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProEvent;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzProEventService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;

@Service("zjTzProEventService")
public class ZjTzProEventServiceImpl implements ZjTzProEventService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProEventMapper zjTzProEventMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzProEventListByCondition(ZjTzProEvent zjTzProEvent) {
        if (zjTzProEvent == null) {
            zjTzProEvent = new ZjTzProEvent();
        }
        // ????????????
        PageHelper.startPage(zjTzProEvent.getPage(),zjTzProEvent.getLimit());
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            	zjTzProEvent.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzProEvent> zjTzProEventList = zjTzProEventMapper.selectByZjTzProEventList(zjTzProEvent);
        for (ZjTzProEvent zjTzProEvent2 : zjTzProEventList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzProEvent2.getProEventId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzProEvent2.setZjTzFileList(zjTzFileList);
		}
        // ??????????????????
        PageInfo<ZjTzProEvent> p = new PageInfo<>(zjTzProEventList);

        return repEntity.okList(zjTzProEventList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProEventDetails(ZjTzProEvent zjTzProEvent) {
        if (zjTzProEvent == null) {
            zjTzProEvent = new ZjTzProEvent();
        }
        // ????????????
        ZjTzProEvent dbZjTzProEvent = zjTzProEventMapper.selectByPrimaryKey(zjTzProEvent.getProEventId());
        // ????????????
        if (dbZjTzProEvent != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzProEvent.getProEventId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProEvent.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzProEvent);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzProEvent(ZjTzProEvent zjTzProEvent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzProEvent.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzProEvent.setProjectName(manage.getProjectName());
        }
        zjTzProEvent.setHomeShow("0");
        zjTzProEvent.setProEventId(UuidUtil.generate());
        zjTzProEvent.setCreateUserInfo(userKey, realName);
        int flag = zjTzProEventMapper.insert(zjTzProEvent);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzProEvent.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzProEvent.getProEventId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProEvent);
        }
    }

    @Override
    public ResponseEntity updateZjTzProEvent(ZjTzProEvent zjTzProEvent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProEvent dbzjTzProEvent = zjTzProEventMapper.selectByPrimaryKey(zjTzProEvent.getProEventId());
        if (dbzjTzProEvent != null && StrUtil.isNotEmpty(dbzjTzProEvent.getProEventId())) {
//           // ??????id
//           dbzjTzProEvent.setProjectId(zjTzProEvent.getProjectId());
//           // ??????name
//           dbzjTzProEvent.setProjectName(zjTzProEvent.getProjectName());
           // ??????
           dbzjTzProEvent.setTitle(zjTzProEvent.getTitle());
           // ????????????
           dbzjTzProEvent.setContent(zjTzProEvent.getContent());
//           // ????????????
//           dbzjTzProEvent.setRegisterDate(zjTzProEvent.getRegisterDate());
//           // ????????????
//           dbzjTzProEvent.setRegisterPerson(zjTzProEvent.getRegisterPerson());
           dbzjTzProEvent.setHappenTime(zjTzProEvent.getHappenTime());
           // ??????
           dbzjTzProEvent.setModifyUserInfo(userKey, realName);
           flag = zjTzProEventMapper.updateByPrimaryKey(dbzjTzProEvent);
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzProEvent.getProEventId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzProEvent.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzProEvent.getProEventId());
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
            return repEntity.ok("sys.data.update",zjTzProEvent);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProEvent(List<ZjTzProEvent> zjTzProEventList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProEventList != null && zjTzProEventList.size() > 0) {
        	// ????????????
        	for (ZjTzProEvent zjTzProEvent : zjTzProEventList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzProEvent.getProEventId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzProEvent zjTzProEvent = new ZjTzProEvent();
           zjTzProEvent.setModifyUserInfo(userKey, realName);
           flag = zjTzProEventMapper.batchDeleteUpdateZjTzProEvent(zjTzProEventList, zjTzProEvent);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProEventList);
        }
    }

	@Override
	public ResponseEntity toHomeShowZjTzProEvent(ZjTzProEvent zjTzProEvent) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzProEvent dbzjTzProEvent = zjTzProEventMapper.selectByPrimaryKey(zjTzProEvent.getProEventId());
		if (dbzjTzProEvent != null && StrUtil.isNotEmpty(dbzjTzProEvent.getProEventId())) {
			if(StrUtil.equals(dbzjTzProEvent.getHomeShow(), "1")) {
    			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
    		}
			dbzjTzProEvent.setHomeShow("1");
			dbzjTzProEvent.setModifyUserInfo(userKey, realName);
			flag = zjTzProEventMapper.updateByPrimaryKey(dbzjTzProEvent);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",dbzjTzProEvent);
		}
	
	}

	@Override
	public ResponseEntity batchRecallZjTzProEvent(List<ZjTzProEvent> zjTzProEventList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProEventList != null && zjTzProEventList.size() > 0) {
        	for (ZjTzProEvent dbzjTzProEvent : zjTzProEventList) {
        		if(StrUtil.equals(dbzjTzProEvent.getHomeShow(), "0")) {
        			return repEntity.layerMessage("no", "??????????????????????????????????????????");
        		}
        	}
        	ZjTzProEvent zjTzProEvent = new ZjTzProEvent();
        	zjTzProEvent.setHomeShow("0");
        	zjTzProEvent.setModifyUserInfo(userKey, realName); 
        	flag = zjTzProEventMapper.batchRecallZjTzProEvent(zjTzProEventList, zjTzProEvent);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProEventList);
        }
	
	}

	@Override
	public ResponseEntity exportZjTzProEventList(ZjTzProEvent zjTzProEvent, HttpServletResponse response) {
		if (zjTzProEvent == null) {
            zjTzProEvent = new ZjTzProEvent();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            	zjTzProEvent.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            }
        }
        // ????????????
        List<ZjTzProEvent> zjTzProEventList = zjTzProEventMapper.exportZjTzProEventList(zjTzProEvent);
     // ??????
        List<List<?>> rowsList = Lists.newArrayList();
//        List<?> row1 = CollUtil.newArrayList("????????????????????????????????????????????????");
        List<?> row2 = CollUtil.newArrayList("??????", "????????????", "????????????", "??????","????????????","????????????");
//        rowsList.add(row1);
        rowsList.add(row2);
        
        
        // ??????????????????????????????????????????
        int i = 0;
        if(zjTzProEventList != null && zjTzProEventList.size()>0) {
            for(ZjTzProEvent dbzjTzProEvent:zjTzProEventList) {
                rowsList.add(CollUtil.newArrayList(
                		i = i + 1,
                		dbzjTzProEvent.getManageUnit(),
        				dbzjTzProEvent.getProjectName(),
        				dbzjTzProEvent.getTitle(),
                		DateUtil.format(dbzjTzProEvent.getHappenTime(), DatePattern.NORM_DATE_PATTERN),
                		dbzjTzProEvent.getContent().replaceAll("&nbsp;", "")
                        )
                );
            }

            // ????????????
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
            String fileName = "?????????????????????????????????????????????.xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
         // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //???????????????
            int index = 2;
            int index2 = 2;
          //??????????????????????????????????????????
            Map<String, List<ZjTzProEvent>> companyNameMaps =
            		zjTzProEventList.stream().collect(Collectors.groupingBy(dbzjTzProEvent->dbzjTzProEvent.getManageUnit(),LinkedHashMap::new,Collectors.toList()));
            for (Map.Entry<String, List<ZjTzProEvent>> listEntry : companyNameMaps.entrySet()) {
            	List<ZjTzProEvent> companyNameList = listEntry.getValue();
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
            Map<String, List<ZjTzProEvent>> projectNameMaps =
            		zjTzProEventList.stream().collect(Collectors.groupingBy(dbzjTzProEvent->dbzjTzProEvent.getProjectName(),LinkedHashMap::new,Collectors.toList()));
            for (Map.Entry<String, List<ZjTzProEvent>> list2Entry : projectNameMaps.entrySet()) {
            	List<ZjTzProEvent> projectNameList = list2Entry.getValue();
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
            writer.merge(row2.size() - 1, "?????????????????????????????????????????????");
            writer.setColumnWidth(0, 5);//??????
            writer.setColumnWidth(1, 10);//????????????
            writer.setColumnWidth(2, 60);//????????????
            writer.setColumnWidth(3, 60);//??????
            writer.setColumnWidth(4, 12);//????????????
            writer.setColumnWidth(5, 60);//????????????
            
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
                        "attachment; filename=" + fileName);
//                        		new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
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
