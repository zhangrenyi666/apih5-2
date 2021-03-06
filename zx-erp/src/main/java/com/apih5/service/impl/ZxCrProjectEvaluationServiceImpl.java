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
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrProjectEvaluation.setCompanyId("");
        	zxCrProjectEvaluation.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCrProjectEvaluation.setCompanyId(zxCrProjectEvaluation.getOrgID());
        	zxCrProjectEvaluation.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCrProjectEvaluation.setProjectId(zxCrProjectEvaluation.getOrgID());
        }
		// ????????????
		PageHelper.startPage(zxCrProjectEvaluation.getPage(), zxCrProjectEvaluation.getLimit());
		// ????????????
		List<ZxCrProjectEvaluation> zxCrProjectEvaluationList = zxCrProjectEvaluationMapper
				.selectByZxCrProjectEvaluationList(zxCrProjectEvaluation);

		// ?????????????????????
		for (ZxCrProjectEvaluation zxCrProjectEvaluation1 : zxCrProjectEvaluationList) {
			ZxCrProjectEvaluationScore zxCrProjectEvaluationScore = new ZxCrProjectEvaluationScore();
			zxCrProjectEvaluationScore.setMainID(zxCrProjectEvaluation1.getZxCrProjectEvaluationId());
			List<ZxCrProjectEvaluationScore> projCreditScoreListAll = zxCrProjectEvaluationScoreMapper
					.selectByZxCrProjectEvaluationScoreList(zxCrProjectEvaluationScore);
			zxCrProjectEvaluation1.setProjectEvaluationScoreList(projCreditScoreListAll);
		}

		// ?????????????????????????????????
		for (ZxCrProjectEvaluation zxCrProjectEvaluation3 : zxCrProjectEvaluationList) {
			ZxCrProjectEvaluationBad zxCrProjectEvaluationBad = new ZxCrProjectEvaluationBad();
			zxCrProjectEvaluationBad.setMainID(zxCrProjectEvaluation3.getZxCrProjectEvaluationId());
			List<ZxCrProjectEvaluationBad> projCreditBadBehaListAll = zxCrProjectEvaluationBadMapper
					.selectByZxCrProjectEvaluationBadList(zxCrProjectEvaluationBad);
			zxCrProjectEvaluation3.setProjectEvaluationBadList(projCreditBadBehaListAll);
		}

		// ????????????
		for (ZxCrProjectEvaluation zxCrProjectEvaluation2 : zxCrProjectEvaluationList) {
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(zxCrProjectEvaluation2.getZxCrProjectEvaluationId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
			zxCrProjectEvaluation2.setFileList(zxErpFiles);
		}
		// ??????????????????
		PageInfo<ZxCrProjectEvaluation> p = new PageInfo<>(zxCrProjectEvaluationList);
		return repEntity.okList(zxCrProjectEvaluationList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationDetail(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		if (zxCrProjectEvaluation == null) {
			zxCrProjectEvaluation = new ZxCrProjectEvaluation();
		}
		// ????????????
		ZxCrProjectEvaluation dbZxCrProjectEvaluation = zxCrProjectEvaluationMapper
				.selectByPrimaryKey(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
		// ????????????
		if (dbZxCrProjectEvaluation != null) {
			// ??????
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(zxCrProjectEvaluation.getZxCrProjectEvaluationId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
			zxCrProjectEvaluation.setFileList(zxErpFiles);
			return repEntity.ok(dbZxCrProjectEvaluation);
		} else {
			return repEntity.layerMessage("no", "????????????");
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
		// ????????????????????????????????????Check
		String[] array = zxCrProjectEvaluation.getContractAmt().toString().split("\\.");
		if (array.length > 1) {
			if (array[0].length() > 10 || array[1].length() > 6) {
				return repEntity.layerMessage("no", "??????????????????????????????????????????????????????10????????????????????????????????????6????????????????????????");
			}
		} else {
			if (array[0].length() > 10) {
				return repEntity.layerMessage("no", "??????????????????????????????????????????????????????10????????????????????????????????????6????????????????????????");
			}
		}
		if (zxCrProjectEvaluation.getCheckStandard().equals("1")) {
			// ?????????????????????
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
			// ?????????????????????????????????
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
		// ????????????
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
			// ??????id
			dbZxCrProjectEvaluation.setOrgId(zxCrProjectEvaluation.getOrgId());
			// ????????????
			dbZxCrProjectEvaluation.setOrgName(zxCrProjectEvaluation.getOrgName());
			// ????????????id
			dbZxCrProjectEvaluation.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			// ??????????????????
			dbZxCrProjectEvaluation.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			// ?????????????????????
			dbZxCrProjectEvaluation.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			// ????????????
			dbZxCrProjectEvaluation.setResCode(zxCrProjectEvaluation.getResCode());
			// ??????ID
			dbZxCrProjectEvaluation.setResID(zxCrProjectEvaluation.getResID());
			// ????????????
			dbZxCrProjectEvaluation.setResName(zxCrProjectEvaluation.getResName());
			// ??????????????????
			dbZxCrProjectEvaluation.setCatCode(zxCrProjectEvaluation.getCatCode());
			// ????????????id
			dbZxCrProjectEvaluation.setCatID(zxCrProjectEvaluation.getCatID());
			// ????????????
			dbZxCrProjectEvaluation.setCatName(zxCrProjectEvaluation.getCatName());
			// ????????????
			dbZxCrProjectEvaluation.setPeriod(zxCrProjectEvaluation.getPeriod());
			// ????????????
			dbZxCrProjectEvaluation.setCheckDate(zxCrProjectEvaluation.getCheckDate());
			// ????????????
			dbZxCrProjectEvaluation.setInDate(zxCrProjectEvaluation.getInDate());
			// ????????????
			dbZxCrProjectEvaluation.setOutDate(zxCrProjectEvaluation.getOutDate());
			// ?????????????????????????????????
			dbZxCrProjectEvaluation.setContractAmt(zxCrProjectEvaluation.getContractAmt());
			// ???????????????
			dbZxCrProjectEvaluation.setTotalScore(zxCrProjectEvaluation.getTotalScore());
			// ?????????????????????
			dbZxCrProjectEvaluation.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			// editTime
			dbZxCrProjectEvaluation.setEditTime(zxCrProjectEvaluation.getEditTime());
			// comID
			dbZxCrProjectEvaluation.setComID(zxCrProjectEvaluation.getComID());
			// comName
			dbZxCrProjectEvaluation.setComName(zxCrProjectEvaluation.getComName());
			// comOrders
			dbZxCrProjectEvaluation.setComOrders(zxCrProjectEvaluation.getComOrders());
			// ????????????
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// ?????????
			dbZxCrProjectEvaluation.setPreparer(zxCrProjectEvaluation.getPreparer());
			// ?????????
			dbZxCrProjectEvaluation.setAuditor(zxCrProjectEvaluation.getAuditor());
			// ????????????
			dbZxCrProjectEvaluation.setCheckStandard(zxCrProjectEvaluation.getCheckStandard());
			// ?????????????????????
			dbZxCrProjectEvaluation.setChargeManPhone(zxCrProjectEvaluation.getChargeManPhone());
			// ??????
			dbZxCrProjectEvaluation.setRemarks(zxCrProjectEvaluation.getRemarks());
			// ??????
			dbZxCrProjectEvaluation.setSort(zxCrProjectEvaluation.getSort());
			// ??????
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			flag = zxCrProjectEvaluationMapper.updateByPrimaryKey(dbZxCrProjectEvaluation);
			if (zxCrProjectEvaluation.getCheckStandard().equals("1")) {
				// ???????????????(???????????????)
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
				// ???????????????(???????????????????????????)
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
			// ???????????????(??????)
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
		// ??????
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
				// ?????????????????????
				ZxCrProjectEvaluationScore zxCrProjCreditScoresSelect = new ZxCrProjectEvaluationScore();
				zxCrProjCreditScoresSelect.setMainID(ZxCrProjectEvaluation2.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationScore> zxCrProjCreditScore = zxCrProjectEvaluationScoreMapper
						.selectByZxCrProjectEvaluationScoreList(zxCrProjCreditScoresSelect);
				if (zxCrProjCreditScore != null && zxCrProjCreditScore.size() > 0) {
					zxCrProjCreditScoresSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationScoreMapper.batchDeleteUpdateZxCrProjectEvaluationScore(zxCrProjCreditScore,
							zxCrProjCreditScoresSelect);
				}
				// ?????????????????????????????????
				ZxCrProjectEvaluationBad zxCrProjCreditBadBehaSelect = new ZxCrProjectEvaluationBad();
				zxCrProjCreditBadBehaSelect.setMainID(ZxCrProjectEvaluation2.getZxCrProjectEvaluationId());
				List<ZxCrProjectEvaluationBad> ZxCrProjCreditBadBeha = zxCrProjectEvaluationBadMapper
						.selectByZxCrProjectEvaluationBadList(zxCrProjCreditBadBehaSelect);
				if (ZxCrProjCreditBadBeha != null && ZxCrProjCreditBadBeha.size() > 0) {
					zxCrProjCreditBadBehaSelect.setModifyUserInfo(userKey, realName);
					zxCrProjectEvaluationBadMapper.batchDeleteUpdateZxCrProjectEvaluationBad(ZxCrProjCreditBadBeha,
							zxCrProjCreditBadBehaSelect);
				}
				// ????????????
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
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCrProjectEvaluationList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity getZxCrCustomerInfoList() {
		// ????????????
		List<ZxCrCustomerInfo> zxCrCustomerInfoList = zxCrCustomerInfoMapper.selectAll();
		// ??????????????????
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
			// ????????????
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// ??????
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			// ?????????
			dbZxCrProjectEvaluation.setAuditor(realName);
			flag = zxCrProjectEvaluationMapper.updateByAuditStatus(dbZxCrProjectEvaluation);
			this.updateHalfYearCreditEvaItem(dbZxCrProjectEvaluation);
			this.updateCrJYearCreditEvaItem(dbZxCrProjectEvaluation);
		}
		// ??????
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
			// ????????????
			dbZxCrProjectEvaluation.setAuditStatus(zxCrProjectEvaluation.getAuditStatus());
			// ??????
			dbZxCrProjectEvaluation.setModifyUserInfo(userKey, realName);
			flag = zxCrProjectEvaluationMapper.updateByAuditStatusOut(dbZxCrProjectEvaluation);
			this.updateHalfYearCreditEvaItem(dbZxCrProjectEvaluation);
			this.updateCrJYearCreditEvaItem(dbZxCrProjectEvaluation);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCrProjectEvaluation);
		}
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationListByCatName() {
		ZxCrColCategory zxCrColCategory = new ZxCrColCategory();
		// ??????????????????
		List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryDear(zxCrColCategory);
		// ??????????????????
		PageInfo<ZxCrColCategory> p = new PageInfo<>(zxCrColCategoryList);
		return repEntity.okList(zxCrColCategoryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCrProjectEvaluationListByResName(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		if (StrUtil.isEmpty(zxCrProjectEvaluation.getParentID())) {
			return repEntity.layerMessage("no", "???????????????????????????");
		}
		ZxCrColResource zxCrColResource = new ZxCrColResource();
		zxCrColResource.setCategoryID(zxCrProjectEvaluation.getCatID());
		// ??????????????????
		List<ZxCrColResource> zxCrColResourceList = zxCrColResourceMapper.selectByZxCrColResourceList(zxCrColResource);
		// ??????????????????
		PageInfo<ZxCrColResource> p = new PageInfo<>(zxCrColResourceList);
		return repEntity.okList(zxCrColResourceList, p.getTotal());
	}

	//????????????????????????????????????
	private void updateHalfYearCreditEvaItem(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		String year = zxCrProjectEvaluation.getPeriod().substring(0,4);
		String quarter = zxCrProjectEvaluation.getPeriod().substring(4,6);
		// ??? ??????,??????,????????????,????????????,???????????????????????????,?????????????????????????????????????????????
		ZxCrProjectEvaluation projectQuery = new ZxCrProjectEvaluation();
		projectQuery.setPeriodYear(year);
		projectQuery.setCompanyId(zxCrProjectEvaluation.getCompanyId());
		projectQuery.setProjectId(zxCrProjectEvaluation.getProjectId());
		projectQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		projectQuery.setCatID(zxCrProjectEvaluation.getCatID());
		projectQuery.setResID(zxCrProjectEvaluation.getResID());
		List<ZxCrProjectEvaluation> projectEvaluations;
		if (Integer.parseInt(quarter) <= 2) { // ???????????????
			projectEvaluations = zxCrProjectEvaluationMapper.selectFirstHalfYearProjectEvaluationList(projectQuery);
		} else { // ?????????
			projectEvaluations = zxCrProjectEvaluationMapper.selectSecondHalfYearProjectEvaluationList(projectQuery);
		}

		//??? ??????,??????,????????????,????????????,????????????,??????????????????????????????
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
		// ???????????? ?????????
		if (CollectionUtils.isEmpty(halfYearCreditEvaItems)) {
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				return;
			}
			evaItem = new ZxCrHalfYearCreditEvaItem();
			//?????????????????????
			evaItem.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			//????????????
			evaItem.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			evaItem.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			//?????????????????????
			evaItem.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			//??????????????????
			evaItem.setProjectName(zxCrProjectEvaluation.getOrgName());
//			//????????????
//			evaItem.setInDate(zxCrProjectEvaluation.getInDate());
//			//????????????
//			evaItem.setOutDate(zxCrProjectEvaluation.getOutDate());
//			//????????????
//			evaItem.setContractAmt(zxCrProjectEvaluation.getContractAmt());
			//????????????ID
			evaItem.setCatID(zxCrProjectEvaluation.getCatID());
			//??????id
			evaItem.setResID(zxCrProjectEvaluation.getResID());
			//??????????????????
			evaItem.setPeriodYear(String.valueOf(year));
			//??????????????????1 12?????? 2 34??????
			evaItem.setQuartEvalID(Integer.parseInt(quarter) <= 2 ? "1" : "2");
			evaItem.setProjectID(zxCrProjectEvaluation.getProjectId());
			evaItem.setComID(zxCrProjectEvaluation.getCompanyId());
			evaItem.setCompanyId(zxCrProjectEvaluation.getCompanyId());
			// ????????????????????????
			this.halfYearEvaluationUpdate(evaItem, projectEvaluations);
			//????????????ID
			evaItem.setZxCrHalfYearCreditEvaItemId(UuidUtil.generate());
			zxCrHalfYearCreditEvaItemMapper.insertInit(evaItem);
		} else {
			evaItem = halfYearCreditEvaItems.get(0);
			if (CollectionUtils.isEmpty(projectEvaluations)) {
				zxCrHalfYearCreditEvaItemMapper.deleteByPrimaryKey(evaItem.getZxCrHalfYearCreditEvaItemId());
			}
			// ????????????????????????
			this.halfYearEvaluationUpdate(evaItem, projectEvaluations);
			zxCrHalfYearCreditEvaItemMapper.updateByPrimaryKey(evaItem);
		}
	}

	// ??????????????????????????????
	private void halfYearEvaluationUpdate(ZxCrHalfYearCreditEvaItem evaItem, List<ZxCrProjectEvaluation> projectList) {
		Map<String, List<ZxCrProjectEvaluation>> projectMap = projectList.stream().collect(Collectors.groupingBy(p -> p.getPeriod()));
		evaItem.setContractAmt(projectList.stream().map(p -> p.getContractAmt()).reduce(BigDecimal.ZERO,BigDecimal::add));
		for (String period : projectMap.keySet()) {
			List<ZxCrProjectEvaluation> projectEvaluations = projectMap.get(period);
			BigDecimal bd100= new BigDecimal(100);
			BigDecimal bd90= new BigDecimal(90);
			BigDecimal bd75= new BigDecimal(75);
			BigDecimal bd60= new BigDecimal(60);
			//?????????????????????
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
			//??????????????????D?????????
			if (evaItem.getFirstLevel().equals("D") || evaItem.getSecondLevel().equals("D")) {
				evaItem.setdLevel("D");
			} else {
				evaItem.setdLevel("-");
			}
			//??????????????????
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
			//??????????????????
			if (bdLastScore.compareTo(new BigDecimal(60)) == 1) {
				evaItem.setLastLevel("D");
			}
		}
	}

	// ???????????????????????????
	private void updateCrJYearCreditEvaItem(ZxCrProjectEvaluation zxCrProjectEvaluation) {
		String year = zxCrProjectEvaluation.getPeriod().substring(0,4);
		String quarter = zxCrProjectEvaluation.getPeriod().substring(4,6);
		// ??? ??????,??????,????????????,????????????,??????????????????,??????????????????????????????
		ZxCrProjectEvaluation projectQuery = new ZxCrProjectEvaluation();
		projectQuery.setCompanyId(zxCrProjectEvaluation.getCompanyId());
		projectQuery.setProjectId(zxCrProjectEvaluation.getProjectId());
		projectQuery.setCustomerId(zxCrProjectEvaluation.getCustomerId());
		projectQuery.setCatID(zxCrProjectEvaluation.getCatID());
		projectQuery.setResID(zxCrProjectEvaluation.getResID());
		projectQuery.setPeriodYear(year);
		projectQuery.setAuditStatus("1");
		List<ZxCrProjectEvaluation> projectEvaluations = zxCrProjectEvaluationMapper.selectByZxCrProjectEvaluationList(projectQuery);
		//??? ??????,??????,????????????,????????????,??????????????????,?????????????????????????????????
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
			//?????????????????????
			evaItem.setOrgCertificate(zxCrProjectEvaluation.getOrgCertificate());
			//????????????
			evaItem.setCustomerId(zxCrProjectEvaluation.getCustomerId());
			evaItem.setCustomerName(zxCrProjectEvaluation.getCustomerName());
			//?????????????????????
			evaItem.setChargeMan(zxCrProjectEvaluation.getChargeMan());
			//??????????????????
			evaItem.setProjectName(zxCrProjectEvaluation.getOrgName());
			//????????????ID
			evaItem.setCatID(zxCrProjectEvaluation.getCatID());
			//??????id
			evaItem.setResID(zxCrProjectEvaluation.getResID());
			//??????????????????
			evaItem.setPeriodYear(String.valueOf(year));
			evaItem.setProjectID(zxCrProjectEvaluation.getProjectId());
			evaItem.setComID(zxCrProjectEvaluation.getCompanyId());
			//????????????ID
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
		// ?????????
		List<ZxCrProjectEvaluation> firstHalfYear = projectEvaluations.stream().filter(p -> p.getPeriodQuarter() <= 2).collect(Collectors.toList());
		// ?????????
		List<ZxCrProjectEvaluation> secondHalfYear = projectEvaluations.stream().filter(p -> p.getPeriodQuarter() > 2).collect(Collectors.toList());
		//????????????
		evaItem.setContractAmt(projectEvaluations.stream().map(p -> p.getContractAmt()).reduce(BigDecimal.ZERO,BigDecimal::add));
		//?????????????????????????????????
		BigDecimal bd100= new BigDecimal(100);
		BigDecimal bd90= new BigDecimal(90);
		BigDecimal bd75= new BigDecimal(75);
		BigDecimal bd60= new BigDecimal(60);
		//????????????????????????????????????????????????
		BigDecimal bdFirstSoce = firstHalfYear.stream().map(p -> p.getTotalScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
		if(bdFirstSoce == null) {
			bdFirstSoce = new BigDecimal(0);
		}
		evaItem.setFirstSoce(bdFirstSoce.toString());
		//????????????????????????????????????????????????????????????
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
		//??????????????????????????????????????????
		evaItem.setFirstLevel(Grade1);
		//????????????????????????????????????????????????
		BigDecimal bdSecondScore = secondHalfYear.stream().map(p -> p.getTotalScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (bdSecondScore == null) {
			bdSecondScore = new BigDecimal(0);
		}
		evaItem.setSecondScore(bdSecondScore.toString());
		//????????????????????????????????????????????????????????????
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
		//??????????????????????????????????????????
		evaItem.setSecondLevel(Grade2);
		//??????????????????D?????????
		if (evaItem.getFirstLevel().equals("D") || evaItem.getSecondLevel().equals("D")) {
			evaItem.setdLevel("D");
		} else {
			evaItem.setdLevel("-");
		}
		//????????????????????????
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
		//??????????????????
		evaItem.setLastLevel(Grade);
		//??????????????????
		evaItem.setScoreOfAdditionSubtraction(evaItem.getScoreOfAdditionSubtraction());
		//????????????????????????????????????
		evaItem.setRevisedScoresOfRelevantDepartments(evaItem.getRevisedScoresOfRelevantDepartments());
		//??????????????????????????????????????????????????????????????????????????????
		evaItem.setInformedDuringInspection(evaItem.getInformedDuringInspection());
	}

}
