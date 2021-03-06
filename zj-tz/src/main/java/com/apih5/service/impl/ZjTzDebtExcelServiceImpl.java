package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzDebtExcelMapper;
import com.apih5.mybatis.dao.ZjTzDebtMapper;
import com.apih5.mybatis.pojo.ZjTzDebt;
import com.apih5.mybatis.pojo.ZjTzDebtExcel;
import com.apih5.service.ZjTzDebtExcelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zjTzDebtExcelService")
public class ZjTzDebtExcelServiceImpl implements ZjTzDebtExcelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzDebtExcelMapper zjTzDebtExcelMapper;
    
    @Autowired(required = true)
    private ZjTzDebtMapper zjTzDebtMapper;

    @Override
    public ResponseEntity getZjTzDebtExcelListByCondition(ZjTzDebtExcel zjTzDebtExcel) {
        if (zjTzDebtExcel == null) {
            zjTzDebtExcel = new ZjTzDebtExcel();
        }
        if(StrUtil.isNotEmpty(zjTzDebtExcel.getWorkId())) {
        	ZjTzDebt Debt = new ZjTzDebt();
        	Debt.setWorkId(zjTzDebtExcel.getWorkId());
        	List<ZjTzDebt> Debts = zjTzDebtMapper.selectByZjTzDebtList(Debt);
        	if(Debts != null && Debts.size() >0) {
        		zjTzDebtExcel.setDebtId(Debts.get(0).getDebtId());
        	}
        }
        if(StrUtil.isEmpty(zjTzDebtExcel.getDebtId())) {
        	return repEntity.layerMessage("no", "??????id?????????");
        }
        // ????????????
        PageHelper.startPage(zjTzDebtExcel.getPage(),zjTzDebtExcel.getLimit());
        // ????????????
        List<ZjTzDebtExcel> zjTzDebtExcelList = zjTzDebtExcelMapper.selectByZjTzDebtExcelList(zjTzDebtExcel);
        // ??????????????????
        PageInfo<ZjTzDebtExcel> p = new PageInfo<>(zjTzDebtExcelList);

        return repEntity.okList(zjTzDebtExcelList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDebtExcelDetails(ZjTzDebtExcel zjTzDebtExcel) {
        if (zjTzDebtExcel == null) {
            zjTzDebtExcel = new ZjTzDebtExcel();
        }
        // ????????????
        ZjTzDebtExcel dbZjTzDebtExcel = zjTzDebtExcelMapper.selectByPrimaryKey(zjTzDebtExcel.getDebtExcelId());
        // ????????????
        if (dbZjTzDebtExcel != null) {
            return repEntity.ok(dbZjTzDebtExcel);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzDebtExcel.setDebtExcelId(UuidUtil.generate());
        zjTzDebtExcel.setCreateUserInfo(userKey, realName);
        int flag = zjTzDebtExcelMapper.insert(zjTzDebtExcel);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDebtExcel);
        }
    }

    @Override
    public ResponseEntity updateZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDebtExcel dbzjTzDebtExcel = zjTzDebtExcelMapper.selectByPrimaryKey(zjTzDebtExcel.getDebtExcelId());
        if (dbzjTzDebtExcel != null && StrUtil.isNotEmpty(dbzjTzDebtExcel.getDebtExcelId())) {
           // ??????????????????id
           dbzjTzDebtExcel.setDebtId(zjTzDebtExcel.getDebtId());
           // ??????1
           dbzjTzDebtExcel.setExt11(zjTzDebtExcel.getExt11());
           // ??????1
           dbzjTzDebtExcel.setExt12(zjTzDebtExcel.getExt12());
           // ??????1
           dbzjTzDebtExcel.setExt13(zjTzDebtExcel.getExt13());
           // ??????2
           dbzjTzDebtExcel.setExt21(zjTzDebtExcel.getExt21());
           // ??????2
           dbzjTzDebtExcel.setExt22(zjTzDebtExcel.getExt22());
           // ??????2
           dbzjTzDebtExcel.setExt23(zjTzDebtExcel.getExt23());
           // ??????
           dbzjTzDebtExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzDebtExcelMapper.updateByPrimaryKey(dbzjTzDebtExcel);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDebtExcel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDebtExcel(List<ZjTzDebtExcel> zjTzDebtExcelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDebtExcelList != null && zjTzDebtExcelList.size() > 0) {
           ZjTzDebtExcel zjTzDebtExcel = new ZjTzDebtExcel();
           zjTzDebtExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzDebtExcelMapper.batchDeleteUpdateZjTzDebtExcel(zjTzDebtExcelList, zjTzDebtExcel);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDebtExcelList);
        }
    }

	@Override
	public ResponseEntity batchImportZjTzDebtExcel(ZjTzDebtExcel zjTzDebtExcel) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		 if(StrUtil.isNotEmpty(zjTzDebtExcel.getWorkId())) {
	        	ZjTzDebt Debt = new ZjTzDebt();
	        	Debt.setWorkId(zjTzDebtExcel.getWorkId());
	        	List<ZjTzDebt> Debts = zjTzDebtMapper.selectByZjTzDebtList(Debt);
	        	if(Debts != null && Debts.size() >0) {
	        		zjTzDebtExcel.setDebtId(Debts.get(0).getDebtId());
	        	}
	        }
		if(StrUtil.isEmpty(zjTzDebtExcel.getDebtId())) {
			return repEntity.layerMessage("no", "????????????,???????????????");
		}
		String filePath = Apih5Properties.getFilePath() + zjTzDebtExcel.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//???????????????????????????????????????
			if(readAll != null && readAll.size() >0) {
				ZjTzDebtExcel record = new ZjTzDebtExcel();
				record.setDebtId(zjTzDebtExcel.getDebtId());
				List<ZjTzDebtExcel> debtExcels = zjTzDebtExcelMapper.selectByZjTzDebtExcelList(record);
				if(debtExcels != null && debtExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zjTzDebtExcelMapper.batchDeleteUpdateZjTzDebtExcel(debtExcels, record);
				}
			}
	    	for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		String ext11 = json.getStr("???  ???");
	    		String ext12 = json.getStr("??????");
	    		String ext13 = json.getStr("??????");
	    		String ext21 = json.getStr("???  ???2");
	    		String ext22 = json.getStr("??????2");
	    		String ext23 = json.getStr("??????2");
	    		
	    		BigDecimal ext13_bd = new BigDecimal("0");
	    		if(ext13 != null && StrUtil.isNotEmpty(ext13.toString())) {
	    			ext13_bd = new BigDecimal(ext13.toString());
	    		}
	    		
	    		BigDecimal ext23_bd = new BigDecimal("0");
	    		if(ext23 != null && StrUtil.isNotEmpty(ext23.toString())) {
	    			ext23_bd = new BigDecimal(ext23.toString());
	    		}
	    		
	    		//add
	    		ZjTzDebtExcel base = new ZjTzDebtExcel();
	    		base.setDebtExcelId(UuidUtil.generate());
	    		base.setDebtId(zjTzDebtExcel.getDebtId());
	    		base.setExt11(ext11);
	    		base.setExt12(ext12);
	    		base.setExt13(ext13_bd.toPlainString());
	    		base.setExt21(ext21);
	    		base.setExt22(ext22);
	    		base.setExt23(ext23_bd.toPlainString());
	    		base.setDelFlag("0");
	    		base.setCreateUserInfo(userKey, realName);
	    		zjTzDebtExcelMapper.insert(base);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		return repEntity.ok("ok");
	}
}
