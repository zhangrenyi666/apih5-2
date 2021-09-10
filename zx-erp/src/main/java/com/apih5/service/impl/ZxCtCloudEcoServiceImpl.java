package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.utils.CloudEcoUtils;
import com.google.common.collect.Maps;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrCustomerInfoMapper;
import com.apih5.mybatis.dao.ZxCtCloudEcoMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;
import com.apih5.mybatis.pojo.ZxCtCloudEco;
import com.apih5.service.ZxCtCloudEcoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxCtCloudEcoService")
public class ZxCtCloudEcoServiceImpl implements ZxCtCloudEcoService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCtCloudEcoMapper zxCtCloudEcoMapper;
	@Autowired(required = true)
	private ZxCrCustomerInfoMapper zxCrCustomerInfoMapper;

	@Override
	public ResponseEntity getZxCtCloudEcoListByCondition(ZxCtCloudEco zxCtCloudEco) {
		if (zxCtCloudEco == null) {
			zxCtCloudEco = new ZxCtCloudEco();
		}
		// 分页查询
		PageHelper.startPage(zxCtCloudEco.getPage(), zxCtCloudEco.getLimit());
		// 获取数据
		List<ZxCtCloudEco> zxCtCloudEcoList = zxCtCloudEcoMapper.selectByZxCtCloudEcoList(zxCtCloudEco);
		// 得到分页信息
		PageInfo<ZxCtCloudEco> p = new PageInfo<>(zxCtCloudEcoList);

		return repEntity.okList(zxCtCloudEcoList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtCloudEcoDetails(ZxCtCloudEco zxCtCloudEco) {
		if (zxCtCloudEco == null) {
			zxCtCloudEco = new ZxCtCloudEco();
		}
		// 获取数据
		ZxCtCloudEco dbZxCtCloudEco = zxCtCloudEcoMapper.selectByPrimaryKey(zxCtCloudEco.getId());
		// 数据存在
		if (dbZxCtCloudEco != null) {
			return repEntity.ok(dbZxCtCloudEco);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtCloudEco(ZxCtCloudEco zxCtCloudEco) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtCloudEco.setId(UuidUtil.generate());
		zxCtCloudEco.setCreateUserInfo(userKey, realName);
		int flag = zxCtCloudEcoMapper.insert(zxCtCloudEco);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtCloudEco);
		}
	}

	@Override
	public ResponseEntity updateZxCtCloudEco(ZxCtCloudEco zxCtCloudEco) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtCloudEco dbzxCtCloudEco = zxCtCloudEcoMapper.selectByPrimaryKey(zxCtCloudEco.getId());
		if (dbzxCtCloudEco != null && StrUtil.isNotEmpty(dbzxCtCloudEco.getId())) {
			// 序号
			dbzxCtCloudEco.setSerialNumber(zxCtCloudEco.getSerialNumber());
			// 方案编号
			dbzxCtCloudEco.setSchemeNo(zxCtCloudEco.getSchemeNo());
			// 方案名称
			dbzxCtCloudEco.setSchemeName(zxCtCloudEco.getSchemeName());
			// 执行完成时间
			dbzxCtCloudEco.setDoFinishTime(zxCtCloudEco.getDoFinishTime());
			// 方案状态
			dbzxCtCloudEco.setSchemeStatus(zxCtCloudEco.getSchemeStatus());
			// 三级单位
			dbzxCtCloudEco.setThirdUnit(zxCtCloudEco.getThirdUnit());
			// 包件编号
			dbzxCtCloudEco.setPackageNo(zxCtCloudEco.getPackageNo());
			// 包件名称
			dbzxCtCloudEco.setPackageName(zxCtCloudEco.getPackageName());
			// 定标金额
			dbzxCtCloudEco.setScaledAmount(zxCtCloudEco.getScaledAmount());
			// 投标单位
			dbzxCtCloudEco.setWinningUnit(zxCtCloudEco.getWinningUnit());
			// 包件需求单位
			dbzxCtCloudEco.setDemandUnit(zxCtCloudEco.getDemandUnit());
			// 社会统一信用代码证号
			dbzxCtCloudEco.setOrgCertificate(zxCtCloudEco.getOrgCertificate());
			// 备注
			dbzxCtCloudEco.setRemark(zxCtCloudEco.getRemark());
			// 编辑时间
			dbzxCtCloudEco.setEditTime(zxCtCloudEco.getEditTime());
			// 中标情况
			dbzxCtCloudEco.setWinRemark(zxCtCloudEco.getWinRemark());
			// 工程类别
			dbzxCtCloudEco.setProjectType(zxCtCloudEco.getProjectType());
			// 共通
			dbzxCtCloudEco.setModifyUserInfo(userKey, realName);
			flag = zxCtCloudEcoMapper.updateByPrimaryKey(dbzxCtCloudEco);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCtCloudEco);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtCloudEco(List<ZxCtCloudEco> zxCtCloudEcoList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtCloudEcoList != null && zxCtCloudEcoList.size() > 0) {
			ZxCtCloudEco zxCtCloudEco = new ZxCtCloudEco();
			zxCtCloudEco.setModifyUserInfo(userKey, realName);
			flag = zxCtCloudEcoMapper.batchDeleteUpdateZxCtCloudEco(zxCtCloudEcoList, zxCtCloudEco);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCtCloudEcoList);
		}
	}

	@Override
	public ResponseEntity batchInputZxCtCloudEco(ZxCtCloudEco zxCtCloudEco) {
		if (zxCtCloudEco == null) {
			zxCtCloudEco = new ZxCtCloudEco();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String filePath = zxCtCloudEco.getImportFileList().get(0).getUrl();
		int flag = 0;
		ExcelReader reader = ExcelUtil.getReader(filePath);
		List<Map<String, Object>> readAll = reader.readAll();
		for (Map<String, Object> map : readAll) {
			JSONObject json = new JSONObject(map);
			ZxCtCloudEco cloudEco = new ZxCtCloudEco();
			cloudEco.setId(UuidUtil.generate());
			// 序号
			cloudEco.setSerialNumber(json.getStr("序号"));
			// 方案编号
			cloudEco.setSchemeNo(json.getStr("方案编号"));
			// 方案名称
			cloudEco.setSchemeName(json.getStr("方案名称"));
			// 执行完成时间
			if (StrUtil.isNotEmpty(json.getStr("执行完成时间"))) {
				cloudEco.setDoFinishTime(DateUtil.parse(json.getStr("执行完成时间")));
			} else {
				cloudEco.setDoFinishTime(null);
			}
			// 方案状态
			cloudEco.setSchemeStatus(json.getStr("方案状态"));
			// 三级单位
			cloudEco.setThirdUnit(json.getStr("三级单位"));
			// 包件编号
			cloudEco.setPackageNo(json.getStr("包件编号"));
			// 包件名称
			cloudEco.setPackageName(json.getStr("包件名称"));
			// 定标金额
			if (StrUtil.isNotEmpty(json.getStr("定标金额"))) {
				cloudEco.setScaledAmount(new BigDecimal(json.getStr("定标金额")));
			} else {
				cloudEco.setScaledAmount(new BigDecimal("0"));
			}
			// 投标单位
			cloudEco.setWinningUnit(json.getStr("投标单位"));
			// 包件需求单位
			cloudEco.setDemandUnit(json.getStr("包件需求单位"));
			// 社会统一信用代码证号
			cloudEco.setOrgCertificate(json.getStr("社会统一信用代码证号"));
			// 备注
			cloudEco.setRemark(json.getStr("备注"));
			// 中标情况
			cloudEco.setWinRemark(json.getStr("中标情况"));
			// 工程类别
			cloudEco.setProjectType(json.getStr("工程类别"));
			cloudEco.setCreateUserInfo(userKey, realName);
			flag = zxCtCloudEcoMapper.insert(cloudEco);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtCloudEco);
		}
	}

	@Override
	public ResponseEntity gcsgGetZxCtCloudEcoSelect(ZxCtCloudEco zxCtCloudEco) {
		if (zxCtCloudEco == null) {
			zxCtCloudEco = new ZxCtCloudEco();
		}
		// 子查询改为代码实现
		List<ZxCrCustomerInfo> customerList = zxCrCustomerInfoMapper
				.selectByZxCrCustomerInfoList(new ZxCrCustomerInfo());
		Map<String, Object> certificateHash = new HashMap<String, Object>();
		Map<String, Object> customerHash = new HashMap<String, Object>();
		if (customerList.size() > 0) {
			customerList.stream().forEach(customer -> {
				certificateHash.put(customer.getOrgCertificate(), 1);
				customerHash.put(customer.getCustomerName(), 1);
			});
		}
		PageHelper.startPage(zxCtCloudEco.getPage(), zxCtCloudEco.getLimit());
		List<ZxCtCloudEco> zxCtCloudEcoList = zxCtCloudEcoMapper.selectByZxCtCloudEcoList(zxCtCloudEco);
		if (zxCtCloudEcoList.size() > 0) {
			zxCtCloudEcoList.stream().forEach(eco -> {
				if (certificateHash.containsKey(eco.getOrgCertificate())
						|| customerHash.containsKey(eco.getWinningUnit()))
					eco.setIsRela("1");
				else
					eco.setIsRela("0");
			});
		}
		PageInfo<ZxCtCloudEco> p = new PageInfo<>(zxCtCloudEcoList);
		return repEntity.okList(zxCtCloudEcoList, p.getTotal());
	}

	public ResponseEntity synZxCtCloudEco(){
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String url = "https://open.iccec.cn/oauth/token?grant_type=client_credentials";
		Map map = new HashMap<>();
		map.put("client_id", "102345@102067");
		map.put("client_secret", "c4da874f22454a44be");
		String result = HttpUtil.post(url,map);
		JSONObject resultJsonObject = new JSONObject(result);
		if(resultJsonObject.getStr("access_token")==null){
			logger.info("获取token失败");
			repEntity.layerMessage("no", "获取token失败");
		}
		List<ZxCtCloudEco> editList = new ArrayList();
		int total = 0 ;

		String schPursckurl = "https://open.iccec.cn/apis/open/esi/purch/qryPlanList";
		String token =resultJsonObject.getStr("access_token");
		Map headersMap = Maps.newHashMap();
		JSONObject paramMap = new JSONObject();

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		date = (Date) calendar.getTime();

		paramMap.set("exeBegintime", date);
		paramMap.set("exeEndtime", DateUtil.date());
		headersMap.put("Content-Type","application/json");
		headersMap.put("Authorization",resultJsonObject.getStr("access_token"));
		String schPursckJson =CloudEcoUtils.sendPost4Business(schPursckurl,paramMap.toString(),resultJsonObject.getStr("access_token"));
		JSONObject schPursckJsonObject = new JSONObject(schPursckJson);
		JSONArray jsonArray =schPursckJsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("schPursckList");

		if(jsonArray != null && jsonArray.size()>0) {
			String dockInfoBOSurl = "https://open.iccec.cn/apis/open/esi/purch/qryPlanDetail";
			//获取实际产值
			for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {

				JSONObject jsonObjectItme = (JSONObject)iterator.next();

				if(jsonObjectItme.getStr("purSckInfoId")!=null){
					JSONObject paramMap2 = new JSONObject();
					paramMap2.set("purSckInfoId",jsonObjectItme.getStr("purSckInfoId"));
					String dockInfoBOSJson =CloudEcoUtils.sendPost4Business(dockInfoBOSurl,paramMap2.toString(),resultJsonObject.getStr("access_token"));
					 //cn.hutool.http.HttpUtil.createGet(schPursckurl).addHeaders(headersMap).execute().body();
					JSONObject dockInfoBOSJsonObject = new JSONObject(dockInfoBOSJson);
					JSONObject schPursckDockInfoBO =dockInfoBOSJsonObject.getJSONObject("data").getJSONObject("data").getJSONObject("schPursckDockInfoBO");
					JSONArray schPackDockInfoBOS =dockInfoBOSJsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("schPackDockInfoBOS");
					JSONArray schPackidetlDockInfoBOS =dockInfoBOSJsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("schPackidetlDockInfoBOS");
					JSONArray caliInfoDockBOS =dockInfoBOSJsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("caliInfoDockBOS");
					JSONArray supJoinDockBOS =dockInfoBOSJsonObject.getJSONObject("data").getJSONObject("data").getJSONArray("supJoinDockBOS");
					String schemeNo = "";
					String schemeName = "";
					String projectType = "";
					String remark = "";
					String schemeStatus = "";
					String orgnComyName = "";
					Date exeEndtime = new Date();
					if(schPursckDockInfoBO!=null){
						schemeNo = schPursckDockInfoBO.getStr("pursckInfoCode");
						schemeName = schPursckDockInfoBO.getStr("pursckInfoName");
						projectType = schPursckDockInfoBO.getStr("proTypeName");
						remark = schPursckDockInfoBO.getStr("memo");
						schemeStatus = schPursckDockInfoBO.getStr("stateName");
						orgnComyName = schPursckDockInfoBO.getStr("OrgnComyName");
						exeEndtime = schPursckDockInfoBO.getDate("exeEndtime");
					}
					Map<Object,JSONObject> packMap = new HashMap();
					Map<Object,JSONObject> caliMap = new HashMap();
					if (schPackDockInfoBOS != null) {
						for (Iterator<Object> iterator1 = schPackDockInfoBOS.iterator(); iterator1.hasNext();) {
							JSONObject schPackDockInfoBO = (JSONObject)iterator1.next();
							//JSONObject schPackDockInfoBO =  schPackDockInfoBOS.getJSONObject(j);
							if(schPackDockInfoBO!=null){
								packMap.put(schPackDockInfoBO.getStr("packInfoId"),
										schPackDockInfoBO);
							}
						}
					}
					if (caliInfoDockBOS != null) {
						for (Iterator<Object> iterator2 = caliInfoDockBOS.iterator(); iterator2.hasNext();) {
							JSONObject caliInfoDockBO =  (JSONObject)iterator2.next();
							if(caliInfoDockBO!=null){
								caliMap.put(caliInfoDockBO.getStr("packInfoId"),
										caliInfoDockBO);
							}
						}
					}
					if (supJoinDockBOS != null) {
					for (Iterator<Object> iterator3 = supJoinDockBOS.iterator(); iterator3.hasNext(); ) {// 循环投标单位
						JSONObject supJoinDockBO = (JSONObject)iterator3.next();
						if(supJoinDockBO==null){
							System.out.println("投标单位为空------");
							continue;
						}
						Long packInfoId = supJoinDockBO.getLong("packInfoId");
						String packageNo = packInfoId.toString();
						String packageName = "";
						String demandUnit = "";
						JSONObject schPackDockInfoBO = packMap.get(packInfoId);
						if(schPackDockInfoBO!=null){
							packageName = schPackDockInfoBO.getStr("packName");
							demandUnit = schPackDockInfoBO.getStr("requComyName");
						}
						ZxCtCloudEco zxCtCloudEco =new ZxCtCloudEco();
						//CloudEcoBO cloudEcoBO = new CloudEcoBO();
						zxCtCloudEco.setSchemeNo(schemeNo);
						zxCtCloudEco.setSchemeName(schemeName);
						zxCtCloudEco.setProjectType(projectType);

						zxCtCloudEco.setSchemeStatus(schemeStatus);
						zxCtCloudEco.setDoFinishTime(exeEndtime);
						zxCtCloudEco.setThirdUnit(orgnComyName);

						zxCtCloudEco.setRemark(remark);

						zxCtCloudEco.setPackageNo(schemeNo + "-"
								+ packageNo);
						zxCtCloudEco.setPackageName(packageName);
						zxCtCloudEco.setDemandUnit(demandUnit);

						zxCtCloudEco.setOrgCertificate(supJoinDockBO.getStr("supplierId"));
						total++;
						//投标单位
						zxCtCloudEco.setWinningUnit(supJoinDockBO.getStr("supplierName"));
						zxCtCloudEco.setScaledAmount(new BigDecimal("0"));
						zxCtCloudEco.setWinRemark("未中标");
						// 是否中标start
						if (caliMap.get(packInfoId)!=null) {
							JSONObject caliInfoDockBO = caliMap.get(packInfoId);
							double scaledAmount = 0.0;
							if(StrUtil.isNotEmpty(supJoinDockBO.getStr("supplierId"))
									&&supJoinDockBO.getStr("supplierId").equals(caliInfoDockBO.getStr("supplierId"))){
								if (caliInfoDockBO.getStr("bidPriceAmt") != null) {
									scaledAmount = caliInfoDockBO
											.getDouble("bidPriceAmt").doubleValue();
								}
								zxCtCloudEco.setScaledAmount(new BigDecimal("scaledAmount"));
								zxCtCloudEco.setWinRemark("已中标");
							}
						}
						editList.add(zxCtCloudEco);
						// 是否中标end
					}}

				}
			}

			for (int i = 0; i < editList.size(); i++) {

				ZxCtCloudEco zxCtCloudEco = new ZxCtCloudEco();
				zxCtCloudEco.setSchemeNo(editList.get(i).getSchemeNo());
				zxCtCloudEco.setPackageNo(editList.get(i).getPackageNo());
				zxCtCloudEco.setOrgCertificate(editList.get(i).getOrgCertificate());
				List<ZxCtCloudEco> dbZxCtCloudEco =zxCtCloudEcoMapper.selectByZxCtCloudEcoList(zxCtCloudEco);
				if(dbZxCtCloudEco.size()>0){
					batchDeleteUpdateZxCtCloudEco(dbZxCtCloudEco);
				}
				editList.get(i).setId(UuidUtil.generate());
				editList.get(i).setCreateUserInfo(userKey, realName);
				zxCtCloudEcoMapper.insert(editList.get(i));
			}
		}
		return repEntity.ok(editList);

	}
}
