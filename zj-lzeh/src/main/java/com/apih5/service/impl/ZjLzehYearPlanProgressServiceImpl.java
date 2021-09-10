package com.apih5.service.impl;

import java.math.BigDecimal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZjLzehFileMapper;
import com.apih5.mybatis.pojo.ZjLzehFile;
import com.apih5.utils.ZjLzehUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehYearPlanProgressMapper;
import com.apih5.mybatis.pojo.ZjLzehYearPlanProgress;
import com.apih5.service.ZjLzehYearPlanProgressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zjLzehYearPlanProgressService")
public class ZjLzehYearPlanProgressServiceImpl implements ZjLzehYearPlanProgressService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehYearPlanProgressMapper zjLzehYearPlanProgressMapper;

    @Autowired(required = true)
    private ZjLzehFileMapper zjLzehFileMapper;

    @Override
    public ResponseEntity getZjLzehYearPlanProgressListByCondition(ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        if (zjLzehYearPlanProgress == null) {
            zjLzehYearPlanProgress = new ZjLzehYearPlanProgress();
        }
        //初始化url、外部接口访问的参数param
        ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();

        Map<String, String> headersMap = Maps.newHashMap();
        headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));

        // 分页查询
        PageHelper.startPage(zjLzehYearPlanProgress.getPage(),zjLzehYearPlanProgress.getLimit());
        // 获取数据
        List<ZjLzehYearPlanProgress> zjLzehYearPlanProgressList = zjLzehYearPlanProgressMapper.selectByZjLzehYearPlanProgressList(zjLzehYearPlanProgress);

        //遍历年计划列表
        for (ZjLzehYearPlanProgress zjpp:zjLzehYearPlanProgressList
             ) {
            //年计划年度类型从date转为String并放入参数中
            String year = DateUtil.format(zjpp.getYear(),"yyyy");
            zjpp.setShowYear(year);
            String url = ZjLzehUtil.OTHER_URL + "a/progress/progressPjLz2h/getListData?projectId=" + ZjLzehUtil.PROJECT_ID + "&period=" + year;
            //访问外部接口
            String result = cn.hutool.http.HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
            //接收外部接口返回的json

            JSONObject resultJsonObject = new JSONObject(result);
            JSONArray jsonArray = resultJsonObject.getJSONObject("rtnObj").getJSONArray("data");

            if(jsonArray != null && jsonArray.size()>0) {
                BigDecimal amt= new BigDecimal("0");
                //获取实际产值
                for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObjectItme = (JSONObject)iterator.next();
                    if(jsonObjectItme.getDouble("amt")!=null){
                        amt = CalcUtils.calcAdd(amt,new BigDecimal(jsonObjectItme.getDouble("amt")));
                    }
                }
                amt= CalcUtils.calcDivide(amt,new BigDecimal("10000"),2);
                zjLzehYearPlanProgress.setYearOutValue(amt);
                //计算任务完成率（实际产值/计划产值）
                zjLzehYearPlanProgress.setCompleteRate(CalcUtils.calcMultiply(CalcUtils.calcDivide(zjLzehYearPlanProgress.getYearOutValue(),zjLzehYearPlanProgress.getPlanYearOutValue(),4),new BigDecimal("100")));
            }
            updateZjLzehYearPlanProgress(zjpp);
            //查询附件
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjpp.getZjLzehYearPlanProgressId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjpp.setFileList(files);
        }

        // 得到分页信息
        PageInfo<ZjLzehYearPlanProgress> p = new PageInfo<>(zjLzehYearPlanProgressList);


        return repEntity.okList(zjLzehYearPlanProgressList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehYearPlanProgressDetail(ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        if (zjLzehYearPlanProgress == null) {
            zjLzehYearPlanProgress = new ZjLzehYearPlanProgress();
        }
        // 获取数据
        ZjLzehYearPlanProgress dbZjLzehYearPlanProgress = zjLzehYearPlanProgressMapper.selectByPrimaryKey(zjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
        String year = DateUtil.format(dbZjLzehYearPlanProgress.getYear(),"yyyy");
        dbZjLzehYearPlanProgress.setShowYear(year);
        ZjLzehFile file = new ZjLzehFile();
        file.setOtherId(dbZjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
        List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
        dbZjLzehYearPlanProgress.setFileList(files);
        // 数据存在
        if (dbZjLzehYearPlanProgress != null) {
            return repEntity.ok(dbZjLzehYearPlanProgress);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehYearPlanProgress(ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehYearPlanProgress.setZjLzehYearPlanProgressId(UuidUtil.generate());
        zjLzehYearPlanProgress.setCreateUserInfo(userKey, realName);

        ZjLzehYearPlanProgress zjLzehYearPlanProgress1 =new ZjLzehYearPlanProgress();
        zjLzehYearPlanProgress1.setYear(zjLzehYearPlanProgress.getYear());
         List<ZjLzehYearPlanProgress> zjLzehYearPlanProgressList = zjLzehYearPlanProgressMapper.selectByZjLzehYearPlanProgressList(zjLzehYearPlanProgress1);
        if(zjLzehYearPlanProgressList.size()>0){
            return repEntity.layerMessage("no","该年度计划已存在！");
        }

        ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();

        String year = DateUtil.format(zjLzehYearPlanProgress.getYear(),"yyyy");
        String url = ZjLzehUtil.OTHER_URL + "a/progress/progressPjLz2h/getListData?projectId=" + ZjLzehUtil.PROJECT_ID + "&period=" + year;
        Map<String, String> headersMap = Maps.newHashMap();
        headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
        String result = cn.hutool.http.HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
        JSONObject resultJsonObject = new JSONObject(result);
        JSONArray jsonArray = resultJsonObject.getJSONObject("rtnObj").getJSONArray("data");

        if(jsonArray != null && jsonArray.size()>0) {
            BigDecimal amt= new BigDecimal("0");
            //获取实际产值
            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObjectItme = (JSONObject)iterator.next();
                if(jsonObjectItme.getDouble("amt")!=null){
                    amt = CalcUtils.calcAdd(amt,new BigDecimal(jsonObjectItme.getDouble("amt")));
                }
            }
            amt= CalcUtils.calcDivide(amt,new BigDecimal("10000"),2);
            zjLzehYearPlanProgress.setYearOutValue(amt);
            zjLzehYearPlanProgress.setCompleteRate(CalcUtils.calcMultiply(CalcUtils.calcDivide(zjLzehYearPlanProgress.getYearOutValue(),zjLzehYearPlanProgress.getPlanYearOutValue(),2),new BigDecimal("100")));
        }
        int flag = zjLzehYearPlanProgressMapper.insert(zjLzehYearPlanProgress);

        if(zjLzehYearPlanProgress.getFileList() != null && zjLzehYearPlanProgress.getFileList().size() >0) {
            for (ZjLzehFile file : zjLzehYearPlanProgress.getFileList()) {
                file.setUid(UuidUtil.generate());
                file.setOtherId(zjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
                file.setCreateUserInfo(userKey, realName);
                zjLzehFileMapper.insert(file);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehYearPlanProgress);
        }
    }

    @Override
    public ResponseEntity updateZjLzehYearPlanProgress(ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehYearPlanProgress dbZjLzehYearPlanProgress = zjLzehYearPlanProgressMapper.selectByPrimaryKey(zjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
        if (dbZjLzehYearPlanProgress != null && StrUtil.isNotEmpty(dbZjLzehYearPlanProgress.getZjLzehYearPlanProgressId())) {
           // 年度
           dbZjLzehYearPlanProgress.setYear(zjLzehYearPlanProgress.getYear());
           // 计划年产值
           dbZjLzehYearPlanProgress.setPlanYearOutValue(zjLzehYearPlanProgress.getPlanYearOutValue());
           // 实际年产值
           dbZjLzehYearPlanProgress.setYearOutValue(zjLzehYearPlanProgress.getYearOutValue());
           // 任务完成率
           dbZjLzehYearPlanProgress.setCompleteRate(zjLzehYearPlanProgress.getCompleteRate());
           // 备注
           dbZjLzehYearPlanProgress.setRemarks(zjLzehYearPlanProgress.getRemarks());
           // 排序
           dbZjLzehYearPlanProgress.setSort(zjLzehYearPlanProgress.getSort());
           // 共通
           dbZjLzehYearPlanProgress.setModifyUserInfo(userKey, realName);
           flag = zjLzehYearPlanProgressMapper.updateByPrimaryKey(dbZjLzehYearPlanProgress);


            //附件先删除再新增
            ZjLzehFile delFile = new ZjLzehFile();

            if(zjLzehYearPlanProgress.getFileList() != null && zjLzehYearPlanProgress.getFileList().size() >0) {
                delFile.setOtherId(zjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
                List<ZjLzehFile> delFileList = zjLzehFileMapper.selectByZjLzehFileList(delFile);
                if(delFileList != null && delFileList.size() >0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zjLzehFileMapper.batchDeleteUpdateZjLzehFile(delFileList, delFile);
                }
                for (ZjLzehFile file : zjLzehYearPlanProgress.getFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zjLzehYearPlanProgress.getZjLzehYearPlanProgressId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zjLzehFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehYearPlanProgress);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehYearPlanProgress(List<ZjLzehYearPlanProgress> zjLzehYearPlanProgressList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehYearPlanProgressList != null && zjLzehYearPlanProgressList.size() > 0) {
           ZjLzehYearPlanProgress zjLzehYearPlanProgress = new ZjLzehYearPlanProgress();
           zjLzehYearPlanProgress.setModifyUserInfo(userKey, realName);
           flag = zjLzehYearPlanProgressMapper.batchDeleteUpdateZjLzehYearPlanProgress(zjLzehYearPlanProgressList, zjLzehYearPlanProgress);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehYearPlanProgressList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
