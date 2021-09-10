package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzCashMapper;
import com.apih5.mybatis.dao.ZjTzCompSupContentMapper;
import com.apih5.mybatis.dao.ZjTzDebtMapper;
import com.apih5.mybatis.dao.ZjTzFinanceMonthMapper;
import com.apih5.mybatis.dao.ZjTzPppTermMapper;
import com.apih5.mybatis.dao.ZjTzProfitMapper;
import com.apih5.mybatis.dao.ZjTzSupContentMapper;
import com.apih5.mybatis.dao.ZjTzSupReportMapper;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.mybatis.pojo.ZjTzCompSupContent;
import com.apih5.mybatis.pojo.ZjTzDebt;
import com.apih5.mybatis.pojo.ZjTzFinanceMonth;
import com.apih5.mybatis.pojo.ZjTzPppTerm;
import com.apih5.mybatis.pojo.ZjTzProfit;
import com.apih5.mybatis.pojo.ZjTzSupContent;
import com.apih5.mybatis.pojo.ZjTzSupReport;
import com.apih5.service.ZjTzSupReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zjTzSupReportService")
public class ZjTzSupReportServiceImpl implements ZjTzSupReportService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSupReportMapper zjTzSupReportMapper;
    
    @Autowired(required = true)
    private ZjTzSupContentMapper zjTzSupContentMapper;
    
    @Autowired(required = true)
    private ZjTzCompSupContentMapper zjTzCompSupContentMapper;
    
    @Autowired(required = true)
    private ZjTzPppTermMapper zjTzPppTermMapper;

    @Autowired(required = true)
    private ZjTzFinanceMonthMapper zjTzFinanceMonthMapper;
    
    @Autowired(required = true)
    private ZjTzDebtMapper zjTzDebtMapper;
    
    @Autowired(required = true)
    private ZjTzProfitMapper zjTzProfitMapper;
    
    @Autowired(required = true)
    private ZjTzCashMapper zjTzCashMapper;

    


    @Override
    public ResponseEntity getZjTzSupReportListByCondition(ZjTzSupReport zjTzSupReport) {
        if (zjTzSupReport == null) {
            zjTzSupReport = new ZjTzSupReport();
        }
        // 分页查询
        PageHelper.startPage(zjTzSupReport.getPage(),zjTzSupReport.getLimit());
        // 获取数据
        List<ZjTzSupReport> zjTzSupReportList = zjTzSupReportMapper.selectByZjTzSupReportList(zjTzSupReport);
        // 得到分页信息
        PageInfo<ZjTzSupReport> p = new PageInfo<>(zjTzSupReportList);

        return repEntity.okList(zjTzSupReportList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSupReportDetails(ZjTzSupReport zjTzSupReport) {
        if (zjTzSupReport == null) {
            zjTzSupReport = new ZjTzSupReport();
        }
        // 获取数据
        ZjTzSupReport dbZjTzSupReport = zjTzSupReportMapper.selectByPrimaryKey(zjTzSupReport.getReportId());
        // 数据存在
        if (dbZjTzSupReport != null) {
            return repEntity.ok(dbZjTzSupReport);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSupReport(ZjTzSupReport zjTzSupReport) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	boolean reportFlag = false;
    	ZjTzSupContent content = new ZjTzSupContent();
    	content.setComprehensiveSupId(zjTzSupReport.getOtherId());
    	List<ZjTzSupContent> contents = zjTzSupContentMapper.selectByZjTzSupContentList(content);
    	if(contents != null && contents.size() >0) {
    		for (ZjTzSupContent zjTzSupContent : contents) {
				if(zjTzSupContent.getCorrectiveDate() != null) {
					reportFlag = true;
				}
			}
    	}
    	ZjTzCompSupContent compSupContent = new ZjTzCompSupContent();
    	compSupContent.setCompSupReportId(zjTzSupReport.getOtherId());
    	List<ZjTzCompSupContent> compSupContents = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(compSupContent);
    	if(compSupContents != null && compSupContents.size() >0) {
    		for (ZjTzCompSupContent zjTzCompSupContent : compSupContents) {
				if(zjTzCompSupContent.getCorrectiveDate() != null) {
					reportFlag = true;
				}
			}
    	}
    	//ppp分析上报
    	ZjTzPppTerm pppTerm = zjTzPppTermMapper.selectByPrimaryKey(zjTzSupReport.getOtherId());
    	if(pppTerm != null && StrUtil.isNotEmpty(pppTerm.getPppTermId())) {
    		reportFlag = true;
    	}
    	ZjTzFinanceMonth financeMonth = zjTzFinanceMonthMapper.selectByPrimaryKey(zjTzSupReport.getOtherId());
    	if(financeMonth != null && StrUtil.isNotEmpty(financeMonth.getFinanceMonthId())) {
    		reportFlag = true;
    	}
    	ZjTzDebt debt = zjTzDebtMapper.selectByPrimaryKey(zjTzSupReport.getOtherId());
    	if(debt != null && StrUtil.isNotEmpty(debt.getDebtId())) {
    		reportFlag = true;
    	}
    	ZjTzProfit profit = zjTzProfitMapper.selectByPrimaryKey(zjTzSupReport.getOtherId());
    	if(profit != null && StrUtil.isNotEmpty(profit.getProfitId())) {
    		reportFlag = true;
    	}
    	ZjTzCash cash = zjTzCashMapper.selectByPrimaryKey(zjTzSupReport.getOtherId());
    	if(cash != null && StrUtil.isNotEmpty(cash.getCashId())) {
    		reportFlag = true;
    	}
    	
    	
    	if(reportFlag) {
    		zjTzSupReport.setReportId(UuidUtil.generate());
    		zjTzSupReport.setOtherId(zjTzSupReport.getOtherId());
        	//上报人员
        	List<JSONObject> approve1List = zjTzSupReport.getPersonList();
        	if(approve1List != null && approve1List.size()>0) {
        		Object object = approve1List.get(0);
        		JSONObject jsonObject = new JSONObject(object);
        		zjTzSupReport.setReportUserKey(jsonObject.getStr("value"));
        		zjTzSupReport.setReportUserName(jsonObject.getStr("label"));
        	}
        	zjTzSupReport.setCreateUserInfo(userKey, realName);
        	int flag = zjTzSupReportMapper.insert(zjTzSupReport);
        	if (flag == 0) {
        		return repEntity.errorSave();
        	}
        	else {
        		return repEntity.ok("sys.data.sava", zjTzSupReport);
        	}
    	}else {
    		return repEntity.layerMessage("no", "还未填写，不能上报！！");
    	}
    }

    @Override
    public ResponseEntity updateZjTzSupReport(ZjTzSupReport zjTzSupReport) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSupReport dbzjTzSupReport = zjTzSupReportMapper.selectByPrimaryKey(zjTzSupReport.getReportId());
        if (dbzjTzSupReport != null && StrUtil.isNotEmpty(dbzjTzSupReport.getReportId())) {
           // 督察要点通报内容id
           dbzjTzSupReport.setOtherId(zjTzSupReport.getOtherId());
           // 整改人Key
           dbzjTzSupReport.setReportUserKey(zjTzSupReport.getReportUserKey());
           // 整改人name
           dbzjTzSupReport.setReportUserName(zjTzSupReport.getReportUserName());
           // 共通
           dbzjTzSupReport.setModifyUserInfo(userKey, realName);
           flag = zjTzSupReportMapper.updateByPrimaryKey(dbzjTzSupReport);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSupReport);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSupReport(List<ZjTzSupReport> zjTzSupReportList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSupReportList != null && zjTzSupReportList.size() > 0) {
           ZjTzSupReport zjTzSupReport = new ZjTzSupReport();
           zjTzSupReport.setModifyUserInfo(userKey, realName);
           flag = zjTzSupReportMapper.batchDeleteUpdateZjTzSupReport(zjTzSupReportList, zjTzSupReport);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSupReportList);
        }
    }
}
