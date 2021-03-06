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
import com.apih5.mybatis.dao.ZjTzProfitExcelMapper;
import com.apih5.mybatis.dao.ZjTzProfitMapper;
import com.apih5.mybatis.pojo.ZjTzProfit;
import com.apih5.mybatis.pojo.ZjTzProfitExcel;
import com.apih5.service.ZjTzProfitExcelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zjTzProfitExcelService")
public class ZjTzProfitExcelServiceImpl implements ZjTzProfitExcelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProfitExcelMapper zjTzProfitExcelMapper;
    
    @Autowired(required = true)
    private ZjTzProfitMapper zjTzProfitMapper;

    @Override
    public ResponseEntity getZjTzProfitExcelListByCondition(ZjTzProfitExcel zjTzProfitExcel) {
        if (zjTzProfitExcel == null) {
            zjTzProfitExcel = new ZjTzProfitExcel();
        }
        if(StrUtil.isNotEmpty(zjTzProfitExcel.getWorkId())) {
        	ZjTzProfit Profit = new ZjTzProfit();
        	Profit.setWorkId(zjTzProfitExcel.getWorkId());
        	List<ZjTzProfit> Profits = zjTzProfitMapper.selectByZjTzProfitList(Profit);
        	if(Profits != null && Profits.size() >0) {
        		zjTzProfitExcel.setProfitId(Profits.get(0).getProfitId());
        	}
        }
        if(StrUtil.isEmpty(zjTzProfitExcel.getProfitId())) {
        	return repEntity.layerMessage("no", "??????id?????????");
        }
        // ????????????
        PageHelper.startPage(zjTzProfitExcel.getPage(),zjTzProfitExcel.getLimit());
        // ????????????
        List<ZjTzProfitExcel> zjTzProfitExcelList = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(zjTzProfitExcel);
        // ??????????????????
        PageInfo<ZjTzProfitExcel> p = new PageInfo<>(zjTzProfitExcelList);

        return repEntity.okList(zjTzProfitExcelList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProfitExcelDetails(ZjTzProfitExcel zjTzProfitExcel) {
        if (zjTzProfitExcel == null) {
            zjTzProfitExcel = new ZjTzProfitExcel();
        }
        // ????????????
        ZjTzProfitExcel dbZjTzProfitExcel = zjTzProfitExcelMapper.selectByPrimaryKey(zjTzProfitExcel.getProfitExcelId());
        // ????????????
        if (dbZjTzProfitExcel != null) {
            return repEntity.ok(dbZjTzProfitExcel);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProfitExcel.setProfitExcelId(UuidUtil.generate());
        zjTzProfitExcel.setCreateUserInfo(userKey, realName);
        int flag = zjTzProfitExcelMapper.insert(zjTzProfitExcel);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProfitExcel);
        }
    }

    @Override
    public ResponseEntity updateZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProfitExcel dbzjTzProfitExcel = zjTzProfitExcelMapper.selectByPrimaryKey(zjTzProfitExcel.getProfitExcelId());
        if (dbzjTzProfitExcel != null && StrUtil.isNotEmpty(dbzjTzProfitExcel.getProfitExcelId())) {
           // ??????id
           dbzjTzProfitExcel.setProfitId(zjTzProfitExcel.getProfitId());
           // ??????
           dbzjTzProfitExcel.setExt11(zjTzProfitExcel.getExt11());
           // ??????
           dbzjTzProfitExcel.setExt12(zjTzProfitExcel.getExt12());
           // ????????????
           dbzjTzProfitExcel.setExt13(zjTzProfitExcel.getExt13());
           // ????????????
           dbzjTzProfitExcel.setExt14(zjTzProfitExcel.getExt14());
           // ??????
           dbzjTzProfitExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzProfitExcelMapper.updateByPrimaryKey(dbzjTzProfitExcel);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProfitExcel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProfitExcel(List<ZjTzProfitExcel> zjTzProfitExcelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProfitExcelList != null && zjTzProfitExcelList.size() > 0) {
           ZjTzProfitExcel zjTzProfitExcel = new ZjTzProfitExcel();
           zjTzProfitExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzProfitExcelMapper.batchDeleteUpdateZjTzProfitExcel(zjTzProfitExcelList, zjTzProfitExcel);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProfitExcelList);
        }
    }

	@Override
	public ResponseEntity batchImportZjTzProfitExcel(ZjTzProfitExcel zjTzProfitExcel) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if(StrUtil.isNotEmpty(zjTzProfitExcel.getWorkId())) {
        	ZjTzProfit Profit = new ZjTzProfit();
        	Profit.setWorkId(zjTzProfitExcel.getWorkId());
        	List<ZjTzProfit> Profits = zjTzProfitMapper.selectByZjTzProfitList(Profit);
        	if(Profits != null && Profits.size() >0) {
        		zjTzProfitExcel.setProfitId(Profits.get(0).getProfitId());
        	}
        }
		if(StrUtil.isEmpty(zjTzProfitExcel.getProfitId())) {
			return repEntity.layerMessage("no", "????????????,???????????????");
		}
		String filePath = Apih5Properties.getFilePath() + zjTzProfitExcel.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//???????????????????????????????????????
			if(readAll != null && readAll.size() >0) {
				ZjTzProfitExcel record = new ZjTzProfitExcel();
				record.setProfitId(zjTzProfitExcel.getProfitId());
				List<ZjTzProfitExcel> ProfitExcels = zjTzProfitExcelMapper.selectByZjTzProfitExcelList(record);
				if(ProfitExcels != null && ProfitExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zjTzProfitExcelMapper.batchDeleteUpdateZjTzProfitExcel(ProfitExcels, record);
				}
			}
			for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		String ext11 = json.getStr("???    ???");
	    		String ext12 = json.getStr("??????");
	    		String ext13 = json.getStr("????????????");
	    		String ext14 = json.getStr("????????????");
	    		
	    		BigDecimal ext13_bd = new BigDecimal("0");
	    		if(ext13 != null && StrUtil.isNotEmpty(ext13.toString())) {
	    			ext13_bd = new BigDecimal(ext13.toString());
	    		}
	    		
	    		BigDecimal ext14_bd = new BigDecimal("0");
	    		if(ext14 != null && StrUtil.isNotEmpty(ext14.toString())) {
	    			ext14_bd = new BigDecimal(ext14.toString());
	    		}
	    		//add
	    		ZjTzProfitExcel base = new ZjTzProfitExcel();
	    		base.setProfitExcelId(UuidUtil.generate());
	    		base.setProfitId(zjTzProfitExcel.getProfitId());
	    		base.setExt11(ext11);
	    		base.setExt12(ext12);
	    		base.setExt13(ext13_bd.toPlainString());
	    		base.setExt14(ext14_bd.toPlainString());
	    		base.setDelFlag("0");
	    		base.setCreateUserInfo(userKey, realName);
	    		zjTzProfitExcelMapper.insert(base);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		return repEntity.ok("ok");
	}
}
