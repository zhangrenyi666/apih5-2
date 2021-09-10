package com.apih5.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjLzehProduceTaskPlanItemMapper;
import com.apih5.mybatis.dao.ZjLzehProduceTaskPlanMapper;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlan;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlanItem;
import com.apih5.service.ZjLzehProduceTaskPlanItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjLzehProduceTaskPlanItemService")
public class ZjLzehProduceTaskPlanItemServiceImpl implements ZjLzehProduceTaskPlanItemService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanItemMapper zjLzehProduceTaskPlanItemMapper;

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanMapper zjLzehProduceTaskPlanMapper;

    @Override
    public ResponseEntity getZjLzehProduceTaskPlanItemListByCondition(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        if (zjLzehProduceTaskPlanItem == null) {
            zjLzehProduceTaskPlanItem = new ZjLzehProduceTaskPlanItem();
        }
        // 分页查询
        PageHelper.startPage(zjLzehProduceTaskPlanItem.getPage(),zjLzehProduceTaskPlanItem.getLimit());
        // 获取数据
        List<ZjLzehProduceTaskPlanItem> zjLzehProduceTaskPlanItemList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem);
        // 得到分页信息
        PageInfo<ZjLzehProduceTaskPlanItem> p = new PageInfo<>(zjLzehProduceTaskPlanItemList);

        return repEntity.okList(zjLzehProduceTaskPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehProduceTaskPlanItemDetail(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        if (zjLzehProduceTaskPlanItem == null) {
            zjLzehProduceTaskPlanItem = new ZjLzehProduceTaskPlanItem();
        }
        // 获取数据
        ZjLzehProduceTaskPlanItem dbZjLzehProduceTaskPlanItem = zjLzehProduceTaskPlanItemMapper.selectByPrimaryKey(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanItemId());
        // 数据存在
        if (dbZjLzehProduceTaskPlanItem != null) {
            return repEntity.ok(dbZjLzehProduceTaskPlanItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehProduceTaskPlanItem.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
        zjLzehProduceTaskPlanItem.setZjLzehProduceTaskPlanItemId(UuidUtil.generate());
        zjLzehProduceTaskPlanItem.setCreateUserInfo(userKey, realName);
        int flag = zjLzehProduceTaskPlanItemMapper.insert(zjLzehProduceTaskPlanItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehProduceTaskPlanItem);
        }
    }

    @Override
    public ResponseEntity updateZjLzehProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehProduceTaskPlanItem dbZjLzehProduceTaskPlanItem = zjLzehProduceTaskPlanItemMapper.selectByPrimaryKey(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanItemId());
        if (dbZjLzehProduceTaskPlanItem != null && StrUtil.isNotEmpty(dbZjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanItemId())) {
           // 生产目标任务计划ID
           dbZjLzehProduceTaskPlanItem.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
           // 事项描述
           dbZjLzehProduceTaskPlanItem.setMatterDescription(zjLzehProduceTaskPlanItem.getMatterDescription());
           // 控制要求
           dbZjLzehProduceTaskPlanItem.setControlDemand(zjLzehProduceTaskPlanItem.getControlDemand());
           // 计划完成时间
           dbZjLzehProduceTaskPlanItem.setPlanMakespan(zjLzehProduceTaskPlanItem.getPlanMakespan());
           // 实际完成时间
           dbZjLzehProduceTaskPlanItem.setRealMakespan(zjLzehProduceTaskPlanItem.getRealMakespan());
           // 责任部门
           dbZjLzehProduceTaskPlanItem.setDutyDepartment(zjLzehProduceTaskPlanItem.getDutyDepartment());
           // 责任人
           dbZjLzehProduceTaskPlanItem.setDutyPerson(zjLzehProduceTaskPlanItem.getDutyPerson());
           // 分管领导
           dbZjLzehProduceTaskPlanItem.setManaggeLead(zjLzehProduceTaskPlanItem.getManaggeLead());
           // 配合人员
           dbZjLzehProduceTaskPlanItem.setCoordPerson(zjLzehProduceTaskPlanItem.getCoordPerson());
           // 配合部门
           dbZjLzehProduceTaskPlanItem.setCoordDepartment(zjLzehProduceTaskPlanItem.getCoordDepartment());
           // 完成情况
           dbZjLzehProduceTaskPlanItem.setCompletion(zjLzehProduceTaskPlanItem.getCompletion());
           // 备注
           dbZjLzehProduceTaskPlanItem.setRemarks(zjLzehProduceTaskPlanItem.getRemarks());
           // 排序
           dbZjLzehProduceTaskPlanItem.setSort(zjLzehProduceTaskPlanItem.getSort());
           // 共通
           dbZjLzehProduceTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehProduceTaskPlanItemMapper.updateByPrimaryKey(dbZjLzehProduceTaskPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehProduceTaskPlanItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlanItem(List<ZjLzehProduceTaskPlanItem> zjLzehProduceTaskPlanItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehProduceTaskPlanItemList != null && zjLzehProduceTaskPlanItemList.size() > 0) {
           ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem = new ZjLzehProduceTaskPlanItem();
           zjLzehProduceTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehProduceTaskPlanItemMapper.batchDeleteUpdateZjLzehProduceTaskPlanItem(zjLzehProduceTaskPlanItemList, zjLzehProduceTaskPlanItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehProduceTaskPlanItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 导入生产目标任务计划明细
     * @author suncg
     * @param zjLzehProduceTaskPlanItem
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity importProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem){
        // 上传文件为空
        if (zjLzehProduceTaskPlanItem.getFileList() == null || zjLzehProduceTaskPlanItem.getFileList().size() == 0) {
            return repEntity.layerMessage("no", "没有导入文件!");
        }

        ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem2= new ZjLzehProduceTaskPlanItem();
        zjLzehProduceTaskPlanItem2.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
        List<ZjLzehProduceTaskPlanItem> zjLzehManageTaskPlanItemList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem2);
        batchDeleteUpdateZjLzehProduceTaskPlanItem(zjLzehManageTaskPlanItemList);
        int i = 0;
        List<ZjLzehProduceTaskPlanItem> excelImportList = new ArrayList<ZjLzehProduceTaskPlanItem>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<Map<String, Object>> excelList = null;
        String path = Apih5Properties.getFilePath() + zjLzehProduceTaskPlanItem.getFileList().get(0).getRelativeUrl();
        ExcelReader reader = ExcelUtil.getReader(path);
        try {

            excelList = reader.readAll();

            for (Map<String, Object> item : excelList) {
                ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem1 = new ZjLzehProduceTaskPlanItem();
                JSONObject json = new JSONObject(item);
                if (StrUtil.isNotEmpty(json.getStr("任务事项"))) {
                    zjLzehProduceTaskPlanItem1.setMatterDescription(json.getStr("任务事项"));
                }
                if (StrUtil.isNotEmpty(json.getStr("事项说明"))) {
                    zjLzehProduceTaskPlanItem1.setControlDemand(json.getStr("事项说明"));
                }
                if (json.getDate("计划完成时间")!=null) {
                    zjLzehProduceTaskPlanItem1.setPlanMakespan(json.getDate("计划完成时间"));
                }
                if (json.getDate("实际完成时间")!=null ) {
                    zjLzehProduceTaskPlanItem1.setRealMakespan(json.getDate("实际完成时间"));
                }
                if (StrUtil.isNotEmpty(json.getStr("责任部门"))) {
                    zjLzehProduceTaskPlanItem1.setDutyDepartment(json.getStr("责任部门"));
                }
                if (StrUtil.isNotEmpty(json.getStr("责任人"))) {
                    zjLzehProduceTaskPlanItem1.setDutyPerson(json.getStr("责任人"));
                }
                if (StrUtil.isNotEmpty(json.getStr("分管领导"))) {
                    zjLzehProduceTaskPlanItem1.setManaggeLead(json.getStr("分管领导"));
                }
                if (StrUtil.isNotEmpty(json.getStr("配合人员"))) {
                    zjLzehProduceTaskPlanItem1.setCoordPerson(json.getStr("配合人员"));
                }
                if (StrUtil.isNotEmpty(json.getStr("配合部门"))) {
                    zjLzehProduceTaskPlanItem1.setCoordDepartment(json.getStr("配合部门"));
                }
                if (StrUtil.isNotEmpty(json.getStr("完成情况"))) {
                    zjLzehProduceTaskPlanItem1.setCompletion(json.getStr("完成情况"));
                }
                if (StrUtil.isNotEmpty(json.getStr("完成情况说明"))) {
                    zjLzehProduceTaskPlanItem1.setCompleteDes(json.getStr("完成情况说明"));
                }
                zjLzehProduceTaskPlanItem1.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
                zjLzehProduceTaskPlanItem1.setZjLzehProduceTaskPlanItemId(UuidUtil.generate());
                zjLzehProduceTaskPlanItem1.setCreateUserInfo(userKey, realName);
                excelImportList.add(zjLzehProduceTaskPlanItem1);
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
        int flag = zjLzehProduceTaskPlanItemMapper.insertProduceTaskPlanItemList(excelImportList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            List<ZjLzehProduceTaskPlanItem> sumList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem2);
            ZjLzehProduceTaskPlan zjLzehProduceTaskPlan = zjLzehProduceTaskPlanMapper.selectByPrimaryKey(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
            zjLzehProduceTaskPlan.setTaskQty(sumList.size());
            zjLzehProduceTaskPlanMapper.updateByPrimaryKey(zjLzehProduceTaskPlan);
            return repEntity.ok("sys.data.sava", excelImportList);
        }

    }

	@Override
	public ResponseEntity exportProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem,
			HttpServletResponse response) {

        if (zjLzehProduceTaskPlanItem == null) {
        	zjLzehProduceTaskPlanItem = new ZjLzehProduceTaskPlanItem();
        }
        // 获取数据
        List<ZjLzehProduceTaskPlanItem> dbList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem);
        
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("任务事项", "事项说明", "计划完成时间","责任部门","责任人","分管领导",
                "配合人员","配合部门","实际完成时间","完成情况说明","完成情况");
        rowsList.add(row1);
        
        
        // 数据装载（与上面的表头对应）
        if(dbList != null && dbList.size()>0) {
            for(ZjLzehProduceTaskPlanItem dbzjLzehProduceTaskPlanItem:dbList) {
                rowsList.add(CollUtil.newArrayList(
                		dbzjLzehProduceTaskPlanItem.getMatterDescription(),
                		dbzjLzehProduceTaskPlanItem.getControlDemand(),
                		DateUtil.format(dbzjLzehProduceTaskPlanItem.getPlanMakespan(), DatePattern.NORM_DATE_PATTERN),
                		dbzjLzehProduceTaskPlanItem.getDutyDepartment(),
                		dbzjLzehProduceTaskPlanItem.getDutyPerson(),
                		dbzjLzehProduceTaskPlanItem.getManaggeLead(),
                		dbzjLzehProduceTaskPlanItem.getCoordPerson(),
                		dbzjLzehProduceTaskPlanItem.getCoordDepartment(),
                		DateUtil.format(dbzjLzehProduceTaskPlanItem.getRealMakespan(), DatePattern.NORM_DATE_PATTERN),
                		dbzjLzehProduceTaskPlanItem.getCompleteDes(),
                		dbzjLzehProduceTaskPlanItem.getCompletion()
                        )
                );
            }

            // 报表名称
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
//            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            response.setHeader("Content-Disposition", "attachment; filename=" + URLUtil.encode("云电商分包定标数据表.xlsx"));
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
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
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + new String("生产目标任务计划.xlsx".getBytes("utf-8"), "iso-8859-1"));
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
        } else {
            return repEntity.ok("无数据");
        }

	
	}
}
