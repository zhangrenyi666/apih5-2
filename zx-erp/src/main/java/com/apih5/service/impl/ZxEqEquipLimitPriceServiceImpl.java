package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.mybatis.dao.ZxEqEquipLimitPriceItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipLimitPriceMapper;
import com.apih5.mybatis.dao.ZxEqGlobalCodeMapper;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;
import com.apih5.mybatis.pojo.ZxEqGlobalCode;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPrice;
import com.apih5.service.ZxEqEquipLimitPriceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxEqEquipLimitPriceService")
public class ZxEqEquipLimitPriceServiceImpl implements ZxEqEquipLimitPriceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipLimitPriceMapper zxEqEquipLimitPriceMapper;
    
    @Autowired(required = true)
    private ZxEqEquipLimitPriceItemMapper zxEqEquipLimitPriceItemMapper;

    @Autowired(required = true)
    private ZxEqGlobalCodeMapper zxEqGlobalCodeMapper;
    
    @Autowired(required = true)
	private SequenceService sequenceService;
    
    @Override
    public ResponseEntity getZxEqEquipLimitPriceListByCondition(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        if (zxEqEquipLimitPrice == null) {
            zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
        }
        
        if(StrUtil.equals(zxEqEquipLimitPrice.getUreportFlag(), "all")) {
        	
        }else {
        	  HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
              String userId = TokenUtils.getUserId(request);
              String ext1 = TokenUtils.getExt1(request);
              // 集团全部可见
              if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                      || StrUtil.equals("admin", userId)) {
              	zxEqEquipLimitPrice.setComID("");
              	zxEqEquipLimitPrice.setOrgID("");
              } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                  // 公司只看见自己的
              	zxEqEquipLimitPrice.setComID(zxEqEquipLimitPrice.getOrgID());
              	zxEqEquipLimitPrice.setOrgID("");
              } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                      || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                  // 项目通过右上角数据
              	zxEqEquipLimitPrice.setOrgID(zxEqEquipLimitPrice.getOrgID());//使用单位
              }
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipLimitPrice.getPage(),zxEqEquipLimitPrice.getLimit());
        // 获取数据
        List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList = zxEqEquipLimitPriceMapper.selectByZxEqEquipLimitPriceList(zxEqEquipLimitPrice);
        for (ZxEqEquipLimitPrice zxEqEquipLimitPrice2 : zxEqEquipLimitPriceList) {
        	ZxEqEquipLimitPriceItem item = new ZxEqEquipLimitPriceItem();
        	item.setMainID(zxEqEquipLimitPrice2.getId());
        	List<ZxEqEquipLimitPriceItem> itemList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(item);
        	zxEqEquipLimitPrice2.setItemList(itemList);
        }
        // 得到分页信息
        PageInfo<ZxEqEquipLimitPrice> p = new PageInfo<>(zxEqEquipLimitPriceList);

        return repEntity.okList(zxEqEquipLimitPriceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipLimitPriceDetails(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
    	if (zxEqEquipLimitPrice == null) {
    		zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
    	}
    	ZxEqEquipLimitPrice dbZxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
    	if(StrUtil.isNotEmpty(zxEqEquipLimitPrice.getWorkId())) {
    		ZxEqEquipLimitPrice contrCsjz = new ZxEqEquipLimitPrice();
    		contrCsjz.setWorkId(zxEqEquipLimitPrice.getWorkId());
    		List<ZxEqEquipLimitPrice> csList = zxEqEquipLimitPriceMapper.selectByZxEqEquipLimitPriceList(contrCsjz);
    		if(csList != null && csList.size() >0) {
    			dbZxEqEquipLimitPrice = csList.get(0);
    		}
    	}else if(StrUtil.isNotEmpty(zxEqEquipLimitPrice.getId())) {
    		// 获取数据
    		dbZxEqEquipLimitPrice = zxEqEquipLimitPriceMapper.selectByPrimaryKey(zxEqEquipLimitPrice.getId());
    	}
    	if (dbZxEqEquipLimitPrice != null && StrUtil.isNotEmpty(dbZxEqEquipLimitPrice.getId())) {
    		ZxEqEquipLimitPriceItem item = new ZxEqEquipLimitPriceItem();
    		item.setMainID(dbZxEqEquipLimitPrice.getId());
    		List<ZxEqEquipLimitPriceItem> itemList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(item);
    		dbZxEqEquipLimitPrice.setItemList(itemList);
    	}
        // 数据存在
        if (dbZxEqEquipLimitPrice != null) {
            return repEntity.ok(dbZxEqEquipLimitPrice);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zxEqEquipLimitPrice.getPeriodDate() != null) {
        	zxEqEquipLimitPrice.setPeriod(DateUtil.format(zxEqEquipLimitPrice.getPeriodDate(), "yyyy-MM"));
        }
        //校验
        ZxEqEquipLimitPrice price = new ZxEqEquipLimitPrice();
        price.setOrgID(zxEqEquipLimitPrice.getOrgID());
        price.setPeriod(zxEqEquipLimitPrice.getPeriod());
        List<ZxEqEquipLimitPrice> priceList = zxEqEquipLimitPriceMapper.getZxEqEquipLimitPriceListLess(price);
        if(priceList != null && priceList.size() >0) {
        	 return repEntity.layerMessage("no", "该期次已存在或有已有期次之前的期次");
        }
        
        
        zxEqEquipLimitPrice.setId(UuidUtil.generate());
        zxEqEquipLimitPrice.setApih5FlowStatus("-1");
        zxEqEquipLimitPrice.setCreateUserInfo(userKey, realName);
        
      
        
        int flag = zxEqEquipLimitPriceMapper.insert(zxEqEquipLimitPrice);
        if(zxEqEquipLimitPrice.getItemList() != null && zxEqEquipLimitPrice.getItemList().size() >0) {	
        	for (ZxEqEquipLimitPriceItem lib : zxEqEquipLimitPrice.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMainID(zxEqEquipLimitPrice.getId());
        		lib.setApih5FlowStatus(zxEqEquipLimitPrice.getApih5FlowStatus());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqEquipLimitPriceItemMapper.insert(lib);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipLimitPrice);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipLimitPrice dbzxEqEquipLimitPrice = zxEqEquipLimitPriceMapper.selectByPrimaryKey(zxEqEquipLimitPrice.getId());
        if (dbzxEqEquipLimitPrice != null && StrUtil.isNotEmpty(dbzxEqEquipLimitPrice.getId())) {
        	  // 数据期次
            dbzxEqEquipLimitPrice.setPeriodDate(zxEqEquipLimitPrice. getPeriodDate());
            if(zxEqEquipLimitPrice.getPeriodDate() != null) {
            	dbzxEqEquipLimitPrice.setPeriod(DateUtil.format(zxEqEquipLimitPrice.getPeriodDate(), "yyyy-MM"));
            }
        	
        	//校验
            ZxEqEquipLimitPrice price = new ZxEqEquipLimitPrice();
            price.setIdFlag(dbzxEqEquipLimitPrice.getId());
            price.setOrgID(dbzxEqEquipLimitPrice.getOrgID());
            price.setPeriod(dbzxEqEquipLimitPrice.getPeriod());
            List<ZxEqEquipLimitPrice> priceList = zxEqEquipLimitPriceMapper.getZxEqEquipLimitPriceListLess(price);
            if(priceList != null && priceList.size() >0) {
            	 return repEntity.layerMessage("no", "该期次已存在或有已有期次之前的期次");
            }
        	
        	
        	
        	
        	//评审编号
        	dbzxEqEquipLimitPrice.setApplyNo(zxEqEquipLimitPrice.getApplyNo());
            // 填报日期
            dbzxEqEquipLimitPrice.setReportDate(zxEqEquipLimitPrice.getReportDate());
            // 备注
            dbzxEqEquipLimitPrice.setRemark(zxEqEquipLimitPrice.getRemark());
            // 流程id
            dbzxEqEquipLimitPrice.setWorkId(zxEqEquipLimitPrice.getWorkId());
            // 流程状态
            dbzxEqEquipLimitPrice.setApih5FlowStatus(zxEqEquipLimitPrice.getApih5FlowStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField1(zxEqEquipLimitPrice.getOpinionContent(realName,
				        dbzxEqEquipLimitPrice.getOpinionField1()));
			}
			//
			if(StrUtil.equals("opinionField2", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField2(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField2()));
			}
			//
			if(StrUtil.equals("opinionField3", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField3(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField3()));
			}
			//
			if(StrUtil.equals("opinionField4", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField4(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField4()));
			}
			//
			if(StrUtil.equals("opinionField5", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField5(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField5()));
			}
			//
			if(StrUtil.equals("opinionField6", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField6(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField6()));
			}
			//
			if(StrUtil.equals("opinionField7", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField7(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField7()));
			}
			//
			if(StrUtil.equals("opinionField8", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField8(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField8()));
			}
			//
			if(StrUtil.equals("opinionField9", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField9(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField9()));
			}
			//
			if(StrUtil.equals("opinionField10", zxEqEquipLimitPrice.getOpinionField(), true)){
				dbzxEqEquipLimitPrice.setOpinionField10(dbzxEqEquipLimitPrice.getOpinionContent(realName, dbzxEqEquipLimitPrice.getOpinionField10()));
			}
           // 共通
           dbzxEqEquipLimitPrice.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipLimitPriceMapper.updateByPrimaryKey(dbzxEqEquipLimitPrice);
           //先删除再新增
           ZxEqEquipLimitPriceItem delLib = new ZxEqEquipLimitPriceItem();
           delLib.setMainID(zxEqEquipLimitPrice.getId());
           List<ZxEqEquipLimitPriceItem> delLibList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqEquipLimitPriceItemMapper.batchDeleteUpdateZxEqEquipLimitPriceItem(delLibList, delLib);
           }
           if(zxEqEquipLimitPrice.getItemList() != null && zxEqEquipLimitPrice.getItemList().size() >0) {	
        	   for (ZxEqEquipLimitPriceItem lib : zxEqEquipLimitPrice.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMainID(zxEqEquipLimitPrice.getId());
        		   lib.setApih5FlowStatus(dbzxEqEquipLimitPrice.getApih5FlowStatus());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEquipLimitPriceItemMapper.insert(lib);
        	   }
           }
           //
           
           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipLimitPrice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPrice(List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxEqEquipLimitPriceList != null && zxEqEquipLimitPriceList.size() > 0) {
        	//删除
    		JSONArray jsonArray = new JSONArray();
        	for (ZxEqEquipLimitPrice zxEqEquipLimitPrice : zxEqEquipLimitPriceList) {
        		jsonArray.add(zxEqEquipLimitPrice.getWorkId());
        		ZxEqEquipLimitPriceItem delLib = new ZxEqEquipLimitPriceItem();
    			delLib.setMainID(zxEqEquipLimitPrice.getId());
    			List<ZxEqEquipLimitPriceItem> delLibList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEquipLimitPriceItemMapper.batchDeleteUpdateZxEqEquipLimitPriceItem(delLibList, delLib);
    			}
    		}
        	String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
        	String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
        	ZxEqEquipLimitPrice zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
        	zxEqEquipLimitPrice.setModifyUserInfo(userKey, realName);
        	flag = zxEqEquipLimitPriceMapper.batchDeleteUpdateZxEqEquipLimitPrice(zxEqEquipLimitPriceList, zxEqEquipLimitPrice);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipLimitPriceList);
        }
    }

    @Override
    public ResponseEntity copyZxEqEquipLimitPrice(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipLimitPrice dbzxEqEquipLimitPrice = zxEqEquipLimitPriceMapper.selectByPrimaryKey(zxEqEquipLimitPrice.getId());
    	if (dbzxEqEquipLimitPrice != null && StrUtil.isNotEmpty(dbzxEqEquipLimitPrice.getId())) {
    		//id
    		dbzxEqEquipLimitPrice.setId(UuidUtil.generate());
    		// 评审编号
    		dbzxEqEquipLimitPrice.setApplyNo(dbzxEqEquipLimitPrice.getApplyNo()+"复制");
    		//workId
    		dbzxEqEquipLimitPrice.setWorkId("");
    		//审核状态
    		dbzxEqEquipLimitPrice.setApih5FlowStatus("-1");
    		dbzxEqEquipLimitPrice.setOpinionField1("");
    		dbzxEqEquipLimitPrice.setOpinionField2("");
    		dbzxEqEquipLimitPrice.setOpinionField3("");
    		// 共通
    		dbzxEqEquipLimitPrice.setCreateUserInfo(userKey, realName);
    		flag = zxEqEquipLimitPriceMapper.insert(dbzxEqEquipLimitPrice);
    		//add明细
    		ZxEqEquipLimitPriceItem copyItem = new ZxEqEquipLimitPriceItem();
    		copyItem.setMainID(zxEqEquipLimitPrice.getId());
    		List<ZxEqEquipLimitPriceItem> copyItemList = zxEqEquipLimitPriceItemMapper.selectByZxEqEquipLimitPriceItemList(copyItem);
    		if(copyItemList != null && copyItemList.size() >0) {
    			for (ZxEqEquipLimitPriceItem lib : copyItemList) {
    				lib.setId(UuidUtil.generate());
    				lib.setMainID(dbzxEqEquipLimitPrice.getId());
    				lib.setCreateUserInfo(userKey, realName);
    				flag = zxEqEquipLimitPriceItemMapper.insert(lib);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipLimitPrice);
    	}
    }

	@Override
	public ResponseEntity getZxEqEquipLimitPriceApplyNo(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
		if (zxEqEquipLimitPrice == null) {
            zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
        }
        //生成评审编号
        String applyNo = "";
        ZxEqGlobalCode globalCode = new ZxEqGlobalCode();
        globalCode.setGlobalDesc(zxEqEquipLimitPrice.getOrgName());
        List<ZxEqGlobalCode> globalCodeList = zxEqGlobalCodeMapper.selectByZxEqGlobalCodeList(globalCode);
        if(globalCodeList != null && globalCodeList.size() >0) {
        	applyNo = globalCodeList.get(0).getGlobalCode();
        }
        
        ZxEqGlobalCode code = zxEqGlobalCodeMapper.selectByPrimaryKey(zxEqEquipLimitPrice.getPeriod());
        if(code != null && StrUtil.isNotEmpty(code.getId())) {
        	applyNo = applyNo+"-"+code.getGlobalCode();
        }
        // 评审编号
        int ysdh = sequenceService.getSequenceNextval("zx_eq_applyNo");
        String auto = String.format("%03d", ysdh);
        applyNo = applyNo+"-"+auto;
        return repEntity.ok(applyNo);
	}

	@Override
	public List<ZxEqEquipLimitPrice> ureportZxEqEquipLimitPriceVO(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
		if (zxEqEquipLimitPrice == null) {
            zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipLimitPrice.getPage(),zxEqEquipLimitPrice.getLimit());
        // 获取数据
        List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList = zxEqEquipLimitPriceMapper.selectZxEqEquipLimitPriceVO(zxEqEquipLimitPrice);
      
		return zxEqEquipLimitPriceList;
	}
	@Override
	public ResponseEntity ureportZxEqEquipLimitPriceVOIdle(ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
		if (zxEqEquipLimitPrice == null) {
			zxEqEquipLimitPrice = new ZxEqEquipLimitPrice();
		}
		// 分页查询
		PageHelper.startPage(zxEqEquipLimitPrice.getPage(),zxEqEquipLimitPrice.getLimit());
		// 获取数据
		List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList = zxEqEquipLimitPriceMapper.selectZxEqEquipLimitPriceVO(zxEqEquipLimitPrice);
		// 得到分页信息
		PageInfo<ZxEqEquipLimitPrice> p = new PageInfo<>(zxEqEquipLimitPriceList);

		return repEntity.okList(zxEqEquipLimitPriceList, p.getTotal());

	}
    
}
