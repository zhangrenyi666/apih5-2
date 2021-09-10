package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzComplianceDealMapper;
import com.apih5.mybatis.dao.ZjTzComplianceDetailMapper;
import com.apih5.mybatis.dao.ZjTzDesignFlowMapper;
import com.apih5.mybatis.dao.ZjTzInvXmhgqkMapper;
import com.apih5.mybatis.dao.ZjTzInvXmtzqkMapper;
import com.apih5.mybatis.dao.ZjTzInvXmyyqkMapper;
import com.apih5.mybatis.dao.ZjTzInvXmzjqkMapper;
import com.apih5.mybatis.dao.ZjTzPartManageMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzRiskDetailMapper;
import com.apih5.mybatis.dao.ZjTzRiskMapper;
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;
import com.apih5.mybatis.pojo.ZjTzDesignFlow;
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;
import com.apih5.mybatis.pojo.ZjTzPartManage;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzRisk;
import com.apih5.mybatis.pojo.ZjTzRiskDetail;
import com.apih5.service.ZjTzInvXmtzqkService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ibm.icu.text.SimpleDateFormat;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjTzInvXmtzqkService")
public class ZjTzInvXmtzqkServiceImpl implements ZjTzInvXmtzqkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzInvXmtzqkMapper zjTzInvXmtzqkMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzInvXmyyqkMapper zjTzInvXmyyqkMapper;
    
    @Autowired(required = true)
    private ZjTzInvXmhgqkMapper zjTzInvXmhgqkMapper;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzInvXmzjqkMapper zjTzInvXmzjqkMapper;
    
    @Autowired(required = true)
    private ZjTzRiskMapper zjTzRiskMapper;//风险管理
    
    @Autowired(required = true)
    private ZjTzRiskDetailMapper zjTzRiskDetailMapper;//风险管理
    
    @Autowired(required = true)
    private ZjTzDesignFlowMapper zjTzDesignFlowMapper;//设计流程
    
    @Autowired(required = true)
    private ZjTzPartManageMapper zjTzPartManageMapper;//设计流程
    
    @Autowired(required = true)
    private ZjTzComplianceDealMapper zjTzComplianceDealMapper;//合法合规
    
    @Autowired(required = true)
    private ZjTzComplianceDetailMapper zjTzComplianceDetailMapper;//合法合规
    @Override
    public ResponseEntity getZjTzInvXmtzqkListByCondition(ZjTzInvXmtzqk zjTzInvXmtzqk) {
        if (zjTzInvXmtzqk == null) {
            zjTzInvXmtzqk = new ZjTzInvXmtzqk();
        }
        // 分页查询
        PageHelper.startPage(zjTzInvXmtzqk.getPage(),zjTzInvXmtzqk.getLimit());
        // 获取数据
        List<ZjTzInvXmtzqk> zjTzInvXmtzqkList = zjTzInvXmtzqkMapper.selectByZjTzInvXmtzqkList(zjTzInvXmtzqk);
        // 得到分页信息
        PageInfo<ZjTzInvXmtzqk> p = new PageInfo<>(zjTzInvXmtzqkList);

        return repEntity.okList(zjTzInvXmtzqkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzInvXmtzqkDetails(ZjTzInvXmtzqk zjTzInvXmtzqk) {
        if (zjTzInvXmtzqk == null) {
            zjTzInvXmtzqk = new ZjTzInvXmtzqk();
        }
        // 获取数据
        ZjTzInvXmtzqk dbZjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectByPrimaryKey(zjTzInvXmtzqk.getId());
        // 数据存在
        if (dbZjTzInvXmtzqk != null) {
            return repEntity.ok(dbZjTzInvXmtzqk);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzInvXmtzqk(ZjTzInvXmtzqk zjTzInvXmtzqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzInvXmtzqk.setId(UuidUtil.generate());
        zjTzInvXmtzqk.setCreateUserInfo(userKey, realName);
        int flag = zjTzInvXmtzqkMapper.insert(zjTzInvXmtzqk);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzInvXmtzqk);
        }
    }

    @Override
    public ResponseEntity updateZjTzInvXmtzqk(ZjTzInvXmtzqk zjTzInvXmtzqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectByPrimaryKey(zjTzInvXmtzqk.getId());
        if (dbzjTzInvXmtzqk != null && StrUtil.isNotEmpty(dbzjTzInvXmtzqk.getId())) {
           // 投资项目id
           dbzjTzInvXmtzqk.setInvProId(zjTzInvXmtzqk.getInvProId());
           // 总投资额
           dbzjTzInvXmtzqk.setZtze(zjTzInvXmtzqk.getZtze());
           // 总建安费
           dbzjTzInvXmtzqk.setZjaf(zjTzInvXmtzqk.getZjaf());
           // 总征拆费
           dbzjTzInvXmtzqk.setZzcf(zjTzInvXmtzqk.getZzcf());
           // 投资完成本期
           dbzjTzInvXmtzqk.setTzwcbq(zjTzInvXmtzqk.getTzwcbq());
           // 投资完成本年
           dbzjTzInvXmtzqk.setTzwcbn(zjTzInvXmtzqk.getTzwcbn());
           // 投资完成开累
           dbzjTzInvXmtzqk.setTzwckl(zjTzInvXmtzqk.getTzwckl());
           // 投资完成比例
           dbzjTzInvXmtzqk.setTzwcbl(zjTzInvXmtzqk.getTzwcbl());
           // 完成建安费本期
           dbzjTzInvXmtzqk.setWcjafbq(zjTzInvXmtzqk.getWcjafbq());
           // 完成建安费本年
           dbzjTzInvXmtzqk.setWcjafbn(zjTzInvXmtzqk.getWcjafbn());
           // 完成建安费开累
           dbzjTzInvXmtzqk.setWcjafkl(zjTzInvXmtzqk.getWcjafkl());
           // 完成建安费比例
           dbzjTzInvXmtzqk.setWcjafbl(zjTzInvXmtzqk.getWcjafbl());
           // 支出建安费本期
           dbzjTzInvXmtzqk.setZcjafbq(zjTzInvXmtzqk.getZcjafbq());
           // 支出建安费本年
           dbzjTzInvXmtzqk.setZcjafbn(zjTzInvXmtzqk.getZcjafbn());
           // 支出建安费开累
           dbzjTzInvXmtzqk.setZcjafkl(zjTzInvXmtzqk.getZcjafkl());
           // 支出建安费比例
           dbzjTzInvXmtzqk.setZcjafbl(zjTzInvXmtzqk.getZcjafbl());
           // 完成征拆费本期
           dbzjTzInvXmtzqk.setWczcfbq(zjTzInvXmtzqk.getWczcfbq());
           // 完成征拆费本年
           dbzjTzInvXmtzqk.setWczcfbn(zjTzInvXmtzqk.getWczcfbn());
           // 完成征拆费开累
           dbzjTzInvXmtzqk.setWczcfkl(zjTzInvXmtzqk.getWczcfkl());
           // 完成征拆费比例
           dbzjTzInvXmtzqk.setWczcfbl(zjTzInvXmtzqk.getWczcfbl());
           // 支出征拆费本期
           dbzjTzInvXmtzqk.setZczcfbq(zjTzInvXmtzqk.getZczcfbq());
           // 支出征拆费本年
           dbzjTzInvXmtzqk.setZczcfbn(zjTzInvXmtzqk.getZczcfbn());
           // 支出征拆费开累
           dbzjTzInvXmtzqk.setZczcfkl(zjTzInvXmtzqk.getZczcfkl());
           // 支出征拆费比例
           dbzjTzInvXmtzqk.setZczcfbl(zjTzInvXmtzqk.getZczcfbl());
           // 周期类型
           dbzjTzInvXmtzqk.setPeriodType(zjTzInvXmtzqk.getPeriodType());
           // 当前周期
           dbzjTzInvXmtzqk.setPeriodValue(zjTzInvXmtzqk.getPeriodValue());
           // 单据机构
           dbzjTzInvXmtzqk.setOrg(zjTzInvXmtzqk.getOrg());
           // 币种
           dbzjTzInvXmtzqk.setCurrency(zjTzInvXmtzqk.getCurrency());
           // 排序
           dbzjTzInvXmtzqk.setSort(zjTzInvXmtzqk.getSort());
           // 创建者
           dbzjTzInvXmtzqk.setCreateBy(zjTzInvXmtzqk.getCreateBy());
           // 创建单位
           dbzjTzInvXmtzqk.setCreateOrg(zjTzInvXmtzqk.getCreateOrg());
           // 创建时间
           dbzjTzInvXmtzqk.setCreateDate(zjTzInvXmtzqk.getCreateDate());
           // 更新者
           dbzjTzInvXmtzqk.setUpdateBy(zjTzInvXmtzqk.getUpdateBy());
           // 更新单位
           dbzjTzInvXmtzqk.setUpdateOrg(zjTzInvXmtzqk.getUpdateOrg());
           // 更新时间
           dbzjTzInvXmtzqk.setUpdateDate(zjTzInvXmtzqk.getUpdateDate());
           // 备注信息
           dbzjTzInvXmtzqk.setRemarks(zjTzInvXmtzqk.getRemarks());
           // 一公局集团建安费
           dbzjTzInvXmtzqk.setYgjjtjaf(zjTzInvXmtzqk.getYgjjtjaf());
           // 项目整体完成计量本期
           dbzjTzInvXmtzqk.setXmztwcjlbq(zjTzInvXmtzqk.getXmztwcjlbq());
           // 项目整体完成计量本年
           dbzjTzInvXmtzqk.setXmztwcjlbn(zjTzInvXmtzqk.getXmztwcjlbn());
           // 项目整体完成计量开累
           dbzjTzInvXmtzqk.setXmztwcjlkl(zjTzInvXmtzqk.getXmztwcjlkl());
           // 项目整体完成计量比例
           dbzjTzInvXmtzqk.setXmztwcjlbl(zjTzInvXmtzqk.getXmztwcjlbl());
           // 一公局集团完成建安费本期
           dbzjTzInvXmtzqk.setYgjjtwcjafbq(zjTzInvXmtzqk.getYgjjtwcjafbq());
           // 一公局集团完成建安费本年
           dbzjTzInvXmtzqk.setYgjjtwcjafbn(zjTzInvXmtzqk.getYgjjtwcjafbn());
           // 一公局集团完成建安费开累
           dbzjTzInvXmtzqk.setYgjjtwcjafkl(zjTzInvXmtzqk.getYgjjtwcjafkl());
           // 一公局集团完成建安费比例
           dbzjTzInvXmtzqk.setYgjjtwcjafbl(zjTzInvXmtzqk.getYgjjtwcjafbl());
           // 一公局集团完成计量本期
           dbzjTzInvXmtzqk.setYgjjtwcjlbq(zjTzInvXmtzqk.getYgjjtwcjlbq());
           // 一公局集团完成计量本年
           dbzjTzInvXmtzqk.setYgjjtwcjlbn(zjTzInvXmtzqk.getYgjjtwcjlbn());
           // 一公局集团完成计量开累
           dbzjTzInvXmtzqk.setYgjjtwcjlkl(zjTzInvXmtzqk.getYgjjtwcjlkl());
           // 一公局集团完成计量比例
           dbzjTzInvXmtzqk.setYgjjtwcjlbl(zjTzInvXmtzqk.getYgjjtwcjlbl());
           // 一公局集团支出建安费本期
           dbzjTzInvXmtzqk.setYgjjtzcjafbq(zjTzInvXmtzqk.getYgjjtzcjafbq());
           // 一公局集团支出建安费本年
           dbzjTzInvXmtzqk.setYgjjtzcjafbn(zjTzInvXmtzqk.getYgjjtzcjafbn());
           // 一公局集团支出建安费开累
           dbzjTzInvXmtzqk.setYgjjtzcjafkl(zjTzInvXmtzqk.getYgjjtzcjafkl());
           // 一公局集团支出建安费比例
           dbzjTzInvXmtzqk.setYgjjtzcjafbl(zjTzInvXmtzqk.getYgjjtzcjafbl());
           // 备注
           dbzjTzInvXmtzqk.setBz(zjTzInvXmtzqk.getBz());
           // 
           dbzjTzInvXmtzqk.setYesOrNoJGC(zjTzInvXmtzqk.getYesOrNoJGC());
           // 
           dbzjTzInvXmtzqk.setWcjafbqJGC(zjTzInvXmtzqk.getWcjafbqJGC());
           // 
           dbzjTzInvXmtzqk.setWcjafbnJGC(zjTzInvXmtzqk.getWcjafbnJGC());
           // 
           dbzjTzInvXmtzqk.setWcjafklJGC(zjTzInvXmtzqk.getWcjafklJGC());
           // 
           dbzjTzInvXmtzqk.setWcjafblJGC(zjTzInvXmtzqk.getWcjafblJGC());
           // 
           dbzjTzInvXmtzqk.setXmztwcjlbqJGC(zjTzInvXmtzqk.getXmztwcjlbqJGC());
           // 
           dbzjTzInvXmtzqk.setXmztwcjlbnJGC(zjTzInvXmtzqk.getXmztwcjlbnJGC());
           // 
           dbzjTzInvXmtzqk.setXmztwcjlklJGC(zjTzInvXmtzqk.getXmztwcjlklJGC());
           // 
           dbzjTzInvXmtzqk.setXmztwcjlblJGC(zjTzInvXmtzqk.getXmztwcjlblJGC());
           // 
           dbzjTzInvXmtzqk.setZcjafbqJGC(zjTzInvXmtzqk.getZcjafbqJGC());
           // 
           dbzjTzInvXmtzqk.setZcjafbnJGC(zjTzInvXmtzqk.getZcjafbnJGC());
           // 
           dbzjTzInvXmtzqk.setZcjafklJGC(zjTzInvXmtzqk.getZcjafklJGC());
           // 
           dbzjTzInvXmtzqk.setZcjafblJGC(zjTzInvXmtzqk.getZcjafblJGC());
           // 
           dbzjTzInvXmtzqk.setYgjwcbqJGC(zjTzInvXmtzqk.getYgjwcbqJGC());
           // 
           dbzjTzInvXmtzqk.setYgjwcbnJGC(zjTzInvXmtzqk.getYgjwcbnJGC());
           // 
           dbzjTzInvXmtzqk.setYgjwcklJGC(zjTzInvXmtzqk.getYgjwcklJGC());
           // 
           dbzjTzInvXmtzqk.setYgjwcblJGC(zjTzInvXmtzqk.getYgjwcblJGC());
           // 
           dbzjTzInvXmtzqk.setYgjjlbqJGC(zjTzInvXmtzqk.getYgjjlbqJGC());
           // 
           dbzjTzInvXmtzqk.setYgjjlbnJGC(zjTzInvXmtzqk.getYgjjlbnJGC());
           // 
           dbzjTzInvXmtzqk.setYgjjlklJGC(zjTzInvXmtzqk.getYgjjlklJGC());
           // 
           dbzjTzInvXmtzqk.setYgjjlblJGC(zjTzInvXmtzqk.getYgjjlblJGC());
           // 权益投资完成本期
           dbzjTzInvXmtzqk.setQytzwcbq(zjTzInvXmtzqk.getQytzwcbq());
           // 权益投资完成本年
           dbzjTzInvXmtzqk.setQytzwcbn(zjTzInvXmtzqk.getQytzwcbn());
           // 权益投资完成开累
           dbzjTzInvXmtzqk.setQytzwckl(zjTzInvXmtzqk.getQytzwckl());
           // 权益投资完成比例
           dbzjTzInvXmtzqk.setQytzwcbl(zjTzInvXmtzqk.getQytzwcbl());
           // 共通
           dbzjTzInvXmtzqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmtzqkMapper.updateByPrimaryKey(dbzjTzInvXmtzqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzInvXmtzqk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzInvXmtzqk(List<ZjTzInvXmtzqk> zjTzInvXmtzqkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzInvXmtzqkList != null && zjTzInvXmtzqkList.size() > 0) {
           ZjTzInvXmtzqk zjTzInvXmtzqk = new ZjTzInvXmtzqk();
           zjTzInvXmtzqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmtzqkMapper.batchDeleteUpdateZjTzInvXmtzqk(zjTzInvXmtzqkList, zjTzInvXmtzqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzInvXmtzqkList);
        }
    }
    
    /**
     * 
     * 投资基础数据详情
     */
	@Override
	public ResponseEntity getZjTzInvXmtzqkMonthlyReportList(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
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
	            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
	            	zjTzInvXmtzqk.setProjectId("");
	            	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
	            }
	        } else {
	            // 集团人员时
//	        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
	            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
	            	zjTzInvXmtzqk.setProjectId("");
	            }
	        }
	        List<String> periodList = Lists.newArrayList();
	        //生成月报
        if (zjTzInvXmtzqk.getPeriod() != null) {
        	String period = DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM");
        	zjTzInvXmtzqk.setPeriodValue(period);
        	switch (period.substring(4)) {
        	case "01":
        		periodList.add(period);
        		break;
        	case "02":
        		periodList.add(period.substring(0, 4) + "01");
        		periodList.add(period);
        		break;
			case "03":
				periodList.add(period.substring(0, 4) + "01");
				periodList.add(period.substring(0, 4) + "02");
        		periodList.add(period);
				break;
			case "04":
				periodList.add(period);
				break;
			case "05":
				periodList.add(period.substring(0, 4) + "04");
        		periodList.add(period);
				break;
			case "06":
				periodList.add(period.substring(0, 4) + "04");
				periodList.add(period.substring(0, 4) + "05");
        		periodList.add(period);
				break;
			case "07":
				periodList.add(period);
				break;
			case "08":
				periodList.add(period.substring(0, 4) + "07");
        		periodList.add(period);
				break;
			case "09":
				periodList.add(period.substring(0, 4) + "07");
				periodList.add(period.substring(0, 4) + "08");
        		periodList.add(period);
				break;
			case "10":
				periodList.add(period);
				break;
			case "11":
				periodList.add(period.substring(0, 4) + "10");
        		periodList.add(period);
				break;
			case "12":
				periodList.add(period.substring(0, 4) + "10");
				periodList.add(period.substring(0, 4) + "11");
        		periodList.add(period);
				break;
			default:
				break;
			}
        	//生成月报
        	List<ZjTzInvXmtzqk> zjTzInvXmtzqkMonthlyReportList = zjTzInvXmtzqkMapper.selectZjTzInvXmtzqkMonthlyReportListByPeriodValue(zjTzInvXmtzqk, periodList);
        	List<ZjTzInvXmtzqk> newzjTzInvXmtzqkMonthlyReportList = Lists.newArrayList();
        	for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : zjTzInvXmtzqkMonthlyReportList) {
        		
        		ZjTzInvXmtzqk newzjTzInvXmtzqk = new ZjTzInvXmtzqk();
        		// 单位名称
        		newzjTzInvXmtzqk.setGldw(dbzjTzInvXmtzqk.getComname());
        		// 项目名称
        		newzjTzInvXmtzqk.setProName(dbzjTzInvXmtzqk.getProName());
        		// 项目类别
        		newzjTzInvXmtzqk.setProCategory(dbzjTzInvXmtzqk.getCategoryName());
        		// 股权比例
        		newzjTzInvXmtzqk.setSzgq(dbzjTzInvXmtzqk.getSzgq());
        		// 批复名称
        		newzjTzInvXmtzqk.setPfname(dbzjTzInvXmtzqk.getPfname());;
        		// 备注
        		newzjTzInvXmtzqk.setBz(dbzjTzInvXmtzqk.getBz());
        		// 总投资额
        		BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZtze(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZtze(ztze);
        		// 一公局施工份额
        		newzjTzInvXmtzqk.setYgjsgfe(dbzjTzInvXmtzqk.getYgjsgfe());
        		
        		// 总建安费
        		BigDecimal jafMoney = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getJafMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setJafMoney(jafMoney);
        		// 一公局建安费
        		BigDecimal ygjjtjaf = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjaf(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtjaf(ygjjtjaf);
        		// 总征拆费
        		BigDecimal zcfMoney = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcfMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZzcf(zcfMoney);
        		
        		// 投资完成本期
        		BigDecimal tzwcbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbq(tzwcbq);
        		// 投资完成本季	
        		BigDecimal tzwcbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbj(tzwcbj);
        		// 投资完成本年	
        		BigDecimal tzwcbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbn(tzwcbn);
        		// 投资完成开累	
        		BigDecimal tzwckl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwckl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk .setTzwckl(tzwckl);
        		// 投资完成比例	
        		newzjTzInvXmtzqk.setTzwcbl(dbzjTzInvXmtzqk.getTzwcbl());
        		// 权益投资完成本期
        		BigDecimal qytzwcbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbq(qytzwcbq);
        		// 权益投资完成本季
        		BigDecimal qytzwcbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbj(qytzwcbj);
        		// 权益投资完成本年
        		BigDecimal qytzwcbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbn(qytzwcbn);
        		// 权益投资完成开累
        		BigDecimal qytzwckl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwckl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwckl(qytzwckl);
        		// 权益投资完成比例
        		newzjTzInvXmtzqk.setQytzwcbl(dbzjTzInvXmtzqk.getQytzwcbl());
        		
        		// 项目整体完成建安费本期
        		BigDecimal wcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbq(wcjafbq);
        		// 项目整体完成建安费本季
        		BigDecimal wcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbj(wcjafbj);
        		// 项目整体完成建安费本年
        		BigDecimal wcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbn(wcjafbn);
        		// 项目整体完成建安费开累
        		BigDecimal wcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafkl(wcjafkl);
        		// 项目整体完成建安费比例
        		newzjTzInvXmtzqk.setWcjafbl(dbzjTzInvXmtzqk.getWcjafbl());
        		// 项目整体完成计量本期
        		BigDecimal xmztwcjlbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbq(xmztwcjlbq);
        		// 项目整体完成计量本季
        		BigDecimal xmztwcjlbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbj(xmztwcjlbj);
        		// 项目整体完成计量本年
        		BigDecimal xmztwcjlbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbn(xmztwcjlbn);
        		//  项目整体完成计量开累
        		BigDecimal xmztwcjlkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlkl(xmztwcjlkl);
        		// 项目整体完成计量比例
        		newzjTzInvXmtzqk.setXmztwcjlbl(dbzjTzInvXmtzqk.getXmztwcjlbl());
        		// 项目整体支出建安费本期
        		BigDecimal zcjafbq  = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbq(zcjafbq);
        		// 项目整体支出建安费本季
        		BigDecimal zcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbj(zcjafbj);
        		// 项目整体支出建安费本年
        		BigDecimal zcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbn(zcjafbn);
        		// 项目整体支出建安费开累
        		BigDecimal zcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafkl(zcjafkl);
        		// 项目整体支出建安费比例
        		newzjTzInvXmtzqk.setZcjafbl(dbzjTzInvXmtzqk.getZcjafbl());
        		// 其中一公局完成建安费本期
        		BigDecimal ygjjtwcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbq(ygjjtwcjafbq);
        		// 其中一公局完成建安费本季
        		BigDecimal ygjjtwcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbj(ygjjtwcjafbj);
        		// 其中一公局完成建安费本年
        		BigDecimal ygjjtwcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbn(ygjjtwcjafbn);
        		// 其中一公局完成建安费开累
        		BigDecimal ygjjtwcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafkl(ygjjtwcjafkl);
        		// 其中一公局完成建安费比例
        		newzjTzInvXmtzqk.setYgjjtwcjafbl(dbzjTzInvXmtzqk.getYgjjtwcjafbl());
        		// 其中一公局完成计量本期
        		BigDecimal ygjjtwcjlbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbq(ygjjtwcjlbq);
        		// 其中一公局完成计量本季
        		BigDecimal ygjjtwcjlbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbj(ygjjtwcjlbj);
        		// 其中一公局完成计量本年
        		BigDecimal ygjjtwcjlbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbn(ygjjtwcjlbn);
        		// 其中一公局完成计量开累
        		BigDecimal ygjjtwcjlkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlkl(ygjjtwcjlkl);
        		// 其中一公局完成计量比例
        		newzjTzInvXmtzqk.setYgjjtwcjlbl(dbzjTzInvXmtzqk.getYgjjtwcjlbl());
        		// 其中对一公局支出建安费本期
        		BigDecimal ygjjtzcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbq(ygjjtzcjafbq);
        		// 其中对一公局支出建安费本季
        		BigDecimal ygjjtzcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbj(ygjjtzcjafbj);
        		// 其中对一公局支出建安费本年
        		BigDecimal ygjjtzcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbn(ygjjtzcjafbn);
        		// 其中对一公局支出建安费开累
        		BigDecimal ygjjtzcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafkl(ygjjtzcjafkl);
        		// 其中对一公局支出建安费比例
        		newzjTzInvXmtzqk.setYgjjtzcjafbl(dbzjTzInvXmtzqk.getYgjjtzcjafbl());
        		// 完成征拆费本期
        		BigDecimal wczcfbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbq(wczcfbq);
        		// 完成征拆费本季
        		BigDecimal wczcfbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbj(wczcfbj);
        		// 完成征拆费本年
        		BigDecimal wczcfbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbn(wczcfbn);
        		// 完成征拆费开累
        		BigDecimal wczcfkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfkl(wczcfkl);
        		// 完成征拆费比例
        		newzjTzInvXmtzqk.setWczcfbl(dbzjTzInvXmtzqk.getWczcfbl());
        		// 支出征拆费本期
        		BigDecimal zczcfbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbq(zczcfbq);
        		// 支出征拆费本季
        		BigDecimal zczcfbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbj(zczcfbj);
        		// 支出征拆费本年
        		BigDecimal zczcfbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbn(zczcfbn);
        		// 支出征拆费开累
        		BigDecimal zczcfkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfkl(zczcfkl);
        		// 支出征拆费比例
        		newzjTzInvXmtzqk.setZczcfbl(dbzjTzInvXmtzqk.getZczcfbl());
        		
        		newzjTzInvXmtzqkMonthlyReportList.add(newzjTzInvXmtzqk);
        	}
        	return repEntity.ok(newzjTzInvXmtzqkMonthlyReportList);
		}else {
			//基础数据详情
			JSONObject returnJSONObject = new JSONObject();
			String periodValue = zjTzInvXmtzqk.getPeriodValue();
			switch (periodValue.substring(4)) {
        	case "01":
        		periodList.add(periodValue);
        		break;
        	case "02":
        		periodList.add(periodValue.substring(0, 4) + "01");
        		periodList.add(periodValue);
        		break;
			case "03":
				periodList.add(periodValue.substring(0, 4) + "01");
				periodList.add(periodValue.substring(0, 4) + "02");
        		periodList.add(periodValue);
				break;
			case "04":
				periodList.add(periodValue);
				break;
			case "05":
				periodList.add(periodValue.substring(0, 4) + "04");
        		periodList.add(periodValue);
				break;
			case "06":
				periodList.add(periodValue.substring(0, 4) + "04");
				periodList.add(periodValue.substring(0, 4) + "05");
        		periodList.add(periodValue);
				break;
			case "07":
				periodList.add(periodValue);
				break;
			case "08":
				periodList.add(periodValue.substring(0, 4) + "07");
        		periodList.add(periodValue);
				break;
			case "09":
				periodList.add(periodValue.substring(0, 4) + "07");
				periodList.add(periodValue.substring(0, 4) + "08");
        		periodList.add(periodValue);
				break;
			case "10":
				periodList.add(periodValue);
				break;
			case "11":
				periodList.add(periodValue.substring(0, 4) + "10");
        		periodList.add(periodValue);
				break;
			case "12":
				periodList.add(periodValue.substring(0, 4) + "10");
				periodList.add(periodValue.substring(0, 4) + "11");
        		periodList.add(periodValue);
				break;
			default:
				break;
			}
			List<ZjTzInvXmtzqk> zjTzInvXmtzqkMonthlyReportList = zjTzInvXmtzqkMapper.selectZjTzInvXmtzqkMonthlyReportListByPeriodValue(zjTzInvXmtzqk, periodList);
			for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : zjTzInvXmtzqkMonthlyReportList) {
            JSONArray jsonArray = new JSONArray();
            
    		// 基础数据详情上半部分
            // 项目编号
    		returnJSONObject.set("proNum", StrUtil.isEmpty(dbzjTzInvXmtzqk.getProNum()) ? "" : dbzjTzInvXmtzqk.getProNum());
    		// 项目名称
    		returnJSONObject.set("proName", StrUtil.isEmpty(dbzjTzInvXmtzqk.getProName()) ? "" : dbzjTzInvXmtzqk.getProName());
    		// 管理单位
    		returnJSONObject.set("comname", StrUtil.isEmpty(dbzjTzInvXmtzqk.getComname()) ? "" : dbzjTzInvXmtzqk.getComname());
    		// 股权比例
    		returnJSONObject.set("szgq", dbzjTzInvXmtzqk.getSzgq() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getSzgq());
    		// 项目类别
    		returnJSONObject.set("categoryName", StrUtil.isEmpty(dbzjTzInvXmtzqk.getCategoryName()) ? "" : dbzjTzInvXmtzqk.getCategoryName());
    		
    		// 一公局施工份额
    		returnJSONObject.set("ygjsgfe", dbzjTzInvXmtzqk.getYgjsgfe() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getYgjsgfe());
    		// 总投资额
    		returnJSONObject.set("ztze", dbzjTzInvXmtzqk.getZtze() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getZtze());
    		// 一公局建安费
    		returnJSONObject.set("ygjjtjaf", dbzjTzInvXmtzqk.getYgjjaf() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getYgjjaf());
    		// 总建安费
    		returnJSONObject.set("jafMoney", dbzjTzInvXmtzqk.getJafMoney() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getJafMoney());
    		// 批复名称
    		returnJSONObject.set("pfname", StrUtil.isEmpty(dbzjTzInvXmtzqk.getPfname()) ? "" : dbzjTzInvXmtzqk.getPfname());
    		// 总征拆费
    		returnJSONObject.set("zcfMoney", dbzjTzInvXmtzqk.getZcfMoney() == null ? new BigDecimal(0) : dbzjTzInvXmtzqk.getZcfMoney());
    		//填报时间
    		returnJSONObject.set("createDate", dbzjTzInvXmtzqk.getCreateDate() == null ? new Date() : dbzjTzInvXmtzqk.getCreateDate());
    		// 备注
    		returnJSONObject.set("bz", StrUtil.isEmpty(dbzjTzInvXmtzqk.getBz()) ? "" : dbzjTzInvXmtzqk.getBz());
    		// 报表年月
    		returnJSONObject.set("periodValue", StrUtil.isEmpty(dbzjTzInvXmtzqk.getPeriodValue()) ? "" : dbzjTzInvXmtzqk.getPeriodValue());
    		
    		// 基础数据下半部分
    		
    		JSONObject jsonObject_tzwc = new JSONObject();
    		// 投资完成本期
    		BigDecimal tzwcbq = dbzjTzInvXmtzqk.getTzwcbq();
    		// 投资完成本季	
    		BigDecimal tzwcbj = dbzjTzInvXmtzqk.getTzwcbj();
    		// 投资完成本年	
    		BigDecimal tzwcbn = dbzjTzInvXmtzqk.getTzwcbn();
    		// 投资完成开累	
    		BigDecimal tzwckl = dbzjTzInvXmtzqk.getTzwckl();
    		// 投资完成比例	
    		BigDecimal tzwcbl = dbzjTzInvXmtzqk.getTzwcbl();
    		// 投资完成
    		jsonObject_tzwc.set("id", UuidUtil.generate());
    		jsonObject_tzwc.set("parentID", "0");
    		jsonObject_tzwc.set("workName", "投资完成");
    		jsonObject_tzwc.set("period", tzwcbq == null ? new BigDecimal(0) : tzwcbq);
    		jsonObject_tzwc.set("season", tzwcbj == null ? new BigDecimal(0) : tzwcbj);
    		jsonObject_tzwc.set("year", tzwcbn == null ? new BigDecimal(0) : tzwcbn);
    		jsonObject_tzwc.set("total", tzwckl == null ? new BigDecimal(0) : tzwckl);
    		jsonObject_tzwc.set("rate", tzwcbl == null ? new BigDecimal(0) : tzwcbl);
    		jsonArray.set(jsonObject_tzwc);
    		
    		// 权益投资完成
    		JSONObject jsonObject_qytzwc = new JSONObject();
    		jsonObject_qytzwc.set("id", UuidUtil.generate());
    		jsonObject_qytzwc.set("parentID", "0");
    		jsonObject_qytzwc.set("workName", "权益投资完成");
    		jsonObject_qytzwc.set("period", "");
    		jsonObject_qytzwc.set("season", "");
    		jsonObject_qytzwc.set("year", "");
    		jsonObject_qytzwc.set("total", "");
    		jsonObject_qytzwc.set("rate", "");
    		jsonArray.set(jsonObject_qytzwc);
    		
    		// 建安
    		JSONObject jsonObject_ja = new JSONObject();
    		jsonObject_ja.set("id", "99999");
    		jsonObject_ja.set("parentID", "0");
    		jsonObject_ja.set("workName", "建安");
    		
    		JSONObject jsonObject_wc = new JSONObject();
    		// 项目整体完成建安费本期
    		BigDecimal wcjafbq = dbzjTzInvXmtzqk.getWcjafbq();
    		// 项目整体完成建安费本季
    		BigDecimal wcjafbj =dbzjTzInvXmtzqk.getWcjafbj();
    		// 项目整体完成建安费本年
    		BigDecimal wcjafbn = dbzjTzInvXmtzqk.getWcjafbn();
    		// 项目整体完成建安费开累
    		BigDecimal wcjafkl = dbzjTzInvXmtzqk.getWcjafkl();
    		// 项目整体完成建安费比例
    		BigDecimal wcjafbl = dbzjTzInvXmtzqk.getWcjafbl();
    		// 项目整体完成建安费
    		jsonObject_wc.set("id", UuidUtil.generate());
    		jsonObject_wc.set("parentID", "99999");
    		jsonObject_wc.set("workName", "项目整体完成建安费");
    		jsonObject_wc.set("period", wcjafbq == null ? new BigDecimal(0) : wcjafbq);
    		jsonObject_wc.set("season", wcjafbj == null ? new BigDecimal(0) : wcjafbj);
    		jsonObject_wc.set("year", wcjafbn == null ? new BigDecimal(0) : wcjafbn);
    		jsonObject_wc.set("total", wcjafkl == null ? new BigDecimal(0) : wcjafkl);
    		jsonObject_wc.set("rate", wcjafbl == null ? new BigDecimal(0) : wcjafbl);//zcjafbl
    		
    		JSONObject jsonObject_wcjl = new JSONObject();
    		// 项目整体完成计量本期
    		BigDecimal xmztwcjlbq = dbzjTzInvXmtzqk.getXmztwcjlbq();
    		// 项目整体完成计量本季
    		BigDecimal xmztwcjlbj = dbzjTzInvXmtzqk.getXmztwcjlbj();
    		// 项目整体完成计量本年
    		BigDecimal xmztwcjlbn = dbzjTzInvXmtzqk.getXmztwcjlbn();
    		// 项目整体完成计量开累
    		BigDecimal xmztwcjlkl = dbzjTzInvXmtzqk.getXmztwcjlkl();
    		// 项目整体完成计量比例
    		BigDecimal xmztwcjlbl = dbzjTzInvXmtzqk.getXmztwcjlbl();
    		// 项目整体完成计量
    		jsonObject_wcjl.set("id", UuidUtil.generate());
    		jsonObject_wcjl.set("parentID", "99999");
    		jsonObject_wcjl.set("workName", "项目整体完成计量");
    		jsonObject_wcjl.set("period", xmztwcjlbq == null ? new BigDecimal(0) : xmztwcjlbq);
    		jsonObject_wcjl.set("season", xmztwcjlbj == null ? new BigDecimal(0) : xmztwcjlbj);
    		jsonObject_wcjl.set("year", xmztwcjlbn == null ? new BigDecimal(0) : xmztwcjlbn);
    		jsonObject_wcjl.set("total", xmztwcjlkl == null ? new BigDecimal(0) : xmztwcjlkl);
    		jsonObject_wcjl.set("rate", xmztwcjlbl == null ? new BigDecimal(0) : xmztwcjlbl);
    		
    		JSONObject jsonObject_zc = new JSONObject();
    		//项目整体支出建安费本期
    		BigDecimal zcjafbq = dbzjTzInvXmtzqk.getZcjafbq();
    		// 项目整体支出建安费本季
    		BigDecimal zcjafbj = dbzjTzInvXmtzqk.getZcjafbj();
    		// 项目整体支出建安费本年
    		BigDecimal zcjafbn = dbzjTzInvXmtzqk.getZcjafbn();
    		// 项目整体支出建安费开累
    		BigDecimal zcjafkl = dbzjTzInvXmtzqk.getZcjafkl();
    		// 项目整体支出建安费比例
    		BigDecimal zcjafbl = dbzjTzInvXmtzqk.getZcjafbl();
    		// 项目整体支出建安费
    		jsonObject_zc.set("id", UuidUtil.generate());
    		jsonObject_zc.set("parentID", "99999");
    		jsonObject_zc.set("workName", "项目整体支出建安费");
    		jsonObject_zc.set("period", zcjafbq == null ? new BigDecimal(0) : zcjafbq);
    		jsonObject_zc.set("season", zcjafbj == null ? new BigDecimal(0) : zcjafbj);
    		jsonObject_zc.set("year", zcjafbn == null ? new BigDecimal(0) : zcjafbn);
    		jsonObject_zc.set("total", zcjafkl == null ? new BigDecimal(0) : zcjafkl);
    		jsonObject_zc.set("rate", zcjafbl == null ? new BigDecimal(0) : zcjafbl);
    		
    		JSONObject jsonObject_ygjwc = new JSONObject();
    		// 其中一公局完成建安费本期
    		BigDecimal ygjjtwcjafbq = dbzjTzInvXmtzqk.getYgjjtwcjafbq();
    		// 其中一公局完成建安费本季
    		BigDecimal ygjjtwcjafbj = dbzjTzInvXmtzqk.getYgjjtwcjafbj();
    		// 其中一公局完成建安费本年
    		BigDecimal ygjjtwcjafbn = dbzjTzInvXmtzqk.getYgjjtwcjafbn();
    		// 其中一公局完成建安费开累
    		BigDecimal ygjjtwcjafkl = dbzjTzInvXmtzqk.getYgjjtwcjafkl();
    		// 其中一公局完成建安费比例
    		BigDecimal ygjjtwcjafbl = dbzjTzInvXmtzqk.getYgjjtwcjafbl();
    		// 其中一公局完成建安费
    		jsonObject_ygjwc.set("id", UuidUtil.generate());
    		jsonObject_ygjwc.set("parentID", "99999");
    		jsonObject_ygjwc.set("workName", "其中一公局完成建安费");
    		jsonObject_ygjwc.set("period", ygjjtwcjafbq == null ? new BigDecimal(0) : ygjjtwcjafbq);
    		jsonObject_ygjwc.set("season", ygjjtwcjafbj == null ? new BigDecimal(0) : ygjjtwcjafbj);
    		jsonObject_ygjwc.set("year", ygjjtwcjafbn == null ? new BigDecimal(0) : ygjjtwcjafbn);
    		jsonObject_ygjwc.set("total", ygjjtwcjafkl == null ? new BigDecimal(0) : ygjjtwcjafkl);
    		jsonObject_ygjwc.set("rate", ygjjtwcjafbl == null ? new BigDecimal(0) : ygjjtwcjafbl);
    		
    		JSONObject jsonObject_ygjwcjl = new JSONObject();
    		// 其中一公局完成计量本期
    		BigDecimal ygjjtwcjlbq = dbzjTzInvXmtzqk.getYgjjtwcjlbq();
    		// 其中一公局完成计量本季
    		BigDecimal ygjjtwcjlbj = dbzjTzInvXmtzqk.getYgjjtwcjlbj();
    		// 其中一公局完成计量本年
    		BigDecimal ygjjtwcjlbn = dbzjTzInvXmtzqk.getYgjjtwcjlbn();
    		// 其中一公局完成计量开累
    		BigDecimal ygjjtwcjlkl = dbzjTzInvXmtzqk.getYgjjtwcjlkl();
    		// 其中一公局完成计量比例
    		BigDecimal ygjjtwcjlbl = dbzjTzInvXmtzqk.getYgjjtwcjlbl();
    		// 其中一公局完成计量
    		jsonObject_ygjwcjl.set("id", UuidUtil.generate());
    		jsonObject_ygjwcjl.set("parentID", "99999");
    		jsonObject_ygjwcjl.set("workName", "其中一公局完成计量");
    		jsonObject_ygjwcjl.set("period", ygjjtwcjlbq == null ? new BigDecimal(0) : ygjjtwcjlbq);
    		jsonObject_ygjwcjl.set("season", ygjjtwcjlbj == null ? new BigDecimal(0) : ygjjtwcjlbj);
    		jsonObject_ygjwcjl.set("year", ygjjtwcjlbn == null ? new BigDecimal(0) : ygjjtwcjlbn);
    		jsonObject_ygjwcjl.set("total", ygjjtwcjlkl == null ? new BigDecimal(0) : ygjjtwcjlkl);
    		jsonObject_ygjwcjl.set("rate", ygjjtwcjlbl == null ? new BigDecimal(0) : ygjjtwcjlbl);
    		
    		JSONObject jsonObject_ygjzc = new JSONObject();
    		// 其中对一公局支出建安费本期
    		BigDecimal ygjjtzcjafbq = dbzjTzInvXmtzqk.getYgjjtzcjafbq();
    		// 其中对一公局支出建安费本季
    		BigDecimal ygjjtzcjafbj = dbzjTzInvXmtzqk.getYgjjtzcjafbj();
    		// 其中对一公局支出建安费本年
    		BigDecimal ygjjtzcjafbn = dbzjTzInvXmtzqk.getYgjjtzcjafbn();
    		// 其中对一公局支出建安费开累
    		BigDecimal ygjjtzcjafkl = dbzjTzInvXmtzqk.getYgjjtzcjafkl();
    		// 其中对一公局支出建安费比例
    		BigDecimal ygjjtzcjafbl = dbzjTzInvXmtzqk.getYgjjtzcjafbl();
    		// 其中对一公局支出建安费
    		jsonObject_ygjzc.set("id", UuidUtil.generate());
    		jsonObject_ygjzc.set("parentID", "99999");
    		jsonObject_ygjzc.set("workName", "其中对一公局支出建安费");
    		jsonObject_ygjzc.set("period", ygjjtzcjafbq == null ? new BigDecimal(0) : ygjjtzcjafbq);
    		jsonObject_ygjzc.set("season", ygjjtzcjafbj == null ? new BigDecimal(0) : ygjjtzcjafbj);
    		jsonObject_ygjzc.set("year", ygjjtzcjafbn == null ? new BigDecimal(0) : ygjjtzcjafbn);
    		jsonObject_ygjzc.set("total", ygjjtzcjafkl == null ? new BigDecimal(0) : ygjjtzcjafkl);
    		jsonObject_ygjzc.set("rate", ygjjtzcjafbl == null ? new BigDecimal(0) : ygjjtzcjafbl);
    		
    		List<JSONObject> zjTzInvXmtzqkListJa = new ArrayList<>();
    		zjTzInvXmtzqkListJa.add(jsonObject_wc);
    		zjTzInvXmtzqkListJa.add(jsonObject_wcjl);
    		zjTzInvXmtzqkListJa.add(jsonObject_zc);
    		zjTzInvXmtzqkListJa.add(jsonObject_ygjwc);
    		zjTzInvXmtzqkListJa.add(jsonObject_ygjwcjl);
    		zjTzInvXmtzqkListJa.add(jsonObject_ygjzc);
    		jsonObject_ja.set("children", zjTzInvXmtzqkListJa);
    		//建安
    		jsonArray.set(jsonObject_ja);

    		// 征拆
    		JSONObject jsonObject_zhengchai = new JSONObject();
    		jsonObject_zhengchai.set("id", "88888");
    		jsonObject_zhengchai.set("parentID", "0");
    		jsonObject_zhengchai.set("workName", "征拆");
    		//======
    		JSONObject jsonObject_zhengchai_finish = new JSONObject();
    		// 完成征拆费本期
    		BigDecimal wczcfbq = dbzjTzInvXmtzqk.getWczcfbq();
    		// 完成征拆费本季
    		BigDecimal wczcfbj = dbzjTzInvXmtzqk.getWczcfbj();
    		// 完成征拆费本年
    		BigDecimal wczcfbn = dbzjTzInvXmtzqk.getWczcfbn();
    		// 完成征拆费开累
    		BigDecimal wczcfkl = dbzjTzInvXmtzqk.getWczcfkl();
    		// 完成征拆费比例
    		BigDecimal wczcfbl = dbzjTzInvXmtzqk.getWczcfbl();
    		// 完成征拆费
    		jsonObject_zhengchai_finish.set("id", UuidUtil.generate());
    		jsonObject_zhengchai_finish.set("parentID", "88888");
    		jsonObject_zhengchai_finish.set("workName", "完成征拆费");
    		jsonObject_zhengchai_finish.set("period", wczcfbq == null ? new BigDecimal(0) : wczcfbq);
    		jsonObject_zhengchai_finish.set("season", wczcfbj == null ? new BigDecimal(0) : wczcfbj);
    		jsonObject_zhengchai_finish.set("year", wczcfbn == null ? new BigDecimal(0) : wczcfbn);
    		jsonObject_zhengchai_finish.set("total", wczcfkl == null ? new BigDecimal(0) : wczcfkl);
    		jsonObject_zhengchai_finish.set("rate", wczcfbl == null ? new BigDecimal(0) : wczcfbl);
    		//====
    		JSONObject jsonObject_zhengchai_unFinish = new JSONObject();
    		// 支出征拆费本期
    		BigDecimal zczcfbq = dbzjTzInvXmtzqk.getZczcfbq();
    		// 支出征拆费本季
    		BigDecimal zczcfbj = dbzjTzInvXmtzqk.getZczcfbj();
    		// 支出征拆费本年
    		BigDecimal zczcfbn = dbzjTzInvXmtzqk.getZczcfbn();
    		// 支出征拆费开累
    		BigDecimal zczcfkl = dbzjTzInvXmtzqk.getZczcfkl();
    		// 支出征拆费比例
    		BigDecimal zczcfbl = dbzjTzInvXmtzqk.getZczcfbl();
    		// 支出征拆费
    		jsonObject_zhengchai_unFinish.set("id", UuidUtil.generate());
    		jsonObject_zhengchai_unFinish.set("parentID", "88888");
    		jsonObject_zhengchai_unFinish.set("workName", "支出征拆费");
    		jsonObject_zhengchai_unFinish.set("period", zczcfbq == null ? new BigDecimal(0) : zczcfbq);
    		jsonObject_zhengchai_unFinish.set("season", zczcfbj == null ? new BigDecimal(0) : zczcfbj);
    		jsonObject_zhengchai_unFinish.set("year", zczcfbn == null ? new BigDecimal(0) : zczcfbn);
    		jsonObject_zhengchai_unFinish.set("total", zczcfkl == null ? new BigDecimal(0) : zczcfkl);
    		jsonObject_zhengchai_unFinish.set("rate", zczcfbl == null ? new BigDecimal(0) : zczcfbl);
    		//===合并zjTzInvXmtzqkList同级的子集并别数据
    		
    		List<JSONObject> zjTzInvXmtzqkListzhengchai = new ArrayList<>();
    		zjTzInvXmtzqkListzhengchai.add(jsonObject_zhengchai_finish);
    		zjTzInvXmtzqkListzhengchai.add(jsonObject_zhengchai_unFinish);
    		jsonObject_zhengchai.set("children", zjTzInvXmtzqkListzhengchai);
    		//==外层大项的分别set
    		jsonArray.set(jsonObject_zhengchai);
    		
    		returnJSONObject.set("children",jsonArray);
			}
			if (returnJSONObject == null) {
				returnJSONObject = new JSONObject();
			}
			return repEntity.ok(returnJSONObject);
		}
        
	}
	/**
	 * 
	 * 投资情况基础数据列表
	 */
	@Override
	public ResponseEntity getZjTzInvXmtzqkMonthlyReportListBasicData(ZjTzInvXmtzqk zjTzInvXmtzqk) {
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
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            }
        }
		List<ZjTzInvXmtzqk> zjTzInvXmtzqkMonthlyReportListBasicData = zjTzInvXmtzqkMapper.selectZjTzInvXmtzqkMonthlyReportListBasicData(zjTzInvXmtzqk);
		if(zjTzInvXmtzqkMonthlyReportListBasicData == null) {
			zjTzInvXmtzqkMonthlyReportListBasicData = Lists.newArrayList();
		}
		return repEntity.ok(zjTzInvXmtzqkMonthlyReportListBasicData);
	}
	/**
	 * 
	 * 月报导出
	 */
	@Override
	public List<ZjTzInvXmtzqk> exportZjTzInvXmtzqkMonthlyReportList(ZjTzInvXmtzqk zjTzInvXmtzqk) {
        if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
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
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            }
        }
        List<String> periodList = Lists.newArrayList();
        int orderNum = 0;
        List<ZjTzInvXmtzqk> newzjTzInvXmtzqkMonthlyReportList = Lists.newArrayList();
		String period = DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM");
        if (period != null) {
        	zjTzInvXmtzqk.setPeriodValue(period);
        	switch (period.substring(4)) {
        	case "01":
        		periodList.add(period);
        		break;
        	case "02":
        		periodList.add(period.substring(0, 4) + "01");
        		periodList.add(period);
        		break;
			case "03":
				periodList.add(period.substring(0, 4) + "01");
				periodList.add(period.substring(0, 4) + "02");
        		periodList.add(period);
				break;
			case "04":
				periodList.add(period);
				break;
			case "05":
				periodList.add(period.substring(0, 4) + "04");
        		periodList.add(period);
				break;
			case "06":
				periodList.add(period.substring(0, 4) + "04");
				periodList.add(period.substring(0, 4) + "05");
        		periodList.add(period);
				break;
			case "07":
				periodList.add(period);
				break;
			case "08":
				periodList.add(period.substring(0, 4) + "07");
        		periodList.add(period);
				break;
			case "09":
				periodList.add(period.substring(0, 4) + "07");
				periodList.add(period.substring(0, 4) + "08");
        		periodList.add(period);
				break;
			case "10":
				periodList.add(period);
				break;
			case "11":
				periodList.add(period.substring(0, 4) + "10");
        		periodList.add(period);
				break;
			case "12":
				periodList.add(period.substring(0, 4) + "10");
				periodList.add(period.substring(0, 4) + "11");
        		periodList.add(period);
				break;
			default:
				break;
			}
        	List<ZjTzInvXmtzqk> zjTzInvXmtzqkMonthlyReportList = zjTzInvXmtzqkMapper.selectZjTzInvXmtzqkMonthlyReportListByPeriodValue(zjTzInvXmtzqk, periodList);
        	for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : zjTzInvXmtzqkMonthlyReportList) {
        		orderNum = orderNum + 1;
        		ZjTzInvXmtzqk newzjTzInvXmtzqk = new ZjTzInvXmtzqk();
        		// 报表周期
        		newzjTzInvXmtzqk.setPeriodValue("报表周期：" + dbzjTzInvXmtzqk.getPeriodValue());
        		// 序号
        		newzjTzInvXmtzqk.setOrderNum(String.valueOf(orderNum));
        		// 单位名称
        		newzjTzInvXmtzqk.setComname(dbzjTzInvXmtzqk.getComname());
        		// 项目名称
        		newzjTzInvXmtzqk.setProName(dbzjTzInvXmtzqk.getProName());
        		// 项目类别
        		newzjTzInvXmtzqk.setCategoryName(dbzjTzInvXmtzqk.getCategoryName());
        		// 股权比例
        		newzjTzInvXmtzqk.setSzgq(dbzjTzInvXmtzqk.getSzgq());
        		//一公局施工份额
        		newzjTzInvXmtzqk.setYgjsgfe(dbzjTzInvXmtzqk.getYgjsgfe());
        		// 批复名称
        		newzjTzInvXmtzqk.setPfname(dbzjTzInvXmtzqk.getPfname());
        		// 备注
        		newzjTzInvXmtzqk.setBz(dbzjTzInvXmtzqk.getBz());
        		// 总投资额
        		BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZtze(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZtze(ztze);
        		// 总建安费
        		BigDecimal jafMoney = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getJafMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setJafMoney(jafMoney);
        		// 一公局建安费
        		BigDecimal ygjjaf = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjaf(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjaf(ygjjaf);
        		// 总征拆费
        		BigDecimal zcfMoney = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcfMoney(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcfMoney(zcfMoney);
        		
        		// 投资完成本期
        		BigDecimal tzwcbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbq(tzwcbq);
        		// 投资完成本季	
        		BigDecimal tzwcbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbj(tzwcbj);
        		// 投资完成本年	
        		BigDecimal tzwcbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwcbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setTzwcbn(tzwcbn);
        		// 投资完成开累	
        		BigDecimal tzwckl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getTzwckl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk .setTzwckl(tzwckl);
        		// 投资完成比例	
        		newzjTzInvXmtzqk.setTzwcbl(dbzjTzInvXmtzqk.getTzwcbl());
        		
        		// 权益投资完成本期
        		BigDecimal qytzwcbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbq(qytzwcbq);
        		// 权益投资完成本季	
        		BigDecimal qytzwcbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbj(qytzwcbj);
        		// 权益投资完成本年	
        		BigDecimal qytzwcbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setQytzwcbn(qytzwcbn);
        		// 权益投资完成开累	
        		BigDecimal qytzwckl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwckl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk .setQytzwckl(qytzwckl);
        		// 权益投资完成比例	
        		newzjTzInvXmtzqk.setQytzwcbl(dbzjTzInvXmtzqk.getQytzwcbl());
        		
        		// 项目整体完成建安费本期
        		BigDecimal wcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbq(wcjafbq);
        		// 项目整体完成建安费本季
        		BigDecimal wcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbj(wcjafbj);
        		// 项目整体完成建安费本年
        		BigDecimal wcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafbn(wcjafbn);
        		// 项目整体完成建安费开累
        		BigDecimal wcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWcjafkl(wcjafkl);
        		// 项目整体完成建安费比例
        		newzjTzInvXmtzqk.setWcjafbl(dbzjTzInvXmtzqk.getWcjafbl());
        		// 项目整体完成计量本期
        		BigDecimal xmztwcjlbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbq(xmztwcjlbq);
        		// 项目整体完成计量本季
        		BigDecimal xmztwcjlbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbj(xmztwcjlbj);
        		// 项目整体完成计量本年
        		BigDecimal xmztwcjlbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlbn(xmztwcjlbn);
        		//  项目整体完成计量开累
        		BigDecimal xmztwcjlkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmztwcjlkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setXmztwcjlkl(xmztwcjlkl);
        		// 项目整体完成计量比例
        		newzjTzInvXmtzqk.setXmztwcjlbl(dbzjTzInvXmtzqk.getXmztwcjlbl());
        		// 项目整体支出建安费本期
        		BigDecimal zcjafbq  = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbq(zcjafbq);
        		// 项目整体支出建安费本季
        		BigDecimal zcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbj(zcjafbj);
        		// 项目整体支出建安费本年
        		BigDecimal zcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafbn(zcjafbn);
        		// 项目整体支出建安费开累
        		BigDecimal zcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZcjafkl(zcjafkl);
        		// 项目整体支出建安费比例
        		newzjTzInvXmtzqk.setZcjafbl(dbzjTzInvXmtzqk.getZcjafbl());
        		// 其中一公局完成建安费本期
        		BigDecimal ygjjtwcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbq(ygjjtwcjafbq);
        		// 其中一公局完成建安费本季
        		BigDecimal ygjjtwcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbj(ygjjtwcjafbj);
        		// 其中一公局完成建安费本年
        		BigDecimal ygjjtwcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafbn(ygjjtwcjafbn);
        		// 其中一公局完成建安费开累
        		BigDecimal ygjjtwcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjafkl(ygjjtwcjafkl);
        		// 其中一公局完成建安费比例
        		newzjTzInvXmtzqk.setYgjjtwcjafbl(dbzjTzInvXmtzqk.getYgjjtwcjafbl());
        		// 其中一公局完成计量本期
        		BigDecimal ygjjtwcjlbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbq(ygjjtwcjlbq);
        		// 其中一公局完成计量本季
        		BigDecimal ygjjtwcjlbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbj(ygjjtwcjlbj);
        		// 其中一公局完成计量本年
        		BigDecimal ygjjtwcjlbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlbn(ygjjtwcjlbn);
        		// 其中一公局完成计量开累
        		BigDecimal ygjjtwcjlkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjlkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtwcjlkl(ygjjtwcjlkl);
        		// 其中一公局完成计量比例
        		newzjTzInvXmtzqk.setYgjjtwcjlbl(dbzjTzInvXmtzqk.getYgjjtwcjlbl());
        		// 其中对一公局支出建安费本期
        		BigDecimal ygjjtzcjafbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbq(ygjjtzcjafbq);
        		// 其中对一公局支出建安费本季
        		BigDecimal ygjjtzcjafbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbj(ygjjtzcjafbj);
        		// 其中对一公局支出建安费本年
        		BigDecimal ygjjtzcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafbn(ygjjtzcjafbn);
        		// 其中对一公局支出建安费开累
        		BigDecimal ygjjtzcjafkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtzcjafkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setYgjjtzcjafkl(ygjjtzcjafkl);
        		// 其中对一公局支出建安费比例
        		newzjTzInvXmtzqk.setYgjjtzcjafbl(dbzjTzInvXmtzqk.getYgjjtzcjafbl());
        		// 完成征拆费本期
        		BigDecimal wczcfbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbq(wczcfbq);
        		// 完成征拆费本季
        		BigDecimal wczcfbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbj(wczcfbj);
        		// 完成征拆费本年
        		BigDecimal wczcfbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfbn(wczcfbn);
        		// 完成征拆费开累
        		BigDecimal wczcfkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getWczcfkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setWczcfkl(wczcfkl);
        		// 完成征拆费比例
        		newzjTzInvXmtzqk.setWczcfbl(dbzjTzInvXmtzqk.getWczcfbl());
        		// 支出征拆费本期
        		BigDecimal zczcfbq = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbq(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbq(zczcfbq);
        		// 支出征拆费本季
        		BigDecimal zczcfbj = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbj(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbj(zczcfbj);
        		// 支出征拆费本年
        		BigDecimal zczcfbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfbn(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfbn(zczcfbn);
        		// 支出征拆费开累
        		BigDecimal zczcfkl = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZczcfkl(), new BigDecimal(10000), 2);
        		newzjTzInvXmtzqk.setZczcfkl(zczcfkl);
        		// 支出征拆费比例
        		newzjTzInvXmtzqk.setZczcfbl(dbzjTzInvXmtzqk.getZczcfbl());
        		
        		newzjTzInvXmtzqkMonthlyReportList.add(newzjTzInvXmtzqk);
        	}		
	}
        return newzjTzInvXmtzqkMonthlyReportList;
}
	/**
	 * 首页进度预警计划进度
	 * 
	 */
	@Override
	public ResponseEntity getHomeProgressWarningPlanningProgress(ZjTzInvXmtzqk zjTzInvXmtzqk) {
			if (zjTzInvXmtzqk == null) {
				zjTzInvXmtzqk = new ZjTzInvXmtzqk();
			}
		 	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userId = TokenUtils.getUserId(request);
	        String ext1 = TokenUtils.getExt1(request);
	        // 不是集团的人员时
	        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
	        	//其他身份
	        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
	        } 
	        if (!StrUtil.isEmpty(DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM"))) {
	        	String period = DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM");
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime(zjTzInvXmtzqk.getPeriod());
	        	int month = cal.get(cal.MONTH) + 1;
	        	int quarter = 0;
	        	//判断季度
	        	if (month >= 1 && month <= 3) {
	        		zjTzInvXmtzqk.setQuarter("1");
				}else if (month >= 4 && month <= 6) {
	        		zjTzInvXmtzqk.setQuarter("2");
	        	}else if (month >= 7 && month <= 9) {
	        		zjTzInvXmtzqk.setQuarter("3");
	        	}else if (month >= 10 && month <= 12) {
	        		zjTzInvXmtzqk.setQuarter("4");
				}
	        	zjTzInvXmtzqk.setPeriodValue(period);
			}
	        if (DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod())) {
	        	zjTzInvXmtzqk.setColourFlag("red");
			}
	        List<ZjTzInvXmtzqk> dbPlanningProgressList = zjTzInvXmtzqkMapper.selectHomeProgressWarningPlanningProgressWc(zjTzInvXmtzqk);
	        int quarter = DateUtil.quarter(zjTzInvXmtzqk.getPeriod());
	        if (dbPlanningProgressList != null && dbPlanningProgressList.size() > 0) {
				for (ZjTzInvXmtzqk zjTzInvXmtzqk2 : dbPlanningProgressList) {
					if (DateUtil.year(new Date()) == DateUtil.year(zjTzInvXmtzqk.getPeriod()) && quarter != 1 && zjTzInvXmtzqk2.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0) {
						zjTzInvXmtzqk2.setColourFlag("yellow");
					}else if (DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod()) && quarter != 1 && zjTzInvXmtzqk2.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0 && zjTzInvXmtzqk2.getSnztzbfb().compareTo(new BigDecimal(0.6)) >= 0) {
						zjTzInvXmtzqk2.setColourFlag("yellow");
					}else if (DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod()) && zjTzInvXmtzqk2.getSnztzbfb().compareTo(new BigDecimal(0.6)) < 0) {
						zjTzInvXmtzqk2.setColourFlag("red");
					}else {
						zjTzInvXmtzqk2.setColourFlag("green");
					}
					BigDecimal gqbl = zjTzInvXmtzqk2.getGqbl();
					zjTzInvXmtzqk2.setGqbl(gqbl.setScale(2, BigDecimal.ROUND_HALF_UP));
					//本年完成权益投资百分比
					BigDecimal bnqytzbfb = CalcUtils.calcMultiply(zjTzInvXmtzqk2.getBnqytzbfb(), new BigDecimal(100));
					zjTzInvXmtzqk2.setBnqytzbfb(bnqytzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
					//本年完成局建安费百分比
					BigDecimal bnjjafbfb = CalcUtils.calcMultiply(zjTzInvXmtzqk2.getBnjjafbfb(), new BigDecimal(100));
					zjTzInvXmtzqk2.setBnjjafbfb(bnjjafbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
					//截至上季度完成权益投资计划百分比
					BigDecimal bjqytzbfb = CalcUtils.calcMultiply(zjTzInvXmtzqk2.getBjqytzbfb(), new BigDecimal(100));
					zjTzInvXmtzqk2.setBjqytzbfb(bjqytzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
					//截至上季度完成局建安费计划百分比
					BigDecimal bjjjafbfb = CalcUtils.calcMultiply(zjTzInvXmtzqk2.getBjjjafbfb(), new BigDecimal(100));
					zjTzInvXmtzqk2.setBjjjafbfb(bjjjafbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
	        
	        return repEntity.ok(dbPlanningProgressList);
	}
	/**
	 * 
	 * 导出计划进度报表
	 */
	@Override
	public ResponseEntity exportHomeProgressWarningPlanningProgress(ZjTzInvXmtzqk zjTzInvXmtzqk,
			HttpServletResponse response) {
//        HttpServletResponse response = new HttpServletResponse();
        if (zjTzInvXmtzqk == null) {
        	zjTzInvXmtzqk = new ZjTzInvXmtzqk();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        } 
        if (!StrUtil.isEmpty(DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM"))) {
        	String period = DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM");
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(zjTzInvXmtzqk.getPeriod());
        	int month = cal.get(cal.MONTH) + 1;
        	int quarter = 0;
        	//判断季度
        	if (month >= 1 && month <= 3) {
        		zjTzInvXmtzqk.setQuarter("1");
			}else if (month >= 4 && month <= 6) {
        		zjTzInvXmtzqk.setQuarter("2");
        	}else if (month >= 7 && month <= 9) {
        		zjTzInvXmtzqk.setQuarter("3");
        	}else if (month >= 10 && month <= 12) {
        		zjTzInvXmtzqk.setQuarter("4");
			}
        	zjTzInvXmtzqk.setPeriodValue(period);
		}else {
			Date date = new Date();
			String period = DateUtil.format(date, "yyyyMM");
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(date);
        	int month = cal.get(cal.MONTH) + 1;
        	int quarter = 0;
        	//判断季度
        	if (month >= 1 && month <= 3) {
        		zjTzInvXmtzqk.setQuarter("1");
			}else if (month >= 4 && month <= 6) {
        		zjTzInvXmtzqk.setQuarter("2");
        	}else if (month >= 7 && month <= 9) {
        		zjTzInvXmtzqk.setQuarter("3");
        	}else if (month >= 10 && month <= 12) {
        		zjTzInvXmtzqk.setQuarter("4");
			}
        	zjTzInvXmtzqk.setPeriodValue(period);
		}
        if (zjTzInvXmtzqk.getPeriod() != null && DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod())) {
        	zjTzInvXmtzqk.setColourFlag("red");
		}
        // 获取数据
        List<ZjTzInvXmtzqk> dbPlanningProgressList = zjTzInvXmtzqkMapper.selectHomeProgressWarningPlanningProgressWc(zjTzInvXmtzqk);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        String currSort = "";
        String warnSort = "";
        if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "1")) {
        	currSort = "项目编号";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "2")) {
			currSort = "年度计划完成权益投资金额升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "3")) {
			currSort = "年度计划完成权益投资金额降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "4")) {
			currSort = "年度计划完成局建安费金额升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "5")) {
			currSort = "年度计划完成局建安费金额降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "6")) {
			currSort = "本年完成权益投资金额升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "7")) {
			currSort = "本年完成权益投资金额降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "8")) {
			currSort = "本年完成局建安费金额升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "9")) {
			currSort = "本年完成局建安费金额降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "10")) {
			currSort = "本年完成权益投资百分比升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "11")) {
			currSort = "本年完成权益投资百分比降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "12")) {
			currSort = "本年完成局建安费百分比升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "13")) {
			currSort = "本年完成局建安费百分比降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "14")) {
			currSort = "上季度完成权益投资百分比升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "15")) {
			currSort = "上季度完成权益投资百分比降序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "16")) {
			currSort = "上季度完成局建安费百分比升序";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "17")) {
			currSort = "上季度完成局建安费百分比降序";
		}
        if (StrUtil.equals(zjTzInvXmtzqk.getWarnSelect(), "yellow")) {
        	warnSort = "黄色预警";
		}else if (StrUtil.equals(zjTzInvXmtzqk.getWarnSelect(), "red")) {
			warnSort = "红色预警";
		}
        
        List<?> row1 = CollUtil.newArrayList("截至日期："+DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyy-MM"),"", "当前排序："+currSort, "","","预警筛选："+warnSort,"","","管理单位筛选："+zjTzInvXmtzqk.getComname(),"","","",
                "","单位：万元");
        List<?> row2 = CollUtil.newArrayList("预警","项目编号", "项目名称", "项目简称","股权比例","管理单位","年度计划完成权益投资","年度计划完成局建安费","本年完成权益投资","本年完成局建安费","本年完成权益投资占计划百分比","本年完成局建安费占计划百分比",
                "截至上季度完成权益投资季度计划百分比","截至上季度完成局建安费季度计划百分比");
        rowsList.add(row1);
        rowsList.add(row2);
        
        int quarter = DateUtil.quarter(zjTzInvXmtzqk.getPeriod());
        // 数据装载（与上面的表头对应）
        if(dbPlanningProgressList != null && dbPlanningProgressList.size()>0) {
            for(ZjTzInvXmtzqk dbzjTzInvXmtzqk:dbPlanningProgressList) {
            	String colourFlag = "";
            	if (DateUtil.year(new Date()) == DateUtil.year(zjTzInvXmtzqk.getPeriod()) && quarter != 1 && dbzjTzInvXmtzqk.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0) {
            		colourFlag = "☆";//空星代表黄
				}else if (DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod()) && quarter != 1 && dbzjTzInvXmtzqk.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0 && dbzjTzInvXmtzqk.getSnztzbfb().compareTo(new BigDecimal(0.6)) >= 0) {
					colourFlag = "☆";
				}else if (DateUtil.year(new Date()) != DateUtil.year(zjTzInvXmtzqk.getPeriod()) && dbzjTzInvXmtzqk.getSnztzbfb().compareTo(new BigDecimal(0.6)) < 0) {
					colourFlag = "★";//实星代表红
				}else {
					colourFlag = "";//绿色空着
				}
                rowsList.add(CollUtil.newArrayList(
                		colourFlag,
                		dbzjTzInvXmtzqk.getProNum(),
                		dbzjTzInvXmtzqk.getProName(),
                		dbzjTzInvXmtzqk.getShortName(),
                		dbzjTzInvXmtzqk.getGqbl(),
                		dbzjTzInvXmtzqk.getComname(),
                		dbzjTzInvXmtzqk.getBnjhwcqytz(),
                		dbzjTzInvXmtzqk.getYgjbnjhwcja(),
                		dbzjTzInvXmtzqk.getQytzwcbn(),
                		dbzjTzInvXmtzqk.getYgjjtwcjafbn(),
                		dbzjTzInvXmtzqk.getBnqytzbfb(),
                		dbzjTzInvXmtzqk.getBnjjafbfb(),
                		dbzjTzInvXmtzqk.getBjqytzbfb(),
                		dbzjTzInvXmtzqk.getBjjjafbfb()

                        )
                );
            }

            // 报表名称
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "首页导出计划进度表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            if (StrUtil.equals(zjTzInvXmtzqk.getSortType(), "1")) {
            	//定义启始行
            	int index = 2;
            	int index2 = 2;
            	//按照管理单位分组数据汇总处理
//            	Map<String, List<ZjTzInvXmtzqk>> companyNameGroupList = dbPlanningProgressList.stream().sorted(Comparator.comparing(ZjTzInvXmtzqk::getComname)).collect(Collectors.groupingBy(ZjTzInvXmtzqk::getComname, LinkedHashMap::new, Collectors.toList()));
            Map<String, List<ZjTzInvXmtzqk>> companyNameGroupList =
            		dbPlanningProgressList.stream().collect(Collectors.groupingBy(dbzjTzInvXmtzqk->dbzjTzInvXmtzqk.getComname(),LinkedHashMap::new,Collectors.toList()));
            	for (Map.Entry<String, List<ZjTzInvXmtzqk>> listEntry : companyNameGroupList.entrySet()) {
            		List<ZjTzInvXmtzqk> companyNameList = listEntry.getValue();
            		//根据数据条数设置合并单元格信息
            		if (companyNameList.size() == 1) {
            			//一条数据不合并
            			index = index + companyNameList.size();
            			index2 = index2 + companyNameList.size();
            		}else {
            			//规则编写
            			writer.merge(index, index + companyNameList.size() - 1, 5, 5, null, true);
            			index = index + companyNameList.size();
            		}
            	}
			}
            writer.setColumnWidth(0, 20);//预警：第1列9px宽
            writer.setColumnWidth(1, 13);//项目编号：第1列9px宽
            writer.setColumnWidth(2, 50);//项目名称：第1列9px宽
            writer.setColumnWidth(3, 25);//项目简称
            writer.setColumnWidth(4, 13);//股权比例
            writer.setColumnWidth(5, 17);//管理单位
            writer.setColumnWidth(6, 20);//年度计划完成权益投资
            writer.setColumnWidth(7, 20);//年度计划完成局建安费
            writer.setColumnWidth(8, 20);//本年完成权益投资
            writer.setColumnWidth(9, 20);//本年完成局建安费
            writer.setColumnWidth(10, 30);//本年完成权益投资占计划百分比
            writer.setColumnWidth(11, 30);//本年完成局建安费占计划百分比
            writer.setColumnWidth(12, 37);//截至上季度完成权益投资季度计划百分比
            writer.setColumnWidth(13, 37);//截至上季度完成局建安费季度计划百分比
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
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
	
	/**
	 * 首页计划进度考核排名
	 * 
	 */
	@Override
	public ResponseEntity getHomeProgressWarningChecking(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        String year = zjTzInvXmtzqk.getEndTime().substring(0, 4);
        if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第一季度")) {
			zjTzInvXmtzqk.setQuarter("1");
        }else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第二季度")) {
			zjTzInvXmtzqk.setQuarter("2");
		}else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第三季度")) {
			zjTzInvXmtzqk.setQuarter("3");
		}else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第四季度")) {
        	zjTzInvXmtzqk.setQuarter("4");
        }
        zjTzInvXmtzqk.setPeriodValue(year);
        List<ZjTzInvXmtzqk> warningCheckingList = Lists.newArrayList();
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        	warningCheckingList = zjTzInvXmtzqkMapper.selectHomeProgressWarningCheckingNotAdmin(zjTzInvXmtzqk);
        }else {
			
        	warningCheckingList = zjTzInvXmtzqkMapper.selectHomeProgressWarningCheckingAdmin(zjTzInvXmtzqk);
		}
		
		if (warningCheckingList != null && warningCheckingList.size() > 0) {
			for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : warningCheckingList) {
				BigDecimal bjqytzbfb = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getBjqytzbfb(), new BigDecimal(100));
				dbzjTzInvXmtzqk.setBjqytzbfb(bjqytzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bjjjafbfb = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getBjjjafbfb(), new BigDecimal(100));
				dbzjTzInvXmtzqk.setBjjjafbfb(bjjjafbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bnztzbfb = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getBnztzbfb(), new BigDecimal(100));
				dbzjTzInvXmtzqk.setBnztzbfb(bnztzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				if (!StrUtil.equals(zjTzInvXmtzqk.getDataType(), "3")) {
					ZjTzInvXmtzqk zjTzInvXmtzqk2 = new ZjTzInvXmtzqk();
					
					zjTzInvXmtzqk2.setProjectId(dbzjTzInvXmtzqk.getProjectId());
					zjTzInvXmtzqk2.setPeriodValue(zjTzInvXmtzqk.getPeriodValue());
					zjTzInvXmtzqk2.setQuarter(zjTzInvXmtzqk.getQuarter());
					zjTzInvXmtzqk2.setDataType(zjTzInvXmtzqk.getDataType());
					
					ZjTzInvXmtzqk dbZjTzInvXmtzqk2 = zjTzInvXmtzqkMapper.selectHomeProgressWarningCheckingSj(zjTzInvXmtzqk2);
					if (dbZjTzInvXmtzqk2 == null) {
						dbZjTzInvXmtzqk2 = new ZjTzInvXmtzqk();
					}
					dbzjTzInvXmtzqk.setSjqytzpm(dbZjTzInvXmtzqk2.getSjqytzpm());
					dbzjTzInvXmtzqk.setSjjjafpm(dbZjTzInvXmtzqk2.getSjjjafpm());
				}
			}
		}
		return repEntity.ok(warningCheckingList);
	}
	
	/**
	 * 首页计划进度预警信息
	 * @param zjTzInvXmtzqk
	 */
	@Override
	public ResponseEntity getHomeProgressWarningInfo(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        String year = zjTzInvXmtzqk.getEndTime().substring(0, 4);
        if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第一季度")) {
			zjTzInvXmtzqk.setQuarter("1");
        }else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第二季度")) {
			zjTzInvXmtzqk.setQuarter("2");
		}else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第三季度")) {
			zjTzInvXmtzqk.setQuarter("3");
		}else if (StrUtil.contains(zjTzInvXmtzqk.getEndTime(), "第四季度")) {
        	zjTzInvXmtzqk.setQuarter("4");
        }
        zjTzInvXmtzqk.setPeriodValue(year);
        if (StrUtil.equals(zjTzInvXmtzqk.getDataType(), "3") && StrUtil.equals(year, Integer.toString(DateUtil.year(new Date())))) {
        	return repEntity.layerMessage("NO", "无数据");
        }
		List<ZjTzInvXmtzqk> warningInfoList = zjTzInvXmtzqkMapper.selectHomeProgressWarningInfo(zjTzInvXmtzqk);
		if (warningInfoList != null && warningInfoList.size() > 0) {
			for (ZjTzInvXmtzqk dbZjTzInvXmtzqk : warningInfoList) {
				BigDecimal bjqytzbfb = CalcUtils.calcMultiply(dbZjTzInvXmtzqk.getBjqytzbfb(), new BigDecimal(100));
				dbZjTzInvXmtzqk.setBjqytzbfb(bjqytzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bjjjafbfb = CalcUtils.calcMultiply(dbZjTzInvXmtzqk.getBjjjafbfb(), new BigDecimal(100));
				dbZjTzInvXmtzqk.setBjjjafbfb(bjjjafbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal bnztzbfb = CalcUtils.calcMultiply(dbZjTzInvXmtzqk.getBnztzbfb(), new BigDecimal(100));
				dbZjTzInvXmtzqk.setBnztzbfb(bnztzbfb.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(warningInfoList);
	}
	/**
	 * 首页计划进度年度目标完成情况
	 * 
	 */
	@Override
	public ResponseEntity getHomeProgressWarningCompleteStatus(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        ZjTzInvXmtzqk dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        	ZjTzInvXmtzqk returnJhValue = zjTzInvXmtzqkMapper.selectHomeProgressWarningCompleteStatusNotAdmin(zjTzInvXmtzqk);
        	ZjTzInvXmtzqk returnWcValue = zjTzInvXmtzqkMapper.selectHomeProgressWarningCompleteStatusAdmin(zjTzInvXmtzqk);
        	// 本年计划权益投资总额
    		BigDecimal bnjhwcqytz = CalcUtils.calcDivide(returnJhValue.getBnjhwcqytz(), new BigDecimal(10000));
        	dbzjTzInvXmtzqk.setBnjhwcqytz(bnjhwcqytz.setScale(2, BigDecimal.ROUND_HALF_UP));
        	// 本年计划局建安费
    		BigDecimal ygjbnjhwcja = CalcUtils.calcDivide(returnJhValue.getYgjbnjhwcja(), new BigDecimal(10000));
        	dbzjTzInvXmtzqk.setYgjbnjhwcja(ygjbnjhwcja.setScale(2, BigDecimal.ROUND_HALF_UP));
        	// 本年完成权益投资总额
    		BigDecimal qytzwcbn = CalcUtils.calcDivide(returnWcValue.getQytzwcbn(), new BigDecimal(10000));
        	dbzjTzInvXmtzqk.setQytzwcbn(qytzwcbn.setScale(2, BigDecimal.ROUND_HALF_UP));
        	// 本年完成局建安费
    		BigDecimal ygjjtwcjafbn = CalcUtils.calcDivide(returnWcValue.getYgjjtwcjafbn(), new BigDecimal(10000));
        	dbzjTzInvXmtzqk.setYgjjtwcjafbn(ygjjtwcjafbn.setScale(2, BigDecimal.ROUND_HALF_UP));
        }else {
			//局用户读取表
        	dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectHomeProgressWarningCompleteStatusAdmin(zjTzInvXmtzqk);
        	if (dbzjTzInvXmtzqk != null) {
        		// 本年完成权益投资总额
        		BigDecimal qytzwcbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getQytzwcbn(), new BigDecimal(10000));
        		dbzjTzInvXmtzqk.setQytzwcbn(qytzwcbn.setScale(2, BigDecimal.ROUND_HALF_UP));
        		// 本年计划权益投资总额
        		BigDecimal bnjhwcqytz = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getBnjhwcqytz(), new BigDecimal(10000));
        		dbzjTzInvXmtzqk.setBnjhwcqytz(bnjhwcqytz.setScale(2, BigDecimal.ROUND_HALF_UP));
        		// 本年完成局建安费
        		BigDecimal ygjjtwcjafbn = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjjtwcjafbn(), new BigDecimal(10000));
        		dbzjTzInvXmtzqk.setYgjjtwcjafbn(ygjjtwcjafbn.setScale(2, BigDecimal.ROUND_HALF_UP));
        		// 本年计划局建安费
        		BigDecimal ygjbnjhwcja = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getYgjbnjhwcja(), new BigDecimal(10000));
        		dbzjTzInvXmtzqk.setYgjbnjhwcja(ygjbnjhwcja.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		if (dbzjTzInvXmtzqk == null) {
			dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		return repEntity.ok(dbzjTzInvXmtzqk);
	}
	/**
	 * 管理单位
	 */
	@Override
	public ResponseEntity getHomeChartComnameStatis(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        String period = DateUtil.format(zjTzInvXmtzqk.getPeriod(), "yyyyMM");
        if (period != null) {
        	zjTzInvXmtzqk.setPeriodValue(period);
        }
		List<ZjTzInvXmtzqk> comnameStatisList = zjTzInvXmtzqkMapper.selectHomeChartComnameStatis(zjTzInvXmtzqk);
		if (comnameStatisList != null && comnameStatisList.size() > 0) {
			for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : comnameStatisList) {
				BigDecimal xmzht = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmzht(), new BigDecimal(10000)); 
				dbzjTzInvXmtzqk.setXmzht(xmzht.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal xmzja = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getXmzja(), new BigDecimal(10000));
				dbzjTzInvXmtzqk.setXmzja(xmzja.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(comnameStatisList);
	}
	
	/**
	 * 
	 * 首页图表数据趋势数据
	 */
	@Override
	public ResponseEntity getHomeChartTrendData(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		 int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear - 4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			JSONArray jsonArray = new JSONArray();
			JSONObject json = new JSONObject();

			JSONArray jsonArrayN = new JSONArray();
			JSONArray jsonArrayV = new JSONArray();
			JSONArray jsonArrayT = new JSONArray();
			List<Object> list = Lists.newArrayList();

			// 签约合同额
			List<Object> listItem1 = Lists.newArrayList();
			List<Object> tblistItem1 = Lists.newArrayList();
			JSONObject jsonItemHte = new JSONObject();
			
			// 完成投资额
			List<Object> listItem2 = Lists.newArrayList();
			List<Object> tblistItem2 = Lists.newArrayList();
			JSONObject jsonItemTze = new JSONObject();
			
			// 本年权益签约合同额
			List<Object> listItem3 = Lists.newArrayList();
			List<Object> tblistItem3 = Lists.newArrayList();
			JSONObject jsonItemQyHte = new JSONObject();
			
			// 本年完成权益投资额
			List<Object> listItem4 = Lists.newArrayList();
			List<Object> tblistItem4 = Lists.newArrayList();
			JSONObject jsonItemQyTze = new JSONObject();
			
			
			//当年数据
			if (StrUtil.equals(zjTzInvXmtzqk.getData(), "1")) {

				// 按年显示
				for (int year = startYear; year <= endYear; year++) {
					list.add(year + "年");
					
					zjTzInvXmtzqk.setPeriodValue(String.valueOf(year));
					ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectHomeChartTrendData(zjTzInvXmtzqk);

					if (dbzjTzInvXmtzqk != null) {
							jsonItemHte.set("name", "本年签约合同额");
							BigDecimal bnamount1 = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getBnamount1(), new BigDecimal(10000));
							listItem1.add(bnamount1.setScale(2, BigDecimal.ROUND_HALF_UP));
							BigDecimal tbamount = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbamount(), new BigDecimal(100));
							tblistItem1.add(tbamount.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemTze.set("name", "本年完成投资额");
							listItem2.add(dbzjTzInvXmtzqk.getTzwcbn());
							BigDecimal tbtzwc = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbtzwc(), new BigDecimal(100));
							tblistItem2.add(tbtzwc.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemQyHte.set("name", "本年权益签约合同额");
							BigDecimal bnamount2 = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getBnamount2(), new BigDecimal(10000));
							listItem3.add(bnamount2.setScale(2, BigDecimal.ROUND_HALF_UP));
							BigDecimal tbqyamount = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbqyamount(), new BigDecimal(100));
							tblistItem3.add(tbqyamount.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemQyTze.set("name", "本年完成权益投资额");
							listItem4.add(dbzjTzInvXmtzqk.getQytzwcbn());
							BigDecimal tbqytzwc = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbqytzwc(), new BigDecimal(100));
							tblistItem4.add(tbqytzwc.setScale(2, BigDecimal.ROUND_HALF_UP));
							
					}else {
						jsonItemHte.set("name", "本年签约合同额");
						listItem1.add(0);
						tblistItem1.add(0.00);

						jsonItemTze.set("name", "本年完成投资额");
						listItem2.add(0);
						tblistItem2.add(0.00);
						
						jsonItemQyHte.set("name", "本年权益签约合同额");
						listItem3.add(0);
						tblistItem3.add(0.00);
						
						jsonItemQyTze.set("name", "本年完成权益投资额");
						listItem4.add(0);
						tblistItem4.add(0.00);
					}
				}

				jsonItemHte.set("hte", listItem1);
				jsonItemHte.set("tbamount", tblistItem1);
				jsonArrayV.add(jsonItemHte);
				
				jsonItemTze.set("tze", listItem2);
				jsonItemTze.set("tbtzwc", tblistItem2);
				jsonArrayV.add(jsonItemTze);
				
				jsonItemQyHte.set("qyhte", listItem3);
				jsonItemQyHte.set("tbqyamount", tblistItem3);
				jsonArrayV.add(jsonItemQyHte);
				
				jsonItemQyTze.set("qytze", listItem4);
				jsonItemQyTze.set("tbqytzwc", tblistItem4);
				jsonArrayV.add(jsonItemQyTze);

				json.set("DataOne", list);
				json.set("DataTwo", jsonArrayN);
				json.set("DataThree", jsonArrayV);
			}
			else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "2")) {
				// 按开累显示
				for (int year = startYear; year <= endYear; year++) {
					list.add(year + "年");
					
					zjTzInvXmtzqk.setPeriodValue(String.valueOf(year));
					ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectHomeChartTrendData(zjTzInvXmtzqk);

					if (dbzjTzInvXmtzqk != null) {
							jsonItemHte.set("name", "本年开累签约合同额");
							BigDecimal klamount1 = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getKlamount1(), new BigDecimal(10000));
							listItem1.add(klamount1.setScale(2, BigDecimal.ROUND_HALF_UP));
							BigDecimal tbamount = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbamount(), new BigDecimal(100));
							tblistItem1.add(tbamount.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemTze.set("name", "本年开累完成投资额");
							listItem2.add(dbzjTzInvXmtzqk.getTzwckl());
							BigDecimal tbtzwc = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbtzwc(), new BigDecimal(100));
							tblistItem2.add(tbtzwc.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemQyHte.set("name", "本年开累权益签约合同额");
							BigDecimal klamount2 = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getKlamount2(), new BigDecimal(10000));
							listItem3.add(klamount2.setScale(2, BigDecimal.ROUND_HALF_UP));
							BigDecimal tbqyamount = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbqyamount(), new BigDecimal(100));
							tblistItem3.add(tbqyamount.setScale(2, BigDecimal.ROUND_HALF_UP));
							
							jsonItemQyTze.set("name", "本年开累完成权益投资额");
							listItem4.add(dbzjTzInvXmtzqk.getQytzwckl());
							BigDecimal tbqytzwc = CalcUtils.calcMultiply(dbzjTzInvXmtzqk.getTbqytzwc(), new BigDecimal(100));
							tblistItem4.add(tbqytzwc.setScale(2, BigDecimal.ROUND_HALF_UP));
					}else {
						jsonItemHte.set("name", "本年开累签约合同额");
						listItem1.add(0);
						tblistItem1.add(0.00);

						jsonItemTze.set("name", "本年开累完成投资额");
						listItem2.add(0);
						tblistItem2.add(0.00);
						
						jsonItemQyHte.set("name", "本年开累权益签约合同额");
						listItem3.add(0);
						tblistItem3.add(0.00);
						
						jsonItemQyTze.set("name", "本年开累完成权益投资额");
						listItem4.add(0);
						tblistItem4.add(0.00);
					}
				}

				jsonItemHte.set("hte", listItem1);
				jsonItemHte.set("tbamount", tblistItem1);
				jsonArrayV.add(jsonItemHte);

				jsonItemTze.set("tze", listItem2);
				jsonItemTze.set("tbtzwc", tblistItem2);
				jsonArrayV.add(jsonItemTze);
				
				jsonItemQyHte.set("qyhte", listItem3);
				jsonItemQyHte.set("tbqyamount", tblistItem3);
				jsonArrayV.add(jsonItemQyHte);
				
				jsonItemQyTze.set("qytze", listItem4);
				jsonItemQyTze.set("tbqytzwc", tblistItem4);
				jsonArrayV.add(jsonItemQyTze);

				json.set("DataOne", list);
				json.set("DataTwo", jsonArrayN);
				json.set("DataThree", jsonArrayV);
			}
			jsonArray.add(json);
		return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 首页地域总览-总合同/总建安
	 */
	@Override
	public ResponseEntity getHomeRegionalOverviewZhtAndZja(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectHomeRegionalOverviewZhtAndZja(zjTzInvXmtzqk);
		Date date = new Date();
		String year = DateUtil.format(date, "yyyy");
		zjTzInvXmtzqk.setPeriodValue(year);
		ZjTzInvXmtzqk dbzjTzInvXmtzqkbn = zjTzInvXmtzqkMapper.selectHomeRegionalOverviewTzwcbnAndWcjafbn(zjTzInvXmtzqk);
		if (dbzjTzInvXmtzqk == null) {
			dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		BigDecimal zht = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZht(), new BigDecimal(10000));
		BigDecimal zja = CalcUtils.calcDivide(dbzjTzInvXmtzqk.getZja(), new BigDecimal(10000));
		if (dbzjTzInvXmtzqkbn == null) {
			dbzjTzInvXmtzqkbn = new ZjTzInvXmtzqk();
		}
		BigDecimal tzwcbhl = CalcUtils.calcMultiply(dbzjTzInvXmtzqkbn.getTzwcbhl(), new BigDecimal(100));
		dbzjTzInvXmtzqkbn.setTzwcbhl(tzwcbhl.setScale(2, BigDecimal.ROUND_HALF_UP));
		BigDecimal wcjafbhl = CalcUtils.calcMultiply(dbzjTzInvXmtzqkbn.getWcjafbhl(), new BigDecimal(100));
		dbzjTzInvXmtzqkbn.setWcjafbhl(wcjafbhl.setScale(2, BigDecimal.ROUND_HALF_UP));
		//总合同额
		dbzjTzInvXmtzqkbn.setZht(zht.setScale(2, BigDecimal.ROUND_HALF_UP));
		//总建安费
		dbzjTzInvXmtzqkbn.setZja(zja.setScale(2, BigDecimal.ROUND_HALF_UP));
		return repEntity.ok(dbzjTzInvXmtzqkbn);
	}
	/**
	 * 
	 * 首页地域总览-全国/省份地图
	 */
	@Override
	public ResponseEntity getHomeRegionalOverviewMap(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		List<ZjTzInvXmtzqk> newzjTzInvXmtzqkList = Lists.newArrayList();
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
        JSONArray jsonArray1 = new JSONArray();
 		if (!StrUtil.isEmpty(zjTzInvXmtzqk.getAreaName())) {
 			JSONObject jsonObject2 = new JSONObject();
			ZjTzInvXmtzqk dbzjTzInvXmtzqkProvince = zjTzInvXmtzqkMapper.selectHomeRegionalOverviewProvinceMap(zjTzInvXmtzqk);
			jsonObject2.set("value", dbzjTzInvXmtzqkProvince);
			jsonArray1.add(jsonObject2);
 		}else {
 			List<ZjTzInvXmtzqk> zjTzInvXmtzqkCountryList = zjTzInvXmtzqkMapper.selectHomeRegionalOverviewCountryMap(zjTzInvXmtzqk);
 			JSONArray jsonArray = new JSONArray();
 			if (zjTzInvXmtzqkCountryList != null && zjTzInvXmtzqkCountryList.size() > 0) {
 				String mapXY = "{\"澳门特别行政区\":[113.561363,22.195837],\"香港特别行政区\":[114.176926,22.282885],\"新疆\":[85.496065,42.578833],\"宁夏省\":[106.248359,38.495858],\"西藏\":[84.539405,32.155006],\"广西省\":[108.676659,23.939414],\"内蒙古\":[111.761683,40.851539],\"台湾省\":[121.073212,23.814022],\"青海省\":[95.577783,36.784717],\"甘肃省\":[95.945729,40.30888],\"陕西省\":[108.823837,34.502288],\"云南省\":[99.772367,24.74851],\"贵州省\":[106.76334,26.747834],\"四川省\":[103.010291,31.021112],\"海南省\":[109.927675,19.330953],\"广东省\":[113.607134,23.465049],\"湖南省\":[111.620226,28.126687],\"湖北省\":[112.724064,31.589801],\"河南省\":[113.165599,34.074708],\"山东省\":[117.507361,36.42872],\"江西省\":[115.741221,28.387337],\"福建省\":[118.022485,26.483236],\"安徽省\":[117.213004,32.21759],\"浙江省\":[119.862215,29.165396],\"江苏省\":[119.715037,33.336628],\"黑龙江省\":[128.104204,47.629239],\"吉林省\":[126.558831,43.603562],\"辽宁省\":[122.879372,41.702523],\"山西省\":[112.552703,37.881654],\"河北省\":[114.509573,38.050475],\"重庆\":[106.468983,29.745065],\"上海\":[121.481177,31.274291],\"天津\":[117.229767,39.0071],\"北京\":[116.411087,39.912695]}";
 				JSONObject mapJSON = new JSONObject(mapXY);
 				for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : zjTzInvXmtzqkCountryList) {
 					JSONObject jsonObject1 = new JSONObject();
 					jsonObject1.set("name", dbzjTzInvXmtzqk.getAreaName());
 					jsonObject1.set("value", dbzjTzInvXmtzqk.getCount());
 					jsonObject1.set("map", mapJSON.getJSONArray(dbzjTzInvXmtzqk.getAreaName()));
 					jsonArray1.add(jsonObject1);
				}
			}
		}
		
		return repEntity.ok(jsonArray1);
	}
	/**
	 * 
	 * 建设页面-产值完成情况
	 */
	@Override
	public ResponseEntity getConstructPageProduction(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
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
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            }
        }
		Date date = new Date();
		String periodValue = DateUtil.format(date, "yyyyMM");
		List<String> periodValueList = Lists.newArrayList();
		if (periodValue != null) { 
			String year = periodValue.substring(0, 4);
        	zjTzInvXmtzqk.setPeriodValue(year);
        	switch (periodValue.substring(4)) {
        	case "01":
        		zjTzInvXmtzqk.setQuarter("1");
        		break;
        	case "02":
        		periodValueList.add(year + "01");
        		zjTzInvXmtzqk.setQuarter("1");
        		break;
			case "03":
				periodValueList.add(year + "01");
				periodValueList.add(year + "02");
				zjTzInvXmtzqk.setQuarter("1");
				break;
			case "04":
				zjTzInvXmtzqk.setQuarter("2");
				break;
			case "05":
				periodValueList.add(year + "04");
				zjTzInvXmtzqk.setQuarter("2");
				break;
			case "06":
				periodValueList.add(year + "04");
				periodValueList.add(year + "05");
				zjTzInvXmtzqk.setQuarter("2");
				break;
			case "07":
				zjTzInvXmtzqk.setQuarter("3");
				break;
			case "08":
				periodValueList.add(year + "07");
				zjTzInvXmtzqk.setQuarter("3");
				break;
			case "09":
				periodValueList.add(year + "07");
				periodValueList.add(year + "08");
				zjTzInvXmtzqk.setQuarter("3");
				break;
			case "10":
				zjTzInvXmtzqk.setQuarter("4");
				break;
			case "11":
				periodValueList.add(year + "10");
				zjTzInvXmtzqk.setQuarter("4");
				break;
			case "12":
				periodValueList.add(year + "10");
				periodValueList.add(year + "11");
				zjTzInvXmtzqk.setQuarter("4");
				break;
			default:
				break;
			}
        }
		List<Object> list = Lists.newArrayList();
		ZjTzInvXmtzqk newzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectAllPeriod(zjTzInvXmtzqk);
		ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectConstructPageProduction(periodValueList, zjTzInvXmtzqk);
		if (newzjTzInvXmtzqk == null) {
			newzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		if (dbzjTzInvXmtzqk == null) {
			dbzjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
		
		JSONArray jsonArray =  new JSONArray();
//		JSONObject jsonItem = new JSONObject();
		// 总投资
		JSONArray jsonArrayZtz =  new JSONArray();
		JSONObject jsonItemZtz1 = new JSONObject();
		jsonItemZtz1.set("name", "总投资完成情况");
		jsonItemZtz1.set("value", newzjTzInvXmtzqk.getAmount1());
		jsonArrayZtz.add(jsonItemZtz1);
		JSONObject jsonItemZtz2 = new JSONObject();
		jsonItemZtz2.set("name", "累计完成投资");
		jsonItemZtz2.set("value", newzjTzInvXmtzqk.getTzwckl());
		jsonArrayZtz.add(jsonItemZtz2);
		JSONObject jsonItemZtz3 = new JSONObject();
		jsonItemZtz3.set("name", "未完成投资金额");
		jsonItemZtz3.set("value", newzjTzInvXmtzqk.getWtzwckl());
		jsonArrayZtz.add(jsonItemZtz3);
		
		jsonArray.add(jsonArrayZtz);
		//总建安
		JSONArray jsonArrayZja =  new JSONArray();
		JSONObject jsonItemZja1 = new JSONObject();
		jsonItemZja1.set("name", "总建安完成情况");
		jsonItemZja1.set("value", newzjTzInvXmtzqk.getAmount3());
		jsonArrayZja.add(jsonItemZja1);
		JSONObject jsonItemZja2 = new JSONObject();
		jsonItemZja2.set("name", "累计完成建安");
		jsonItemZja2.set("value", newzjTzInvXmtzqk.getWcjafkl());
		jsonArrayZja.add(jsonItemZja2);
		JSONObject jsonItemZja3 = new JSONObject();
		jsonItemZja3.set("name", "未完成建安金额");
		jsonItemZja3.set("value", newzjTzInvXmtzqk.getWwcjafkl());
		jsonArrayZja.add(jsonItemZja3);
		
		jsonArray.add(jsonArrayZja);
		//本年投资
		JSONArray jsonArrayBntz =  new JSONArray();
		JSONObject jsonItemBntz1 = new JSONObject();
		jsonItemBntz1.set("name", "本年计划完成总投资");
		jsonItemBntz1.set("value", dbzjTzInvXmtzqk.getBnjhwcztz());
		jsonArrayBntz.add(jsonItemBntz1);
		JSONObject jsonItemBntz2 = new JSONObject();
		jsonItemBntz2.set("name", "本年完成总投资");
		jsonItemBntz2.set("value", dbzjTzInvXmtzqk.getTzwcbn());
		jsonArrayBntz.add(jsonItemBntz2);
		JSONObject jsonItemBntz3 = new JSONObject();
		jsonItemBntz3.set("name", "本年未完成总投资");
		jsonItemBntz3.set("value", dbzjTzInvXmtzqk.getWtzwcbn());
		jsonArrayBntz.add(jsonItemBntz3);
		
		jsonArray.add(jsonArrayBntz);
		//本年建安
		JSONArray jsonArrayBnja =  new JSONArray();
		JSONObject jsonItemBnja1 = new JSONObject();
		jsonItemBnja1.set("name", "本年计划完成建安费");
		jsonItemBnja1.set("value", dbzjTzInvXmtzqk.getBnjhwcjaf());
		jsonArrayBnja.add(jsonItemBnja1);
		JSONObject jsonItemBnja2 = new JSONObject();
		jsonItemBnja2.set("name", "本年完成建安费");
		jsonItemBnja2.set("value", dbzjTzInvXmtzqk.getWcjafbn());
		jsonArrayBnja.add(jsonItemBnja2);
		JSONObject jsonItemBnja3 = new JSONObject();
		jsonItemBnja3.set("name", "本年未完成建安费");
		jsonItemBnja3.set("value", dbzjTzInvXmtzqk.getWwcjafbn());
		jsonArrayBnja.add(jsonItemBnja3);
		
		jsonArray.add(jsonArrayBnja);
		//本季投资
		JSONArray jsonArrayBjtz =  new JSONArray();
		JSONObject jsonItemBjtz1 = new JSONObject();
		jsonItemBjtz1.set("name", "本季计划完成总投资");
		jsonItemBjtz1.set("value", dbzjTzInvXmtzqk.getJhwcztzbj());
		jsonArrayBjtz.add(jsonItemBjtz1);
		JSONObject jsonItemBjtz2 = new JSONObject();
		jsonItemBjtz2.set("name", "本季完成总投资");
		jsonItemBjtz2.set("value", dbzjTzInvXmtzqk.getTzwcbj());
		jsonArrayBjtz.add(jsonItemBjtz2);
		JSONObject jsonItemBjtz3 = new JSONObject();
		jsonItemBjtz3.set("name", "本季未完成总投资");
		jsonItemBjtz3.set("value", dbzjTzInvXmtzqk.getWtzwcbj());
		jsonArrayBjtz.add(jsonItemBjtz3);
		
		jsonArray.add(jsonArrayBjtz);
		//本季建安
		JSONArray jsonArrayBjja =  new JSONArray();
		JSONObject jsonItemBjja1 = new JSONObject();
		jsonItemBjja1.set("name", "本季计划完成建安费");
		jsonItemBjja1.set("value", dbzjTzInvXmtzqk.getJhwcjafbj());
		jsonArrayBjja.add(jsonItemBjja1);
		JSONObject jsonItemBjja2 = new JSONObject();
		jsonItemBjja2.set("name", "本季完成建安费");
		jsonItemBjja2.set("value", dbzjTzInvXmtzqk.getWcjafbj());
		jsonArrayBjja.add(jsonItemBjja2);
		JSONObject jsonItemBjja3 = new JSONObject();
		jsonItemBjja3.set("name", "本季未完成建安费");
		jsonItemBjja3.set("value", dbzjTzInvXmtzqk.getWwcjafbj());
		jsonArrayBjja.add(jsonItemBjja3);
		
		jsonArray.add(jsonArrayBjja);
		
		return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 建设页趋势数据
	 */
	@Override
	public ResponseEntity getConstructPageTrendData(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		if (zjTzInvXmtzqk == null) {
			zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
	
	
		List<ZjTzInvXmtzqk> trendDataTzList = Lists.newArrayList();

		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();

		JSONArray jsonArrayN = new JSONArray();
		JSONArray jsonArrayV = new JSONArray();
		List<Object> list = Lists.newArrayList();

		// 投资完成
		List<Object> listItem1 = Lists.newArrayList();
		JSONObject jsonItemTzwc = new JSONObject();

		// 建安完成
		List<Object> listItem2 = Lists.newArrayList();
		JSONObject jsonItemWcjaf = new JSONObject();
		
		//每年每季投资完成情况
		if (StrUtil.equals(zjTzInvXmtzqk.getData(), "0")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-2;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			for (int year = startYear; year <= endYear; year++) {
				zjTzInvXmtzqk.setPeriodValue(String.valueOf(year));
				trendDataTzList = zjTzInvXmtzqkMapper.selectConstructPageTrendDataByQuarter(zjTzInvXmtzqk);
				// 季度
				list.add("1季度");
				list.add("2季度");
				list.add("3季度");
				list.add("4季度");

				// 年份
				JSONObject jsonItemN = new JSONObject();
				jsonItemN.set("value", year);
				jsonArrayN.add(jsonItemN);

				if (trendDataTzList != null && trendDataTzList.size() > 0) {
						
						List<String> strs = new ArrayList<>();
						for (ZjTzInvXmtzqk dbzjTzInvXmtzqk : trendDataTzList) {
							strs.add(dbzjTzInvXmtzqk.getJidu());
						}
						
						for (int i = 1; i <= 4; i++) {
							if (strs.contains(i + "")) {
								int j = strs.indexOf(i + "");
								
								jsonItemTzwc.set("name", "本季投资完成");
								listItem1.add(trendDataTzList.get(j).getTzwcbj());
								
								jsonItemWcjaf.set("name", "本季建安完成");
								listItem2.add(trendDataTzList.get(j).getWcjafbj());
							} else {
								jsonItemTzwc.set("name", "本季投资完成");
								listItem1.add(0);
								
								jsonItemWcjaf.set("name", "本季建安完成");
								listItem2.add(0);
							}
							
						}
				}else {
					for (int i = 0; i < 4; i++) {
						jsonItemTzwc.set("name", "本季投资完成");
						listItem1.add(0);

						jsonItemWcjaf.set("name", "本季建安完成");
						listItem2.add(0);
					}
				}
			}
			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
			//所选年份完成投资开累数据
		} else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "1")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}

			// 按年显示
			for (int year = startYear; year <= endYear; year++) {
				list.add(year + "年");
				
				zjTzInvXmtzqk.setPeriodValue(String.valueOf(year));
				ZjTzInvXmtzqk dbzjTzInvXmtzqk = zjTzInvXmtzqkMapper.selectConstructPageTrendDataByYear(zjTzInvXmtzqk);

				if (dbzjTzInvXmtzqk != null ) {
						jsonItemTzwc.set("name", "投资完成");
						listItem1.add(dbzjTzInvXmtzqk.getTzwckl());
						jsonItemWcjaf.set("name", "建安完成");
						listItem2.add(dbzjTzInvXmtzqk.getWcjafkl());
				}else {
					jsonItemTzwc.set("name", "投资完成");
					listItem1.add(0);

					jsonItemWcjaf.set("name", "建安完成");
					listItem2.add(0);
				}
			}

			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
			//本项目所选年份当年运营情况
		}else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "2")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			ZjTzInvXmyyqk zjTzInvXmyyqk = new ZjTzInvXmyyqk();
			zjTzInvXmyyqk.setProjectId(zjTzInvXmtzqk.getProjectId());
			// 按年显示
			for (int year = startYear; year <= endYear; year++) {
				list.add(year + "年");
				zjTzInvXmyyqk.setPeriodValue(String.valueOf(year));
				ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectOperatePageTrendDataByYearBn(zjTzInvXmyyqk);

				if (dbzjTzInvXmyyqk != null) {
						jsonItemTzwc.set("name", "本年投评收入");
						listItem1.add(dbzjTzInvXmyyqk.getBntpsr());

						jsonItemWcjaf.set("name", "本年营业收入");
						listItem2.add(dbzjTzInvXmyyqk.getBnYyzsr());
				}else {
					jsonItemTzwc.set("name", "本年投评收入");
					listItem1.add(0);

					jsonItemWcjaf.set("name", "本年营业收入");
					listItem2.add(0);
				}
			}

			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
			//本项目所选年份开累运营情况
		}else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "3")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			// 按年显示
			for (int year = startYear; year <= endYear; year++) {
				list.add(year + "年");
				ZjTzInvXmyyqk zjTzInvXmyyqk = new ZjTzInvXmyyqk();
				zjTzInvXmyyqk.setProjectId(zjTzInvXmtzqk.getProjectId());
				zjTzInvXmyyqk.setPeriodValue(String.valueOf(year));
				ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectOperatePageTrendDataByYearKl(zjTzInvXmyyqk);

				if (dbzjTzInvXmyyqk != null) {
						jsonItemTzwc.set("name", "开累投评收入");
						listItem1.add(dbzjTzInvXmyyqk.getKltpsr());

						jsonItemWcjaf.set("name", "开累营业收入");
						listItem2.add(dbzjTzInvXmyyqk.getKlYyzsr());
				}else {
					jsonItemTzwc.set("name", "开累投评收入");
					listItem1.add(0);

					jsonItemWcjaf.set("name", "开累营业收入");
					listItem2.add(0);
				}
			}

			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
			//本项目所选年份当年回购情况
		}else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "4")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			// 按年显示
			for (int year = startYear; year <= endYear; year++) {
				list.add(year + "年");
				ZjTzInvXmhgqk zjTzInvXmhgqk = new ZjTzInvXmhgqk();
				zjTzInvXmhgqk.setProjectId(zjTzInvXmtzqk.getProjectId());
				zjTzInvXmhgqk.setPeriodValue(String.valueOf(year));
				ZjTzInvXmhgqk dbzjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectHgPageTrendDataByYearBn(zjTzInvXmhgqk);

				if (dbzjTzInvXmhgqk != null) {
						jsonItemTzwc.set("name", "本年应回购金额");
						listItem1.add(dbzjTzInvXmhgqk.getBnyhg());

						jsonItemWcjaf.set("name", "本年实际回购金额");
						listItem2.add(dbzjTzInvXmhgqk.getBnlj());
				}else {
					jsonItemTzwc.set("name", "本年应回购金额");
					listItem1.add(0);

					jsonItemWcjaf.set("name", "本年实际回购金额");
					listItem2.add(0);
				}
			}

			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
			//本项目所选年份开累回购情况
		}else if (StrUtil.equals(zjTzInvXmtzqk.getData(), "5")) {
			int endYear =  DateUtil.year(new Date());
			if(zjTzInvXmtzqk.getEndYear() != null) {
				endYear =  DateUtil.year(zjTzInvXmtzqk.getEndYear());
			}
			
			int startYear = endYear-4;
			if(zjTzInvXmtzqk.getStartYear() != null) {
				startYear = DateUtil.year(zjTzInvXmtzqk.getStartYear());
			}
			// 按年显示
			for (int year = startYear; year <= endYear; year++) {
				list.add(year + "年");
				ZjTzInvXmhgqk zjTzInvXmhgqk = new ZjTzInvXmhgqk();
				zjTzInvXmhgqk.setProjectId(zjTzInvXmtzqk.getProjectId());
				zjTzInvXmhgqk.setPeriodValue(String.valueOf(year));
				ZjTzInvXmhgqk dbzjTzInvXmhgqk = zjTzInvXmhgqkMapper.selectHgPageTrendDataByYearKl(zjTzInvXmhgqk);

				if (dbzjTzInvXmhgqk != null) {
						jsonItemTzwc.set("name", "开累应回购金额");
						listItem1.add(dbzjTzInvXmhgqk.getYjhgze());

						jsonItemWcjaf.set("name", "开累已回购金额");
						listItem2.add(dbzjTzInvXmhgqk.getSjhgje());
				}else {
					jsonItemTzwc.set("name", "开累应回购金额");
					listItem1.add(0);

					jsonItemWcjaf.set("name", "开累已回购金额");
					listItem2.add(0);
				}
			}

			jsonItemTzwc.set("tzwckl", listItem1);
			jsonArrayV.add(jsonItemTzwc);

			jsonItemWcjaf.set("wcjafkl", listItem2);
			jsonArrayV.add(jsonItemWcjaf);

			json.set("DataOne", list);
			json.set("DataTwo", jsonArrayN);
			json.set("DataThree", jsonArrayV);
		}
		jsonArray.add(json);
		return repEntity.ok(jsonArray);
	}
	/**
	 * 
	 * 建设页-产值排名
	 */
	@Override
	public ResponseEntity getConstructPageProductionRanking(ZjTzInvXmtzqk zjTzInvXmtzqk) {
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
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            	zjTzInvXmtzqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmtzqk.getProjectId(), true)) {
            	zjTzInvXmtzqk.setProjectId("");
            }
        }
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(new Date());
        	int month = cal.get(cal.MONTH) + 1;
        	int quarter = 0;
        	//判断季度
        	if (month >= 1 && month <= 3) {
        		zjTzInvXmtzqk.setQuarter("1");
			}else if (month >= 4 && month <= 6) {
        		zjTzInvXmtzqk.setQuarter("2");
        	}else if (month >= 7 && month <= 9) {
        		zjTzInvXmtzqk.setQuarter("3");
        	}else if (month >= 10 && month <= 12) {
        		zjTzInvXmtzqk.setQuarter("4");
			}
        	zjTzInvXmtzqk.setPeriodValue(DateUtil.format(new Date(), "yyyy"));
        	//上季度完成总投资百分比排名
        	ZjTzInvXmtzqk dbzjTzInvXmtzqkQytz = zjTzInvXmtzqkMapper.selectConstructPageProductionRankingQytz(zjTzInvXmtzqk);
        	ZjTzInvXmtzqk dbzjTzInvXmtzqkJjaf = zjTzInvXmtzqkMapper.selectConstructPageProductionRankingJjaf(zjTzInvXmtzqk);
        	if (dbzjTzInvXmtzqkQytz == null) {
        		dbzjTzInvXmtzqkQytz = new ZjTzInvXmtzqk();
			}
        	if (dbzjTzInvXmtzqkJjaf == null) {
        		dbzjTzInvXmtzqkJjaf = new ZjTzInvXmtzqk();
        	}
        	dbzjTzInvXmtzqkJjaf.setSjqytzpm(dbzjTzInvXmtzqkQytz.getSjqytzpm());
        	return repEntity.ok(dbzjTzInvXmtzqkJjaf);
	}
	/**
	 * 
	 * 项目页面预警
	 */
	@Override
	public ResponseEntity getProjectPageWarning(ZjTzInvXmtzqk zjTzInvXmtzqk) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzInvXmtzqk == null) {
        	zjTzInvXmtzqk = new ZjTzInvXmtzqk();
		}
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        //工期预警
        String colourFlag1 = "";
        //产值预警
        String colourFlag2 = "";
        //资金占用预警
        String colourFlag3 = "";
        //投评营收预警
        String colourFlag4 = "";
        //风险管理预警
        String colourFlag5 = "";
        //设计流程预警
        String colourFlag6 = "";
        //合法合规预警
        String colourFlag7 = "";
        
        
        Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	
        if (StrUtil.equals(zjTzInvXmtzqk.getWarnType(), "1")) {
			
        	// 获取数据
        	ZjTzProManage zjTzProManage = new ZjTzProManage();
        	zjTzProManage.setProjectId(zjTzInvXmtzqk.getProjectId());
        	List<ZjTzProManage> constructPeriodStatusList = zjTzProManageMapper.selectHomeProjectStatus(zjTzProManage);
        	if (constructPeriodStatusList != null && constructPeriodStatusList.size() > 0) {
        		for (ZjTzProManage dbzjTzProManage : constructPeriodStatusList) {
        			
        			
        			//建设期结束标志为交工
        			if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "0")) {
        				// 填写了实际竣工日期
        				if (dbzjTzProManage.getComplateDateActrual() != null) {
        					if (dbzjTzProManage.getComplateDateActrual() != null && dbzjTzProManage.getComplateDatePlan() != null) {
        						
        						//实际竣工日期
        						Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
        						//应竣工日期
        						Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        						//实际竣工日期早于等于应竣工日期
        						if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
        							if (dbzjTzProManage.getHandoverDateActrual() != null && dbzjTzProManage.getHandoverDatePlan()!= null) {
        								//实际交工日期
        								Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
        								//应交工日期
        								Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
        								
        								//实际交工日期早于等于应交工日期
        								if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
        								}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        								}
        							}
        							//实际竣工日期晚于应竣工日期
        						}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
        							if (dbzjTzProManage.getHandoverDateActrual() != null && dbzjTzProManage.getHandoverDatePlan() != null) {
        								
        								//实际交工日期
        								Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
        								//应交工日期
        								Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
        								//实际交工日期早于等于应交工日期
        								if (handoverDateActrual.compareTo(handoverDatePlan) <= 0 ) {
        									//实际交工日期晚于应交工日期
        								}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        								}
        							}
        						}
        					}
        					//未填写实际竣工日期
        				}else if (dbzjTzProManage.getComplateDateActrual() == null) {
        					//填写了实际交工日期
        					if (dbzjTzProManage.getHandoverDateActrual() != null) {
        						if (dbzjTzProManage.getHandoverDateActrual() != null && dbzjTzProManage.getHandoverDatePlan() != null) {
        							//实际交工日期
        							Date handoverDateActrual = dbzjTzProManage.getHandoverDateActrual();
        							//应交工日期
        							Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
        							
        							//实际交工日期早于等于应交工日期
        							if (handoverDateActrual.compareTo(handoverDatePlan) <= 0) {
        								if (dbzjTzProManage.getCurrDate() != null && dbzjTzProManage.getComplateDatePlan() != null) {
        									//当前日期
        									Date currDate = dbzjTzProManage.getCurrDate();
        									//应竣工日期
        									Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        									
        									//当前日期早于等于应竣工日期
        									if (currDate.compareTo(complateDatePlan) <= 0) {
        										//当前日期晚于应竣工日期
        									}else if (currDate.compareTo(complateDatePlan) > 0) {
        										colourFlag1 = "red";
        										
        									}
        								}
        								//实际交工日期晚于应交工日期
        							}else if (handoverDateActrual.compareTo(handoverDatePlan) > 0) {
        								if (dbzjTzProManage.getCurrDate() != null && dbzjTzProManage.getComplateDatePlan() != null) {
        									//当前日期
        									Date currDate = dbzjTzProManage.getCurrDate();
        									//应竣工日期
        									Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        									
        									//当前日期早于等于应竣工日期
        									if (currDate.compareTo(complateDatePlan) <= 0) {
        										//当前日期晚于应竣工日期
        									}else if (currDate.compareTo(complateDatePlan) > 0) {
        										colourFlag1 = "red";
        										
        									}
        								}
        								
        							}
        						}
        						//未填写实际交工日期
        					}else if (dbzjTzProManage.getHandoverDateActrual() == null) {
        						if (dbzjTzProManage.getCurrDate() != null && dbzjTzProManage.getHandoverDatePlan() != null) {
        							//当前日期
        							Date currDate = dbzjTzProManage.getCurrDate();
        							//应交工日期
        							Date handoverDatePlan = dbzjTzProManage.getHandoverDatePlan();
        							
        							//当前日期早于等于应交工日期
        							if (currDate.compareTo(handoverDatePlan) <= 0) {
        							}else if (currDate.compareTo(handoverDatePlan) > 0) {
        								colourFlag1 = "red";
        								
        							}
        						}
        					}
        				}
        				
        				//建设期结束标志为竣工
        			}else if (StrUtil.equals(dbzjTzProManage.getConstructEnd(), "1")) {
        				//填写了实际竣工日期
        				if (dbzjTzProManage.getComplateDateActrual() != null) {
        					if (dbzjTzProManage.getComplateDateActrual() != null && dbzjTzProManage.getComplateDatePlan() != null) {
        						//实际竣工日期
        						Date complateDateActrual = dbzjTzProManage.getComplateDateActrual();
        						//应竣工日期
        						Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        						//实际竣工日期早于等于应竣工日期
        						if (complateDateActrual.compareTo(complateDatePlan) <= 0) {
        							
        							//实际竣工日期晚于应竣工日期
        						}else if (complateDateActrual.compareTo(complateDatePlan) > 0) {
        							
        						}
        					}
        					//未填写实际竣工日期	
        				}else if (dbzjTzProManage.getComplateDateActrual() == null) {
        					if (dbzjTzProManage.getCurrDate() != null && dbzjTzProManage.getComplateDatePlan() != null) {
        						//当前日期
        						Date currDate = dbzjTzProManage.getCurrDate();
        						//应竣工日期
        						Date complateDatePlan = dbzjTzProManage.getComplateDatePlan();
        						//当前日期早于等于应竣工日期
        						if (dbzjTzProManage.getCurrDate().compareTo(dbzjTzProManage.getComplateDatePlan()) <= 0) {
        							//当前日期晚于应竣工日期
        						}else if (dbzjTzProManage.getCurrDate().compareTo(dbzjTzProManage.getComplateDatePlan()) > 0) {
        							colourFlag1 = "red";
        						}
        						
        					}
        				}
        			}
        		}
        		jsonObject.set("colourFlag1", colourFlag1);
        		
        	}
        	
        	//计划产值预警
        	int month = cal.get(cal.MONTH) + 1;
        	int quarter = 0;
        	//判断季度
        	if (month >= 1 && month <= 3) {
        		zjTzInvXmtzqk.setQuarter("1");
        	}else if (month >= 4 && month <= 6) {
        		zjTzInvXmtzqk.setQuarter("2");
        	}else if (month >= 7 && month <= 9) {
        		zjTzInvXmtzqk.setQuarter("3");
        	}else if (month >= 10 && month <= 12) {
        		zjTzInvXmtzqk.setQuarter("4");
        	}
        	zjTzInvXmtzqk.setPeriodValue(DateUtil.format(new Date(), "yyyyMM"));
        	ZjTzInvXmtzqk zjTzInvXmtzqk2 = zjTzInvXmtzqkMapper.selectProjectPagePlanningWarning(zjTzInvXmtzqk);
        	int currQuarter = DateUtil.quarter(new Date());
        	if (zjTzInvXmtzqk2 != null ) {
        		if (currQuarter != 1 && zjTzInvXmtzqk2.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0) {
        			colourFlag2 = "yellow";
        		}else if (currQuarter == 1 && zjTzInvXmtzqk2.getBjqytzbfb().compareTo(new BigDecimal(0.7)) < 0 && zjTzInvXmtzqk2.getSnztzbfb().compareTo(new BigDecimal(0.6)) >= 0) {
        			colourFlag2 = "yellow";
        		}else if (currQuarter == 1 && zjTzInvXmtzqk2.getSnztzbfb().compareTo(new BigDecimal(0.6)) < 0) {
        			colourFlag2 = "red";
        		}
        	}
        	jsonObject.set("colourFlag2", colourFlag2);
        	
        	//资金占用预警
        	ZjTzInvXmzjqk zjTzInvXmzjqk = new ZjTzInvXmzjqk();
        	zjTzInvXmzjqk.setProjectId(zjTzInvXmtzqk.getProjectId());
        	if (DateUtil.month(new Date()) == 1) {
        		int year = cal.get(cal.YEAR) - 1;
        		zjTzInvXmzjqk.setPeriodValue(year + "12");
        	} else {
        		zjTzInvXmzjqk.setPeriodValue(DateUtil.format(new Date(), "yyyyMM"));
        		
        	}
        	ZjTzInvXmzjqk dbzjTzInvXmzjqk = zjTzInvXmzjqkMapper.selectProjectPageCapitalWarning(zjTzInvXmzjqk);
        	if (dbzjTzInvXmzjqk != null && dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl().compareTo(dbzjTzInvXmzjqk.getZbjzykhzb()) > 0) {
        		colourFlag3 = "red";
        	}
        	jsonObject.set("colourFlag3", colourFlag3);
        	
			//风险管理预警
			ZjTzRisk zjTzRisk = new ZjTzRisk();
			zjTzRisk.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzRisk.setReleaseId("1");
			List<ZjTzRisk> zjTzRiskList = zjTzRiskMapper.selectByZjTzRiskList(zjTzRisk);
			if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
				for (ZjTzRisk zjTzRisk2 : zjTzRiskList) {
					ZjTzRiskDetail riskDetail = new ZjTzRiskDetail();
					riskDetail.setRiskId(zjTzRisk2.getRiskId());
					List<ZjTzRiskDetail> riskDetails = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(riskDetail);
					if (riskDetails != null && riskDetails.size() > 0) {
						for (ZjTzRiskDetail zjTzRiskDetail : riskDetails) {
							if(zjTzRiskDetail.getPlanTime() != null) {
								if(zjTzRiskDetail.getPlanTime().compareTo(new Date()) <0) {
									colourFlag5 = "red";
								}
							}
							
						}
					}
				}
			}
			jsonObject.set("colourFlag5", colourFlag5);
			
			//设计流程预警
			// 获取数据
			ZjTzDesignFlow zjTzDesignFlow = new ZjTzDesignFlow();
			zjTzDesignFlow.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzDesignFlow.setReleaseId("1");
			List<ZjTzDesignFlow> zjTzDesignFlowList = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlow);
			for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
