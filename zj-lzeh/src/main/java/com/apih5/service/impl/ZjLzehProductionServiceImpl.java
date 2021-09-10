package com.apih5.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehProductionMapper;
import com.apih5.mybatis.pojo.ZjLzehProduction;
import com.apih5.mybatis.pojo.ZjLzehRebarSupermarket;
import com.apih5.service.ZjLzehProductionService;
import com.apih5.utils.ZjLzehUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

@Service("zjLzehProductionService")
public class ZjLzehProductionServiceImpl implements ZjLzehProductionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehProductionMapper zjLzehProductionMapper;

    @Override
    public ResponseEntity getZjLzehProductionListByCondition(ZjLzehProduction zjLzehProduction) {
        if (zjLzehProduction == null) {
            zjLzehProduction = new ZjLzehProduction();
        }
        // 分页查询
        PageHelper.startPage(zjLzehProduction.getPage(),zjLzehProduction.getLimit());
        //写死id
        zjLzehProduction.setProductionId("fbfe1d928e2544d6b6e9bcb72341fa36");
        // 获取数据
        List<ZjLzehProduction> zjLzehProductionList = zjLzehProductionMapper.selectByZjLzehProductionList(zjLzehProduction);
        // 得到分页信息
        PageInfo<ZjLzehProduction> p = new PageInfo<>(zjLzehProductionList);
        return repEntity.okList(zjLzehProductionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehProductionDetails(ZjLzehProduction zjLzehProduction) {
        if (zjLzehProduction == null) {
            zjLzehProduction = new ZjLzehProduction();
        }
        // 获取数据
        ZjLzehProduction dbZjLzehProduction = zjLzehProductionMapper.selectByPrimaryKey(zjLzehProduction.getProductionId());
        // 数据存在
        if (dbZjLzehProduction != null) {
            return repEntity.ok(dbZjLzehProduction);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehProduction(ZjLzehProduction zjLzehProduction) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehProduction.setProductionId(UuidUtil.generate());
        zjLzehProduction.setCreateUserInfo(userKey, realName);
        int flag = zjLzehProductionMapper.insert(zjLzehProduction);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehProduction);
        }
    }

    @Override
    public ResponseEntity updateZjLzehProduction(ZjLzehProduction zjLzehProduction) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehProduction dbzjLzehProduction = zjLzehProductionMapper.selectByPrimaryKey(zjLzehProduction.getProductionId());
        if (dbzjLzehProduction != null && StrUtil.isNotEmpty(dbzjLzehProduction.getProductionId())) {
           // 合同总计金额
           dbzjLzehProduction.setTotalContractAmount(zjLzehProduction.getTotalContractAmount());
           // 累计完成金额
           dbzjLzehProduction.setAccumulatedCompletionAmount(zjLzehProduction.getAccumulatedCompletionAmount());
           // 本年计划金额
           dbzjLzehProduction.setCurrentYearSchemeAmount(zjLzehProduction.getCurrentYearSchemeAmount());
           // 本年完成金额
           dbzjLzehProduction.setCurrentYearCompletionAmount(zjLzehProduction.getCurrentYearCompletionAmount());
           // 本月计划金额
           dbzjLzehProduction.setCurrentMonthSchemeAmount(zjLzehProduction.getCurrentMonthSchemeAmount());
           // 本月完成金额
           dbzjLzehProduction.setCurrentMonthCompletionAmount(zjLzehProduction.getCurrentMonthCompletionAmount());
           // 共通
           dbzjLzehProduction.setModifyUserInfo(userKey, realName);
           flag = zjLzehProductionMapper.updateByPrimaryKey(dbzjLzehProduction);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehProduction);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehProduction(List<ZjLzehProduction> zjLzehProductionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehProductionList != null && zjLzehProductionList.size() > 0) {
           ZjLzehProduction zjLzehProduction = new ZjLzehProduction();
           zjLzehProduction.setModifyUserInfo(userKey, realName);
           flag = zjLzehProductionMapper.batchDeleteUpdateZjLzehProduction(zjLzehProductionList, zjLzehProduction);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehProductionList);
        }
    }

	@Override
	public ResponseEntity updateZjLzehProductionByProjectId(ZjLzehProduction zjLzehProduction) {
		int flag = 0;
		ZjLzehProduction dbzjLzehProduction = zjLzehProductionMapper.selectByPrimaryKey(ZjLzehUtil.PROJECT_ID);
        if (dbzjLzehProduction != null && StrUtil.isNotEmpty(dbzjLzehProduction.getProductionId())) {
        	ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        	JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();
        	String timeStr=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        	System.out.println("日期：" + timeStr);
        	String url = ZjLzehUtil.OTHER_URL + "a/progress/ProgressMobile/getOutputValue?projectId=" + ZjLzehUtil.PROJECT_ID + "&busDate=" + timeStr;
        	Map<String, String> headersMap = Maps.newHashMap();
        	headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
        	String result = HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
        	JSONObject dataJSONObject = new JSONObject(result);
        	System.out.println("dataJSONObject:"+dataJSONObject);
        	//	合同总计金额
        	BigDecimal ctTotalAmt = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("ctTotalAmt");
        	//	累计完成金额
        	BigDecimal ctCompleteAmt = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("ctCompleteAmt");
        	//	未完成合同金额
        	BigDecimal unfinishedAmt = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("unfinishedAmt");
        	//	本年计划产值
        	BigDecimal yThisPlanValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("yThisPlanValue");
        	//	本年已完成产值
        	BigDecimal yCompleteValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("yCompleteValue");
        	//	本年未完成产值
        	BigDecimal yNotCompleteValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("yNotCompleteValue");
        	//	本月计划产值
        	BigDecimal mThisPlanValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("mThisPlanValue");
        	//	本月已完成产值
        	BigDecimal mCompleteValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("mCompleteValue");
        	//	本月未完成产值
        	BigDecimal mNotCompleteValue = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("mNotCompleteValue");

        	dbzjLzehProduction.setTotalContractAmount(ctTotalAmt);
        	dbzjLzehProduction.setAccumulatedCompletionAmount(ctCompleteAmt);
        	dbzjLzehProduction.setCurrentYearSchemeAmount(yThisPlanValue);
        	dbzjLzehProduction.setCurrentYearCompletionAmount(yCompleteValue);
        	dbzjLzehProduction.setCurrentMonthSchemeAmount(mThisPlanValue);
        	dbzjLzehProduction.setCurrentMonthCompletionAmount(mCompleteValue);
        	dbzjLzehProduction.setProductionId(ZjLzehUtil.PROJECT_ID);
        	flag = zjLzehProductionMapper.updateByPrimaryKey(dbzjLzehProduction);

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehProduction);
        }
	}
}
