package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzDebtExcelMapper;
import com.apih5.mybatis.dao.ZjTzDebtMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzDebt;
import com.apih5.mybatis.pojo.ZjTzDebtExcel;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzDebtService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDebtService")
public class ZjTzDebtServiceImpl implements ZjTzDebtService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzDebtMapper zjTzDebtMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzDebtExcelMapper zjTzDebtExcelMapper;

    @Override
    public ResponseEntity getZjTzDebtListByCondition(ZjTzDebt zjTzDebt) {
        if (zjTzDebt == null) {
            zjTzDebt = new ZjTzDebt();
        }
        // 分页查询
        PageHelper.startPage(zjTzDebt.getPage(),zjTzDebt.getLimit());
        // 获取数据
        List<ZjTzDebt> zjTzDebtList = zjTzDebtMapper.selectByZjTzDebtList(zjTzDebt);
        for (ZjTzDebt zjTzDebt2 : zjTzDebtList) {
        	ZjTzDebtExcel excel1 = new ZjTzDebtExcel();
        	excel1.setDebtId(zjTzDebt2.getDebtId());
        	excel1.setExt11("资产总计");
        	List<ZjTzDebtExcel> excelsTotal1 = zjTzDebtExcelMapper.selectByZjTzDebtExcelList(excel1);
        	if(excelsTotal1 != null && excelsTotal1.size() >0) {
        		zjTzDebt2.setTotal1(excelsTotal1.get(0).getExt13());
        	}
        	ZjTzDebtExcel excel2 = new ZjTzDebtExcel();
        	excel2.setDebtId(zjTzDebt2.getDebtId());
        	excel2.setExt21("负债和所有者权益总计");
        	List<ZjTzDebtExcel> excelsTotal2 = zjTzDebtExcelMapper.selectByZjTzDebtExcelList(excel2);
        	if(excelsTotal2 != null && excelsTotal2.size() >0) {
        		zjTzDebt2.setTotal2(excelsTotal2.get(0).getExt23());
        	}
		}
        // 得到分页信息
        PageInfo<ZjTzDebt> p = new PageInfo<>(zjTzDebtList);
        return repEntity.okList(zjTzDebtList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDebtDetails(ZjTzDebt zjTzDebt) {
    	if (zjTzDebt == null) {
			zjTzDebt = new ZjTzDebt();
		}
		if(StrUtil.isNotEmpty(zjTzDebt.getWorkId())){
			ZjTzDebt cycle = new ZjTzDebt();
			cycle.setWorkId(zjTzDebt.getWorkId());
			List<ZjTzDebt> lifeCycleList = zjTzDebtMapper.selectByZjTzDebtList(zjTzDebt);
			if(lifeCycleList != null && lifeCycleList.size() >0) {
				zjTzDebt = lifeCycleList.get(0);
			}
		}else if(StrUtil.isNotEmpty(zjTzDebt.getDebtId())){
			zjTzDebt = zjTzDebtMapper.selectByPrimaryKey(zjTzDebt.getDebtId());
		}
		// 数据存在
		if (zjTzDebt != null && StrUtil.isNotEmpty(zjTzDebt.getDebtId())) {
			return repEntity.ok(zjTzDebt);
		}
		else {
			return repEntity.layerMessage("no", "无数据！");
		}
    }
    @Override
    public ResponseEntity saveZjTzDebt(ZjTzDebt zjTzDebt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zjTzDebt.getYearMonthDate() != null) {
        	zjTzDebt.setYearMonthStr(DateUtil.format(zjTzDebt.getYearMonthDate(), "yyyy-MM"));
        	//重复校验
        	ZjTzDebt Debt = new ZjTzDebt();
        	Debt.setProjectId(zjTzDebt.getProjectId());
        	Debt.setYearMonthStr(zjTzDebt.getYearMonthStr());
        	List<ZjTzDebt> Debts = zjTzDebtMapper.selectByZjTzDebtList(Debt);
        	if(Debts != null && Debts.size() >0) {
        		return repEntity.layerMessage("no", "该年月本项目已经添加。请重新选择！");
        	}
        }
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzDebt.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzDebt.setProjectName(manage.getProjectName());
        }
        
        zjTzDebt.setDebtId(UuidUtil.generate());
        zjTzDebt.setCreateUserInfo(userKey, realName);
        int flag = zjTzDebtMapper.insert(zjTzDebt);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDebt);
        }
    }

    @Override
    public ResponseEntity updateZjTzDebt(ZjTzDebt zjTzDebt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDebt dbzjTzDebt = zjTzDebtMapper.selectByPrimaryKey(zjTzDebt.getDebtId());
        if (dbzjTzDebt != null && StrUtil.isNotEmpty(dbzjTzDebt.getDebtId())) {
//           // 年月date
//           dbzjTzDebt.setYearMonthDate(zjTzDebt.getYearMonthDate());
//           // 年月str
//           dbzjTzDebt.setYearMonthStr(zjTzDebt.getYearMonthStr());
//           // 项目id
//           dbzjTzDebt.setProjectId(zjTzDebt.getProjectId());
//           // 项目name
//           dbzjTzDebt.setProjectName(zjTzDebt.getProjectName());
//           // 登记日期
//           dbzjTzDebt.setRegisterDate(zjTzDebt.getRegisterDate());
//           // 登记用户
//           dbzjTzDebt.setRegisterPerson(zjTzDebt.getRegisterPerson());
//           // 资产总计(元)
//           dbzjTzDebt.setTotal1(zjTzDebt.getTotal1());
//           // 负债和所有者权益总计(元)
//           dbzjTzDebt.setTotal2(zjTzDebt.getTotal2());
        	// 流程id
        	dbzjTzDebt.setWorkId(zjTzDebt.getWorkId());
        	// 流程状态
        	dbzjTzDebt.setApih5FlowStatus(zjTzDebt.getApih5FlowStatus());
        	dbzjTzDebt.setOpinionField1(zjTzDebt.getOpinionField1());
        	if (StrUtil.equals("opinionField1", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField1(getOpinionContent(realName, dbzjTzDebt.getOpinionField1(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField2", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField2(getOpinionContent(realName, dbzjTzDebt.getOpinionField2(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField3", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField3(getOpinionContent(realName, dbzjTzDebt.getOpinionField3(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField4", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField4(getOpinionContent(realName, dbzjTzDebt.getOpinionField4(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField5", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField5(getOpinionContent(realName, dbzjTzDebt.getOpinionField5(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField6", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField6(getOpinionContent(realName, dbzjTzDebt.getOpinionField6(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField7", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField7(getOpinionContent(realName, dbzjTzDebt.getOpinionField7(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField8", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField8(getOpinionContent(realName, dbzjTzDebt.getOpinionField8(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField9", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField9(getOpinionContent(realName, dbzjTzDebt.getOpinionField9(), zjTzDebt.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField10", zjTzDebt.getOpinionField(), true)) {
        		dbzjTzDebt.setOpinionField10(getOpinionContent(realName, dbzjTzDebt.getOpinionField10(), zjTzDebt.getOpinionContent()));
        	}
        	// 共通
        	dbzjTzDebt.setModifyUserInfo(userKey, realName);
           flag = zjTzDebtMapper.updateByPrimaryKey(dbzjTzDebt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDebt);
        }
    }

    private String getOpinionContent(String userName, String dbOpinionContent, String opinionContent){
  		if(StrUtil.isNotEmpty(opinionContent)){
  			opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
  			opinionContent += "<br/>" + userName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
  		}
  		return opinionContent;
  	}
    @Override
    public ResponseEntity batchDeleteUpdateZjTzDebt(List<ZjTzDebt> zjTzDebtList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzDebtList != null && zjTzDebtList.size() > 0) {
    		for (ZjTzDebt zjTzDebt : zjTzDebtList) {
    			ZjTzDebtExcel debtExcel = new ZjTzDebtExcel();
    			debtExcel.setDebtId(zjTzDebt.getDebtId());
    			List<ZjTzDebtExcel> debtExcels = zjTzDebtExcelMapper.selectByZjTzDebtExcelList(debtExcel);
    			if(debtExcels != null && debtExcels.size() >0) {
    				debtExcel.setModifyUserInfo(userKey, realName);
    				zjTzDebtExcelMapper.batchDeleteUpdateZjTzDebtExcel(debtExcels, debtExcel);
    			}
    		}
    		ZjTzDebt zjTzDebt = new ZjTzDebt();
    		zjTzDebt.setModifyUserInfo(userKey, realName);
    		flag = zjTzDebtMapper.batchDeleteUpdateZjTzDebt(zjTzDebtList, zjTzDebt);
    	}
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDebtList);
        }
    }
}
