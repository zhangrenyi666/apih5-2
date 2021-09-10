package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyAssessmentDeptMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessmentDept;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;
import com.apih5.service.ZjXmJxQuarterlyWeightManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxQuarterlyWeightManagementService")
public class ZjXmJxQuarterlyWeightManagementServiceImpl implements ZjXmJxQuarterlyWeightManagementService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightManagementMapper zjXmJxQuarterlyWeightManagementMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentDeptMapper zjXmJxQuarterlyAssessmentDeptMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyWeightManagementListByCondition(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		if (zjXmJxQuarterlyWeightManagement == null) {
			zjXmJxQuarterlyWeightManagement = new ZjXmJxQuarterlyWeightManagement();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyWeightManagement.getPage(), zjXmJxQuarterlyWeightManagement.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyWeightManagement> zjXmJxQuarterlyWeightManagementList = zjXmJxQuarterlyWeightManagementMapper
				.selectByZjXmJxQuarterlyWeightManagementList(zjXmJxQuarterlyWeightManagement);
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyWeightManagement> p = new PageInfo<>(zjXmJxQuarterlyWeightManagementList);

		return repEntity.okList(zjXmJxQuarterlyWeightManagementList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyWeightManagementDetail(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		if (zjXmJxQuarterlyWeightManagement == null) {
			zjXmJxQuarterlyWeightManagement = new ZjXmJxQuarterlyWeightManagement();
		}
		// 获取数据
		ZjXmJxQuarterlyWeightManagement dbZjXmJxQuarterlyWeightManagement = zjXmJxQuarterlyWeightManagementMapper
				.selectByPrimaryKey(zjXmJxQuarterlyWeightManagement.getManagementId());
		// 数据存在
		if (dbZjXmJxQuarterlyWeightManagement != null) {
			return repEntity.ok(dbZjXmJxQuarterlyWeightManagement);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyWeightManagement.setManagementId(UuidUtil.generate());
		zjXmJxQuarterlyWeightManagement.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyWeightManagementMapper.insert(zjXmJxQuarterlyWeightManagement);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyWeightManagement);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyWeightManagement dbZjXmJxQuarterlyWeightManagement = zjXmJxQuarterlyWeightManagementMapper
				.selectByPrimaryKey(zjXmJxQuarterlyWeightManagement.getManagementId());
		if (dbZjXmJxQuarterlyWeightManagement != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyWeightManagement.getManagementId())) {
			// 项目类型id
			dbZjXmJxQuarterlyWeightManagement.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyWeightManagement.setProjectTypeName(zjXmJxQuarterlyWeightManagement.getProjectTypeName());
			// 路桥事业部权重
			dbZjXmJxQuarterlyWeightManagement
					.setRoadBridgeWeight(zjXmJxQuarterlyWeightManagement.getRoadBridgeWeight());
			// 城市房建事业部权重
			dbZjXmJxQuarterlyWeightManagement.setHousingWeight(zjXmJxQuarterlyWeightManagement.getHousingWeight());
			// 铁路轨道事业部权重
			dbZjXmJxQuarterlyWeightManagement.setTrackWeight(zjXmJxQuarterlyWeightManagement.getTrackWeight());
			// 技术质量部权重
			dbZjXmJxQuarterlyWeightManagement.setTechnicalWeight(zjXmJxQuarterlyWeightManagement.getTechnicalWeight());
			// 安全监督部权重
			dbZjXmJxQuarterlyWeightManagement.setSafetyWeight(zjXmJxQuarterlyWeightManagement.getSafetyWeight());
			// 经营考核部权重
			dbZjXmJxQuarterlyWeightManagement.setBusinessWeight(zjXmJxQuarterlyWeightManagement.getBusinessWeight());
			// 财务部权重
			dbZjXmJxQuarterlyWeightManagement.setFinanceWeight(zjXmJxQuarterlyWeightManagement.getFinanceWeight());
			// 物资设备部权重
			dbZjXmJxQuarterlyWeightManagement.setMaterialsWeight(zjXmJxQuarterlyWeightManagement.getMaterialsWeight());
			// 人力资源部权重
			dbZjXmJxQuarterlyWeightManagement
					.setHumanResourcesWeight(zjXmJxQuarterlyWeightManagement.getHumanResourcesWeight());
			// 法律部权重
			dbZjXmJxQuarterlyWeightManagement.setLegalWeight(zjXmJxQuarterlyWeightManagement.getLegalWeight());
			// 办公室权重
			dbZjXmJxQuarterlyWeightManagement.setOfficeWeight(zjXmJxQuarterlyWeightManagement.getOfficeWeight());
			// 经营考核部供应链管理部权重
			dbZjXmJxQuarterlyWeightManagement
					.setSupplyChainWeight(zjXmJxQuarterlyWeightManagement.getSupplyChainWeight());
			// 经营考核部收尾中心权重
			dbZjXmJxQuarterlyWeightManagement
					.setClosingCenterWeight(zjXmJxQuarterlyWeightManagement.getClosingCenterWeight());
			// 是否是收尾项目
			dbZjXmJxQuarterlyWeightManagement.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
			// 备注
			dbZjXmJxQuarterlyWeightManagement.setRemarks(zjXmJxQuarterlyWeightManagement.getRemarks());
			// 排序
			dbZjXmJxQuarterlyWeightManagement.setSort(zjXmJxQuarterlyWeightManagement.getSort());
			// 共通
			dbZjXmJxQuarterlyWeightManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyWeightManagementMapper.updateByPrimaryKey(dbZjXmJxQuarterlyWeightManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyWeightManagement);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightManagement(
			List<ZjXmJxQuarterlyWeightManagement> zjXmJxQuarterlyWeightManagementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyWeightManagementList != null && zjXmJxQuarterlyWeightManagementList.size() > 0) {
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement = new ZjXmJxQuarterlyWeightManagement();
			zjXmJxQuarterlyWeightManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyWeightManagementMapper.batchDeleteUpdateZjXmJxQuarterlyWeightManagement(
					zjXmJxQuarterlyWeightManagementList, zjXmJxQuarterlyWeightManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyWeightManagementList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity singleUpdateZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 每次更新只更新一个值,并且没有空值
		ZjXmJxQuarterlyWeightManagement dbZjXmJxQuarterlyWeightManagement = zjXmJxQuarterlyWeightManagementMapper
				.selectByPrimaryKey(zjXmJxQuarterlyWeightManagement.getManagementId());
		if (dbZjXmJxQuarterlyWeightManagement != null) {
			// 非收尾→0:无类型区分 1:路桥类型、2:房建类型、3:轨道类型
			if (StrUtil.equals("0", zjXmJxQuarterlyWeightManagement.getProjectType())) {
				zjXmJxQuarterlyWeightManagement.setProjectTypeName("全部类型");
			} else if (StrUtil.equals("1", zjXmJxQuarterlyWeightManagement.getProjectType())) {
				zjXmJxQuarterlyWeightManagement.setProjectTypeName("路桥类型");
			} else if (StrUtil.equals("2", zjXmJxQuarterlyWeightManagement.getProjectType())) {
				zjXmJxQuarterlyWeightManagement.setProjectTypeName("房建类型");
			} else if (StrUtil.equals("3", zjXmJxQuarterlyWeightManagement.getProjectType())) {
				zjXmJxQuarterlyWeightManagement.setProjectTypeName("轨道类型");
			}
			// 项目类型id(必传)
			dbZjXmJxQuarterlyWeightManagement.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyWeightManagement.setProjectTypeName(zjXmJxQuarterlyWeightManagement.getProjectTypeName());
			// 是否是收尾项目(必传)
			dbZjXmJxQuarterlyWeightManagement.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
			// 1、权重必传
			// if (zjXmJxQuarterlyWeightManagement.getRoadBridgeWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getRoadBridgeWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("路桥事业部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "路桥事业部没有该项目类型数据,权重应为零");
				}
			}
			// 路桥事业部权重
			dbZjXmJxQuarterlyWeightManagement
					.setRoadBridgeWeight(zjXmJxQuarterlyWeightManagement.getRoadBridgeWeight());
			// }
			// 2、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getHousingWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getHousingWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("城市房建事业部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "城市房建事业部没有该项目类型数据,权重应为零");
				}
			}
			// 城市房建事业部权重
			dbZjXmJxQuarterlyWeightManagement.setHousingWeight(zjXmJxQuarterlyWeightManagement.getHousingWeight());
			// }
			// 3、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getTrackWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getTrackWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("铁路轨道事业部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "铁路轨道事业部没有该项目类型数据,权重应为零");
				}
			}
			// 铁路轨道事业部权重
			dbZjXmJxQuarterlyWeightManagement.setTrackWeight(zjXmJxQuarterlyWeightManagement.getTrackWeight());
			// }
			// 4、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getTechnicalWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getTechnicalWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("技术质量部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "技术质量部没有该项目类型数据,权重应为零");
				}
			}
			// 技术质量部权重
			dbZjXmJxQuarterlyWeightManagement.setTechnicalWeight(zjXmJxQuarterlyWeightManagement.getTechnicalWeight());
			// }
			// 5、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getSafetyWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getSafetyWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("安全监督部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "安全监督部没有该项目类型数据,权重应为零");
				}
			}
			// 安全监督部权重
			dbZjXmJxQuarterlyWeightManagement.setSafetyWeight(zjXmJxQuarterlyWeightManagement.getSafetyWeight());

			// }
			// 6、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getBusinessWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getBusinessWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("经营考核部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "经营考核部没有该项目类型数据,权重应为零");
				}
			}
			// 经营考核部权重
			dbZjXmJxQuarterlyWeightManagement.setBusinessWeight(zjXmJxQuarterlyWeightManagement.getBusinessWeight());
			// }
			// 7、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getFinanceWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getFinanceWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("财务部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "财务部没有该项目类型数据,权重应为零");
				}
			}
			// 财务部权重
			dbZjXmJxQuarterlyWeightManagement.setFinanceWeight(zjXmJxQuarterlyWeightManagement.getFinanceWeight());

			// }
			// 8、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getMaterialsWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getMaterialsWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("物资设备部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "物资设备部没有该项目类型数据,权重应为零");
				}
			}
			// 物资设备部权重
			dbZjXmJxQuarterlyWeightManagement.setMaterialsWeight(zjXmJxQuarterlyWeightManagement.getMaterialsWeight());
			// }
			// 9、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getHumanResourcesWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getHumanResourcesWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("人力资源部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "人力资源部没有该项目类型数据,权重应为零");
				}
			}
			// 人力资源部权重
			dbZjXmJxQuarterlyWeightManagement
					.setHumanResourcesWeight(zjXmJxQuarterlyWeightManagement.getHumanResourcesWeight());

			// }
			// 10、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getLegalWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getLegalWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("法律部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "法律部没有该项目类型数据,权重应为零");
				}
			}
			// 法律部权重
			dbZjXmJxQuarterlyWeightManagement.setLegalWeight(zjXmJxQuarterlyWeightManagement.getLegalWeight());

			// }
			// 11、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getOfficeWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getOfficeWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("办公室");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "办公室没有该项目类型数据,权重应为零");
				}
			}
			// 办公室权重
			dbZjXmJxQuarterlyWeightManagement.setOfficeWeight(zjXmJxQuarterlyWeightManagement.getOfficeWeight());

			// }
			// 12、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getSupplyChainWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getSupplyChainWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("经营考核部供应链管理部");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "经营考核部供应链管理部没有该项目类型数据,权重应为零");
				}
			}
			// 经营考核部供应链管理部权重
			dbZjXmJxQuarterlyWeightManagement
					.setSupplyChainWeight(zjXmJxQuarterlyWeightManagement.getSupplyChainWeight());

			// }
			// 13、权重必传,check是否合理(根据部门表)
			// if (zjXmJxQuarterlyWeightManagement.getClosingCenterWeight() != null) {
			// 如果不是0,check是否合理(根据部门表)
			if (zjXmJxQuarterlyWeightManagement.getClosingCenterWeight() != 0) {
				ZjXmJxQuarterlyAssessmentDept check = new ZjXmJxQuarterlyAssessmentDept();
				check.setIsClosed(zjXmJxQuarterlyWeightManagement.getIsClosed());
				check.setProjectType(zjXmJxQuarterlyWeightManagement.getProjectType());
				check.setDeptName("经营考核部收尾中心");
				int count = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check);
				if (count < 1) {
					return repEntity.layerMessage("no", "经营考核部收尾中心没有该项目类型数据,权重应为零");
				}
			}
			// 经营考核部收尾中心权重
			dbZjXmJxQuarterlyWeightManagement
					.setClosingCenterWeight(zjXmJxQuarterlyWeightManagement.getClosingCenterWeight());
			// }
			// check所有部门的权重和为100
			int sum = zjXmJxQuarterlyWeightManagement.getRoadBridgeWeight()
					+ zjXmJxQuarterlyWeightManagement.getHousingWeight()
					+ zjXmJxQuarterlyWeightManagement.getTrackWeight()
					+ zjXmJxQuarterlyWeightManagement.getTechnicalWeight()
					+ zjXmJxQuarterlyWeightManagement.getSafetyWeight()
					+ zjXmJxQuarterlyWeightManagement.getBusinessWeight()
					+ zjXmJxQuarterlyWeightManagement.getFinanceWeight()
					+ zjXmJxQuarterlyWeightManagement.getMaterialsWeight()
					+ zjXmJxQuarterlyWeightManagement.getHumanResourcesWeight()
					+ zjXmJxQuarterlyWeightManagement.getLegalWeight()
					+ zjXmJxQuarterlyWeightManagement.getOfficeWeight()
					+ zjXmJxQuarterlyWeightManagement.getSupplyChainWeight()
					+ zjXmJxQuarterlyWeightManagement.getClosingCenterWeight();
			if (sum != 100) {
				return repEntity.layerMessage("no", "权重和应为100。");
			}
			// 共通
			dbZjXmJxQuarterlyWeightManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyWeightManagementMapper.updateByPrimaryKey(dbZjXmJxQuarterlyWeightManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyWeightManagement);
		}
	}

}
