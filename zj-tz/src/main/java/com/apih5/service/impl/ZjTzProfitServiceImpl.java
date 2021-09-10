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
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProfitExcelMapper;
import com.apih5.mybatis.dao.ZjTzProfitMapper;
import com.apih5.mybatis.pojo.ZjTzProfit;
import com.apih5.mybatis.pojo.ZjTzProfitExcel;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzProfitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProfitService")
public class ZjTzProfitServiceImpl implements ZjTzProfitService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProfitMapper zjTzProfitMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzProfitExcelMapper zjTzProfitExcelMapper;

    @Override
    public ResponseEntity getZjTzProfitListByCondition(ZjTzProfit zjTzProfit) {
        if (zjTzProfit == null) {
            zjTzProfit = new ZjTzProfit();
        }
        // 分页查询
        PageHelper.startPage(zjTzProfit.getPage(),zjTzProfit.getLimit());
        // 获取数据
        List<ZjTzProfit> zjTzProfitList = zjTzProfitMapper.selectByZjTzProfitList(zjTzProfit);
        for (ZjTzProfit zjTzProfit2 : zjTzProfitList) {
			
        	
        	ZjTzProfitExcel excel1 = new ZjTzProfitExcel();
        	excel1.setProfitId(zjTzProfit2.getProfitId());
        	excel1.setExt11("一、营业总收入");
        	List<ZjTzProfitExcel> excelsTotal1 = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(excel1);
        	if(excelsTotal1 != null && excelsTotal1.size() >0) {
        		zjTzProfit2.setTotal1(excelsTotal1.get(0).getExt13());
        	}
        	ZjTzProfitExcel excel2 = new ZjTzProfitExcel();
        	excel2.setProfitId(zjTzProfit2.getProfitId());
        	excel2.setExt11("二、营业总成本");
        	List<ZjTzProfitExcel> excelsTotal2 = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(excel2);
        	if(excelsTotal2 != null && excelsTotal2.size() >0) {
        		zjTzProfit2.setTotal2(excelsTotal2.get(0).getExt13());
        	}
        	ZjTzProfitExcel excel3 = new ZjTzProfitExcel();
        	excel3.setProfitId(zjTzProfit2.getProfitId());
        	excel3.setExt11("三、 营业利润（亏损以“-”号填列）");
        	List<ZjTzProfitExcel> excelsTotal3 = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(excel3);
        	if(excelsTotal3 != null && excelsTotal3.size() >0) {
        		zjTzProfit2.setTotal3(excelsTotal3.get(0).getExt13());
        	}
        	
        	ZjTzProfitExcel excel4 = new ZjTzProfitExcel();
        	excel4.setProfitId(zjTzProfit2.getProfitId());
        	excel4.setExt11("四、利润总额（亏损以“-”号填列）");
        	List<ZjTzProfitExcel> excelsTotal4 = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(excel4);
        	if(excelsTotal4 != null && excelsTotal4.size() >0) {
        		zjTzProfit2.setTotal4(excelsTotal4.get(0).getExt13());
        	}
        	ZjTzProfitExcel excel5 = new ZjTzProfitExcel();
        	excel5.setProfitId(zjTzProfit2.getProfitId());
        	excel5.setExt11("五、净利润（亏损以“-”号填列）");
        	List<ZjTzProfitExcel> excelsTotal5 = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(excel5);
        	if(excelsTotal5 != null && excelsTotal5.size() >0) {
        		zjTzProfit2.setTotal5(excelsTotal5.get(0).getExt13());
        	}
		}
        // 得到分页信息
        PageInfo<ZjTzProfit> p = new PageInfo<>(zjTzProfitList);

        return repEntity.okList(zjTzProfitList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProfitDetails(ZjTzProfit zjTzProfit) {
    	if (zjTzProfit == null) {
			zjTzProfit = new ZjTzProfit();
		}
		if(StrUtil.isNotEmpty(zjTzProfit.getWorkId())){
			ZjTzProfit cycle = new ZjTzProfit();
			cycle.setWorkId(zjTzProfit.getWorkId());
			List<ZjTzProfit> lifeCycleList = zjTzProfitMapper.selectByZjTzProfitList(zjTzProfit);
			if(lifeCycleList != null && lifeCycleList.size() >0) {
				zjTzProfit = lifeCycleList.get(0);
			}
		}else if(StrUtil.isNotEmpty(zjTzProfit.getProfitId())){
			zjTzProfit = zjTzProfitMapper.selectByPrimaryKey(zjTzProfit.getProfitId());
		}
		// 数据存在
		if (zjTzProfit != null && StrUtil.isNotEmpty(zjTzProfit.getProfitId())) {
			return repEntity.ok(zjTzProfit);
		}
		else {
			return repEntity.layerMessage("no", "无数据！");
		}
    }
    @Override
    public ResponseEntity saveZjTzProfit(ZjTzProfit zjTzProfit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        if(zjTzProfit.getYearMonthDate() != null) {
        	zjTzProfit.setYearMonthStr(DateUtil.format(zjTzProfit.getYearMonthDate(), "yyyy-MM"));
        	//重复校验
        	ZjTzProfit Profit = new ZjTzProfit();
        	Profit.setProjectId(zjTzProfit.getProjectId());
        	Profit.setYearMonthStr(zjTzProfit.getYearMonthStr());
        	List<ZjTzProfit> Profits = zjTzProfitMapper.selectByZjTzProfitList(Profit);
        	if(Profits != null && Profits.size() >0) {
        		return repEntity.layerMessage("no", "该年月本项目已经添加。请重新选择！");
        	}
        }
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzProfit.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzProfit.setProjectName(manage.getProjectName());
        }
        
        
        zjTzProfit.setProfitId(UuidUtil.generate());
        zjTzProfit.setCreateUserInfo(userKey, realName);
        int flag = zjTzProfitMapper.insert(zjTzProfit);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProfit);
        }
    }

    @Override
    public ResponseEntity updateZjTzProfit(ZjTzProfit zjTzProfit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProfit dbzjTzProfit = zjTzProfitMapper.selectByPrimaryKey(zjTzProfit.getProfitId());
        if (dbzjTzProfit != null && StrUtil.isNotEmpty(dbzjTzProfit.getProfitId())) {
//           // 年月date
//           dbzjTzProfit.setYearMonthDate(zjTzProfit.getYearMonthDate());
//           // 年月str
//           dbzjTzProfit.setYearMonthStr(zjTzProfit.getYearMonthStr());
//           // 项目id
//           dbzjTzProfit.setProjectId(zjTzProfit.getProjectId());
//           // 项目name
//           dbzjTzProfit.setProjectName(zjTzProfit.getProjectName());
//           // 登记日期
//           dbzjTzProfit.setRegisterDate(zjTzProfit.getRegisterDate());
//           // 登记用户
//           dbzjTzProfit.setRegisterPerson(zjTzProfit.getRegisterPerson());
//           // 营业总收入(元)
//           dbzjTzProfit.setTotal1(zjTzProfit.getTotal1());
//           // 营业总成本(元)
//           dbzjTzProfit.setTotal2(zjTzProfit.getTotal2());
//           // 营业利润(元)
//           dbzjTzProfit.setTotal3(zjTzProfit.getTotal3());
//           // 利润总额(元)
//           dbzjTzProfit.setTotal4(zjTzProfit.getTotal4());
//           // 净利润(元)
//           dbzjTzProfit.setTotal5(zjTzProfit.getTotal5());
           
        	// 流程id
        	dbzjTzProfit.setWorkId(zjTzProfit.getWorkId());
        	// 流程状态
        	dbzjTzProfit.setApih5FlowStatus(zjTzProfit.getApih5FlowStatus());
        	dbzjTzProfit.setOpinionField1(zjTzProfit.getOpinionField1());
        	if (StrUtil.equals("opinionField1", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField1(getOpinionContent(realName, dbzjTzProfit.getOpinionField1(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField2", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField2(getOpinionContent(realName, dbzjTzProfit.getOpinionField2(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField3", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField3(getOpinionContent(realName, dbzjTzProfit.getOpinionField3(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField4", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField4(getOpinionContent(realName, dbzjTzProfit.getOpinionField4(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField5", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField5(getOpinionContent(realName, dbzjTzProfit.getOpinionField5(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField6", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField6(getOpinionContent(realName, dbzjTzProfit.getOpinionField6(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField7", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField7(getOpinionContent(realName, dbzjTzProfit.getOpinionField7(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField8", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField8(getOpinionContent(realName, dbzjTzProfit.getOpinionField8(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField9", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField9(getOpinionContent(realName, dbzjTzProfit.getOpinionField9(), zjTzProfit.getOpinionContent()));
        	}
        	if (StrUtil.equals("opinionField10", zjTzProfit.getOpinionField(), true)) {
        		dbzjTzProfit.setOpinionField10(getOpinionContent(realName, dbzjTzProfit.getOpinionField10(), zjTzProfit.getOpinionContent()));
        	}
        	// 共通
           dbzjTzProfit.setModifyUserInfo(userKey, realName);
           flag = zjTzProfitMapper.updateByPrimaryKey(dbzjTzProfit);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProfit);
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
    public ResponseEntity batchDeleteUpdateZjTzProfit(List<ZjTzProfit> zjTzProfitList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProfitList != null && zjTzProfitList.size() > 0) {
        	for (ZjTzProfit zjTzProfit : zjTzProfitList) {
    			ZjTzProfitExcel ProfitExcel = new ZjTzProfitExcel();
    			ProfitExcel.setProfitId(zjTzProfit.getProfitId());
    			List<ZjTzProfitExcel> ProfitExcels = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(ProfitExcel);
    			if(ProfitExcels != null && ProfitExcels.size() >0) {
    				ProfitExcel.setModifyUserInfo(userKey, realName);
    				zjTzProfitExcelMapper.batchDeleteUpdateZjTzProfitExcel(ProfitExcels, ProfitExcel);
    			}
    		}
        	ZjTzProfit zjTzProfit = new ZjTzProfit();
           zjTzProfit.setModifyUserInfo(userKey, realName);
           flag = zjTzProfitMapper.batchDeleteUpdateZjTzProfit(zjTzProfitList, zjTzProfit);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProfitList);
        }
    }
}
