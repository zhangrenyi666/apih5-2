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
        // 分页查询
        PageHelper.startPage(zjTzProEvent.getPage(),zjTzProEvent.getLimit());
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            	zjTzProEvent.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzProEvent> zjTzProEventList = zjTzProEventMapper.selectByZjTzProEventList(zjTzProEvent);
        for (ZjTzProEvent zjTzProEvent2 : zjTzProEventList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzProEvent2.getProEventId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzProEvent2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzProEvent> p = new PageInfo<>(zjTzProEventList);

        return repEntity.okList(zjTzProEventList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProEventDetails(ZjTzProEvent zjTzProEvent) {
        if (zjTzProEvent == null) {
            zjTzProEvent = new ZjTzProEvent();
        }
        // 获取数据
        ZjTzProEvent dbZjTzProEvent = zjTzProEventMapper.selectByPrimaryKey(zjTzProEvent.getProEventId());
        // 数据存在
        if (dbZjTzProEvent != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzProEvent.getProEventId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzProEvent.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzProEvent);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
        // 附件list
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
//           // 项目id
//           dbzjTzProEvent.setProjectId(zjTzProEvent.getProjectId());
//           // 项目name
//           dbzjTzProEvent.setProjectName(zjTzProEvent.getProjectName());
           // 标题
           dbzjTzProEvent.setTitle(zjTzProEvent.getTitle());
           // 主要内容
           dbzjTzProEvent.setContent(zjTzProEvent.getContent());
//           // 登记日期
//           dbzjTzProEvent.setRegisterDate(zjTzProEvent.getRegisterDate());
//           // 登记用户
//           dbzjTzProEvent.setRegisterPerson(zjTzProEvent.getRegisterPerson());
           dbzjTzProEvent.setHappenTime(zjTzProEvent.getHappenTime());
           // 共通
           dbzjTzProEvent.setModifyUserInfo(userKey, realName);
           flag = zjTzProEventMapper.updateByPrimaryKey(dbzjTzProEvent);
           // 删除附件再新增
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
        // 失败
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
        	// 删除附件
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
        // 失败
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
    			return repEntity.layerMessage("no", "已在广而告之中的数据不可再进行广而告之");
    		}
			dbzjTzProEvent.setHomeShow("1");
			dbzjTzProEvent.setModifyUserInfo(userKey, realName);
			flag = zjTzProEventMapper.updateByPrimaryKey(dbzjTzProEvent);
		}
		// 失败
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
        			return repEntity.layerMessage("no", "未在广而告之中的数据不可撤回");
        		}
        	}
        	ZjTzProEvent zjTzProEvent = new ZjTzProEvent();
        	zjTzProEvent.setHomeShow("0");
        	zjTzProEvent.setModifyUserInfo(userKey, realName); 
        	flag = zjTzProEventMapper.batchRecallZjTzProEvent(zjTzProEventList, zjTzProEvent);
        }
        // 失败
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
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            	zjTzProEvent.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzProEvent.getProjectId(), true)) {
            	zjTzProEvent.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzProEvent> zjTzProEventList = zjTzProEventMapper.exportZjTzProEventList(zjTzProEvent);
     // 表头
        List<List<?>> rowsList = Lists.newArrayList();
//        List<?> row1 = CollUtil.newArrayList("一公局集团投资项目获奖情况统计表");
        List<?> row2 = CollUtil.newArrayList("序号", "管理单位", "项目名称", "标题","发生时间","主要内容");
//        rowsList.add(row1);
        rowsList.add(row2);
        
        
        // 数据装载（与上面的表头对应）
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

            // 报表名称
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "一公局集团投资项目大事记统计表.xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
         // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //定义启始行
            int index = 2;
            int index2 = 2;
          //按照管理单位分组数据汇总处理
            Map<String, List<ZjTzProEvent>> companyNameMaps =
            		zjTzProEventList.stream().collect(Collectors.groupingBy(dbzjTzProEvent->dbzjTzProEvent.getManageUnit(),LinkedHashMap::new,Collectors.toList()));
            for (Map.Entry<String, List<ZjTzProEvent>> listEntry : companyNameMaps.entrySet()) {
            	List<ZjTzProEvent> companyNameList = listEntry.getValue();
            		//根据数据条数设置合并单元格信息
            		if (companyNameList.size() == 1) {
            			//一条数据不合并
            			index = index + companyNameList.size();
            		}else {
            			//规则编写
            			writer.merge(index, index + companyNameList.size() - 1, 1, 1, null, true);
            			index = index + companyNameList.size();
            			//按照项目名称进行分组
            		}
            }
            Map<String, List<ZjTzProEvent>> projectNameMaps =
            		zjTzProEventList.stream().collect(Collectors.groupingBy(dbzjTzProEvent->dbzjTzProEvent.getProjectName(),LinkedHashMap::new,Collectors.toList()));
            for (Map.Entry<String, List<ZjTzProEvent>> list2Entry : projectNameMaps.entrySet()) {
            	List<ZjTzProEvent> projectNameList = list2Entry.getValue();
            	//根据数据条数设置合并单元格信息
            	if(projectNameList.size() == 1){//一条数据不合并
            		index2 = index2 + projectNameList.size();
            	}else {
            		//规则编写
            		writer.merge(index2, index2 + projectNameList.size() - 1, 2, 2,
            				null, true);
            		index2 = index2 + projectNameList.size();
            		
            	}
            }
            writer.merge(row2.size() - 1, "一公局集团投资项目大事记统计表");
            writer.setColumnWidth(0, 5);//序号
            writer.setColumnWidth(1, 10);//管理单位
            writer.setColumnWidth(2, 60);//项目名称
            writer.setColumnWidth(3, 60);//标题
            writer.setColumnWidth(4, 12);//发生时间
            writer.setColumnWidth(5, 60);//主要内容
            
            writer.setRowHeight(0, 25);
            // 设置样式
            StyleSet style = writer.getStyleSet();
            CellStyle cellStyle = style.getCellStyle();
            cellStyle.setWrapText(true);
            CellStyle headCellStyle = style.getHeadCellStyle();
            //水平居中
            headCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headCellStyle.setWrapText(true);
            
            //设置内容字体
            Font font = writer.createFont();
            //加粗
            font.setBold(true);
            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            
            headCellStyle.setFont(font);
            writer.setStyleSet(style);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + fileName);
//                        		new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("无数据");
        }
	}

}
