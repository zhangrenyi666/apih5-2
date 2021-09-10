package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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
import com.apih5.mybatis.dao.ZjLzehMonthPlanProgressMapper;
import com.apih5.mybatis.pojo.ZjLzehMonthPlanProgress;
import com.apih5.service.ZjLzehMonthPlanProgressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehMonthPlanProgressService")
public class ZjLzehMonthPlanProgressServiceImpl implements ZjLzehMonthPlanProgressService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehMonthPlanProgressMapper zjLzehMonthPlanProgressMapper;

    @Autowired(required = true)
    private ZjLzehFileMapper zjLzehFileMapper;

    @Override
    public ResponseEntity getZjLzehMonthPlanProgressListByCondition(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        if (zjLzehMonthPlanProgress == null) {
            zjLzehMonthPlanProgress = new ZjLzehMonthPlanProgress();
        }
        //初始化url、外部接口访问的参数param
        ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();


        Map<String, String> headersMap = Maps.newHashMap();
        headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
        // 分页查询
        PageHelper.startPage(zjLzehMonthPlanProgress.getPage(),zjLzehMonthPlanProgress.getLimit());
        // 获取数据
        List<ZjLzehMonthPlanProgress> zjLzehMonthPlanProgressList = zjLzehMonthPlanProgressMapper.selectByZjLzehMonthPlanProgressList(zjLzehMonthPlanProgress);
        for (ZjLzehMonthPlanProgress zjpp:zjLzehMonthPlanProgressList
        ) {

            String year = DateUtil.format(zjpp.getMonth(),"yyyy-MM");
            zjpp.setShowMonth(year);
            String url = ZjLzehUtil.OTHER_URL + "a/progress/progressPjLz2h/getListData?projectId=" + ZjLzehUtil.PROJECT_ID + "&period=" + year;
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
                zjpp.setMonthOutValue(amt);
                //计算任务完成率（实际产值/计划产值）
                zjpp.setCompleteRate(CalcUtils.calcMultiply(CalcUtils.calcDivide(zjpp.getMonthOutValue(),zjpp.getPlanMonthOutValue(),4),new BigDecimal("100")));
            }
            updateZjLzehMonthPlanProgress(zjpp);
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjpp.getZjLzehMonthPlanProgressId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjpp.setFileList(files);
        }

        // 得到分页信息
        PageInfo<ZjLzehMonthPlanProgress> p = new PageInfo<>(zjLzehMonthPlanProgressList);


        return repEntity.okList(zjLzehMonthPlanProgressList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehMonthPlanProgressDetail(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        if (zjLzehMonthPlanProgress == null) {
            zjLzehMonthPlanProgress = new ZjLzehMonthPlanProgress();
        }
        // 获取数据
        ZjLzehMonthPlanProgress dbZjLzehMonthPlanProgress = zjLzehMonthPlanProgressMapper.selectByPrimaryKey(zjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());

        String year = DateUtil.format(dbZjLzehMonthPlanProgress.getMonth(),"yyyy-MM");
        dbZjLzehMonthPlanProgress.setShowMonth(year);
        ZjLzehFile file = new ZjLzehFile();
        file.setOtherId(dbZjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());
        List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
        dbZjLzehMonthPlanProgress.setFileList(files);
        // 数据存在
        if (dbZjLzehMonthPlanProgress != null) {
            return repEntity.ok(dbZjLzehMonthPlanProgress);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehMonthPlanProgress(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehMonthPlanProgress.setZjLzehMonthPlanProgressId(UuidUtil.generate());
        zjLzehMonthPlanProgress.setCreateUserInfo(userKey, realName);


        ZjLzehMonthPlanProgress zjLzehMonthPlanProgress1 =new ZjLzehMonthPlanProgress();
        zjLzehMonthPlanProgress1.setMonth(zjLzehMonthPlanProgress.getMonth());
        List<ZjLzehMonthPlanProgress> zjLzehMonthPlanProgressList = zjLzehMonthPlanProgressMapper.selectByZjLzehMonthPlanProgressList(zjLzehMonthPlanProgress1);
        if(zjLzehMonthPlanProgressList.size()>0){
            return  repEntity.layerMessage("no","该月份的数据已存在");
        }
        ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();


        Map<String, String> headersMap = Maps.newHashMap();
        headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
        String year = DateUtil.format(zjLzehMonthPlanProgress.getMonth(),"yyyy-MM");
        String url = ZjLzehUtil.OTHER_URL + "a/progress/progressPjLz2h/getListData?projectId=" + ZjLzehUtil.PROJECT_ID+ "&period=" + year ;
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
            zjLzehMonthPlanProgress.setMonthOutValue(amt);
            zjLzehMonthPlanProgress.setCompleteRate(CalcUtils.calcMultiply(CalcUtils.calcDivide(zjLzehMonthPlanProgress.getMonthOutValue(),zjLzehMonthPlanProgress.getPlanMonthOutValue(),4),new BigDecimal("100")));
        }
        int flag = zjLzehMonthPlanProgressMapper.insert(zjLzehMonthPlanProgress);

        if(zjLzehMonthPlanProgress.getFileList() != null && zjLzehMonthPlanProgress.getFileList().size() >0) {
            for (ZjLzehFile file : zjLzehMonthPlanProgress.getFileList()) {
                file.setUid(UuidUtil.generate());
                file.setOtherId(zjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());
                file.setCreateUserInfo(userKey, realName);
                zjLzehFileMapper.insert(file);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehMonthPlanProgress);
        }
    }

    @Override
    public ResponseEntity updateZjLzehMonthPlanProgress(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehMonthPlanProgress dbZjLzehMonthPlanProgress = zjLzehMonthPlanProgressMapper.selectByPrimaryKey(zjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());
        if (dbZjLzehMonthPlanProgress != null && StrUtil.isNotEmpty(dbZjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId())) {
           // 月份
           dbZjLzehMonthPlanProgress.setMonth(zjLzehMonthPlanProgress.getMonth());
           // 计划月产值
           dbZjLzehMonthPlanProgress.setPlanMonthOutValue(zjLzehMonthPlanProgress.getPlanMonthOutValue());
           // 实际月产值
           dbZjLzehMonthPlanProgress.setMonthOutValue(zjLzehMonthPlanProgress.getMonthOutValue());
           // 任务完成率
           dbZjLzehMonthPlanProgress.setCompleteRate(zjLzehMonthPlanProgress.getCompleteRate());
           // 备注
           dbZjLzehMonthPlanProgress.setRemarks(zjLzehMonthPlanProgress.getRemarks());
           // 排序
           dbZjLzehMonthPlanProgress.setSort(zjLzehMonthPlanProgress.getSort());
           // 共通
           dbZjLzehMonthPlanProgress.setModifyUserInfo(userKey, realName);
           flag = zjLzehMonthPlanProgressMapper.updateByPrimaryKey(dbZjLzehMonthPlanProgress);

            //附件先删除再新增
            ZjLzehFile delFile = new ZjLzehFile();
            if(zjLzehMonthPlanProgress.getFileList() != null && zjLzehMonthPlanProgress.getFileList().size() >0) {
                delFile.setOtherId(zjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());
                List<ZjLzehFile> delFileList = zjLzehFileMapper.selectByZjLzehFileList(delFile);
                if(delFileList != null && delFileList.size() >0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zjLzehFileMapper.batchDeleteUpdateZjLzehFile(delFileList, delFile);
                }
                for (ZjLzehFile file : zjLzehMonthPlanProgress.getFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zjLzehMonthPlanProgress.getZjLzehMonthPlanProgressId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zjLzehFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehMonthPlanProgress);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehMonthPlanProgress(List<ZjLzehMonthPlanProgress> zjLzehMonthPlanProgressList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehMonthPlanProgressList != null && zjLzehMonthPlanProgressList.size() > 0) {
           ZjLzehMonthPlanProgress zjLzehMonthPlanProgress = new ZjLzehMonthPlanProgress();
           zjLzehMonthPlanProgress.setModifyUserInfo(userKey, realName);
           flag = zjLzehMonthPlanProgressMapper.batchDeleteUpdateZjLzehMonthPlanProgress(zjLzehMonthPlanProgressList, zjLzehMonthPlanProgress);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehMonthPlanProgressList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
