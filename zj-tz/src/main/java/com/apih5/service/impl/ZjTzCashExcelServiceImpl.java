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
import com.apih5.mybatis.dao.ZjTzCashExcelMapper;
import com.apih5.mybatis.dao.ZjTzCashMapper;
import com.apih5.mybatis.pojo.ZjTzCash;
import com.apih5.mybatis.pojo.ZjTzCashExcel;
import com.apih5.service.ZjTzCashExcelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zjTzCashExcelService")
public class ZjTzCashExcelServiceImpl implements ZjTzCashExcelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzCashExcelMapper zjTzCashExcelMapper;
    
    @Autowired(required = true)
    private ZjTzCashMapper zjTzCashMapper;

    @Override
    public ResponseEntity getZjTzCashExcelListByCondition(ZjTzCashExcel zjTzCashExcel) {
        if (zjTzCashExcel == null) {
            zjTzCashExcel = new ZjTzCashExcel();
        }
        if(StrUtil.isNotEmpty(zjTzCashExcel.getWorkId())) {
        	ZjTzCash cash = new ZjTzCash();
        	cash.setWorkId(zjTzCashExcel.getWorkId());
        	List<ZjTzCash> cashs = zjTzCashMapper.selectByZjTzCashList(cash);
        	if(cashs != null && cashs.size() >0) {
        		zjTzCashExcel.setCashId(cashs.get(0).getCashId());
        	}
        }
        if(StrUtil.isEmpty(zjTzCashExcel.getCashId())) {
        	return repEntity.layerMessage("no", "主键id没有传");
        }
        // 分页查询
        PageHelper.startPage(zjTzCashExcel.getPage(),zjTzCashExcel.getLimit());
        // 获取数据
        List<ZjTzCashExcel> zjTzCashExcelList = zjTzCashExcelMapper.selectByZjTzCashExcelList(zjTzCashExcel);
        // 得到分页信息
        PageInfo<ZjTzCashExcel> p = new PageInfo<>(zjTzCashExcelList);

        return repEntity.okList(zjTzCashExcelList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzCashExcelDetails(ZjTzCashExcel zjTzCashExcel) {
        if (zjTzCashExcel == null) {
            zjTzCashExcel = new ZjTzCashExcel();
        }
        // 获取数据
        ZjTzCashExcel dbZjTzCashExcel = zjTzCashExcelMapper.selectByPrimaryKey(zjTzCashExcel.getCashExcelId());
        // 数据存在
        if (dbZjTzCashExcel != null) {
            return repEntity.ok(dbZjTzCashExcel);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzCashExcel(ZjTzCashExcel zjTzCashExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzCashExcel.setCashExcelId(UuidUtil.generate());
        zjTzCashExcel.setCreateUserInfo(userKey, realName);
        int flag = zjTzCashExcelMapper.insert(zjTzCashExcel);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzCashExcel);
        }
    }

    @Override
    public ResponseEntity updateZjTzCashExcel(ZjTzCashExcel zjTzCashExcel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzCashExcel dbzjTzCashExcel = zjTzCashExcelMapper.selectByPrimaryKey(zjTzCashExcel.getCashExcelId());
        if (dbzjTzCashExcel != null && StrUtil.isNotEmpty(dbzjTzCashExcel.getCashExcelId())) {
           // 利润id
           dbzjTzCashExcel.setCashId(zjTzCashExcel.getCashId());
           // 项目
           dbzjTzCashExcel.setExt11(zjTzCashExcel.getExt11());
           // 行次
           dbzjTzCashExcel.setExt12(zjTzCashExcel.getExt12());
           // 本期金额
           dbzjTzCashExcel.setExt13(zjTzCashExcel.getExt13());
           // 上期金额
           dbzjTzCashExcel.setExt14(zjTzCashExcel.getExt14());
           // 共通
           dbzjTzCashExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzCashExcelMapper.updateByPrimaryKey(dbzjTzCashExcel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCashExcel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzCashExcel(List<ZjTzCashExcel> zjTzCashExcelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCashExcelList != null && zjTzCashExcelList.size() > 0) {
           ZjTzCashExcel zjTzCashExcel = new ZjTzCashExcel();
           zjTzCashExcel.setModifyUserInfo(userKey, realName);
           flag = zjTzCashExcelMapper.batchDeleteUpdateZjTzCashExcel(zjTzCashExcelList, zjTzCashExcel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzCashExcelList);
        }
    }

	@Override
	public ResponseEntity batchImportZjTzCashExcel(ZjTzCashExcel zjTzCashExcel) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		 if(StrUtil.isNotEmpty(zjTzCashExcel.getWorkId())) {
	        	ZjTzCash cash = new ZjTzCash();
	        	cash.setWorkId(zjTzCashExcel.getWorkId());
	        	List<ZjTzCash> cashs = zjTzCashMapper.selectByZjTzCashList(cash);
	        	if(cashs != null && cashs.size() >0) {
	        		zjTzCashExcel.setCashId(cashs.get(0).getCashId());
	        	}
	        }
		if(StrUtil.isEmpty(zjTzCashExcel.getCashId())) {
			return repEntity.layerMessage("no", "请先选择,然后再导入");
		}
		String filePath = Apih5Properties.getFilePath() + zjTzCashExcel.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//重复导入删除上次的导入记录
			if(readAll != null && readAll.size() >0) {
				ZjTzCashExcel record = new ZjTzCashExcel();
				record.setCashId(zjTzCashExcel.getCashId());
				List<ZjTzCashExcel> debtExcels = zjTzCashExcelMapper.selectByZjTzCashExcelList(record);
				if(debtExcels != null && debtExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zjTzCashExcelMapper.batchDeleteUpdateZjTzCashExcel(debtExcels, record);
				}
			}
			for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		String ext11 = json.getStr("项     目");
	    		String ext12 = json.getStr("行次");
	    		String ext13 = json.getStr("本年累计金额");
	    		String ext14 = json.getStr("上年同期累计金额");
	    		
	    		BigDecimal ext13_bd = new BigDecimal("0");
	    		if(ext13 != null && StrUtil.isNotEmpty(ext13.toString())) {
	    			ext13_bd = new BigDecimal(ext13.toString());
	    		}
	    		
	    		BigDecimal ext14_bd = new BigDecimal("0");
	    		if(ext14 != null && StrUtil.isNotEmpty(ext14.toString())) {
	    			ext14_bd = new BigDecimal(ext14.toString());
	    		}
	    		
	    		//add
	    		ZjTzCashExcel base = new ZjTzCashExcel();
	    		base.setCashExcelId(UuidUtil.generate());
	    		base.setCashId(zjTzCashExcel.getCashId());
	    		base.setExt11(ext11);
	    		base.setExt12(ext12);
	    		base.setExt13(ext13_bd.toPlainString());
	    		base.setExt14(ext14_bd.toPlainString());
	    		base.setDelFlag("0");
	    		base.setCreateUserInfo(userKey, realName);
	    		zjTzCashExcelMapper.insert(base);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		return repEntity.ok("ok");
	}
}
