package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzInvXmtzqkMapper;
import com.apih5.mybatis.dao.ZjTzInvXmyyqkMapper;
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;
import com.apih5.service.ZjTzInvXmyyqkService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzInvXmyyqkService")
public class ZjTzInvXmyyqkServiceImpl implements ZjTzInvXmyyqkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzInvXmyyqkMapper zjTzInvXmyyqkMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzInvXmtzqkMapper zjTzInvXmtzqkMapper;
    
    @Override
    public ResponseEntity getZjTzInvXmyyqkListByCondition(ZjTzInvXmyyqk zjTzInvXmyyqk) {
        if (zjTzInvXmyyqk == null) {
            zjTzInvXmyyqk = new ZjTzInvXmyyqk();
        }
        // 分页查询
        PageHelper.startPage(zjTzInvXmyyqk.getPage(),zjTzInvXmyyqk.getLimit());
        // 获取数据
        List<ZjTzInvXmyyqk> zjTzInvXmyyqkList = zjTzInvXmyyqkMapper.selectByZjTzInvXmyyqkList(zjTzInvXmyyqk);
        // 得到分页信息
        PageInfo<ZjTzInvXmyyqk> p = new PageInfo<>(zjTzInvXmyyqkList);

        return repEntity.okList(zjTzInvXmyyqkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzInvXmyyqkDetails(ZjTzInvXmyyqk zjTzInvXmyyqk) {
        if (zjTzInvXmyyqk == null) {
            zjTzInvXmyyqk = new ZjTzInvXmyyqk();
        }
        // 获取数据
        ZjTzInvXmyyqk dbZjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectByPrimaryKey(zjTzInvXmyyqk.getId());
        // 数据存在
        if (dbZjTzInvXmyyqk != null) {
            return repEntity.ok(dbZjTzInvXmyyqk);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzInvXmyyqk(ZjTzInvXmyyqk zjTzInvXmyyqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzInvXmyyqk.setId(UuidUtil.generate());
        zjTzInvXmyyqk.setCreateUserInfo(userKey, realName);
        int flag = zjTzInvXmyyqkMapper.insert(zjTzInvXmyyqk);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzInvXmyyqk);
        }
    }

    @Override
    public ResponseEntity updateZjTzInvXmyyqk(ZjTzInvXmyyqk zjTzInvXmyyqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectByPrimaryKey(zjTzInvXmyyqk.getId());
        if (dbzjTzInvXmyyqk != null && StrUtil.isNotEmpty(dbzjTzInvXmyyqk.getId())) {
           // 
           dbzjTzInvXmyyqk.setInvProId(zjTzInvXmyyqk.getInvProId());
           // 
           dbzjTzInvXmyyqk.setBqYyzsr(zjTzInvXmyyqk.getBqYyzsr());
           // 
           dbzjTzInvXmyyqk.setBqYywsr(zjTzInvXmyyqk.getBqYywsr());
           // 
           dbzjTzInvXmyyqk.setBqLxzc(zjTzInvXmyyqk.getBqLxzc());
           // 
           dbzjTzInvXmyyqk.setBqLyze(zjTzInvXmyyqk.getBqLyze());
           // 
           dbzjTzInvXmyyqk.setBqLr(zjTzInvXmyyqk.getBqLr());
           // 
           dbzjTzInvXmyyqk.setLjZcze(zjTzInvXmyyqk.getLjZcze());
           // 
           dbzjTzInvXmyyqk.setLjFzze(zjTzInvXmyyqk.getLjFzze());
           // 
           dbzjTzInvXmyyqk.setLjCltxsl(zjTzInvXmyyqk.getLjCltxsl());
           // 
           dbzjTzInvXmyyqk.setRjcltxsl(zjTzInvXmyyqk.getRjcltxsl());
           // 
           dbzjTzInvXmyyqk.setDnrjyyclslycz(zjTzInvXmyyqk.getDnrjyyclslycz());
           // 
           dbzjTzInvXmyyqk.setDnrjyyclslsjz(zjTzInvXmyyqk.getDnrjyyclslsjz());
           // 
           dbzjTzInvXmyyqk.setPeriodType(zjTzInvXmyyqk.getPeriodType());
           // 
           dbzjTzInvXmyyqk.setPeriodValue(zjTzInvXmyyqk.getPeriodValue());
           // 
           dbzjTzInvXmyyqk.setOrg(zjTzInvXmyyqk.getOrg());
           // 
           dbzjTzInvXmyyqk.setCurrency(zjTzInvXmyyqk.getCurrency());
           // 
           dbzjTzInvXmyyqk.setSort(zjTzInvXmyyqk.getSort());
           // 
           dbzjTzInvXmyyqk.setCreateBy(zjTzInvXmyyqk.getCreateBy());
           // 
           dbzjTzInvXmyyqk.setCreateOrg(zjTzInvXmyyqk.getCreateOrg());
           // 
           dbzjTzInvXmyyqk.setCreateDate(zjTzInvXmyyqk.getCreateDate());
           // 
           dbzjTzInvXmyyqk.setUpdateBy(zjTzInvXmyyqk.getUpdateBy());
           // 
           dbzjTzInvXmyyqk.setUpdateOrg(zjTzInvXmyyqk.getUpdateOrg());
           // 
           dbzjTzInvXmyyqk.setUpdateDate(zjTzInvXmyyqk.getUpdateDate());
           // 
           dbzjTzInvXmyyqk.setRemarks(zjTzInvXmyyqk.getRemarks());
           // 
           dbzjTzInvXmyyqk.setYyksrq(zjTzInvXmyyqk.getYyksrq());
           // 
           dbzjTzInvXmyyqk.setYyjsrq(zjTzInvXmyyqk.getYyjsrq());
           // 
           dbzjTzInvXmyyqk.setLjjyqs(zjTzInvXmyyqk.getLjjyqs());
           // 
           dbzjTzInvXmyyqk.setLjyxsj(zjTzInvXmyyqk.getLjyxsj());
           // 
           dbzjTzInvXmyyqk.setBnYyzsr(zjTzInvXmyyqk.getBnYyzsr());
           // 
           dbzjTzInvXmyyqk.setBnYywsr(zjTzInvXmyyqk.getBnYywsr());
           // 
           dbzjTzInvXmyyqk.setBnLxzc(zjTzInvXmyyqk.getBnLxzc());
           // 
           dbzjTzInvXmyyqk.setBnLyze(zjTzInvXmyyqk.getBnLyze());
           // 
           dbzjTzInvXmyyqk.setBnLr(zjTzInvXmyyqk.getBnLr());
           // 
           dbzjTzInvXmyyqk.setKlYyzsr(zjTzInvXmyyqk.getKlYyzsr());
           // 
           dbzjTzInvXmyyqk.setKlYywsr(zjTzInvXmyyqk.getKlYywsr());
           // 
           dbzjTzInvXmyyqk.setKlLxzc(zjTzInvXmyyqk.getKlLxzc());
           // 
           dbzjTzInvXmyyqk.setKlLyze(zjTzInvXmyyqk.getKlLyze());
           // 
           dbzjTzInvXmyyqk.setKlLr(zjTzInvXmyyqk.getKlLr());
           // 
           dbzjTzInvXmyyqk.setCltxqkBysl(zjTzInvXmyyqk.getCltxqkBysl());
           // 
           dbzjTzInvXmyyqk.setCltxqkByrjtxsl(zjTzInvXmyyqk.getCltxqkByrjtxsl());
           // 
           dbzjTzInvXmyyqk.setCltxqkBnsl(zjTzInvXmyyqk.getCltxqkBnsl());
           // 
           dbzjTzInvXmyyqk.setCltxqkZyyksrjtxsl(zjTzInvXmyyqk.getCltxqkZyyksrjtxsl());
           // 
           dbzjTzInvXmyyqk.setBz(zjTzInvXmyyqk.getBz());
           // 共通
           dbzjTzInvXmyyqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmyyqkMapper.updateByPrimaryKey(dbzjTzInvXmyyqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzInvXmyyqk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzInvXmyyqk(List<ZjTzInvXmyyqk> zjTzInvXmyyqkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzInvXmyyqkList != null && zjTzInvXmyyqkList.size() > 0) {
           ZjTzInvXmyyqk zjTzInvXmyyqk = new ZjTzInvXmyyqk();
           zjTzInvXmyyqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmyyqkMapper.batchDeleteUpdateZjTzInvXmyyqk(zjTzInvXmyyqkList, zjTzInvXmyyqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzInvXmyyqkList);
        }
    }
    /**
     * 
     * 运营基础数据列表
     */
	@Override
	public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicData(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		if (zjTzInvXmyyqk == null) {
			zjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userId = TokenUtils.getUserId(request);
	        String ext1 = TokenUtils.getExt1(request);
	        // 不是集团的人员时
	        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
	        	//公司身份
	        	if(StrUtil.equals("2", ext1)) {
	            
	        	}else {
//	            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
	            }
	        	// 选择全部项目是，通过拼接的sql去查询
	            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
	            	zjTzInvXmyyqk.setProjectId("");
	            	zjTzInvXmyyqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
	            }
	        } else {
	            // 集团人员时
//	        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
	            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
	            	zjTzInvXmyyqk.setProjectId("");
	            }
	        }
			List<ZjTzInvXmyyqk> zjTzInvXmyyqkMonthlyReportListBasicData = zjTzInvXmyyqkMapper.selectZjTzInvXmyyqkMonthlyReportListBasicData(zjTzInvXmyyqk);
			if(zjTzInvXmyyqkMonthlyReportListBasicData == null) {
				zjTzInvXmyyqkMonthlyReportListBasicData = Lists.newArrayList();
			}
			return repEntity.ok(zjTzInvXmyyqkMonthlyReportListBasicData);
	}
	/**
     * 
     * 运营基础数据详情
     */
	@Override
	public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicDataDetail(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		if (zjTzInvXmyyqk == null) {
			zjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		JSONObject returnJSONObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		ZjTzInvXmyyqk dbzjTzInvXmyyqk= zjTzInvXmyyqkMapper.selectZjTzInvXmyyqkMonthlyReportListBasicDataDetail(zjTzInvXmyyqk);
		if (dbzjTzInvXmyyqk == null) {
			dbzjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		//项目编号
		returnJSONObject.set("proNum", StrUtil.isEmpty(dbzjTzInvXmyyqk.getProNum()) ? "" : dbzjTzInvXmyyqk.getProNum());
		// 项目名称
		returnJSONObject.set("proName", StrUtil.isEmpty(dbzjTzInvXmyyqk.getProName()) ? "" : dbzjTzInvXmyyqk.getProName());
		// 管理单位
		returnJSONObject.set("comname", StrUtil.isEmpty(dbzjTzInvXmyyqk.getComname()) ? "" : dbzjTzInvXmyyqk.getComname());
		// 项目类型
		returnJSONObject.set("typeName", StrUtil.isEmpty(dbzjTzInvXmyyqk.getTypeName()) ? "" : dbzjTzInvXmyyqk.getTypeName());
		// 项目类别
		returnJSONObject.set("categoryName", StrUtil.isEmpty(dbzjTzInvXmyyqk.getCategoryName()) ? "" : dbzjTzInvXmyyqk.getCategoryName());
		// 股权比例
		returnJSONObject.set("szgq", dbzjTzInvXmyyqk.getSzgq() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getSzgq());
		// 运营起始日
		returnJSONObject.set("yyksrq", dbzjTzInvXmyyqk.getYyksrq() == null ? new Date() : dbzjTzInvXmyyqk.getYyksrq());
		// 累计经营期数（月）
		returnJSONObject.set("ljjyqs", dbzjTzInvXmyyqk.getLjjyqs() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getLjjyqs());
		//投评月均收入
		returnJSONObject.set("tpyjsr", dbzjTzInvXmyyqk.getTpyjsr() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getTpyjsr());
		// 累计运营时间（日）
		returnJSONObject.set("ljyxsj", dbzjTzInvXmyyqk.getLjyxsj() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getLjyxsj());
		// 期末资产总额
		returnJSONObject.set("ljZcze", dbzjTzInvXmyyqk.getLjZcze() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getLjZcze());
		// 期末负债总额
		returnJSONObject.set("ljFzze", dbzjTzInvXmyyqk.getLjFzze() == null ? new BigDecimal(0) : dbzjTzInvXmyyqk.getLjFzze());
		//投评月均车辆通行数量（辆）
		BigDecimal tpyjcltxsl = CalcUtils.calcMultiply(dbzjTzInvXmyyqk.getTpyjcltxsl(), new BigDecimal(10000));
		returnJSONObject.set("tpyjcltxsl", tpyjcltxsl == null ? new BigDecimal(0) : tpyjcltxsl);
		// 本月车辆通行数量（辆）
		BigDecimal cltxqkBysl = CalcUtils.calcMultiply(dbzjTzInvXmyyqk.getCltxqkBysl(), new BigDecimal(10000));
		returnJSONObject.set("cltxqkBysl", cltxqkBysl == null ? new BigDecimal(0) : cltxqkBysl);
		// 本年车辆通行数量（辆）
		BigDecimal cltxqkBnsl = CalcUtils.calcMultiply(dbzjTzInvXmyyqk.getCltxqkBnsl(), new BigDecimal(10000));
		returnJSONObject.set("cltxqkBnsl", cltxqkBnsl == null ? new BigDecimal(0) : cltxqkBnsl);
		// 累计车辆通行数量（辆）
		BigDecimal ljCltxsl = CalcUtils.calcMultiply(dbzjTzInvXmyyqk.getLjCltxsl(), new BigDecimal(10000));
		returnJSONObject.set("ljCltxsl", ljCltxsl == null ? new BigDecimal(0) : ljCltxsl);
		// 日均车辆通行数量（辆）
		returnJSONObject.set("rjcltxsl", dbzjTzInvXmyyqk.getRjcltxsl() == null ? 0 : dbzjTzInvXmyyqk.getRjcltxsl());
		// 当年日均运营车辆数量预测值（辆）
		returnJSONObject.set("dnrjyyclslycz", dbzjTzInvXmyyqk.getDnrjyyclslycz() == null ? 0 : dbzjTzInvXmyyqk.getDnrjyyclslycz());
		// 当年日均运营车辆数量实际值（辆）
		returnJSONObject.set("dnrjyyclslsjz", dbzjTzInvXmyyqk.getDnrjyyclslsjz() == null ? 0 : dbzjTzInvXmyyqk.getDnrjyyclslsjz());
		//填报时间
		returnJSONObject.set("createDate", dbzjTzInvXmyyqk.getCreateDate() == null ? new Date() : dbzjTzInvXmyyqk.getCreateDate());
		//备注
		returnJSONObject.set("bz", StrUtil.isEmpty(dbzjTzInvXmyyqk.getBz()) ? "" : dbzjTzInvXmyyqk.getBz());
		//报表年月
		returnJSONObject.set("periodValue", StrUtil.isEmpty(dbzjTzInvXmyyqk.getPeriodValue()) ? "" : dbzjTzInvXmyyqk.getPeriodValue());
		
		JSONObject jsonObject_yyzsr = new JSONObject();
		// 营业总收入-本期
		BigDecimal bqYyzsr = dbzjTzInvXmyyqk.getBqYyzsr();
		//营业总收入-本年
		BigDecimal bnYyzsr = dbzjTzInvXmyyqk.getBnYyzsr();
		// 营业总收入-开累
		BigDecimal klYyzsr = dbzjTzInvXmyyqk.getKlYyzsr();
		// 营业总收入
		jsonObject_yyzsr.set("id", UuidUtil.generate());
		jsonObject_yyzsr.set("parentID", "0");
		jsonObject_yyzsr.set("workName", "营业总收入");
		jsonObject_yyzsr.set("bq", bqYyzsr == null ? new BigDecimal(0) : bqYyzsr);
		jsonObject_yyzsr.set("bn", bnYyzsr == null ? new BigDecimal(0) : bnYyzsr);
		jsonObject_yyzsr.set("kl", klYyzsr == null ? new BigDecimal(0) : klYyzsr);
		jsonArray.set(jsonObject_yyzsr);
        
		JSONObject jsonObject_yywsr = new JSONObject();
        // 营业外收入-本期
		BigDecimal bqYywsr = dbzjTzInvXmyyqk.getBqYywsr();
		// 营业外收入-本年
		BigDecimal bnYywsr = dbzjTzInvXmyyqk.getBnYywsr();
		// 营业外收入-开累
		BigDecimal klYywsr = dbzjTzInvXmyyqk.getKlYywsr();
		// 营业外收入
		jsonObject_yywsr.set("id", UuidUtil.generate());
		jsonObject_yywsr.set("parentID", "0");
		jsonObject_yywsr.set("workName", "营业外收入");
		jsonObject_yywsr.set("bq", bqYywsr == null ? new BigDecimal(0) : bqYywsr);
		jsonObject_yywsr.set("bn", bnYywsr == null ? new BigDecimal(0) : bnYywsr);
		jsonObject_yywsr.set("kl", klYywsr == null ? new BigDecimal(0) : klYywsr);
		jsonArray.set(jsonObject_yywsr);
		
		JSONObject jsonObject_lxzc = new JSONObject();
		// 利息支出-本期	
		BigDecimal bqLxzc = dbzjTzInvXmyyqk.getBqLxzc();
		// 利息支出-本年	
		BigDecimal bnLxzc = dbzjTzInvXmyyqk.getBnLxzc();
		// 利息支出-开累	
		BigDecimal klLxzc = dbzjTzInvXmyyqk.getKlLxzc();
		// 利息支出
		jsonObject_lxzc.set("id", UuidUtil.generate());
		jsonObject_lxzc.set("parentID", "0");
		jsonObject_lxzc.set("workName", "利息支出");
		jsonObject_lxzc.set("bq", bqLxzc == null ? new BigDecimal(0) : bqLxzc);
		jsonObject_lxzc.set("bn", bnLxzc == null ? new BigDecimal(0) : bnLxzc);
		jsonObject_lxzc.set("kl", klLxzc == null ? new BigDecimal(0) : klLxzc);
		jsonArray.set(jsonObject_lxzc);
		
		JSONObject jsonObject_lrze = new JSONObject();
		// 利润总额-本期	
		BigDecimal bqLyze = dbzjTzInvXmyyqk.getBqLyze();
		// 利润总额-本年	
		BigDecimal bnLyze = dbzjTzInvXmyyqk.getBnLyze();
		// 利润总额-开累	
		BigDecimal klLyze = dbzjTzInvXmyyqk.getKlLyze();
		// 利润总额
		jsonObject_lrze.set("id", UuidUtil.generate());
		jsonObject_lrze.set("parentID", "0");
		jsonObject_lrze.set("workName", "利润总额");
		jsonObject_lrze.set("bq", bqLyze == null ? new BigDecimal(0) : bqLyze);
		jsonObject_lrze.set("bn", bnLyze == null ? new BigDecimal(0) : bnLyze);
		jsonObject_lrze.set("kl", klLyze == null ? new BigDecimal(0) : klLyze);
		jsonArray.set(jsonObject_lrze);

		JSONObject jsonObject_jlr = new JSONObject();
		// 净利润-本期	
		BigDecimal bqLr = dbzjTzInvXmyyqk.getBqLr();
		// 净利润-本年	
		BigDecimal bnLr = dbzjTzInvXmyyqk.getBnLr();
		// 净利润-开累	
		BigDecimal klLr = dbzjTzInvXmyyqk.getKlLr();
		// 净利润
		jsonObject_jlr.set("id", UuidUtil.generate());
		jsonObject_jlr.set("parentID", "0");
		jsonObject_jlr.set("workName", "净利润");
		jsonObject_jlr.set("bq", bqLr == null ? new BigDecimal(0) : bqLr);
		jsonObject_jlr.set("bn", bnLr == null ? new BigDecimal(0) : bnLr);
		jsonObject_jlr.set("kl", klLr == null ? new BigDecimal(0) : klLr);
		jsonArray.set(jsonObject_jlr);

		returnJSONObject.set("children",jsonArray);
		return repEntity.ok(returnJSONObject);
	}
	/**
	 *首页图表数据运营数据
	 * 
	 */
	@Override
	public ResponseEntity getHomeChartOperateData(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		if (zjTzInvXmyyqk == null) {
			zjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmyyqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		if (zjTzInvXmyyqk.getPeriod() != null) {
			String period = DateUtil.format(zjTzInvXmyyqk.getPeriod(), "yyyyMM");
			zjTzInvXmyyqk.setPeriodValue(period);
		}
		List<ZjTzInvXmyyqk> chartOperateDataList = zjTzInvXmyyqkMapper.selectHomeChartOperateData(zjTzInvXmyyqk);
		if (chartOperateDataList != null && chartOperateDataList.size() > 0) {
			for (ZjTzInvXmyyqk zjTzInvXmyyqk2 : chartOperateDataList) {
				//总投资额
				BigDecimal hte = CalcUtils.calcDivide(zjTzInvXmyyqk2.getHte(), new BigDecimal(10000));
				zjTzInvXmyyqk2.setHte(hte.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				BigDecimal kltpbfb = CalcUtils.calcMultiply(zjTzInvXmyyqk2.getKltpbfb(), new BigDecimal(100));
				zjTzInvXmyyqk2.setKltpbfb(kltpbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bntpbfb = CalcUtils.calcMultiply(zjTzInvXmyyqk2.getBntpbfb(), new BigDecimal(100));
				zjTzInvXmyyqk2.setBntpbfb(bntpbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bqtpbfb = CalcUtils.calcMultiply(zjTzInvXmyyqk2.getBqtpbfb(), new BigDecimal(100));
				zjTzInvXmyyqk2.setBqtpbfb(bqtpbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(chartOperateDataList);
	}
	/**
	 * 
	 * 运营页面投评与收入情况
	 */
	@Override
	public ResponseEntity getOperatePageCommentAndIncome(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		if (zjTzInvXmyyqk == null) {
			zjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
            	zjTzInvXmyyqk.setProjectId("");
            	zjTzInvXmyyqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
            	zjTzInvXmyyqk.setProjectId("");
            }
        }

		ZjTzInvXmtzqk zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		zjTzInvXmtzqk.setProjectId(zjTzInvXmyyqk.getProjectId());
		
		ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectAllPeriod(zjTzInvXmtzqk);
		ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectOperatePageCommentAndIncome(zjTzInvXmyyqk);
		if (dbzjTzInvXmtzqk == null) {
			dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		if (dbzjTzInvXmyyqk == null) {
			dbzjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		JSONArray jsonArray =  new JSONArray();
//		JSONObject jsonItem = new JSONObject();
		// 总投资
		JSONArray jsonArrayZtz =  new JSONArray();
		JSONObject jsonItemZtz1 = new JSONObject();
		jsonItemZtz1.set("name", "总投资完成情况");
		jsonItemZtz1.set("value", dbzjTzInvXmtzqk.getAmount1());
		jsonArrayZtz.add(jsonItemZtz1);
		JSONObject jsonItemZtz2 = new JSONObject();
		jsonItemZtz2.set("name", "累计完成投资");
		jsonItemZtz2.set("value", dbzjTzInvXmtzqk.getTzwckl());
		jsonArrayZtz.add(jsonItemZtz2);
		JSONObject jsonItemZtz3 = new JSONObject();
		jsonItemZtz3.set("name", "未完成投资金额");
		jsonItemZtz3.set("value", dbzjTzInvXmtzqk.getWtzwckl());
		jsonArrayZtz.add(jsonItemZtz3);
		
		jsonArray.add(jsonArrayZtz);
		//总建安
		JSONArray jsonArrayZja =  new JSONArray();
		JSONObject jsonItemZja1 = new JSONObject();
		jsonItemZja1.set("name", "总建安完成情况");
		jsonItemZja1.set("value", dbzjTzInvXmtzqk.getAmount3());
		jsonArrayZja.add(jsonItemZja1);
		JSONObject jsonItemZja2 = new JSONObject();
		jsonItemZja2.set("name", "累计完成建安");
		jsonItemZja2.set("value", dbzjTzInvXmtzqk.getWcjafkl());
		jsonArrayZja.add(jsonItemZja2);
		JSONObject jsonItemZja3 = new JSONObject();
		jsonItemZja3.set("name", "未完成建安金额");
		jsonItemZja3.set("value", dbzjTzInvXmtzqk.getWwcjafkl());
		jsonArrayZja.add(jsonItemZja3);
		
		jsonArray.add(jsonArrayZja);
		//投评总收入
		JSONArray jsonArrayTpzsr =  new JSONArray();
		JSONObject jsonItemTpzsr1 = new JSONObject();
		jsonItemTpzsr1.set("name", "投评总收入");
		jsonItemTpzsr1.set("value", dbzjTzInvXmyyqk.getKltpsr());
		jsonArrayTpzsr.add(jsonItemTpzsr1);
		JSONObject jsonItemTpzsr2 = new JSONObject();
		jsonItemTpzsr2.set("name", "开累营业收入");
		jsonItemTpzsr2.set("value", dbzjTzInvXmyyqk.getKlYyzsr());
		jsonArrayTpzsr.add(jsonItemTpzsr2);
		JSONObject jsonItemTpzsr3 = new JSONObject();
		jsonItemTpzsr3.set("name", "未完成总收入");
		jsonItemTpzsr3.set("value", dbzjTzInvXmyyqk.getWklYyzsr());
		jsonArrayTpzsr.add(jsonItemTpzsr3);
		
		jsonArray.add(jsonArrayTpzsr);
		//本年投评收入
		JSONArray jsonArrayBntpsr =  new JSONArray();
		JSONObject jsonItemBntpsr1 = new JSONObject();
		jsonItemBntpsr1.set("name", "本年投评收入");
		jsonItemBntpsr1.set("value", dbzjTzInvXmyyqk.getBntpsr());
		jsonArrayBntpsr.add(jsonItemBntpsr1);
		JSONObject jsonItemBntpsr2 = new JSONObject();
		jsonItemTpzsr2.set("name", "本年营业收入");
		jsonItemTpzsr2.set("value", dbzjTzInvXmyyqk.getBnYyzsr());
		jsonArrayBntpsr.add(jsonItemTpzsr2);
		JSONObject jsonItemBntpsr3 = new JSONObject();
		jsonItemBntpsr3.set("name", "本年未完成收入");
		jsonItemBntpsr3.set("value", dbzjTzInvXmyyqk.getWbnYyzsr());
		jsonArrayBntpsr.add(jsonItemBntpsr3);
		
		jsonArray.add(jsonArrayBntpsr);
		//本月投评收入
		JSONArray jsonArrayBytpsr =  new JSONArray();
		JSONObject jsonItemBytpsr1 = new JSONObject();
		jsonItemBytpsr1.set("name", "本月投评收入");
		jsonItemBytpsr1.set("value", dbzjTzInvXmyyqk.getBqtpsr());
		jsonArrayBytpsr.add(jsonItemBytpsr1);
		JSONObject jsonItemBytpsr2 = new JSONObject();
		jsonItemBytpsr2.set("name", "本月营业收入");
		jsonItemBytpsr2.set("value", dbzjTzInvXmyyqk.getBqYyzsr());
		jsonArrayBytpsr.add(jsonItemBytpsr2);
		JSONObject jsonItemBytpsr3 = new JSONObject();
		jsonItemBytpsr3.set("name", "本月未完成收入");
		jsonItemBytpsr3.set("value", dbzjTzInvXmyyqk.getWbqYyzsr());
		jsonArrayBytpsr.add(jsonItemBytpsr3);
		
		jsonArray.add(jsonArrayBytpsr);
		return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 运营页当前时期
	 */
	@Override
	public ResponseEntity getOperatePageCurrentPeriod(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		if (zjTzInvXmyyqk == null) {
			zjTzInvXmyyqk = new ZjTzInvXmyyqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
            	zjTzInvXmyyqk.setProjectId("");
            	zjTzInvXmyyqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmyyqk.getProjectId(), true)) {
            	zjTzInvXmyyqk.setProjectId("");
            }
        }
		ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectOperatePageCurrentPeriod(zjTzInvXmyyqk);
		return repEntity.ok(dbzjTzInvXmyyqk);
	}
}
