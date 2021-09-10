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
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqRentOutEqRecordItemMapper;
import com.apih5.mybatis.dao.ZxEqRentOutEqRecordMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecord;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqRentOutEqRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxEqRentOutEqRecordService")
public class ZxEqRentOutEqRecordServiceImpl implements ZxEqRentOutEqRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqRentOutEqRecordMapper zxEqRentOutEqRecordMapper;
    
    @Autowired(required = true)
    private ZxEqRentOutEqRecordItemMapper zxEqRentOutEqRecordItemMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxEqRentOutEqRecordListByCondition(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        if (zxEqRentOutEqRecord == null) {
            zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqRentOutEqRecord.setCompanyId("");
        	zxEqRentOutEqRecord.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqRentOutEqRecord.setCompanyId(zxEqRentOutEqRecord.getOrgID());
        	zxEqRentOutEqRecord.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());
        }
        
        
        // 分页查询
        PageHelper.startPage(zxEqRentOutEqRecord.getPage(),zxEqRentOutEqRecord.getLimit());
        // 获取数据
        List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList = zxEqRentOutEqRecordMapper.selectByZxEqRentOutEqRecordList(zxEqRentOutEqRecord);
        for (ZxEqRentOutEqRecord zxEqRentOutEqRecord2 : zxEqRentOutEqRecordList) {
        	ZxEqRentOutEqRecordItem item = new ZxEqRentOutEqRecordItem();
        	item.setBillID(zxEqRentOutEqRecord2.getId());
        	List<ZxEqRentOutEqRecordItem> itemList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(item);
        	zxEqRentOutEqRecord2.setItemList(itemList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqRentOutEqRecord2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqRentOutEqRecord2.setFileList(fileList);
        }
        // 得到分页信息
        PageInfo<ZxEqRentOutEqRecord> p = new PageInfo<>(zxEqRentOutEqRecordList);

        return repEntity.okList(zxEqRentOutEqRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqRentOutEqRecordDetails(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        if (zxEqRentOutEqRecord == null) {
            zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
        }
        // 获取数据
        ZxEqRentOutEqRecord dbZxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
        // 数据存在
        if (dbZxEqRentOutEqRecord != null) {
            return repEntity.ok(dbZxEqRentOutEqRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqRentOutEqRecord.setId(UuidUtil.generate());
        zxEqRentOutEqRecord.setCreateUserInfo(userKey, realName);
        int flag = zxEqRentOutEqRecordMapper.insert(zxEqRentOutEqRecord);
        if(zxEqRentOutEqRecord.getItemList() != null && zxEqRentOutEqRecord.getItemList().size() >0) {	
        	for (ZxEqRentOutEqRecordItem lib : zxEqRentOutEqRecord.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setBillID(zxEqRentOutEqRecord.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqRentOutEqRecordItemMapper.insert(lib);
			}
        }
        if(zxEqRentOutEqRecord.getFileList() != null && zxEqRentOutEqRecord.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqRentOutEqRecord.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqRentOutEqRecord.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqRentOutEqRecord);
        }
    }

    @Override
    public ResponseEntity updateZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqRentOutEqRecord dbzxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
        if (dbzxEqRentOutEqRecord != null && StrUtil.isNotEmpty(dbzxEqRentOutEqRecord.getId())) {
           // 单据编号
           dbzxEqRentOutEqRecord.setBillNo(zxEqRentOutEqRecord.getBillNo());
           // 使用单位id
           dbzxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());
           // 使用单位name
           dbzxEqRentOutEqRecord.setOrgName(zxEqRentOutEqRecord.getOrgName());
           // 出租单位id
           dbzxEqRentOutEqRecord.setRentOrgID(zxEqRentOutEqRecord.getRentOrgID());
           // 出租单位name
           dbzxEqRentOutEqRecord.setRentOrgName(zxEqRentOutEqRecord.getRentOrgName());
           // 生产厂家
           dbzxEqRentOutEqRecord.setEqName(zxEqRentOutEqRecord.getEqName());
           // 技术规格
           dbzxEqRentOutEqRecord.setSpec(zxEqRentOutEqRecord.getSpec());
           // 填写日期
           dbzxEqRentOutEqRecord.setBusDate(zxEqRentOutEqRecord.getBusDate());
           // 备注
           dbzxEqRentOutEqRecord.setRemark(zxEqRentOutEqRecord.getRemark());
           // 
           dbzxEqRentOutEqRecord.setOuterEquipID(zxEqRentOutEqRecord.getOuterEquipID());
           // 状态
           dbzxEqRentOutEqRecord.setStatus(zxEqRentOutEqRecord.getStatus());
           // 共通
           dbzxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordMapper.updateByPrimaryKey(dbzxEqRentOutEqRecord);
         //先删除再新增
           ZxEqRentOutEqRecordItem delLib = new ZxEqRentOutEqRecordItem();
           delLib.setBillID(zxEqRentOutEqRecord.getId());
           List<ZxEqRentOutEqRecordItem> delLibList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(delLibList, delLib);
           }
           if(zxEqRentOutEqRecord.getItemList() != null && zxEqRentOutEqRecord.getItemList().size() >0) {	
        	   for (ZxEqRentOutEqRecordItem lib : zxEqRentOutEqRecord.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setBillID(zxEqRentOutEqRecord.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqRentOutEqRecordItemMapper.insert(lib);
        	   }
           }
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqRentOutEqRecord.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqRentOutEqRecord.getFileList() != null && zxEqRentOutEqRecord.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqRentOutEqRecord.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqRentOutEqRecord.getId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqRentOutEqRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecord(List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqRentOutEqRecordList != null && zxEqRentOutEqRecordList.size() > 0) {
        	for (ZxEqRentOutEqRecord zxEqRentOutEqRecord : zxEqRentOutEqRecordList) {
    			ZxEqRentOutEqRecordItem delLib = new ZxEqRentOutEqRecordItem();
    			delLib.setBillID(zxEqRentOutEqRecord.getId());
    			List<ZxEqRentOutEqRecordItem> delLibList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqRentOutEqRecord.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqRentOutEqRecord zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
           zxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordMapper.batchDeleteUpdateZxEqRentOutEqRecord(zxEqRentOutEqRecordList, zxEqRentOutEqRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqRentOutEqRecordList);
        }
    }

    @Override
    public ResponseEntity auditZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqRentOutEqRecord dbzxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
    	if (dbzxEqRentOutEqRecord != null && StrUtil.isNotEmpty(dbzxEqRentOutEqRecord.getId())) {
    		// 状态
    		dbzxEqRentOutEqRecord.setStatus(zxEqRentOutEqRecord.getStatus());
    		// 共通
    		dbzxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqRentOutEqRecordMapper.updateByPrimaryKey(dbzxEqRentOutEqRecord);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqRentOutEqRecord);
    	}
    }

	@Override
	public ResponseEntity importZxEqRentOutEqRecordList(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		//单位编号,使用编号,出租单位,机械名称,技术规格,填写日期,备注
		zxEqRentOutEqRecord.setId(UuidUtil.generate());
		zxEqRentOutEqRecord.setBillNo(zxEqRentOutEqRecord.getBillNo());// 单据编号
		zxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());// 使用单位id
		zxEqRentOutEqRecord.setOrgName(zxEqRentOutEqRecord.getOrgName());// 使用单位name
		zxEqRentOutEqRecord.setRentOrgID(zxEqRentOutEqRecord.getRentOrgID());// 出租单位id
		zxEqRentOutEqRecord.setRentOrgName(zxEqRentOutEqRecord.getRentOrgName());// 出租单位name
		zxEqRentOutEqRecord.setEqName(zxEqRentOutEqRecord.getEqName());// 生产厂家
		zxEqRentOutEqRecord.setSpec(zxEqRentOutEqRecord.getSpec());// 技术规格
		zxEqRentOutEqRecord.setBusDate(zxEqRentOutEqRecord.getBusDate());// 填写日期
		zxEqRentOutEqRecord.setRemark(zxEqRentOutEqRecord.getRemark());// 备注
		zxEqRentOutEqRecord.setCreateUserInfo(userKey, realName);
		flag = zxEqRentOutEqRecordMapper.insert(zxEqRentOutEqRecord);
		//新增子明细
		String filePath = Apih5Properties.getFilePath() + zxEqRentOutEqRecord.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//重复导入删除上次的导入记录
			if(readAll != null && readAll.size() >0) {
				ZxEqRentOutEqRecordItem record = new ZxEqRentOutEqRecordItem();
				record.setBillID(zxEqRentOutEqRecord.getId());
				List<ZxEqRentOutEqRecordItem> debtExcels = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(record);
				if(debtExcels != null && debtExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(debtExcels, record);
				}
			}
			for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		//add
	    		ZxEqRentOutEqRecordItem base = new ZxEqRentOutEqRecordItem();
	    		base.setId(UuidUtil.generate());
	    		base.setBillID(zxEqRentOutEqRecord.getId());
	    		if(StrUtil.isNotEmpty(json.getStr("日期"))){
	    			base.setUseDate(DateUtil.parse(json.getStr("日期")));
				}else{
					base.setUseDate(null);
				}
	    		base.setContent(json.getStr("工作内容"));
	    		base.setPlace(json.getStr("使用地点"));
	    		base.setUnit(json.getStr("完成工程量单位"));
	    		if(StrUtil.isNotEmpty(json.getStr("完成工程量数量"))) {
	    			base.setQty(new BigDecimal(json.getStr("完成工程量数量")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("里程数"))) {
	    			base.setMile(new BigDecimal(json.getStr("里程数")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("运转台时"))) {
	    			base.setActualQty(new BigDecimal(json.getStr("运转台时")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("待工停滞台时"))) {
	    			base.setWaitQty(new BigDecimal(json.getStr("待工停滞台时")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("气候停滞台时"))) {
	    			base.setWeatherQty(new BigDecimal(json.getStr("气候停滞台时")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("故障停滞台时"))) {
	    			base.setProblemQty(new BigDecimal(json.getStr("故障停滞台时")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("汽油消耗(升)"))) {
	    			base.setGasQty(new BigDecimal(json.getStr("汽油消耗(升)")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("柴油消耗(升)"))) {
	    			base.setDervQty(new BigDecimal(json.getStr("柴油消耗(升)")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("煤消耗(千克)"))) {
	    			base.setCoalQty(new BigDecimal(json.getStr("煤消耗(千克)")));
	    		}
	    		base.setDriverName(json.getStr("司机名称"));
	    		base.setCreateUserInfo(userKey, realName);
	    		flag = zxEqRentOutEqRecordItemMapper.insert(base);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		if (flag == 0) {
			return repEntity.errorSave();
		}
		return repEntity.ok("ok");
	}
}
