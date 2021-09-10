package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehRebarSupermarketMapper;
import com.apih5.mybatis.pojo.ZjLzehRebarSupermarket;
import com.apih5.service.ZjLzehRebarSupermarketService;
import com.apih5.utils.ZjLzehUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjLzehRebarSupermarketService")
public class ZjLzehRebarSupermarketServiceImpl implements ZjLzehRebarSupermarketService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehRebarSupermarketMapper zjLzehRebarSupermarketMapper;

    @Override
    public ResponseEntity getZjLzehRebarSupermarketListByCondition(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        if (zjLzehRebarSupermarket == null) {
            zjLzehRebarSupermarket = new ZjLzehRebarSupermarket();
        }
        // 分页查询
        PageHelper.startPage(zjLzehRebarSupermarket.getPage(),zjLzehRebarSupermarket.getLimit());
        //写死ID
        zjLzehRebarSupermarket.setRebarSupermarketId("1EES23IEB00I02010002000004912983");
        // 获取数据
        List<ZjLzehRebarSupermarket> zjLzehRebarSupermarketList = zjLzehRebarSupermarketMapper.selectByZjLzehRebarSupermarketList(zjLzehRebarSupermarket);
        
        if (zjLzehRebarSupermarketList != null && zjLzehRebarSupermarketList.size() > 0) {
			for (ZjLzehRebarSupermarket dbzjLzehRebarSupermarket : zjLzehRebarSupermarketList) {
				//累计已收料量
				BigDecimal cumulativeHasBeenReceivingNumber = dbzjLzehRebarSupermarket.getCumulativeHasBeenReceivingNumber();
				//现场累计使用量
				BigDecimal cumulativeUsageNumber = dbzjLzehRebarSupermarket.getCumulativeUsageNumber();
				//累计废料总量
				BigDecimal cumulativeTotalWaste = dbzjLzehRebarSupermarket.getCumulativeTotalWaste();
				//剩余库存量
				BigDecimal number = CalcUtils.calcSubtract(cumulativeHasBeenReceivingNumber, cumulativeUsageNumber);
				BigDecimal remainingStockNumber = CalcUtils.calcSubtract(number, cumulativeTotalWaste);
				dbzjLzehRebarSupermarket.setRemainingStockNumber(remainingStockNumber) ;
			}
		}
        // 得到分页信息
        PageInfo<ZjLzehRebarSupermarket> p = new PageInfo<>(zjLzehRebarSupermarketList);

        return repEntity.okList(zjLzehRebarSupermarketList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehRebarSupermarketDetails(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        if (zjLzehRebarSupermarket == null) {
            zjLzehRebarSupermarket = new ZjLzehRebarSupermarket();
        }
        // 获取数据
        ZjLzehRebarSupermarket dbZjLzehRebarSupermarket = zjLzehRebarSupermarketMapper.selectByPrimaryKey(zjLzehRebarSupermarket.getRebarSupermarketId());
        // 数据存在
        if (dbZjLzehRebarSupermarket != null) {
            return repEntity.ok(dbZjLzehRebarSupermarket);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehRebarSupermarket(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehRebarSupermarket.setRebarSupermarketId(UuidUtil.generate());
        zjLzehRebarSupermarket.setCreateUserInfo(userKey, realName);
        int flag = zjLzehRebarSupermarketMapper.insert(zjLzehRebarSupermarket);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehRebarSupermarket);
        }
    }

    @Override
    public ResponseEntity updateZjLzehRebarSupermarket(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehRebarSupermarket dbzjLzehRebarSupermarket = zjLzehRebarSupermarketMapper.selectByPrimaryKey(zjLzehRebarSupermarket.getRebarSupermarketId());
        if (dbzjLzehRebarSupermarket != null && StrUtil.isNotEmpty(dbzjLzehRebarSupermarket.getRebarSupermarketId())) {
           // 钢筋总需用量
           dbzjLzehRebarSupermarket.setRebarRequirementNumber(zjLzehRebarSupermarket.getRebarRequirementNumber());
           // 现场累计使用量
           dbzjLzehRebarSupermarket.setCumulativeUsageNumber(zjLzehRebarSupermarket.getCumulativeUsageNumber());
           // 累计已收料量
           dbzjLzehRebarSupermarket.setCumulativeHasBeenReceivingNumber(zjLzehRebarSupermarket.getCumulativeHasBeenReceivingNumber());
           // 剩余库存量
           dbzjLzehRebarSupermarket.setRemainingStockNumber(zjLzehRebarSupermarket.getRemainingStockNumber());
           // 累计废料总量
           dbzjLzehRebarSupermarket.setCumulativeTotalWaste(zjLzehRebarSupermarket.getCumulativeTotalWaste());
           // 共通
           dbzjLzehRebarSupermarket.setModifyUserInfo(userKey, realName);
           flag = zjLzehRebarSupermarketMapper.updateByPrimaryKey(dbzjLzehRebarSupermarket);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehRebarSupermarket);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehRebarSupermarket(List<ZjLzehRebarSupermarket> zjLzehRebarSupermarketList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehRebarSupermarketList != null && zjLzehRebarSupermarketList.size() > 0) {
           ZjLzehRebarSupermarket zjLzehRebarSupermarket = new ZjLzehRebarSupermarket();
           zjLzehRebarSupermarket.setModifyUserInfo(userKey, realName);
           flag = zjLzehRebarSupermarketMapper.batchDeleteUpdateZjLzehRebarSupermarket(zjLzehRebarSupermarketList, zjLzehRebarSupermarket);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehRebarSupermarketList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
	@Override
	public ResponseEntity updateZjLzehRebarSupermarketByProjectId(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
		int flag = 0;
        ZjLzehRebarSupermarket dbzjLzehRebarSupermarket = zjLzehRebarSupermarketMapper.selectByPrimaryKey(ZjLzehUtil.PROJECT_ID);
        if (dbzjLzehRebarSupermarket != null && StrUtil.isNotEmpty(dbzjLzehRebarSupermarket.getRebarSupermarketId())) {

    		//  钢筋总需用量
    		BigDecimal rebarRequirementNumber = getTotalNumByApiName("mtMaterial/getTotalMaterialNum");
    		dbzjLzehRebarSupermarket.setRebarRequirementNumber(rebarRequirementNumber);
    		//	现场累计使用量
    		BigDecimal cumulativeUsageNumber = getTotalNumByApiName("mtRequirePlanItemLz2h/getItemSignNum");
    		dbzjLzehRebarSupermarket.setCumulativeUsageNumber(cumulativeUsageNumber);
    		//	累计已收料量
    		BigDecimal cumulativeHasBeenReceivingNumber = getTotalNumByApiName("mtReceiveLz2h/getReceiveLz2hNum");
    		dbzjLzehRebarSupermarket.setCumulativeHasBeenReceivingNumber(cumulativeHasBeenReceivingNumber);
    		//	剩余库存量
    		BigDecimal remainingStockNumber = getTotalNumByApiName("mtInventoryItemLz2h/getInventoryItemLz2hNum");
    		dbzjLzehRebarSupermarket.setRemainingStockNumber(remainingStockNumber);
    		//	累计废料总量
    		BigDecimal cumulativeTotalWaste = getTotalNumByApiName("mtDealLz2h/getDealLz2hNum");
    		dbzjLzehRebarSupermarket.setCumulativeTotalWaste(cumulativeTotalWaste);
        	
    		dbzjLzehRebarSupermarket.setModifyUserInfo("", "");
        	flag = zjLzehRebarSupermarketMapper.updateByPrimaryKey(dbzjLzehRebarSupermarket);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehRebarSupermarket);
        }
	}

    @Override
    public ResponseEntity getZjLzehPageDataForView(ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
        JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();
        String url = ZjLzehUtil.OTHER_URL + "a/mt/mtRequirePlanLz2h/getPageDataForView?projectId=" + ZjLzehUtil.PROJECT_ID;
        Map<String, String> headersMap = Maps.newHashMap();
        headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
        String result = HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
        JSONObject dataJSONObject = new JSONObject(result);
        JSONArray jsonArray = dataJSONObject.getJSONObject("rtnObj").getJSONArray("data");
        JSONArray returnJsonArray = new JSONArray();
        if(jsonArray != null && jsonArray.size()>0) {
            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObjectItme = (JSONObject)iterator.next();
                String[] fullNames =  jsonObjectItme.getStr("fullName").split("-");
                String fullName = fullNames[fullNames.length-3] + "-" + fullNames[fullNames.length-1];
                jsonObjectItme.set("positionName", fullName);
                if ("已签收".equals(jsonObjectItme.getStr("auditStatusName"))) {
                	jsonObjectItme.set("auditStatusName", "已配送");
				}
                returnJsonArray.add(jsonObjectItme);
            }
        }
        return repEntity.ok(jsonArray);
    }
	
	//----↓↓↓ 私有方法 ↓↓↓---------
    /**
     * 获取不同接口的totalNum
     * 
     * @param apiName 接口名称
     * @return totalNum具体数据
     */
    private BigDecimal getTotalNumByApiName(String apiName) {
    	ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
    	JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();
    	String url = ZjLzehUtil.OTHER_URL + "a/mt/" + apiName + "?projectId=" + ZjLzehUtil.PROJECT_ID;
	    Map<String, String> headersMap = Maps.newHashMap();
	    headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
	    String result = HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
	    JSONObject dataJSONObject = new JSONObject(result);
	    BigDecimal totalNum = dataJSONObject.getJSONObject("rtnObj").getBigDecimal("totalNum");
	    return totalNum;
	}

}
