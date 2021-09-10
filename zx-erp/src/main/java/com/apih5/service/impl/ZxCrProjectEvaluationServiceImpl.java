package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import flex.messaging.io.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.service.ZxCrProjectEvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxCrProjectEvaluationService")
public class ZxCrProjectEvaluationServiceImpl implements ZxCrProjectEvaluationService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCrProjectEvaluationMapper zxCrProjectEvaluationMapper;

	@Autowired(required = true)
	private ZxCrCustomerInfoMapper zxCrCustomerInfoMapper;

	@Autowired(required = true)
	private ZxCrProjectEvaluationScoreMapper zxCrProjectEvaluationScoreMapper;

	@Autowired(required = true)
	private ZxCrProjectEvaluationBadMapper zxCrProjectEvaluationBadMapper;

	@Autowired(required = true)
	private ZxCrColCategoryMapper zxCrColCategoryMapper;

	@Autowired(required = true)
	private ZxCrColResourceMapper zxCrColResourceMapper;

	@Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;

	@Autowired(required = true)
	private ZxCrHalfYearCreditEvaItemMapper zxCrHalfYearCreditEvaItemMapper;

	@Autowired(required = true)
	private ZxCrJYearCreditEvaItemMapper zxCrJYearCreditEvaItemMapper;

	@Override
	public ResponseEntity getZxCrProjectEvaluationListByCondition(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		if (zxCrProjectEvaluation == null) {
			zxCrProjectEvaluation = new ZxCrProjectEvaluation();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrProjectEvaluation.setCompanyId("");
        	zxCrProjectEvaluation.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrProjectEvaluation.setCompanyId(zxCrProjectEvaluation.getOrgID());
        	zxCrProjectEvaluation.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrProjectEvaluation.setProjectId(zxCrProjectEvaluation.getOrgID());
        }
		// 分页查询
		PageHelper.startPage(zxCrProjectEvaluation.getPage(), zxCrProjectEvaluation.getLimit());
		// 获取数据
		List<ZxCrProjectEvaluation> zxCrProjectEvaluationList = zxCrProjectEvaluationMapper
				.selectByZxCrProjectEvaluationList(zxCrProjectEvaluation);

		// 查询打分考核表
		for (ZxCrProjectEvaluation zxCrProjectEvaluation1 : zxCrProjectEvaluationList) {
			ZxCrProjectEvaluationScore zxCrProjectEvaluationScore = new ZxCrProjectEvaluationScore();
			zxCrProjectEvaluationScore.setMainID(zxCrProjectEvaluation1.getZxCrProjectEvaluationId());
			List<ZxCrProjectEvaluationScore> projCreditScoreListAll = zxCrProjectEvaluationScoreMapper
					.selectByZxCrProjectEvaluationScoreList(zxCrProjectEvaluationScore);
			zxCrProjectEvaluation1.setProjectEvaluationScoreList(projCreditScoreListAll);
		}

		// 查询严重不良行为考核表
		for (ZxCrProjectEvaluation zxCrProjectEvaluation3 : zxCrProjectEvaluationList) {
			ZxCrProjectEvaluationBad zxCrProjectEvaluationBad = new ZxCrProjectEvaluationBad();
			zxCrProjectEvaluationBad.setMainID(zxCrProjectEvaluation3.getZxCrProjectEvaluationId());
			List<ZxCrProjectEvaluationBad> projCreditBadBehaListAll = zxCrProjectEvaluationBadMapper
					.selectByZxCrProjectEvaluationBadList(zxCrProjectEvaluationBad);
			zxCrProjectEvaluation3.setProjectEvaluationBadList(projCreditBadBehaListAll);
		}

		// 查询附件
		for (ZxCrProjectEvaluation zxCrProjectEvaluation2 : zxCrProjectEvaluationList) {
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(zxCrProjectEvaluation2.getZxCrProjectEvaluationId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
			zxCrProjectEvaluation2.setFileList(zxErpFiles);
		}
		// 得到分页信息
		PageInfo<ZxCrProjectEvaluation> p = new PageInfo<>(zxCrProjectEvaluationList);
		return repEntity.okList(zxCrProjectEvaluationList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationDetail(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		if (zxCrProjectEvaluation == null) {
			zxCrProjectEvaluation = new ZxCrProjectEvaluation();
		}
		// 获取数据
		ZxCrProjectEvaluation dbZxCrProjectEvaluation = zxCrProjectEvaluationMapper
				.selectByPrimaryKey(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
		// 数据存在
		if (dbZxCrProjectEvaluation != null) {
			// 附件
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
			zxCrProjectEvaluation.setFileList(zxErpFiles);
			return repEntity.ok(dbZxCrProjectEvaluation);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCrProjectEvaluation(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCrProjectEvaluation.setZxCrProjectEvaluationId(UuidUtil.generate());
		zxCrProjectEvaluation.setCreateUserInfo(userKey, realName);
		zxCrProjectEvaluation.setAuditStatus("0");
		String year = zxCrProjectEvaluation.getPeriod().substring(0, 4);
		String quarter = zxCrProjectEvaluation.getPeriod().substring(4, 6);
		zxCrProjectEvaluation.setPeriodYear(year);
		zxCrProjectEvaluation.setPeriodQuarter(Integer.parseInt(quarter));
		// 承建工程合同额（万元）的Check
		String[] array = zxCrProjectEvaluation.getContractAmt().toString().split("\\.");
		if (array.length > 1) {
			if (array[0].length() > 10 || array[1].length() > 6) {
				return repEntity.layerMessage("no", "承建工程合同额（万元）只接受小数点前10位范围内的数字，小数点后6位范围内的数字！");
			}
		} else {
			if (array[0].length() > 10) {
				return repEntity.layerMessage("no", "承建工程合同额（万元）只接受小数点前10位范围内的数字，小数点后6位范围内的数字！");
			}
		}
		if (zxCrProjectEvaluation.getCheckStandard().equals("1")) {
			// 添加打分考核表
			List<ZxCrProjectEvaluationScore> ProjCreditScoreListAll = zxCrProjectEvaluation
					.getProjectEvaluationScoreList();
			if (ProjCreditScoreListAll != null && ProjCreditScoreListAll.size() > 0) {
				for (ZxCrProjectEvaluationScore zxCrProjCreditScore : ProjCreditScoreListAll) {
					zxCrProjCreditScore.setMainID(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
					zxCrProjCreditScore.setZxCrProjectEvaluationScoreId(UuidUtil.generate());
					zxCrProjCreditScore.setCreateUserInfo(userKey, realName);
					zxCrProjectEvaluationScoreMapper.insert(zxCrProjCreditScore);
				}
			}
		} else if (zxCrProjectEvaluation.getCheckStandard().equals("0")) {
			// 添加严重不良行为考核表
			List<ZxCrProjectEvaluationBad> ProjCreditBadBehaListAll = zxCrProjectEvaluation
					.getProjectEvaluationBadList();
			if (ProjCreditBadBehaListAll != null && ProjCreditBadBehaListAll.size() > 0) {
				for (ZxCrProjectEvaluationBad zxCrProjCreditBadBeha : ProjCreditBadBehaListAll) {
					zxCrProjCreditBadBeha.setMainID(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
					zxCrProjCreditBadBeha.setZxCrProjectEvaluationBadId(UuidUtil.generate());
					zxCrProjCreditBadBeha.setCreateUserInfo(userKey, realName);
					zxCrProjectEvaluationBadMapper.insert(zxCrProjCreditBadBeha);
				}
			}
		}
		// 添加附件
		List<ZxErpFile> fileList = zxCrProjectEvaluation.getFileList();
		if (fileList != null && fileList.size() > 0) {
			for (ZxErpFile zxErpFile : fileList) {
				zxErpFile.setOtherId(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
				zxErpFile.setUid((UuidUtil.generate()));
				zxErpFile.setCreateUserInfo(userKey, realName);
				zxErpFileMapper.insert(zxErpFile);
			}
		}
		int flag = zxCrProjectEvaluationMapper.insert(zxCrProjectEvaluation);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCrProjectEvaluation);
		}
	}

	@Override
	public ResponseEntity updateZxCrProjectEvaluation(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCrProjectEvaluation dbZxCrProjectEvaluation = zxCrProjectEvaluationMapper
				.selectByPrimaryKey(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
		if (dbZxCrProjectEvaluation != null
				&& StrUtil.isNotEmpty(dbZxCrProjectEvaluation.getZxCrProjectEvaluationId())) {
			// 项目id
			dbZxCrProjectEvaluation.setOrgId(zxCrProjectEvaluation.getOrgId());
			// 项目名称
			dbZxCrProjectEvaluation.setOrgName(zxCrProjectEvaluation.getOrgName());
			// 协作单位id
			dbZxCrProjectEvaluation.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			// 协作单位名称
			dbZxCrProjectEvaluation.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			// 组织机构代码证
			dbZxCrProjectEvaluation.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			// 分类代码
			dbZxCrProjectEvaluation.setResCode(zxCrProjectEvaluation.getResCode());
			// 分类ID
			dbZxCrProjectEvaluation.setResID(zxCrProjectEvaluation.getResID());
			// 分类名称
			dbZxCrProjectEvaluation.setResName(zxCrProjectEvaluation.getResName());
			// 专业类别代码
			dbZxCrProjectEvaluation.setCatCode(zxCrProjectEvaluation.getCatCode());
			// 专业类别id
			dbZxCrProjectEvaluation.setCatID(zxCrProjectEvaluation.getCatID());
			// 专业类别
			dbZxCrProjectEvaluation.setCatName(zxCrProjectEvaluation.getCatName());
			// 考核期次
			dbZxCrProjectEvaluation.setPeriod(zxCrProjectEvaluation.getPeriod());
			// 考核日期
			dbZxCrProjectEvaluation.setCheckDate(zxCrProjectEvaluation.getCheckDate());
			// 进场日期
			dbZxCrProjectEvaluation.setInDate(zxCrProjectEvaluation.getInDate());
			// 退场日期
			dbZxCrProjectEvaluation.setOutDate(zxCrProjectEvaluation.getOutDate());
			// 承建工程合同额（万元）
			dbZxCrProjectEvaluation.setContractAmt(zxCrProjectEvaluation.getContractAmt());
			// 考核总得分
			dbZxCrProjectEvaluation.setTotalScore(zxCrProjectEvaluation.getTotalScore());
			// 协作单位负责人
			dbZxCrProjectEvaluation.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			// editTime
			dbZxCrProjectEvaluation.setEditTime(zxCrProjectEvaluation.getEditTime());
			// comID
			dbZxCrProjectEvaluation.setComID(zxCrProjectEvaluation.getComID());
			// comName
			dbZxCrProjectEvaluation.setComName(zxCrProjectEvaluation.getComName());
			// comOrders
			dbZxCrProjectEvaluation.setComOrders(zxCrProjectEvaluation.getComOrders());
			// 审核状态
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// 填报人
			dbZxCrProjectEvaluation.setPreparer(zxCrProjectEvaluation.getPreparer());
			// 审核人
			dbZxCrProjectEvaluation.setAuditor(zxCrProjectEvaluation.getAuditor());
			// 考核标准
			dbZxCrProjectEvaluation.setCheckStandard(zxCrProjectEvaluation.getCheckStandard());
			// 负责人联系电话
			dbZxCrProjectEvaluation.setChargeManPhone(zxCrProjectEvaluation.getChargeManPhone());
			// 备注
			dbZxCrProjectEvaluation.setRemarks(zxCrProjectEvaluation.getRemarks());
			// 排序
			dbZxCrProjectEvaluation.setSort(zxCrProjectEvaluation.getSort());
			// 共通
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			flag = zxCrProjectEvaluationMapper.updateByPrimaryKey(dbZxCrProjectEvaluation);
			if (zxCrProjectEvaluation.getCheckStandard().equals("1")) {
				// 修改再新增(打分考核表)
				ZxCrProjectEvaluationScore zxCrProjCreditScoresSelect = new ZxCrProjectEvaluationScore();
				zxCrProjCreditScoresSelect.setMainID(dbZxCrProjectEvaluation.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationScore> zxCrProjCreditScore = zxCrProjectEvaluationScoreMapper
						.selectByZxCrProjectEvaluationScoreList(zxCrProjCreditScoresSelect);
				if (zxCrProjCreditScore != null && zxCrProjCreditScore.size() > 0) {
					zxCrProjCreditScoresSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationScoreMapper.batchDeleteUpdateZxCrProjectEvaluationScore(zxCrProjCreditScore,
							zxCrProjCreditScoresSelect);
				}
				List<ZxCrProjectEvaluationScore> ProjCreditScoreListAll = zxCrProjectEvaluation
						.getProjectEvaluationScoreList();
				if (ProjCreditScoreListAll != null && ProjCreditScoreListAll.size() > 0) {
					for (ZxCrProjectEvaluationScore zxCrProjCreditScore1 : ProjCreditScoreListAll) {
						zxCrProjCreditScore1.setMainID(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
						zxCrProjCreditScore1.setZxCrProjectEvaluationScoreId(UuidUtil.generate());
						zxCrProjCreditScore1.setCreateUserInfo(userKey, realName);
						zxCrProjectEvaluationScoreMapper.insert(zxCrProjCreditScore1);
					}
				}
			} else if (zxCrProjectEvaluation.getCheckStandard().equals("0")) {
				// 修改再新增(严重不良行为考核表)
				ZxCrProjectEvaluationBad zxCrProjCreditBadBehaSelect = new ZxCrProjectEvaluationBad();
				zxCrProjCreditBadBehaSelect.setMainID(dbZxCrProjectEvaluation.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationBad> ZxCrProjCreditBadBeha = zxCrProjectEvaluationBadMapper
						.selectByZxCrProjectEvaluationBadList(zxCrProjCreditBadBehaSelect);
				if (ZxCrProjCreditBadBeha != null && ZxCrProjCreditBadBeha.size() > 0) {
					zxCrProjCreditBadBehaSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationBadMapper.batchDeleteUpdateZxCrProjectEvaluationBad(ZxCrProjCreditBadBeha,
							zxCrProjCreditBadBehaSelect);
				}
				List<ZxCrProjectEvaluationBad> ProjCreditBadBehaListAll = zxCrProjectEvaluation
						.getProjectEvaluationBadList();
				if (ProjCreditBadBehaListAll != null && ProjCreditBadBehaListAll.size() > 0) {
					for (ZxCrProjectEvaluationBad zxCrProjCreditBadBeha : ProjCreditBadBehaListAll) {
						zxCrProjCreditBadBeha.setMainID(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
						zxCrProjCreditBadBeha.setZxCrProjectEvaluationBadId(UuidUtil.generate());
						zxCrProjCreditBadBeha.setCreateUserInfo(userKey, realName);
						zxCrProjectEvaluationBadMapper.insert(zxCrProjCreditBadBeha);
					}
				}
			}
			// 修改在新增(附件)
			ZxErpFile zxErpFileSelect = new ZxErpFile();
			zxErpFileSelect.setOtherId(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
			if (zxErpFiles != null && zxErpFiles.size() > 0) {
				zxErpFileSelect.setModifyUserInfo(userKey, realName);
				zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
			}
			List<ZxErpFile> fileList = zxCrProjectEvaluation.getFileList();
			if (fileList != null && fileList.size() > 0) {
				for (ZxErpFile zxErpFile : fileList) {
					zxErpFile.setOtherId(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
					zxErpFile.setUid((UuidUtil.generate()));
					zxErpFile.setCreateUserInfo(userKey, realName);
					zxErpFileMapper.insert(zxErpFile);
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCrProjectEvaluation);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCrProjectEvaluation(
			List<ZxCrProjectEvaluation> zxCrProjectEvaluationList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCrProjectEvaluationList != null && zxCrProjectEvaluationList.size() > 0) {
			for (ZxCrProjectEvaluation ZxCrProjectEvaluation2 : zxCrProjectEvaluationList) {
				// 删除打分考核表
				ZxCrProjectEvaluationScore zxCrProjCreditScoresSelect = new ZxCrProjectEvaluationScore();
				zxCrProjCreditScoresSelect.setMainID(ZxCrProjectEvaluation2.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationScore> zxCrProjCreditScore = zxCrProjectEvaluationScoreMapper
						.selectByZxCrProjectEvaluationScoreList(zxCrProjCreditScoresSelect);
				if (zxCrProjCreditScore != null && zxCrProjCreditScore.size() > 0) {
					zxCrProjCreditScoresSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationScoreMapper.batchDeleteUpdateZxCrProjectEvaluationScore(zxCrProjCreditScore,
							zxCrProjCreditScoresSelect);
				}
				// 删除严重不良行为考核表
				ZxCrProjectEvaluationBad zxCrProjCreditBadBehaSelect = new ZxCrProjectEvaluationBad();
				zxCrProjCreditBadBehaSelect.setMainID(ZxCrProjectEvaluation2.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationBad> ZxCrProjCreditBadBeha = zxCrProjectEvaluationBadMapper
						.selectByZxCrProjectEvaluationBadList(zxCrProjCreditBadBehaSelect);
				if (ZxCrProjCreditBadBeha != null && ZxCrProjCreditBadBeha.size() > 0) {
					zxCrProjCreditBadBehaSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationBadMapper.batchDeleteUpdateZxCrProjectEvaluationBad(ZxCrProjCreditBadBeha,
							zxCrProjCreditBadBehaSelect);
				}
				// 删除附件
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(ZxCrProjectEvaluation2.getZxCrProjectEvaluationId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
			}
			ZxCrProjectEvaluation zxCrProjectEvaluation = new ZxCrProjectEvaluation();
			zxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			flag = zxCrProjectEvaluationMapper.batchDeleteUpdateZxCrProjectEvaluation(zxCrProjectEvaluationList,
					zxCrProjectEvaluation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCrProjectEvaluationList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxCrCustomerInfoList() {
		// 获取数据
		List<ZxCrCustomerInfo> zxCrCustomerInfoList = zxCrCustomerInfoMapper.selectAll();
		// 得到分页信息
		PageInfo<ZxCrCustomerInfo> p = new PageInfo<>(zxCrCustomerInfoList);
		return repEntity.okList(zxCrCustomerInfoList, p.getTotal());
	}

	@Override
	public ResponseEntity updateAuditStatusOne(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCrProjectEvaluation dbZxCrProjectEvaluation = zxCrProjectEvaluationMapper
				.selectByPrimaryKey(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
		if (dbZxCrProjectEvaluation != null
				&& StrUtil.isNotEmpty(dbZxCrProjectEvaluation.getZxCrProjectEvaluationId())) {
			// 审核状态
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// 共通
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			// 审核人
			dbZxCrProjectEvaluation.setAuditor(realName);
			flag = zxCrProjectEvaluationMapper.updateByAuditStatus(dbZxCrProjectEvaluation);
			this.updateHalfYearCreditEvaItem(dbZxCrProjectEvaluation);
			this.updateCrJYearCreditEvaItem(dbZxCrProjectEvaluation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCrProjectEvaluation);
		}
	}

	@Override
	public ResponseEntity updateAuditStatusOut(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCrProjectEvaluation dbZxCrProjectEvaluation = zxCrProjectEvaluationMapper
				.selectByPrimaryKey(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
		if (dbZxCrProjectEvaluation != null
				&& StrUtil.isNotEmpty(dbZxCrProjectEvaluation.getZxCrProjectEvaluationId())) {
			// 审核状态
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// 共通
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			flag = zxCrProjectEvaluationMapper.updateByAuditStatusOut(dbZxCrProjectEvaluation);
			this.updateHalfYearCreditEvaItem(dbZxCrProjectEvaluation);
			this.updateCrJYearCreditEvaItem(dbZxCrProjectEvaluation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCrProjectEvaluation);
		}
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationListByCatName() {
		ZxCrColCategory zxCrColCategory = new ZxCrColCategory();
		// 获取专业类别
		List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryDear(zxCrColCategory);
		// 得到分页信息
		PageInfo<ZxCrColCategory> p = new PageInfo<>(zxCrColCategoryList);
		return repEntity.okList(zxCrColCategoryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationListByResName(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		if (StrUtil.isEmpty(zxCrProjectEvaluation.getParentID())) {
			return repEntity.layerMessage("no", "请先选择专业名称！");
		}
		ZxCrColResource zxCrColResource = new ZxCrColResource();
		zxCrColResource.setCategoryID(zxCrProjectEvaluation.getCatID());
		// 获取专业类别
		List<ZxCrColResource> zxCrColResourceList = zxCrColResourceMapper.selectByZxCrColResourceList(zxCrColResource);
		// 得到分页信息
		PageInfo<ZxCrColResource> p = new PageInfo<>(zxCrColResourceList);
		return repEntity.okList(zxCrColResourceList, p.getTotal());
	}

	//更新公司半年信用评价明细
	private void updateHalfYearCreditEvaItem(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		String year = zxCrProjectEvaluation.getPeriod().substring(0,4);
		String quarter = zxCrProjectEvaluation.getPeriod().substring(4,6);
		// 按 公司,项目,协作单位,专业分类,评价期次年份和季度,查询上半年或下半年所有项目评价
		ZxCrProjectEvaluation projectQuery = new ZxCrProjectEvaluation();
		projectQuery.setPeriodYear(year);
		projectQuery.setCompanyId(zxCrProjectEvaluation.getCompanyId());
		projectQuery.setProjectId(zxCrProjectEvaluation.getProjectId());
		projectQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		projectQuery.setCatID(zxCrProjectEvaluation.getCatID());
		projectQuery.setResID(zxCrProjectEvaluation.getResID());
		List<ZxCrProjectEvaluation> projectEvaluations;
		if (Integer.parseInt(quarter) <= 2) { // 上半年所有
			projectEvaluations = zxCrProjectEvaluationMapper.selectFirstHalfYearProjectEvaluationList(projectQuery);
		} else { // 下半年
			projectEvaluations = zxCrProjectEvaluationMapper.selectSecondHalfYearProjectEvaluationList(projectQuery);
		}

		//按 公司,项目,协作单位,专业分类,评价期次,查询公司半年评价数据
		ZxCrHalfYearCreditEvaItem dbQuery = new ZxCrHalfYearCreditEvaItem();
		dbQuery.setCatID(zxCrProjectEvaluation.getCatID());
		dbQuery.setResID(zxCrProjectEvaluation.getResID());
		dbQuery.setPeriodYear(year);
		dbQuery.setQuartEvalID(Integer.parseInt(quarter) <= 2 ? "1" : "2");
		dbQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		dbQuery.setComID(zxCrProjectEvaluation.getCompanyId());
		dbQuery.setProjectID(zxCrProjectEvaluation.getProjectId());
		List<ZxCrHalfYearCreditEvaItem> halfYearCreditEvaItems = zxCrHalfYearCreditEvaItemMapper.selectByZxCrHalfYearCreditEvaItemList(dbQuery);
		ZxCrHalfYearCreditEvaItem evaItem;
		// 如果没有 则新增
		if (CollectionUtils.isEmpty(halfYearCreditEvaItems)) {
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				return;
			}
			evaItem = new ZxCrHalfYearCreditEvaItem();
			//组织机构代码证
			evaItem.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			//协作单位
			evaItem.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			evaItem.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			//协作单位负责人
			evaItem.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			//工程所属项目
			evaItem.setProjectName(zxCrProjectEvaluation.getOrgName());
//			//进场日期
//			evaItem.setInDate(zxCrProjectEvaluation.getInDate());
//			//退场日期
//			evaItem.setOutDate(zxCrProjectEvaluation.getOutDate());
//			//合同总额
//			evaItem.setContractAmt(zxCrProjectEvaluation.getContractAmt());
			//专业类别ID
			evaItem.setCatID(zxCrProjectEvaluation.getCatID());
			//分类id
			evaItem.setResID(zxCrProjectEvaluation.getResID());
			//评价期次年分
			evaItem.setPeriodYear(String.valueOf(year));
			//季度评价期次1 12季度 2 34季度
			evaItem.setQuartEvalID(Integer.parseInt(quarter) <= 2 ? "1" : "2");
			evaItem.setProjectID(zxCrProjectEvaluation.getProjectId());
			evaItem.setComID(zxCrProjectEvaluation.getCompanyId());
			evaItem.setCompanyId(zxCrProjectEvaluation.getCompanyId());
			// 跟新半年信用评价
			this.halfYearEvaluationUpdate(evaItem, projectEvaluations);
			//随机主键ID
			evaItem.setZxCrHalfYearCreditEvaItemId(UuidUtil.generate());
			zxCrHalfYearCreditEvaItemMapper.insertInit(evaItem);
		} else {
			evaItem = halfYearCreditEvaItems.get(0);
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				zxCrHalfYearCreditEvaItemMapper.deleteByPrimaryKey(evaItem.getZxCrHalfYearCreditEvaItemId());
			}
			// 跟新半年信用评价
			this.halfYearEvaluationUpdate(evaItem, projectEvaluations);
			zxCrHalfYearCreditEvaItemMapper.updateByPrimaryKey(evaItem);
		}
	}

	// 半年项目评价跟新汇总
	private void halfYearEvaluationUpdate(ZxCrHalfYearCreditEvaItem evaItem, List<ZxCrProjectEvaluation> projectList) {
		Map<String, List<ZxCrProjectEvaluation>> projectMap = projectList.stream().collect(Collectors.groupingBy(p -> p.getPeriod()));
		evaItem.setContractAmt(projectList.stream().map(p -> p.getContractAmt()).reduce(BigDecimal.ZERO,BigDecimal::add));
		for (String period : projectMap.keySet()) {
			List<ZxCrProjectEvaluation> projectEvaluations = projectMap.get(period);
			BigDecimal bd100= new BigDecimal(100);
			BigDecimal bd90= new BigDecimal(90);
			BigDecimal bd75= new BigDecimal(75);
			BigDecimal bd60= new BigDecimal(60);
			//根据分数定等级
			String grade = "";
			BigDecimal bdLastScore = projectEvaluations.stream().map(p -> p.getTotalScore()).reduce(BigDecimal.ZERO,BigDecimal::add);
			if (bdLastScore.compareTo(bd100) == -1 && bdLastScore.compareTo(bd90) > -1) {
				grade ="A";
			} else if(bdLastScore.compareTo(bd90) == -1 && bdLastScore.compareTo(bd75) > -1) {
				grade ="B";
			} else if(bdLastScore.compareTo(bd75) == -1 && bdLastScore.compareTo(bd60) > -1) {
				grade ="C";
			} else if(bdLastScore.compareTo(bd60) == -1) {
				grade ="D";
			}
			String quarter = period.substring(4,6);
			if (Integer.parseInt(quarter) <= 2) {
				if (quarter.equals("01")) {
					evaItem.setFirstSoce(String.valueOf(bdLastScore));
					evaItem.setFirstLevel(grade);
				}
				if (quarter.equals("02")) {
					evaItem.setSecondScore(String.valueOf(bdLastScore));
					evaItem.setSecondLevel(grade);
				}
			} else {
				if (quarter.equals("03")) {
					evaItem.setFirstSoce(String.valueOf(bdLastScore));
					evaItem.setFirstLevel(grade);
				}
				if (quarter.equals("04")) {
					evaItem.setSecondScore(String.valueOf(bdLastScore));
					evaItem.setSecondLevel(grade);
				}
			}
			//有无直接降为D的行为
			if (evaItem.getFirstLevel().equals("D") || evaItem.getSecondLevel().equals("D")) {
				evaItem.setdLevel("D");
			} else {
				evaItem.setdLevel("-");
			}
			//最终评价得分
			if (StrUtil.isNotEmpty(evaItem.getFirstSoce()) && StrUtil.isNotEmpty(evaItem.getSecondScore())) {
				BigDecimal bd = new BigDecimal(evaItem.getFirstSoce());
				BigDecimal bd2 = new BigDecimal(evaItem.getSecondScore());
				BigDecimal bd3 = bd.add(bd2);
				BigDecimal bd4 = bd3.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_UP);
				evaItem.setLastScore(bd4.toString());
			} else {
				if (StrUtil.isEmpty(evaItem.getFirstSoce())) {
					BigDecimal bd = new BigDecimal(0);
					BigDecimal bd2 = new BigDecimal(evaItem.getSecondScore());
					BigDecimal bd3 = bd.add(bd2);
					BigDecimal bd4 = bd3.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_UP);
					evaItem.setLastScore(bd4.toString());
				} else if (StrUtil.isEmpty(evaItem.getSecondScore())) {
					BigDecimal bd = new BigDecimal(0);
					BigDecimal bd2 = new BigDecimal(evaItem.getFirstSoce());
					BigDecimal bd3 = bd.add(bd2);
					BigDecimal bd4 = bd3.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_UP);
					evaItem.setLastScore(bd4.toString());
				}
			}
			//信用评价等级
			if (bdLastScore.compareTo(new BigDecimal(60)) == 1) {
				evaItem.setLastLevel("D");
			}
		}
	}

	// 更新局年度信用评价
	private void updateCrJYearCreditEvaItem(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		String year = zxCrProjectEvaluation.getPeriod().substring(0,4);
		String quarter = zxCrProjectEvaluation.getPeriod().substring(4,6);
		// 按 公司,项目,协作单位,专业分类,评价期次年份,查询全年所有项目评价
		ZxCrProjectEvaluation projectQuery = new ZxCrProjectEvaluation();
		projectQuery.setCompanyId(zxCrProjectEvaluation.getCompanyId());
		projectQuery.setProjectId(zxCrProjectEvaluation.getProjectId());
		projectQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		projectQuery.setCatID(zxCrProjectEvaluation.getCatID());
		projectQuery.setResID(zxCrProjectEvaluation.getResID());
		projectQuery.setPeriodYear(year);
		projectQuery.setAuditStatus("1");
		List<ZxCrProjectEvaluation> projectEvaluations = zxCrProjectEvaluationMapper.selectByZxCrProjectEvaluationList(projectQuery);
		//按 公司,项目,协作单位,专业分类,评价期次年份,查询局全年信用评价数据
		ZxCrJYearCreditEvaItem dbQuery = new ZxCrJYearCreditEvaItem();
		dbQuery.setCatID(zxCrProjectEvaluation.getCatID());
		dbQuery.setResID(zxCrProjectEvaluation.getResID());
		dbQuery.setPeriodYear(year);
		dbQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		dbQuery.setComID(zxCrProjectEvaluation.getCompanyId());
		dbQuery.setProjectID(zxCrProjectEvaluation.getProjectId());
		List<ZxCrJYearCreditEvaItem> yearCreditEvaItems = zxCrJYearCreditEvaItemMapper.selectByZxCrJYearCreditEvaItemList(dbQuery);
		ZxCrJYearCreditEvaItem evaItem;
		if (CollectionUtils.isEmpty(yearCreditEvaItems)) {
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				return;
			}
			evaItem = new ZxCrJYearCreditEvaItem();
			//组织机构代码证
			evaItem.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			//协作单位
			evaItem.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			evaItem.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			//协作单位负责人
			evaItem.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			//工程所属项目
			evaItem.setProjectName(zxCrProjectEvaluation.getOrgName());
			//专业类别ID
			evaItem.setCatID(zxCrProjectEvaluation.getCatID());
			//分类id
			evaItem.setResID(zxCrProjectEvaluation.getResID());
			//评价期次年分
			evaItem.setPeriodYear(String.valueOf(year));
			evaItem.setProjectID(zxCrProjectEvaluation.getProjectId());
			evaItem.setComID(zxCrProjectEvaluation.getCompanyId());
			//随机主键ID
			evaItem.setZxCrJYearCreditEvaItemId(UuidUtil.generate());
			this.jYearCreditEvaItemUpdate(evaItem, projectEvaluations);
			zxCrJYearCreditEvaItemMapper.insertInit(evaItem);
		} else {
			evaItem = yearCreditEvaItems.get(0);
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				zxCrJYearCreditEvaItemMapper.deleteByPrimaryKey(evaItem.getZxCrJYearCreditEvaItemId());
			}
			this.jYearCreditEvaItemUpdate(evaItem, projectEvaluations);
			zxCrJYearCreditEvaItemMapper.updateByPrimaryKey(evaItem);
		}
	}

	private void jYearCreditEvaItemUpdate(ZxCrJYearCreditEvaItem evaItem, List<ZxCrProjectEvaluation> projectEvaluations) {
		// 上半年
		List<ZxCrProjectEvaluation> firstHalfYear = projectEvaluations.stream().filter(p -> p.getPeriodQuarter() <= 2).collect(Collectors.toList());
		// 下半年
		List<ZxCrProjectEvaluation> secondHalfYear = projectEvaluations.stream().filter(p -> p.getPeriodQuarter() > 2).collect(Collectors.toList());
		//合同总额
		evaItem.setContractAmt(projectEvaluations.stream().map(p -> p.getContractAmt()).reduce(BigDecimal.ZERO,BigDecimal::add));
		//信用评价根据分数定等级
		BigDecimal bd100= new BigDecimal(100);
		BigDecimal bd90= new BigDecimal(90);
		BigDecimal bd75= new BigDecimal(75);
		BigDecimal bd60= new BigDecimal(60);
		//局信用评价得分（上半年综合平均）
		BigDecimal bdFirstSoce = firstHalfYear.stream().map(p -> p.getTotalScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
		if(bdFirstSoce == null) {
			bdFirstSoce = new BigDecimal(0);
		}
		evaItem.setFirstSoce(bdFirstSoce.toString());
		//（上半年综合平均）信用评价根据分数定等级
		String Grade1 ="";
		if (bdFirstSoce.compareTo(bd100) == -1 && bdFirstSoce.compareTo(bd90) > -1) {
			Grade1 ="A";
		} else if (bdFirstSoce.compareTo(bd90) == -1 && bdFirstSoce.compareTo(bd75) > -1) {
			Grade1 ="B";
		} else if (bdFirstSoce.compareTo(bd75) == -1 && bdFirstSoce.compareTo(bd60) > -1) {
			Grade1 ="C";
		} else if (bdFirstSoce.compareTo(bd60) == -1) {
			Grade1 ="D";
		}
		//局评价等级（上半年综合平均）
		evaItem.setFirstLevel(Grade1);
		//局信用评价得分（下半年综合平均）
		BigDecimal bdSecondScore = secondHalfYear.stream().map(p -> p.getTotalScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (bdSecondScore == null) {
			bdSecondScore = new BigDecimal(0);
		}
		evaItem.setSecondScore(bdSecondScore.toString());
		//（下半年综合平均）信用评价根据分数定等级
		String Grade2 = "";
		if (bdSecondScore.compareTo(bd100) == -1 && bdSecondScore.compareTo(bd90) > -1) {
			Grade2 ="A";
		} else if (bdSecondScore.compareTo(bd90) == -1 && bdSecondScore.compareTo(bd75) > -1) {
			Grade2 ="B";
		} else if (bdSecondScore.compareTo(bd75) == -1 && bdSecondScore.compareTo(bd60) > -1) {
			Grade2 ="C";
		} else if (bdSecondScore.compareTo(bd60) == -1) {
			Grade2 ="D";
		}
		//局评价等级（下半年综合平均）
		evaItem.setSecondLevel(Grade2);
		//有无直接降为D的行为
		if (evaItem.getFirstLevel().equals("D") || evaItem.getSecondLevel().equals("D")) {
			evaItem.setdLevel("D");
		} else {
			evaItem.setdLevel("-");
		}
		//最终信用评价得分
		BigDecimal bdLastScore = bdFirstSoce.add(bdSecondScore).divide(new BigDecimal(2), 6, BigDecimal.ROUND_HALF_UP);
		evaItem.setLastScore(bdLastScore.toString());
		String Grade = "";
		if (bdLastScore.compareTo(bd100) == -1 && bdLastScore.compareTo(bd90) > -1) {
			Grade ="A";
		} else if (bdLastScore.compareTo(bd90) == -1 && bdLastScore.compareTo(bd75) > -1) {
			Grade ="B";
		} else if (bdLastScore.compareTo(bd75) == -1 && bdLastScore.compareTo(bd60) > -1) {
			Grade ="C";
		} else if (bdLastScore.compareTo(bd60) == -1) {
			Grade ="D";
		}
		//信用评价等级
		evaItem.setLastLevel(Grade);
		//加减分项得分
		evaItem.setScoreOfAdditionSubtraction(evaItem.getScoreOfAdditionSubtraction());
		//相关部门（单位）修正分值
		evaItem.setRevisedScoresOfRelevantDepartments(evaItem.getRevisedScoresOfRelevantDepartments());
		//在省市级工程质量安全监督机构及相关部门的检查中被通报
		evaItem.setInformedDuringInspection(evaItem.getInformedDuringInspection());
	}

}