//        	String colourFlag6 = "";
				//根据里层的数据状态返给前台外层的预警状态
				ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
				zjTzPartManage.setDesignFlowId(designFlow.getDesignFlowId());
				//11.中标前 均为黄        
				//22.中标后：   ==实际日期为空时  1. 计划日期>=今日   白色    
				//2. 计划日期<今日     红色      
				//== 实际日期不为空时  蓝色
				List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
				
				boolean bidPartIdFalg = false;//是否有中标时状态
				int orderNum = 0;
				
				for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
					if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
						bidPartIdFalg = true;
						orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
					}
				}
				
				if(bidPartIdFalg) {
					//中标前
					ZjTzPartManage lessRecord = new ZjTzPartManage();
					lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					lessRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
					for (ZjTzPartManage zjTzPartManage3 : partLessList) {
						zjTzPartManage3.setColorFlag("yellow");
						colourFlag6 = "yellow";
						
					}
					//中标后
					ZjTzPartManage greaterRecord = new ZjTzPartManage();
					greaterRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					greaterRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partGreaterList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(greaterRecord);
					for (ZjTzPartManage zjTzPartManage3 : partGreaterList) {
						if(StrUtil.equals(zjTzPartManage3.getFileFlag(), "1")) {
							zjTzPartManage3.setColorFlag("blue");
						}else {
							if(zjTzPartManage3.getPlanDate() != null) {
								if(DateUtil.compare(zjTzPartManage3.getPlanDate(), new Date()) >=0) {
									zjTzPartManage3.setColorFlag("white");
								}else {
									zjTzPartManage3.setColorFlag("red");
								}
							}else {
								zjTzPartManage3.setColorFlag("red");
							}
						}
					}
					for (ZjTzPartManage part : partGreaterList) {
						if(StrUtil.equals(part.getColorFlag(), "red")) {
							colourFlag6 = "red";
						}
					}
				}else {
					for (ZjTzPartManage zjTzPartManage2 : zjTzPartManageList) {
						zjTzPartManage2.setColorFlag("yellow");
					}
					colourFlag6 = "yellow";
				}
			}
			jsonObject.set("colourFlag6", colourFlag6);
			
			//合法合规预警
			// 获取数据 
			ZjTzComplianceDeal zjTzComplianceDeal = new ZjTzComplianceDeal();
			zjTzComplianceDeal.setProjectId(zjTzInvXmtzqk.getProjectId());
			List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
			//列表页面列出前7个步骤进行预警，设置预警规则，==预警时间根据当前日期和应办理完成日期进行对比
			//绿色表示正常进行中， 
			//黄色表示距离办理时间还有15天，
			//红色表示应办理完成日期超出时间规定。
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				ZjTzComplianceDetail detailSelect = new ZjTzComplianceDetail();
				detailSelect.setComplianceDealId(zjTzComplianceDeal2.getComplianceDealId());
				detailSelect.setNum("001");
				List<ZjTzComplianceDetail> detailSelectList1 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList1 != null && detailSelectList1.size() >0) {
					if(StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "1")) {
						if(detailSelectList1.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList1.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList1.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList1.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList1.get(0).setColourFlag("red");
							}else {
								detailSelectList1.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList1.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList1.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase1(detailSelectList1.get(0).getColourFlag());
				}
				
				detailSelect.setNum("002");
				List<ZjTzComplianceDetail> detailSelectList2 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList2 != null && detailSelectList2.size() >0) {
					if(StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "1")) {
						if(detailSelectList2.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList2.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList2.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList2.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList2.get(0).setColourFlag("red");
							}else {
								detailSelectList2.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList2.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList2.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase2(detailSelectList2.get(0).getColourFlag());
				}
				
				detailSelect.setNum("003");
				List<ZjTzComplianceDetail> detailSelectList3 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList3 != null && detailSelectList3.size() >0) {
					if(StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "1")) {
						if(detailSelectList3.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList3.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList3.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList3.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList3.get(0).setColourFlag("red");
							}else {
								detailSelectList3.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList3.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList3.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase3(detailSelectList3.get(0).getColourFlag());
				}
				
				detailSelect.setNum("004");
				List<ZjTzComplianceDetail> detailSelectList4 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList4 != null && detailSelectList4.size() >0) {
					if(StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "1")) {
						if(detailSelectList4.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList4.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList4.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList4.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList4.get(0).setColourFlag("red");
							}else {
								detailSelectList4.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList4.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList4.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase4(detailSelectList4.get(0).getColourFlag());
				}
				
				detailSelect.setNum("005");
				List<ZjTzComplianceDetail> detailSelectList5 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList5 != null && detailSelectList5.size() >0) {
					if(StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "1")) {
						if(detailSelectList5.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList5.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList5.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList5.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList5.get(0).setColourFlag("red");
							}else {
								detailSelectList5.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList5.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList5.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase5(detailSelectList5.get(0).getColourFlag());
				}
				
				detailSelect.setNum("006");
				List<ZjTzComplianceDetail> detailSelectList6 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList6 != null && detailSelectList6.size() >0) {
					if(StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "1")) {
						if(detailSelectList6.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList6.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList6.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList6.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList6.get(0).setColourFlag("red");
							}else {
								detailSelectList6.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList6.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList6.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase6(detailSelectList6.get(0).getColourFlag());
				}
				
				detailSelect.setNum("007");
				List<ZjTzComplianceDetail> detailSelectList7 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList7 != null && detailSelectList7.size() >0) {
					if(StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "1")) {
						if(detailSelectList7.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList7.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList7.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList7.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList7.get(0).setColourFlag("red");
							}else {
								detailSelectList7.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList7.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList7.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase7(detailSelectList7.get(0).getColourFlag());
				}
				
			}
			
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				if(StrUtil.equals(zjTzComplianceDeal2.getBase1(), "red") 
						|| StrUtil.equals(zjTzComplianceDeal2.getBase2(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase3(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase4(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase5(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase6(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase7(), "red")
						) {
					colourFlag7 = "red";
					
				}
			}
			jsonObject.set("colourFlag7", colourFlag7);

		}else if (StrUtil.equals(zjTzInvXmtzqk.getWarnType(), "2")) {
			//投评营收预警
			ZjTzInvXmyyqk zjTzInvXmyyqk = new ZjTzInvXmyyqk();
			zjTzInvXmyyqk.setProjectId(zjTzInvXmtzqk.getProjectId());
			if (DateUtil.month(new Date()) == 1) {
				int year = cal.get(cal.YEAR) - 1;
				zjTzInvXmyyqk.setPeriodValue(year + "12");
			} else {
				zjTzInvXmyyqk.setPeriodValue(DateUtil.format(new Date(), "yyyyMM"));
				
			}
			ZjTzInvXmyyqk dbzjTzInvXmyyqk = zjTzInvXmyyqkMapper.selectProjectPageYyWarning(zjTzInvXmyyqk);
			if (dbzjTzInvXmyyqk != null && dbzjTzInvXmyyqk.getBqYyzsr().compareTo(dbzjTzInvXmyyqk.getBqtpsr()) < 0) {
				colourFlag4 = "red";
			}
			jsonObject.set("colourFlag4", colourFlag4);
			//风险管理预警
			ZjTzRisk zjTzRisk = new ZjTzRisk();
			zjTzRisk.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzRisk.setReleaseId("1");
			List<ZjTzRisk> zjTzRiskList = zjTzRiskMapper.selectByZjTzRiskList(zjTzRisk);
			if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
				for (ZjTzRisk zjTzRisk2 : zjTzRiskList) {
					ZjTzRiskDetail riskDetail = new ZjTzRiskDetail();
					riskDetail.setRiskId(zjTzRisk2.getRiskId());
					List<ZjTzRiskDetail> riskDetails = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(riskDetail);
					if (riskDetails != null && riskDetails.size() > 0) {
						for (ZjTzRiskDetail zjTzRiskDetail : riskDetails) {
							if(zjTzRiskDetail.getPlanTime() != null) {
								if(zjTzRiskDetail.getPlanTime().compareTo(new Date()) <0) {
									colourFlag5 = "red";
								}
							}
							
						}
					}
				}
			}
			jsonObject.set("colourFlag5", colourFlag5);
			
			//设计流程预警
			// 获取数据
			ZjTzDesignFlow zjTzDesignFlow = new ZjTzDesignFlow();
			zjTzDesignFlow.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzDesignFlow.setReleaseId("1");
			List<ZjTzDesignFlow> zjTzDesignFlowList = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlow);
			for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
//        	String colourFlag6 = "";
				//根据里层的数据状态返给前台外层的预警状态
				ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
				zjTzPartManage.setDesignFlowId(designFlow.getDesignFlowId());
				//11.中标前 均为黄        
				//22.中标后：   ==实际日期为空时  1. 计划日期>=今日   白色    
				//2. 计划日期<今日     红色      
				//== 实际日期不为空时  蓝色
				List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
				
				boolean bidPartIdFalg = false;//是否有中标时状态
				int orderNum = 0;
				
				for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
					if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
						bidPartIdFalg = true;
						orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
					}
				}
				
				if(bidPartIdFalg) {
					//中标前
					ZjTzPartManage lessRecord = new ZjTzPartManage();
					lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					lessRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
					for (ZjTzPartManage zjTzPartManage3 : partLessList) {
						zjTzPartManage3.setColorFlag("yellow");
						colourFlag6 = "yellow";
						
					}
					//中标后
					ZjTzPartManage greaterRecord = new ZjTzPartManage();
					greaterRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					greaterRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partGreaterList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(greaterRecord);
					for (ZjTzPartManage zjTzPartManage3 : partGreaterList) {
						if(StrUtil.equals(zjTzPartManage3.getFileFlag(), "1")) {
							zjTzPartManage3.setColorFlag("blue");
						}else {
							if(zjTzPartManage3.getPlanDate() != null) {
								if(DateUtil.compare(zjTzPartManage3.getPlanDate(), new Date()) >=0) {
									zjTzPartManage3.setColorFlag("white");
								}else {
									zjTzPartManage3.setColorFlag("red");
								}
							}else {
								zjTzPartManage3.setColorFlag("red");
							}
						}
					}
					for (ZjTzPartManage part : partGreaterList) {
						if(StrUtil.equals(part.getColorFlag(), "red")) {
							colourFlag6 = "red";
						}
					}
				}else {
					for (ZjTzPartManage zjTzPartManage2 : zjTzPartManageList) {
						zjTzPartManage2.setColorFlag("yellow");
					}
					colourFlag6 = "yellow";
				}
			}
			jsonObject.set("colourFlag6", colourFlag6);
			
			//合法合规预警
			// 获取数据 
			ZjTzComplianceDeal zjTzComplianceDeal = new ZjTzComplianceDeal();
			zjTzComplianceDeal.setProjectId(zjTzInvXmtzqk.getProjectId());
			List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
			//列表页面列出前7个步骤进行预警，设置预警规则，==预警时间根据当前日期和应办理完成日期进行对比
			//绿色表示正常进行中， 
			//黄色表示距离办理时间还有15天，
			//红色表示应办理完成日期超出时间规定。
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				ZjTzComplianceDetail detailSelect = new ZjTzComplianceDetail();
				detailSelect.setComplianceDealId(zjTzComplianceDeal2.getComplianceDealId());
				detailSelect.setNum("001");
				List<ZjTzComplianceDetail> detailSelectList1 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList1 != null && detailSelectList1.size() >0) {
					if(StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "1")) {
						if(detailSelectList1.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList1.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList1.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList1.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList1.get(0).setColourFlag("red");
							}else {
								detailSelectList1.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList1.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList1.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase1(detailSelectList1.get(0).getColourFlag());
				}
				
				detailSelect.setNum("002");
				List<ZjTzComplianceDetail> detailSelectList2 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList2 != null && detailSelectList2.size() >0) {
					if(StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "1")) {
						if(detailSelectList2.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList2.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList2.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList2.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList2.get(0).setColourFlag("red");
							}else {
								detailSelectList2.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList2.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList2.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase2(detailSelectList2.get(0).getColourFlag());
				}
				
				detailSelect.setNum("003");
				List<ZjTzComplianceDetail> detailSelectList3 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList3 != null && detailSelectList3.size() >0) {
					if(StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "1")) {
						if(detailSelectList3.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList3.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList3.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList3.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList3.get(0).setColourFlag("red");
							}else {
								detailSelectList3.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList3.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList3.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase3(detailSelectList3.get(0).getColourFlag());
				}
				
				detailSelect.setNum("004");
				List<ZjTzComplianceDetail> detailSelectList4 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList4 != null && detailSelectList4.size() >0) {
					if(StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "1")) {
						if(detailSelectList4.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList4.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList4.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList4.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList4.get(0).setColourFlag("red");
							}else {
								detailSelectList4.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList4.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList4.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase4(detailSelectList4.get(0).getColourFlag());
				}
				
				detailSelect.setNum("005");
				List<ZjTzComplianceDetail> detailSelectList5 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList5 != null && detailSelectList5.size() >0) {
					if(StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "1")) {
						if(detailSelectList5.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList5.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList5.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList5.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList5.get(0).setColourFlag("red");
							}else {
								detailSelectList5.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList5.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList5.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase5(detailSelectList5.get(0).getColourFlag());
				}
				
				detailSelect.setNum("006");
				List<ZjTzComplianceDetail> detailSelectList6 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList6 != null && detailSelectList6.size() >0) {
					if(StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "1")) {
						if(detailSelectList6.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList6.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList6.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList6.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList6.get(0).setColourFlag("red");
							}else {
								detailSelectList6.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList6.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList6.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase6(detailSelectList6.get(0).getColourFlag());
				}
				
				detailSelect.setNum("007");
				List<ZjTzComplianceDetail> detailSelectList7 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList7 != null && detailSelectList7.size() >0) {
					if(StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "1")) {
						if(detailSelectList7.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList7.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList7.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList7.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList7.get(0).setColourFlag("red");
							}else {
								detailSelectList7.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList7.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList7.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase7(detailSelectList7.get(0).getColourFlag());
				}
				
			}
			
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				if(StrUtil.equals(zjTzComplianceDeal2.getBase1(), "red") 
						|| StrUtil.equals(zjTzComplianceDeal2.getBase2(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase3(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase4(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase5(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase6(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase7(), "red")
						) {
					colourFlag7 = "red";
					
				}
			}
			jsonObject.set("colourFlag7", colourFlag7);

		}else if (StrUtil.equals(zjTzInvXmtzqk.getWarnType(), "3")) {
			//风险管理预警
			ZjTzRisk zjTzRisk = new ZjTzRisk();
			zjTzRisk.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzRisk.setReleaseId("1");
			List<ZjTzRisk> zjTzRiskList = zjTzRiskMapper.selectByZjTzRiskList(zjTzRisk);
			if (zjTzRiskList != null && zjTzRiskList.size() > 0) {
				for (ZjTzRisk zjTzRisk2 : zjTzRiskList) {
					ZjTzRiskDetail riskDetail = new ZjTzRiskDetail();
					riskDetail.setRiskId(zjTzRisk2.getRiskId());
					List<ZjTzRiskDetail> riskDetails = zjTzRiskDetailMapper.selectByZjTzRiskDetailList(riskDetail);
					if (riskDetails != null && riskDetails.size() > 0) {
						for (ZjTzRiskDetail zjTzRiskDetail : riskDetails) {
							if(zjTzRiskDetail.getPlanTime() != null) {
								if(zjTzRiskDetail.getPlanTime().compareTo(new Date()) <0) {
									colourFlag5 = "red";
								}
							}
							
						}
					}
				}
			}
			jsonObject.set("colourFlag5", colourFlag5);
			
			//设计流程预警
			// 获取数据
			ZjTzDesignFlow zjTzDesignFlow = new ZjTzDesignFlow();
			zjTzDesignFlow.setProjectId(zjTzInvXmtzqk.getProjectId());
//        zjTzDesignFlow.setReleaseId("1");
			List<ZjTzDesignFlow> zjTzDesignFlowList = zjTzDesignFlowMapper.selectByZjTzDesignFlowList(zjTzDesignFlow);
			for (ZjTzDesignFlow designFlow : zjTzDesignFlowList) {
//        	String colourFlag6 = "";
				//根据里层的数据状态返给前台外层的预警状态
				ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
				zjTzPartManage.setDesignFlowId(designFlow.getDesignFlowId());
				//11.中标前 均为黄        
				//22.中标后：   ==实际日期为空时  1. 计划日期>=今日   白色    
				//2. 计划日期<今日     红色      
				//== 实际日期不为空时  蓝色
				List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
				
				boolean bidPartIdFalg = false;//是否有中标时状态
				int orderNum = 0;
				
				for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
					if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
						bidPartIdFalg = true;
						orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
					}
				}
				
				if(bidPartIdFalg) {
					//中标前
					ZjTzPartManage lessRecord = new ZjTzPartManage();
					lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					lessRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
					for (ZjTzPartManage zjTzPartManage3 : partLessList) {
						zjTzPartManage3.setColorFlag("yellow");
						colourFlag6 = "yellow";
						
					}
					//中标后
					ZjTzPartManage greaterRecord = new ZjTzPartManage();
					greaterRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
					greaterRecord.setOrderNum(orderNum);
					List<ZjTzPartManage> partGreaterList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(greaterRecord);
					for (ZjTzPartManage zjTzPartManage3 : partGreaterList) {
						if(StrUtil.equals(zjTzPartManage3.getFileFlag(), "1")) {
							zjTzPartManage3.setColorFlag("blue");
						}else {
							if(zjTzPartManage3.getPlanDate() != null) {
								if(DateUtil.compare(zjTzPartManage3.getPlanDate(), new Date()) >=0) {
									zjTzPartManage3.setColorFlag("white");
								}else {
									zjTzPartManage3.setColorFlag("red");
								}
							}else {
								zjTzPartManage3.setColorFlag("red");
							}
						}
					}
					for (ZjTzPartManage part : partGreaterList) {
						if(StrUtil.equals(part.getColorFlag(), "red")) {
							colourFlag6 = "red";
						}
					}
				}else {
					for (ZjTzPartManage zjTzPartManage2 : zjTzPartManageList) {
						zjTzPartManage2.setColorFlag("yellow");
					}
					colourFlag6 = "yellow";
				}
			}
			jsonObject.set("colourFlag6", colourFlag6);
			
			//合法合规预警
			// 获取数据 
			ZjTzComplianceDeal zjTzComplianceDeal = new ZjTzComplianceDeal();
			zjTzComplianceDeal.setProjectId(zjTzInvXmtzqk.getProjectId());
			List<ZjTzComplianceDeal> zjTzComplianceDealList = zjTzComplianceDealMapper.selectByZjTzComplianceDealList(zjTzComplianceDeal);
			//列表页面列出前7个步骤进行预警，设置预警规则，==预警时间根据当前日期和应办理完成日期进行对比
			//绿色表示正常进行中， 
			//黄色表示距离办理时间还有15天，
			//红色表示应办理完成日期超出时间规定。
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				ZjTzComplianceDetail detailSelect = new ZjTzComplianceDetail();
				detailSelect.setComplianceDealId(zjTzComplianceDeal2.getComplianceDealId());
				detailSelect.setNum("001");
				List<ZjTzComplianceDetail> detailSelectList1 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList1 != null && detailSelectList1.size() >0) {
					if(StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList1.get(0).getDealSituation(), "1")) {
						if(detailSelectList1.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList1.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList1.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList1.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList1.get(0).setColourFlag("red");
							}else {
								detailSelectList1.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList1.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList1.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase1(detailSelectList1.get(0).getColourFlag());
				}
				
				detailSelect.setNum("002");
				List<ZjTzComplianceDetail> detailSelectList2 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList2 != null && detailSelectList2.size() >0) {
					if(StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList2.get(0).getDealSituation(), "1")) {
						if(detailSelectList2.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList2.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList2.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList2.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList2.get(0).setColourFlag("red");
							}else {
								detailSelectList2.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList2.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList2.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase2(detailSelectList2.get(0).getColourFlag());
				}
				
				detailSelect.setNum("003");
				List<ZjTzComplianceDetail> detailSelectList3 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList3 != null && detailSelectList3.size() >0) {
					if(StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList3.get(0).getDealSituation(), "1")) {
						if(detailSelectList3.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList3.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList3.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList3.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList3.get(0).setColourFlag("red");
							}else {
								detailSelectList3.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList3.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList3.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase3(detailSelectList3.get(0).getColourFlag());
				}
				
				detailSelect.setNum("004");
				List<ZjTzComplianceDetail> detailSelectList4 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList4 != null && detailSelectList4.size() >0) {
					if(StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList4.get(0).getDealSituation(), "1")) {
						if(detailSelectList4.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList4.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList4.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList4.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList4.get(0).setColourFlag("red");
							}else {
								detailSelectList4.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList4.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList4.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase4(detailSelectList4.get(0).getColourFlag());
				}
				
				detailSelect.setNum("005");
				List<ZjTzComplianceDetail> detailSelectList5 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList5 != null && detailSelectList5.size() >0) {
					if(StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList5.get(0).getDealSituation(), "1")) {
						if(detailSelectList5.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList5.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList5.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList5.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList5.get(0).setColourFlag("red");
							}else {
								detailSelectList5.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList5.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList5.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase5(detailSelectList5.get(0).getColourFlag());
				}
				
				detailSelect.setNum("006");
				List<ZjTzComplianceDetail> detailSelectList6 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList6 != null && detailSelectList6.size() >0) {
					if(StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList6.get(0).getDealSituation(), "1")) {
						if(detailSelectList6.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList6.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList6.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList6.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList6.get(0).setColourFlag("red");
							}else {
								detailSelectList6.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList6.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList6.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase6(detailSelectList6.get(0).getColourFlag());
				}
				
				detailSelect.setNum("007");
				List<ZjTzComplianceDetail> detailSelectList7 = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(detailSelect);
				if(detailSelectList7 != null && detailSelectList7.size() >0) {
					if(StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "0") || StrUtil.equals(detailSelectList7.get(0).getDealSituation(), "1")) {
						if(detailSelectList7.get(0).getShouldFinishDate() != null) {
							if(DateUtil.between(detailSelectList7.get(0).getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
								detailSelectList7.get(0).setColourFlag("yellow");
							}else if(DateUtil.compare(detailSelectList7.get(0).getShouldFinishDate(),new Date()) <0) {
								detailSelectList7.get(0).setColourFlag("red");
							}else {
								detailSelectList7.get(0).setColourFlag("green");
							}
						}else {
							detailSelectList7.get(0).setColourFlag("green");
						}
					}else {
						detailSelectList7.get(0).setColourFlag("green");
					}
					zjTzComplianceDeal2.setBase7(detailSelectList7.get(0).getColourFlag());
				}
				
			}
			
			for (ZjTzComplianceDeal zjTzComplianceDeal2 : zjTzComplianceDealList) {
				if(StrUtil.equals(zjTzComplianceDeal2.getBase1(), "red") 
						|| StrUtil.equals(zjTzComplianceDeal2.getBase2(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase3(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase4(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase5(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase6(), "red")
						|| StrUtil.equals(zjTzComplianceDeal2.getBase7(), "red")
						) {
					colourFlag7 = "red";
					
				}
			}
			jsonObject.set("colourFlag7", colourFlag7);
			
		}
        
        
        jsonArray.add(jsonObject);
		return repEntity.ok(jsonArray);
	}
}
