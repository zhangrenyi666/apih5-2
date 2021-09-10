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
import com.apih5.mybatis.dao.ZjTzCashExcelMapper;
import com.apih5.mybatis.dao.ZjTzCashMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.mybatis.pojo.ZjTzCashExcel;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.service.ZjTzCashService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzCashService")
public class ZjTzCashServiceImpl implements ZjTzCashService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzCashMapper zjTzCashMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzCashExcelMapper zjTzCashExcelMapper;

    @Override
    public ResponseEntity getZjTzCashListByCondition(ZjTzCash zjTzCash) {
        if (zjTzCash == null) {
            zjTzCash = new ZjTzCash();
        }
        // 分页查询
        PageHelper.startPage(zjTzCash.getPage(),zjTzCash.getLimit());
        // 获取数据
        List<ZjTzCash> zjTzCashList = zjTzCashMapper.selectByZjTzCashList(zjTzCash);
        for (ZjTzCash zjTzCash2 : zjTzCashList) {
        	ZjTzCashExcel excel1 = new ZjTzCashExcel();
        	excel1.setCashId(zjTzCash2.getCashId());
        	excel1.setExt11("一、经营活动产生的现金流量：");
        	List<ZjTzCashExcel> cashExcelsTotal1 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel1);
        	if(cashExcelsTotal1 != null && cashExcelsTotal1.size() >0) {
        		zjTzCash2.setTotal1(cashExcelsTotal1.get(0).getExt13());
        	}
        	ZjTzCashExcel excel2 = new ZjTzCashExcel();
        	excel2.setCashId(zjTzCash2.getCashId());
        	excel2.setExt11("二、投资活动产生的现金流量：");
        	List<ZjTzCashExcel> cashExcelsTotal2 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel2);
        	if(cashExcelsTotal2 != null && cashExcelsTotal2.size() >0) {
        		zjTzCash2.setTotal2(cashExcelsTotal2.get(0).getExt13());
        	}
        	ZjTzCashExcel excel3 = new ZjTzCashExcel();
        	excel3.setCashId(zjTzCash2.getCashId());
        	excel3.setExt11("三、筹资活动产生的现金流量：");
        	List<ZjTzCashExcel> cashExcelsTotal3 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel3);
        	if(cashExcelsTotal3 != null && cashExcelsTotal3.size() >0) {
        		zjTzCash2.setTotal3(cashExcelsTotal3.get(0).getExt13());
        	}
        	ZjTzCashExcel excel4 = new ZjTzCashExcel();
        	excel4.setCashId(zjTzCash2.getCashId());
        	excel4.setExt11("四、汇率变动对现金及现金等价物的影响");
        	List<ZjTzCashExcel> cashExcelsTotal4 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel4);
        	if(cashExcelsTotal4 != null && cashExcelsTotal4.size() >0) {
        		zjTzCash2.setTotal4(cashExcelsTotal4.get(0).getExt13());
        	}
        	ZjTzCashExcel excel5 = new ZjTzCashExcel();
        	excel5.setCashId(zjTzCash2.getCashId());
        	excel5.setExt11("五、现金及现金等价物净增加额");
        	List<ZjTzCashExcel> cashExcelsTotal5 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel5);
        	if(cashExcelsTotal5 != null && cashExcelsTotal5.size() >0) {
        		zjTzCash2.setTotal5(cashExcelsTotal5.get(0).getExt13());
        	}
        	ZjTzCashExcel excel6 = new ZjTzCashExcel();
        	excel6.setCashId(zjTzCash2.getCashId());
        	excel6.setExt11("六、期末现金及现金等价物余额");
        	List<ZjTzCashExcel> cashExcelsTotal6 = zjTzCashExcelMapper.selectByZjTzCashExcelList(excel6);
        	if(cashExcelsTotal6 != null && cashExcelsTotal6.size() >0) {
        		zjTzCash2.setTotal6(cashExcelsTotal6.get(0).getExt13());
        	}
        	
        	
		}
        // 得到分页信息
        PageInfo<ZjTzCash> p = new PageInfo<>(zjTzCashList);

        return repEntity.okList(zjTzCashList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzCashDetails(ZjTzCash zjTzCash) {
    	if (zjTzCash == null) {
			zjTzCash = new ZjTzCash();
		}
		if(StrUtil.isNotEmpty(zjTzCash.getWorkId())){
			ZjTzCash cycle = new ZjTzCash();
			cycle.setWorkId(zjTzCash.getWorkId());
			List<ZjTzCash> lifeCycleList = zjTzCashMapper.selectByZjTzCashList(zjTzCash);
			if(lifeCycleList != null && lifeCycleList.size() >0) {
				zjTzCash = lifeCycleList.get(0);
			}
		}else if(StrUtil.isNotEmpty(zjTzCash.getCashId())){
			zjTzCash = zjTzCashMapper.selectByPrimaryKey(zjTzCash.getCashId());
		}
		// 数据存在
		if (zjTzCash != null && StrUtil.isNotEmpty(zjTzCash.getCashId())) {
			return repEntity.ok(zjTzCash);
		}
		else {
			return repEntity.layerMessage("no", "无数据！");
		}
    }
    @Override
    public ResponseEntity saveZjTzCash(ZjTzCash zjTzCash) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        if(zjTzCash.getYearMonthDate() != null) {
        	zjTzCash.setYearMonthStr(DateUtil.format(zjTzCash.getYearMonthDate(), "yyyy-MM"));
        	//重复校验
        	ZjTzCash Cash = new ZjTzCash();
        	Cash.setProjectId(zjTzCash.getProjectId());
        	Cash.setYearMonthStr(zjTzCash.getYearMonthStr());
        	List<ZjTzCash> Cashs = zjTzCashMapper.selectByZjTzCashList(Cash);
        	if(Cashs != null && Cashs.size() >0) {
        		return repEntity.layerMessage("no", "该年月本项目已经添加。请重新选择！");
        	}
        }
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzCash.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzCash.setProjectName(manage.getProjectName());
        }
        zjTzCash.setCashId(UuidUtil.generate());
        zjTzCash.setCreateUserInfo(userKey, realName);
        int flag = zjTzCashMapper.insert(zjTzCash);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzCash);
        }
    }

    @Override
    public ResponseEntity updateZjTzCash(ZjTzCash zjTzCash) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzCash dbzjTzCash = zjTzCashMapper.selectByPrimaryKey(zjTzCash.getCashId());
        if (dbzjTzCash != null && StrUtil.isNotEmpty(dbzjTzCash.getCashId())) {
//           // 年月date
//           dbzjTzCash.setYearMonthDate(zjTzCash.getYearMonthDate());
//           // 年月str
//           dbzjTzCash.setYearMonthStr(zjTzCash.getYearMonthStr());
//           // 项目id
//           dbzjTzCash.setProjectId(zjTzCash.getProjectId());
//           // 项目name
//           dbzjTzCash.setProjectName(zjTzCash.getProjectName());
//           // 登记日期
//           dbzjTzCash.setRegisterDate(zjTzCash.getRegisterDate());
//           // 登记用户
//           dbzjTzCash.setRegisterPerson(zjTzCash.getRegisterPerson());
//           // 经营活动产生的现金流量净额（元）
//           dbzjTzCash.setTotal1(zjTzCash.getTotal1());
//           // 投资活动产生的现金流量净额（元）
//           dbzjTzCash.setTotal2(zjTzCash.getTotal2());
//           // 筹资活动产生的现金流量净额（元）
//           dbzjTzCash.setTotal3(zjTzCash.getTotal3());
//           // 汇率变动对现金及现金等价物的影响
//           dbzjTzCash.setTotal4(zjTzCash.getTotal4());
//           // 现金及现金等价物净增加额（元）
//           dbzjTzCash.setTotal5(zjTzCash.getTotal5());
//           // 期末现金及现金等价物余额（元）
//           dbzjTzCash.setTotal6(zjTzCash.getTotal6());
        	// 流程id
        	dbzjTzCash.setWorkId(zjTzCash.getWorkId());
        	// 流程状态
        	dbzjTzCash.setApih5FlowStatus(zjTzCash.getApih5FlowStatus());
        	dbzjTzCash.setOpinionField1(zjTzCash.getOpinionField1());
        	if (StrUtil.equals("opinionField1", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField1(getOpinionContent(realName, dbzjTzCash.getOpinionField1(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField2", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField2(getOpinionContent(realName, dbzjTzCash.getOpinionField2(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField3", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField3(getOpinionContent(realName, dbzjTzCash.getOpinionField3(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField4", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField4(getOpinionContent(realName, dbzjTzCash.getOpinionField4(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField5", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField5(getOpinionContent(realName, dbzjTzCash.getOpinionField5(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField6", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField6(getOpinionContent(realName, dbzjTzCash.getOpinionField6(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField7", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField7(getOpinionContent(realName, dbzjTzCash.getOpinionField7(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField8", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField8(getOpinionContent(realName, dbzjTzCash.getOpinionField8(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField9", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField9(getOpinionContent(realName, dbzjTzCash.getOpinionField9(), zjTzCash.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField10", zjTzCash.getOpinionField(), true)) {
        		dbzjTzCash.setOpinionField10(getOpinionContent(realName, dbzjTzCash.getOpinionField10(), zjTzCash.getOpinionContent()));
        	}
        	// 共通
           dbzjTzCash.setModifyUserInfo(userKey, realName);
           flag = zjTzCashMapper.updateByPrimaryKey(dbzjTzCash);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCash);
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
    public ResponseEntity batchDeleteUpdateZjTzCash(List<ZjTzCash> zjTzCashList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCashList != null && zjTzCashList.size() > 0) {
        	for (ZjTzCash zjTzCash : zjTzCashList) {
    			ZjTzCashExcel CashExcel = new ZjTzCashExcel();
    			CashExcel.setCashId(zjTzCash.getCashId());
    			List<ZjTzCashExcel> CashExcels = zjTzCashExcelMapper.selectByZjTzCashExcelList(CashExcel);
    			if(CashExcels != null && CashExcels.size() >0) {
    				CashExcel.setModifyUserInfo(userKey, realName);
    				zjTzCashExcelMapper.batchDeleteUpdateZjTzCashExcel(CashExcels, CashExcel);
    			}
    		}
        	ZjTzCash zjTzCash = new ZjTzCash();
           zjTzCash.setModifyUserInfo(userKey, realName);
           flag = zjTzCashMapper.batchDeleteUpdateZjTzCash(zjTzCashList, zjTzCash);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzCashList);
        }
    }
}
