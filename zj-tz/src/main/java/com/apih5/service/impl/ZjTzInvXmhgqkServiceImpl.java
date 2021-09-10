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
import com.apih5.mybatis.dao.ZjTzInvXmhgqkMapper;
import com.apih5.mybatis.dao.ZjTzInvXmtzqkMapper;
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;
import com.apih5.service.ZjTzInvXmhgqkService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzInvXmhgqkService")
public class ZjTzInvXmhgqkServiceImpl implements ZjTzInvXmhgqkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzInvXmhgqkMapper zjTzInvXmhgqkMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzInvXmtzqkMapper zjTzInvXmtzqkMapper;
    
    @Override
    public ResponseEntity getZjTzInvXmhgqkListByCondition(ZjTzInvXmhgqk zjTzInvXmhgqk) {
        if (zjTzInvXmhgqk == null) {
            zjTzInvXmhgqk = new ZjTzInvXmhgqk();
        }
        // 分页查询
        PageHelper.startPage(zjTzInvXmhgqk.getPage(),zjTzInvXmhgqk.getLimit());
        // 获取数据
        List<ZjTzInvXmhgqk> zjTzInvXmhgqkList = zjTzInvXmhgqkMapper.selectByZjTzInvXmhgqkList(zjTzInvXmhgqk);
        // 得到分页信息
        PageInfo<ZjTzInvXmhgqk> p = new PageInfo<>(zjTzInvXmhgqkList);

        return repEntity.okList(zjTzInvXmhgqkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzInvXmhgqkDetails(ZjTzInvXmhgqk zjTzInvXmhgqk) {
        if (zjTzInvXmhgqk == null) {
            zjTzInvXmhgqk = new ZjTzInvXmhgqk();
        }
        // 获取数据
        ZjTzInvXmhgqk dbZjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectByPrimaryKey(zjTzInvXmhgqk.getId());
        // 数据存在
        if (dbZjTzInvXmhgqk != null) {
            return repEntity.ok(dbZjTzInvXmhgqk);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzInvXmhgqk(ZjTzInvXmhgqk zjTzInvXmhgqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzInvXmhgqk.setId(UuidUtil.generate());
        zjTzInvXmhgqk.setCreateUserInfo(userKey, realName);
        int flag = zjTzInvXmhgqkMapper.insert(zjTzInvXmhgqk);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzInvXmhgqk);
        }
    }

    @Override
    public ResponseEntity updateZjTzInvXmhgqk(ZjTzInvXmhgqk zjTzInvXmhgqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzInvXmhgqk dbzjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectByPrimaryKey(zjTzInvXmhgqk.getId());
        if (dbzjTzInvXmhgqk != null && StrUtil.isNotEmpty(dbzjTzInvXmhgqk.getId())) {
           // 
           dbzjTzInvXmhgqk.setInvProId(zjTzInvXmhgqk.getInvProId());
           // 
           dbzjTzInvXmhgqk.setHgq(zjTzInvXmhgqk.getHgq());
           // 
           dbzjTzInvXmhgqk.setYshsk(zjTzInvXmhgqk.getYshsk());
           // 
           dbzjTzInvXmhgqk.setSjhgsj(zjTzInvXmhgqk.getSjhgsj());
           // 
           dbzjTzInvXmhgqk.setSjhgje(zjTzInvXmhgqk.getSjhgje());
           // 
           dbzjTzInvXmhgqk.setHgjeBq(zjTzInvXmhgqk.getHgjeBq());
           // 
           dbzjTzInvXmhgqk.setHgjeHgbl(zjTzInvXmhgqk.getHgjeHgbl());
           // 
           dbzjTzInvXmhgqk.setPeriodType(zjTzInvXmhgqk.getPeriodType());
           // 
           dbzjTzInvXmhgqk.setPeriodValue(zjTzInvXmhgqk.getPeriodValue());
           // 
           dbzjTzInvXmhgqk.setOrg(zjTzInvXmhgqk.getOrg());
           // 
           dbzjTzInvXmhgqk.setCurrency(zjTzInvXmhgqk.getCurrency());
           // 
           dbzjTzInvXmhgqk.setSort(zjTzInvXmhgqk.getSort());
           // 
           dbzjTzInvXmhgqk.setCreateBy(zjTzInvXmhgqk.getCreateBy());
           // 
           dbzjTzInvXmhgqk.setCreateOrg(zjTzInvXmhgqk.getCreateOrg());
           // 
           dbzjTzInvXmhgqk.setCreateDate(zjTzInvXmhgqk.getCreateDate());
           // 
           dbzjTzInvXmhgqk.setUpdateBy(zjTzInvXmhgqk.getUpdateBy());
           // 
           dbzjTzInvXmhgqk.setUpdateOrg(zjTzInvXmhgqk.getUpdateOrg());
           // 
           dbzjTzInvXmhgqk.setUpdateDate(zjTzInvXmhgqk.getUpdateDate());
           // 
           dbzjTzInvXmhgqk.setRemarks(zjTzInvXmhgqk.getRemarks());
           // 
           dbzjTzInvXmhgqk.setBnlj(zjTzInvXmhgqk.getBnlj());
           // 
           dbzjTzInvXmhgqk.setKglj(zjTzInvXmhgqk.getKglj());
           // 
           dbzjTzInvXmhgqk.setBz(zjTzInvXmhgqk.getBz());
           // 共通
           dbzjTzInvXmhgqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmhgqkMapper.updateByPrimaryKey(dbzjTzInvXmhgqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzInvXmhgqk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzInvXmhgqk(List<ZjTzInvXmhgqk> zjTzInvXmhgqkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzInvXmhgqkList != null && zjTzInvXmhgqkList.size() > 0) {
           ZjTzInvXmhgqk zjTzInvXmhgqk = new ZjTzInvXmhgqk();
           zjTzInvXmhgqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmhgqkMapper.batchDeleteUpdateZjTzInvXmhgqk(zjTzInvXmhgqkList, zjTzInvXmhgqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzInvXmhgqkList);
        }
    }
    /**
     * 
     * 回购基础数据
     */
	@Override
	public ResponseEntity getZjTzInvXmhgqkMonthlyReportList(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		if (zjTzInvXmhgqk == null) {
			zjTzInvXmhgqk = new ZjTzInvXmhgqk();
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
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            }
        }
		
		if (zjTzInvXmhgqk.getPeriod() != null) {
			String period = DateUtil.format(zjTzInvXmhgqk.getPeriod(), "yyyyMM");
			zjTzInvXmhgqk.setPeriodValue(period);
			//生成回购月报
			List<ZjTzInvXmhgqk> zjTzInvXmhgqkMonthlyReportList = zjTzInvXmhgqkMapper.selectZjTzInvXmhgqkMonthlyReportListByPeriodValue(zjTzInvXmhgqk);
			List<ZjTzInvXmhgqk> newzjTzInvXmhgqkMonthlyReportList = Lists.newArrayList();
			for (ZjTzInvXmhgqk dbzjTzInvXmhgqk : zjTzInvXmhgqkMonthlyReportList) {
				ZjTzInvXmhgqk newzjTzInvXmhgqk = new ZjTzInvXmhgqk();
				// 单位名称
				newzjTzInvXmhgqk.setGldw(dbzjTzInvXmhgqk.getComname());
				// 项目名称
				newzjTzInvXmhgqk.setProName(dbzjTzInvXmhgqk.getProName());
				// 项目类别
				newzjTzInvXmhgqk.setProCategory(dbzjTzInvXmhgqk.getCategoryName());
				// 股权比例
				newzjTzInvXmhgqk.setSzgq(dbzjTzInvXmhgqk.getSzgq());
				// 合同回购期
				newzjTzInvXmhgqk.setHgq(dbzjTzInvXmhgqk.getHgq());
				// 回购起始日
				newzjTzInvXmhgqk.setHgxyDate(dbzjTzInvXmhgqk.getHgxyDate());
				// 累计回购期
				newzjTzInvXmhgqk.setLjhgq(dbzjTzInvXmhgqk.getLjhgq());
				// 回购比例
				newzjTzInvXmhgqk.setHgjeHgbl(dbzjTzInvXmhgqk.getHgjeHgbl());
				// 备注
				newzjTzInvXmhgqk.setBz(dbzjTzInvXmhgqk.getBz());
				// 总投资额
        		BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getZtze(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setZtze(ztze);
        		// 预计回购总额
        		BigDecimal hgxyMoney = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getHgxyMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setHgxyMoney(hgxyMoney);
        		// 回购金额本期
        		BigDecimal hgjeBq = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getHgjeBq(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setHgjeBq(hgjeBq);
        		// 回购金额本年
        		BigDecimal bnlj = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getBnlj(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setBnlj(bnlj);
        		// 回购金额开累
        		BigDecimal sjhgje = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getSjhgje(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setSjhgje(sjhgje);
        		//回购款使用情况-偿还银行贷款
    			BigDecimal chyhdk = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChyhdk(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChyhdk(chyhdk);
        		//回购款使用情况-偿还贷款利息
    			BigDecimal chdklx = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChdklx(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChdklx(chdklx);
        		//回购款使用情况-偿还资本金	
    			BigDecimal chzbj = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChzbj(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChzbj(chzbj);
        		//回购款使用情况-资金集中	
    			BigDecimal zjjz = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getZjjz(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setZjjz(zjjz);
        		//回购款使用情况-其他	
    			BigDecimal qt = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getQt(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setQt(qt);
        		
        		newzjTzInvXmhgqkMonthlyReportList.add(newzjTzInvXmhgqk);
			}
			
			
			return repEntity.ok(newzjTzInvXmhgqkMonthlyReportList);
		}else {
			JSONObject returnJSONObject = new JSONObject();
			//回购基础数据详情
			List<ZjTzInvXmhgqk> zjTzInvXmhgqkMonthlyReportList = zjTzInvXmhgqkMapper.selectZjTzInvXmhgqkMonthlyReportListByPeriodValue(zjTzInvXmhgqk);
			for (ZjTzInvXmhgqk dbzjTzInvXmhgqk : zjTzInvXmhgqkMonthlyReportList) {
				
				// 基础数据上半部分
				// 项目编号
				returnJSONObject.set("proNum", StrUtil.isEmpty(dbzjTzInvXmhgqk.getProNum()) ? "" : dbzjTzInvXmhgqk.getProNum());
				// 项目名称
	    		returnJSONObject.set("proName", StrUtil.isEmpty(dbzjTzInvXmhgqk.getProName()) ? "" : dbzjTzInvXmhgqk.getProName());
	    		//管理单位
	    		returnJSONObject.set("comname", StrUtil.isEmpty(dbzjTzInvXmhgqk.getComname()) ? "" : dbzjTzInvXmhgqk.getComname());
	    		//项目类型
	    		returnJSONObject.set("typeName", StrUtil.isEmpty(dbzjTzInvXmhgqk.getTypeName()) ? "" : dbzjTzInvXmhgqk.getTypeName());
	    		// 项目类别
	    		returnJSONObject.set("categoryName", StrUtil.isEmpty(dbzjTzInvXmhgqk.getCategoryName()) ? "" : dbzjTzInvXmhgqk.getCategoryName());
	    		//股权比例
	    		returnJSONObject.set("szgq", dbzjTzInvXmhgqk.getSzgq() == null ? new BigDecimal(0) : dbzjTzInvXmhgqk.getSzgq());
	    		//预计回购总额
	    		returnJSONObject.set("hgxyMoney", dbzjTzInvXmhgqk.getHgxyMoney() == null ? new BigDecimal(0) : dbzjTzInvXmhgqk.getHgxyMoney());
	    		//合同回购期（年)
	    		returnJSONObject.set("hgq", StrUtil.isEmpty(dbzjTzInvXmhgqk.getHgq()) ? "" : dbzjTzInvXmhgqk.getHgq());
	    		//回购起始日
	    		returnJSONObject.set("hgxyDate", dbzjTzInvXmhgqk.getHgxyDate() == null ? new Date() : dbzjTzInvXmhgqk.getHgxyDate());
	    		//累计回购期（月）
	    		returnJSONObject.set("ljhgq",  dbzjTzInvXmhgqk.getLjhgq() == null ? new BigDecimal(0) : dbzjTzInvXmhgqk.getLjhgq());
	    		//回购比例（%）
	    		returnJSONObject.set("hgjeHgbl", StrUtil.isEmpty(dbzjTzInvXmhgqk.getHgjeHgbl()) ? "" : dbzjTzInvXmhgqk.getHgjeHgbl());
	    		//填报时间
	    		returnJSONObject.set("createDate", dbzjTzInvXmhgqk.getCreateDate() == null ? new Date() : dbzjTzInvXmhgqk.getCreateDate());
	    		//备注
	    		returnJSONObject.set("bz", StrUtil.isEmpty(dbzjTzInvXmhgqk.getBz()) ? "" : dbzjTzInvXmhgqk.getBz());
	    		//报表年月
	    		returnJSONObject.set("periodValue", StrUtil.isEmpty(dbzjTzInvXmhgqk.getPeriodValue()) ? "" : dbzjTzInvXmhgqk.getPeriodValue());
	    		
	    		JSONArray jsonArray_hgje = new JSONArray();
	    		// 回购金额
	    		JSONObject jsonObject_hgje = new JSONObject();
	    		// 回购金额本期
	    		BigDecimal hgjeBq = dbzjTzInvXmhgqk.getHgjeBq();
	    		// 回购金额本年
	    		BigDecimal bnlj = dbzjTzInvXmhgqk.getBnlj();
	    		// 回购金额开累
	    		BigDecimal sjhgje = dbzjTzInvXmhgqk.getSjhgje();
	    		jsonObject_hgje.set("id", UuidUtil.generate());
	    		jsonObject_hgje.set("parentID", "0");
	    		jsonObject_hgje.set("workName", "回购金额");
	    		jsonObject_hgje.set("bq", hgjeBq == null ? new BigDecimal(0) : hgjeBq);
	    		jsonObject_hgje.set("bn", bnlj == null ? new BigDecimal(0) : bnlj);
	    		jsonObject_hgje.set("kl", sjhgje == null ? new BigDecimal(0) : sjhgje);
	    		jsonArray_hgje.set(jsonObject_hgje);
	    		
	    		JSONArray jsonArray_hgkljsyqk = new JSONArray();
	    		// 回购款累计使用情况
	    		JSONObject jsonObject_hgkljsyqk = new JSONObject();
	    		// 回购款使用情况-偿还银行贷款
	    		BigDecimal chyhdk = dbzjTzInvXmhgqk.getChyhdk();
	    		// 回购款使用情况-偿还贷款利息	
	    		BigDecimal chdklx = dbzjTzInvXmhgqk.getChdklx();
	    		// 回购款使用情况-偿还资本金	
	    		BigDecimal chzbj = dbzjTzInvXmhgqk.getChzbj();
	    		// 回购款使用情况-资金集中	
	    		BigDecimal zjjz = dbzjTzInvXmhgqk.getZjjz();
	    		// 回购款使用情况-其他
	    		BigDecimal qt = dbzjTzInvXmhgqk.getQt();
	    		// 回购款累计使用情况
	    		jsonObject_hgkljsyqk.set("id", UuidUtil.generate());
	    		jsonObject_hgkljsyqk.set("parentID", "0");
	    		jsonObject_hgkljsyqk.set("workName", "回购款累计使用情况");
	    		jsonObject_hgkljsyqk.set("chyhdk", chyhdk == null ? new BigDecimal(0) : chyhdk);
	    		jsonObject_hgkljsyqk.set("chdklx", chdklx == null ? new BigDecimal(0) : chdklx);
	    		jsonObject_hgkljsyqk.set("chzbj", chzbj == null ? new BigDecimal(0) : chzbj);
	    		jsonObject_hgkljsyqk.set("zjjz", zjjz == null ? new BigDecimal(0) : zjjz);
	    		jsonObject_hgkljsyqk.set("qt", qt == null ? new BigDecimal(0) : qt);
	    		jsonArray_hgkljsyqk.set(jsonObject_hgkljsyqk);
	    		
	    		returnJSONObject.set("children", jsonArray_hgje);
	    		returnJSONObject.set("children2", jsonArray_hgkljsyqk);
			}
			return repEntity.ok(returnJSONObject);
		}
	}
	/**
	 * 
	 * 回购基础数据列表
	 */
	@Override
	public ResponseEntity getZjTzInvXmhgqkMonthlyReportListBasicData(ZjTzInvXmhgqk zjTzInvXmhgqk) {
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
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            }
        }
		List<ZjTzInvXmhgqk> zjTzInvXmhgqkMonthlyReportListBasicData = zjTzInvXmhgqkMapper.selectZjTzInvXmhgqkMonthlyReportListBasicData(zjTzInvXmhgqk);
		if (zjTzInvXmhgqkMonthlyReportListBasicData == null) {
			zjTzInvXmhgqkMonthlyReportListBasicData = Lists.newArrayList();
		}
		return repEntity.ok(zjTzInvXmhgqkMonthlyReportListBasicData);
	}
	/**
	 * 
	 * 回购月报导出
	 */
	@Override
	public List<ZjTzInvXmhgqk> exportZjTzInvXmhgqkMonthlyReportList(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		if (zjTzInvXmhgqk == null) {
			zjTzInvXmhgqk = new ZjTzInvXmhgqk();
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
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            }
        }
		int orderNum = 0;
		List<ZjTzInvXmhgqk> newzjTzInvXmhgqkMonthlyReportList = Lists.newArrayList();
		String period = DateUtil.format(zjTzInvXmhgqk.getPeriod(), "yyyyMM");
		if (period != null) {
			zjTzInvXmhgqk.setPeriodValue(period);
			List<ZjTzInvXmhgqk> zjTzInvXmhgqkMonthlyReportList = zjTzInvXmhgqkMapper.selectZjTzInvXmhgqkMonthlyReportListByPeriodValue(zjTzInvXmhgqk);
			for (ZjTzInvXmhgqk dbzjTzInvXmhgqk : zjTzInvXmhgqkMonthlyReportList) {
				ZjTzInvXmhgqk newzjTzInvXmhgqk = new ZjTzInvXmhgqk();
				// 序号
				orderNum = orderNum + 1;
				newzjTzInvXmhgqk.setOrderNum(String.valueOf(orderNum));
				// 报表周期
				newzjTzInvXmhgqk.setPeriodValue("报表周期：" + dbzjTzInvXmhgqk.getPeriodValue());
				// 单位名称
				newzjTzInvXmhgqk.setComname(dbzjTzInvXmhgqk.getComname());
				// 项目名称
				newzjTzInvXmhgqk.setProName(dbzjTzInvXmhgqk.getProName());
				// 项目类别
				newzjTzInvXmhgqk.setCategoryName(dbzjTzInvXmhgqk.getCategoryName());
				// 股权比例
				newzjTzInvXmhgqk.setSzgq(dbzjTzInvXmhgqk.getSzgq());
				// 合同回购期
				newzjTzInvXmhgqk.setHgq(dbzjTzInvXmhgqk.getHgq());
				// 回购起始日
				newzjTzInvXmhgqk.setHgxyDate(dbzjTzInvXmhgqk.getHgxyDate());
				newzjTzInvXmhgqk.setHgxyDateStr(DateUtil.format(newzjTzInvXmhgqk.getHgxyDate(), DatePattern.NORM_DATE_PATTERN));
				// 累计回购期
				newzjTzInvXmhgqk.setLjhgq(dbzjTzInvXmhgqk.getLjhgq());
				// 回购比例
				newzjTzInvXmhgqk.setHgjeHgbl(dbzjTzInvXmhgqk.getHgjeHgbl());
				// 备注
				newzjTzInvXmhgqk.setBz(dbzjTzInvXmhgqk.getBz());
				// 总投资额
        		BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getZtze(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setZtze(ztze);
        		// 预计回购总额
        		BigDecimal hgxyMoney = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getHgxyMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setHgxyMoney(hgxyMoney);
        		// 回购金额本期
        		BigDecimal hgjeBq = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getHgjeBq(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setHgjeBq(hgjeBq);
        		// 回购金额本年
        		BigDecimal bnlj = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getBnlj(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setBnlj(bnlj);
        		// 回购金额开累
        		BigDecimal sjhgje = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getSjhgje(), new BigDecimal(10000), 2);
        		newzjTzInvXmhgqk.setSjhgje(sjhgje);
        		//回购款使用情况-偿还银行贷款
    			BigDecimal chyhdk = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChyhdk(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChyhdk(chyhdk);
        		//回购款使用情况-偿还贷款利息
    			BigDecimal chdklx = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChdklx(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChdklx(chdklx);
        		//回购款使用情况-偿还资本金	
    			BigDecimal chzbj = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getChzbj(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setChzbj(chzbj);
        		//回购款使用情况-资金集中	
    			BigDecimal zjjz = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getZjjz(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setZjjz(zjjz);
        		//回购款使用情况-其他	
    			BigDecimal qt = CalcUtils.calcDivide(dbzjTzInvXmhgqk.getQt(), new BigDecimal(10000), 2);
    			newzjTzInvXmhgqk.setQt(qt);
        		
        		newzjTzInvXmhgqkMonthlyReportList.add(newzjTzInvXmhgqk);
			}
	}
		return newzjTzInvXmhgqkMonthlyReportList;
}
	/**
	 * 
	 * 
	 * 首页图表数据回购数据
	 */
	@Override
	public ResponseEntity getHomeChartHgData(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		if (zjTzInvXmhgqk == null) {
			zjTzInvXmhgqk = new ZjTzInvXmhgqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		if (zjTzInvXmhgqk.getPeriod() != null) {
			String period = DateUtil.format(zjTzInvXmhgqk.getPeriod(), "yyyyMM");
			zjTzInvXmhgqk.setPeriodValue(period);
		}
		List<ZjTzInvXmhgqk> chartHgDataList = zjTzInvXmhgqkMapper.selectHomeChartHgData(zjTzInvXmhgqk);
		if (chartHgDataList != null && chartHgDataList.size() > 0) {
			for (ZjTzInvXmhgqk zjTzInvXmhgqk2 : chartHgDataList) {
				//总投资额
				BigDecimal hte = CalcUtils.calcDivide(zjTzInvXmhgqk2.getHte(), new BigDecimal(10000));
				zjTzInvXmhgqk2.setHte(hte.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				BigDecimal klhgbl = CalcUtils.calcMultiply(zjTzInvXmhgqk2.getKlhgbl(), new BigDecimal(100));
				zjTzInvXmhgqk2.setKlhgbl(klhgbl.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(chartHgDataList);
	}
	/**
	 * 
	 * 回购页面回购情况
	 */
	@Override
	public ResponseEntity getHgPageHgStatus(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		if (zjTzInvXmhgqk == null) {
			zjTzInvXmhgqk = new ZjTzInvXmhgqk();
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
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            }
        }
		ZjTzInvXmtzqk zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		zjTzInvXmtzqk.setProjectId(zjTzInvXmhgqk.getProjectId());
		ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectAllPeriod(zjTzInvXmtzqk);
		ZjTzInvXmhgqk dbzjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectHgPageHgStatus(zjTzInvXmhgqk);
		if (dbzjTzInvXmtzqk == null) {
			dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		if (dbzjTzInvXmhgqk == null) {
			dbzjTzInvXmhgqk = new ZjTzInvXmhgqk();
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
		//预计回购总额
		JSONArray jsonArrayYjhgze =  new JSONArray();
		JSONObject jsonItemYjhgze1 = new JSONObject();
		jsonItemYjhgze1.set("name", "预计回购总额");
		jsonItemYjhgze1.set("value", dbzjTzInvXmhgqk.getYjhgze());
		jsonArrayYjhgze.add(jsonItemYjhgze1);
		JSONObject jsonItemYjhgze2 = new JSONObject();
		jsonItemYjhgze2.set("name", "开累已回购总额");
		jsonItemYjhgze2.set("value", dbzjTzInvXmhgqk.getSjhgje());
		jsonArrayYjhgze.add(jsonItemYjhgze2);
		JSONObject jsonItemYjhgze3 = new JSONObject();
		jsonItemYjhgze3.set("name", "未完成回购总额");
		jsonItemYjhgze3.set("value", dbzjTzInvXmhgqk.getWklhgje());
		jsonArrayYjhgze.add(jsonItemYjhgze3);
		
		jsonArray.add(jsonArrayYjhgze);
		//本年应回购金额
		JSONArray jsonArrayBnyhgje =  new JSONArray();
		JSONObject jsonItemBnyhgje1 = new JSONObject();
		jsonItemBnyhgje1.set("name", "本年应回购金额");
		jsonItemBnyhgje1.set("value", dbzjTzInvXmhgqk.getBnyhg());
		jsonArrayBnyhgje.add(jsonItemBnyhgje1);
		JSONObject jsonItemBnyhgje2 = new JSONObject();
		jsonItemBnyhgje2.set("name", "本年实际回购金额");
		jsonItemBnyhgje2.set("value", dbzjTzInvXmhgqk.getBnlj());
		jsonArrayBnyhgje.add(jsonItemBnyhgje2);
		JSONObject jsonItemBnyhgje3 = new JSONObject();
		jsonItemBnyhgje3.set("name", "本年未回购金额");
		jsonItemBnyhgje3.set("value", dbzjTzInvXmhgqk.getWbnlj());
		jsonArrayBnyhgje.add(jsonItemBnyhgje3);
		
		jsonArray.add(jsonArrayBnyhgje);
		return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 回购页-当前时期
	 */
	@Override
	public ResponseEntity getHgPageCurrentPeriod(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		if (zjTzInvXmhgqk == null) {
			zjTzInvXmhgqk = new ZjTzInvXmhgqk();
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
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            	zjTzInvXmhgqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmhgqk.getProjectId(), true)) {
            	zjTzInvXmhgqk.setProjectId("");
            }
        }
		ZjTzInvXmhgqk dbzjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectHgPageCurrentPeriod(zjTzInvXmhgqk);
		return repEntity.ok(dbzjTzInvXmhgqk);
	}
}