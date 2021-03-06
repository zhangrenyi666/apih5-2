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
        // ????????????
        PageHelper.startPage(zjLzehProduceTaskPlanItem.getPage(),zjLzehProduceTaskPlanItem.getLimit());
        // ????????????
        List<ZjLzehProduceTaskPlanItem> zjLzehProduceTaskPlanItemList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem);
        // ??????????????????
        PageInfo<ZjLzehProduceTaskPlanItem> p = new PageInfo<>(zjLzehProduceTaskPlanItemList);

        return repEntity.okList(zjLzehProduceTaskPlanItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehProduceTaskPlanItemDetail(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem) {
        if (zjLzehProduceTaskPlanItem == null) {
            zjLzehProduceTaskPlanItem = new ZjLzehProduceTaskPlanItem();
        }
        // ????????????
        ZjLzehProduceTaskPlanItem dbZjLzehProduceTaskPlanItem = zjLzehProduceTaskPlanItemMapper.selectByPrimaryKey(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanItemId());
        // ????????????
        if (dbZjLzehProduceTaskPlanItem != null) {
            return repEntity.ok(dbZjLzehProduceTaskPlanItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ????????????????????????ID
           dbZjLzehProduceTaskPlanItem.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setMatterDescription(zjLzehProduceTaskPlanItem.getMatterDescription());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setControlDemand(zjLzehProduceTaskPlanItem.getControlDemand());
           // ??????????????????
           dbZjLzehProduceTaskPlanItem.setPlanMakespan(zjLzehProduceTaskPlanItem.getPlanMakespan());
           // ??????????????????
           dbZjLzehProduceTaskPlanItem.setRealMakespan(zjLzehProduceTaskPlanItem.getRealMakespan());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setDutyDepartment(zjLzehProduceTaskPlanItem.getDutyDepartment());
           // ?????????
           dbZjLzehProduceTaskPlanItem.setDutyPerson(zjLzehProduceTaskPlanItem.getDutyPerson());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setManaggeLead(zjLzehProduceTaskPlanItem.getManaggeLead());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setCoordPerson(zjLzehProduceTaskPlanItem.getCoordPerson());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setCoordDepartment(zjLzehProduceTaskPlanItem.getCoordDepartment());
           // ????????????
           dbZjLzehProduceTaskPlanItem.setCompletion(zjLzehProduceTaskPlanItem.getCompletion());
           // ??????
           dbZjLzehProduceTaskPlanItem.setRemarks(zjLzehProduceTaskPlanItem.getRemarks());
           // ??????
           dbZjLzehProduceTaskPlanItem.setSort(zjLzehProduceTaskPlanItem.getSort());
           // ??????
           dbZjLzehProduceTaskPlanItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehProduceTaskPlanItemMapper.updateByPrimaryKey(dbZjLzehProduceTaskPlanItem);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehProduceTaskPlanItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    /**
     * ????????????????????????????????????
     * @author suncg
     * @param zjLzehProduceTaskPlanItem
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity importProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem){
        // ??????????????????
        if (zjLzehProduceTaskPlanItem.getFileList() == null || zjLzehProduceTaskPlanItem.getFileList().size() == 0) {
            return repEntity.layerMessage("no", "??????????????????!");
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
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setMatterDescription(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setControlDemand(json.getStr("????????????"));
                }
                if (json.getDate("??????????????????")!=null) {
                    zjLzehProduceTaskPlanItem1.setPlanMakespan(json.getDate("??????????????????"));
                }
                if (json.getDate("??????????????????")!=null ) {
                    zjLzehProduceTaskPlanItem1.setRealMakespan(json.getDate("??????????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setDutyDepartment(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("?????????"))) {
                    zjLzehProduceTaskPlanItem1.setDutyPerson(json.getStr("?????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setManaggeLead(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setCoordPerson(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setCoordDepartment(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("????????????"))) {
                    zjLzehProduceTaskPlanItem1.setCompletion(json.getStr("????????????"));
                }
                if (StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
                    zjLzehProduceTaskPlanItem1.setCompleteDes(json.getStr("??????????????????"));
                }
                zjLzehProduceTaskPlanItem1.setZjLzehProduceTaskPlanId(zjLzehProduceTaskPlanItem.getZjLzehProduceTaskPlanId());
                zjLzehProduceTaskPlanItem1.setZjLzehProduceTaskPlanItemId(UuidUtil.generate());
                zjLzehProduceTaskPlanItem1.setCreateUserInfo(userKey, realName);
                excelImportList.add(zjLzehProduceTaskPlanItem1);
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
        // ????????????
        List<ZjLzehProduceTaskPlanItem> dbList = zjLzehProduceTaskPlanItemMapper.selectByZjLzehProduceTaskPlanItemList(zjLzehProduceTaskPlanItem);
        
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("????????????", "????????????", "??????????????????","????????????","?????????","????????????",
                "????????????","????????????","??????????????????","??????????????????","????????????");
        rowsList.add(row1);
        
        
        // ??????????????????????????????????????????
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

            // ????????????
//            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
//            String fileName = "xxx??????-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            response.setHeader("Content-Disposition", "attachment; filename=" + URLUtil.encode("??????????????????????????????.xlsx"));
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
            try {
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
        } else {
            return repEntity.ok("?????????");
        }

	
	}
}
