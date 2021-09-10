package com.apih5.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZjLzehManageTaskPlanMapper;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlan;
import com.apih5.service.ZjLzehManageTaskPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehManageTaskPlanItemMapper;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlanItem;
import com.apih5.service.ZjLzehManageTaskPlanItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zjLzehManageTaskPlanItemService")
public class ZjLzehManageTaskPlanItemServiceImpl implements ZjLzehManageTaskPlanItemService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanItemMapper zjLzehManageTaskPlanItemMapper;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanService zjLzehManageTaskPlanService;

    @Autowired(required = true)
    private ZjLzehManageTaskPlanMapper zjLzehManageTaskPlanMapper;

    @Override
    public ResponseEntity getZjLzehManageTaskPlanItemListByCondition(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        if (zjLzehManageTaskPlanItem == null) {
            zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
        }
        // 分页查询
        PageHelper.startPage(zjLzehManageTaskPlanItem.getPage(),zjLzehManageTaskPlanItem.getLimit());
        // 获取数据
        List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem);
        // 得到分页信息
        PageInfo<ZjLzehManageTaskPlanItem> p = new PageInfo<>(zjLzehManageTaskPlanItemList);

        return repEntity.okList(zjLzehManageTaskPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehManageTaskPlanItemDetail(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        if (zjLzehManageTaskPlanItem == null) {
            zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
        }
        // 获取数据
        ZjLzehManageTaskPlanItem dbZjLzehManageTaskPlanItem = zjLzehManageTaskPlanItemMapper.selectByPrimaryKey(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanItemId());
        // 数据存在
        if (dbZjLzehManageTaskPlanItem != null) {
            return repEntity.ok(dbZjLzehManageTaskPlanItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehManageTaskPlanItem.setZjLzehManageTaskPlanItemId(UuidUtil.generate());
        zjLzehManageTaskPlanItem.setCreateUserInfo(userKey, realName);
        int flag = zjLzehManageTaskPlanItemMapper.insert(zjLzehManageTaskPlanItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehManageTaskPlanItem);
        }
    }

    @Override
    public ResponseEntity updateZjLzehManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehManageTaskPlanItem dbZjLzehManageTaskPlanItem = zjLzehManageTaskPlanItemMapper.selectByPrimaryKey(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanItemId());
        if (dbZjLzehManageTaskPlanItem != null && StrUtil.isNotEmpty(dbZjLzehManageTaskPlanItem.getZjLzehManageTaskPlanItemId())) {
           // 事项描述
           dbZjLzehManageTaskPlanItem.setMatterDescription(zjLzehManageTaskPlanItem.getMatterDescription());
           // 控制要求
           dbZjLzehManageTaskPlanItem.setControlDemand(zjLzehManageTaskPlanItem.getControlDemand());
           // 计划完成时间
           dbZjLzehManageTaskPlanItem.setPlanMakespan(zjLzehManageTaskPlanItem.getPlanMakespan());
           // 实际完成时间
           dbZjLzehManageTaskPlanItem.setRealMakespan(zjLzehManageTaskPlanItem.getRealMakespan());
           // 责任部门
           dbZjLzehManageTaskPlanItem.setDutyDepartment(zjLzehManageTaskPlanItem.getDutyDepartment());
           // 责任人
           dbZjLzehManageTaskPlanItem.setDutyPerson(zjLzehManageTaskPlanItem.getDutyPerson());
           // 分管领导
           dbZjLzehManageTaskPlanItem.setManaggeLead(zjLzehManageTaskPlanItem.getManaggeLead());
           // 配合人员
           dbZjLzehManageTaskPlanItem.setCoordPerson(zjLzehManageTaskPlanItem.getCoordPerson());
           // 配合部门
           dbZjLzehManageTaskPlanItem.setCoordDepartment(zjLzehManageTaskPlanItem.getCoordDepartment());
           // 完成情况
           dbZjLzehManageTaskPlanItem.setCompletion(zjLzehManageTaskPlanItem.getCompletion());
           // 经营目标计划ID
           dbZjLzehManageTaskPlanItem.setZjLzehManageTaskPlanId(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
           // 备注
           dbZjLzehManageTaskPlanItem.setRemarks(zjLzehManageTaskPlanItem.getRemarks());
           // 排序
           dbZjLzehManageTaskPlanItem.setSort(zjLzehManageTaskPlanItem.getSort());
           // 共通
           dbZjLzehManageTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehManageTaskPlanItemMapper.updateByPrimaryKey(dbZjLzehManageTaskPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehManageTaskPlanItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlanItem(List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehManageTaskPlanItemList != null && zjLzehManageTaskPlanItemList.size() > 0) {
           ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
           zjLzehManageTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehManageTaskPlanItemMapper.batchDeleteUpdateZjLzehManageTaskPlanItem(zjLzehManageTaskPlanItemList, zjLzehManageTaskPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehManageTaskPlanItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    /**
     * 导入经营目标任务计划明细
     * @author suncg
     * @param zjLzehManageTaskPlanItem
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity importManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem){
        // 上传文件为空
        if (zjLzehManageTaskPlanItem.getFileList() == null || zjLzehManageTaskPlanItem.getFileList().size() == 0) {
            return repEntity.layerMessage("no", "没有导入文件!");
        }

        ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem2= new ZjLzehManageTaskPlanItem();
        zjLzehManageTaskPlanItem2.setZjLzehManageTaskPlanId(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
        List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem2);
        batchDeleteUpdateZjLzehManageTaskPlanItem(zjLzehManageTaskPlanItemList);
        int i = 0;
        List<ZjLzehManageTaskPlanItem> excelImportList = new ArrayList<ZjLzehManageTaskPlanItem>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<Map<String, Object>> excelList = null;
        String path = Apih5Properties.getFilePath() + zjLzehManageTaskPlanItem.getFileList().get(0).getRelativeUrl();
        ExcelReader reader = ExcelUtil.getReader(path);
        try {

            excelList = reader.readAll();

            for (Map<String, Object> item : excelList) {
                ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem1 = new ZjLzehManageTaskPlanItem();
                JSONObject json = new JSONObject(item);
                if (StrUtil.isNotEmpty(json.getStr("任务事项"))) {
                    zjLzehManageTaskPlanItem1.setMatterDescription(json.getStr("任务事项"));
                }
                if (StrUtil.isNotEmpty(json.getStr("事项说明"))) {
                    zjLzehManageTaskPlanItem1.setControlDemand(json.getStr("事项说明"));
                }
                if (json.getDate("计划完成时间")!=null) {
                    zjLzehManageTaskPlanItem1.setPlanMakespan(json.getDate("计划完成时间"));
                }
                if (json.getDate("实际完成时间")!=null ) {
                    zjLzehManageTaskPlanItem1.setRealMakespan(json.getDate("实际完成时间"));
                }
                if (StrUtil.isNotEmpty(json.getStr("责任部门"))) {
                    zjLzehManageTaskPlanItem1.setDutyDepartment(json.getStr("责任部门"));
                }
                if (StrUtil.isNotEmpty(json.getStr("责任人"))) {
                    zjLzehManageTaskPlanItem1.setDutyPerson(json.getStr("责任人"));
                }
                if (StrUtil.isNotEmpty(json.getStr("分管领导"))) {
                    zjLzehManageTaskPlanItem1.setManaggeLead(json.getStr("分管领导"));
                }
                if (StrUtil.isNotEmpty(json.getStr("配合人员"))) {
                    zjLzehManageTaskPlanItem1.setCoordPerson(json.getStr("配合人员"));
                }
                if (StrUtil.isNotEmpty(json.getStr("配合部门"))) {
                    zjLzehManageTaskPlanItem1.setCoordDepartment(json.getStr("配合部门"));
                }
                if (StrUtil.isNotEmpty(json.getStr("完成情况"))) {
                    zjLzehManageTaskPlanItem1.setCompletion(json.getStr("完成情况"));
                }
                if (StrUtil.isNotEmpty(json.getStr("完成情况说明"))) {
                    zjLzehManageTaskPlanItem1.setCompleteDes(json.getStr("完成情况说明"));
                }
                zjLzehManageTaskPlanItem1.setZjLzehManageTaskPlanId(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
                zjLzehManageTaskPlanItem1.setZjLzehManageTaskPlanItemId(UuidUtil.generate());
                zjLzehManageTaskPlanItem1.setCreateUserInfo(userKey, realName);
                excelImportList.add(zjLzehManageTaskPlanItem1);
                i++;
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, "导入（存储）失败" + e.getMessage());
            logger.info("UuidUtil.generate()【" + excelList.size() + "】行");
            logger.info("导入的数据：" + excelList);
            logger.info("待存储的数据：" + excelImportList);
            logger.info("待存储行数：" + i);
            return repEntity.layerMessage("no", "失败！错误数据" + e.getMessage() + "行" + excelList.size() + "/" + i);
        } finally {
            reader.close();
        }
        int flag = zjLzehManageTaskPlanItemMapper.insertManageTaskPlanItemList(excelImportList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            List<ZjLzehManageTaskPlanItem> sumList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem2);
            ZjLzehManageTaskPlan zjLzehManageTaskPlan = zjLzehManageTaskPlanMapper.selectByPrimaryKey(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
            zjLzehManageTaskPlan.setTaskQty(sumList.size());
            zjLzehManageTaskPlanMapper.updateByPrimaryKey(zjLzehManageTaskPlan);
            return repEntity.ok("sys.data.sava", excelImportList);
        }

    }
    
    /**
     * 导出经营目标任务计划明细
     * @author zry
     * @param zjLzehManageTaskPlanItem
     * */
	@Override
	public ResponseEntity exportManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem, HttpServletResponse response) {
        if (zjLzehManageTaskPlanItem == null) {
        	zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
        }
        // 获取数据
        List<ZjLzehManageTaskPlanItem> dbList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem);
        
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("任务事项", "事项说明", "计划完成时间","责任部门","责任人","分管领导",
                "配合人员","配合部门","实际完成时间","完成情况说明","完成情况");
        rowsList.add(row1);
        
        
        // 数据装载（与上面的表头对应）
        if(dbList != null && dbList.size()>0) {
            for(ZjLzehManageTaskPlanItem dbzjLzehManageTaskPlanItem:dbList) {
                rowsList.add(CollUtil.newArrayList(
                		dbzjLzehManageTaskPlanItem.getMatterDescription(),
                		dbzjLzehManageTaskPlanItem.getControlDemand(),
                		DateUtil.format(dbzjLzehManageTaskPlanItem.getPlanMakespan(), DatePattern.NORM_DATE_PATTERN),
                		dbzjLzehManageTaskPlanItem.getDutyDepartment(),
                		dbzjLzehManageTaskPlanItem.getDutyPerson(),
                		dbzjLzehManageTaskPlanItem.getManaggeLead(),
                		dbzjLzehManageTaskPlanItem.getCoordPerson(),
                		dbzjLzehManageTaskPlanItem.getCoordDepartment(),
                		DateUtil.format(dbzjLzehManageTaskPlanItem.getRealMakespan(), DatePattern.NORM_DATE_PATTERN),
                		dbzjLzehManageTaskPlanItem.getCompleteDes(),
                		dbzjLzehManageTaskPlanItem.getCompletion()
                        )
                );
            }

            // 报表名称
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
//            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.setColumnWidth(0, 20);//任务事项
            writer.setColumnWidth(1, 45);//事项说明
            writer.setColumnWidth(2, 15);//计划完成时间
            writer.setColumnWidth(3, 10);//责任部门
            writer.setColumnWidth(4, 10);//责任人
            writer.setColumnWidth(5, 10);//分管领导
            writer.setColumnWidth(6, 30);//配合人员
            writer.setColumnWidth(7, 10);//配合部门
            writer.setColumnWidth(8, 15);//实际完成时间
            writer.setColumnWidth(9, 40);//完成情况说明
            writer.setColumnWidth(10, 40);//完成情况
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
            	response.setHeader("Content-Disposition",
                        "attachment; filename=" + new String("经营目标任务计划.xlsx".getBytes("utf-8"), "iso-8859-1"));
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
           return null;
        }else {
        	 List<List<?>> rows = CollUtil.newArrayList(rowsList);
             // 通过工具类创建writer，创建xlsx格式
             ExcelWriter writer = ExcelUtil.getWriter(true);
             writer.setColumnWidth(0, 20);//任务事项
             writer.setColumnWidth(1, 45);//事项说明
             writer.setColumnWidth(2, 15);//计划完成时间
             writer.setColumnWidth(3, 10);//责任部门
             writer.setColumnWidth(4, 10);//责任人
             writer.setColumnWidth(5, 10);//分管领导
             writer.setColumnWidth(6, 30);//配合人员
             writer.setColumnWidth(7, 10);//配合部门
             writer.setColumnWidth(8, 15);//实际完成时间
             writer.setColumnWidth(9, 40);//完成情况说明
             writer.setColumnWidth(10, 40);//完成情况
             // 设置response下载弹窗
             // response.reset();
             response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
             // out为OutputStream，需要写出到的目标流
             ServletOutputStream out = null;
             try {
             	response.setHeader("Content-Disposition",
                         "attachment; filename=" + new String("经营目标任务计划.xlsx".getBytes("utf-8"), "iso-8859-1"));
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
            return repEntity.ok("无数据");
        }

	}
}
