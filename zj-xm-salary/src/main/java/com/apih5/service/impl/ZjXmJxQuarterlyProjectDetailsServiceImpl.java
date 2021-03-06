package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyProjectDetailsMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightDetailsMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessment;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyProjectDetails;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;
import com.apih5.service.ZjXmJxQuarterlyProjectDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxQuarterlyProjectDetailsService")
public class ZjXmJxQuarterlyProjectDetailsServiceImpl implements ZjXmJxQuarterlyProjectDetailsService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyProjectDetailsMapper zjXmJxQuarterlyProjectDetailsMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightDetailsMapper zjXmJxQuarterlyWeightDetailsMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentMapper zjXmJxQuarterlyAssessmentMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsListByCondition(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ????????????
		PageHelper.startPage(zjXmJxQuarterlyProjectDetails.getPage(), zjXmJxQuarterlyProjectDetails.getLimit());
		// ????????????
		List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList = zjXmJxQuarterlyProjectDetailsMapper
				.selectByZjXmJxQuarterlyProjectDetailsList(zjXmJxQuarterlyProjectDetails);
		// ??????????????????
		PageInfo<ZjXmJxQuarterlyProjectDetails> p = new PageInfo<>(zjXmJxQuarterlyProjectDetailsList);

		return repEntity.okList(zjXmJxQuarterlyProjectDetailsList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDetail(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ????????????
		ZjXmJxQuarterlyProjectDetails dbZjXmJxQuarterlyProjectDetails = zjXmJxQuarterlyProjectDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getDetailsId());
		// ????????????
		if (dbZjXmJxQuarterlyProjectDetails != null) {
			return repEntity.ok(dbZjXmJxQuarterlyProjectDetails);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyProjectDetails(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyProjectDetails.setDetailsId(UuidUtil.generate());
		zjXmJxQuarterlyProjectDetails.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyProjectDetailsMapper.insert(zjXmJxQuarterlyProjectDetails);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyProjectDetails);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyProjectDetails(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyProjectDetails dbZjXmJxQuarterlyProjectDetails = zjXmJxQuarterlyProjectDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getDetailsId());
		if (dbZjXmJxQuarterlyProjectDetails != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyProjectDetails.getDetailsId())) {
			// ????????????id
			dbZjXmJxQuarterlyProjectDetails.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
			// ??????id
			dbZjXmJxQuarterlyProjectDetails.setProjectId(zjXmJxQuarterlyProjectDetails.getProjectId());
			// ????????????
			dbZjXmJxQuarterlyProjectDetails.setProjectName(zjXmJxQuarterlyProjectDetails.getProjectName());
			// ????????????/???????????????key
			dbZjXmJxQuarterlyProjectDetails.setManagerKey(zjXmJxQuarterlyProjectDetails.getManagerKey());
			// ????????????/???????????????name
			dbZjXmJxQuarterlyProjectDetails.setManagerName(zjXmJxQuarterlyProjectDetails.getManagerName());
			// ??????ID
			dbZjXmJxQuarterlyProjectDetails.setDeptId(zjXmJxQuarterlyProjectDetails.getDeptId());
			// ????????????
			dbZjXmJxQuarterlyProjectDetails.setDeptName(zjXmJxQuarterlyProjectDetails.getDeptName());
			// ????????????id
			dbZjXmJxQuarterlyProjectDetails.setProjectStatus(zjXmJxQuarterlyProjectDetails.getProjectStatus());
			// ??????????????????
			dbZjXmJxQuarterlyProjectDetails.setProjectStatusName(zjXmJxQuarterlyProjectDetails.getProjectStatusName());
			// ????????????id
			dbZjXmJxQuarterlyProjectDetails.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
			// ??????????????????
			dbZjXmJxQuarterlyProjectDetails.setProjectTypeName(zjXmJxQuarterlyProjectDetails.getProjectTypeName());
			// ????????????
			dbZjXmJxQuarterlyProjectDetails.setLibraryTitle(zjXmJxQuarterlyProjectDetails.getLibraryTitle());
			// ????????????
			dbZjXmJxQuarterlyProjectDetails.setLibraryContent(zjXmJxQuarterlyProjectDetails.getLibraryContent());
			// ???????????????key
			dbZjXmJxQuarterlyProjectDetails.setPersonLiableKey(zjXmJxQuarterlyProjectDetails.getPersonLiableKey());
			// ?????????????????????
			dbZjXmJxQuarterlyProjectDetails.setPersonLiableName(zjXmJxQuarterlyProjectDetails.getPersonLiableName());
			// ?????????????????????
			dbZjXmJxQuarterlyProjectDetails.setIsFixedScore(zjXmJxQuarterlyProjectDetails.getIsFixedScore());
			// ??????
			dbZjXmJxQuarterlyProjectDetails.setScore(zjXmJxQuarterlyProjectDetails.getScore());
			// ???/????????????
			dbZjXmJxQuarterlyProjectDetails.setLowerLimitScore(zjXmJxQuarterlyProjectDetails.getLowerLimitScore());
			// ???/????????????
			dbZjXmJxQuarterlyProjectDetails.setUpperLimitScore(zjXmJxQuarterlyProjectDetails.getUpperLimitScore());
			// ?????????????????????
			dbZjXmJxQuarterlyProjectDetails.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
			// ????????????
			dbZjXmJxQuarterlyProjectDetails.setConfirmStatus(zjXmJxQuarterlyProjectDetails.getConfirmStatus());
			// ??????
			dbZjXmJxQuarterlyProjectDetails.setRemarks(zjXmJxQuarterlyProjectDetails.getRemarks());
			// ??????
			dbZjXmJxQuarterlyProjectDetails.setSort(zjXmJxQuarterlyProjectDetails.getSort());
			// ??????
			dbZjXmJxQuarterlyProjectDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyProjectDetailsMapper.updateByPrimaryKey(dbZjXmJxQuarterlyProjectDetails);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyProjectDetails);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyProjectDetails(
			List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyProjectDetailsList != null && zjXmJxQuarterlyProjectDetailsList.size() > 0) {
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
			zjXmJxQuarterlyProjectDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyProjectDetailsMapper.batchDeleteUpdateZjXmJxQuarterlyProjectDetails(
					zjXmJxQuarterlyProjectDetailsList, zjXmJxQuarterlyProjectDetails);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyProjectDetailsList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity batchConfirmZjXmJxQuarterlyProjectDetails(
			List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyProjectDetailsList != null && zjXmJxQuarterlyProjectDetailsList.size() > 0) {
			flag = zjXmJxQuarterlyProjectDetailsMapper
					.batchConfirmZjXmJxQuarterlyProjectDetails(zjXmJxQuarterlyProjectDetailsList);
		}
		// ??????
		if (flag == 0) {
			return repEntity.layerMessage("no", "???????????????????????????");
		} else {
			return repEntity.ok("?????????????????????");
		}
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsByPersonLiable(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// ?????????????????????????????????????????????
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// ????????????
		zjXmJxQuarterlyProjectDetails.setProjectDetailsList(libraryList);
		List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsByPersonLiable(zjXmJxQuarterlyProjectDetails);
		return repEntity.okList(dataList, dataList.size());
	}

	@Override
	public ResponseEntity submitZjXmJxQuarterlyProjectDetailsByPersonLiable(List<Map<String, Object>> mapList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		List<ZjXmJxQuarterlyProjectDetails> updateScoreList = Lists.newArrayList();
		// ??????????????????????????????
		if (mapList.size() > 0) {
			for (Map<String, Object> map : mapList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// ??????key???32???uuid???????????????id
					// ?????????id?????????id?????????id?????????????????????????
					if (entry.getKey().length() == 32) {
						ZjXmJxQuarterlyProjectDetails update = new ZjXmJxQuarterlyProjectDetails();
						update.setDeptId(String.valueOf(map.get("deptId")));
						update.setProjectId(String.valueOf(map.get("projectId")));
						update.setAssessmentId(String.valueOf(map.get("assessmentId")));
						update.setLibraryId(entry.getKey());
						update.setActualScore(new BigDecimal(String.valueOf(entry.getValue())));
						update.setModifyUserInfo(userKey, realName);
						updateScoreList.add(update);
					}
				}
			}
		}
		if (updateScoreList.size() > 0) {
			flag = zjXmJxQuarterlyProjectDetailsMapper.batchUpdateZjXmJxQuarterlyProjectDetailsScore(updateScoreList);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("???????????????");
		}
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDeptColumn(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ?????????????????????
		// ?????????????????????????????????????????????
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// ??????????????????
		// JSONArray array = JSONUtil.createArray();
//		JSONObject obj1 = JSONUtil.createObj();
//		obj1.set("??????", "actualStatusName");
//		array.add(obj1);
//		JSONObject obj2 = JSONUtil.createObj();
//		obj2.set("????????????", "projectName");
//		array.add(obj2);
//		JSONObject obj3 = JSONUtil.createObj();
//		obj3.set("????????????/???????????????", "managerName");
//		array.add(obj3);
//		JSONObject obj4 = JSONUtil.createObj();
//		obj4.set("??????????????????", "totalScore");
//		array.add(obj4);
//		JSONObject obj5 = JSONUtil.createObj();
//		obj5.set("????????????", "");
//		// obj5.set("????????????", "");
//		obj5.set("?????????", "");
//		obj5.set("???????????????", "");
//		array.add(obj5);
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map1.put("??????", "actualStatusName");
		list.add(map1);
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("????????????", "projectName");
		list.add(map2);
		Map<String, Object> map3 = new LinkedHashMap<String, Object>();
		map3.put("????????????/???????????????", "managerName");
		list.add(map3);
		Map<String, Object> map4 = new LinkedHashMap<String, Object>();
		map4.put("??????????????????", "totalScore");
		list.add(map4);
		Map<String, Object> map5 = new LinkedHashMap<String, Object>();
		map5.put("????????????", "");
		// obj5.set("????????????", "");
		map5.put("?????????", "");
		map5.put("???????????????", "");
		list.add(map5);
		// ??????
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				// JSONObject obj6 = JSONUtil.createObj();
				// obj6.set(columnTitle.getLibraryContent(), columnTitle.getLibraryId());
				// obj6.set(String.valueOf(columnTitle.getScore()), columnTitle.getLibraryId());
				// obj6.set(columnTitle.getPersonLiableName(), columnTitle.getLibraryId());
				// array.add(obj6);
				Map<String, Object> map6 = new LinkedHashMap<String, Object>();
				map6.put(columnTitle.getLibraryContent(), columnTitle.getLibraryId());
				// ?????????????????????
				if (StrUtil.equals("1", columnTitle.getIsFixedScore())) {
					map6.put(String.valueOf(columnTitle.getScore()), columnTitle.getLibraryId());
				} else {
					map6.put(String.valueOf(columnTitle.getLowerLimitScore()) + "~" + columnTitle.getUpperLimitScore(),
							columnTitle.getLibraryId());
				}
				map6.put(columnTitle.getPersonLiableName(), columnTitle.getLibraryId());
				list.add(map6);
			}
		}
		return repEntity.okList(list, list.size());
	}

	@Override
	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByDept(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// ?????????????????????????????????????????????
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// ????????????
		zjXmJxQuarterlyProjectDetails.setProjectDetailsList(libraryList);
		List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
				.countZjXmJxQuarterlyProjectDetailsByDept(zjXmJxQuarterlyProjectDetails);
		return repEntity.okList(dataList, dataList.size());
	}

	@Override
	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByWeight(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// ?????????????????????????????????????????????
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// ????????????
		zjXmJxQuarterlyProjectDetails.setProjectDetailsList(libraryList);
		List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
				.countZjXmJxQuarterlyProjectDetailsByWeight(zjXmJxQuarterlyProjectDetails);
		// ????????????????????????
		// ???????????????????????????????????????
		ZjXmJxQuarterlyWeightDetails search = new ZjXmJxQuarterlyWeightDetails();
		search.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		search.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
		search.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
		List<ZjXmJxQuarterlyWeightDetails> list = zjXmJxQuarterlyWeightDetailsMapper
				.selectByZjXmJxQuarterlyWeightDetailsList(search);
		if (dataList.size() > 0 && list.size() == 1) {
			for (Map<String, Object> map : dataList) {
				// ??????map???????????????
				BigDecimal sum = BigDecimal.ZERO;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// ??????key?????????
					if (!Pattern.matches("^[a-zA-Z]*", String.valueOf(entry.getKey().charAt(0)))) {
						int weight = getDeptWeightByDeptName(entry.getKey(), list.get(0));
						BigDecimal product = CalcUtils.calcMultiply(new BigDecimal(String.valueOf(entry.getValue())),
								new BigDecimal(weight));
						sum = CalcUtils.calcAdd(sum, product);
					}
				}
				// ?????????
				BigDecimal totalScore = CalcUtils.calcDivide(sum, new BigDecimal(100), 1);
				map.put("totalScore", totalScore);
			}
		}
		return repEntity.okList(dataList, dataList.size());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsWeightColumn(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ?????????????????????
		// ?????????????????????????????????????????????
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// ??????????????????
		List<Map<String, Object>> returnList = Lists.newArrayList();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map1.put("??????", "actualStatusName");
		returnList.add(map1);
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("????????????", "projectName");
		returnList.add(map2);
		Map<String, Object> map3 = new LinkedHashMap<String, Object>();
		map3.put("???????????????/???????????????", "managerName");
		returnList.add(map3);
		Map<String, Object> map4 = new LinkedHashMap<String, Object>();
		map4.put("????????????", "totalScore");
		map4.put("??????", "totalScore");
		returnList.add(map4);
		// ??????
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				// ???map5??????key??????
				// Map<String, Object> map5 = new LinkedHashMap<String, Object>();
				TreeMap<String, Object> map5 = new TreeMap<String, Object>(new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (NumberUtil.isNumber(o1) && !NumberUtil.isNumber(o2)) {
							return 1;
						} else if (!NumberUtil.isNumber(o1) && NumberUtil.isNumber(o2)) {
							return -1;
						} else {
							return 0;
						}
					}
				});
				map5.put(columnTitle.getDeptName(), columnTitle.getDeptName());
				// ???????????????????????????????????????
				ZjXmJxQuarterlyWeightDetails search = new ZjXmJxQuarterlyWeightDetails();
				search.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
				search.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
				search.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
				List<ZjXmJxQuarterlyWeightDetails> list = zjXmJxQuarterlyWeightDetailsMapper
						.selectByZjXmJxQuarterlyWeightDetailsList(search);
				if (list.size() == 1) {
					map5.put(String.valueOf(getDeptWeightByDeptName(columnTitle.getDeptName(), list.get(0))),
							columnTitle.getDeptName());
				}
				returnList.add(map5);
			}
		}
		return repEntity.okList(returnList, returnList.size());
	}

	private int getDeptWeightByDeptName(String deptName, ZjXmJxQuarterlyWeightDetails weightObj) {
		int weight = 0;
		if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getRoadBridgeWeight();
		} else if (deptName.indexOf("??????????????????") > -1) {
			weight = weightObj.getHousingWeight();
		} else if (deptName.indexOf("??????????????????") > -1) {
			weight = weightObj.getTrackWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getTechnicalWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getSafetyWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getBusinessWeight();
		} else if (deptName.indexOf("?????????") > -1) {
			weight = weightObj.getFinanceWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getMaterialsWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getHumanResourcesWeight();
		} else if (deptName.indexOf("?????????") > -1) {
			weight = weightObj.getLegalWeight();
		} else if (deptName.indexOf("?????????") > -1) {
			weight = weightObj.getOfficeWeight();
		} else if (deptName.indexOf("??????????????????") > -1) {
			weight = weightObj.getSupplyChainWeight();
		} else if (deptName.indexOf("????????????") > -1) {
			weight = weightObj.getClosingCenterWeight();
		}
		return weight;
	}

	@Override
	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsConfirmStatus(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		JSONObject obj = JSONUtil.createObj();
		int count = zjXmJxQuarterlyProjectDetailsMapper
				.checkZjXmJxQuarterlyProjectDetailsConfirmStatus(zjXmJxQuarterlyProjectDetails);
		if (count > 0) {
			obj.set("buttonFlag", "show");
		} else {
			obj.set("buttonFlag", "hide");
		}
		// ??????
		return repEntity.ok(obj);
	}

	@Override
	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsActualScore(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		JSONObject obj = JSONUtil.createObj();
		int count = zjXmJxQuarterlyProjectDetailsMapper
				.checkZjXmJxQuarterlyProjectDetailsActualScore(zjXmJxQuarterlyProjectDetails);
		if (count > 0) {
			obj.set("buttonFlag", "show");
		} else {
			obj.set("buttonFlag", "hide");
		}
		// ??????
		return repEntity.ok(obj);
	}

	@Override
	public void exportZjXmJxQuarterlyProjectDetailsDeptExcel(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails, HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ?????????????????????
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// ??????????????????
		List<List<?>> rowsList = Lists.newArrayList();
		// ??????
		List<String> row1 = CollUtil.newArrayList("??????", "??????", "????????????", "????????????/???????????????", "??????????????????", "????????????");
		List<String> row2 = CollUtil.newArrayList("", "", "", "", "", "???????????????");
		List<String> row3 = CollUtil.newArrayList("", "", "", "", "", "???????????????");
		String deptName = "";
		if (columnTitleList.size() > 0) {
			deptName = columnTitleList.get(0).getDeptName();
			// ??????????????????????????????
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				row1.add(columnTitle.getLibraryContent());
				// ?????????????????????
				if (StrUtil.equals("1", columnTitle.getIsFixedScore())) {
					row2.add(String.valueOf(columnTitle.getScore()));
				} else {
					row2.add(String.valueOf(columnTitle.getLowerLimitScore()) + "~" + columnTitle.getUpperLimitScore());
				}
				row3.add(columnTitle.getPersonLiableName());
			}
			// ????????????
			zjXmJxQuarterlyProjectDetails.setProjectDetailsList(columnTitleList);
			List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
					.countZjXmJxQuarterlyProjectDetailsByDept(zjXmJxQuarterlyProjectDetails);
			// ????????????
			// rowsList.add(CollUtil.newArrayList(row1));
			rowsList.add(row1);
			rowsList.add(row2);
			rowsList.add(row3);
			if (dataList != null && dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					List<String> contentList = Lists.newArrayList();
					String rankNo = dataList.get(i).get("rankNo").toString();
					contentList.add(rankNo.substring(0, rankNo.indexOf(".")));
					contentList.add(dataList.get(i).get("actualStatusName").toString());
					contentList.add(dataList.get(i).get("projectName").toString());
					contentList.add(dataList.get(i).get("managerName").toString());
					contentList.add(dataList.get(i).get("totalScore").toString());
					contentList.add("");
					for (int j = 0; j < columnTitleList.size(); j++) {
						contentList.add(dataList.get(i).get(columnTitleList.get(j).getLibraryId()).toString());
					}
					// ???????????????rowsList
					// rowsList.add(CollUtil.newArrayList(contentList));
					rowsList.add(contentList);
				}
			}
		}
		// ??????????????????????????????????????????
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// ?????????????????????writer?????????xlsx??????
		// response???HttpServletResponse??????
		// ??????response????????????
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		// test.xls??????????????????????????????????????????????????????????????????????????????
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out???OutputStream??????????????????????????????
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String((deptName + "???????????????????????????").getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// ????????????
			// ????????????????????????
			ZjXmJxQuarterlyAssessment dbQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
					.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getAssessmentId());
			// 2020??????????????????????????????????????????????????????????????????????????????
			String header = "";
			if (dbQuarterlyAssessment != null) {
				String quarter = "";
				if (StrUtil.equals("1", dbQuarterlyAssessment.getQuarter())) {
					quarter = "?????????";
				} else if (StrUtil.equals("2", dbQuarterlyAssessment.getQuarter())) {
					quarter = "?????????";
				} else if (StrUtil.equals("3", dbQuarterlyAssessment.getQuarter())) {
					quarter = "?????????";
				} else if (StrUtil.equals("4", dbQuarterlyAssessment.getQuarter())) {
					quarter = "?????????";
				}
				header = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "???" + quarter + deptName
						+ dbQuarterlyAssessment.getAssessmentTitle() + "?????????";
			}
			// ??????
			writer.merge(row1.size() - 1, header);
			writer.merge(1, 3, 0, 0, "", false);
			writer.merge(1, 3, 1, 1, "", false);
			writer.merge(1, 3, 2, 2, "", true);
			writer.merge(1, 3, 3, 3, "", true);
			writer.merge(1, 3, 4, 4, "", true);
			// ???????????????????????????????????????????????????????????????
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ??????writer???????????????
			if (writer != null) {
				writer.close();
			}
			// ????????????????????????Servlet???
			if (out != null) {
				IoUtil.close(out);
			}
		}
	}

	@Override
	public void exportZjXmJxQuarterlyProjectDetailsWeightExcel(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails, HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// ????????????????????????
		ZjXmJxQuarterlyAssessment dbQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		// ???????????? 2020???3?????????????????????????????????????????????????????????
		String header = "";
		String quarter = "";
		String yearQuarter = "";
		if (dbQuarterlyAssessment != null) {
			if (StrUtil.equals("1", dbQuarterlyAssessment.getQuarter())) {
				quarter = "?????????";
			} else if (StrUtil.equals("2", dbQuarterlyAssessment.getQuarter())) {
				quarter = "?????????";
			} else if (StrUtil.equals("3", dbQuarterlyAssessment.getQuarter())) {
				quarter = "?????????";
			} else if (StrUtil.equals("4", dbQuarterlyAssessment.getQuarter())) {
				quarter = "?????????";
			}
			header = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "???" + quarter
					+ dbQuarterlyAssessment.getAssessmentTitle() + "???????????????";
			yearQuarter = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "???" + quarter;
		}
		// ???????????????????????????????????????
		ZjXmJxQuarterlyWeightDetails search = new ZjXmJxQuarterlyWeightDetails();
		search.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		search.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
		search.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
		List<ZjXmJxQuarterlyWeightDetails> list = zjXmJxQuarterlyWeightDetailsMapper
				.selectByZjXmJxQuarterlyWeightDetailsList(search);
		// ?????????????????????
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// ??????????????????
		List<List<?>> rowsList = Lists.newArrayList();
		// ??????
		List<String> row1 = CollUtil.newArrayList("??????", "??????", "????????????", "???????????????/???????????????", yearQuarter + "????????????",
				"????????????????????????????????????");
		List<String> row2 = CollUtil.newArrayList("", "", "", "", "");
		List<String> row3 = CollUtil.newArrayList("", "", "", "", "??????");
		// ??????
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				row2.add(columnTitle.getDeptName());
				if (list.size() == 1) {
					row3.add(String.valueOf(getDeptWeightByDeptName(columnTitle.getDeptName(), list.get(0))));
				}
			}
			// ????????????
			// rowsList.add(CollUtil.newArrayList(row1));
			rowsList.add(row1);
			rowsList.add(row2);
			rowsList.add(row3);
			// ????????????
			zjXmJxQuarterlyProjectDetails.setProjectDetailsList(columnTitleList);
			List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
					.countZjXmJxQuarterlyProjectDetailsByWeight(zjXmJxQuarterlyProjectDetails);
			// ????????????????????????
			if (dataList.size() > 0 && list.size() == 1) {
				for (Map<String, Object> map : dataList) {
					// ??????map???????????????
					BigDecimal sum = BigDecimal.ZERO;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						// ??????key?????????
						if (!Pattern.matches("^[a-zA-Z]*", String.valueOf(entry.getKey().charAt(0)))) {
							int weight = getDeptWeightByDeptName(entry.getKey(), list.get(0));
							BigDecimal product = CalcUtils.calcMultiply(
									new BigDecimal(String.valueOf(entry.getValue())), new BigDecimal(weight));
							sum = CalcUtils.calcAdd(sum, product);
						}
					}
					// ?????????
					BigDecimal totalScore = CalcUtils.calcDivide(sum, new BigDecimal(100), 1);
					map.put("totalScore", totalScore);
					// ????????????
					List<String> contentList = Lists.newArrayList();
					String rankNo = map.get("rankNo").toString();
					contentList.add(rankNo.substring(0, rankNo.indexOf(".")));
					contentList.add(map.get("actualStatusName").toString());
					contentList.add(map.get("projectName").toString());
					contentList.add(map.get("managerName").toString());
					contentList.add(map.get("totalScore").toString());
					for (int j = 0; j < columnTitleList.size(); j++) {
						contentList.add(map.get(columnTitleList.get(j).getDeptName()).toString());
					}
					// ???????????????rowsList
					// rowsList.add(CollUtil.newArrayList(contentList));
					rowsList.add(contentList);
				}
			}
		}
		// ??????????????????????????????????????????
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// ?????????????????????writer?????????xlsx??????
		// response???HttpServletResponse??????
		// ??????response????????????
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		// test.xls??????????????????????????????????????????????????????????????????????????????
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out???OutputStream??????????????????????????????
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String((yearQuarter + "?????????????????????").getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// ??????
			writer.merge(row1.size() - 1, header, true);
			writer.merge(1, 3, 0, 0, "", false);
			writer.merge(1, 3, 1, 1, "", false);
			writer.merge(1, 3, 2, 2, "", false);
			writer.merge(1, 3, 3, 3, "", false);
			writer.merge(1, 2, 4, 4, "", false);
			if (row1.size() > 6) {
				writer.merge(1, 1, 5, row1.size() - 1, "", false);
			}
			// ???????????????????????????????????????????????????????????????
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ??????writer???????????????
			if (writer != null) {
				writer.close();
			}
			// ????????????????????????Servlet???
			if (out != null) {
				IoUtil.close(out);
			}
		}
	}

}
