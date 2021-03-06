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
        // ????????????
        PageHelper.startPage(zjLzehManageTaskPlanItem.getPage(),zjLzehManageTaskPlanItem.getLimit());
        // ????????????
        List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem);
        // ??????????????????
        PageInfo<ZjLzehManageTaskPlanItem> p = new PageInfo<>(zjLzehManageTaskPlanItemList);

        return repEntity.okList(zjLzehManageTaskPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehManageTaskPlanItemDetail(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem) {
        if (zjLzehManageTaskPlanItem == null) {
            zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
        }
        // ????????????
        ZjLzehManageTaskPlanItem dbZjLzehManageTaskPlanItem = zjLzehManageTaskPlanItemMapper.selectByPrimaryKey(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanItemId());
        // ????????????
        if (dbZjLzehManageTaskPlanItem != null) {
            return repEntity.ok(dbZjLzehManageTaskPlanItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ????????????
           dbZjLzehManageTaskPlanItem.setMatterDescription(zjLzehManageTaskPlanItem.getMatterDescription());
           // ????????????
           dbZjLzehManageTaskPlanItem.setControlDemand(zjLzehManageTaskPlanItem.getControlDemand());
           // ??????????????????
           dbZjLzehManageTaskPlanItem.setPlanMakespan(zjLzehManageTaskPlanItem.getPlanMakespan());
           // ??????????????????
           dbZjLzehManageTaskPlanItem.setRealMakespan(zjLzehManageTaskPlanItem.getRealMakespan());
           // ????????????
           dbZjLzehManageTaskPlanItem.setDutyDepartment(zjLzehManageTaskPlanItem.getDutyDepartment());
           // ?????????
           dbZjLzehManageTaskPlanItem.setDutyPerson(zjLzehManageTaskPlanItem.getDutyPerson());
           // ????????????
           dbZjLzehManageTaskPlanItem.setManaggeLead(zjLzehManageTaskPlanItem.getManaggeLead());
           // ????????????
           dbZjLzehManageTaskPlanItem.setCoordPerson(zjLzehManageTaskPlanItem.getCoordPerson());
           // ????????????
           dbZjLzehManageTaskPlanItem.setCoordDepartment(zjLzehManageTaskPlanItem.getCoordDepartment());
           // ????????????
           dbZjLzehManageTaskPlanItem.setCompletion(zjLzehManageTaskPlanItem.getCompletion());
           // ??????????????????ID
           dbZjLzehManageTaskPlanItem.setZjLzehManageTaskPlanId(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
           // ??????
           dbZjLzehManageTaskPlanItem.setRemarks(zjLzehManageTaskPlanItem.getRemarks());
           // ??????
           dbZjLzehManageTaskPlanItem.setSort(zjLzehManageTaskPlanItem.getSort());
           // ??????
           dbZjLzehManageTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehManageTaskPlanItemMapper.updateByPrimaryKey(dbZjLzehManageTaskPlanItem);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehManageTaskPlanItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????


    /**
     * ????????????????????????????????????
     * @author suncg
     * @param zjLzehManageTaskPlanItem
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity importManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem){
        // ??????????????????
        if (zjLzehManageTaskPlanItem.getFileList() == null || zjLzehManageTaskPlanItem.getFileList().size() == 0) {
            return repEntity.layerMessage("no", "??????????????????!");
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
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setMatterDescription(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setControlDemand(json.getStr("????????????"));
                }
                if (json.getDate("??????????????????")!=null) {
                    zjLzehManageTaskPlanItem1.setPlanMakespan(json.getDate("??????????????????"));
                }
                if (json.getDate("??????????????????")!=null ) {
                    zjLzehManageTaskPlanItem1.setRealMakespan(json.getDate("??????????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setDutyDepartment(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("?????????"))) {
                    zjLzehManageTaskPlanItem1.setDutyPerson(json.getStr("?????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setManaggeLead(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setCoordPerson(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setCoordDepartment(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehManageTaskPlanItem1.setCompletion(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
                    zjLzehManageTaskPlanItem1.setCompleteDes(json.getStr("??????????????????"));
                }
                zjLzehManageTaskPlanItem1.setZjLzehManageTaskPlanId(zjLzehManageTaskPlanItem.getZjLzehManageTaskPlanId());
                zjLzehManageTaskPlanItem1.setZjLzehManageTaskPlanItemId(UuidUtil.generate());
                zjLzehManageTaskPlanItem1.setCreateUserInfo(userKey, realName);
                excelImportList.add(zjLzehManageTaskPlanItem1);
                i++;
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, "????????????????????????" + e.getMessage());
            logger.info("UuidUtil.generate()???" + excelList.size() + "??????");
            logger.info("??????????????????" + excelList);
            logger.info("?????????????????????" + excelImportList);
            logger.info("??????????????????" + i);
            return repEntity.layerMessage("no", "?????????????????????" + e.getMessage() + "???" + excelList.size() + "/" + i);
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
     * ????????????????????????????????????
     * @author zry
     * @param zjLzehManageTaskPlanItem
     * */
	@Override
	public ResponseEntity exportManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem, HttpServletResponse response) {
        if (zjLzehManageTaskPlanItem == null) {
        	zjLzehManageTaskPlanItem = new ZjLzehManageTaskPlanItem();
        }
        // ????????????
        List<ZjLzehManageTaskPlanItem> dbList = zjLzehManageTaskPlanItemMapper.selectByZjLzehManageTaskPlanItemList(zjLzehManageTaskPlanItem);
        
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????", "????????????", "??????????????????","????????????","?????????","????????????",
                "????????????","????????????","??????????????????","??????????????????","????????????");
        rowsList.add(row1);
        
        
        // ??????????????????????????????????????????
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

            // ????????????
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
//            String fileName = "xxx??????-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.setColumnWidth(0, 20);//????????????
            writer.setColumnWidth(1, 45);//????????????
            writer.setColumnWidth(2, 15);//??????????????????
            writer.setColumnWidth(3, 10);//????????????
            writer.setColumnWidth(4, 10);//?????????
            writer.setColumnWidth(5, 10);//????????????
            writer.setColumnWidth(6, 30);//????????????
            writer.setColumnWidth(7, 10);//????????????
            writer.setColumnWidth(8, 15);//??????????????????
            writer.setColumnWidth(9, 40);//??????????????????
            writer.setColumnWidth(10, 40);//????????????
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
            	response.setHeader("Content-Disposition",
                        "attachment; filename=" + new String("????????????????????????.xlsx".getBytes("utf-8"), "iso-8859-1"));
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
           return null;
        }else {
        	 List<List<?>> rows = CollUtil.newArrayList(rowsList);
             // ?????????????????????writer?????????xlsx??????
             ExcelWriter writer = ExcelUtil.getWriter(true);
             writer.setColumnWidth(0, 20);//????????????
             writer.setColumnWidth(1, 45);//????????????
             writer.setColumnWidth(2, 15);//??????????????????
             writer.setColumnWidth(3, 10);//????????????
             writer.setColumnWidth(4, 10);//?????????
             writer.setColumnWidth(5, 10);//????????????
             writer.setColumnWidth(6, 30);//????????????
             writer.setColumnWidth(7, 10);//????????????
             writer.setColumnWidth(8, 15);//??????????????????
             writer.setColumnWidth(9, 40);//??????????????????
             writer.setColumnWidth(10, 40);//????????????
             // ??????response????????????
             // response.reset();
             response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
             // out???OutputStream??????????????????????????????
             ServletOutputStream out = null;
             try {
             	response.setHeader("Content-Disposition",
                         "attachment; filename=" + new String("????????????????????????.xlsx".getBytes("utf-8"), "iso-8859-1"));
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
            return repEntity.ok("?????????");
        }

	}
}
