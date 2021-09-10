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
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyProjectDetails.getPage(), zjXmJxQuarterlyProjectDetails.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList = zjXmJxQuarterlyProjectDetailsMapper
				.selectByZjXmJxQuarterlyProjectDetailsList(zjXmJxQuarterlyProjectDetails);
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyProjectDetails> p = new PageInfo<>(zjXmJxQuarterlyProjectDetailsList);

		return repEntity.okList(zjXmJxQuarterlyProjectDetailsList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDetail(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// 获取数据
		ZjXmJxQuarterlyProjectDetails dbZjXmJxQuarterlyProjectDetails = zjXmJxQuarterlyProjectDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getDetailsId());
		// 数据存在
		if (dbZjXmJxQuarterlyProjectDetails != null) {
			return repEntity.ok(dbZjXmJxQuarterlyProjectDetails);
		} else {
			return repEntity.layerMessage("no", "无数据！");
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
			// 季度考核id
			dbZjXmJxQuarterlyProjectDetails.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
			// 项目id
			dbZjXmJxQuarterlyProjectDetails.setProjectId(zjXmJxQuarterlyProjectDetails.getProjectId());
			// 项目名称
			dbZjXmJxQuarterlyProjectDetails.setProjectName(zjXmJxQuarterlyProjectDetails.getProjectName());
			// 项目经理/收尾负责人key
			dbZjXmJxQuarterlyProjectDetails.setManagerKey(zjXmJxQuarterlyProjectDetails.getManagerKey());
			// 项目经理/收尾负责人name
			dbZjXmJxQuarterlyProjectDetails.setManagerName(zjXmJxQuarterlyProjectDetails.getManagerName());
			// 部门ID
			dbZjXmJxQuarterlyProjectDetails.setDeptId(zjXmJxQuarterlyProjectDetails.getDeptId());
			// 部门名称
			dbZjXmJxQuarterlyProjectDetails.setDeptName(zjXmJxQuarterlyProjectDetails.getDeptName());
			// 项目状态id
			dbZjXmJxQuarterlyProjectDetails.setProjectStatus(zjXmJxQuarterlyProjectDetails.getProjectStatus());
			// 项目状态名称
			dbZjXmJxQuarterlyProjectDetails.setProjectStatusName(zjXmJxQuarterlyProjectDetails.getProjectStatusName());
			// 项目类型id
			dbZjXmJxQuarterlyProjectDetails.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyProjectDetails.setProjectTypeName(zjXmJxQuarterlyProjectDetails.getProjectTypeName());
			// 考核标题
			dbZjXmJxQuarterlyProjectDetails.setLibraryTitle(zjXmJxQuarterlyProjectDetails.getLibraryTitle());
			// 考核内容
			dbZjXmJxQuarterlyProjectDetails.setLibraryContent(zjXmJxQuarterlyProjectDetails.getLibraryContent());
			// 考核责任人key
			dbZjXmJxQuarterlyProjectDetails.setPersonLiableKey(zjXmJxQuarterlyProjectDetails.getPersonLiableKey());
			// 考核责任人姓名
			dbZjXmJxQuarterlyProjectDetails.setPersonLiableName(zjXmJxQuarterlyProjectDetails.getPersonLiableName());
			// 是否是固定分数
			dbZjXmJxQuarterlyProjectDetails.setIsFixedScore(zjXmJxQuarterlyProjectDetails.getIsFixedScore());
			// 分数
			dbZjXmJxQuarterlyProjectDetails.setScore(zjXmJxQuarterlyProjectDetails.getScore());
			// 加/减分下限
			dbZjXmJxQuarterlyProjectDetails.setLowerLimitScore(zjXmJxQuarterlyProjectDetails.getLowerLimitScore());
			// 加/减分上限
			dbZjXmJxQuarterlyProjectDetails.setUpperLimitScore(zjXmJxQuarterlyProjectDetails.getUpperLimitScore());
			// 是否是收尾项目
			dbZjXmJxQuarterlyProjectDetails.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
			// 确认状态
			dbZjXmJxQuarterlyProjectDetails.setConfirmStatus(zjXmJxQuarterlyProjectDetails.getConfirmStatus());
			// 备注
			dbZjXmJxQuarterlyProjectDetails.setRemarks(zjXmJxQuarterlyProjectDetails.getRemarks());
			// 排序
			dbZjXmJxQuarterlyProjectDetails.setSort(zjXmJxQuarterlyProjectDetails.getSort());
			// 共通
			dbZjXmJxQuarterlyProjectDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyProjectDetailsMapper.updateByPrimaryKey(dbZjXmJxQuarterlyProjectDetails);
		}
		// 失败
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
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyProjectDetailsList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

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
		// 失败
		if (flag == 0) {
			return repEntity.layerMessage("no", "确认失败，请重试。");
		} else {
			return repEntity.ok("项目确认成功。");
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
		// 获取该条件下的项目以及题库集合
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// 获取数据
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
		// 将列数据整理成行数据
		if (mapList.size() > 0) {
			for (Map<String, Object> map : mapList) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// 如果key是32位uuid则是指标库id
					// 指标库id和项目id和部门id可以确定一条数据?
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
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("提交成功。");
		}
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDeptColumn(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// 获取列标题内容
		// 获取该条件下的项目以及题库集合
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// 返回汇总数据
		// JSONArray array = JSONUtil.createArray();
//		JSONObject obj1 = JSONUtil.createObj();
//		obj1.set("状态", "actualStatusName");
//		array.add(obj1);
//		JSONObject obj2 = JSONUtil.createObj();
//		obj2.set("项目名称", "projectName");
//		array.add(obj2);
//		JSONObject obj3 = JSONUtil.createObj();
//		obj3.set("项目经理/收尾负责人", "managerName");
//		array.add(obj3);
//		JSONObject obj4 = JSONUtil.createObj();
//		obj4.set("部门考核得分", "totalScore");
//		array.add(obj4);
//		JSONObject obj5 = JSONUtil.createObj();
//		obj5.set("考核内容", "");
//		// obj5.set("评分标准", "");
//		obj5.set("考核分", "");
//		obj5.set("考核责任人", "");
//		array.add(obj5);
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map1.put("状态", "actualStatusName");
		list.add(map1);
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("项目名称", "projectName");
		list.add(map2);
		Map<String, Object> map3 = new LinkedHashMap<String, Object>();
		map3.put("项目经理/收尾负责人", "managerName");
		list.add(map3);
		Map<String, Object> map4 = new LinkedHashMap<String, Object>();
		map4.put("部门考核得分", "totalScore");
		list.add(map4);
		Map<String, Object> map5 = new LinkedHashMap<String, Object>();
		map5.put("考核内容", "");
		// obj5.set("评分标准", "");
		map5.put("考核分", "");
		map5.put("考核责任人", "");
		list.add(map5);
		// 表头
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				// JSONObject obj6 = JSONUtil.createObj();
				// obj6.set(columnTitle.getLibraryContent(), columnTitle.getLibraryId());
				// obj6.set(String.valueOf(columnTitle.getScore()), columnTitle.getLibraryId());
				// obj6.set(columnTitle.getPersonLiableName(), columnTitle.getLibraryId());
				// array.add(obj6);
				Map<String, Object> map6 = new LinkedHashMap<String, Object>();
				map6.put(columnTitle.getLibraryContent(), columnTitle.getLibraryId());
				// 是否是固定分数
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
		// 获取该条件下的项目以及题库集合
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// 获取数据
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
		// 获取该条件下的项目以及题库集合
		List<ZjXmJxQuarterlyProjectDetails> libraryList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// 获取数据
		zjXmJxQuarterlyProjectDetails.setProjectDetailsList(libraryList);
		List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
				.countZjXmJxQuarterlyProjectDetailsByWeight(zjXmJxQuarterlyProjectDetails);
		// 根据权重算出总分
		// 根据部门名称获取对应的权重
		ZjXmJxQuarterlyWeightDetails search = new ZjXmJxQuarterlyWeightDetails();
		search.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		search.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
		search.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
		List<ZjXmJxQuarterlyWeightDetails> list = zjXmJxQuarterlyWeightDetailsMapper
				.selectByZjXmJxQuarterlyWeightDetailsList(search);
		if (dataList.size() > 0 && list.size() == 1) {
			for (Map<String, Object> map : dataList) {
				// 便利map算出综合分
				BigDecimal sum = BigDecimal.ZERO;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// 如果key是中文
					if (!Pattern.matches("^[a-zA-Z]*", String.valueOf(entry.getKey().charAt(0)))) {
						int weight = getDeptWeightByDeptName(entry.getKey(), list.get(0));
						BigDecimal product = CalcUtils.calcMultiply(new BigDecimal(String.valueOf(entry.getValue())),
								new BigDecimal(weight));
						sum = CalcUtils.calcAdd(sum, product);
					}
				}
				// 综合分
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
		// 获取列标题内容
		// 获取该条件下的项目以及题库集合
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// 返回汇总数据
		List<Map<String, Object>> returnList = Lists.newArrayList();
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map1.put("状态", "actualStatusName");
		returnList.add(map1);
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("项目名称", "projectName");
		returnList.add(map2);
		Map<String, Object> map3 = new LinkedHashMap<String, Object>();
		map3.put("项目负责人/收尾负责人", "managerName");
		returnList.add(map3);
		Map<String, Object> map4 = new LinkedHashMap<String, Object>();
		map4.put("考核分值", "totalScore");
		map4.put("权重", "totalScore");
		returnList.add(map4);
		// 表头
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				// 对map5按照key排序
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
				// 根据部门名称获取对应的权重
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
		if (deptName.indexOf("路桥事业") > -1) {
			weight = weightObj.getRoadBridgeWeight();
		} else if (deptName.indexOf("城市房建事业") > -1) {
			weight = weightObj.getHousingWeight();
		} else if (deptName.indexOf("铁路轨道事业") > -1) {
			weight = weightObj.getTrackWeight();
		} else if (deptName.indexOf("技术质量") > -1) {
			weight = weightObj.getTechnicalWeight();
		} else if (deptName.indexOf("安全监督") > -1) {
			weight = weightObj.getSafetyWeight();
		} else if (deptName.indexOf("经营考核") > -1) {
			weight = weightObj.getBusinessWeight();
		} else if (deptName.indexOf("财务部") > -1) {
			weight = weightObj.getFinanceWeight();
		} else if (deptName.indexOf("物资设备") > -1) {
			weight = weightObj.getMaterialsWeight();
		} else if (deptName.indexOf("人力资源") > -1) {
			weight = weightObj.getHumanResourcesWeight();
		} else if (deptName.indexOf("法律部") > -1) {
			weight = weightObj.getLegalWeight();
		} else if (deptName.indexOf("办公室") > -1) {
			weight = weightObj.getOfficeWeight();
		} else if (deptName.indexOf("供应链管理部") > -1) {
			weight = weightObj.getSupplyChainWeight();
		} else if (deptName.indexOf("收尾中心") > -1) {
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
		// 失败
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
		// 失败
		return repEntity.ok(obj);
	}

	@Override
	public void exportZjXmJxQuarterlyProjectDetailsDeptExcel(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails, HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxQuarterlyProjectDetails == null) {
			zjXmJxQuarterlyProjectDetails = new ZjXmJxQuarterlyProjectDetails();
		}
		// 获取列标题内容
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn1(zjXmJxQuarterlyProjectDetails);
		// 返回汇总数据
		List<List<?>> rowsList = Lists.newArrayList();
		// 表头
		List<String> row1 = CollUtil.newArrayList("序号", "状态", "项目名称", "项目经理/收尾负责人", "部门考核得分", "考核内容");
		List<String> row2 = CollUtil.newArrayList("", "", "", "", "", "考核标准分");
		List<String> row3 = CollUtil.newArrayList("", "", "", "", "", "考核责任人");
		String deptName = "";
		if (columnTitleList.size() > 0) {
			deptName = columnTitleList.get(0).getDeptName();
			// 否则动态获取统计数据
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				row1.add(columnTitle.getLibraryContent());
				// 是否是固定分数
				if (StrUtil.equals("1", columnTitle.getIsFixedScore())) {
					row2.add(String.valueOf(columnTitle.getScore()));
				} else {
					row2.add(String.valueOf(columnTitle.getLowerLimitScore()) + "~" + columnTitle.getUpperLimitScore());
				}
				row3.add(columnTitle.getPersonLiableName());
			}
			// 获取数据
			zjXmJxQuarterlyProjectDetails.setProjectDetailsList(columnTitleList);
			List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
					.countZjXmJxQuarterlyProjectDetailsByDept(zjXmJxQuarterlyProjectDetails);
			// 装载数据
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
					// 装载完放入rowsList
					// rowsList.add(CollUtil.newArrayList(contentList));
					rowsList.add(contentList);
				}
			}
		}
		// 数据装载（与上面的表头对应）
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// 通过工具类创建writer，创建xlsx格式
		// response为HttpServletResponse对象
		// 设置response下载弹窗
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String((deptName + "项目季度考核统计表").getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			// 获取季度考核信息
			ZjXmJxQuarterlyAssessment dbQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
					.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getAssessmentId());
			// 2020年二季度铁路轨道事业部对板块内项目综合管理考核评分表
			String header = "";
			if (dbQuarterlyAssessment != null) {
				String quarter = "";
				if (StrUtil.equals("1", dbQuarterlyAssessment.getQuarter())) {
					quarter = "一季度";
				} else if (StrUtil.equals("2", dbQuarterlyAssessment.getQuarter())) {
					quarter = "二季度";
				} else if (StrUtil.equals("3", dbQuarterlyAssessment.getQuarter())) {
					quarter = "三季度";
				} else if (StrUtil.equals("4", dbQuarterlyAssessment.getQuarter())) {
					quarter = "四季度";
				}
				header = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "年" + quarter + deptName
						+ dbQuarterlyAssessment.getAssessmentTitle() + "评分表";
			}
			// 表头
			writer.merge(row1.size() - 1, header);
			writer.merge(1, 3, 0, 0, "", false);
			writer.merge(1, 3, 1, 1, "", false);
			writer.merge(1, 3, 2, 2, "", true);
			writer.merge(1, 3, 3, 3, "", true);
			writer.merge(1, 3, 4, 4, "", true);
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭writer，释放内存
			if (writer != null) {
				writer.close();
			}
			// 此处记得关闭输出Servlet流
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
		// 获取季度考核信息
		ZjXmJxQuarterlyAssessment dbQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		// 设置标题 2020年3季度收尾交账项目综合管理考核评分汇总表
		String header = "";
		String quarter = "";
		String yearQuarter = "";
		if (dbQuarterlyAssessment != null) {
			if (StrUtil.equals("1", dbQuarterlyAssessment.getQuarter())) {
				quarter = "一季度";
			} else if (StrUtil.equals("2", dbQuarterlyAssessment.getQuarter())) {
				quarter = "二季度";
			} else if (StrUtil.equals("3", dbQuarterlyAssessment.getQuarter())) {
				quarter = "三季度";
			} else if (StrUtil.equals("4", dbQuarterlyAssessment.getQuarter())) {
				quarter = "四季度";
			}
			header = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "年" + quarter
					+ dbQuarterlyAssessment.getAssessmentTitle() + "评分汇总表";
			yearQuarter = DateUtil.year(dbQuarterlyAssessment.getYearMonth()) + "年" + quarter;
		}
		// 根据部门名称获取对应的权重
		ZjXmJxQuarterlyWeightDetails search = new ZjXmJxQuarterlyWeightDetails();
		search.setAssessmentId(zjXmJxQuarterlyProjectDetails.getAssessmentId());
		search.setProjectType(zjXmJxQuarterlyProjectDetails.getProjectType());
		search.setIsClosed(zjXmJxQuarterlyProjectDetails.getIsClosed());
		List<ZjXmJxQuarterlyWeightDetails> list = zjXmJxQuarterlyWeightDetailsMapper
				.selectByZjXmJxQuarterlyWeightDetailsList(search);
		// 获取列标题内容
		List<ZjXmJxQuarterlyProjectDetails> columnTitleList = zjXmJxQuarterlyProjectDetailsMapper
				.getZjXmJxQuarterlyProjectDetailsDynamicColumn2(zjXmJxQuarterlyProjectDetails);
		// 返回汇总数据
		List<List<?>> rowsList = Lists.newArrayList();
		// 表头
		List<String> row1 = CollUtil.newArrayList("序号", "状态", "项目名称", "项目负责人/收尾负责人", yearQuarter + "考核分值",
				"机关职能部室及事业部评分");
		List<String> row2 = CollUtil.newArrayList("", "", "", "", "");
		List<String> row3 = CollUtil.newArrayList("", "", "", "", "权重");
		// 表头
		if (columnTitleList.size() > 0) {
			for (ZjXmJxQuarterlyProjectDetails columnTitle : columnTitleList) {
				row2.add(columnTitle.getDeptName());
				if (list.size() == 1) {
					row3.add(String.valueOf(getDeptWeightByDeptName(columnTitle.getDeptName(), list.get(0))));
				}
			}
			// 装载数据
			// rowsList.add(CollUtil.newArrayList(row1));
			rowsList.add(row1);
			rowsList.add(row2);
			rowsList.add(row3);
			// 获取数据
			zjXmJxQuarterlyProjectDetails.setProjectDetailsList(columnTitleList);
			List<Map<String, Object>> dataList = zjXmJxQuarterlyProjectDetailsMapper
					.countZjXmJxQuarterlyProjectDetailsByWeight(zjXmJxQuarterlyProjectDetails);
			// 根据权重算出总分
			if (dataList.size() > 0 && list.size() == 1) {
				for (Map<String, Object> map : dataList) {
					// 便利map算出综合分
					BigDecimal sum = BigDecimal.ZERO;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						// 如果key是中文
						if (!Pattern.matches("^[a-zA-Z]*", String.valueOf(entry.getKey().charAt(0)))) {
							int weight = getDeptWeightByDeptName(entry.getKey(), list.get(0));
							BigDecimal product = CalcUtils.calcMultiply(
									new BigDecimal(String.valueOf(entry.getValue())), new BigDecimal(weight));
							sum = CalcUtils.calcAdd(sum, product);
						}
					}
					// 综合分
					BigDecimal totalScore = CalcUtils.calcDivide(sum, new BigDecimal(100), 1);
					map.put("totalScore", totalScore);
					// 构造数据
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
					// 装载完放入rowsList
					// rowsList.add(CollUtil.newArrayList(contentList));
					rowsList.add(contentList);
				}
			}
		}
		// 数据装载（与上面的表头对应）
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// 通过工具类创建writer，创建xlsx格式
		// response为HttpServletResponse对象
		// 设置response下载弹窗
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String((yearQuarter + "考核评分汇总表").getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 表头
			writer.merge(row1.size() - 1, header, true);
			writer.merge(1, 3, 0, 0, "", false);
			writer.merge(1, 3, 1, 1, "", false);
			writer.merge(1, 3, 2, 2, "", false);
			writer.merge(1, 3, 3, 3, "", false);
			writer.merge(1, 2, 4, 4, "", false);
			if (row1.size() > 6) {
				writer.merge(1, 1, 5, row1.size() - 1, "", false);
			}
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭writer，释放内存
			if (writer != null) {
				writer.close();
			}
			// 此处记得关闭输出Servlet流
			if (out != null) {
				IoUtil.close(out);
			}
		}
	}

}
